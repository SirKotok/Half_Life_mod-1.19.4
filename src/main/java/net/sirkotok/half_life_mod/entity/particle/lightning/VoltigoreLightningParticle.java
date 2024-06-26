package net.sirkotok.half_life_mod.entity.particle.lightning;

import com.github.alexthe666.citadel.client.render.LightningBoltData;
import com.github.alexthe666.citadel.client.render.LightningRender;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.Mth;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.joml.Vector4f;

public class VoltigoreLightningParticle extends Particle {
    private LightningRender lightningRender = new LightningRender();

    public VoltigoreLightningParticle(ClientLevel world, double x, double y, double z, double color, double yd, double zd) {
        super(world, x, y, z);
        this.setSize(1.0F, 1.0F);
        this.x = x;
        this.y = y;
        this.z = z;
        this.xd = 0;
        this.yd = 0;
        this.zd = 0;
        Vec3 lightningTo = findLightningToPos(world, x, y, z, random.nextInt(4,6));
        Vec3 to = lightningTo.subtract(x, y, z);
        this.lifetime = (int) Math.ceil(to.length());
        int sections = 6 * this.lifetime;
        Vector4f ColorVec = new Vector4f(0.5F , 0.01F, 0.99F, 0.3F);
        if (color == 10 ) ColorVec = new Vector4f(0.1F , 0.8F, 0.1F, 0.3F);
        LightningBoltData.BoltRenderInfo boltData = new LightningBoltData.BoltRenderInfo(0.01F, 0.15F, 0.1F, 0.05F, ColorVec , 0.6F);
        LightningBoltData bolt = new LightningBoltData(boltData, Vec3.ZERO, to, sections)
                .size(0.1F + random.nextFloat() * 0.1F)
                .lifespan(5)
                .spawn(LightningBoltData.SpawnFunction.delay(3));
        lightningRender.update(this, bolt, 1.0F);
    }

    public boolean shouldCull() {
        return false;
    }

    private Vec3 findLightningToPos(ClientLevel world, double x, double y, double z, int range) {
        Vec3 vec3 = new Vec3(x, y, z);
        for (int i = 0; i < 10; i++) {
            Vec3 vec31 = vec3.add(random.nextFloat() * range - range / 2F, random.nextFloat() * range - range / 2F, random.nextFloat() * range - range / 2F);
            if (canSeeBlock(vec3, vec31)) {
                return vec31;
            }
        }
        return vec3;
    }

    private boolean canSeeBlock(Vec3 from, Vec3 to) {
        BlockHitResult result = this.level.clip(new ClipContext(from, to, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, null));
        return Vec3.atCenterOf(result.getBlockPos()).distanceTo(to) < 3.0F;
    }


    public void tick() {
        this.xo = this.x;
        this.yo = this.y;
        this.zo = this.z;
        this.xd = 0;
        this.yd = 0;
        this.zd = 0;
        if (this.age++ >= this.lifetime) {
            this.remove();
        } else {
            this.move(this.xd, this.yd, this.zd);
            this.yd -= (double) this.gravity;
        }
    }


    public void render(VertexConsumer consumer, Camera camera, float partialTick) {
        MultiBufferSource.BufferSource multibuffersource$buffersource = Minecraft.getInstance().renderBuffers().bufferSource();
        Vec3 cameraPos = camera.getPosition();
        float x = (float) (Mth.lerp((double) partialTick, this.xo, this.x));
        float y = (float) (Mth.lerp((double) partialTick, this.yo, this.y));
        float z = (float) (Mth.lerp((double) partialTick, this.zo, this.z));
        PoseStack posestack = new PoseStack();
        posestack.pushPose();
        posestack.translate(-cameraPos.x, -cameraPos.y, -cameraPos.z);
        posestack.translate(x, y, z);
        lightningRender.render(partialTick, posestack, multibuffersource$buffersource);
        multibuffersource$buffersource.endBatch();
        posestack.popPose();
    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.CUSTOM;
    }

    @OnlyIn(Dist.CLIENT)
    public static class Factory implements ParticleProvider<SimpleParticleType> {

        public Factory() {
        }

        public Particle createParticle(SimpleParticleType typeIn, ClientLevel worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            return new VoltigoreLightningParticle(worldIn, x, y, z, xSpeed, ySpeed, zSpeed);
        }
    }


}
