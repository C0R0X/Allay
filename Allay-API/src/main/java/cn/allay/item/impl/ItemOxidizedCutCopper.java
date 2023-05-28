package cn.allay.item.impl;

import cn.allay.item.ItemStack;
import cn.allay.item.data.VanillaItemId;
import cn.allay.item.type.ItemType;
import cn.allay.item.type.ItemTypeBuilder;
import cn.allay.item.type.ItemTypeRegistry;

/**
 * Author: daoge_cmd <br>
 * Allay Project <br>
 */
public interface ItemOxidizedCutCopper extends ItemStack {
    ItemType<ItemOxidizedCutCopper> TYPE = ItemTypeBuilder
            .builder(ItemOxidizedCutCopper.class)
            .vanillaItem(VanillaItemId.OXIDIZED_CUT_COPPER, true)
            .addBasicComponents()
            .build().register(ItemTypeRegistry.getRegistry());
}