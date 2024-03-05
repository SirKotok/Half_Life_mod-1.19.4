package net.sirkotok.half_life_mod.entity.mob_geckolib.client.renderers.renderlayer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.sirkotok.half_life_mod.HalfLifeMod;
import net.sirkotok.half_life_mod.entity.mob_geckolib.custom.Scientist;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoRenderer;
import software.bernie.geckolib.renderer.layer.GeoRenderLayer;

public class ScientistRenderPantsLayer extends GeoRenderLayer<Scientist> {

    public ScientistRenderPantsLayer(GeoRenderer<Scientist> entityRenderer) {
        super(entityRenderer);
    }



    private static final ResourceLocation HDSP =
            new ResourceLocation(HalfLifeMod.MOD_ID,"textures/entity/scientist_hdsp.png");
    private static final ResourceLocation NORMALSP =
            new ResourceLocation(HalfLifeMod.MOD_ID,"textures/entity/scientist_normalsp.png");


    @Override
    public void render(PoseStack poseStack, Scientist animatable, BakedGeoModel bakedModel, RenderType renderType, MultiBufferSource bufferSource, VertexConsumer buffer, float partialTick, int packedLight, int packedOverlay) {

        ResourceLocation TEXTURE = NORMALSP;
        if (animatable.getShirt() != 0) TEXTURE = HDSP;


        RenderType armorRenderType = RenderType.entityCutout(TEXTURE) ;

        getRenderer().reRender(getDefaultBakedModel(animatable), poseStack, bufferSource, animatable, armorRenderType,
                bufferSource.getBuffer(armorRenderType), partialTick, packedLight, packedOverlay,
                1, 1, 1, 1);
    }





}
