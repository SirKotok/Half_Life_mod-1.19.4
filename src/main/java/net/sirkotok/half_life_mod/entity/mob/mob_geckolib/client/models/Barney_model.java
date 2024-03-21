package net.sirkotok.half_life_mod.entity.mob.mob_geckolib.client.models;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EquipmentSlot;
import net.sirkotok.half_life_mod.HalfLifeMod;
import net.sirkotok.half_life_mod.entity.mob.mob_geckolib.custom.Barney;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;


public class Barney_model extends GeoModel<Barney> {

    @Override
    public ResourceLocation getModelResource(Barney animatable) {
        return new ResourceLocation(HalfLifeMod.MOD_ID, "geo/guard.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(Barney animatable) {
        if (animatable.getItemBySlot(EquipmentSlot.HEAD).isEmpty()) {
            if (animatable.getItemBySlot(EquipmentSlot.CHEST).isEmpty()) {
                return new ResourceLocation(HalfLifeMod.MOD_ID, "textures/entity/barney_with_no_armor.png");
            } else return new ResourceLocation(HalfLifeMod.MOD_ID, "textures/entity/barney_with_armor_vest.png");
        }
        if (animatable.getItemBySlot(EquipmentSlot.CHEST).isEmpty()) {
            return new ResourceLocation(HalfLifeMod.MOD_ID, "textures/entity/barney_with_armor_helmet.png");
        }
        return new ResourceLocation(HalfLifeMod.MOD_ID, "textures/entity/barney_with_armor.png");

    }


    @Override
    public ResourceLocation getAnimationResource(Barney animatable) {
        return new ResourceLocation(HalfLifeMod.MOD_ID, "animations/guard.animation.json");
    }


    @Override
    public void setCustomAnimations(Barney animatable, long instanceId, AnimationState<Barney> animationState) {
        if (animatable.hlisangry()) return;
        CoreGeoBone head = getAnimationProcessor().getBone("head");
        if (head != null) {
            EntityModelData entityData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);

            head.setRotX(entityData.headPitch() * Mth.DEG_TO_RAD);
            head.setRotY(entityData.netHeadYaw() * Mth.DEG_TO_RAD);
        }
    }
}

