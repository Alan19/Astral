package com.minerarcana.astral.data.provider;

import com.minerarcana.astral.Astral;
import com.minerarcana.astral.block.AstralBlocks;
import com.minerarcana.astral.item.AstralItems;
import com.minerarcana.astral.item.alchemy.AstralPotions;
import com.minerarcana.astral.item.alchemy.PotionRegistryGroup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.alchemy.Potion;
import net.neoforged.neoforge.common.data.LanguageProvider;

import java.text.MessageFormat;

public class AstralEnglishLocalization extends LanguageProvider {
    public AstralEnglishLocalization(PackOutput output, String locale) {
        super(output, Astral.MOD_ID, locale);
    }

    @Override
    protected void addTranslations() {
        add(AstralBlocks.FEVERWEED_PLANT.get(), "Feverweed");
        add(AstralItems.SNOWBERRIES.get(), "Snowberries");
        add(AstralItems.ASTRAL_TAB.get(), "Astral");

        processPotionFamily("Feverweed Brew", "Splashing Feverweed Brew", "Feverweed Mist", "Arrow of Feverweed", AstralPotions.FEVERWEED_BREW);
        processPotionFamily("Snowberry Brew", "Splashing Snowberry Brew", "Snowberry Mist", "Arrow of Snowberry", AstralPotions.SNOWBERRY_BREW);
//        processPotionFamily("Potion of Astral Travel", "Splash Potion of Astral Travel", "Lingering Potion of Astral Travel", "Arrow of Astral Travel", ASTRAL_TRAVEL_POTION);
//        processPotionFamily("Potion of Mind Venom", "Splash Potion of Mind Venom", "Lingering Potion of Mind Venom", "Arrow of Mind Venom", MIND_VENOM_POTION);
    }

    public void add(CreativeModeTab tab, String name) {
        add(MessageFormat.format("itemGroup.{0}", BuiltInRegistries.CREATIVE_MODE_TAB.getKey(tab).getPath()), name);
    }

    private void processPotionFamily(String potionName, String splashPotionName, String lingeringPotionName, String arrowName, PotionRegistryGroup potionRegistryGroup) {
        add(potionRegistryGroup.getBasePotion().get(), potionName, splashPotionName, lingeringPotionName, arrowName);
        potionRegistryGroup.getLongPotion().ifPresent(potion -> add(potion, potionName, splashPotionName, lingeringPotionName, arrowName));
        potionRegistryGroup.getStrongPotion().ifPresent(potion -> add(potion, potionName, splashPotionName, lingeringPotionName, arrowName));
    }

    private void add(Potion potion, String potionName, String splashPotionName, String lingeringPotionName, String arrowName) {
        String base = "item.minecraft";
        String potionRegistryName = BuiltInRegistries.POTION.getKey(potion).getPath();
        add(base + ".potion.effect." + potionRegistryName, potionName);
        add(base + ".splash_potion.effect." + potionRegistryName, splashPotionName);
        add(base + ".lingering_potion.effect." + potionRegistryName, lingeringPotionName);
        add(base + ".tipped_arrow.effect." + potionRegistryName, arrowName);
    }
}
