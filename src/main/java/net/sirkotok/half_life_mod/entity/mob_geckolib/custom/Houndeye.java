package net.sirkotok.half_life_mod.entity.mob_geckolib.custom;


import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.sirkotok.half_life_mod.entity.base.HalfLifeMonster;
import net.sirkotok.half_life_mod.entity.base.HalfLifeNeutral;
import net.sirkotok.half_life_mod.entity.brain.behaviour.*;
import net.sirkotok.half_life_mod.entity.modinterface.HasLeaderMob;
import net.sirkotok.half_life_mod.misc.gamerules.HalfLifeGameRules;
import net.sirkotok.half_life_mod.sound.HalfLifeSounds;
import net.sirkotok.half_life_mod.misc.util.HLTags;
import net.sirkotok.half_life_mod.misc.util.HLperUtil;
import net.sirkotok.half_life_mod.misc.util.InfightingUtil;
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
import net.tslat.smartbrainlib.api.core.behaviour.custom.target.TargetOrRetaliate;
import net.tslat.smartbrainlib.api.core.sensor.ExtendedSensor;
import net.tslat.smartbrainlib.api.core.sensor.custom.NearbyBlocksSensor;
import net.tslat.smartbrainlib.api.core.sensor.vanilla.HurtBySensor;
import net.tslat.smartbrainlib.api.core.sensor.vanilla.NearbyLivingEntitySensor;
import net.tslat.smartbrainlib.api.core.sensor.vanilla.NearbyPlayersSensor;
import net.tslat.smartbrainlib.util.BrainUtils;
import net.tslat.smartbrainlib.util.EntityRetrievalUtil;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class Houndeye extends HalfLifeMonster implements GeoEntity, HasLeaderMob<Houndeye>, SmartBrainOwner<Houndeye> {



    @Override
    public boolean canBeLeashed(Player p_21418_) {
        return !this.isLeashed();
    }

    private AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    protected float chance = 0.1f;





    public static final EntityDataAccessor<Boolean> IS_ANGRY = SynchedEntityData.defineId(Bullsquid.class, EntityDataSerializers.BOOLEAN);
    public static final EntityDataAccessor<Boolean> IS_LIGHT = SynchedEntityData.defineId(Houndeye.class, EntityDataSerializers.BOOLEAN);
    public static final EntityDataAccessor<Boolean> IS_LEADER = SynchedEntityData.defineId(Houndeye.class, EntityDataSerializers.BOOLEAN);
    public static final EntityDataAccessor<Integer> SAD_TIMESTAMP = SynchedEntityData.defineId(Houndeye.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Integer> SQUAD_SIZE = SynchedEntityData.defineId(Houndeye.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Boolean> AI_STOP = SynchedEntityData.defineId(Bullsquid.class, EntityDataSerializers.BOOLEAN);
    public static final EntityDataAccessor<Integer> AI_STOP_TIMESTAMP = SynchedEntityData.defineId(Bullsquid.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Integer> AI_STOP_DELAY = SynchedEntityData.defineId(Bullsquid.class, EntityDataSerializers.INT);


    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(SAD_TIMESTAMP, 0);
        this.entityData.define(IS_LIGHT, false);
        this.entityData.define(IS_LEADER, false);
        this.entityData.define(IS_ANGRY, false);
        this.entityData.define(AI_STOP, false);
        this.entityData.define(AI_STOP_TIMESTAMP, 0);
        this.entityData.define(AI_STOP_DELAY, 0);
        this.entityData.define(SQUAD_SIZE, 1);

    }


    public void stopAiFor(int delay) {
        this.entityData.set(AI_STOP_TIMESTAMP, this.tickCount);
        this.entityData.set(AI_STOP, true);
        this.entityData.set(AI_STOP_DELAY, delay);
    }

    public int ai_stop_timestamp() {
        return this.entityData.get(AI_STOP_TIMESTAMP);
    }
    public boolean aistopped() {
        return this.entityData.get(AI_STOP);
    }
    public int ai_stop_remaining() {
        return this.entityData.get(AI_STOP_DELAY);
    }

    protected boolean isangry() {
        return this.entityData.get(IS_ANGRY);
    }

    public int getSadTimestamp() {
        return this.entityData.get(SAD_TIMESTAMP);
    }
    protected void setSadTimestamp(int sad) {
        this.entityData.set(SAD_TIMESTAMP, sad);
    }


    public boolean islight() {
        return this.entityData.get(IS_LIGHT);
    }
    public boolean isleader() {
        return this.entityData.get(IS_LEADER);
    }
    public int getsquaidsize() {
        return this.entityData.get(SQUAD_SIZE);
    }



    protected void setlight(boolean glow) {
        this.entityData.set(IS_LIGHT, glow);
    }
    protected void setthistoleader(boolean yes) {
        this.entityData.set(IS_LEADER, yes);
    }
    protected void setSquadSize(int size) {
        this.entityData.set(SQUAD_SIZE, size);
    }




    public void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);

        pCompound.putInt("Squad_size", this.getsquaidsize() - 1 );
        pCompound.putBoolean("Light", this.islight());
    }

    public void readAdditionalSaveData(CompoundTag pCompound) {

        this.setSquadSize(pCompound.getInt("Squad_size") + 1);
        this.setlight(pCompound.getBoolean("Light"));
        super.readAdditionalSaveData(pCompound);
    }







    public Houndeye(EntityType type, Level level) {
        super(type, level);
        this.xpReward = 5;
    }



    public Houndeye leader;
    public List<Houndeye> dogs;

    public Houndeye getLeader() {
        if (this.leader == null) return this;
        return this.leader;
    }


    public void setLeader(LivingEntity dog) {
        this.leader = (Houndeye) dog;
    }

    public List<Houndeye> getDogs() {
        if (this.dogs == null) return new ArrayList<>();
        return this.dogs;
    }
    public void setDogs(List<Houndeye> list) {
        this.dogs = list;
    }

    public void addDogtoDogs(Houndeye dog) {
        if (this.dogs == null) this.dogs = new ArrayList<>();
        if (this.getDogs().contains(dog)) return;
        this.dogs.add(dog);
    }


    public void removeDogfromDogs(Houndeye dog) {
        if (this.dogs == null) this.dogs = new ArrayList<>();
        if (!this.getDogs().contains(dog)) return;
        this.dogs.remove(dog);
    }


    @Override
    public void tick() {
        super.tick();




        if (!this.level.isClientSide()) {
            ServerLevel pLevel = (ServerLevel) this.level;
            BlockPos pBlockPos = this.blockPosition();
            int rad = 12;

            boolean yes = this.isleader() && aistopped();
            if (yes) {
            List<Houndeye> targets = EntityRetrievalUtil.getEntities((Level) pLevel,
                    new AABB(pBlockPos.getX() - rad, pBlockPos.getY() - rad, pBlockPos.getZ() - rad,
                            pBlockPos.getX() + rad, pBlockPos.getY() + rad, pBlockPos.getZ() + rad), obj -> (obj instanceof Player pl && !(pl.isCreative() || pl.isSpectator())) || obj instanceof IronGolem || obj instanceof HalfLifeNeutral ||
                            obj instanceof AbstractVillager);
            boolean one = false;
            for (Houndeye dog : this.getDogs()) {
              if (dog.getLastAttacker() != null) one = true;
            }
            yes = yes && (!targets.isEmpty() || one);

            }


            if (aistopped() && ((this.tickCount - ai_stop_timestamp() > ai_stop_remaining()) || yes)) {

                for (Houndeye dog : this.getDogs()) {
                if (dog == this) dog.triggerAnim("long", "jump");
                else dog.triggerAnim("long", "startled");
                dog.entityData.set(AI_STOP, false);
                }

            }





            if (this.isDeadOrDying() && this.tickCount % 20 == 0) {


                for (Houndeye dog : this.getDogs()) {
                    if (dog != null && dog.isAlive() && (BrainUtils.getTargetOfEntity(dog) == null
                            || this.isleader())) {
                        BrainUtils.setTargetOfEntity(dog, this.getKillCredit());
                    }
                }


                int radius = 100;
                List<Houndeye> houndeyes = EntityRetrievalUtil.getEntities((Level) pLevel,
                        new AABB(pBlockPos.getX() - radius, pBlockPos.getY() - radius, pBlockPos.getZ() - radius,
                                pBlockPos.getX() + radius, pBlockPos.getY() + radius, pBlockPos.getZ() + radius), obj -> obj instanceof Houndeye);

                for (Houndeye dog : houndeyes) {
                    dog.removeDogfromDogs(this);
                }

            } else if (RandomSource.create().nextFloat() < 0.002f) this.triggerAnim("blink", "blink");



            boolean notsad = (this.tickCount - this.getSadTimestamp() > 800 || this.getSadTimestamp() == 0);

            if(notsad && this.getLeader().isDeadOrDying() ) {
                this.setSadTimestamp(this.tickCount);
                notsad = false;
                this.setLeader(this);
                List<Houndeye> a = new ArrayList<>();
                a.add(this);
                this.setDogs(a);
                this.setthistoleader(false);
                this.setSquadSize(this.getDogs().size());
             //   this.setCustomName(Component.literal("sad" + this.getsquaidsize()));
            }


                if (this.tickCount % 20 == 0 && notsad && !this.isDeadOrDying()) {

                    this.addDogtoDogs(this);
                    this.setSquadSize(this.getDogs().size());
                    int radius = 20;
                    List<Houndeye> houndeyes = EntityRetrievalUtil.getEntities((Level) pLevel,
                            new AABB(pBlockPos.getX() - radius, pBlockPos.getY() - radius, pBlockPos.getZ() - radius,
                                    pBlockPos.getX() + radius, pBlockPos.getY() + radius, pBlockPos.getZ() + radius), obj -> obj instanceof Houndeye && !((Houndeye) obj).isDeadOrDying());
                    List<UUID> uuids = new ArrayList<>();


                    for (Houndeye dog : houndeyes) {
                        uuids.add(dog.getUUID());
                    }
                    int max = HLperUtil.getMaxUUIDnumber(uuids);
                    this.setLeader(houndeyes.get(max));
                    this.getLeader().addDogtoDogs(this);
                    this.setDogs(this.getLeader().getDogs());


                    if (this.isleader() && houndeyes.size() < 2) {
                        List<Houndeye> a = new ArrayList<>();
                        a.add(this);
                        this.setDogs(a);
                    }


                    if (this.getLeader() == this && this.getsquaidsize() >= 2) {
                        this.setthistoleader(true);
                   //     this.setCustomName(Component.literal("leader" + this.getsquaidsize()));
                    } else  {
                        this.setthistoleader(false);
                    //    this.setCustomName(Component.literal("alone" + this.getsquaidsize()));
                   //      if (this.getsquaidsize() >=2) {
                    //   this.setCustomName(Component.literal("sub" + this.getsquaidsize())); }
                    }






                    if (BrainUtils.getTargetOfEntity(this) != null) {
                        for (Houndeye dog : this.getDogs()) {
                            if (dog != null && dog.isAlive() && (BrainUtils.getTargetOfEntity(dog) == null
                                    || (this.isleader() && this.tickCount - this.getLastHurtByMobTimestamp() < 400))) {
                                BrainUtils.setTargetOfEntity(dog, BrainUtils.getTargetOfEntity(this));
                            }
                        }
                    }




                }
        }
    }






    public static AttributeSupplier setAttributes() {
        return Monster.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 15D)
                .add(Attributes.ATTACK_DAMAGE, 4f)
                .add(Attributes.ATTACK_SPEED, 1.0f)
                .add(Attributes.ATTACK_KNOCKBACK, 1.0f)
                .add(Attributes.MOVEMENT_SPEED, 0.28f).build();
    }



    @Override
    protected float getSoundVolume() {
        return 0.8f;
    }


    protected SoundEvent getBlastSound() {
        switch (this.random.nextInt(1,4)) {
            case 1:  return HalfLifeSounds.SONIC_BLAST_1.get();
            case 2:  return HalfLifeSounds.SONIC_BLAST_2.get();
            case 3:  return HalfLifeSounds.SONIC_BLAST_3.get();
        }
        return HalfLifeSounds.HEADCRAB_1_ATTACK_1.get();
    }

    protected SoundEvent getBuildupSound() {
        switch (this.random.nextInt(1,4)) {
            case 1:  return HalfLifeSounds.HOUNDEYE_ATTACK_1.get();
            case 2:  return HalfLifeSounds.HOUNDEYE_ATTACK_2.get();
            case 3:  return HalfLifeSounds.HOUNDEYE_ATTACK_3.get();
        }
        return HalfLifeSounds.HEADCRAB_1_ATTACK_1.get();
    }



    protected SoundEvent getDeathSound() {
        switch (this.random.nextInt(1,4)) {
            case 1:  return HalfLifeSounds.HOUNDEYE_DIE_1.get();
            case 2:  return HalfLifeSounds.HOUNDEYE_DIE_2.get();
            case 3:  return HalfLifeSounds.HOUNDEYE_DIE_3.get();
        }
        return HalfLifeSounds.HEADCRAB_1_DIE_1.get();
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource pDamageSource) {
        switch (this.random.nextInt(1,6)) {
            case 1:  return HalfLifeSounds.HOUNDEYE_PAIN1.get();
            case 2:  return HalfLifeSounds.HOUNDEYE_PAIN2.get();
            case 3:  return HalfLifeSounds.HOUNDEYE_PAIN3.get();
            case 4:  return HalfLifeSounds.HOUNDEYE_PAIN4.get();
            case 5:  return HalfLifeSounds.HOUNDEYE_PAIN5.get();
        }
        return HalfLifeSounds.HEADCRAB_1_DIE_1.get();
    }


    protected SoundEvent getStepSound() {
        switch (this.random.nextInt(1,4)) {
            case 1:  return HalfLifeSounds.HOUNDEYE_STEP1.get();
            case 2:  return HalfLifeSounds.HOUNDEYE_STEP2.get();
            case 3:  return HalfLifeSounds.HOUNDEYE_STEP3.get();
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


    protected SoundEvent getAmbientSound() {
        switch (this.random.nextInt(1,5)) {
            case 1:  return HalfLifeSounds.HOUNDEYE_IDLE1.get();
            case 2:  return HalfLifeSounds.HOUNDEYE_IDLE2.get();
            case 3:  return HalfLifeSounds.HOUNDEYE_IDLE3.get();
            case 4:  return HalfLifeSounds.HOUNDEYE_IDLE4.get();
        }
        return SoundEvents.FROG_STEP;
    }
    protected SoundEvent getAlertSound() {
        switch (this.random.nextInt(1,4)) {
            case 1:  return HalfLifeSounds.HOUNDEYE_ALERT1.get();
            case 2:  return HalfLifeSounds.HOUNDEYE_ALERT2.get();
            case 3:  return HalfLifeSounds.HOUNDEYE_ALERT3.get();
        }
        return SoundEvents.FROG_STEP;
    }
    protected SoundEvent getBarkSound() {
        return HalfLifeSounds.HOUNDEYE_BARK.get();
    }




    @Override
    protected Brain.Provider<?> brainProvider() {
        return new SmartBrainProvider<>(this);
    }

    @Override
    protected void customServerAiStep() {
        if (!this.level.getGameRules().getRule(HalfLifeGameRules.HALF_LIFE_AI_IS_ON).get()) return;
        if (!aistopped()) {
            tickBrain(this);}
    }



    @Override
    public List<ExtendedSensor<Houndeye>> getSensors() {
        return ObjectArrayList.of(
                new HurtBySensor<>(),
                new NearbyBlocksSensor<Houndeye>().setRadius(8, 4).setPredicate((state, entity) -> state.is(HLTags.Blocks.HOUNDEYE_INTEREST)),
                new NearbyPlayersSensor<>(),
                new NearbyLivingEntitySensor<Houndeye>()
                        .setPredicate((target, entity) -> InfightingUtil.nonfactionSpecific(target))
                            );
    }







    public void performattack() {
        Level pLevel = this.getLevel();
        BlockPos pBlockPos = this.blockPosition();
        float radius = 3.9f;
        List<LivingEntity> entities = EntityRetrievalUtil.getEntities(pLevel,
                new AABB(pBlockPos.getX() - radius, pBlockPos.getY() - radius, pBlockPos.getZ() - radius,
                        pBlockPos.getX() + radius, pBlockPos.getY() + radius, pBlockPos.getZ() + radius), obj -> obj instanceof LivingEntity && !(obj instanceof Houndeye));
        int mod = Math.min(this.getsquaidsize(), 4);
        for (LivingEntity entity : entities) {
            this.ConfigurabledoHurtTargetShieldBoolean(false, entity, 1f, mod, 2, null, 0, false);
        }
    }


    @Override
    public BrainActivityGroup<Houndeye> getCoreTasks() { // These are the tasks that run all the time (usually)
        return BrainActivityGroup.coreTasks(
                new LookAtTarget<>().startCondition(entity -> !entity.hasEffect(MobEffects.LUCK)),                    // Have the entity turn to face and look at its current look target
                new MoveToWalkTarget<>());
    }

    @Override
    public BrainActivityGroup<Houndeye> getIdleTasks() { // These are the tasks that run when the mob isn't doing anything else (usually)
        return BrainActivityGroup.idleTasks(
                new FirstApplicableBehaviour<Houndeye>(
                        new CustomBehaviour<>(entity -> this.entityData.set(IS_ANGRY, false)).startCondition(entity -> this.isangry()),
                        new TargetOrRetaliate<>(),
                        new HoundeyeCuriocityBehaviour<>(),
                        new SetPlayerLookTarget<>(),
                        new SetRandomLookTarget<>()),
                        new FollowLeaderBehaviour<Houndeye>().startCondition(entity -> (this.getLeader() != null) && (this.distanceTo(this.getLeader()) > 4)),
                new OneRandomBehaviour<>(
                        new SetBlockToWalkTarget<>().cooldownFor(entity -> 200),
                        new HoundeyeSleepingBehaviour<>().startCondition(entity -> this.isleader() && RandomSource.create().nextFloat() < 0.01).cooldownFor(entity -> 800),
                        new SetRandomWalkTarget<>(),
                        new Idle<>().runFor(entity -> entity.getRandom().nextInt(30, 90))));
    }




    @Override
    public BrainActivityGroup<Houndeye> getFightTasks() { // These are the tasks that handle fighting
        return BrainActivityGroup.fightTasks(
                new InvalidateAttackTarget<>(),
                new Retaliate<>(),
                new CustomBehaviour<Houndeye>(entity -> playSound(this.getAlertSound())).startCondition(entity -> !this.isangry() && this.isleader()),
                new CustomBehaviour<Houndeye>(entity -> this.entityData.set(IS_ANGRY, true)).startCondition(entity -> !this.isangry()),
                new OneRandomBehaviour<>(
                        new SetWalkTargetToAttackTargetIfNoWalkTarget<>().speedMod(1.32f),
                        new SetWalkTargetToAttackTargetIfNoWalkTarget<>().speedMod(1.27f),
                        new SetWalkTargetToAttackTargetIfNoWalkTarget<>().speedMod(1.24f),
                        new SetWalkTargetToRandomSpotAroundAttackTarget<>().radius(5, 2).speedMod(1.30f),
                        new SetWalkTargetToRandomSpotAroundAttackTarget<>().radius(5, 2).speedMod(1.25f),
                        new SetRandomWalkTarget<>().setRadius(7, 5).speedModifier(1.25f),
                        new FollowLeaderBehaviour<Houndeye>().startCondition(entity -> !this.isleader())
                ),
                new OneRandomBehaviour<>(
                        new CustomBehaviour<Houndeye>(entity -> this.jumpback()).startCondition(entity -> this.getTarget() != null && this.distanceTo(this.getTarget()) < 3.2 && RandomSource.create().nextInt(30) == 10).whenStarting(entity -> this.triggerAnim("long", "jump")).cooldownFor(entity -> 450 + RandomSource.create().nextInt(500)),
                        new BarkAtTarget<Houndeye>(40, this.getBarkSound()).whenStarting(entity -> this.triggerAnim("long", "bark")).cooldownFor(entity -> 90),
                        new Houndeyeattackbehavior<>(25, this.getBlastSound(), this.getBuildupSound()).cooldownFor(entity -> 60 + RandomSource.create().nextInt(60))
                   ));

    }

    protected void jumpback() {
        double rot = this.yBodyRot*Math.PI/180;
        double cos = Math.cos(rot);
        double sin = Math.sin(rot);
        this.setDeltaMovement(0.8*sin, 0.5, -0.8*cos);
    }


    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {

        controllerRegistrar.add(new AnimationController<>(this, "noattack", 0, this::noattackpredicate));
        controllerRegistrar.add(new AnimationController<>(this, "loops", 0, this::predicate));
        controllerRegistrar.add(new AnimationController<>(this, "blink", state -> PlayState.STOP)
                .triggerableAnim("blink", RawAnimation.begin().then("animation.houndeye.blink", Animation.LoopType.PLAY_ONCE)));
        controllerRegistrar.add(new AnimationController<>(this, "long", state -> PlayState.STOP)
                .triggerableAnim("guard", RawAnimation.begin().then("animation.houndeye.standguard", Animation.LoopType.PLAY_ONCE))
                .triggerableAnim("sleep", RawAnimation.begin().then("animation.houndeye.sleep", Animation.LoopType.PLAY_ONCE))
                .triggerableAnim("startled", RawAnimation.begin().then("animation.houndeye.startled", Animation.LoopType.PLAY_ONCE))
                .triggerableAnim("jump", RawAnimation.begin().then("animation.houndeye.jumpback", Animation.LoopType.PLAY_ONCE))
                .triggerableAnim("attack", RawAnimation.begin().then("animation.houndeye.attack", Animation.LoopType.PLAY_ONCE))
                .triggerableAnim("curious", RawAnimation.begin().then("animation.houndeye.curious", Animation.LoopType.PLAY_ONCE))
                .triggerableAnim("bark", RawAnimation.begin().then("animation.houndeye.bark", Animation.LoopType.PLAY_ONCE))
        );
        controllerRegistrar.add(new AnimationController<>(this, "attack", state -> PlayState.STOP)
                .triggerableAnim("attack", RawAnimation.begin().then("animation.houndeye.actuallyattack", Animation.LoopType.PLAY_ONCE)));

    }


    private <T extends GeoAnimatable> PlayState noattackpredicate(AnimationState<T> tAnimationState) {
        tAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.houndeye.noattack", Animation.LoopType.LOOP));
        return PlayState.CONTINUE;
    }
    private <T extends GeoAnimatable> PlayState predicate(AnimationState<T> tAnimationState) {



        if(tAnimationState.isMoving() && this.isOnGround()) {
            tAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.houndeye.walk", Animation.LoopType.LOOP));
            return PlayState.CONTINUE;
        }

        tAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.houndeye.idle", Animation.LoopType.LOOP));
        return PlayState.CONTINUE;

    }






    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }




    @Nullable
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor p_33601_, DifficultyInstance p_33602_, MobSpawnType p_33603_, @Nullable SpawnGroupData p_33604_, @Nullable CompoundTag p_33605_) {
        RandomSource randomsource = p_33601_.getRandom();
        float i = randomsource.nextFloat();
        if (i < chance) this.setlight(true);
     //   this.setCustomName(Component.literal(this.getUUID().toString()));
        return super.finalizeSpawn(p_33601_, p_33602_, p_33603_, p_33604_, p_33605_);
    }





}
