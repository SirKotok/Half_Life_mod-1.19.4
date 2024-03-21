package net.sirkotok.half_life_mod.entity.mob.mob_geckolib.client.models;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.sirkotok.half_life_mod.HalfLifeMod;
import net.sirkotok.half_life_mod.entity.mob.mob_geckolib.custom.HL2Zombie_fast;
import software.bernie.geckolib.model.GeoModel;


public class Zombiehl2_model_fast extends GeoModel<HL2Zombie_fast> {

    @Override
    public ResourceLocation getModelResource(HL2Zombie_fast animatable) {
        return new ResourceLocation(HalfLifeMod.MOD_ID, "geo/fzombie.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(HL2Zombie_fast animatable) {
        return new ResourceLocation(HalfLifeMod.MOD_ID, "textures/entity/fzombie.png");
    }



    @Override
    public ResourceLocation getAnimationResource(HL2Zombie_fast animatable) {
        return new ResourceLocation(HalfLifeMod.MOD_ID, "animations/fzombie.animation.json");
    }


    @Override
    public RenderType getRenderType(HL2Zombie_fast animatable, ResourceLocation texture) {
        return RenderType.entityTranslucent(texture);
    }



}

