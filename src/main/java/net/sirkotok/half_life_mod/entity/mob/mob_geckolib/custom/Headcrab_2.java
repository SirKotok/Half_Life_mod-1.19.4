package net.sirkotok.half_life_mod.entity.mob.mob_geckolib.custom;


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
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.sirkotok.half_life_mod.entity.base.HalfLifeMonster;
import net.sirkotok.half_life_mod.entity.brain.behaviour.BiteWhileJumpingBehavior;
import net.sirkotok.half_life_mod.entity.brain.behaviour.HeadCrabJumpBehavior;

import net.sirkotok.half_life_mod.entity.brain.behaviour.Retaliate;
import net.sirkotok.half_life_mod.entity.brain.behaviour.TargetOrRetaliateHLT;
import net.sirkotok.half_life_mod.misc.gamerules.HalfLifeGameRules;
import net.sirkotok.half_life_mod.sound.HalfLifeSounds;
import net.sirkotok.half_life_mod.misc.util.HLTags;
import net.sirkotok.half_life_mod.misc.util.HLperUtil;
import net.sirkotok.half_life_mod.misc.util.InfightingUtil;
import net.tslat.smartbrainlib.api.SmartBrainOwner;
import net.tslat.smartbrainlib.api.core.BrainActivityGroup;
import net.tslat.smartbrainlib.api.core.SmartBrainProvider;
import net.tslat.smartbrainlib.api.core.behaviour.FirstApplicableBehaviour;
import net.tslat.smartbrainlib.api.core.behaviour.OneRandomBehaviour;
import net.tslat.smartbrainlib.api.core.behaviour.custom.look.LookAtTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.misc.AvoidSun;
import net.tslat.smartbrainlib.api.core.behaviour.custom.misc.Idle;
import net.tslat.smartbrainlib.api.core.behaviour.custom.move.FleeTarget;
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

import java.util.List;


public class Headcrab_2 extends HalfLifeMonster implements GeoEntity, SmartBrainOwner<Headcrab_2> {

    @Override
    protected float getSoundVolume() {
        return 0.5f;
    }

    @Override
    public boolean canBeLeashed(Player p_21418_) {
        return !this.isLeashed();
    }


    private AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);




    public static final EntityDataAccessor<Boolean> IS_ANGRY = SynchedEntityData.defineId(Headcrab_2.class, EntityDataSerializers.BOOLEAN);

    public static final EntityDataAccessor<Boolean> NO_DROP = SynchedEntityData.defineId(Headcrab_2.class, EntityDataSerializers.BOOLEAN);


    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(IS_ANGRY, false);
        this.entityData.define(NO_DROP, false);
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
    protected boolean getNoDrop(){
        return  this.entityData.get(NO_DROP);
    }

    public void addAdditionalSaveData(CompoundTag p_33619_) {
        super.addAdditionalSaveData(p_33619_);
        p_33619_.putBoolean("dontdrop", this.getNoDrop());
    }

    public void readAdditionalSaveData(CompoundTag p_33607_) {;
        this.setNoDrop(p_33607_.getBoolean("dontdrop"));
        super.readAdditionalSaveData(p_33607_);
    }

    @Override
    public boolean shouldDropExperience() {
        return !getNoDrop();
    }





    public Headcrab_2(EntityType type, Level level) {
        super(type, level);
        this.setPathfindingMalus(BlockPathTypes.WATER, -1.0F);
        this.xpReward = 3;
    }


    public static AttributeSupplier setAttributes() {
        return Monster.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 8D)
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
        switch (this.random.nextInt(1,4)) {
            case 1:  return HalfLifeSounds.HEADCRAB_1_ATTACK_1.get();
            case 2:  return HalfLifeSounds.HEADCRAB_1_ATTACK_2.get();
            case 3:  return HalfLifeSounds.HEADCRAB_1_ATTACK_3.get();
        }
        return HalfLifeSounds.HEADCRAB_1_ATTACK_1.get();
    }

    protected SoundEvent getBiteSound() {
        return HalfLifeSounds.HEADCRAB_2_HEADBITE.get();
    }

    protected SoundEvent getHurtSound(DamageSource p_33034_) {
        if (p_33034_.is(DamageTypes.ON_FIRE)) return HalfLifeSounds.HEADCRAB_2_BURNING.get();
        switch (this.random.nextInt(1,4)) {
            case 1:  return HalfLifeSounds.HEADCRAB_1_PAIN_1.get();
            case 2:  return HalfLifeSounds.HEADCRAB_1_PAIN_2.get();
            case 3:  return HalfLifeSounds.HEADCRAB_1_PAIN_3.get();
        }
        return HalfLifeSounds.HEADCRAB_1_PAIN_1.get();
    }

    protected SoundEvent getDeathSound() {
        switch (this.random.nextInt(1,3)) {
            case 1:  return HalfLifeSounds.HEADCRAB_1_DIE_1.get();
            case 2:  return HalfLifeSounds.HEADCRAB_1_DIE_2.get();
        }
        return HalfLifeSounds.HEADCRAB_1_DIE_1.get();
    }


    protected SoundEvent getAmbientSound() {



        if (this.isangry()) {
            return HalfLifeSounds.HEADCRAB_1_ALERT_1.get();
            }

        switch (this.random.nextInt(1,4)) {
            case 1:  return HalfLifeSounds.HEADCRAB_1_IDLE_1.get();
            case 2:  return HalfLifeSounds.HEADCRAB_1_IDLE_2.get();
            case 3:  return HalfLifeSounds.HEADCRAB_1_IDLE_3.get();
        }
        return HalfLifeSounds.HEADCRAB_1_ALERT_1.get();
    }





    @Override
    protected Brain.Provider<?> brainProvider() {
        return new SmartBrainProvider<>(this);
    }

    @Override
    protected void customServerAiStep() {
        if (!this.level.getGameRules().getRule(HalfLifeGameRules.HALF_LIFE_AI_IS_ON).get()) return;

        if (!this.isInWaterOrBubble()) {
        tickBrain(this);}

    }



    @Override
    public List<ExtendedSensor<Headcrab_2>> getSensors() {
        return ObjectArrayList.of(
                new HurtBySensor<>(),
                new NearbyPlayersSensor<>(),
                new NearbyLivingEntitySensor<Headcrab_2>()
                        .setPredicate((target, entity)  ->
                                InfightingUtil.HeadcrabFactionSpecific(target) || InfightingUtil.commonenemy(target)
                        ));
    }



    @Override
    public BrainActivityGroup<Headcrab_2> getCoreTasks() { // These are the tasks that run all the time (usually)
        return BrainActivityGroup.coreTasks(

                new LookAtTarget<>(),                      // Have the entity turn to face and look at its current look target
                new MoveToWalkTarget<>());


    }

    @Override
    public BrainActivityGroup<Headcrab_2> getIdleTasks() {
        return BrainActivityGroup.idleTasks(
                new FirstApplicableBehaviour<Headcrab_2>(


                        new TargetOrRetaliateHLT<>(),
                        new AvoidSun<>(),
                        new SetPlayerLookTarget<>(),
                        new SetRandomLookTarget<>()),
                new OneRandomBehaviour<>(

                        new SetRandomWalkTarget<>(),
                        new Idle<>().runFor(entity -> entity.getRandom().nextInt(30, 90))));
    }




    @Override
    public BrainActivityGroup<Headcrab_2> getFightTasks() { // These are the tasks that handle fighting
        return BrainActivityGroup.fightTasks(

                new InvalidateAttackTarget<>(),
                new SetWalkTargetToAttackTarget<>(),
                new Retaliate<>(),
                new FleeTarget<>().fleeDistance(3).startCondition(entity -> distanceTo(HLperUtil.TargetOrThis(this)) < 3.5), // .stopIf(entity -> distanceTo(BrainUtils.getTargetOfEntity(this)) > 3.5),
          //      new StrafeTarget<>().strafeDistance(3.5f).stopStrafingWhen(entity -> distanceTo(BrainUtils.getTargetOfEntity(this)) > 3.5),
                new BiteWhileJumpingBehavior<>(30, getBiteSound(), 0.2f).startCondition(entity -> !isOnGround()).cooldownFor(entity -> 20),
                new HeadCrabJumpBehavior<>(14, getJumpSound(), null).SetMinDistance(3).whenStarting(entity -> triggerAnim("jump", "jump")).cooldownFor(entity -> 65)
        );

    }

     @Override
    public void tick() {
         super.tick();
         if (!this.level.isClientSide && this.isPassenger()) {
             Entity target = this.getVehicle();
             if (!this.level.isClientSide && this.isPassenger()) {
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
                 if (target instanceof LivingEntity){
                     if (!(target.getType().is(HLTags.EntityTypes.FACTION_HEADCRAB))) {
                         this.playSound(this.getBiteSound());
                         ((LivingEntity) target).addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 50, 5, false, false), this);
                         this.doHurtTarget(target);}
                 }
             }
             }
    }



    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {


        controllerRegistrar.add(new AnimationController<>(this, "controller", 0, this::predicate));
        controllerRegistrar.add(new AnimationController<>(this, "jump", state -> PlayState.STOP)
                .triggerableAnim("jump", RawAnimation.begin().then("animation.headcrab.jump", Animation.LoopType.PLAY_ONCE)));


    }


    private <T extends GeoAnimatable> PlayState predicate(AnimationState<T> tAnimationState) {

        if (this.isPassenger()) {
            tAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.headcrab.zombie", Animation.LoopType.LOOP));
            return PlayState.CONTINUE;
        }

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





