package net.sirkotok.half_life_mod.item.client.model;

import net.minecraft.resources.ResourceLocation;
import net.sirkotok.half_life_mod.HalfLifeMod;
import net.sirkotok.half_life_mod.item.custom.gun.GravityGun;
import net.sirkotok.half_life_mod.item.custom.gun.SuperGravityGun;
import software.bernie.geckolib.model.GeoModel;

public class GravityGun_model<T extends GravityGun> extends GeoModel<T> {

    @Override
    public ResourceLocation getModelResource(T animatable) {
        return new ResourceLocation(HalfLifeMod.MOD_ID, "geo/gravitygun.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(T animatable) {
        if (animatable instanceof SuperGravityGun) return new ResourceLocation(HalfLifeMod.MOD_ID, "textures/item/gravitygun_blue.png");
        return new ResourceLocation(HalfLifeMod.MOD_ID, "textures/item/gravitygun_orange.png");
    }


    @Override
    public ResourceLocation getAnimationResource(T animatable) {
        return new ResourceLocation(HalfLifeMod.MOD_ID, "animations/gravitygun.animation.json");
    }



}
