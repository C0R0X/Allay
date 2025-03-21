package org.allaymc.server.item.impl;

import org.allaymc.api.component.interfaces.Component;
import org.allaymc.api.item.initinfo.ItemStackInitInfo;
import org.allaymc.api.item.interfaces.ItemChalkboardStack;
import org.allaymc.server.component.interfaces.ComponentProvider;

import java.util.List;

public class ItemChalkboardStackImpl extends ItemStackImpl implements ItemChalkboardStack {
    public ItemChalkboardStackImpl(ItemStackInitInfo initInfo,
                                   List<ComponentProvider<? extends Component>> componentProviders) {
        super(initInfo, componentProviders);
    }
}
