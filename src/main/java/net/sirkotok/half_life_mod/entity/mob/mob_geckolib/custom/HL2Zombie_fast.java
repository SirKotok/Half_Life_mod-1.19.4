package net.sirkotok.half_life_mod.entity.mob.mob_geckolib.custom;


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
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraftforge.event.ForgeEventFactory;
import net.sirkotok.half_life_mod.entity.HalfLifeEntities;
import net.sirkotok.half_life_mod.entity.base.HalfLifeMonster;
import net.sirkotok.half_life_mod.entity.brain.behaviour.*;
import net.sirkotok.half_life_mod.misc.gamerules.HalfLifeGameRules;
import net.sirkotok.half_life_mod.sound.HalfLifeSounds;
import net.sirkotok.half_life_mod.misc.util.CommonSounds;
import net.sirkotok.half_life_mod.misc.util.HLperUtil;
import net.sirkotok.half_life_mod.misc.util.InfightingUtil;
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
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.List;


public class HL2Zombie_fast extends HalfLifeMonster implements GeoEntity, SmartBrainOwner<HL2Zombie_fast> {



    private AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);




    public static final EntityDataAccessor<Boolean> IS_ANGRY = SynchedEntityData.defineId(HL2Zombie_fast.class, EntityDataSerializers.BOOLEAN);


    public static final EntityDataAccessor<Boolean> MOVING = SynchedEntityData.defineId(HL2Zombie_fast.class, EntityDataSerializers.BOOLEAN);
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(IS_ANGRY, false);



    }
    protected boolean isangry() {
        return this.entityData.get(IS_ANGRY);
    }





    public HL2Zombie_fast(EntityType type, Level level) {
        super(type, level);
        this.setPathfindingMalus(BlockPathTypes.WATER, -1.0F);
    }


    public static AttributeSupplier setAttributes() {
        return Monster.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 20D)
                .add(Attributes.ATTACK_DAMAGE, 5f)
                .add(Attributes.ATTACK_SPEED, 1.0f)
                .add(Attributes.ATTACK_KNOCKBACK, 1.0f)
                .add(Attributes.MOVEMENT_SPEED, 0.38f).build();
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
        switch (this.random.nextInt(1,7)) {
            case 1:  return HalfLifeSounds.ZOMBIE_PAIN1.get();
            case 2:  return HalfLifeSounds.ZOMBIE_PAIN2.get();
            case 3:  return HalfLifeSounds.ZOMBIE_PAIN3.get();
            case 4:  return HalfLifeSounds.ZOMBIE_PAIN4.get();
            case 5:  return HalfLifeSounds.ZOMBIE_PAIN5.get();
            case 6:  return HalfLifeSounds.ZOMBIE_PAIN6.get();
        }
        return HalfLifeSounds.HEADCRAB_1_PAIN_1.get();
    }


    protected SoundEvent getHitSound() {return HalfLifeSounds.ZOMBIE_HIT.get();}

    protected SoundEvent getFrenzySound() {return HalfLifeSounds.FZ_FRENZY1.get();}
    protected SoundEvent getLoopSound() {return HalfLifeSounds.FZ_BREATHE_LOOP1.get();}
    protected SoundEvent getJumpSound() {
        return (random.nextFloat() < 0.2) ? HalfLifeSounds.FZ_SCREAM1.get() : HalfLifeSounds.FZ_LEAP1.get();
    }

    protected SoundEvent getHorrorSound() {
        return (random.nextFloat() < 0.5) ? HalfLifeSounds.FZ_ALERT_CLOSE1.get() : HalfLifeSounds.FZ_ALERT_FAR1.get();
    }

    protected SoundEvent getAmbientSound() {
            switch (this.random.nextInt(1,4)) {
                case 1:  return HalfLifeSounds.FZ_IDLE1.get();
                case 2:  return HalfLifeSounds.FZ_IDLE2.get();
                case 3:  return HalfLifeSounds.FZ_IDLE3.get();
            }

        return HalfLifeSounds.HEADCRAB_1_ALERT_1.get();
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

        switch (this.random.nextInt(1,4)) {
            case 1:  return HalfLifeSounds.ZOMBIE_DIE1.get();
            case 2:  return HalfLifeSounds.ZOMBIE_DIE2.get();
            case 3:  return HalfLifeSounds.ZOMBIE_DIE3.get(); }



        return super.getDeathSound();
    }

    @Override
    protected Brain.Provider<?> brainProvider() {
        return new SmartBrainProvider<>(this);
    }

    @Override
    protected void customServerAiStep() {
        if (!this.level.getGameRules().getRule(HalfLifeGameRules.HALF_LIFE_AI_IS_ON).get()) return;
        tickBrain(this);
    }



    @Override
    public List<ExtendedSensor<HL2Zombie_fast>> getSensors() {
        return ObjectArrayList.of(
                new HurtBySensor<>(),
                new NearbyPlayersSensor<>(),
                new NearbyLivingEntitySensor<HL2Zombie_fast>()
                        .setPredicate((target, entity) ->
                                (this.getFirstPassenger() instanceof HalfLifeMonster headcrab && target.equals(headcrab.getLastHurtByMob())) ||
                                        InfightingUtil.HeadcrabFactionSpecific(target) || InfightingUtil.commonenemy(target)
                        ));
    }



    @Override
    public BrainActivityGroup<HL2Zombie_fast> getCoreTasks() {
        return BrainActivityGroup.coreTasks(
                new LookAtTarget<>(),
                new MoveToWalkTarget<>());
    }



    @Override
    public BrainActivityGroup<HL2Zombie_fast> getIdleTasks() {
        return BrainActivityGroup.idleTasks(
                new FirstApplicableBehaviour<HL2Zombie_fast>(
                        new CustomBehaviour<>(entity -> this.entityData.set(IS_ANGRY, false)).startCondition(entity -> this.entityData.get(IS_ANGRY).equals(true)),
                        new TargetOrRetaliateHLT<>(),
                        new SetPlayerLookTarget<>(),
                        new SetRandomLookTarget<>()),
                new SetJumpTargetToRandom<>().radius(15, 10).cooldownFor(entity -> 10),
                new OneRandomBehaviour<>(
                        new LongJumpToJumpTarget<>(20, 2F, this.getJumpSound()).whenStarting(entity -> triggerAnim("onetime", "jump"))
                                .cooldownFor(entity -> random.nextInt(100, 600)).startCondition(entity -> this.tickCount > 100 && this.relocate),
                        new SetRandomWalkTarget<>().setRadius(16, 10),
                        new Idle<>().runFor(entity -> entity.getRandom().nextInt(10, 40))));
    }


    @Override
    public BrainActivityGroup<HL2Zombie_fast> getFightTasks() { // These are the tasks that handle fighting
        return BrainActivityGroup.fightTasks(
                new InvalidateAttackTarget<>(),
                new SetWalkTargetToAttackTarget<>().speedMod(1.35f),
                new CustomBehaviour<>(entity -> this.entityData.set(IS_ANGRY, true)).startCondition(entity -> this.entityData.get(IS_ANGRY).equals(false)).whenStarting(entity -> CommonSounds.PlaySoundAsOwn(this, this.getHorrorSound())),
                new Retaliate<>(),
                new SetJumpTargetToRandomSpotAroundAttackTarget<>(0).cooldownFor(entity -> 10),
                new LongJumpToJumpTarget<>(20, 2F, this.getJumpSound()).whenStarting(entity -> triggerAnim("onetime", "jump"))
                        .startCondition(entity -> entity.distanceTo(HLperUtil.TargetOrThis(entity)) > 5)
                        .cooldownFor(entity -> random.nextInt(50, 300)),
                new BiteWhileJumpingBehavior<>(30, null, 0f).startCondition(entity -> !isOnGround()).cooldownFor(entity -> 20),
                new HeadCrabJumpBehavior<>(2, this.getJumpSound(), null).SetMinDistance(5).whenStarting(entity -> triggerAnim("onetime", "jump")).cooldownFor(entity -> 100),
                new DoubleMeleeAttack<HL2Zombie_fast>(54, 8, 0, 1, 1, null , 0,
                        CommonSounds.getClawHitSound(), null, this.getMissSound(), null, null, 40, this.getFrenzySound())
                               .whenStarting(entity ->triggerAnim("onetime", "double"))
                               .cooldownFor(entity -> 12)


        );

    }

    @Override
    public float getVoicePitch() {
        return 1+random.nextFloat()*random.nextFloat()-random.nextFloat()*random.nextFloat();
    }


    protected boolean relocate;
     @Override
    public void tick() {
         super.tick();
         if (tickCount % 60 == 0 && this.isangry())
             CommonSounds.PlaySoundAsOwn(this, this.getLoopSound());
         if ((this.tickCount > 15 && (this.getPassengers().isEmpty())) || (this.getFirstPassenger() instanceof LivingEntity entity && entity.isDeadOrDying())) {
             this.setHealth(0);
         }


        if (this.tickCount % 50 == 0) this.relocate = this.random.nextFloat() < 0.05f;
        if (this.tickCount % 100 == 0 && this.shouldDiscardFriction() && this.isOnGround()) {
            this.setDiscardFriction(false); }

    }




    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(new AnimationController<>(this, "controller", 0, this::predicate));
        controllerRegistrar.add(new AnimationController<>(this, "onetime", state -> PlayState.STOP)
                .triggerableAnim("double", RawAnimation.begin().then("animation.fzombie.attack", Animation.LoopType.PLAY_ONCE))
                .triggerableAnim("jump", RawAnimation.begin().then("animation.fzombie.jump", Animation.LoopType.PLAY_ONCE))

        );
    }


    private <T extends GeoAnimatable> PlayState predicate(AnimationState<T> tAnimationState) {

        if(tAnimationState.isMoving()) {
            tAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.fzombie.run", Animation.LoopType.LOOP));
            return PlayState.CONTINUE;
        }

        tAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.fzombie.idle", Animation.LoopType.LOOP));
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
            Headcrab_Fast summon = HalfLifeEntities.HEADCRAB_FAST.get().create(level);
            if (summon != null) {
                summon.moveTo(this.position());
                summon.startRiding(this);
                ForgeEventFactory.onFinalizeSpawn((Mob) summon, (ServerLevelAccessor) level, level.getCurrentDifficultyAt(summon.blockPosition()), MobSpawnType.TRIGGERED, null, null);
                level.addFreshEntity(summon);
            }
        }


        return super.finalizeSpawn(p_33601_, p_33602_, p_33603_, p_33604_, p_33605_);
    }
}





