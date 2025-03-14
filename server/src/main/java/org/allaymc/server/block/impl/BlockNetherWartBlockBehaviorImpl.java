package org.allaymc.server.block.impl;

import org.allaymc.api.block.interfaces.BlockNetherWartBlockBehavior;
import org.allaymc.api.component.interfaces.Component;
import org.allaymc.server.component.interfaces.ComponentProvider;

import java.util.List;

public class BlockNetherWartBlockBehaviorImpl extends BlockBehaviorImpl implements BlockNetherWartBlockBehavior {
    public BlockNetherWartBlockBehaviorImpl(
            List<ComponentProvider<? extends Component>> componentProviders) {
        super(componentProviders);
    }
}
