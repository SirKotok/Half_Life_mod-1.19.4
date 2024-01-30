package net.sirkotok.half_life_mod.entity.mob_geckolib.custom;


import com.mojang.authlib.GameProfile;
import com.mojang.util.UUIDTypeAdapter;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.ForgeEventFactory;
import net.sirkotok.half_life_mod.entity.HalfLifeEntities;
import net.sirkotok.half_life_mod.entity.base.HalfLifeMonster;
import net.sirkotok.half_life_mod.entity.base.HalfLifeNeutral;
import net.sirkotok.half_life_mod.entity.brain.behaviour.*;
import net.sirkotok.half_life_mod.sound.HalfLifeSounds;
import net.sirkotok.half_life_mod.util.CommonSounds;
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
import net.tslat.smartbrainlib.api.core.behaviour.custom.path.SetWalkTargetToAttackTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.target.InvalidateAttackTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.target.SetAttackTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.target.SetPlayerLookTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.target.SetRandomLookTarget;
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

    public static final EntityDataAccessor<String> UUID_STRING_P = SynchedEntityData.defineId(HL1ZombieScientist.class, EntityDataSerializers.STRING);
    public static final EntityDataAccessor<String> PLAYER_NAME_STRING = SynchedEntityData.defineId(HL1ZombieScientist.class, EntityDataSerializers.STRING);


    public static final EntityDataAccessor<Boolean> MOVING = SynchedEntityData.defineId(HL1ZombieScientist.class, EntityDataSerializers.BOOLEAN);
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(IS_ANGRY, false);
        this.entityData.define(MOVING, false);
        this.entityData.define(ID_TEXTURE, 1);
        this.entityData.define(ID_SHIRT, 0);
        this.entityData.define(DATA_COAT_ID, (byte)0);
        this.entityData.define(UUID_STRING_P, "");
        this.entityData.define(PLAYER_NAME_STRING, "");

    }
    protected boolean isangry() {
        return this.entityData.get(IS_ANGRY);
    }

    protected boolean moving() {
        return this.entityData.get(MOVING);
    }

    public String getplayerUUID() {
        return this.entityData.get(UUID_STRING_P);
    }
    public String getplayername() {
        return this.entityData.get(PLAYER_NAME_STRING);
    }
    public void setPlayerUUID(String playerUUID) {
        this.entityData.set(UUID_STRING_P, playerUUID);
    }
    public void setPlayerName(String playerName) {
        this.entityData.set(PLAYER_NAME_STRING, playerName);
    }


    protected void makemoving(boolean t) {this.entityData.set(MOVING, t);
    }
    public int getShirt() {
        return this.entityData.get(ID_SHIRT);
    }
    public void setShirt(int texture) {
        this.entityData.set(ID_SHIRT, texture);
    }

    public void setColor(DyeColor pDyeColor) {
        byte b0 = this.entityData.get(DATA_COAT_ID);
        this.entityData.set(DATA_COAT_ID, (byte)(b0 & 240 | pDyeColor.getId() & 15));
    }

    public int gettexture() {
        return this.entityData.get(ID_TEXTURE);
    }
    public void settexture(int texture) {
        this.entityData.set(ID_TEXTURE, texture);
    }

    public void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        pCompound.putInt("ScientistTexture", this.gettexture() - 1 );
        pCompound.putInt("Shirt", this.getShirt() - 1 );
        pCompound.putString("pUUID_String", this.getplayerUUID() );
        pCompound.putString("pPlayer_name", this.getplayername() );
        pCompound.putByte("Color", (byte)this.getColor().getId());
    }

    public void readAdditionalSaveData(CompoundTag pCompound) {
        this.settexture(pCompound.getInt("ScientistTexture") + 1);
        this.setShirt(pCompound.getInt("Shirt") + 1);
        this.setPlayerUUID(pCompound.getString("pUUID_String"));
        this.setPlayerName(pCompound.getString("pPlayer_name"));
        this.setColor(DyeColor.byId(pCompound.getByte("Color")));
        super.readAdditionalSaveData(pCompound);
    }

    public GameProfile getprofile() {
        if (!this.getplayerUUID().equals("")) {return new GameProfile(UUIDTypeAdapter.fromString(this.getplayerUUID()), this.getplayername());}
        return null;
    }


    public HL1ZombieScientist(EntityType type, Level level) {
        super(type, level);
        this.setPathfindingMalus(BlockPathTypes.WATER, -1.0F);
    }


    public static AttributeSupplier setAttributes() {
        return Monster.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 20D)
                .add(Attributes.ATTACK_DAMAGE, 4f)
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



    protected SoundEvent getMissSound() {
        switch (this.random.nextInt(1,3)) {
            case 1:  return HalfLifeSounds.CLAW_MISS1.get();
            case 2:  return HalfLifeSounds.CLAW_MISS2.get();
        }
        return HalfLifeSounds.HEADCRAB_1_PAIN_1.get();
    }

    protected SoundEvent getHurtSound(DamageSource p_33034_) {
        switch (this.random.nextInt(1,3)) {
            case 1:  return HalfLifeSounds.ZO_PAIN1.get();
            case 2:  return HalfLifeSounds.ZO_PAIN2.get();
        }
        return HalfLifeSounds.HEADCRAB_1_PAIN_1.get();
    }

    protected SoundEvent getLongSound() {return HalfLifeSounds.ZO_ATTACK1.get();}
    protected SoundEvent getShortSound() {return HalfLifeSounds.ZO_ATTACK2.get();}

    protected SoundEvent getAmbientSound() {
        if (this.isangry()) {
            switch (this.random.nextInt(1,4)) {
                case 1:  return HalfLifeSounds.ZO_ALERT10.get();
                case 2:  return HalfLifeSounds.ZO_ALERT20.get();
                case 3:  return HalfLifeSounds.ZO_ALERT30.get();
            }
        }
        switch (this.random.nextInt(1,5)) {
            case 1:  return HalfLifeSounds.ZO_IDLE1.get();
            case 2:  return HalfLifeSounds.ZO_IDLE2.get();
            case 3:  return HalfLifeSounds.ZO_IDLE3.get();
            case 4:  return HalfLifeSounds.ZO_IDLE4.get();
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
                        new TargetOrRetaliateHLT<>(),
                        new SetPlayerLookTarget<>(),
                        new SetRandomLookTarget<>()),
                new OneRandomBehaviour<>(
                        new SetRandomWalkTarget<>().setRadius(13, 7),
                        new Idle<>().runFor(entity -> entity.getRandom().nextInt(10, 40))));
    }


    @Override
    public BrainActivityGroup<HL1ZombieScientist> getFightTasks() { // These are the tasks that handle fighting
        return BrainActivityGroup.fightTasks(
                new InvalidateAttackTarget<>(),
                new SetWalkTargetToAttackTarget<>(),
                new Retaliate<>(),
               new OneRandomBehaviour<>(
               new ConfigurableAnimatableMeleeAttack<HL1ZombieScientist>(13, 0.1f, 1.5f, 1, null, 0, CommonSounds.getClawHitSound(), null, this.getMissSound(), this.getLongSound(), this.getShortSound())
                .whenStarting(entity -> triggerAnim("onetime", "big"))
                .cooldownFor(entity -> random.nextInt(10, 15)),
                new DoubleMeleeAttack<HL1ZombieScientist>(21, 13, 0, 1, 1, null , 0, CommonSounds.getClawHitSound(), null, this.getMissSound(), this.getLongSound(), this.getShortSound())
                        .whenStarting(entity ->triggerAnim("onetime", "double"))
                        .cooldownFor(entity -> random.nextInt(10, 15)))

        );

    }

    @Override
    public void push(Entity pEntity) {
        if (!this.isPassengerOfSameVehicle(pEntity)) {
            if (!pEntity.noPhysics && !this.noPhysics) {
                double d0 = pEntity.getX() - this.getX();
                double d1 = pEntity.getZ() - this.getZ();
                double d2 = Mth.absMax(d0, d1);
                if (d2 >= (double)0.01F) {
                    d2 = Math.sqrt(d2);
                    d0 /= d2;
                    d1 /= d2;
                    double d3 = 1.0D / d2;
                    if (d3 > 1.0D) {
                        d3 = 1.0D;
                    }

                    d0 *= d3;
                    d1 *= d3;
                    d0 *= (double)0.05F;
                    d1 *= (double)0.05F;
                    if (this.isPushable()) {
                        this.push(-d0, 0.0D, -d1);
                    }

                    if (pEntity.isPushable()) {
                        pEntity.push(d0, 0.0D, d1);
                    }
                }

            }
        }
    }


    @Nullable
    @Override
    public LivingEntity getControllingPassenger() {
        return null;
    }

    @Override
    public float getVoicePitch() {
        return 1+random.nextFloat()*random.nextFloat();
    }



     @Override
    public void tick() {
         super.tick();

         if (this.getFirstPassenger() instanceof HalfLifeMonster monster) {
             if (monster.getLastHurtByMob() != null) {
                 BrainUtils.setTargetOfEntity(this, monster.getLastHurtByMob());
             }
         }



         if (this.tickCount > 10 && (this.getPassengers().isEmpty() || !(this.getFirstPassenger() instanceof Headcrab_1))) {
             this.setHealth(0);
         }
     }





    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(new AnimationController<>(this, "controller", 0, this::predicate));
        controllerRegistrar.add(new AnimationController<>(this, "onetime", state -> PlayState.STOP)
                .triggerableAnim("double", RawAnimation.begin().then("animation.zombie.doubleattack", Animation.LoopType.PLAY_ONCE))
                .triggerableAnim("big", RawAnimation.begin().then("animation.zombie.bigattack", Animation.LoopType.PLAY_ONCE))
        );
    }


    private <T extends GeoAnimatable> PlayState predicate(AnimationState<T> tAnimationState) {

        if(tAnimationState.isMoving()) {
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

        if (!p_33603_.equals(MobSpawnType.CONVERSION)) {RandomSource randomsource = p_33601_.getRandom();
        int i = randomsource.nextInt(4);
        int j = randomsource.nextInt(2);
        this.settexture(i+1);
        this.setShirt(j);
            Headcrab_1 summon = HalfLifeEntities.HEADCRAB_HL1.get().create(level);
            if (summon != null) {
                summon.moveTo(this.position());
                summon.startRiding(this);
                ForgeEventFactory.onFinalizeSpawn((Mob) summon, (ServerLevelAccessor) level, level.getCurrentDifficultyAt(summon.blockPosition()), MobSpawnType.TRIGGERED, null, null);
                level.addFreshEntity(summon);
            }
        }


        return super.finalizeSpawn(p_33601_, p_33602_, p_33603_, p_33604_, p_33605_);
    }
}





