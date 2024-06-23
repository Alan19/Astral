package com.minerarcana.astral.tags;

import com.minerarcana.astral.Astral;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.Tags;

public class AstralTags {
    public static final TagKey<Block> FEVERWEED_GROW_BLOCK = TagKey.create(Registries.BLOCK, new ResourceLocation(Astral.MOD_ID, "feverweed_grow_block"));
}
