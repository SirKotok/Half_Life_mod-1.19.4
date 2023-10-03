package net.sirkotok.half_life_mod.entity.mob.client.renderers;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.sirkotok.half_life_mod.entity.mob.client.models.Headcrab_fast_model;
import net.sirkotok.half_life_mod.entity.mob.client.models.Headcrab_m2;
import net.sirkotok.half_life_mod.entity.mob.custom.Headcrab_2;
import net.sirkotok.half_life_mod.entity.mob.custom.Headcrab_Fast;
import software.bernie.geckolib.renderer.GeoEntityRenderer;


public class Headcrab_fastrenderer extends GeoEntityRenderer<Headcrab_Fast> {
    public Headcrab_fastrenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new Headcrab_fast_model());
    }



    @Override
    public void render(Headcrab_Fast entity, float entityYaw,
                       float partialTick, PoseStack poseStack,
                       MultiBufferSource bufferSource, int packedLight) {

        poseStack.scale(0.85f, 0.85f, 0.85f);

        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }
}


//   @Override
//   public ResourceLocation getTextureLocation(PigDogBoogeyman animatable) {
//      return new ResourceLocation(PactMod.MOD_ID, "textures/entity/pigdogother1.png");
//   }