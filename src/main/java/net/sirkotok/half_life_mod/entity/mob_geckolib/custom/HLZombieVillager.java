package net.sirkotok.half_life_mod.entity.mob_geckolib.custom;


import com.mojang.authlib.GameProfile;
import com.mojang.logging.LogUtils;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.Dynamic;
import com.mojang.util.UUIDTypeAdapter;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.npc.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraftforge.event.ForgeEventFactory;
import net.sirkotok.half_life_mod.entity.HalfLifeEntities;
import net.sirkotok.half_life_mod.entity.base.HalfLifeMonster;
import net.sirkotok.half_life_mod.entity.base.HalfLifeNeutral;
import net.sirkotok.half_life_mod.entity.brain.behaviour.ConfigurableAnimatableMeleeAttack;
import net.sirkotok.half_life_mod.entity.brain.behaviour.Retaliate;
import net.sirkotok.half_life_mod.entity.brain.behaviour.TargetOrRetaliateHLT;
import net.sirkotok.half_life_mod.sound.HalfLifeSounds;
import net.sirkotok.half_life_mod.util.CommonSounds;
import net.sirkotok.half_life_mod.util.HLTags;
import net.sirkotok.half_life_mod.util.InfightingUtil;
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
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.List;


public class HLZombieVillager extends HalfLifeMonster implements GeoEntity, SmartBrainOwner<HLZombieVillager> {
    private static final Logger LOGGER = LogUtils.getLogger();

    //TODO: make actually work
    private AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);




    public static final EntityDataAccessor<Boolean> IS_ANGRY = SynchedEntityData.defineId(HLZombieVillager.class, EntityDataSerializers.BOOLEAN);

    private static final EntityDataAccessor<VillagerData> DATA_VILLAGER_DATA = SynchedEntityData.defineId(HLZombieVillager.class, EntityDataSerializers.VILLAGER_DATA);

    protected void defineSynchedData() {
        super.defineSynchedData();


        this.entityData.define(IS_ANGRY, false);
        this.entityData.define(DATA_VILLAGER_DATA, new VillagerData(VillagerType.PLAINS, VillagerProfession.NONE, 1));
    }




    public void setVillagerData(VillagerData p_35437_) {
        this.entityData.set(DATA_VILLAGER_DATA, p_35437_);
    }

    public VillagerData getVillagerData() {
        return this.entityData.get(DATA_VILLAGER_DATA);
    }



    protected boolean isangry() {
        return this.entityData.get(IS_ANGRY);
    }


    public void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        VillagerData.CODEC.encodeStart(NbtOps.INSTANCE, this.getVillagerData()).resultOrPartial(LOGGER::error).ifPresent((p_35454_) -> {
            pCompound.put("VillagerData", p_35454_);
        });

    }

    public void readAdditionalSaveData(CompoundTag pCompound) {
        if (pCompound.contains("VillagerData", 10)) {
            DataResult<VillagerData> dataresult = VillagerData.CODEC.parse(new Dynamic<>(NbtOps.INSTANCE, pCompound.get("VillagerData")));
            dataresult.resultOrPartial(LOGGER::error).ifPresent(this::setVillagerData);
        }
        super.readAdditionalSaveData(pCompound);
    }




    public HLZombieVillager(EntityType type, Level level) {
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
        if (this.getheadcrabtype() == 2) {
        switch (this.random.nextInt(1,7)) {
            case 1:  return HalfLifeSounds.ZOMBIE_PAIN1.get();
            case 2:  return HalfLifeSounds.ZOMBIE_PAIN2.get();
            case 3:  return HalfLifeSounds.ZOMBIE_PAIN3.get();
            case 4:  return HalfLifeSounds.ZOMBIE_PAIN4.get();
            case 5:  return HalfLifeSounds.ZOMBIE_PAIN5.get();
            case 6:  return HalfLifeSounds.ZOMBIE_PAIN6.get();
        }
        }

        switch (this.random.nextInt(1,3)) {
            case 1:  return HalfLifeSounds.ZO_PAIN1.get();
            case 2:  return HalfLifeSounds.ZO_PAIN2.get();
        }

        return HalfLifeSounds.HEADCRAB_1_PAIN_1.get();
    }


    protected SoundEvent getHitSound() {
        if (this.getheadcrabtype() == 2) return HalfLifeSounds.ZOMBIE_HIT.get();
        return CommonSounds.getClawHitSound();
    }

    protected SoundEvent getLongSound() {return HalfLifeSounds.ZO_ATTACK1.get();}
    protected SoundEvent getShortSound() {return HalfLifeSounds.ZO_ATTACK2.get();}

    protected SoundEvent getAmbientSound() {

        if (this.getheadcrabtype() == 2) {

        if (this.isangry()) {
            switch (this.random.nextInt(1,4)) {
                case 1:  return HalfLifeSounds.ZOMBIE_ALERT1.get();
                case 2:  return HalfLifeSounds.ZOMBIE_ALERT2.get();
                case 3:  return HalfLifeSounds.ZOMBIE_ALERT3.get();
            }
        }
        switch (this.random.nextInt(1,15)) {
            case 1:  return HalfLifeSounds.ZOMBIE_VOICE_IDLE1.get();
            case 2:  return HalfLifeSounds.ZOMBIE_VOICE_IDLE2.get();
            case 3:  return HalfLifeSounds.ZOMBIE_VOICE_IDLE3.get();
            case 4:  return HalfLifeSounds.ZOMBIE_VOICE_IDLE4.get();
            case 5:  return HalfLifeSounds.ZOMBIE_VOICE_IDLE5.get();
            case 6:  return HalfLifeSounds.ZOMBIE_VOICE_IDLE6.get();
            case 7:  return HalfLifeSounds.ZOMBIE_VOICE_IDLE7.get();
            case 8:  return HalfLifeSounds.ZOMBIE_VOICE_IDLE8.get();
            case 9:  return HalfLifeSounds.ZOMBIE_VOICE_IDLE9.get();
            case 10:  return HalfLifeSounds.ZOMBIE_VOICE_IDLE10.get();
            case 11:  return HalfLifeSounds.ZOMBIE_VOICE_IDLE11.get();
            case 12:  return HalfLifeSounds.ZOMBIE_VOICE_IDLE12.get();
            case 13:  return HalfLifeSounds.ZOMBIE_VOICE_IDLE13.get();
            case 14:  return HalfLifeSounds.ZOMBIE_VOICE_IDLE14.get();
        }
        }

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


    protected SoundEvent getStepSound() {
        switch (this.random.nextInt(1,7)) {
            case 1:  return HalfLifeSounds.ZOMBIE_FOOT1.get();
            case 2:  return HalfLifeSounds.ZOMBIE_FOOT2.get();
            case 3:  return HalfLifeSounds.ZOMBIE_FOOT3.get();
            case 4:  return HalfLifeSounds.ZOMBIE_FOOT_SLIDE1.get();
            case 5:  return HalfLifeSounds.ZOMBIE_FOOT_SLIDE2.get();
            case 6:  return HalfLifeSounds.ZOMBIE_FOOT_SLIDE3.get();
        }
        return SoundEvents.FROG_STEP;
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


    @Override
    protected void pushEntities() {
        super.pushEntities();
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


    public int getheadcrabtype() {
        if (this.getFirstPassenger() == null) return -1;
        if (this.getFirstPassenger() instanceof Headcrab_2) return 2;
        if (this.getFirstPassenger() instanceof Headcrab_3) return 3;
        return 1;
    }


    @Nullable
    @Override
    public LivingEntity getControllingPassenger() {
        return null;
    }

    @Override
    protected SoundEvent getDeathSound() {



        switch (this.random.nextInt(1,4)) {
            case 1:  return HalfLifeSounds.ZOMBIE_DIE1.get();
            case 2:  return HalfLifeSounds.ZOMBIE_DIE2.get();
            case 3:  return HalfLifeSounds.ZOMBIE_DIE3.get(); }



        return super.getDeathSound();
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
    public List<ExtendedSensor<HLZombieVillager>> getSensors() {
        return ObjectArrayList.of(
                new HurtBySensor<>(),
                new NearbyPlayersSensor<>(),
                new NearbyLivingEntitySensor<HLZombieVillager>()
                        .setPredicate((target, entity) ->
                                (this.getFirstPassenger() instanceof HalfLifeMonster headcrab && target.equals(headcrab.getLastHurtByMob())) ||
                                        InfightingUtil.HeadcrabFactionSpecific(target) || InfightingUtil.commonenemy(target)
                        ));
    }



    @Override
    public BrainActivityGroup<HLZombieVillager> getCoreTasks() {
        return BrainActivityGroup.coreTasks(
                new LookAtTarget<>(),
                new MoveToWalkTarget<>());
    }



    @Override
    public BrainActivityGroup<HLZombieVillager> getIdleTasks() {
        return BrainActivityGroup.idleTasks(
                new FirstApplicableBehaviour<HLZombieVillager>(
                        new CustomBehaviour<>(entity -> this.entityData.set(IS_ANGRY, false)).startCondition(entity -> this.entityData.get(IS_ANGRY).equals(true)),
                        new TargetOrRetaliateHLT<>(),
                        new SetPlayerLookTarget<>(),
                        new SetRandomLookTarget<>()),
                new OneRandomBehaviour<>(
                        new SetRandomWalkTarget<>().setRadius(13, 7),
                        new Idle<>().runFor(entity -> entity.getRandom().nextInt(10, 40))));
    }


    @Override
    public BrainActivityGroup<HLZombieVillager> getFightTasks() { // These are the tasks that handle fighting
        return BrainActivityGroup.fightTasks(
                new InvalidateAttackTarget<>(),
                new SetWalkTargetToAttackTarget<>(),
                new Retaliate<>(),
                new CustomBehaviour<>(entity -> this.entityData.set(IS_ANGRY, true)).startCondition(entity -> this.entityData.get(IS_ANGRY).equals(false)),
                new OneRandomBehaviour<>(
               new ConfigurableAnimatableMeleeAttack<HLZombieVillager>(8, 0f, 1f, 1, null, 0, this.getHitSound(), null, this.getMissSound(), this.getLongSound(), this.getShortSound())
                .whenStarting(entity -> triggerAnim("onetime", "big"))
                .cooldownFor(entity -> random.nextInt(10, 15)),
                       new ConfigurableAnimatableMeleeAttack<HLZombieVillager>(8, 0f, 1f, 1, null, 0, this.getHitSound(), null, this.getMissSound(), this.getLongSound(), this.getShortSound())
                               .whenStarting(entity -> triggerAnim("onetime", "left"))
                               .cooldownFor(entity -> random.nextInt(10, 20)),
                       new ConfigurableAnimatableMeleeAttack<HLZombieVillager>(8, 0f, 1f, 1, null, 0, this.getHitSound(), null, this.getMissSound(), this.getLongSound(), this.getShortSound())
                               .whenStarting(entity -> triggerAnim("onetime", "right"))
                               .cooldownFor(entity -> random.nextInt(10, 15))

        ));

    }

    @Override
    public float getVoicePitch() {
        return 1+random.nextFloat()*random.nextFloat()-random.nextFloat()*random.nextFloat();
    }



     @Override
    public void tick() {
         super.tick();
         if ((this.tickCount > 15 && (this.getPassengers().isEmpty())) || (this.getFirstPassenger() instanceof LivingEntity entity && entity.isDeadOrDying())) {
             this.setHealth(0);
         }
     }





    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(new AnimationController<>(this, "controller", 0, this::predicate));
        controllerRegistrar.add(new AnimationController<>(this, "onetime", state -> PlayState.STOP)
                .triggerableAnim("big", RawAnimation.begin().then("animation.vzombie.attack3", Animation.LoopType.PLAY_ONCE))
                .triggerableAnim("right", RawAnimation.begin().then("animation.vzombie.attack", Animation.LoopType.PLAY_ONCE))
                .triggerableAnim("left", RawAnimation.begin().then("animation.vzombie.attack2", Animation.LoopType.PLAY_ONCE))
        );
    }


    private <T extends GeoAnimatable> PlayState predicate(AnimationState<T> tAnimationState) {

        if(tAnimationState.isMoving()) {
            tAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.vzombie.walk", Animation.LoopType.LOOP));
            return PlayState.CONTINUE;
        }

        tAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.vzombie.idle", Animation.LoopType.LOOP));
        return PlayState.CONTINUE;

    }


    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }


    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor p_33601_, DifficultyInstance p_33602_, MobSpawnType p_33603_, @javax.annotation.Nullable SpawnGroupData p_33604_, @javax.annotation.Nullable CompoundTag p_33605_) {

        if (!p_33603_.equals(MobSpawnType.CONVERSION)) {
            int i = random.nextInt(3);
            float j = random.nextFloat();
            if (j < 0.9f) {
                switch (i) {
                    case 0: {
                        Headcrab_2 summon = HalfLifeEntities.HEADCRAB_HL2.get().create(level);
                        if (summon != null) {
                            summon.moveTo(this.position());
                            summon.startRiding(this);
                            ForgeEventFactory.onFinalizeSpawn((Mob) summon, (ServerLevelAccessor) level, level.getCurrentDifficultyAt(summon.blockPosition()), MobSpawnType.TRIGGERED, null, null);
                            level.addFreshEntity(summon);
                        }
                        break;
                    }
                    case 1: {
                        Headcrab_1 summon = HalfLifeEntities.HEADCRAB_HL1.get().create(level);
                        if (summon != null) {
                            summon.moveTo(this.position());
                            summon.startRiding(this);
                            ForgeEventFactory.onFinalizeSpawn((Mob) summon, (ServerLevelAccessor) level, level.getCurrentDifficultyAt(summon.blockPosition()), MobSpawnType.TRIGGERED, null, null);
                            level.addFreshEntity(summon);
                        }
                        break;
                    }
                    case 2: {

                        Headcrab_3 summon = HalfLifeEntities.HEADCRAB_HLA.get().create(level);
                        if (summon != null) {
                            summon.moveTo(this.position());
                            summon.startRiding(this);
                            ForgeEventFactory.onFinalizeSpawn((Mob) summon, (ServerLevelAccessor) level, level.getCurrentDifficultyAt(summon.blockPosition()), MobSpawnType.TRIGGERED, null, null);
                            level.addFreshEntity(summon);
                        }
                    }


                }

            } else {
                Headcrab_Armored summon = HalfLifeEntities.HEADCRAB_ARMORED.get().create(level);
                if (summon != null) {
                    summon.moveTo(this.position());
                    summon.startRiding(this);
                    ForgeEventFactory.onFinalizeSpawn((Mob) summon, (ServerLevelAccessor) level, level.getCurrentDifficultyAt(summon.blockPosition()), MobSpawnType.TRIGGERED, null, null);
                    level.addFreshEntity(summon);
                }
            }
        }

        return super.finalizeSpawn(p_33601_, p_33602_, p_33603_, p_33604_, p_33605_);
    }
}





