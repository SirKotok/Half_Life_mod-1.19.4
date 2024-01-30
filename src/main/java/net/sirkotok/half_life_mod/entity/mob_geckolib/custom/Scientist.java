package net.sirkotok.half_life_mod.entity.mob_geckolib.custom;


import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.Tags;
import net.sirkotok.half_life_mod.entity.base.HalfLifeMonster;
import net.sirkotok.half_life_mod.entity.base.HalfLifeNeutral;
import net.sirkotok.half_life_mod.entity.brain.behaviour.*;
import net.sirkotok.half_life_mod.entity.brain.sensor.NearestHealableAllySensor;
import net.sirkotok.half_life_mod.sound.HalfLifeSounds;
import net.sirkotok.half_life_mod.util.HLperUtil;
import net.tslat.smartbrainlib.api.SmartBrainOwner;
import net.tslat.smartbrainlib.api.core.BrainActivityGroup;
import net.tslat.smartbrainlib.api.core.SmartBrainProvider;
import net.tslat.smartbrainlib.api.core.behaviour.FirstApplicableBehaviour;
import net.tslat.smartbrainlib.api.core.behaviour.OneRandomBehaviour;
import net.tslat.smartbrainlib.api.core.behaviour.custom.attack.AnimatableMeleeAttack;
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
import net.tslat.smartbrainlib.api.core.behaviour.custom.target.TargetOrRetaliate;
import net.tslat.smartbrainlib.api.core.sensor.ExtendedSensor;
import net.tslat.smartbrainlib.api.core.sensor.vanilla.HurtBySensor;
import net.tslat.smartbrainlib.api.core.sensor.vanilla.NearbyLivingEntitySensor;
import net.tslat.smartbrainlib.api.core.sensor.vanilla.NearbyPlayersSensor;
import net.tslat.smartbrainlib.util.BrainUtils;
import org.checkerframework.checker.units.qual.C;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

import javax.annotation.Nullable;
import java.util.List;


public class Scientist extends HalfLifeNeutral implements GeoEntity, SmartBrainOwner<Scientist> {

    private Player heal;

    private AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);


    public DyeColor getColor() {
        return DyeColor.byId(this.entityData.get(DATA_COAT_ID) & 15);
    }

    private static final EntityDataAccessor<Byte> DATA_COAT_ID = SynchedEntityData.defineId(Scientist.class, EntityDataSerializers.BYTE);

    private static final EntityDataAccessor<Integer> HEALCOOLDOWN = SynchedEntityData.defineId(Scientist.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> HEALTIMER = SynchedEntityData.defineId(Scientist.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> HEALTIMER2 = SynchedEntityData.defineId(Scientist.class, EntityDataSerializers.INT);
       public static final EntityDataAccessor<Integer> ID_SHIRT = SynchedEntityData.defineId(Scientist.class, EntityDataSerializers.INT);
    //0 = normal
    //1 = hd
    public static final EntityDataAccessor<Boolean> IS_ANGRY = SynchedEntityData.defineId(Scientist.class, EntityDataSerializers.BOOLEAN);
    public static final EntityDataAccessor<Boolean> IS_RUNNING = SynchedEntityData.defineId(Scientist.class, EntityDataSerializers.BOOLEAN);
    @Override
    public InteractionResult interactAt(Player pPlayer, Vec3 pVec, InteractionHand pHand) {

        ItemStack itemstack = pPlayer.getItemInHand(pHand);
        if (!itemstack.isEmpty()) {
            Item item = itemstack.getItem();
            if (item instanceof DyeItem dyeItem) {
                DyeColor color = dyeItem.getDyeColor();
                if (this.getColor() != color) {
                    this.setColor(color);
                   if (!pPlayer.getAbilities().instabuild) {itemstack.shrink(1);}
                   return InteractionResult.CONSUME;
                } else return InteractionResult.PASS;
            }
        }

        if (this.entityData.get(HEALTIMER2) > 0 || this.entityData.get(IS_ANGRY)) return InteractionResult.PASS;

        if (pPlayer.getHealth() < pPlayer.getMaxHealth()*0.7) {
            this.playSound(this.getHealingSound(), this.getSoundVolume(), this.getVoicePitch());
            HLperUtil.slowEntityFor(this, 25);
            this.entityData.set(HEALTIMER, 16);
            this.entityData.set(HEALTIMER2, 25);
            heal = pPlayer;
            triggerAnim("onetime", "heal");
            return InteractionResult.PASS;
        }



        return super.interactAt(pPlayer, pVec, pHand);
    }

    public static final EntityDataAccessor<Integer> ID_TEXTURE = SynchedEntityData.defineId(Scientist.class, EntityDataSerializers.INT);


    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(HEALCOOLDOWN, 0);
        this.entityData.define(HEALTIMER, 0);
        this.entityData.define(HEALTIMER2, 0);
        this.entityData.define(ID_TEXTURE, 1);
        this.entityData.define(ID_SHIRT, 0);
        this.entityData.define(DATA_COAT_ID, (byte)0);
        this.entityData.define(IS_ANGRY, false);
        this.entityData.define(IS_RUNNING, false);

    }


    public boolean hlisangry() {
        return this.entityData.get(IS_ANGRY);
    }


    @Override
    public void tick() {
        super.tick();
        if (this.hasEffect(MobEffects.MOVEMENT_SPEED)) this.entityData.set(IS_RUNNING, true); else this.entityData.set(IS_RUNNING, false);
        if (this.entityData.get(HEALCOOLDOWN) > 0) this.entityData.set(HEALCOOLDOWN, this.entityData.get(HEALCOOLDOWN)-1);
        if (this.entityData.get(HEALTIMER) > 0) this.entityData.set(HEALTIMER, this.entityData.get(HEALTIMER)-1);
        if (this.entityData.get(HEALTIMER2) > 0) this.entityData.set(HEALTIMER2, this.entityData.get(HEALTIMER2)-1);
        if (this.entityData.get(HEALTIMER) == 1 && heal != null && this.isWithinMeleeAttackRange(heal)) {
            heal.heal(4f);
            this.playSound(this.getHealingSyrSound(), this.getSoundVolume(), this.getVoicePitch());
            this.entityData.set(HEALCOOLDOWN, 1600);
        }
    }

    @Override
    public double getMeleeAttackRangeSqr(LivingEntity pEntity) {
        return (double)(this.getBbWidth() * 2.0F * this.getBbWidth() * 2.0F + pEntity.getBbWidth())*1.3F;
    }


    @Override
    public SoundEvent getStartFollowingSound() {
        switch (this.random.nextInt(1,7)) {
            case 1:  return HalfLifeSounds.GUARD_FOLLOW_1.get();
            case 2:  return HalfLifeSounds.GUARD_FOLLOW_2.get();
            case 3:  return HalfLifeSounds.GUARD_FOLLOW_3.get();
            case 4:  return HalfLifeSounds.GUARD_FOLLOW_4.get();
            case 5:  return HalfLifeSounds.GUARD_FOLLOW_5.get();
            case 6:  return HalfLifeSounds.GUARD_FOLLOW_6.get();
        }
        return HalfLifeSounds.HEADCRAB_1_ATTACK_1.get();
    }


    public SoundEvent getHealingSound() {
        switch (this.random.nextInt(1,7)) {
            case 1:  return HalfLifeSounds.GUARD_FOLLOW_1.get();
            case 2:  return HalfLifeSounds.GUARD_FOLLOW_2.get();
            case 3:  return HalfLifeSounds.GUARD_FOLLOW_3.get();
            case 4:  return HalfLifeSounds.GUARD_FOLLOW_4.get();
            case 5:  return HalfLifeSounds.GUARD_FOLLOW_5.get();
            case 6:  return HalfLifeSounds.GUARD_FOLLOW_6.get();
        }
        return HalfLifeSounds.HEADCRAB_1_ATTACK_1.get();
    }

    public SoundEvent getHealingSyrSound() {
        return HalfLifeSounds.HEADCRAB_1_ATTACK_1.get();
    }


    @Override
    public SoundEvent getStopFollowingSound() {
        switch (this.random.nextInt(1,7)) {
            case 1:  return HalfLifeSounds.GUARD_STAY_1.get();
            case 2:  return HalfLifeSounds.GUARD_STAY_2.get();
            case 3:  return HalfLifeSounds.GUARD_STAY_3.get();
            case 4:  return HalfLifeSounds.GUARD_STAY_4.get();
            case 5:  return HalfLifeSounds.GUARD_STAY_5.get();
            case 6:  return HalfLifeSounds.GUARD_STAY_6.get();
        }
        return HalfLifeSounds.HEADCRAB_1_ATTACK_1.get();
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




      public int getShirt() {
        return this.entityData.get(ID_SHIRT);
    }
    protected void setShirt(int texture) {
        this.entityData.set(ID_SHIRT, texture);
    }





    public Scientist(EntityType type, Level level) {
        super(type, level);
        this.xpReward = 3;
    }


    public static AttributeSupplier setAttributes() {
        return Monster.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 15D)
                .add(Attributes.ATTACK_DAMAGE, 3f)
                .add(Attributes.ATTACK_SPEED, 1.0f)
                .add(Attributes.ATTACK_KNOCKBACK, 1.0f)
                .add(Attributes.MOVEMENT_SPEED, 0.22f).build();
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
    public List<ExtendedSensor<Scientist>> getSensors() {
        return ObjectArrayList.of(
                new HurtBySensor<>(),
                new NearbyPlayersSensor<Scientist>().setRadius(60d),
                new NearestHealableAllySensor<Scientist>().setPredicate((target, entity) -> target.getHealth() < target.getMaxHealth() && target != entity && target.isAlive()),
                new NearbyLivingEntitySensor<Scientist>()
                        .setPredicate((target, entity) ->
                                target instanceof Enemy || this.ismyenemy(target)));
    }



    @Override
    public BrainActivityGroup<Scientist> getCoreTasks() { // These are the tasks that run all the time (usually)
        return BrainActivityGroup.coreTasks(
                new LookAtTarget<>(),                      // Have the entity turn to face and look at its current look target
                new MoveToWalkTarget<>());
    }

    @Override
    public BrainActivityGroup<Scientist> getIdleTasks() { // These are the tasks that run when the mob isn't doing anything else (usually)
        return BrainActivityGroup.idleTasks(
                new FirstApplicableBehaviour<Scientist>(
                        new CustomBehaviour<>(entity -> this.entityData.set(IS_ANGRY, false)).startCondition(entity -> this.entityData.get(IS_ANGRY).equals(true)),
                        new CustomBehaviour<>(entity -> BrainUtils.clearMemory(entity, MemoryModuleType.WALK_TARGET)).cooldownFor(entity -> 60),
                        new TargetOrRetaliateHLT<>(),
                        new SetPlayerLookTarget<>(),
                        new SetRandomLookTarget<>(),
                        new SetWalkTargetToHealTarget<>().startCondition(entity -> !(this.entityData.get(HEALCOOLDOWN) > 0)).cooldownFor(entity -> 40),
                        new AnimatableMeleeHeal<>(18, this.getHealingSyrSound(), null).whenActivating(entity -> this.entityData.set(HEALCOOLDOWN, 1600)).whenStarting(entity -> triggerAnim("onetime", "heal")).startCondition(entity -> !(this.entityData.get(HEALCOOLDOWN) > 0)),
                        new HL1FollowSpeedup<>().cooldownFor(entity -> 25),
                new HalfLife1SetFollowToWalkTarget<>().cooldownFor(entity -> 10),
                new OneRandomBehaviour<>(
                        new SetRandomWalkTarget<>(),          // Set a random walk target to a nearby position
                        new Idle<>().runFor(entity -> entity.getRandom().nextInt(30, 90))))); // Do nothing for 1.5->4.5 seconds
    }




    @Override
    public BrainActivityGroup<Scientist> getFightTasks() { // These are the tasks that handle fighting
        return BrainActivityGroup.fightTasks(
                new InvalidateAttackTarget<>(),
                new Retaliate<>(),
                new CustomBehaviour<>(entity -> this.entityData.set(IS_ANGRY, true)).startCondition(entity -> this.entityData.get(IS_ANGRY).equals(false)),
                new FleeTarget<>().speedModifier(1.4f).fleeDistance(14),
                new CustomBehaviour<>(entity -> this.entityData.set(STRING_UUID_FOLLOW, "")).startCondition(entity -> !this.entityData.get(STRING_UUID_FOLLOW).equals("")),
                new CustomBehaviour<>(entity -> HLperUtil.slowEntityFor(this, 20))
                        .whenStarting(entity -> triggerAnim("onetime", "fear"))
                        .cooldownFor(entity -> random.nextInt(0, 250))
                   );

    }



    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(new AnimationController<>(this, "nosyr", 0, this::nosyr));
        controllerRegistrar.add(new AnimationController<>(this, "controller", 0, this::predicate));
        controllerRegistrar.add(new AnimationController<>(this, "onetime", state -> PlayState.STOP)
                .triggerableAnim("heal", RawAnimation.begin().then("animation.scientist.medbay", Animation.LoopType.PLAY_ONCE))
                .triggerableAnim("fear", RawAnimation.begin().then("animation.scientist.cover", Animation.LoopType.PLAY_ONCE))
        );


    }

    private <T extends GeoAnimatable> PlayState nosyr(AnimationState<T> tAnimationState) {

        tAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.scientist.nosir", Animation.LoopType.LOOP));
        return PlayState.CONTINUE;

    }


    private <T extends GeoAnimatable> PlayState predicate(AnimationState<T> tAnimationState) {

        if(tAnimationState.isMoving() && this.isOnGround()) {

            if (this.entityData.get(IS_ANGRY)) {
                tAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.scientist.runfear", Animation.LoopType.LOOP));
                return PlayState.CONTINUE;
            }


            if (this.entityData.get(IS_RUNNING)) {
                tAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.scientist.run", Animation.LoopType.LOOP));
                return PlayState.CONTINUE;
            }

            tAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.scientist.walk", Animation.LoopType.LOOP));
            return PlayState.CONTINUE;
        }

        tAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.scientist.idle", Animation.LoopType.LOOP));
        return PlayState.CONTINUE;

    }


    @Nullable
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor p_33601_, DifficultyInstance p_33602_, MobSpawnType p_33603_, @Nullable SpawnGroupData p_33604_, @Nullable CompoundTag p_33605_) {
        RandomSource randomsource = p_33601_.getRandom();
        int i = randomsource.nextInt(4);
        int j = randomsource.nextInt(2);
        this.settexture(i+1);
        this.setShirt(j);
        return super.finalizeSpawn(p_33601_, p_33602_, p_33603_, p_33604_, p_33605_);
    }


    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

}

