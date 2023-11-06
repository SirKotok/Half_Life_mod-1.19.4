package net.sirkotok.half_life_mod.entity.projectile.client.model;

import net.minecraft.resources.ResourceLocation;
import net.sirkotok.half_life_mod.HalfLifeMod;
import net.sirkotok.half_life_mod.entity.projectile.PitdroneSpike;
import net.sirkotok.half_life_mod.entity.projectile.VoltigoreShock;
import software.bernie.geckolib.model.GeoModel;

public class Spike_model extends GeoModel<PitdroneSpike> {

    @Override
    public ResourceLocation getModelResource(PitdroneSpike animatable) {
        return new ResourceLocation(HalfLifeMod.MOD_ID, "geo/pitdrone_spike.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(PitdroneSpike animatable) {
        return new ResourceLocation(HalfLifeMod.MOD_ID, "textures/entity/pit_drone_spike.png");
    }


    @Override
    public ResourceLocation getAnimationResource(PitdroneSpike animatable) {
        return new ResourceLocation(HalfLifeMod.MOD_ID, "animations/voltigore_projectile.animation.json");
    }



}