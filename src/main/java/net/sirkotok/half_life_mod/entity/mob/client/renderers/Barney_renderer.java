package net.sirkotok.half_life_mod.entity.mob.client.renderers;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.sirkotok.half_life_mod.entity.mob.client.models.Barney_model;
import net.sirkotok.half_life_mod.entity.mob.client.models.Chumtoad_model;
import net.sirkotok.half_life_mod.entity.mob.custom.Barney;
import net.sirkotok.half_life_mod.entity.mob.custom.Chumtoad;
import software.bernie.geckolib.renderer.GeoEntityRenderer;


public class Barney_renderer extends GeoEntityRenderer<Barney> {
    public Barney_renderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new Barney_model());
    }



    @Override
    public void render(Barney entity, float entityYaw,
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