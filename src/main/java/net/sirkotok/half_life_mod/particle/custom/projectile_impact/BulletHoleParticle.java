package net.sirkotok.half_life_mod.particle.custom.projectile_impact;

import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.joml.Quaternionf;
import org.joml.Vector3f;

public class BulletHoleParticle extends TextureSheetParticle {

    int direction;

    protected BulletHoleParticle(ClientLevel pLevel, double pX, double pY, double pZ, SpriteSet spriteSet, double direction, double pYSpeed, double pZSpeed) {
        super(pLevel, pX, pY, pZ, 0, 0, 0);
        this.friction = 1f;
        this.xd = 0;
        this.yd = 0;
        this.zd = 0;
       // this.quadSize *= 1f;
        this.lifetime = 200;
        this.setSpriteFromAge(spriteSet);
        this.direction = (int)direction;
        this.rCol = 1f;
        this.gCol = 1f;
        this.bCol = 1f;
    }



    @Override
    public void tick() {
        super.tick();
        fadeout();
    }

    private void fadeout() {
        this.alpha = (-(1/(float)this.lifetime)*age+1);
    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }





    // Ordering index for D-U-N-S-W-E (DOWN UP NORTH SOUTH WEST EAST)
    public Quaternionf getRotation(int i) {
        Quaternionf quaternionf;
        switch (i) {
            case 0:
                quaternionf = (new Quaternionf()).rotationX((float)-Math.PI/2);
                break;
            case 1:
                quaternionf = (new Quaternionf()).rotationX((float)Math.PI/2);
                break;
            case 2:
                quaternionf = (new Quaternionf()).rotationZ((float)-Math.PI); //
                break;
            case 3:
                quaternionf = (new Quaternionf()).rotationX((float)Math.PI); //
                break;
            case 4:
                quaternionf = (new Quaternionf()).rotationY((float)Math.PI/2);
                break;
            case 5:
                quaternionf = (new Quaternionf()).rotationY((float)-Math.PI/2);
                break;
            default:
                throw new IncompatibleClassChangeError();
        }

        return quaternionf;
    }





    @Override
    public void render(VertexConsumer pBuffer, Camera pRenderInfo, float pPartialTicks) {

         Vec3 vec3 = pRenderInfo.getPosition();
            float f = (float)(Mth.lerp((double)pPartialTicks, this.xo, this.x) - vec3.x());
            float f1 = (float)(Mth.lerp((double)pPartialTicks, this.yo, this.y) - vec3.y());
            float f2 = (float)(Mth.lerp((double)pPartialTicks, this.zo, this.z) - vec3.z());

         Quaternionf quaternionf = getRotation(this.direction);



            Vector3f[] avector3f = new Vector3f[]{new Vector3f(-1.0F, -1.0F, 0.0F), new Vector3f(-1.0F, 1.0F, 0.0F), new Vector3f(1.0F, 1.0F, 0.0F), new Vector3f(1.0F, -1.0F, 0.0F)};
            float f3 = 0.1f;

            for(int i = 0; i < 4; ++i) {
                Vector3f vector3f = avector3f[i];
                vector3f.rotate(quaternionf);
                vector3f.mul(f3);
                vector3f.add(f, f1, f2);
            }

            float f6 = this.getU0();
            float f7 = this.getU1();
            float f4 = this.getV0();
            float f5 = this.getV1();
            int j = this.getLightColor(pPartialTicks);
            pBuffer.vertex((double)avector3f[0].x(), (double)avector3f[0].y(), (double)avector3f[0].z()).uv(f7, f5).color(this.rCol, this.gCol, this.bCol, this.alpha).uv2(j).endVertex();
            pBuffer.vertex((double)avector3f[1].x(), (double)avector3f[1].y(), (double)avector3f[1].z()).uv(f7, f4).color(this.rCol, this.gCol, this.bCol, this.alpha).uv2(j).endVertex();
            pBuffer.vertex((double)avector3f[2].x(), (double)avector3f[2].y(), (double)avector3f[2].z()).uv(f6, f4).color(this.rCol, this.gCol, this.bCol, this.alpha).uv2(j).endVertex();
            pBuffer.vertex((double)avector3f[3].x(), (double)avector3f[3].y(), (double)avector3f[3].z()).uv(f6, f5).color(this.rCol, this.gCol, this.bCol, this.alpha).uv2(j).endVertex();
        }



    @OnlyIn(Dist.CLIENT)
    public static class Provider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet sprites;

        public Provider(SpriteSet spriteSet) {
            this.sprites = spriteSet;
        }

        public Particle createParticle(SimpleParticleType particleType, ClientLevel level,
                                       double x, double y, double z,
                                       double dx, double dy, double dz) {
            return new BulletHoleParticle(level, x, y, z, this.sprites, dx, dy, dz);
        }
    }




}




/*
 @Override
 public void renderParticle(Tessellator tessellator, float p_70539_2_, float p_70539_3_, float p_70539_4_, float p_70539_5_, float p_70539_6_, float p_70539_7_) {

 */