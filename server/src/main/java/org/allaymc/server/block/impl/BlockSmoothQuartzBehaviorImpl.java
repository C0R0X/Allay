package org.allaymc.server.block.impl;

import org.allaymc.api.block.interfaces.BlockSmoothQuartzBehavior;
import org.allaymc.api.component.interfaces.Component;
import org.allaymc.server.component.interfaces.ComponentProvider;

import java.util.List;

public class BlockSmoothQuartzBehaviorImpl extends BlockBehaviorImpl implements BlockSmoothQuartzBehavior {
    public BlockSmoothQuartzBehaviorImpl(
            List<ComponentProvider<? extends Component>> componentProviders) {
        super(componentProviders);
    }
}
