package net.sirkotok.half_life_mod.entity.projectile;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.*;
import net.sirkotok.half_life_mod.entity.HalfLifeEntities;
import net.sirkotok.half_life_mod.entity.particle.HalfLifeParticles;
import net.sirkotok.half_life_mod.sound.HalfLifeSounds;
import net.tslat.smartbrainlib.util.EntityRetrievalUtil;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.List;

public class SporeShot extends ThrowableProjectile implements GeoEntity {

    public SporeShot(EntityType<? extends ThrowableProjectile> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public SporeShot(Level pLevel, LivingEntity pShooter) {
        super(HalfLifeEntities.SPORESHOT.get(), pShooter, pLevel);
    }



    public static final EntityDataAccessor<Float> JUMP = SynchedEntityData.defineId(SporeShot.class, EntityDataSerializers.FLOAT);
    public static final EntityDataAccessor<Integer> TICkS_REMAINING =  SynchedEntityData.defineId(SporeShot.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Integer> JUMP_TIMESTAMP =  SynchedEntityData.defineId(SporeShot.class, EntityDataSerializers.INT);






    @Override
    protected void defineSynchedData() {
        this.entityData.define(JUMP, 1f);
        this.entityData.define(TICkS_REMAINING , 100);
        this.entityData.define(JUMP_TIMESTAMP , 0);
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
        this.playSound(HalfLifeSounds.SPLAUNCHER_IMPACT.get(), 0.8f, 1);
        if (!this.level.isClientSide) {
        ServerLevel serverlevel = (ServerLevel) level;
        serverlevel.sendParticles(HalfLifeParticles.YELLOW_SMOKE.get(), this.getX(), this.getY(), this.getZ(), random.nextInt(5, 15), random.nextFloat()/7, random.nextFloat()/7, random.nextFloat()/7, random.nextFloat()/5);
        Entity entity1 = this.getOwner();
        BlockPos pBlockPos = this.blockPosition();
        if (entity1 != null) {
                float rad = 3.5f;
                List<LivingEntity> targets = EntityRetrievalUtil.getEntities((Level) serverlevel,
                        new AABB(pBlockPos.getX() - rad, pBlockPos.getY() - rad, pBlockPos.getZ() - rad,
                                pBlockPos.getX() + rad, pBlockPos.getY() + rad, pBlockPos.getZ() + rad), obj -> obj instanceof LivingEntity);
                if (entity1 instanceof LivingEntity) {
                    for (LivingEntity entity : targets) {
                        entity.hurt(this.damageSources().mobProjectile(this, (LivingEntity) entity1 ), 8f); }
                }}
            this.discard();
    }}


    @Override
    public void tick() {
        super.tick();
        this.level.addParticle(ParticleTypes.ITEM_SLIME, this.getX(), this.getY(), this.getZ(), 0, -0.1f, 0);
        if (this.tickCount > this.getlifetime()){
            this.explode();
        }
    }


    public float minus = -0.8f;
    protected float assigncorrect(int i){
        if (i == 0) {
            return 1;
        } else return minus;
    }
    public int richochet = 0;
    public int maxrichochet = 10;
    public boolean amogus = true;


    public SoundEvent getSporeHitSound(){
        switch(random.nextInt(0,4)) {
            case 0: return HalfLifeSounds.SPORE_HIT1.get();
            case 1: return HalfLifeSounds.SPORE_HIT2.get();
            case 2: return HalfLifeSounds.SPORE_HIT3.get();
            case 3: return HalfLifeSounds.SPLAUNCHER_BOUNCE.get();
        }
        return HalfLifeSounds.SPORE_HIT1.get();
    }


    protected void onHitBlock(BlockHitResult blockhit) {
        super.onHitBlock(blockhit);

        Vec3i vec3i = blockhit.getDirection().getNormal();
        Vec3 vec31 = new Vec3(assigncorrect(vec3i.getX()), assigncorrect(vec3i.getY()), assigncorrect(vec3i.getZ()));
        Vec3 vec3 = this.getDeltaMovement().multiply(vec31);
        this.richochet++;
        if (!this.amogus) this.explode();
        if (this.richochet > maxrichochet) this.explode();
        else {
            this.setDeltaMovement(vec3);
            }
    }


    @Override
    protected void onHit(HitResult pResult) {
        this.playSound(getSporeHitSound());
        super.onHit(pResult);
    }

    @Override
    protected void onHitEntity(EntityHitResult pResult) {
        this.explode();
    }
    /*
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
    } */


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
