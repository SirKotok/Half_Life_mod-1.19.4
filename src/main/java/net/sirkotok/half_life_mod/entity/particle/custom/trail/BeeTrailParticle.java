package net.sirkotok.half_life_mod.entity.particle.custom.trail;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.sirkotok.half_life_mod.HalfLifeMod;

public class BeeTrailParticle extends TrailParticle {
    private static final ResourceLocation TRAIL_TEXTURE = new ResourceLocation(HalfLifeMod.MOD_ID, "textures/particle/trail.png");

    private double flowX;
    private double flowY;
    private double flowZ;

    private double originX;

    private double originY;

    private double originZ;

    private double targetX;

    private double targetY;

    private double targetZ;

    public BeeTrailParticle(ClientLevel world, double x, double y, double z, double xd, double yd, double zd) {
        super(world, x, y, z, 0, 0, 0);
        flowX = xd;
        flowY = yd;
        flowZ = zd;
        originX = x;
        originY = y;
        originZ = z;
        targetX = flowX;
        targetY = flowY;
        targetZ = flowZ;
        this.trailA = 0F;
        this.lifetime = 20 + this.random.nextInt(10);
        this.gravity = 0;
    }


    public void tick() {
        float fade = 1F - age / (float) lifetime;
        this.trailA = 0.5F * fade;

        Vec3 vec3 = distanceVec(targetX, targetY, targetZ);
        if (distanceVec(flowX, flowY, flowZ).length() < 1) {
            targetX = originX;
            targetY = originY;
            targetZ = originZ;
        } else if (distanceVec(originX, originY, originZ).length() < 1) {
            targetX = flowX;
            targetY = flowY;
            targetZ = flowZ;
        }
        Vec3 movement = vec3.normalize().scale(0.02F);
        this.xd += movement.x;
        this.yd += movement.y;
        this.zd += movement.z;
        super.tick();
    }

    public int sampleCount() {
        return 30;
    }

    public int sampleStep() {
        return 2;
    }

    private Vec3 distanceVec(double x, double y, double z) {
        return new Vec3(x, y, z).subtract(this.x, this.y, this.z);
    }

    @Override
    public float getTrailHeight() {
        return 0.3F;
    }

    public int getLightColor(float f) {
        return 240;
    }

    @Override
    public ResourceLocation getTrailTexture() {
        return TRAIL_TEXTURE;
    }

    @OnlyIn(Dist.CLIENT)
    public static class Factory implements ParticleProvider<SimpleParticleType> {

        public Factory() {
        }

        public Particle createParticle(SimpleParticleType typeIn, ClientLevel worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            BeeTrailParticle particle = new BeeTrailParticle(worldIn, x, y, z, xSpeed, ySpeed, zSpeed);
            particle.trailR = 0.9F + worldIn.random.nextFloat() * 0.1F;
            particle.trailG = 0.2F + worldIn.random.nextFloat() * 0.05F;
            particle.trailB = 0.2F + worldIn.random.nextFloat() * 0.05F;
            return particle;
        }
    }

}
