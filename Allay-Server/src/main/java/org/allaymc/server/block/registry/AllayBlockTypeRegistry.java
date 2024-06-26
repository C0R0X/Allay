package org.allaymc.server.block.registry;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import me.tongfei.progressbar.ConsoleProgressBarConsumer;
import me.tongfei.progressbar.ProgressBar;
import org.allaymc.api.block.registry.BlockTypeRegistry;
import org.allaymc.api.block.type.BlockType;
import org.allaymc.api.utils.Identifier;
import org.allaymc.api.i18n.I18n;
import org.allaymc.api.i18n.TrKeys;
import org.allaymc.api.registry.SimpleMappedRegistry;
import org.allaymc.api.utils.ReflectionUtils;
import org.allaymc.server.block.type.BlockTypeDefaultInitializer;
import org.allaymc.server.block.type.BlockTypeInitializer;
import org.cloudburstmc.protocol.bedrock.data.definitions.BlockDefinition;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Allay Project 2023/3/26
 *
 * @author daoge_cmd
 */
@Slf4j
public final class AllayBlockTypeRegistry extends SimpleMappedRegistry<Identifier, BlockType<?>, Map<Identifier, BlockType<?>>> implements BlockTypeRegistry {
    public AllayBlockTypeRegistry() {
        super(null, input -> new ConcurrentHashMap<>());
    }

    @SneakyThrows
    public void init() {
        log.info(I18n.get().tr(TrKeys.A_BLOCKTYPE_LOADING));
        var defaultInitializers = ReflectionUtils.getAllStaticVoidParameterlessMethods(BlockTypeDefaultInitializer.class);
        var initializers = ReflectionUtils.getAllStaticVoidParameterlessMethods(BlockTypeInitializer.class);
        try (var progressBar = ProgressBar
                .builder()
                .setInitialMax(defaultInitializers.size())
                .setTaskName("Loading Block Types")
                .setConsumer(new ConsoleProgressBarConsumer(System.out))
                .build()) {
            initializers.forEach(method -> callInitializer(method, null));
            defaultInitializers.forEach(method -> callInitializer(method, progressBar));
        }
        rebuildDefinitionList();
        log.info(I18n.get().tr(TrKeys.A_BLOCKTYPE_LOADED, defaultInitializers.size()));
    }

    private static void callInitializer(Method method, ProgressBar progressBar) {
        try {
            method.invoke(null);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        } finally {
            if (progressBar != null)
                progressBar.step();
        }
    }

    private final List<BlockDefinition> blockDefinitions = new ArrayList<>();

    @Override
    public List<BlockDefinition> getBlockDefinitions() {
        return blockDefinitions;
    }

    private void rebuildDefinitionList() {
        blockDefinitions.clear();
        for (var blockType : this.getContent().values()) {
            blockType.getAllStates().forEach(state -> blockDefinitions.add(state.toNetworkBlockDefinition()));
        }
    }
}
