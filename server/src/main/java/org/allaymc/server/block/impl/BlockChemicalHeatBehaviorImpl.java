package org.allaymc.server.block.impl;

import org.allaymc.api.block.interfaces.BlockChemicalHeatBehavior;
import org.allaymc.api.component.interfaces.Component;
import org.allaymc.server.component.interfaces.ComponentProvider;

import java.util.List;

public class BlockChemicalHeatBehaviorImpl extends BlockBehaviorImpl implements BlockChemicalHeatBehavior {
    public BlockChemicalHeatBehaviorImpl(
            List<ComponentProvider<? extends Component>> componentProviders) {
        super(componentProviders);
    }
}
