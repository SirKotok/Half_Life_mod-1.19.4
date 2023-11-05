package net.sirkotok.half_life_mod.entity.mob_effect_entity.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.sirkotok.half_life_mod.entity.mob_effect_entity.client.model.Voltigore_projectile_e_model;
import net.sirkotok.half_life_mod.entity.mob_effect_entity.custom.VoltigoreProjectileAftereffect;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.renderer.layer.AutoGlowingGeoLayer;

public class Voltigore_projectile_e_renderer extends GeoEntityRenderer<VoltigoreProjectileAftereffect> {
    public Voltigore_projectile_e_renderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new Voltigore_projectile_e_model());
        addRenderLayer(new AutoGlowingGeoLayer<>(this));
    }


    @Override
    public void render(VoltigoreProjectileAftereffect entity, float entityYaw,
                       float partialTick, PoseStack poseStack,
                       MultiBufferSource bufferSource, int packedLight) {
        poseStack.scale(1.5f, 1.5f, 1.5f);

        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }


}
