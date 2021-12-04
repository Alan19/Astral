package com.alan19.astral.network;

import com.alan19.astral.client.gui.AstralContainerProvider;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fml.network.NetworkHooks;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class SendOpenAstralInventory {
    public static void encode(SendOpenAstralInventory sendOpenAstralInventory, FriendlyByteBuf packetBuffer) {
        //No need to encode
    }

    public static SendOpenAstralInventory decode(FriendlyByteBuf packetBuffer) {
        return new SendOpenAstralInventory();
    }

    public static void handle(SendOpenAstralInventory sendOpenAstralInventory, Supplier<NetworkEvent.Context> contextSupplier) {
        contextSupplier.get().enqueueWork(() -> {
            ServerPlayer player = contextSupplier.get().getSender();
            if (player != null) {
                ItemStack stack = player.inventory.getCarried();
                player.inventory.setCarried(ItemStack.EMPTY);
                NetworkHooks.openGui(player, new AstralContainerProvider());

                if (!stack.isEmpty()) {
                    player.inventory.setCarried(stack);
                    AstralNetwork.syncPacketGrabbedItem(player, stack);
                }
            }
        });
        contextSupplier.get().setPacketHandled(true);
    }
}
