package net.sirkotok.half_life_mod.entity.projectile;

import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
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
import net.sirkotok.half_life_mod.entity.mob_geckolib.custom.VortigauntHL1;
import net.sirkotok.half_life_mod.item.HalfLifeItems;
import net.sirkotok.half_life_mod.particle.HalfLifeParticles;
import net.sirkotok.half_life_mod.sound.HalfLifeSounds;

public class Bullet extends FireballNoTrail {



    public Bullet(EntityType<? extends Bullet> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }


    public Bullet(Level pLevel, LivingEntity pShooter) {
        super(HalfLifeEntities.BULLET_ONE.get(), pShooter, pLevel);
    }

    public Bullet(Level pLevel, LivingEntity pShooter, double pOffsetX, double pOffsetY, double pOffsetZ) {
        super(HalfLifeEntities.BULLET_ONE.get(), pShooter, pOffsetX, pOffsetY, pOffsetZ, pLevel);
    }


    public Bullet(Level pLevel, double pX, double pY, double pZ, double pOffsetX, double pOffsetY, double pOffsetZ) {
        super(HalfLifeEntities.BULLET_ONE.get(), pX, pY, pZ, pOffsetX, pOffsetY, pOffsetZ, pLevel);
    }

    public ItemStack GetAllwaysItem() {
        return HalfLifeItems.FAKE_BULLET.get().getDefaultInstance();
    }



    public static final EntityDataAccessor<Float> BULLETDAMAGE = SynchedEntityData.defineId(Bullet.class, EntityDataSerializers.FLOAT);
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(BULLETDAMAGE, 3.5f);
    }


    public void setdamage(float i){
        this.entityData.set(BULLETDAMAGE, i);
    }
    public float getdamage(){
        return entityData.get(BULLETDAMAGE);
    }




    @Override
    protected boolean shouldBurn() {
        return false;
    }


    protected SoundEvent getHitSound(){
        switch (this.random.nextInt(1,3)) {
            case 1:  return HalfLifeSounds.BULLET_HIT_1.get();
            case 2:  return HalfLifeSounds.BULLET_HIT_2.get();
        }
        return HalfLifeSounds.HEADCRAB_1_DIE_1.get();
    }



    protected void onHitEntity(EntityHitResult pResult) {
        super.onHitEntity(pResult);
        if (!this.level.isClientSide) {
            Entity entity = pResult.getEntity();
            Entity entity1 = this.getOwner();
            if (entity1 instanceof LivingEntity) {
                LivingEntity shooter = (LivingEntity) entity1;
                entity.hurt(this.damageSources().mobProjectile(this, shooter), this.getdamage());
            }
        }
    }
    // entity.hurt( new DamageSource(getHolderOrThro(ModDamageTypes.HL_BULLET), getdamage());
 // new DamageSource(this.damageTypes.getHolderOrThrow(pDamageTypeKey));
 // ModDamageSources.shotbullet(this, shooter)
// this.level.damageSources().source(ModDamageTypes.HL_BULLET)

    public void makeParticle(int direction, BlockHitResult blockhit){
        switch(direction){
            case 0:  this.getLevel().addAlwaysVisibleParticle(HalfLifeParticles.BULLET_HOLE.get(), blockhit.getLocation().x(), blockhit.getLocation().y()-0.01f, blockhit.getLocation().z(), direction, 0, 0);
                break;
            case 1: this.getLevel().addAlwaysVisibleParticle(HalfLifeParticles.BULLET_HOLE.get(), blockhit.getLocation().x(), blockhit.getLocation().y()+0.01f, blockhit.getLocation().z(), direction, 0, 0);
                break;
            case 2: this.getLevel().addAlwaysVisibleParticle(HalfLifeParticles.BULLET_HOLE.get(), blockhit.getLocation().x(), blockhit.getLocation().y(), blockhit.getLocation().z()-0.01f, direction, 0, 0);
                break;
            case 3: this.getLevel().addAlwaysVisibleParticle(HalfLifeParticles.BULLET_HOLE.get(), blockhit.getLocation().x(), blockhit.getLocation().y(), blockhit.getLocation().z()+0.01f, direction, 0, 0);
                break;
            case 4: this.getLevel().addAlwaysVisibleParticle(HalfLifeParticles.BULLET_HOLE.get(), blockhit.getLocation().x()-0.01f, blockhit.getLocation().y(), blockhit.getLocation().z(), direction, 0, 0);
                break;
            case 5: this.getLevel().addAlwaysVisibleParticle(HalfLifeParticles.BULLET_HOLE.get(), blockhit.getLocation().x()+0.01f, blockhit.getLocation().y(), blockhit.getLocation().z(), direction, 0, 0);
                break;
        }
    }



    protected void onHitBlock(BlockHitResult blockhit) {
        super.onHitBlock(blockhit);
       // Ordering index for D-U-N-S-W-E (DOWN UP NORTH SOUTH WEST EAST)
        int direction = blockhit.getDirection().get3DDataValue();
        if (this.level.isClientSide()){
        this.makeParticle(direction, blockhit);
        this.discard();
    }}

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
