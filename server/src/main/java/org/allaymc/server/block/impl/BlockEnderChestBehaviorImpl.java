package org.allaymc.server.block.impl;

import lombok.experimental.Delegate;
import org.allaymc.api.block.component.BlockEntityHolderComponent;
import org.allaymc.api.block.interfaces.BlockEnderChestBehavior;
import org.allaymc.api.blockentity.interfaces.BlockEntityEnderChest;
import org.allaymc.api.component.interfaces.Component;
import org.allaymc.server.component.interfaces.ComponentProvider;

import java.util.List;

public class BlockEnderChestBehaviorImpl extends BlockBehaviorImpl implements BlockEnderChestBehavior {
    @Delegate
    protected BlockEntityHolderComponent<BlockEntityEnderChest> blockEntityHolderComponent;

    public BlockEnderChestBehaviorImpl(List<ComponentProvider<? extends Component>> componentProviders) {
        super(componentProviders);
    }
}
