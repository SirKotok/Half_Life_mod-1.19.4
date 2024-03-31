package net.sirkotok.half_life_mod.entity.mob.mob_geckolib.client.models;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.sirkotok.half_life_mod.HalfLifeMod;
import net.sirkotok.half_life_mod.entity.mob.mob_geckolib.custom.AlienGrunt;
import net.sirkotok.half_life_mod.entity.mob.mob_geckolib.custom.Gargantua;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;


public class Gargantua_model extends GeoModel<Gargantua> {

    @Override
    public ResourceLocation getModelResource(Gargantua animatable) {
        return new ResourceLocation(HalfLifeMod.MOD_ID, "geo/gargantua.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(Gargantua animatable) {
        if (animatable.isangry()) return new ResourceLocation(HalfLifeMod.MOD_ID, "textures/entity/gargred.png");
        return new ResourceLocation(HalfLifeMod.MOD_ID, "textures/entity/garg.png");
    }


    @Override
    public ResourceLocation getAnimationResource(Gargantua animatable) {
        return new ResourceLocation(HalfLifeMod.MOD_ID, "animations/gargantua.animation.json");
    }


}

