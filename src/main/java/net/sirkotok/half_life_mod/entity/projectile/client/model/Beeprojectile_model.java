package net.sirkotok.half_life_mod.entity.projectile.client.model;

import net.minecraft.resources.ResourceLocation;
import net.sirkotok.half_life_mod.HalfLifeMod;
import net.sirkotok.half_life_mod.entity.projectile.BeeProjectile;
import net.sirkotok.half_life_mod.entity.projectile.PitdroneSpike;
import software.bernie.geckolib.model.GeoModel;

public class Beeprojectile_model extends GeoModel<BeeProjectile> {

    @Override
    public ResourceLocation getModelResource(BeeProjectile animatable) {
        return new ResourceLocation(HalfLifeMod.MOD_ID, "geo/beeprojectile.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(BeeProjectile animatable) {
        return new ResourceLocation(HalfLifeMod.MOD_ID, "textures/entity/beeprojectile.png");
    }


    @Override
    public ResourceLocation getAnimationResource(BeeProjectile animatable) {
        return new ResourceLocation(HalfLifeMod.MOD_ID, "animations/voltigore_projectile.animation.json");
    }



}