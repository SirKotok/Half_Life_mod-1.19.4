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
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.Mth;
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
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.sirkotok.half_life_mod.block.ModBlocks;
import net.sirkotok.half_life_mod.entity.base.HalfLifeMonster;
import net.sirkotok.half_life_mod.entity.base.HalfLifeNeutral;
import net.sirkotok.half_life_mod.entity.brain.ModMemoryModuleType;
import net.sirkotok.half_life_mod.entity.brain.behaviour.*;
import net.sirkotok.half_life_mod.entity.brain.sensor.NearbySecondBlocksSensor;
import net.sirkotok.half_life_mod.entity.projectile.AcidBall;
import net.sirkotok.half_life_mod.entity.projectile.VoltigoreShock;
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
import net.tslat.smartbrainlib.api.core.behaviour.custom.path.SetWalkTargetToAttackTarget;
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
import java.util.List;

public class Vortigore extends HalfLifeMonster implements GeoEntity, SmartBrainOwner<Vortigore>, RangedAttackMob {

    private AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    public Vortigore(EntityType type, Level level) {
        super(type, level);
        this.xpReward = 30;
    }





    @Override
    public boolean hurt(DamageSource pSource, float pAmount) {
        if (pSource.is(DamageTypes.IN_WALL)) {
            return false;
        }
        return super.hurt(pSource, pAmount);
    }

    @Override
    protected void actuallyHurt(DamageSource p_21240_, float p_21241_) {
        if (p_21240_.is(DamageTypes.IN_WALL)) {
            return; }

        super.actuallyHurt(p_21240_, p_21241_);
    }


    public static final EntityDataAccessor<Integer> VORTIGORES_AROUND = SynchedEntityData.defineId(Bullsquid.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Integer> AGE = SynchedEntityData.defineId(Bullsquid.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Integer> AGEUP = SynchedEntityData.defineId(Bullsquid.class, EntityDataSerializers.INT);


    public static final EntityDataAccessor<Boolean> IS_BABY = SynchedEntityData.defineId(Bullsquid.class, EntityDataSerializers.BOOLEAN);

    public static final EntityDataAccessor<Boolean> IS_ANGRY = SynchedEntityData.defineId(Bullsquid.class, EntityDataSerializers.BOOLEAN);

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(IS_BABY, false);
        this.entityData.define(IS_ANGRY, false);
        this.entityData.define(AGE, 0);

        this.entityData.define(VORTIGORES_AROUND, 0);
        this.entityData.define(AGEUP, 0);
    }


    public void setthistobaby(boolean yes) {
        this.entityData.set(IS_BABY, yes);
    }
    public boolean isBB() {
        return this.entityData.get(IS_BABY);
    }

    public void setage(int age) {
        this.entityData.set(AGE, age);
    }
    public int getage() {
        return this.entityData.get(AGE);
    }

    public void setvround(int age) {
        this.entityData.set(VORTIGORES_AROUND, age);
    }
    public int getvround() {
        return this.entityData.get(VORTIGORES_AROUND);
    }

    public float getmod() {
        if (this.isBB()) return 0.15f;
        return 1f;
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
        pCompound.putBoolean("Bb", this.isBB());
    }

    public void readAdditionalSaveData(CompoundTag pCompound) {
        this.setage(pCompound.getInt("Ag") + 1);
        this.setageup(pCompound.getInt("Agup") + 1);
        this.setthistobaby(pCompound.getBoolean("Bb"));

        super.readAdditionalSaveData(pCompound);
    }

    @Override
    protected AABB makeBoundingBox() {
        if (this.isBB()) return this.makeBB(this.getX(), this.getY(), this.getZ());
        return super.makeBoundingBox();
    }

    public AABB makeBB(double pX, double pY, double pZ) {
        float f = 0.65f / 2.0F;
        float f1 = 0.65f;
        return new AABB(pX - (double)f, pY, pZ - (double)f, pX + (double)f, pY + (double)f1, pZ + (double)f);
    }




    public static AttributeSupplier setAttributes() {
        return Monster.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 200D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 1.0D)
                .add(Attributes.ARMOR, 10D)
                .add(Attributes.ATTACK_DAMAGE, 16f)
                .add(Attributes.ATTACK_SPEED, 5f)
                .add(Attributes.ATTACK_KNOCKBACK, 1.5f)
                .add(Attributes.MOVEMENT_SPEED, 0.26f).build();
    }


    @Override
    public void aiStep() {
        super.aiStep();
       // this.setBoundingBox(this.makeBoundingBox());
    }



    protected boolean isangry() {
        return this.entityData.get(IS_ANGRY);
    }


    @Override
    public void tick() {
        super.tick();

        if (this.tickCount % 20 == 0) this.refreshDimensions();

        if (this.tickCount % 100 == 0 && !this.level.isClientSide()) {
            ServerLevel pLevel = (ServerLevel) this.level;
            BlockPos pBlockPos = this.blockPosition();
            int rad = 20;
                List<Vortigore> vortigores = EntityRetrievalUtil.getEntities((Level) pLevel,
                        new AABB(pBlockPos.getX() - rad, pBlockPos.getY() - rad, pBlockPos.getZ() - rad,
                                pBlockPos.getX() + rad, pBlockPos.getY() + rad, pBlockPos.getZ() + rad), obj -> obj instanceof Vortigore);
            this.setvround(vortigores.size());

        }


        if (this.tickCount % 40 == 0) {this.setage(this.getage()+1);}
        if (this.isBB()) {



            this.xpReward = 10;
         if (this.getHealth() > 15) {
             this.setHealth(15);
         }


        if (this.getage() > 3600) {
            this.setthistobaby(false);
            this.xpReward = 30;
            this.setHealth(this.getMaxHealth());
        }
        }


    }


    @Override
    public EntityDimensions getDimensions(Pose pPose) {
        if (this.isBB()) {
           return super.getDimensions(pPose).scale(0.3f);
        }
            return super.getDimensions(pPose);
    }

    @Override
    public double getMeleeAttackRangeSqr(LivingEntity pEntity) {
        if (this.isBB()) return (double)(this.getBbWidth() * 2F * this.getBbWidth() * 2F + pEntity.getBbWidth());
        return (double)(this.getBbWidth() * 1.3F * this.getBbWidth() * 1.3F + pEntity.getBbWidth());
    }

    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(new AnimationController<>(this, "noattack", 0, this::noattack));
        controllerRegistrar.add(new AnimationController<>(this, "allways", 0, this::headpredicate));
        controllerRegistrar.add(new AnimationController<>(this, "allways2", 0, this::tailpredicate));
        controllerRegistrar.add(new AnimationController<>(this, "main_loop", 0, this::predicate));
        controllerRegistrar.add(new AnimationController<>(this, "onetime", state -> PlayState.STOP)
                .triggerableAnim("right", RawAnimation.begin().then("animation.vortigore.attackright", Animation.LoopType.PLAY_ONCE))
                .triggerableAnim("shoot", RawAnimation.begin().then("animation.vortigore.shoot", Animation.LoopType.PLAY_ONCE))
                .triggerableAnim("bigattack", RawAnimation.begin().then("animation.vortigore.bigattack", Animation.LoopType.PLAY_ONCE))
                .triggerableAnim("egg", RawAnimation.begin().then("animation.vortigore.egg", Animation.LoopType.PLAY_ONCE))
        );



    }

    private <T extends GeoAnimatable> PlayState noattack(AnimationState<T> tAnimationState) {
        tAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.vortigore.noattack", Animation.LoopType.LOOP));
        return PlayState.CONTINUE;
    }

    private <T extends GeoAnimatable> PlayState predicate(AnimationState<T> tAnimationState) {
        if(tAnimationState.isMoving()) {
            if (!this.isangry()) {
                tAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.vortigore.walk", Animation.LoopType.LOOP));
                return PlayState.CONTINUE;
            }
            tAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.vortigore.run", Animation.LoopType.LOOP));
            return PlayState.CONTINUE;
        }


        tAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.vortigore.idle", Animation.LoopType.LOOP));

        return PlayState.CONTINUE;
    }

    private <T extends GeoAnimatable> PlayState tailpredicate(AnimationState<T> tAnimationState) {
        tAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.vortigore.tail", Animation.LoopType.LOOP));
        return PlayState.CONTINUE;
    }

    private <T extends GeoAnimatable> PlayState headpredicate(AnimationState<T> tAnimationState) {
        tAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.vortigore.head", Animation.LoopType.LOOP));
        return PlayState.CONTINUE;
    }





    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
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
    public List<ExtendedSensor<Vortigore>> getSensors() {
        return ObjectArrayList.of(
                new HurtBySensor<>(),
                new NearbyPlayersSensor<>(),
                new NearbyBlocksSensor<Vortigore>().setRadius(20, 5).setPredicate((state, entity) -> state.is(ModBlocks.VOLTIGORE_NEST.get())),
                new NearbySecondBlocksSensor<Vortigore>().setRadius(30, 5).setPredicate((state, entity) -> state.is(ModBlocks.VOLTIGORE_EGG.get())),
                new NearbyLivingEntitySensor<Vortigore>()
                        .setPredicate((target, entity) ->
                                target instanceof Player || target instanceof IronGolem || target instanceof HalfLifeNeutral ||
                                        target instanceof AbstractVillager));
    }



    @Override
    public BrainActivityGroup<Vortigore> getCoreTasks() { // These are the tasks that run all the time (usually)
        return BrainActivityGroup.coreTasks(
                new LookAtTarget<>(),                      // Have the entity turn to face and look at its current look target
                new MoveToWalkTarget<>());
    }

    @Override
    public BrainActivityGroup<Vortigore> getIdleTasks() { // These are the tasks that run when the mob isn't doing anything else (usually)
        return BrainActivityGroup.idleTasks(
                new FirstApplicableBehaviour<Vortigore>(
                        new TargetOrRetaliate<>(),
                        new CustomBehaviour<>(entity -> BrainUtils.clearMemory(this, ModMemoryModuleType.CHECKED_LOCATIONS.get())).cooldownFor(entity -> 3000),
                        new CustomBehaviour<>(entity -> this.entityData.set(IS_ANGRY, false)).startCondition(entity -> this.isangry()),
                        new SetPlayerLookTarget<>(),
                        new VortigoreLayAnEggBehavior<>().startCondition(entity -> !this.isBB() && this.getvround() < 10 ),
                        new SetRandomLookTarget<>()),
                new OneRandomBehaviour<>(
                        new SetBlockToWalkTarget<>(),
                        new SetBlockToWalkTarget<>().cooldownFor(entity -> 200).startCondition(entity -> !this.isBB() && this.getvround() < 10 ),
                        new SetRandomWalkTarget<>(),          // Set a random walk target to a nearby position
                        new Idle<>().runFor(entity -> entity.getRandom().nextInt(40, 100)))); // Do nothing for 1.5->4.5 seconds
    }






    @Override
    public BrainActivityGroup<Vortigore> getFightTasks() { // These are the tasks that handle fighting
        return BrainActivityGroup.fightTasks(
                new InvalidateAttackTarget<>(),
                new Retaliate<>(),
                new CustomBehaviour<>(entity -> this.entityData.set(IS_ANGRY, true)).startCondition(entity -> !this.isangry()),
                new SetWalkTargetToAttackTarget<>().speedMod(1.5f),
                new FirstApplicableBehaviour<>(
                        new ConfigurableAnimatableMeleeAttack<Vortigore>(15, 1f * (this.isBB() ? 0 : 1), this.getmod(), 2 * (this.isBB() ? 0 : 1), null, 0, this.getBigAttackSound())
                                .whenStarting(entity -> triggerAnim("onetime", "bigattack"))
                                .cooldownFor(entity -> random.nextInt(20, 40))
                                .startCondition(entity -> BrainUtils.getMemory(this, MemoryModuleType.ATTACK_TARGET).getHealth()<10.5f),
                        new ConfigurableAnimatableMeleeAttack<Vortigore>(10, 0.5f * (this.isBB() ? 0 : 1), this.getmod(), (this.isBB() ? 0 : 1), null, 0, this.getRightAttackSound())
                                .whenStarting(entity -> triggerAnim("onetime", "right"))
                                .cooldownFor(entity -> random.nextInt(15, 25)),
                        new StopAndShoot<Vortigore>(12, 10, 1f).cooldownFor(entity -> random.nextInt(50, 90))
                                .whenStarting(entity -> triggerAnim("onetime", "shoot"))
                                .startCondition(entity -> !this.isBB())
                )


                );
    }




    @Nullable
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor p_33601_, DifficultyInstance p_33602_, MobSpawnType p_33603_, @Nullable SpawnGroupData p_33604_, @Nullable CompoundTag p_33605_) {
        RandomSource randomsource = p_33601_.getRandom();
        int i = randomsource.nextInt(900, 3600);
        this.setageup(i);
        return super.finalizeSpawn(p_33601_, p_33602_, p_33603_, p_33604_, p_33605_);
    }


    @Override
    public void performRangedAttack(LivingEntity livingentity, float p_33318_) {

        double d0 = this.distanceToSqr(livingentity);
        double d1 = livingentity.getX() - this.getX();
        double d2 = livingentity.getY(0.4D) - this.getY(0.4D);
        double d3 = livingentity.getZ() - this.getZ();
        double d4 = Math.sqrt(Math.sqrt(d0)) * 0.5D;


        this.playSound(getSpitSound(), this.getSoundVolume(), 1.2f);


        VoltigoreShock acidBall = new VoltigoreShock(this.level, this, d1, d2, d3); //this.getRandom().triangle(d1, 2.297D * d4)
        acidBall.setPos(this.getX() - (double)(this.getBbWidth() + 0.9F) * 0.5D * (double) Mth.sin(this.yBodyRot * ((float)Math.PI / 180F)), this.getEyeY() - (double)0.1F, this.getZ() + (double)(this.getBbWidth() + 0.9F) * 0.5D * (double) Mth.cos(this.yBodyRot * ((float)Math.PI / 180F)));
        this.level.addFreshEntity(acidBall);

    }


    @Override
    public float getVoicePitch() {
        if (this.isBB()) return 1.5f;
        return super.getVoicePitch();
    }

    public SoundEvent getSpitSound() {
        return ModSounds.VORTIGORE_SHOCK.get();
    }
    public SoundEvent getRightAttackSound() {
        return ModSounds.VORTIGORE_RIGHTATTACK.get();
    }
    public SoundEvent getBigAttackSound() {
        return ModSounds.VORTIGORE_BIGATTACK.get();
    }

    protected SoundEvent getDeathSound() {
        switch (this.random.nextInt(1,4)) {
            case 1:  return ModSounds.VORTIGORE_DIE1.get();
            case 2:  return ModSounds.VORTIGORE_DIE2.get();
            case 3:  return ModSounds.VORTIGORE_DIE3.get();
        }
        return ModSounds.HEADCRAB_1_DIE_1.get();
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource pDamageSource) {
        switch (this.random.nextInt(1,5)) {
            case 1:  return ModSounds.VORTIGORE_PAIN1.get();
            case 2:  return ModSounds.VORTIGORE_PAIN2.get();
            case 3:  return ModSounds.VORTIGORE_PAIN3.get();
            case 4:  return ModSounds.VORTIGORE_PAIN4.get();
        }
        return ModSounds.HEADCRAB_1_DIE_1.get();
    }


    protected SoundEvent getStepSound() {
        int i = this.random.nextInt(1,4);
        if (this.isangry()) i = this.random.nextInt(1,6);
        switch (i) {
            case 1:  return ModSounds.VORTIGORE_STEP1.get();
            case 2:  return ModSounds.VORTIGORE_STEP2.get();
            case 3:  return ModSounds.VORTIGORE_STEP3.get();
            case 4:  return ModSounds.VORTIGORE_RUN_STEP1.get();
            case 5:  return ModSounds.VORTIGORE_RUN_STEP2.get();
        }
        return SoundEvents.FROG_STEP;
    }

    @Override
    protected void playStepSound(BlockPos pPos, BlockState pState) {
        if (this.tickCount%2 == 0) {
            BlockState blockstate = this.level.getBlockState(pPos.above());
            boolean flag = blockstate.is(BlockTags.INSIDE_STEP_SOUND_BLOCKS);
            if (flag || !pState.getMaterial().isLiquid()) {
                this.playSound(getStepSound());
            }
        }
    }






    protected SoundEvent getAmbientSound() {

        int i = this.random.nextInt(1,4);
        int j = this.random.nextInt(4, 7);
        int k = this.random.nextInt(7,10);
        int f = HLperUtil.getrandomof(i, j, k, this.getvround() > 2, this.isangry());


        switch (f) {
            case 1:  return ModSounds.VORTIGORE_IDLE1.get();
            case 2:  return ModSounds.VORTIGORE_IDLE2.get();
            case 3:  return ModSounds.VORTIGORE_IDLE3.get();
            case 4:  return ModSounds.VORTIGORE_TALK1.get();
            case 5:  return ModSounds.VORTIGORE_TALK2.get();
            case 6:  return ModSounds.VORTIGORE_TALK3.get();
            case 7:  return ModSounds.VORTIGORE_ALERT1.get();
            case 8:  return ModSounds.VORTIGORE_ALERT2.get();
            case 9:  return ModSounds.VORTIGORE_ALERT3.get();
        }
        return SoundEvents.FROG_STEP;
    }








}
