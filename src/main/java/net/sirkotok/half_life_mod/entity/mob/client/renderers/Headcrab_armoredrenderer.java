package net.sirkotok.half_life_mod.entity.mob.client.renderers;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.sirkotok.half_life_mod.entity.mob.client.models.Headcrab_armor_model;
import net.sirkotok.half_life_mod.entity.mob.client.models.Headcrab_m3;
import net.sirkotok.half_life_mod.entity.mob.custom.Headcrab_3;
import net.sirkotok.half_life_mod.entity.mob.custom.Headcrab_Armored;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.renderer.layer.AutoGlowingGeoLayer;


public class Headcrab_armoredrenderer extends GeoEntityRenderer<Headcrab_Armored> {
    public Headcrab_armoredrenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new Headcrab_armor_model());
        addRenderLayer(new AutoGlowingGeoLayer<>(this));
    }



    @Override
    public void render(Headcrab_Armored entity, float entityYaw,
                       float partialTick, PoseStack poseStack,
                       MultiBufferSource bufferSource, int packedLight) {

        poseStack.scale(0.9f, 0.9f, 0.9f);

        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }
}

