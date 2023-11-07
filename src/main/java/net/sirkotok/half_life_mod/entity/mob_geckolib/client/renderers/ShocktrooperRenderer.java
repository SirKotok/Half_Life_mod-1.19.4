package net.sirkotok.half_life_mod.entity.mob_geckolib.client.renderers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.sirkotok.half_life_mod.entity.mob_geckolib.client.models.Shoctrooper_model;
import net.sirkotok.half_life_mod.entity.mob_geckolib.client.models.Vortigore_model;
import net.sirkotok.half_life_mod.entity.mob_geckolib.custom.Shocktrooper;
import net.sirkotok.half_life_mod.entity.mob_geckolib.custom.Voltigore;
import org.joml.Matrix4f;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.renderer.layer.AutoGlowingGeoLayer;


public class ShocktrooperRenderer extends GeoEntityRenderer<Shocktrooper> {
    public ShocktrooperRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new Shoctrooper_model());
        addRenderLayer(new AutoGlowingGeoLayer<>(this));
    }



    @Override
    public void render(Shocktrooper entity, float entityYaw,
                       float partialTick, PoseStack poseStack,
                       MultiBufferSource bufferSource, int packedLight) {
        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }



}

