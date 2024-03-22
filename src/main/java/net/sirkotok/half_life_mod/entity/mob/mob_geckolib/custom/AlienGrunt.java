package net.sirkotok.half_life_mod.entity.mob.mob_geckolib.custom;


import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.BlockPos;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.sirkotok.half_life_mod.entity.base.HalfLifeMonster;
import net.sirkotok.half_life_mod.entity.brain.behaviour.*;
import net.sirkotok.half_life_mod.entity.mob.modinterface.VariableRangedMob;
import net.sirkotok.half_life_mod.entity.particle.HalfLifeParticles;
import net.sirkotok.half_life_mod.entity.projectile.BeeProjectile;
import net.sirkotok.half_life_mod.entity.projectile.ControllerBaseProjectile;
import net.sirkotok.half_life_mod.entity.projectile.ControllerBigProjectile;
import net.sirkotok.half_life_mod.misc.gamerules.HalfLifeGameRules;
import net.sirkotok.half_life_mod.misc.util.CommonSounds;
import net.sirkotok.half_life_mod.misc.util.HLTags;
import net.sirkotok.half_life_mod.misc.util.HLperUtil;
import net.sirkotok.half_life_mod.misc.util.InfightingUtil;
import net.sirkotok.half_life_mod.sound.HalfLifeSounds;
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
import net.tslat.smartbrainlib.api.core.sensor.ExtendedSensor;
import net.tslat.smartbrainlib.api.core.sensor.custom.NearbyBlocksSensor;
import net.tslat.smartbrainlib.api.core.sensor.vanilla.HurtBySensor;
import net.tslat.smartbrainlib.api.core.sensor.vanilla.NearbyLivingEntitySensor;
import net.tslat.smartbrainlib.api.core.sensor.vanilla.NearbyPlayersSensor;
import net.tslat.smartbrainlib.util.BrainUtils;
import net.tslat.smartbrainlib.util.EntityRetrievalUtil;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.List;


public class AlienGrunt extends HalfLifeMonster implements GeoEntity, RangedAttackMob, VariableRangedMob, SmartBrainOwner<AlienGrunt> {








    private AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);


    public static final EntityDataAccessor<Boolean> IS_EATING = SynchedEntityData.defineId(AlienGrunt.class, EntityDataSerializers.BOOLEAN);
    public static final EntityDataAccessor<Boolean> IS_ANGRY = SynchedEntityData.defineId(AlienGrunt.class, EntityDataSerializers.BOOLEAN);

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(IS_ANGRY, false);
        this.entityData.define(IS_EATING, false);
    }


    @Override
    public void tick() {
        super.tick();

        if (this.tickCount % 100 == 0 && !this.level.isClientSide()) {
            ServerLevel pLevel = (ServerLevel) this.level;
            BlockPos pBlockPos = this.blockPosition();
            int rad = 20;
            List<Mob> xen_fac = EntityRetrievalUtil.getEntities((Level) pLevel,
                    new AABB(pBlockPos.getX() - rad, pBlockPos.getY() - rad, pBlockPos.getZ() - rad,
                            pBlockPos.getX() + rad, pBlockPos.getY() + rad, pBlockPos.getZ() + rad), obj -> obj.getType().is(HLTags.EntityTypes.FACTION_XEN));
            if (BrainUtils.getTargetOfEntity(this) != null) {
                for (Mob x : xen_fac) {
                    if (BrainUtils.getTargetOfEntity(x) == null) {
                        BrainUtils.setTargetOfEntity(x, BrainUtils.getTargetOfEntity(this));
                    }
                }
            }
        }

    }


    public boolean isangry() {
        return this.entityData.get(IS_ANGRY);
    }
    public boolean iseating() {
        return this.entityData.get(IS_EATING);
    }

    public AlienGrunt(EntityType type, Level level) {
        super(type, level);
        this.xpReward = 15;
    }







    public static AttributeSupplier setAttributes() {
        return Monster.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 80D)
                .add(Attributes.ARMOR, 10D)
                .add(Attributes.ARMOR_TOUGHNESS, 10D)
                .add(Attributes.ATTACK_DAMAGE, 8f)
                .add(Attributes.ATTACK_SPEED, 1.0f)
                .add(Attributes.ATTACK_KNOCKBACK, 1.0f)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.5f)
                .add(Attributes.MOVEMENT_SPEED, 0.22f).build();
    }



    public static SoundEvent getfiresound() {
        switch (RandomSource.create().nextInt(1,4)) {
            case 1:  return HalfLifeSounds.AG_FIRE1.get();
            case 2:  return HalfLifeSounds.AG_FIRE2.get();
            case 3:  return HalfLifeSounds.AG_FIRE3.get();
        }
        return HalfLifeSounds.HEADCRAB_1_DIE_1.get();
    }


    public SoundEvent getattacksound() {
        switch (this.random.nextInt(1,4)) {
            case 1:  return HalfLifeSounds.AG_ATTACK1.get();
            case 2:  return HalfLifeSounds.AG_ATTACK2.get();
            case 3:  return HalfLifeSounds.AG_ATTACK3.get();
        }
        return HalfLifeSounds.HEADCRAB_1_DIE_1.get();
    }

    protected SoundEvent getDeathSound() {
        switch (this.random.nextInt(1,6)) {
            case 1:  return HalfLifeSounds.AG_DIE1.get();
            case 2:  return HalfLifeSounds.AG_DIE2.get();
            case 3:  return HalfLifeSounds.AG_DIE3.get();
            case 4:  return HalfLifeSounds.AG_DIE4.get();
            case 5:  return HalfLifeSounds.AG_DIE5.get();
        }
        return HalfLifeSounds.HEADCRAB_1_DIE_1.get();
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource pDamageSource) {
        switch (this.random.nextInt(1,6)) {
            case 1:  return HalfLifeSounds.AG_PAIN1.get();
            case 2:  return HalfLifeSounds.AG_PAIN2.get();
            case 3:  return HalfLifeSounds.AG_PAIN3.get();
            case 4:  return HalfLifeSounds.AG_PAIN4.get();
            case 5:  return HalfLifeSounds.AG_PAIN5.get();
        }
        return HalfLifeSounds.HEADCRAB_1_DIE_1.get();
    }




    protected SoundEvent getAmbientSound() {
        int f = !this.isangry() ? random.nextInt(1, 6) : random.nextInt(random.nextFloat() < 0.5f ? 0 : 6, 10);
        switch (f) {
            case 1:  return HalfLifeSounds.AG_IDLE1.get();
            case 2:  return HalfLifeSounds.AG_IDLE2.get();
            case 3:  return HalfLifeSounds.AG_IDLE3.get();
            case 4:  return HalfLifeSounds.AG_IDLE4.get();
            case 5:  return HalfLifeSounds.AG_IDLE5.get();
            case 6:  return HalfLifeSounds.AG_ALERT1.get();
            case 7:  return HalfLifeSounds.AG_ALERT2.get();
            case 8:  return HalfLifeSounds.AG_ALERT3.get();
            case 9:  return HalfLifeSounds.AG_ALERT4.get();
            case 10:  return HalfLifeSounds.AG_ALERT5.get();
        }
        return SoundEvents.FROG_STEP;
    }






    @Override
    protected float getSoundVolume() {
        return 0.6f;
    }



    protected SoundEvent getStep1Sound() {
        switch (this.random.nextInt(1,3)) {
            case 1:  return HalfLifeSounds.AG_STEP1.get();
            case 2:  return HalfLifeSounds.AG_STEP3.get();
        }
        return SoundEvents.FROG_STEP;
    }
    protected SoundEvent getStep2Sound() {
        switch (this.random.nextInt(1,3)) {
            case 1:  return HalfLifeSounds.AG_STEP2.get();
            case 2:  return HalfLifeSounds.AG_STEP4.get();
        }
        return SoundEvents.FROG_STEP;
    }

    @Override
    protected void playStepSound(BlockPos pPos, BlockState pState) {
        if (this.tickCount%3 == 0) {
            BlockState blockstate = this.level.getBlockState(pPos.above());
            boolean flag = blockstate.is(BlockTags.INSIDE_STEP_SOUND_BLOCKS);
            if (flag || !pState.getMaterial().isLiquid()) {
                if (this.tickCount%2 == 0) this.playSound(getStep1Sound());
                else this.playSound(getStep2Sound());
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
    public List<ExtendedSensor<AlienGrunt>> getSensors() {
        return ObjectArrayList.of(
                new HurtBySensor<>(),
                new NearbyPlayersSensor<>(),
                new NearbyLivingEntitySensor<AlienGrunt>()
                        .setPredicate((target, entity) -> InfightingUtil.commonenemy(target) || InfightingUtil.XenForcesSpecific(target)
                              ));
    }



    @Override
    public BrainActivityGroup<AlienGrunt> getCoreTasks() {
        return BrainActivityGroup.coreTasks(
                new LookAtTarget<>(),
                new MoveToWalkTarget<>());

    }

    @Override
    public BrainActivityGroup<AlienGrunt> getIdleTasks() { // These are the tasks that run when the mob isn't doing anything else (usually)
        return BrainActivityGroup.idleTasks(
                  new FirstApplicableBehaviour<AlienGrunt>(
                                new TargetOrRetaliateHLT<>(),
                                new SetPlayerLookTarget<>(),
                                new SetRandomLookTarget<>()),
                  new OneRandomBehaviour<>(
                                new SetRandomWalkTarget<>(),
                                new Idle<>().runFor(entity -> entity.getRandom().nextInt(10, 40))
                                        .whenStarting(entity -> this.entityData.set(IS_ANGRY, false))));

    }



    @Override
    public BrainActivityGroup<AlienGrunt> getFightTasks() { // These are the tasks that handle fighting
        return BrainActivityGroup.fightTasks(
                new InvalidateAttackTarget<>(),
                new Retaliate<>(),
                new CustomBehaviour<>(entity -> this.entityData.set(IS_ANGRY, true)).startCondition(entity -> !this.entityData.get(IS_ANGRY)),
                new SetWalkTargetToRandomSpotAroundAttackTarget<>().speedMod(1.3f).startCondition(entity ->  this.distanceTo(HLperUtil.TargetOrThis(this))  > 15),
                new OneRandomBehaviour<>(
                        new SetRandomWalkTarget<>().setRadius(15, 5).speedModifier(1.3f).cooldownFor(entity -> 300),
                        new SetWalkTargetToRandomSpotAroundAttackTarget<>().radius(10, 4).speedMod(1.3f),
                        new SetWalkTargetToRandomSpotAroundAttackTarget<>().radius(10, 4).speedMod(1.3f),
                        new SetWalkTargetToRandomSpotAroundAttackTarget<>().radius(15, 7).speedMod(1.3f)
                ),
                new FirstApplicableBehaviour<>(
                    new ConfigurableAnimatableMeleeAttack<AlienGrunt>(13, 0.1f, 1, 2f, null, 0, CommonSounds.getClawHitSound(), this.getattacksound(), CommonSounds.getClawMissSound(), null, null)
                                .whenStarting(entity -> triggerAnim("onetime", "m1"))
                                .cooldownFor(entity -> random.nextInt(30, 50)),
                    new ConfigurableAnimatableMeleeAttack<AlienGrunt>(13, 0.1f, 1, 2f, null, 0, CommonSounds.getClawHitSound(), this.getattacksound(), CommonSounds.getClawMissSound(), null, null)
                                .whenStarting(entity -> triggerAnim("onetime", "m2"))
                                .cooldownFor(entity -> random.nextInt(30, 50)),
                    new OneRandomBehaviour<>(
                         new StopAndShootOverTime<>(21, 1, 6, 19, 4, 0.7f, false, null, null).cooldownFor(entity -> random.nextInt(40, 100))
                             .whenStarting(entity -> triggerAnim("onetime", "s3")),
                         new StopAndShootOverTime<>(30, 1, 6, 27, 4, 0.7f, false, null, null).cooldownFor(entity -> random.nextInt(40, 100))
                                .whenStarting(entity -> triggerAnim("onetime", "s5")),
                         new StopAndShootOverTime<>(45, 1, 6, 39, 4, 0.7f, false, null, null).cooldownFor(entity -> random.nextInt(40, 100))
                         .whenStarting(entity -> triggerAnim("onetime", "s8"))
                ))
                );
    }





    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(new AnimationController<>(this, "controller", 0, this::idle));
        controllerRegistrar.add(new AnimationController<>(this, "mouth", 0, this::mouth));
        controllerRegistrar.add(new AnimationController<>(this, "noshoot", 0, this::noshoot));
        controllerRegistrar.add(new AnimationController<>(this, "gun", 0, this::gun));
        controllerRegistrar.add(new AnimationController<>(this, "onetime", state -> PlayState.STOP)
                .triggerableAnim("s3", RawAnimation.begin().then("animation.aliengrunt.hiveshootx3", Animation.LoopType.PLAY_ONCE))
                .triggerableAnim("s5", RawAnimation.begin().then("animation.aliengrunt.hiveshootx5", Animation.LoopType.PLAY_ONCE))
                .triggerableAnim("s8", RawAnimation.begin().then("animation.aliengrunt.hiveshootx8", Animation.LoopType.PLAY_ONCE))
                .triggerableAnim("m1", RawAnimation.begin().then("animation.aliengrunt.melee1", Animation.LoopType.PLAY_ONCE))
                .triggerableAnim("m2", RawAnimation.begin().then("animation.aliengrunt.melee2", Animation.LoopType.PLAY_ONCE))
                .triggerableAnim("eat", RawAnimation.begin().then("animation.aliengrunt.eat", Animation.LoopType.PLAY_ONCE))
        );
    }



    private <T extends GeoAnimatable> PlayState mouth(AnimationState<T> tAnimationState) {
        tAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.aliengrunt.mouthallways", Animation.LoopType.LOOP));
        return PlayState.CONTINUE;
    }
    private <T extends GeoAnimatable> PlayState noshoot(AnimationState<T> tAnimationState) {
        tAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.aliengrunt.noshoot", Animation.LoopType.LOOP));
        return PlayState.CONTINUE;
    }
    private <T extends GeoAnimatable> PlayState gun(AnimationState<T> tAnimationState) {
        tAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.aliengrunt.hivegun", Animation.LoopType.LOOP));
        return PlayState.CONTINUE;
    }

    private <T extends GeoAnimatable> PlayState idle(AnimationState<T> tAnimationState) {
        if(tAnimationState.isMoving()) {

            if (this.entityData.get(IS_ANGRY)) {
                tAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.aliengrunt.run", Animation.LoopType.LOOP));
                return PlayState.CONTINUE;
            }

            tAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.aliengrunt.walk", Animation.LoopType.LOOP));
            return PlayState.CONTINUE;
        }

        tAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.aliengrunt.idle", Animation.LoopType.LOOP));
        return PlayState.CONTINUE;
    }


    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    @Override
    public void performRangedAttack(LivingEntity pTarget, float pVelocity) {
        double d0 = this.distanceToSqr(pTarget);
        double d1 = pTarget.getX() - this.getX();
        double d2 = pTarget.getY(0.4D) - this.getY(0.4D);
        double d3 = pTarget.getZ() - this.getZ();
        ControllerBigProjectile acidBall = new ControllerBigProjectile(this.level, this, d1, d2, d3, pTarget);
        acidBall.setPos(this.getX() - (double)(this.getBbWidth() + 1.0F) * 0.5D * (double)Mth.sin(this.yBodyRot * ((float)Math.PI / 180F)), this.getEyeY() - (double)0.1F, this.getZ() + (double)(this.getBbWidth() + 1.0F) * 0.5D * (double) Mth.cos(this.yBodyRot * ((float)Math.PI / 180F)));
        acidBall.shoot(d1, d2, d3, pVelocity, 1f);
        this.level.addFreshEntity(acidBall);
    }

    @Override
    public void performVariableRangedAttack(LivingEntity pTarget, float pVelocity, float down) {
        double d0 = this.distanceToSqr(pTarget);
        double d1 = pTarget.getX() - this.getX();
        double d2 = pTarget.getY(0.4D) - this.getY(0.4D);
        double d3 = pTarget.getZ() - this.getZ();
        CommonSounds.PlaySoundAsOwn(this, this.getfiresound());
        BeeProjectile acidBall = new BeeProjectile(this.level, this, d1, d2, d3);
        acidBall.setPos(this.getX() - (double)(this.getBbWidth() + 1.0F) * 0.5D * (double)Mth.sin(this.yBodyRot * ((float)Math.PI / 180F)), this.getEyeY() - (double)0.5F, this.getZ() + (double)(this.getBbWidth() + 1.0F) * 0.5D * (double) Mth.cos(this.yBodyRot * ((float)Math.PI / 180F)));
        this.level.addFreshEntity(acidBall);
    }
}

