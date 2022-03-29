package com.alan19.astral.network;

import com.alan19.astral.api.AstralAPI;
import com.alan19.astral.api.sleepmanager.ISleepManager;
import com.alan19.astral.api.sleepmanager.SleepManager;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.util.LogicalSidedProvider;
import net.minecraftforge.network.NetworkEvent;

import java.util.Optional;
import java.util.function.Supplier;

public class SendAstralTravelStarting {
    private final int id;
    private final ISleepManager sleepManager;

    public SendAstralTravelStarting(int id, ISleepManager sleepManager) {
        this.id = id;
        this.sleepManager = sleepManager;
    }

    public static SendAstralTravelStarting decode(FriendlyByteBuf packetBuffer) {
        int playerId = packetBuffer.readInt();
        SleepManager sleepManager = new SleepManager();
        sleepManager.setSleep(packetBuffer.readInt());
        sleepManager.setGoingToInnerRealm(packetBuffer.readBoolean());
        return new SendAstralTravelStarting(playerId, sleepManager);
    }

    public static void encode(SendAstralTravelStarting sendAstralTravelStarting, FriendlyByteBuf packetBuffer) {
        packetBuffer.writeInt(sendAstralTravelStarting.id);
        packetBuffer.writeInt(sendAstralTravelStarting.sleepManager.getSleep());
        packetBuffer.writeBoolean(sendAstralTravelStarting.sleepManager.isGoingToInnerRealm());
    }

    public static void handle(SendAstralTravelStarting sendAstralTravelStarting, Supplier<NetworkEvent.Context> contextSupplier) {
        System.out.println("Received on client!");
        contextSupplier.get().enqueueWork(() -> {
            final Optional<Level> optionalWorld = LogicalSidedProvider.CLIENTWORLD.get(contextSupplier.get().getDirection().getReceptionSide());
            optionalWorld.ifPresent(world -> {
                if (world.getEntity(sendAstralTravelStarting.id) instanceof Player) {
                    Player player = (Player) world.getEntity(sendAstralTravelStarting.id);
                    AstralAPI.getSleepManager(player).ifPresent(sleepManager1 -> {
                        sleepManager1.setSleep(sendAstralTravelStarting.sleepManager.getSleep());
                        sleepManager1.setGoingToInnerRealm(sendAstralTravelStarting.sleepManager.isGoingToInnerRealm());
                    });
                }
            });
        });
        contextSupplier.get().setPacketHandled(true);
    }
}
