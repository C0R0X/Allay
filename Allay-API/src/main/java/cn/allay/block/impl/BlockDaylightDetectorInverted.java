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
public interface BlockDaylightDetectorInverted extends Block {
    BlockType<BlockDaylightDetectorInverted> TYPE = BlockTypeBuilder
            .builder(BlockDaylightDetectorInverted.class)
            .vanillaBlock(VanillaBlockId.DAYLIGHT_DETECTOR_INVERTED, true)
            .withProperties(VanillaBlockPropertyTypes.REDSTONE_SIGNAL)
            .addBasicComponents()
            .build().register(BlockTypeRegistry.getRegistry());
}