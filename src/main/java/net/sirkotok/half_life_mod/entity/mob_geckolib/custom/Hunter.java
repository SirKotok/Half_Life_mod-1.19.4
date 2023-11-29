package net.sirkotok.half_life_mod.entity.mob_geckolib.custom;


import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.BlockPos;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
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
import net.minecraft.world.phys.Vec3;
import net.sirkotok.half_life_mod.entity.base.HalfLifeMonster;
import net.sirkotok.half_life_mod.entity.base.HalfLifeNeutral;
import net.sirkotok.half_life_mod.entity.brain.behaviour.*;
import net.sirkotok.half_life_mod.entity.modinterface.VariableRangedMob;
import net.sirkotok.half_life_mod.entity.projectile.AcidBall;
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
import net.tslat.smartbrainlib.api.core.behaviour.custom.move.StrafeTarget;
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

import java.util.List;


public class Hunter extends HalfLifeMonster implements GeoEntity, RangedAttackMob, VariableRangedMob, SmartBrainOwner<Hunter> {


    private AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);




    public static final EntityDataAccessor<Boolean> ISOPEN = SynchedEntityData.defineId(Hunter.class, EntityDataSerializers.BOOLEAN);
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(ISOPEN, false);

    }


    public boolean getOpen(){
        return this.entityData.get(ISOPEN);
    }
    public void setOpen(boolean b){
        this.entityData.set(ISOPEN, b);
    }





    public int getphase(){
        return this.getHealth()/this.getMaxHealth() > 0.4f ? 10 : 1;
    }


    public Hunter(EntityType type, Level level) {
        super(type, level);
        this.xpReward = 20;
    }







    public static AttributeSupplier setAttributes() {
        return Monster.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 50D)
                .add(Attributes.ATTACK_DAMAGE, 12f).add(Attributes.ARMOR, 20)
                .add(Attributes.ATTACK_SPEED, 1.0f).add(Attributes.KNOCKBACK_RESISTANCE, 0.9f)
                .add(Attributes.ATTACK_KNOCKBACK, 1.6f)
                .add(Attributes.MOVEMENT_SPEED, 0.25f).build();
    }




   /*

    @Override
    protected SoundEvent getAmbientSound() {
        if (random.nextFloat() < 0.5f) return ModSounds.MANHACK_LOOP1.get();
        return ModSounds.MANHACK_LOOP2.get();
    }

    @Override
    public void playAmbientSound() {
        SoundEvent soundevent = this.getAmbientSound();
        if (soundevent != null) {
            this.playSound(soundevent, this.getSoundVolume()/4, this.getVoicePitch());
        }
    }

    protected SoundEvent getDeathSound() {
        return ModSounds.MANHACK_DIES.get();
    }




    protected SoundEvent getHurtSound(DamageSource pSource) {
        return this.getGrindSound();
    }
 */


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
                new MoveToWalkTarget<>()
        );

    }

    @Override
    public BrainActivityGroup<Hunter> getIdleTasks() { // These are the tasks that run when the mob isn't doing anything else (usually)
        return BrainActivityGroup.idleTasks(
                new FirstApplicableBehaviour<Hunter>(
                        new TargetOrRetaliateHLT<>(),
                         new SetPlayerLookTarget<>(),
                        new SetRandomLookTarget<>()),
               new OneRandomBehaviour<>(
                new SetRandomWalkTarget<>(),
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
                new CustomBehaviour<>(entity -> BrainUtils.clearMemory(this, MemoryModuleType.WALK_TARGET)).cooldownFor(entity -> 200),
                new Retaliate<>(),
                new OneRandomBehaviour<>(
                        new SetRandomWalkTarget<>().setRadius(20, 10).speedModifier(1.6f),
                        new SetRandomWalkTarget<>().setRadius(10, 5).speedModifier(1.8f),
                        new Idle<>().runFor(entity -> entity.getRandom().nextInt(10, 20)),
                        new SetWalkTargetToRandomSpotAroundAttackTarget<>().radius(10, 5).speedMod(1.8f)
                ).cooldownFor(entity -> this.getphase()*5),
                new SetWalkTargetToRandomSpotAroundAttackTarget<>().speedMod(1.8f).startCondition(entity -> this.distanceTo(HLperUtil.TargetOrThis(this)) > 14),
                new FirstApplicableBehaviour<>(
                        new OneRandomBehaviour<>(
                                new ConfigurableAnimatableMeleeAttack<Hunter>(9, 0.5f, 1, 1, null, 0, null, null)
                                        .whenStarting(entity -> triggerAnim("onetime", "attack1"))
                                        .cooldownFor(entity -> random.nextInt(10, 20)),
                                new ConfigurableAnimatableMeleeAttack<Hunter>(9, 0.5f, 1, 1, null, 0, null, null)
                                        .whenStarting(entity -> triggerAnim("onetime", "attack1"))
                                        .cooldownFor(entity -> random.nextInt(10, 20))
                        ),
                        new StopAndShootOverTime<>(50, 1, 12, 40, 4, 1f, false).cooldownFor(entity -> random.nextInt(100, 200))
                                .whenStarting(entity -> triggerAnim("onetime", "shoot")),
                        new StopAndShootOverTime<>(50, 1, 12, 40, 4, 1f, true).cooldownFor(entity -> random.nextInt(100, 200))
                                .whenStarting(entity -> triggerAnim("onetime", "shoot")))
                );
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
                .triggerableAnim("attack1", RawAnimation.begin().then("animation.hunter.attack", Animation.LoopType.PLAY_ONCE))
                .triggerableAnim("attack2", RawAnimation.begin().then("animation.hunter.attack2", Animation.LoopType.PLAY_ONCE))
                .triggerableAnim("shoot", RawAnimation.begin().then("animation.hunter.shoot", Animation.LoopType.PLAY_ONCE))
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


     //   this.playSound(getSpitSound(), this.getSoundVolume(), 1.2f);


        AcidBall acidBall = new AcidBall(this.level, this, d1, d2, d3); //this.getRandom().triangle(d1, 2.297D * d4)
        acidBall.setPos(this.getX() - (double)(this.getBbWidth() + 1.0F) * 0.5D * (double)Mth.sin(this.yBodyRot * ((float)Math.PI / 180F)), this.getEyeY() - (double)0.1F, this.getZ() + (double)(this.getBbWidth() + 1.0F) * 0.5D * (double) Mth.cos(this.yBodyRot * ((float)Math.PI / 180F)));
        this.level.addFreshEntity(acidBall);

    }

    @Override
    public void performRangedAttack(LivingEntity pTarget, float pVelocity) {
    }

}

