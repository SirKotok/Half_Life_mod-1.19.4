package net.sirkotok.half_life_mod.entity.projectile.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.sirkotok.half_life_mod.entity.mob_geckolib.client.models.Snark_model;
import net.sirkotok.half_life_mod.entity.mob_geckolib.custom.Snark;
import net.sirkotok.half_life_mod.entity.projectile.VoltigoreShock;
import org.joml.Quaternionf;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.renderer.layer.AutoGlowingGeoLayer;

public class Voltigore_projectile_renderer extends GeoEntityRenderer<VoltigoreShock> {
    public Voltigore_projectile_renderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new Voltigore_projectile_model());
        addRenderLayer(new AutoGlowingGeoLayer<>(this));
    }



    @Override
    public void render(VoltigoreShock entity, float entityYaw,
                       float partialTick, PoseStack poseStack,
                       MultiBufferSource bufferSource, int packedLight) {
        poseStack.scale(1.5f, 1.5f, 1.5f);

        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }


}
