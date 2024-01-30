package net.sirkotok.half_life_mod.entity.mob_geckolib.client.models;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.sirkotok.half_life_mod.HalfLifeMod;
import net.sirkotok.half_life_mod.entity.mob_geckolib.custom.HL1ZombieScientist;
import net.sirkotok.half_life_mod.entity.mob_geckolib.custom.HL2Zombie;
import software.bernie.geckolib.model.GeoModel;


public class Zombiehl2_model extends GeoModel<HL2Zombie> {

    @Override
    public ResourceLocation getModelResource(HL2Zombie animatable) {
        if (animatable.getprofile() != null) {
            return new ResourceLocation(HalfLifeMod.MOD_ID, "geo/sz5_hl2_p.geo.json");
        }
        return new ResourceLocation(HalfLifeMod.MOD_ID, "geo/sz5_hl2.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(HL2Zombie animatable) {
        return new ResourceLocation(HalfLifeMod.MOD_ID, "textures/entity/zombie_allways_hl2.png");
    }



    @Override
    public ResourceLocation getAnimationResource(HL2Zombie animatable) {
        return new ResourceLocation(HalfLifeMod.MOD_ID, "animations/hl2zombie.animation.json");
    }


    @Override
    public RenderType getRenderType(HL2Zombie animatable, ResourceLocation texture) {
        return RenderType.entityTranslucent(texture);
    }



}

