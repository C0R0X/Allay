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
public interface BlockWaxedWeatheredCutCopperStairs extends Block {
    BlockType<BlockWaxedWeatheredCutCopperStairs> TYPE = BlockTypeBuilder
            .builder(BlockWaxedWeatheredCutCopperStairs.class)
            .vanillaBlock(VanillaBlockId.WAXED_WEATHERED_CUT_COPPER_STAIRS, true)
            .withProperties(VanillaBlockPropertyTypes.UPSIDE_DOWN_BIT,
                    VanillaBlockPropertyTypes.WEIRDO_DIRECTION)
            .addBasicComponents()
            .build();
}