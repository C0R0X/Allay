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
public interface BlockCobblestone extends Block {
    BlockType<BlockCobblestone> TYPE = BlockTypeBuilder
            .builder(BlockCobblestone.class)
            .vanillaBlock(VanillaBlockId.COBBLESTONE, true)
            .addBasicComponents()
            .build().register(BlockTypeRegistry.getRegistry());
}