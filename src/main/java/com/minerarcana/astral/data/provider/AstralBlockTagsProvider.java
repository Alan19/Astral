package com.minerarcana.astral.data.provider;

import com.minerarcana.astral.Astral;
import com.minerarcana.astral.tags.AstralTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.concurrent.CompletableFuture;

public class AstralBlockTagsProvider extends BlockTagsProvider {

    public AstralBlockTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, Astral.MOD_ID, existingFileHelper);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        tag(AstralTags.FEVERWEED_GROW_BLOCK).addTags(BlockTags.DIRT, BlockTags.LEAVES);
        tag(AstralTags.SNOWBERRY_PLANTABLE_ON).addTags(BlockTags.SNOW, Tags.Blocks.GRAVEL, BlockTags.ICE, BlockTags.DIRT);
    }
}
