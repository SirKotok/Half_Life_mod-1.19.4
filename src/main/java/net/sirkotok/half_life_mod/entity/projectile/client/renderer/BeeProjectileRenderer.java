package net.sirkotok.half_life_mod.entity.projectile.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;
import net.sirkotok.half_life_mod.HalfLifeMod;
import net.sirkotok.half_life_mod.entity.mob.mob_geckolib.custom.Snark;
import net.sirkotok.half_life_mod.entity.projectile.BeeProjectile;
import net.sirkotok.half_life_mod.entity.projectile.PitdroneSpike;
import net.sirkotok.half_life_mod.entity.projectile.arrowlike.Flechette;
import net.sirkotok.half_life_mod.entity.projectile.client.model.Beeprojectile_model;
import net.sirkotok.half_life_mod.entity.projectile.client.model.Spike_model;
import org.joml.Matrix3f;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import static net.minecraft.client.renderer.texture.OverlayTexture.NO_OVERLAY;
public class BeeProjectileRenderer extends GeoEntityRenderer<BeeProjectile> {
    public BeeProjectileRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new Beeprojectile_model());
    }


    @Override
    public void render(BeeProjectile entity, float entityYaw,
                       float partialTick, PoseStack poseStack,
                       MultiBufferSource bufferSource, int packedLight) {
      //  poseStack.scale(0.9f, 0.9f, 0.9f);

            float yRot = entity.getYRot();
            float XRot = entity.getXRot();
            poseStack.rotateAround(new Quaternionf().rotationY(-yRot), 0, 0, 0);
            poseStack.rotateAround(new Quaternionf().rotationX(-XRot), 0, 0, 0);

        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }

    @Override
    public void preRender(PoseStack poseStack, BeeProjectile animatable, BakedGeoModel model, MultiBufferSource bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, float red, float green, float blue,
                          float alpha) {
        this.scaleModelForRender(0.6f, 0.6f, poseStack, animatable, model, isReRender, partialTick, packedLight, packedOverlay);
    }




}
