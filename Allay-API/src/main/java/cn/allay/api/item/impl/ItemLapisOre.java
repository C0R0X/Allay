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
public interface ItemLapisOre extends ItemStack {
    ItemType<ItemLapisOre> TYPE = ItemTypeBuilder
            .builder(ItemLapisOre.class)
            .vanillaItem(VanillaItemId.LAPIS_ORE, true)
            .addBasicComponents()
            .build().register(ItemTypeRegistry.getRegistry());
}