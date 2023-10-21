package net.sirkotok.half_life_mod.entity.mob_geckolib.client.renderers;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.sirkotok.half_life_mod.entity.mob_geckolib.client.models.Penguin_model;
import net.sirkotok.half_life_mod.entity.mob_geckolib.custom.Penguin;
import net.sirkotok.half_life_mod.entity.mob_geckolib.custom.Snark;
import org.joml.Quaternionf;
import software.bernie.geckolib.renderer.GeoEntityRenderer;


public class PenguinRenderer extends GeoEntityRenderer<Penguin> {
    public PenguinRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new Penguin_model());
    }



    @Override
    public void render(Penguin entity, float entityYaw,
                       float partialTick, PoseStack poseStack,
                       MultiBufferSource bufferSource, int packedLight) {
        poseStack.scale(0.9f, 0.9f, 0.9f);
        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.player!=null && entity.tickCount<6) {
            float yRot = minecraft.player.getYRot()/180f*(float)Math.PI;
            poseStack.rotateAround(new Quaternionf().rotationY(-yRot), 0, 0, 0);
        }

        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }

}


//   @Override
//   public ResourceLocation getTextureLocation(PigDogBoogeyman animatable) {
//      return new ResourceLocation(PactMod.MOD_ID, "textures/entity/pigdogother1.png");
//   }