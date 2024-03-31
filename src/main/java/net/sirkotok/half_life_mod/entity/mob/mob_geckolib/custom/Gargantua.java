package net.sirkotok.half_life_mod.entity.mob.mob_geckolib.custom;


import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.Vec3;
import net.sirkotok.half_life_mod.entity.base.HalfLifeMonster;
import net.sirkotok.half_life_mod.entity.brain.behaviour.*;
import net.sirkotok.half_life_mod.entity.mob.mob_geckolib.custom.parts.GargAttack;
import net.sirkotok.half_life_mod.entity.mob.modinterface.MultiMeleeEntity;
import net.sirkotok.half_life_mod.entity.particle.HalfLifeParticles;
import net.sirkotok.half_life_mod.entity.projectile.Bullet;
import net.sirkotok.half_life_mod.entity.projectile.FloorBullet;
import net.sirkotok.half_life_mod.misc.config.HalfLifeCommonConfigs;
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
import net.tslat.smartbrainlib.api.core.behaviour.custom.target.InvalidateAttackTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.target.SetPlayerLookTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.target.SetRandomLookTarget;
import net.tslat.smartbrainlib.api.core.sensor.ExtendedSensor;
import net.tslat.smartbrainlib.api.core.sensor.vanilla.HurtBySensor;
import net.tslat.smartbrainlib.api.core.sensor.vanilla.NearbyLivingEntitySensor;
import net.tslat.smartbrainlib.api.core.sensor.vanilla.NearbyPlayersSensor;
import net.tslat.smartbrainlib.util.BrainUtils;
import net.tslat.smartbrainlib.util.EntityRetrievalUtil;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.List;


public class Gargantua extends HalfLifeMonster implements GeoEntity, RangedAttackMob, MultiMeleeEntity, SmartBrainOwner<Gargantua> {


    private AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    private final GargAttack melee;
    private final GargAttack fire11;
    private final GargAttack fire12;
    private final GargAttack fire13;
    private final GargAttack fire14;
    private final GargAttack fire15;
    private final GargAttack fire16;
    private final GargAttack fire21;
    private final GargAttack fire22;
    private final GargAttack fire23;
    private final GargAttack fire24;
    private final GargAttack fire25;
    private final GargAttack fire26;

    private final GargAttack[] attacks;

    @Override
    public boolean isMultipartEntity() {
        return true;
    }


    public static final EntityDataAccessor<Boolean> IS_ANGRY = SynchedEntityData.defineId(Gargantua.class, EntityDataSerializers.BOOLEAN);
    public static final EntityDataAccessor<Boolean> IS_FIRE = SynchedEntityData.defineId(Gargantua.class, EntityDataSerializers.BOOLEAN);

    protected void defineSynchedData() {
        super.defineSynchedData();

        this.entityData.define(IS_ANGRY, false);
        this.entityData.define(IS_FIRE, false);

    }
    private int dyingticks = 0;


    public int fire = 0;
    @Override
    protected void dropExperience() {
    }

    protected void actuallydropExperience() {
        if (this.level instanceof ServerLevel && !this.wasExperienceConsumed() && (this.isAlwaysExperienceDropper() || this.lastHurtByPlayerTime > 0 && this.shouldDropExperience() && this.level.getGameRules().getBoolean(GameRules.RULE_DOMOBLOOT))) {
            int reward = net.minecraftforge.event.ForgeEventFactory.getExperienceDrop(this, this.lastHurtByPlayer, this.getExperienceReward());
            ExperienceOrb.award((ServerLevel)this.level, this.position(), reward);
        }
    }

    public boolean fireImmune() {
        return true;
    }


    @Override
    public void aiStep() {
        super.aiStep();

        this.tickfront(melee);
        this.tickallFire();


        if (!this.level.isClientSide()) this.entityData.set(IS_FIRE, this.fire > 0);
        if (entityData.get(IS_FIRE)) {
            if (BrainUtils.getTargetOfEntity(this) != null) {
                Vec3 vec31 =  BrainUtils.getTargetOfEntity(this).position();
                Vec3 vec3 = this.position();
                Vec3 between = vec3.subtract(vec31);
                double yangle = HLperUtil.yanglefromvec3(between);
                double yRot = this.getYRot();
                this.setYRot((float) (yRot + (yangle - yRot)*0.1f));
            }
        }




        if (fire > -2) fire--;

        if (fire == 0 && !this.level.isClientSide()) {
            this.playSound(HalfLifeSounds.GAR_FLAMEOFF1.get(), 0.4f, 1);
        }



    }

    @Override
    public void tick() {
        super.tick();



        if (!this.hasEffect(MobEffects.MOVEMENT_SLOWDOWN) && !this.level.isClientSide()) this.fire = -5;

        if (this.isDeadOrDying() && !this.level.isClientSide()) {
            if (dyingticks == 0) this.triggerAnim("onetime", "die");
            dyingticks++;
            if (dyingticks == 17){
                this.level.explode(this, this.getX(), this.getY(), this.getZ(), 5f, Level.ExplosionInteraction.MOB);
            }
            if (dyingticks == 18) {
                this.actuallydropExperience();
            }
        }










        if (this.tickCount % 100 == 0 && !this.level.isClientSide()) {
            InfightingUtil.alertsameteam(this);
        }



    }

    private void tickfront(GargAttack pPart) {
        float i = length()-1.8f;
        double yrot = (this.yBodyRot)/180*Math.PI;
        double d1 = Math.sin(yrot);
        double d2 = Math.cos(yrot);
        Vec3 vec3 = new Vec3((float)this.getX()-i*d1, this.getY(), (float)this.getZ()+i*d2);
        pPart.moveTo(vec3);
    }

    @Override
    public net.minecraftforge.entity.PartEntity<?>[] getParts() { return this.attacks; }


    public boolean isangry() {
        return this.entityData.get(IS_ANGRY);
    }
    public boolean isfire() {
        return this.entityData.get(IS_FIRE);
    }

    float cube = 2.2f;

    public Gargantua(EntityType type, Level level) {
        super(type, level);
        this.xpReward = 50;
        this.setMaxUpStep(2f);
        this.setPathfindingMalus(BlockPathTypes.DAMAGE_FIRE, 0);
        this.setPathfindingMalus(BlockPathTypes.DANGER_FIRE, 0);
        this.melee = new GargAttack(this, "melee", 4.5F, 1f);
        this.fire11 = new GargAttack(this, "f11", 3, 3);
        this.fire12 = new GargAttack(this, "f12", cube, cube);
        this.fire13 = new GargAttack(this, "f13", cube, cube);
        this.fire14 = new GargAttack(this, "f14", cube, cube);
        this.fire15 = new GargAttack(this, "f15", cube, cube);
        this.fire16 = new GargAttack(this, "f16", cube, cube);
        this.fire21 = new GargAttack(this, "f21", 3, 3);
        this.fire22 = new GargAttack(this, "f22", cube, cube);
        this.fire23 = new GargAttack(this, "f23", cube, cube);
        this.fire24 = new GargAttack(this, "f24", cube, cube);
        this.fire25 = new GargAttack(this, "f25", cube, cube);
        this.fire26 = new GargAttack(this, "f26", cube, cube);
        this.attacks = new GargAttack[]{
                this.melee,
                this.fire11,
                this.fire12,
                this.fire13,
                this.fire14,
                this.fire15,
                this.fire16,
                this.fire21,
                this.fire22,
                this.fire23,
                this.fire24,
                this.fire25,
                this.fire26,

        };
        this.setId(ENTITY_COUNTER.getAndAdd(this.attacks.length + 1) + 1);
    }

    @Override
    public void setId(int pId) {
        super.setId(pId);
        for (int i = 0; i < this.attacks.length; i++) // Forge: Fix MC-158205: Set part ids to successors of parent mob id
            this.attacks[i].setId(pId + i + 1);
    }

    public void recreateFromPacket(ClientboundAddEntityPacket pPacket) {
        super.recreateFromPacket(pPacket);
        this.yBodyRot = 0.0F;
        this.yBodyRotO = 0.0F;
    }


    @Override
    public boolean performMultiAttack(boolean after, Entity entity, float disablechance, float attack_modifier, float knockback_modifier, @javax.annotation.Nullable MobEffect effect, int duration, boolean visible) {
        boolean u = false;
        List<Entity> list = this.level.getEntities(this, this.melee.getBoundingBox(), obj -> obj instanceof LivingEntity && !(obj instanceof Gargantua));
        for (Entity entity1 : list) {
            if (entity1 instanceof LivingEntity living) {
                this.ConfigurabledoHurtTarget(entity1, disablechance, attack_modifier, knockback_modifier, effect, duration, visible);
                u = true;
            }
        }
        return u;
    }

    @Override
    public boolean isinside() {
        List<Entity> list = this.level.getEntities(this, this.melee.getBoundingBox(), obj -> obj instanceof LivingEntity && !(obj instanceof Gargantua));
        return !list.isEmpty();
    }


    private float length(){
        return this.getBbWidth()/2+1.3f;
    }




    @Override
    public boolean hurt(DamageSource pSource, float pAmount) {
        if (pSource.is(DamageTypeTags.IS_PROJECTILE) && !pSource.is(HLTags.DamageTypes.ARMORPIERCING)) {
            this.playSound(CommonSounds.getRicSound(), 0.8f,  this.getVoicePitch());
            }
        if (!pSource.is(HLTags.DamageTypes.ARMORPIERCING)) {
            return false;
        }
        if (pSource.is(DamageTypeTags.IS_EXPLOSION)) return super.hurt(pSource, pAmount);
        return super.hurt(pSource, pAmount*0.2f);
    }



    public static AttributeSupplier setAttributes() {
        return Monster.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 80D)
                .add(Attributes.ATTACK_DAMAGE, 20f)
                .add(Attributes.ATTACK_SPEED, 1.0f)
                .add(Attributes.ATTACK_KNOCKBACK, 3.0f)
                .add(Attributes.KNOCKBACK_RESISTANCE, 5f)
                .add(Attributes.FOLLOW_RANGE, 64)
                .add(Attributes.MOVEMENT_SPEED, 0.22f).build();
    }


    @Override
    public double getMeleeAttackRangeSqr(LivingEntity pEntity) {
        return (double)(this.getBbWidth() * this.getBbWidth() + pEntity.getBbWidth());
    }

    public SoundEvent getattacksound() {
        switch (this.random.nextInt(1,4)) {
            case 1:  return HalfLifeSounds.GAR_ATTACK1.get();
            case 2:  return HalfLifeSounds.GAR_ATTACK2.get();
            case 3:  return HalfLifeSounds.GAR_ATTACK3.get();
        }
        return HalfLifeSounds.HEADCRAB_1_DIE_1.get();
    }

    protected SoundEvent getDeathSound() {
        switch (this.random.nextInt(1,3)) {
            case 1:  return HalfLifeSounds.GAR_DIE1.get();
            case 2:  return HalfLifeSounds.GAR_DIE2.get();
        }
        return HalfLifeSounds.HEADCRAB_1_DIE_1.get();
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource pDamageSource) {
        switch (this.random.nextInt(1,4)) {
            case 1:  return HalfLifeSounds.GAR_PAIN1.get();
            case 2:  return HalfLifeSounds.GAR_PAIN2.get();
            case 3:  return HalfLifeSounds.GAR_PAIN3.get();
        }
        return HalfLifeSounds.HEADCRAB_1_DIE_1.get();
    }




    protected SoundEvent getAmbientSound() {
        int f = !this.isangry() ? random.nextInt(1, 6) : random.nextInt(random.nextFloat() < 0.8f ? 0 : 6, 9);
        switch (f) {
            case 1:  return HalfLifeSounds.GAR_IDLE1.get();
            case 2:  return HalfLifeSounds.GAR_IDLE2.get();
            case 3:  return HalfLifeSounds.GAR_IDLE3.get();
            case 4:  return HalfLifeSounds.GAR_IDLE4.get();
            case 5:  return HalfLifeSounds.GAR_IDLE5.get();
            case 6:  return HalfLifeSounds.GAR_ALERT1.get();
            case 7:  return HalfLifeSounds.GAR_ALERT2.get();
            case 8:  return HalfLifeSounds.GAR_ALERT3.get();
        }
        return SoundEvents.FROG_STEP;
    }






    @Override
    protected float getSoundVolume() {
        return 0.8f;
    }



    protected SoundEvent getStep1Sound() {
        return HalfLifeSounds.GAR_STEP1.get();
    }
    protected SoundEvent getStep2Sound() {
        return HalfLifeSounds.GAR_STEP2.get();
    }

    @Override
    protected void playStepSound(BlockPos pPos, BlockState pState) {
        if (this.tickCount%2 == 0) {
            BlockState blockstate = this.level.getBlockState(pPos.above());
            boolean flag = blockstate.is(BlockTags.INSIDE_STEP_SOUND_BLOCKS);
            if (flag || !pState.getMaterial().isLiquid()) {
                if (this.tickCount%4 == 0) this.playSound(getStep1Sound());
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
    public List<ExtendedSensor<Gargantua>> getSensors() {
        return ObjectArrayList.of(
                new HurtBySensor<>(),
                new NearbyPlayersSensor<>(),
                new NearbyLivingEntitySensor<Gargantua>()
                        .setPredicate((target, entity) -> InfightingUtil.commonenemy(target) || InfightingUtil.XenForcesSpecific(target)
                              ));
    }



    @Override
    public BrainActivityGroup<Gargantua> getCoreTasks() {
        return BrainActivityGroup.coreTasks(
                new LookAtTarget<>(),
                new MoveToWalkTarget<>());
    }

    @Override
    public BrainActivityGroup<Gargantua> getIdleTasks() { // These are the tasks that run when the mob isn't doing anything else (usually)
        return BrainActivityGroup.idleTasks(
                  new FirstApplicableBehaviour<Gargantua>(
                                new TargetOrRetaliateHLT<>(),
                                new SetPlayerLookTarget<>(),
                                new SetRandomLookTarget<>()),
                  new OneRandomBehaviour<>(
                          new SetRandomWalkTarget<>(),
                          new Idle<>().runFor(entity -> entity.getRandom().nextInt(10, 40))
                                  .whenStarting(entity -> this.entityData.set(IS_ANGRY, false))));

    }



    public void hit() {
        this.triggerAnim("onetime", "m1");
        if (this.fire > 0) this.playSound(HalfLifeSounds.GAR_FLAMEOFF1.get(), 0.4f, 1);
        this.fire = -4;
    }
    public void fire() {

        HLperUtil.slowEntityFor(this, 5);
        ServerLevel pLevel = (ServerLevel) this.level;
        BlockPos pBlockPos = this.blockPosition();
        int rad = 10;
        if (this.fire == 6) this.playSound(HalfLifeSounds.GAR_FLAMEON1.get(), 0.4f, 1);
        else {
            this.playSound(HalfLifeSounds.GAR_FLAMERUN1.get(), 0.4f, 1);
        for (GargAttack part : this.attacks) {
            if (!part.name.equals("melee")) {
        List<LivingEntity> enemy = EntityRetrievalUtil.getEntities((Level) pLevel,
                part.getBoundingBox(), obj ->  obj instanceof LivingEntity living && !living.fireImmune());
        if (BrainUtils.getTargetOfEntity(this) != null) {
            for (LivingEntity x : enemy) {
                x.hurt(this.damageSources().inFire(), 4);
                x.setSecondsOnFire(5);
            }
        }
        }
        }
        }
    }

    @Override
    public boolean ConfigurabledoHurtTarget(Entity entity, float disablechance, float attack_modifier, float knockback_modifier, @Nullable MobEffect effect, int duration, boolean visible) {
        Vec3 between = entity.position().subtract(this.position()).normalize().scale(2);
        entity.addDeltaMovement(new Vec3 (between.x(), 1.2f, between.z()));
        return super.ConfigurabledoHurtTarget(entity, disablechance, attack_modifier, knockback_modifier, effect, duration, visible);
    }

    @Override
    public BrainActivityGroup<Gargantua> getFightTasks() { // These are the tasks that handle fighting
        return BrainActivityGroup.fightTasks(
                new InvalidateAttackTarget<>(),
                new Retaliate<>(),
                new CustomBehaviour<>(entity -> this.entityData.set(IS_ANGRY, true)).startCondition(entity -> !this.entityData.get(IS_ANGRY)),
                new SetWalkTargetToRandomSpotAroundAttackTarget<>().speedMod(1.3f).startCondition(entity ->  this.distanceTo(HLperUtil.TargetOrThis(this))  > 15),
                new SetWalkTargetToAttackTargetIfNoWalkTarget<>(),
                new FirstApplicableBehaviour<>(
                        new CustomBehaviour<>(entity -> this.fire = 7 + Math.max(this.fire, 0)).whenStarting(entity -> this.fire()).startCondition(entity -> HLperUtil.DistanceToTarget(this) < 7).cooldownFor(entity -> 4),
                        new MultiMeleeAttack<Gargantua>(true, 12, 1f, 1, 2f, null, 0, CommonSounds.getClawHitSound(), this.getattacksound(), CommonSounds.getClawMissSound())
                                .whenStarting(entity -> this.hit())
                                .cooldownFor(entity -> random.nextInt(30, 50)),
                         new StopAndShoot<Gargantua>(12, 15, 0.33f).cooldownFor(entity -> random.nextInt(200, 500))
                        .whenStarting(entity -> triggerAnim("onetime", "shoot")).startCondition(entity -> HLperUtil.DistanceToTarget(this) > 5))
                );
    }





    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(new AnimationController<>(this, "controller", 0, this::idle));
        controllerRegistrar.add(new AnimationController<>(this, "stretch", 0, this::stretch));
        controllerRegistrar.add(new AnimationController<>(this, "onetime", state -> PlayState.CONTINUE)
                .triggerableAnim("m1", RawAnimation.begin().then("animation.garg.melee", Animation.LoopType.PLAY_ONCE))
                .triggerableAnim("die", RawAnimation.begin().then("animation.garg.die", Animation.LoopType.PLAY_ONCE))
                .triggerableAnim("shoot", RawAnimation.begin().then("animation.garg.stomp", Animation.LoopType.PLAY_ONCE))
               // .triggerableAnim("fire", RawAnimation.begin().then("animation.garg.fire", Animation.LoopType.PLAY_ONCE))
        );
    }



    private <T extends GeoAnimatable> PlayState stretch(AnimationState<T> tAnimationState) {
        tAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.garg.stretch", Animation.LoopType.LOOP));
        return PlayState.CONTINUE;
    }

    private <T extends GeoAnimatable> PlayState idle(AnimationState<T> tAnimationState) {

        if (this.entityData.get(IS_FIRE)) {
            tAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.garg.fire", Animation.LoopType.LOOP));
            return PlayState.CONTINUE;
        }


        if(tAnimationState.isMoving()) {

            if (this.entityData.get(IS_ANGRY)) {
                tAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.garg.run", Animation.LoopType.LOOP));
                return PlayState.CONTINUE;
            }

            tAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.garg.walk", Animation.LoopType.LOOP));
            return PlayState.CONTINUE;
        }

        tAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.garg.idle", Animation.LoopType.LOOP));
        return PlayState.CONTINUE;
    }


    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    private GargAttack getAttackByName(String s) {
        switch (s) {
            case "f11" : return this.fire11;
            case "f12" : return this.fire12;
            case "f13" : return this.fire13;
            case "f14" : return this.fire14;
            case "f15" : return this.fire15;
            case "f16" : return this.fire16;
            case "f21" : return this.fire21;
            case "f22" : return this.fire22;
            case "f23" : return this.fire23;
            case "f24" : return this.fire24;
            case "f25" : return this.fire25;
            case "f26" : return this.fire26;
        }
        return this.melee;
    }





    private void tickallFire(){
        int i = 1;
        int k = 1;
        while (k < 7) {
                if (i == 1) {
                    tickFire(getAttackByName("f1"+k), i, k*0.15f);
                    i = -1;
                } else {
                    tickFire(getAttackByName("f2"+k), i, k*0.15f);
                    i = 1;
                    k++;
                }
            }
        }

        public static boolean canbelitonfire(Level level, BlockPos pos) {
        boolean b = false;
        for (Direction d : Direction.values()) {
            b = b || BaseFireBlock.canBePlacedAt(level, pos, d);
        }
        return b;
        }

        public void lightblockonfire(BlockPos blockpos1) {
        if (this.random.nextFloat() < HalfLifeCommonConfigs.GARG_SETFIRE_CHANCE.get())
        if (!this.level.isClientSide()) {
            if (canbelitonfire(this.level, blockpos1)) {
              //  level.playSound(blockpos1, SoundEvents.FLINTANDSTEEL_USE, SoundSource.BLOCKS, 1.0F, level.getRandom().nextFloat() * 0.4F + 0.8F);
                BlockState blockstate1 = BaseFireBlock.getState(level, blockpos1);
                level.setBlock(blockpos1, blockstate1, 11);
              //  level.gameEvent(GameEvent.BLOCK_PLACE, blockpos1);
        }
        }
    }


    private void tickFire(GargAttack segmeng, int side, float percent) {
        double x = this.getFireStart(side).x()+(-this.getFireStart(side).x()+this.getFireEnd(side).x())*percent;
        double y = this.getFireStart(side).y()+(-this.getFireStart(side).y()+this.getFireEnd(side).y())*percent;
        double z = this.getFireStart(side).z()+(-this.getFireStart(side).z()+this.getFireEnd(side).z())*percent;
        segmeng.moveTo(x, y-segmeng.getBbHeight()/2, z);
        if (this.entityData.get(IS_FIRE)) {


                BlockPos fireblock = segmeng.blockPosition();
                lightblockonfire(fireblock);
                lightblockonfire(fireblock.above());
                lightblockonfire(fireblock.below());
                lightblockonfire(fireblock.north());
                lightblockonfire(fireblock.east());
                lightblockonfire(fireblock.west());
                lightblockonfire(fireblock.south());


        level.addParticle(HalfLifeParticles.STAT_GLOW.get(), x, y+0.4f, z, 2, 0.4d, 0); }
    }

    private Vec3 rotvec(int angledegree, float length){
        double yrot = (this.yBodyRot+angledegree)/180*Math.PI;
        double d1 = Math.sin(yrot);
        double d2 = Math.cos(yrot);
        return new Vec3((float)this.getX()-length*d1, this.getY()+1.67f, (float)this.getZ()+length*d2);
    }

    private Vec3 getFireStart(int side) {
        return rotvec(40*side, 2);
    }
    private Vec3 getFireEnd(int side) {
        return rotvec(10*side, 7);
    }


    @Override
    public void performRangedAttack(LivingEntity livingentity, float pVelocity) {
        double d1 = livingentity.getX() - this.getX();
        double d2 = 0;
        double d3 = livingentity.getZ() - this.getZ();
        this.playSound(HalfLifeSounds.GAR_STOMP1.get(), this.getSoundVolume(), 1f);
        FloorBullet bullet = new FloorBullet(this.level, this, d1, d2, d3);
        bullet.setPos(this.getX(), this.getY()+0.1f, this.getZ());
        bullet.shoot(d1, d2, d3, pVelocity, 1f);
        bullet.setspeed(pVelocity);
        this.level.addFreshEntity(bullet);
        this.performMultiAttack(true, livingentity, 1, 1.5f, 0.5f, null, 0, false);
    }




}

