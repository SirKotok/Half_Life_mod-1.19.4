package net.sirkotok.half_life_mod.item.client.model;

import net.minecraft.resources.ResourceLocation;
import net.sirkotok.half_life_mod.HalfLifeMod;
import net.sirkotok.half_life_mod.item.custom.gun.Displacer_cannon;
import net.sirkotok.half_life_mod.item.custom.gun.Displacer_nether;
import software.bernie.geckolib.model.GeoModel;

public class Displacer_nether_model extends GeoModel<Displacer_nether> {

    @Override
    public ResourceLocation getModelResource(Displacer_nether animatable) {
        return new ResourceLocation(HalfLifeMod.MOD_ID, "geo/displacer_cannon.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(Displacer_nether animatable) {
        return new ResourceLocation(HalfLifeMod.MOD_ID, "textures/item/displacer_nether.png");
    }


    @Override
    public ResourceLocation getAnimationResource(Displacer_nether animatable) {
        return new ResourceLocation(HalfLifeMod.MOD_ID, "animations/displacer_cannon.animation.json");
    }



}
