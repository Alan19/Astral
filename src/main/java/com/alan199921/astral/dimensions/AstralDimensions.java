package com.alan199921.astral.dimensions;

import com.alan199921.astral.Astral;
import com.alan199921.astral.dimensions.innerrealm.InnerRealmBiomeProvider;
import com.alan199921.astral.dimensions.innerrealm.InnerRealmChunkGenerator;
import com.alan199921.astral.dimensions.innerrealm.InnerRealmDimension;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.biome.provider.BiomeProviderType;
import net.minecraft.world.biome.provider.SingleBiomeProviderSettings;
import net.minecraft.world.dimension.Dimension;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.gen.ChunkGeneratorType;
import net.minecraft.world.gen.GenerationSettings;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.ModDimension;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.BiFunction;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class AstralDimensions {
    public static final ResourceLocation INNER_REALM = new ResourceLocation(Astral.MOD_ID + ":" + "inner_realm");
    public static ModDimension innerRealm = new ModDimension() {
        @Override
        public BiFunction<World, DimensionType, ? extends Dimension> getFactory() {
            return InnerRealmDimension::new;
        }
    }.setRegistryName("astral:inner_realm");

    public static ChunkGeneratorType<GenerationSettings, InnerRealmChunkGenerator> generatorType = new ChunkGeneratorType<>(InnerRealmChunkGenerator::new, false, GenerationSettings::new);

    public static BiomeProviderType<SingleBiomeProviderSettings, InnerRealmBiomeProvider> biomeProviderType = new BiomeProviderType<>(InnerRealmBiomeProvider::new, SingleBiomeProviderSettings::new);

    @SubscribeEvent
    public static void onDimensionModRegistry(final RegistryEvent.Register<ModDimension> event) {
        event.getRegistry().register(AstralDimensions.innerRealm);
        DimensionManager.registerDimension(new ResourceLocation(Astral.MOD_ID, "inner_realm"), AstralDimensions.innerRealm, null, true);
    }

}