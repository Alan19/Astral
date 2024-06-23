package com.minerarcana.astral.item.alchemy;

import com.google.common.collect.ImmutableList;
import net.minecraft.world.effect.MobEffectInstance;

import java.util.List;

import static net.minecraft.world.effect.MobEffects.*;

public class PotionEffectInstances {
    public static final int SNOWBERRY_BREW_NAUSEA_DURATION = 600;
    public static final int SNOWBERRY_BREW_REGENERATION_DURATION = 600;
    public static final int FEVERWEED_BREW_LUCK_DURATION = 600;
    public static final int FEVERWEED_BREW_HUNGER_DURATION = 600;

    //Potions
    public static final List<MobEffectInstance> STRONG_SNOWBERRY_INSTANCE = ImmutableList.of(new MobEffectInstance(REGENERATION, SNOWBERRY_BREW_REGENERATION_DURATION * 2 / 3, 2), new MobEffectInstance(CONFUSION, SNOWBERRY_BREW_NAUSEA_DURATION * 2 / 3, 2));
    public static final List<MobEffectInstance> LONG_SNOWBERRY_INSTANCE = ImmutableList.of(new MobEffectInstance(REGENERATION, SNOWBERRY_BREW_REGENERATION_DURATION * 2, 1), new MobEffectInstance(CONFUSION, SNOWBERRY_BREW_NAUSEA_DURATION * 2, 1));
    public static final List<MobEffectInstance> SNOWBERRY_BASE_INSTANCE = ImmutableList.of(new MobEffectInstance(REGENERATION, SNOWBERRY_BREW_REGENERATION_DURATION, 1), new MobEffectInstance(CONFUSION, SNOWBERRY_BREW_NAUSEA_DURATION, 1));
    public static final List<MobEffectInstance> STRONG_FEVERWEED_INSTANCE = ImmutableList.of(new MobEffectInstance(LUCK, FEVERWEED_BREW_LUCK_DURATION * 2 / 3, 2), new MobEffectInstance(HUNGER, FEVERWEED_BREW_HUNGER_DURATION * 2 / 3, 2));
    public static final List<MobEffectInstance> BASE_FEVERWEED_INSTANCE = ImmutableList.of(new MobEffectInstance(LUCK, FEVERWEED_BREW_LUCK_DURATION, 1), new MobEffectInstance(HUNGER, FEVERWEED_BREW_HUNGER_DURATION, 1));
    public static final List<MobEffectInstance> LONG_FEVERWEED_INSTANCE = ImmutableList.of(new MobEffectInstance(LUCK, FEVERWEED_BREW_LUCK_DURATION * 2, 1), new MobEffectInstance(HUNGER, FEVERWEED_BREW_HUNGER_DURATION * 2, 1));

    //Botanical Brewery
    public static final List<MobEffectInstance> SNOWBERRY_BOTANICAL_BREW = ImmutableList.of(new MobEffectInstance(REGENERATION, SNOWBERRY_BREW_REGENERATION_DURATION * 2, 2), new MobEffectInstance(CONFUSION, SNOWBERRY_BREW_NAUSEA_DURATION * 2, 2));
    public static final List<MobEffectInstance> FEVERWEED_BOTANICAL_BREW = ImmutableList.of(new MobEffectInstance(LUCK, FEVERWEED_BREW_LUCK_DURATION * 2, 2), new MobEffectInstance(HUNGER, FEVERWEED_BREW_HUNGER_DURATION * 2, 2));
}
