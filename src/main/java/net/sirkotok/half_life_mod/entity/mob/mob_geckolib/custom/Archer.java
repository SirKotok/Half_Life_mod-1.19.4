package net.sirkotok.half_life_mod.entity.mob.mob_geckolib.custom;


import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.fluids.FluidType;
import net.sirkotok.half_life_mod.entity.base.HalfLifeMonster;
import net.sirkotok.half_life_mod.entity.brain.behaviour.*;
import net.sirkotok.half_life_mod.entity.brain.sensor.SmellSensor;
import net.sirkotok.half_life_mod.entity.projectile.AcidBall;
import net.sirkotok.half_life_mod.misc.gamerules.HalfLifeGameRules;
import net.sirkotok.half_life_mod.sound.HalfLifeSounds;
import net.sirkotok.half_life_mod.misc.util.HLperUtil;
import net.sirkotok.half_life_mod.misc.util.InfightingUtil;
import net.tslat.smartbrainlib.api.SmartBrainOwner;
import net.tslat.smartbrainlib.api.core.BrainActivityGroup;
import net.tslat.smartbrainlib.api.core.SmartBrainProvider;
import net.tslat.smartbrainlib.api.core.behaviour.FirstApplicableBehaviour;
import net.tslat.smartbrainlib.api.core.behaviour.custom.look.LookAtTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.misc.CustomBehaviour;
import net.tslat.smartbrainlib.api.core.behaviour.custom.move.MoveToWalkTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.path.SetWalkTargetToAttackTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.target.InvalidateAttackTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.target.SetPlayerLookTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.target.SetRandomLookTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.target.TargetOrRetaliate;
import net.tslat.smartbrainlib.api.core.sensor.ExtendedSensor;
import net.tslat.smartbrainlib.api.core.sensor.custom.NearbyBlocksSensor;
import net.tslat.smartbrainlib.api.core.sensor.vanilla.HurtBySensor;
import net.tslat.smartbrainlib.api.core.sensor.vanilla.NearbyLivingEntitySensor;
import net.tslat.smartbrainlib.api.core.sensor.vanilla.NearbyPlayersSensor;
import net.tslat.smartbrainlib.util.BrainUtils;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.List;


public class Archer extends HalfLifeMonster implements GeoEntity, RangedAttackMob, SmartBrainOwner<Archer> {


    @Override
    public float getVoicePitch() {
        return 3;
    }

    private BlockPos targetPosition;

    private int attackbehaviour;
    private AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);


    @Override
    protected void playSwimSound(float pVolume) {
        if (RandomSource.create().nextFloat() < 0.08f) super.playSwimSound(pVolume);
    }



    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        if (this.getTarget() != null)
            switch (RandomSource.create().nextInt(1, 4)) {
                case 1:
                    return HalfLifeSounds.ICHY_ALERT1.get();
                case 2:
                    return HalfLifeSounds.ICHY_ALERT2.get();
                case 3:
                    return HalfLifeSounds.ICHY_ALERT3.get();
            }
        switch (RandomSource.create().nextInt(1, 5)) {
            case 1:
                return HalfLifeSounds.ICHY_IDLE1.get();
            case 2:
                return HalfLifeSounds.ICHY_IDLE2.get();
            case 3:
                return HalfLifeSounds.ICHY_IDLE3.get();
            case 4:
                return HalfLifeSounds.ICHY_IDLE4.get();
        }
        return HalfLifeSounds.ICHY_IDLE1.get();
    }


    public SoundEvent getBiteSound() {
        switch (RandomSource.create().nextInt(1, 4)) {
            case 1:
                return HalfLifeSounds.ICHY_BITE1.get();
            case 2:
                return HalfLifeSounds.ICHY_BITE2.get();
        }
        return HalfLifeSounds.LEECH_BITE3.get();
    }






    @Override
    public void aiStep() {
        super.aiStep();

        if (this.tickCount % 4 == 0 && this.getwateremountbelow() > 0 && !this.isInWaterOrBubble()) {
            this.setDeltaMovement(this.getDeltaMovement().add(0, -0.4f, 0));
            if (this.random.nextFloat() < 0.1f) BrainUtils.clearMemory(this, MemoryModuleType.WALK_TARGET);
        }


    }


    @Override
    protected SoundEvent getDeathSound() {
        switch (RandomSource.create().nextInt(1, 5)) {
            case 1:
                return HalfLifeSounds.ICHY_DIE1.get();
            case 2:
                return HalfLifeSounds.ICHY_DIE2.get();
            case 3:
                return HalfLifeSounds.ICHY_DIE3.get();
            case 4:
                return HalfLifeSounds.ICHY_DIE4.get();
        }
        return HalfLifeSounds.ICHY_IDLE1.get();
    }


    @Override
    protected SoundEvent getHurtSound(DamageSource pDamageSource) {
        switch (RandomSource.create().nextInt(1, 5)) {
            case 1:
                return HalfLifeSounds.ICHY_PAIN1.get();
            case 2:
                return HalfLifeSounds.ICHY_PAIN2.get();
            case 3:
                return HalfLifeSounds.ICHY_PAIN3.get();
            case 4:
                return HalfLifeSounds.ICHY_PAIN4.get();
        }
        return HalfLifeSounds.ICHY_IDLE1.get();
    }


    @Override
    public boolean hurt(DamageSource pSource, float pAmount) {

        if (this.attackbehaviour == 1 && RandomSource.create().nextFloat() < 0.1f) {
            BrainUtils.clearMemory(this, MemoryModuleType.WALK_TARGET);
            this.attackbehaviour = 0;
        }


        return super.hurt(pSource, pAmount);
    }

    public Archer(EntityType type, Level level) {
        super(type, level);
        this.xpReward = 20;
        this.setPathfindingMalus(BlockPathTypes.WATER, 0.0F);
        this.setPathfindingMalus(BlockPathTypes.WATER_BORDER, -1.0F);
    }



    public static AttributeSupplier setAttributes() {
        return Monster.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 30D)
                .add(Attributes.ATTACK_DAMAGE, 4f)
                .add(Attributes.ATTACK_SPEED, 3.0f)
                .add(Attributes.ATTACK_KNOCKBACK, 1f)
                .add(Attributes.MOVEMENT_SPEED, 0.15f).build();
    }


    public int getwateremountbelow() {
        int i = 0;
        for (int j = 1; j < 20; j++) {
            BlockPos pos = new BlockPos(this.blockPosition().getX(), this.blockPosition().getY() - j, this.blockPosition().getZ());
            BlockState blockstate = this.level.getBlockState(pos);
            if (!blockstate.is(Blocks.WATER)) return i;
            i++;
        }
        return i;
    }

    public int getwateremountabove() {
        int i = 0;
        for (int j = 1; j < 20; j++) {
            BlockPos pos = new BlockPos(this.blockPosition().getX(), this.blockPosition().getY() + j, this.blockPosition().getZ());
            BlockState blockstate = this.level.getBlockState(pos);
            if (!blockstate.is(Blocks.WATER)) return i;
            i++;
        }
        return i;
    }


    @Override
    protected float getSoundVolume() {
        return 0.5f;
    }



    @Override
    protected Brain.Provider<?> brainProvider() {
        return new SmartBrainProvider<>(this);
    }

    @Override
    protected void customServerAiStep() {
        if (!this.level.getGameRules().getRule(HalfLifeGameRules.HALF_LIFE_AI_IS_ON).get()) return;
        if (!this.isInWaterOrBubble() && this.getwateremountbelow() == 0) return;

        tickBrain(this);


            if (BrainUtils.getTargetOfEntity(this) != null && this.attackbehaviour == 0) {
                Vec3 vec31 =  BrainUtils.getTargetOfEntity(this).position();
                Vec3 vec3 = this.position();
                Vec3 between = vec3.subtract(vec31);
                this.setYRot((float) HLperUtil.yanglefromvec3(between));
                this.setXRot((float) HLperUtil.yanglefromvec3(between));
                return;
            }

            if (this.targetPosition != null && (!this.level.getBlockState(this.targetPosition).equals(Blocks.WATER.defaultBlockState()) || this.targetPosition.getY() <= this.level.getMinBuildHeight())) {
                this.targetPosition = null;
            }

            if (this.targetPosition == null || this.random.nextInt(30) == 0 || this.targetPosition.closerToCenterThan(this.position(), 2.0D)) {
                this.targetPosition = BlockPos.containing(this.getX() + (double) this.random.nextInt(7) - (double) this.random.nextInt(7), this.getY() + (double) this.random.nextInt(6) - 2.0D, this.getZ() + (double) this.random.nextInt(7) - (double) this.random.nextInt(7));
            }

            double d2 = (double) this.targetPosition.getX() + 0.5D - this.getX();
            double d0 = (double) this.targetPosition.getY() + 0.1D - this.getY();
            double d1 = (double) this.targetPosition.getZ() + 0.5D - this.getZ();
            Vec3 vec3 = this.getDeltaMovement();
            Vec3 vec31 = vec3.add((Math.signum(d2) * 0.5D - vec3.x) * (double) 0.1F, (Math.signum(d0) * (double) 0.7F - vec3.y) * (double) 0.1F, (Math.signum(d1) * 0.5D - vec3.z) * (double) 0.1F);
            float f = (float) (Mth.atan2(vec31.z, vec31.x) * (double) (180F / (float) Math.PI)) - 90.0F;
            float f1 = Mth.wrapDegrees(f - this.getYRot());
            this.zza = 0.5F;
            this.setYRot(this.getYRot() + f1);


    }


    @Override
    public List<ExtendedSensor<Archer>> getSensors() {
        return ObjectArrayList.of(
                new HurtBySensor<>(),
                new NearbyPlayersSensor<>(),
                new NearbyBlocksSensor<Archer>().setRadius(16D, 16D).setPredicate((state, entity) -> state.is(Blocks.WATER)),
                new NearbyLivingEntitySensor<Archer>().setRadius(32, 32)
                        .setPredicate((target, entity) ->
                                InfightingUtil.nonfactionSpecific(target)
                        ));
    }


    @Override
    public BrainActivityGroup<Archer> getCoreTasks() { // These are the tasks that run all the time (usually)
        return BrainActivityGroup.coreTasks(
                new LookAtTarget<>(),
                new MoveToWalkTarget<>().startCondition(entity -> !this.isInWaterOrBubble() && this.isOnGround())
        );
    }

    @Override
    public BrainActivityGroup<Archer> getIdleTasks() {
        return BrainActivityGroup.idleTasks(
                new FirstApplicableBehaviour<Archer>(
                        new CustomBehaviour<>(entity -> BrainUtils.clearMemory(this, MemoryModuleType.WALK_TARGET)).cooldownFor(entity -> 150),
                        new CustomBehaviour<>(entity -> this.attackbehaviour = 0).startCondition(entity -> this.attackbehaviour != 0),
                        new TargetOrRetaliate<>(),
                        new SetPlayerLookTarget<>(),
                        new SetRandomLookTarget<>(),
                        new SetBlockToWalkTargetNoInterest<>(),
                        new FirstApplicableBehaviour<>(
                                new PushToWalkTarget<>(true)
                                        .setXZMoveSpeedMod(entity -> 0.3f)
                                        .startCondition(entity -> (this.isInWaterOrBubble() || !this.isOnGround()) && this.getwateremountbelow() < 3)
                                        .cooldownFor(entity -> 1),
                        new PushToWalkTarget<>()
                                .setXZMoveSpeedMod(entity -> 0.2f)
                                .startCondition(entity -> this.isInWaterOrBubble())
                                .cooldownFor(entity -> entity.getRandom().nextInt(1, 2)))
                )
        );

    }


    public void setAttackbehaviour(int attackbehaviour) {
        this.attackbehaviour = attackbehaviour;
    }

    @Override
    public void jumpInFluid(FluidType type) {
    }

    @Override
    public BrainActivityGroup<Archer> getFightTasks() { // These are the tasks that handle fighting
        return BrainActivityGroup.fightTasks(
                new CustomBehaviour<>(entity -> this.setAttackbehaviour(random.nextInt(2))).startCondition(entity -> this.random.nextFloat() < 0.01f).cooldownFor(entity -> 400),
                new CustomBehaviour<>(entity -> this.setAttackbehaviour(0)).startCondition(entity -> this.attackbehaviour == 1 && (!HLperUtil.TargetOrThis(this).isInWaterOrBubble() || (entity.distanceTo(HLperUtil.TargetOrThis(this)) > 5f && entity.distanceTo(HLperUtil.TargetOrThis(this)) < 12f))),
                new CustomBehaviour<>(entity -> this.setAttackbehaviour(1)).startCondition(entity -> this.attackbehaviour == 0 && ((entity.distanceTo(HLperUtil.TargetOrThis(this)) < 2f || entity.distanceTo(HLperUtil.TargetOrThis(this)) > 18 ) && HLperUtil.TargetOrThis(this).isInWaterOrBubble())),
                new CustomBehaviour<>(entity -> BrainUtils.clearMemory(this, MemoryModuleType.WALK_TARGET)).cooldownFor(entity -> 200),
                new CustomBehaviour<>(entity -> BrainUtils.clearMemory(this, MemoryModuleType.WALK_TARGET)).startCondition(entity -> this.attackbehaviour == 0).cooldownFor(entity -> 200),
                new InvalidateAttackTarget<Archer>(),
                new Retaliate<Archer>(),
                new FirstApplicableBehaviour<>(
                        new SetBlockToWalkTargetNoInterest<Archer>().startCondition(entity -> (this.isInWaterOrBubble() || !this.isOnGround()) && this.attackbehaviour == 0),
                        new SetWalkTargetToAttackTarget<Archer>().startCondition(entity -> (this.isInWaterOrBubble() || !this.isOnGround()) && this.attackbehaviour != 0)
                        ),
                new FirstApplicableBehaviour<>(
                        new PushToWalkTarget<>(true)
                                .setXZMoveSpeedMod(entity -> 0.3f)
                                .startCondition(entity -> (this.isInWaterOrBubble() || !this.isOnGround()) && this.attackbehaviour == 0 && this.getwateremountbelow() < 3)
                                .cooldownFor(entity -> 1),
                        new PushToWalkTarget<>()
                                .setXZMoveSpeedMod(entity -> 0.3f)
                                .startCondition(entity -> (this.isInWaterOrBubble() || !this.isOnGround()) && this.attackbehaviour == 0 )
                                .cooldownFor(entity -> 1),
                        new PushToWalkTargetDontStop<>()
                                .setXZMoveSpeedMod(entity -> 0.33f)
                                .startCondition(entity -> (this.isInWaterOrBubble() || !this.isOnGround()) && this.attackbehaviour != 0)
                                .cooldownFor(entity -> 1)
                ),
                new FirstApplicableBehaviour<>(
                new ConfigurableAnimatableMeleeAttack<Archer>(4, 0, 1f, 1f, null, 0, this.getBiteSound(), null)
                                .whenStarting(entity -> triggerAnim("onetime", "attack"))
                                .cooldownFor(entity -> 13),
                 new StopAndShoot<Archer>(3, 7, 0.5f, null).cooldownFor(entity -> random.nextInt(50, 100))
                .whenStarting(entity -> triggerAnim("onetime", "shoot")).startCondition(entity -> this.attackbehaviour == 0))
        );

    }


    @Override
    public boolean canDrownInFluidType(FluidType type) {
        if (type.equals(ForgeMod.WATER_TYPE.get())) {
            return false;
        }
        if (type.equals(ForgeMod.EMPTY_TYPE.get())) return true;
        return super.canDrownInFluidType(type);
    }


    @Override
    protected float getWaterSlowDown() {
        //  if (this.getTarget() != null) return 0.98f;
        return 0.5f;

    }




    @Override
    public boolean isPushedByFluid(FluidType type) {
        if (type.equals(ForgeMod.WATER_TYPE.get())) {
            return false;
        }
        return super.isPushedByFluid(type);
    }

    @Override
    public boolean isNoGravity() {
        return this.isInWaterOrBubble();
    }

    @Override
    public double getFluidMotionScale(FluidType type) {
        if (type.equals(ForgeMod.WATER_TYPE.get())) {
            return 0.5;
        }
        return super.getFluidMotionScale(type);
    }


    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(new AnimationController<>(this, "swim", 0, this::idle));
        controllerRegistrar.add(new AnimationController<>(this, "flippers", 0, this::mouthpred));
        controllerRegistrar.add(new AnimationController<>(this, "onetime", state -> PlayState.STOP)
                .triggerableAnim("attack", RawAnimation.begin().then("animation.archer.bite", Animation.LoopType.PLAY_ONCE))
                .triggerableAnim("shoot", RawAnimation.begin().then("animation.archer.shoot", Animation.LoopType.PLAY_ONCE)));
    }

    private <T extends GeoAnimatable> PlayState idle(AnimationState<T> tAnimationState) {

        if (!this.isInWaterOrBubble() && this.getwateremountbelow() == 0) {
            tAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.archer.onland", Animation.LoopType.LOOP));
            return PlayState.CONTINUE;
        }

        tAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.archer.swim", Animation.LoopType.LOOP));
        return PlayState.CONTINUE;


    }


    private <T extends GeoAnimatable> PlayState mouthpred(AnimationState<T> tAnimationState) {
        tAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.archer.flipper", Animation.LoopType.LOOP));
        return PlayState.CONTINUE;

    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }


    protected SoundEvent getSpitSound() {
        switch (this.random.nextInt(1,4)) {
            case 1:  return HalfLifeSounds.BULLSQUID_SHOOT_1.get();
            case 2:  return HalfLifeSounds.BULLSQUID_SHOOT_2.get();
            case 3:  return HalfLifeSounds.BULLSQUID_SHOOT_3.get();
        }
        return HalfLifeSounds.HEADCRAB_1_PAIN_1.get();

    }



    @Override
    public void performRangedAttack(LivingEntity livingentity, float p_33318_) {

        double d0 = this.distanceToSqr(livingentity);
        double d1 = livingentity.getX() - this.getX();
        double d2 = livingentity.getY(0.4D) - this.getY(0.4D);
        double d3 = livingentity.getZ() - this.getZ();
        double d4 = Math.sqrt(Math.sqrt(d0)) * 0.5D;


       this.playSound(getSpitSound(), this.getSoundVolume(), this.getVoicePitch());

        AcidBall acidBall = new AcidBall(this.level, this, d1, d2, d3); //this.getRandom().triangle(d1, 2.297D * d4)
        acidBall.setPos(this.getX() - (double)(this.getBbWidth() + 1.0F) * 0.5D * (double) Mth.sin(this.yBodyRot * ((float)Math.PI / 180F)), this.getEyeY() - (double)0.1F, this.getZ() + (double)(this.getBbWidth() + 1.0F) * 0.5D * (double) Mth.cos(this.yBodyRot * ((float)Math.PI / 180F)));
        this.level.addFreshEntity(acidBall);

    }



    public static boolean checkSharkSpawnRules(EntityType<Archer> pDrowned, ServerLevelAccessor pServerLevel, MobSpawnType pMobSpawnType, BlockPos pPos, RandomSource pRandom) {
        if (!pServerLevel.getFluidState(pPos.below()).is(FluidTags.WATER)) {
            return false;
        } else {
            Holder<Biome> holder = pServerLevel.getBiome(pPos);
            boolean flag = pServerLevel.getDifficulty() != Difficulty.PEACEFUL && isDarkEnoughToSpawn(pServerLevel, pPos, pRandom) && (pMobSpawnType == MobSpawnType.SPAWNER || pServerLevel.getFluidState(pPos).is(FluidTags.WATER));
            if (holder.is(BiomeTags.MORE_FREQUENT_DROWNED_SPAWNS)) {
                return pRandom.nextInt(50) == 0 && flag;
            } else {
                return pRandom.nextInt(50) == 0 && isDeepEnoughToSpawn(pServerLevel, pPos) && flag;
            }
        }
    }

    private static boolean isDeepEnoughToSpawn(LevelAccessor pLevel, BlockPos pPos) {
        return pPos.getY() < pLevel.getSeaLevel() - 5;
    }

    public boolean checkSpawnObstruction(LevelReader pLevel) {
        return pLevel.isUnobstructed(this);
    }






}
