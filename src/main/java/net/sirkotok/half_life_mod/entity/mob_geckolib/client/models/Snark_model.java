package net.sirkotok.half_life_mod.entity.mob_geckolib.client.models;

import net.minecraft.resources.ResourceLocation;
import net.sirkotok.half_life_mod.HalfLifeMod;
import net.sirkotok.half_life_mod.entity.mob_geckolib.custom.Snark;
import software.bernie.geckolib.model.GeoModel;


public class Snark_model extends GeoModel<Snark> {

    @Override
    public ResourceLocation getModelResource(Snark animatable) {
        return new ResourceLocation(HalfLifeMod.MOD_ID, "geo/snark.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(Snark animatable) {
        return new ResourceLocation(HalfLifeMod.MOD_ID, "textures/entity/snark.png");
    }


    @Override
    public ResourceLocation getAnimationResource(Snark animatable) {
        return new ResourceLocation(HalfLifeMod.MOD_ID, "animations/snark.animation.json");
    }



}

