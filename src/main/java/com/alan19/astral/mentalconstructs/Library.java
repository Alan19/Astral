package com.alan19.astral.mentalconstructs;

import net.minecraft.world.entity.player.Player;

public class Library extends MentalConstruct {
    @Override
    public void performEffect(Player player, int level) {
        if (player.experienceLevel < level) {
            player.giveExperienceLevels(1);
            player.experienceProgress = 0;
        }
    }

    @Override
    public EffectType getEffectType() {
        return EffectType.CONDITIONAL;
    }

    @Override
    public MentalConstructType getType() {
        return AstralMentalConstructs.LIBRARY.get();
    }


}
