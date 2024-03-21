package net.sirkotok.half_life_mod.entity.mob.mob_geckolib.client.models;

import net.minecraft.resources.ResourceLocation;
import net.sirkotok.half_life_mod.HalfLifeMod;
import net.sirkotok.half_life_mod.entity.mob.mob_geckolib.custom.GonarchBM;
import software.bernie.geckolib.model.GeoModel;

public class GonarchBM_model extends GeoModel<GonarchBM> {

    @Override
    public ResourceLocation getModelResource(GonarchBM animatable) {
        return new ResourceLocation(HalfLifeMod.MOD_ID, "geo/bigmomma.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(GonarchBM animatable) {
        return new ResourceLocation(HalfLifeMod.MOD_ID, "textures/entity/blackmesagonarch.png");
    }


    @Override
    public ResourceLocation getAnimationResource(GonarchBM animatable) {
        return new ResourceLocation(HalfLifeMod.MOD_ID, "animations/mom.animation.json");
    }



}
