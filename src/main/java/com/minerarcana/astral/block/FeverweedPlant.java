package com.minerarcana.astral.block;

import com.minerarcana.astral.tags.AstralTags;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.neoforged.neoforge.common.IPlantable;

public class FeverweedPlant extends BushBlock {
    public static final MapCodec<FeverweedPlant> CODEC = RecordCodecBuilder.mapCodec(
            feverweedPlantInstance -> feverweedPlantInstance.group(ResourceKey
                            .codec(Registries.CONFIGURED_FEATURE).fieldOf("feature")
                            .forGetter(feverweedPlant -> feverweedPlant.feature), propertiesCodec())
                    .apply(feverweedPlantInstance, FeverweedPlant::new)
    );

    private final ResourceKey<ConfiguredFeature<?, ?>> feature;

    public FeverweedPlant(ResourceKey<ConfiguredFeature<?, ?>> configuredFeatureResourceKey, Properties properties) {
        super(properties);
        feature = configuredFeatureResourceKey;
    }

    @Override
    protected MapCodec<? extends BushBlock> codec() {
        return CODEC;
    }

    @Override
    public boolean canSustainPlant(BlockState state, BlockGetter world, BlockPos pos, Direction facing, IPlantable plantable) {
        return super.canSustainPlant(state, world, pos, facing, plantable) || state.is(AstralTags.FEVERWEED_GROW_BLOCK);
    }
}
