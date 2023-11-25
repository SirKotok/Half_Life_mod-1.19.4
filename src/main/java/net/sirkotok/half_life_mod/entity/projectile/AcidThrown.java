package net.sirkotok.half_life_mod.entity.projectile;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.sirkotok.half_life_mod.entity.ModEntities;
import net.sirkotok.half_life_mod.entity.base.FireballNoTrail;
import net.sirkotok.half_life_mod.particle.ModParticles;
import net.sirkotok.half_life_mod.sound.ModSounds;
import net.sirkotok.half_life_mod.util.ModTags;

public class AcidThrown extends ThrowableItemProjectile {
    public AcidThrown(EntityType<AcidThrown> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.setItem(Items.SNOWBALL.getDefaultInstance());
    }
    public AcidThrown(EntityType<AcidThrown> pEntityType, double pX, double pY, double pZ, Level pLevel) {
        this(pEntityType, pLevel);
        this.setPos(pX, pY, pZ);
    }

    public AcidThrown(EntityType<AcidThrown> pEntityType, LivingEntity pShooter, Level pLevel) {
        this(pEntityType, pShooter.getX(), pShooter.getY() + pShooter.getBbHeight(), pShooter.getZ(), pLevel);
        this.setOwner(pShooter);
    }

    public AcidThrown(Level pLevel, LivingEntity pShooter) {
        this(ModEntities.ACID_THROWN.get(), pShooter, pLevel);
    }



    protected SoundEvent getAcidSound(){
            switch (this.random.nextInt(1,3)) {
                case 1:  return ModSounds.BULLSQUID_ACID_1.get();
                case 2:  return ModSounds.BULLSQUID_ACID_2.get();
            }
            return ModSounds.HEADCRAB_1_DIE_1.get();
    }

    protected SoundEvent getHitSound(){
        switch (this.random.nextInt(1,4)) {
            case 1:  return ModSounds.BULLSQUID_SPIT_1.get();
            case 2:  return ModSounds.BULLSQUID_SPIT_2.get();
            case 3:  return ModSounds.BULLSQUID_SPIT_3.get();
        }
        return ModSounds.HEADCRAB_1_DIE_1.get();
    }

    protected void onHitEntity(EntityHitResult pResult) {
        super.onHitEntity(pResult);
        if (!this.level.isClientSide) {
            Entity entity = pResult.getEntity();
            if (entity.getType().is(ModTags.EntityTypes.FACTION_HEADCRAB)) return;
            playSound(this.getAcidSound(), 0.3f, 1f);
            Entity entity1 = this.getOwner();
            if (entity1 instanceof LivingEntity) {
                LivingEntity bullsquid = (LivingEntity) entity1;
                entity.hurt(this.damageSources().mobProjectile(this, bullsquid), 20f);
            }
        }
    }
    /*
    protected void onHitBlock(BlockHitResult blockhit) {
        super.onHitBlock(blockhit);
        int direction = blockhit.getDirection().get3DDataValue();
        if (this.level.isClientSide){
            switch(direction){
                case 0:  getLevel().addAlwaysVisibleParticle(ModParticles.SPIT_HIT.get(), blockhit.getLocation().x(), blockhit.getLocation().y()-0.01f, blockhit.getLocation().z(), direction, 0, 0);
                    break;
                case 1: getLevel().addAlwaysVisibleParticle(ModParticles.SPIT_HIT.get(), blockhit.getLocation().x(), blockhit.getLocation().y()+0.01f, blockhit.getLocation().z(), direction, 0, 0);
                    break;
                case 2: getLevel().addAlwaysVisibleParticle(ModParticles.SPIT_HIT.get(), blockhit.getLocation().x(), blockhit.getLocation().y(), blockhit.getLocation().z()-0.01f, direction, 0, 0);
                    break;
                case 3: getLevel().addAlwaysVisibleParticle(ModParticles.SPIT_HIT.get(), blockhit.getLocation().x(), blockhit.getLocation().y(), blockhit.getLocation().z()+0.01f, direction, 0, 0);
                    break;
                case 4: getLevel().addAlwaysVisibleParticle(ModParticles.SPIT_HIT.get(), blockhit.getLocation().x()-0.01f, blockhit.getLocation().y(), blockhit.getLocation().z(), direction, 0, 0);
                    break;
                case 5: getLevel().addAlwaysVisibleParticle(ModParticles.SPIT_HIT.get(), blockhit.getLocation().x()+0.01f, blockhit.getLocation().y(), blockhit.getLocation().z(), direction, 0, 0);
                    break;
            }}
        this.discard();
    } */

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


    @Override
    protected float getGravity() {
        return 0.03F;
    }

    /**
     * Returns {@code true} if other Entities should be prevented from moving through this Entity.
     */
    public boolean isPickable() {
        return false;
    }

    @Override
    protected Item getDefaultItem() {
        return Items.SNOWBALL;
    }

    /**
     * Called when the entity is attacked.
     */
    public boolean hurt(DamageSource pSource, float pAmount) {
        return false;
    }



}
