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
import net.minecraft.world.item.DyeColor;
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
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.List;


public class HL1ZombieScientist extends HalfLifeMonster implements GeoEntity, SmartBrainOwner<HL1ZombieScientist> {



    private AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);




    public static final EntityDataAccessor<Boolean> IS_ANGRY = SynchedEntityData.defineId(HL1ZombieScientist.class, EntityDataSerializers.BOOLEAN);
    public DyeColor getColor() {
        return DyeColor.byId(this.entityData.get(DATA_COAT_ID) & 15);
    }

    private static final EntityDataAccessor<Byte> DATA_COAT_ID = SynchedEntityData.defineId(HL1ZombieScientist.class, EntityDataSerializers.BYTE);
    public static final EntityDataAccessor<Integer> ID_SHIRT = SynchedEntityData.defineId(HL1ZombieScientist.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Integer> ID_TEXTURE = SynchedEntityData.defineId(HL1ZombieScientist.class, EntityDataSerializers.INT);


    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(IS_ANGRY, false);
        this.entityData.define(ID_TEXTURE, 1);
        this.entityData.define(ID_SHIRT, 0);
        this.entityData.define(DATA_COAT_ID, (byte)0);

    }
    protected boolean isangry() {
        return this.entityData.get(IS_ANGRY);
    }
    public int getShirt() {
        return this.entityData.get(ID_SHIRT);
    }
    protected void setShirt(int texture) {
        this.entityData.set(ID_SHIRT, texture);
    }

    public void setColor(DyeColor pDyeColor) {
        byte b0 = this.entityData.get(DATA_COAT_ID);
        this.entityData.set(DATA_COAT_ID, (byte)(b0 & 240 | pDyeColor.getId() & 15));
    }

    public int gettexture() {
        return this.entityData.get(ID_TEXTURE);
    }
    protected void settexture(int texture) {
        this.entityData.set(ID_TEXTURE, texture);
    }

    public void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        pCompound.putInt("ScientistTexture", this.gettexture() - 1 );
        pCompound.putInt("Shirt", this.getShirt() - 1 );
        pCompound.putByte("Color", (byte)this.getColor().getId());
    }

    public void readAdditionalSaveData(CompoundTag pCompound) {
        this.settexture(pCompound.getInt("ScientistTexture") + 1);
        this.setShirt(pCompound.getInt("Shirt") + 1);
        this.setColor(DyeColor.byId(pCompound.getByte("Color")));
        super.readAdditionalSaveData(pCompound);
    }


    public HL1ZombieScientist(EntityType type, Level level) {
        super(type, level);
        this.setPathfindingMalus(BlockPathTypes.WATER, -1.0F);
    }


    public static AttributeSupplier setAttributes() {
        return Monster.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 20D)
                .add(Attributes.ATTACK_DAMAGE, 2f)
                .add(Attributes.ATTACK_SPEED, 1.0f)
                .add(Attributes.ATTACK_KNOCKBACK, 1.0f)
                .add(Attributes.MOVEMENT_SPEED, 0.2f).build();
    }

    @Override
    protected void actuallyHurt(DamageSource p_21240_, float p_21241_) {
        if (p_21240_.is(DamageTypes.IN_WALL)) {
            return; }
        super.actuallyHurt(p_21240_, p_21241_);
    }

    @Override
    protected float getSoundVolume() {
        return 0.5f;
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
    public List<ExtendedSensor<HL1ZombieScientist>> getSensors() {
        return ObjectArrayList.of(
                new HurtBySensor<>(),
                new NearbyPlayersSensor<>(),
                new NearbyLivingEntitySensor<HL1ZombieScientist>()
                        .setPredicate((target, entity) ->
                            target instanceof Player ||
                                    target.getType().is(HLTags.EntityTypes.FACTION_COMBINE) || target instanceof IronGolem || target instanceof HalfLifeNeutral ||
                            target instanceof AbstractVillager));
    }



    @Override
    public BrainActivityGroup<HL1ZombieScientist> getCoreTasks() {
        return BrainActivityGroup.coreTasks(
                new LookAtTarget<>(),
                new MoveToWalkTarget<>());
    }



    @Override
    public BrainActivityGroup<HL1ZombieScientist> getIdleTasks() {
        return BrainActivityGroup.idleTasks(
                new FirstApplicableBehaviour<HL1ZombieScientist>(
                        new TargetOrRetaliate<>(),
                        new SetPlayerLookTarget<>(),
                        new SetRandomLookTarget<>()),
                new OneRandomBehaviour<>(
                        new SetRandomWalkTarget<>().setRadius(8, 5),
                        new Idle<>().runFor(entity -> entity.getRandom().nextInt(20, 50))));
    }


    @Override
    public BrainActivityGroup<HL1ZombieScientist> getFightTasks() { // These are the tasks that handle fighting
        return BrainActivityGroup.fightTasks(
                new InvalidateAttackTarget<>(),
                new SetWalkTargetToRandomSpotAroundAttackTarget<>(),
                new Retaliate<>()
        );

    }

     @Override
    public void tick() {
         super.tick();
     }





    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(new AnimationController<>(this, "controller", 0, this::predicate));
        controllerRegistrar.add(new AnimationController<>(this, "attack", state -> PlayState.STOP)
                .triggerableAnim("double", RawAnimation.begin().then("animation.zombie.doubleattack", Animation.LoopType.PLAY_ONCE)));
    }


    private <T extends GeoAnimatable> PlayState predicate(AnimationState<T> tAnimationState) {

        if(tAnimationState.isMoving() && this.isOnGround()) {
            tAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.zombie.walk", Animation.LoopType.LOOP));
            return PlayState.CONTINUE;
        }

        tAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.zombie.idle", Animation.LoopType.LOOP));
        return PlayState.CONTINUE;

    }


    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }


    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor p_33601_, DifficultyInstance p_33602_, MobSpawnType p_33603_, @javax.annotation.Nullable SpawnGroupData p_33604_, @javax.annotation.Nullable CompoundTag p_33605_) {
        RandomSource randomsource = p_33601_.getRandom();
        int i = randomsource.nextInt(4);
        int j = randomsource.nextInt(2);
        this.settexture(i+1);
        this.setShirt(j);
        return super.finalizeSpawn(p_33601_, p_33602_, p_33603_, p_33604_, p_33605_);
    }
}





