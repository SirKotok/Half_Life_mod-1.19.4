package net.sirkotok.half_life_mod.entity.mob.mob_geckolib.client.models;

import net.minecraft.resources.ResourceLocation;
import net.sirkotok.half_life_mod.HalfLifeMod;
import net.sirkotok.half_life_mod.entity.mob.mob_geckolib.custom.Chumtoad;
import software.bernie.geckolib.model.GeoModel;


public class Chumtoad_model extends GeoModel<Chumtoad> {

    @Override
    public ResourceLocation getModelResource(Chumtoad animatable) {
        if (animatable.getIsSixeye()) return new ResourceLocation(HalfLifeMod.MOD_ID, "geo/chumtoad_6eye.geo.json");
        return new ResourceLocation(HalfLifeMod.MOD_ID, "geo/chumtoad_v2.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(Chumtoad animatable) {
            if (!animatable.getIsSixeye()) {switch (animatable.gettexture()) {
                 case 0: return new ResourceLocation(HalfLifeMod.MOD_ID, "textures/entity/chumtoadoriginal.png");
                 case 1: return new ResourceLocation(HalfLifeMod.MOD_ID, "textures/entity/chumtoadpurple.png");
                 case 2: return new ResourceLocation(HalfLifeMod.MOD_ID, "textures/entity/chumtoadblue.png");
                 case 3: return new ResourceLocation(HalfLifeMod.MOD_ID, "textures/entity/chumtoadbrown.png");
                 case 4: return new ResourceLocation(HalfLifeMod.MOD_ID, "textures/entity/chumtoadgreen.png");
                 case 5: return new ResourceLocation(HalfLifeMod.MOD_ID, "textures/entity/chumtoadconcept.png");
           }} else {
                switch (animatable.gettexture()) {
                    case 0: return new ResourceLocation(HalfLifeMod.MOD_ID, "textures/entity/chumtoad6eyeoriginal.png");
                    case 1: return new ResourceLocation(HalfLifeMod.MOD_ID, "textures/entity/chumtoad6eyepurple.png");
                    case 2: return new ResourceLocation(HalfLifeMod.MOD_ID, "textures/entity/chumtoad6eyeblue.png");
                    case 3: return new ResourceLocation(HalfLifeMod.MOD_ID, "textures/entity/chumtoad6eyebrown.png");
                    case 4: return new ResourceLocation(HalfLifeMod.MOD_ID, "textures/entity/chumtoad6eyegreen.png");
                    case 5: return new ResourceLocation(HalfLifeMod.MOD_ID, "textures/entity/chumtoad6eyeconcept.png");
                }
            }
        return new ResourceLocation(HalfLifeMod.MOD_ID, "textures/entity/chumtoadoriginal.png");
    }


    @Override
    public ResourceLocation getAnimationResource(Chumtoad animatable) {
        return new ResourceLocation(HalfLifeMod.MOD_ID, "animations/chumtoad.animation.json");
    }



}

