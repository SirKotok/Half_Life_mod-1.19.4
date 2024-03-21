package net.sirkotok.half_life_mod.entity.mob.mob_geckolib.client.renderers;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.sirkotok.half_life_mod.entity.mob.mob_geckolib.custom.Bullsquid;
import net.sirkotok.half_life_mod.entity.mob.mob_geckolib.client.models.Bullsquid_model;

import software.bernie.geckolib.renderer.GeoEntityRenderer;


public class Bullsquid_renderer extends GeoEntityRenderer<Bullsquid> {
    public Bullsquid_renderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new Bullsquid_model());
    }



    @Override
    public void render(Bullsquid entity, float entityYaw,
                       float partialTick, PoseStack poseStack,
                       MultiBufferSource bufferSource, int packedLight) {

        poseStack.scale(1f, 1f, 1f);

        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }
}


//   @Override
//   public ResourceLocation getTextureLocation(PigDogBoogeyman animatable) {
//      return new ResourceLocation(PactMod.MOD_ID, "textures/entity/pigdogother1.png");
//   }