package cn.allay.api.block.impl;

import cn.allay.api.block.Block;
import cn.allay.api.block.type.BlockType;
import cn.allay.api.block.type.BlockTypeBuilder;
import cn.allay.api.data.VanillaBlockId;
import cn.allay.api.data.VanillaBlockPropertyTypes;

/**
 * Author: daoge_cmd | Cool_Loong <br>
 * Allay Project <br>
 */
public interface BlockFrostedIce extends Block {
  BlockType<BlockFrostedIce> TYPE = BlockTypeBuilder
          .builder(BlockFrostedIce.class)
          .vanillaBlock(VanillaBlockId.FROSTED_ICE, true)
          .withProperties(VanillaBlockPropertyTypes.AGE_4)
          .addBasicComponents()
          .build();
}