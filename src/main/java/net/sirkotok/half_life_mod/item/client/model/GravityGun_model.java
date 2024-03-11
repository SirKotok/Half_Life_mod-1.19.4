package net.sirkotok.half_life_mod.item.client.model;

import net.minecraft.resources.ResourceLocation;
import net.sirkotok.half_life_mod.HalfLifeMod;
import net.sirkotok.half_life_mod.item.custom.gun.Displacer_cannon;
import software.bernie.geckolib.model.GeoModel;

public class GravityGun_model extends GeoModel<Displacer_cannon> {

    @Override
    public ResourceLocation getModelResource(Displacer_cannon animatable) {
        return new ResourceLocation(HalfLifeMod.MOD_ID, "geo/gravitygun.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(Displacer_cannon animatable) {
        return new ResourceLocation(HalfLifeMod.MOD_ID, "textures/item/gravitygun_orange.png");
    }

 //  return new ResourceLocation(HalfLifeMod.MOD_ID, "textures/item/gravitygun_blue.png");

    @Override
    public ResourceLocation getAnimationResource(Displacer_cannon animatable) {
        return new ResourceLocation(HalfLifeMod.MOD_ID, "animations/gravitygun.animation.json");
    }



}
