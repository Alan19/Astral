package com.minerarcana.astral.block;

import com.minerarcana.astral.item.AstralItems;
import com.minerarcana.astral.tags.AstralTags;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SnowLayerBlock;
import net.minecraft.world.level.block.SweetBerryBushBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.neoforged.neoforge.common.CommonHooks;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Comparator;

public class SnowberryBush extends SweetBerryBushBlock {
    public SnowberryBush(Properties properties) {
        super(properties);
    }

    @Override
    protected boolean mayPlaceOn(BlockState pState, BlockGetter pLevel, BlockPos pPos) {
        return pState.is(AstralTags.SNOWBERRY_PLANTABLE_ON);
    }

    @Override
    public ItemStack getCloneItemStack(BlockState state, HitResult target, LevelReader level, BlockPos pos, Player player) {
        return new ItemStack(AstralItems.SNOWBERRIES.get());
    }

    @Override
    public boolean canSurvive(BlockState pState, LevelReader pLevel, BlockPos pPos) {
        return super.canSurvive(pState, pLevel, pPos);
    }

    public void spreadSnow(ServerLevel worldIn, BlockPos pos, RandomSource rand) {
        BlockPos.betweenClosedStream(pos.offset(-1, 0, -1), pos.offset(1, 0, 1))
                .filter(blockPos -> isBlockAirOrSnow(blockPos, worldIn))
                .map(blockPos -> getPosSnowLevelPair(blockPos.immutable(), worldIn))
                .min(Comparator.comparing(Pair::getRight))
                .ifPresent(blockPosIntegerPair -> {
                    final BlockState blockState = worldIn.getBlockState(blockPosIntegerPair.getLeft());
                    if (blockState.getBlock() == Blocks.AIR) {
                        worldIn.setBlock(blockPosIntegerPair.getLeft(), Blocks.SNOW.defaultBlockState(), 2);
                    } else if (blockState.getBlock() == Blocks.SNOW) {
                        worldIn.setBlock(blockPosIntegerPair.getLeft(), blockState.setValue(SnowLayerBlock.LAYERS, blockState.getValue(SnowLayerBlock.LAYERS) + 1), 2);
                    }
                });
    }

    private boolean isBlockAirOrSnow(BlockPos blockPos, ServerLevel worldIn) {
        BlockState state = worldIn.getBlockState(blockPos);
        return Blocks.SNOW.defaultBlockState().canSurvive(worldIn, blockPos) && (state.getBlock() == Blocks.SNOW ? state.getValue(SnowLayerBlock.LAYERS) < 8 : state.getBlock() == Blocks.AIR);
    }

    private Pair<BlockPos, Integer> getPosSnowLevelPair(BlockPos pos, ServerLevel worldIn) {
        if (worldIn.getBlockState(pos).getBlock() == Blocks.AIR) {
            return Pair.of(pos, 0);
        } else if (worldIn.getBlockState(pos).getBlock() == Blocks.SNOW) {
            return Pair.of(pos, worldIn.getBlockState(pos).getValue(SnowLayerBlock.LAYERS));
        }
        return Pair.of(pos, -1);
    }

    @Override
    public void performBonemeal(ServerLevel worldIn, RandomSource rand, BlockPos pos, BlockState state) {
        super.performBonemeal(worldIn, rand, pos, state);
        spreadSnow(worldIn, pos, rand);
    }

    @Override
    public void randomTick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom) {
        // Copied from SweetBerryBushBlock.java
        int i = pState.getValue(AGE);
        if (i < 3 && pLevel.getRawBrightness(pPos.above(), 0) >= 9 && CommonHooks.onCropsGrowPre(pLevel, pPos, pState, pRandom.nextInt(5) == 0)) {
            BlockState blockstate = pState.setValue(AGE, i + 1);
            pLevel.setBlock(pPos, blockstate, 2);
            spreadSnow(pLevel, pPos, pRandom);
            pLevel.gameEvent(GameEvent.BLOCK_CHANGE, pPos, GameEvent.Context.of(blockstate));
            CommonHooks.onCropsGrowPost(pLevel, pPos, pState);
        }
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        // Copied from SweetBerryBushBlock.java
        int age = pState.getValue(AGE);
        boolean flag = age == 3;
        if (!flag && pPlayer.getItemInHand(pHand).is(Items.BONE_MEAL)) {
            return InteractionResult.PASS;
        } else if (age > 1) {
            int j = 1 + pLevel.random.nextInt(2);
            popResource(pLevel, pPos, new ItemStack(AstralItems.SNOWBERRIES.get(), j + (flag ? 1 : 0)));
            pLevel.playSound(null, pPos, SoundEvents.SWEET_BERRY_BUSH_PICK_BERRIES, SoundSource.BLOCKS, 1.0F, 0.8F + pLevel.random.nextFloat() * 0.4F);
            BlockState blockstate = pState.setValue(AGE, 1);
            pLevel.setBlock(pPos, blockstate, 2);
            pLevel.gameEvent(GameEvent.BLOCK_CHANGE, pPos, GameEvent.Context.of(pPlayer, blockstate));
            return InteractionResult.sidedSuccess(pLevel.isClientSide);
        } else {
            return super.use(pState, pLevel, pPos, pPlayer, pHand, pHit);
        }
    }
}
