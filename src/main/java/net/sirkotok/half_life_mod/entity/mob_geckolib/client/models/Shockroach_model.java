package net.sirkotok.half_life_mod.entity.mob_geckolib.client.models;

import net.minecraft.resources.ResourceLocation;
import net.sirkotok.half_life_mod.HalfLifeMod;
import net.sirkotok.half_life_mod.entity.mob_geckolib.custom.Shockroach;
import software.bernie.geckolib.model.GeoModel;

public class Shockroach_model extends GeoModel<Shockroach> {

    @Override
    public ResourceLocation getModelResource(Shockroach animatable) {
        return new ResourceLocation(HalfLifeMod.MOD_ID, "geo/shockroach.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(Shockroach animatable) {
        return new ResourceLocation(HalfLifeMod.MOD_ID, "textures/entity/shockroach.png");
    }


    @Override
    public ResourceLocation getAnimationResource(Shockroach animatable) {
        return new ResourceLocation(HalfLifeMod.MOD_ID, "animations/shockroach.animation.json");
    }



}
