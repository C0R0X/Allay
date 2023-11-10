package org.allaymc.api.item.interfaces.concrete;

import org.allaymc.api.data.VanillaItemId;
import org.allaymc.api.item.ItemStack;
import org.allaymc.api.item.type.ItemType;
import org.allaymc.api.item.type.ItemTypeBuilder;

/**
 * @author daoge_cmd <br>
 * Allay Project <br>
 */
public interface ItemLightBlueConcreteStack extends ItemStack {
  ItemType<ItemLightBlueConcreteStack> LIGHT_BLUE_CONCRETE_TYPE = ItemTypeBuilder
          .builder(ItemLightBlueConcreteStack.class)
          .vanillaItem(VanillaItemId.LIGHT_BLUE_CONCRETE)
          .build();
}