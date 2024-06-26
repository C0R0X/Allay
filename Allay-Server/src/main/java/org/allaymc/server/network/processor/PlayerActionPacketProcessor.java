package org.allaymc.server.network.processor;

import org.allaymc.api.entity.interfaces.EntityPlayer;
import org.allaymc.api.math.location.Location3f;
import org.allaymc.api.network.processor.PacketProcessor;
import org.cloudburstmc.protocol.bedrock.data.entity.EntityDataTypes;
import org.cloudburstmc.protocol.bedrock.packet.BedrockPacketType;
import org.cloudburstmc.protocol.bedrock.packet.PlayerActionPacket;
import org.cloudburstmc.protocol.common.PacketSignal;

public class PlayerActionPacketProcessor extends PacketProcessor<PlayerActionPacket> {
    @Override
    public PacketSignal handleAsync(EntityPlayer player, PlayerActionPacket packet) {
        return switch (packet.getAction()) {
            case RESPAWN -> {
                var spawnPoint = player.getSpawnPoint();
                var dimension = spawnPoint.dimension();
                dimension.getChunkService().getOrLoadChunkSynchronously(spawnPoint.x() >> 4, spawnPoint.z() >> 4);
                dimension.addPlayer(player, () -> {
                    player.teleport(new Location3f(spawnPoint.x(), spawnPoint.y(), spawnPoint.z(), dimension));
                    player.setSprinting(false);
                    player.setSneaking(false);
                    player.removeAllEffects();
                    player.setHealth(player.getMaxHealth());
                    player.setAndSendEntityData(EntityDataTypes.AIR_SUPPLY, (short) 400);
                });
                yield PacketSignal.HANDLED;
            }
            case DIMENSION_CHANGE_SUCCESS -> {
                player.sendDimensionChangeSuccess();
                yield PacketSignal.HANDLED;
            }
            default -> PacketSignal.UNHANDLED;
        };
    }

    @Override
    public BedrockPacketType getPacketType() {
        return BedrockPacketType.PLAYER_ACTION;
    }
}
