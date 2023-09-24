package cn.allay.api.item.interfaces.waxedcutcopperslab;

import cn.allay.api.data.VanillaItemId;
import cn.allay.api.item.ItemStack;
import cn.allay.api.item.type.ItemType;
import cn.allay.api.item.type.ItemTypeBuilder;

/**
 * @author daoge_cmd <br>
 * Allay Project <br>
 */
public interface ItemWaxedCutCopperSlabStack extends ItemStack {
    ItemType<ItemWaxedCutCopperSlabStack> WAXED_CUT_COPPER_SLAB_TYPE = ItemTypeBuilder
            .builder(ItemWaxedCutCopperSlabStack.class)
            .vanillaItem(VanillaItemId.WAXED_CUT_COPPER_SLAB)
            .build();
}