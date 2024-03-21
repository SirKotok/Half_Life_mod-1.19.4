package net.sirkotok.half_life_mod.entity.mob.mob_geckolib.client.models;

import net.minecraft.resources.ResourceLocation;
import net.sirkotok.half_life_mod.HalfLifeMod;
import net.sirkotok.half_life_mod.entity.mob.mob_geckolib.custom.Cockroach;
import software.bernie.geckolib.model.GeoModel;

public class Cockroach_model extends GeoModel<Cockroach> {

    @Override
    public ResourceLocation getModelResource(Cockroach animatable) {
        return new ResourceLocation(HalfLifeMod.MOD_ID, "geo/roach.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(Cockroach animatable) {
        return new ResourceLocation(HalfLifeMod.MOD_ID, "textures/entity/roach.png");
    }


    @Override
    public ResourceLocation getAnimationResource(Cockroach animatable) {
        return new ResourceLocation(HalfLifeMod.MOD_ID, "animations/cockroach.animation.json");
    }



}
