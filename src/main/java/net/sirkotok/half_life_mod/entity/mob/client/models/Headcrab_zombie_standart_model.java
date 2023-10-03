package net.sirkotok.half_life_mod.entity.mob.client.models;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.core.UUIDUtil;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.sirkotok.half_life_mod.HalfLifeMod;

import net.sirkotok.half_life_mod.entity.mob.custom.Headcrab_zombie_standart;
import software.bernie.geckolib.model.GeoModel;


public class Headcrab_zombie_standart_model extends GeoModel<Headcrab_zombie_standart> {

    @Override
    public ResourceLocation getModelResource(Headcrab_zombie_standart animatable) {
        return new ResourceLocation(HalfLifeMod.MOD_ID, "geo/headcrab_zombie_player_model.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(Headcrab_zombie_standart animatable) {
        if (animatable.getprofile() != null) {
            return DefaultPlayerSkin.getDefaultSkin(UUIDUtil.getOrCreatePlayerUUID(animatable.getprofile()));
        }

        return new ResourceLocation(HalfLifeMod.MOD_ID, "textures/entity/minecraft_steve.png");
    }


    @Override
    public ResourceLocation getAnimationResource(Headcrab_zombie_standart animatable) {
        return new ResourceLocation(HalfLifeMod.MOD_ID, "animations/hheadcrab_zombie_player_animations.json");
    }

}




