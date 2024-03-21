package net.sirkotok.half_life_mod.entity.mob.mob_geckolib.client.models;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.core.UUIDUtil;
import net.minecraft.resources.ResourceLocation;
import net.sirkotok.half_life_mod.HalfLifeMod;
import net.sirkotok.half_life_mod.entity.mob.mob_geckolib.custom.HL1ZombieScientist;
import software.bernie.geckolib.model.GeoModel;


public class Zombiehl1_scientist_model extends GeoModel<HL1ZombieScientist> {

    @Override
    public ResourceLocation getModelResource(HL1ZombieScientist animatable) {
        if (animatable.getprofile() != null) {
            if (DefaultPlayerSkin.getSkinModelName(UUIDUtil.getOrCreatePlayerUUID(animatable.getprofile())).equals("slim"))
                return new ResourceLocation(HalfLifeMod.MOD_ID, "geo/sz5_ps.geo.json");
            return new ResourceLocation(HalfLifeMod.MOD_ID, "geo/sz5_p.geo.json");
        }
        return new ResourceLocation(HalfLifeMod.MOD_ID, "geo/sz6.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(HL1ZombieScientist animatable) {
        return new ResourceLocation(HalfLifeMod.MOD_ID, "textures/entity/zombiehl1_allways.png");
    }



    @Override
    public ResourceLocation getAnimationResource(HL1ZombieScientist animatable) {
        return new ResourceLocation(HalfLifeMod.MOD_ID, "animations/hl1zombie.animation.json");
    }


    @Override
    public RenderType getRenderType(HL1ZombieScientist animatable, ResourceLocation texture) {
        return RenderType.entityTranslucent(texture);
    }



}

