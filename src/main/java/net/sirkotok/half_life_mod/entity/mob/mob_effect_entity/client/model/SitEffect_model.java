package net.sirkotok.half_life_mod.entity.mob.mob_effect_entity.client.model;

import net.minecraft.resources.ResourceLocation;
import net.sirkotok.half_life_mod.HalfLifeMod;
import net.sirkotok.half_life_mod.entity.mob.mob_effect_entity.custom.SitThenBlowUpEffect;
import software.bernie.geckolib.model.GeoModel;

public class SitEffect_model extends GeoModel<SitThenBlowUpEffect> {

    @Override
    public ResourceLocation getModelResource(SitThenBlowUpEffect animatable) {
        return new ResourceLocation(HalfLifeMod.MOD_ID, "geo/shocktrooper_projectile.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(SitThenBlowUpEffect animatable) {
        return new ResourceLocation(HalfLifeMod.MOD_ID, "textures/entity/shocktrooperandroach.png");
    }


    @Override
    public ResourceLocation getAnimationResource(SitThenBlowUpEffect animatable) {
        return new ResourceLocation(HalfLifeMod.MOD_ID, "animations/shocktrooper.animation.json");
    }



}