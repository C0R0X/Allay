package org.allaymc.server.world.storage;

import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import org.allaymc.api.blockentity.BlockEntity;
import org.allaymc.api.blockentity.BlockEntityHelper;
import org.allaymc.api.entity.Entity;
import org.allaymc.api.entity.EntityHelper;
import org.allaymc.api.server.Server;
import org.allaymc.api.utils.HashUtils;
import org.allaymc.api.utils.exception.WorldStorageException;
import org.allaymc.api.world.DimensionInfo;
import org.allaymc.api.world.WorldData;
import org.allaymc.api.world.chunk.Chunk;
import org.allaymc.api.world.storage.WorldStorage;
import org.allaymc.server.world.chunk.AllayUnsafeChunk;
import org.cloudburstmc.nbt.NbtMap;

import javax.annotation.concurrent.NotThreadSafe;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

/**
 * TODO: optimize it for better memory footprint
 * <p>
 * Allay Project 2023/7/1
 *
 * @author daoge_cmd
 */
@NotThreadSafe
public class AllayNonPersistentWorldStorage implements WorldStorage {
    private final Map<Long, Chunk> chunks = new Long2ObjectOpenHashMap<>();
    private final Map<Long, Set<NbtMap>> entities = new Long2ObjectOpenHashMap<>();
    private final Map<Long, Set<NbtMap>> blockEntities = new Long2ObjectOpenHashMap<>();
    private WorldData worldData;

    public AllayNonPersistentWorldStorage() {
        this.worldData = WorldData.builder().build();
    }

    @Override
    public CompletableFuture<Chunk> readChunk(int x, int z, DimensionInfo dimensionInfo) {
        return CompletableFuture.completedFuture(readChunkSync(x, z, dimensionInfo));
    }

    @Override
    public Chunk readChunkSync(int x, int z, DimensionInfo dimensionInfo) throws WorldStorageException {
        var dimension = Server.getInstance().getWorldPool().getWorld(worldData.getName()).getDimension(dimensionInfo.dimensionId());
        var hash = HashUtils.hashXZ(x, z);
        var chunk = chunks.get(hash);
        if (chunk == null)
            chunk = AllayUnsafeChunk.builder().emptyChunk(x, z, dimensionInfo).toSafeChunk();
        readEntities(hash).stream().map(nbt -> EntityHelper.fromNBT(dimension, nbt)).forEach(e -> dimension.getEntityService().addEntity(e));
        readBlockEntities(hash).stream().map(nbt -> BlockEntityHelper.fromNBT(dimension, nbt)).forEach(chunk::addBlockEntity);
        return chunk;
    }

    @Override
    public CompletableFuture<Void> writeChunk(Chunk chunk) {
        writeChunkSync(chunk);
        return CompletableFuture.completedFuture(null);
    }

    @Override
    public void writeChunkSync(Chunk chunk) {
        var hash = HashUtils.hashXZ(chunk.getX(), chunk.getZ());
        chunks.put(hash, chunk);
        writeEntities(hash, chunk.getEntities().values());
        writeBlockEntities(hash, chunk.getBlockEntities().values());
    }

    protected Set<NbtMap> readEntities(long chunkHash) {
        return entities.getOrDefault(chunkHash, Set.of());
    }

    protected Set<NbtMap> readBlockEntities(long chunkHash) {
        return blockEntities.getOrDefault(chunkHash, Set.of());
    }

    protected void writeEntities(long chunkHash, Collection<Entity> entities) {
        this.entities.put(chunkHash, entities.stream().map(Entity::saveNBT).collect(Collectors.toSet()));
    }

    protected void writeBlockEntities(long chunkHash, Collection<BlockEntity> blockEntities) {
        this.blockEntities.put(chunkHash, blockEntities.stream().map(BlockEntity::saveNBT).collect(Collectors.toSet()));
    }

    @Override
    public boolean containChunk(int x, int z, DimensionInfo dimensionInfo) {
        return chunks.containsKey(HashUtils.hashXZ(x, z));
    }

    @Override
    public void writeWorldData(WorldData worldData) {
        this.worldData = worldData;
    }

    @Override
    public WorldData readWorldData() {
        return this.worldData;
    }

    @Override
    public void shutdown() {}
}
