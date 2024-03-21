package net.sirkotok.half_life_mod.entity.mob.mob_geckolib.client.models;

import net.minecraft.resources.ResourceLocation;
import net.sirkotok.half_life_mod.HalfLifeMod;
import net.sirkotok.half_life_mod.entity.mob.mob_geckolib.custom.Pitdrone;
import software.bernie.geckolib.model.GeoModel;


public class Pitdrone_model extends GeoModel<Pitdrone> {

    @Override
    public ResourceLocation getModelResource(Pitdrone animatable) {
        return new ResourceLocation(HalfLifeMod.MOD_ID, "geo/pitdrone.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(Pitdrone animatable) {
       int i = animatable.getammo();
       return new ResourceLocation(HalfLifeMod.MOD_ID, "textures/entity/pitdrone"+i+".png");
    }


    @Override
    public ResourceLocation getAnimationResource(Pitdrone animatable) {
        return new ResourceLocation(HalfLifeMod.MOD_ID, "animations/pitdrone.animation.json");
    }



}

