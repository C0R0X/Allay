package org.allaymc.server.pack;

import com.google.common.base.Preconditions;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.allaymc.api.i18n.I18n;
import org.allaymc.api.i18n.TrKeys;
import org.allaymc.api.pack.Pack;
import org.allaymc.api.pack.PackLoader;
import org.allaymc.api.pack.PackManifest;
import org.allaymc.api.pack.PackRegistry;
import org.allaymc.api.registry.SimpleMappedRegistry;
import org.allaymc.api.server.Server;
import org.allaymc.server.pack.defaults.ResourcePack;
import org.allaymc.server.pack.loader.ZipPackLoader;
import org.cloudburstmc.protocol.bedrock.packet.ResourcePackStackPacket;
import org.cloudburstmc.protocol.bedrock.packet.ResourcePacksInfoPacket;
import org.jetbrains.annotations.UnmodifiableView;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Allay Project 28/01/2024
 *
 * @author IWareQ
 */
@Slf4j
public class AllayPackRegistry extends SimpleMappedRegistry<UUID, Pack, Map<UUID, Pack>> implements PackRegistry {

    private final List<PackLoader.Factory> packLoaders = new ArrayList<>();
    private final Map<Pack.Type, Pack.Factory> packFactories = new EnumMap<>(Pack.Type.class);

    private final ResourcePacksInfoPacket packsInfos = new ResourcePacksInfoPacket();
    private final ResourcePackStackPacket packStack = new ResourcePackStackPacket();

    private final Path PACKS_PATH = Path.of("resource_packs");
    private final List<String> EXCLUDED_FORMATS = List.of(".tmp", ".key", ".bak");

    public AllayPackRegistry() {
        super(null, i -> new ConcurrentHashMap<>());
        // Just left a '*' here, if we put in an exact game version,
        // it is possible that client won't send back ResourcePackClientResponsePacket(packIds=[*], status=COMPLETED)
        this.packStack.setGameVersion("*");
        this.init();
    }

    @SneakyThrows
    private void init() {
        if (Files.exists(PACKS_PATH) && Server.SETTINGS.resourcePackSettings().autoEncrypt()) {
            encryptPacks();
        } else {
            Files.createDirectories(PACKS_PATH);
        }

        this.registerLoaderFactory(ZipPackLoader.FACTORY);
        // todo: more pack loaders

        this.registerPackFactory(Pack.Type.RESOURCES, ResourcePack.FACTORY);
        // todo: more pack factories

        log.info(I18n.get().tr(TrKeys.A_PACK_LOADING));
        this.loadResourcePacks(PACKS_PATH);
    }

    @SneakyThrows
    private void encryptPacks() {
        log.info(I18n.get().tr(TrKeys.A_PACK_AUTOENCRYPT_ENABLED));
        try (var stream = Files.newDirectoryStream(PACKS_PATH)) {
            for (var zipPack : stream) {
                if (!PackUtils.isZipPack(zipPack)) continue;
                var keyPath = PACKS_PATH.resolve(zipPack.getFileName().toString() + ".key");
                if (Files.exists(keyPath)) continue;
                log.info(I18n.get().tr(TrKeys.A_PACK_ENCRYPTING, zipPack.getFileName()));
                var backupPath = PACKS_PATH.resolve(zipPack.getFileName().toString() + ".bak");
                Files.copy(zipPack, backupPath, StandardCopyOption.REPLACE_EXISTING);
                var tmpPath = PACKS_PATH.resolve(zipPack.getFileName().toString() + ".tmp");
                Files.deleteIfExists(tmpPath);
                String key;
                try {
                    Files.move(zipPack, tmpPath);
                    key = PackEncryptor.encrypt(tmpPath, zipPack);
                } finally {
                    Files.delete(tmpPath);
                }
                Files.writeString(keyPath, key);
                log.info(I18n.get().tr(TrKeys.A_PACK_ENCRYPTED, zipPack.getFileName(), key));
            }
        }
    }

    @Override
    public void loadResourcePacks(Path path) {
        Preconditions.checkNotNull(path, "path");
        Preconditions.checkArgument(Files.isDirectory(path), "%s is not a directory", path);

        // find loaders
        List<PackLoader> foundedLoaders = new ArrayList<>();
        try (var stream = Files.newDirectoryStream(path)) {
            for (var entry : stream) {
                if (isExcludedFormat(entry)) continue;
                var loader = this.findLoader(entry);
                if (loader == null) continue;
                foundedLoaders.add(loader);
            }
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        // Load manifests
        Map<PackLoader, PackManifest> loader2manifest = new HashMap<>();
        for (var loader : foundedLoaders) {
            var manifest = PackManifest.load(loader);
            if (manifest == null || !manifest.isValid()) continue;
            log.info(I18n.get().tr(TrKeys.A_PACK_LOADING_ENTRY, manifest.getHeader().getName()));
            loader2manifest.put(loader, manifest);
        }

        foundedLoaders.clear();

        loader2manifest.forEach((loader, manifest) -> {
            var module = manifest.getModules().getFirst();
            var factory = this.packFactories.get(module.getType());
            if (factory == null) {
                log.warn("Unsupported pack type {}", module.getType());
                return;
            }

            var uuid = manifest.getHeader().getUuid();
            var pack = factory.create(loader, manifest);
            this.register(uuid, pack);

            // prepare for network
            loader.getNetworkPreparedFile();
            log.info(I18n.get().tr(TrKeys.A_PACK_LOADED_ENTRY, pack.getName()));
        });

        if (!this.getContent().isEmpty()) this.generatePackets();

        log.info(I18n.get().tr(TrKeys.A_PACK_LOADED, this.getContent().size()));
    }

    @Override
    public ResourcePackStackPacket getPackStackPacket() {
        return this.packStack;
    }

    @Override
    public ResourcePacksInfoPacket getPacksInfoPacket() {
        return this.packsInfos;
    }

    private PackLoader findLoader(Path path) throws IOException {
        PackLoader loader = null;
        for (var factory : this.packLoaders) {
            if (factory.canLoad(path)) {
                loader = factory.create(path);
                break;
            }
        }

        if (loader == null) log.warn("Could not load '{}' due to format not recognized", path);
        return loader;
    }

    private boolean isExcludedFormat(Path path) {
        var pathStr = path.toString();
        return EXCLUDED_FORMATS.stream().anyMatch(pathStr::endsWith);
    }

    private void generatePackets() {
        var forceResourcePacks = Server.SETTINGS.resourcePackSettings().forceResourcePacks();
        this.packsInfos.setForcedToAccept(forceResourcePacks);
        this.packsInfos.getBehaviorPackInfos().clear();
        this.packsInfos.getResourcePackInfos().clear();

        this.packStack.setForcedToAccept(forceResourcePacks);
        this.packStack.getBehaviorPacks().clear();
        this.packStack.getResourcePacks().clear();

        for (var pack : this.getContent().values()) {
            var id = pack.getId().toString();
            var version = pack.getStringVersion();
            var type = pack.getType();

            var packEntry = new ResourcePacksInfoPacket.Entry(
                    id,
                    version,
                    pack.getSize(),
                    pack.getContentKey(),
                    "", // TODO: Sub pack name
                    id,
                    type == Pack.Type.SCRIPT,
                    pack.getManifest().getCapabilities().contains(PackManifest.Capability.RAYTRACED)
            );
            var stackEntry = new ResourcePackStackPacket.Entry(id, version, "");
            if (type == Pack.Type.RESOURCES) {
                packsInfos.getResourcePackInfos().add(packEntry);
                packStack.getResourcePacks().add(stackEntry);
            } else if (type == Pack.Type.DATA || type == Pack.Type.SCRIPT) {
                packsInfos.getBehaviorPackInfos().add(packEntry);
                packStack.getBehaviorPacks().add(stackEntry);
            }
        }
    }

    @Override
    public void registerLoaderFactory(PackLoader.Factory factory) {
        this.packLoaders.add(factory);
    }

    @Override
    public @UnmodifiableView List<PackLoader.Factory> getLoadersFactory() {
        return Collections.unmodifiableList(this.packLoaders);
    }

    @Override
    public void registerPackFactory(Pack.Type packType, Pack.Factory factory) {
        this.packFactories.put(packType, factory);
    }

    @Override
    public @UnmodifiableView Map<Pack.Type, Pack.Factory> getPacksFactory() {
        return Collections.unmodifiableMap(this.packFactories);
    }
}
