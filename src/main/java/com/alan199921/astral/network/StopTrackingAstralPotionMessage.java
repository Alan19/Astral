package com.alan199921.astral.network;

import com.alan199921.astral.effects.AstralEffects;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.world.World;
import net.minecraftforge.fml.LogicalSidedProvider;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.Optional;
import java.util.function.Supplier;

public class StopTrackingAstralPotionMessage {
    private final int entityID;

    public StopTrackingAstralPotionMessage(int entityID) {
        this.entityID = entityID;
    }

    public static StopTrackingAstralPotionMessage decode(PacketBuffer packetBuffer){
        return new StopTrackingAstralPotionMessage(packetBuffer.readInt());
    }

    public static void encode(StopTrackingAstralPotionMessage startTrackingAstralPotionMessage, PacketBuffer packetBuffer){
        packetBuffer.writeInt(startTrackingAstralPotionMessage.entityID);
    }

    public static void handle(StopTrackingAstralPotionMessage startTrackingAstralPotionMessage, Supplier<NetworkEvent.Context> contextSupplier){
        contextSupplier.get().enqueueWork(() -> {
            final Optional<World> optionalWorld = LogicalSidedProvider.CLIENTWORLD.get(contextSupplier.get().getDirection().getReceptionSide());

            optionalWorld.ifPresent(world -> {
                        Entity clientEntity = world.getEntityByID(startTrackingAstralPotionMessage.entityID);
                        assert clientEntity != null;
                        if (clientEntity.isLiving()) {
                            LivingEntity clientLivingEntity = (LivingEntity) world.getEntityByID(startTrackingAstralPotionMessage.entityID);
                            clientLivingEntity.removeActivePotionEffect(AstralEffects.ASTRAL_TRAVEL_EFFECT);
                        }
                    }
            );
        });
        contextSupplier.get().setPacketHandled(true);
    }
}
