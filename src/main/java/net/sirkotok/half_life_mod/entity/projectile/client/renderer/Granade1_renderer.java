package net.sirkotok.half_life_mod.entity.projectile.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.sirkotok.half_life_mod.entity.projectile.Granade;
import net.sirkotok.half_life_mod.entity.projectile.SporeShot;
import net.sirkotok.half_life_mod.entity.projectile.client.model.Granade1_model;
import net.sirkotok.half_life_mod.entity.projectile.client.model.Spore_model;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class Granade1_renderer extends GeoEntityRenderer<Granade> {
    public Granade1_renderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new Granade1_model());
    }



    @Override
    public void render(Granade entity, float entityYaw,
                       float partialTick, PoseStack poseStack,
                       MultiBufferSource bufferSource, int packedLight) {
        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }


}
