package net.sirkotok.half_life_mod.entity.mob.mob_geckolib.client.renderers.renderlayer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.sirkotok.half_life_mod.HalfLifeMod;
import net.sirkotok.half_life_mod.entity.mob.mob_geckolib.custom.Gargantua;
import net.sirkotok.half_life_mod.entity.mob.mob_geckolib.custom.HecuGrunt;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoRenderer;
import software.bernie.geckolib.renderer.layer.GeoRenderLayer;

public class HecuHelmetRenderLayer extends GeoRenderLayer<HecuGrunt> {

    public HecuHelmetRenderLayer(GeoRenderer<HecuGrunt> entityRenderer) {
        super(entityRenderer);
    }


    private static final ResourceLocation HELM1 =
            new ResourceLocation(HalfLifeMod.MOD_ID,"textures/entity/hecu/hecu_helmet1.png");
    private static final ResourceLocation HELM2 =
            new ResourceLocation(HalfLifeMod.MOD_ID,"textures/entity/hecu/hecu_helmet2.png");
    private static final ResourceLocation HELM_MEDBAY =
            new ResourceLocation(HalfLifeMod.MOD_ID,"textures/entity/hecu/hecu_helmet_medic.png");
    private static final ResourceLocation RED =
            new ResourceLocation(HalfLifeMod.MOD_ID,"textures/entity/hecu/hecu_red_beret.png");
    private static final ResourceLocation GREEN =
            new ResourceLocation(HalfLifeMod.MOD_ID,"textures/entity/hecu/hecu_green_beret.png");
    private static final ResourceLocation BLACK =
            new ResourceLocation(HalfLifeMod.MOD_ID,"textures/entity/hecu/hecu_black_beret.png");
    private static final ResourceLocation BANDANA =
            new ResourceLocation(HalfLifeMod.MOD_ID,"textures/entity/hecu/hecu_bandana.png");




    @Override
    public void render(PoseStack poseStack, HecuGrunt animatable, BakedGeoModel bakedModel, RenderType renderType, MultiBufferSource bufferSource, VertexConsumer buffer, float partialTick, int packedLight, int packedOverlay) {

        if (animatable.isBalaclava()) return;

        ResourceLocation TEXTURE = HELM1;

        switch(animatable.getHelmet()) {
            case 1: TEXTURE = HELM2; break;
            case 2: TEXTURE = RED; break;
            case 3: TEXTURE = GREEN; break;
            case 4: TEXTURE = BLACK; break;
        }

        if (animatable.isMedic()) {
            TEXTURE = HELM_MEDBAY;
        }
        if (animatable.isBandana()) TEXTURE = BANDANA;



        RenderType rendertype = RenderType.entityCutout(TEXTURE) ;
        getRenderer().reRender(getDefaultBakedModel(animatable), poseStack, bufferSource, animatable, rendertype,
                bufferSource.getBuffer(rendertype), partialTick, packedLight, packedOverlay,
                1, 1, 1, 1);
    }



}
