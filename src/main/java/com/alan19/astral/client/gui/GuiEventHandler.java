package com.alan19.astral.client.gui;

import com.alan19.astral.Astral;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.gui.screen.inventory.CreativeScreen;
import net.minecraft.client.gui.screen.inventory.InventoryScreen;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class GuiEventHandler {
    @SubscribeEvent
    public static void addWidgetToInventory(GuiScreenEvent.InitGuiEvent.Post event) {
        Screen screen = event.getGui();
        if (screen instanceof InventoryScreen || screen instanceof CreativeScreen) {
            ContainerScreen<?> gui = (ContainerScreen<?>) screen;
            int textSize = screen instanceof CreativeScreen ? 10 : 14;
            event.addWidget(new AstralInventoryButton(gui.getGuiLeft() + 150, gui.height / 2 - 22, 20, 18, 0, 0, 19, new ResourceLocation(Astral.MOD_ID, "textures/gui/astral_inventory_button.png")));
        }
    }
}
