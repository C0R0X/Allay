package cn.allay.api.block.interfaces.graywool;

import cn.allay.api.block.BlockBehavior;
import cn.allay.api.block.type.BlockType;
import cn.allay.api.block.type.BlockTypeBuilder;
import cn.allay.api.data.VanillaBlockId;

/**
 * @author daoge_cmd | Cool_Loong <br>
 * Allay Project <br>
 */
public interface BlockGrayWoolBehavior extends BlockBehavior {
    BlockType<BlockGrayWoolBehavior> GRAY_WOOL_TYPE = BlockTypeBuilder
            .builder(BlockGrayWoolBehavior.class)
            .vanillaBlock(VanillaBlockId.GRAY_WOOL)
            .build();
}