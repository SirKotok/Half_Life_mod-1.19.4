package net.sirkotok.half_life_mod.entity.mob_geckolib.client.models;

import net.minecraft.resources.ResourceLocation;
import net.sirkotok.half_life_mod.HalfLifeMod;
import net.sirkotok.half_life_mod.entity.mob_geckolib.custom.Headcrab_1;
import net.sirkotok.half_life_mod.entity.mob_geckolib.custom.Pzombie;
import software.bernie.geckolib.model.GeoModel;


public class PZombie_model extends GeoModel<Pzombie> {

    @Override
    public ResourceLocation getModelResource(Pzombie animatable) {
        return new ResourceLocation(HalfLifeMod.MOD_ID, "geo/pzombie.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(Pzombie animatable) {
        return new ResourceLocation(HalfLifeMod.MOD_ID, "textures/entity/pzombie.png");
    }


    @Override
    public ResourceLocation getAnimationResource(Pzombie animatable) {
        return new ResourceLocation(HalfLifeMod.MOD_ID, "animations/pzombie.animation.json");
    }



}

