package org.allaymc.server.item.initializer.element;

import org.allaymc.api.data.VanillaItemId;
import org.allaymc.api.item.interfaces.element.ItemElement63Stack;
import org.allaymc.api.item.type.ItemTypeBuilder;
import org.allaymc.api.item.type.ItemTypes;

/**
 * @author daoge_cmd <br>
 * Allay Project <br>
 */
public interface ItemElement63StackInitializer {
  static void init() {
    ItemTypes.ELEMENT_63_TYPE = ItemTypeBuilder
            .builder(ItemElement63Stack.class)
            .vanillaItem(VanillaItemId.ELEMENT_63)
            .build();
  }
}