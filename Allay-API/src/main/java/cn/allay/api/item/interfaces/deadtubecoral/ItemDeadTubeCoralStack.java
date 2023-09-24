package cn.allay.api.item.interfaces.deadtubecoral;

import cn.allay.api.data.VanillaItemId;
import cn.allay.api.item.ItemStack;
import cn.allay.api.item.type.ItemType;
import cn.allay.api.item.type.ItemTypeBuilder;

/**
 * @author daoge_cmd <br>
 * Allay Project <br>
 */
public interface ItemDeadTubeCoralStack extends ItemStack {
    ItemType<ItemDeadTubeCoralStack> DEAD_TUBE_CORAL_TYPE = ItemTypeBuilder
            .builder(ItemDeadTubeCoralStack.class)
            .vanillaItem(VanillaItemId.DEAD_TUBE_CORAL)
            .build();
}