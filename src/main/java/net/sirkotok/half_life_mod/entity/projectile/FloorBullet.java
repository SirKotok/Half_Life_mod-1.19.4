package net.sirkotok.half_life_mod.entity.projectile;

import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.*;
import net.sirkotok.half_life_mod.entity.HalfLifeEntities;
import net.sirkotok.half_life_mod.entity.base.FireballNoTrail;
import net.sirkotok.half_life_mod.entity.mob.mob_geckolib.custom.Gargantua;
import net.sirkotok.half_life_mod.entity.mob.mob_geckolib.custom.Shocktrooper;
import net.sirkotok.half_life_mod.entity.particle.HalfLifeParticles;
import net.sirkotok.half_life_mod.item.HalfLifeItems;
import net.sirkotok.half_life_mod.misc.damagesource.HalfLifeDamageTypes;
import net.sirkotok.half_life_mod.sound.HalfLifeSounds;
import net.tslat.smartbrainlib.util.EntityRetrievalUtil;

import java.util.List;

public class FloorBullet extends FireballNoTrail {


    public FloorBullet(EntityType<? extends FloorBullet> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
      //  this.setMaxUpStep(1f);
    }


    public FloorBullet(Level pLevel, LivingEntity pShooter) {
        super(HalfLifeEntities.BULLET_FLOOR.get(), pShooter, pLevel);
      //  this.setMaxUpStep(1f);
    }

    public FloorBullet(Level pLevel, LivingEntity pShooter, double pOffsetX, double pOffsetY, double pOffsetZ) {
        super(HalfLifeEntities.BULLET_FLOOR.get(), pShooter, pOffsetX, pOffsetY, pOffsetZ, pLevel);
     //   this.setMaxUpStep(1f);
    }


    public FloorBullet(Level pLevel, double pX, double pY, double pZ, double pOffsetX, double pOffsetY, double pOffsetZ) {
        super(HalfLifeEntities.BULLET_FLOOR.get(), pX, pY, pZ, pOffsetX, pOffsetY, pOffsetZ, pLevel);
       // this.setMaxUpStep(1f);
    }

    public ItemStack GetAllwaysItem() {
        return HalfLifeItems.FAKE_BULLET.get().getDefaultInstance();
    }



    public static final EntityDataAccessor<Float> BULLETDAMAGE = SynchedEntityData.defineId(FloorBullet.class, EntityDataSerializers.FLOAT);
    public static final EntityDataAccessor<Float> SPEED = SynchedEntityData.defineId(FloorBullet.class, EntityDataSerializers.FLOAT);
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(BULLETDAMAGE, 19f);
        this.entityData.define(SPEED, 0.01f);
    }


    public void setdamage(float i){
        this.entityData.set(BULLETDAMAGE, i);
    }
    public float getdamage(){
        return entityData.get(BULLETDAMAGE);
    }

    public void setspeed(float i){
        this.entityData.set(SPEED, i);
    }
    public float getspeed(){
        return entityData.get(SPEED);
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
            playSound(this.getHitSound(), 0.3f, 1f);
            Entity entity = pResult.getEntity();
            Entity entity1 = this.getOwner();
            if (entity1 instanceof LivingEntity shooter) {
                entity.hurt(HalfLifeDamageTypes.bulletdamage(level.registryAccess(), this, shooter), this.getdamage());
                this.discard();
            }
        }
    }


    private boolean sound = false;

    @Override
    public void tick() {
        super.tick();
        this.checkBlock();
        if (!sound) {
        this.playSound(HalfLifeSounds.MINE_CHARGE.get(), 0.5f, 0.5f);
        this.sound = true;}
        this.setDeltaMovement(this.getDeltaMovement().normalize().scale(this.getspeed()));
        level.addParticle(HalfLifeParticles.STAT_GLOW.get(), this.getX(), this.getY(), this.getZ(), 2, 1.5d, 2);
        int k = random.nextInt(0,10);
        while (k<10) {
        level.addParticle(HalfLifeParticles.GARG_GLOW.get(), this.getX(), this.getY(), this.getZ(), 2, 0.3d, 8);
        k++;
        }

    }


    protected void checkEntity() {
        if (this.level.isClientSide()) return;
        ServerLevel pLevel = (ServerLevel) this.level;
        List<LivingEntity> hit = EntityRetrievalUtil.getEntities((Level) pLevel,
                this.getBoundingBox(), obj -> obj instanceof LivingEntity && !(obj instanceof Gargantua));
        if (!hit.isEmpty()) {
            EntityHitResult result = new EntityHitResult(hit.get(0), hit.get(0).position());
            onHit(result);
        }

    }
    protected void checkBlock() {
        BlockState state = level.getBlockState(this.blockPosition());
        BlockState below = level.getBlockState(this.blockPosition().below());
        BlockState above = level.getBlockState(this.blockPosition().above());
        this.setDeltaMovement(this.getDeltaMovement().x(), 0, this.getDeltaMovement().z());
        if (state.isAir() && below.isAir()) this.setPos(this.getX(), this.getBlockY()-1, this.getZ());
        if (state.isAir() && !below.isAir()) this.setPos(this.getX(), this.getBlockY(), this.getZ());
        if (!state.isAir() && above.isAir()) this.setPos(this.getX(), this.getBlockY()+1, this.getZ());
        if (!state.isAir() && !above.isAir()) this.discard();
    }



    protected void onHitBlock(BlockHitResult blockhit) {
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



}
