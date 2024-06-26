package net.sirkotok.half_life_mod.item.client.model;

import net.minecraft.resources.ResourceLocation;
import net.sirkotok.half_life_mod.HalfLifeMod;
import net.sirkotok.half_life_mod.item.custom.ChumtoadItem;
import software.bernie.geckolib.model.GeoModel;

public class ChumtoadItemModel extends GeoModel<ChumtoadItem> {

    @Override
    public ResourceLocation getModelResource(ChumtoadItem animatable) {
        return new ResourceLocation(HalfLifeMod.MOD_ID, "geo/chumtoad_v2.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(ChumtoadItem animatable) {
        return new ResourceLocation(HalfLifeMod.MOD_ID, "textures/entity/chumtoadoriginal.png");
    }


    @Override
    public ResourceLocation getAnimationResource(ChumtoadItem animatable) {
        return new ResourceLocation(HalfLifeMod.MOD_ID, "animations/chumtoad.animation.json");
    }
}
