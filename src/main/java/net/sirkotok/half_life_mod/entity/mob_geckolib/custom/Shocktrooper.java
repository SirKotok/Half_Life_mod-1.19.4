package net.sirkotok.half_life_mod.entity.mob_geckolib.custom;


import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.BlockPos;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.event.ForgeEventFactory;
import net.sirkotok.half_life_mod.entity.ModEntities;
import net.sirkotok.half_life_mod.entity.base.HalfLifeMonster;
import net.sirkotok.half_life_mod.entity.base.HalfLifeNeutral;
import net.sirkotok.half_life_mod.entity.brain.behaviour.*;
import net.sirkotok.half_life_mod.entity.brain.sensor.SmellSensor;
import net.sirkotok.half_life_mod.entity.projectile.ShockProjectile;
import net.sirkotok.half_life_mod.sound.ModSounds;
import net.sirkotok.half_life_mod.util.HLperUtil;
import net.sirkotok.half_life_mod.util.ModTags;
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
import net.tslat.smartbrainlib.api.core.behaviour.custom.target.InvalidateAttackTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.target.SetPlayerLookTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.target.SetRandomLookTarget;
import net.tslat.smartbrainlib.api.core.sensor.ExtendedSensor;
import net.tslat.smartbrainlib.api.core.sensor.vanilla.HurtBySensor;
import net.tslat.smartbrainlib.api.core.sensor.vanilla.NearbyLivingEntitySensor;
import net.tslat.smartbrainlib.api.core.sensor.vanilla.NearbyPlayersSensor;
import net.tslat.smartbrainlib.util.BrainUtils;
import net.tslat.smartbrainlib.util.EntityRetrievalUtil;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.List;


public class Shocktrooper extends HalfLifeMonster implements RangedAttackMob, GeoEntity, SmartBrainOwner<Shocktrooper> {

    @Override
    protected float getSoundVolume() {
        return 0.5f;
    }


    @Override
    public void die(DamageSource pDamageSource) {
        super.die(pDamageSource);
        if (!level.isClientSide()) {
            ServerLevel serverLevel = (ServerLevel) level;
                Shockroach summon = ModEntities.SHOCKROACH.get().create(serverLevel);
                if (summon != null) {
                    summon.moveTo(this.position());
                    ForgeEventFactory.onFinalizeSpawn((Mob) summon, (ServerLevelAccessor) serverLevel, serverLevel.getCurrentDifficultyAt(summon.blockPosition()), MobSpawnType.REINFORCEMENT, null, null);
                    serverLevel.addFreshEntity(summon);
                    if (this.getKillCredit() != null) {
                    BrainUtils.setMemory(summon, MemoryModuleType.ATTACK_TARGET, this.getKillCredit()); }
                }
        }


    }

    private AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);



    public static final EntityDataAccessor<Boolean> IS_SITTING = SynchedEntityData.defineId(Shocktrooper.class, EntityDataSerializers.BOOLEAN);
    public static final EntityDataAccessor<Boolean> IS_ANGRY = SynchedEntityData.defineId(Shocktrooper.class, EntityDataSerializers.BOOLEAN);
    public static final EntityDataAccessor<Integer> RACE_X_AROUND = SynchedEntityData.defineId(Bullsquid.class, EntityDataSerializers.INT);


    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(IS_SITTING, false);
        this.entityData.define(IS_ANGRY, false);
        this.entityData.define(RACE_X_AROUND, 0);
    }

    public void setxround(int emount) {
        this.entityData.set(RACE_X_AROUND, emount);
    }
    public int getxround() {
        return this.entityData.get(RACE_X_AROUND);
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.level.isClientSide()) {
            if (RandomSource.create().nextFloat() < 0.002f) this.triggerAnim("blink", "blink");
            if (this.tickCount % 100 == 0) {
                ServerLevel pLevel = (ServerLevel) this.level;
                BlockPos pBlockPos = this.blockPosition();
                int rad = 20;
                List<Mob> race_x = EntityRetrievalUtil.getEntities((Level) pLevel,
                        new AABB(pBlockPos.getX() - rad, pBlockPos.getY() - rad, pBlockPos.getZ() - rad,
                                pBlockPos.getX() + rad, pBlockPos.getY() + rad, pBlockPos.getZ() + rad), obj -> obj.getType().is(ModTags.EntityTypes.FACTION_RACE_X));
                this.setxround(race_x.size());
                if (BrainUtils.getTargetOfEntity(this) != null) {
                    for (Mob x : race_x) {
                        if (BrainUtils.getTargetOfEntity(x) == null) {
                            BrainUtils.setTargetOfEntity(x, BrainUtils.getTargetOfEntity(this));
                        }
                    }
                }
            }
        }

    }

    public void setIsSitting(boolean sit) {
        this.entityData.set(IS_SITTING, sit);
    }
    public boolean getIssitting() {
        return this.entityData.get(IS_SITTING);
    }



    protected boolean isangry() {
        return this.entityData.get(IS_ANGRY);
    }

    public Shocktrooper(EntityType type, Level level) {
        super(type, level);
        this.xpReward = 10;
         this.setPathfindingMalus(BlockPathTypes.DAMAGE_FIRE, -1F);
        this.setPathfindingMalus(BlockPathTypes.DANGER_FIRE, -1F);
    }


    public static AttributeSupplier setAttributes() {
        return Monster.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 45D)
                .add(Attributes.ATTACK_DAMAGE, 10f)
                .add(Attributes.ATTACK_SPEED, 1.0f)
                .add(Attributes.ATTACK_KNOCKBACK, 1f)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.2f)
                .add(Attributes.ARMOR, 5f)
                .add(Attributes.MOVEMENT_SPEED, 0.30f).build();
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
    public List<ExtendedSensor<Shocktrooper>> getSensors() {
        return ObjectArrayList.of(
                new SmellSensor<>(),
                new HurtBySensor<>(),
                new NearbyPlayersSensor<>(),
                new NearbyLivingEntitySensor<Shocktrooper>()
                        .setPredicate((target, entity) ->
                            target instanceof Player ||
                            target instanceof IronGolem ||
                            target instanceof AbstractVillager ||
                            target instanceof HalfLifeNeutral
                            ));
    }



    @Override
    public BrainActivityGroup<Shocktrooper> getCoreTasks() {
        return BrainActivityGroup.coreTasks(
                new LookAtTarget<>(),
                new MoveToWalkTarget<>());
    }




    @Override
    public BrainActivityGroup<Shocktrooper> getIdleTasks() {
        return BrainActivityGroup.idleTasks(
                new FirstApplicableBehaviour<Shocktrooper>(
                        new TargetOrRetaliateHLT<>(),
                        new SetPlayerLookTarget<>(),
                        new SetRandomLookTarget<>()),
                new OneRandomBehaviour<>(
                        new SetRandomWalkTarget<>(),
                        new Idle<>().runFor(entity -> entity.getRandom().nextInt(10, 20)),
                        new Idle<>().runFor(entity -> entity.getRandom().nextInt(10, 90))
                                .whenStarting(entity -> this.entityData.set(IS_ANGRY, false))));
    }



    @Override
    public BrainActivityGroup<Shocktrooper> getFightTasks() {
        return BrainActivityGroup.fightTasks(
                new InvalidateAttackTarget<>(),
                new Retaliate<>(),
                new CustomBehaviour<>(entity -> this.entityData.set(IS_ANGRY, true)).startCondition(entity -> !this.isangry()),
                new OneRandomBehaviour<>(
                        new SetRandomWalkTarget<>().setRadius(4, 3).startCondition(entity -> this.getxround() > 1),
                        new SetRandomWalkTarget<>().setRadius(8, 4).startCondition(entity -> this.getxround() > 2),
                        new SetWalkTargetToRandomSpotAroundAttackTarget<>(),
                        new SetWalkTargetToRandomSpotAroundAttackTarget<>(),
                        new SetWalkTargetToRandomSpotAroundAttackTarget<>(),
                         new SetWalkTargetToRandomSpotAroundAttackTarget<>(),
                        new SetWalkTargetToRandomSpotAroundAttackTarget<>()
                ),
                new FirstApplicableBehaviour<>(
                new DoubleMeleeAttack<Shocktrooper>(10, 6, 0.1f, 1, 1, null , 0, null, this.getDoubleAttackSound())
                        .whenStarting(entity ->triggerAnim("onetime", "doubleattack"))
                        .cooldownFor(entity -> random.nextInt(10, 15)),
                new StopAndShoot<Shocktrooper>(3, 8, 1f)
                        .whenStarting(entity -> triggerAnim("onetime", "shoot"))
                        .cooldownFor(entity -> random.nextInt(0, 9) == 0 ? 100 : 8)
                )
        );

    }




    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(new AnimationController<>(this, "noattack", 0, this::noattack));
        controllerRegistrar.add(new AnimationController<>(this, "mouth", 0, this::headpredicate));
        controllerRegistrar.add(new AnimationController<>(this, "tail", 0, this::tailpredicate));
        controllerRegistrar.add(new AnimationController<>(this, "mainloop", 0, this::mainpredicate));
        controllerRegistrar.add(new AnimationController<>(this, "onetime", state -> PlayState.STOP)
                .triggerableAnim("doubleattack", RawAnimation.begin().then("animation.shocktrooper.meleedouble", Animation.LoopType.PLAY_ONCE))
                .triggerableAnim("shoot", RawAnimation.begin().then("animation.shocktrooper.shoot_roach", Animation.LoopType.PLAY_ONCE))
                .triggerableAnim("spore", RawAnimation.begin().then("animation.shocktrooper.spit", Animation.LoopType.PLAY_ONCE))
                .triggerableAnim("reload", RawAnimation.begin().then("animation.pitdrone.reload", Animation.LoopType.PLAY_ONCE)));
        controllerRegistrar.add(new AnimationController<>(this, "blink", state -> PlayState.STOP)
                        .triggerableAnim("blink", RawAnimation.begin().then("animation.shocktrooper.blink", Animation.LoopType.PLAY_ONCE)));

    }

    private <T extends GeoAnimatable> PlayState headpredicate(AnimationState<T> tAnimationState) {
        tAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.shocktrooper.mouth", Animation.LoopType.LOOP));
        return PlayState.CONTINUE;
    }

    private <T extends GeoAnimatable> PlayState noattack(AnimationState<T> tAnimationState) {
        tAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.shocktrooper.noattack", Animation.LoopType.LOOP));
        return PlayState.CONTINUE;
    }

    private <T extends GeoAnimatable> PlayState tailpredicate(AnimationState<T> tAnimationState) {
        tAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.shocktrooper.tail", Animation.LoopType.LOOP));
        return PlayState.CONTINUE;
    }


    private <T extends GeoAnimatable> PlayState mainpredicate(AnimationState<T> tAnimationState) {




        if(tAnimationState.isMoving()) {
            tAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.shocktrooper.walk", Animation.LoopType.LOOP));
            return PlayState.CONTINUE;
        }

        if (this.getIssitting()) {
            tAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.shocktrooper.pose_leg", Animation.LoopType.LOOP));
            return PlayState.CONTINUE;
        }


        if (this.isangry()) {
            tAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.shocktrooper.idle2", Animation.LoopType.LOOP));
            return PlayState.CONTINUE;
        }

        tAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.shocktrooper.idle", Animation.LoopType.LOOP));
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

        this.playSound(getShootSound(), this.getSoundVolume(), this.getVoicePitch());


       ShockProjectile acidBall = new ShockProjectile(this.level, this, d1, d2, d3);
        acidBall.setPos(this.getX() - (double)(this.getBbWidth() + 1.0F) * 0.5D * (double)Mth.sin(this.yBodyRot * ((float)Math.PI / 180F)), this.getEyeY() - (double)0.1F, this.getZ() + (double)(this.getBbWidth() + 1.0F) * 0.5D * (double) Mth.cos(this.yBodyRot * ((float)Math.PI / 180F)));
        this.level.addFreshEntity(acidBall);

    }


    @Override
    public void playAmbientSound() {
        super.playAmbientSound();
    }

    public SoundEvent getShootSound() {
        return ModSounds.SHOCK_FIRE.get();
    }
    public SoundEvent getDoubleAttackSound() {
       return ModSounds.SHOCKTROOPER_ATTACK.get();
    }


    protected SoundEvent getDeathSound() {
        switch (this.random.nextInt(1,5)) {
            case 1:  return ModSounds.SHOCKTROOPER_DIE1.get();
            case 2:  return ModSounds.SHOCKTROOPER_DIE2.get();
            case 3:  return ModSounds.SHOCKTROOPER_DIE3.get();
            case 4:  return ModSounds.SHOCKTROOPER_DIE4.get();
        }
        return ModSounds.HEADCRAB_1_DIE_1.get();
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource pDamageSource) {
        switch (this.random.nextInt(1,5)) {
            case 1:  return ModSounds.SHOCKTROOPER_PAIN1.get();
            case 2:  return ModSounds.SHOCKTROOPER_PAIN2.get();
            case 3:  return ModSounds.SHOCKTROOPER_PAIN3.get();
            case 4:  return ModSounds.SHOCKTROOPER_PAIN4.get();
            case 5:  return ModSounds.SHOCKTROOPER_PAIN5.get();
        }
        return ModSounds.HEADCRAB_1_DIE_1.get();
    }

    @Override
    public int getAmbientSoundInterval() {
        return 200;
    }

    protected SoundEvent getAmbientSound() {
        return ModSounds.SHOCKTROOPER_IDLE.get();
    }




















}





