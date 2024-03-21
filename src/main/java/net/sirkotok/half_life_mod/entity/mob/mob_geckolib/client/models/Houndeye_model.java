package net.sirkotok.half_life_mod.entity.mob.mob_geckolib.client.models;

import net.minecraft.resources.ResourceLocation;
import net.sirkotok.half_life_mod.HalfLifeMod;
import net.sirkotok.half_life_mod.entity.mob.mob_geckolib.custom.Houndeye;
import software.bernie.geckolib.model.GeoModel;


public class Houndeye_model extends GeoModel<Houndeye> {

    @Override
    public ResourceLocation getModelResource(Houndeye animatable) {
        return new ResourceLocation(HalfLifeMod.MOD_ID, "geo/houndeye.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(Houndeye dog) {

        if (dog.islight()) {
            if (dog.getsquaidsize()>3) return new ResourceLocation(HalfLifeMod.MOD_ID, "textures/entity/houndeye_g_purple.png");
            switch(dog.getsquaidsize()) {
                case 1: return new ResourceLocation(HalfLifeMod.MOD_ID, "textures/entity/houndeye_g_light_blue.png");
                case 2: return new ResourceLocation(HalfLifeMod.MOD_ID, "textures/entity/houndeye_g_blue.png");
                case 3: return new ResourceLocation(HalfLifeMod.MOD_ID, "textures/entity/houndeye_g_deep_blue.png");
            }
        }
         if (dog.getsquaidsize()>3) return new ResourceLocation(HalfLifeMod.MOD_ID, "textures/entity/houndeye_purple.png");
        switch(dog.getsquaidsize()) {
            case 1: return new ResourceLocation(HalfLifeMod.MOD_ID, "textures/entity/houndeye_light_blue.png");
            case 2: return new ResourceLocation(HalfLifeMod.MOD_ID, "textures/entity/houndeye_blue.png");
            case 3: return new ResourceLocation(HalfLifeMod.MOD_ID, "textures/entity/houndeye_deep_blue.png");
        }

        return new ResourceLocation(HalfLifeMod.MOD_ID, "textures/entity/houndeye_light_blue.png");
    }


    @Override
    public ResourceLocation getAnimationResource(Houndeye animatable) {
        return new ResourceLocation(HalfLifeMod.MOD_ID, "animations/houndeye.animation.json");
    }



}

