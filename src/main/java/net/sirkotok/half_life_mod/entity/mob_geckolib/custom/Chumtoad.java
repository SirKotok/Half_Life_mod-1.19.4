package net.sirkotok.half_life_mod.entity.mob_geckolib.custom;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.NeutralMob;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.fluids.FluidType;
import net.sirkotok.half_life_mod.entity.base.CatchableMonster;

import net.sirkotok.half_life_mod.entity.brain.behaviour.Retaliate;
import net.sirkotok.half_life_mod.item.ModItems;
import net.tslat.smartbrainlib.api.SmartBrainOwner;
import net.tslat.smartbrainlib.api.core.BrainActivityGroup;
import net.tslat.smartbrainlib.api.core.SmartBrainProvider;
import net.tslat.smartbrainlib.api.core.behaviour.FirstApplicableBehaviour;
import net.tslat.smartbrainlib.api.core.behaviour.OneRandomBehaviour;
import net.tslat.smartbrainlib.api.core.behaviour.custom.look.LookAtTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.misc.Idle;
import net.tslat.smartbrainlib.api.core.behaviour.custom.move.FleeTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.move.MoveToWalkTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.path.SetRandomWalkTarget;
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

public class Chumtoad extends CatchableMonster implements GeoEntity, SmartBrainOwner<Chumtoad> {

    public Item getweopon(){
        return ModItems.CHUMTOAD_THROWER.get();
    }

    private AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    public Chumtoad(EntityType type, Level level) {
        super(type, level);
        this.xpReward = 3;
    }



    @Override
    public boolean canDrownInFluidType(FluidType type) {
        if (type.equals(ForgeMod.WATER_TYPE.get())) {
            return false;
        }
        return super.canDrownInFluidType(type);
    }

    @Override
    protected float getWaterSlowDown() {
        return 0.98f;
    }


    @Override
    public boolean isPushedByFluid(FluidType type) {
        if (type.equals(ForgeMod.WATER_TYPE.get())) {
            return false;
        }
        return super.isPushedByFluid(type);
    }



    @Override
    public double getFluidMotionScale(FluidType type) {
        if (type.equals(ForgeMod.WATER_TYPE.get())) {
            return 1;
        }
        return super.getFluidMotionScale(type);
    }

    @Override
    public double getFluidJumpThreshold() {
        return 0.0D;
    }

    @Override
    public void jumpInFluid(FluidType type) {
        self().setDeltaMovement(self().getDeltaMovement().add(0.0D, 0.5f, 0.0D));
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



    public static AttributeSupplier setAttributes() {
        return Monster.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 10D)
                .add(Attributes.ATTACK_DAMAGE, 3f)
                .add(Attributes.ATTACK_SPEED, 1.0f)
                .add(Attributes.ATTACK_KNOCKBACK, 1.0f)
                .add(Attributes.MOVEMENT_SPEED, 0.3f).build();
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(new AnimationController<>(this, "controller", 0, this::predicate)); }

    private <T extends GeoAnimatable> PlayState predicate(AnimationState<T> tAnimationState) {

        if(!this.isOnGround()) {
            tAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.chumtoad.jump", Animation.LoopType.LOOP));
            return PlayState.CONTINUE;
        }
        if(tAnimationState.isMoving()) {
            tAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.chumtoad.hop", Animation.LoopType.LOOP));
            return PlayState.CONTINUE;
        }

        tAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.chumtoad.idle", Animation.LoopType.LOOP));
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
    public List<ExtendedSensor<Chumtoad>> getSensors() {
        return ObjectArrayList.of(
                new HurtBySensor<>(),
                new NearbyPlayersSensor<>(),
                new NearbyLivingEntitySensor<Chumtoad>()
                        .setPredicate((target, entity) ->
                                (!(target instanceof Chumtoad) && target instanceof Enemy) || target instanceof NeutralMob));
    }



    @Override
    public BrainActivityGroup<Chumtoad> getCoreTasks() { // These are the tasks that run all the time (usually)
        return BrainActivityGroup.coreTasks(
              //  new MakeTargetThis<Chumtoad>().cooldownFor(entity -> 100),
                new LookAtTarget<>(),                      // Have the entity turn to face and look at its current look target
                new MoveToWalkTarget<>());
    }

    @Override
    public BrainActivityGroup<Chumtoad> getIdleTasks() { // These are the tasks that run when the mob isn't doing anything else (usually)
        return BrainActivityGroup.idleTasks(
                new FirstApplicableBehaviour<Chumtoad>(
                        new TargetOrRetaliate<>(),
                        new SetPlayerLookTarget<>(),
                        new SetRandomLookTarget<>()),
                new OneRandomBehaviour<>(
                        new SetRandomWalkTarget<>(),          // Set a random walk target to a nearby position
                        new Idle<>().runFor(entity -> entity.getRandom().nextInt(30, 90)))); // Do nothing for 1.5->4.5 seconds
    }




    @Override
    public BrainActivityGroup<Chumtoad> getFightTasks() { // These are the tasks that handle fighting
        return BrainActivityGroup.fightTasks(
                new InvalidateAttackTarget<>(),
                new Retaliate<>(),
                 new FleeTarget<>().speedModifier(1.1f)
                );
    }





}
