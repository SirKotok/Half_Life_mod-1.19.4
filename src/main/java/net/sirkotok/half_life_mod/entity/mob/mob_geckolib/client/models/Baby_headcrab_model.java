package net.sirkotok.half_life_mod.entity.mob.mob_geckolib.client.models;

import net.minecraft.resources.ResourceLocation;
import net.sirkotok.half_life_mod.HalfLifeMod;
import net.sirkotok.half_life_mod.entity.mob.mob_geckolib.custom.Baby_Headcrab;
import software.bernie.geckolib.model.GeoModel;

public class Baby_headcrab_model extends GeoModel<Baby_Headcrab> {

    @Override
    public ResourceLocation getModelResource(Baby_Headcrab animatable) {
        return new ResourceLocation(HalfLifeMod.MOD_ID, "geo/babyheadcrab.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(Baby_Headcrab animatable) {
        return new ResourceLocation(HalfLifeMod.MOD_ID, "textures/entity/babyheadcrab.png");
    }


    @Override
    public ResourceLocation getAnimationResource(Baby_Headcrab animatable) {
        return new ResourceLocation(HalfLifeMod.MOD_ID, "animations/babyheadcrab.animation.json");
    }



}
