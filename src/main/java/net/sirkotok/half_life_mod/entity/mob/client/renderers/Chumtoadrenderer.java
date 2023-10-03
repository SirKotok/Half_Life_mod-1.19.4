package net.sirkotok.half_life_mod.entity.mob.client.renderers;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.sirkotok.half_life_mod.entity.mob.client.models.Chumtoad_model;
import net.sirkotok.half_life_mod.entity.mob.client.models.Headcrab_m1;
import net.sirkotok.half_life_mod.entity.mob.custom.Chumtoad;
import net.sirkotok.half_life_mod.entity.mob.custom.Headcrab_1;
import software.bernie.geckolib.renderer.GeoEntityRenderer;


public class Chumtoadrenderer extends GeoEntityRenderer<Chumtoad> {
    public Chumtoadrenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new Chumtoad_model());
    }



    @Override
    public void render(Chumtoad entity, float entityYaw,
                       float partialTick, PoseStack poseStack,
                       MultiBufferSource bufferSource, int packedLight) {

        poseStack.scale(0.9f, 0.9f, 0.9f);

        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }
}


//   @Override
//   public ResourceLocation getTextureLocation(PigDogBoogeyman animatable) {
//      return new ResourceLocation(PactMod.MOD_ID, "textures/entity/pigdogother1.png");
//   }