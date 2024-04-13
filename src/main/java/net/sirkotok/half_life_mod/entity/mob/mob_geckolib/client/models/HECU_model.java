package net.sirkotok.half_life_mod.entity.mob.mob_geckolib.client.models;

import net.minecraft.resources.ResourceLocation;
import net.sirkotok.half_life_mod.HalfLifeMod;
import net.sirkotok.half_life_mod.entity.mob.mob_geckolib.custom.HecuGrunt;
import net.sirkotok.half_life_mod.entity.mob.mob_geckolib.custom.Shocktrooper;
import software.bernie.geckolib.model.GeoModel;

public class HECU_model extends GeoModel<HecuGrunt> {

    @Override
    public ResourceLocation getModelResource(HecuGrunt animatable) {
        return new ResourceLocation(HalfLifeMod.MOD_ID, "geo/hecu.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(HecuGrunt animatable) {
        return new ResourceLocation(HalfLifeMod.MOD_ID, "textures/entity/hecu/hecu_base.png");
    }


    @Override
    public ResourceLocation getAnimationResource(HecuGrunt animatable) {
        return new ResourceLocation(HalfLifeMod.MOD_ID, "animations/hecu.animation.json");
    }



}
