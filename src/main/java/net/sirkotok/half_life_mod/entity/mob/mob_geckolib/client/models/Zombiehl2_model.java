package net.sirkotok.half_life_mod.entity.mob.mob_geckolib.client.models;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.core.UUIDUtil;
import net.minecraft.resources.ResourceLocation;
import net.sirkotok.half_life_mod.HalfLifeMod;
import net.sirkotok.half_life_mod.entity.mob.mob_geckolib.custom.HL2Zombie;
import software.bernie.geckolib.model.GeoModel;


public class Zombiehl2_model extends GeoModel<HL2Zombie> {

    @Override
    public ResourceLocation getModelResource(HL2Zombie animatable) {
        if (animatable.getprofile() != null) {
            if (DefaultPlayerSkin.getSkinModelName(UUIDUtil.getOrCreatePlayerUUID(animatable.getprofile())).equals("slim"))
                return new ResourceLocation(HalfLifeMod.MOD_ID, "geo/sz5_hl2_ps.geo.json");
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

