package cn.allay.api.item.interfaces.cookedrabbit;

import cn.allay.api.data.VanillaItemId;
import cn.allay.api.item.ItemStack;
import cn.allay.api.item.type.ItemType;
import cn.allay.api.item.type.ItemTypeBuilder;

/**
 * @author daoge_cmd <br>
 * Allay Project <br>
 */
public interface ItemCookedRabbitStack extends ItemStack {
    ItemType<ItemCookedRabbitStack> COOKED_RABBIT_TYPE = ItemTypeBuilder
            .builder(ItemCookedRabbitStack.class)
            .vanillaItem(VanillaItemId.COOKED_RABBIT)
            .build();
}