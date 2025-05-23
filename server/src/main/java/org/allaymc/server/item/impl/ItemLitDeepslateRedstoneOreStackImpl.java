package org.allaymc.server.item.impl;

import java.util.List;
import org.allaymc.api.component.interfaces.Component;
import org.allaymc.api.item.initinfo.ItemStackInitInfo;
import org.allaymc.api.item.interfaces.ItemLitDeepslateRedstoneOreStack;
import org.allaymc.server.component.interfaces.ComponentProvider;

public class ItemLitDeepslateRedstoneOreStackImpl extends ItemStackImpl implements ItemLitDeepslateRedstoneOreStack {
    public ItemLitDeepslateRedstoneOreStackImpl(ItemStackInitInfo initInfo,
            List<ComponentProvider<? extends Component>> componentProviders) {
        super(initInfo, componentProviders);
    }
}
