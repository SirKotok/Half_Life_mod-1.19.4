package net.sirkotok.half_life_mod.entity.mob_geckolib.client.renderers;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.sirkotok.half_life_mod.entity.mob_geckolib.client.models.Antlion_model;
import net.sirkotok.half_life_mod.entity.mob_geckolib.client.models.Headcrab_m1;
import net.sirkotok.half_life_mod.entity.mob_geckolib.custom.Antlion;
import net.sirkotok.half_life_mod.entity.mob_geckolib.custom.Headcrab_1;
import software.bernie.geckolib.renderer.GeoEntityRenderer;


public class Antlion_renderer extends GeoEntityRenderer<Antlion> {
    public Antlion_renderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new Antlion_model());
    }



    @Override
    public void render(Antlion entity, float entityYaw,
                       float partialTick, PoseStack poseStack,
                       MultiBufferSource bufferSource, int packedLight) {

        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }
}


