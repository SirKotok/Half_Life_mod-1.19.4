package net.sirkotok.half_life_mod.item.client.model;

import net.minecraft.resources.ResourceLocation;
import net.sirkotok.half_life_mod.HalfLifeMod;
import net.sirkotok.half_life_mod.item.custom.gun.Deagle_Item;
import net.sirkotok.half_life_mod.item.custom.gun.SAW_Item;
import software.bernie.geckolib.model.GeoModel;

public class SAW_ItemModel extends GeoModel<SAW_Item> {

    @Override
    public ResourceLocation getModelResource(SAW_Item animatable) {
        return new ResourceLocation(HalfLifeMod.MOD_ID, "geo/m249.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(SAW_Item animatable) {
        return new ResourceLocation(HalfLifeMod.MOD_ID, "textures/item/m249.png");
    }


    @Override
    public ResourceLocation getAnimationResource(SAW_Item animatable) {
        return new ResourceLocation(HalfLifeMod.MOD_ID, "animations/m249.animation.json");
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
