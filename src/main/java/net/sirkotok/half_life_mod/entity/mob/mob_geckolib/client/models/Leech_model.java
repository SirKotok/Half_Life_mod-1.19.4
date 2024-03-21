package net.sirkotok.half_life_mod.entity.mob.mob_geckolib.client.models;

import net.minecraft.resources.ResourceLocation;
import net.sirkotok.half_life_mod.HalfLifeMod;
import net.sirkotok.half_life_mod.entity.mob.mob_geckolib.custom.Leech;
import software.bernie.geckolib.model.GeoModel;

public class Leech_model extends GeoModel<Leech> {

    @Override
    public ResourceLocation getModelResource(Leech animatable) {
        return new ResourceLocation(HalfLifeMod.MOD_ID, "geo/leech.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(Leech animatable) {
        return new ResourceLocation(HalfLifeMod.MOD_ID, "textures/entity/leech.png");
    }


    @Override
    public ResourceLocation getAnimationResource(Leech animatable) {
        return new ResourceLocation(HalfLifeMod.MOD_ID, "animations/leech.animation.json");
    }



}
