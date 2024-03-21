package net.sirkotok.half_life_mod.entity.mob.mob_effect_entity.client.model;

import net.minecraft.resources.ResourceLocation;
import net.sirkotok.half_life_mod.HalfLifeMod;
import net.sirkotok.half_life_mod.entity.mob.mob_effect_entity.custom.ShockWaveEffect;
import software.bernie.geckolib.model.GeoModel;


public class Shockwave_model extends GeoModel<ShockWaveEffect> {

    @Override
    public ResourceLocation getModelResource(ShockWaveEffect animatable) {
        return new ResourceLocation(HalfLifeMod.MOD_ID, "geo/shockwave.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(ShockWaveEffect dog) {

         if (dog.getsquaidsize()>3) return new ResourceLocation(HalfLifeMod.MOD_ID, "textures/entity/houndeye_purple.png");
        switch(dog.getsquaidsize()) {
            case 1: return new ResourceLocation(HalfLifeMod.MOD_ID, "textures/entity/houndeye_light_blue.png");
            case 2: return new ResourceLocation(HalfLifeMod.MOD_ID, "textures/entity/houndeye_blue.png");
            case 3: return new ResourceLocation(HalfLifeMod.MOD_ID, "textures/entity/houndeye_deep_blue.png");
        }

        return new ResourceLocation(HalfLifeMod.MOD_ID, "textures/entity/houndeye_light_blue.png");
    }


    @Override
    public ResourceLocation getAnimationResource(ShockWaveEffect animatable) {
        return new ResourceLocation(HalfLifeMod.MOD_ID, "animations/houndeye.animation.json");
    }



}

