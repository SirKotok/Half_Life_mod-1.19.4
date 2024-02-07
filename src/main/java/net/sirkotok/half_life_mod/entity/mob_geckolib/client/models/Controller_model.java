package net.sirkotok.half_life_mod.entity.mob_geckolib.client.models;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.sirkotok.half_life_mod.HalfLifeMod;
import net.sirkotok.half_life_mod.entity.mob_geckolib.custom.Controller;
import net.sirkotok.half_life_mod.entity.mob_geckolib.custom.Hunter;
import net.sirkotok.half_life_mod.entity.mob_geckolib.custom.Manhack;
import software.bernie.geckolib.model.GeoModel;


public class Controller_model extends GeoModel<Controller> {



    @Override
    public ResourceLocation getModelResource(Controller animatable) {
        return new ResourceLocation(HalfLifeMod.MOD_ID, "geo/controller.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(Controller animatable) {
        return new ResourceLocation(HalfLifeMod.MOD_ID, "textures/entity/controller_noattack.png");
    }


    @Override
    public ResourceLocation getAnimationResource(Controller animatable) {
        return new ResourceLocation(HalfLifeMod.MOD_ID, "animations/controller.animation.json");
    }


    @Override
    public RenderType getRenderType(Controller animatable, ResourceLocation texture) {
        return RenderType.entityTranslucent(texture);
    }

}

