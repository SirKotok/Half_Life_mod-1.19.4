package net.sirkotok.half_life_mod.entity.mob.mob_geckolib.custom;


import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.sirkotok.half_life_mod.entity.mob.modinterface.HasLeaderMob;
import net.sirkotok.half_life_mod.misc.effect.HalfLifeEffects;
import net.sirkotok.half_life_mod.entity.base.HalfLifeMonster;
import net.sirkotok.half_life_mod.entity.brain.ModMemoryModuleType;
import net.sirkotok.half_life_mod.entity.brain.behaviour.*;
import net.sirkotok.half_life_mod.entity.brain.sensor.SmellSensor;
import net.sirkotok.half_life_mod.entity.projectile.AcidBall;
import net.sirkotok.half_life_mod.misc.gamerules.HalfLifeGameRules;
import net.sirkotok.half_life_mod.entity.particle.HalfLifeParticles;
import net.sirkotok.half_life_mod.sound.HalfLifeSounds;
import net.sirkotok.half_life_mod.misc.util.CommonSounds;
import net.sirkotok.half_life_mod.misc.util.HLTags;
import net.sirkotok.half_life_mod.misc.util.HLperUtil;
import net.sirkotok.half_life_mod.misc.util.InfightingUtil;
import net.tslat.smartbrainlib.api.SmartBrainOwner;
import net.tslat.smartbrainlib.api.core.BrainActivityGroup;
import net.tslat.smartbrainlib.api.core.SmartBrainProvider;
import net.tslat.smartbrainlib.api.core.behaviour.FirstApplicableBehaviour;
import net.tslat.smartbrainlib.api.core.behaviour.OneRandomBehaviour;
import net.tslat.smartbrainlib.api.core.behaviour.custom.look.LookAtTarget;
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
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

import javax.annotation.Nullable;
import java.util.List;


public class AntlionWorker extends HalfLifeMonster implements RangedAttackMob, HasLeaderMob<AntlionWorker>, GeoEntity, SmartBrainOwner<AntlionWorker> {

    @Override
    protected float getSoundVolume() {
        return 0.7f;
    }

    protected boolean relocate;


    private AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);




    public static final EntityDataAccessor<Boolean> IS_ANGRY = SynchedEntityData.defineId(AntlionWorker.class, EntityDataSerializers.BOOLEAN);
    public static final EntityDataAccessor<Integer> ID_TEXTURE = SynchedEntityData.defineId(AntlionWorker.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Integer> FLIP_OVER_TIMESTAMP = SynchedEntityData.defineId(Antlion.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Integer> VORTED = SynchedEntityData.defineId(Antlion.class, EntityDataSerializers.INT);


    protected void defineSynchedData() {
        super.defineSynchedData();

        this.entityData.define(IS_ANGRY, false);
        this.entityData.define(VORTED, 0);
        this.entityData.define(FLIP_OVER_TIMESTAMP, -50);
        this.entityData.define(ID_TEXTURE, 1);

    }

    public boolean isflippedover() {
        return (this.tickCount - this.entityData.get(FLIP_OVER_TIMESTAMP)) < 36;
    }
    public void flipover(int vort) {
        this.triggerAnim("onetime", "flip");
        this.entityData.set(FLIP_OVER_TIMESTAMP, this.tickCount);
        HLperUtil.slowEntityFor(this, 36);
        this.entityData.set(VORTED, vort);
    }

    public int getvort() {
        return this.entityData.get(VORTED);
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
    }

    public void readAdditionalSaveData(CompoundTag p_33607_) {
        this.settexture(p_33607_.getInt("Texture") + 1);
        super.readAdditionalSaveData(p_33607_);
    }




    public boolean isangry() {
        return this.entityData.get(IS_ANGRY);
    }

    public AntlionWorker(EntityType type, Level level) {
        super(type, level);
        this.setPathfindingMalus(BlockPathTypes.WATER, -1.0F);
        this.xpReward = 7;
    }


    public static AttributeSupplier setAttributes() {
        return Monster.createMobAttributes()
                    .add(Attributes.MAX_HEALTH,18D)
                .add(Attributes.ATTACK_DAMAGE, 5f)
                .add(Attributes.ATTACK_SPEED, 1.0f)
                .add(Attributes.MOVEMENT_SPEED, 0.30f).build();
    }

    protected SoundEvent getAttackGrowlSound() {
        switch (this.random.nextInt(1,7)) {
            case 1:  return HalfLifeSounds.ANTLION_ATTACK1.get();
            case 2:  return HalfLifeSounds.ANTLION_ATTACK2.get();
            case 3:  return HalfLifeSounds.ANTLION_ATTACK3.get();
            case 4:  return HalfLifeSounds.ANTLION_ATTACK4.get();
            case 5:  return HalfLifeSounds.ANTLION_ATTACK5.get();
            case 6:  return HalfLifeSounds.ANTLION_ATTACK6.get();
        }
        return HalfLifeSounds.HEADCRAB_1_HEADBITE.get();
    }


    protected SoundEvent getSpitSound() {
        switch (this.random.nextInt(1,4)) {
            case 1:  return HalfLifeSounds.ANTLION_SHOOT1.get();
            case 2:  return HalfLifeSounds.ANTLION_SHOOT2.get();
            case 3:  return HalfLifeSounds.ANTLION_SHOOT3.get();
        }
        return HalfLifeSounds.HEADCRAB_1_HEADBITE.get();
    }




    protected SoundEvent getHurtSound(DamageSource p_33034_) {
        switch (this.random.nextInt(1,3)) {
            case 1:  return HalfLifeSounds.ANT_PAIN1.get();
            case 2:  return HalfLifeSounds.ANT_PAIN2.get();
        }
        return HalfLifeSounds.HEADCRAB_1_PAIN_1.get();
    }

    protected SoundEvent getDeathSound() {
        switch (this.random.nextInt(1,3)) {
            case 1:  return HalfLifeSounds.ANTLION_BURST1.get();
            case 2:  return HalfLifeSounds.ANTLION_BURST2.get();
        }
        return HalfLifeSounds.HEADCRAB_1_PAIN_1.get();
    }


    protected SoundEvent getAmbientSound() {

        if (this.isangry() && random.nextFloat() < 0.01) return HalfLifeSounds.ANT_DISTRACT1.get();

        switch (this.random.nextInt(1,6)) {
            case 1:  return HalfLifeSounds.ANT_IDLE1.get();
            case 2:  return HalfLifeSounds.ANT_IDLE2.get();
            case 3:  return HalfLifeSounds.ANT_IDLE3.get();
            case 4:  return HalfLifeSounds.ANT_IDLE4.get();
            case 5:  return HalfLifeSounds.ANT_IDLE5.get();
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
        if ((!this.isInWaterOrBubble() || this.isOnGround()) && !this.isflippedover()) {
            tickBrain(this);}
    }




    @Nullable
    public LivingEntity leader;


    public void setLeader(@Nullable LivingEntity entity) {
        this.leader = entity;
    }

    @Override
    @Nullable
    public LivingEntity getLeader() {
        return this.leader;
    }

    @Override
    public void tick() {
        super.tick();

        if (this.isflippedover() && level.isClientSide()) {
            if (this.getvort() > 0 && this.tickCount % 2 == 0) {
                Vec3 startPos = new Vec3(this.getX(), this.getY()+0.6, this.getZ());
                this.level.addParticle(HalfLifeParticles.VORT_LIGHTNING.get(), startPos.x, startPos.y, startPos.z, this.getvort(), 0, 0);
            }
        }


        if (this.tickCount % 20 == 0 && this.getLeader() instanceof Player pl && (pl.isSpectator() || !pl.hasEffect(HalfLifeEffects.ANTLION_PHEROMONE_FRIEND.get()))) this.setLeader(this);
        if (this.tickCount % 50 == 0) this.relocate = this.random.nextFloat() < 0.05f;
        if (this.tickCount % 100 == 0 && this.shouldDiscardFriction() && this.isOnGround()) {
        this.setDiscardFriction(false); }


        if (this.tickCount % 100 == 0 && !this.level.isClientSide()) {
            ServerLevel pLevel = (ServerLevel) this.level;
            BlockPos pBlockPos = this.blockPosition();
            int rad = 15;
            List<Mob> race_x = EntityRetrievalUtil.getEntities((Level) pLevel,
                    new AABB(pBlockPos.getX() - rad, pBlockPos.getY() - rad, pBlockPos.getZ() - rad,
                            pBlockPos.getX() + rad, pBlockPos.getY() + rad, pBlockPos.getZ() + rad), obj -> obj.getType().is(HLTags.EntityTypes.FACTION_ANTLION));
            if (BrainUtils.getTargetOfEntity(this) != null) {
                for (Mob x : race_x) {
                    if (BrainUtils.getTargetOfEntity(x) == null) {
                        BrainUtils.setTargetOfEntity(x, BrainUtils.getTargetOfEntity(this));
                    }
                }
            }
        }

    }

    @Override
    public List<ExtendedSensor<AntlionWorker>> getSensors() {
        return ObjectArrayList.of(
                new SmellSensor<>(),
                new HurtBySensor<>(),
                new NearbyPlayersSensor<>(),
                new NearbyLivingEntitySensor<AntlionWorker>()
                        .setPredicate((target, entity)  ->
                                (InfightingUtil.AntlionSpecific(target) || InfightingUtil.commonenemy(target) || target.hasEffect(HalfLifeEffects.ANTLION_PHEROMONE_FOE.get())
                                ) && !target.hasEffect(HalfLifeEffects.ANTLION_PHEROMONE_FRIEND.get())));
    }



    @Override
    public BrainActivityGroup<AntlionWorker> getCoreTasks() {
        return BrainActivityGroup.coreTasks(
                new FollowLeaderImmideatlyBehaviour<AntlionWorker>().cooldownFor(entity -> 5),
                new SetJumpTargetToRandomSpotAroundLeader<AntlionWorker>(0).cooldownFor(entity -> 10),
                new LongJumpToJumpTarget<>(30, 1.5F, this.getFlySound()).whenStarting(entity -> triggerAnim("onetime", "fly"))
                        .startCondition(entity -> this.getLeader() != null && this.distanceTo(this.getLeader()) > 7)
                        .cooldownFor(entity -> random.nextInt(50, 300)),
                new LookAtTarget<>(),
                new MoveToWalkTarget<>());
    }


    protected SoundEvent getFlySound() { return HalfLifeSounds.ANT_FLY1.get();}

    @Override
    public BrainActivityGroup<AntlionWorker> getIdleTasks() {
        return BrainActivityGroup.idleTasks(
                new FirstApplicableBehaviour<AntlionWorker>(
                        new TargetOrRetaliateHLT<>().startCondition(entity -> (this.getLeader() == null || this.getLeader() == this) && BrainUtils.getMemory(this, ModMemoryModuleType.NOANTLIONATTACK.get()) == null),
                        new SetPlayerLookTarget<>(),
                        new SetRandomLookTarget<>()),
                new SetJumpTargetToRandom<>().radius(15, 10).cooldownFor(entity -> 10),
                new OneRandomBehaviour<>(
                        new LongJumpToJumpTarget<>(30, 1.5F,this.getFlySound() ).whenStarting(entity -> triggerAnim("onetime", "fly"))
                                .cooldownFor(entity -> random.nextInt(50, 600)).startCondition(entity -> this.tickCount > 100 && this.relocate),
                        new SetRandomWalkTarget<>(),
                        new Idle<>().runFor(entity -> entity.getRandom().nextInt(30, 90))
                                .whenStarting(entity -> this.entityData.set(IS_ANGRY, false))));
    }


    @Override
    public BrainActivityGroup<AntlionWorker> getFightTasks() {
        return BrainActivityGroup.fightTasks(
                new InvalidateAttackTarget<>().invalidateIf((entity, target) -> this.getLeader() != null && this.getLeader() != this),
                new InvalidateAttackTarget<>(),
                new InvalidateAttackTarget<>().invalidateIf((entity, target) -> target.hasEffect(HalfLifeEffects.ANTLION_PHEROMONE_FRIEND.get()) || (this.getLeader() != null && this.getLeader().equals(target))),
                new SetWalkTargetToRandomSpotAroundAttackTarget<>().radius(10, 5).speedMod(1.23f).startCondition(entity -> entity.distanceTo(HLperUtil.TargetOrThis(entity)) > 12 && (this.getLeader() == this || this.getLeader() == null)),
                new SetRandomWalkTarget<>().speedModifier(1.23f).cooldownFor(entity -> random.nextInt(80, 600)).startCondition(entity -> (this.getLeader() == this || this.getLeader() == null)),
                new Retaliate<>(),
                new SetJumpTargetToRandomSpotAroundAttackTarget<>(1).radius(10, 8).cooldownFor(entity -> 10),
                new LongJumpToJumpTarget<>(30, 1.5F, this.getFlySound()).whenStarting(entity -> triggerAnim("onetime", "fly"))
                        .startCondition(entity -> entity.distanceTo(HLperUtil.TargetOrThis(entity)) < 5 || !BrainUtils.canSee(entity, HLperUtil.TargetOrThis(entity)) || entity.distanceToSqr(HLperUtil.TargetOrThis(entity)) > 12 || this.relocate)
                        .cooldownFor(entity -> random.nextInt(50, 150)),
                new FirstApplicableBehaviour<>(
                new OneRandomBehaviour<>(
                new ConfigurableAnimatableMeleeAttack<AntlionWorker>(9, 0f, 1f, 1, null, 0, CommonSounds.getClawHitSound(), this.getAttackGrowlSound(), null, null, null)
                        .whenStarting(entity -> triggerAnim("onetime", "attack1"))
                        .cooldownFor(entity -> random.nextInt(10, 35)),
                new ConfigurableAnimatableMeleeAttack<AntlionWorker>(9, 0f, 1f, 1, null, 0, CommonSounds.getClawHitSound(), this.getAttackGrowlSound(), null, null, null)
                        .whenStarting(entity -> triggerAnim("onetime", "attack2"))
                        .cooldownFor(entity -> random.nextInt(10, 35))),
                new StopAndShoot<>(10, 5, 2f).startCondition(entity -> this.distanceTo(HLperUtil.TargetOrThis(this)) < 15)
                        .whenStarting(entity -> triggerAnim("onetime", "shoot")).cooldownFor(entity -> random.nextInt(30, 60)))
        );
    }




    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(new AnimationController<>(this, "allways", 0, this::headpredicate));
        controllerRegistrar.add(new AnimationController<>(this, "main_loop", 0, this::predicate));
        controllerRegistrar.add(new AnimationController<>(this, "onetime", state -> PlayState.STOP)
                .triggerableAnim("attack1", RawAnimation.begin().then("animation.antlion.attack1", Animation.LoopType.PLAY_ONCE))
                .triggerableAnim("attack2", RawAnimation.begin().then("animation.antlion.attack2", Animation.LoopType.PLAY_ONCE))
                .triggerableAnim("flip", RawAnimation.begin().then("animation.antlion.fallandstand", Animation.LoopType.PLAY_ONCE))
                .triggerableAnim("shoot", RawAnimation.begin().then("animation.antlion.shoot", Animation.LoopType.PLAY_ONCE))
                .triggerableAnim("fly", RawAnimation.begin().then("animation.antlion.fly", Animation.LoopType.PLAY_ONCE))

        );



    }


    private <T extends GeoAnimatable> PlayState headpredicate(AnimationState<T> tAnimationState) {
        tAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.antlion.mouth", Animation.LoopType.LOOP));
        return PlayState.CONTINUE;
    }
    private <T extends GeoAnimatable> PlayState predicate(AnimationState<T> tAnimationState) {


        if(tAnimationState.isMoving() && this.isOnGround()) {
            tAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.antlion.walk", Animation.LoopType.LOOP));
            return PlayState.CONTINUE;
        }


        if (this.isInWaterOrBubble()) {
            tAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.antlion.drown", Animation.LoopType.LOOP));
            return PlayState.CONTINUE;
        }


        tAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.antlion.idle", Animation.LoopType.LOOP));
        return PlayState.CONTINUE;

    }




    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }


    @Nullable
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor p_33601_, DifficultyInstance p_33602_, MobSpawnType p_33603_, @Nullable SpawnGroupData p_33604_, @Nullable CompoundTag p_33605_) {
        RandomSource randomsource = p_33601_.getRandom();
        int i = randomsource.nextInt(1, 5);
        this.settexture(i);
        return super.finalizeSpawn(p_33601_, p_33602_, p_33603_, p_33604_, p_33605_);
    }

    // TODO: Correct projectile
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






}





