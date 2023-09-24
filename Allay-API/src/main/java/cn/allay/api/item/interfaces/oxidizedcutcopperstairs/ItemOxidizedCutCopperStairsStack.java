package cn.allay.api.item.interfaces.oxidizedcutcopperstairs;

import cn.allay.api.data.VanillaItemId;
import cn.allay.api.item.ItemStack;
import cn.allay.api.item.component.base.ItemBaseComponentImpl;
import cn.allay.api.item.type.ItemType;
import cn.allay.api.item.type.ItemTypeBuilder;

/**
 * @author daoge_cmd <br>
 * Allay Project <br>
 */
public interface ItemOxidizedCutCopperStairsStack extends ItemStack {
    ItemType<ItemOxidizedCutCopperStairsStack> OXIDIZED_CUT_COPPER_STAIRS_TYPE = ItemTypeBuilder
            .builder(ItemOxidizedCutCopperStairsStack.class)
            .vanillaItem(VanillaItemId.OXIDIZED_CUT_COPPER_STAIRS)
            .addComponent(ItemBaseComponentImpl::new, ItemBaseComponentImpl.class)
            .build();
}