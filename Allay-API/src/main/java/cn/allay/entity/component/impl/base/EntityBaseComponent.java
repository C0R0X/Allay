package cn.allay.entity.component.impl.base;

import cn.allay.component.annotation.Inject;
import cn.allay.entity.Entity;
import cn.allay.entity.type.EntityType;
import cn.allay.math.location.LocRO;

/**
 * Author: daoge_cmd <br>
 * Date: 2023/5/26 <br>
 * Allay Project <br>
 */
public interface EntityBaseComponent {
    @Inject
    EntityType<? extends Entity> getEntityType();

    @Inject
    LocRO<Float> getLocation();
}