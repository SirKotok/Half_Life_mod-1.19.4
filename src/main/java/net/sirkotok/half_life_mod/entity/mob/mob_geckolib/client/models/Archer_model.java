package net.sirkotok.half_life_mod.entity.mob.mob_geckolib.client.models;

import net.minecraft.resources.ResourceLocation;
import net.sirkotok.half_life_mod.HalfLifeMod;
import net.sirkotok.half_life_mod.entity.base.HalfLifeMonster;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.model.GeoModel;


public class Archer_model<T extends HalfLifeMonster & GeoEntity> extends GeoModel<T> {

    @Override
    public ResourceLocation getModelResource(T animatable) {
        return new ResourceLocation(HalfLifeMod.MOD_ID, "geo/archer.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(T animatable) {
        return new ResourceLocation(HalfLifeMod.MOD_ID, "textures/entity/archer.png");
    }


    @Override
    public ResourceLocation getAnimationResource(T animatable) {
        return new ResourceLocation(HalfLifeMod.MOD_ID, "animations/archer.animation.json");
    }



}

