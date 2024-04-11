package net.sirkotok.half_life_mod.entity.mob.mob_geckolib.client.renderers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.sirkotok.half_life_mod.entity.mob.mob_geckolib.client.renderers.renderlayer.ScientistRenderCoatLayer;
import net.sirkotok.half_life_mod.entity.mob.mob_geckolib.client.renderers.renderlayer.ScientistRenderHeadLayer;
import net.sirkotok.half_life_mod.entity.mob.mob_geckolib.client.renderers.renderlayer.ScientistRenderPantsLayer;
import net.sirkotok.half_life_mod.entity.mob.mob_geckolib.client.models.Scientist_model;
import net.sirkotok.half_life_mod.entity.mob.mob_geckolib.custom.Scientist;
import net.sirkotok.half_life_mod.misc.util.HLperUtil;
import org.joml.Quaternionf;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.util.RenderUtils;


public class Scientist_renderer2 extends GeoEntityRenderer<Scientist> {
    public Scientist_renderer2(EntityRendererProvider.Context renderManager) {
        super(renderManager, new Scientist_model());
        addRenderLayer(new ScientistRenderCoatLayer(this));
        addRenderLayer(new ScientistRenderHeadLayer(this));
        addRenderLayer(new ScientistRenderPantsLayer(this));
    }



    @Override
    public void render(Scientist entity, float entityYaw,
                       float partialTick, PoseStack poseStack,
                       MultiBufferSource bufferSource, int packedLight) {
            super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }



}
