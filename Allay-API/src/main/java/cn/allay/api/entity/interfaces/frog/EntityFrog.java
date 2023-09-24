package cn.allay.api.entity.interfaces.frog;

import cn.allay.api.data.VanillaEntityId;
import cn.allay.api.entity.Entity;
import cn.allay.api.entity.type.EntityType;
import cn.allay.api.entity.type.EntityTypeBuilder;

/**
 * @author daoge_cmd <br>
 * Allay Project <br>
 */
public interface EntityFrog extends Entity {
  EntityType<EntityFrog> FROG_TYPE = EntityTypeBuilder
          .builder(EntityFrog.class)
          .vanillaEntity(VanillaEntityId.FROG)
          .build();
}