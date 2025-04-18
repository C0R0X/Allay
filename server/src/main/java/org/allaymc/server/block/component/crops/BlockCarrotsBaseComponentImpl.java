package org.allaymc.server.block.component.crops;

import org.allaymc.api.block.BlockBehavior;
import org.allaymc.api.block.FortuneDropHelper;
import org.allaymc.api.block.dto.BlockStateWithPos;
import org.allaymc.api.block.property.type.BlockPropertyTypes;
import org.allaymc.api.block.type.BlockType;
import org.allaymc.api.entity.Entity;
import org.allaymc.api.item.ItemStack;
import org.allaymc.api.item.type.ItemTypes;

import java.util.Set;

/**
 * @author daoge_cmd
 */
public class BlockCarrotsBaseComponentImpl extends BlockCropsBaseComponentImpl {
    public BlockCarrotsBaseComponentImpl(BlockType<? extends BlockBehavior> blockType) {
        super(blockType);
    }

    @Override
    public Set<ItemStack> getDrops(BlockStateWithPos blockState, ItemStack usedItem, Entity entity) {
        var growth = blockState.blockState().getPropertyValue(BlockPropertyTypes.GROWTH);
        if (growth < BlockPropertyTypes.GROWTH.getMax()) {
            return Set.of(ItemTypes.CARROT.createItemStack());
        }

        return Set.of(ItemTypes.CARROT.createItemStack(FortuneDropHelper.binomial(usedItem, 1)));
    }
}
