package com.minerarcana.astral.block;

import com.minerarcana.astral.tags.AstralTags;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;

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
    protected boolean mayPlaceOn(BlockState pState, BlockGetter pLevel, BlockPos pPos) {
        return pState.is(AstralTags.FEVERWEED_GROW_BLOCK);
    }

    @Override
    public void randomTick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom) {
        if (pRandom.nextInt(25) == 0) {
            int i = 5;

            for (BlockPos blockpos : BlockPos.betweenClosed(pPos.offset(-4, -1, -4), pPos.offset(4, 1, 4))) {
                if (pLevel.getBlockState(blockpos).is(this) && --i <= 0) {
                    return;
                }
            }

            BlockPos blockpos1 = pPos.offset(pRandom.nextInt(3) - 1, pRandom.nextInt(2) - pRandom.nextInt(2), pRandom.nextInt(3) - 1);

            for (int k = 0; k < 4; ++k) {
                if (pLevel.isEmptyBlock(blockpos1) && pState.canSurvive(pLevel, blockpos1)) {
                    pPos = blockpos1;
                }

                blockpos1 = pPos.offset(pRandom.nextInt(3) - 1, pRandom.nextInt(2) - pRandom.nextInt(2), pRandom.nextInt(3) - 1);
            }

            if (pLevel.isEmptyBlock(blockpos1) && pState.canSurvive(pLevel, blockpos1)) {
                pLevel.setBlock(blockpos1, pState, 2);
            }
        }
    }
}
