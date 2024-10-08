package org.allaymc.api.eventbus.event.entity;

import org.allaymc.api.entity.Entity;
import org.allaymc.api.entity.effect.EffectInstance;

/**
 * Allay Project 2024/8/3
 *
 * @author daoge_cmd
 */
public class EntityEffectRemoveEvent extends EntityEffectEvent {
    public EntityEffectRemoveEvent(Entity entity, EffectInstance effect) {
        super(entity, effect);
    }
}
