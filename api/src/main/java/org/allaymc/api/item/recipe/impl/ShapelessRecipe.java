package org.allaymc.api.item.recipe.impl;

import lombok.Builder;
import lombok.Getter;
import org.allaymc.api.item.ItemStack;
import org.allaymc.api.item.descriptor.ItemDescriptor;
import org.allaymc.api.item.recipe.input.CraftingInput;
import org.allaymc.api.item.recipe.input.Input;
import org.allaymc.api.utils.Identifier;
import org.cloudburstmc.protocol.bedrock.data.inventory.crafting.CraftingDataType;
import org.cloudburstmc.protocol.bedrock.data.inventory.crafting.recipe.RecipeData;
import org.cloudburstmc.protocol.bedrock.data.inventory.crafting.recipe.ShapelessRecipeData;
import org.cloudburstmc.protocol.bedrock.data.inventory.descriptor.ItemDescriptorWithCount;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.allaymc.api.item.type.ItemTypes.AIR;

/**
 * Represents a shapeless recipe.
 *
 * @author daoge_cmd
 */
public class ShapelessRecipe extends CraftingRecipe {
    @Getter
    protected ItemDescriptor[] ingredients;

    @Builder
    protected ShapelessRecipe(ItemDescriptor[] ingredients, Identifier identifier, ItemStack[] outputs, String tag, UUID uuid, int priority) {
        super(identifier, outputs, tag, uuid, priority);
        this.ingredients = ingredients;
        this.networkRecipeDataCache = buildNetworkRecipeData();
    }

    @Override
    public boolean match(Input input) {
        if (!(input instanceof CraftingInput craftingInput)) return false;

        var inputItems = collectNonAirItems(craftingInput.getFlattenItems());
        if (inputItems.size() != ingredients.length) return false;

        var itemPool = new ArrayList<>(inputItems);

        var checkCount = ingredients.length;
        for (var ingredient : ingredients) {
            var index = findItem(itemPool, ingredient);
            if (index != -1) {
                itemPool.remove(index);
                checkCount--;
            }
        }

        return checkCount == 0 && itemPool.isEmpty();
    }

    protected List<ItemStack> collectNonAirItems(ItemStack[] items) {
        return Arrays.stream(items).filter(item -> item.getItemType() != AIR).toList();
    }

    protected int findItem(List<ItemStack> itemPool, ItemDescriptor target) {
        for (int index = 0; index < itemPool.size(); index++) {
            var item = itemPool.get(index);
            if (target.match(item)) {
                return index;
            }
        }
        return -1;
    }

    @Override
    public CraftingDataType getType() {
        return CraftingDataType.SHAPELESS;
    }

    protected RecipeData buildNetworkRecipeData() {
        return ShapelessRecipeData.of(
                getType(), identifier.toString(),
                buildNetworkIngredients(), buildNetworkOutputs(),
                uuid, tag, priority, networkId
        );
    }

    protected List<ItemDescriptorWithCount> buildNetworkIngredients() {
        return Arrays.stream(ingredients)
                .map(ingredient -> new ItemDescriptorWithCount(ingredient.toNetwork(), 1))
                .toList();
    }
}
