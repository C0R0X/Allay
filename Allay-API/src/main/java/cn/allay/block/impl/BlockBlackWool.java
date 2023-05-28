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
public interface BlockBlackWool extends Block {
    BlockType<BlockBlackWool> TYPE = BlockTypeBuilder
            .builder(BlockBlackWool.class)
            .vanillaBlock(VanillaBlockId.BLACK_WOOL, true)
            .addBasicComponents()
            .build().register(BlockTypeRegistry.getRegistry());
}