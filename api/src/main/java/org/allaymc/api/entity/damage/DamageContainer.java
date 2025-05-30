package org.allaymc.api.entity.damage;

import com.google.common.collect.Sets;
import it.unimi.dsi.fastutil.Pair;
import lombok.Getter;
import lombok.Setter;
import org.allaymc.api.block.type.BlockTypes;
import org.allaymc.api.entity.Entity;
import org.allaymc.api.entity.interfaces.EntityPlayer;
import org.allaymc.api.i18n.MayContainTrKey;
import org.allaymc.api.i18n.TrKeys;
import org.allaymc.api.utils.Utils;

import java.util.*;
import java.util.function.Function;
import java.util.function.UnaryOperator;

import static org.allaymc.api.entity.damage.DamageContainer.DamageType.*;

/**
 * DamageContainer is a container that stores the information of the damage,
 * including the attacker, the damage type, the source damage, the final damage, etc.
 *
 * @author daoge_cmd
 */
@Getter
public class DamageContainer {
    public static final int DEFAULT_DAMAGE_COOL_DOWN = 10;
    public static Set<DamageType> CANNOT_BE_REDUCED_BY_ARMOR_DAMAGE_TYPES = Sets.newHashSet(
            FIRE_TICK, SUFFOCATION, DROWN,
            STARVE, VOID, MAGIC, COMMAND,
            FLY_INTO_WALL, FREEZING, SONIC_BOOM
    );

    /**
     * The attacker of the damage <br>
     * Attacker not only can be an entity, but also can be a block or even null!
     */
    protected Object attacker;
    protected DamageType damageType;
    protected float sourceDamage;
    @Setter
    protected float finalDamage;
    @Setter
    protected float customKnockback = -1;
    protected float knockbackResistance = 0;
    @Setter
    protected int coolDown = DEFAULT_DAMAGE_COOL_DOWN;
    @Setter
    protected boolean critical;

    public DamageContainer(Object attacker, DamageType damageType, float sourceDamage) {
        // attacker is nullable
        this.attacker = attacker;
        this.damageType = Objects.requireNonNull(damageType);
        this.sourceDamage = sourceDamage;
        this.finalDamage = sourceDamage;
    }

    /**
     * Create a simple attack damage container.
     *
     * @param sourceDamage the source damage.
     *
     * @return the damage container.
     */
    public static DamageContainer simpleAttack(float sourceDamage) {
        return new DamageContainer(null, API, sourceDamage);
    }

    /**
     * Create an entity attack damage container.
     *
     * @param attacker     the attacker.
     * @param sourceDamage the source damage.
     *
     * @return the damage container.
     */
    public static DamageContainer entityAttack(Entity attacker, float sourceDamage) {
        var damageContainer = new DamageContainer(attacker, ENTITY_ATTACK, sourceDamage);
        damageContainer.setCritical(attacker.canCriticalAttack());
        return damageContainer;
    }

    /**
     * Create a starve damage container.
     *
     * @param sourceDamage the source damage.
     *
     * @return the damage container.
     */
    public static DamageContainer starve(float sourceDamage) {
        return new DamageContainer(null, STARVE, sourceDamage);
    }

    /**
     * Create a fall damage container.
     *
     * @param sourceDamage the source damage.
     *
     * @return the damage container.
     */
    public static DamageContainer fall(float sourceDamage) {
        return new DamageContainer(null, FALL, sourceDamage);
    }

    /**
     * Create a falling block damage container.
     *
     * @param sourceDamage the source damage.
     *
     * @return the damage container.
     */
    public static DamageContainer fallingBlock(float sourceDamage) {
        return new DamageContainer(null, FALLING_BLOCK, sourceDamage);
    }

    /**
     * Create a magic effect damage container.
     *
     * @param sourceDamage the source damage.
     *
     * @return the damage container.
     */
    public static DamageContainer magicEffect(float sourceDamage) {
        return new DamageContainer(null, MAGIC, sourceDamage);
    }

    /**
     * Create a magma damage container.
     *
     * @param sourceDamage the source damage.
     *
     * @return the damage container.
     */
    public static DamageContainer magma(float sourceDamage) {
        return new DamageContainer(null, MAGMA, sourceDamage);
    }

    /**
     * Create a drown damage container.
     *
     * @param sourceDamage the source damage.
     *
     * @return the damage container.
     */
    public static DamageContainer drown(float sourceDamage) {
        return new DamageContainer(null, DROWN, sourceDamage);
    }

    /**
     * Create a fire tick damage container.
     *
     * @param sourceDamage the source damage.
     *
     * @return the damage container.
     */
    public static DamageContainer fireTick(float sourceDamage) {
        return new DamageContainer(null, FIRE_TICK, sourceDamage);
    }

    /**
     * Create a lava damage container.
     *
     * @param sourceDamage the source damage.
     *
     * @return the damage container.
     */
    public static DamageContainer lava(float sourceDamage) {
        return new DamageContainer(null, LAVA, sourceDamage);
    }

    /**
     * Create a block explosion damage container.
     *
     * @param sourceDamage the source damage.
     *
     * @return the damage container.
     */
    public static DamageContainer blockExplosion(float sourceDamage) {
        return new DamageContainer(null, BLOCK_EXPLOSION, sourceDamage);
    }

    /**
     * Create an entity explosion damage container.
     *
     * @param attacker     the entity that exploded.
     * @param sourceDamage the source damage.
     *
     * @return the damage container.
     */
    public static DamageContainer entityExplosion(Entity attacker, float sourceDamage) {
        return new DamageContainer(attacker, ENTITY_EXPLOSION, sourceDamage);
    }

    /**
     * Create a contact damage container.
     *
     * @param sourceDamage the source damage.
     *
     * @return the damage container.
     */
    public static DamageContainer contact(float sourceDamage) {
        return new DamageContainer(null, CONTACT, sourceDamage);
    }

    /**
     * Get the attacker.
     *
     * @param <T> the type of the attacker.
     *
     * @return the attacker, or {@code null} if the attacker is not present.
     */
    public <T> T getAttacker() {
        // noinspection unchecked
        return (T) attacker;
    }

    /**
     * Check if the damage has a custom knockback.
     *
     * @return {@code true} if the damage has a custom knockback, otherwise {@code false}.
     */
    public boolean hasCustomKnockback() {
        return customKnockback != -1;
    }

    /**
     * Update the final damage using the given updater.
     *
     * @param updater the updater.
     */
    public void updateFinalDamage(UnaryOperator<Float> updater) {
        this.finalDamage = updater.apply(this.finalDamage);
    }

    /**
     * Check if the damage can be reduced by armor.
     *
     * @return {@code true} if the damage can be reduced by armor, otherwise {@code false}.
     */
    public boolean canBeReducedByArmor() {
        return !CANNOT_BE_REDUCED_BY_ARMOR_DAMAGE_TYPES.contains(damageType);
    }

    public static class DamageType {
        public static DamageType ANVIL = fixed(TrKeys.M_DEATH_ATTACK_ANVIL);
        /**
         * Block explosion damage
         */
        public static DamageType BLOCK_EXPLOSION = dynamic(block -> {
            if (block == BlockTypes.BED) return TrKeys.M_DEATH_ATTACK_EXPLOSION_BY_BED;
            return TrKeys.M_DEATH_ATTACK_EXPLOSION;
        });
        public static DamageType CHARGING = defaultFixed(); // TODO
        /**
         * Damage caused by contact with a block such as a Cactus
         */
        public static DamageType CONTACT = dynamic(block -> {
            if (block == BlockTypes.CACTUS) return TrKeys.M_DEATH_ATTACK_CACTUS;
            if (block == BlockTypes.SWEET_BERRY_BUSH) return TrKeys.M_DEATH_ATTACK_SWEETBERRY;
            return TrKeys.M_DEATH_ATTACK_GENERIC;
        });
        /**
         * Damage caused by running out of air underwater
         */
        public static DamageType DROWN = fixed(TrKeys.M_DEATH_ATTACK_DROWN);
        /**
         * Damage caused by being attacked by another entity
         */
        public static DamageType ENTITY_ATTACK = dynamicWithExtraSingleParam(attacker -> switch (attacker) {
            case EntityPlayer $ -> TrKeys.M_DEATH_ATTACK_PLAYER;
            case Entity $ -> TrKeys.M_DEATH_ATTACK_MOB;
            default -> TrKeys.M_DEATH_ATTACK_GENERIC;
        }, attacker -> switch (attacker) {
            case EntityPlayer player -> player.getDisplayName();
            case Entity entity -> entity.getNameTag() == null ? entity.getDisplayName() : entity.getNameTag();
            default -> null;
        });
        /**
         * Entity explosion damage
         */
        public static DamageType ENTITY_EXPLOSION = dynamicWithExtraSingleParam(attacker -> {
            if (attacker instanceof Entity) return TrKeys.M_DEATH_ATTACK_EXPLOSION_PLAYER;
            return TrKeys.M_DEATH_ATTACK_EXPLOSION;
        }, attacker -> switch (attacker) {
            case EntityPlayer player -> player.getDisplayName();
            case Entity entity -> entity.getNameTag() == null ? entity.getDisplayName() : entity.getNameTag();
            default -> null;
        });
        /**
         * Fall damage
         */
        public static DamageType FALL = fixed(TrKeys.M_DEATH_ATTACK_FALL);
        public static DamageType FALLING_BLOCK = fixed(TrKeys.M_DEATH_ATTACK_FALLINGBLOCK);
        /**
         * Damage caused by standing in fire
         */
        public static DamageType FIRE = fixed(TrKeys.M_DEATH_ATTACK_ONFIRE);
        public static DamageType FIREBALL = fixed(TrKeys.M_DEATH_ATTACK_FIREBALL);
        /**
         * Burn damage
         */
        public static DamageType FIRE_TICK = fixed(TrKeys.M_DEATH_ATTACK_INFIRE);
        public static DamageType FIREWORKS = fixed(TrKeys.M_DEATH_ATTACK_FIREWORKS);
        public static DamageType FLY_INTO_WALL = fixed(TrKeys.M_DEATH_ATTACK_FLYINTOWALL);
        public static DamageType FREEZING = fixed(TrKeys.M_DEATH_ATTACK_FREEZE);
        /**
         * Damage caused by standing in lava
         */
        public static DamageType LAVA = fixed(TrKeys.M_DEATH_ATTACK_LAVA);
        /**
         * Damage caused by being struck by lightning
         */
        public static DamageType LIGHTNING = fixed(TrKeys.M_DEATH_ATTACK_LIGHTNINGBOLT);
        /**
         * Potion or spell damage
         */
        public static DamageType MAGIC = fixed(TrKeys.M_DEATH_ATTACK_MAGIC);
        /**
         * Damage caused by standing on magma block
         */
        public static DamageType MAGMA = fixed(TrKeys.M_DEATH_ATTACK_MAGMA);
        public static DamageType PISTON = defaultFixed(); // TODO
        /**
         * Damage caused by being hit by a projectile such as an Arrow
         */
        public static DamageType PROJECTILE = fixedWithExtraSingleParam(TrKeys.M_DEATH_ATTACK_ARROW, attacker -> switch (attacker) {
            case EntityPlayer player -> player.getDisplayName();
            case Entity entity -> entity.getNameTag() == null ? entity.getDisplayName() : entity.getNameTag();
            default -> null;
        });
        public static DamageType RAM_ATTACK = defaultFixed(); // TODO
        public static DamageType STALACTITE = fixed(TrKeys.M_DEATH_ATTACK_STALACTITE);
        /**
         * Damage caused by hunger
         */
        public static DamageType STARVE = fixed(TrKeys.M_DEATH_ATTACK_STARVE);
        /**
         * Damage caused by being put in a block
         */
        public static DamageType SUFFOCATION = fixed(TrKeys.M_DEATH_ATTACK_INWALL);
        /**
         * Damage caused by submitting /kill command
         */
        public static DamageType COMMAND = defaultFixed();
        public static DamageType SONIC_BOOM = fixed(TrKeys.M_DEATH_ATTACK_SONICBOOM);
        public static DamageType TEMPERATURE = defaultFixed(); // TODO
        /**
         * Damage caused by thorns enchantment
         */
        public static DamageType THORNS = fixedWithExtraSingleParam(TrKeys.M_DEATH_ATTACK_THORNS, attacker -> switch (attacker) {
            case EntityPlayer player -> player.getDisplayName();
            case Entity entity -> entity.getNameTag() == null ? entity.getDisplayName() : entity.getNameTag();
            default -> null;
        });
        /**
         * Damage caused by falling into the void
         */
        public static DamageType VOID = fixed(TrKeys.M_DEATH_ATTACK_OUTOFWORLD);
        public static DamageType WITHER = fixed(TrKeys.M_DEATH_ATTACK_WITHER);
        /**
         * Plugins
         */
        public static DamageType API = defaultFixed();

        protected Function<Object, String> deathInfoProvider;
        protected Function<Object, String[]> deathInfoExtraParamsProvider;

        private DamageType(Function<Object, String> deathInfoProvider) {
            this(deathInfoProvider, $ -> Utils.EMPTY_STRING_ARRAY);
        }

        private DamageType(Function<Object, String> deathInfoProvider, Function<Object, String[]> deathInfoExtraParamsProvider) {
            this.deathInfoProvider = deathInfoProvider;
            this.deathInfoExtraParamsProvider = deathInfoExtraParamsProvider;
        }

        public static DamageType fixed(@MayContainTrKey String deathInfo) {
            return new DamageType($ -> deathInfo);
        }

        public static DamageType dynamic(Function<Object, String> deathInfoProvider) {
            return new DamageType(deathInfoProvider);
        }

        public static DamageType dynamicWithExtraParams(Function<Object, String> deathInfoProvider, Function<Object, String[]> deathInfoExtraParamsProvider) {
            return new DamageType(deathInfoProvider, deathInfoExtraParamsProvider);
        }

        public static DamageType dynamicWithExtraSingleParam(Function<Object, String> deathInfoProvider, Function<Object, String> deathInfoExtraSingleParamProvider) {
            return new DamageType(deathInfoProvider, attacker -> {
                var singleParam = deathInfoExtraSingleParamProvider.apply(attacker);
                return singleParam != null ? new String[]{singleParam} : Utils.EMPTY_STRING_ARRAY;
            });
        }

        public static DamageType fixedWithExtraSingleParam(@MayContainTrKey String deathInfo, Function<Object, String> deathInfoExtraSingleParamProvider) {
            return new DamageType($ -> deathInfo, attacker -> {
                var singleParam = deathInfoExtraSingleParamProvider.apply(attacker);
                return singleParam != null ? new String[]{singleParam} : Utils.EMPTY_STRING_ARRAY;
            });
        }

        public static DamageType defaultFixed() {
            return new DamageType($ -> TrKeys.M_DEATH_ATTACK_GENERIC);
        }

        public Pair<String, String[]> getDeathInfo(Entity victim, Object attacker) {
            // Put victim name to the first place
            List<String> params = new ArrayList<>();
            params.add(victim.getDisplayName());
            params.addAll(Arrays.asList(deathInfoExtraParamsProvider.apply(attacker)));
            return Pair.of(deathInfoProvider.apply(attacker), params.toArray(String[]::new));
        }
    }
}
