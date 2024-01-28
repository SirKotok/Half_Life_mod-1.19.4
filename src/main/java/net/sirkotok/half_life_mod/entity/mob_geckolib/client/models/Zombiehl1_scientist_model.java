package net.sirkotok.half_life_mod.entity.mob_geckolib.client.models;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.sirkotok.half_life_mod.HalfLifeMod;
import net.sirkotok.half_life_mod.entity.mob_geckolib.custom.HL1ZombieScientist;
import net.sirkotok.half_life_mod.entity.mob_geckolib.custom.Manhack;
import net.sirkotok.half_life_mod.entity.mob_geckolib.custom.Scientist;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;


public class Zombiehl1_scientist_model extends GeoModel<HL1ZombieScientist> {

    @Override
    public ResourceLocation getModelResource(HL1ZombieScientist animatable) {
        return new ResourceLocation(HalfLifeMod.MOD_ID, "geo/headcrabzombiehl1_2.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(HL1ZombieScientist animatable) {
        return new ResourceLocation(HalfLifeMod.MOD_ID, "textures/entity/zombiehl1_allways.png");
    }


    @Override
    public ResourceLocation getAnimationResource(HL1ZombieScientist animatable) {
        return new ResourceLocation(HalfLifeMod.MOD_ID, "animations/hl1zombie.animation.json");
    }

}

