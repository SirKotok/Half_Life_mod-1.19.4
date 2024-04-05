package net.sirkotok.half_life_mod.item.client.model;

import net.minecraft.resources.ResourceLocation;
import net.sirkotok.half_life_mod.HalfLifeMod;
import net.sirkotok.half_life_mod.item.custom.ChumtoadItem;
import software.bernie.geckolib.model.GeoModel;

public class Chumtoad6eyeItemModel extends GeoModel<ChumtoadItem> {

    @Override
    public ResourceLocation getModelResource(ChumtoadItem animatable) {
        return new ResourceLocation(HalfLifeMod.MOD_ID, "geo/chumtoad_6eye.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(ChumtoadItem animatable) {
        return new ResourceLocation(HalfLifeMod.MOD_ID, "textures/entity/chumtoadblue.png");
    }


    @Override
    public ResourceLocation getAnimationResource(ChumtoadItem animatable) {
        return new ResourceLocation(HalfLifeMod.MOD_ID, "animations/aliengrunt.animation.json");
    }
}
