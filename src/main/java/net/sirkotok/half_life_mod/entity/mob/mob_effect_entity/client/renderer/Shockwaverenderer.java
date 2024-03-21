package net.sirkotok.half_life_mod.entity.mob.mob_effect_entity.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.sirkotok.half_life_mod.entity.mob.mob_effect_entity.custom.ShockWaveEffect;
import net.sirkotok.half_life_mod.entity.mob.mob_effect_entity.client.model.Shockwave_model;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.renderer.layer.AutoGlowingGeoLayer;


public class Shockwaverenderer extends GeoEntityRenderer<ShockWaveEffect> {
    public Shockwaverenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new Shockwave_model());
        addRenderLayer(new AutoGlowingGeoLayer<>(this));

    }



    @Override
    public void render(ShockWaveEffect entity, float entityYaw,
                       float partialTick, PoseStack poseStack,
                       MultiBufferSource bufferSource, int packedLight) {

        poseStack.scale(1.3f, 1.3f, 1.3f);

        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }
}


//   @Override
//   public ResourceLocation getTextureLocation(PigDogBoogeyman animatable) {
//      return new ResourceLocation(PactMod.MOD_ID, "textures/entity/pigdogother1.png");
//   }