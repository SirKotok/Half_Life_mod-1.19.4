package net.sirkotok.half_life_mod.entity.mob.client.models;

import net.minecraft.resources.ResourceLocation;
import net.sirkotok.half_life_mod.HalfLifeMod;
import net.sirkotok.half_life_mod.entity.mob.custom.Headcrab_3;
import net.sirkotok.half_life_mod.entity.mob.custom.Headcrab_Armored;
import software.bernie.geckolib.model.GeoModel;


public class Headcrab_armor_model extends GeoModel<Headcrab_Armored> {

    @Override
    public ResourceLocation getModelResource(Headcrab_Armored animatable) {
        return new ResourceLocation(HalfLifeMod.MOD_ID, "geo/headcrab_armored_model.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(Headcrab_Armored animatable) {
        return new ResourceLocation(HalfLifeMod.MOD_ID, "textures/entity/headcrab_armored_t1.png");
    }


    @Override
    public ResourceLocation getAnimationResource(Headcrab_Armored animatable) {
        return new ResourceLocation(HalfLifeMod.MOD_ID, "animations/headcrab_m3_anim.json");
    }



}

