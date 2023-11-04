package net.sirkotok.half_life_mod.entity.mob_geckolib.client.renderers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.world.entity.LivingEntity;
import net.sirkotok.half_life_mod.entity.mob_geckolib.client.models.Snark_model;
import net.sirkotok.half_life_mod.entity.mob_geckolib.client.models.Vortigore_model;
import net.sirkotok.half_life_mod.entity.mob_geckolib.custom.Snark;
import net.sirkotok.half_life_mod.entity.mob_geckolib.custom.Vortigore;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.renderer.layer.AutoGlowingGeoLayer;


public class VortigoreRenderer extends GeoEntityRenderer<Vortigore> {
    public VortigoreRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new Vortigore_model());
    }



    @Override
    public void render(Vortigore entity, float entityYaw,
                       float partialTick, PoseStack poseStack,
                       MultiBufferSource bufferSource, int packedLight) {
        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }


    @Override
    public void preRender(PoseStack poseStack, Vortigore animatable, BakedGeoModel model, MultiBufferSource bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, float red, float green, float blue,
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