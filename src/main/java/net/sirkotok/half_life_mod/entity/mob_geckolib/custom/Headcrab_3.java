package net.sirkotok.half_life_mod.entity.mob_geckolib.custom;


import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundSetPassengersPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.sirkotok.half_life_mod.entity.base.HalfLifeMonster;
import net.sirkotok.half_life_mod.entity.base.HalfLifeNeutral;
import net.sirkotok.half_life_mod.entity.brain.behaviour.AlyxcrabJumpBehavior;
import net.sirkotok.half_life_mod.entity.brain.behaviour.Retaliate;
import net.sirkotok.half_life_mod.sound.HalfLifeSounds;
import net.sirkotok.half_life_mod.util.HLTags;
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

import java.util.List;


public class Headcrab_3 extends HalfLifeMonster implements GeoEntity, SmartBrainOwner<Headcrab_3> {


    @Override
    protected float getSoundVolume() {
        return 0.5f;
    }


 //TODO: Change the jumpcode someday to be actually good



    @Override
    public boolean canBeLeashed(Player p_21418_) {
        return !this.isLeashed();
    }




    private AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);




    public static final EntityDataAccessor<Boolean> IS_ANGRY = SynchedEntityData.defineId(Headcrab_3.class, EntityDataSerializers.BOOLEAN);
    public static final EntityDataAccessor<Boolean> IS_JUMPING = SynchedEntityData.defineId(Headcrab_3.class, EntityDataSerializers.BOOLEAN);

    public static final EntityDataAccessor<Boolean> AI_STOP = SynchedEntityData.defineId(Headcrab_3.class, EntityDataSerializers.BOOLEAN);
    public static final EntityDataAccessor<Integer> AI_STOP_TIMESTAMP = SynchedEntityData.defineId(Headcrab_3.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Integer> JUMP_TIMESTAMP = SynchedEntityData.defineId(Headcrab_3.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Integer> AI_STOP_DELAY = SynchedEntityData.defineId(Headcrab_3.class, EntityDataSerializers.INT);

    public static final EntityDataAccessor<Boolean> NO_DROP = SynchedEntityData.defineId(Headcrab_3.class, EntityDataSerializers.BOOLEAN);

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(IS_ANGRY, false);
        this.entityData.define(IS_JUMPING, false);
        this.entityData.define(NO_DROP, false);
        this.entityData.define(AI_STOP, false);
        this.entityData.define(AI_STOP_TIMESTAMP, 0);
        this.entityData.define(JUMP_TIMESTAMP, 0);
        this.entityData.define(AI_STOP_DELAY, 0);
    }
    protected boolean isangry() {
        return this.entityData.get(IS_ANGRY);
    }


    @Override
    public void aiStep() {
        super.aiStep();
        if(this.getTags().contains("nodrop")) this.setNoDrop(true);
    }

    protected void setNoDrop(boolean noDrop){
        this.entityData.set(NO_DROP, noDrop);
    }
    protected boolean  getNoDrop(){
        return this.entityData.get(NO_DROP);
    }

    public void addAdditionalSaveData(CompoundTag p_33619_) {
        super.addAdditionalSaveData(p_33619_);
        p_33619_.putBoolean("dontdrop", this.getNoDrop());
    }

    public void readAdditionalSaveData(CompoundTag p_33607_) {;
        this.setNoDrop(p_33607_.getBoolean("dontdrop"));
        super.readAdditionalSaveData(p_33607_);
    }



    public void stopAiFor(int delay) {
        this.entityData.set(AI_STOP_TIMESTAMP, this.tickCount);
        this.entityData.set(AI_STOP, true);
        this.entityData.set(AI_STOP_DELAY, delay);
    }

    public int ai_stop_timestamp() {
        return this.entityData.get(AI_STOP_TIMESTAMP);
    }
    public boolean aistopped() {
        return this.entityData.get(AI_STOP);
    }
    public int ai_stop_remaining() {
        return this.entityData.get(AI_STOP_DELAY);
    }





    public Headcrab_3(EntityType type, Level level) {
        super(type, level);
        this.setPathfindingMalus(BlockPathTypes.WATER, -1.0F);
        this.xpReward = 3;
    }


    public static AttributeSupplier setAttributes() {
        return Monster.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 10D)
                .add(Attributes.ATTACK_DAMAGE, 3f)
                .add(Attributes.ATTACK_SPEED, 1.0f)
                .add(Attributes.ATTACK_KNOCKBACK, 1.0f)
                .add(Attributes.MOVEMENT_SPEED, 0.22f).build();
    }


    @Override
    protected void playHurtSound(DamageSource source) {
        if (source.is(DamageTypes.IN_WALL)) {
            return; }
        super.playHurtSound(source);
    }
    @Override
    protected void actuallyHurt(DamageSource source, float number) {
        if (source.is(DamageTypes.IN_WALL)) {
            return; }

        super.actuallyHurt(source, number*(this.getVehicle() != null && this.getVehicle().getType().is(HLTags.EntityTypes.FACTION_HEADCRAB) ? 0.5f : 1f));
    }

    protected SoundEvent getJumpSound() {
        return HalfLifeSounds.HEADCRAB_A_JUMP.get();
    }

    protected SoundEvent getBiteSound() {
        switch (this.random.nextInt(1,5)) {
            case 1:  return HalfLifeSounds.HEADCRAB_A_BITE_1.get();
            case 2:  return HalfLifeSounds.HEADCRAB_A_BITE_2.get();
            case 3:  return HalfLifeSounds.HEADCRAB_A_BITE_3.get();
            case 4:  return HalfLifeSounds.HEADCRAB_A_BITE_4.get();
        }
        return HalfLifeSounds.HEADCRAB_A_BITE_1.get();
    }

    protected SoundEvent getHurtSound(DamageSource p_33034_) {
        switch (this.random.nextInt(1,7)) {
            case 1:  return HalfLifeSounds.HEADCRAB_A_PAIN_1.get();
            case 2:  return HalfLifeSounds.HEADCRAB_A_PAIN_2.get();
            case 3:  return HalfLifeSounds.HEADCRAB_A_PAIN_3.get();
            case 4:  return HalfLifeSounds.HEADCRAB_A_PAIN_4.get();
            case 5:  return HalfLifeSounds.HEADCRAB_A_PAIN_5.get();
            case 6:  return HalfLifeSounds.HEADCRAB_A_PAIN_6.get();
        }
        return HalfLifeSounds.HEADCRAB_1_PAIN_1.get();
    }

    protected SoundEvent getDeathSound() {
        switch (this.random.nextInt(1,5)) {
            case 1:  return HalfLifeSounds.HEADCRAB_A_DIE_1.get();
            case 2:  return HalfLifeSounds.HEADCRAB_A_DIE_2.get();
            case 3:  return HalfLifeSounds.HEADCRAB_A_DIE_3.get();
            case 4:  return HalfLifeSounds.HEADCRAB_A_DIE_4.get();
        }
        return HalfLifeSounds.HEADCRAB_1_DIE_1.get();
    }


    protected SoundEvent getAmbientSound() {

        if (this.isangry()) {
            switch (this.random.nextInt(1,3)) {
                case 1:  return HalfLifeSounds.HEADCRAB_A_ALERT_1.get();
                case 2:  return HalfLifeSounds.HEADCRAB_A_ALERT_2.get();
            }
        }
        switch (this.random.nextInt(1,16)) {
            case 1:  return HalfLifeSounds.HEADCRAB_A_IDLE_1.get();
            case 2:  return HalfLifeSounds.HEADCRAB_A_IDLE_2.get();
            case 3:  return HalfLifeSounds.HEADCRAB_A_IDLE_3.get();
            case 4:  return HalfLifeSounds.HEADCRAB_A_IDLE_4.get();
            case 5:  return HalfLifeSounds.HEADCRAB_A_IDLE_5.get();
            case 6:  return HalfLifeSounds.HEADCRAB_A_IDLE_6.get();
            case 7:  return HalfLifeSounds.HEADCRAB_A_IDLE_7.get();
            case 8:  return HalfLifeSounds.HEADCRAB_A_IDLE_8.get();
            case 9:  return HalfLifeSounds.HEADCRAB_A_IDLE_9.get();
            case 10:  return HalfLifeSounds.HEADCRAB_A_IDLE_10.get();
            case 11:  return HalfLifeSounds.HEADCRAB_A_IDLE_11.get();
            case 12:  return HalfLifeSounds.HEADCRAB_A_IDLE_12.get();
            case 13:  return HalfLifeSounds.HEADCRAB_A_IDLE_13.get();
            case 14:  return HalfLifeSounds.HEADCRAB_A_IDLE_14.get();
            case 15:  return HalfLifeSounds.HEADCRAB_A_IDLE_15.get();
        }
        return HalfLifeSounds.HEADCRAB_1_ALERT_1.get();
    }





    @Override
    protected Brain.Provider<?> brainProvider() {
        return new SmartBrainProvider<>(this);
    }

    @Override
    protected void customServerAiStep() {
        if (!this.aistopped()) {
        if (!this.isInWaterOrBubble()) {
        tickBrain(this);}}
    }



    @Override
    public List<ExtendedSensor<Headcrab_3>> getSensors() {
        return ObjectArrayList.of(
                new HurtBySensor<>(),
                new NearbyPlayersSensor<>(),
                new NearbyLivingEntitySensor<Headcrab_3>()
                        .setPredicate((target, entity) ->
                            target instanceof Player || target instanceof IronGolem || target instanceof HalfLifeNeutral ||
                            target instanceof AbstractVillager));
    }



    @Override
    public BrainActivityGroup<Headcrab_3> getCoreTasks() { // These are the tasks that run all the time (usually)
        return BrainActivityGroup.coreTasks(
                new LookAtTarget<>().startCondition(entity -> !this.aistopped()),                      // Have the entity turn to face and look at its current look target
                new MoveToWalkTarget<>().startCondition(entity -> !this.aistopped()));


    }

    @Override
    public BrainActivityGroup<Headcrab_3> getIdleTasks() { // These are the tasks that run when the mob isn't doing anything else (usually)
        return BrainActivityGroup.idleTasks(
                new FirstApplicableBehaviour<Headcrab_3>(


                        new TargetOrRetaliate<>(),

                        new SetPlayerLookTarget<>(),
                        new SetRandomLookTarget<>()),
                new OneRandomBehaviour<>(

                        new SetRandomWalkTarget<>(),          // Set a random walk target to a nearby position
                        new Idle<>().runFor(entity -> entity.getRandom().nextInt(30, 90)))); // Do nothing for 1.5->4.5 seconds
    }




    @Override
    public BrainActivityGroup<Headcrab_3> getFightTasks() { // These are the tasks that handle fighting
        return BrainActivityGroup.fightTasks(
                new InvalidateAttackTarget<>(),
                new SetWalkTargetToAttackTarget<>(),
                new Retaliate<>(),
                new AlyxcrabJumpBehavior<>(12, 135, 35, getJumpSound(), getBiteSound(), 1f)
                        .whenStarting(entity -> this.entityData.set(JUMP_TIMESTAMP, this.tickCount))
                        .cooldownFor(entity -> 40)
        );

    }


    @Override
    public void tick() {
         super.tick();



         if ((this.tickCount - this.entityData.get(JUMP_TIMESTAMP) == 35) && !this.isPassenger()) {
             this.stopAiFor(100);
             this.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 100, 100, false, false, false));
         }

         if (aistopped() && ((this.tickCount - ai_stop_timestamp() > ai_stop_remaining()))) {
             this.entityData.set(AI_STOP, false);
         }



         if (!this.level.isClientSide && this.isPassenger() && (this.tickCount % 5) == 0) {
             this.triggerAnim("jump", "cancel");
             Entity target = this.getVehicle();
             if (target instanceof LivingEntity){
                 if  (!((LivingEntity) target).getItemBySlot(EquipmentSlot.HEAD).isEmpty()) {
                     this.stopRiding();
                     if(target instanceof ServerPlayer player) {
                         player.connection.send(new ClientboundSetPassengersPacket(player)); } // automatically done in 1.20.1 so no need to do that
                 }
                 if (target.getType().is(HLTags.EntityTypes.FACTION_HEADCRAB)) {
                     this.setYRot(target.getYRot());
                 }
             }
         }

         if (!this.level.isClientSide && this.isPassenger() && (this.tickCount % 35) == 0) {
             Entity target = this.getVehicle();
             if (target instanceof LivingEntity){
                 if (!(target.getType().is(HLTags.EntityTypes.FACTION_HEADCRAB))) {
                     this.playSound(this.getBiteSound());
                     ((LivingEntity) target).addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 50), this);
                     this.doHurtTarget(target);}
             }
         }
     }



    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {


        controllerRegistrar.add(new AnimationController<>(this, "controller", 0, this::predicate));
        controllerRegistrar.add(new AnimationController<>(this, "jump", state -> PlayState.STOP)
                .triggerableAnim("jump", RawAnimation.begin().then("animation.headcrab.jump", Animation.LoopType.PLAY_ONCE))
                .triggerableAnim("cancel", RawAnimation.begin().then("animation.headcrab.cancelanim", Animation.LoopType.PLAY_ONCE)));


    }


    private <T extends GeoAnimatable> PlayState predicate(AnimationState<T> tAnimationState) {



        if(tAnimationState.isMoving() && this.isOnGround()) {
            tAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.headcrab.walk2", Animation.LoopType.LOOP));
            return PlayState.CONTINUE;
        }
        if (this.isInWaterOrBubble()) {
            tAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.headcrab.drown", Animation.LoopType.LOOP));
            return PlayState.CONTINUE;
        }
        tAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.headcrab.idle", Animation.LoopType.LOOP));
        return PlayState.CONTINUE;

    }





    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

}





