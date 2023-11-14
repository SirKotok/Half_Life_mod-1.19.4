package net.sirkotok.half_life_mod.entity.mob_geckolib.custom;


import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.BlockPos;
import net.minecraft.network.protocol.game.ClientboundSetPassengersPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.WalkTarget;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.fluids.FluidType;
import net.sirkotok.half_life_mod.entity.base.HalfLifeMonster;
import net.sirkotok.half_life_mod.entity.base.HalfLifeNeutral;
import net.sirkotok.half_life_mod.entity.brain.behaviour.BiteWhileJumpingBehavior;
import net.sirkotok.half_life_mod.entity.brain.behaviour.HeadCrabJumpBehavior;
import net.sirkotok.half_life_mod.entity.brain.behaviour.PushToWalkTarget;
import net.sirkotok.half_life_mod.entity.brain.behaviour.Retaliate;
import net.sirkotok.half_life_mod.sound.ModSounds;
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
import net.tslat.smartbrainlib.api.core.behaviour.custom.target.TargetOrRetaliate;
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


public class Manhack extends HalfLifeMonster implements GeoEntity, SmartBrainOwner<Manhack> {



    @Override
    protected void actuallyHurt(DamageSource p_21240_, float p_21241_) {
        if (p_21240_.is(DamageTypes.IN_WALL)) {
            return; }

        super.actuallyHurt(p_21240_, p_21241_);
    }





    @Override
    public boolean canBeLeashed(Player p_21418_) {
        return !this.isLeashed();
    }

    private AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);



    public static final EntityDataAccessor<Integer> CAN_CONTROL_TIMESTAMP = SynchedEntityData.defineId(Manhack.class, EntityDataSerializers.INT);
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(CAN_CONTROL_TIMESTAMP, 0);
    }


    public boolean cancontrol(){
        boolean flag1 = this.tickCount < 50;
        boolean flag2 = (this.tickCount - this.entityData.get(CAN_CONTROL_TIMESTAMP) > 50);
        return flag1 || flag2;
    }

    public void setcontroltimestamp(){
        this.entityData.set(CAN_CONTROL_TIMESTAMP, this.tickCount);
    }


    @Override
    public boolean isNoGravity() {
        return true;
    }

    public Manhack(EntityType type, Level level) {
        super(type, level);
        this.xpReward = 3;
    }


    public static AttributeSupplier setAttributes() {
        return Monster.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 10D)
                .add(Attributes.ATTACK_DAMAGE, 3f)
                .add(Attributes.ATTACK_SPEED, 1.0f)
                .add(Attributes.ATTACK_KNOCKBACK, 1.0f)
                .add(Attributes.MOVEMENT_SPEED, 0.22f).build();
    }


    public int getairemount() {
        int i = 0;
        for (int j = 1; j<20; j++){
            BlockPos pos = new BlockPos(this.blockPosition().getX(), this.blockPosition().getY() - j, this.blockPosition().getZ());
            BlockState blockstate = this.level.getBlockState(pos);
            if (!blockstate.isAir()) return i;
            i++;}
        return i;
    }




    @Override
    public void aiStep() {
        super.aiStep();

        if (this.tickCount % 4 == 0) {
            int m = random.nextFloat() < 0.5f ? 1 : -1;
        if (BrainUtils.getMemory(this, MemoryModuleType.WALK_TARGET) == null) this.setDeltaMovement(this.getDeltaMovement().x(), 0.2f * m * random.nextFloat(), this.getDeltaMovement().z());
        if (this.getairemount() < 3 && this.random.nextFloat() < 0.1f) this.setDeltaMovement(this.getDeltaMovement().x(), 0.3f, this.getDeltaMovement().z());
        if (this.getairemount() > 10) this.setDeltaMovement(this.getDeltaMovement().x(), -0.05, this.getDeltaMovement().z());

        List<Entity> list = this.level.getEntities(this, this.getBoundingBox(), obj -> obj instanceof LivingEntity && !(obj instanceof Manhack));
        for (Entity entity : list){
            if (entity instanceof LivingEntity living) {
                this.doHurtTarget(living);
                this.playSound(getGrindFleshSound(), getSoundVolume(), getVoicePitch());
                int i = random.nextFloat() < 0.5f ? 1 : -1;
                int j = random.nextFloat() < 0.5f ? 1 : -1;
                int k = random.nextFloat() < 0.5f ? 1 : -1;
                this.setDeltaMovement(i*2*random.nextFloat(), j*2*random.nextFloat(), k*2*random.nextFloat());
                this.setcontroltimestamp();
            }
        }
    }
    }


    protected SoundEvent getGrindFleshSound() {
        switch (this.random.nextInt(1,4)) {
            case 1:  return ModSounds.MANHACK_GRIND_FLESH_1.get();
            case 2:  return ModSounds.MANHACK_GRIND_FLESH_2.get();
            case 3:  return ModSounds.MANHACK_GRIND_FLESH_3.get();
        }
        return ModSounds.HEADCRAB_1_ATTACK_1.get();
    }

    protected SoundEvent getGrindSound(){
    switch (this.random.nextInt(1,6)) {
        case 1:  return ModSounds.MANHACK_GRIND1.get();
        case 2:  return ModSounds.MANHACK_GRIND2.get();
        case 3:  return ModSounds.MANHACK_GRIND3.get();
        case 4:  return ModSounds.MANHACK_GRIND4.get();
        case 5:  return ModSounds.MANHACK_GRIND5.get();
    }
        return ModSounds.HEADCRAB_1_ATTACK_1.get();
   }

    @Override
    protected SoundEvent getAmbientSound() {
        if (random.nextFloat() < 0.5f) return ModSounds.MANHACK_LOOP1.get();
        return ModSounds.MANHACK_LOOP2.get();
    }


    @Override
    public int getAmbientSoundInterval() {
        return 145;
    }

    @Override
    public void knockback(double pStrength, double pX, double pZ) {
        net.minecraftforge.event.entity.living.LivingKnockBackEvent event = net.minecraftforge.common.ForgeHooks.onLivingKnockBack(this, (float) pStrength, pX, pZ);
        if(event.isCanceled()) return;
        pStrength = event.getStrength();
        pX = event.getRatioX();
        pZ = event.getRatioZ();
        pStrength *= 1.0D - this.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE);
        if (!(pStrength <= 0.0D)) {
            this.hasImpulse = true;
            Vec3 vec3 = this.getDeltaMovement();
            Vec3 vec31 = (new Vec3(pX, 0.0D, pZ)).normalize().scale(pStrength);
            this.setDeltaMovement(vec3.x / 2.0D - vec31.x, Math.min(0.3D, vec3.y / 2.0D + pStrength), vec3.z / 2.0D - vec31.z);
        }
    }

    @Override
    protected float getSoundVolume() {
        return 0.5f;
    }

     @Override
    public boolean hurt(DamageSource pSource, float pAmount) {
        if (this.level.isClientSide) {
            return false;
        } else {
            if (!pSource.is(DamageTypeTags.AVOIDS_GUARDIAN_THORNS) && !pSource.is(DamageTypes.THORNS)) {
                Entity entity = pSource.getDirectEntity();
                if (entity instanceof LivingEntity living) {
                    if (living.getMainHandItem().isEmpty()) {
                    living.hurt(this.damageSources().thorns(this), 1.0F);
                }
                }
            }
            return super.hurt(pSource, pAmount);
        }
    }

    protected SoundEvent getHurtSound(DamageSource pSource) {
        Entity entity = pSource.getDirectEntity();
            if (entity instanceof LivingEntity living && !(living instanceof IronGolem)) {
                if (!pSource.is(DamageTypeTags.AVOIDS_GUARDIAN_THORNS) && !pSource.is(DamageTypes.THORNS)) {
                    if (living.getMainHandItem().isEmpty()) {
                        return this.getGrindFleshSound();
                    }
                }
            }
        return this.getGrindSound();
    }


    protected SoundEvent getDeathSound() {
        return ModSounds.MANHACK_DIES.get();
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
    public List<ExtendedSensor<Manhack>> getSensors() {
        return ObjectArrayList.of(
                new HurtBySensor<>(),
                new NearbyPlayersSensor<>(),
                new NearbyLivingEntitySensor<Manhack>()
                        .setPredicate((target, entity) ->
                            target instanceof Player || target instanceof IronGolem || target instanceof HalfLifeNeutral ||
                            target instanceof AbstractVillager || (target instanceof Enemy && !target.getType().is(ModTags.EntityTypes.FACTION_COMBINE))));
    }



    @Override
    public BrainActivityGroup<Manhack> getCoreTasks() {
        return BrainActivityGroup.coreTasks(
                new LookAtTarget<>(),
                new PushToWalkTarget<>().startCondition(entity -> this.cancontrol()),
                new CustomBehaviour<>(entity -> BrainUtils.clearMemory(this, MemoryModuleType.WALK_TARGET)).cooldownFor(entity -> 150)
        );

    }

    @Override
    public BrainActivityGroup<Manhack> getIdleTasks() { // These are the tasks that run when the mob isn't doing anything else (usually)
        return BrainActivityGroup.idleTasks(
                new FirstApplicableBehaviour<Manhack>(
                        new TargetOrRetaliate<>(),
                         new SetPlayerLookTarget<>(),
                        new SetRandomLookTarget<>()),
                        new SetRandomWalkTarget<>()

        );
    }




    @Override
    public BrainActivityGroup<Manhack> getFightTasks() { // These are the tasks that handle fighting
        return BrainActivityGroup.fightTasks(
                new InvalidateAttackTarget<>(),
                new Retaliate<>(),
                new SetWalkTargetToAttackTarget<>()
                );
    }





    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {


    //    controllerRegistrar.add(new AnimationController<>(this, "direction", 0, this::predicate));
        controllerRegistrar.add(new AnimationController<>(this, "spin", 0, this::allways));

        controllerRegistrar.add(new AnimationController<>(this, "die", state -> PlayState.STOP)
                .triggerableAnim("die", RawAnimation.begin().then("animation.manhack.die", Animation.LoopType.PLAY_ONCE)));


    }



  //  private <T extends GeoAnimatable> PlayState predicate(AnimationState<T> tAnimationState) {
//
  // }


    private <T extends GeoAnimatable> PlayState allways(AnimationState<T> tAnimationState) {
        tAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.manhack.spin", Animation.LoopType.LOOP));
        return PlayState.CONTINUE;

    }




    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

}

