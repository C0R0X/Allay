package cn.allay.api.block.interfaces.weatheredcopper;

import cn.allay.api.block.BlockBehavior;
import cn.allay.api.block.type.BlockType;
import cn.allay.api.block.type.BlockTypeBuilder;
import cn.allay.api.data.VanillaBlockId;

/**
 * @author daoge_cmd | Cool_Loong <br>
 * Allay Project <br>
 */
public interface BlockWeatheredCopperBehavior extends BlockBehavior {
    BlockType<BlockWeatheredCopperBehavior> WEATHERED_COPPER_TYPE = BlockTypeBuilder
            .builder(BlockWeatheredCopperBehavior.class)
            .vanillaBlock(VanillaBlockId.WEATHERED_COPPER)
            .build();
}