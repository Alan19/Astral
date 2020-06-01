package com.alan19.astral.datagen;

import com.alan19.astral.Astral;
import com.alan19.astral.datagen.providers.*;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;

@Mod.EventBusSubscriber(modid = Astral.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenerators {

    @SubscribeEvent
    public static void gatherData(final GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        generator.addProvider(new LootTables(generator));
        final Advancements advancements = new Advancements(generator);
        generator.addProvider(advancements);
        generator.addProvider(new EnglishLocalizaton(generator, advancements));
        generator.addProvider(new Recipes(generator));
        generator.addProvider(new BlockTagsGenerator(generator));
        generator.addProvider(new ItemTagGenerator(generator));
            generator.addProvider(new Blockstates(generator, Astral.MOD_ID, event.getExistingFileHelper()));
            generator.addProvider(new ItemModels(generator, event.getExistingFileHelper()));
        generator.addProvider(new PatchouliBooks(generator, Astral.MOD_ID, "en_us"));
    }
}