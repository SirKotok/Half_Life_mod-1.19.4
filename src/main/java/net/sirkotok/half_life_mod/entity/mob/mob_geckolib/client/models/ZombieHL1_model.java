package net.sirkotok.half_life_mod.entity.mob.mob_geckolib.client.models;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.core.UUIDUtil;
import net.minecraft.resources.ResourceLocation;
import net.sirkotok.half_life_mod.HalfLifeMod;
import net.sirkotok.half_life_mod.entity.mob.mob_geckolib.custom.ZombieHl1;
import software.bernie.geckolib.model.GeoModel;


public class ZombieHL1_model extends GeoModel<ZombieHl1> {

    @Override
    public ResourceLocation getModelResource(ZombieHl1 animatable) {
        if (animatable.getprofile() != null) {
            if (DefaultPlayerSkin.getSkinModelName(UUIDUtil.getOrCreatePlayerUUID(animatable.getprofile())).equals("slim"))
                return new ResourceLocation(HalfLifeMod.MOD_ID, "geo/sz5_ps.geo.json");
            return new ResourceLocation(HalfLifeMod.MOD_ID, "geo/zombie2_hl1.geo.json");
        }
        return new ResourceLocation(HalfLifeMod.MOD_ID, "geo/zombie2_hl1.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(ZombieHl1 animatable) {
        return new ResourceLocation(HalfLifeMod.MOD_ID, "textures/entity/zombiehl1_base.png");
    }



    @Override
    public ResourceLocation getAnimationResource(ZombieHl1 animatable) {
        return new ResourceLocation(HalfLifeMod.MOD_ID, "animations/zombie2_hl1.animation.json");
    }


    @Override
    public RenderType getRenderType(ZombieHl1 animatable, ResourceLocation texture) {
        return RenderType.entityTranslucent(texture);
    }



}

