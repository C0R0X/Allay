package org.allaymc.server.item.initializer.egg;

import org.allaymc.api.data.VanillaItemId;
import org.allaymc.api.item.interfaces.egg.ItemGhastSpawnEggStack;
import org.allaymc.api.item.type.ItemTypeBuilder;
import org.allaymc.api.item.type.ItemTypes;

/**
 * @author daoge_cmd <br>
 * Allay Project <br>
 */
public interface ItemGhastSpawnEggStackInitializer {
  static void init() {
    ItemTypes.GHAST_SPAWN_EGG_TYPE = ItemTypeBuilder
            .builder(ItemGhastSpawnEggStack.class)
            .vanillaItem(VanillaItemId.GHAST_SPAWN_EGG)
            .build();
  }
}