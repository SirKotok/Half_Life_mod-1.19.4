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

public class VortArcLightningParticle extends Particle {

    private LightningRender lightningRender = new LightningRender();
    private static final Vector4f LIGHTNING_COLOR = new Vector4f(0.1F , 0.8F, 0.1F, 0.3F);

    public VortArcLightningParticle(ClientLevel world, double x, double y, double z, double xd, double yd, double zd) {
        super(world, x, y, z);
        this.setSize(6.0F, 6.0F);
        this.x = x;
        this.y = y;
        this.z = z;
    //    Vec3 to = findLightningToPos (this.level, x, y, z, new Vec3(xd, yd, zd)); //.add(x, y, z)
    //    Vec3 lightningTo  = to.subtract(x, y, z);
    //    lightningTo = lightningTo.add(lightningTo.scale(0.1f));
        Vec3 lightningTo = new Vec3(xd, yd, zd);
        this.lifetime = (int) Math.ceil(lightningTo.length());
        int sections = 4 * this.lifetime;
        LightningBoltData.BoltRenderInfo boltData = new LightningBoltData.BoltRenderInfo(0.01F, 0.01F, 0.1F, 0.1F, LIGHTNING_COLOR, 0.2F);
        LightningBoltData bolt = new LightningBoltData(boltData, Vec3.ZERO, new Vec3(xd, yd, zd), sections)
                .size(0.1F + random.nextFloat() * 0.2F)
                .fade(LightningBoltData.FadeFunction.fade(0.5F))
                .lifespan(this.lifetime + 1)
                .spawn(LightningBoltData.SpawnFunction.delay(2));  //LightningBoltData.SpawnFunction.NO_DELAY
        lightningRender.update(this, bolt, 1.0F);
    }

    public boolean shouldCull() {
        return false;
    }


    private Vec3 findLightningToPos(ClientLevel world, double x, double y, double z, Vec3 pos) {
        Vec3 vec3 = new Vec3(x, y, z);
        int j = 10;
        double  dx = (pos.x - vec3.x)/j;
        double  dy = (pos.y - vec3.y)/j;
        double  dz = (pos.z - vec3.z)/j;
        for (int i = 0; i < j; i++) {
            Vec3 vec31 = vec3.add(dx, dy, dz);
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
            return new VortArcLightningParticle(worldIn, x, y, z, xSpeed, ySpeed, zSpeed);
        }
    }
}