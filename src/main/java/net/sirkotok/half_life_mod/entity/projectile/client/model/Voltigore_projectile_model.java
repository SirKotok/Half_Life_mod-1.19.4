package net.sirkotok.half_life_mod.entity.projectile.client.model;

import net.minecraft.resources.ResourceLocation;
import net.sirkotok.half_life_mod.HalfLifeMod;
import net.sirkotok.half_life_mod.entity.mob_geckolib.custom.Voltigore;
import net.sirkotok.half_life_mod.entity.projectile.VoltigoreShock;
import software.bernie.geckolib.model.GeoModel;

public class Voltigore_projectile_model extends GeoModel<VoltigoreShock> {

    @Override
    public ResourceLocation getModelResource(VoltigoreShock animatable) {
        return new ResourceLocation(HalfLifeMod.MOD_ID, "geo/voltigore_projectile.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(VoltigoreShock animatable) {
        return new ResourceLocation(HalfLifeMod.MOD_ID, "textures/entity/voltigore_projectile.png");
    }


    @Override
    public ResourceLocation getAnimationResource(VoltigoreShock animatable) {
        return new ResourceLocation(HalfLifeMod.MOD_ID, "animations/voltigore_projectile.animation.json");
    }



}