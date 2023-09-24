package cn.allay.api.block.interfaces.element60;

import cn.allay.api.block.BlockBehavior;
import cn.allay.api.block.type.BlockType;
import cn.allay.api.block.type.BlockTypeBuilder;
import cn.allay.api.data.VanillaBlockId;

/**
 * @author daoge_cmd | Cool_Loong <br>
 * Allay Project <br>
 */
public interface BlockElement60Behavior extends BlockBehavior {
    BlockType<BlockElement60Behavior> ELEMENT_60_TYPE = BlockTypeBuilder
            .builder(BlockElement60Behavior.class)
            .vanillaBlock(VanillaBlockId.ELEMENT_60)
            .build();
}