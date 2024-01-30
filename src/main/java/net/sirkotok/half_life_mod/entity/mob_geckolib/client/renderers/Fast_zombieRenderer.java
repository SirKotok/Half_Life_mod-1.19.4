package net.sirkotok.half_life_mod.entity.mob_geckolib.client.renderers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.sirkotok.half_life_mod.entity.mob_geckolib.client.models.Bullsquid_model;
import net.sirkotok.half_life_mod.entity.mob_geckolib.client.models.Zombiehl2_model_fast;
import net.sirkotok.half_life_mod.entity.mob_geckolib.custom.Bullsquid;
import net.sirkotok.half_life_mod.entity.mob_geckolib.custom.HL2Zombie_fast;
import net.sirkotok.half_life_mod.entity.mob_geckolib.custom.Voltigore;
import org.joml.Matrix4f;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;


public class Fast_zombieRenderer extends GeoEntityRenderer<HL2Zombie_fast> {
    public Fast_zombieRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new Zombiehl2_model_fast());
    }



    @Override
    public void render(HL2Zombie_fast entity, float entityYaw,
                       float partialTick, PoseStack poseStack,
                       MultiBufferSource bufferSource, int packedLight) {

        poseStack.scale(1f, 1f, 1f);

        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }




}


//   @Override
//   public ResourceLocation getTextureLocation(PigDogBoogeyman animatable) {
//      return new ResourceLocation(PactMod.MOD_ID, "textures/entity/pigdogother1.png");
//   }