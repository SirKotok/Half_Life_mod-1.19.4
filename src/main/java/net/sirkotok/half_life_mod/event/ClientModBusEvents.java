package net.sirkotok.half_life_mod.event;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.sirkotok.half_life_mod.HalfLifeMod;
import net.sirkotok.half_life_mod.block.blockentity.ModBlockEntities;
import net.sirkotok.half_life_mod.block.blockentity.client.VoltigoreEggRenderer;
import net.sirkotok.half_life_mod.entity.ModEntities;
import net.sirkotok.half_life_mod.entity.mob_effect_entity.client.renderer.Shockwaverenderer;
import net.sirkotok.half_life_mod.entity.mob_effect_entity.client.renderer.Voltigore_projectile_e_renderer;
import net.sirkotok.half_life_mod.entity.mob_geckolib.client.renderers.*;
import net.sirkotok.half_life_mod.entity.mob_normal.client.ModModelLayers;
import net.sirkotok.half_life_mod.entity.mob_normal.client.models.Barnacle_Model;
import net.sirkotok.half_life_mod.entity.mob_normal.client.renderers.Barnacle_Renderer;
import net.sirkotok.half_life_mod.entity.projectile.SporeShot;
import net.sirkotok.half_life_mod.entity.projectile.client.renderer.Pitdrone_spike_renderer;
import net.sirkotok.half_life_mod.entity.projectile.client.renderer.Spore_renderer;
import net.sirkotok.half_life_mod.entity.projectile.client.renderer.Voltigore_projectile_renderer;
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
        EntityRenderers.register(ModEntities.VOLTIGOREPROJECTEFFECT.get(), Voltigore_projectile_e_renderer::new);
        EntityRenderers.register(ModEntities.SHOCKWAVEEFFECT.get(), Shockwaverenderer::new);
        EntityRenderers.register(ModEntities.SPORESHOT.get(), Spore_renderer::new);
        EntityRenderers.register(ModEntities.HOTEYE.get(), Hoteyerenderer::new);
        EntityRenderers.register(ModEntities.PITDRONE.get(), Pitdrone_renderer::new);
        EntityRenderers.register(ModEntities.HOUNDEYE.get(), Houndeyerenderer::new);
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
        EntityRenderers.register(ModEntities.GONARCH.get(), Gonarch_renderer::new);
        EntityRenderers.register(ModEntities.CHUMTOAD.get(), Chumtoadrenderer::new);
        EntityRenderers.register(ModEntities.SNARK.get(), Snarkrenderer::new);
        EntityRenderers.register(ModEntities.SHOCKROACH.get(), ShockRoachrenderer::new);
        EntityRenderers.register(ModEntities.PENGUIN.get(), PenguinRenderer::new);
        EntityRenderers.register(ModEntities.VOLTIGORE.get(), VortigoreRenderer::new);
        EntityRenderers.register(ModEntities.SHOCKTROOPER.get(), ShocktrooperRenderer::new);
        EntityRenderers.register(ModEntities.BARNEY.get(), Barney_renderer::new);
        EntityRenderers.register(ModEntities.COCKROACH.get(), CockroachRenderer::new);
        EntityRenderers.register(ModEntities.BABY_HEADCRAB.get(), BabyHeadcrab_renderer::new);
        EntityRenderers.register(ModEntities.MANHACK.get(), ManhackRenderer::new);
        EntityRenderers.register(ModEntities.LEECH.get(), Leechrenderer::new);
        EntityRenderers.register(ModEntities.SHARK.get(), SharkRenderer::new);
        EntityRenderers.register(ModEntities.ACID_BALL.get(), ThrownItemRenderer::new);
        EntityRenderers.register(ModEntities.VOLTIGORE_SHOCK.get(), Voltigore_projectile_renderer::new);
        EntityRenderers.register(ModEntities.PITDRONE_SPIKE.get(), Pitdrone_spike_renderer::new);
        EntityRenderers.register(ModEntities.SHOCK_SHOT.get(), ThrownItemRenderer::new);
        EntityRenderers.register(ModEntities.BULLET_ONE.get(), ThrownItemRenderer::new);
        EntityRenderers.register(ModEntities.ACID_THROWN.get(), ThrownItemRenderer::new);
        EntityRenderers.register(ModEntities.BARNACLE.get(), Barnacle_Renderer::new);

        BlockEntityRenderers.register(ModBlockEntities.VOLTIGORE_EGG_BLOCK_ENTITY.get(), VoltigoreEggRenderer::new);
    }


    @SubscribeEvent
    public static void registerLayer(EntityRenderersEvent.RegisterLayerDefinitions event){
        event.registerLayerDefinition(ModModelLayers.BARNACLE_LAYER, Barnacle_Model::createBodyLayer);
    }


}
