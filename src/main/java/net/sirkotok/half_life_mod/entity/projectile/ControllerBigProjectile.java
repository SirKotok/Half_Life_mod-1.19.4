package net.sirkotok.half_life_mod.entity.projectile;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.sirkotok.half_life_mod.entity.HalfLifeEntities;
import net.sirkotok.half_life_mod.entity.base.FireballNoTrail;
import net.sirkotok.half_life_mod.entity.base.HomingFballNoTrail;
import net.sirkotok.half_life_mod.item.HalfLifeItems;
import net.sirkotok.half_life_mod.sound.HalfLifeSounds;

public class ControllerBigProjectile extends HomingFballNoTrail {

    public ControllerBigProjectile(EntityType<ControllerBigProjectile> pEntityType, Level pLevel) {
        super(pEntityType, pLevel, null);
    }
    public ControllerBigProjectile(EntityType<ControllerBigProjectile> pEntityType, Level pLevel, LivingEntity pTarget) {
        super(pEntityType, pLevel, pTarget);
    }

    public ControllerBigProjectile(Level pLevel, LivingEntity pShooter, LivingEntity pTarget) {
        super(HalfLifeEntities.CON_BIG_PROJECTILE.get(), pShooter, pLevel, pTarget);
    }

    public ControllerBigProjectile(Level pLevel, LivingEntity pShooter, double pOffsetX, double pOffsetY, double pOffsetZ, LivingEntity pTarget) {
        super(HalfLifeEntities.CON_BIG_PROJECTILE.get(), pShooter, pOffsetX, pOffsetY, pOffsetZ, pLevel, pTarget);
    }

    public ControllerBigProjectile(Level pLevel, double pX, double pY, double pZ, double pOffsetX, double pOffsetY, double pOffsetZ, LivingEntity pTarget) {
        super(HalfLifeEntities.CON_BIG_PROJECTILE.get(), pX, pY, pZ, pOffsetX, pOffsetY, pOffsetZ, pLevel, pTarget);
    }


    @Override
    public void tick() {
        super.tick();
    }

    @Override
    protected boolean shouldBurn() {
        return false;
    }


    @Override
    public ItemStack GetAllwaysItem() {
        return HalfLifeItems.FAKE_CON.get().getDefaultInstance();
    }


    protected SoundEvent getHitSound() {
        return HalfLifeSounds.CON_ELECTRO4.get();
    }


    public float getDamageEmount(){
        return 3f;
    }




    protected void onHitEntity(EntityHitResult pResult) {
        super.onHitEntity(pResult);
        if (!this.level.isClientSide) {
            Entity entity = pResult.getEntity();
            Entity entity1 = this.getOwner();
            if (entity1 instanceof LivingEntity) {
                LivingEntity shooter = (LivingEntity) entity1;
                entity.hurt(this.damageSources().mobProjectile(this, shooter), getDamageEmount());
            }
        }
    }
    protected void onHitBlock(BlockHitResult blockhit) {
        super.onHitBlock(blockhit);
        }

    protected void onHit(HitResult pResult) {
        super.onHit(pResult);
        if (!this.level.isClientSide) {
            playSound(this.getHitSound(), 0.3f, 1f);
            this.discard();
        }

    }


    public boolean isPickable() {
        return false;
    }


    public boolean hurt(DamageSource pSource, float pAmount) {
        return false;
    }





}
