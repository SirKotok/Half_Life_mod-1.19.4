package net.sirkotok.half_life_mod.entity.mob.client.models;

import net.minecraft.resources.ResourceLocation;
import net.sirkotok.half_life_mod.HalfLifeMod;
import net.sirkotok.half_life_mod.entity.mob.custom.Chumtoad;
import net.sirkotok.half_life_mod.entity.mob.custom.Headcrab_1;
import software.bernie.geckolib.model.GeoModel;


public class Chumtoad_model extends GeoModel<Chumtoad> {

    @Override
    public ResourceLocation getModelResource(Chumtoad animatable) {
        return new ResourceLocation(HalfLifeMod.MOD_ID, "geo/chumtoad.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(Chumtoad animatable) {
        return new ResourceLocation(HalfLifeMod.MOD_ID, "textures/entity/chumtoad.png");
    }


    @Override
    public ResourceLocation getAnimationResource(Chumtoad animatable) {
        return new ResourceLocation(HalfLifeMod.MOD_ID, "animations/chumtoad.animation.json");
    }



}

