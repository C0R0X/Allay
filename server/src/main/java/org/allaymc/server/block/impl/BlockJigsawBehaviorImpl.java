package org.allaymc.server.block.impl;

import org.allaymc.api.block.interfaces.BlockJigsawBehavior;
import org.allaymc.api.component.interfaces.Component;
import org.allaymc.server.component.interfaces.ComponentProvider;

import java.util.List;

public class BlockJigsawBehaviorImpl extends BlockBehaviorImpl implements BlockJigsawBehavior {
    public BlockJigsawBehaviorImpl(
            List<ComponentProvider<? extends Component>> componentProviders) {
        super(componentProviders);
    }
}
