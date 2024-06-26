package org.allaymc.api.world.generator;

import org.allaymc.api.world.Dimension;
import org.allaymc.api.world.chunk.UnsafeChunk;

/**
 * Allay Project 8/23/2023
 *
 * @author Cool_Loong
 */
public record ChunkGenerateContext(UnsafeChunk chunk, Dimension dimension) {

}
