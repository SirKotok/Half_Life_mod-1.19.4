package net.sirkotok.half_life_mod.entity.mob.mob_geckolib.custom;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
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
import net.sirkotok.half_life_mod.entity.brain.behaviour.*;
import net.sirkotok.half_life_mod.entity.projectile.Bullet;
import net.sirkotok.half_life_mod.misc.gamerules.HalfLifeGameRules;
import net.sirkotok.half_life_mod.item.HalfLifeItems;
import net.sirkotok.half_life_mod.sound.HalfLifeSounds;
import net.sirkotok.half_life_mod.misc.util.HLperUtil;
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

    public boolean hlisangry() {
        return this.entityData.get(IS_ANGRY);
    }




    public SoundEvent getQuestionSound() {
        switch (this.random.nextInt(1,9)) {
            case 1:  return HalfLifeSounds.BA_IDLE_Q1.get();
            case 2:  return HalfLifeSounds.BA_IDLE_Q2.get();
            case 3:  return HalfLifeSounds.BA_IDLE_Q3.get();
            case 4:  return HalfLifeSounds.BA_IDLE_Q4.get();
            case 5:  return HalfLifeSounds.BA_IDLE_Q6.get();
            case 6:  return HalfLifeSounds.BA_IDLE_Q7.get();
            case 7:  return HalfLifeSounds.BA_IDLE_Q8.get();
            case 8:  return HalfLifeSounds.BA_IDLE_Q5.get();
        }
        return HalfLifeSounds.HEADCRAB_1_ATTACK_1.get();
    }
    public SoundEvent getIdleCommentSound() {
        switch (this.random.nextInt(1,9)) {
            case 1:  return HalfLifeSounds.BA_IDLE_C1.get();
            case 2:  return HalfLifeSounds.BA_IDLE_C2.get();
            case 3:  return HalfLifeSounds.BA_IDLE_C3.get();
            case 4:  return HalfLifeSounds.BA_IDLE_C4.get();
            case 5:  return HalfLifeSounds.BA_IDLE_C6.get();
            case 6:  return HalfLifeSounds.BA_IDLE_C7.get();
            case 7:  return HalfLifeSounds.BA_IDLE_C8.get();
            case 8:  return HalfLifeSounds.BA_IDLE_C5.get();
        }
        return HalfLifeSounds.HEADCRAB_1_ATTACK_1.get();
    }
    public SoundEvent getAnswerSound() {
        switch (this.random.nextInt(1,8)) {
            case 1:  return HalfLifeSounds.BA_IDLE_A1.get();
            case 2:  return HalfLifeSounds.BA_IDLE_A2.get();
            case 3:  return HalfLifeSounds.BA_IDLE_A3.get();
            case 4:  return HalfLifeSounds.BA_IDLE_A4.get();
            case 5:  return HalfLifeSounds.BA_IDLE_A5.get();
            case 6:  return HalfLifeSounds.BA_IDLE_A6.get();
            case 7:  return HalfLifeSounds.BA_IDLE_A7.get();
        }
        return HalfLifeSounds.HEADCRAB_1_ATTACK_1.get();
    }
    public SoundEvent getIdleHurtSound() {
        switch (this.random.nextInt(1,3)) {
            case 1:  return HalfLifeSounds.BA_IDLE_HURT1.get();
            case 2:  return HalfLifeSounds.BA_IDLE_HURT2.get();
        }
        return HalfLifeSounds.HEADCRAB_1_ATTACK_1.get();
    }
    public SoundEvent getIdlePLHurtSound() {
        switch (this.random.nextInt(1,3)) {
            case 1:  return HalfLifeSounds.BA_IDLE_PL_HURT1.get();
            case 2:  return HalfLifeSounds.BA_IDLE_PL_HURT2.get();
        }
        return HalfLifeSounds.HEADCRAB_1_ATTACK_1.get();
    }
    public SoundEvent getKillReactionSound() {
        switch (this.random.nextInt(1,7)) {
            case 1:  return HalfLifeSounds.BA_KILL1.get();
            case 2:  return HalfLifeSounds.BA_KILL2.get();
            case 3:  return HalfLifeSounds.BA_KILL3.get();
            case 4:  return HalfLifeSounds.BA_KILL4.get();
            case 5:  return HalfLifeSounds.BA_KILL5.get();
            case 6:  return HalfLifeSounds.BA_KILL6.get();
        }
        return HalfLifeSounds.HEADCRAB_1_ATTACK_1.get();
    }


    @Override
    public SoundEvent getWarningSound() {
        switch (this.random.nextInt(1,6)) {
            case 1:  return HalfLifeSounds.BA_WARN1.get();
            case 2:  return HalfLifeSounds.BA_WARN2.get();
            case 3:  return HalfLifeSounds.BA_WARN3.get();
            case 4:  return HalfLifeSounds.BA_WARN4.get();
            case 5:  return HalfLifeSounds.BA_WARN6.get();
        }
        return HalfLifeSounds.HEADCRAB_1_ATTACK_1.get();
    }

    @Override
    public SoundEvent getAttackReactionSound() {
        switch (this.random.nextInt(1,5)) {
            case 1:  return HalfLifeSounds.BA_AP1.get();
            case 2:  return HalfLifeSounds.BA_AP2.get();
            case 3:  return HalfLifeSounds.BA_AP3.get();
            case 4:  return HalfLifeSounds.BA_AP4.get();
        }
        return HalfLifeSounds.HEADCRAB_1_ATTACK_1.get();
    }



    @Override
    public SoundEvent getStartFollowingSound() {
        switch (this.random.nextInt(1,8)) {
            case 1:  return HalfLifeSounds.BA_FOLLOW1.get();
            case 2:  return HalfLifeSounds.BA_FOLLOW2.get();
            case 3:  return HalfLifeSounds.BA_FOLLOW3.get();
            case 4:  return HalfLifeSounds.BA_FOLLOW4.get();
            case 5:  return HalfLifeSounds.BA_FOLLOW5.get();
            case 6:  return HalfLifeSounds.BA_FOLLOW6.get();
            case 7:  return HalfLifeSounds.BA_FOLLOW7.get();
        }
        return HalfLifeSounds.HEADCRAB_1_ATTACK_1.get();
    }
    @Override
    public SoundEvent getStopFollowingSound() {
        switch (this.random.nextInt(1,8)) {
            case 1:  return HalfLifeSounds.BA_STAY1.get();
            case 2:  return HalfLifeSounds.BA_STAY2.get();
            case 3:  return HalfLifeSounds.BA_STAY3.get();
            case 4:  return HalfLifeSounds.BA_STAY4.get();
            case 5:  return HalfLifeSounds.BA_STAY5.get();
            case 6:  return HalfLifeSounds.BA_STAY6.get();
            case 7:  return HalfLifeSounds.BA_STAY7.get();
        }
        return HalfLifeSounds.HEADCRAB_1_ATTACK_1.get();
    }



    @Nullable
    protected SoundEvent getHurtSound(DamageSource pDamageSource) {
        switch (this.random.nextInt(1,4)) {
            case 1:  return HalfLifeSounds.GUARD_PAIN_1.get();
            case 2:  return HalfLifeSounds.GUARD_PAIN_2.get();
            case 3:  return HalfLifeSounds.GUARD_PAIN_3.get();
        }
        return HalfLifeSounds.HEADCRAB_1_ATTACK_1.get();
    }

    @Nullable
    protected SoundEvent getDeathSound() {
        switch (this.random.nextInt(1,4)) {
            case 1:  return HalfLifeSounds.BA_DIE1.get();
            case 2:  return HalfLifeSounds.BA_DIE2.get();
            case 3:  return HalfLifeSounds.BA_DIE3.get();
        }
        return HalfLifeSounds.HEADCRAB_1_ATTACK_1.get();
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
    protected Brain.Provider<?> brainProvider() {
        return new SmartBrainProvider<>(this);
    }

    @Override
    protected void customServerAiStep() {
        if (!this.level.getGameRules().getRule(HalfLifeGameRules.HALF_LIFE_AI_IS_ON).get()) return;
        tickBrain(this);
    }



    @Override
    public List<ExtendedSensor<Barney>> getSensors() {
        return ObjectArrayList.of(
                new HurtBySensor<>(),
                new NearbyPlayersSensor<>(),
                new NearbyLivingEntitySensor<Barney>()
                        .setPredicate((target, entity) ->
                                target instanceof Enemy || this.ismyenemy(target)));
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
                        new CustomBehaviour<>(entity -> this.entityData.set(IS_ANGRY, false)).startCondition(entity -> this.entityData.get(IS_ANGRY).equals(true)),
                        new TargetOrRetaliateHLT<>(),
                        new SetPlayerLookTarget<>(),
                        new HL1FollowSpeedup<>().cooldownFor(entity -> 25),
                        new SetRandomLookTarget<>()),
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
                new CustomBehaviour<>(entity -> this.entityData.set(IS_ANGRY, true)).startCondition(entity -> this.entityData.get(IS_ANGRY).equals(false)),
                new SetWalkTargetToRandomSpotAroundAttackTarget<Barney>().speedMod(1.3f),
                new StopAndShoot<Barney>(16, 3, 4f).startCondition(entity -> this.distanceTo(HLperUtil.TargetOrThis(this)) < 12)
                                .whenStarting(entity -> triggerAnim("attack", "shoot")).startCondition(entity -> this.entityData.get(IS_ANGRY))
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




    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(new AnimationController<>(this, "noflash", 0, this::flashpredicate));
        controllerRegistrar.add(new AnimationController<>(this, "move", 0, this::predicate));
        controllerRegistrar.add(new AnimationController<>(this, "angry", 0, this::attackpredicate));
        controllerRegistrar.add(new AnimationController<>(this, "attack", state -> PlayState.STOP)
                .triggerableAnim("shoot", RawAnimation.begin().then("animation.guard.shoot", Animation.LoopType.PLAY_ONCE)))
        ;}


    private <T extends GeoAnimatable> PlayState flashpredicate(AnimationState<T> tAnimationState) {
        tAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.guard.no_flash", Animation.LoopType.LOOP));
        return PlayState.CONTINUE;
    }


    private <T extends GeoAnimatable> PlayState attackpredicate(AnimationState<T> tAnimationState) {
        if(this.entityData.get(IS_ANGRY)) {
            tAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.guard.aim_straight", Animation.LoopType.LOOP));
            return PlayState.CONTINUE;
        }
        tAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.guard.no_gun", Animation.LoopType.LOOP));
        return PlayState.CONTINUE;
    }

    private <T extends GeoAnimatable> PlayState predicate(AnimationState<T> tAnimationState) {


        if(tAnimationState.isMoving()) {
            tAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.guard.walk", Animation.LoopType.LOOP));
            return PlayState.CONTINUE;
        }


        tAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.guard.idle", Animation.LoopType.LOOP));

        return PlayState.CONTINUE;

    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }



}
