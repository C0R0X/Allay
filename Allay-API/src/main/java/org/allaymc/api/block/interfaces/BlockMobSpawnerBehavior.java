package org.allaymc.api.block.interfaces;

import org.allaymc.api.block.BlockBehavior;
import org.allaymc.api.block.type.BlockType;
import org.allaymc.api.block.type.BlockTypeBuilder;
import org.allaymc.api.data.VanillaBlockId;

/**
 * @author daoge_cmd | Cool_Loong <br>
 * Allay Project <br>
 */
public interface BlockMobSpawnerBehavior extends BlockBehavior {
  BlockType<BlockMobSpawnerBehavior> MOB_SPAWNER_TYPE = BlockTypeBuilder
          .builder(BlockMobSpawnerBehavior.class)
          .vanillaBlock(VanillaBlockId.MOB_SPAWNER)
          .build();
}