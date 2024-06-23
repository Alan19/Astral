package com.minerarcana.astral.item;

import com.minerarcana.astral.Astral;
import com.minerarcana.astral.block.AstralBlocks;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class AstralItems {
    // Create a Deferred Register to hold Items which will all be registered under the "astral" namespace
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(Astral.MOD_ID);

    public static final Supplier<Item> SNOWBERRIES = ITEMS.register("snowberries", () -> new Item(new Item.Properties().food(new FoodProperties.Builder()
            .fast()
            .effect(() -> new MobEffectInstance(MobEffects.REGENERATION, 300, 1), 1)
            .effect(() -> new MobEffectInstance(MobEffects.CONFUSION, 300, 1), 1)
            .saturationMod(-1)
            .nutrition(1)
            .build())));

    public static final Supplier<BlockItem> FEVERWEED = ITEMS.register("feverweed", () -> new BlockItem(AstralBlocks.FEVERWEED_PLANT.get(), new Item.Properties().food(new FoodProperties.Builder()
            .fast()
            .effect(() -> new MobEffectInstance(MobEffects.LUCK, 300, 1), 1)
            .effect(() -> new MobEffectInstance(MobEffects.HUNGER, 300, 1), 1)
            .saturationMod(-1)
            .nutrition(1)
            .build())));
}
