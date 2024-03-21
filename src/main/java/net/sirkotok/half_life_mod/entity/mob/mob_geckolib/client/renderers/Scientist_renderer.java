package net.sirkotok.half_life_mod.entity.mob.mob_geckolib.client.renderers;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.DyeColor;
import net.sirkotok.half_life_mod.HalfLifeMod;
import net.sirkotok.half_life_mod.entity.mob.mob_geckolib.client.models.Scientist_model;
import net.sirkotok.half_life_mod.entity.mob.mob_geckolib.custom.Scientist;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.core.object.Color;
import software.bernie.geckolib.renderer.DynamicGeoEntityRenderer;

import javax.annotation.Nullable;


public class Scientist_renderer extends DynamicGeoEntityRenderer<Scientist> {
    public Scientist_renderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new Scientist_model());
    }

    private static final ResourceLocation HEAD1 =
            new ResourceLocation(HalfLifeMod.MOD_ID,"textures/entity/scientist_head1.png");
    private static final ResourceLocation HEAD2 =
           new ResourceLocation(HalfLifeMod.MOD_ID,"textures/entity/scientist_head2.png");

    private static final ResourceLocation HEAD3 =
            new ResourceLocation(HalfLifeMod.MOD_ID,"textures/entity/scientist_head3.png");
    private static final ResourceLocation HEAD4 =
            new ResourceLocation(HalfLifeMod.MOD_ID,"textures/entity/scientist_head4.png");







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





    private static final ResourceLocation HDSP =
            new ResourceLocation(HalfLifeMod.MOD_ID,"textures/entity/scientist_hdsp.png");
    private static final ResourceLocation NORMALSP =
            new ResourceLocation(HalfLifeMod.MOD_ID,"textures/entity/scientist_normalsp.png");


    @Override
    public void render(Scientist entity, float entityYaw,
                       float partialTick, PoseStack poseStack,
                       MultiBufferSource bufferSource, int packedLight) {

        poseStack.scale(1f, 1f, 1f);

        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }


    @Override
    public Color getRenderColor(Scientist animatable, float partialTick, int packedLight) {
        return super.getRenderColor(animatable, partialTick, packedLight);
    }

    @Nullable
       @Override
     protected ResourceLocation getTextureOverrideForBone(GeoBone bone, Scientist animatable, float partialTick) {
       int i = animatable.gettexture();
       int j = animatable.getShirt();
        if (bone.getName().equals("head")) {
        switch (i){
           case 1: return HEAD1;
           case 2: return HEAD2;
           case 3: return HEAD3;
           case 4: return HEAD4;
        }}

        if ((bone.getName().equals("righthand") || bone.getName().equals("lefthand")) && i == 4) {
            return HEAD4;
        }

        if (bone.getName().equals("shirt") || bone.getName().equals("leftlegleg") || bone.getName().equals("rightlegleg")) {
            return j == 0 ? NORMALSP : HDSP;
           }

           if (bone.getName().equals("coat") || bone.getName().equals("leftlegcoat") || bone.getName().equals("rightlegcoat") || bone.getName().equals("leftarmcoat") || bone.getName().equals("rightarmcoat")) {
               DyeColor color = animatable.getColor();
               int c = color.getId();
               switch(c){
                   case 0 : return WHITE_COAT0;
                   case 1 : return ORANGE_COAT1;
                   case 2 : return MAGENTA_COAT2;
                   case 3 : return LIGHTBLUE_COAT3;
                   case 4 : return YELLOW_COAT4;
                   case 5 : return LIME_COAT5;
                   case 6 : return PINK_COAT6;
                   case 7 : return GREY_COAT7;
                   case 8 : return LIGHTGREY_COAT8;
                   case 9 : return CYAN_COAT9;
                   case 10 : return PURPLE_COAT10;
                   case 11 : return BLUE_COAT11;
                   case 12 : return BROWN_COAT12;
                   case 13 : return GREEN_COAT13;
                   case 14 : return RED_COAT14;
                   case 15 : return BLACK_COAT15;
               }



           }

           return null;

     }






}
