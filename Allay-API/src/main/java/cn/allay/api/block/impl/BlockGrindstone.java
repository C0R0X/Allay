package cn.allay.api.block.impl;

import cn.allay.api.block.Block;
import cn.allay.api.block.type.BlockType;
import cn.allay.api.block.type.BlockTypeBuilder;
import cn.allay.api.data.VanillaBlockId;
import cn.allay.api.data.VanillaBlockPropertyTypes;

/**
 * Author: daoge_cmd | Cool_Loong <br>
 * Allay Project <br>
 */
public interface BlockGrindstone extends Block {
    BlockType<BlockGrindstone> TYPE = BlockTypeBuilder
            .builder(BlockGrindstone.class)
            .vanillaBlock(VanillaBlockId.GRINDSTONE, true)
            .withProperties(VanillaBlockPropertyTypes.ATTACHMENT,
                    VanillaBlockPropertyTypes.DIRECTION)
            .addBasicComponents()
            .build();
}