package net.sirkotok.half_life_mod.damagesource;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.sirkotok.half_life_mod.HalfLifeMod;

public class ModDamageTypes {
    public static final DeferredRegister<DamageType> DAMAGE_TYPES =
            DeferredRegister.create(Registries.DAMAGE_TYPE, HalfLifeMod.MOD_ID);



    public static final ResourceKey<DamageType> HL_BULLET = register("hl_bullet");




    private static ResourceKey<DamageType> register(String name)
    {
        return ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation(HalfLifeMod.MOD_ID, name));
    }

    public static void register(IEventBus eventBus) {
        DAMAGE_TYPES.register(eventBus);
    }



}
