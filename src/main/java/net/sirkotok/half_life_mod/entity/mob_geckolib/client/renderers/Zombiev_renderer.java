package net.sirkotok.half_life_mod.entity.mob_geckolib.client.renderers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.core.UUIDUtil;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.DyeColor;
import net.sirkotok.half_life_mod.HalfLifeMod;
import net.sirkotok.half_life_mod.entity.mob_geckolib.client.models.Zombiehl2_model;
import net.sirkotok.half_life_mod.entity.mob_geckolib.client.models.Zombiev_model;
import net.sirkotok.half_life_mod.entity.mob_geckolib.client.renderers.renderlayer.VZombieProffesionLayer;
import net.sirkotok.half_life_mod.entity.mob_geckolib.custom.HL2Zombie;
import net.sirkotok.half_life_mod.entity.mob_geckolib.custom.HLZombieVillager;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.renderer.DynamicGeoEntityRenderer;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.renderer.layer.GeoRenderLayer;

import javax.annotation.Nullable;


public class Zombiev_renderer extends DynamicGeoEntityRenderer<HLZombieVillager> { //
    public Zombiev_renderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new Zombiev_model());
        addRenderLayer(new VZombieProffesionLayer(this));
    }
    private static final ResourceLocation VILLAGER_BASE_SKIN = new ResourceLocation("textures/entity/villager/villager.png");




/*
    @Override
    public void applyRenderLayersForBone(PoseStack poseStack, HLZombieVillager animatable, GeoBone bone, RenderType renderType, MultiBufferSource bufferSource, VertexConsumer buffer, float partialTick, int packedLight, int packedOverlay) {

        if (bone.getName().equals("be"))
            super.applyRenderLayersForBone(poseStack, animatable, bone, renderType, bufferSource, buffer, partialTick, packedLight, packedOverlay);
        if (bone.getName().equals("by"))
            super.applyRenderLayersForBone(poseStack, animatable, bone, renderType, bufferSource, buffer, partialTick, packedLight, packedOverlay);
        if (bone.getName().equals("ou"))
            super.applyRenderLayersForBone(poseStack, animatable, bone, renderType, bufferSource, buffer, partialTick, packedLight, packedOverlay);
        if (bone.getName().equals("bi"))
            super.applyRenderLayersForBone(poseStack, animatable, bone, renderType, bufferSource, buffer, partialTick, packedLight, packedOverlay);
        if (bone.getName().equals("ob"))
            super.applyRenderLayersForBone(poseStack, animatable, bone, renderType, bufferSource, buffer, partialTick, packedLight, packedOverlay);


    } */



    @Nullable
       @Override
     protected ResourceLocation getTextureOverrideForBone(GeoBone bone, HLZombieVillager animatable, float partialTick) {

        if (bone.getName().equals("be"))
            return  VILLAGER_BASE_SKIN;
        if (bone.getName().equals("by"))
            return  VILLAGER_BASE_SKIN;
        if (bone.getName().equals("ou"))
            return  VILLAGER_BASE_SKIN;
        if (bone.getName().equals("bi"))
            return  VILLAGER_BASE_SKIN;
        if (bone.getName().equals("ob"))
            return  VILLAGER_BASE_SKIN;



        return null;
        }

    }
