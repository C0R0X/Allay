package org.allaymc.server.entity.component;

import lombok.Getter;
import lombok.Setter;
import org.allaymc.api.block.component.BlockFallableComponent;
import org.allaymc.api.block.data.BlockFace;
import org.allaymc.api.block.tag.BlockCustomTags;
import org.allaymc.api.block.type.BlockState;
import org.allaymc.api.block.type.BlockTypes;
import org.allaymc.api.entity.component.EntityDamageComponent;
import org.allaymc.api.entity.component.EntityFallingBlockBaseComponent;
import org.allaymc.api.entity.damage.DamageContainer;
import org.allaymc.api.entity.initinfo.EntityInitInfo;
import org.allaymc.api.registry.Registries;
import org.cloudburstmc.nbt.NbtMap;
import org.cloudburstmc.protocol.bedrock.data.entity.EntityDataTypes;
import org.cloudburstmc.protocol.bedrock.data.entity.EntityFlag;
import org.joml.Vector3d;
import org.joml.primitives.AABBd;
import org.joml.primitives.AABBdc;

import java.util.Objects;

/**
 * @author IWareQ
 */
@Getter
@Setter
public class EntityFallingBlockBaseComponentImpl extends EntityBaseComponentImpl implements EntityFallingBlockBaseComponent {
    protected BlockState blockState;

    public EntityFallingBlockBaseComponentImpl(EntityInitInfo info) {
        super(info);
        // The initial onGround state for falling block is false
        // And it will be either turned into block or item based
        // on the block which the falling block fell on
        this.onGround = false;
    }

    @Override
    protected void initMetadata() {
        Objects.requireNonNull(blockState, "blockState");

        updateHitBoxAndCollisionBoxMetadata();
        metadata.set(EntityFlag.HAS_GRAVITY, true);
        metadata.set(EntityFlag.FIRE_IMMUNE, true);
        metadata.set(EntityFlag.HAS_COLLISION, false);
        metadata.set(EntityDataTypes.VARIANT, blockState.blockStateHash());
    }

    @Override
    public void tick(long currentTick) {
        super.tick(currentTick);
        tickFalling();
    }

    protected void tickFalling() {
        if (this.willBeDespawnedNextTick() || onGround) {
            // The falling block entity already became block
            return;
        }

        var dimension = getDimension();
        var currentBlock = dimension.getBlockState(location);
        if (currentBlock.getBlockType() == BlockTypes.AIR) {
            return;
        }

        if (currentBlock.getBlockType().hasBlockTag(BlockCustomTags.REPLACEABLE)) {
            dimension.setBlockState(location.floor(new Vector3d()), BlockTypes.AIR.getDefaultState());
        }
    }

    @Override
    public void onFall(double fallDistance) {
        super.onFall(fallDistance);

        var dimension = getDimension();
        // It is better to place the block when the Entity is removed, so that it looks visually normal.
        dimension.getEntityService().removeEntity(thisEntity, () -> {
            if (!(blockState.getBehavior() instanceof BlockFallableComponent fallableComponent)) {
                return;
            }

            var damage = fallableComponent.calculateDamage(fallDistance);
            if (damage > 0) {
                dimension.getEntityService().getPhysicsService().computeCollidingEntities(getOffsetAABB(), true)
                        .stream()
                        .filter(entity -> entity instanceof EntityDamageComponent)
                        .map(EntityDamageComponent.class::cast)
                        .forEach(entity -> entity.attack(DamageContainer.fallingBlock(damage)));
            }

            if (!getBlockStateStandingOn().blockState().getBlockStateData().shape().isFull(BlockFace.UP)) {
                // Falling on a block which is not full in upper face, for example torch.
                // In this case, the falling block should be turned into item instead of block
                dimension.dropItem(blockState.toItemStack(), location);
            } else {
                fallableComponent.onLanded(location, fallDistance, blockState);
            }
        });
    }

    @Override
    public NbtMap saveNBT() {
        return super.saveNBT()
                .toBuilder()
                .putInt("BlockStateHash", blockState.blockStateHash())
                .build();
    }

    @Override
    public void loadNBT(NbtMap nbt) {
        super.loadNBT(nbt);
        nbt.listenForInt("BlockStateHash", blockStateHash ->
                blockState = Registries.BLOCK_STATE_PALETTE.get(blockStateHash)
        );
    }

    @Override
    public AABBdc getAABB() {
        return new AABBd(-0.49, 0, -0.49, 0.49, 0.98, 0.49);
    }

    @Override
    public double getGravity() {
        return 0.04;
    }

    @Override
    public float getNetworkOffset() {
        return 0.49f;
    }
}
