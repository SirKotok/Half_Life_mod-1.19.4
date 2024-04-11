package net.sirkotok.half_life_mod.entity.mob.mob_geckolib.client.renderers.renderlayer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.core.UUIDUtil;
import net.minecraft.resources.ResourceLocation;
import net.sirkotok.half_life_mod.HalfLifeMod;
import net.sirkotok.half_life_mod.entity.mob.mob_geckolib.custom.ZombieHl1;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoRenderer;
import software.bernie.geckolib.renderer.layer.GeoRenderLayer;

public class ZombieScientistRenderBaseLayer extends GeoRenderLayer<ZombieHl1> {

    public ZombieScientistRenderBaseLayer(GeoRenderer<ZombieHl1> entityRenderer) {
        super(entityRenderer);
    }





    private static final ResourceLocation SCIENTIST =
            new ResourceLocation(HalfLifeMod.MOD_ID,"textures/entity/scientistbody_nosyringe.png");

    private static final ResourceLocation BARNEY =
            new ResourceLocation(HalfLifeMod.MOD_ID,"textures/entity/barney_nogun.png");






    @Override
    public void render(PoseStack poseStack, ZombieHl1 animatable, BakedGeoModel bakedModel, RenderType renderType, MultiBufferSource bufferSource, VertexConsumer buffer, float partialTick, int packedLight, int packedOverlay) {


        ResourceLocation TEXTURE = SCIENTIST;
        int i = animatable.gettexture();
        if (i == -5) TEXTURE = BARNEY;
        else if (animatable.getprofile() != null) {
            TEXTURE = DefaultPlayerSkin.getDefaultSkin(UUIDUtil.getOrCreatePlayerUUID(animatable.getprofile()));
        }

            RenderType armorRenderType = RenderType.entityCutout(TEXTURE);
        getRenderer().reRender(getDefaultBakedModel(animatable), poseStack, bufferSource, animatable, armorRenderType,
                bufferSource.getBuffer(armorRenderType), partialTick, packedLight, packedOverlay,
                1, 1, 1, 1);

    }

}
