package com.minerarcana.astral.data.provider;

import com.minerarcana.astral.Astral;
import com.minerarcana.astral.block.AstralBlocks;
import com.minerarcana.astral.tags.AstralTags;
import com.minerarcana.astral.worldgen.AstralFeatures;
import com.minerarcana.astral.worldgen.SnowberryPatchConfig;
import net.minecraft.core.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.placement.*;
import net.neoforged.neoforge.common.world.BiomeModifiers;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class AstralDatapackRegistryProvider extends BaseDatapackRegistryProvider {
    public AstralDatapackRegistryProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, BUILDER, Astral.MOD_ID);
    }

    private static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
            .add(NeoForgeRegistries.Keys.BIOME_MODIFIERS, context -> {
                HolderSet.Named<Biome> isTaiga = context.lookup(Registries.BIOME).getOrThrow(BiomeTags.IS_TAIGA);
                HolderSet.Named<Biome> isJungle = context.lookup(Registries.BIOME).getOrThrow(BiomeTags.IS_JUNGLE);

                HolderSet.Direct<PlacedFeature> snowberryHolderSet = HolderSet.direct(Holder.direct(new PlacedFeature(Holder.direct(new ConfiguredFeature<>(AstralFeatures.PATCH_SNOWBERRIES.get(),
                        new SnowberryPatchConfig(2,
                                5,
                                2,
                                5,
                                128
                        ))), List.of(RarityFilter.onAverageOnceEvery(25),
                        InSquarePlacement.spread(),
                        HeightmapPlacement.onHeightmap(Heightmap.Types.WORLD_SURFACE_WG),
                        BiomeFilter.biome()))));
                HolderSet.Direct<PlacedFeature> feverweedHolderSet = HolderSet.direct(Holder.direct(new PlacedFeature(Holder.direct(new ConfiguredFeature<>(Feature.RANDOM_PATCH,
                        FeatureUtils.simpleRandomPatchConfiguration(96, PlacementUtils.filtered(Feature.SIMPLE_BLOCK,
                                new SimpleBlockConfiguration(BlockStateProvider.simple(AstralBlocks.FEVERWEED_PLANT.get())),
                                BlockPredicate.allOf(BlockPredicate.ONLY_IN_AIR_PREDICATE, BlockPredicate.matchesTag(Direction.DOWN.getNormal(), AstralTags.FEVERWEED_GROW_BLOCK)))))), List.of(RarityFilter.onAverageOnceEvery(10),
                        InSquarePlacement.spread(),
                        HeightmapPlacement.onHeightmap(Heightmap.Types.WORLD_SURFACE_WG),
                        BiomeFilter.biome()))));
                context.register(biomeModifier(new ResourceLocation(Astral.MOD_ID, "patch_snowberry_bush")), new BiomeModifiers.AddFeaturesBiomeModifier(isTaiga, snowberryHolderSet, GenerationStep.Decoration.VEGETAL_DECORATION));
                context.register(biomeModifier(new ResourceLocation(Astral.MOD_ID, "patch_feverweed")), new BiomeModifiers.AddFeaturesBiomeModifier(isJungle, feverweedHolderSet, GenerationStep.Decoration.VEGETAL_DECORATION));
            });
}
