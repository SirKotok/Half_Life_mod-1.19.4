package net.sirkotok.half_life_mod.item.client.model;

import net.minecraft.resources.ResourceLocation;
import net.sirkotok.half_life_mod.HalfLifeMod;
import net.sirkotok.half_life_mod.item.custom.gun.Displacer_cannon;
import net.sirkotok.half_life_mod.item.custom.gun.SMG_1_Item;
import software.bernie.geckolib.model.GeoModel;

public class Displacer_gun_model extends GeoModel<Displacer_cannon> {

    @Override
    public ResourceLocation getModelResource(Displacer_cannon animatable) {
        return new ResourceLocation(HalfLifeMod.MOD_ID, "geo/displacer_cannon.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(Displacer_cannon animatable) {
        return new ResourceLocation(HalfLifeMod.MOD_ID, "textures/item/displacer_cannon.png");
    }


    @Override
    public ResourceLocation getAnimationResource(Displacer_cannon animatable) {
        return new ResourceLocation(HalfLifeMod.MOD_ID, "animations/displacer_cannon.animation.json");
    }



}
