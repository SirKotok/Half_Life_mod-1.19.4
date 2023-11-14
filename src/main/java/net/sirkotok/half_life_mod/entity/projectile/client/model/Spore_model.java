package net.sirkotok.half_life_mod.entity.projectile.client.model;

import net.minecraft.resources.ResourceLocation;
import net.sirkotok.half_life_mod.HalfLifeMod;
import net.sirkotok.half_life_mod.entity.projectile.PitdroneSpike;
import net.sirkotok.half_life_mod.entity.projectile.SporeShot;
import software.bernie.geckolib.model.GeoModel;

public class Spore_model extends GeoModel<SporeShot> {

    @Override
    public ResourceLocation getModelResource(SporeShot animatable) {
        return new ResourceLocation(HalfLifeMod.MOD_ID, "geo/shocktrooper_projectile.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(SporeShot animatable) {
        return new ResourceLocation(HalfLifeMod.MOD_ID, "textures/entity/shocktrooperandroach.png");
    }


    @Override
    public ResourceLocation getAnimationResource(SporeShot animatable) {
        return new ResourceLocation(HalfLifeMod.MOD_ID, "animations/shocktrooper.animation.json");
    }



}