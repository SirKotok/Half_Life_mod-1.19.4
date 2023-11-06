package net.sirkotok.half_life_mod.entity.mob_geckolib.client.renderers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.sirkotok.half_life_mod.entity.mob_geckolib.client.models.Bullsquid_model;
import net.sirkotok.half_life_mod.entity.mob_geckolib.client.models.Pitdrone_model;
import net.sirkotok.half_life_mod.entity.mob_geckolib.custom.Bullsquid;
import net.sirkotok.half_life_mod.entity.mob_geckolib.custom.Pitdrone;
import net.sirkotok.half_life_mod.entity.mob_geckolib.custom.Voltigore;
import org.joml.Matrix4f;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;


public class Pitdrone_renderer extends GeoEntityRenderer<Pitdrone> {
    public Pitdrone_renderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new Pitdrone_model());
    }



    @Override
    public void render(Pitdrone entity, float entityYaw,
                       float partialTick, PoseStack poseStack,
                       MultiBufferSource bufferSource, int packedLight) {

        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);



    }

    @Override
    public void preRender(PoseStack poseStack, Pitdrone animatable, BakedGeoModel model, MultiBufferSource bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, float red, float green, float blue,
                          float alpha) {
        this.entityRenderTranslations = new Matrix4f(poseStack.last().pose());
        scaleModelForRender(1.3f, 1.3f, poseStack, animatable, model, isReRender, partialTick, packedLight, packedOverlay);
    }



}



//   @Override
//   public ResourceLocation getTextureLocation(PigDogBoogeyman animatable) {
//      return new ResourceLocation(PactMod.MOD_ID, "textures/entity/pigdogother1.png");
//   }