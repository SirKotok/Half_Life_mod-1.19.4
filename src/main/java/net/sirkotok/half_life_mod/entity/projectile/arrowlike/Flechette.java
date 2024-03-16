package net.sirkotok.half_life_mod.entity.projectile.arrowlike;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.sirkotok.half_life_mod.entity.HalfLifeEntities;
import net.sirkotok.half_life_mod.sound.HalfLifeSounds;
import net.tslat.smartbrainlib.util.EntityRetrievalUtil;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.List;

public class Flechette extends AbstractArrow implements GeoEntity{

    // TODO: make the rotation correct

    public boolean start = false;
    public int remaining = 0;
    public Flechette(EntityType<? extends AbstractArrow> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public Flechette(EntityType<? extends AbstractArrow> pEntityType, double pX, double pY, double pZ, Level pLevel) {
        super(pEntityType, pX, pY, pZ, pLevel);
    }

    public Flechette(Level pLevel, LivingEntity pShooter) {
        this(HalfLifeEntities.FLECHETTE.get(), pShooter.getX(), pShooter.getY(), pShooter.getZ(), pLevel);
        this.setOwner(pShooter);
        this.setRot(pShooter.getYRot(), pShooter.getXRot());
    }


    @Override
    public void tick() {
        super.tick();


        if (this.inGround) start = true;
        if (start) {
            if (remaining == 1) {
                this.playSound(this.getPreExplodeSound(), 0.2f, 1);
            }
            remaining++;
            if (remaining == 30 && !this.level.isClientSide()) {
                this.playSound(this.getExplodeSound(), 0.3f, 1);
                this.discard();
                ServerLevel serverlevel = (ServerLevel) level;
                Entity entity = this.getOwner();
                BlockPos pBlockPos = this.blockPosition();
                int rad = 2;
                List<LivingEntity> targets = EntityRetrievalUtil.getEntities((Level) serverlevel,
                        new AABB(pBlockPos.getX() - rad, pBlockPos.getY() - rad, pBlockPos.getZ() - rad,
                                pBlockPos.getX() + rad, pBlockPos.getY() + rad, pBlockPos.getZ() + rad), obj -> obj instanceof LivingEntity);

                for (LivingEntity entity2 : targets) {
                    if (entity2.equals(entity)) {
                       entity2.hurt(this.damageSources().mobProjectile(this, (LivingEntity) entity), 2f);
                    } else {
                        entity2.hurt(this.damageSources().mobProjectile(this, (LivingEntity) entity), 4f);
                    }
                }


            }
        }
    }

    @Override
    protected void onHitEntity(EntityHitResult pResult) {
        this.setBaseDamage(1D);
        this.playSound(this.getfleshhitSound(), 0.5f, 1);
        super.onHitEntity(pResult);
        this.discard();
    }

    @Override
    protected void onHitBlock(BlockHitResult pResult) {
        this.playSound(this.getGroundhitSound(), 0.5f, 1);
        super.onHitBlock(pResult);
    }

    protected ItemStack getPickupItem() {
        return null;
    }


    protected SoundEvent getPreExplodeSound() {
        switch (this.random.nextInt(1,4)) {
            case 1:  return HalfLifeSounds.HUNTER_FLECHETTE_PREEXPLODE2.get();
            case 2:  return HalfLifeSounds.HUNTER_FLECHETTE_PREEXPLODE3.get();
            case 3:  return HalfLifeSounds.HUNTER_FLECHETTE_PREEXPLODE1.get();
        }
        return SoundEvents.FROG_STEP;
    }

    protected SoundEvent getGroundhitSound() {
        switch (this.random.nextInt(1,6)) {
            case 1:  return HalfLifeSounds.FLECHETTE_IMPACT_STICK1.get();
            case 2:  return HalfLifeSounds.FLECHETTE_IMPACT_STICK2.get();
            case 3:  return HalfLifeSounds.FLECHETTE_IMPACT_STICK3.get();
            case 4:  return HalfLifeSounds.FLECHETTE_IMPACT_STICK5.get();
            case 5:  return HalfLifeSounds.FLECHETTE_IMPACT_STICK4.get();
        }
        return SoundEvents.FROG_STEP;
    }
    protected SoundEvent getfleshhitSound() {
        switch (this.random.nextInt(1,5)) {
            case 1:  return HalfLifeSounds.FLECHETTE_FLESH_IMPACT1.get();
            case 2:  return HalfLifeSounds.FLECHETTE_FLESH_IMPACT2.get();
            case 3:  return HalfLifeSounds.FLECHETTE_FLESH_IMPACT3.get();
            case 4:  return HalfLifeSounds.FLECHETTE_FLESH_IMPACT4.get();
        }
        return SoundEvents.FROG_STEP;
    }

    protected SoundEvent getExplodeSound() {
        switch (this.random.nextInt(1,4)) {
            case 1:  return HalfLifeSounds.FLECHETTE_EXPLODE1.get();
            case 2:  return HalfLifeSounds.FLECHETTE_EXPLODE2.get();
            case 3:  return HalfLifeSounds.FLECHETTE_EXPLODE3.get();
        }
        return SoundEvents.FROG_STEP;
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
