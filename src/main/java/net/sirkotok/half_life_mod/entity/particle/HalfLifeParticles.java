package net.sirkotok.half_life_mod.entity.particle;

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

    public static final RegistryObject<SimpleParticleType> VORT_LIGHTNING =
            PARTICLE_TYPES.register("vort_lightning", () -> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> BEETRAIL =
            PARTICLE_TYPES.register("beetrail", () -> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> ORANGEGLOW =
            PARTICLE_TYPES.register("orangeglow", () -> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> VORT_ARC_LIGHTNING =
            PARTICLE_TYPES.register("vort_arc_lightning", () -> new SimpleParticleType(false));

    public static final RegistryObject<SimpleParticleType> VORT2_ARC_LIGHTNING =
            PARTICLE_TYPES.register("vort2_arc_lightning", () -> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> GRAV_GUN_ORANGE_LIGHTNING =
            PARTICLE_TYPES.register("gravgun_orange_lightning", () -> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> GRAV_GUN_BLUE_LIGHTNING =
            PARTICLE_TYPES.register("gravgun_blue_lightning", () -> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> VOLT_LIGHTNING =
            PARTICLE_TYPES.register("volt_lightning", () -> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> YELLOW_SMOKE =
            PARTICLE_TYPES.register("yellowsmokeparticle", () -> new SimpleParticleType(true));
    public static final RegistryObject<SimpleParticleType> BULLET_HOLE =
            PARTICLE_TYPES.register("bullet_hole", () -> new SimpleParticleType(true));
    public static final RegistryObject<SimpleParticleType> SPIT_HIT =
            PARTICLE_TYPES.register("spit_hit", () -> new SimpleParticleType(true));

    public static final RegistryObject<SimpleParticleType> SHOCK_IMPACT =
            PARTICLE_TYPES.register("shock_impact", () -> new SimpleParticleType(true));

    public static final RegistryObject<SimpleParticleType> STAT_GLOW = PARTICLE_TYPES.register("stationary_glow", () -> new SimpleParticleType(true));
    public static final RegistryObject<SimpleParticleType> BEE_GLOW = PARTICLE_TYPES.register("bee_glow", () -> new SimpleParticleType(true));




    public static void register(IEventBus eventBus){
        PARTICLE_TYPES.register(eventBus);
    }
}
