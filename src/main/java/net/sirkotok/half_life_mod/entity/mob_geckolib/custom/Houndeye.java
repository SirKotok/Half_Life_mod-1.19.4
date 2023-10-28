package net.sirkotok.half_life_mod.entity.mob_geckolib.custom;


import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.sirkotok.half_life_mod.entity.base.HalfLifeMonster;
import net.sirkotok.half_life_mod.entity.base.HalfLifeNeutral;
import net.sirkotok.half_life_mod.entity.brain.behaviour.Retaliate;
import net.sirkotok.half_life_mod.sound.ModSounds;
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
import net.tslat.smartbrainlib.api.core.behaviour.custom.target.TargetOrRetaliate;
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


public class Houndeye extends HalfLifeMonster implements GeoEntity, SmartBrainOwner<Houndeye> {



    @Override
    public boolean canBeLeashed(Player p_21418_) {
        return !this.isLeashed();
    }

    private AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    protected float chance = 0.1f;


    public static final EntityDataAccessor<Boolean> IS_LIGHT = SynchedEntityData.defineId(Houndeye.class, EntityDataSerializers.BOOLEAN);
    public static final EntityDataAccessor<Boolean> IS_LEADER = SynchedEntityData.defineId(Houndeye.class, EntityDataSerializers.BOOLEAN);
    public static final EntityDataAccessor<Integer> SQUAD_SIZE = SynchedEntityData.defineId(Houndeye.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Integer> DOG_ID = SynchedEntityData.defineId(Houndeye.class, EntityDataSerializers.INT);


    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(IS_LIGHT, false);
        this.entityData.define(IS_LEADER, false);
        this.entityData.define(SQUAD_SIZE, 1);
        this.entityData.define(DOG_ID, -1);
    }

    public boolean islight() {
        return this.entityData.get(IS_LIGHT);
    }
    public boolean isleader() {
        return this.entityData.get(IS_LEADER);
    }
    public int getsquaidsize() {
        return this.entityData.get(SQUAD_SIZE);
    }
    public int getdogid() {
        return this.entityData.get(DOG_ID);
    }

    protected void setlight(boolean glow) {
        this.entityData.set(IS_LIGHT, glow);
    }
    protected void makethisleader(boolean yes) {
        this.entityData.set(IS_LEADER, yes);
    }
    protected void setSquadSize(int size) {
        this.entityData.set(SQUAD_SIZE, size);
    }
    protected void setDogId(int id) {
        this.entityData.set(DOG_ID, id);
    }


    public void addAdditionalSaveData(CompoundTag p_33619_) {
        super.addAdditionalSaveData(p_33619_);
        p_33619_.putInt("Dog_id", this.getdogid() - 1 );
        p_33619_.putInt("Squad_size", this.getsquaidsize() - 1 );
        p_33619_.putBoolean("Light", this.islight());
        p_33619_.putBoolean("Leader", this.isleader());
    }

    public void readAdditionalSaveData(CompoundTag p_33607_) {
        this.setDogId(p_33607_.getInt("Dog_id") + 1);
        this.setSquadSize(p_33607_.getInt("Squad_size") + 1);
        this.setlight(p_33607_.getBoolean("Light"));
        this.makethisleader(p_33607_.getBoolean("Leader"));
        super.readAdditionalSaveData(p_33607_);
    }







    public Houndeye(EntityType type, Level level) {
        super(type, level);
        this.xpReward = 3;
    }


    public static AttributeSupplier setAttributes() {
        return Monster.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 15D)
                .add(Attributes.ATTACK_DAMAGE, 3f)
                .add(Attributes.ATTACK_SPEED, 1.0f)
                .add(Attributes.ATTACK_KNOCKBACK, 1.0f)
                .add(Attributes.MOVEMENT_SPEED, 0.28f).build();
    }



    @Override
    protected float getSoundVolume() {
        return 0.5f;
    }


    protected SoundEvent getJumpSound() {
        switch (this.random.nextInt(1,4)) {
            case 1:  return ModSounds.HEADCRAB_1_ATTACK_1.get();
            case 2:  return ModSounds.HEADCRAB_1_ATTACK_2.get();
            case 3:  return ModSounds.HEADCRAB_1_ATTACK_3.get();
    }
    return ModSounds.HEADCRAB_1_ATTACK_1.get();
    }

    protected SoundEvent getBiteSound() {
        return ModSounds.HEADCRAB_1_HEADBITE.get();
    }

    protected SoundEvent getHurtSound(DamageSource p_33034_) {
        switch (this.random.nextInt(1,4)) {
            case 1:  return ModSounds.HEADCRAB_1_PAIN_1.get();
            case 2:  return ModSounds.HEADCRAB_1_PAIN_2.get();
            case 3:  return ModSounds.HEADCRAB_1_PAIN_3.get();
        }
        return ModSounds.HEADCRAB_1_PAIN_1.get();
    }

    protected SoundEvent getDeathSound() {
        switch (this.random.nextInt(1,3)) {
            case 1:  return ModSounds.HEADCRAB_1_DIE_1.get();
            case 2:  return ModSounds.HEADCRAB_1_DIE_2.get();
        }
        return ModSounds.HEADCRAB_1_DIE_1.get();
    }


    protected SoundEvent getAmbientSound() {

        return ModSounds.HEADCRAB_1_ALERT_1.get();
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
    public List<ExtendedSensor<Houndeye>> getSensors() {
        return ObjectArrayList.of(
                new HurtBySensor<>(),
                new NearbyPlayersSensor<>(),
                new NearbyLivingEntitySensor<Houndeye>()
                        .setPredicate((target, entity) ->
                            target instanceof Player || target instanceof IronGolem || target instanceof HalfLifeNeutral ||
                            target instanceof AbstractVillager));
    }



    @Override
    public BrainActivityGroup<Houndeye> getCoreTasks() { // These are the tasks that run all the time (usually)
        return BrainActivityGroup.coreTasks(
                new LookAtTarget<>(),                      // Have the entity turn to face and look at its current look target
                new MoveToWalkTarget<>());
    }

    @Override
    public BrainActivityGroup<Houndeye> getIdleTasks() { // These are the tasks that run when the mob isn't doing anything else (usually)
        return BrainActivityGroup.idleTasks(
                new FirstApplicableBehaviour<Houndeye>(

                        new TargetOrRetaliate<>(),

                        new SetPlayerLookTarget<>(),
                        new SetRandomLookTarget<>()),
                new OneRandomBehaviour<>(

                        new SetRandomWalkTarget<>(),          // Set a random walk target to a nearby position
                        new Idle<>().runFor(entity -> entity.getRandom().nextInt(30, 90)))); // Do nothing for 1.5->4.5 seconds
    }




    @Override
    public BrainActivityGroup<Houndeye> getFightTasks() { // These are the tasks that handle fighting
        return BrainActivityGroup.fightTasks(
                new InvalidateAttackTarget<>(),
                new Retaliate<>(),
                new SetWalkTargetToAttackTarget<>()

                   );

    }

     @Override
    public void tick() {
        super.tick();
    }



    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {

        controllerRegistrar.add(new AnimationController<>(this, "noattack", 0, this::noattackpredicate));
        controllerRegistrar.add(new AnimationController<>(this, "loops", 0, this::predicate));
        controllerRegistrar.add(new AnimationController<>(this, "blink", state -> PlayState.STOP)
                .triggerableAnim("blink", RawAnimation.begin().then("animation.houndeye.blink", Animation.LoopType.PLAY_ONCE)));
        controllerRegistrar.add(new AnimationController<>(this, "long", state -> PlayState.STOP)
                .triggerableAnim("guard", RawAnimation.begin().then("animation.houndeye.standguard", Animation.LoopType.PLAY_ONCE))
                .triggerableAnim("sleep", RawAnimation.begin().then("animation.houndeye.sleep", Animation.LoopType.PLAY_ONCE))
                .triggerableAnim("startled", RawAnimation.begin().then("animation.houndeye.startled", Animation.LoopType.PLAY_ONCE))
                .triggerableAnim("jump", RawAnimation.begin().then("animation.houndeye.jumpback", Animation.LoopType.PLAY_ONCE))
                .triggerableAnim("attack", RawAnimation.begin().then("animation.houndeye.attack", Animation.LoopType.PLAY_ONCE))
                .triggerableAnim("curious", RawAnimation.begin().then("animation.houndeye.curious", Animation.LoopType.PLAY_ONCE))
        );
        controllerRegistrar.add(new AnimationController<>(this, "attack", state -> PlayState.STOP)
                .triggerableAnim("attack", RawAnimation.begin().then("animation.houndeye.actuallyattack", Animation.LoopType.PLAY_ONCE)));

    }


    private <T extends GeoAnimatable> PlayState noattackpredicate(AnimationState<T> tAnimationState) {
        tAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.houndeye.noattack", Animation.LoopType.LOOP));
        return PlayState.CONTINUE;
    }
    private <T extends GeoAnimatable> PlayState predicate(AnimationState<T> tAnimationState) {



        if(tAnimationState.isMoving() && this.isOnGround()) {
            tAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.houndeye.walk", Animation.LoopType.LOOP));
            return PlayState.CONTINUE;
        }

        tAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.houndeye.idle", Animation.LoopType.LOOP));
        return PlayState.CONTINUE;

    }






    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }




    @Nullable
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor p_33601_, DifficultyInstance p_33602_, MobSpawnType p_33603_, @Nullable SpawnGroupData p_33604_, @Nullable CompoundTag p_33605_) {
        RandomSource randomsource = p_33601_.getRandom();
        float i = randomsource.nextFloat();
        if (i < chance) this.setlight(true);
        int j = randomsource.nextInt(1000);
        this.setDogId(j);
        return super.finalizeSpawn(p_33601_, p_33602_, p_33603_, p_33604_, p_33605_);
    }




}

