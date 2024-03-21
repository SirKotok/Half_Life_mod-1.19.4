package net.sirkotok.half_life_mod.entity.mob.mob_geckolib.client.renderers;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.sirkotok.half_life_mod.entity.mob.mob_geckolib.client.models.PZombie_model;
import net.sirkotok.half_life_mod.entity.mob.mob_geckolib.custom.Pzombie;
import software.bernie.geckolib.renderer.GeoEntityRenderer;


public class pzombieRenderer extends GeoEntityRenderer<Pzombie> {
    public pzombieRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new PZombie_model());
    }



    @Override
    public void render(Pzombie entity, float entityYaw,
                       float partialTick, PoseStack poseStack,
                       MultiBufferSource bufferSource, int packedLight) {


        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }




}

