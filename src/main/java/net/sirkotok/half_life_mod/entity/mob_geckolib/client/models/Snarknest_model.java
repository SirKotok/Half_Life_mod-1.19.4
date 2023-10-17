package net.sirkotok.half_life_mod.entity.mob_geckolib.client.models;

import net.minecraft.resources.ResourceLocation;
import net.sirkotok.half_life_mod.HalfLifeMod;
import net.sirkotok.half_life_mod.entity.mob_geckolib.custom.Snarknest;
import software.bernie.geckolib.model.GeoModel;


public class Snarknest_model extends GeoModel<Snarknest> {

    @Override
    public ResourceLocation getModelResource(Snarknest animatable) {
        return new ResourceLocation(HalfLifeMod.MOD_ID, "geo/snarknest.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(Snarknest animatable) {
        return new ResourceLocation(HalfLifeMod.MOD_ID, "textures/entity/snarknest.png");
    }


    @Override
    public ResourceLocation getAnimationResource(Snarknest animatable) {
        return new ResourceLocation(HalfLifeMod.MOD_ID, "animations/snarknest.animation.json");
    }



}

