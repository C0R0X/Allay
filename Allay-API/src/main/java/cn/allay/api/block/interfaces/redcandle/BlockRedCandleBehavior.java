package cn.allay.api.block.interfaces.redcandle;

import cn.allay.api.block.BlockBehavior;
import cn.allay.api.block.type.BlockType;
import cn.allay.api.block.type.BlockTypeBuilder;
import cn.allay.api.data.VanillaBlockId;
import cn.allay.api.data.VanillaBlockPropertyTypes;

/**
 * @author daoge_cmd | Cool_Loong <br>
 * Allay Project <br>
 */
public interface BlockRedCandleBehavior extends BlockBehavior {
    BlockType<BlockRedCandleBehavior> RED_CANDLE_TYPE = BlockTypeBuilder
            .builder(BlockRedCandleBehavior.class)
            .vanillaBlock(VanillaBlockId.RED_CANDLE)
            .setProperties(VanillaBlockPropertyTypes.CANDLES, VanillaBlockPropertyTypes.LIT)
            .build();
}