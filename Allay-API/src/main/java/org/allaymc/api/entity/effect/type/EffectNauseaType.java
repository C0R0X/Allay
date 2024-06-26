package org.allaymc.api.entity.effect.type;

import org.allaymc.api.entity.effect.AbstractEffectType;
import org.allaymc.api.utils.Identifier;

import java.awt.*;

/**
 * Allay Project 2023/10/27
 *
 * @author daoge_cmd
 */
public class EffectNauseaType extends AbstractEffectType {
    public static final EffectNauseaType NAUSEA_TYPE = new EffectNauseaType();

    private EffectNauseaType() {
        super(9, new Identifier("minecraft:nausea"), new Color(85, 29, 74), true);
    }
}
