package net.sirkotok.half_life_mod.entity.mob.client.renderers;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.sirkotok.half_life_mod.entity.mob.client.models.Penguin_model;
import net.sirkotok.half_life_mod.entity.mob.custom.Penguin;
import software.bernie.geckolib.renderer.GeoEntityRenderer;


public class PenguinRenderer extends GeoEntityRenderer<Penguin> {
    public PenguinRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new Penguin_model());
    }



    @Override
    public void render(Penguin entity, float entityYaw,
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