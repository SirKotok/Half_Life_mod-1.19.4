package net.sirkotok.half_life_mod.entity.mob.mob_geckolib.client.models;

import net.minecraft.resources.ResourceLocation;
import net.sirkotok.half_life_mod.HalfLifeMod;
import net.sirkotok.half_life_mod.entity.mob.mob_geckolib.custom.AquaticBullsquid;
import net.sirkotok.half_life_mod.entity.mob.mob_geckolib.custom.Bullsquid;
import software.bernie.geckolib.model.GeoModel;


public class AquaticBullsquid_model extends GeoModel<AquaticBullsquid> {

    @Override
    public ResourceLocation getModelResource(AquaticBullsquid animatable) {
        return new ResourceLocation(HalfLifeMod.MOD_ID, "geo/aquatic_bullsquid.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(AquaticBullsquid animatable) {
        return new ResourceLocation(HalfLifeMod.MOD_ID, "textures/entity/aquatic_bullsquid.png");
    }


    @Override
    public ResourceLocation getAnimationResource(AquaticBullsquid animatable) {
        return new ResourceLocation(HalfLifeMod.MOD_ID, "animations/aquatic_bullsquid.animation.json");
    }



}

