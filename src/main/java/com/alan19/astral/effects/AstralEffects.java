package com.alan19.astral.effects;

import com.alan19.astral.Astral;
import com.alan19.astral.util.Constants;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.potion.Effect;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class AstralEffects {

    private static final DeferredRegister<Effect> EFFECTS = new DeferredRegister<>(ForgeRegistries.POTIONS, Astral.MOD_ID);

    public static final RegistryObject<Effect> ASTRAL_TRAVEL = EFFECTS.register("astral_travel", () -> new AstralTravelEffect().addAttributesModifier(Constants.ASTRAL_ATTACK_DAMAGE, Constants.ASTRAL_EFFECT_DAMAGE_BOOST.toString(), 0, AttributeModifier.Operation.ADDITION));

    public static void register(IEventBus modBus) {
        EFFECTS.register(modBus);
    }
}
