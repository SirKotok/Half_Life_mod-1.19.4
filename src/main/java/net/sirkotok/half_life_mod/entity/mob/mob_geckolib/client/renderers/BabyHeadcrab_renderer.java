package net.sirkotok.half_life_mod.entity.mob.mob_geckolib.client.renderers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.sirkotok.half_life_mod.entity.mob.mob_geckolib.custom.Baby_Headcrab;
import net.sirkotok.half_life_mod.entity.mob.mob_geckolib.client.models.Baby_headcrab_model;
import org.joml.Matrix4f;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;


public class BabyHeadcrab_renderer extends GeoEntityRenderer<Baby_Headcrab> {
    public BabyHeadcrab_renderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new Baby_headcrab_model());
    }



    @Override
    public void render(Baby_Headcrab entity, float entityYaw,
                       float partialTick, PoseStack poseStack,
                       MultiBufferSource bufferSource, int packedLight) {
        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }


    @Override
    public void preRender(PoseStack poseStack, Baby_Headcrab animatable, BakedGeoModel model, MultiBufferSource bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, float red, float green, float blue,
                          float alpha) {
        this.entityRenderTranslations = new Matrix4f(poseStack.last().pose());
        this.scaleModelForRender(0.6f, 0.6f, poseStack, animatable, model, isReRender, partialTick, packedLight, packedOverlay);
    }

}


