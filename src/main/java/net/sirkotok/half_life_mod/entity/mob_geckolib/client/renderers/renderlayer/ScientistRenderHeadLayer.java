package net.sirkotok.half_life_mod.entity.mob_geckolib.client.renderers.renderlayer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.sirkotok.half_life_mod.HalfLifeMod;
import net.sirkotok.half_life_mod.entity.mob_geckolib.custom.Scientist;
import software.bernie.example.entity.CoolKidEntity;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoRenderer;
import software.bernie.geckolib.renderer.layer.GeoRenderLayer;

public class ScientistRenderHeadLayer extends GeoRenderLayer<Scientist> {

    public ScientistRenderHeadLayer(GeoRenderer<Scientist> entityRenderer) {
        super(entityRenderer);
    }


    private static final ResourceLocation HEAD1 =
            new ResourceLocation(HalfLifeMod.MOD_ID,"textures/entity/scientist_head1.png");
    private static final ResourceLocation HEAD2 =
            new ResourceLocation(HalfLifeMod.MOD_ID,"textures/entity/scientist_head2.png");

    private static final ResourceLocation HEAD3 =
            new ResourceLocation(HalfLifeMod.MOD_ID,"textures/entity/scientist_head3.png");
    private static final ResourceLocation HEAD4 =
            new ResourceLocation(HalfLifeMod.MOD_ID,"textures/entity/scientist_head4.png");



    @Override
    public void render(PoseStack poseStack, Scientist animatable, BakedGeoModel bakedModel, RenderType renderType, MultiBufferSource bufferSource, VertexConsumer buffer, float partialTick, int packedLight, int packedOverlay) {
        int i = 0;
        ResourceLocation TEXTURE = HEAD1;
        switch(animatable.gettexture()) {
            case 1: break;
            case 2: TEXTURE = HEAD2; break;
            case 3: TEXTURE = HEAD3; break;
            case 4: TEXTURE = HEAD4; break;
        }

        RenderType armorRenderType = RenderType.entityCutout(TEXTURE) ;

        getRenderer().reRender(getDefaultBakedModel(animatable), poseStack, bufferSource, animatable, armorRenderType,
                bufferSource.getBuffer(armorRenderType), partialTick, packedLight, packedOverlay,
                1, 1, 1, 1);
    }



}
