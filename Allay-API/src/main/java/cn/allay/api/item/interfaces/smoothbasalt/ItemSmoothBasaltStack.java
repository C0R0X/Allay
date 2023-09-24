package cn.allay.api.item.interfaces.smoothbasalt;

import cn.allay.api.data.VanillaItemId;
import cn.allay.api.item.ItemStack;
import cn.allay.api.item.type.ItemType;
import cn.allay.api.item.type.ItemTypeBuilder;

/**
 * @author daoge_cmd <br>
 * Allay Project <br>
 */
public interface ItemSmoothBasaltStack extends ItemStack {
    ItemType<ItemSmoothBasaltStack> SMOOTH_BASALT_TYPE = ItemTypeBuilder
            .builder(ItemSmoothBasaltStack.class)
            .vanillaItem(VanillaItemId.SMOOTH_BASALT)
            .build();
}