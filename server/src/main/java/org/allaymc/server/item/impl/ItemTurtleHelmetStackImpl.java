package org.allaymc.server.item.impl;

import lombok.experimental.Delegate;
import org.allaymc.api.component.interfaces.Component;
import org.allaymc.api.item.component.ItemArmorBaseComponent;
import org.allaymc.api.item.component.ItemRepairableComponent;
import org.allaymc.api.item.initinfo.ItemStackInitInfo;
import org.allaymc.api.item.interfaces.ItemTurtleHelmetStack;
import org.allaymc.server.component.interfaces.ComponentProvider;

import java.util.List;

public class ItemTurtleHelmetStackImpl extends ItemStackImpl implements ItemTurtleHelmetStack {
    @Delegate
    protected ItemArmorBaseComponent armorBaseComponent;
    @Delegate
    protected ItemRepairableComponent repairableComponent;

    public ItemTurtleHelmetStackImpl(ItemStackInitInfo initInfo, List<ComponentProvider<? extends Component>> componentProviders) {
        super(initInfo, componentProviders);
    }
}
