package net.sirkotok.half_life_mod.entity.mob_geckolib.client.models;

import net.minecraft.resources.ResourceLocation;
import net.sirkotok.half_life_mod.HalfLifeMod;
import net.sirkotok.half_life_mod.entity.mob_geckolib.custom.Leech;
import net.sirkotok.half_life_mod.entity.mob_geckolib.custom.Shark;
import software.bernie.geckolib.model.GeoModel;

public class Shark_model extends GeoModel<Shark> {

    @Override
    public ResourceLocation getModelResource(Shark animatable) {
        return new ResourceLocation(HalfLifeMod.MOD_ID, "geo/ichthyosaur.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(Shark animatable) {
        return new ResourceLocation(HalfLifeMod.MOD_ID, "textures/entity/icht1.png");
    }


    @Override
    public ResourceLocation getAnimationResource(Shark animatable) {
        return new ResourceLocation(HalfLifeMod.MOD_ID, "animations/shark.animation.json");
    }



}
