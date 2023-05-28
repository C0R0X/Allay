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
public interface BlockCrimsonStem extends Block {
    BlockType<BlockCrimsonStem> TYPE = BlockTypeBuilder
            .builder(BlockCrimsonStem.class)
            .vanillaBlock(VanillaBlockId.CRIMSON_STEM, true)
            .withProperties(VanillaBlockPropertyTypes.PILLAR_AXIS)
            .addBasicComponents()
            .build().register(BlockTypeRegistry.getRegistry());
}