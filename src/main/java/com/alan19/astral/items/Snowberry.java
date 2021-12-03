package com.alan19.astral.items;

import com.alan19.astral.blocks.AstralBlocks;
import com.alan19.astral.configs.AstralConfig;
import net.minecraft.item.BlockNamedItem;
import net.minecraft.item.Food;
import net.minecraft.item.Item;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;

public class Snowberry extends BlockNamedItem {
    /**
     * Snowberries heal 1 saturation and hunger
     * Gives regeneration 2 and nausea 2 for 15 seconds (depends on configs)
     */
    public Snowberry() {
        super(AstralBlocks.SNOWBERRY_BUSH.get(), new Item.Properties()
                .tab(AstralItems.ASTRAL_ITEMS)
                .food(new Food.Builder()
                        .alwaysEat()
                        .saturationMod(-1F)
                        .nutrition(1)
                        .fast()
                        .effect(() -> new EffectInstance(Effects.CONFUSION, AstralConfig.getEffectDuration().snowberryNauseaDuration.get(), 1), 1)
                        .effect(() -> new EffectInstance(Effects.REGENERATION, AstralConfig.getEffectDuration().snowberryRegenerationDuration.get(), 1), 1)
                        .build()));
    }
}
