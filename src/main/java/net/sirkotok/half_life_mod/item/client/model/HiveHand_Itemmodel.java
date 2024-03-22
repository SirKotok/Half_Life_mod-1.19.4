package net.sirkotok.half_life_mod.item.client.model;

import net.minecraft.resources.ResourceLocation;
import net.sirkotok.half_life_mod.HalfLifeMod;
import net.sirkotok.half_life_mod.item.custom.gun.Hivehand_Item;
import net.sirkotok.half_life_mod.item.custom.gun.Shockroach_Item;
import software.bernie.geckolib.model.GeoModel;

public class HiveHand_Itemmodel extends GeoModel<Hivehand_Item> {

    @Override
    public ResourceLocation getModelResource(Hivehand_Item animatable) {
        return new ResourceLocation(HalfLifeMod.MOD_ID, "geo/hivegun.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(Hivehand_Item animatable) {
        return new ResourceLocation(HalfLifeMod.MOD_ID, "textures/item/hivegun.png");
    }


    @Override
    public ResourceLocation getAnimationResource(Hivehand_Item animatable) {
        return new ResourceLocation(HalfLifeMod.MOD_ID, "animations/hivegun.animation.json");
    }



}
