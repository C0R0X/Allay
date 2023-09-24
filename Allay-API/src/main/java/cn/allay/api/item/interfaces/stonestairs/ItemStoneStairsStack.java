package cn.allay.api.item.interfaces.stonestairs;

import cn.allay.api.data.VanillaItemId;
import cn.allay.api.item.ItemStack;
import cn.allay.api.item.component.base.ItemBaseComponentImpl;
import cn.allay.api.item.type.ItemType;
import cn.allay.api.item.type.ItemTypeBuilder;

/**
 * @author daoge_cmd <br>
 * Allay Project <br>
 */
public interface ItemStoneStairsStack extends ItemStack {
    ItemType<ItemStoneStairsStack> STONE_STAIRS_TYPE = ItemTypeBuilder
            .builder(ItemStoneStairsStack.class)
            .vanillaItem(VanillaItemId.STONE_STAIRS)
            .addComponent(ItemBaseComponentImpl::new, ItemBaseComponentImpl.class)
            .build();
}