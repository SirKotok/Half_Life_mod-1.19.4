package net.sirkotok.half_life_mod.entity.mob_geckolib.client.models;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.sirkotok.half_life_mod.HalfLifeMod;
import net.sirkotok.half_life_mod.entity.mob_geckolib.custom.Hunter;
import net.sirkotok.half_life_mod.entity.mob_geckolib.custom.Manhack;
import software.bernie.geckolib.model.GeoModel;


public class Hunter_model extends GeoModel<Hunter> {

    @Override
    public ResourceLocation getModelResource(Hunter animatable) {
        return new ResourceLocation(HalfLifeMod.MOD_ID, "geo/hunter.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(Hunter animatable) {
        return new ResourceLocation(HalfLifeMod.MOD_ID, "textures/entity/hunter.png");
    }


    @Override
    public ResourceLocation getAnimationResource(Hunter animatable) {
        return new ResourceLocation(HalfLifeMod.MOD_ID, "animations/hunter.animation.json");
    }

}

