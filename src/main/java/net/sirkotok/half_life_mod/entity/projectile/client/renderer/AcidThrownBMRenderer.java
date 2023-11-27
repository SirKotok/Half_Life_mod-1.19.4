package net.sirkotok.half_life_mod.entity.projectile.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.sirkotok.half_life_mod.entity.projectile.AcidThrownBM;
import net.sirkotok.half_life_mod.entity.projectile.SporeShot;
import net.sirkotok.half_life_mod.entity.projectile.client.model.Acid_thrown_BM_model;
import net.sirkotok.half_life_mod.entity.projectile.client.model.Spore_model;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class AcidThrownBMRenderer extends GeoEntityRenderer<AcidThrownBM> {
    public AcidThrownBMRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new Acid_thrown_BM_model());
    }



    @Override
    public void render(AcidThrownBM entity, float entityYaw,
                       float partialTick, PoseStack poseStack,
                       MultiBufferSource bufferSource, int packedLight) {
        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }


}
