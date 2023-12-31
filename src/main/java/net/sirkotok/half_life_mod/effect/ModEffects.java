package net.sirkotok.half_life_mod.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.sirkotok.half_life_mod.HalfLifeMod;

public class ModEffects  {
    public static final DeferredRegister<MobEffect> MOB_EFFECTS
            = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, HalfLifeMod.MOD_ID);


    public static final RegistryObject<MobEffect> SET_HEALTH_TO_ONE =
            MOB_EFFECTS.register("set_health_to_one", () -> new SetHealthTo1(MobEffectCategory.HARMFUL, 3124687));



    public static void register(IEventBus eventBus) {
        MOB_EFFECTS.register(eventBus);
    }
}
