package net.sirkotok.half_life_mod.entity.mob.mob_geckolib.client.models;

import net.minecraft.resources.ResourceLocation;
import net.sirkotok.half_life_mod.HalfLifeMod;
import net.sirkotok.half_life_mod.entity.mob.mob_geckolib.custom.Bullsquid;

import software.bernie.geckolib.model.GeoModel;


public class Bullsquid_model extends GeoModel<Bullsquid> {

    @Override
    public ResourceLocation getModelResource(Bullsquid animatable) {
        return new ResourceLocation(HalfLifeMod.MOD_ID, "geo/bullsquid_model.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(Bullsquid animatable) {
        if (animatable.gettexture() % 15 == 0) {
        return new ResourceLocation(HalfLifeMod.MOD_ID, "textures/entity/bullsquid_t2.png"); }


        if (animatable.gettexture() == 42) {
            return new ResourceLocation(HalfLifeMod.MOD_ID, "textures/entity/bullsquid_t3.png"); }

        if (animatable.gettexture() == 69) {
            return new ResourceLocation(HalfLifeMod.MOD_ID, "textures/entity/bullsquid_t4.png"); }

        if (animatable.gettexture() == 51) {
            return new ResourceLocation(HalfLifeMod.MOD_ID, "textures/entity/bullsquid_t5.png"); }

        if (animatable.gettexture() == 52 || animatable.gettexture() == 53 || animatable.gettexture() == 54) {
            return new ResourceLocation(HalfLifeMod.MOD_ID, "textures/entity/bullsquid_t6.png"); }

        return new ResourceLocation(HalfLifeMod.MOD_ID, "textures/entity/bullsquid_t1.png");
    }


    @Override
    public ResourceLocation getAnimationResource(Bullsquid animatable) {
        return new ResourceLocation(HalfLifeMod.MOD_ID, "animations/bullsquid.animation.json");
    }



}

