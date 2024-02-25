package org.allaymc.server.block.initializer;

import org.allaymc.api.block.interfaces.BlockPoweredComparatorBehavior;
import org.allaymc.api.block.type.BlockTypeBuilder;
import org.allaymc.api.block.type.BlockTypes;
import org.allaymc.api.data.VanillaBlockId;
import org.allaymc.api.data.VanillaBlockPropertyTypes;

/**
 * @author daoge_cmd <br>
 * Allay Project <br>
 */
public interface BlockPoweredComparatorBehaviorInitializer {
  static void init() {
    BlockTypes.POWERED_COMPARATOR_TYPE = BlockTypeBuilder
            .builder(BlockPoweredComparatorBehavior.class)
            .vanillaBlock(VanillaBlockId.POWERED_COMPARATOR)
            .setProperties(VanillaBlockPropertyTypes.MINECRAFT_CARDINAL_DIRECTION, VanillaBlockPropertyTypes.OUTPUT_LIT_BIT, VanillaBlockPropertyTypes.OUTPUT_SUBTRACT_BIT)
            .build();
  }
}