package com.alan19.astral.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class EgoMembrane extends Block {
    public EgoMembrane() {
        super(Properties.create(Material.DRAGON_EGG)
                .hardnessAndResistance(2f)
                .lightValue(14));
    }

    @Override
    public void onBlockHarvested(World worldIn, BlockPos pos, BlockState state, PlayerEntity player) {
        worldIn.setBlockState(pos, AstralBlocks.EGO_MEMBRANE.get().getDefaultState(), 2);

    }
}
