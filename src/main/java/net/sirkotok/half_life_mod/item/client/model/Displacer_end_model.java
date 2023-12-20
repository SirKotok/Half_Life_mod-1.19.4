package net.sirkotok.half_life_mod.item.client.model;

import net.minecraft.resources.ResourceLocation;
import net.sirkotok.half_life_mod.HalfLifeMod;
import net.sirkotok.half_life_mod.item.custom.gun.Displacer_end;
import net.sirkotok.half_life_mod.item.custom.gun.Displacer_nether;
import software.bernie.geckolib.model.GeoModel;

public class Displacer_end_model extends GeoModel<Displacer_end> {

    @Override
    public ResourceLocation getModelResource(Displacer_end animatable) {
        return new ResourceLocation(HalfLifeMod.MOD_ID, "geo/displacer_cannon.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(Displacer_end animatable) {
        return new ResourceLocation(HalfLifeMod.MOD_ID, "textures/item/displacer_end.png");
    }


    @Override
    public ResourceLocation getAnimationResource(Displacer_end animatable) {
        return new ResourceLocation(HalfLifeMod.MOD_ID, "animations/displacer_cannon.animation.json");
    }



}
