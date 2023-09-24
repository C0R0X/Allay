package cn.allay.api.item.interfaces.shaperarmortrimsmithingtemplate;

import cn.allay.api.data.VanillaItemId;
import cn.allay.api.item.ItemStack;
import cn.allay.api.item.type.ItemType;
import cn.allay.api.item.type.ItemTypeBuilder;

/**
 * @author daoge_cmd <br>
 * Allay Project <br>
 */
public interface ItemShaperArmorTrimSmithingTemplateStack extends ItemStack {
    ItemType<ItemShaperArmorTrimSmithingTemplateStack> SHAPER_ARMOR_TRIM_SMITHING_TEMPLATE_TYPE = ItemTypeBuilder
            .builder(ItemShaperArmorTrimSmithingTemplateStack.class)
            .vanillaItem(VanillaItemId.SHAPER_ARMOR_TRIM_SMITHING_TEMPLATE)
            .build();
}