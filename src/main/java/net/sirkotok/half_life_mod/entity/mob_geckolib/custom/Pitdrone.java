package net.sirkotok.half_life_mod.entity.mob_geckolib.custom;


import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BlockTags;
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
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.AABB;
import net.sirkotok.half_life_mod.entity.base.HalfLifeMonster;
import net.sirkotok.half_life_mod.entity.base.HalfLifeNeutral;
import net.sirkotok.half_life_mod.entity.brain.behaviour.*;
import net.sirkotok.half_life_mod.entity.brain.sensor.SmellSensor;
import net.sirkotok.half_life_mod.entity.modinterface.AmmoCountMob;
import net.sirkotok.half_life_mod.entity.projectile.AcidBall;
import net.sirkotok.half_life_mod.entity.projectile.PitdroneSpike;
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
import net.tslat.smartbrainlib.api.core.behaviour.custom.path.SetWalkTargetToAttackTarget;
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
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

import javax.annotation.Nullable;
import java.util.List;


public class Pitdrone extends HalfLifeMonster implements AmmoCountMob, RangedAttackMob, GeoEntity, SmartBrainOwner<Pitdrone> {

    @Override
    protected float getSoundVolume() {
        return 0.5f;
    }


    private AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);




    public static final EntityDataAccessor<Boolean> IS_ANGRY = SynchedEntityData.defineId(Pitdrone.class, EntityDataSerializers.BOOLEAN);
    public static final EntityDataAccessor<Integer> AMMO = SynchedEntityData.defineId(Pitdrone.class, EntityDataSerializers.INT);

    public static final EntityDataAccessor<Boolean> CAN_RELOAD = SynchedEntityData.defineId(Pitdrone.class, EntityDataSerializers.BOOLEAN);
    public static final EntityDataAccessor<Integer> RACE_X_AROUND = SynchedEntityData.defineId(Bullsquid.class, EntityDataSerializers.INT);


    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(IS_ANGRY, false);
        this.entityData.define(CAN_RELOAD, true);
        this.entityData.define(AMMO, 6);
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
        if (this.tickCount % 100 == 0 && !this.level.isClientSide()) {
            ServerLevel pLevel = (ServerLevel) this.level;
            BlockPos pBlockPos = this.blockPosition();
            int rad = 20;
            List<Mob> race_x = EntityRetrievalUtil.getEntities((Level) pLevel,
                    new AABB(pBlockPos.getX() - rad, pBlockPos.getY() - rad, pBlockPos.getZ() - rad,
                            pBlockPos.getX() + rad, pBlockPos.getY() + rad, pBlockPos.getZ() + rad), obj -> obj.getType().is(ModTags.EntityTypes.FACTION_RACE_X));
            this.setxround(race_x.size());
            if (BrainUtils.getTargetOfEntity(this) != null && !(BrainUtils.getTargetOfEntity(this) instanceof Shockroach)) {
                for (Mob x : race_x) {
                    if (BrainUtils.getTargetOfEntity(x) == null) {
                        BrainUtils.setTargetOfEntity(x, BrainUtils.getTargetOfEntity(this));
                    }
                }
            }
            }

    }

    public int getammo() {
        int i = this.entityData.get(AMMO);
        return Math.max(Math.min(i, 6), 0);
    }
    protected void setammo(int ammo) {
         this.entityData.set(AMMO, ammo);
    }

    public void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        pCompound.putBoolean("CanReload", this.canreload());
        pCompound.putInt("Ammocount", this.getammo());
    }

    public void readAdditionalSaveData(CompoundTag pCompound) {
        this.setammo(pCompound.getInt("Ammocount"));
        this.setcanreload(pCompound.getBoolean("CanReload"));
        super.readAdditionalSaveData(pCompound);
    }



    public boolean canreload() {
        return this.entityData.get(CAN_RELOAD);
    }
    public void setcanreload(boolean reload) {
        this.entityData.set(CAN_RELOAD, reload);
    }


    protected boolean isangry() {
        return this.entityData.get(IS_ANGRY);
    }

    public Pitdrone(EntityType type, Level level) {
        super(type, level);
        this.xpReward = 10;
         this.setPathfindingMalus(BlockPathTypes.DAMAGE_FIRE, -1F);
        this.setPathfindingMalus(BlockPathTypes.DANGER_FIRE, -1F);
    }


    public static AttributeSupplier setAttributes() {
        return Monster.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 25D)
                .add(Attributes.ATTACK_DAMAGE, 6f)
                .add(Attributes.ATTACK_SPEED, 1.0f)
                .add(Attributes.ATTACK_KNOCKBACK, 1f)
                .add(Attributes.ARMOR, 5f)
                .add(Attributes.MOVEMENT_SPEED, 0.32f).build();
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
    public List<ExtendedSensor<Pitdrone>> getSensors() {
        return ObjectArrayList.of(
                new SmellSensor<>(),
                new HurtBySensor<>(),
                new NearbyPlayersSensor<>(),
                new NearbyLivingEntitySensor<Pitdrone>()
                        .setPredicate((target, entity) ->
                            target instanceof Player ||
                            target.getType().is(ModTags.EntityTypes.HEAD_CRAB) ||
                            target instanceof IronGolem ||
                            target instanceof AbstractVillager ||
                            target instanceof Shockroach ||
                            target instanceof Bullsquid ||
                            target instanceof HalfLifeNeutral
                            ));
    }



    @Override
    public BrainActivityGroup<Pitdrone> getCoreTasks() {
        return BrainActivityGroup.coreTasks(
                new LookAtTarget<>(),
                new MoveToWalkTarget<>());
    }




    @Override
    public BrainActivityGroup<Pitdrone> getIdleTasks() {
        return BrainActivityGroup.idleTasks(
                new FirstApplicableBehaviour<Pitdrone>(
                        new TargetOrRetaliateHLT<>(),
                        new SetPlayerLookTarget<>(),
                        new SetRandomLookTarget<>()),
                new OneRandomBehaviour<>(
                        new SetRandomWalkTarget<>(),
                        new Idle<>().runFor(entity -> entity.getRandom().nextInt(10, 40))
                                .whenStarting(entity -> this.entityData.set(IS_ANGRY, false))));
    }

    @Override
    public boolean isWithinMeleeAttackRange(LivingEntity p_217067_) {
        double d0 = this.getPerceivedTargetDistanceSquareForMeleeAttack(p_217067_);
        return d0 <= this.getMeleeAttackRangeSqr(p_217067_)*1.5;
    }

    @Override
    public BrainActivityGroup<Pitdrone> getFightTasks() {
        return BrainActivityGroup.fightTasks(
                new InvalidateAttackTarget<>(),
                new Retaliate<>(),
                new CustomBehaviour<>(entity -> this.entityData.set(IS_ANGRY, true)).startCondition(entity -> !this.isangry()),
                new CustomBehaviour<>(entity -> BrainUtils.clearMemory(this, MemoryModuleType.WALK_TARGET)).startCondition(entity -> this.random.nextFloat() < 0.003f),
                new OneRandomBehaviour<>(
                        new SetRandomWalkTarget<>().setRadius(4, 3).startCondition(entity -> this.getxround() > 2),
                        new SetRandomWalkTarget<>().setRadius(8, 4).startCondition(entity -> this.getxround() > 4),
                        new SetWalkTargetToAttackTarget<Pitdrone>().speedMod(1.4f),
                        new SetWalkTargetToAttackTarget<Pitdrone>().speedMod(1.2f),
                        new SetWalkTargetToRandomSpotAroundAttackTarget<>()
                ),
                new FirstApplicableBehaviour<>(
                        new OneRandomBehaviour<>(
                new ConfigurableAnimatableMeleeAttack<Pitdrone>(12, 0, 1, 1, null, 0, null, this.getBigAttackSound())
                        .whenStarting(entity -> triggerAnim("onetime", "bigattack"))
                        .cooldownFor(entity -> random.nextInt(10, 15)),
                new DoubleMeleeAttack<Pitdrone>(20, 10, 0, 1, 1, null , 0, null, this.getDoubleAttackSound())
                        .whenStarting(entity ->triggerAnim("onetime", "doubleattack"))
                        .cooldownFor(entity -> random.nextInt(10, 15))
                        ),
                    new FirstApplicableBehaviour<>(
                            new StopAndReload<Pitdrone>(14, 8, null).cooldownFor(entity -> 100)
                            .whenStarting(entity -> triggerAnim("onetime", "reload")).startCondition(entity -> this.getammo() == 0 && this.canreload()),
                            new StopAndShoot<Pitdrone>(14, 8, 1f)
                                    .whenStarting(entity -> triggerAnim("onetime", "shoot")).startCondition(entity -> this.getammo() > 0)
                    ).cooldownFor(entity -> random.nextInt(0, 9) == 0 ? 50*random.nextInt(1, 8) : 10)
                )
        );

    }




    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(new AnimationController<>(this, "allways", 0, this::headpredicate));
        controllerRegistrar.add(new AnimationController<>(this, "allways2", 0, this::tailpredicate));
        controllerRegistrar.add(new AnimationController<>(this, "mainloop", 0, this::mainpredicate));
        controllerRegistrar.add(new AnimationController<>(this, "onetime", state -> PlayState.STOP)
                .triggerableAnim("doubleattack", RawAnimation.begin().then("animation.pitdrone.attacktwice", Animation.LoopType.PLAY_ONCE))
                .triggerableAnim("shoot", RawAnimation.begin().then("animation.pitdrone.shoot", Animation.LoopType.PLAY_ONCE))
                .triggerableAnim("bigattack", RawAnimation.begin().then("animation.pitdrone.attackbig", Animation.LoopType.PLAY_ONCE))
                .triggerableAnim("reload", RawAnimation.begin().then("animation.pitdrone.reload", Animation.LoopType.PLAY_ONCE))
        );

    }

    private <T extends GeoAnimatable> PlayState headpredicate(AnimationState<T> tAnimationState) {
        tAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.pitdrone.mouth", Animation.LoopType.LOOP));
        return PlayState.CONTINUE;
    }



    private <T extends GeoAnimatable> PlayState tailpredicate(AnimationState<T> tAnimationState) {
        tAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.pitdrone.tail", Animation.LoopType.LOOP));
        return PlayState.CONTINUE;
    }


    private <T extends GeoAnimatable> PlayState mainpredicate(AnimationState<T> tAnimationState) {

        if(tAnimationState.isMoving()) {
            tAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.pitdrone.run", Animation.LoopType.LOOP));
            return PlayState.CONTINUE;
        }


        tAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.pitdrone.idle", Animation.LoopType.LOOP));
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
        this.setammo(this.getammo()-1);
        this.playSound(getSpitSound(), this.getSoundVolume(), this.getVoicePitch());


        PitdroneSpike acidBall = new PitdroneSpike(this.level, this, d1, d2, d3); //this.getRandom().triangle(d1, 2.297D * d4)
        acidBall.setPos(this.getX() - (double)(this.getBbWidth() + 1.0F) * 0.5D * (double)Mth.sin(this.yBodyRot * ((float)Math.PI / 180F)), this.getEyeY() - (double)0.1F, this.getZ() + (double)(this.getBbWidth() + 1.0F) * 0.5D * (double) Mth.cos(this.yBodyRot * ((float)Math.PI / 180F)));
        this.level.addFreshEntity(acidBall);

    }





    @Nullable
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor p_33601_, DifficultyInstance p_33602_, MobSpawnType p_33603_, @Nullable SpawnGroupData p_33604_, @Nullable CompoundTag p_33605_) {
        RandomSource randomsource = p_33601_.getRandom();
        int i = randomsource.nextInt(7);
        float f = randomsource.nextFloat();
        this.setcanreload(!(f < 0.1f));
        this.setammo(i);
        return super.finalizeSpawn(p_33601_, p_33602_, p_33603_, p_33604_, p_33605_);
    }

    @Override
    public void performReloadAction() {
        this.setammo(6);
    }










    public SoundEvent getSpitSound() {
        return this.random.nextInt(2) == 1 ?  ModSounds.PITDRONE_SPIKE1.get() : ModSounds.PITDRONE_SPIKE2.get();
    }
    public SoundEvent getDoubleAttackSound() {
       return ModSounds.PITDRONE_MELEEATTACK2.get();
    }
    public SoundEvent getBigAttackSound() {
        return ModSounds.PITDRONE_MELEEATTACK1.get();
    }

    protected SoundEvent getDeathSound() {
        switch (this.random.nextInt(1,4)) {
            case 1:  return ModSounds.PITDRONE_DIE1.get();
            case 2:  return ModSounds.PITDRONE_DIE2.get();
            case 3:  return ModSounds.PITDRONE_DIE3.get();
        }
        return ModSounds.HEADCRAB_1_DIE_1.get();
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource pDamageSource) {
        switch (this.random.nextInt(1,5)) {
            case 1:  return ModSounds.PITDRONE_PAIN1.get();
            case 2:  return ModSounds.PITDRONE_PAIN2.get();
            case 3:  return ModSounds.PITDRONE_PAIN3.get();
            case 4:  return ModSounds.PITDRONE_PAIN4.get();
        }
        return ModSounds.HEADCRAB_1_DIE_1.get();
    }


    protected SoundEvent getMetalStepsound() {
        return ModSounds.PITDRONE_METAL_STEP.get();
    }

    @Override
    protected void playStepSound(BlockPos pPos, BlockState pState) {

        if (!pState.getMaterial().equals(Material.METAL)) {
        super.playStepSound(pPos, pState);
        return;
        }


        if (this.tickCount%2 == 0) {
            BlockState blockstate = this.level.getBlockState(pPos.above());
            boolean flag = blockstate.is(BlockTags.INSIDE_STEP_SOUND_BLOCKS);
            if (flag || !pState.getMaterial().isLiquid()) {
                this.playSound(getMetalStepsound(), this.getSoundVolume(), this.getVoicePitch());
            }
        }
    }






    protected SoundEvent getAmbientSound() {

        int i = this.random.nextInt(1,4);
        int j = this.random.nextInt(4, 8);
        int k = this.random.nextInt(8,11);
        int f = HLperUtil.getrandomof(i, j, k, this.getxround() > 2, this.isangry());


        switch (f) {
            case 1:  return ModSounds.PITDRONE_IDLE1.get();
            case 2:  return ModSounds.PITDRONE_IDLE2.get();
            case 3:  return ModSounds.PITDRONE_IDLE3.get();
            case 4:  return ModSounds.PITDRONE_TALK1.get();
            case 5:  return ModSounds.PITDRONE_TALK2.get();
            case 6:  return ModSounds.PITDRONE_TALK3.get();
            case 7:  return ModSounds.PITDRONE_TALK4.get();
            case 8:  return ModSounds.PITDRONE_IDLE4.get();
            case 9:  return ModSounds.PITDRONE_IDLE5.get();
            case 10:  return ModSounds.PITDRONE_IDLE6.get();
        }
        return SoundEvents.FROG_STEP;
    }




















}





