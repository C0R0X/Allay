package cn.allay.api.item.interfaces.deepslatecoalore;

import cn.allay.api.data.VanillaItemId;
import cn.allay.api.item.ItemStack;
import cn.allay.api.item.type.ItemType;
import cn.allay.api.item.type.ItemTypeBuilder;

/**
 * @author daoge_cmd <br>
 * Allay Project <br>
 */
public interface ItemDeepslateCoalOreStack extends ItemStack {
    ItemType<ItemDeepslateCoalOreStack> DEEPSLATE_COAL_ORE_TYPE = ItemTypeBuilder
            .builder(ItemDeepslateCoalOreStack.class)
            .vanillaItem(VanillaItemId.DEEPSLATE_COAL_ORE)
            .build();
}