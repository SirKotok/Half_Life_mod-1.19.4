package net.sirkotok.half_life_mod.entity.mob.client.renderers;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.sirkotok.half_life_mod.entity.mob.client.models.Chumtoad_model;
import net.sirkotok.half_life_mod.entity.mob.client.models.Snarknest_model;
import net.sirkotok.half_life_mod.entity.mob.custom.Chumtoad;
import net.sirkotok.half_life_mod.entity.mob.custom.Snarknest;
import software.bernie.geckolib.renderer.GeoEntityRenderer;


public class Snarknestrenderer extends GeoEntityRenderer<Snarknest> {
    public Snarknestrenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new Snarknest_model());
    }



    @Override
    public void render(Snarknest entity, float entityYaw,
                       float partialTick, PoseStack poseStack,
                       MultiBufferSource bufferSource, int packedLight) {

        poseStack.scale(1.2f, 1.2f, 1.2f);

        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }
}


//   @Override
//   public ResourceLocation getTextureLocation(PigDogBoogeyman animatable) {
//      return new ResourceLocation(PactMod.MOD_ID, "textures/entity/pigdogother1.png");
//   }