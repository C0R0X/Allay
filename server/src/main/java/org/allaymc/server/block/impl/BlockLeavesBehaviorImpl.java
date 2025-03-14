package org.allaymc.server.block.impl;

import org.allaymc.api.block.interfaces.BlockLeavesBehavior;
import org.allaymc.api.component.interfaces.Component;
import org.allaymc.server.component.interfaces.ComponentProvider;

import java.util.List;

public class BlockLeavesBehaviorImpl extends BlockBehaviorImpl implements BlockLeavesBehavior {
    public BlockLeavesBehaviorImpl(
            List<ComponentProvider<? extends Component>> componentProviders) {
        super(componentProviders);
    }
}
