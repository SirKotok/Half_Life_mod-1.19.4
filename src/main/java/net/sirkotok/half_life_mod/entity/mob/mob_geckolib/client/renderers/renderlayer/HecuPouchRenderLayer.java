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

public class HecuPouchRenderLayer extends GeoRenderLayer<HecuGrunt> {

    public HecuPouchRenderLayer(GeoRenderer<HecuGrunt> entityRenderer) {
        super(entityRenderer);
    }


    private static final ResourceLocation NORMAL =
            new ResourceLocation(HalfLifeMod.MOD_ID,"textures/entity/hecu/hecu_puchbase.png");
    private static final ResourceLocation DECAY =
            new ResourceLocation(HalfLifeMod.MOD_ID,"textures/entity/hecu/hecu_puchdecay.png");
    private static final ResourceLocation FOUR =
            new ResourceLocation(HalfLifeMod.MOD_ID,"textures/entity/hecu/hecu_puch4.png");
    private static final ResourceLocation VENT =
            new ResourceLocation(HalfLifeMod.MOD_ID,"textures/entity/hecu/hecu_puch3.png");
    private static final ResourceLocation MEDBAY =
            new ResourceLocation(HalfLifeMod.MOD_ID,"textures/entity/hecu/hecu_puchmedbay.png");





    @Override
    public void render(PoseStack poseStack, HecuGrunt animatable, BakedGeoModel bakedModel, RenderType renderType, MultiBufferSource bufferSource, VertexConsumer buffer, float partialTick, int packedLight, int packedOverlay) {

        ResourceLocation TEXTURE = NORMAL;
        switch (animatable.getPouch()) {
            case 1: TEXTURE = FOUR; break;
            case 2: TEXTURE = DECAY; break;
        }
        if (animatable.isMedic()) {
            TEXTURE = MEDBAY;
        }
        if (animatable.isVent()) TEXTURE = VENT;



        RenderType rendertype = RenderType.entityCutout(TEXTURE) ;
        getRenderer().reRender(getDefaultBakedModel(animatable), poseStack, bufferSource, animatable, rendertype,
                bufferSource.getBuffer(rendertype), partialTick, packedLight, packedOverlay,
                1, 1, 1, 1);
    }



}
