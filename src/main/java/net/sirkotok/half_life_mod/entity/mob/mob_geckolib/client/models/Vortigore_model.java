package net.sirkotok.half_life_mod.entity.mob.mob_geckolib.client.models;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.sirkotok.half_life_mod.HalfLifeMod;
import net.sirkotok.half_life_mod.entity.mob.mob_geckolib.custom.Voltigore;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;


public class Vortigore_model extends GeoModel<Voltigore> {

    @Override
    public ResourceLocation getModelResource(Voltigore animatable) {
        return new ResourceLocation(HalfLifeMod.MOD_ID, "geo/vortigore.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(Voltigore animatable) {
        if (animatable.isBB()) return new ResourceLocation(HalfLifeMod.MOD_ID, "textures/entity/baby_vortigore.png");
        return new ResourceLocation(HalfLifeMod.MOD_ID, "textures/entity/vortigore.png");
    }


    @Override
    public ResourceLocation getAnimationResource(Voltigore animatable) {
        return new ResourceLocation(HalfLifeMod.MOD_ID, "animations/vortigore.animation.json");
    }

    @Override
    public void setCustomAnimations(Voltigore animatable, long instanceId, AnimationState<Voltigore> animationState) {

        CoreGeoBone head = getAnimationProcessor().getBone("head");
        if (head != null) {
            EntityModelData entityData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);

            head.setRotX(entityData.headPitch()/2 * Mth.DEG_TO_RAD);
            head.setRotY(entityData.netHeadYaw()/2 * Mth.DEG_TO_RAD);
        }
    }



}

