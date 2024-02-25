package org.allaymc.server.item.initializer.wall;

import org.allaymc.api.data.VanillaItemId;
import org.allaymc.api.item.interfaces.wall.ItemMudBrickWallStack;
import org.allaymc.api.item.type.ItemTypeBuilder;
import org.allaymc.api.item.type.ItemTypes;

/**
 * @author daoge_cmd <br>
 * Allay Project <br>
 */
public interface ItemMudBrickWallStackInitializer {
  static void init() {
    ItemTypes.MUD_BRICK_WALL_TYPE = ItemTypeBuilder
            .builder(ItemMudBrickWallStack.class)
            .vanillaItem(VanillaItemId.MUD_BRICK_WALL)
            .build();
  }
}