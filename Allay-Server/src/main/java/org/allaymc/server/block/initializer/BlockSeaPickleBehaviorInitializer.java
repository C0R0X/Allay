package org.allaymc.server.block.initializer;

import org.allaymc.api.block.interfaces.BlockSeaPickleBehavior;
import org.allaymc.api.block.type.BlockTypeBuilder;
import org.allaymc.api.block.type.BlockTypes;
import org.allaymc.api.data.VanillaBlockId;
import org.allaymc.api.data.VanillaBlockPropertyTypes;

/**
 * @author daoge_cmd <br>
 * Allay Project <br>
 */
public interface BlockSeaPickleBehaviorInitializer {
  static void init() {
    BlockTypes.SEA_PICKLE_TYPE = BlockTypeBuilder
            .builder(BlockSeaPickleBehavior.class)
            .vanillaBlock(VanillaBlockId.SEA_PICKLE)
            .setProperties(VanillaBlockPropertyTypes.CLUSTER_COUNT, VanillaBlockPropertyTypes.DEAD_BIT)
            .build();
  }
}