package com.alan19.astral.blocks.etherealblocks;

import com.alan19.astral.blocks.AstralBlocks;
import com.alan19.astral.effects.AstralEffects;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.PushReaction;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;

import javax.annotation.ParametersAreNonnullByDefault;

import net.minecraft.block.AbstractBlock.Properties;

public class AstralMeridian extends Block implements Ethereal {

    /**
     * Direction IntegerProperty:
     * 0 = North
     * 1 = East
     * 2 = South
     * 3 = West
     * <p>
     * Each Astral Meridian have a cardinal direction associated with it when it is right clicked with a Key of
     * Enlightenment for the direction to break blocks in
     */
    public static final IntegerProperty DIRECTION = IntegerProperty.create("direction", 0, 3);

    public AstralMeridian() {
        super(Properties.of(Material.PORTAL)
                .strength(-1.0F, 3600000.0F)
                .noDrops()
                .lightLevel(value -> 14));

        this.registerDefaultState(this.getStateDefinition().any().setValue(DIRECTION, 0));
    }

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder.add(DIRECTION));
    }

    @Override
    @ParametersAreNonnullByDefault
    public void playerWillDestroy(World worldIn, BlockPos pos, BlockState state, PlayerEntity player) {
        worldIn.setBlock(pos, AstralBlocks.EGO_MEMBRANE.get().defaultBlockState(), 2);
    }

    @Override
    @ParametersAreNonnullByDefault
    public ActionResultType use(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        if (handIn == Hand.MAIN_HAND && player.getItemInHand(handIn).isEmpty()) {
            player.removeEffect(AstralEffects.ASTRAL_TRAVEL.get());
            return ActionResultType.SUCCESS;
        }
        return super.use(state, worldIn, pos, player, handIn, hit);
    }

    @Override
    public PushReaction getPistonPushReaction(BlockState state) {
        return PushReaction.IGNORE;
    }
}
