package net.sirkotok.half_life_mod.entity.projectile.client.model;

import net.minecraft.resources.ResourceLocation;
import net.sirkotok.half_life_mod.HalfLifeMod;
import net.sirkotok.half_life_mod.entity.projectile.AcidThrownBM;
import software.bernie.geckolib.model.GeoModel;

public class Acid_thrown_BM_model extends GeoModel<AcidThrownBM> {

    @Override
    public ResourceLocation getModelResource(AcidThrownBM animatable) {
        return new ResourceLocation(HalfLifeMod.MOD_ID, "geo/shocktrooper_projectile.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(AcidThrownBM animatable) {
        return new ResourceLocation(HalfLifeMod.MOD_ID, "textures/entity/shocktrooperandroach.png");
    }


    @Override
    public ResourceLocation getAnimationResource(AcidThrownBM animatable) {
        return new ResourceLocation(HalfLifeMod.MOD_ID, "animations/shocktrooper.animation.json");
    }



}