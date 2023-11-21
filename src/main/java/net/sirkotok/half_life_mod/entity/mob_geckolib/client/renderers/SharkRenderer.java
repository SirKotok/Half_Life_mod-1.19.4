package net.sirkotok.half_life_mod.entity.mob_geckolib.client.renderers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.sirkotok.half_life_mod.entity.mob_geckolib.client.models.Cockroach_model;
import net.sirkotok.half_life_mod.entity.mob_geckolib.client.models.Shark_model;
import net.sirkotok.half_life_mod.entity.mob_geckolib.custom.Cockroach;
import net.sirkotok.half_life_mod.entity.mob_geckolib.custom.Shark;
import org.joml.Matrix4f;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;


public class SharkRenderer extends GeoEntityRenderer<Shark> {
    public SharkRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new Shark_model());
    }



    @Override
    public void render(Shark entity, float entityYaw,
                       float partialTick, PoseStack poseStack,
                       MultiBufferSource bufferSource, int packedLight) {
        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }


}


