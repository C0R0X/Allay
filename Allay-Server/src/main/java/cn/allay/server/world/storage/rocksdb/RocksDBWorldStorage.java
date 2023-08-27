package cn.allay.server.world.storage.rocksdb;

import cn.allay.api.server.Server;
import cn.allay.api.world.Difficulty;
import cn.allay.api.world.DimensionInfo;
import cn.allay.api.world.WorldData;
import cn.allay.api.world.chunk.Chunk;
import cn.allay.api.world.gamerule.GameRule;
import cn.allay.api.world.gamerule.GameRules;
import cn.allay.api.world.storage.WorldStorage;
import cn.allay.api.world.storage.WorldStorageException;
import cn.allay.server.utils.LevelDBKey;
import cn.allay.server.world.chunk.AllayChunk;
import cn.allay.server.world.chunk.AllayUnsafeChunk;
import org.cloudburstmc.nbt.NbtMap;
import org.cloudburstmc.nbt.NbtMapBuilder;
import org.cloudburstmc.nbt.NbtUtils;
import org.cloudburstmc.protocol.bedrock.data.GameType;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3i;
import org.rocksdb.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.concurrent.CompletableFuture;

/**
 * Allay Project 8/22/2023
 *
 * @author Cool_Loong
 */
public class RocksDBWorldStorage implements WorldStorage, AutoCloseable {
    static {
        RocksDB.loadLibrary();
    }

    private WorldData worldDataCache;
    private final Path path;
    private final RocksDB db;

    public RocksDBWorldStorage(Path path) throws WorldStorageException {
        this(path, new Options().setCreateIfMissing(true));
    }

    public RocksDBWorldStorage(Path path, Options options) throws WorldStorageException {
        try {
            this.path = path;
            File dbFolder = path.resolve("db").toFile();
            if (!dbFolder.exists()) dbFolder.mkdirs();
            db = RocksDB.open(options, dbFolder.getAbsolutePath());
        } catch (RocksDBException e) {
            throw new WorldStorageException(e);
        }
        worldDataCache = readWorldData();
    }


    @Override
    public CompletableFuture<Chunk> readChunk(int x, int z) throws WorldStorageException {
        return CompletableFuture.supplyAsync(() -> {
            AllayUnsafeChunk.Builder builder = AllayUnsafeChunk.builder().chunkX(x).chunkZ(z).dimensionInfo(getWorldDataCache().getDimensionInfo());
            try {
                byte[] versionValue = this.db.get(LevelDBKey.VERSION.getKey(x, z));
                if (versionValue == null || versionValue.length != 1) {
                    return null;
                }
//                byte[] finalizationState = this.db.get(LevelDBKey.CHUNK_FINALIZED_STATE.getKey(x, z));
//                if (finalizationState == null) {
//                    builder.state(UnsafeChunk.STATE_FINISHED);
//                } else {
//                    builder.state(Unpooled.wrappedBuffer(finalizationState).readIntLE() + 1);
//                }
                byte chunkVersion = versionValue[0];
                RocksdbChunkSerializer serializer = RocksdbChunkSerializer.Provider.of(chunkVersion);
                serializer.deserialize(this.db, builder);
                return new AllayChunk(builder.build());
            } catch (RocksDBException e) {
                throw new RuntimeException(e);
            }
        }, Server.getInstance().getVirtualThreadPool());
    }

    @Override
    public CompletableFuture<Void> writeChunk(Chunk chunk) throws WorldStorageException {
        return CompletableFuture.supplyAsync(() -> {
            RocksdbChunkSerializer serializer = RocksdbChunkSerializer.Provider.of(0);
            try (WriteBatch writeBatch = new WriteBatch()) {
                writeBatch.put(LevelDBKey.VERSION.getKey(chunk.getX(), chunk.getZ()), new byte[]{0});
                chunk.batchProcess(
                        (l1, l2, l3, c) -> {
                            long stamp1 = l1.writeLock();
                            long stamp2 = l2.writeLock();
                            long stamp3 = l3.writeLock();
                            try {
                                serializer.serialize(writeBatch, c);
                            } finally {
                                l1.unlockWrite(stamp1);
                                l2.unlockWrite(stamp2);
                                l3.unlockWrite(stamp3);
                            }
                        }
                );
                this.db.write(new WriteOptions(), writeBatch);
                return null;
            } catch (RocksDBException e) {
                throw new RuntimeException(e);
            }
        }, Server.getInstance().getVirtualThreadPool());
    }

    @Override
    public boolean containChunk(int x, int z) {
        return false;
    }

    @Override
    public synchronized void writeWorldData(WorldData worldData) {
        File levelDat = path.resolve("level.dat").toFile();
        try (var input = NbtUtils.createGZIPWriter(new FileOutputStream(levelDat))) {
            worldDataCache = worldData;
            input.writeTag(createWorldDataNBT(worldData));
        } catch (IOException e) {
            throw new WorldStorageException(e);
        }
    }

    @Override
    @NotNull
    public synchronized WorldData readWorldData() throws WorldStorageException {
        File levelDat = path.resolve("level.dat").toFile();
        try (var input = NbtUtils.createGZIPReader(new FileInputStream(levelDat))) {
            return createWorldData(((NbtMap) input.readTag()));
        } catch (IOException e) {
            throw new WorldStorageException(e);
        }
    }

    @Override
    public WorldData getWorldDataCache() {
        return worldDataCache;
    }

    @Override
    public void close() {
        db.close();
        worldDataCache = null;
    }

    private WorldData createWorldData(NbtMap data) {
        WorldData.WorldDataBuilder builder = WorldData.builder();
        NbtMap gameRulesNbt = data.getCompound("GameRules");
        GameRules gameRules = new GameRules();
        for (var key : gameRulesNbt.keySet()) {
            GameRule gameRule = GameRule.fromName(key);
            if (gameRule != null) {
                if (gameRule.getType() == GameRule.Type.INT) {
                    gameRules.put(gameRule, Integer.parseInt(gameRulesNbt.get(key).toString()));
                } else {
                    gameRules.put(gameRule, Boolean.parseBoolean(gameRulesNbt.get(key).toString()));
                }
            }
        }
        NbtMap dimensionInfo = data.getCompound("DimensionInfo");
        return builder.gameRules(gameRules)
                .spawnPoint(new Vector3i(data.getInt("SpawnX"), data.getInt("SpawnY"), data.getInt("SpawnZ")))
                .difficulty(Difficulty.getDifficulty(data.getInt("Difficulty")))
                .time(data.getLong("Time"))
                .lightningTick(data.getInt("thunderTime"))
                .rainTick(data.getInt("rainTime"))
                .gameType(GameType.from(data.getInt("GameType")))
                .levelName(data.getString("LevelName"))
                .dimensionInfo(new DimensionInfo(dimensionInfo.getInt("dimensionId"),
                        dimensionInfo.getInt("minHeight"),
                        dimensionInfo.getInt("maxHeight"),
                        dimensionInfo.getInt("chunkSectionSize")
                ))
                .tickingRadius(data.getInt("tickingRadius"))
                .viewDistance(data.getInt("viewDistance"))
                .generatorOptions(data.getString("generatorOptions"))
                .randomSeed(data.getLong("seed"))
                .build();
    }

    private NbtMap createWorldDataNBT(WorldData data) {
        NbtMapBuilder builder = NbtMap.builder();
        GameRules gameRules = data.getGameRules();

        NbtMapBuilder builder2 = NbtMap.builder();
        for (var entry : gameRules.getGameRules().entrySet()) {
            builder2.putString(entry.getKey().getName(), entry.getValue().toString());
        }

        NbtMapBuilder builder3 = NbtMap.builder();
        builder3.putInt("dimensionId", data.getDimensionInfo().dimensionId())
                .putInt("minHeight", data.getDimensionInfo().minHeight())
                .putInt("maxHeight", data.getDimensionInfo().maxHeight())
                .putInt("chunkSectionSize", data.getDimensionInfo().chunkSectionSize());

        return builder.putCompound("GameRules", builder2.build())
                .putInt("SpawnX", data.getSpawnPoint().x())
                .putInt("SpawnY", data.getSpawnPoint().y())
                .putInt("SpawnZ", data.getSpawnPoint().z())
                .putInt("Difficulty", data.getDifficulty().ordinal())
                .putLong("Time", data.getTime())
                .putLong("thunderTime", data.getLightningTick())
                .putLong("rainTime", data.getRainTick())
                .putInt("GameType", data.getGameType().ordinal())
                .putInt("tickingRadius", data.getTickingRadius())
                .putInt("viewDistance", data.getViewDistance())
                .putString("LevelName", data.getLevelName())
                .putString("generatorOptions", data.getGeneratorOptions())
                .putLong("seed", data.getRandomSeed())
                .putCompound("DimensionInfo", builder3.build())
                .build();
    }
}