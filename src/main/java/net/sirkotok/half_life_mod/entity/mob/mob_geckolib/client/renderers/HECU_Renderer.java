package net.sirkotok.half_life_mod.entity.mob.mob_geckolib.client.renderers;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.sirkotok.half_life_mod.entity.mob.mob_geckolib.client.models.HECU_model;
import net.sirkotok.half_life_mod.entity.mob.mob_geckolib.client.models.Scientist_model;
import net.sirkotok.half_life_mod.entity.mob.mob_geckolib.client.renderers.renderlayer.*;
import net.sirkotok.half_life_mod.entity.mob.mob_geckolib.custom.HecuGrunt;
import net.sirkotok.half_life_mod.entity.mob.mob_geckolib.custom.Scientist;
import software.bernie.geckolib.renderer.GeoEntityRenderer;


public class HECU_Renderer extends GeoEntityRenderer<HecuGrunt> {
    public HECU_Renderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new HECU_model());
        addRenderLayer(new HecuHeadRenderLayer(this));
        addRenderLayer(new HecuCigRenderLayer(this));
        addRenderLayer(new HecuHandRenderLayer(this));
        addRenderLayer(new HecuHelmetRenderLayer(this));
        addRenderLayer(new HecuBackPackRenderLayer(this));
        addRenderLayer(new HecuPouchRenderLayer(this));
    }



    @Override
    public void render(HecuGrunt entity, float entityYaw,
                       float partialTick, PoseStack poseStack,
                       MultiBufferSource bufferSource, int packedLight) {
            super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }



}
