package cn.allay.block.impl;

import cn.allay.block.Block;
import cn.allay.block.data.VanillaBlockId;
import cn.allay.block.type.BlockType;
import cn.allay.block.type.BlockTypeBuilder;
import cn.allay.block.type.BlockTypeRegistry;

/**
 * Author: daoge_cmd <br>
 * Allay Project <br>
 */
public interface BlockWarpedNylium extends Block {
    BlockType<BlockWarpedNylium> TYPE = BlockTypeBuilder
            .builder(BlockWarpedNylium.class)
            .vanillaBlock(VanillaBlockId.WARPED_NYLIUM, true)
            .addBasicComponents()
            .build().register(BlockTypeRegistry.getRegistry());
}