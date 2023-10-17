package net.sirkotok.half_life_mod.entity.mob_geckolib.client.models;

import net.minecraft.resources.ResourceLocation;
import net.sirkotok.half_life_mod.HalfLifeMod;
import net.sirkotok.half_life_mod.entity.mob_geckolib.custom.Penguin;
import software.bernie.geckolib.model.GeoModel;


public class Penguin_model extends GeoModel<Penguin> {

    @Override
    public ResourceLocation getModelResource(Penguin animatable) {
        return new ResourceLocation(HalfLifeMod.MOD_ID, "geo/penguin.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(Penguin animatable) {
        return new ResourceLocation(HalfLifeMod.MOD_ID, "textures/entity/penguin.png");
    }


    @Override
    public ResourceLocation getAnimationResource(Penguin animatable) {
        return new ResourceLocation(HalfLifeMod.MOD_ID, "animations/snark.animation.json");
    }



}

