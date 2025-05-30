package org.allaymc.server.block.impl;

import org.allaymc.api.block.interfaces.BlockTrialSpawnerBehavior;
import org.allaymc.api.component.interfaces.Component;
import org.allaymc.server.component.interfaces.ComponentProvider;

import java.util.List;

public class BlockTrialSpawnerBehaviorImpl extends BlockBehaviorImpl implements BlockTrialSpawnerBehavior {
    public BlockTrialSpawnerBehaviorImpl(
            List<ComponentProvider<? extends Component>> componentProviders) {
        super(componentProviders);
    }
}
