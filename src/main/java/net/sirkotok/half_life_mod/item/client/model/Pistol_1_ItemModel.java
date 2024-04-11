package net.sirkotok.half_life_mod.item.client.model;

import net.minecraft.resources.ResourceLocation;
import net.sirkotok.half_life_mod.HalfLifeMod;
import net.sirkotok.half_life_mod.item.custom.gun.Pistol_1_Item;
import software.bernie.geckolib.model.GeoModel;

public class Pistol_1_ItemModel extends GeoModel<Pistol_1_Item> {

    @Override
    public ResourceLocation getModelResource(Pistol_1_Item animatable) {
        return new ResourceLocation(HalfLifeMod.MOD_ID, "geo/pistol_one_item.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(Pistol_1_Item animatable) {
        return new ResourceLocation(HalfLifeMod.MOD_ID, "textures/item/pistol_one_item.png");
    }


    @Override
    public ResourceLocation getAnimationResource(Pistol_1_Item animatable) {
        return new ResourceLocation(HalfLifeMod.MOD_ID, "animations/pistol_one_item.animation.json");
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
