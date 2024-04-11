package net.sirkotok.half_life_mod.entity.mob.mob_geckolib.client.renderers;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.sirkotok.half_life_mod.entity.mob.mob_geckolib.client.models.ZombieHL1_model;
import net.sirkotok.half_life_mod.entity.mob.mob_geckolib.custom.ZombieHl1;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class Zombie2_HL1Renderer extends GeoEntityRenderer<ZombieHl1> {
    public Zombie2_HL1Renderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new ZombieHL1_model());
     //   addRenderLayer(new ZombieScientistRenderBaseLayer(this));
     //   addRenderLayer(new ZombieScientistRenderCoatLayer(this));
     //   addRenderLayer(new ZombieScientistRenderHeadLayer(this));
     //   addRenderLayer(new ZombieScientistRenderPantsLayer(this));
    }


    @Override
    public RenderType getRenderType(ZombieHl1 animatable, ResourceLocation texture, @Nullable MultiBufferSource bufferSource, float partialTick) {
        return RenderType.entityTranslucent(texture);
    }

    @Override
    public void render(ZombieHl1 entity, float entityYaw,
                       float partialTick, PoseStack poseStack,
                       MultiBufferSource bufferSource, int packedLight) {

        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }




}
