package net.sirkotok.half_life_mod.item.client.model;

import net.minecraft.resources.ResourceLocation;
import net.sirkotok.half_life_mod.HalfLifeMod;
import net.sirkotok.half_life_mod.item.custom.gun.Shockroach_Item;
import software.bernie.geckolib.model.GeoModel;

public class Shockroach_Itemmodel extends GeoModel<Shockroach_Item> {

    @Override
    public ResourceLocation getModelResource(Shockroach_Item animatable) {
        return new ResourceLocation(HalfLifeMod.MOD_ID, "geo/shockroach.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(Shockroach_Item animatable) {
        return new ResourceLocation(HalfLifeMod.MOD_ID, "textures/item/shockroach.png");
    }


    @Override
    public ResourceLocation getAnimationResource(Shockroach_Item animatable) {
        return new ResourceLocation(HalfLifeMod.MOD_ID, "animations/shockroach.animation.json");
    }



}
