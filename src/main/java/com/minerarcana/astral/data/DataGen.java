package com.minerarcana.astral.data;

import com.minerarcana.astral.Astral;
import com.minerarcana.astral.data.provider.*;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.concurrent.CompletableFuture;

@Mod.EventBusSubscriber(modid = Astral.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGen {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        Astral.LOGGER.info("Data generator started!");

        DataGenerator generator = event.getGenerator();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();

        PackOutput packOutput = generator.getPackOutput();
        generator.addProvider(true, new ItemModels(packOutput, existingFileHelper));
        generator.addProvider(true, new AstralBlockStateProvider(packOutput, existingFileHelper));
        generator.addProvider(true, new EnglishLocalization(packOutput, "en_us"));
        generator.addProvider(true, new AstralDatapackRegistryProvider(packOutput, lookupProvider));
        generator.addProvider(true, new AstralBlockTagsProvider(packOutput, lookupProvider, existingFileHelper));
    }
}
