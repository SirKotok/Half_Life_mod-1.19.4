package net.sirkotok.half_life_mod.entity.mob_geckolib.custom;


import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraftforge.event.ForgeEventFactory;
import net.sirkotok.half_life_mod.entity.HalfLifeEntities;
import net.sirkotok.half_life_mod.entity.base.HalfLifeMonster;
import net.sirkotok.half_life_mod.entity.base.HalfLifeNeutral;
import net.sirkotok.half_life_mod.entity.brain.behaviour.BiteWhileJumpingBehavior;
import net.sirkotok.half_life_mod.entity.brain.behaviour.HeadCrabJumpBehavior;
import net.sirkotok.half_life_mod.entity.brain.behaviour.Retaliate;
import net.sirkotok.half_life_mod.entity.brain.behaviour.SetWalkTargetToRandomSpotAroundAttackTarget;
import net.sirkotok.half_life_mod.sound.HalfLifeSounds;
import net.sirkotok.half_life_mod.util.HLTags;
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
import net.tslat.smartbrainlib.api.core.behaviour.custom.target.TargetOrRetaliate;
import net.tslat.smartbrainlib.api.core.sensor.ExtendedSensor;
import net.tslat.smartbrainlib.api.core.sensor.vanilla.HurtBySensor;
import net.tslat.smartbrainlib.api.core.sensor.vanilla.NearbyLivingEntitySensor;
import net.tslat.smartbrainlib.api.core.sensor.vanilla.NearbyPlayersSensor;
import net.tslat.smartbrainlib.util.BrainUtils;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.List;


public class Baby_Headcrab extends HalfLifeMonster implements GeoEntity, SmartBrainOwner<Baby_Headcrab> {

    @Override
    public boolean canBeLeashed(Player p_21418_) {
        return !this.isLeashed();
    }


    private AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);




    public static final EntityDataAccessor<Boolean> IS_ANGRY = SynchedEntityData.defineId(Baby_Headcrab.class, EntityDataSerializers.BOOLEAN);

    public static final EntityDataAccessor<Integer> AGE = SynchedEntityData.defineId(Baby_Headcrab.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Integer> AGEUP = SynchedEntityData.defineId(Baby_Headcrab.class, EntityDataSerializers.INT);


    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(IS_ANGRY, false);
        this.entityData.define(AGE, 0);
        this.entityData.define(AGEUP, 0);
    }
    protected boolean isangry() {
        return this.entityData.get(IS_ANGRY);
    }


    public void setage(int age) {
        this.entityData.set(AGE, age);
    }
    public int getage() {
        return this.entityData.get(AGE);
    }

    public void setageup(int age) {
        this.entityData.set(AGEUP, age);
    }
    public int getagetoageup() {
        return this.entityData.get(AGEUP);
    }



    public void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        pCompound.putInt("Agup", this.getagetoageup() - 1 );
        pCompound.putInt("Ag", this.getage() - 1 );
    }

    public void readAdditionalSaveData(CompoundTag pCompound) {
        this.setage(pCompound.getInt("Ag") + 1);
        this.setageup(pCompound.getInt("Agup") + 1);
        super.readAdditionalSaveData(pCompound);
    }


    public Baby_Headcrab(EntityType type, Level level) {
        super(type, level);
        this.setPathfindingMalus(BlockPathTypes.WATER, -1.0F);
    }


    public static AttributeSupplier setAttributes() {
        return Monster.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 2D)
                .add(Attributes.ATTACK_DAMAGE, 1f)
                .add(Attributes.ATTACK_SPEED, 1.0f)
                .add(Attributes.ATTACK_KNOCKBACK, 1.0f)
                .add(Attributes.MOVEMENT_SPEED, 0.4f).build();
    }

    @Override
    protected void actuallyHurt(DamageSource p_21240_, float p_21241_) {
        if (p_21240_.is(DamageTypes.IN_WALL)) {
            return; }

        super.actuallyHurt(p_21240_, p_21241_);
    }

    @Override
    protected float getSoundVolume() {
        return 0.35f;
    }

    @Override
    public float getVoicePitch() {
        return 3;
    }

    protected SoundEvent getJumpSound() {
        switch (this.random.nextInt(1,4)) {
            case 1:  return HalfLifeSounds.HEADCRAB_1_ATTACK_1.get();
            case 2:  return HalfLifeSounds.HEADCRAB_1_ATTACK_2.get();
            case 3:  return HalfLifeSounds.HEADCRAB_1_ATTACK_3.get();
        }
        return HalfLifeSounds.HEADCRAB_1_ATTACK_1.get();
    }

    protected SoundEvent getBiteSound() {
        return HalfLifeSounds.HEADCRAB_1_HEADBITE.get();
    }

    protected SoundEvent getHurtSound(DamageSource p_33034_) {
        switch (this.random.nextInt(1,4)) {
            case 1:  return HalfLifeSounds.HEADCRAB_1_PAIN_1.get();
            case 2:  return HalfLifeSounds.HEADCRAB_1_PAIN_2.get();
            case 3:  return HalfLifeSounds.HEADCRAB_1_PAIN_3.get();
        }
        return HalfLifeSounds.HEADCRAB_1_PAIN_1.get();
    }

    protected SoundEvent getDeathSound() {
        switch (this.random.nextInt(1,3)) {
            case 1:  return HalfLifeSounds.HEADCRAB_1_DIE_1.get();
            case 2:  return HalfLifeSounds.HEADCRAB_1_DIE_2.get();
        }
        return HalfLifeSounds.HEADCRAB_1_DIE_1.get();
    }


    protected SoundEvent getAmbientSound() {
        if (this.isangry()) {
            switch (this.random.nextInt(1,3)) {
                case 1:  return HalfLifeSounds.HEADCRAB_1_ALERT_1.get();
                case 2:  return HalfLifeSounds.HEADCRAB_1_ALERT_2.get();
            }
        }
        switch (this.random.nextInt(1,6)) {
            case 1:  return HalfLifeSounds.HEADCRAB_1_IDLE_1.get();
            case 2:  return HalfLifeSounds.HEADCRAB_1_IDLE_2.get();
            case 3:  return HalfLifeSounds.HEADCRAB_1_IDLE_3.get();
            case 4:  return HalfLifeSounds.HEADCRAB_1_IDLE_4.get();
            case 5:  return HalfLifeSounds.HEADCRAB_1_IDLE_5.get();
        }
        return HalfLifeSounds.HEADCRAB_1_ALERT_1.get();
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
    public List<ExtendedSensor<Baby_Headcrab>> getSensors() {
        return ObjectArrayList.of(
                new HurtBySensor<>(),
                new NearbyPlayersSensor<>(),
                new NearbyLivingEntitySensor<Baby_Headcrab>()
                        .setPredicate((target, entity) ->
                            target instanceof Player ||
                                    target.getType().is(HLTags.EntityTypes.FACTION_COMBINE) || target instanceof IronGolem || target instanceof HalfLifeNeutral ||
                            target instanceof AbstractVillager));
    }



    @Override
    public BrainActivityGroup<Baby_Headcrab> getCoreTasks() {
        return BrainActivityGroup.coreTasks(
                new LookAtTarget<>(),
                new MoveToWalkTarget<>());
    }



    @Override
    public BrainActivityGroup<Baby_Headcrab> getIdleTasks() {
        return BrainActivityGroup.idleTasks(
                new FirstApplicableBehaviour<Baby_Headcrab>(
                        new TargetOrRetaliate<>(),
                        new SetPlayerLookTarget<>(),
                        new SetRandomLookTarget<>()),
                new OneRandomBehaviour<>(
                        new SetRandomWalkTarget<>().setRadius(8, 5),
                        new Idle<>().runFor(entity -> entity.getRandom().nextInt(20, 50))));
    }


    @Override
    public BrainActivityGroup<Baby_Headcrab> getFightTasks() { // These are the tasks that handle fighting
        return BrainActivityGroup.fightTasks(
                new InvalidateAttackTarget<>(),
                new SetWalkTargetToRandomSpotAroundAttackTarget<>(),
                new Retaliate<>(),
                new BiteWhileJumpingBehavior<>(30, getBiteSound(), -1).startCondition(entity -> !isOnGround()).cooldownFor(entity -> 20),
                new HeadCrabJumpBehavior<>(0, getJumpSound(), null).whenStarting(entity -> triggerAnim("jump", "jump")).cooldownFor(entity -> 80)
        );

    }

     @Override
    public void tick() {
         super.tick();

         if (!this.level.isClientSide() && this.tickCount % 20 == 0) this.setage(this.getage()+1);
         if (this.getage() > this.getagetoageup() && !this.level.isClientSide() && this.tickCount % 5 == 0) {
             int i = RandomSource.create().nextInt(-4, 8);
             this.doage((ServerLevel) this.level, i);
         }
     }


    @Override
    public boolean shouldDropExperience() {
        return false;
    }

    public void doage(ServerLevel level, int i){
        LivingEntity summon = null;
        if (i < 0) i = RandomSource.create().nextInt(4);
        if (i == 0) {summon = HalfLifeEntities.HEADCRAB_HL1.get().create(level);}
        else if (i == 1) {summon = HalfLifeEntities.HEADCRAB_HL2.get().create(level);}
        else if (i == 3) {summon = HalfLifeEntities.HEADCRAB_HLA.get().create(level);}
        else if (i == 4) {summon = HalfLifeEntities.HEADCRAB_POISON_HL2.get().create(level);}
        else if (i == 5) {summon = HalfLifeEntities.HEADCRAB_POISON_HLA.get().create(level);}
        else if (i == 6) {summon = HalfLifeEntities.HEADCRAB_FAST.get().create(level);}
        else if (i == 7) {summon = HalfLifeEntities.HEADCRAB_ARMORED.get().create(level);}
         if (summon != null) {
             summon.moveTo(this.position());
             ForgeEventFactory.onFinalizeSpawn((Mob) summon, (ServerLevelAccessor) level, level.getCurrentDifficultyAt(summon.blockPosition()), this.getSpawnType(), null, null);
             level.addFreshEntity(summon);
             if (this.hasCustomName()) summon.setCustomName(this.getCustomName());
             if (summon instanceof Headcrab_1) ((Headcrab_1) summon).setNoDrop(true);
             if (summon instanceof Headcrab_2) ((Headcrab_2) summon).setNoDrop(true);
             if (summon instanceof Headcrab_3) ((Headcrab_3) summon).setNoDrop(true);
             if (summon instanceof Headcrab_Poison_3) ((Headcrab_Poison_3) summon).setNoDrop(true);
             if (summon instanceof Headcrab_Poison_2) ((Headcrab_Poison_2) summon).setNoDrop(true);
             if (summon instanceof Headcrab_Fast) ((Headcrab_Fast) summon).setNoDrop(true);
             BrainUtils.setMemory(summon, MemoryModuleType.ATTACK_TARGET, BrainUtils.getMemory(this, MemoryModuleType.ATTACK_TARGET));
             this.discard();
         }
     }




    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(new AnimationController<>(this, "controller", 0, this::predicate));
        controllerRegistrar.add(new AnimationController<>(this, "jump", state -> PlayState.STOP)
                .triggerableAnim("jump", RawAnimation.begin().then("animation.bbhc.jump", Animation.LoopType.PLAY_ONCE)));
    }


    private <T extends GeoAnimatable> PlayState predicate(AnimationState<T> tAnimationState) {



        if(tAnimationState.isMoving() && this.isOnGround()) {
            tAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.bbhc.walk", Animation.LoopType.LOOP));
            return PlayState.CONTINUE;
        }

        tAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.bbhc.idle", Animation.LoopType.LOOP));
        return PlayState.CONTINUE;

    }


    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }


    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor pLevel, DifficultyInstance pDifficulty, MobSpawnType pReason, @Nullable SpawnGroupData pSpawnData, @Nullable CompoundTag pDataTag) {
        this.setageup(RandomSource.create().nextInt(30, 600));
        return super.finalizeSpawn(pLevel, pDifficulty, pReason, pSpawnData, pDataTag);
    }
}





