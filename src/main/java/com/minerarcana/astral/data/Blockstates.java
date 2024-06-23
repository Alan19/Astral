package com.minerarcana.astral.data;

import com.minerarcana.astral.Astral;
import com.minerarcana.astral.block.AstralBlocks;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ConfiguredModel;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.function.Supplier;

public class Blockstates extends BlockStateProvider {
    public Blockstates(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, Astral.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        simpleCross(AstralBlocks.FEVERWEED_PLANT);
    }

    private void simpleCross(Supplier<? extends Block> block) {
        simpleBlock(block.get(), new ConfiguredModel(models()
                .cross(BuiltInRegistries.BLOCK.getKey(block.get()).getPath(), modLoc("block/" + BuiltInRegistries.BLOCK.getKey(block.get()).getPath()))
                .renderType("cutout_mipped")));
    }
}
