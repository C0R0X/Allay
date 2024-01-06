package org.allaymc.api.item.component.common;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.experimental.Accessors;
import org.allaymc.api.utils.JSONUtils;
import org.cloudburstmc.nbt.NbtMap;

/**
 * Allay Project 2023/5/19
 *
 * @author daoge_cmd
 */
@Getter
@Accessors(fluent = true)
@Builder
@EqualsAndHashCode
public class ItemAttributes {
    public static ItemAttributes DEFAULT = ItemAttributes.builder().build();
    @Builder.Default
    protected int armorValue = 0;
    @Builder.Default
    protected int attackDamage = 0;
    //auxValuesDescription
    @Builder.Default
    protected boolean canBeCharged = false;
    @Builder.Default
    protected boolean canBeDepleted = false;
    @Builder.Default
    protected boolean canDestroyInCreative = false;
    @Builder.Default
    protected boolean canUseOnSimTick = false;
    @Builder.Default
    protected int cooldownTime = 0;
    @Builder.Default
    protected String cooldownType = "";
    @Builder.Default
    protected int creativeCategory = 0;
    @Builder.Default
    protected String creativeGroup = "";
    @Builder.Default
    protected int id = Integer.MAX_VALUE;
    @Builder.Default
    protected boolean isArmor = false;
    @Builder.Default
    protected boolean isBlockPlanterItem = false;
    @Builder.Default
    protected boolean isDamageable = false;
    @Builder.Default
    protected boolean isDye = false;
    @Builder.Default
    protected boolean isDyeable = false;
    @Builder.Default
    protected boolean isElytra = false;
    @Builder.Default
    protected boolean isFertilizer = false;
    @Builder.Default
    protected boolean isFood = false;
    @Builder.Default
    protected boolean isThrowable = false;
    @Builder.Default
    protected boolean isUseable = false;
    @Builder.Default
    protected String itemColorName = "";
    @Builder.Default
    protected int itemColorRGB = 0;
    @Builder.Default
    protected int maxDamage = 0;
    @Builder.Default
    protected int maxStackSize = 64;
    @Builder.Default
    protected String name = "";
    @Builder.Default
    protected int toughnessValue = 0;
    @Builder.Default
    protected float viewDamping = 1.0f;

    public static ItemAttributes fromJson(String json) {
        return JSONUtils.from(json, ItemAttributes.class);
    }

    //TODO: test
    public static ItemAttributes fromNBT(NbtMap nbt) {
        return ItemAttributes
                .builder()
                .armorValue(nbt.getInt("armorValue"))
                .attackDamage(nbt.getInt("attackDamage"))
                .canBeCharged(nbt.getBoolean("canBeCharged"))
                .canBeDepleted(nbt.getBoolean("canBeDepleted"))
                .canDestroyInCreative(nbt.getBoolean("canDestroyInCreative"))
                .canUseOnSimTick(nbt.getBoolean("canUseOnSimTick"))
                .cooldownTime(nbt.getInt("cooldownTime"))
                .cooldownType(nbt.getString("cooldownType"))
                .creativeCategory(nbt.getInt("creativeCategory"))
                .creativeGroup(nbt.getString("creativeGroup"))
                .id(nbt.getInt("id"))
                .isArmor(nbt.getBoolean("isArmor"))
                .isBlockPlanterItem(nbt.getBoolean("isBlockPlanterItem"))
                .isDamageable(nbt.getBoolean("isDamageable"))
                .isDye(nbt.getBoolean("isDye"))
                .isDyeable(nbt.getBoolean("isDyeable"))
                .isElytra(nbt.getBoolean("isElytra"))
                .isFertilizer(nbt.getBoolean("isFertilizer"))
                .isFood(nbt.getBoolean("isFood"))
                .isThrowable(nbt.getBoolean("isThrowable"))
                .isUseable(nbt.getBoolean("isUseable"))
                .itemColorName(nbt.getString("itemColorName"))
                .itemColorRGB(nbt.getInt("itemColorRGB"))
                .maxDamage(nbt.getInt("maxDamage"))
                .maxStackSize(nbt.getInt("maxStackSize"))
                .name(nbt.getString("name"))
                .toughnessValue(nbt.getInt("toughnessValue"))
                .viewDamping(nbt.getFloat("viewDamping"))
                .build();
    }
}