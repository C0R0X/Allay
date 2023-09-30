package cn.allay.api.block.interfaces.stainedglass;

import cn.allay.api.block.BlockBehavior;
import cn.allay.api.block.type.BlockType;
import cn.allay.api.block.type.BlockTypeBuilder;
import cn.allay.api.data.VanillaBlockId;
import cn.allay.api.data.VanillaBlockPropertyTypes;

/**
 * @author daoge_cmd | Cool_Loong <br>
 * Allay Project <br>
 */
public interface BlockHardStainedGlassBehavior extends BlockBehavior {
  BlockType<BlockHardStainedGlassBehavior> HARD_STAINED_GLASS_TYPE = BlockTypeBuilder
          .builder(BlockHardStainedGlassBehavior.class)
          .vanillaBlock(VanillaBlockId.HARD_STAINED_GLASS)
          .setProperties(VanillaBlockPropertyTypes.COLOR)
          .build();
}