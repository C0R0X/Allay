package cn.allay.api.block.interfaces.element0;

import cn.allay.api.block.BlockBehavior;
import cn.allay.api.block.type.BlockType;
import cn.allay.api.block.type.BlockTypeBuilder;
import cn.allay.api.data.VanillaBlockId;

/**
 * @author daoge_cmd | Cool_Loong <br>
 * Allay Project <br>
 */
public interface BlockElement0Behavior extends BlockBehavior {
    BlockType<BlockElement0Behavior> ELEMENT_0_TYPE = BlockTypeBuilder
            .builder(BlockElement0Behavior.class)
            .vanillaBlock(VanillaBlockId.ELEMENT_0)
            .build();
}