package net.sirkotok.half_life_mod.entity.mob_geckolib.custom;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import net.sirkotok.half_life_mod.entity.base.HalfLifeMonster;
import net.sirkotok.half_life_mod.entity.brain.behaviour.BiteWhileJumpingBehavior;
import net.sirkotok.half_life_mod.entity.brain.behaviour.HeadCrabJumpBehavior;
import net.sirkotok.half_life_mod.entity.brain.behaviour.LeapTowardTargetBehavior;
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
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

import javax.annotation.Nullable;
import java.util.List;

public class Snark extends HalfLifeMonster implements GeoEntity, SmartBrainOwner<Snark> {

    private AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    public Snark(EntityType type, Level level) {
        super(type, level);
        this.xpReward = 0;
    }


    @Override
    public boolean shouldDropExperience() {
        return !(this.getSpawnType().equals(MobSpawnType.REINFORCEMENT));
    }

    public static final EntityDataAccessor<Integer> ROT = SynchedEntityData.defineId(Bullsquid.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Boolean> LIVED = SynchedEntityData.defineId(Bullsquid.class, EntityDataSerializers.BOOLEAN);


    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(ROT, 1);
        this.entityData.define(LIVED, false);
    }


    @Override
    public void tick() {
        super.tick();
        if (this.tickCount % 50 == 0) {
            this.entityData.set(ROT, this.entityData.get(ROT)+1);
        }
        if (this.tickCount > 20) {
            this.entityData.set(LIVED, true);
        }

        if (this.tickCount > 400 && this.isAlive()) explode();
    }



    public void explode(){
        this.playSound(ModSounds.SNARK_BLAST.get(), 0.3f, 1);
        this.dead = true;
        this.discard();
    }





    protected SoundEvent getBiteSound() {
        return ModSounds.SNARK_ATTACK.get();
    }


    @Nullable
    protected SoundEvent getAmbientSound() {
            switch (this.random.nextInt(1,4)) {
                case 1:  return ModSounds.SNARK_IDLE_1.get();
                case 2:  return ModSounds.SNARK_IDLE_2.get();
    }
        return ModSounds.SNARK_IDLE_3.get();
    }



    @Nullable
    protected SoundEvent getDeathSound() {
        return ModSounds.SNARK_DIE.get();
    }







    public static AttributeSupplier setAttributes() {
        return Monster.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 1D)
                .add(Attributes.ATTACK_DAMAGE, 2f)
                .add(Attributes.ATTACK_SPEED, 5f)
                .add(Attributes.ATTACK_KNOCKBACK, 1.0f)
                .add(Attributes.MOVEMENT_SPEED, 0.42f).build();
    }



    /* controllerRegistrar.add(new AnimationController<>(this, "allways", 0, this::headpredicate));
        controllerRegistrar.add(new AnimationController<>(this, "main_loop", 0, this::predicate));
        controllerRegistrar.add(new AnimationController<>(this, "onetime", state -> PlayState.STOP)
                .triggerableAnim("bite", RawAnimation.begin().then("animation.bullsquid.bite", Animation.LoopType.PLAY_ONCE))*/

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(new AnimationController<>(this, "allways", 0, this::headpredicate));
        controllerRegistrar.add(new AnimationController<>(this, "controller", 0, this::predicate)); }

    private <T extends GeoAnimatable> PlayState headpredicate(AnimationState<T> tAnimationState) {
        tAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.snark.eye", Animation.LoopType.LOOP));
        return PlayState.CONTINUE;
    }

    private <T extends GeoAnimatable> PlayState predicate(AnimationState<T> tAnimationState) {

        if(!this.isOnGround()) {
            if (this.entityData.get(ROT) % 2 == 0)
            tAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.snark.rot1", Animation.LoopType.LOOP));
            else tAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.snark.rot2", Animation.LoopType.LOOP));
            return PlayState.CONTINUE;
        }



        if(tAnimationState.isMoving()) {
            tAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.snark.walk", Animation.LoopType.LOOP));
            return PlayState.CONTINUE;
        }

        return PlayState.STOP;
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    @Override
    protected float getSoundVolume() {
        return 0.3f;
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
    public List<ExtendedSensor<Snark>> getSensors() {
        return ObjectArrayList.of(
                new HurtBySensor<>(),
                new NearbyPlayersSensor<>(),
                new NearbyLivingEntitySensor<Snark>()
                        .setPredicate((target, entity) ->
                                (!(target instanceof Snark) && !(target instanceof Snarknest))));
    }



    @Override
    public BrainActivityGroup<Snark> getCoreTasks() { // These are the tasks that run all the time (usually)
        return BrainActivityGroup.coreTasks(
              //  new MakeTargetThis<Chumtoad>().cooldownFor(entity -> 100),
                new LookAtTarget<>(),                      // Have the entity turn to face and look at its current look target
                new MoveToWalkTarget<>());
    }

    @Override
    public BrainActivityGroup<Snark> getIdleTasks() { // These are the tasks that run when the mob isn't doing anything else (usually)
        return BrainActivityGroup.idleTasks(
                new FirstApplicableBehaviour<Snark>(
                        new TargetOrRetaliate<>().startCondition(entity -> this.entityData.get(LIVED)),
                        new SetPlayerLookTarget<>(),
                        new SetRandomLookTarget<>()),
                new OneRandomBehaviour<>(
                        new SetRandomWalkTarget<>(),          // Set a random walk target to a nearby position
                        new Idle<>().runFor(entity -> entity.getRandom().nextInt(10, 20)))); // Do nothing for 1.5->4.5 seconds
    }




    @Override
    public BrainActivityGroup<Snark> getFightTasks() { // These are the tasks that handle fighting
        return BrainActivityGroup.fightTasks(
                new InvalidateAttackTarget<>(),
                new Retaliate<>(),
                new SetWalkTargetToAttackTarget<>(),
                new BiteWhileJumpingBehavior<>(10, this.getBiteSound(), -1),
                new LeapTowardTargetBehavior<>(5, 1f),
                new HeadCrabJumpBehavior<>(5, null, null)
                );
    }



















}
