package net.sirkotok.half_life_mod.item.client.model;

import net.minecraft.resources.ResourceLocation;
import net.sirkotok.half_life_mod.HalfLifeMod;
import net.sirkotok.half_life_mod.item.custom.SnarkItem;
import software.bernie.geckolib.model.GeoModel;


public class SnarkItemmodel extends GeoModel<SnarkItem> {

    @Override
    public ResourceLocation getModelResource(SnarkItem animatable) {
        return new ResourceLocation(HalfLifeMod.MOD_ID, "geo/snark.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(SnarkItem animatable) {
        return new ResourceLocation(HalfLifeMod.MOD_ID, "textures/item/snark.png");
    }


    @Override
    public ResourceLocation getAnimationResource(SnarkItem animatable) {
        return new ResourceLocation(HalfLifeMod.MOD_ID, "animations/snark.animation.json");
    }



}

