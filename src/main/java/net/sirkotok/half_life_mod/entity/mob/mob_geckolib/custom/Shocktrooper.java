package net.sirkotok.half_life_mod.entity.mob.mob_geckolib.custom;


import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.behavior.SetWalkTargetAwayFrom;
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
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.ForgeEventFactory;
import net.sirkotok.half_life_mod.entity.HalfLifeEntities;
import net.sirkotok.half_life_mod.entity.base.HalfLifeMonster;
import net.sirkotok.half_life_mod.entity.base.HalfLifeNeutral;
import net.sirkotok.half_life_mod.entity.brain.behaviour.*;
import net.sirkotok.half_life_mod.entity.brain.sensor.SmellSensor;
import net.sirkotok.half_life_mod.entity.mob.modinterface.DoubleRangedMob;
import net.sirkotok.half_life_mod.entity.mob.modinterface.HasLeaderMob;
import net.sirkotok.half_life_mod.entity.projectile.ShockProjectile;
import net.sirkotok.half_life_mod.misc.gamerules.HalfLifeGameRules;
import net.sirkotok.half_life_mod.misc.util.CommonSounds;
import net.sirkotok.half_life_mod.misc.util.InfightingUtil;
import net.sirkotok.half_life_mod.sound.HalfLifeSounds;
import net.sirkotok.half_life_mod.misc.util.HLperUtil;
import net.sirkotok.half_life_mod.misc.util.HLTags;
import net.tslat.smartbrainlib.api.SmartBrainOwner;
import net.tslat.smartbrainlib.api.core.BrainActivityGroup;
import net.tslat.smartbrainlib.api.core.SmartBrainProvider;
import net.tslat.smartbrainlib.api.core.behaviour.FirstApplicableBehaviour;
import net.tslat.smartbrainlib.api.core.behaviour.OneRandomBehaviour;
import net.tslat.smartbrainlib.api.core.behaviour.custom.look.LookAtTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.misc.CustomBehaviour;
import net.tslat.smartbrainlib.api.core.behaviour.custom.misc.Idle;
import net.tslat.smartbrainlib.api.core.behaviour.custom.move.FleeTarget;
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
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class Shocktrooper extends HalfLifeMonster implements RangedAttackMob, DoubleRangedMob, GeoEntity, SmartBrainOwner<Shocktrooper> {

    @Override
    protected float getSoundVolume() {
        return 0.5f;
    }




    @Override
    public void die(DamageSource pDamageSource) {
        super.die(pDamageSource);
        if (!level.isClientSide()) {
            ServerLevel serverLevel = (ServerLevel) level;
                Shockroach summon = HalfLifeEntities.SHOCKROACH.get().create(serverLevel);
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
    public static final EntityDataAccessor<Integer> ORDER_ID = SynchedEntityData.defineId(Shocktrooper.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Boolean> CAN_ATTACK = SynchedEntityData.defineId(Shocktrooper.class, EntityDataSerializers.BOOLEAN);








    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(CAN_ATTACK, true);
        this.entityData.define(IS_SITTING, false);
        this.entityData.define(IS_ANGRY, false);
        this.entityData.define(ORDER_ID, 1);
    }




    public int getorder(){
        return this.entityData.get(ORDER_ID);
    }

    public void setorder(int order){
        this.entityData.set(ORDER_ID, order);
    }

    public boolean getcanattack() {
        return this.getorder() == 1;
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
                .add(Attributes.MAX_HEALTH, 55D)
                .add(Attributes.ATTACK_DAMAGE, 10f)
                .add(Attributes.ATTACK_SPEED, 1.0f)
                .add(Attributes.ATTACK_KNOCKBACK, 1f)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.25f)
                .add(Attributes.ARMOR, 8f)
                .add(Attributes.ARMOR_TOUGHNESS, 5f)
                .add(Attributes.MOVEMENT_SPEED, 0.34f).build();
    }





    @Override
    protected Brain.Provider<?> brainProvider() {
        return new SmartBrainProvider<>(this);
    }

    // ORDERS: 1 = stay; 2 = change position;

    int hit = 0;
    int track = 200;

    public void changeorder(){
        if (track < 200) return;
        if (random.nextFloat() < 0.1f) return;
        if (this.getorder() != 1) this.setorder(1); else setorder(0);
        this.hit = 0;
        track = 0;
    }


    @Override
    public boolean hurt(DamageSource pSource, float pAmount) {
        if (!this.getcanattack()) this.changeorder();
        return super.hurt(pSource, pAmount);
    }

    @Override
    public void aiStep() {
        super.aiStep();
        if (BrainUtils.getTargetOfEntity(this) != null) {
        if (!this.getcanattack() && !this.level.isClientSide() && this.tickCount % (70) == 0) {
            ServerLevel pLevel = (ServerLevel) this.level;
            BlockPos pBlockPos = this.blockPosition();
            int rad = 15;
            List<Mob> myfaction = EntityRetrievalUtil.getEntities((Level) pLevel,
                    new AABB(pBlockPos.getX() - rad, pBlockPos.getY() - rad, pBlockPos.getZ() - rad,
                            pBlockPos.getX() + rad, pBlockPos.getY() + rad, pBlockPos.getZ() + rad), obj -> obj instanceof Shocktrooper trooper && trooper.getcanattack() && !obj.equals(this));
            int size = myfaction.size();
            this.hit+=(14-Math.min(size, 13))+1;
            if (this.random.nextInt(100) < Math.min(hit, 80)) this.changeorder();
        }
        if (!this.level.isClientSide() && this.getcanattack() && this.tickCount % (150+random.nextInt(200)) == 0) {
        ServerLevel pLevel = (ServerLevel) this.level;
        BlockPos pBlockPos = this.blockPosition();
        int rad = 15;
        List<Mob> myfaction = EntityRetrievalUtil.getEntities((Level) pLevel,
                new AABB(pBlockPos.getX() - rad, pBlockPos.getY() - rad, pBlockPos.getZ() - rad,
                        pBlockPos.getX() + rad, pBlockPos.getY() + rad, pBlockPos.getZ() + rad), obj -> obj instanceof Shocktrooper trooper && trooper.getcanattack() && !obj.equals(this));

        int size = myfaction.size();
        this.hit+=size+1;
        if (this.random.nextInt(100) < Math.min(hit, 70)) this.changeorder();
    }
    }
    }
    @Override
    public void tick() {
        track++;
        super.tick();
        if (this.tickCount % 50 == 0) {
            InfightingUtil.alertsameteam(this);
        }
    }



    @Override
    protected void customServerAiStep() {
        if (!this.level.getGameRules().getRule(HalfLifeGameRules.HALF_LIFE_AI_IS_ON).get()) return;
        tickBrain(this);
    }






    @Override
    public List<ExtendedSensor<Shocktrooper>> getSensors() {
        return ObjectArrayList.of(
                new HurtBySensor<>(),
                new NearbyPlayersSensor<>(),
                new NearbyLivingEntitySensor<Shocktrooper>()
                        .setPredicate((target, entity) ->
                                InfightingUtil.commonenemy(target) || InfightingUtil.RaceXSpecific(target)
                            ));
    }



    @Override
    public BrainActivityGroup<Shocktrooper> getCoreTasks() {
        return BrainActivityGroup.coreTasks(
                new LookAtTarget<>(),
                new MoveToWalkTarget<>(),
                new CustomBehaviour<>(entity -> setorder(random.nextInt(2))).cooldownFor(entity -> random.nextInt(300, 1000)));
    }




    @Override
    public BrainActivityGroup<Shocktrooper> getIdleTasks() {
        return BrainActivityGroup.idleTasks(
                new FirstApplicableBehaviour<Shocktrooper>(
                        new FirstApplicableBehaviour<>(
                                new CustomBehaviour<>(entity -> setIsSitting(random.nextBoolean())).startCondition(entity -> !getIssitting() && getorder() == 1),
                                new CustomBehaviour<>(entity -> setIsSitting(false)).startCondition(entity -> getIssitting())
                        ).cooldownFor(entity -> 200+random.nextInt(200)),
                        new TargetOrRetaliateHLT<>(),
                        new SetPlayerLookTarget<>(),
                        new CustomBehaviour<>(entity -> this.entityData.set(IS_ANGRY, false)).startCondition(entity -> this.isangry()),
                        new SetRandomLookTarget<>()),
                new OneRandomBehaviour<>(
                        new SetRandomWalkTarget<>(),
                        new Idle<>().runFor(entity -> entity.getRandom().nextInt(10, 20)),
                        new Idle<>().runFor(entity -> entity.getRandom().nextInt(10, 90))
                ));
    }



    int shotdelay = 0;


    @Override
    public BrainActivityGroup<Shocktrooper> getFightTasks() {
        return BrainActivityGroup.fightTasks(
                new InvalidateAttackTarget<>(),
                new Retaliate<>(),
                new SetWalkTargetToRandomSpotAroundAttackTarget<>().startCondition(entity -> distanceTo(HLperUtil.TargetOrThis(this)) > 15),
                new SetWalkTargetToRandomSpotAwayFromAttackTarget<>().startCondition(entity -> distanceTo(HLperUtil.TargetOrThis(this)) < 4),
                new CustomBehaviour<>(entity -> this.entityData.set(IS_ANGRY, true)).whenStarting(entity -> CommonSounds.PlaySoundAsOwn(this, this.getSpotSound(HLperUtil.TargetOrThis(this) instanceof Player))).startCondition(entity -> !this.isangry()),
                new FirstApplicableBehaviour<>(
                new CustomBehaviour<>(entity -> setIsSitting(random.nextBoolean())).startCondition(entity -> !getIssitting() && getorder() == 1),
                new CustomBehaviour<>(entity -> setIsSitting(false)).startCondition(entity -> getIssitting())
                ).cooldownFor(entity -> 200),
                new FirstApplicableBehaviour<Shocktrooper>(
                        new SetWalkTargetToRandomSpotAroundAttackTarget<>().startCondition(entity -> distanceTo(HLperUtil.TargetOrThis(this)) > 10).whenStarting(entity -> CommonSounds.PlaySoundAsOwn(this, HalfLifeSounds.SHOCKTROOPER_CHARGE.get())),
                        new SetWalkTargetToRandomSpotAwayFromAttackTarget<>().startCondition(entity -> distanceTo(HLperUtil.TargetOrThis(this)) < 8),
                        new SetRandomWalkTarget<>()
                ).startCondition(entity -> !entity.getcanattack()),
                new FirstApplicableBehaviour<>(
                new DoubleMeleeAttack<Shocktrooper>(10, 6, 0.1f, 1, 1, null , 0, null, this.getDoubleAttackSound())
                        .whenStarting(entity ->triggerAnim("onetime", "doubleattack"))
                        .cooldownFor(entity -> random.nextInt(10, 15)),
                new StopAndShoot<Shocktrooper>(3, 3, 1.6f).startCondition(entity -> this.getcanattack())
                        .whenStarting(entity -> triggerAnim("onetime", "shoot"))
                        .cooldownFor(entity -> this.shotdelay % 3 == 0 ? 14 : 3)
                )
        );

    }


    @Override
    public double getMeleeAttackRangeSqr(LivingEntity pEntity) {
        return (double)(this.getBbWidth() * 2.5F * this.getBbWidth() * 2.5F + pEntity.getBbWidth());
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
                .triggerableAnim("spore", RawAnimation.begin().then("animation.shocktrooper.spit", Animation.LoopType.PLAY_ONCE)));
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



    private float length(){
        return this.getBbWidth()/2+0.5f;
    }

    private Vec3 rotvec(int angledegree){
        float i = length();
        double yrot = (this.yBodyRot+angledegree)/180*Math.PI;
        double d1 = Math.sin(yrot);
        double d2 = Math.cos(yrot);
        return new Vec3((float)this.getX()-i*d1, this.getY()+(this.getIssitting() ? 1.20 : 1.35f), (float)this.getZ()+i*d2);
    }

    @Override
    public void performSecondRangedAttack(LivingEntity livingentity, float p_33318_) {
    }

        @Override
    public void performRangedAttack(LivingEntity pTarget, float p_33318_) {

        Vec3 attackpos = this.rotvec(36);
        double d1 = pTarget.getX() - attackpos.x();
        double d2 = pTarget.getY(0.4D) - attackpos.y();
        double d3 = pTarget.getZ() - attackpos.z();
       shotdelay++;

        this.playSound(getShootSound(), this.getSoundVolume(), this.getVoicePitch());


       ShockProjectile acidBall = new ShockProjectile(this.level, this, d1, d2, d3);
        acidBall.setPos(attackpos);
        acidBall.shoot(d1, d2, d3, p_33318_, 1f);
        this.level.addFreshEntity(acidBall);

    }


    @Override
    public void playAmbientSound() {
        super.playAmbientSound();
    }

    public SoundEvent getShootSound() {
        return HalfLifeSounds.SHOCK_FIRE.get();
    }
    public SoundEvent getDoubleAttackSound() {
       return HalfLifeSounds.SHOCKTROOPER_ATTACK.get();
    }


    protected SoundEvent getDeathSound() {
        switch (this.random.nextInt(1,5)) {
            case 1:  return HalfLifeSounds.SHOCKTROOPER_DIE1.get();
            case 2:  return HalfLifeSounds.SHOCKTROOPER_DIE2.get();
            case 3:  return HalfLifeSounds.SHOCKTROOPER_DIE3.get();
            case 4:  return HalfLifeSounds.SHOCKTROOPER_DIE4.get();
        }
        return HalfLifeSounds.HEADCRAB_1_DIE_1.get();
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource pDamageSource) {
        switch (this.random.nextInt(1,6)) {
            case 1:  return HalfLifeSounds.SHOCKTROOPER_PAIN1.get();
            case 2:  return HalfLifeSounds.SHOCKTROOPER_PAIN2.get();
            case 3:  return HalfLifeSounds.SHOCKTROOPER_PAIN3.get();
            case 4:  return HalfLifeSounds.SHOCKTROOPER_PAIN4.get();
            case 5:  return HalfLifeSounds.SHOCKTROOPER_PAIN5.get();
        }
        return HalfLifeSounds.HEADCRAB_1_DIE_1.get();
    }

    @Override
    public int getAmbientSoundInterval() {
        return 200;
    }

    protected SoundEvent getAmbientSound() {
        boolean a = this.isangry();
        int f = a ? random.nextInt(1, 6) : random.nextFloat() < 0.3 ? 6 : random.nextInt(1, 4);
        switch (f) {
            case 1:
                return HalfLifeSounds.SHOCKTROOPER_IDLE.get();
            case 2:
                return HalfLifeSounds.SHOCKTROOPER_IDLE_A.get();
            case 3:
                return HalfLifeSounds.SHOCKTROOPER_IDLE_Q.get();
            case 4:
                return HalfLifeSounds.SHOCKTROOPER_IDLE_CHECK_IN.get();
            case 5:
                return HalfLifeSounds.SHOCKTROOPER_IDLE_CLEAR.get();
            case 6:
                return HalfLifeSounds.SHOCKTROOPER_TAUNT.get();
        }
        return HalfLifeSounds.HEADCRAB_1_DIE_1.get();
    }

    protected SoundEvent getSpotSound(Boolean yes) {
        if (yes && random.nextFloat() < 0.8f) {
        int f = random.nextInt(1, 4);
        switch (f) {
            case 1:
                return HalfLifeSounds.SHOCKTROOPER_PL_SPOT1.get();
            case 2:
                return HalfLifeSounds.SHOCKTROOPER_PL_SPOT2.get();
            case 3:
                return HalfLifeSounds.SHOCKTROOPER_PL_SPOT3.get();
        }
        }
        return HalfLifeSounds.SHOCKTROOPER_ENEMY_SPOT.get();
    }


    }









