package net.sirkotok.half_life_mod.entity.projectile.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.sirkotok.half_life_mod.entity.projectile.PitdroneSpike;
import net.sirkotok.half_life_mod.entity.projectile.VoltigoreShock;
import net.sirkotok.half_life_mod.entity.projectile.arrowlike.Flechette;
import net.sirkotok.half_life_mod.entity.projectile.client.model.Spike_model;
import net.sirkotok.half_life_mod.entity.projectile.client.model.Voltigore_projectile_model;
import org.joml.Quaternionf;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.renderer.layer.AutoGlowingGeoLayer;

public class Pitdrone_spike_renderer extends GeoEntityRenderer<PitdroneSpike> {
    public Pitdrone_spike_renderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new Spike_model());
    }



    @Override
    public void render(PitdroneSpike entity, float entityYaw,
                       float partialTick, PoseStack poseStack,
                       MultiBufferSource bufferSource, int packedLight) {

        float yRot = entity.getYRot();
        poseStack.rotateAround(new Quaternionf().rotationY(-yRot), 0, 0, 0);
        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }


}
