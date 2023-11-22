package net.sirkotok.half_life_mod.entity.mob_geckolib.client.models;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.sirkotok.half_life_mod.HalfLifeMod;
import net.sirkotok.half_life_mod.entity.mob_geckolib.custom.Leech;
import net.sirkotok.half_life_mod.entity.mob_geckolib.custom.Manhack;
import net.sirkotok.half_life_mod.entity.mob_geckolib.custom.Shark;
import software.bernie.geckolib.model.GeoModel;

public class Shark_model extends GeoModel<Shark> {

    @Override
    public ResourceLocation getModelResource(Shark animatable) {
        return new ResourceLocation(HalfLifeMod.MOD_ID, "geo/ichthyosaur.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(Shark animatable) {
        if (animatable.gettexture()) return new ResourceLocation(HalfLifeMod.MOD_ID, "textures/entity/icht2.png");
        return new ResourceLocation(HalfLifeMod.MOD_ID, "textures/entity/icht1.png");
    }


    @Override
    public ResourceLocation getAnimationResource(Shark animatable) {
        return new ResourceLocation(HalfLifeMod.MOD_ID, "animations/shark.animation.json");
    }


    @Override
   public RenderType getRenderType(Shark animatable, ResourceLocation texture) {
        if (animatable.gettexture()) return RenderType.entityTranslucent(texture);
        return RenderType.entityCutoutNoCull(texture);
    }


}
