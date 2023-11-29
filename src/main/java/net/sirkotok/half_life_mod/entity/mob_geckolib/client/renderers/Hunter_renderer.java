package net.sirkotok.half_life_mod.entity.mob_geckolib.client.renderers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.sirkotok.half_life_mod.entity.mob_geckolib.client.models.Hunter_model;
import net.sirkotok.half_life_mod.entity.mob_geckolib.client.models.Pitdrone_model;
import net.sirkotok.half_life_mod.entity.mob_geckolib.custom.Hunter;
import net.sirkotok.half_life_mod.entity.mob_geckolib.custom.Pitdrone;
import org.joml.Matrix4f;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.renderer.layer.AutoGlowingGeoLayer;


public class Hunter_renderer extends GeoEntityRenderer<Hunter> {
    public Hunter_renderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new Hunter_model());
        addRenderLayer(new AutoGlowingGeoLayer<>(this));
    }


    @Override
    public void render(Hunter entity, float entityYaw,
                       float partialTick, PoseStack poseStack,
                       MultiBufferSource bufferSource, int packedLight) {
        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }



}



//   @Override
//   public ResourceLocation getTextureLocation(PigDogBoogeyman animatable) {
//      return new ResourceLocation(PactMod.MOD_ID, "textures/entity/pigdogother1.png");
//   }