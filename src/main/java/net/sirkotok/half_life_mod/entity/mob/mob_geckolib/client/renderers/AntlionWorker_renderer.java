package net.sirkotok.half_life_mod.entity.mob.mob_geckolib.client.renderers;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.sirkotok.half_life_mod.entity.mob.mob_geckolib.client.models.AntlionWorker_model;
import net.sirkotok.half_life_mod.entity.mob.mob_geckolib.custom.AntlionWorker;
import software.bernie.geckolib.renderer.GeoEntityRenderer;


public class AntlionWorker_renderer extends GeoEntityRenderer<AntlionWorker> {
    public AntlionWorker_renderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new AntlionWorker_model());
    }



    @Override
    public void render(AntlionWorker entity, float entityYaw,
                       float partialTick, PoseStack poseStack,
                       MultiBufferSource bufferSource, int packedLight) {

        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }
}


