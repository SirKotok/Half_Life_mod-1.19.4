package net.sirkotok.half_life_mod.entity.mob.mob_geckolib.client.renderers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.ChatFormatting;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.Pose;
import net.sirkotok.half_life_mod.entity.mob.mob_geckolib.client.models.AlientGrunt_model;
import net.sirkotok.half_life_mod.entity.mob.mob_geckolib.client.models.Gargantua_model;
import net.sirkotok.half_life_mod.entity.mob.mob_geckolib.custom.AlienGrunt;
import net.sirkotok.half_life_mod.entity.mob.mob_geckolib.custom.Gargantua;
import net.sirkotok.half_life_mod.entity.mob.mob_geckolib.custom.Gonarch;
import org.joml.Quaternionf;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.renderer.layer.AutoGlowingGeoLayer;
import software.bernie.geckolib.util.RenderUtils;


public class GargantuaRenderer extends GeoEntityRenderer<Gargantua> {
    public GargantuaRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new Gargantua_model());
        addRenderLayer(new AutoGlowingGeoLayer<>(this));



    }




    @Override
    protected void applyRotations(Gargantua livingEntity, PoseStack poseStack, float ageInTicks, float rotationYaw, float partialTick) {
        Pose pose = livingEntity.getPose();

        if (pose != Pose.SLEEPING)
            poseStack.mulPose(Axis.YP.rotationDegrees(180f - rotationYaw));

        if (livingEntity.isAutoSpinAttack()) {
            poseStack.mulPose(Axis.XP.rotationDegrees(-90f - livingEntity.getXRot()));
            poseStack.mulPose(Axis.YP.rotationDegrees((livingEntity.tickCount + partialTick) * -75f));
        }
        else if (pose == Pose.SLEEPING) {
            Direction bedOrientation = livingEntity.getBedOrientation();
            poseStack.mulPose(Axis.YP.rotationDegrees(bedOrientation != null ? RenderUtils.getDirectionAngle(bedOrientation) : rotationYaw));
            poseStack.mulPose(Axis.ZP.rotationDegrees(getDeathMaxRotation(livingEntity)));
            poseStack.mulPose(Axis.YP.rotationDegrees(270f));
        }
        else if (livingEntity.hasCustomName()) {
            String name = livingEntity.getName().getString();
            name = ChatFormatting.stripFormatting(name);
            if (name != null && (name.equals("Dinnerbone") || name.equalsIgnoreCase("Grumm"))) {
                poseStack.translate(0, livingEntity.getBbHeight() + 0.1f, 0);
                poseStack.mulPose(Axis.ZP.rotationDegrees(180f));
            }
        }
    }



}
