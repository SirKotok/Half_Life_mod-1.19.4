package net.sirkotok.half_life_mod.entity.mob_geckolib.client.models;

import net.minecraft.resources.ResourceLocation;
import net.sirkotok.half_life_mod.HalfLifeMod;
import net.sirkotok.half_life_mod.entity.mob_geckolib.custom.Hoteye;
import net.sirkotok.half_life_mod.entity.mob_geckolib.custom.VortigauntHL1;
import software.bernie.geckolib.model.GeoModel;


public class VortHL1_model extends GeoModel<VortigauntHL1> {

    @Override
    public ResourceLocation getModelResource(VortigauntHL1 animatable) {
        return new ResourceLocation(HalfLifeMod.MOD_ID, "geo/vortigaunthl1.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(VortigauntHL1 dog) {

        return new ResourceLocation(HalfLifeMod.MOD_ID, "textures/entity/vorthl1.png");
    }


    @Override
    public ResourceLocation getAnimationResource(VortigauntHL1 animatable) {
        return new ResourceLocation(HalfLifeMod.MOD_ID, "animations/vort.animation.json");
    }



}

