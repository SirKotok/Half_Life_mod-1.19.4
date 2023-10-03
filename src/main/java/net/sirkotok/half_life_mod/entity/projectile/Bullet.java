package net.sirkotok.half_life_mod.entity.projectile;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.sirkotok.half_life_mod.entity.ModEntities;
import net.sirkotok.half_life_mod.entity.base.FireballNoTrail;
import net.sirkotok.half_life_mod.item.ModItems;
import net.sirkotok.half_life_mod.sound.ModSounds;

public class Bullet extends FireballNoTrail {

    public Bullet(EntityType<Bullet> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    //    this.setItem(ModItems.FAKE_BULLET.get().getDefaultInstance());
    }


    public Bullet(Level pLevel, LivingEntity pShooter) {
        super(ModEntities.BULLET_ONE.get(), pShooter, pLevel);

    }
    public Bullet(Level pLevel, LivingEntity pShooter, double pOffsetX, double pOffsetY, double pOffsetZ) {
        super(ModEntities.BULLET_ONE.get(), pShooter, pOffsetX, pOffsetY, pOffsetZ, pLevel);

    }

    public Bullet(Level pLevel, double pX, double pY, double pZ, double pOffsetX, double pOffsetY, double pOffsetZ) {
        super(ModEntities.BULLET_ONE.get(), pX, pY, pZ, pOffsetX, pOffsetY, pOffsetZ, pLevel);
    }

    public ItemStack GetAllwaysItem() {
        return ModItems.FAKE_BULLET.get().getDefaultInstance();
    }



    @Override
    protected boolean shouldBurn() {
        return false;
    }


    protected SoundEvent getHitSound(){
        switch (this.random.nextInt(1,3)) {
            case 1:  return ModSounds.BULLET_HIT_1.get();
            case 2:  return ModSounds.BULLET_HIT_2.get();
        }
        return ModSounds.HEADCRAB_1_DIE_1.get();
    }

    public float getDamangeEmount(){
        return 3.5f;
    }

    protected void onHitEntity(EntityHitResult pResult) {
        super.onHitEntity(pResult);
        if (!this.level.isClientSide) {
            Entity entity = pResult.getEntity();
            Entity entity1 = this.getOwner();
            if (entity1 instanceof LivingEntity) {
                LivingEntity shooter = (LivingEntity) entity1;
                entity.hurt(this.damageSources().mobProjectile(this, shooter), getDamangeEmount());
            }
        }
    }

    protected void onHitBlock(BlockHitResult pResult) {
        super.onHitBlock(pResult);
    }

    /**
     * Called when this EntityFireball hits a block or entity.
     */
    protected void onHit(HitResult pResult) {
        super.onHit(pResult);
        if (!this.level.isClientSide) {
            playSound(this.getHitSound(), 0.3f, 1f);
            this.discard();
        }
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



}
