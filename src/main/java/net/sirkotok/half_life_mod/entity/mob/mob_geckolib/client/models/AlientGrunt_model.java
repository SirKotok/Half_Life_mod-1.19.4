package net.sirkotok.half_life_mod.entity.mob.mob_geckolib.client.models;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.sirkotok.half_life_mod.HalfLifeMod;
import net.sirkotok.half_life_mod.entity.base.HalfLifeMonster;
import net.sirkotok.half_life_mod.entity.mob.mob_geckolib.custom.AlienGrunt;
import net.sirkotok.half_life_mod.entity.mob.mob_geckolib.custom.VortigauntHL1;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;


public class AlientGrunt_model extends GeoModel<AlienGrunt> {

    @Override
    public ResourceLocation getModelResource(AlienGrunt animatable) {
        return new ResourceLocation(HalfLifeMod.MOD_ID, "geo/aliengrunt.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(AlienGrunt animatable) {
        return new ResourceLocation(HalfLifeMod.MOD_ID, "textures/entity/aliengrunt.png");
    }


    @Override
    public ResourceLocation getAnimationResource(AlienGrunt animatable) {
        return new ResourceLocation(HalfLifeMod.MOD_ID, "animations/aliengrunt.animation.json");
    }
    @Override
    public void setCustomAnimations(AlienGrunt animatable, long instanceId, AnimationState<AlienGrunt> animationState) {
        if (animatable.iseating()) return;
        CoreGeoBone head = getAnimationProcessor().getBone("head");
        if (head != null) {
            EntityModelData entityData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);

            head.setRotX(entityData.headPitch()/2 * Mth.DEG_TO_RAD);
            head.setRotY(entityData.netHeadYaw()/2 * Mth.DEG_TO_RAD);
        }
    }


}

