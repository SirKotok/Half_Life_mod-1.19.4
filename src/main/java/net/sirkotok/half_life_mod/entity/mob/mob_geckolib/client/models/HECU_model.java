package net.sirkotok.half_life_mod.entity.mob.mob_geckolib.client.models;

import net.minecraft.resources.ResourceLocation;
import net.sirkotok.half_life_mod.HalfLifeMod;
import net.sirkotok.half_life_mod.entity.mob.mob_geckolib.custom.HecuGrunt;
import net.sirkotok.half_life_mod.entity.mob.mob_geckolib.custom.Shocktrooper;
import software.bernie.geckolib.core.animation.Animation;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.model.GeoModel;

public class HECU_model extends GeoModel<HecuGrunt> {

    @Override
    public ResourceLocation getModelResource(HecuGrunt animatable) {
       int i = animatable.getGunType();
       switch (i){
               case 1:
                   return new ResourceLocation(HalfLifeMod.MOD_ID, "geo/hecu_shotgun.geo.json");
               case 2:
                   return new ResourceLocation(HalfLifeMod.MOD_ID, "geo/hecu_pistol.geo.json");
               case 3:
                   return new ResourceLocation(HalfLifeMod.MOD_ID, "geo/hecu_deagle.geo.json");
               case 4:
                   return new ResourceLocation(HalfLifeMod.MOD_ID, "geo/hecu_saw.geo.json");
       }

        return new ResourceLocation(HalfLifeMod.MOD_ID, "geo/hecu_smg.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(HecuGrunt animatable) {
        return new ResourceLocation(HalfLifeMod.MOD_ID, "textures/entity/hecu/hecu_base.png");
    }


    @Override
    public ResourceLocation getAnimationResource(HecuGrunt animatable) {
        return new ResourceLocation(HalfLifeMod.MOD_ID, "animations/hecu.animation.json");
    }



}
