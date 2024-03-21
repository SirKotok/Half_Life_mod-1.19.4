package net.sirkotok.half_life_mod.entity.mob.mob_geckolib.client.renderers;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.sirkotok.half_life_mod.entity.mob.mob_geckolib.client.models.Headcrab_poison_a_model;
import net.sirkotok.half_life_mod.entity.mob.mob_geckolib.custom.Headcrab_Poison_3;
import software.bernie.geckolib.renderer.GeoEntityRenderer;


public class Headcrab_poison_3renderer extends GeoEntityRenderer<Headcrab_Poison_3> {
    public Headcrab_poison_3renderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new Headcrab_poison_a_model());
    }



    @Override
    public void render(Headcrab_Poison_3 entity, float entityYaw,
                       float partialTick, PoseStack poseStack,
                       MultiBufferSource bufferSource, int packedLight) {

        poseStack.scale(0.9f, 0.9f, 0.9f);

        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }
}


//   @Override
//   public ResourceLocation getTextureLocation(PigDogBoogeyman animatable) {
//      return new ResourceLocation(PactMod.MOD_ID, "textures/entity/pigdogother1.png");
//   }