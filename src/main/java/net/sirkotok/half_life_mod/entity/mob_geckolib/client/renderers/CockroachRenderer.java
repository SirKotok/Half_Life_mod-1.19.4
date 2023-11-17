package net.sirkotok.half_life_mod.entity.mob_geckolib.client.renderers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.sirkotok.half_life_mod.entity.mob_geckolib.client.models.Cockroach_model;
import net.sirkotok.half_life_mod.entity.mob_geckolib.custom.Cockroach;
import net.sirkotok.half_life_mod.entity.mob_geckolib.custom.Shockroach;
import net.sirkotok.half_life_mod.entity.mob_geckolib.custom.Voltigore;
import org.joml.Matrix4f;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;


public class CockroachRenderer extends GeoEntityRenderer<Cockroach> {
    public CockroachRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new Cockroach_model());
    }



    @Override
    public void render(Cockroach entity, float entityYaw,
                       float partialTick, PoseStack poseStack,
                       MultiBufferSource bufferSource, int packedLight) {
        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }


    @Override
    public void preRender(PoseStack poseStack, Cockroach animatable, BakedGeoModel model, MultiBufferSource bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, float red, float green, float blue,
                          float alpha) {
        this.entityRenderTranslations = new Matrix4f(poseStack.last().pose());
        this.scaleModelForRender(0.6f, 0.6f, poseStack, animatable, model, isReRender, partialTick, packedLight, packedOverlay);
    }

}


