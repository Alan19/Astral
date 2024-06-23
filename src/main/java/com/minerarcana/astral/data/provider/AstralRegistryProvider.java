package com.minerarcana.astral.data.provider;

import com.minerarcana.astral.Astral;
import com.minerarcana.astral.worldgen.AstralFeatures;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class AstralRegistryProvider extends DatapackBuiltinEntriesProvider {
    private static final ResourceKey<ConfiguredFeature<?, ?>> CONFIGURED_SNOWBERRY_BUSH = ResourceKey.create(Registries.CONFIGURED_FEATURE, new ResourceLocation(Astral.MOD_ID, "snowberry_bush"));
    private static final ResourceKey<PlacedFeature> PLACED_SNOWBERRY_BUSH = ResourceKey.create(Registries.PLACED_FEATURE, new ResourceLocation(Astral.MOD_ID, "snowberry_bush"));


    public AstralRegistryProvider(PackOutput output, CompletableFuture<RegistrySetBuilder.PatchedRegistries> registries, Set<String> modIds) {
        super(output, registries, modIds);
    }
}
