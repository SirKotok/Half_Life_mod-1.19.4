package net.sirkotok.half_life_mod.entity.mob_geckolib.custom;


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
import net.sirkotok.half_life_mod.entity.HalfLifeEntities;
import net.sirkotok.half_life_mod.entity.base.HalfLifeMonster;
import net.sirkotok.half_life_mod.entity.base.HalfLifeNeutral;
import net.sirkotok.half_life_mod.entity.brain.behaviour.*;
import net.sirkotok.half_life_mod.entity.brain.sensor.SmellSensor;
import net.sirkotok.half_life_mod.entity.modinterface.DoubleRangedMob;
import net.sirkotok.half_life_mod.entity.modinterface.HasLeaderMob;
import net.sirkotok.half_life_mod.entity.projectile.ShockProjectile;
import net.sirkotok.half_life_mod.misc.gamerules.HalfLifeGameRules;
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


public class Shocktrooper extends HalfLifeMonster implements RangedAttackMob, DoubleRangedMob, HasLeaderMob<Shocktrooper>, GeoEntity, SmartBrainOwner<Shocktrooper> {
    //TODO: implement the spore launcher projectile thing so it works. Fix the position of the shooting from the shockroach.
    @Override
    protected float getSoundVolume() {
        return 0.5f;
    }



    public Shocktrooper leader;
    public List<Shocktrooper> shocktroopers;


    public Shocktrooper getLeader() {
       if (this.leader == null) return this;
       return this.leader;
       }



    public void setLeader(LivingEntity troop) {
        this.leader = (Shocktrooper) troop;
    }

    public List<Shocktrooper> getTroopers() {
        if (this.shocktroopers == null) return new ArrayList<>();
        return this.shocktroopers;
    }
    public void setShocktroopers(List<Shocktrooper> list) {
        this.shocktroopers = list;
    }

    public void addTroopertoTroopers(Shocktrooper troop) {
        if (this.shocktroopers == null) this.shocktroopers = new ArrayList<>();
        if (this.getTroopers().contains(troop)) return;
        this.shocktroopers.add(troop);
    }


    public void removeTrooperfromTroopers(Shocktrooper troop) {
        if (this.shocktroopers == null) this.shocktroopers = new ArrayList<>();
        if (!this.getTroopers().contains(troop)) return;
        this.shocktroopers.remove(troop);
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


    public static final EntityDataAccessor<Boolean> IS_LEADER = SynchedEntityData.defineId(Shocktrooper.class, EntityDataSerializers.BOOLEAN);
    public static final EntityDataAccessor<Boolean> IS_SITTING = SynchedEntityData.defineId(Shocktrooper.class, EntityDataSerializers.BOOLEAN);
    public static final EntityDataAccessor<Boolean> IS_ANGRY = SynchedEntityData.defineId(Shocktrooper.class, EntityDataSerializers.BOOLEAN);
    public static final EntityDataAccessor<Integer> RACE_X_AROUND = SynchedEntityData.defineId(Shocktrooper.class, EntityDataSerializers.INT);

    public static final EntityDataAccessor<Integer> ORDER_ID = SynchedEntityData.defineId(Shocktrooper.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Integer> CURRENT_ORDER_ID = SynchedEntityData.defineId(Shocktrooper.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Boolean> CAN_ATTACK = SynchedEntityData.defineId(Shocktrooper.class, EntityDataSerializers.BOOLEAN);
    // ORDER LIST:
    // 0 - stand ground
    // 1 - retreat
    // 2 - charge enemy
    // 3 - sit down
    // 4 - change position







    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(CAN_ATTACK, true);
        this.entityData.define(IS_LEADER, false);
        this.entityData.define(IS_SITTING, false);
        this.entityData.define(IS_ANGRY, false);
        this.entityData.define(RACE_X_AROUND, 0);
        this.entityData.define(ORDER_ID, 0);
        this.entityData.define(CURRENT_ORDER_ID, 0);
    }

    public void setxround(int emount) {
        this.entityData.set(RACE_X_AROUND, emount);
    }
    public int getxround() {
        return this.entityData.get(RACE_X_AROUND);
    }


    public int getorder(){
        return this.entityData.get(ORDER_ID);
    }
    public void setorder(int order){
        this.entityData.set(ORDER_ID, order);
    }
    public int getcurrentorder(){
        return this.entityData.get(CURRENT_ORDER_ID);
    }
    public void setcurrentorder(int order){
        this.entityData.set(CURRENT_ORDER_ID, order);
    }

    public boolean getcanattack() {
        return this.entityData.get(CAN_ATTACK);
    }
    public void setCanAttack(boolean b){
        this.entityData.set(CAN_ATTACK, b);
    }




    public boolean isleader() {
        return this.entityData.get(IS_LEADER);
    }
    protected void setthistoleader(boolean yes) {
        this.entityData.set(IS_LEADER, yes);
    }


    @Override
    public void tick() {
        super.tick();
        if (!this.level.isClientSide()) {
            if (RandomSource.create().nextFloat() < 0.002f) this.triggerAnim("blink", "blink");
            ServerLevel pLevel = (ServerLevel) this.level;
            BlockPos pBlockPos = this.blockPosition();
            if (this.tickCount % 100 == 0) {

                int rad = 20;
                List<Mob> race_x = EntityRetrievalUtil.getEntities((Level) pLevel,
                        new AABB(pBlockPos.getX() - rad, pBlockPos.getY() - rad, pBlockPos.getZ() - rad,
                                pBlockPos.getX() + rad, pBlockPos.getY() + rad, pBlockPos.getZ() + rad), obj -> obj.getType().is(HLTags.EntityTypes.FACTION_RACE_X));
                this.setxround(race_x.size());
                if (BrainUtils.getTargetOfEntity(this) != null) {
                    for (Mob x : race_x) {
                        if (BrainUtils.getTargetOfEntity(x) == null) {
                            BrainUtils.setTargetOfEntity(x, BrainUtils.getTargetOfEntity(this));
                        }
                    }
                }
            }


            if (this.isDeadOrDying() && this.tickCount % 20 == 0) {
                for (Shocktrooper shocktrooper : this.getTroopers()) {
                    if (shocktrooper != null && shocktrooper.isAlive() && (BrainUtils.getTargetOfEntity(shocktrooper) == null
                            || this.isleader())) {
                        BrainUtils.setTargetOfEntity(shocktrooper, this.getKillCredit());
                    }
                }


                int radius = 100;
                List<Shocktrooper > shocktroopers = EntityRetrievalUtil.getEntities((Level) pLevel,
                        new AABB(pBlockPos.getX() - radius, pBlockPos.getY() - radius, pBlockPos.getZ() - radius,
                                pBlockPos.getX() + radius, pBlockPos.getY() + radius, pBlockPos.getZ() + radius), obj -> obj instanceof Shocktrooper);

                for (Shocktrooper trooper : shocktroopers) {
                    trooper.removeTrooperfromTroopers(this);
                }

            }

            if(this.getLeader().isDeadOrDying()) {
                this.setLeader(this);
                List<Shocktrooper> a = new ArrayList<>();
                a.add(this);
                this.setShocktroopers(a);
                this.setthistoleader(false);
            }


            if (this.tickCount % 20 == 0 && !this.isDeadOrDying()) {
                this.addTroopertoTroopers(this);
                int radius = 20;
                List<Shocktrooper> shocktroopers = EntityRetrievalUtil.getEntities((Level) pLevel,
                        new AABB(pBlockPos.getX() - radius, pBlockPos.getY() - radius, pBlockPos.getZ() - radius,
                                pBlockPos.getX() + radius, pBlockPos.getY() + radius, pBlockPos.getZ() + radius), obj -> obj instanceof Shocktrooper && !((Shocktrooper) obj).isDeadOrDying());
                List<UUID> uuids = new ArrayList<>();
                for (Shocktrooper trooper : shocktroopers) {
                    uuids.add(trooper.getUUID());
                }
                int max = HLperUtil.getMaxUUIDnumber(uuids);
                this.setLeader(shocktroopers.get(max));
                this.getLeader().addTroopertoTroopers(this);
                this.setShocktroopers(this.getLeader().getTroopers());


                if (this.isleader() && shocktroopers.size() < 2) {
                    List<Shocktrooper> a = new ArrayList<>();
                    a.add(this);
                    this.setShocktroopers(a);
                }

                if (this.getLeader() == this) {
                    this.setthistoleader(true);
                } else  {
                    this.setthistoleader(false);
                }

                if (BrainUtils.getTargetOfEntity(this) != null) {
                    for (Shocktrooper trooper : this.getTroopers()) {
                        if (trooper != null && trooper.isAlive() && (BrainUtils.getTargetOfEntity(trooper) == null
                                || (this.isleader() && this.tickCount - this.getLastHurtByMobTimestamp() < 400))) {
                            BrainUtils.setTargetOfEntity(trooper, BrainUtils.getTargetOfEntity(this));
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
                .add(Attributes.MOVEMENT_SPEED, 0.33f).build();
    }





    @Override
    protected Brain.Provider<?> brainProvider() {
        return new SmartBrainProvider<>(this);
    }

    // ORDER LIST:
    // 0 - stand ground
    // 1 - retreat
    // 2 - charge enemy
    // 3 - sit down
    // 4 - change position
    // 5 - group up
    // 6 - go melee
    @Override
    public void aiStep() {
        super.aiStep();

        if ((this.isleader() && this.tickCount % 100 == 0)) {
        List<Shocktrooper> army = this.getTroopers();
            if (army != null && !army.isEmpty()) {
                int j = army.size();
                int i = random.nextInt(0, j);
                int k = random.nextInt(0, j);
                if (k == i) k = random.nextInt(0, j);
                int f = 0;
                for (Shocktrooper soldier : army) {
                    if (random.nextFloat() < 0.05f) {
                        return;
                    }
                    int l = random.nextInt(0, 7);

                    if (f == i || f == k || random.nextFloat() > 0.99f) {
                        soldier.setCanAttack(true);
                    } else  {
                        soldier.setCanAttack(false);
                        if (l == 0 || l == 3) l = random.nextInt(0, 7);
                        if ((l == 0 || l == 3) && random.nextFloat() < 0.2f) l = random.nextInt(0, 7);
                    }

                    LivingEntity target = BrainUtils.getTargetOfEntity(soldier);
                    if (target != null) {
                        if (this.distanceTo(target) > 8f) soldier.setorder(2);
                        if (this.distanceTo(target) < 3f && random.nextFloat() > 0.5f)  {
                            l = 1;
                        }
                        if (this.distanceTo(target) < 5f && random.nextFloat() > 0.9f)  {
                            l = 6;
                        }
                        if (this.distanceTo(target) > 9f || this.distanceTo(target) < 4f && random.nextFloat() < 0.1f) soldier.setCanAttack(true);
                    }
                    soldier.setorder(l);
                    f++;


                    int dedebug = soldier.isleader() ? 1 : 0;
                    int debug = soldier.getcanattack() ? 1 : 0;
                    soldier.setCustomName(Component.literal(soldier.getorder() + "shoot" + debug + "lead" + dedebug + "army" + j));


                }

            }
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
                new SmellSensor<>(),
                new HurtBySensor<>(),
                new NearbyPlayersSensor<>(),
                new NearbyLivingEntitySensor<Shocktrooper>()
                        .setPredicate((target, entity) ->
                            target instanceof Player ||
                                    target.getType().is(HLTags.EntityTypes.FACTION_COMBINE) ||
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
                        new CustomBehaviour<>(entity -> this.entityData.set(IS_ANGRY, false)).startCondition(entity -> this.isangry()),
                        new SetRandomLookTarget<>()),
                new OneRandomBehaviour<>(
                        new FollowLeaderBehaviour<Shocktrooper>().startCondition(entity -> (this.getLeader() != null) && (this.distanceTo(this.getLeader()) > 10)),
                        new SetRandomWalkTarget<>(),
                        new Idle<>().runFor(entity -> entity.getRandom().nextInt(10, 20)),
                        new Idle<>().runFor(entity -> entity.getRandom().nextInt(10, 90))
                ));
    }

    // ORDER LIST:
    // 0 - stand ground
    // 1 - retreat
    // 2 - charge enemy
    // 3 - sit down
    // 4 - change position

    @Override
    public BrainActivityGroup<Shocktrooper> getFightTasks() {
        return BrainActivityGroup.fightTasks(
                new InvalidateAttackTarget<>(),
                new Retaliate<>(),
                new CustomBehaviour<>(entity -> this.setIsSitting(false)).startCondition(entity -> this.getorder() != 3),
                new CustomBehaviour<>(entity -> this.entityData.set(IS_ANGRY, true)).startCondition(entity -> !this.isangry()),
                new CustomBehaviour<>(entity -> BrainUtils.clearMemory(this, MemoryModuleType.WALK_TARGET)).startCondition(entity -> this.getcurrentorder() != this.getorder()),
                new CustomBehaviour<>(entity -> this.setcurrentorder(this.getorder())).startCondition(entity -> this.getcurrentorder() != this.getorder()),
                new FirstApplicableBehaviour<>(
                     new FollowLeaderBehaviour<Shocktrooper>().startCondition(entity -> this.getorder() == 5 && (this.getLeader() != null) && (this.distanceTo(this.getLeader()) > 8)),
                     new SetWalkTargetToRandomSpotAroundAttackTarget<Shocktrooper>().startCondition(entity -> this.getorder() == 2),
                     new SetRandomWalkTarget<>().startCondition(entity -> this.getorder() == 4),
                     new CustomBehaviour<>(entity -> entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 200, 100, false, false, false))).startCondition(entity -> this.getorder() == 3).whenStarting(entity -> setIsSitting(true)),
                     new FleeTarget<>().fleeDistance(7).speedModifier(1.2f).startCondition(entity -> this.getorder() == 1),
                     new SetWalkTargetToAttackTarget<>().startCondition(entity -> this.getorder() == 6)
                ),
                new FirstApplicableBehaviour<>(
                new DoubleMeleeAttack<Shocktrooper>(10, 6, 0.1f, 1, 1, null , 0, null, this.getDoubleAttackSound())
                        .whenStarting(entity ->triggerAnim("onetime", "doubleattack"))
                        .cooldownFor(entity -> random.nextInt(10, 15)),
                new StopAndShoot<Shocktrooper>(3, 8, 1.6f).startCondition(entity -> this.getcanattack())
                        .whenStarting(entity -> triggerAnim("onetime", "shoot"))
                        .cooldownFor(entity -> random.nextFloat() < 0.05f ? 100 : 7)
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


    @Override
    public void performSecondRangedAttack(LivingEntity livingentity, float p_33318_) {
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
        switch (this.random.nextInt(1,5)) {
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
        return HalfLifeSounds.SHOCKTROOPER_IDLE.get();
    }




















}





