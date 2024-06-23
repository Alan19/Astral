package com.minerarcana.astral.data.provider;

import com.minerarcana.astral.Astral;
import com.minerarcana.astral.block.AstralBlocks;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SweetBerryBushBlock;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ConfiguredModel;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.function.Supplier;

public class AstralBlockStateProvider extends BlockStateProvider {
    private ExistingFileHelper exFileHelper;

    public AstralBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, Astral.MOD_ID, exFileHelper);
        this.exFileHelper = exFileHelper;
    }

    @Override
    protected void registerStatesAndModels() {
        simpleCross(AstralBlocks.FEVERWEED_PLANT);
    }

    private void simpleCross(Supplier<? extends Block> block) {
        simpleBlock(block.get(), new ConfiguredModel(models()
                .cross(BuiltInRegistries.BLOCK.getKey(block.get()).getPath(), modLoc("block/" + BuiltInRegistries.BLOCK.getKey(block.get()).getPath()))
                .renderType("cutout_mipped")));
        getVariantBuilder(AstralBlocks.SNOWBERRY_BUSH.get()).forAllStates(state -> ConfiguredModel.builder().modelFile(new ModelFile.ExistingModelFile(new ResourceLocation(Astral.MOD_ID, "block/snowberry_bush_" + state.getValue(SweetBerryBushBlock.AGE)), exFileHelper)).build());
    }
}
