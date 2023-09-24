package cn.allay.api.item.interfaces.netheritehelmet;

import cn.allay.api.data.VanillaItemId;
import cn.allay.api.item.ItemStack;
import cn.allay.api.item.type.ItemType;
import cn.allay.api.item.type.ItemTypeBuilder;

/**
 * @author daoge_cmd <br>
 * Allay Project <br>
 */
public interface ItemNetheriteHelmetStack extends ItemStack {
    ItemType<ItemNetheriteHelmetStack> NETHERITE_HELMET_TYPE = ItemTypeBuilder
            .builder(ItemNetheriteHelmetStack.class)
            .vanillaItem(VanillaItemId.NETHERITE_HELMET)
            .build();
}