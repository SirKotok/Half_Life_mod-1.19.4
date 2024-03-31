package net.sirkotok.half_life_mod.entity.mob.mob_geckolib.client.renderers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.sirkotok.half_life_mod.entity.mob.mob_geckolib.client.models.Houndeye_model;
import net.sirkotok.half_life_mod.entity.mob.mob_geckolib.custom.Houndeye;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.renderer.layer.AutoGlowingGeoLayer;


public class Houndeyerenderer extends GeoEntityRenderer<Houndeye> {
    public Houndeyerenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new Houndeye_model());
        addRenderLayer(new AutoGlowingGeoLayer<>(this));

    }

    @Override
    public void applyRenderLayers(PoseStack poseStack, Houndeye animatable, BakedGeoModel model, RenderType renderType, MultiBufferSource bufferSource, VertexConsumer buffer, float partialTick, int packedLight, int packedOverlay) {
        if (!animatable.islight()) return;
        super.applyRenderLayers(poseStack, animatable, model, renderType, bufferSource, buffer, partialTick, packedLight, packedOverlay);
    }

    @Override
    public void render(Houndeye entity, float entityYaw,
                       float partialTick, PoseStack poseStack,
                       MultiBufferSource bufferSource, int packedLight) {

        poseStack.scale(1.3f, 1.3f, 1.3f);

        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }
}

