package net.sirkotok.half_life_mod.entity.mob.mob_geckolib.client.renderers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.ChatFormatting;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.Pose;
import net.sirkotok.half_life_mod.entity.mob.mob_geckolib.custom.Gonarch;
import net.sirkotok.half_life_mod.entity.mob.mob_geckolib.client.models.Gonarch_model;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.util.RenderUtils;


public class Gonarch_renderer extends GeoEntityRenderer<Gonarch> {
    public Gonarch_renderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new Gonarch_model());
    }



    @Override
    public void render(Gonarch entity, float entityYaw,
                       float partialTick, PoseStack poseStack,
                       MultiBufferSource bufferSource, int packedLight) {
        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }

    @Override
    protected void applyRotations(Gonarch animatable, PoseStack poseStack, float ageInTicks, float rotationYaw, float partialTick) {
        Pose pose = animatable.getPose();
        Gonarch livingEntity = animatable;

        if (pose != Pose.SLEEPING)
            poseStack.mulPose(Axis.YP.rotationDegrees(180f - rotationYaw));

        if (livingEntity.isAutoSpinAttack()) {
            poseStack.mulPose(Axis.XP.rotationDegrees(-90f - livingEntity.getXRot()));
            poseStack.mulPose(Axis.YP.rotationDegrees((livingEntity.tickCount + partialTick) * -75f));
        }
        else if (pose == Pose.SLEEPING) {
            Direction bedOrientation = livingEntity.getBedOrientation();
            poseStack.mulPose(Axis.YP.rotationDegrees(bedOrientation != null ? RenderUtils.getDirectionAngle(bedOrientation) : rotationYaw));
            poseStack.mulPose(Axis.ZP.rotationDegrees(getDeathMaxRotation(animatable)));
            poseStack.mulPose(Axis.YP.rotationDegrees(270f));
        }
        else if (animatable.hasCustomName()) {
            String name = animatable.getName().getString();
            name = ChatFormatting.stripFormatting(name);
            if (name != null && (name.equals("Dinnerbone") || name.equalsIgnoreCase("Grumm"))) {
                poseStack.translate(0, animatable.getBbHeight() + 0.1f, 0);
                poseStack.mulPose(Axis.ZP.rotationDegrees(180f));
            }
        }
    }


    /*
    @Override
    public void preRender(PoseStack poseStack, Gonarch animatable, BakedGeoModel model, MultiBufferSource bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, float red, float green, float blue,
                          float alpha) {
        this.entityRenderTranslations = new Matrix4f(poseStack.last().pose());

    } */

}


