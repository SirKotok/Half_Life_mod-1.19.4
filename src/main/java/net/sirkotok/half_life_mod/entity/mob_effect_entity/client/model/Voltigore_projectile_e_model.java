package net.sirkotok.half_life_mod.entity.mob_effect_entity.client.model;

import net.minecraft.resources.ResourceLocation;
import net.sirkotok.half_life_mod.HalfLifeMod;
import net.sirkotok.half_life_mod.entity.mob_effect_entity.custom.VoltigoreProjectileAftereffect;
import software.bernie.geckolib.model.GeoModel;

public class Voltigore_projectile_e_model extends GeoModel<VoltigoreProjectileAftereffect> {

    @Override
    public ResourceLocation getModelResource(VoltigoreProjectileAftereffect animatable) {
        return new ResourceLocation(HalfLifeMod.MOD_ID, "geo/voltigore_projectile.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(VoltigoreProjectileAftereffect animatable) {
        return new ResourceLocation(HalfLifeMod.MOD_ID, "textures/entity/voltigore_projectile.png");
    }


    @Override
    public ResourceLocation getAnimationResource(VoltigoreProjectileAftereffect animatable) {
        return new ResourceLocation(HalfLifeMod.MOD_ID, "animations/voltigore_projectile.animation.json");
    }



}