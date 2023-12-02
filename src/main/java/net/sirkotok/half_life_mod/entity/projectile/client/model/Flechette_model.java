package net.sirkotok.half_life_mod.entity.projectile.client.model;

import net.minecraft.resources.ResourceLocation;
import net.sirkotok.half_life_mod.HalfLifeMod;
import net.sirkotok.half_life_mod.entity.projectile.PitdroneSpike;
import net.sirkotok.half_life_mod.entity.projectile.arrowlike.Flechette;
import software.bernie.geckolib.model.GeoModel;

public class Flechette_model extends GeoModel<Flechette> {

    @Override
    public ResourceLocation getModelResource(Flechette animatable) {
        return new ResourceLocation(HalfLifeMod.MOD_ID, "geo/flechette.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(Flechette animatable) {
        return new ResourceLocation(HalfLifeMod.MOD_ID, "textures/entity/flechette.png");
    }


    @Override
    public ResourceLocation getAnimationResource(Flechette animatable) {
        return new ResourceLocation(HalfLifeMod.MOD_ID, "animations/voltigore_projectile.animation.json");
    }



}