package com.minerarcana.astral.data;

import com.minerarcana.astral.Astral;
import net.minecraft.data.DataGenerator;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.data.event.GatherDataEvent;

@Mod.EventBusSubscriber(modid = Astral.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGen {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        Astral.LOGGER.info("Data generator started!");
        DataGenerator generator = event.getGenerator();

        generator.addProvider(true, new ItemModels(generator.getPackOutput(), event.getExistingFileHelper()));
    }
}
