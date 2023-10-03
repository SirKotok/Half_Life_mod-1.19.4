package net.sirkotok.half_life_mod.item.client.model;

import net.minecraft.resources.ResourceLocation;
import net.sirkotok.half_life_mod.HalfLifeMod;
import net.sirkotok.half_life_mod.item.custom.PenguinItem;

import software.bernie.geckolib.model.GeoModel;


public class PenguinItemmodel extends GeoModel<PenguinItem> {

    @Override
    public ResourceLocation getModelResource(PenguinItem animatable) {
        return new ResourceLocation(HalfLifeMod.MOD_ID, "geo/penguin.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(PenguinItem animatable) {
        return new ResourceLocation(HalfLifeMod.MOD_ID, "textures/item/penguin.png");
    }


    @Override
    public ResourceLocation getAnimationResource(PenguinItem animatable) {
        return new ResourceLocation(HalfLifeMod.MOD_ID, "animations/snark.animation.json");
    }



}

