package net.sirkotok.half_life_mod.entity.mob_geckolib.client.models;

import net.minecraft.resources.ResourceLocation;
import net.sirkotok.half_life_mod.HalfLifeMod;
import net.sirkotok.half_life_mod.entity.mob_geckolib.custom.Shockroach;
import net.sirkotok.half_life_mod.entity.mob_geckolib.custom.Shocktrooper;
import software.bernie.geckolib.model.GeoModel;

public class Shoctrooper_model extends GeoModel<Shocktrooper> {

    @Override
    public ResourceLocation getModelResource(Shocktrooper animatable) {
        return new ResourceLocation(HalfLifeMod.MOD_ID, "geo/shocktrooper.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(Shocktrooper animatable) {
        if (animatable.isDeadOrDying()) return new ResourceLocation(HalfLifeMod.MOD_ID, "textures/entity/shocktrooper.png");
        return new ResourceLocation(HalfLifeMod.MOD_ID, "textures/entity/shocktrooperandroach.png");
    }


    @Override
    public ResourceLocation getAnimationResource(Shocktrooper animatable) {
        return new ResourceLocation(HalfLifeMod.MOD_ID, "animations/shocktrooper.animation.json");
    }



}
