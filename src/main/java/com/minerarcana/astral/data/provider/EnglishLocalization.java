package com.minerarcana.astral.data.provider;

import com.minerarcana.astral.Astral;
import com.minerarcana.astral.block.AstralBlocks;
import com.minerarcana.astral.item.AstralItems;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;

public class EnglishLocalization extends LanguageProvider {
    public EnglishLocalization(PackOutput output, String locale) {
        super(output, Astral.MOD_ID, locale);
    }

    @Override
    protected void addTranslations() {
        add(AstralBlocks.FEVERWEED_PLANT.get(), "Feverweed");
        add(AstralItems.SNOWBERRIES.get(), "Snowberries");
    }
}
