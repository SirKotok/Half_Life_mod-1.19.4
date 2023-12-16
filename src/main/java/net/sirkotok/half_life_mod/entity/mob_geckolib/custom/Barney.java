package net.sirkotok.half_life_mod.entity.mob_geckolib.custom;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.sirkotok.half_life_mod.entity.base.HalfLifeNeutral;
import net.sirkotok.half_life_mod.entity.brain.behaviour.HalfLife1SetFollowToWalkTarget;
import net.sirkotok.half_life_mod.entity.brain.behaviour.Retaliate;
import net.sirkotok.half_life_mod.entity.brain.behaviour.StopAndShoot;
import net.sirkotok.half_life_mod.entity.brain.behaviour.TargetOrRetaliateHLT;
import net.sirkotok.half_life_mod.entity.projectile.Bullet;
import net.sirkotok.half_life_mod.item.HalfLifeItems;
import net.sirkotok.half_life_mod.sound.HalfLifeSounds;
import net.sirkotok.half_life_mod.util.HLperUtil;
import net.tslat.smartbrainlib.api.SmartBrainOwner;
import net.tslat.smartbrainlib.api.core.BrainActivityGroup;
import net.tslat.smartbrainlib.api.core.SmartBrainProvider;
import net.tslat.smartbrainlib.api.core.behaviour.FirstApplicableBehaviour;
import net.tslat.smartbrainlib.api.core.behaviour.OneRandomBehaviour;
import net.tslat.smartbrainlib.api.core.behaviour.custom.look.LookAtTarget;
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
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

import javax.annotation.Nullable;
import java.util.List;

public class Barney extends HalfLifeNeutral implements GeoEntity, SmartBrainOwner<Barney>, RangedAttackMob {


    //TODO completely remake the AI, and add sounds, and that stuff. Currently bad.

    private AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    public Barney(EntityType type, Level level) {
        super(type, level);
        this.xpReward = 3;
    }


    public static final EntityDataAccessor<Boolean> IS_ANGRY = SynchedEntityData.defineId(Barney.class, EntityDataSerializers.BOOLEAN);

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(IS_ANGRY, false);
    }


    @Override
    public SoundEvent getStartFollowingSound() {
        switch (this.random.nextInt(1,7)) {
            case 1:  return HalfLifeSounds.GUARD_FOLLOW_1.get();
            case 2:  return HalfLifeSounds.GUARD_FOLLOW_2.get();
            case 3:  return HalfLifeSounds.GUARD_FOLLOW_3.get();
            case 4:  return HalfLifeSounds.GUARD_FOLLOW_4.get();
            case 5:  return HalfLifeSounds.GUARD_FOLLOW_5.get();
            case 6:  return HalfLifeSounds.GUARD_FOLLOW_6.get();
        }
        return HalfLifeSounds.HEADCRAB_1_ATTACK_1.get();
    }
    @Override
    public SoundEvent getStopFollowingSound() {
        switch (this.random.nextInt(1,7)) {
            case 1:  return HalfLifeSounds.GUARD_STAY_1.get();
            case 2:  return HalfLifeSounds.GUARD_STAY_2.get();
            case 3:  return HalfLifeSounds.GUARD_STAY_3.get();
            case 4:  return HalfLifeSounds.GUARD_STAY_4.get();
            case 5:  return HalfLifeSounds.GUARD_STAY_5.get();
            case 6:  return HalfLifeSounds.GUARD_STAY_6.get();
        }
        return HalfLifeSounds.HEADCRAB_1_ATTACK_1.get();
    }

    @Nullable
    protected SoundEvent getAmbientSound() {
        return SoundEvents.FROG_AMBIENT;
    }

    @Nullable
    protected SoundEvent getHurtSound(DamageSource pDamageSource) {
        return SoundEvents.FROG_HURT;
    }

    @Nullable
    protected SoundEvent getDeathSound() {
        return SoundEvents.FROG_DEATH;
    }





    public static AttributeSupplier setAttributes() {
        return Monster.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 15D)
                .add(Attributes.ARMOR, 5f)
                .add(Attributes.ATTACK_DAMAGE, 3f)
                .add(Attributes.ATTACK_SPEED, 1.0f)
                .add(Attributes.ATTACK_KNOCKBACK, 1.0f)
                .add(Attributes.MOVEMENT_SPEED, 0.24f).build();
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(new AnimationController<>(this, "move", 0, this::predicate));
        controllerRegistrar.add(new AnimationController<>(this, "angry", 0, this::attackpredicate));
        controllerRegistrar.add(new AnimationController<>(this, "attack", state -> PlayState.STOP)
                .triggerableAnim("shoot", RawAnimation.begin().then("animation.human_model.shoot", Animation.LoopType.PLAY_ONCE)))
        ;}

    private <T extends GeoAnimatable> PlayState attackpredicate(AnimationState<T> tAnimationState) {
        if(this.entityData.get(IS_ANGRY)) {
            tAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.human_model.gun_in_hand", Animation.LoopType.LOOP));
            return PlayState.CONTINUE;
        }
        tAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.human_model.idle", Animation.LoopType.LOOP));
        return PlayState.CONTINUE;
    }

    private <T extends GeoAnimatable> PlayState predicate(AnimationState<T> tAnimationState) {


        if(tAnimationState.isMoving()) {
            tAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.human_model.walk", Animation.LoopType.LOOP));
            return PlayState.CONTINUE;
        }


        tAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.human_model.idle", Animation.LoopType.LOOP));

        return PlayState.CONTINUE;

    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
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
    public List<ExtendedSensor<Barney>> getSensors() {
        return ObjectArrayList.of(
                new HurtBySensor<>(),
                new NearbyPlayersSensor<>(),
                new NearbyLivingEntitySensor<Barney>()
                        .setPredicate((target, entity) ->
                                target instanceof Enemy));
    }



    @Override
    public BrainActivityGroup<Barney> getCoreTasks() { // These are the tasks that run all the time (usually)
        return BrainActivityGroup.coreTasks(
                new LookAtTarget<>(),                      // Have the entity turn to face and look at its current look target
                new MoveToWalkTarget<>());
    }

    @Override
    public BrainActivityGroup<Barney> getIdleTasks() { // These are the tasks that run when the mob isn't doing anything else (usually)
        return BrainActivityGroup.idleTasks(
                new FirstApplicableBehaviour<Barney>(
                        new TargetOrRetaliateHLT<>(),
                        new SetPlayerLookTarget<>(),
                        new SetRandomLookTarget<>()).whenStarting(entity -> this.entityData.set(IS_ANGRY, false)),
                new HalfLife1SetFollowToWalkTarget<>().cooldownFor(entity -> 20),
                new OneRandomBehaviour<>(
                        new SetRandomWalkTarget<>(),          // Set a random walk target to a nearby position
                        new Idle<>().runFor(entity -> entity.getRandom().nextInt(30, 90)))); // Do nothing for 1.5->4.5 seconds
    }




    @Override
    public BrainActivityGroup<Barney> getFightTasks() { // These are the tasks that handle fighting
        return BrainActivityGroup.fightTasks(
                new Retaliate<>(),
                new InvalidateAttackTarget<>(),
                new SetWalkTargetToAttackTarget<Barney>().whenStarting(entity -> this.entityData.set(IS_ANGRY, true)),
                new StopAndShoot<Barney>(14, 0, 4f).startCondition(entity -> this.distanceTo(HLperUtil.TargetOrThis(this)) < 12)
                                .whenStarting(entity -> triggerAnim("attack", "shoot"))
        );
    }


    @Override
    protected float getSoundVolume() {
        return 0.4f;
    }

    @Nullable
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor p_33601_, DifficultyInstance p_33602_, MobSpawnType p_33603_, @Nullable SpawnGroupData p_33604_, @Nullable CompoundTag p_33605_) {
        if (RandomSource.create().nextFloat() < 0.9f) this.setItemSlot(EquipmentSlot.HEAD, HalfLifeItems.SECURITY_GUARD_HELMET.get().getDefaultInstance());
        if (RandomSource.create().nextFloat() < 0.9f) this.setItemSlot(EquipmentSlot.CHEST, HalfLifeItems.SECURITY_GUARD_VEST.get().getDefaultInstance());

        this.setItemSlot(EquipmentSlot.MAINHAND, HalfLifeItems.PISTOL.get().getDefaultInstance());

        return super.finalizeSpawn(p_33601_, p_33602_, p_33603_, p_33604_, p_33605_);
    }


    @Override
    public void performRangedAttack(LivingEntity livingentity, float p_33318_) {

        double d0 = this.distanceToSqr(livingentity);
        double d1 = livingentity.getX() - this.getX();
        double d2 = livingentity.getY(0.4D) - this.getY(0.4D);
        double d3 = livingentity.getZ() - this.getZ();

        this.playSound(HalfLifeSounds.PISTOL_SHOOT.get(), this.getSoundVolume(), 1f);


        Bullet bullet = new Bullet(this.level, this, d1, d2, d3);
        bullet.setPos(this.getX() - (double)(this.getBbWidth() + 1.0F) * 0.5D * (double) Mth.sin(this.yBodyRot * ((float)Math.PI / 180F)), this.getEyeY() - (double)0.1F, this.getZ() + (double)(this.getBbWidth() + 1.0F) * 0.5D * (double) Mth.cos(this.yBodyRot * ((float)Math.PI / 180F)));
        bullet.shoot(d1, d2, d3, p_33318_, 1f);
        this.level.addFreshEntity(bullet);

    }

}
