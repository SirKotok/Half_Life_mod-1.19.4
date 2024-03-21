package net.sirkotok.half_life_mod.entity.projectile;

import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.sirkotok.half_life_mod.entity.HalfLifeEntities;
import net.sirkotok.half_life_mod.entity.base.FireballNoTrail;
import net.sirkotok.half_life_mod.entity.mob.mob_geckolib.custom.Antlion;
import net.sirkotok.half_life_mod.entity.mob.mob_geckolib.custom.AntlionWorker;
import net.sirkotok.half_life_mod.entity.mob.mob_geckolib.custom.VortigauntHL1;
import net.sirkotok.half_life_mod.entity.mob.mob_geckolib.custom.VortigauntHL2;
import net.sirkotok.half_life_mod.item.HalfLifeItems;
import net.sirkotok.half_life_mod.entity.particle.HalfLifeParticles;

public class VortLightningProjectile extends FireballNoTrail {


    @Override
    public void tick() {
        super.tick();
        if (this.tickCount > getTime()) this.discard();
    }

    public VortLightningProjectile(EntityType<? extends VortLightningProjectile> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }


    public VortLightningProjectile(Level pLevel, LivingEntity pShooter) {
        super(HalfLifeEntities.VORT_LIGHTNING_PROJETILE.get(), pShooter, pLevel);
    }

    public VortLightningProjectile(Level pLevel, LivingEntity pShooter, double pOffsetX, double pOffsetY, double pOffsetZ) {
        super(HalfLifeEntities.VORT_LIGHTNING_PROJETILE.get(), pShooter, pOffsetX, pOffsetY, pOffsetZ, pLevel);
    }


    public VortLightningProjectile(Level pLevel, double pX, double pY, double pZ, double pOffsetX, double pOffsetY, double pOffsetZ) {
        super(HalfLifeEntities.VORT_LIGHTNING_PROJETILE.get(), pX, pY, pZ, pOffsetX, pOffsetY, pOffsetZ, pLevel);
    }

    public ItemStack GetAllwaysItem() {
        return HalfLifeItems.FAKE_BULLET.get().getDefaultInstance();
    }


    public static final EntityDataAccessor<Integer> TIME = SynchedEntityData.defineId(VortLightningProjectile.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Float> BULLETDAMAGE = SynchedEntityData.defineId(VortLightningProjectile.class, EntityDataSerializers.FLOAT);
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(BULLETDAMAGE, 10f);
        this.entityData.define(TIME, 3);
    }


    public void setdamage(float i){
        this.entityData.set(BULLETDAMAGE, i);
    }
    public float getdamage(){
        return entityData.get(BULLETDAMAGE);
    }


    public void setTime(int i){
        this.entityData.set(TIME, i);
    }
    public float getTime(){
        return entityData.get(TIME);
    }

    @Override
    protected boolean shouldBurn() {
        return false;
    }





    protected void onHitEntity(EntityHitResult pResult) {
        super.onHitEntity(pResult);
        if (this.level.isClientSide && this.getOwner() instanceof VortigauntHL2) {
            Vec3 vec31 = pResult.getLocation();
            this.level.addParticle(HalfLifeParticles.STAT_GLOW.get(), vec31.x, vec31.y, vec31.z, 1, 1d, 10);
        }
        if (!this.level.isClientSide) {
            Entity entity = pResult.getEntity();
            Entity entity1 = this.getOwner();
            if (entity1 instanceof LivingEntity) {
                LivingEntity shooter = (LivingEntity) entity1;
                if (random.nextFloat() < 0.5f) {
                int i = 0;
                if (shooter instanceof VortigauntHL1) i = 1;
                if (shooter instanceof VortigauntHL2) i = 2;
                if (entity instanceof AntlionWorker) ((AntlionWorker) entity).flipover(i);
                if (entity instanceof Antlion) ((Antlion) entity).flipover(i);
                }
                entity.hurt(this.damageSources().mobProjectile(this, shooter), this.getdamage());
            }
        }
    }




    protected void onHitBlock(BlockHitResult blockhit) {
        super.onHitBlock(blockhit);
        if (!this.level.isClientSide()){
        this.discard();
    }}

    protected void onHit(HitResult pResult) {

        HitResult.Type hitresult$type = pResult.getType();
        if (hitresult$type == HitResult.Type.ENTITY) {
            EntityHitResult result = (EntityHitResult)pResult;
            if (result.getEntity() == this.getOwner() && this.getOwner() instanceof VortigauntHL1) return;
        }



        super.onHit(pResult);
        if (!this.level.isClientSide) {
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
