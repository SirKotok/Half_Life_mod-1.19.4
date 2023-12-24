package net.sirkotok.half_life_mod.worldgen.dimension.specialeffects;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.DimensionSpecialEffects;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix4f;

public class XenSpecialEffects extends DimensionSpecialEffects {

    public XenSpecialEffects() {
        super(256f, false, SkyType.END, false, false);
    }

    @Override
    public Vec3 getBrightnessDependentFogColor(Vec3 p_108894_, float p_108895_) {
        return p_108894_.scale((double)0.15F);
    }

    public boolean isFoggyAt(int p_108891_, int p_108892_) {
        return false;
    }


    @Override
    public boolean renderClouds(ClientLevel level, int ticks, float partialTick, PoseStack poseStack, double camX, double camY, double camZ, Matrix4f projectionMatrix) {
        return true;
    }

    @Override
    public boolean renderSky(ClientLevel level, int ticks, float partialTick, PoseStack poseStack, Camera camera, Matrix4f projectionMatrix, boolean isFoggy, Runnable setupFog) {
        return true;
    }

    @Override
    public boolean renderSnowAndRain(ClientLevel level, int ticks, float partialTick, LightTexture lightTexture, double camX, double camY, double camZ) {
        return true;
    }

    @Override
    public boolean tickRain(ClientLevel level, int ticks, Camera camera) {
        return true;
    }
}
