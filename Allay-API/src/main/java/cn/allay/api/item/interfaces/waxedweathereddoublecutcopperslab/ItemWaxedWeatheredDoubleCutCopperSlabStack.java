package cn.allay.api.item.interfaces.waxedweathereddoublecutcopperslab;

import cn.allay.api.data.VanillaItemId;
import cn.allay.api.item.ItemStack;
import cn.allay.api.item.type.ItemType;
import cn.allay.api.item.type.ItemTypeBuilder;

/**
 * @author daoge_cmd <br>
 * Allay Project <br>
 */
public interface ItemWaxedWeatheredDoubleCutCopperSlabStack extends ItemStack {
    ItemType<ItemWaxedWeatheredDoubleCutCopperSlabStack> WAXED_WEATHERED_DOUBLE_CUT_COPPER_SLAB_TYPE = ItemTypeBuilder
            .builder(ItemWaxedWeatheredDoubleCutCopperSlabStack.class)
            .vanillaItem(VanillaItemId.WAXED_WEATHERED_DOUBLE_CUT_COPPER_SLAB)
            .build();
}