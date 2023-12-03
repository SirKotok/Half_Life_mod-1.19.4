package net.sirkotok.half_life_mod.item.client.model;

import net.minecraft.resources.ResourceLocation;
import net.sirkotok.half_life_mod.HalfLifeMod;
import net.sirkotok.half_life_mod.item.custom.gun.Pistol_1_Item;
import net.sirkotok.half_life_mod.item.custom.gun.SMG_1_Item;
import software.bernie.geckolib.model.GeoModel;

public class SMG_1_ItemModel extends GeoModel<SMG_1_Item> {

    @Override
    public ResourceLocation getModelResource(SMG_1_Item animatable) {
        return new ResourceLocation(HalfLifeMod.MOD_ID, "geo/smg_hl1.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(SMG_1_Item animatable) {
        return new ResourceLocation(HalfLifeMod.MOD_ID, "textures/item/smg_hl1.png");
    }


    @Override
    public ResourceLocation getAnimationResource(SMG_1_Item animatable) {
        return new ResourceLocation(HalfLifeMod.MOD_ID, "animations/smg_hl1.animation.json");
    }



}
