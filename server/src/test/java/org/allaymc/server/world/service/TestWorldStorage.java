package org.allaymc.server.world.service;

import org.allaymc.api.entity.Entity;
import org.allaymc.api.world.DimensionInfo;
import org.allaymc.api.world.WorldData;
import org.allaymc.api.world.chunk.Chunk;
import org.allaymc.api.world.chunk.ChunkState;
import org.allaymc.api.world.storage.WorldStorage;
import org.allaymc.server.world.chunk.AllayUnsafeChunk;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * @author daoge_cmd
 */
public class TestWorldStorage implements WorldStorage {
    @Override
    public CompletableFuture<Chunk> readChunk(int chunkX, int chunkZ, DimensionInfo dimensionInfo) {
        var chunk = AllayUnsafeChunk.builder().newChunk(chunkX, chunkZ, dimensionInfo).toSafeChunk();
        ((AllayUnsafeChunk) chunk.toUnsafeChunk()).setState(ChunkState.FINISHED);
        return CompletableFuture.completedFuture(chunk);
    }

    @Override
    public CompletableFuture<Void> writeChunk(Chunk chunk) {
        return CompletableFuture.completedFuture(null);
    }

    @Override
    public Chunk readChunkSync(int chunkX, int chunkZ, DimensionInfo dimensionInfo) {return null;}

    @Override
    public void writeChunkSync(Chunk chunk) {}

    @Override
    public CompletableFuture<Map<Long, Entity>> readEntities(int chunkX, int chunkZ, DimensionInfo dimensionInfo) {return CompletableFuture.completedFuture(Map.of());}

    @Override
    public Map<Long, Entity> readEntitiesSync(int chunkX, int chunkZ, DimensionInfo dimensionInfo) {
        return Map.of();
    }

    @Override
    public CompletableFuture<Void> writeEntities(int chunkX, int chunkZ, DimensionInfo dimensionInfo, Map<Long, Entity> entities) {return null;}

    @Override
    public void writeEntitiesSync(int chunkX, int chunkZ, DimensionInfo dimensionInfo, Map<Long, Entity> entities) {}

    @Override
    public boolean containChunk(int chunkX, int chunkZ, DimensionInfo dimensionInfo) {return false;}

    @Override
    public void writeWorldData(WorldData worldData) {}

    @Override
    public WorldData readWorldData() {return null;}

    @Override
    public void shutdown() {}
}
