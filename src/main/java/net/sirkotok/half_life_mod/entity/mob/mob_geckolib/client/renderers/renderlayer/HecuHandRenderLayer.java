package net.sirkotok.half_life_mod.entity.mob.mob_geckolib.client.renderers.renderlayer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.sirkotok.half_life_mod.HalfLifeMod;
import net.sirkotok.half_life_mod.entity.mob.mob_geckolib.custom.HecuGrunt;
import net.sirkotok.half_life_mod.entity.mob.mob_geckolib.custom.Scientist;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoRenderer;
import software.bernie.geckolib.renderer.layer.GeoRenderLayer;

public class HecuHandRenderLayer extends GeoRenderLayer<HecuGrunt> {

    public HecuHandRenderLayer(GeoRenderer<HecuGrunt> entityRenderer) {
        super(entityRenderer);
    }


    private static final ResourceLocation SKIN1 =
            new ResourceLocation(HalfLifeMod.MOD_ID,"textures/entity/hecu/hecu_handwhite.png");
    private static final ResourceLocation SKIN2 =
            new ResourceLocation(HalfLifeMod.MOD_ID,"textures/entity/hecu/hecu_handblack.png");





    @Override
    public void render(PoseStack poseStack, HecuGrunt animatable, BakedGeoModel bakedModel, RenderType renderType, MultiBufferSource bufferSource, VertexConsumer buffer, float partialTick, int packedLight, int packedOverlay) {

        ResourceLocation TEXTURE = SKIN1;
        if (!animatable.getSkin()) TEXTURE = SKIN2;

        RenderType rendertype = RenderType.entityCutout(TEXTURE) ;

        getRenderer().reRender(getDefaultBakedModel(animatable), poseStack, bufferSource, animatable, rendertype,
                bufferSource.getBuffer(rendertype), partialTick, packedLight, packedOverlay,
                1, 1, 1, 1);
    }



}
