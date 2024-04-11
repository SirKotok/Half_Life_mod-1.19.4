package net.sirkotok.half_life_mod.item.client.model;

import net.minecraft.resources.ResourceLocation;
import net.sirkotok.half_life_mod.HalfLifeMod;
import net.sirkotok.half_life_mod.item.custom.gun.Pistol_1_Item;
import net.sirkotok.half_life_mod.item.custom.gun.Shotgun_1_Item;
import software.bernie.geckolib.model.GeoModel;

public class Shotty_1_ItemModel extends GeoModel<Shotgun_1_Item> {

    @Override
    public ResourceLocation getModelResource(Shotgun_1_Item animatable) {
        return new ResourceLocation(HalfLifeMod.MOD_ID, "geo/shotgun_hl1.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(Shotgun_1_Item animatable) {
        return new ResourceLocation(HalfLifeMod.MOD_ID, "textures/item/shotgun_hl1.png");
    }


    @Override
    public ResourceLocation getAnimationResource(Shotgun_1_Item animatable) {
        return new ResourceLocation(HalfLifeMod.MOD_ID, "animations/shotgun_hl1.animation.json");
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
