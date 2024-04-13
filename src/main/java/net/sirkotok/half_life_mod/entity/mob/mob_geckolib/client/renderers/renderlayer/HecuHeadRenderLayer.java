package net.sirkotok.half_life_mod.entity.mob.mob_geckolib.client.renderers.renderlayer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.sirkotok.half_life_mod.HalfLifeMod;
import net.sirkotok.half_life_mod.entity.mob.mob_geckolib.custom.HecuGrunt;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoRenderer;
import software.bernie.geckolib.renderer.layer.GeoRenderLayer;

public class HecuHeadRenderLayer extends GeoRenderLayer<HecuGrunt> {

    public HecuHeadRenderLayer(GeoRenderer<HecuGrunt> entityRenderer) {
        super(entityRenderer);
    }


    private static final ResourceLocation WHITE =
            new ResourceLocation(HalfLifeMod.MOD_ID,"textures/entity/hecu/hecu_headwhite.png");
    private static final ResourceLocation BLACK =
            new ResourceLocation(HalfLifeMod.MOD_ID,"textures/entity/hecu/hecu_headblack.png");
    private static final ResourceLocation MEDIC_WHITE =
            new ResourceLocation(HalfLifeMod.MOD_ID,"textures/entity/hecu/hecu_headmedicwhite.png");
    private static final ResourceLocation MEDIC_BLACK =
            new ResourceLocation(HalfLifeMod.MOD_ID,"textures/entity/hecu/hecu_headmedicblack.png");
    private static final ResourceLocation BAL =
            new ResourceLocation(HalfLifeMod.MOD_ID,"textures/entity/hecu/hecu_headbal1.png");
    private static final ResourceLocation GASMASK =
            new ResourceLocation(HalfLifeMod.MOD_ID,"textures/entity/hecu/hecu_headgasmask.png");




    @Override
    public void render(PoseStack poseStack, HecuGrunt animatable, BakedGeoModel bakedModel, RenderType renderType, MultiBufferSource bufferSource, VertexConsumer buffer, float partialTick, int packedLight, int packedOverlay) {

        ResourceLocation TEXTURE = animatable.getSkin() ? WHITE : BLACK;
        if (animatable.isMedic()) {
            TEXTURE = animatable.getSkin() ? MEDIC_WHITE : MEDIC_BLACK;
        }
        if (animatable.getGruntType() == 0) TEXTURE = GASMASK;
        if (animatable.getGruntType() == 2) TEXTURE = BAL;


        RenderType rendertype = RenderType.entityCutout(TEXTURE) ;
        getRenderer().reRender(getDefaultBakedModel(animatable), poseStack, bufferSource, animatable, rendertype,
                bufferSource.getBuffer(rendertype), partialTick, packedLight, packedOverlay,
                1, 1, 1, 1);
    }



}
