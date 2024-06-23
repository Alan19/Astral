package com.minerarcana.astral.block;

import com.minerarcana.astral.Astral;
import com.minerarcana.astral.worldgen.AstralFeatures;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class AstralBlocks {
    // Create a Deferred Register to hold Blocks which will all be registered under the "astral" namespace
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(Astral.MOD_ID);

    public static final Supplier<SnowberryBush> SNOWBERRY_BUSH = BLOCKS.register("snowberry_bush", () -> new SnowberryBush(BlockBehaviour.Properties.of()
            .mapColor(MapColor.PLANT)
            .randomTicks()
            .noCollission()
            .sound(SoundType.SWEET_BERRY_BUSH)
            .strength(0.2f)
            .pushReaction(PushReaction.DESTROY)));
    public static final Supplier<FeverweedPlant> FEVERWEED_PLANT = BLOCKS.register("feverweed", () -> new FeverweedPlant(AstralFeatures.PATCH_FEVERWEED, BlockBehaviour.Properties.of()
            .sound(SoundType.VINE)
            .mapColor(MapColor.PLANT)
            .randomTicks()
            .noCollission()
            .destroyTime(0)));
}
