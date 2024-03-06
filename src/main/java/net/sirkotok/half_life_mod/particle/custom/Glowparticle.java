package net.sirkotok.half_life_mod.particle.custom;

import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.world.level.LightLayer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class Glowparticle extends TextureSheetParticle {

    private final int halfLife;

    public Glowparticle(ClientLevel level, SpriteSet spriteSet, double x, double y, double z, double ColorID, double Size, double lifetime) {
        super(level, x, y, z, 0.0D, 0.0D, 0.0D);
        this.xd *= 0;
        this.yd *= 0;
        this.zd *= 0;
        if (ColorID == 1) {
        this.rCol = 0.1F;
        this.gCol = 0.8F;
        this.bCol = 0.1F; }
        else  {
            this.rCol = 0.99F;
            this.gCol = 0.64F;
            this.bCol = 0.01F;
        }
        this.quadSize = (float) Size;
        this.lifetime = 1+(int)lifetime;
        this.setSpriteFromAge(spriteSet);
        this.halfLife = this.lifetime / 2;
        this.hasPhysics = true;
    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }

    @Override
    public void render(VertexConsumer buffer, Camera camera, float partialTicks) {
        this.alpha = this.getGlowBrightness();
        super.render(buffer, camera, partialTicks);
    }

    @Override
    public void tick() {
        super.tick();
    }
    public float getGlowBrightness() {
        return 1;
        /*
        int lifeTime = this.lifetime - this.age;
        if (lifeTime <= this.halfLife) {
            return (float) lifeTime / (float) this.halfLife;
        } else {
            return Math.max(1.0f - (((float) lifeTime - this.halfLife) / this.halfLife), 0);
        } */
    }

    @Override
    public int getLightColor(float partialTicks) {
        return 0xF000F0;
    }

    @OnlyIn(Dist.CLIENT)
    public static class Provider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet sprites;

        public Provider(SpriteSet spriteSet) {
            this.sprites = spriteSet;
        }

        @Override
        public Particle createParticle(SimpleParticleType type, ClientLevel level, double x, double y, double z, double Color_ID, double Size, double zSpeed) {
            return new Glowparticle(level, this.sprites,
                    x, y, z, Color_ID, Size, zSpeed);

        }
    }



}
