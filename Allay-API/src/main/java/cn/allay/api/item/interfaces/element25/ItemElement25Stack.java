package cn.allay.api.item.interfaces.element25;

import cn.allay.api.data.VanillaItemId;
import cn.allay.api.item.ItemStack;
import cn.allay.api.item.type.ItemType;
import cn.allay.api.item.type.ItemTypeBuilder;

/**
 * @author daoge_cmd <br>
 * Allay Project <br>
 */
public interface ItemElement25Stack extends ItemStack {
    ItemType<ItemElement25Stack> ELEMENT_25_TYPE = ItemTypeBuilder
            .builder(ItemElement25Stack.class)
            .vanillaItem(VanillaItemId.ELEMENT_25)
            .build();
}