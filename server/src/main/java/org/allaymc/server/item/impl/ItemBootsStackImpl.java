package org.allaymc.server.item.impl;

import lombok.experimental.Delegate;
import org.allaymc.api.component.interfaces.Component;
import org.allaymc.api.item.component.ItemArmorBaseComponent;
import org.allaymc.api.item.component.ItemTrimmableComponent;
import org.allaymc.api.item.initinfo.ItemStackInitInfo;
import org.allaymc.api.item.interfaces.ItemBootsStack;
import org.allaymc.server.component.interfaces.ComponentProvider;

import java.util.List;

public class ItemBootsStackImpl extends ItemStackImpl implements ItemBootsStack {
    @Delegate
    protected ItemTrimmableComponent trimComponent;

    public ItemBootsStackImpl(ItemStackInitInfo initInfo, List<ComponentProvider<? extends Component>> componentProviders) {
        super(initInfo, componentProviders);
    }

    @Delegate
    @Override
    protected ItemArmorBaseComponent getBaseComponent() {
        return (ItemArmorBaseComponent) super.getBaseComponent();
    }
}
