package net.sirkotok.half_life_mod.entity.mob.mob_geckolib.client.models;

import net.minecraft.resources.ResourceLocation;
import net.sirkotok.half_life_mod.HalfLifeMod;
import net.sirkotok.half_life_mod.entity.mob.mob_geckolib.custom.Headcrab_Fast;
import software.bernie.geckolib.model.GeoModel;


public class Headcrab_fast_model extends GeoModel<Headcrab_Fast> {

    @Override
    public ResourceLocation getModelResource(Headcrab_Fast animatable) {
        return new ResourceLocation(HalfLifeMod.MOD_ID, "geo/headcrab_fast_model.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(Headcrab_Fast animatable) {
        return new ResourceLocation(HalfLifeMod.MOD_ID, "textures/entity/fast_headcrab_texture.png");
    }


    @Override
    public ResourceLocation getAnimationResource(Headcrab_Fast animatable) {
        return new ResourceLocation(HalfLifeMod.MOD_ID, "animations/fastheadcrab.animation.json");
    }



}

