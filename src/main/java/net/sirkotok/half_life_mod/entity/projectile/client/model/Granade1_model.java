package net.sirkotok.half_life_mod.entity.projectile.client.model;

import net.minecraft.resources.ResourceLocation;
import net.sirkotok.half_life_mod.HalfLifeMod;
import net.sirkotok.half_life_mod.entity.projectile.Granade;
import net.sirkotok.half_life_mod.entity.projectile.PitdroneSpike;
import software.bernie.geckolib.model.GeoModel;

public class Granade1_model extends GeoModel<Granade> {

    @Override
    public ResourceLocation getModelResource(Granade animatable) {
        return new ResourceLocation(HalfLifeMod.MOD_ID, "geo/granade.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(Granade animatable) {
        return new ResourceLocation(HalfLifeMod.MOD_ID, "textures/item/granade.png");
    }


    @Override
    public ResourceLocation getAnimationResource(Granade animatable) {
        return new ResourceLocation(HalfLifeMod.MOD_ID, "animations/controller.animation.json");
    }



}