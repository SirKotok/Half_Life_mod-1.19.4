package net.sirkotok.half_life_mod.entity.mob_geckolib.client.models;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.core.UUIDUtil;
import net.minecraft.resources.ResourceLocation;
import net.sirkotok.half_life_mod.HalfLifeMod;
import net.sirkotok.half_life_mod.entity.mob_geckolib.custom.HL2Zombie;
import net.sirkotok.half_life_mod.entity.mob_geckolib.custom.HLZombieVillager;
import net.sirkotok.half_life_mod.entity.mob_geckolib.custom.Headcrab_2;
import net.sirkotok.half_life_mod.entity.mob_geckolib.custom.Headcrab_3;
import software.bernie.geckolib.model.GeoModel;


public class Zombiev_model extends GeoModel<HLZombieVillager> {

    @Override
    public ResourceLocation getModelResource(HLZombieVillager animatable) {
        return new ResourceLocation(HalfLifeMod.MOD_ID, "geo/vzombie.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(HLZombieVillager animatable) {
        if (animatable.getFirstPassenger() instanceof Headcrab_2) return new ResourceLocation(HalfLifeMod.MOD_ID, "textures/entity/zombie_villager_base_hl2.png");
        if (animatable.getFirstPassenger() instanceof Headcrab_3) return new ResourceLocation(HalfLifeMod.MOD_ID, "textures/entity/zombie_villager_base_hla.png");

        return new ResourceLocation(HalfLifeMod.MOD_ID, "textures/entity/zombie_villager_base_hl1.png");
    }



    @Override
    public ResourceLocation getAnimationResource(HLZombieVillager animatable) {
        return new ResourceLocation(HalfLifeMod.MOD_ID, "animations/vzombie.animation.json");
    }


    @Override
    public RenderType getRenderType(HLZombieVillager animatable, ResourceLocation texture) {
        return RenderType.entityTranslucent(texture);
    }



}

