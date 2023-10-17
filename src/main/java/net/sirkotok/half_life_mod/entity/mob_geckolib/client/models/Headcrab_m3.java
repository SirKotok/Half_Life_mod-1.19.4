package net.sirkotok.half_life_mod.entity.mob_geckolib.client.models;

import net.minecraft.resources.ResourceLocation;
import net.sirkotok.half_life_mod.HalfLifeMod;
import net.sirkotok.half_life_mod.entity.mob_geckolib.custom.Headcrab_3;
import software.bernie.geckolib.model.GeoModel;


public class Headcrab_m3 extends GeoModel<Headcrab_3> {

    @Override
    public ResourceLocation getModelResource(Headcrab_3 animatable) {
        return new ResourceLocation(HalfLifeMod.MOD_ID, "geo/headcrab_model_3.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(Headcrab_3 animatable) {
        return new ResourceLocation(HalfLifeMod.MOD_ID, "textures/entity/headcrab_m3_t1.png");
    }


    @Override
    public ResourceLocation getAnimationResource(Headcrab_3 animatable) {
        return new ResourceLocation(HalfLifeMod.MOD_ID, "animations/headcrab_m3_anim.json");
    }



}

