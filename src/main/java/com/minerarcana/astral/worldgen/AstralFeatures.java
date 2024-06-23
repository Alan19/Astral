package com.minerarcana.astral.worldgen;

import com.minerarcana.astral.Astral;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class AstralFeatures {
    public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(Registries.FEATURE, Astral.MOD_ID);
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_FEVERWEED = FeatureUtils.createKey("patch_feverweed");
    public static final DeferredHolder<Feature<?>, SnowberryBushFeature> PATCH_SNOWBERRIES = FEATURES.register("snowberries", SnowberryBushFeature::new);
}
