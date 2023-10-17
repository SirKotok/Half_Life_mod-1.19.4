package net.sirkotok.half_life_mod.entity.mob_geckolib.client.models;

import net.minecraft.resources.ResourceLocation;

import net.sirkotok.half_life_mod.HalfLifeMod;
import net.sirkotok.half_life_mod.entity.mob_geckolib.custom.Headcrab_1;


import software.bernie.geckolib.model.GeoModel;


public class Headcrab_m1 extends GeoModel<Headcrab_1> {

    @Override
    public ResourceLocation getModelResource(Headcrab_1 animatable) {
        return new ResourceLocation(HalfLifeMod.MOD_ID, "geo/headcrab_model_1.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(Headcrab_1 animatable) {
        return new ResourceLocation(HalfLifeMod.MOD_ID, "textures/entity/headcrab_m1_t1.png");
    }


    @Override
    public ResourceLocation getAnimationResource(Headcrab_1 animatable) {
        return new ResourceLocation(HalfLifeMod.MOD_ID, "animations/headcrab_m1.animation.json");
    }



}

