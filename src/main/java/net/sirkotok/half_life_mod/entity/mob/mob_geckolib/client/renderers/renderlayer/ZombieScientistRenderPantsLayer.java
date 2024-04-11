package net.sirkotok.half_life_mod.entity.mob.mob_geckolib.client.renderers.renderlayer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.sirkotok.half_life_mod.HalfLifeMod;
import net.sirkotok.half_life_mod.entity.mob.mob_geckolib.custom.Scientist;
import net.sirkotok.half_life_mod.entity.mob.mob_geckolib.custom.ZombieHl1;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoRenderer;
import software.bernie.geckolib.renderer.layer.GeoRenderLayer;

public class ZombieScientistRenderPantsLayer extends GeoRenderLayer<ZombieHl1> {

    public ZombieScientistRenderPantsLayer(GeoRenderer<ZombieHl1> entityRenderer) {
        super(entityRenderer);
    }



    private static final ResourceLocation HDSP =
            new ResourceLocation(HalfLifeMod.MOD_ID,"textures/entity/scientist_hdsp.png");
    private static final ResourceLocation NORMALSP =
            new ResourceLocation(HalfLifeMod.MOD_ID,"textures/entity/scientist_normalsp.png");




    @Override
    public void render(PoseStack poseStack, ZombieHl1 animatable, BakedGeoModel bakedModel, RenderType renderType, MultiBufferSource bufferSource, VertexConsumer buffer, float partialTick, int packedLight, int packedOverlay) {


        int i = animatable.gettexture();
        if (i == -5 || animatable.getprofile() != null) {
            return;
        }

        ResourceLocation TEXTURE = NORMALSP;
        if (animatable.getShirt() != 0) TEXTURE = HDSP;


        RenderType armorRenderType = RenderType.entityTranslucent(TEXTURE) ;

        getRenderer().reRender(getDefaultBakedModel(animatable), poseStack, bufferSource, animatable, armorRenderType,
                bufferSource.getBuffer(armorRenderType), partialTick, packedLight, packedOverlay,
                1, 1, 1, 1);
    }





}
