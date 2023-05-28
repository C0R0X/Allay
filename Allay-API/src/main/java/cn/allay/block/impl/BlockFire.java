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
public interface BlockFire extends Block {
    BlockType<BlockFire> TYPE = BlockTypeBuilder
            .builder(BlockFire.class)
            .vanillaBlock(VanillaBlockId.FIRE, true)
            .withProperties(VanillaBlockPropertyTypes.AGE)
            .addBasicComponents()
            .build().register(BlockTypeRegistry.getRegistry());
}