package org.allaymc.server.blockentity.component.furnace;

import org.allaymc.api.block.type.BlockType;
import org.allaymc.api.block.type.BlockTypes;
import org.allaymc.api.blockentity.initinfo.BlockEntityInitInfo;
import org.allaymc.api.item.recipe.impl.FurnaceRecipe;

/**
 * @author daoge_cmd
 */
public class BlockEntitySmokerFurnaceBaseComponentImpl extends BlockEntityFurnaceBaseComponentImpl {
    public BlockEntitySmokerFurnaceBaseComponentImpl(BlockEntityInitInfo initInfo) {
        super(initInfo);
    }

    @Override
    public BlockType<?> getLitBlockType() {
        return BlockTypes.LIT_SMOKER;
    }

    @Override
    public BlockType<?> getUnlitBlockType() {
        return BlockTypes.SMOKER;
    }

    @Override
    public String getFurnaceRecipeTag() {
        return FurnaceRecipe.SMOKER_TAG;
    }

    @Override
    public float getIdealSpeed() {
        return 2;
    }
}
