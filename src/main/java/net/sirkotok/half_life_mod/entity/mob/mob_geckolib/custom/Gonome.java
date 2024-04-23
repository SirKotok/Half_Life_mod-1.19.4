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
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.Vec3;
import net.sirkotok.half_life_mod.entity.base.HalfLifeMonster;
import net.sirkotok.half_life_mod.entity.brain.behaviour.*;
import net.sirkotok.half_life_mod.entity.projectile.AcidBall;
import net.sirkotok.half_life_mod.misc.gamerules.HalfLifeGameRules;
import net.sirkotok.half_life_mod.misc.util.CommonSounds;
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
import net.tslat.smartbrainlib.api.core.sensor.vanilla.HurtBySensor;
import net.tslat.smartbrainlib.api.core.sensor.vanilla.NearbyLivingEntitySensor;
import net.tslat.smartbrainlib.api.core.sensor.vanilla.NearbyPlayersSensor;
import net.tslat.smartbrainlib.util.BrainUtils;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

import javax.annotation.Nullable;
import java.util.List;


public class Gonome extends HalfLifeMonster implements RangedAttackMob, GeoEntity, SmartBrainOwner<Gonome> {

    @Override
    protected float getSoundVolume() {
        return 1.1f;
    }

    private AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);




    public static final EntityDataAccessor<Boolean> IS_ANGRY = SynchedEntityData.defineId(Gonome.class, EntityDataSerializers.BOOLEAN);
    public static final EntityDataAccessor<Boolean> IS_CLOSE = SynchedEntityData.defineId(Gonome.class, EntityDataSerializers.BOOLEAN);



    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(IS_CLOSE, false);
        this.entityData.define(IS_ANGRY, false);
    }


    protected void checkdistance() {
        if (!isangry()) return;
        setisclose(!(HLperUtil.DistanceToTarget(this) > 8));
    }


    protected boolean isclose() {
        return this.entityData.get(IS_CLOSE);
    }
    protected void setisclose(boolean b) {
        this.entityData.set(IS_CLOSE, b);
    }



    protected boolean isangry() {
        return this.entityData.get(IS_ANGRY);
    }
    protected void setangry(boolean b) {
        this.entityData.set(IS_ANGRY, b);
    }

    public Gonome(EntityType type, Level level) {
        super(type, level);
        this.xpReward = 15;
         this.setPathfindingMalus(BlockPathTypes.DAMAGE_FIRE, -1F);
        this.setPathfindingMalus(BlockPathTypes.DANGER_FIRE, -1F);
        this.setPathfindingMalus(BlockPathTypes.WATER, 0.0F);
        this.setPathfindingMalus(BlockPathTypes.WATER_BORDER, 0.0F);
    }


    public static AttributeSupplier setAttributes() {
        return Monster.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 45D)
                .add(Attributes.ATTACK_DAMAGE, 5f)
                .add(Attributes.ATTACK_SPEED, 1.0f)
                .add(Attributes.ATTACK_KNOCKBACK, 1.2f).add(Attributes.KNOCKBACK_RESISTANCE, 0.25f)
                .add(Attributes.ARMOR, 6f)
                .add(Attributes.MOVEMENT_SPEED, 0.22f).build();
    }


    protected SoundEvent getAttackGrowlSound() {
        return HalfLifeSounds.GONOME_MELEE2.get();
    }


    protected SoundEvent getAttackSound() {
        return HalfLifeSounds.GONOME_MELEE1.get();
    }



    protected SoundEvent getHurtSound(DamageSource p_33034_) {
        switch (this.random.nextInt(1,5)) {
            case 1:  return HalfLifeSounds.GONOME_PAIN1.get();
            case 2:  return HalfLifeSounds.GONOME_PAIN2.get();
            case 3:  return HalfLifeSounds.GONOME_PAIN3.get();
            case 4:  return HalfLifeSounds.GONOME_PAIN4.get();
        }
        return HalfLifeSounds.HEADCRAB_1_PAIN_1.get();
    }

    protected SoundEvent getDeathSound() {
        switch (this.random.nextInt(1,4)) {
            case 1:  return HalfLifeSounds.GONOME_DEATH2.get();
            case 2:  return HalfLifeSounds.GONOME_DEATH4.get();
            case 3: return HalfLifeSounds.GONOME_DEATH3.get();
        }
        return HalfLifeSounds.HEADCRAB_1_DIE_1.get();
    }


    protected SoundEvent getAmbientSound() {

        switch (this.random.nextInt(1,4)) {
            case 1:  return HalfLifeSounds.GONOME_IDLE1.get();
            case 2:  return HalfLifeSounds.GONOME_IDLE2.get();
            case 3:  return HalfLifeSounds.GONOME_IDLE3.get();
        }
        return HalfLifeSounds.HEADCRAB_1_ALERT_1.get();
    }





    @Override
    protected Brain.Provider<?> brainProvider() {
        return new SmartBrainProvider<>(this);
    }


    @Override
    public void aiStep() {
        super.aiStep();
        if (this.isangry() && !this.isclose() && this.tickCount % 10 == 0) this.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 11, 1, false, false, false));

    }

    @Override
    protected void customServerAiStep() {
        if (!this.level.getGameRules().getRule(HalfLifeGameRules.HALF_LIFE_AI_IS_ON).get()) return;
        tickBrain(this);
    }



    @Override
    public void tick() {
       super.tick();
   //    if ((this.tickCount % 200 == 0) && (this.getDeltaMovement().length() < 0.2f)) this.checkdistance();

    }

    @Override
    public List<ExtendedSensor<Gonome>> getSensors() {
        return ObjectArrayList.of(
                new HurtBySensor<>(),
                new NearbyPlayersSensor<>(),
                new NearbyLivingEntitySensor<Gonome>()
                        .setPredicate((target, entity) ->
                            InfightingUtil.nonfactionSpecific(target) || InfightingUtil.HeadcrabFactionSpecific(target)));
    }



    @Override
    public BrainActivityGroup<Gonome> getCoreTasks() {
        return BrainActivityGroup.coreTasks(
                new LookAtTarget<>(),
                new MoveToWalkTarget<>());
    }




    @Override
    public BrainActivityGroup<Gonome> getIdleTasks() {
        return BrainActivityGroup.idleTasks(
                new FirstApplicableBehaviour<Gonome>(
                       new TargetOrRetaliateHLT<>(),
                        new SetPlayerLookTarget<>(),
                        new SetRandomLookTarget<>()),
                    new CustomBehaviour<>(entity -> this.setangry(false)).startCondition(entity -> this.isangry()),
                new OneRandomBehaviour<>(
                        new SetRandomWalkTarget<>(),
                        new Idle<>().runFor(entity -> entity.getRandom().nextInt(30, 120))));

    }


    @Override
    public BrainActivityGroup<Gonome> getFightTasks() {
        return BrainActivityGroup.fightTasks(
                new InvalidateAttackTarget<>(),
                new Retaliate<>(),
                new CustomBehaviour<>(entity -> this.setangry(false)).startCondition(entity -> this.isangry()),
                new SetWalkTargetToAttackTarget<Gonome>().speedMod(1.95f).whenStarting(entity -> this.entityData.set(IS_ANGRY, true)),
                new FirstApplicableBehaviour<>(
                new OneRandomBehaviour<>(
                new DoubleMeleeAttack<Gonome>(19, 12, 0, 1.8f, 1, null , 0, CommonSounds.getClawHitSound(), this.getAttackSound(), CommonSounds.getClawMissSound(), null, null)
                                .whenStarting(entity -> attackcheck("onetime", "double"))
                                .cooldownFor(entity -> random.nextInt(15, 25)),
                new MeleeAttackOverTime<Gonome>(60, 0, 5, 50, 10, 0, 1, 0.3f,
                        null, 0, null, this.getAttackGrowlSound(), CommonSounds.getClawHitSound(), CommonSounds.getClawMissSound())
                                .whenStarting(entity -> attackcheck("onetime", "big"))
                                .cooldownFor(entity -> random.nextInt(10, 25))),
                new StopAndShoot<Gonome>(28, 12, 2f, HalfLifeSounds.GONOME_JUMPATTACK.get()).cooldownFor(entity -> random.nextInt(20, 40))
                                .whenStarting(entity -> triggerAnim("onetime", "shoot"))
                                .cooldownFor(entity -> (int) (random.nextInt(40, 250)/Math.max(1, ((int) Math.sqrt(HLperUtil.DistanceToTarget(this)*2)))))
                )

        );



    }



    protected SoundEvent getStepSound() {
        return HalfLifeSounds.GONOME_RUN.get();
    }

    @Override
    protected void playStepSound(BlockPos pPos, BlockState pState) {
        if (this.tickCount%3 == 0) {
            BlockState blockstate = this.level.getBlockState(pPos.above());
            boolean flag = blockstate.is(BlockTags.INSIDE_STEP_SOUND_BLOCKS);
            if (flag || !pState.getMaterial().isLiquid()) {
                this.playSound(getStepSound());
            }
        }
    }




    public void attackcheck(String s, String s1) {
        triggerAnim(s,  s1);
        checkdistance();
    }




    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(new AnimationController<>(this, "headcrab", 0, this::headpredicate));
        controllerRegistrar.add(new AnimationController<>(this, "hands", 0, this::handpredicate));
        controllerRegistrar.add(new AnimationController<>(this, "mouth", 0, this::mouthpredicate));
        controllerRegistrar.add(new AnimationController<>(this, "main_loop", 0, this::predicate));
        controllerRegistrar.add(new AnimationController<>(this, "onetime", state -> PlayState.STOP)
                .triggerableAnim("shoot", RawAnimation.begin().then("animation.gonome.shoot", Animation.LoopType.PLAY_ONCE)) // 28 - 40
                .triggerableAnim("double", RawAnimation.begin().then("animation.gonome.meleetwo", Animation.LoopType.PLAY_ONCE))//12 - 19 - 22
                .triggerableAnim("big", RawAnimation.begin().then("animation.gonome.meleebig", Animation.LoopType.PLAY_ONCE)) // 0.25 - 2.5 - 3

        );



    }

    private <T extends GeoAnimatable> PlayState headpredicate(AnimationState<T> tAnimationState) {
        tAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.gonome.headcrab", Animation.LoopType.LOOP));
        return PlayState.CONTINUE;
    }
    private <T extends GeoAnimatable> PlayState handpredicate(AnimationState<T> tAnimationState) {
        tAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.gonome.fingers", Animation.LoopType.LOOP));
        return PlayState.CONTINUE;
    }
    private <T extends GeoAnimatable> PlayState mouthpredicate(AnimationState<T> tAnimationState) {
        tAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.gonome.mouth", Animation.LoopType.LOOP));
        return PlayState.CONTINUE;
    }


    private <T extends GeoAnimatable> PlayState predicate(AnimationState<T> tAnimationState) {

        if(tAnimationState.isMoving()) {
         if (!this.isangry()) {
            tAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.gonome.walk", Animation.LoopType.LOOP));
            return PlayState.CONTINUE;
        }
         if (this.isclose()) {
            tAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.gonome.runclose", Animation.LoopType.LOOP));
            return PlayState.CONTINUE;
         }
            tAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.gonome.runfar", Animation.LoopType.LOOP));
            return PlayState.CONTINUE;
        }


        tAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.gonome.idle", Animation.LoopType.LOOP));

        return PlayState.CONTINUE;

    }


    private float length(){
        return this.getBbWidth()/2+1f;
    }

    private Vec3 rotvec(int angledegree){
        float i = length();
        double yrot = (this.yBodyRot+angledegree)/180*Math.PI;
        double d1 = Math.sin(yrot);
        double d2 = Math.cos(yrot);
        return new Vec3((float)this.getX()-i*d1, this.getY()+1.67f, (float)this.getZ()+i*d2);
    }



    @Override
    public void performRangedAttack(LivingEntity pTarget, float p_33318_) {
        Vec3 attackpos = this.rotvec(38);
        double d1 = pTarget.getX() - attackpos.x();
        double d2 = pTarget.getY(0.4D) - attackpos.y();
        double d3 = pTarget.getZ() - attackpos.z();
        this.checkdistance();
        AcidBall acidBall = new AcidBall(this.level, this, d1, d2, d3);
        acidBall.setPos(attackpos);
        this.level.addFreshEntity(acidBall);

    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }


}





