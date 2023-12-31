package net.sirkotok.half_life_mod.particle;

import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.sirkotok.half_life_mod.HalfLifeMod;

public class HalfLifeParticles {
    public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES =
            DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, HalfLifeMod.MOD_ID);



    public static final RegistryObject<SimpleParticleType> BULLET_HOLE =
            PARTICLE_TYPES.register("bullet_hole", () -> new SimpleParticleType(true));
    public static final RegistryObject<SimpleParticleType> SPIT_HIT =
            PARTICLE_TYPES.register("spit_hit", () -> new SimpleParticleType(true));

    public static final RegistryObject<SimpleParticleType> SHOCK_IMPACT =
            PARTICLE_TYPES.register("shock_impact", () -> new SimpleParticleType(true));

    public static void register(IEventBus eventBus){
        PARTICLE_TYPES.register(eventBus);
    }
}
