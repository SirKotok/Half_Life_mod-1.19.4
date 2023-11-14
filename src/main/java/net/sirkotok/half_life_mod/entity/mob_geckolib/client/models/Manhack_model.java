package net.sirkotok.half_life_mod.entity.mob_geckolib.client.models;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.sirkotok.half_life_mod.HalfLifeMod;
import net.sirkotok.half_life_mod.entity.mob_geckolib.custom.Manhack;
import net.sirkotok.half_life_mod.entity.mob_geckolib.custom.Snark;
import software.bernie.geckolib.model.GeoModel;


public class Manhack_model extends GeoModel<Manhack> {

    @Override
    public ResourceLocation getModelResource(Manhack animatable) {
        return new ResourceLocation(HalfLifeMod.MOD_ID, "geo/manhack.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(Manhack animatable) {
        return new ResourceLocation(HalfLifeMod.MOD_ID, "textures/entity/manhack.png");
    }


    @Override
    public ResourceLocation getAnimationResource(Manhack animatable) {
        return new ResourceLocation(HalfLifeMod.MOD_ID, "animations/manhack.animation.json");
    }

    @Override
    public RenderType getRenderType(Manhack animatable, ResourceLocation texture) {
        return RenderType.entityTranslucent(texture);
    }
}

