package cn.allay.block.impl;

import cn.allay.block.Block;
import cn.allay.block.data.VanillaBlockId;
import cn.allay.block.property.vanilla.VanillaBlockPropertyTypes;
import cn.allay.block.type.BlockType;
import cn.allay.block.type.BlockTypeBuilder;
import cn.allay.block.type.BlockTypeRegistry;

/**
 * Author: daoge_cmd <br>
 * Allay Project <br>
 */
public interface BlockBed extends Block {
    BlockType<BlockBed> TYPE = BlockTypeBuilder
            .builder(BlockBed.class)
            .vanillaBlock(VanillaBlockId.BED, true)
            .withProperties(VanillaBlockPropertyTypes.DIRECTION,
                    VanillaBlockPropertyTypes.HEAD_PIECE_BIT,
                    VanillaBlockPropertyTypes.OCCUPIED_BIT)
            .addBasicComponents()
            .build().register(BlockTypeRegistry.getRegistry());
}