package net.sirkotok.half_life_mod.entity.mob_geckolib.client.models;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.sirkotok.half_life_mod.HalfLifeMod;
import net.sirkotok.half_life_mod.entity.mob_geckolib.custom.Hunter;
import net.sirkotok.half_life_mod.entity.mob_geckolib.custom.Manhack;
import net.sirkotok.half_life_mod.entity.mob_geckolib.custom.Voltigore;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;


public class Hunter_model extends GeoModel<Hunter> {

    @Override
    public ResourceLocation getModelResource(Hunter animatable) {
        return new ResourceLocation(HalfLifeMod.MOD_ID, "geo/hunter.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(Hunter animatable) {
        return new ResourceLocation(HalfLifeMod.MOD_ID, "textures/entity/hunter.png");
    }


    @Override
    public ResourceLocation getAnimationResource(Hunter animatable) {
        return new ResourceLocation(HalfLifeMod.MOD_ID, "animations/hunter.animation.json");
    }

    /*
    @Override
    public void setCustomAnimations(Hunter animatable, long instanceId, AnimationState<Hunter> animationState) {

        CoreGeoBone eye10 = getAnimationProcessor().getBone("eye10");
        CoreGeoBone eye11 = getAnimationProcessor().getBone("eye11");
        CoreGeoBone eye12 = getAnimationProcessor().getBone("eye12");
        CoreGeoBone eye20 = getAnimationProcessor().getBone("eye20");
        CoreGeoBone eye21 = getAnimationProcessor().getBone("eye21");
        CoreGeoBone eye22 = getAnimationProcessor().getBone("eye22");
        if (animatable.eye1()){
        if (eye10 != null) {
            EntityModelData entityData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);
            eye10.setRotX(entityData.headPitch()/5 * Mth.DEG_TO_RAD);
            eye10.setRotY(entityData.netHeadYaw()/5 * Mth.DEG_TO_RAD);
        }
        if (eye11 != null) {
            EntityModelData entityData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);
            eye11.setRotX(entityData.headPitch()/2 * Mth.DEG_TO_RAD);
            eye11.setRotY(entityData.netHeadYaw()/2 * Mth.DEG_TO_RAD);
        }

        if (eye12 != null) {
            EntityModelData entityData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);
            eye12.setRotX(entityData.headPitch() * Mth.DEG_TO_RAD);
            eye12.setRotY(entityData.netHeadYaw() * Mth.DEG_TO_RAD);
        }}
        if (animatable.eye2()) {
        if (eye20 != null) {
            EntityModelData entityData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);
            eye20.setRotX(entityData.headPitch()/5 * Mth.DEG_TO_RAD);
            eye20.setRotY(entityData.netHeadYaw()/5 * Mth.DEG_TO_RAD);
        }

        if (eye21 != null) {
            EntityModelData entityData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);
            eye21.setRotX(entityData.headPitch()/2 * Mth.DEG_TO_RAD);
            eye21.setRotY(entityData.netHeadYaw()/2 * Mth.DEG_TO_RAD);
        }
        if (eye22 != null) {
            EntityModelData entityData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);
            eye22.setRotX(entityData.headPitch() * Mth.DEG_TO_RAD);
            eye22.setRotY(entityData.netHeadYaw() * Mth.DEG_TO_RAD);
        }}


    } */







}

