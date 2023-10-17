package net.sirkotok.half_life_mod.entity.mob_normal.client.renderers;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.sirkotok.half_life_mod.HalfLifeMod;
import net.sirkotok.half_life_mod.entity.mob_normal.client.ModModelLayers;
import net.sirkotok.half_life_mod.entity.mob_normal.client.models.Barnacle_Model;
import net.sirkotok.half_life_mod.entity.mob_normal.custom.Barnacle;

import javax.annotation.Nullable;

public class Barnacle_Renderer extends MobRenderer<Barnacle, Barnacle_Model<Barnacle>> {

    public Barnacle_Renderer(EntityRendererProvider.Context pContext) {
        super(pContext, new Barnacle_Model<>(pContext.bakeLayer(ModModelLayers.BARNACLE_LAYER)), 0f);
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
        super.setupRotations(pEntityLiving, pMatrixStack, pAgeInTicks, pRotationYaw + 180.0F, pPartialTicks);
        pMatrixStack.translate(0.0D, 0.5D, 0.0D);
        pMatrixStack.mulPose(pEntityLiving.getAttachFace().getOpposite().getRotation());
        pMatrixStack.translate(0.0D, -0.5D, 0.0D);
    }

}

/*
 * Returns the location of an entity's texture. */

    /* public ResourceLocation getTextureLocation(Shulker pEntity) {
    return getTextureLocation(pEntity.getColor());
    }

    public static ResourceLocation getTextureLocation(@Nullable DyeColor pColor) {
        return pColor == null ? DEFAULT_TEXTURE_LOCATION : TEXTURE_LOCATION[pColor.getId()];
    }

    protected void setupRotations(Shulker pEntityLiving, PoseStack pMatrixStack, float pAgeInTicks, float pRotationYaw, float pPartialTicks) {
        super.setupRotations(pEntityLiving, pMatrixStack, pAgeInTicks, pRotationYaw + 180.0F, pPartialTicks);
        pMatrixStack.translate(0.0D, 0.5D, 0.0D);
        pMatrixStack.mulPose(pEntityLiving.getAttachFace().getOpposite().getRotation());
        pMatrixStack.translate(0.0D, -0.5D, 0.0D);
    }

     */


