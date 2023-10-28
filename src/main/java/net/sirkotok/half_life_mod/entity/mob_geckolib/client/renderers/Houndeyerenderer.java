package net.sirkotok.half_life_mod.entity.mob_geckolib.client.renderers;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.sirkotok.half_life_mod.entity.mob_geckolib.client.models.Headcrab_m1;
import net.sirkotok.half_life_mod.entity.mob_geckolib.client.models.Houndeye_model;
import net.sirkotok.half_life_mod.entity.mob_geckolib.custom.Headcrab_1;
import net.sirkotok.half_life_mod.entity.mob_geckolib.custom.Houndeye;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.renderer.layer.AutoGlowingGeoLayer;


public class Houndeyerenderer extends GeoEntityRenderer<Houndeye> {
    public Houndeyerenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new Houndeye_model());
        addRenderLayer(new AutoGlowingGeoLayer<>(this));

    }



    @Override
    public void render(Houndeye entity, float entityYaw,
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