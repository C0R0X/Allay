package cn.allay.api.item.interfaces.fencegate;

import cn.allay.api.data.VanillaItemId;
import cn.allay.api.item.ItemStack;
import cn.allay.api.item.type.ItemType;
import cn.allay.api.item.type.ItemTypeBuilder;

/**
 * @author daoge_cmd <br>
 * Allay Project <br>
 */
public interface ItemCrimsonFenceGateStack extends ItemStack {
  ItemType<ItemCrimsonFenceGateStack> CRIMSON_FENCE_GATE_TYPE = ItemTypeBuilder
          .builder(ItemCrimsonFenceGateStack.class)
          .vanillaItem(VanillaItemId.CRIMSON_FENCE_GATE)
          .build();
}