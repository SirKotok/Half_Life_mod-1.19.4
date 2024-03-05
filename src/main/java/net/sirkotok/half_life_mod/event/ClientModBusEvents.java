package net.sirkotok.half_life_mod.event;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterDimensionSpecialEffectsEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.sirkotok.half_life_mod.HalfLifeMod;
import net.sirkotok.half_life_mod.block.blockentity.HalfLifeBlockEntities;
import net.sirkotok.half_life_mod.block.blockentity.client.VoltigoreEggRenderer;
import net.sirkotok.half_life_mod.entity.HalfLifeEntities;
import net.sirkotok.half_life_mod.entity.mob_effect_entity.client.renderer.Shockwaverenderer;
import net.sirkotok.half_life_mod.entity.mob_effect_entity.client.renderer.SitRenderer;
import net.sirkotok.half_life_mod.entity.mob_effect_entity.client.renderer.Voltigore_projectile_e_renderer;
import net.sirkotok.half_life_mod.entity.mob_effect_entity.client.renderer.VortShockwaverenderer;
import net.sirkotok.half_life_mod.entity.mob_geckolib.client.models.Zombiehl2_model_fast;
import net.sirkotok.half_life_mod.entity.mob_geckolib.client.renderers.*;
import net.sirkotok.half_life_mod.entity.mob_geckolib.custom.HLZombieVillager;
import net.sirkotok.half_life_mod.entity.mob_normal.client.ModModelLayers;
import net.sirkotok.half_life_mod.entity.mob_normal.client.models.Barnacle_Model;
import net.sirkotok.half_life_mod.entity.mob_normal.client.renderers.Barnacle_Renderer;
import net.sirkotok.half_life_mod.entity.projectile.client.renderer.*;
import net.sirkotok.half_life_mod.particle.HalfLifeParticles;
import net.sirkotok.half_life_mod.particle.custom.projectile_impact.BigImpactParticle;
import net.sirkotok.half_life_mod.particle.custom.projectile_impact.BulletHoleParticle;
import net.sirkotok.half_life_mod.particle.lightning.*;
import net.sirkotok.half_life_mod.util.KeyBinding;
import net.sirkotok.half_life_mod.worldgen.dimension.specialeffects.XenSpecialEffects;

@Mod.EventBusSubscriber(modid = HalfLifeMod.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientModBusEvents {



    @SubscribeEvent
    public static void onKeyRegister(RegisterKeyMappingsEvent event) {
        event.register(KeyBinding.RELOAD_KEY);
    }

    @SubscribeEvent
    public static void registerParticleFactories(RegisterParticleProvidersEvent event){
        Minecraft.getInstance().particleEngine.register(HalfLifeParticles.BULLET_HOLE.get(),
                BulletHoleParticle.Provider::new);
        Minecraft.getInstance().particleEngine.register(HalfLifeParticles.SPIT_HIT.get(),
                BigImpactParticle.Provider::new);
        Minecraft.getInstance().particleEngine.register(HalfLifeParticles.SHOCK_IMPACT.get(),
                BigImpactParticle.Provider::new);
        event.registerSpecial(HalfLifeParticles.VORT_LIGHTNING.get(), new VortLightningParticle.Factory());
        event.registerSpecial(HalfLifeParticles.VOLT_LIGHTNING.get(), new VoltigoreLightningParticle.Factory());
        event.registerSpecial(HalfLifeParticles.VORT_ARC_LIGHTNING.get(), new VortArcLightningParticle.Factory());
        event.registerSpecial(HalfLifeParticles.VORT2_ARC_LIGHTNING.get(), new Vort2ArcLightningParticle.Factory());


        event.registerSpecial(HalfLifeParticles.ORANGEGLOW.get(), new OrangeGlowParticle.Factory());
    }
    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event)
    {
        EntityRenderers.register(HalfLifeEntities.VOLTIGOREPROJECTEFFECT.get(), Voltigore_projectile_e_renderer::new);
        EntityRenderers.register(HalfLifeEntities.SHOCKWAVEEFFECT.get(), Shockwaverenderer::new);
        EntityRenderers.register(HalfLifeEntities.VORTSHOCKWAVEEFFECT.get(), VortShockwaverenderer::new);
        EntityRenderers.register(HalfLifeEntities.SITBLOWUP.get(), SitRenderer::new);
        EntityRenderers.register(HalfLifeEntities.SPORESHOT.get(), Spore_renderer::new);
        EntityRenderers.register(HalfLifeEntities.HOTEYE.get(), Hoteyerenderer::new);
        EntityRenderers.register(HalfLifeEntities.CONTROLLER.get(), ControllerRenderer::new);
        EntityRenderers.register(HalfLifeEntities.PITDRONE.get(), Pitdrone_renderer::new);
        EntityRenderers.register(HalfLifeEntities.HOUNDEYE.get(), Houndeyerenderer::new);
        EntityRenderers.register(HalfLifeEntities.SNARKNEST.get(), Snarknestrenderer::new);
        EntityRenderers.register(HalfLifeEntities.HEADCRAB_HL1.get(), Headcrab_1renderer::new);
        EntityRenderers.register(HalfLifeEntities.ANTLION.get(), Antlion_renderer::new);
        EntityRenderers.register(HalfLifeEntities.ANTLIONWORKER.get(), AntlionWorker_renderer::new);
        EntityRenderers.register(HalfLifeEntities.HEADCRAB_HL2.get(), Headcrab_2renderer::new);
        EntityRenderers.register(HalfLifeEntities.HEADCRAB_POISON_HL2.get(), Headcrab_poison_2renderer::new);
        EntityRenderers.register(HalfLifeEntities.HEADCRAB_POISON_HLA.get(), Headcrab_poison_3renderer::new);
        EntityRenderers.register(HalfLifeEntities.HEADCRAB_HLA.get(), Headcrab_3renderer::new);
        EntityRenderers.register(HalfLifeEntities.HEADCRAB_ARMORED.get(), Headcrab_armoredrenderer::new);
        EntityRenderers.register(HalfLifeEntities.HEADCRAB_FAST.get(), Headcrab_fastrenderer::new);
        EntityRenderers.register(HalfLifeEntities.BULLSQUID.get(), Bullsquid_renderer::new);
        EntityRenderers.register(HalfLifeEntities.GONARCH.get(), Gonarch_renderer::new);
        EntityRenderers.register(HalfLifeEntities.CHUMTOAD.get(), Chumtoadrenderer::new);
        EntityRenderers.register(HalfLifeEntities.GONARCHBM.get(), GonarchBM_renderer::new);
        EntityRenderers.register(HalfLifeEntities.SNARK.get(), Snarkrenderer::new);
        EntityRenderers.register(HalfLifeEntities.SHOCKROACH.get(), ShockRoachrenderer::new);
        EntityRenderers.register(HalfLifeEntities.PENGUIN.get(), PenguinRenderer::new);
        EntityRenderers.register(HalfLifeEntities.VOLTIGORE.get(), VortigoreRenderer::new);
        EntityRenderers.register(HalfLifeEntities.SHOCKTROOPER.get(), ShocktrooperRenderer::new);
        EntityRenderers.register(HalfLifeEntities.BARNEY.get(), Barney_renderer::new);
        EntityRenderers.register(HalfLifeEntities.SCIENTIST.get(), Scientist_renderer::new);
        EntityRenderers.register(HalfLifeEntities.ZOMBIE_SCIENTIST_HL1.get(), Scientist_zombiehl1_renderer::new);
        EntityRenderers.register(HalfLifeEntities.ZOMBIE_HL2.get(), Zombiehl2_renderer::new);
        EntityRenderers.register(HalfLifeEntities.ZOMBIE_FAST.get(), Fast_zombieRenderer::new);
        EntityRenderers.register(HalfLifeEntities.PZOMBIE.get(), pzombieRenderer::new);
        EntityRenderers.register(HalfLifeEntities.VORTHL1.get(), VortHL1_renderer::new);
        EntityRenderers.register(HalfLifeEntities.VORTHL2.get(), VortHL2_renderer::new);
        EntityRenderers.register(HalfLifeEntities.VZOMBIE.get(), Zombiev_renderer::new);
        EntityRenderers.register(HalfLifeEntities.COCKROACH.get(), CockroachRenderer::new);
        EntityRenderers.register(HalfLifeEntities.BABY_HEADCRAB.get(), BabyHeadcrab_renderer::new);
        EntityRenderers.register(HalfLifeEntities.MANHACK.get(), ManhackRenderer::new);
        EntityRenderers.register(HalfLifeEntities.HUNTER.get(), Hunter_renderer::new);
        EntityRenderers.register(HalfLifeEntities.LEECH.get(), Leechrenderer::new);
        EntityRenderers.register(HalfLifeEntities.SHARK.get(), SharkRenderer::new);
        EntityRenderers.register(HalfLifeEntities.ACID_BALL.get(), ThrownItemRenderer::new);
        EntityRenderers.register(HalfLifeEntities.VOLTIGORE_SHOCK.get(), Voltigore_projectile_renderer::new);
        EntityRenderers.register(HalfLifeEntities.PITDRONE_SPIKE.get(), Pitdrone_spike_renderer::new);
        EntityRenderers.register(HalfLifeEntities.FLECHETTE.get(), Flechette_renderer::new);
        EntityRenderers.register(HalfLifeEntities.SHOCK_SHOT.get(), ThrownItemRenderer::new);
        EntityRenderers.register(HalfLifeEntities.BULLET_ONE.get(), ThrownItemRenderer::new);
        EntityRenderers.register(HalfLifeEntities.BULLET_TP.get(), ThrownItemRenderer::new);
        EntityRenderers.register(HalfLifeEntities.ACID_THROWN.get(), ThrownItemRenderer::new);
        EntityRenderers.register(HalfLifeEntities.BUGBAIT_PROJECTILE.get(), ThrownItemRenderer::new);
        EntityRenderers.register(HalfLifeEntities.ACID_THROWNBM.get(), AcidThrownBMRenderer::new);
        EntityRenderers.register(HalfLifeEntities.UNDER_NADE.get(), Undernade_renderer::new);
        EntityRenderers.register(HalfLifeEntities.BARNACLE.get(), Barnacle_Renderer::new);

        EntityRenderers.register(HalfLifeEntities.VORT_LIGHTNING_PROJETILE.get(), NoRenderer::new);


        BlockEntityRenderers.register(HalfLifeBlockEntities.VOLTIGORE_EGG_BLOCK_ENTITY.get(), VoltigoreEggRenderer::new);
    }



    @SubscribeEvent
    public static void XenSpecificEffectsEvent(RegisterDimensionSpecialEffectsEvent event){
        event.register(new ResourceLocation(HalfLifeMod.MOD_ID, "xen_effects"), new XenSpecialEffects());
    }


    @SubscribeEvent
    public static void registerLayer(EntityRenderersEvent.RegisterLayerDefinitions event){
        event.registerLayerDefinition(ModModelLayers.BARNACLE_LAYER, Barnacle_Model::createBodyLayer);
    }


}
