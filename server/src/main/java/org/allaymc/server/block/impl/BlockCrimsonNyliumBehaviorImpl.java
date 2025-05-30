package org.allaymc.server.block.impl;

import org.allaymc.api.block.interfaces.BlockCrimsonNyliumBehavior;
import org.allaymc.api.component.interfaces.Component;
import org.allaymc.server.component.interfaces.ComponentProvider;

import java.util.List;

public class BlockCrimsonNyliumBehaviorImpl extends BlockBehaviorImpl implements BlockCrimsonNyliumBehavior {
    public BlockCrimsonNyliumBehaviorImpl(
            List<ComponentProvider<? extends Component>> componentProviders) {
        super(componentProviders);
    }
}
