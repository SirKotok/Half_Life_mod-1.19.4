package net.sirkotok.half_life_mod.entity.projectile;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Fireball;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.*;
import net.sirkotok.half_life_mod.entity.HalfLifeEntities;
import net.sirkotok.half_life_mod.entity.base.AbstractBulletProjectile;
import net.sirkotok.half_life_mod.entity.base.FireballNoTrail;
import net.sirkotok.half_life_mod.entity.mob.mob_geckolib.custom.Voltigore;
import net.sirkotok.half_life_mod.entity.particle.HalfLifeParticles;
import net.sirkotok.half_life_mod.misc.util.HLperUtil;
import net.sirkotok.half_life_mod.misc.util.InfightingUtil;
import net.sirkotok.half_life_mod.sound.HalfLifeSounds;
import net.tslat.smartbrainlib.util.EntityRetrievalUtil;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.Comparator;
import java.util.List;

public class BeeProjectile extends FireballNoTrail implements GeoEntity {
    public BeeProjectile(EntityType<BeeProjectile> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.amogus = true;
    }
    public BeeProjectile(Level pLevel, LivingEntity pShooter, boolean amogus) {
        this(HalfLifeEntities.BEE_PROJECTILE.get(), pShooter.getX(), pShooter.getEyeY() - (double)0.4F, pShooter.getZ(), pLevel, amogus);
        this.setOwner(pShooter);
    }

    protected BeeProjectile(EntityType<BeeProjectile> pEntityType, double pX, double pY, double pZ, Level pLevel, boolean amogus) {
        super(pEntityType, pLevel);
        this.setPos(pX, pY, pZ);
        this.amogus = amogus;
    }
    public BeeProjectile(Level pLevel, LivingEntity pShooter, double pOffsetX, double pOffsetY, double pOffsetZ) {
        super(HalfLifeEntities.BEE_PROJECTILE.get(), pShooter, pOffsetX, pOffsetY, pOffsetZ, pLevel);
        this.amogus = true;
    }

    public BeeProjectile(Level pLevel, double pX, double pY, double pZ, double pOffsetX, double pOffsetY, double pOffsetZ) {
        super(HalfLifeEntities.BEE_PROJECTILE.get(), pX, pY, pZ, pOffsetX, pOffsetY, pOffsetZ, pLevel);
        this.amogus = true;
    }


    @Override
    protected float getInertia() {
        return 1;
    }


    public Vec3 previouspos;

    public final boolean amogus;

    @Override
    public void tick() {
        super.tick();

      if (this.previouspos == null) this.previouspos = this.position();


      level.addParticle(HalfLifeParticles.STAT_GLOW.get(), this.position().x(), this.position().y(), this.position().z(), 0, 0.1, 3);



        if (this.tickCount % 7 == 0 && this.random.nextFloat() < 0.4f) this.playSound(this.getBuzzSound());
        if (!this.level.isClientSide() && this.tickCount % 5 == 0 && this.amogus) {
        ServerLevel serverlevel = (ServerLevel) level;
        BlockPos pBlockPos = this.blockPosition();
            int rad = 7;
            List<LivingEntity> targets = EntityRetrievalUtil.getEntities((Level) serverlevel,
                    new AABB(pBlockPos.getX() - rad, pBlockPos.getY() - rad, pBlockPos.getZ() - rad,
                            pBlockPos.getX() + rad, pBlockPos.getY() + rad, pBlockPos.getZ() + rad), obj -> !(obj instanceof Player player && (player.isCreative() || player.isSpectator())) && !obj.equals(this.getOwner()) && (obj instanceof LivingEntity entity4 && this.getOwner() instanceof LivingEntity livingowner && !(InfightingUtil.issameteam(livingowner, entity4))));
            if (!targets.isEmpty()) {
            targets.sort(Comparator.comparingDouble(this::distanceToSqr));
            LivingEntity target = targets.get(0);
                Vec3 vec3 = this.getDeltaMovement();
                double k = vec3.length();
                if (k < 1) k = 1;
                vec3 = vec3.normalize().add(target.getEyePosition().subtract(this.position()).normalize()).normalize().scale(k);
                this.setDeltaMovement(vec3);
           //     this.previouspos = this.position();
            }
        }
        HLperUtil.rotateToOppositeVec3(this, this.getDeltaMovement());
    }

    @Override
    protected boolean shouldBurn() {
        return false;
    }

    int richochet = 0;

    protected void onHitEntity(EntityHitResult pResult) {
        super.onHitEntity(pResult);
        if (!this.level.isClientSide) {
            this.playSound(this.getHitSound());
            Entity entity = pResult.getEntity();
            Entity entity1 = this.getOwner();
            if (!(entity1 == entity)) {
            if (entity1 instanceof LivingEntity) {
                LivingEntity bullsquid = (LivingEntity) entity1;
                entity.hurt(this.damageSources().mobProjectile(this, bullsquid), 3f);
            }
            }
        }
        if (!this.level.isClientSide) {
            this.discard();
        }
    }

    protected int assignOneOrMinusOneTo0(int i){
        if (i == 0) {
            return random.nextFloat() < 0.5f ? 1 : -1;
        } else return 3*i;
    }

    protected void onHitBlock(BlockHitResult blockhit) {
        super.onHitBlock(blockhit);
        Vec3i vec3i = blockhit.getDirection().getNormal();
        vec3i = vec3i.offset(assignOneOrMinusOneTo0(vec3i.getX()), assignOneOrMinusOneTo0(vec3i.getY()), assignOneOrMinusOneTo0(vec3i.getZ()));
        Vec3 vec3 = new Vec3(random.nextFloat()*random.nextFloat()*vec3i.getX(), random.nextFloat()*random.nextFloat()*vec3i.getY(), random.nextFloat()*random.nextFloat()*vec3i.getZ());
        this.richochet++;
        if (!this.amogus) discard();
        if (this.richochet > 6) this.discard();
        else { this.setDeltaMovement(vec3);
        this.previouspos = this.position(); }
    }

    /**
     * Called when this EntityFireball hits a block or entity.
     */

    public SoundEvent getBuzzSound(){
        switch(random.nextInt(3)) {
            case 0: return HalfLifeSounds.AG_BUZZ1.get();
            case 1: return HalfLifeSounds.AG_BUZZ2.get();
            case 2: return HalfLifeSounds.AG_BUZZ3.get();
        }
        return HalfLifeSounds.AG_BUZZ1.get();
    }
    public SoundEvent getHitSound(){
        switch(random.nextInt(3)) {
            case 0: return HalfLifeSounds.AG_HORNETHIT1.get();
            case 1: return HalfLifeSounds.AG_HORNETHIT2.get();
            case 2: return HalfLifeSounds.AG_HORNETHIT3.get();
        }
        return HalfLifeSounds.AG_BUZZ1.get();
    }

    protected void onHit(HitResult pResult) {
        super.onHit(pResult);
    }

    /**
     * Returns {@code true} if other Entities should be prevented from moving through this Entity.
     */
    public boolean isPickable() {
        return false;
    }

    /**
     * Called when the entity is attacked.
     */
    public boolean hurt(DamageSource pSource, float pAmount) {
        return false;
    }

    private AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }
}
