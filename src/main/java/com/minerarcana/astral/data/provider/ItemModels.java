package com.minerarcana.astral.data.provider;

import com.minerarcana.astral.Astral;
import com.minerarcana.astral.item.AstralItems;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.function.Supplier;

public class ItemModels extends ItemModelProvider {
    private final ResourceLocation generatedItem = mcLoc("item/generated");


    public ItemModels(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, Astral.MOD_ID, existingFileHelper);
    }


    @Override
    protected void registerModels() {
        forItem(AstralItems.SNOWBERRIES);
        handheldItemFromBlock(AstralItems.FEVERWEED);
    }

    private void handheldItemFromBlock(Supplier<? extends BlockItem> item) {
        singleTexture(BuiltInRegistries.ITEM.getKey(item.get()).getPath(), mcLoc("item/handheld"), "layer0", modLoc("block/" + BuiltInRegistries.ITEM.getKey(item.get()).getPath()));
    }

    private void forItem(Supplier<? extends Item> item) {
        this.singleTexture(BuiltInRegistries.ITEM.getKey(item.get()).getPath(), mcLoc("item/handheld"), "layer0", modLoc("item/" + BuiltInRegistries.ITEM.getKey(item.get()).getPath()));
    }

    private void forBlockItem(Supplier<? extends BlockItem> item) {
        getBuilder(BuiltInRegistries.ITEM.getKey(item.get()).getPath()).parent(new ModelFile.UncheckedModelFile(new ResourceLocation(Astral.MOD_ID, "block/" + BuiltInRegistries.BLOCK.getKey(item.get().getBlock()).getPath())));
    }

    private void forBlockItem(Supplier<? extends BlockItem> item, ResourceLocation modelLocation) {
        getBuilder(BuiltInRegistries.ITEM.getKey(item.get()).getPath()).parent(new ModelFile.UncheckedModelFile(modelLocation));
    }

    private void forBlockItemWithParent(Supplier<? extends BlockItem> item, ResourceLocation modelLocation) {
        singleTexture(BuiltInRegistries.ITEM.getKey(item.get()).getPath(), generatedItem, "layer0", modelLocation);
    }

    private void forBlockItemWithParent(Supplier<? extends BlockItem> item) {
        singleTexture(BuiltInRegistries.ITEM.getKey(item.get()).getPath(), generatedItem, "layer0", modLoc("block/" + BuiltInRegistries.ITEM.getKey(item.get()).getPath()));
    }

    @Override
    public String getName() {
        return "Astral item models";
    }
}
