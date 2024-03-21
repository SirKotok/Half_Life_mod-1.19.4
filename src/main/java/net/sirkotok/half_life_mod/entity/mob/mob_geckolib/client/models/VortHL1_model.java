package net.sirkotok.half_life_mod.entity.mob.mob_geckolib.client.models;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.sirkotok.half_life_mod.HalfLifeMod;
import net.sirkotok.half_life_mod.entity.mob.mob_geckolib.custom.VortigauntHL1;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;


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

    @Override
    public void setCustomAnimations(VortigauntHL1 animatable, long instanceId, AnimationState<VortigauntHL1> animationState) {
      //  if (animatable.isangry()) return;
        CoreGeoBone head = getAnimationProcessor().getBone("head");
        if (head != null) {
            EntityModelData entityData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);

            head.setRotX(entityData.headPitch()/2 * Mth.DEG_TO_RAD);
            head.setRotY(entityData.netHeadYaw()/2 * Mth.DEG_TO_RAD);
        }
    }



}

