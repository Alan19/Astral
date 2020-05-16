package com.alan19.astral;

import com.alan19.astral.api.NBTCapStorage;
import com.alan19.astral.api.bodylink.BodyLinkCapability;
import com.alan19.astral.api.bodylink.IBodyLinkCapability;
import com.alan19.astral.api.constructtracker.ConstructTracker;
import com.alan19.astral.api.constructtracker.IConstructTracker;
import com.alan19.astral.api.heightadjustment.HeightAdjustmentCapability;
import com.alan19.astral.api.heightadjustment.IHeightAdjustmentCapability;
import com.alan19.astral.api.innerrealmchunkclaim.IInnerRealmChunkClaimCapability;
import com.alan19.astral.api.innerrealmchunkclaim.InnerRealmChunkClaimCapability;
import com.alan19.astral.api.innerrealmchunkclaim.InnerRealmChunkClaimStorage;
import com.alan19.astral.api.innerrealmteleporter.IInnerRealmTeleporterCapability;
import com.alan19.astral.api.innerrealmteleporter.InnerRealmTeleporterCapability;
import com.alan19.astral.api.innerrealmteleporter.InnerRealmTeleporterStorage;
import com.alan19.astral.api.psychicinventory.IPsychicInventory;
import com.alan19.astral.api.psychicinventory.PsychicInventory;
import com.alan19.astral.api.sleepmanager.ISleepManager;
import com.alan19.astral.api.sleepmanager.SleepManager;
import com.alan19.astral.blocks.AstralBlocks;
import com.alan19.astral.blocks.BlockRenderHandler;
import com.alan19.astral.blocks.tileentities.AstralTiles;
import com.alan19.astral.commands.AstralCommands;
import com.alan19.astral.configs.AstralConfig;
import com.alan19.astral.entities.AstralEntityRegistry;
import com.alan19.astral.entities.PhysicalBodyEntityRenderer;
import com.alan19.astral.items.AstralItems;
import com.alan19.astral.mentalconstructs.AstralMentalConstructs;
import com.alan19.astral.mentalconstructs.MentalConstructType;
import com.alan19.astral.network.AstralNetwork;
import com.alan19.astral.particle.AstralParticles;
import com.alan19.astral.particle.EtherealReplaceParticle;
import com.alan19.astral.renderer.OfferingBrazierTileEntityRenderer;
import com.alan19.astral.world.AstralFeatures;
import com.alan19.astral.world.OverworldVegetation;
import net.minecraft.client.Minecraft;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.server.FMLServerAboutToStartEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLPaths;
import net.minecraftforge.fml.network.simple.SimpleChannel;
import net.minecraftforge.registries.IForgeRegistryEntry;
import net.minecraftforge.registries.RegistryBuilder;

import static com.alan19.astral.serializing.AstralSerializers.OPTIONAL_GAME_PROFILE;
import static com.alan19.astral.serializing.AstralSerializers.OPTIONAL_ITEMSTACK_HANDLER;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(Astral.MOD_ID)
public class Astral {
    public static final String MOD_ID = "astral";
    public static final SimpleChannel INSTANCE = AstralNetwork.getNetworkChannel();

    public Astral() {
        // Register the setup method for modloading
        final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(this::setup);
        modEventBus.addListener(this::registerModels);
        modEventBus.addListener(this::setRenderLayers);
        modEventBus.addListener(this::registerParticleFactories);

        // Register and load configs
        ModLoadingContext modLoadingContext = ModLoadingContext.get();
        modLoadingContext.registerConfig(ModConfig.Type.COMMON, AstralConfig.initialize());
        AstralConfig.loadConfig(AstralConfig.getInstance().getSpec(), FMLPaths.CONFIGDIR.get().resolve("astral-common.toml"));
        MinecraftForge.EVENT_BUS.addListener(Astral::serverLoad);

        AstralEntityRegistry.register(modEventBus);
        AstralBlocks.register(modEventBus);
        AstralItems.register(modEventBus);
        AstralFeatures.register(modEventBus);
        AstralParticles.register(modEventBus);
        AstralTiles.register(modEventBus);

        modEventBus.addListener(this::newRegistry);
        DistExecutor.runWhenOn(Dist.CLIENT, () -> () -> modEventBus.addListener(ClientEventHandler::clientSetup));
    }

    public void registerModels(ModelRegistryEvent event) {
        RenderingRegistry.registerEntityRenderingHandler(AstralEntityRegistry.PHYSICAL_BODY_ENTITY.get(), PhysicalBodyEntityRenderer::new);
    }

    public void setRenderLayers(FMLClientSetupEvent event) {
        BlockRenderHandler.setRenderLayers();
        BlockRenderHandler.registerBiomeBasedBlockColors();
        ClientRegistry.bindTileEntityRenderer(AstralTiles.OFFERING_BRAZIER_TILE.get(), OfferingBrazierTileEntityRenderer::new);
    }

    public void registerParticleFactories(ParticleFactoryRegisterEvent event) {
        Minecraft.getInstance().particles.registerFactory(AstralParticles.ETHEREAL_REPLACE_PARTICLE.get(), spriteSetIn -> new EtherealReplaceParticle.Factory());
    }

    public void newRegistry(RegistryEvent.NewRegistry newRegistry) {
        makeRegistry("mental_constructs", MentalConstructType.class);
        IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();
        AstralMentalConstructs.register(modBus);
    }

    private static <T extends IForgeRegistryEntry<T>> void makeRegistry(String name, Class<T> type) {
        new RegistryBuilder<T>()
                .setName(new ResourceLocation("transport", name))
                .setType(type)
                .create();
    }


    private void setup(final FMLCommonSetupEvent event) {
        //Initializes worldgen
        OverworldVegetation.addOverworldVegetation();

        //Register Serializers
        DataSerializers.registerSerializer(OPTIONAL_GAME_PROFILE);
        DataSerializers.registerSerializer(OPTIONAL_ITEMSTACK_HANDLER);

    }

    // You can use EventBusSubscriber to automatically subscribe events on the contained class (this is subscribing to the MOD
    // Event bus for receiving Registry Events)
    @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistryEvents {

        @SubscribeEvent
        public static void init(final FMLCommonSetupEvent event) {
            //TODO Refactor Teleporter and Chunk Claim
            CapabilityManager.INSTANCE.register(IInnerRealmTeleporterCapability.class, new InnerRealmTeleporterStorage(), InnerRealmTeleporterCapability::new);
            CapabilityManager.INSTANCE.register(IInnerRealmChunkClaimCapability.class, new InnerRealmChunkClaimStorage(), InnerRealmChunkClaimCapability::new);
            CapabilityManager.INSTANCE.register(IBodyLinkCapability.class, new NBTCapStorage<>(), BodyLinkCapability::new);
            CapabilityManager.INSTANCE.register(IHeightAdjustmentCapability.class, new NBTCapStorage<>(), HeightAdjustmentCapability::new);
            CapabilityManager.INSTANCE.register(IPsychicInventory.class, new NBTCapStorage<>(), PsychicInventory::new);
            CapabilityManager.INSTANCE.register(ISleepManager.class, new NBTCapStorage<>(), SleepManager::new);
            CapabilityManager.INSTANCE.register(IConstructTracker.class, new NBTCapStorage<>(), ConstructTracker::new);
        }

    }

    @SubscribeEvent
    public static void serverLoad(FMLServerAboutToStartEvent event) {
        AstralCommands.registerCommands(event.getServer().getCommandManager().getDispatcher());
    }

}
