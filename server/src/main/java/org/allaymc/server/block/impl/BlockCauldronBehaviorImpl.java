package org.allaymc.server.block.impl;

import org.allaymc.api.block.interfaces.BlockCauldronBehavior;
import org.allaymc.api.component.interfaces.Component;
import org.allaymc.server.component.interfaces.ComponentProvider;

import java.util.List;

public class BlockCauldronBehaviorImpl extends BlockBehaviorImpl implements BlockCauldronBehavior {
    public BlockCauldronBehaviorImpl(
            List<ComponentProvider<? extends Component>> componentProviders) {
        super(componentProviders);
    }
}
