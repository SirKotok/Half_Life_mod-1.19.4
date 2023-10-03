package net.sirkotok.half_life_mod.entity.mob.client.models;

import net.minecraft.resources.ResourceLocation;
import net.sirkotok.half_life_mod.HalfLifeMod;

import net.sirkotok.half_life_mod.entity.mob.custom.Headcrab_2;
import software.bernie.geckolib.model.GeoModel;


public class Headcrab_m2 extends GeoModel<Headcrab_2> {

    @Override
    public ResourceLocation getModelResource(Headcrab_2 animatable) {
        return new ResourceLocation(HalfLifeMod.MOD_ID, "geo/headcrab_model_2.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(Headcrab_2 animatable) {
        return new ResourceLocation(HalfLifeMod.MOD_ID, "textures/entity/headcrab_m2_t1.png");
    }


    @Override
    public ResourceLocation getAnimationResource(Headcrab_2 animatable) {
        return new ResourceLocation(HalfLifeMod.MOD_ID, "animations/headcrab_m1.animation.json");
    }



}

