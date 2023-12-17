package net.sirkotok.half_life_mod.entity.projectile.client;

import net.minecraft.core.BlockPos;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.sirkotok.half_life_mod.entity.HalfLifeEntities;
import net.sirkotok.half_life_mod.entity.base.FireballNoTrail;
import net.sirkotok.half_life_mod.entity.projectile.Bullet;
import net.sirkotok.half_life_mod.item.HalfLifeItems;
import net.sirkotok.half_life_mod.sound.HalfLifeSounds;
import net.sirkotok.half_life_mod.worldgen.dimension.HalfLifeDimensions;
import net.sirkotok.half_life_mod.worldgen.portal.XenTeleporter;

public class TeleportingBullet extends FireballNoTrail {



    public TeleportingBullet(EntityType<? extends TeleportingBullet> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }


    public TeleportingBullet(Level pLevel, LivingEntity pShooter) {
        super(HalfLifeEntities.BULLET_TP.get(), pShooter, pLevel);
    }

    public TeleportingBullet(Level pLevel, LivingEntity pShooter, double pOffsetX, double pOffsetY, double pOffsetZ) {
        super(HalfLifeEntities.BULLET_TP.get(), pShooter, pOffsetX, pOffsetY, pOffsetZ, pLevel);
    }


    public TeleportingBullet(Level pLevel, double pX, double pY, double pZ, double pOffsetX, double pOffsetY, double pOffsetZ) {
        super(HalfLifeEntities.BULLET_TP.get(), pX, pY, pZ, pOffsetX, pOffsetY, pOffsetZ, pLevel);
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


    @Override
    protected void onHitEntity(EntityHitResult pResult) {
        Entity target = pResult.getEntity();
        if (target.canChangeDimensions()) {
            if (target instanceof Mob) {
            ((Mob) target).setPersistenceRequired();
            }
            handleHalfLifePortal(target, target.blockPosition());
        }
    }

    protected SoundEvent getHitSound(){
        switch (this.random.nextInt(1,3)) {
            case 1:  return HalfLifeSounds.BULLET_HIT_1.get();
            case 2:  return HalfLifeSounds.BULLET_HIT_2.get();
        }
        return HalfLifeSounds.HEADCRAB_1_DIE_1.get();
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

    /**
     * Called when the entity is attacked.
     */
    public boolean hurt(DamageSource pSource, float pAmount) {
        return false;
    }

    private void handleHalfLifePortal(Entity target, BlockPos pPos) {
        if (target.level instanceof ServerLevel serverlevel) {
            MinecraftServer minecraftserver = serverlevel.getServer();
            ResourceKey<Level> resourcekey = target.level.dimension() == HalfLifeDimensions.HALFLIFE_LEVEL_KEY ?
                    Level.OVERWORLD : HalfLifeDimensions.HALFLIFE_LEVEL_KEY;
            ServerLevel portalDimension = minecraftserver.getLevel(resourcekey);
            if (portalDimension != null && !target.isPassenger()) {
                if(resourcekey == HalfLifeDimensions.HALFLIFE_LEVEL_KEY) {
                    target.changeDimension(portalDimension, new XenTeleporter(pPos, true));
                } else {
                    target.changeDimension(portalDimension, new XenTeleporter(pPos, false));
                }
            }
        }
    }


}
