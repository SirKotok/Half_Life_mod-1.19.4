package net.sirkotok.half_life_mod.entity.projectile.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.sirkotok.half_life_mod.entity.mob_geckolib.custom.Leech;
import net.sirkotok.half_life_mod.entity.projectile.PitdroneSpike;
import net.sirkotok.half_life_mod.entity.projectile.arrowlike.Flechette;
import net.sirkotok.half_life_mod.entity.projectile.client.model.Flechette_model;
import net.sirkotok.half_life_mod.entity.projectile.client.model.Spike_model;
import org.joml.Matrix4f;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.renderer.layer.AutoGlowingGeoLayer;

public class Flechette_renderer extends GeoEntityRenderer<Flechette> {
    public Flechette_renderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new Flechette_model());
        addRenderLayer(new AutoGlowingGeoLayer<>(this));
    }



    @Override
    public void render(Flechette entity, float entityYaw,
                       float partialTick, PoseStack poseStack,
                       MultiBufferSource bufferSource, int packedLight) {
        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }

    @Override
    public void preRender(PoseStack poseStack, Flechette animatable, BakedGeoModel model, MultiBufferSource bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, float red, float green, float blue,
                          float alpha) {
        this.entityRenderTranslations = new Matrix4f(poseStack.last().pose());
        this.scaleModelForRender(0.6f, 0.6f, poseStack, animatable, model, isReRender, partialTick, packedLight, packedOverlay);
    }

}
