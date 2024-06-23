package com.minerarcana.astral.block;

import com.minerarcana.astral.Astral;
import com.minerarcana.astral.worldgen.AstralFeatures;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class AstralBlocks {
    // Create a Deferred Register to hold Blocks which will all be registered under the "astral" namespace
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(Astral.MOD_ID);

    public static final Supplier<FeverweedPlant> FEVERWEED_PLANT = BLOCKS.register("feverweed_plant", () -> new FeverweedPlant(AstralFeatures.PATCH_FEVERWEED, BlockBehaviour.Properties.of().sound(SoundType.VINE).randomTicks().noCollission().destroyTime(0)));
}
