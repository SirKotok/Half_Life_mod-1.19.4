package net.sirkotok.half_life_mod.entity.mob.client.models;

import net.minecraft.resources.ResourceLocation;
import net.sirkotok.half_life_mod.HalfLifeMod;
import net.sirkotok.half_life_mod.entity.mob.custom.Barney;
import software.bernie.geckolib.model.GeoModel;


public class Barney_model extends GeoModel<Barney> {

    @Override
    public ResourceLocation getModelResource(Barney animatable) {
      //  return new ResourceLocation(HalfLifeMod.MOD_ID, "geo/human_model.geo.json");
        return new ResourceLocation(HalfLifeMod.MOD_ID, "geo/guard_model.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(Barney animatable) {
        return new ResourceLocation(HalfLifeMod.MOD_ID, "textures/entity/barney_with_armor.png");
        //  return new ResourceLocation(HalfLifeMod.MOD_ID, "textures/entity/barney.png");
    }


    @Override
    public ResourceLocation getAnimationResource(Barney animatable) {
        return new ResourceLocation(HalfLifeMod.MOD_ID, "animations/human_model.animation.json");
    }



}

