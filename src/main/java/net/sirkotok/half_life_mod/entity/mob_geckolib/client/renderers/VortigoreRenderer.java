package net.sirkotok.half_life_mod.entity.mob_geckolib.client.renderers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.sirkotok.half_life_mod.entity.mob_geckolib.client.models.Vortigore_model;
import net.sirkotok.half_life_mod.entity.mob_geckolib.custom.Voltigore;
import org.joml.Matrix4f;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;


public class VortigoreRenderer extends GeoEntityRenderer<Voltigore> {
    public VortigoreRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new Vortigore_model());
    }



    @Override
    public void render(Voltigore entity, float entityYaw,
                       float partialTick, PoseStack poseStack,
                       MultiBufferSource bufferSource, int packedLight) {
        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }


    @Override
    public void preRender(PoseStack poseStack, Voltigore animatable, BakedGeoModel model, MultiBufferSource bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, float red, float green, float blue,
                          float alpha) {
        this.entityRenderTranslations = new Matrix4f(poseStack.last().pose());
        if (animatable.isBB()) scaleModelForRender(0.3f, 0.3f, poseStack, animatable, model, isReRender, partialTick, packedLight, packedOverlay);
        else scaleModelForRender(1f, 1f, poseStack, animatable, model, isReRender, partialTick, packedLight, packedOverlay);
    }









}


//   @Override
//   public ResourceLocation getTextureLocation(PigDogBoogeyman animatable) {
//      return new ResourceLocation(PactMod.MOD_ID, "textures/entity/pigdogother1.png");
//   }