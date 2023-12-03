package net.sirkotok.half_life_mod.entity.projectile.client.model;

import net.minecraft.resources.ResourceLocation;
import net.sirkotok.half_life_mod.HalfLifeMod;
import net.sirkotok.half_life_mod.entity.projectile.PitdroneSpike;
import net.sirkotok.half_life_mod.entity.projectile.UnderbarrelGranade;
import software.bernie.geckolib.model.GeoModel;

public class Underbarrelnade_model extends GeoModel<UnderbarrelGranade> {

    @Override
    public ResourceLocation getModelResource(UnderbarrelGranade animatable) {
        return new ResourceLocation(HalfLifeMod.MOD_ID, "geo/under_nade.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(UnderbarrelGranade animatable) {
        return new ResourceLocation(HalfLifeMod.MOD_ID, "textures/entity/under_nade.png");
    }


    @Override
    public ResourceLocation getAnimationResource(UnderbarrelGranade animatable) {
        return new ResourceLocation(HalfLifeMod.MOD_ID, "animations/under_nade.animation.json");
    }



}