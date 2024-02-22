package net.sirkotok.half_life_mod.entity.mob_geckolib.client.models;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.sirkotok.half_life_mod.HalfLifeMod;
import net.sirkotok.half_life_mod.entity.mob_geckolib.custom.Antlion;
import net.sirkotok.half_life_mod.entity.mob_geckolib.custom.Barney;
import net.sirkotok.half_life_mod.entity.mob_geckolib.custom.Manhack;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;


public class Antlion_model extends GeoModel<Antlion> {

    @Override
    public ResourceLocation getModelResource(Antlion animatable) {
        return new ResourceLocation(HalfLifeMod.MOD_ID, "geo/antlion.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(Antlion animatable) {
        int i = animatable.gettexture();
        return new ResourceLocation(HalfLifeMod.MOD_ID, "textures/entity/antlion" + i + ".png");
    }


    @Override
    public ResourceLocation getAnimationResource(Antlion animatable) {
        return new ResourceLocation(HalfLifeMod.MOD_ID, "animations/antlion.animation.json");
    }

    @Override
    public RenderType getRenderType(Antlion animatable, ResourceLocation texture) {
        return RenderType.entityTranslucent(texture);
    }

    @Override
    public void setCustomAnimations(Antlion animatable, long instanceId, AnimationState<Antlion> animationState) {

        CoreGeoBone head = getAnimationProcessor().getBone("head");
        if (head != null) {
            EntityModelData entityData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);

            head.setRotX(entityData.headPitch() * Mth.DEG_TO_RAD);
            head.setRotY(entityData.netHeadYaw() * Mth.DEG_TO_RAD);
        }
    }




}

