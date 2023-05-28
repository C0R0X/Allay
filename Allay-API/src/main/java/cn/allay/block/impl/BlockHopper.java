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
public interface BlockHopper extends Block {
    BlockType<BlockHopper> TYPE = BlockTypeBuilder
            .builder(BlockHopper.class)
            .vanillaBlock(VanillaBlockId.HOPPER, true)
            .withProperties(VanillaBlockPropertyTypes.FACING_DIRECTION,
                    VanillaBlockPropertyTypes.TOGGLE_BIT)
            .addBasicComponents()
            .build().register(BlockTypeRegistry.getRegistry());
}