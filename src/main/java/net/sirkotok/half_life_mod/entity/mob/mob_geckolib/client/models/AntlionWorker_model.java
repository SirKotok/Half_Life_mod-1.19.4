package net.sirkotok.half_life_mod.entity.mob.mob_geckolib.client.models;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.sirkotok.half_life_mod.HalfLifeMod;
import net.sirkotok.half_life_mod.entity.mob.mob_geckolib.custom.AntlionWorker;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;


public class AntlionWorker_model extends GeoModel<AntlionWorker> {

    @Override
    public ResourceLocation getModelResource(AntlionWorker animatable) {
        return new ResourceLocation(HalfLifeMod.MOD_ID, "geo/antlionworker.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(AntlionWorker animatable) {
        return new ResourceLocation(HalfLifeMod.MOD_ID, "textures/entity/antlionworker.png");
    }


    @Override
    public ResourceLocation getAnimationResource(AntlionWorker animatable) {
        return new ResourceLocation(HalfLifeMod.MOD_ID, "animations/antlionworker.animation.json");
    }

    @Override
    public RenderType getRenderType(AntlionWorker animatable, ResourceLocation texture) {
        return RenderType.entityTranslucent(texture);
    }

    @Override
    public void setCustomAnimations(AntlionWorker animatable, long instanceId, AnimationState<AntlionWorker> animationState) {

        CoreGeoBone head = getAnimationProcessor().getBone("head");
        if (head != null) {
            EntityModelData entityData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);

            head.setRotX(entityData.headPitch()/2.3f * Mth.DEG_TO_RAD);
            head.setRotY(entityData.netHeadYaw()/2.3f * Mth.DEG_TO_RAD);
        }
    }




}

