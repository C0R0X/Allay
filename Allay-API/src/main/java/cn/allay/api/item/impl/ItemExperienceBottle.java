package cn.allay.api.item.impl;

import cn.allay.api.data.VanillaItemId;
import cn.allay.api.item.ItemStack;
import cn.allay.api.item.type.ItemType;
import cn.allay.api.item.type.ItemTypeBuilder;
import cn.allay.api.item.type.ItemTypeRegistry;

/**
 * Author: daoge_cmd <br>
 * Allay Project <br>
 */
public interface ItemExperienceBottle extends ItemStack {
    ItemType<ItemExperienceBottle> TYPE = ItemTypeBuilder
            .builder(ItemExperienceBottle.class)
            .vanillaItem(VanillaItemId.EXPERIENCE_BOTTLE, true)
            .addBasicComponents()
            .build().register(ItemTypeRegistry.getRegistry());
}