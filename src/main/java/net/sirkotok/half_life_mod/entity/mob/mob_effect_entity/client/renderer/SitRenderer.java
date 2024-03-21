package net.sirkotok.half_life_mod.entity.mob.mob_effect_entity.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.sirkotok.half_life_mod.entity.mob.mob_effect_entity.custom.SitThenBlowUpEffect;
import net.sirkotok.half_life_mod.entity.mob.mob_effect_entity.client.model.SitEffect_model;
import software.bernie.geckolib.renderer.GeoEntityRenderer;


public class SitRenderer extends GeoEntityRenderer<SitThenBlowUpEffect> {
    public SitRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new SitEffect_model());
    }



    @Override
    public void render(SitThenBlowUpEffect entity, float entityYaw,
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