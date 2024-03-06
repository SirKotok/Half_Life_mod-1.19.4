package net.sirkotok.half_life_mod.entity.mob_geckolib.custom;


import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.BlockPos;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.sirkotok.half_life_mod.entity.base.HalfLifeMonster;
import net.sirkotok.half_life_mod.entity.base.HalfLifeNeutral;
import net.sirkotok.half_life_mod.entity.brain.behaviour.*;
import net.sirkotok.half_life_mod.entity.brain.sensor.SmellSensor;
import net.sirkotok.half_life_mod.entity.projectile.VortLightningProjectile;
import net.sirkotok.half_life_mod.particle.HalfLifeParticles;
import net.sirkotok.half_life_mod.sound.HalfLifeSounds;
import net.sirkotok.half_life_mod.util.CommonSounds;
import net.sirkotok.half_life_mod.util.HLTags;
import net.sirkotok.half_life_mod.util.HLperUtil;
import net.tslat.smartbrainlib.api.SmartBrainOwner;
import net.tslat.smartbrainlib.api.core.BrainActivityGroup;
import net.tslat.smartbrainlib.api.core.SmartBrainProvider;
import net.tslat.smartbrainlib.api.core.behaviour.FirstApplicableBehaviour;
import net.tslat.smartbrainlib.api.core.behaviour.OneRandomBehaviour;
import net.tslat.smartbrainlib.api.core.behaviour.custom.look.LookAtTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.misc.CustomBehaviour;
import net.tslat.smartbrainlib.api.core.behaviour.custom.misc.Idle;
import net.tslat.smartbrainlib.api.core.behaviour.custom.move.FleeTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.move.MoveToWalkTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.path.SetRandomWalkTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.target.InvalidateAttackTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.target.SetPlayerLookTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.target.SetRandomLookTarget;
import net.tslat.smartbrainlib.api.core.sensor.ExtendedSensor;
import net.tslat.smartbrainlib.api.core.sensor.vanilla.HurtBySensor;
import net.tslat.smartbrainlib.api.core.sensor.vanilla.NearbyLivingEntitySensor;
import net.tslat.smartbrainlib.api.core.sensor.vanilla.NearbyPlayersSensor;
import net.tslat.smartbrainlib.util.BrainUtils;
import net.tslat.smartbrainlib.util.EntityRetrievalUtil;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.List;


public class VortigauntHL2 extends HalfLifeNeutral implements RangedAttackMob, GeoEntity, SmartBrainOwner<VortigauntHL2> {

    @Override
    protected float getSoundVolume() {
        return 0.6f;
    }


    private AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);




    public static final EntityDataAccessor<Boolean> IS_ANGRY = SynchedEntityData.defineId(VortigauntHL2.class, EntityDataSerializers.BOOLEAN);

    public static final EntityDataAccessor<Integer> CHARGE = SynchedEntityData.defineId(VortigauntHL2.class, EntityDataSerializers.INT);

    public static final EntityDataAccessor<Integer> ARC_PARTICLES = SynchedEntityData.defineId(VortigauntHL2.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Integer> MAX_ARC_PARTICLES = SynchedEntityData.defineId(VortigauntHL2.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Float> TARGET_X = SynchedEntityData.defineId(VortigauntHL2.class, EntityDataSerializers.FLOAT);
    public static final EntityDataAccessor<Float> TARGET_Y = SynchedEntityData.defineId(VortigauntHL2.class, EntityDataSerializers.FLOAT);
    public static final EntityDataAccessor<Float> TARGET_Z = SynchedEntityData.defineId(VortigauntHL2.class, EntityDataSerializers.FLOAT);

    public static final EntityDataAccessor<Float> START_X = SynchedEntityData.defineId(VortigauntHL2.class, EntityDataSerializers.FLOAT);
    public static final EntityDataAccessor<Float> START_Y = SynchedEntityData.defineId(VortigauntHL2.class, EntityDataSerializers.FLOAT);
    public static final EntityDataAccessor<Float> START_Z = SynchedEntityData.defineId(VortigauntHL2.class, EntityDataSerializers.FLOAT);


    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(IS_ANGRY, false);

        this.entityData.define(CHARGE, 0);
        this.entityData.define(TARGET_Y, 0F);
        this.entityData.define(TARGET_Z, 0F);
        this.entityData.define(TARGET_X, 0F);
        this.entityData.define(START_Z, 0F);
        this.entityData.define(START_Y, 0F);
        this.entityData.define(START_X, 0F);
        this.entityData.define(ARC_PARTICLES, 0);
        this.entityData.define(MAX_ARC_PARTICLES, 0);
    }

    public int getcharge(){
        return this.entityData.get(CHARGE);
    }
    public void setcharge(int i){
        this.entityData.set(CHARGE, i);
    }

    public int getarcp(){
        return this.entityData.get(ARC_PARTICLES);
    }
    public void setarcp(int i){
        this.entityData.set(ARC_PARTICLES, i);
    }

    public int getMAX(){
        return this.entityData.get(MAX_ARC_PARTICLES);
    }
    public void setMAX(int i){
        this.entityData.set(MAX_ARC_PARTICLES, i);
    }

    public Vec3 gettargetvec(){
        return new Vec3(this.entityData.get(TARGET_X),  this.entityData.get(TARGET_Y),  this.entityData.get(TARGET_Z));
    }
    public void settargetvec(Vec3 vec3){
        this.entityData.set(TARGET_X, (float) vec3.x());
        this.entityData.set(TARGET_Y, (float) vec3.y());
        this.entityData.set(TARGET_Z, (float) vec3.z());
    }

    public Vec3 getstartvec(){
        return new Vec3(this.entityData.get(START_X),  this.entityData.get(START_Y),  this.entityData.get(START_Z));
    }
    public void setstartvec(Vec3 vec3){
        this.entityData.set(START_X, (float) vec3.x());
        this.entityData.set(START_Y, (float) vec3.y());
        this.entityData.set(START_Z, (float) vec3.z());
    }


    private float length(){
        return this.getBbWidth()/2+0.88f;
    }

    private Vec3 rotvec(int angledegree){
        float i = length();
        double yrot = (this.yBodyRot+angledegree)/180*Math.PI;
        double d1 = Math.sin(yrot);
        double d2 = Math.cos(yrot);
        return new Vec3((float)this.getX()-i*d1, this.getY()+1.67f, (float)this.getZ()+i*d2);
    }




    @Override
    public void tick() {
        super.tick();



        if (!this.level.isClientSide() && this.getarcp() > 0) {
            LivingEntity target = BrainUtils.getTargetOfEntity(this);
            if (this.getarcp() > this.getMAX()-2 && this.getarcp() > 1 && target != null) {

                Vec3 from = Vec3.atCenterOf(this.blockPosition()).add(0, 1, 0);
                Vec3 vec3 = this.gettargetvec();
                Vec3 to = vec3.subtract(from);

                int time = (int) to.length();

                double d1 = target.getX() - this.getX();
                double d2 = target.getEyeY() - this.getEyeY()+0.1D;
                double d3 = target.getZ() - this.getZ();
                VortLightningProjectile bullet = new VortLightningProjectile(this.level, this, d1, d2, d3);
                bullet.setPos(this.getX() - (double)((this.getBbWidth()+0.5f) + 1.0F) * 0.5D * (double) Mth.sin(this.yBodyRot * ((float)Math.PI / 180F)), this.getEyeY() - (double)0.1F, this.getZ() + (double)((this.getBbWidth()+0.5f) + 1.0F) * 0.5D * (double) Mth.cos(this.yBodyRot * ((float)Math.PI / 180F)));
                bullet.shoot(d1, d2, d3, 2, 1.0f);
                bullet.setTime(3+time);
                this.level.addFreshEntity(bullet);
            }



            this.setarcp(this.getarcp()-1);
        }





        if (level.isClientSide && this.getarcp() > this.getMAX()-4 && this.getarcp() > 1) {
            Vec3 from = Vec3.atCenterOf(this.blockPosition()).add(0, 1, 0);
            for (int i = 0; i < 3 + level.random.nextInt(3); i++) {
                Vec3 vec3 = this.gettargetvec();
                Vec3 to = vec3.subtract(from);
                to = to.add(to.normalize().scale(0.5));
                level.addParticle(HalfLifeParticles.VORT2_ARC_LIGHTNING.get(), from.x, from.y, from.z, to.x, to.y, to.z);
                Vec3 vec31 = new Vec3 (this.getX() - (double)(this.getBbWidth() + 2.2F) * 0.5D * (double) Mth.sin(this.yBodyRot * ((float)Math.PI / 180F)), this.getEyeY() - (double)0.4F, this.getZ() + (double)(this.getBbWidth() + 2.2F) * 0.5D * (double) Mth.cos(this.yBodyRot * ((float)Math.PI / 180F)));
                this.level.addParticle(HalfLifeParticles.STAT_GLOW.get(), vec31.x, vec31.y, vec31.z, 1, 1d, this.getMAX() - this.getarcp());
            }
        }

       if (level.isClientSide && this.getcharge() > 0) {
           if (this.tickCount % 3 == 0) {
          Vec3 startPos1 = this.getEyePosition();
          this.level.addParticle(HalfLifeParticles.VORT_LIGHTNING.get(), startPos1.x, startPos1.y, startPos1.z, 2, 0, 0);
           }
           Vec3 startPos2 = rotvec(70);
           Vec3 startPos3 = rotvec(-70);
           this.level.addParticle(HalfLifeParticles.STAT_GLOW.get(), startPos2.x, startPos2.y, startPos2.z, 1, 0.65d, 0);
           this.level.addParticle(HalfLifeParticles.STAT_GLOW.get(), startPos3.x, startPos3.y, startPos3.z, 1, 0.65d, 0);
       }



        if (this.tickCount % 100 == 0 && !this.level.isClientSide()) {
            ServerLevel pLevel = (ServerLevel) this.level;
            BlockPos pBlockPos = this.blockPosition();
            int rad = 20;
            List<Mob> xen_fac = EntityRetrievalUtil.getEntities((Level) pLevel,
                    new AABB(pBlockPos.getX() - rad, pBlockPos.getY() - rad, pBlockPos.getZ() - rad,
                            pBlockPos.getX() + rad, pBlockPos.getY() + rad, pBlockPos.getZ() + rad), obj -> obj instanceof HalfLifeNeutral);
            if (BrainUtils.getTargetOfEntity(this) != null) {
                for (Mob x : xen_fac) {
                    if (BrainUtils.getTargetOfEntity(x) == null) {
                        BrainUtils.setTargetOfEntity(x, BrainUtils.getTargetOfEntity(this));
                    }
                }
            }
            }

    }





    protected boolean isangry() {
        return this.entityData.get(IS_ANGRY);
    }

    public VortigauntHL2(EntityType type, Level level) {
        super(type, level);
        this.xpReward = 10;
         this.setPathfindingMalus(BlockPathTypes.DAMAGE_FIRE, -1F);
        this.setPathfindingMalus(BlockPathTypes.DANGER_FIRE, -1F);
    }


    public static AttributeSupplier setAttributes() {
        return Monster.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 30D)
                .add(Attributes.ATTACK_DAMAGE, 4f)
                .add(Attributes.ATTACK_SPEED, 1.0f)
                .add(Attributes.ATTACK_KNOCKBACK, 1f)
                .add(Attributes.ARMOR, 2f)
                .add(Attributes.MOVEMENT_SPEED, 0.31f).build();
    }





    @Override
    protected Brain.Provider<?> brainProvider() {
        return new SmartBrainProvider<>(this);
    }

    @Override
    protected void customServerAiStep() {
        tickBrain(this);
    }






    @Override
    public List<ExtendedSensor<VortigauntHL2>> getSensors() {
        return ObjectArrayList.of(
                new SmellSensor<>(),
                new HurtBySensor<>(),
                new NearbyPlayersSensor<>(),
                new NearbyLivingEntitySensor<VortigauntHL2>()
                        .setPredicate((target, entity) ->
                                target instanceof Enemy || this.ismyenemy(target)
                            ));
    }



    @Override
    public BrainActivityGroup<VortigauntHL2> getCoreTasks() {
        return BrainActivityGroup.coreTasks(
                new LookAtTarget<>(),
                new MoveToWalkTarget<>());
    }




    @Override
    public BrainActivityGroup<VortigauntHL2> getIdleTasks() {
        return BrainActivityGroup.idleTasks(
                new FirstApplicableBehaviour<VortigauntHL2>(
                        new TargetOrRetaliateHLT<>(),
                        new SetPlayerLookTarget<>(),
                        new SetRandomLookTarget<>()),
                new HalfLife1SetFollowToWalkTarget<>().cooldownFor(entity -> 20),
                new OneRandomBehaviour<>(
                        new SetRandomWalkTarget<>(),
                        new Idle<>().runFor(entity -> entity.getRandom().nextInt(10, 40))
                                .whenStarting(entity -> this.entityData.set(IS_ANGRY, false))));
    }



    protected SoundEvent getMissSound() {
        switch (this.random.nextInt(1,3)) {
            case 1:  return HalfLifeSounds.CLAW_MISS1.get();
            case 2:  return HalfLifeSounds.CLAW_MISS2.get();
        }
        return HalfLifeSounds.HEADCRAB_1_PAIN_1.get();
    }





    @Override
    public BrainActivityGroup<VortigauntHL2> getFightTasks() {
        return BrainActivityGroup.fightTasks(
                new InvalidateAttackTarget<>(),
                new Retaliate<>(),
                new SetWalkTargetToRandomSpotAroundAttackTarget<>().speedMod(1.1f).startCondition(entity -> this.distanceTo(HLperUtil.TargetOrThis(this))  > 5),
                new CustomBehaviour<>(entity -> this.entityData.set(IS_ANGRY, true)).startCondition(entity -> !this.isangry()),
                new OneRandomBehaviour<>(
                        new SetRandomWalkTarget<>().setRadius(8, 4).cooldownFor(entity -> 100),
                        new SetWalkTargetToRandomSpotAroundAttackTarget<>().speedMod(1.1f),
                        new SetWalkTargetToRandomSpotAroundAttackTarget<>().speedMod(1.2f),
                        new SetWalkTargetToRandomSpotAroundAttackTarget<>().radius(5, 4)
                ),
                new FirstApplicableBehaviour<>(
                new StopAndShoot<VortigauntHL2>(11, 14, 1f, this.getzapsound())
                        .cooldownFor(entity -> random.nextInt(30, 60))
                .whenStarting(entity -> triggerAnim("onetime", "shoot")),
                 new VortWaveattackbehavior<>(11, null, this.getzapsound()).cooldownFor(entity -> 60 + RandomSource.create().nextInt(80))
                )
        );


    }


    public void performattack() {
        Level pLevel = this.getLevel();
        BlockPos pBlockPos = this.blockPosition();
        float radius = 5f;
        List<LivingEntity> entities = EntityRetrievalUtil.getEntities(pLevel,
                new AABB(pBlockPos.getX() - radius, pBlockPos.getY() - radius, pBlockPos.getZ() - radius,
                        pBlockPos.getX() + radius, pBlockPos.getY() + radius, pBlockPos.getZ() + radius), obj -> obj instanceof LivingEntity && ((BrainUtils.getTargetOfEntity(this) != null && obj == BrainUtils.getTargetOfEntity(this)) || !obj.getType().is(HLTags.EntityTypes.FACTION_SCIENCE_TEAM)));
        for (LivingEntity entity : entities) {
            this.ConfigurabledoHurtTargetShieldBoolean(false, entity, 1f, 1.5f, 2, null, 0, false);
            if (entity instanceof AntlionWorker) ((AntlionWorker) entity).flipover(2);
            if (entity instanceof Antlion) ((Antlion) entity).flipover(2);
        }
    }



    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(new AnimationController<>(this, "mainloop", 0, this::mainpredicate));
        controllerRegistrar.add(new AnimationController<>(this, "onetime", state -> PlayState.STOP)
                .triggerableAnim("blast", RawAnimation.begin().then("animation.vort.meleeblast2", Animation.LoopType.PLAY_ONCE))
                .triggerableAnim("shoot", RawAnimation.begin().then("animation.vort.shoot", Animation.LoopType.PLAY_ONCE))
        );

    }



    private <T extends GeoAnimatable> PlayState mainpredicate(AnimationState<T> tAnimationState) {

        if(tAnimationState.isMoving()) {
            tAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.vort.walk", Animation.LoopType.LOOP));
            return PlayState.CONTINUE;
        }


        tAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.vort.idle", Animation.LoopType.LOOP));
        return PlayState.CONTINUE;
    }




    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }






    @Override
    public void performRangedAttack(LivingEntity target, float p_33318_) {
            int l = (int) this.gettargetvec().subtract(this.getEyePosition()).length()/3;
        if (!this.level.isClientSide() && BrainUtils.getTargetOfEntity(this) != null) {
            this.settargetvec(BrainUtils.getTargetOfEntity(this).getEyePosition());
            this.setstartvec(this.getEyePosition());
        }
            this.setarcp((int) 5+l);
            this.setMAX((int) 5+l);
    }







    public SoundEvent getzapsound() {
        return HalfLifeSounds.VORT2_ATTACK.get();
    }

   /*
    protected SoundEvent getDeathSound() {
        switch (this.random.nextInt(1,3)) {
            case 1:  return HalfLifeSounds.SLV_DIE1.get();
            case 2:  return HalfLifeSounds.SLV_DIE2.get();
        }
        return HalfLifeSounds.HEADCRAB_1_DIE_1.get();
    }


    @Override
    protected SoundEvent getHurtSound(DamageSource pDamageSource) {
        switch (this.random.nextInt(1,3)) {
            case 1:  return HalfLifeSounds.SLV_PAIN1.get();
            case 2:  return HalfLifeSounds.SLV_PAIN2.get();
        }
        return HalfLifeSounds.HEADCRAB_1_DIE_1.get();
    }

 */





    public SoundEvent getStartFollowingSound() {
        int f = random.nextInt(1, 12);
        switch (f) {
            case 1:  return HalfLifeSounds.VORT_ACCOMPANY1.get();
            case 2:  return HalfLifeSounds.VORT_ACCOMPANY2.get();
            case 3:  return HalfLifeSounds.VORT_ACCOMPANY3.get();
            case 4:  return HalfLifeSounds.VORT_ACCOMPANY4.get();
            case 5:  return HalfLifeSounds.VORT_ACCOMPANY5.get();
            case 6:  return HalfLifeSounds.VORT_ACCOMPANY6.get();
            case 7:  return HalfLifeSounds.VORT_ACCOMPANY7.get();
            case 8:  return HalfLifeSounds.VORT_ACCOMPANY8.get();
            case 9:  return HalfLifeSounds.VORT_ACCOMPANY9.get();
            case 10:  return HalfLifeSounds.VORT_ACCOMPANY10.get();
            case 11:  return HalfLifeSounds.VORT_ACCOMPANY11.get();
        }
        return SoundEvents.FROG_STEP;
    }
    public SoundEvent getStopFollowingSound() {
        int f = random.nextInt(1, 7);
        switch (f) {
            case 1:  return HalfLifeSounds.VORT_STAY1.get();
            case 2:  return HalfLifeSounds.VORT_STAY2.get();
            case 3:  return HalfLifeSounds.VORT_STAY3.get();
            case 4:  return HalfLifeSounds.VORT_STAY4.get();
            case 5:  return HalfLifeSounds.VORT_STAY5.get();
            case 6:  return HalfLifeSounds.VORT_STAY6.get();
        }
        return SoundEvents.FROG_STEP;
    }


    public SoundEvent getWarningSound() {
        int f = random.nextInt(1, 3);
        switch (f) {
            case 1:  return HalfLifeSounds.VORT_REGRET1.get();
            case 2:  return HalfLifeSounds.VORT_REGRET2.get();
        }
        return SoundEvents.FROG_STEP;
    }

    public SoundEvent getAttackReactionSound() {
        int f = random.nextInt(1, 4);
        switch (f) {
            case 1:  return HalfLifeSounds.VORT_WANTSTOKILL.get();
            case 2:  return HalfLifeSounds.VORT_WANTSTOKILL2.get();
            case 3:  return HalfLifeSounds.VORT_WANTSTOKILL3.get();
        }
        return SoundEvents.FROG_STEP;
    }

    protected SoundEvent getAmbientSound() {
        int f = random.nextInt(2, 11);
        switch (f) {
            case 2:  return HalfLifeSounds.VORTIGESE02.get();
            case 3:  return HalfLifeSounds.VORTIGESE03.get();
            case 4:  return HalfLifeSounds.VORTIGESE04.get();
            case 5:  return HalfLifeSounds.VORTIGESE05.get();
            case 6:  return HalfLifeSounds.VORTIGESE07.get();
            case 7:  return HalfLifeSounds.VORTIGESE08.get();
            case 8:  return HalfLifeSounds.VORTIGESE09.get();
            case 9:  return HalfLifeSounds.VORTIGESE11.get();
            case 10:  return HalfLifeSounds.VORTIGESE12.get();
        }
        return SoundEvents.FROG_STEP;
    }


}





