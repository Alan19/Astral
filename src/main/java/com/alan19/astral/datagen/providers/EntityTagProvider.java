package com.alan19.astral.datagen.providers;

import com.alan19.astral.entities.AstralEntities;
import com.alan19.astral.tags.AstralTags;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.EntityTypeTagsProvider;
import net.minecraft.entity.EntityType;
import net.minecraftforge.fml.RegistryObject;

public class EntityTagProvider extends EntityTypeTagsProvider {
    public EntityTagProvider(DataGenerator generator) {
        super(generator);
    }

    @Override
    protected void registerTags() {
        AstralEntities.ENTITIES.getEntries().forEach(this::addAstralBeing);
        getBuilder(AstralTags.SPIRITUAL_BEINGS).add(EntityType.PHANTOM);
    }

    private void addAstralBeing(RegistryObject<EntityType<?>> entityTypeRegistryObject) {
        getBuilder(AstralTags.ETHEREAL_BEINGS).add(entityTypeRegistryObject.get());
    }
}
