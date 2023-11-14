package net.sirkotok.half_life_mod.entity.projectile.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.sirkotok.half_life_mod.entity.projectile.PitdroneSpike;
import net.sirkotok.half_life_mod.entity.projectile.SporeShot;
import net.sirkotok.half_life_mod.entity.projectile.client.model.Spike_model;
import net.sirkotok.half_life_mod.entity.projectile.client.model.Spore_model;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class Spore_renderer extends GeoEntityRenderer<SporeShot> {
    public Spore_renderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new Spore_model());
    }



    @Override
    public void render(SporeShot entity, float entityYaw,
                       float partialTick, PoseStack poseStack,
                       MultiBufferSource bufferSource, int packedLight) {
        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }


}
