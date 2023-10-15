package net.sirkotok.half_life_mod.event;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.sirkotok.half_life_mod.HalfLifeMod;
import net.sirkotok.half_life_mod.entity.ModEntities;
import net.sirkotok.half_life_mod.entity.mob.client.renderers.*;
import net.sirkotok.half_life_mod.entity.mob.custom.Snarknest;
import net.sirkotok.half_life_mod.particle.ModParticles;
import net.sirkotok.half_life_mod.particle.custom.projectile_impact.BigImpactParticle;
import net.sirkotok.half_life_mod.particle.custom.projectile_impact.BulletHoleParticle;
import net.sirkotok.half_life_mod.util.KeyBinding;

@Mod.EventBusSubscriber(modid = HalfLifeMod.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientModBusEvents {
    @SubscribeEvent
    public static void onKeyRegister(RegisterKeyMappingsEvent event) {
        event.register(KeyBinding.RELOAD_KEY);
    }

    @SubscribeEvent
    public static void registerParticleFactories(RegisterParticleProvidersEvent event){
        Minecraft.getInstance().particleEngine.register(ModParticles.BULLET_HOLE.get(),
                BulletHoleParticle.Provider::new);
        Minecraft.getInstance().particleEngine.register(ModParticles.SPIT_HIT.get(),
                BigImpactParticle.Provider::new);
        Minecraft.getInstance().particleEngine.register(ModParticles.SHOCK_IMPACT.get(),
                BigImpactParticle.Provider::new);
    }
    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event)
    {
        EntityRenderers.register(ModEntities.SNARKNEST.get(), Snarknestrenderer::new);
        EntityRenderers.register(ModEntities.HEADCRAB_HL1.get(), Headcrab_1renderer::new);
        EntityRenderers.register(ModEntities.HEADCRAB_ZOMBIE_1.get(), Headcrab_zombie_standart_renderer::new);
        EntityRenderers.register(ModEntities.HEADCRAB_HL2.get(), Headcrab_2renderer::new);
        EntityRenderers.register(ModEntities.HEADCRAB_POISON_HL2.get(), Headcrab_poison_2renderer::new);
        EntityRenderers.register(ModEntities.HEADCRAB_POISON_HLA.get(), Headcrab_poison_3renderer::new);
        EntityRenderers.register(ModEntities.HEADCRAB_HLA.get(), Headcrab_3renderer::new);
        EntityRenderers.register(ModEntities.HEADCRAB_ARMORED.get(), Headcrab_armoredrenderer::new);
        EntityRenderers.register(ModEntities.HEADCRAB_FAST.get(), Headcrab_fastrenderer::new);
        EntityRenderers.register(ModEntities.BULLSQUID.get(), Bullsquid_renderer::new);
        EntityRenderers.register(ModEntities.CHUMTOAD.get(), Chumtoadrenderer::new);
        EntityRenderers.register(ModEntities.SNARK.get(), Snarkrenderer::new);
        EntityRenderers.register(ModEntities.SHOCKROACH.get(), ShockRoachrenderer::new);
        EntityRenderers.register(ModEntities.PENGUIN.get(), PenguinRenderer::new);
        EntityRenderers.register(ModEntities.BARNEY.get(), Barney_renderer::new);
        EntityRenderers.register(ModEntities.ACID_BALL.get(), ThrownItemRenderer::new);
        EntityRenderers.register(ModEntities.SHOCK_SHOT.get(), ThrownItemRenderer::new);
        EntityRenderers.register(ModEntities.BULLET_ONE.get(), ThrownItemRenderer::new);
    }

}
