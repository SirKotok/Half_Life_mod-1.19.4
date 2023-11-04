package net.sirkotok.half_life_mod.entity.mob_geckolib.client.models;

import net.minecraft.resources.ResourceLocation;
import net.sirkotok.half_life_mod.HalfLifeMod;
import net.sirkotok.half_life_mod.entity.mob_geckolib.custom.Snark;
import net.sirkotok.half_life_mod.entity.mob_geckolib.custom.Vortigore;
import software.bernie.geckolib.model.GeoModel;


public class Vortigore_model extends GeoModel<Vortigore> {

    @Override
    public ResourceLocation getModelResource(Vortigore animatable) {
        return new ResourceLocation(HalfLifeMod.MOD_ID, "geo/vortigore.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(Vortigore animatable) {
        if (animatable.isBB()) return new ResourceLocation(HalfLifeMod.MOD_ID, "textures/entity/baby_vortigore.png");
        return new ResourceLocation(HalfLifeMod.MOD_ID, "textures/entity/vortigore.png");
    }


    @Override
    public ResourceLocation getAnimationResource(Vortigore animatable) {
        return new ResourceLocation(HalfLifeMod.MOD_ID, "animations/vortigore.animation.json");
    }



}

