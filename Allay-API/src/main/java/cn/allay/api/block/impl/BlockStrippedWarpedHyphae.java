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
public interface BlockStrippedWarpedHyphae extends Block {
    BlockType<BlockStrippedWarpedHyphae> TYPE = BlockTypeBuilder
            .builder(BlockStrippedWarpedHyphae.class)
            .vanillaBlock(VanillaBlockId.STRIPPED_WARPED_HYPHAE, true)
            .withProperties(VanillaBlockPropertyTypes.PILLAR_AXIS)
            .addBasicComponents()
            .build();
}