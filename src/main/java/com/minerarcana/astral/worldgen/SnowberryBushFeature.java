package com.minerarcana.astral.worldgen;

import com.minerarcana.astral.block.AstralBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SnowberryBushFeature extends Feature<SnowberryPatchConfig> {
    public SnowberryBushFeature() {
        super(SnowberryPatchConfig.CODEC);
    }

    @Override
    public boolean place(FeaturePlaceContext<SnowberryPatchConfig> pContext) {
        SnowberryPatchConfig snowberryPatchConfig = pContext.config();
        RandomSource randomsource = pContext.random();
        BlockPos blockpos = pContext.origin();
        WorldGenLevel worldgenlevel = pContext.level();
        BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos();
        int j = snowberryPatchConfig.xzSpread() + 1;
        int k = snowberryPatchConfig.ySpread() + 1;

        int generated = 0;
        int tryCount = 0;
        int maxCount = randomsource.nextIntBetweenInclusive(snowberryPatchConfig.minCount(), snowberryPatchConfig.maxCount());
        while (tryCount < snowberryPatchConfig.tries() && generated < maxCount) {
            pos.setWithOffset(blockpos, randomsource.nextInt(j) - randomsource.nextInt(j), randomsource.nextInt(k) - randomsource.nextInt(k), randomsource.nextInt(j) - randomsource.nextInt(j));
            if ((worldgenlevel.isEmptyBlock(pos) || worldgenlevel.getBlockState(pos).is(BlockTags.SNOW)) && AstralBlocks.SNOWBERRY_BUSH.get().canSurvive(worldgenlevel.getBlockState(pos), worldgenlevel, pos)) {
                spawnSnowberries(worldgenlevel, randomsource, pos);
                ++generated;
                System.out.println("Spawning Snowberry at " + blockpos);
            }
            ++tryCount;
        }

        return generated > 0;
    }

    private List<BlockPos> getAdjacentBlocks(BlockPos blockpos) {
        return new ArrayList<>(Arrays.asList(blockpos.east(), blockpos.west(), blockpos.north(), blockpos.south(), blockpos.north().east(), blockpos.north().west(), blockpos.south().east(), blockpos.south().west()));
    }

    private void spawnSnowberries(LevelAccessor worldIn, RandomSource rand, BlockPos generatingPos) {
        worldIn.setBlock(generatingPos.below(), Blocks.SNOW_BLOCK.defaultBlockState(), 2);
        worldIn.setBlock(generatingPos, AstralBlocks.SNOWBERRY_BUSH.get().defaultBlockState(), 2);
        for (BlockPos adjacentPos : getAdjacentBlocks(generatingPos)) {
            int layerLevel = rand.nextInt(4);
            if (worldIn.isEmptyBlock(adjacentPos) && layerLevel > 0 && Blocks.SNOW.defaultBlockState().canSurvive(worldIn, adjacentPos)) {
                worldIn.setBlock(adjacentPos, Blocks.SNOW.getStateDefinition().any().setValue(BlockStateProperties.LAYERS, layerLevel), 2);
            }
        }
    }

}