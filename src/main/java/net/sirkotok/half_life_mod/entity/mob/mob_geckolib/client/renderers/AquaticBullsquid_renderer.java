package net.sirkotok.half_life_mod.entity.mob.mob_geckolib.client.renderers;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.sirkotok.half_life_mod.entity.mob.mob_geckolib.client.models.AquaticBullsquid_model;
import net.sirkotok.half_life_mod.entity.mob.mob_geckolib.client.models.Bullsquid_model;
import net.sirkotok.half_life_mod.entity.mob.mob_geckolib.custom.AquaticBullsquid;
import net.sirkotok.half_life_mod.entity.mob.mob_geckolib.custom.Bullsquid;
import software.bernie.geckolib.renderer.GeoEntityRenderer;


public class AquaticBullsquid_renderer extends GeoEntityRenderer<AquaticBullsquid> {
    public AquaticBullsquid_renderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new AquaticBullsquid_model());
    }



    @Override
    public void render(AquaticBullsquid entity, float entityYaw,
                       float partialTick, PoseStack poseStack,
                       MultiBufferSource bufferSource, int packedLight) {
        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }

}
