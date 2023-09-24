package cn.allay.api.item.interfaces.glowlichen;

import cn.allay.api.data.VanillaItemId;
import cn.allay.api.item.ItemStack;
import cn.allay.api.item.type.ItemType;
import cn.allay.api.item.type.ItemTypeBuilder;

/**
 * @author daoge_cmd <br>
 * Allay Project <br>
 */
public interface ItemGlowLichenStack extends ItemStack {
    ItemType<ItemGlowLichenStack> GLOW_LICHEN_TYPE = ItemTypeBuilder
            .builder(ItemGlowLichenStack.class)
            .vanillaItem(VanillaItemId.GLOW_LICHEN)
            .build();
}