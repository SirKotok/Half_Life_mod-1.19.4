package net.sirkotok.half_life_mod.entity.mob_geckolib.client.models;

import net.minecraft.resources.ResourceLocation;
import net.sirkotok.half_life_mod.HalfLifeMod;
import net.sirkotok.half_life_mod.entity.mob_geckolib.custom.Cockroach;
import net.sirkotok.half_life_mod.entity.mob_geckolib.custom.Gonarch;
import software.bernie.geckolib.model.GeoModel;

public class Gonarch_model extends GeoModel<Gonarch> {

    @Override
    public ResourceLocation getModelResource(Gonarch animatable) {
        return new ResourceLocation(HalfLifeMod.MOD_ID, "geo/bigmomma.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(Gonarch animatable) {
        return new ResourceLocation(HalfLifeMod.MOD_ID, "textures/entity/bigmomma.png");
    }


    @Override
    public ResourceLocation getAnimationResource(Gonarch animatable) {
        return new ResourceLocation(HalfLifeMod.MOD_ID, "animations/mom.animation.json");
    }



}
