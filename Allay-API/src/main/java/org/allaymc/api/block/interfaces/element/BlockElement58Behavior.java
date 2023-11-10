package org.allaymc.api.block.interfaces.element;

import org.allaymc.api.block.BlockBehavior;
import org.allaymc.api.block.type.BlockType;
import org.allaymc.api.block.type.BlockTypeBuilder;
import org.allaymc.api.data.VanillaBlockId;

/**
 * @author daoge_cmd | Cool_Loong <br>
 * Allay Project <br>
 */
public interface BlockElement58Behavior extends BlockBehavior {
  BlockType<BlockElement58Behavior> ELEMENT_58_TYPE = BlockTypeBuilder
          .builder(BlockElement58Behavior.class)
          .vanillaBlock(VanillaBlockId.ELEMENT_58)
          .build();
}