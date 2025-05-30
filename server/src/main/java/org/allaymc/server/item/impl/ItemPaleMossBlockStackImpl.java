package org.allaymc.server.item.impl;

import org.allaymc.api.component.interfaces.Component;
import org.allaymc.api.item.initinfo.ItemStackInitInfo;
import org.allaymc.api.item.interfaces.ItemPaleMossBlockStack;
import org.allaymc.server.component.interfaces.ComponentProvider;

import java.util.List;

public class ItemPaleMossBlockStackImpl extends ItemStackImpl implements ItemPaleMossBlockStack {
    public ItemPaleMossBlockStackImpl(ItemStackInitInfo initInfo,
                                      List<ComponentProvider<? extends Component>> componentProviders) {
        super(initInfo, componentProviders);
    }
}
