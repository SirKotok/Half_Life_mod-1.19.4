package net.sirkotok.half_life_mod.entity.mob_effect_entity.client.model;

import net.minecraft.resources.ResourceLocation;
import net.sirkotok.half_life_mod.HalfLifeMod;
import net.sirkotok.half_life_mod.entity.mob_effect_entity.custom.VortShockWaveEffect;
import software.bernie.geckolib.model.GeoModel;


public class VortShockwaveModel extends GeoModel<VortShockWaveEffect> {

    @Override
    public ResourceLocation getModelResource(VortShockWaveEffect animatable) {
        return new ResourceLocation(HalfLifeMod.MOD_ID, "geo/vort_hl2_effect.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(VortShockWaveEffect dog) {
        return new ResourceLocation(HalfLifeMod.MOD_ID, "textures/entity/vort_hl2_effect.png");
    }


    @Override
    public ResourceLocation getAnimationResource(VortShockWaveEffect animatable) {
        return new ResourceLocation(HalfLifeMod.MOD_ID, "animations/vort_effect.animation.json");
    }



}

