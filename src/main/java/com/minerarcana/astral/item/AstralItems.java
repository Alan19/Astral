package com.minerarcana.astral.item;

import com.minerarcana.astral.Astral;
import com.minerarcana.astral.block.AstralBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemNameBlockItem;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class AstralItems {
    // Create a Deferred Register to hold Items which will all be registered under the "astral" namespace
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(Astral.MOD_ID);

    // Create a Deferred Register to hold CreativeModeTabs which will all be registered under the "astral" namespace
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Astral.MOD_ID);

    public static final Supplier<ItemNameBlockItem> SNOWBERRIES = ITEMS.register("snowberries", () -> new ItemNameBlockItem(AstralBlocks.SNOWBERRY_BUSH.get(), new Item.Properties().food(new FoodProperties.Builder()
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

    // Creates a creative tab with the id "astral:example_tab" for the example item, that is placed after the combat tab
    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> ASTRAL_TAB = CREATIVE_MODE_TABS.register("astral", () -> CreativeModeTab.builder()
            .icon(() -> SNOWBERRIES.get().getDefaultInstance())
            .title(Component.translatable("itemGroup.astral"))
            .displayItems((parameters, output) -> {
                output.accept(AstralItems.FEVERWEED.get());
                output.accept(AstralItems.SNOWBERRIES.get());
            }).build());
}
