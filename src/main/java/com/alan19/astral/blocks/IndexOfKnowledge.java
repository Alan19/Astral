package com.alan19.astral.blocks;

import com.alan19.astral.api.AstralAPI;
import com.alan19.astral.dimensions.AstralDimensions;
import com.alan19.astral.mentalconstructs.AstralMentalConstructs;
import com.alan19.astral.util.Constants;
import com.alan19.astral.util.ExperienceHelper;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.LecternTileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import org.apache.commons.lang3.tuple.Pair;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Random;
import java.util.stream.IntStream;

public class IndexOfKnowledge extends Block implements MentalConstructController {
    protected static final VoxelShape SHAPE = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 12.0D, 16.0D);

    public IndexOfKnowledge() {
        super(AbstractBlock.Properties.of(Material.STONE, MaterialColor.COLOR_RED).strength(1.5F));
        this.registerDefaultState(getStateDefinition().any().setValue(Constants.TRACKED_CONSTRUCT, false).setValue(Constants.LIBRARY_LEVEL, 0).setValue(Constants.CAPPED_LEVEL, false));
    }

    @Nonnull
    @Override
    @ParametersAreNonnullByDefault
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return SHAPE;
    }

    @Nonnull
    @Override
    public ActionResultType use(@Nonnull BlockState state, @Nonnull World worldIn, @Nonnull BlockPos pos, @Nonnull PlayerEntity player, @Nonnull Hand handIn, @Nonnull BlockRayTraceResult hit) {
        final int libraryLevel = state.getValue(Constants.LIBRARY_LEVEL);
        if (worldIn instanceof ServerWorld && worldIn.dimension() == AstralDimensions.INNER_REALM && handIn == Hand.MAIN_HAND) {
            int levelRequirement = (libraryLevel + 1) * 10;
            if (player.experienceLevel >= levelRequirement && calculateLevel(worldIn, pos) > libraryLevel) {
                ExperienceHelper.drainPlayerXP(player, ExperienceHelper.getExperienceForLevel(levelRequirement));
                worldIn.setBlockAndUpdate(pos, state.setValue(Constants.TRACKED_CONSTRUCT, true));
                worldIn.setBlockAndUpdate(pos, state.setValue(Constants.LIBRARY_LEVEL, libraryLevel + 1));
                AstralAPI.getConstructTracker((ServerWorld) worldIn).ifPresent(tracker -> tracker.getMentalConstructsForPlayer(player).modifyConstructInfo(pos, (ServerWorld) worldIn, AstralMentalConstructs.LIBRARY.get(), Math.min(calculateLevel(worldIn, pos), state.getValue(Constants.LIBRARY_LEVEL))));
                worldIn.playSound(player, pos, SoundEvents.ENCHANTMENT_TABLE_USE, SoundCategory.BLOCKS, 1.0F, worldIn.getRandom().nextFloat() * 0.1F + 0.9F);
                state.setValue(Constants.CAPPED_LEVEL, libraryLevel >= calculateLevel(worldIn, pos));
                final int newLevel = state.getValue(Constants.LIBRARY_LEVEL);
                IntStream.range(0, newLevel).forEach(i -> ((ServerWorld) worldIn).sendParticles(ParticleTypes.ENCHANT, pos.getX() + (double) i / newLevel, pos.getY() + .6, pos.getZ() + .5, 1, 0, 0, 0, .01));
                return ActionResultType.SUCCESS;
            }
        }
        state.setValue(Constants.CAPPED_LEVEL, libraryLevel >= calculateLevel(worldIn, pos));
        return super.use(state, worldIn, pos, player, handIn, hit);
    }

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder.add(Constants.TRACKED_CONSTRUCT).add(Constants.LIBRARY_LEVEL).add(Constants.CAPPED_LEVEL));
    }

    /**
     * Calculates the maximum level for the Index of Knowledge, which is equal to the number of bookshelves in a 3 block radius * .25, plus the number of lecterns with books in a 3 block radius * .5
     *
     * @param world The world the Index of Knowledge is in
     * @param pos   The BlockPos of the Index of Knowledge
     * @return The maximum level of the Index of Knowledge
     */
    @Override
    public int calculateLevel(World world, BlockPos pos) {
        return BlockPos.betweenClosedStream(pos.offset(-3, -3, -3), pos.offset(3, 3, 3))
                .map(blockPos -> sumStates(world, blockPos))
                .reduce((integerIntegerPair, integerIntegerPair2) -> Pair.of(integerIntegerPair.getLeft() + integerIntegerPair2.getLeft(), integerIntegerPair.getRight() + integerIntegerPair2.getRight()))
                .map(integerIntegerPair -> (int) (integerIntegerPair.getLeft() * .25 + integerIntegerPair.getRight() * .5))
                .orElse(0);
    }

    private Pair<Integer, Integer> sumStates(World world, BlockPos blockPos) {
        return Pair.of(world.getBlockState(blockPos).getBlock() == Blocks.BOOKSHELF ? 1 : 0, world.getBlockEntity(blockPos) instanceof LecternTileEntity && ((LecternTileEntity) world.getBlockEntity(blockPos)).hasBook() ? 1 : 0);
    }

    @Override
    public void onRemove(@Nonnull BlockState state, @Nonnull World worldIn, @Nonnull BlockPos pos, @Nonnull BlockState newState, boolean isMoving) {
        MentalConstructController.onReplaced(worldIn, pos, this, AstralMentalConstructs.LIBRARY.get());
        super.onRemove(state, worldIn, pos, newState, isMoving);
    }

    @Override
    public void tick(BlockState state, @Nonnull ServerWorld worldIn, @Nonnull BlockPos pos, @Nonnull Random rand) {
        final int libraryLevel = state.getValue(Constants.LIBRARY_LEVEL);
        state.setValue(Constants.CAPPED_LEVEL, libraryLevel >= calculateLevel(worldIn, pos));
        MentalConstructController.tick(state, worldIn, pos, Math.min(calculateLevel(worldIn, pos), libraryLevel), AstralMentalConstructs.LIBRARY.get());
        super.tick(state, worldIn, pos, rand);
    }

    @Override
    public boolean hasAnalogOutputSignal(@Nonnull BlockState state) {
        return true;
    }

    @Override
    public int getAnalogOutputSignal(BlockState blockState, @Nonnull World worldIn, @Nonnull BlockPos pos) {
        return Math.min(calculateLevel(worldIn, pos), blockState.getValue(Constants.LIBRARY_LEVEL));
    }
}
