package net.sirkotok.half_life_mod.entity.mob_geckolib.client.renderers;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.sirkotok.half_life_mod.entity.mob_geckolib.client.models.Headcrab_zombie_standart_model;
import net.sirkotok.half_life_mod.entity.mob_geckolib.custom.Headcrab_zombie_standart;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class Headcrab_zombie_standart_renderer extends GeoEntityRenderer<Headcrab_zombie_standart> {



    public Headcrab_zombie_standart_renderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new Headcrab_zombie_standart_model());
    }



    @Override
    public void render(Headcrab_zombie_standart entity, float entityYaw,
                       float partialTick, PoseStack poseStack,
                       MultiBufferSource bufferSource, int packedLight) {
        poseStack.scale(1f, 1f, 1f);
        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }

    @Override
    public RenderType getRenderType(Headcrab_zombie_standart animatable, ResourceLocation texture, @Nullable MultiBufferSource bufferSource, float partialTick) {
        return super.getRenderType(animatable, texture, bufferSource, partialTick);
    }
}

