package net.sirkotok.half_life_mod.entity.mob.mob_effect_entity.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.sirkotok.half_life_mod.entity.mob.mob_effect_entity.custom.VortShockWaveEffect;
import net.sirkotok.half_life_mod.entity.mob.mob_effect_entity.client.model.VortShockwaveModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.renderer.layer.AutoGlowingGeoLayer;


public class VortShockwaverenderer extends GeoEntityRenderer<VortShockWaveEffect> {
    public VortShockwaverenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new VortShockwaveModel());
        addRenderLayer(new AutoGlowingGeoLayer<>(this));

    }



    @Override
    public void render(VortShockWaveEffect entity, float entityYaw,
                       float partialTick, PoseStack poseStack,
                       MultiBufferSource bufferSource, int packedLight) {
        poseStack.scale(3f, 3f, 3f);
        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }
}


//   @Override
//   public ResourceLocation getTextureLocation(PigDogBoogeyman animatable) {
//      return new ResourceLocation(PactMod.MOD_ID, "textures/entity/pigdogother1.png");
//   }