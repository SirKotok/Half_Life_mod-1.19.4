package net.sirkotok.half_life_mod.datagen;

import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.world.damagesource.DamageType;
import net.sirkotok.half_life_mod.misc.damagesource.HalfLifeDamageTypes;

public class ModDamageTypesBootstrap {
    protected static void bootstrap(BootstapContext<DamageType> context)
    {
                context.register(HalfLifeDamageTypes.HL_BULLET, new DamageType("hl_bullet", 0.1F));
    }
}
