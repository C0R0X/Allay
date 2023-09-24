package cn.allay.api.block.interfaces.element12;

import cn.allay.api.block.BlockBehavior;
import cn.allay.api.block.type.BlockType;
import cn.allay.api.block.type.BlockTypeBuilder;
import cn.allay.api.data.VanillaBlockId;

/**
 * @author daoge_cmd | Cool_Loong <br>
 * Allay Project <br>
 */
public interface BlockElement12Behavior extends BlockBehavior {
    BlockType<BlockElement12Behavior> ELEMENT_12_TYPE = BlockTypeBuilder
            .builder(BlockElement12Behavior.class)
            .vanillaBlock(VanillaBlockId.ELEMENT_12)
            .build();
}