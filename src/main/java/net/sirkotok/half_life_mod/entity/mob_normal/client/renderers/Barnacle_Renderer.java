package net.sirkotok.half_life_mod.entity.mob_normal.client.renderers;

import com.github.alexthe666.citadel.client.render.LightningBoltData;
import com.github.alexthe666.citadel.client.render.LightningRender;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.sirkotok.half_life_mod.HalfLifeMod;
import net.sirkotok.half_life_mod.entity.mob_geckolib.custom.VortigauntHL1;
import net.sirkotok.half_life_mod.entity.mob_normal.client.ModModelLayers;
import net.sirkotok.half_life_mod.entity.mob_normal.client.models.Barnacle_Model;
import net.sirkotok.half_life_mod.entity.mob_normal.custom.Barnacle;
import net.sirkotok.half_life_mod.util.HLperUtil;
import org.joml.Vector4f;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Barnacle_Renderer extends MobRenderer<Barnacle, Barnacle_Model<Barnacle>> {

    public Barnacle_Renderer(EntityRendererProvider.Context pContext) {
        super(pContext, new Barnacle_Model<>(
                pContext.bakeLayer(ModModelLayers.BARNACLE_LAYER))
                , 0f);

    }

    @Override
    public ResourceLocation getTextureLocation(Barnacle pEntity) {
        return new ResourceLocation(HalfLifeMod.MOD_ID, "textures/entity/barnacle.png");
    }



    public Vec3 getRenderOffset(Barnacle pEntity, float pPartialTicks) {
        return pEntity.getRenderPosition(pPartialTicks).orElse(super.getRenderOffset(pEntity, pPartialTicks));
    }

    public boolean shouldRender(Barnacle pLivingEntity, Frustum pCamera, double pCamX, double pCamY, double pCamZ) {
        return super.shouldRender(pLivingEntity, pCamera, pCamX, pCamY, pCamZ) ? true : pLivingEntity.getRenderPosition(0.0F).filter((p_174374_) -> {
            EntityType<?> entitytype = pLivingEntity.getType();
            float f = entitytype.getHeight() / 2.0F;
            float f1 = entitytype.getWidth() / 2.0F;
            Vec3 vec3 = Vec3.atBottomCenterOf(pLivingEntity.blockPosition());
            return pCamera.isVisible((new AABB(p_174374_.x, p_174374_.y + (double)f, p_174374_.z, vec3.x, vec3.y + (double)f, vec3.z)).inflate((double)f1, (double)f, (double)f1));
        }).isPresent();
    }

    protected void setupRotations(Barnacle pEntityLiving, PoseStack pMatrixStack, float pAgeInTicks, float pRotationYaw, float pPartialTicks) {
        setupRotationsstep1(pEntityLiving, pMatrixStack, pAgeInTicks, pRotationYaw + 180.0F, pPartialTicks);
        pMatrixStack.translate(0.0D, 0.5D, 0.0D);
        pMatrixStack.mulPose(pEntityLiving.getAttachFace().getOpposite().getRotation());
        pMatrixStack.translate(0.0D, -0.5D, 0.0D);
    }



    protected void setupRotationsstep1(Barnacle pEntityLiving, PoseStack pMatrixStack, float pAgeInTicks, float pRotationYaw, float pPartialTicks) {

        if (this.isShaking(pEntityLiving)) {
            pRotationYaw += (float)(Math.cos((double)pEntityLiving.tickCount * 3.25D) * Math.PI * (double)0.4F);
        }

        if (!pEntityLiving.hasPose(Pose.SLEEPING)) {
            pMatrixStack.mulPose(Axis.YP.rotationDegrees(180.0F - pRotationYaw));
        }

        if (pEntityLiving.isAutoSpinAttack()) {
            pMatrixStack.mulPose(Axis.XP.rotationDegrees(-90.0F - pEntityLiving.getXRot()));
            pMatrixStack.mulPose(Axis.YP.rotationDegrees(((float)pEntityLiving.tickCount + pPartialTicks) * -75.0F));
        }  else if (isEntityUpsideDown(pEntityLiving)) {
            pMatrixStack.translate(0.0F, pEntityLiving.getBbHeight() + 0.1F, 0.0F);
            pMatrixStack.mulPose(Axis.ZP.rotationDegrees(180.0F));
        }

    }
    @Override
    public void render(Barnacle pEntity, float pEntityYaw, float pPartialTicks, PoseStack pMatrixStack, MultiBufferSource pBuffer, int pPackedLight) {
        this.model.prepareMobModel(pEntity);
        super.render(pEntity, pEntityYaw, pPartialTicks, pMatrixStack, pBuffer, pPackedLight);
    }
}




