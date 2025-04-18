package org.allaymc.server.entity.impl;

import lombok.experimental.Delegate;
import org.allaymc.api.component.interfaces.Component;
import org.allaymc.api.entity.component.EntityDamageComponent;
import org.allaymc.api.entity.component.EntityXpOrbBaseComponent;
import org.allaymc.api.entity.component.attribute.EntityAttributeComponent;
import org.allaymc.api.entity.initinfo.EntityInitInfo;
import org.allaymc.api.entity.interfaces.EntityXpOrb;
import org.allaymc.server.component.interfaces.ComponentProvider;

import java.util.List;

public class EntityXpOrbImpl extends EntityImpl implements EntityXpOrb {

    @Delegate
    protected EntityDamageComponent damageComponent;
    @Delegate
    protected EntityAttributeComponent attributeComponent;

    public EntityXpOrbImpl(EntityInitInfo initInfo,
                           List<ComponentProvider<? extends Component>> componentProviders) {
        super(initInfo, componentProviders);
    }

    @Delegate
    @Override
    public EntityXpOrbBaseComponent getBaseComponent() {
        return (EntityXpOrbBaseComponent) super.getBaseComponent();
    }
}
