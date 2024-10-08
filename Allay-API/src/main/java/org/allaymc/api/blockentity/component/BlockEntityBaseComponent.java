package org.allaymc.api.blockentity.component;

import org.allaymc.api.block.type.BlockState;
import org.allaymc.api.blockentity.BlockEntity;
import org.allaymc.api.blockentity.type.BlockEntityType;
import org.allaymc.api.entity.interfaces.EntityPlayer;
import org.allaymc.api.math.position.Position3ic;
import org.allaymc.api.world.Dimension;
import org.allaymc.api.world.World;
import org.cloudburstmc.math.vector.Vector3i;
import org.cloudburstmc.nbt.NbtMap;
import org.cloudburstmc.protocol.bedrock.packet.BedrockPacket;
import org.cloudburstmc.protocol.bedrock.packet.BlockEntityDataPacket;

import java.util.Objects;

/**
 * Allay Project 2023/9/15
 *
 * @author daoge_cmd
 */
public interface BlockEntityBaseComponent extends BlockEntityComponent {

    BlockEntityType<? extends BlockEntity> getBlockEntityType();

    Position3ic getPosition();

    default Dimension getDimension() {
        return getPosition().dimension();
    }

    default World getWorld() {
        return getDimension().getWorld();
    }

    NbtMap saveNBT();

    void loadNBT(NbtMap nbt);

    default void applyClientChange(EntityPlayer player, NbtMap nbt) {
        loadNBT(nbt);
    }

    default BlockEntityDataPacket createBlockEntityDataPacket() {
        var packet = new BlockEntityDataPacket();
        var pos = getPosition();
        packet.setBlockPosition(Vector3i.from(pos.x(), pos.y(), pos.z()));
        packet.setData(saveNBT());
        return packet;
    }

    default void sendBlockEntityDataPacketTo(EntityPlayer player) {
        player.sendPacket(createBlockEntityDataPacket());
    }

    default void sendBlockEntityDataPacketToViewers() {
        sendPacketToViewers(createBlockEntityDataPacket());
    }

    default void sendPacketToViewers(BedrockPacket packet) {
        var pos = getPosition();
        var chunk = pos.dimension().getChunkService().getChunkByLevelPos(pos.x(), pos.z());
        Objects.requireNonNull(chunk, "The chunk located at pos " + pos + " is not loaded!");
        chunk.sendChunkPacket(packet);
    }

    default void tick(long currentTick) {}

    default boolean sendToClient() {
        return true;
    }

    default BlockState getBlockState() {
        var pos = getPosition();
        return pos.dimension().getBlockState(pos.x(), pos.y(), pos.z());
    }

    /**
     * Get the custom name of the block entity
     *
     * @return the custom name of the block entity, null if not present
     */
    String getCustomName();

    /**
     * Set the custom name of the block entity
     *
     * @param customName the custom name of the block entity, can be null to remove the custom name
     */
    void setCustomName(String customName);

    default boolean hasCustomName() {
        return getCustomName() != null;
    }

    default void clearCustomName() {
        setCustomName(null);
    }
}
