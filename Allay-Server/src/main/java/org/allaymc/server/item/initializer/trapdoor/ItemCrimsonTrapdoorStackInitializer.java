package org.allaymc.server.item.initializer.trapdoor;

import org.allaymc.api.data.VanillaItemId;
import org.allaymc.api.item.interfaces.trapdoor.ItemCrimsonTrapdoorStack;
import org.allaymc.api.item.type.ItemTypeBuilder;
import org.allaymc.api.item.type.ItemTypes;

/**
 * @author daoge_cmd <br>
 * Allay Project <br>
 */
public interface ItemCrimsonTrapdoorStackInitializer {
  static void init() {
    ItemTypes.CRIMSON_TRAPDOOR_TYPE = ItemTypeBuilder
            .builder(ItemCrimsonTrapdoorStack.class)
            .vanillaItem(VanillaItemId.CRIMSON_TRAPDOOR)
            .build();
  }
}