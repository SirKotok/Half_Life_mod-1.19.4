package net.sirkotok.half_life_mod.entity.mob.custom;


import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.BlockPos;
import net.minecraft.network.protocol.game.ClientboundSetPassengersPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.fluids.FluidType;
import net.sirkotok.half_life_mod.entity.base.CatchableCreature;
import net.sirkotok.half_life_mod.entity.base.HalfLifeEntity;
import net.sirkotok.half_life_mod.entity.base.HalfLifeNeutral;
import net.sirkotok.half_life_mod.entity.brain.behaviour.BiteWhileJumpingBehavior;
import net.sirkotok.half_life_mod.entity.brain.behaviour.HeadCrabJumpBehavior;
import net.sirkotok.half_life_mod.entity.brain.behaviour.Retaliate;
import net.sirkotok.half_life_mod.item.ModItems;
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

import java.util.List;


public class Shockroach extends CatchableCreature implements GeoEntity, SmartBrainOwner<Shockroach> {

    public Item getweopon(){
        return ModItems.SHOCKROACH_ITEM.get();
    }

    @Override
    protected void actuallyHurt(DamageSource p_21240_, float p_21241_) {
        if (p_21240_.is(DamageTypes.IN_WALL)) {
            return; }

        super.actuallyHurt(p_21240_, p_21241_);
    }




    @Override
    public boolean canBeLeashed(Player p_21418_) {
        return !this.isLeashed();
    }

    private AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);




    public static final EntityDataAccessor<Boolean> IS_ANGRY = SynchedEntityData.defineId(Shockroach.class, EntityDataSerializers.BOOLEAN);




    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(IS_ANGRY, false);
    }
    protected boolean isangry() {
        return this.entityData.get(IS_ANGRY);
    }

    


    public Shockroach(EntityType type, Level level) {
        super(type, level);
        this.xpReward = 1;
    }


    public static AttributeSupplier setAttributes() {
        return Monster.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 5D)
                .add(Attributes.ATTACK_DAMAGE, 3f)
                .add(Attributes.ATTACK_SPEED, 1.0f)
                .add(Attributes.ATTACK_KNOCKBACK, 1.0f)
                .add(Attributes.MOVEMENT_SPEED, 0.16f).build();
    }



    @Override
    protected float getSoundVolume() {
        return 0.5f;
    }


    protected SoundEvent getJumpSound() {
        switch (this.random.nextInt(1,3)) {
            case 1:  return ModSounds.SHOCKROACH_JUMP_1.get();
            case 2:  return ModSounds.SHOCKROACH_JUMP_2.get();
    }
    return ModSounds.HEADCRAB_1_ATTACK_1.get();
    }

    protected SoundEvent getBiteSound() {
        return ModSounds.SHOCKROACH_BITE.get();
    }

    protected SoundEvent getHurtSound(DamageSource p_33034_) {
        return ModSounds.SHOCKROACH_PAIN.get();
    }

    protected SoundEvent getDeathSound() {
        return ModSounds.SHOCKROACH_DIE.get();
    }


    protected SoundEvent getAmbientSound() {
        if (this.isangry()) {
            switch (this.random.nextInt(1,3)) {
                case 1:  return ModSounds.SHOCKROACH_ANGRY.get();
            }
        }
        switch (this.random.nextInt(1,4)) {
            case 1:  return ModSounds.SHOCKROACH_IDLE_3.get();
            case 2:  return ModSounds.SHOCKROACH_IDLE_2.get();
            case 3:  return ModSounds.SHOCKROACH_IDLE_1.get();
        }
        return ModSounds.HEADCRAB_1_ALERT_1.get();
    }


    protected SoundEvent getStepSound() {
        return SoundEvents.FROG_STEP;
    }
    @Override
    protected void playStepSound(BlockPos pPos, BlockState pState) {
        BlockState blockstate = this.level.getBlockState(pPos.above());
        boolean flag = blockstate.is(BlockTags.INSIDE_STEP_SOUND_BLOCKS);
        if (flag || !pState.getMaterial().isLiquid()) {
            this.playSound(getStepSound());
        }
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
    public List<ExtendedSensor<Shockroach>> getSensors() {
        return ObjectArrayList.of(
                new HurtBySensor<>(),
                new NearbyPlayersSensor<>(),
                new NearbyLivingEntitySensor<Shockroach>()
                        .setPredicate((target, entity) ->
                            target instanceof Player || target instanceof IronGolem || target instanceof HalfLifeNeutral ||
                            target instanceof AbstractVillager));
    }



    @Override
    public BrainActivityGroup<Shockroach> getCoreTasks() { // These are the tasks that run all the time (usually)
        return BrainActivityGroup.coreTasks(
                new LookAtTarget<>(),                      // Have the entity turn to face and look at its current look target
                new MoveToWalkTarget<>());
    }

    @Override
    public BrainActivityGroup<Shockroach> getIdleTasks() { // These are the tasks that run when the mob isn't doing anything else (usually)
        return BrainActivityGroup.idleTasks(
                new FirstApplicableBehaviour<Shockroach>(

                        new TargetOrRetaliate<>(),

                        new SetPlayerLookTarget<>(),
                        new SetRandomLookTarget<>()),
                new OneRandomBehaviour<>(

                        new SetRandomWalkTarget<>(),          // Set a random walk target to a nearby position
                        new Idle<>().runFor(entity -> entity.getRandom().nextInt(30, 90)).whenStarting(entity -> this.entityData.set(IS_ANGRY, false)))); // Do nothing for 1.5->4.5 seconds
    }




    @Override
    public BrainActivityGroup<Shockroach> getFightTasks() { // These are the tasks that handle fighting
        return BrainActivityGroup.fightTasks(
                new InvalidateAttackTarget<>(),
                new Retaliate<>(),
                new SetWalkTargetToAttackTarget<>().whenStarting(entity -> this.entityData.set(IS_ANGRY, true)),
                new BiteWhileJumpingBehavior<>(30, getBiteSound(), -1f).startCondition(entity -> !isOnGround()).cooldownFor(entity -> 20),
                new HeadCrabJumpBehavior<>(14, getJumpSound(), null).SetMaxDistance(4)
                        .whenStarting(entity -> triggerAnim("jump", "jump"))
                        .cooldownFor(entity -> 45)
        );

    }

     @Override
    public void tick() {
         super.tick();
         if (this.tickCount > 400 && this.isAlive()) {
             this.playSound(getDeathSound());
             this.setHealth(0);
         }
    }



    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {


        controllerRegistrar.add(new AnimationController<>(this, "controller", 0, this::predicate));
        controllerRegistrar.add(new AnimationController<>(this, "jump", state -> PlayState.STOP)
                .triggerableAnim("jump", RawAnimation.begin().then("animation.shockroach.jump", Animation.LoopType.PLAY_ONCE)));


    }



    private <T extends GeoAnimatable> PlayState predicate(AnimationState<T> tAnimationState) {



        if(tAnimationState.isMoving() && this.isOnGround()) {
            tAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.shockroach.walk", Animation.LoopType.LOOP));
            return PlayState.CONTINUE;
        }

        tAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.shockroach.idle", Animation.LoopType.LOOP));
        return PlayState.CONTINUE;

    }





    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

}

