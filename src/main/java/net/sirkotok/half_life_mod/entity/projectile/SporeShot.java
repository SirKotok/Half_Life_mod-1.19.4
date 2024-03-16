package net.sirkotok.half_life_mod.entity.projectile;

import net.minecraft.core.BlockPos;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import net.sirkotok.half_life_mod.entity.HalfLifeEntities;
import net.sirkotok.half_life_mod.entity.particle.HalfLifeParticles;
import net.sirkotok.half_life_mod.misc.util.HLperUtil;
import net.tslat.smartbrainlib.util.EntityRetrievalUtil;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.List;

public class SporeShot extends ThrowableProjectile implements GeoEntity {

    //TODO: implement this thing. Its currently broken but for some reason gives bullet holes 100% of the time? what? need to fix it and make it work as it should.
    public SporeShot(EntityType<? extends ThrowableProjectile> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public SporeShot(Level pLevel, LivingEntity pShooter) {
        super(HalfLifeEntities.SPORESHOT.get(), pShooter, pLevel);
    }



    public static final EntityDataAccessor<Float> JUMP = SynchedEntityData.defineId(SporeShot.class, EntityDataSerializers.FLOAT);
    public static final EntityDataAccessor<Integer> BOUNCE_ID = SynchedEntityData.defineId(SporeShot.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Integer> TICkS_REMAINING =  SynchedEntityData.defineId(SporeShot.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Integer> JUMP_TIMESTAMP =  SynchedEntityData.defineId(SporeShot.class, EntityDataSerializers.INT);




    @Override
    protected void defineSynchedData() {
        this.entityData.define(JUMP, 1f);
        this.entityData.define(BOUNCE_ID, 0);
        this.entityData.define(TICkS_REMAINING , 100);
        this.entityData.define(JUMP_TIMESTAMP , 0);
    }


    public int getbounce() {
        return this.entityData.get(BOUNCE_ID);
    }
    public void setbounce(int bounce_id, float jump){
        this.entityData.set(BOUNCE_ID, bounce_id);
        this.entityData.set(JUMP, jump);
    }

    public float getJump(){
     return this.entityData.get(JUMP);
    }

    public void setlifetime(int ticks){
        this.entityData.set(TICkS_REMAINING, ticks);
    }
    public int getlifetime(){
        return this.entityData.get(TICkS_REMAINING);
    }

    public void setJumpTimestamp(int ticks){
        this.entityData.set(JUMP_TIMESTAMP, ticks);
    }
    public int getjumptimestamp(){
        return this.entityData.get(JUMP_TIMESTAMP);
    }

    public boolean candojump(){
        return this.tickCount - this.getjumptimestamp() > 5;
    }




    public void explode(){
        Entity entity1 = this.getOwner();
        BlockPos pBlockPos = this.blockPosition();
        if (entity1 != null) {
            if (!this.level.isClientSide) {
                ServerLevel serverlevel = (ServerLevel) level;
                int rad = 2;
                List<LivingEntity> targets = EntityRetrievalUtil.getEntities((Level) serverlevel,
                        new AABB(pBlockPos.getX() - rad, pBlockPos.getY() - rad, pBlockPos.getZ() - rad,
                                pBlockPos.getX() + rad, pBlockPos.getY() + rad, pBlockPos.getZ() + rad), obj -> obj instanceof LivingEntity);
                if (entity1 instanceof LivingEntity) {
                    for (LivingEntity entity : targets) {
                        HLperUtil.DisableShieldFor(entity, 0.1f, 20, serverlevel);
                        entity.hurt(this.damageSources().mobProjectile(this, (LivingEntity) entity1 ), 8f); }
                }}
            this.discard();
    }}


    @Override
    public void tick() {
        super.tick();

        if (this.tickCount > this.getlifetime()){
            this.explode();
        }
    }

    @Override
    protected void onHitBlock(BlockHitResult blockhit) {
        super.onHitBlock(blockhit);
        int direction = blockhit.getDirection().get3DDataValue();
        if (this.level.isClientSide()){
            this.makeParticle(direction, blockhit);
            if (this.getbounce() == 1) this.explode();
            if (this.candojump()) this.changedirection(direction, blockhit);
        }
    }

    @Override
    protected void onHitEntity(EntityHitResult pResult) {
        this.explode();
    }

    public void makeParticle(int direction, BlockHitResult blockhit) {
        switch(direction){
            case 0:  this.getLevel().addAlwaysVisibleParticle(HalfLifeParticles.SPIT_HIT.get(), blockhit.getLocation().x(), blockhit.getLocation().y()-0.01f, blockhit.getLocation().z(), direction, 0, 0);
                break;
            case 1: this.getLevel().addAlwaysVisibleParticle(HalfLifeParticles.SPIT_HIT.get(), blockhit.getLocation().x(), blockhit.getLocation().y()+0.01f, blockhit.getLocation().z(), direction, 0, 0);
                break;
            case 2: this.getLevel().addAlwaysVisibleParticle(HalfLifeParticles.SPIT_HIT.get(), blockhit.getLocation().x(), blockhit.getLocation().y(), blockhit.getLocation().z()-0.01f, direction, 0, 0);
                break;
            case 3: this.getLevel().addAlwaysVisibleParticle(HalfLifeParticles.SPIT_HIT.get(), blockhit.getLocation().x(), blockhit.getLocation().y(), blockhit.getLocation().z()+0.01f, direction, 0, 0);
                break;
            case 4: this.getLevel().addAlwaysVisibleParticle(HalfLifeParticles.SPIT_HIT.get(), blockhit.getLocation().x()-0.01f, blockhit.getLocation().y(), blockhit.getLocation().z(), direction, 0, 0);
                break;
            case 5: this.getLevel().addAlwaysVisibleParticle(HalfLifeParticles.SPIT_HIT.get(), blockhit.getLocation().x()+0.01f, blockhit.getLocation().y(), blockhit.getLocation().z(), direction, 0, 0);
                break;
        }
    }

    public void changedirection(int direction, BlockHitResult blockhit) {
        Vec3 movement = this.getDeltaMovement();
        Vec3 newmovement = this.getDeltaMovement();
        this.setJumpTimestamp(this.tickCount);
        float change = this.getJump();
        if ((this.getbounce() == -1) && (direction == 0 || direction == 1) && movement.y() < 0.3) {
            change = 0;
        }
        switch(direction){
            case 0: newmovement = movement.multiply(1, -1*change, 1);
                break;
            case 1: newmovement = movement.multiply(1, -1*change, 1);
                break;
            case 2: newmovement = movement.multiply(1, 1, -1*change);
                break;
            case 3: newmovement = movement.multiply(1, 1, -1*change);
                break;
            case 4: newmovement = movement.multiply(-1*change, 1, 1);
                break;
            case 5: newmovement = movement.multiply(-1*change, 1, 1);
                break;
        }
        this.setDeltaMovement(newmovement);
    }









    @Override
    protected float getGravity() {
        return 0.03F;
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
