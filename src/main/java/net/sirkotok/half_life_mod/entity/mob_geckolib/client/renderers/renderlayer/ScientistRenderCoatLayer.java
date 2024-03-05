package net.sirkotok.half_life_mod.entity.mob_geckolib.client.renderers.renderlayer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.sirkotok.half_life_mod.HalfLifeMod;
import net.sirkotok.half_life_mod.entity.mob_geckolib.custom.Scientist;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoRenderer;
import software.bernie.geckolib.renderer.layer.GeoRenderLayer;

public class ScientistRenderCoatLayer extends GeoRenderLayer<Scientist> {

    public ScientistRenderCoatLayer(GeoRenderer<Scientist> entityRenderer) {
        super(entityRenderer);
    }





    private static final ResourceLocation WHITE_COAT0 =
            new ResourceLocation(HalfLifeMod.MOD_ID,"textures/entity/scientist_coat_white.png");


    private static final ResourceLocation LIGHTBLUE_COAT3 =
            new ResourceLocation(HalfLifeMod.MOD_ID,"textures/entity/scientist_coat_lightblue.png");
    private static final ResourceLocation ORANGE_COAT1 =
            new ResourceLocation(HalfLifeMod.MOD_ID,"textures/entity/scientist_coat_orange.png");
    private static final ResourceLocation MAGENTA_COAT2 =
            new ResourceLocation(HalfLifeMod.MOD_ID,"textures/entity/scientist_coat_magenta.png");

    private static final ResourceLocation YELLOW_COAT4 =
            new ResourceLocation(HalfLifeMod.MOD_ID,"textures/entity/scientist_coat_yellow.png");
    private static final ResourceLocation LIME_COAT5 =
            new ResourceLocation(HalfLifeMod.MOD_ID,"textures/entity/scientist_coat_lime.png");
    private static final ResourceLocation PINK_COAT6 =
            new ResourceLocation(HalfLifeMod.MOD_ID,"textures/entity/scientist_coat_pink.png");
    private static final ResourceLocation GREY_COAT7 =
            new ResourceLocation(HalfLifeMod.MOD_ID,"textures/entity/scientist_coat_grey.png");
    private static final ResourceLocation LIGHTGREY_COAT8 =
            new ResourceLocation(HalfLifeMod.MOD_ID,"textures/entity/scientist_coat_lightgrey.png");

    private static final ResourceLocation PURPLE_COAT10 =
            new ResourceLocation(HalfLifeMod.MOD_ID,"textures/entity/scientist_coat_purple.png");

    private static final ResourceLocation CYAN_COAT9 =
            new ResourceLocation(HalfLifeMod.MOD_ID,"textures/entity/scientist_coat_cyan.png");

    private static final ResourceLocation BLUE_COAT11 =
            new ResourceLocation(HalfLifeMod.MOD_ID,"textures/entity/scientist_coat_blue.png");

    private static final ResourceLocation BROWN_COAT12 =
            new ResourceLocation(HalfLifeMod.MOD_ID,"textures/entity/scientist_coat_brown.png");
    private static final ResourceLocation GREEN_COAT13 =
            new ResourceLocation(HalfLifeMod.MOD_ID,"textures/entity/scientist_coat_green.png");

    private static final ResourceLocation RED_COAT14 =
            new ResourceLocation(HalfLifeMod.MOD_ID,"textures/entity/scientist_coat_red.png");

    private static final ResourceLocation BLACK_COAT15 =
            new ResourceLocation(HalfLifeMod.MOD_ID,"textures/entity/scientist_coat_black.png");



    public void preRender(PoseStack poseStack, Scientist animatable, BakedGeoModel bakedModel, RenderType renderType,
                          MultiBufferSource bufferSource, VertexConsumer buffer, float partialTick,
                          int packedLight, int packedOverlay) {

    }


    @Override
    public void render(PoseStack poseStack, Scientist animatable, BakedGeoModel bakedModel, RenderType renderType, MultiBufferSource bufferSource, VertexConsumer buffer, float partialTick, int packedLight, int packedOverlay) {
        int i = 0;
        ResourceLocation TEXTURE = WHITE_COAT0;
        switch(animatable.getColor().getId()) {
            case 0: break;
            case 1: TEXTURE = ORANGE_COAT1; break;
            case 2: TEXTURE = MAGENTA_COAT2; break;
            case 3: TEXTURE = LIGHTBLUE_COAT3; break;
            case 4: TEXTURE = YELLOW_COAT4; break;
            case 5: TEXTURE = LIME_COAT5; break;
            case 6: TEXTURE = PINK_COAT6; break;
            case 7: TEXTURE = GREY_COAT7; break;
            case 8: TEXTURE = LIGHTGREY_COAT8; break;
            case 9: TEXTURE = CYAN_COAT9; break;
            case 10: TEXTURE = PURPLE_COAT10; break;
            case 11: TEXTURE = BLUE_COAT11; break;
            case 12: TEXTURE = BROWN_COAT12; break;
            case 13: TEXTURE = GREEN_COAT13; break;
            case 14: TEXTURE = RED_COAT14; break;
            case 15: TEXTURE = BLACK_COAT15; break;
        }

        RenderType armorRenderType = RenderType.entityCutout(TEXTURE) ;

        getRenderer().reRender(getDefaultBakedModel(animatable), poseStack, bufferSource, animatable, armorRenderType,
                bufferSource.getBuffer(armorRenderType), partialTick, packedLight, packedOverlay,
                1, 1, 1, 1);


    }




}
