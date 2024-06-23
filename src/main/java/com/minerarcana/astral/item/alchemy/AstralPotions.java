package com.minerarcana.astral.item.alchemy;

import com.minerarcana.astral.Astral;
import com.minerarcana.astral.item.AstralItems;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.registries.DeferredRegister;

@Mod.EventBusSubscriber(modid = Astral.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class AstralPotions {
    public static final DeferredRegister<Potion> POTIONS = DeferredRegister.create(Registries.POTION, Astral.MOD_ID);

    public static final PotionRegistryGroup FEVERWEED_BREW = new PotionRegistryGroup("feverweed_brew", () -> new Potion(PotionEffectInstances.BASE_FEVERWEED_INSTANCE.toArray(new MobEffectInstance[]{})), () -> Ingredient.of(AstralItems.FEVERWEED.get()))
            .setBase(Potions.THICK)
            .addLongBrew(() -> new Potion(PotionEffectInstances.LONG_FEVERWEED_INSTANCE.toArray(new MobEffectInstance[]{})))
            .addStrongBrew(() -> new Potion(PotionEffectInstances.STRONG_FEVERWEED_INSTANCE.toArray(new MobEffectInstance[]{}))).register(POTIONS);
    public static final PotionRegistryGroup SNOWBERRY_BREW = new PotionRegistryGroup("snowberry_brew", () -> new Potion(PotionEffectInstances.SNOWBERRY_BASE_INSTANCE.toArray(new MobEffectInstance[]{})), () -> Ingredient.of(AstralItems.SNOWBERRIES.get()))
            .setBase(Potions.THICK)
            .addLongBrew(() -> new Potion(PotionEffectInstances.LONG_SNOWBERRY_INSTANCE.toArray(new MobEffectInstance[]{})))
            .addStrongBrew(() -> new Potion(PotionEffectInstances.STRONG_SNOWBERRY_INSTANCE.toArray(new MobEffectInstance[]{})))
            .register(POTIONS);

    @SubscribeEvent
    public static void registerRecipes(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            FEVERWEED_BREW.registerBrewingRecipes();
            SNOWBERRY_BREW.registerBrewingRecipes();
        });
    }
}
