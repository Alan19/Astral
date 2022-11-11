package com.minerarcana.astral.world.feature;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public record SnowberryPatchConfig(
        int minCount,
        int maxCount,
        int xzSpread,
        int ySpread,
        int tries
) implements FeatureConfiguration {
    public static final Codec<SnowberryPatchConfig> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            ExtraCodecs.POSITIVE_INT.fieldOf("tries").orElse(128).forGetter(SnowberryPatchConfig::tries),
            ExtraCodecs.NON_NEGATIVE_INT.fieldOf("xz_spread").orElse(7).forGetter(SnowberryPatchConfig::xzSpread),
            ExtraCodecs.NON_NEGATIVE_INT.fieldOf("y_spread").orElse(3).forGetter(SnowberryPatchConfig::ySpread),
            ExtraCodecs.NON_NEGATIVE_INT.fieldOf("min_count").orElse(2).forGetter(SnowberryPatchConfig::minCount),
            ExtraCodecs.POSITIVE_INT.fieldOf("max_count").orElse(5).forGetter(SnowberryPatchConfig::maxCount)
    ).apply(instance, SnowberryPatchConfig::new));

}
