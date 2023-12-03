package net.sirkotok.half_life_mod.entity.mob_geckolib.custom;


import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.BlockPos;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.sirkotok.half_life_mod.entity.base.HalfLifeMonster;
import net.sirkotok.half_life_mod.entity.base.HalfLifeNeutral;
import net.sirkotok.half_life_mod.entity.brain.ModMemoryModuleType;
import net.sirkotok.half_life_mod.entity.brain.behaviour.*;
import net.sirkotok.half_life_mod.entity.modinterface.RushingMob;
import net.sirkotok.half_life_mod.entity.modinterface.VariableRangedMob;
import net.sirkotok.half_life_mod.entity.projectile.AcidBall;
import net.sirkotok.half_life_mod.entity.projectile.arrowlike.Flechette;
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
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.List;


public class Hunter extends HalfLifeMonster implements RushingMob, GeoEntity, RangedAttackMob, VariableRangedMob, SmartBrainOwner<Hunter> {


    private AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);


    public static final EntityDataAccessor<Boolean> ISDEFENDINGSTRIDER = SynchedEntityData.defineId(Hunter.class, EntityDataSerializers.BOOLEAN);
    public static final EntityDataAccessor<Boolean> ISANGRY = SynchedEntityData.defineId(Hunter.class, EntityDataSerializers.BOOLEAN);


    public static final EntityDataAccessor<Boolean> ISRUSHING = SynchedEntityData.defineId(Hunter.class, EntityDataSerializers.BOOLEAN);
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(ISRUSHING, false);
        this.entityData.define(ISANGRY, false);
        this.entityData.define(ISDEFENDINGSTRIDER, false);
    }


    public boolean getRushing(){
        return this.entityData.get(ISRUSHING);
    }
    public void setRushing(boolean b){
        this.entityData.set(ISRUSHING, b);
    }

    public boolean getDefending(){
        return this.entityData.get(ISDEFENDINGSTRIDER);
    }
    public void setDefending(boolean b){
        this.entityData.set(ISDEFENDINGSTRIDER, b);
    }
    public boolean getAngry(){
        return this.entityData.get(ISANGRY);
    }
    public void setAngry(boolean b){
        this.entityData.set(ISANGRY, b);
    }



    public int getphase(){
        return this.getHealth()/this.getMaxHealth() > 0.4f ? 10 : 1;
    }


    public Hunter(EntityType type, Level level) {
        super(type, level);
        this.xpReward = 15;
    }


    @Override
    public void tick() {
        super.tick();
        this.setRushing(BrainUtils.getMemory(this, ModMemoryModuleType.RUSHING.get()) != null);
    }

    public static AttributeSupplier setAttributes() {
        return Monster.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 50D)
                .add(Attributes.ATTACK_DAMAGE, 12f).add(Attributes.ARMOR, 20)
                .add(Attributes.ATTACK_SPEED, 1.0f).add(Attributes.KNOCKBACK_RESISTANCE, 0.9f)
                .add(Attributes.ATTACK_KNOCKBACK, 1.6f)
                .add(Attributes.MOVEMENT_SPEED, 0.34f).build();
    }


    protected SoundEvent getScewerSound() {return ModSounds.MINISTRIDER_SKEWER1.get();}
    protected SoundEvent getPreStrikeSound() {return ModSounds.HUNTER_PRESTRIKE1.get();}
    protected SoundEvent getPreFlechetteSound() {return ModSounds.MINISTRIDER_PREFLECHETTE.get();}
    protected SoundEvent getFireLoopSound() {return ModSounds.HUNTER_FIRE_LOOP3.get();}
    protected SoundEvent getFireSound() {return ModSounds.MINISTRIDER_FIRE1.get();}
    protected SoundEvent getImpactSound(){
        switch (this.random.nextInt(1,7)) {
            case 1:  return ModSounds.BODY_MEDIUM_IMPACT_HARD1.get();
            case 2:  return ModSounds.BODY_MEDIUM_IMPACT_HARD2.get();
            case 3:  return ModSounds.BODY_MEDIUM_IMPACT_HARD3.get();
            case 4:  return ModSounds.BODY_MEDIUM_IMPACT_HARD4.get();
            case 5:  return ModSounds.BODY_MEDIUM_IMPACT_HARD5.get();
            case 6:  return ModSounds.BODY_MEDIUM_IMPACT_HARD6.get();
        }
        return SoundEvents.FROG_STEP;
    }


    protected SoundEvent getFlankSound(){
        switch (this.random.nextInt(1,3)) {
            case 1:  return ModSounds.HUNTER_FLANK_ANNOUNCE1.get();
            case 2:  return ModSounds.HUNTER_FLANK_ANNOUNCE2.get();
        }
        return SoundEvents.FROG_STEP;
    }

    protected SoundEvent getEnemySpotSound(){
        switch (this.random.nextInt(1,6)) {
            case 1:  return ModSounds.HUNTER_FOUNDENEMY_ACK1.get();
            case 2:  return ModSounds.HUNTER_FOUNDENEMY_ACK2.get();
            case 3:  return ModSounds.HUNTER_FOUNDENEMY_ACK3.get();
        }
        switch (this.random.nextInt(1,4)) {
            case 1:  return ModSounds.HUNTER_FOUNDENEMY1.get();
            case 2:  return ModSounds.HUNTER_FOUNDENEMY2.get();
            case 3:  return ModSounds.HUNTER_FOUNDENEMY3.get();
        }
        return SoundEvents.FROG_STEP;
    }




    @Override
    protected SoundEvent getAmbientSound() {

        if (this.getDefending() && this.random.nextFloat() < 0.4) {
            int m = this.random.nextInt(1, 4);
            switch (m) {
                case 1:
                    return ModSounds.HUNTER_DEFENDSTRIDER1.get();
                case 2:
                    return ModSounds.HUNTER_DEFENDSTRIDER2.get();
                case 3:
                    return ModSounds.HUNTER_DEFENDSTRIDER3.get();
            }
        }

        LivingEntity enemytarget = BrainUtils.getTargetOfEntity(this);
        if (enemytarget != null && this.random.nextFloat() < 0.7) {
            if (enemytarget.getHealth()/enemytarget.getMaxHealth() < 0.2 && this.random.nextFloat() < 0.2) {
                int j = this.random.nextInt(1, 6);
                switch (j) {
                    case 1:
                        return ModSounds.HUNTER_LAUGH1.get();
                    case 2:
                        return ModSounds.HUNTER_LAUGH2.get();
                    case 3:
                        return ModSounds.HUNTER_LAUGH3.get();
                    case 4:
                        return ModSounds.HUNTER_LAUGH4.get();
                    case 5:
                        return ModSounds.HUNTER_LAUGH5.get();
                }
            }
            int i = this.random.nextInt(1, 7);
            if (i > 3) i = this.random.nextInt(1, 7);
            switch (i) {
                case 1:
                    return ModSounds.HUNTER_ALERT1.get();
                case 2:
                    return ModSounds.HUNTER_ALERT2.get();
                case 3:
                    return ModSounds.HUNTER_ALERT3.get();
                case 4:
                    return ModSounds.HUNTER_ANGRY1.get();
                case 5:
                    return ModSounds.HUNTER_ANGRY2.get();
                case 6:
                    return ModSounds.HUNTER_ANGRY3.get();
            }
        }

        if (enemytarget == null && this.random.nextFloat() < 0.2) {
            int i = this.random.nextInt(1, 5);
            switch (i) {
                case 1:
                    return ModSounds.HUNTER_SCAN1.get();
                case 2:
                    return ModSounds.HUNTER_SCAN2.get();
                case 3:
                    return ModSounds.HUNTER_SCAN3.get();
                case 4:
                    return ModSounds.HUNTER_SCAN4.get();
            }
        }

        int k = this.random.nextInt(1, 4);
        switch (k) {
            case 1:
                return ModSounds.HUNTER_IDLE1.get();
            case 2:
                return ModSounds.HUNTER_IDLE2.get();
            case 3:
                return ModSounds.HUNTER_IDLE3.get();
        }

        return SoundEvents.FROG_STEP;
    }




    protected SoundEvent getStepSound() {
        int j = this.random.nextInt(1, 6);
        switch (j) {
            case 1:
                return ModSounds.MINISTRIDER_FOOTSTEP1.get();
            case 2:
                return ModSounds.MINISTRIDER_FOOTSTEP2.get();
            case 3:
                return ModSounds.MINISTRIDER_FOOTSTEP3.get();
            case 4:
                return ModSounds.MINISTRIDER_FOOTSTEP4.get();
            case 5:
                return ModSounds.MINISTRIDER_FOOTSTEP5.get();
        }

        return ModSounds.SHOCKROACH_WALK.get();
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

    protected SoundEvent getDeathSound() {
        switch (this.random.nextInt(1,3)) {
            case 1:  return ModSounds.HUNTER_DIE2.get();
            case 2:  return ModSounds.HUNTER_DIE3.get();
        }
        return SoundEvents.FROG_STEP;
    }
    protected SoundEvent getChargeSound() {
        switch (this.random.nextInt(1,3)) {
            case 1:  return ModSounds.HUNTER_CHARGE3.get();
            case 2:  return ModSounds.HUNTER_CHARGE4.get();
        }
        return SoundEvents.FROG_STEP;
    }


    protected SoundEvent getHurtSound(DamageSource pSource) {
        switch (this.random.nextInt(1,3)) {
            case 1:  return ModSounds.HUNTER_PAIN2.get();
            case 2:  return ModSounds.HUNTER_PAIN4.get();
        }
        return SoundEvents.FROG_STEP;
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
        tickBrain(this);
    }



    @Override
    public List<ExtendedSensor<Hunter>> getSensors() {
        return ObjectArrayList.of(
                new HurtBySensor<>(),
                new NearbyPlayersSensor<>(),
                new NearbyLivingEntitySensor<Hunter>()
                        .setPredicate((target, entity) ->
                            target instanceof Player || target instanceof IronGolem || target instanceof HalfLifeNeutral ||
                            target instanceof AbstractVillager || (target instanceof Enemy && !target.getType().is(ModTags.EntityTypes.FACTION_COMBINE))));
    }



    @Override
    public BrainActivityGroup<Hunter> getCoreTasks() {
        return BrainActivityGroup.coreTasks(
                new LookAtTarget<>(),
                new MoveToWalkTarget<>(),
                new CustomBehaviour<>(entity -> BrainUtils.clearMemory(this, MemoryModuleType.HURT_BY_ENTITY)).startCondition(entity -> BrainUtils.getMemory(this, MemoryModuleType.HURT_BY_ENTITY) instanceof Hunter),
                new CustomBehaviour<>(entity -> BrainUtils.clearMemory(this, MemoryModuleType.ATTACK_TARGET)).startCondition(entity -> BrainUtils.getMemory(this, MemoryModuleType.ATTACK_TARGET) instanceof Hunter)
        );

    }

    @Override
    public BrainActivityGroup<Hunter> getIdleTasks() { // These are the tasks that run when the mob isn't doing anything else (usually)
        return BrainActivityGroup.idleTasks(
                new FirstApplicableBehaviour<Hunter>(
                        new CustomBehaviour<>(entity -> this.setAngry(false)).startCondition(entity -> this.getAngry()),
                        new TargetOrRetaliateHLT<>(),
                         new SetPlayerLookTarget<>(),
                        new SetRandomLookTarget<>()),

               new OneRandomBehaviour<>(
                new SetRandomWalkTarget<>().setRadius(20, 10).speedModifier(1f),
                new Idle<>().runFor(entity -> entity.getRandom().nextInt(30, 90)))
        );
    }


    @Override
    public boolean isWithinMeleeAttackRange(LivingEntity pEntity) {
        double d0 = this.getPerceivedTargetDistanceSquareForMeleeAttack(pEntity);
        return d0 <= this.getMeleeAttackRangeSqr(pEntity)+0.3f;
    }

    @Override
    public BrainActivityGroup<Hunter> getFightTasks() { // These are the tasks that handle fighting
        return BrainActivityGroup.fightTasks(
                new InvalidateAttackTarget<>(),
                new InvalidateAttackTarget<>().invalidateIf((entity, target) -> HLperUtil.issameteam(entity, target)),
                new CustomBehaviour<>(entity -> this.setAngry(true)).startCondition(entity -> !this.getAngry() && this.random.nextFloat() < 0.1f).whenStarting(entity -> playSound(getEnemySpotSound(), this.getSoundVolume(), this.getVoicePitch())),
                new CustomBehaviour<>(entity -> BrainUtils.clearMemory(this, MemoryModuleType.WALK_TARGET)).cooldownFor(entity -> 200),
                new Retaliate<>(),
                new SetWalkTargetToRandomSpotAroundAttackTarget<>().speedMod(1f).startCondition(entity -> this.distanceTo(HLperUtil.TargetOrThis(this)) > 15),
                new OneRandomBehaviour<>(
                        new SetRandomWalkTarget<>().setRadius(20, 10).speedModifier(1f).whenStarting(entity -> playSound(getFlankSound(), this.getSoundVolume(), this.getVoicePitch())),
                        new SetRandomWalkTarget<>().setRadius(10, 5).speedModifier(1f),
                        new Idle<>().runFor(entity -> entity.getRandom().nextInt(10, 20)),
                        new SetWalkTargetToRandomSpotAroundAttackTarget<>().radius(10, 5).speedMod(1f)
                ).cooldownFor(entity -> this.getphase()*5),
                new FirstApplicableBehaviour<>(
                        new OneRandomBehaviour<>(
                                new ConfigurableAnimatableMeleeAttack<Hunter>(9, 0.5f, 1, 1.2f, null, 0, null, getPreStrikeSound())
                                        .whenStarting(entity -> triggerAnim("onetime", "attack1"))
                                        .cooldownFor(entity -> random.nextInt(10, 20)),
                                new ConfigurableAnimatableMeleeAttack<Hunter>(9, 0.5f, 1, 1.2f, null, 0, null, getPreStrikeSound())
                                        .whenStarting(entity -> triggerAnim("onetime", "attack1"))
                                        .cooldownFor(entity -> random.nextInt(10, 20)),
                                new ConfigurableAnimatableMeleeAttack<Hunter>(12, 1f, 1.5f, 0.5f, null, 0, null, getScewerSound())
                                        .startCondition(entity -> HLperUtil.TargetOrThis(this).getBbHeight() > 1.4f)
                                        .whenStarting(entity -> triggerAnim("onetime", "attack3"))
                                         .cooldownFor(entity -> random.nextInt(40, 90))
                        ),
                        new OneRandomBehaviour<>(
                         new RushPushToTarget<>(200, 26, 25, 32, (entity, targetpos) -> 1.5f, true, getChargeSound()).whenStarting(entity -> triggerAnim("onetime", "startrun")).cooldownFor(entity -> 400).startCondition(entity -> this.distanceTo(HLperUtil.TargetOrThis(this)) > 8 && this.random.nextFloat() < 0.1f && this.getphase() < 5),
                         new StopAndShootOverTime<>(50, 1, 12, 40, 2, 3.2f, true, getFireLoopSound(), getPreFlechetteSound()).cooldownFor(entity -> random.nextInt(100, 200))
                                .whenStarting(entity -> triggerAnim("onetime", "shoot")),
                         new StopAndShootOverTime<>(50, 1, 12, 40, 2, 3.2f, false, getFireLoopSound(), getPreFlechetteSound()).cooldownFor(entity -> random.nextInt(100, 200))
                                .whenStarting(entity -> triggerAnim("onetime", "shoot")))
                ));
    }







    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(new AnimationController<>(this, "eye10", 0, this::eye10));
        controllerRegistrar.add(new AnimationController<>(this, "eye11", 0, this::eye11));
        controllerRegistrar.add(new AnimationController<>(this, "eye12", 0, this::eye12));
        controllerRegistrar.add(new AnimationController<>(this, "eye20", 0, this::eye20));
        controllerRegistrar.add(new AnimationController<>(this, "eye21", 0, this::eye21));
        controllerRegistrar.add(new AnimationController<>(this, "eye22", 0, this::eye22));
        controllerRegistrar.add(new AnimationController<>(this, "things", 0, this::things));
        controllerRegistrar.add(new AnimationController<>(this, "main_loop", 0, this::predicate));
        controllerRegistrar.add(new AnimationController<>(this, "onetime", state -> PlayState.STOP)
                .triggerableAnim("attack3", RawAnimation.begin().then("animation.hunter.midattack", Animation.LoopType.PLAY_ONCE))
                .triggerableAnim("attack1", RawAnimation.begin().then("animation.hunter.attack", Animation.LoopType.PLAY_ONCE))
                .triggerableAnim("attack2", RawAnimation.begin().then("animation.hunter.attack2", Animation.LoopType.PLAY_ONCE))
                .triggerableAnim("shoot", RawAnimation.begin().then("animation.hunter.shoot", Animation.LoopType.PLAY_ONCE))
                .triggerableAnim("wall", RawAnimation.begin().then("animation.hunter.wall", Animation.LoopType.PLAY_ONCE))
                .triggerableAnim("body", RawAnimation.begin().then("animation.hunter.hitbody", Animation.LoopType.PLAY_ONCE))
                .triggerableAnim("startrun", RawAnimation.begin().then("animation.hunter.startrun", Animation.LoopType.PLAY_ONCE)));

    }

    private <T extends GeoAnimatable> PlayState eye10(AnimationState<T> tAnimationState) {
        tAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.hunter.eye10", Animation.LoopType.LOOP));
        return PlayState.CONTINUE;
    }
    private <T extends GeoAnimatable> PlayState eye11(AnimationState<T> tAnimationState) {
        tAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.hunter.eye11", Animation.LoopType.LOOP));
        return PlayState.CONTINUE;
    }
    private <T extends GeoAnimatable> PlayState eye12(AnimationState<T> tAnimationState) {
        tAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.hunter.eye12", Animation.LoopType.LOOP));
        return PlayState.CONTINUE;
    }
    private <T extends GeoAnimatable> PlayState eye20(AnimationState<T> tAnimationState) {
        tAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.hunter.eye20", Animation.LoopType.LOOP));
        return PlayState.CONTINUE;
    }
    private <T extends GeoAnimatable> PlayState eye21(AnimationState<T> tAnimationState) {
        tAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.hunter.eye21", Animation.LoopType.LOOP));
        return PlayState.CONTINUE;
    }
    private <T extends GeoAnimatable> PlayState eye22(AnimationState<T> tAnimationState) {
        tAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.hunter.eye22", Animation.LoopType.LOOP));
        return PlayState.CONTINUE;
    }
    private <T extends GeoAnimatable> PlayState things(AnimationState<T> tAnimationState) {
        tAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.hunter.things", Animation.LoopType.LOOP));
        return PlayState.CONTINUE;
    }

    private <T extends GeoAnimatable> PlayState predicate(AnimationState<T> tAnimationState) {

        if (this.getRushing()) {
            tAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.hunter.run", Animation.LoopType.LOOP));
            return PlayState.CONTINUE;
        }

        if(tAnimationState.isMoving()) {
            tAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.hunter.walk", Animation.LoopType.LOOP));
            return PlayState.CONTINUE;
        }
        tAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.hunter.idle", Animation.LoopType.LOOP));
        return PlayState.CONTINUE;
    }


    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    @Override
    public void performVariableRangedAttack(LivingEntity livingentity, float pVelocity, float down) {
        double d0 = this.distanceToSqr(livingentity);
        double d1 = (livingentity.getX()*(1 - down*0.05) - this.getX());
        double d2 = (livingentity.getY(0.4D)*(1 - down*0.05) - this.getY(0.4D));
        double d3 = (livingentity.getZ()*(1 - down*0.05) - this.getZ());
        double d4 = Math.sqrt(Math.sqrt(d0)) * 0.5D;

        this.playSound(this.getFireSound(), this.getSoundVolume(), this.getVoicePitch());

        Flechette flechette = new Flechette(this.level, this); //this.getRandom().triangle(d1, 2.297D * d4)
        flechette.setPos(this.getX() - (double)(this.getBbWidth() + 1.0F) * 0.5D * (double)Mth.sin(this.yBodyRot * ((float)Math.PI / 180F)), this.getEyeY() - (double)0.1F, this.getZ() + (double)(this.getBbWidth() + 1.0F) * 0.5D * (double) Mth.cos(this.yBodyRot * ((float)Math.PI / 180F)));
        flechette.shoot(d1, d2, d3, pVelocity, (float)(14 - this.level.getDifficulty().getId() * 4));
        this.level.addFreshEntity(flechette);

    }

    @Override
    public void performRangedAttack(LivingEntity pTarget, float pVelocity) {
    }

    @Override
    public void hitbody() {
        this.triggerAnim("onetime", "body");
        this.playSound(this.getImpactSound(), this.getSoundVolume(), this.getVoicePitch());
    }

    @Override
    public void hitwall() {
        this.triggerAnim("onetime", "wall");
        this.playSound(this.getImpactSound(), this.getSoundVolume(), this.getVoicePitch());
    }

    @Override
    public AABB getrushingbox() {
        AABB box = this.getBoundingBox();
        AABB a = new AABB(box.minX, box.minY, box.minZ, box.maxX, box.maxY, box.maxZ);
        a = a.inflate(0.5f);
        return a;
    }
}

