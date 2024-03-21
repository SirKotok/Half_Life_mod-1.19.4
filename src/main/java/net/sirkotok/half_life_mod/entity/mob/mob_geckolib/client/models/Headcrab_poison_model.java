package net.sirkotok.half_life_mod.entity.mob.mob_geckolib.client.models;

import net.minecraft.resources.ResourceLocation;
import net.sirkotok.half_life_mod.HalfLifeMod;
import net.sirkotok.half_life_mod.entity.mob.mob_geckolib.custom.Headcrab_Poison_2;
import software.bernie.geckolib.model.GeoModel;


public class Headcrab_poison_model extends GeoModel<Headcrab_Poison_2> {

    @Override
    public ResourceLocation getModelResource(Headcrab_Poison_2 animatable) {
        return new ResourceLocation(HalfLifeMod.MOD_ID, "geo/headcrab_poison_model.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(Headcrab_Poison_2 animatable) {
        return new ResourceLocation(HalfLifeMod.MOD_ID, "textures/entity/headcrab_poison_t1.png");
    }


    @Override
    public ResourceLocation getAnimationResource(Headcrab_Poison_2 animatable) {
        return new ResourceLocation(HalfLifeMod.MOD_ID, "animations/headcrab_poison.animation.json");
    }



}

