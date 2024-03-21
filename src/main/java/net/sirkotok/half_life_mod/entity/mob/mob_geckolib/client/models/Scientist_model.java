package net.sirkotok.half_life_mod.entity.mob.mob_geckolib.client.models;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.sirkotok.half_life_mod.HalfLifeMod;
import net.sirkotok.half_life_mod.entity.mob.mob_geckolib.custom.Scientist;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;


public class Scientist_model extends GeoModel<Scientist> {

    @Override
    public ResourceLocation getModelResource(Scientist animatable) {
        return new ResourceLocation(HalfLifeMod.MOD_ID, "geo/scientist.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(Scientist animatable) {
        return new ResourceLocation(HalfLifeMod.MOD_ID, "textures/entity/scientist_body1.png");
    }


    @Override
    public ResourceLocation getAnimationResource(Scientist animatable) {
        return new ResourceLocation(HalfLifeMod.MOD_ID, "animations/scientist.animation.json");
    }

    @Override
    public void setCustomAnimations(Scientist animatable, long instanceId, AnimationState<Scientist> animationState) {
        if (animatable.hlisangry()) return;
        CoreGeoBone head = getAnimationProcessor().getBone("head");
        if (head != null) {
            EntityModelData entityData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);

            head.setRotX(entityData.headPitch() * Mth.DEG_TO_RAD);
            head.setRotY(entityData.netHeadYaw() * Mth.DEG_TO_RAD);
        }
    }
}

