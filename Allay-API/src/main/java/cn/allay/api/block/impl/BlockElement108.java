package cn.allay.api.block.impl;

import cn.allay.api.block.Block;
import cn.allay.api.block.type.BlockType;
import cn.allay.api.block.type.BlockTypeBuilder;
import cn.allay.api.data.VanillaBlockId;

/**
 * Author: daoge_cmd | Cool_Loong <br>
 * Allay Project <br>
 */
public interface BlockElement108 extends Block {
    BlockType<BlockElement108> TYPE = BlockTypeBuilder
            .builder(BlockElement108.class)
            .vanillaBlock(VanillaBlockId.ELEMENT_108, true)
            .addBasicComponents()
            .build();
}