package net.sirkotok.half_life_mod.entity.mob_geckolib.custom;


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

import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.monster.Slime;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;

import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;

import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.fluids.FluidType;
import net.sirkotok.half_life_mod.entity.base.HalfLifeMonster;
import net.sirkotok.half_life_mod.entity.base.HalfLifeNeutral;
import net.sirkotok.half_life_mod.entity.brain.ModMemoryModuleType;
import net.sirkotok.half_life_mod.entity.brain.behaviour.*;
import net.sirkotok.half_life_mod.entity.brain.sensor.SmellSensor;
import net.sirkotok.half_life_mod.entity.projectile.AcidBall;
import net.sirkotok.half_life_mod.sound.HalfLifeSounds;
import net.sirkotok.half_life_mod.util.HLperUtil;
import net.sirkotok.half_life_mod.util.HLTags;
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
import net.tslat.smartbrainlib.api.core.behaviour.custom.path.SetWalkTargetToAttackTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.target.InvalidateAttackTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.target.SetPlayerLookTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.target.SetRandomLookTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.target.TargetOrRetaliate;
import net.tslat.smartbrainlib.api.core.sensor.ExtendedSensor;

import net.tslat.smartbrainlib.api.core.sensor.vanilla.HurtBySensor;
import net.tslat.smartbrainlib.api.core.sensor.vanilla.NearbyLivingEntitySensor;
import net.tslat.smartbrainlib.api.core.sensor.vanilla.NearbyPlayersSensor;
import net.tslat.smartbrainlib.util.BrainUtils;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

import javax.annotation.Nullable;
import java.util.List;


public class Bullsquid extends HalfLifeMonster implements RangedAttackMob, GeoEntity, SmartBrainOwner<Bullsquid> {

    @Override
    protected float getSoundVolume() {
        return 0.5f;
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

    private AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);



    public static final EntityDataAccessor<Boolean> TERRITORIAL = SynchedEntityData.defineId(Bullsquid.class, EntityDataSerializers.BOOLEAN);
    public static final EntityDataAccessor<Boolean> IS_ANGRY = SynchedEntityData.defineId(Bullsquid.class, EntityDataSerializers.BOOLEAN);
    public static final EntityDataAccessor<Integer> ID_TEXTURE = SynchedEntityData.defineId(Bullsquid.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Boolean> IS_EATING = SynchedEntityData.defineId(Bullsquid.class, EntityDataSerializers.BOOLEAN);
    public static final EntityDataAccessor<Boolean> AI_STOP = SynchedEntityData.defineId(Bullsquid.class, EntityDataSerializers.BOOLEAN);
    public static final EntityDataAccessor<Integer> AI_STOP_TIMESTAMP = SynchedEntityData.defineId(Bullsquid.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Integer> AI_STOP_DELAY = SynchedEntityData.defineId(Bullsquid.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Byte> DATA_FLAGS_ID = SynchedEntityData.defineId(Bullsquid.class, EntityDataSerializers.BYTE);
    public static final EntityDataAccessor<Integer> LAST_ON_GROUND_TIMESTAMP = SynchedEntityData.defineId(Bullsquid.class, EntityDataSerializers.INT);


    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(TERRITORIAL, true);
        this.entityData.define(IS_ANGRY, false);
        this.entityData.define(AI_STOP, false);
        this.entityData.define(IS_EATING, false);
        this.entityData.define(ID_TEXTURE, 1);
        this.entityData.define(AI_STOP_TIMESTAMP, 0);
        this.entityData.define(AI_STOP_DELAY, 0);
        this.entityData.define(DATA_FLAGS_ID, (byte)0);
        this.entityData.define(LAST_ON_GROUND_TIMESTAMP, 0);
    }

    public void stopAiFor(int delay) {
        this.entityData.set(AI_STOP_TIMESTAMP, this.tickCount);
        this.entityData.set(AI_STOP, true);
        this.entityData.set(AI_STOP_DELAY, delay);
        this.entityData.set(IS_EATING, true);
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


    public int gettexture() {
        return this.entityData.get(ID_TEXTURE);
    }
    protected void settexture(int texture) {
         this.entityData.set(ID_TEXTURE, texture);
    }

    public void addAdditionalSaveData(CompoundTag p_33619_) {
        super.addAdditionalSaveData(p_33619_);
        p_33619_.putInt("Texture", this.gettexture() - 1 );
        p_33619_.putBoolean("Territorial", this.isterritorial());
    }

    public void readAdditionalSaveData(CompoundTag p_33607_) {
        this.settexture(p_33607_.getInt("Texture") + 1);
        this.setterritorial(p_33607_.getBoolean("Territorial"));
        super.readAdditionalSaveData(p_33607_);
    }

    protected void Smellfor(){
        this.triggerAnim("onetime", "smell");
        this.stopAiFor(60);
    }


    protected boolean isangry() {
        return this.entityData.get(IS_ANGRY);
    }
    protected boolean isterritorial() {
        return this.entityData.get(TERRITORIAL);
    }
    protected void setterritorial(boolean a) {
        this.entityData.set(TERRITORIAL, a);
    }

    public Bullsquid(EntityType type, Level level) {
        super(type, level);
        this.xpReward = 15;
         this.setPathfindingMalus(BlockPathTypes.DAMAGE_FIRE, -1F);
        this.setPathfindingMalus(BlockPathTypes.DANGER_FIRE, -1F);
        this.setPathfindingMalus(BlockPathTypes.WATER, 0.0F);
        this.setPathfindingMalus(BlockPathTypes.WATER_BORDER, 0.0F);
    }


    public static AttributeSupplier setAttributes() {
        return Monster.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 40D)
                .add(Attributes.ATTACK_DAMAGE, 10f)
                .add(Attributes.ATTACK_SPEED, 1.0f)
                .add(Attributes.ATTACK_KNOCKBACK, 2.4f).add(Attributes.KNOCKBACK_RESISTANCE, 0.6f)
                .add(Attributes.ARMOR, 10f)
                .add(Attributes.FOLLOW_RANGE, 64)
                .add(Attributes.MOVEMENT_SPEED, 0.2f).build();
    }


    protected SoundEvent getAttackGrowlSound() {
        switch (this.random.nextInt(1,4)) {
            case 1:  return HalfLifeSounds.BULLSQUID_ATTACK_1.get();
            case 2:  return HalfLifeSounds.BULLSQUID_ATTACK_2.get();
            case 3:  return HalfLifeSounds.BULLSQUID_ATTACK_3.get();
        }
        return HalfLifeSounds.HEADCRAB_1_PAIN_1.get();
    }

    protected SoundEvent getSpitSound() {
        switch (this.random.nextInt(1,4)) {
            case 1:  return HalfLifeSounds.BULLSQUID_SHOOT_1.get();
            case 2:  return HalfLifeSounds.BULLSQUID_SHOOT_2.get();
            case 3:  return HalfLifeSounds.BULLSQUID_SHOOT_3.get();
        }
        return HalfLifeSounds.HEADCRAB_1_PAIN_1.get();

    }
    protected SoundEvent getBiteSound() {
        switch (this.random.nextInt(1,4)) {
            case 1:  return HalfLifeSounds.BULLSQUID_BITE_1.get();
            case 2:  return HalfLifeSounds.BULLSQUID_BITE_2.get();
            case 3:  return HalfLifeSounds.BULLSQUID_BITE_3.get();
        }
        return HalfLifeSounds.HEADCRAB_1_HEADBITE.get();
    }

    protected SoundEvent getHurtSound(DamageSource p_33034_) {
        switch (this.random.nextInt(1,5)) {
            case 1:  return HalfLifeSounds.BULLSQUID_PAIN_1.get();
            case 2:  return HalfLifeSounds.BULLSQUID_PAIN_2.get();
            case 3:  return HalfLifeSounds.BULLSQUID_PAIN_3.get();
            case 4:  return HalfLifeSounds.BULLSQUID_PAIN_4.get();
        }
        return HalfLifeSounds.HEADCRAB_1_PAIN_1.get();
    }

    protected SoundEvent getDeathSound() {
        switch (this.random.nextInt(1,4)) {
            case 1:  return HalfLifeSounds.BULLSQUID_DIE_1.get();
            case 2:  return HalfLifeSounds.BULLSQUID_DIE_2.get();
            case 3: return HalfLifeSounds.BULLSQUID_DIE_3.get();
        }
        return HalfLifeSounds.HEADCRAB_1_DIE_1.get();
    }


    protected SoundEvent getAmbientSound() {

        switch (this.random.nextInt(1,6)) {
            case 1:  return HalfLifeSounds.BULLSQUID_IDLE_1.get();
            case 2:  return HalfLifeSounds.BULLSQUID_IDLE_2.get();
            case 3:  return HalfLifeSounds.BULLSQUID_IDLE_3.get();
            case 4:  return HalfLifeSounds.BULLSQUID_IDLE_4.get();
            case 5:  return HalfLifeSounds.BULLSQUID_IDLE_5.get();
        }
        return HalfLifeSounds.HEADCRAB_1_ALERT_1.get();
    }





    @Override
    protected Brain.Provider<?> brainProvider() {
        return new SmartBrainProvider<>(this);
    }

    @Override
    protected void customServerAiStep() {
        if (!aistopped()) {
        tickBrain(this);}
    }

    public boolean onClimbable() {
        return this.isClimbing();
    }
    public boolean isClimbing() {
        return (this.entityData.get(DATA_FLAGS_ID) & 1) != 0;
    }
    public void setClimbing(boolean pClimbing) {
        byte b0 = this.entityData.get(DATA_FLAGS_ID);
        if (pClimbing) {
            b0 = (byte)(b0 | 1);
        } else {
            b0 = (byte)(b0 & -2);
        }

        this.entityData.set(DATA_FLAGS_ID, b0);
    }

    @Override
    public void tick() {
        super.tick();

        if (this.isOnGround()) {this.entityData.set(LAST_ON_GROUND_TIMESTAMP, this.tickCount);}

        if (!this.level.isClientSide && this.isInWaterOrBubble() && (this.tickCount - this.entityData.get(LAST_ON_GROUND_TIMESTAMP) < 10)) {
            this.setClimbing(this.horizontalCollision);
        }
        if (aistopped() && ((this.tickCount - ai_stop_timestamp() > ai_stop_remaining()) || this.getLastAttacker() != null)) {this.entityData.set(AI_STOP, false);}
    }

    @Override
    public List<ExtendedSensor<Bullsquid>> getSensors() {
        return ObjectArrayList.of(
                new SmellSensor<>(),
             //   new NearbyBlocksSensor<Bullsquid>().setRadius(16D, 4D).setPredicate((state, entity) -> state.is(Blocks.WATER)),
                new HurtBySensor<>(),
                new NearbyPlayersSensor<>(),
                new NearbyLivingEntitySensor<Bullsquid>()
                        .setPredicate((target, entity) ->
                            target instanceof Player ||
                            target.getType().is(HLTags.EntityTypes.HEADCRAB) ||
                            (target.getType().getCategory().getName().equals("creature") && !(target instanceof Cockroach)) ||
                            target instanceof IronGolem ||
                            target instanceof AbstractVillager ||
                            target instanceof Pitdrone ||
                            target instanceof Slime ||
                            (target instanceof Bullsquid bullsquid && bullsquid.isterritorial() && this.isterritorial()) ||
                            target instanceof HalfLifeNeutral
                            ));
    }



    @Override
    public BrainActivityGroup<Bullsquid> getCoreTasks() {
        return BrainActivityGroup.coreTasks(
                new LookAtTarget<>(),
                new MoveToWalkTarget<>());
    }




    @Override
    public BrainActivityGroup<Bullsquid> getIdleTasks() {
        return BrainActivityGroup.idleTasks(
                new FirstApplicableBehaviour<Bullsquid>(
                    //    new InvalidateFoodLocation<>().cooldownFor(entity -> 200), // not used because null pointer, the behavior needs rewrite
                        new CustomBehaviour<>(entity -> BrainUtils.clearMemory(this, ModMemoryModuleType.HUNGRY.get()))
                                .startCondition(entity -> ((this.getHealth() + 2) < this.getMaxHealth())).cooldownFor(entity -> 400),
                        new CustomBehaviour<>(entity -> this.entityData.set(IS_ANGRY, true))
                                .startCondition(entity -> this.getLastAttacker() != null)
                                .whenStarting(entity -> triggerAnim("onetime", "startled"))
                                .cooldownFor(entity -> 200),
                        new TargetOrRetaliate<>(),
                       new EatingBehavior<>().callback(entity -> this.entityData.set(IS_EATING, true))
                               .whenStopping(entity -> this.entityData.set(IS_EATING, false)),
                        new SetFoodToWalkTarget<>().whenStarting(entity -> this.Smellfor()).cooldownFor(entity -> 400),
                        new SetPlayerLookTarget<>(),
                        new SetRandomLookTarget<>()),
                new OneRandomBehaviour<>(
                        new SetRandomWalkTarget<>().dontAvoidWater(),          // Set a random walk target to a nearby position
                        new Idle<>().runFor(entity -> entity.getRandom().nextInt(30, 90))
                                .whenStarting(entity -> this.entityData.set(IS_ANGRY, false)))); // Do nothing for 1.5->4.5 seconds
    }

    @Override
    public boolean isWithinMeleeAttackRange(LivingEntity p_217067_) {
        double d0 = this.getPerceivedTargetDistanceSquareForMeleeAttack(p_217067_);
        return d0 <= this.getMeleeAttackRangeSqr(p_217067_)*1.5;
    }

    @Override
    public BrainActivityGroup<Bullsquid> getFightTasks() {
        return BrainActivityGroup.fightTasks(
                new InvalidateAttackTarget<>(),
                new Retaliate<>(),
                new SetWalkTargetToAttackTarget<Bullsquid>().speedMod(1.84f).whenStarting(entity -> this.entityData.set(IS_ANGRY, true)),
                new FirstApplicableBehaviour<>(
                new ConfigurableAnimatableMeleeAttack<Bullsquid>(20, 1f, 2, 2, null, 0, null, null)
                        .whenStarting(entity -> triggerAnim("onetime", "spin"))
                        .cooldownFor(entity -> random.nextInt(40, 80))
                        .startCondition(entity -> HLperUtil.TargetOrThis(this).getHealth()<10.5f),
                 new ConfigurableAnimatableMeleeAttack<Bullsquid>(15, 0.3f, 1, 2, null, 0, this.getBiteSound(), this.getAttackGrowlSound())
                         .whenStarting(entity -> triggerAnim("onetime", "bite"))
                         .cooldownFor(entity -> random.nextInt(20, 30)),
                        new StopAndShoot<Bullsquid>(21, 0, 1f).cooldownFor(entity -> random.nextInt(20, 40))
                                .whenStarting(entity -> triggerAnim("onetime", "shoot")))

        );

    }




    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {

        controllerRegistrar.add(new AnimationController<>(this, "allways", 0, this::headpredicate));
        controllerRegistrar.add(new AnimationController<>(this, "main_loop", 0, this::predicate));
        controllerRegistrar.add(new AnimationController<>(this, "onetime", state -> PlayState.STOP)
                .triggerableAnim("bite", RawAnimation.begin().then("animation.bullsquid.bite", Animation.LoopType.PLAY_ONCE))
                .triggerableAnim("shoot", RawAnimation.begin().then("animation.bullsquid.shoot", Animation.LoopType.PLAY_ONCE))
                .triggerableAnim("spin", RawAnimation.begin().then("animation.bullsquid.tailspin", Animation.LoopType.PLAY_ONCE))
                .triggerableAnim("startled", RawAnimation.begin().then("animation.bullsquid.startled", Animation.LoopType.PLAY_ONCE))
                .triggerableAnim("start_eat", RawAnimation.begin().then("animation.bullsquid.starteating", Animation.LoopType.PLAY_ONCE))
                .triggerableAnim("stop_eat", RawAnimation.begin().then("animation.bullsquid.stopeating", Animation.LoopType.PLAY_ONCE))
                .triggerableAnim("smell", RawAnimation.begin().then("animation.bullsquid.smell", Animation.LoopType.PLAY_ONCE))
                .triggerableAnim("nothungry", RawAnimation.begin().then("animation.bullsquid.nothungry", Animation.LoopType.PLAY_ONCE))
                .triggerableAnim("eatlong", RawAnimation.begin().then("animation.bullsquid.eatlong", Animation.LoopType.PLAY_ONCE))
        );



    }

    private <T extends GeoAnimatable> PlayState headpredicate(AnimationState<T> tAnimationState) {

        if (BrainUtils.getMemory(this, MemoryModuleType.SNIFFER_HAPPY) != null) {
            if (BrainUtils.getMemory(this, MemoryModuleType.SNIFFER_HAPPY).equals(true)) {return PlayState.CONTINUE;}}
        tAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.bullsquid.tail_head_movement", Animation.LoopType.LOOP));
        return PlayState.CONTINUE;
    }


    private <T extends GeoAnimatable> PlayState predicate(AnimationState<T> tAnimationState) {




        if (BrainUtils.getMemory(this, MemoryModuleType.SNIFFER_HAPPY) != null) {
            if (BrainUtils.getMemory(this, MemoryModuleType.SNIFFER_HAPPY).equals(true)) {return PlayState.CONTINUE;}}

        if(tAnimationState.isMoving()) {
         if (!this.isangry()) {
            tAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.bullsquid.walk", Animation.LoopType.LOOP));
            return PlayState.CONTINUE;
        }
            tAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.bullsquid.run", Animation.LoopType.LOOP));
            return PlayState.CONTINUE;
        }


        tAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.bullsquid.idle", Animation.LoopType.LOOP));

        return PlayState.CONTINUE;

    }




    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    @Override
    public void performRangedAttack(LivingEntity livingentity, float p_33318_) {

        double d0 = this.distanceToSqr(livingentity);
        double d1 = livingentity.getX() - this.getX();
        double d2 = livingentity.getY(0.4D) - this.getY(0.4D);
        double d3 = livingentity.getZ() - this.getZ();
        double d4 = Math.sqrt(Math.sqrt(d0)) * 0.5D;


            this.playSound(getSpitSound(), this.getSoundVolume(), 1.2f);


        AcidBall acidBall = new AcidBall(this.level, this, d1, d2, d3); //this.getRandom().triangle(d1, 2.297D * d4)
        acidBall.setPos(this.getX() - (double)(this.getBbWidth() + 1.0F) * 0.5D * (double)Mth.sin(this.yBodyRot * ((float)Math.PI / 180F)), this.getEyeY() - (double)0.1F, this.getZ() + (double)(this.getBbWidth() + 1.0F) * 0.5D * (double) Mth.cos(this.yBodyRot * ((float)Math.PI / 180F)));
        this.level.addFreshEntity(acidBall);

    }






    @Nullable
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor p_33601_, DifficultyInstance p_33602_, MobSpawnType p_33603_, @Nullable SpawnGroupData p_33604_, @Nullable CompoundTag p_33605_) {
        RandomSource randomsource = p_33601_.getRandom();
        int i = randomsource.nextInt(101);
        int j = randomsource.nextInt(200);
        if (j % 69 == 0) this.setterritorial(false);
        this.settexture(i);
        return super.finalizeSpawn(p_33601_, p_33602_, p_33603_, p_33604_, p_33605_);
    }
}





