package net.sirkotok.half_life_mod.item.client.model;

import net.minecraft.resources.ResourceLocation;
import net.sirkotok.half_life_mod.HalfLifeMod;
import net.sirkotok.half_life_mod.item.custom.gun.Deagle_Item;
import net.sirkotok.half_life_mod.item.custom.gun.Pistol_1_Item;
import software.bernie.geckolib.model.GeoModel;

public class Deagle_ItemModel extends GeoModel<Deagle_Item> {

    @Override
    public ResourceLocation getModelResource(Deagle_Item animatable) {
        return new ResourceLocation(HalfLifeMod.MOD_ID, "geo/desert_eagle.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(Deagle_Item animatable) {
        return new ResourceLocation(HalfLifeMod.MOD_ID, "textures/item/desert_eagle.png");
    }


    @Override
    public ResourceLocation getAnimationResource(Deagle_Item animatable) {
        return new ResourceLocation(HalfLifeMod.MOD_ID, "animations/desert_eagle.animation.json");
    }


}

/*
*
*
*
*
*
*
* */
