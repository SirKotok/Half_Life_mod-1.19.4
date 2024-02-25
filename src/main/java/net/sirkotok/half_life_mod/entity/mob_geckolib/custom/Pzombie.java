package net.sirkotok.half_life_mod.entity.mob_geckolib.custom;


import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.ForgeEventFactory;
import net.sirkotok.half_life_mod.entity.HalfLifeEntities;
import net.sirkotok.half_life_mod.entity.base.HalfLifeMonster;
import net.sirkotok.half_life_mod.entity.base.HalfLifeNeutral;
import net.sirkotok.half_life_mod.entity.brain.behaviour.*;
import net.sirkotok.half_life_mod.entity.modinterface.DoubleRangedMob;
import net.sirkotok.half_life_mod.sound.HalfLifeSounds;
import net.sirkotok.half_life_mod.util.CommonSounds;
import net.sirkotok.half_life_mod.util.HLTags;
import net.tslat.smartbrainlib.api.SmartBrainOwner;
import net.tslat.smartbrainlib.api.core.BrainActivityGroup;
import net.tslat.smartbrainlib.api.core.SmartBrainProvider;
import net.tslat.smartbrainlib.api.core.behaviour.FirstApplicableBehaviour;
import net.tslat.smartbrainlib.api.core.behaviour.OneRandomBehaviour;
import net.tslat.smartbrainlib.api.core.behaviour.custom.look.LookAtTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.misc.CustomBehaviour;
import net.tslat.smartbrainlib.api.core.behaviour.custom.misc.Idle;
import net.tslat.smartbrainlib.api.core.behaviour.custom.move.MoveToWalkTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.path.SetRandomWalkTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.path.SetWalkTargetToAttackTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.target.InvalidateAttackTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.target.SetPlayerLookTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.target.SetRandomLookTarget;
import net.tslat.smartbrainlib.api.core.sensor.ExtendedSensor;
import net.tslat.smartbrainlib.api.core.sensor.vanilla.HurtBySensor;
import net.tslat.smartbrainlib.api.core.sensor.vanilla.NearbyLivingEntitySensor;
import net.tslat.smartbrainlib.api.core.sensor.vanilla.NearbyPlayersSensor;
import net.tslat.smartbrainlib.util.BrainUtils;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.List;


public class Pzombie extends HalfLifeMonster implements GeoEntity, SmartBrainOwner<Pzombie>, RangedAttackMob {



    private AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);




    public static final EntityDataAccessor<Boolean> IS_ANGRY = SynchedEntityData.defineId(Pzombie.class, EntityDataSerializers.BOOLEAN);
    public static final EntityDataAccessor<Boolean> HAS_FIRST = SynchedEntityData.defineId(Pzombie.class, EntityDataSerializers.BOOLEAN);
    public static final EntityDataAccessor<Boolean> HAS_THIRD = SynchedEntityData.defineId(Pzombie.class, EntityDataSerializers.BOOLEAN);
    public static final EntityDataAccessor<Boolean> HAS_SECOND = SynchedEntityData.defineId(Pzombie.class, EntityDataSerializers.BOOLEAN);

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(IS_ANGRY, false);
        this.entityData.define(HAS_FIRST, true);
        this.entityData.define(HAS_SECOND, true);
        this.entityData.define(HAS_THIRD, true);

    }
    protected boolean isangry() {
        return this.entityData.get(IS_ANGRY);
    }

    public boolean hasheadcrabs() {
        return this.entityData.get(HAS_FIRST) || this.entityData.get(HAS_THIRD) || this.entityData.get(HAS_SECOND);
    }




    public Pzombie(EntityType type, Level level) {
        super(type, level);
        this.setPathfindingMalus(BlockPathTypes.WATER, -1.0F);
    }


    public static AttributeSupplier setAttributes() {
        return Monster.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 60D)
                .add(Attributes.ATTACK_DAMAGE, 10f)
                .add(Attributes.ATTACK_SPEED, 1.0f)
                .add(Attributes.ATTACK_KNOCKBACK, 1.0f)
                .add(Attributes.MOVEMENT_SPEED, 0.15f).build();
    }

    @Override
    protected void actuallyHurt(DamageSource p_21240_, float p_21241_) {
        if (p_21240_.is(DamageTypes.IN_WALL)) {
            return; }
        super.actuallyHurt(p_21240_, p_21241_);
    }

    @Override
    protected float getSoundVolume() {
        return 0.5f;
    }



    protected SoundEvent getMissSound() {
        switch (this.random.nextInt(1,3)) {
            case 1:  return HalfLifeSounds.CLAW_MISS1.get();
            case 2:  return HalfLifeSounds.CLAW_MISS2.get();
        }
        return HalfLifeSounds.HEADCRAB_1_PAIN_1.get();
    }

    protected SoundEvent getHurtSound(DamageSource p_33034_) {
        switch (this.random.nextInt(1,4)) {
            case 1:  return HalfLifeSounds.PZ_PAIN1.get();
            case 2:  return HalfLifeSounds.PZ_PAIN2.get();
            case 3:  return HalfLifeSounds.PZ_PAIN3.get();
        }
        return HalfLifeSounds.HEADCRAB_1_PAIN_1.get();
    }


    protected SoundEvent getHitSound() {return HalfLifeSounds.ZOMBIE_HIT.get();}
    protected SoundEvent getThrowSound() {return (random.nextFloat() < 0.50) ? HalfLifeSounds.PZ_THROW1.get() : HalfLifeSounds.PZ_THROW2.get();}
    protected SoundEvent getWarnSound() { return (random.nextFloat() < 0.50) ? HalfLifeSounds.PZ_WARN1.get() : HalfLifeSounds.PZ_WARN2.get();}



    protected SoundEvent getHorrorSound() {
        float r = random.nextFloat();
        if (r < 0.1f) return  HalfLifeSounds.PZ_CALL1.get();
        return (r < 0.55) ? HalfLifeSounds.PZ_ALERT1.get() : HalfLifeSounds.PZ_ALERT2.get();
    }

    protected SoundEvent getAmbientSound() {
            switch (this.random.nextInt(1,4)) {
                case 1:  return HalfLifeSounds.PZ_IDLE1.get();
                case 2:  return HalfLifeSounds.PZ_IDLE2.get();
                case 3:  return HalfLifeSounds.PZ_IDLE3.get();
            }

        return HalfLifeSounds.HEADCRAB_1_ALERT_1.get();
    }




    public void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        pCompound.putBoolean("has_first", this.entityData.get(HAS_FIRST));
        pCompound.putBoolean("has_second", this.entityData.get(HAS_SECOND));
        pCompound.putBoolean("has_third", this.entityData.get(HAS_THIRD));
    }

    public void readAdditionalSaveData(CompoundTag pCompound) {
        this.entityData.set(HAS_FIRST, pCompound.getBoolean("has_first"));
        this.entityData.set(HAS_SECOND, pCompound.getBoolean("has_second"));
        this.entityData.set(HAS_THIRD, pCompound.getBoolean("has_third"));
        super.readAdditionalSaveData(pCompound);
    }







    @Override
    protected void pushEntities() {
        super.pushEntities();
    }


    @Override
    public void push(Entity pEntity) {
        if (!this.isPassengerOfSameVehicle(pEntity)) {
            if (!pEntity.noPhysics && !this.noPhysics) {
                double d0 = pEntity.getX() - this.getX();
                double d1 = pEntity.getZ() - this.getZ();
                double d2 = Mth.absMax(d0, d1);
                if (d2 >= (double)0.01F) {
                    d2 = Math.sqrt(d2);
                    d0 /= d2;
                    d1 /= d2;
                    double d3 = 1.0D / d2;
                    if (d3 > 1.0D) {
                        d3 = 1.0D;
                    }

                    d0 *= d3;
                    d1 *= d3;
                    d0 *= (double)0.05F;
                    d1 *= (double)0.05F;
                    if (this.isPushable()) {
                        this.push(-d0, 0.0D, -d1);
                    }

                    if (pEntity.isPushable()) {
                        pEntity.push(d0, 0.0D, d1);
                    }
                }

            }
        }
    }

    @Nullable
    @Override
    public LivingEntity getControllingPassenger() {
        return null;
    }

    @Override
    protected SoundEvent getDeathSound() {

        switch (this.random.nextInt(1,3)) {
            case 1:  return HalfLifeSounds.PZ_DIE1.get();
            case 2:  return HalfLifeSounds.PZ_DIE2.get(); }



        return super.getDeathSound();
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
    public List<ExtendedSensor<Pzombie>> getSensors() {
        return ObjectArrayList.of(
                new HurtBySensor<>(),
                new NearbyPlayersSensor<>(),
                new NearbyLivingEntitySensor<Pzombie>()
                        .setPredicate((target, entity) ->
                            target instanceof Player || (this.getFirstPassenger() instanceof HalfLifeMonster headcrab && target.equals(headcrab.getLastHurtByMob())) ||
                                    target.getType().is(HLTags.EntityTypes.FACTION_ANTLION) || target.getType().is(HLTags.EntityTypes.FACTION_COMBINE) || target instanceof IronGolem || target instanceof HalfLifeNeutral ||
                            target instanceof AbstractVillager));
    }



    @Override
    public BrainActivityGroup<Pzombie> getCoreTasks() {
        return BrainActivityGroup.coreTasks(
                new LookAtTarget<>(),
                new MoveToWalkTarget<>());
    }



    @Override
    public BrainActivityGroup<Pzombie> getIdleTasks() {
        return BrainActivityGroup.idleTasks(
                new FirstApplicableBehaviour<Pzombie>(
                        new CustomBehaviour<>(entity -> this.entityData.set(IS_ANGRY, false)).startCondition(entity -> this.entityData.get(IS_ANGRY).equals(true)),
                        new TargetOrRetaliateHLT<>(),
                        new SetPlayerLookTarget<>(),
                        new SetRandomLookTarget<>()),
                new OneRandomBehaviour<>(
                        new SetRandomWalkTarget<>().setRadius(16, 10),
                        new Idle<>().runFor(entity -> entity.getRandom().nextInt(10, 40))));
    }


    @Override
    public BrainActivityGroup<Pzombie> getFightTasks() { // These are the tasks that handle fighting
        return BrainActivityGroup.fightTasks(
                new InvalidateAttackTarget<>(),
                new SetWalkTargetToAttackTarget<>().speedMod(1.25f),
                new CustomBehaviour<>(entity -> this.entityData.set(IS_ANGRY, true)).startCondition(entity -> this.entityData.get(IS_ANGRY).equals(false)).whenStarting(entity -> CommonSounds.PlaySoundAsOwn(this, this.getHorrorSound())),
                new Retaliate<>(),
                new ConfigurableAnimatableMeleeAttack<Pzombie>(13, 0.1f, 1.5f, 1, null, 0, CommonSounds.getClawHitSound(), null, this.getMissSound(), null, null)
                        .whenStarting(entity -> triggerAnim("onetime", "attack"))
                        .cooldownFor(entity -> random.nextInt(10, 15)),
                new OneRandomBehaviour<Pzombie>(
                        new StopAndShoot<>(10, 8, 1, this.getWarnSound()).whenActivating(entity -> this.entityData.set(HAS_FIRST, false)).startCondition(entity -> this.entityData.get(HAS_FIRST)).whenStarting(entity -> triggerAnim("onetime", "t1")),
                        new StopAndShoot<>(10, 8, 1, this.getWarnSound()).whenActivating(entity -> this.entityData.set(HAS_SECOND, false)).startCondition(entity -> this.entityData.get(HAS_SECOND)).whenStarting(entity -> triggerAnim("onetime", "t2")),
                        new StopAndShoot<>(10, 8, 1, this.getWarnSound()).whenActivating(entity -> this.entityData.set(HAS_THIRD, false)).startCondition(entity -> this.entityData.get(HAS_THIRD)).whenStarting(entity -> triggerAnim("onetime", "t3"))
                ).cooldownFor(entity -> random.nextInt(200, 600))
        );

    }

    @Override
    public float getVoicePitch() {
        return 1+random.nextFloat()*random.nextFloat()-random.nextFloat()*random.nextFloat();
    }

    protected SoundEvent getLoopSound() {return HalfLifeSounds.PZ_BREATHE_LOOP1.get();}

     @Override
    public void tick() {
         super.tick();

         if (tickCount % 80 == 0)
             CommonSounds.PlaySoundAsOwn(this, this.getLoopSound());

         if ((this.tickCount > 15 && (this.getPassengers().isEmpty())) || (this.getFirstPassenger() instanceof LivingEntity entity && entity.isDeadOrDying())) {
             this.setHealth(0);
         }

         if (!this.level.isClientSide() && this.isDeadOrDying() && this.hasheadcrabs()) {
             int i = 0;
             boolean f =  this.entityData.get(HAS_FIRST);
             boolean s = this.entityData.get(HAS_SECOND);
             boolean t = this.entityData.get(HAS_THIRD);
             if (f) i++;
             if (s) i++;
             if (t) i++;

             this.entityData.set(HAS_SECOND, false);
             this.entityData.set(HAS_FIRST, false);
             this.entityData.set(HAS_THIRD, false);



             while (i>0) {
                 Headcrab_Poison_2 headcrab = HalfLifeEntities.HEADCRAB_POISON_HL2.get().create(level);
                 if (headcrab != null) {
                     headcrab.moveTo(this.getX(), this.getEyeY(), this.getZ());
                     if (this.getKillCredit() != null) {
                        BrainUtils.setTargetOfEntity(headcrab, this.getKillCredit());
                     }
                     ForgeEventFactory.onFinalizeSpawn((Mob) headcrab, (ServerLevelAccessor) level, level.getCurrentDifficultyAt(headcrab.blockPosition()), MobSpawnType.TRIGGERED, null, null);
                     level.addFreshEntity(headcrab);
                 }
                 i--;
             }
         }


     }





    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(new AnimationController<>(this, "headcrabcontrol", 0, this::headcrabs));
        controllerRegistrar.add(new AnimationController<>(this, "controller", 0, this::predicate));
        controllerRegistrar.add(new AnimationController<>(this, "headcrab1", 0, this::headcrab1));
        controllerRegistrar.add(new AnimationController<>(this, "headcrab2", 0, this::headcrab2));
        controllerRegistrar.add(new AnimationController<>(this, "headcrab3", 0, this::headcrab3));
        controllerRegistrar.add(new AnimationController<>(this, "onetime", state -> PlayState.STOP)
                .triggerableAnim("attack", RawAnimation.begin().then("animation.pzombie.melee", Animation.LoopType.PLAY_ONCE))
                .triggerableAnim("t1", RawAnimation.begin().then("animation.pzombie.throw1", Animation.LoopType.PLAY_ONCE))
                .triggerableAnim("t2", RawAnimation.begin().then("animation.pzombie.throw2", Animation.LoopType.PLAY_ONCE))
                .triggerableAnim("t3", RawAnimation.begin().then("animation.pzombie.throw3", Animation.LoopType.PLAY_ONCE))
        );
    }


    private <T extends GeoAnimatable> PlayState predicate(AnimationState<T> tAnimationState) {

        if(tAnimationState.isMoving()) {
            tAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.pzombie.walk", Animation.LoopType.LOOP));
            return PlayState.CONTINUE;
        }

        tAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.pzombie.idle", Animation.LoopType.LOOP));
        return PlayState.CONTINUE;

    }


    private <T extends GeoAnimatable> PlayState headcrab1(AnimationState<T> tAnimationState) {
        boolean f =  this.entityData.get(HAS_FIRST) && !this.isDeadOrDying();


        if (f) {
            tAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.pzombie.yesall", Animation.LoopType.LOOP));
            return PlayState.CONTINUE;
        }

        tAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.pzombie.no1", Animation.LoopType.LOOP));
        return PlayState.CONTINUE;

    }



    private <T extends GeoAnimatable> PlayState headcrab2(AnimationState<T> tAnimationState) {
        boolean f =  this.entityData.get(HAS_SECOND) && !this.isDeadOrDying();


        if (f) {
            tAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.pzombie.yesall", Animation.LoopType.LOOP));
            return PlayState.CONTINUE;
        }

        tAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.pzombie.no2", Animation.LoopType.LOOP));
        return PlayState.CONTINUE;

    }

    private <T extends GeoAnimatable> PlayState headcrab3(AnimationState<T> tAnimationState) {
        boolean f =  this.entityData.get(HAS_THIRD) && !this.isDeadOrDying();


        if (f) {
            tAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.pzombie.yesall", Animation.LoopType.LOOP));
            return PlayState.CONTINUE;
        }

        tAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.pzombie.no3", Animation.LoopType.LOOP));
        return PlayState.CONTINUE;

    }



    private <T extends GeoAnimatable> PlayState headcrabs(AnimationState<T> tAnimationState) {
        tAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.pzombie.headcrabs", Animation.LoopType.LOOP));
        return PlayState.CONTINUE;

    }




    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }


    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor p_33601_, DifficultyInstance p_33602_, MobSpawnType p_33603_, @javax.annotation.Nullable SpawnGroupData p_33604_, @javax.annotation.Nullable CompoundTag p_33605_) {

        if (!p_33603_.equals(MobSpawnType.CONVERSION)) {
            Headcrab_Poison_2 summon = HalfLifeEntities.HEADCRAB_POISON_HL2.get().create(level);
            if (summon != null) {
                summon.moveTo(this.position());
                summon.startRiding(this);
                ForgeEventFactory.onFinalizeSpawn((Mob) summon, (ServerLevelAccessor) level, level.getCurrentDifficultyAt(summon.blockPosition()), MobSpawnType.TRIGGERED, null, null);
                level.addFreshEntity(summon);
            }
        }


        return super.finalizeSpawn(p_33601_, p_33602_, p_33603_, p_33604_, p_33605_);
    }

    @Override
    public void performRangedAttack(LivingEntity pTarget, float pVelocity) {
        Headcrab_Poison_2 headcrab = HalfLifeEntities.HEADCRAB_POISON_HL2.get().create(level);
        LivingEntity target = BrainUtils.getTargetOfEntity(this);
        CommonSounds.PlaySoundAsOwn(this, this.getThrowSound());
        if (headcrab != null && target != null) {
            headcrab.moveTo(this.getX(), this.getEyeY(), this.getZ());
            BrainUtils.setTargetOfEntity(headcrab, target);
            Vec3 position = new Vec3(headcrab.getX(), headcrab.getY(), headcrab.getZ());
            Vec3 motion = new Vec3(target.getX(), target.getY() + (double) target.getEyeHeight(), target.getZ()).subtract(position).normalize().scale(1.9D);
            headcrab.setDeltaMovement(motion);
            ForgeEventFactory.onFinalizeSpawn((Mob) headcrab, (ServerLevelAccessor) level, level.getCurrentDifficultyAt(headcrab.blockPosition()), MobSpawnType.REINFORCEMENT, null, null);
            level.addFreshEntity(headcrab);
        }
    }


/*

    @Override
    public void die(DamageSource pDamageSource) {

        int i = 0;
        boolean f =  this.entityData.get(HAS_FIRST);
        boolean s = this.entityData.get(HAS_SECOND);
        if (f) i++;
        if (s) i++;

        while (i>0) {
            Headcrab_Poison_2 headcrab = HalfLifeEntities.HEADCRAB_POISON_HL2.get().create(level);
            if (headcrab != null) {
                headcrab.moveTo(this.getX(), this.getEyeY(), this.getZ());
                ForgeEventFactory.onFinalizeSpawn((Mob) headcrab, (ServerLevelAccessor) level, level.getCurrentDifficultyAt(headcrab.blockPosition()), MobSpawnType.TRIGGERED, null, null);
                level.addFreshEntity(headcrab);
            }
            i--;
        }

        super.die(pDamageSource);

    }

 */



}





