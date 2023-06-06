package cn.allay.api.block.impl;

import cn.allay.api.block.Block;
import cn.allay.api.block.type.BlockType;
import cn.allay.api.block.type.BlockTypeBuilder;
import cn.allay.api.data.VanillaBlockId;

/**
 * Author: daoge_cmd | Cool_Loong <br>
 * Allay Project <br>
 */
public interface BlockDripstoneBlock extends Block {
    BlockType<BlockDripstoneBlock> TYPE = BlockTypeBuilder
            .builder(BlockDripstoneBlock.class)
            .vanillaBlock(VanillaBlockId.DRIPSTONE_BLOCK, true)
            .addBasicComponents()
            .build();
}