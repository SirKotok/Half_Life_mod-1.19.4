package net.sirkotok.half_life_mod.entity.mob.mob_geckolib.custom;


import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.BlockPos;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.sirkotok.half_life_mod.entity.brain.behaviour.EatingBehavior;
import net.sirkotok.half_life_mod.entity.brain.behaviour.SetFoodToWalkTarget;
import net.sirkotok.half_life_mod.entity.brain.sensor.SmellSensor;
import net.sirkotok.half_life_mod.misc.gamerules.HalfLifeGameRules;
import net.sirkotok.half_life_mod.sound.HalfLifeSounds;
import net.tslat.smartbrainlib.api.SmartBrainOwner;
import net.tslat.smartbrainlib.api.core.BrainActivityGroup;
import net.tslat.smartbrainlib.api.core.SmartBrainProvider;
import net.tslat.smartbrainlib.api.core.behaviour.FirstApplicableBehaviour;
import net.tslat.smartbrainlib.api.core.behaviour.OneRandomBehaviour;
import net.tslat.smartbrainlib.api.core.behaviour.custom.look.LookAtTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.misc.Idle;
import net.tslat.smartbrainlib.api.core.behaviour.custom.move.EscapeSun;
import net.tslat.smartbrainlib.api.core.behaviour.custom.move.FleeTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.move.MoveToWalkTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.path.SetRandomWalkTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.target.SetPlayerLookTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.target.SetRandomLookTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.target.TargetOrRetaliate;
import net.tslat.smartbrainlib.api.core.sensor.ExtendedSensor;
import net.tslat.smartbrainlib.api.core.sensor.vanilla.HurtBySensor;
import net.tslat.smartbrainlib.api.core.sensor.vanilla.NearbyLivingEntitySensor;
import net.tslat.smartbrainlib.api.core.sensor.vanilla.NearbyPlayersSensor;
import net.tslat.smartbrainlib.util.EntityRetrievalUtil;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.ArrayList;
import java.util.List;


public class Cockroach extends Animal implements GeoEntity, SmartBrainOwner<Cockroach> {

    public List<LivingEntity> around;
    public int light = 0;


    @Override
    protected void actuallyHurt(DamageSource p_21240_, float p_21241_) {
        super.actuallyHurt(p_21240_, p_21241_);
    }




    private AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);




    public static final EntityDataAccessor<Boolean> STARTLED = SynchedEntityData.defineId(Cockroach.class, EntityDataSerializers.BOOLEAN);
    public static final EntityDataAccessor<Integer> STARTLED_TIMESTAMP = SynchedEntityData.defineId(Cockroach.class, EntityDataSerializers.INT);


    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel pLevel, AgeableMob pOtherParent) {
        return null;
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(STARTLED, false);
        this.entityData.define(STARTLED_TIMESTAMP, 0);
    }
    protected boolean isangry() {
        return this.entityData.get(STARTLED);
    }

    public void setStartled(){
        this.entityData.set(STARTLED, true);
        this.entityData.set(STARTLED_TIMESTAMP, this.tickCount);
    }

    @Override
    public void aiStep() {
        super.aiStep();

        if (this.isDeadOrDying() && !this.level.isClientSide()) {
            ServerLevel pLevel = (ServerLevel) this.level;
            BlockPos pBlockPos = this.blockPosition();
            int rad = 10;
            List<Cockroach> roach = EntityRetrievalUtil.getEntities((Level) pLevel,
                    new AABB(pBlockPos.getX() - rad, pBlockPos.getY() - rad, pBlockPos.getZ() - rad,
                            pBlockPos.getX() + rad, pBlockPos.getY() + rad, pBlockPos.getZ() + rad), obj -> obj instanceof Cockroach);
            for (Cockroach cockroach : roach) {
                cockroach.setStartled();
            }
                this.playSound(this.getDeathSound(), this.getSoundVolume(), this.getVoicePitch());
                this.discard();
        }


        if (this.tickCount - this.entityData.get(STARTLED_TIMESTAMP) > 100 || this.tickCount < 100) this.entityData.set(STARTLED, false);

        List<Entity> list = this.level.getEntities(this, this.getBoundingBox(), obj -> obj instanceof LivingEntity && obj.getBbWidth() > 0.5f && !obj.getDeltaMovement().equals(new Vec3(0, 0, 0)));
        if (!list.isEmpty() && !this.isDeadOrDying() && !this.hasCustomName()) {
            this.setHealth(0);
            this.playSound(HalfLifeSounds.COCKROACH_IS_STOMPED.get(), this.getSoundVolume(), this.getVoicePitch());
        }


        if (!this.level.isClientSide() && this.tickCount % 50 == 0) {
        ServerLevel pLevel = (ServerLevel) this.level;
        BlockPos pBlockPos = this.blockPosition();
        int i = pLevel.getBrightness(LightLayer.BLOCK, pBlockPos);
        int rad = 10;
        List<LivingEntity> large = EntityRetrievalUtil.getEntities((Level) pLevel,
                new AABB(pBlockPos.getX() - rad, pBlockPos.getY() - rad, pBlockPos.getZ() - rad,
                        pBlockPos.getX() + rad, pBlockPos.getY() + rad, pBlockPos.getZ() + rad), obj -> obj instanceof LivingEntity && obj.getBbWidth() > 0.5f);
        if (around == null) around = new ArrayList<>();

        if (around.size() < large.size() || i - light > 5) {
            this.setStartled();
        }

        this.light = i;
        this.around = large;


        }


    }

    public Cockroach(EntityType type, Level level) {
        super(type, level);
        this.xpReward = 0;
    }


    @Override
    public boolean shouldDropExperience() {
        return false;
    }

    public static AttributeSupplier setAttributes() {
        return Monster.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 1D)
                .add(Attributes.ATTACK_DAMAGE, 3f)
                .add(Attributes.ATTACK_SPEED, 1.0f)
                .add(Attributes.ATTACK_KNOCKBACK, 1.0f)
                .add(Attributes.MOVEMENT_SPEED, 0.33f).build();
    }



    @Override
    protected float getSoundVolume() {
        return 0.5f;
    }


    protected SoundEvent getDeathSound() {
        return HalfLifeSounds.COCKROACH_DIES.get();
    }

    protected SoundEvent getStepSound() {
        return HalfLifeSounds.COCKROACH_WALK.get();
    }

    @Override
    protected void playStepSound(BlockPos pPos, BlockState pState) {
        if (this.tickCount%9 == 0) {
            BlockState blockstate = this.level.getBlockState(pPos.above());
            boolean flag = blockstate.is(BlockTags.INSIDE_STEP_SOUND_BLOCKS);
            if (flag || !pState.getMaterial().isLiquid()) {
                this.playSound(getStepSound(), this.getSoundVolume(), this.getVoicePitch());
            }
        }
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
    public List<ExtendedSensor<Cockroach>> getSensors() {
        return ObjectArrayList.of(
                new HurtBySensor<>(),
                new SmellSensor<>(),
                new NearbyPlayersSensor<>(),
                new NearbyLivingEntitySensor<Cockroach>()
                        .setPredicate((target, entity) ->
                            target.getBbWidth() > 0.6f ));
    }



    @Override
    public BrainActivityGroup<Cockroach> getCoreTasks() { // These are the tasks that run all the time (usually)
        return BrainActivityGroup.coreTasks(
                new LookAtTarget<>(),                      // Have the entity turn to face and look at its current look target
                new MoveToWalkTarget<>());
    }

    @Override
    public BrainActivityGroup<Cockroach> getIdleTasks() { // These are the tasks that run when the mob isn't doing anything else (usually)
        return BrainActivityGroup.idleTasks(
                new FirstApplicableBehaviour<Cockroach>(
                        new TargetOrRetaliate<>(),
                        new SetPlayerLookTarget<>(),
                        new SetRandomLookTarget<>()),
                        new EatingBehavior<>().callback(entity -> this.entityData.set(STARTLED, false)),
                        new SetFoodToWalkTarget<>().cooldownFor(entity -> 400).startCondition(enitity -> !this.entityData.get(STARTLED)),
                        new EscapeSun<>(),
                new OneRandomBehaviour<>(
                        new SetRandomWalkTarget<>(),
                        new Idle<>().runFor(entity -> entity.getRandom().nextInt(30, 90))
                ).startCondition(enitity -> !this.entityData.get(STARTLED)),
                 new OneRandomBehaviour<>(
                new SetRandomWalkTarget<>().speedModifier((entity, targetPos) -> 1.4f).setRadius(20, 10),
                new Idle<>().runFor(entity -> entity.getRandom().nextInt(5, 10))
              ).startCondition(enitity -> this.entityData.get(STARTLED))

        );

    }




    @Override
    public BrainActivityGroup<Cockroach> getFightTasks() { // These are the tasks that handle fighting
        return BrainActivityGroup.fightTasks(
              new FleeTarget<>().speedModifier(1.4f).startCondition(entity -> this.entityData.get(STARTLED)),
                new OneRandomBehaviour<>(
                        new SetRandomWalkTarget<>(),
                        new Idle<>().runFor(entity -> entity.getRandom().nextInt(30, 90))
                ).startCondition(enitity -> !this.entityData.get(STARTLED)),
                new OneRandomBehaviour<>(
                        new SetRandomWalkTarget<>().speedModifier((entity, targetPos) -> 1.4f).setRadius(20, 10),
                        new Idle<>().runFor(entity -> entity.getRandom().nextInt(5, 10))
                ).startCondition(enitity -> this.entityData.get(STARTLED))


        );

    }




    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(new AnimationController<>(this, "controller", 0, this::predicate));
        controllerRegistrar.add(new AnimationController<>(this, "walk", 0, this::walkpred));

    }



    private <T extends GeoAnimatable> PlayState walkpred(AnimationState<T> tAnimationState) {



        if(tAnimationState.isMoving() && this.isOnGround()) {
            tAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.cockroach.walk", Animation.LoopType.LOOP));
            return PlayState.CONTINUE;
        }

        tAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.cockroach.idle", Animation.LoopType.LOOP));
        return PlayState.CONTINUE;

    }
    private <T extends GeoAnimatable> PlayState predicate(AnimationState<T> tAnimationState) {

        tAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.cockroach.antena", Animation.LoopType.LOOP));
        return PlayState.CONTINUE;

    }




    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

}

