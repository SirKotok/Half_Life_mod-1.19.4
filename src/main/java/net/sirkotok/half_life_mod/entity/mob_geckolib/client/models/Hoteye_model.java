package net.sirkotok.half_life_mod.entity.mob_geckolib.client.models;

import net.minecraft.resources.ResourceLocation;
import net.sirkotok.half_life_mod.HalfLifeMod;
import net.sirkotok.half_life_mod.entity.mob_geckolib.custom.Hoteye;
import software.bernie.geckolib.model.GeoModel;


public class Hoteye_model extends GeoModel<Hoteye> {

    @Override
    public ResourceLocation getModelResource(Hoteye animatable) {
        return new ResourceLocation(HalfLifeMod.MOD_ID, "geo/hoteye.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(Hoteye dog) {

        return new ResourceLocation(HalfLifeMod.MOD_ID, "textures/entity/hoteye.png");
    }


    @Override
    public ResourceLocation getAnimationResource(Hoteye animatable) {
        return new ResourceLocation(HalfLifeMod.MOD_ID, "animations/houndeye.animation.json");
    }



}

