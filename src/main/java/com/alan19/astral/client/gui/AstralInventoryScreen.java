package com.alan19.astral.client.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.DisplayEffectsScreen;
import net.minecraft.client.gui.screen.inventory.InventoryScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;

@OnlyIn(Dist.CLIENT)
public class AstralInventoryScreen extends DisplayEffectsScreen<AstralInventoryContainer> {

    public AstralInventoryScreen(AstralInventoryContainer screenContainer, PlayerInventory inv, ITextComponent titleIn) {
        super(screenContainer, inv, titleIn);
    }

    @Override
    protected void renderBg(@Nonnull MatrixStack matrixStack, float partialTicks, int mouseX, int mouseY) {
        AstralDrawable.ASTRAL_INVENTORY_DRAWABLE.draw(leftPos, topPos, 175, 165);
        int i = this.leftPos;
        int j = this.topPos;

        if (Minecraft.getInstance().player != null) {
            InventoryScreen.renderEntityInInventory(i + 51, j + 75, 30, (float) (i + 51) - mouseX, (float) (j + 75 - 50) - mouseY, Minecraft.getInstance().player);
        }
    }

    @Override
    public void render(@Nonnull MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        renderBackground(matrixStack);
        renderBg(matrixStack, partialTicks, mouseX, mouseY);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        renderLabels(matrixStack, mouseX, mouseY);
        renderTooltip(matrixStack, mouseX, mouseY);
    }
}
