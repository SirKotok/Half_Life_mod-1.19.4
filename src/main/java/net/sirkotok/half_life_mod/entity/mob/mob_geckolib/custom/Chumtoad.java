package net.sirkotok.half_life_mod.entity.mob.mob_geckolib.custom;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.NeutralMob;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.fluids.FluidType;
import net.sirkotok.half_life_mod.entity.base.CatchableMonster;

import net.sirkotok.half_life_mod.entity.brain.behaviour.LongJumpToJumpTarget;
import net.sirkotok.half_life_mod.entity.brain.behaviour.Retaliate;
import net.sirkotok.half_life_mod.entity.brain.behaviour.SetJumpTargetToRandom;
import net.sirkotok.half_life_mod.entity.brain.behaviour.SetJumpTargetToRandomSpotAwayFromAttackTarget;
import net.sirkotok.half_life_mod.misc.gamerules.HalfLifeGameRules;
import net.sirkotok.half_life_mod.item.HalfLifeItems;
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
        return HalfLifeItems.CHUMTOAD_THROWER.get();
    }

    private AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    public Chumtoad(EntityType type, Level level) {
        super(type, level);
        this.xpReward = 3;
    }



    public static final EntityDataAccessor<Integer> ID_TEXTURE = SynchedEntityData.defineId(Chumtoad.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Boolean> IS_SIXEYE = SynchedEntityData.defineId(Chumtoad.class, EntityDataSerializers.BOOLEAN);
    public static final EntityDataAccessor<Boolean> IS_LONGJUMP = SynchedEntityData.defineId(Chumtoad.class, EntityDataSerializers.BOOLEAN);
    public static final EntityDataAccessor<Float> FROG_SPEED = SynchedEntityData.defineId(Chumtoad.class, EntityDataSerializers.FLOAT);

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(IS_SIXEYE, false);
        this.entityData.define(IS_LONGJUMP, false);
        this.entityData.define(ID_TEXTURE, 0);
        this.entityData.define(FROG_SPEED, 0f);
    }
    public int gettexture() {
        return this.entityData.get(ID_TEXTURE);
    }
    public float getFrogSpeed() {
        return this.entityData.get(FROG_SPEED);
    }

    public void setFrogSpeed(float s) {
        this.entityData.set(FROG_SPEED, s);
    }
    public void settexture(int texture) {
        this.entityData.set(ID_TEXTURE, texture);
    }
    public boolean getIsSixeye() {
        return this.entityData.get(IS_SIXEYE);
    }
    public void setIsSixeye(boolean eye) {
        this.entityData.set(IS_SIXEYE, eye);
    }
    public boolean getIsLongJump() {
        return this.entityData.get(IS_LONGJUMP);
    }
    public void setIsLongjump(boolean jump) {
        this.entityData.set(IS_LONGJUMP, jump);
    }

    public void addAdditionalSaveData(CompoundTag p_33619_) {
        super.addAdditionalSaveData(p_33619_);
        p_33619_.putInt("ChumtoadTexture", this.gettexture());
        p_33619_.putBoolean("ChumtoadEye", this.getIsSixeye());
        p_33619_.putFloat("ChumtoadSpeed", this.getFrogSpeed());
        p_33619_.putBoolean("ChumtoadJump", this.getIsLongJump());
    }

    public void readAdditionalSaveData(CompoundTag p_33607_) {
        this.settexture(p_33607_.getInt("ChumtoadTexture"));
        this.setFrogSpeed(p_33607_.getInt("ChumtoadSpeed"));
        this.setIsSixeye(p_33607_.getBoolean("ChumtoadEye"));
        this.setIsLongjump(p_33607_.getBoolean("ChumtoadJump"));
        super.readAdditionalSaveData(p_33607_);
    }


    @Nullable
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor p_33601_, DifficultyInstance p_33602_, MobSpawnType p_33603_, @Nullable SpawnGroupData p_33604_, @Nullable CompoundTag p_33605_) {
        RandomSource randomsource = p_33601_.getRandom();
        this.setPersistenceRequired();
        if (p_33603_.equals(MobSpawnType.REINFORCEMENT))  return super.finalizeSpawn(p_33601_, p_33602_, p_33603_, p_33604_, p_33605_);
        if (randomsource.nextFloat() < 0.05f) this.setIsSixeye(true);
        if (randomsource.nextFloat() < 0.3) this.setFrogSpeed((randomsource.nextFloat()-randomsource.nextFloat())*0.1f);
        if (randomsource.nextFloat() < 0.3f) this.setIsLongjump(true);
        if (randomsource.nextFloat() < 0.05f + (this.getIsSixeye() ? 0.01f : 0.05f)) this.settexture(randomsource.nextInt(1, 6));

        return super.finalizeSpawn(p_33601_, p_33602_, p_33603_, p_33604_, p_33605_);
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
        if (!this.level.getGameRules().getRule(HalfLifeGameRules.HALF_LIFE_AI_IS_ON).get()) return;
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
                        new SetJumpTargetToRandom<>().radius(15, 10).cooldownFor(entity -> 10).startCondition(entity -> this.getIsLongJump()),
                new OneRandomBehaviour<>(
                        new LongJumpToJumpTarget<>(30, 1.5F, SoundEvents.FROG_LONG_JUMP)
                                .cooldownFor(entity -> random.nextInt(80, 700)).startCondition(entity -> this.tickCount > 100 && this.getIsLongJump()),
                        new SetRandomWalkTarget<>().speedModifier(1f+this.getFrogSpeed()),          // Set a random walk target to a nearby position
                        new Idle<>().runFor(entity -> entity.getRandom().nextInt(30, 90)))); // Do nothing for 1.5->4.5 seconds
    }




    @Override
    public BrainActivityGroup<Chumtoad> getFightTasks() { // These are the tasks that handle fighting
        return BrainActivityGroup.fightTasks(
                new InvalidateAttackTarget<>(),
                new Retaliate<>(),
                new SetJumpTargetToRandomSpotAwayFromAttackTarget<>().radius(15, 10).cooldownFor(entity -> 10).startCondition(entity -> this.getIsLongJump()),
                new LongJumpToJumpTarget<>(30, 1.5F, SoundEvents.FROG_LONG_JUMP)
                        .cooldownFor(entity -> random.nextInt(80, 700)).startCondition(entity -> this.tickCount > 100 && this.getIsLongJump()),
                new FleeTarget<>().speedModifier(1.1f+this.getFrogSpeed())
        );
    }





}
