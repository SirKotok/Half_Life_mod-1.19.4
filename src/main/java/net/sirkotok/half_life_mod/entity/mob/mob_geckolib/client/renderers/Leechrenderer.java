package net.sirkotok.half_life_mod.entity.mob.mob_geckolib.client.renderers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.sirkotok.half_life_mod.entity.mob.mob_geckolib.client.models.Leech_model;
import net.sirkotok.half_life_mod.entity.mob.mob_geckolib.custom.Leech;
import org.joml.Matrix4f;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;


public class Leechrenderer extends GeoEntityRenderer<Leech> {
    public Leechrenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new Leech_model());
    }



    @Override
    public void render(Leech entity, float entityYaw,
                       float partialTick, PoseStack poseStack,
                       MultiBufferSource bufferSource, int packedLight) {
        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }


    @Override
    public void preRender(PoseStack poseStack, Leech animatable, BakedGeoModel model, MultiBufferSource bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, float red, float green, float blue,
                          float alpha) {
        this.entityRenderTranslations = new Matrix4f(poseStack.last().pose());
        this.scaleModelForRender(0.3f, 0.3f, poseStack, animatable, model, isReRender, partialTick, packedLight, packedOverlay);
    }

}


