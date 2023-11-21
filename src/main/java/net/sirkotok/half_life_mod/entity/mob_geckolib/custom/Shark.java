package net.sirkotok.half_life_mod.entity.mob_geckolib.custom;


import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.BlockPos;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.fluids.FluidType;
import net.sirkotok.half_life_mod.entity.base.HalfLifeMonster;
import net.sirkotok.half_life_mod.entity.base.HalfLifeNeutral;
import net.sirkotok.half_life_mod.entity.brain.behaviour.PushToWalkTarget;
import net.sirkotok.half_life_mod.entity.brain.behaviour.Retaliate;
import net.sirkotok.half_life_mod.entity.brain.behaviour.TargetOrRetaliateHLT;
import net.sirkotok.half_life_mod.entity.brain.sensor.SmellSensor;
import net.sirkotok.half_life_mod.sound.ModSounds;
import net.sirkotok.half_life_mod.util.ModTags;
import net.tslat.smartbrainlib.api.SmartBrainOwner;
import net.tslat.smartbrainlib.api.core.BrainActivityGroup;
import net.tslat.smartbrainlib.api.core.SmartBrainProvider;
import net.tslat.smartbrainlib.api.core.behaviour.FirstApplicableBehaviour;
import net.tslat.smartbrainlib.api.core.behaviour.custom.look.LookAtTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.path.SetWalkTargetToAttackTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.target.InvalidateAttackTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.target.SetPlayerLookTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.target.SetRandomLookTarget;
import net.tslat.smartbrainlib.api.core.sensor.ExtendedSensor;
import net.tslat.smartbrainlib.api.core.sensor.vanilla.HurtBySensor;
import net.tslat.smartbrainlib.api.core.sensor.vanilla.NearbyLivingEntitySensor;
import net.tslat.smartbrainlib.api.core.sensor.vanilla.NearbyPlayersSensor;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.List;


public class Shark extends HalfLifeMonster implements GeoEntity, SmartBrainOwner<Shark> {

 //TODO: sounds
    public int light = 0;


    @Override
    protected void actuallyHurt(DamageSource p_21240_, float p_21241_) {
        super.actuallyHurt(p_21240_, p_21241_);
    }

    private BlockPos targetPosition;


    private AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);


    @Override
    protected void playSwimSound(float pVolume) {
        if (RandomSource.create().nextFloat() < 0.05f) super.playSwimSound(pVolume);
    }


    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        if (RandomSource.create().nextFloat() < 0.5f) return  ModSounds.LEECH_IDLE1.get();
        else return ModSounds.LEECH_IDLE2.get();
    }



    public SoundEvent getBiteSound(){
        switch(RandomSource.create().nextInt(1,4)){
            case 1: return ModSounds.LEECH_BITE1.get();
            case 2: return ModSounds.LEECH_BITE2.get();
        }
        return ModSounds.LEECH_BITE3.get();
    }

    public static final EntityDataAccessor<Integer> MOUTH_OP = SynchedEntityData.defineId(Shark.class, EntityDataSerializers.INT); // -1 to 1
    public static final EntityDataAccessor<Integer> TITL_UD = SynchedEntityData.defineId(Shark.class, EntityDataSerializers.INT); // -2 to 2
    public static final EntityDataAccessor<Integer> TITL_RL_NUM = SynchedEntityData.defineId(Shark.class, EntityDataSerializers.INT); // 1 to 3
    public static final EntityDataAccessor<Integer> TITL_RL = SynchedEntityData.defineId(Shark.class, EntityDataSerializers.INT); // -1 to 1


    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(MOUTH_OP, 0);
        this.entityData.define(TITL_UD, 0);
        this.entityData.define(TITL_RL, 0);
        this.entityData.define(TITL_RL_NUM, 1);
    }

    public int getMouthPose(){
        return this.entityData.get(MOUTH_OP);
    }
    public int getTiltRL(){
        return this.entityData.get(TITL_RL);
    }
    public int getTiltRLNUM(){
        return this.entityData.get(TITL_RL_NUM);
    }
    public int getTiltUD(){
        return this.entityData.get(TITL_UD);
    }

    public void setAnimationPose(int mouth, int tilt_ud, int tilt_rl, int tilt_rlnum){
        this.entityData.set(MOUTH_OP, mouth);
        this.entityData.set(TITL_RL, tilt_rl);
        this.entityData.set(TITL_UD, tilt_ud);
        this.entityData.set(TITL_RL_NUM, tilt_rlnum);
    }

    public void setMouthPose(int mouth){
        this.setAnimationPose(mouth, this.getTiltUD(), this.getTiltRL(), this.getTiltRLNUM());
    }
    public void setTitlUd(int titlud){
        this.setAnimationPose(this.getMouthPose(), titlud, this.getTiltRL(), this.getTiltRLNUM());
    }
    public void setTitlRl(int titlrl, int titlrlNum){
        this.setAnimationPose(this.getMouthPose(), this.getTiltUD(), titlrl, titlrlNum);
    }

    public void randomtitlRl(){
        int i = RandomSource.create().nextInt(-1, 2);
        int j = RandomSource.create().nextInt(1, 4);
        if (i == 0) i = RandomSource.create().nextInt(-1, 2);
        this.setTitlRl(i, j);
    }

    public void resettitlRl(){
        this.setTitlRl(0, 1);
    }

    public void updatetitlUD(){
        int i = 0;
        Float dy = (float)this.getDeltaMovement().y();
        if (dy > 1) i = 1;
        if (dy > 2) i = 2;
        if (dy < -1) i = -1;
        if (dy < -2) i = -2;

        this.setTitlUd(i);

    }


    @Override
    public boolean doHurtTarget(Entity pEntity) {
        this.randomtitlRl();
        return super.doHurtTarget(pEntity);
    }





    @Override
    public void aiStep() {
        super.aiStep();


        if (this.tickCount % 30 == 0) {
            this.setMouthPose(RandomSource.create().nextInt(-1, 2));
            this.resettitlRl();
        }

        if (this.tickCount % 5 == 0) {
            this.updatetitlUD();

            List<Entity> list = this.level.getEntities(this, this.getBoundingBox(), obj -> obj.equals(this.getTarget()));
            for (Entity entity : list) {
                if (entity instanceof LivingEntity living) {
                    this.doHurtTarget(living);
                    this.playSound(this.getBiteSound(), this.getSoundVolume(), this.getVoicePitch());
                }
            }
        }


    }

    public Shark(EntityType type, Level level) {
        super(type, level);
        this.xpReward = 20;
        this.setPathfindingMalus(BlockPathTypes.WATER, 0.0F);
        this.setPathfindingMalus(BlockPathTypes.WATER_BORDER, 0.0F);
    }



    public static AttributeSupplier setAttributes() {
        return Monster.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 100D)
                .add(Attributes.ATTACK_DAMAGE, 8f)
                .add(Attributes.ATTACK_SPEED, 3.0f)
                .add(Attributes.ATTACK_KNOCKBACK, 0.5f)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.5f)
                .add(Attributes.MOVEMENT_SPEED, 0.30f).build();
    }



        public int getwateremountbelow() {
        int i = 0;
        for (int j = 1; j<20; j++){
            BlockPos pos = new BlockPos(this.blockPosition().getX(), this.blockPosition().getY() - j, this.blockPosition().getZ());
            BlockState blockstate = this.level.getBlockState(pos);
            if (!blockstate.is(Blocks.WATER)) return i;
            i++;}
        return i;
    }

    public int getwateremountabove() {
        int i = 0;
        for (int j = 1; j<20; j++){
            BlockPos pos = new BlockPos(this.blockPosition().getX(), this.blockPosition().getY() + j, this.blockPosition().getZ());
            BlockState blockstate = this.level.getBlockState(pos);
            if (!blockstate.is(Blocks.WATER)) return i;
            i++;}
        return i;
    }







    @Override
    protected float getSoundVolume() {
        return 0.5f;
    }


  //  protected SoundEvent getDeathSound() {
   //     return ModSounds.COCKROACH_DIES.get();
  //  }




    @Override
    protected Brain.Provider<?> brainProvider() {
        return new SmartBrainProvider<>(this);
    }

    @Override
    protected void customServerAiStep() {
        tickBrain(this);

        if (this.isInWaterOrBubble()) {

            if (this.targetPosition != null && (!this.level.getBlockState(this.targetPosition).equals(Blocks.WATER.defaultBlockState()) || this.targetPosition.getY() <= this.level.getMinBuildHeight())) {
                this.targetPosition = null;
            }

            if (this.targetPosition == null || this.random.nextInt(30) == 0 || this.targetPosition.closerToCenterThan(this.position(), 2.0D)) {
                this.targetPosition = BlockPos.containing(this.getX() + (double) this.random.nextInt(7) - (double) this.random.nextInt(7), this.getY() + (double) this.random.nextInt(6) - 2.0D, this.getZ() + (double) this.random.nextInt(7) - (double) this.random.nextInt(7));
            }

            double d2 = (double) this.targetPosition.getX() + 0.5D - this.getX();
            double d0 = (double) this.targetPosition.getY() + 0.1D - this.getY();
            double d1 = (double) this.targetPosition.getZ() + 0.5D - this.getZ();
            Vec3 vec3 = this.getDeltaMovement();
            Vec3 vec31 = vec3.add((Math.signum(d2) * 0.5D - vec3.x) * (double) 0.1F, (Math.signum(d0) * (double) 0.7F - vec3.y) * (double) 0.1F, (Math.signum(d1) * 0.5D - vec3.z) * (double) 0.1F);
         //   if (this.getTarget() == null) this.setDeltaMovement(vec31);
            float f = (float) (Mth.atan2(vec31.z, vec31.x) * (double) (180F / (float) Math.PI)) - 90.0F;
            float f1 = Mth.wrapDegrees(f - this.getYRot());
            this.zza = 0.5F;
            this.setYRot(this.getYRot() + f1);

        }



    }



    @Override
    public List<ExtendedSensor<Shark>> getSensors() {
        return ObjectArrayList.of(
                new HurtBySensor<>(),
                new SmellSensor<>(),
                new NearbyPlayersSensor<>(),
                new NearbyLivingEntitySensor<Shark>()
                        .setPredicate((target, entity) ->
                                (target instanceof Player ||
                                target.getType().is(ModTags.EntityTypes.FACTION_COMBINE) ||
                              /*  (target.getType().getCategory().getName().equals("creature") && !(target instanceof Cockroach)) ||
                                target.getType().getCategory().getName().equals("water_creature") ||
                                target.getType().getCategory().getName().equals("water_ambient") ||
                                target.getType().getCategory().getName().equals("underground_water_creature") || */
                                target.getType().getCategory().getName().equals("axolotls") ||
                                target instanceof IronGolem ||
                                target instanceof AbstractVillager ||
                                target instanceof HalfLifeNeutral ) && target.isInWaterOrBubble() && !(target instanceof Shark)
                        ));
    }



    @Override
    public BrainActivityGroup<Shark> getCoreTasks() { // These are the tasks that run all the time (usually)
        return BrainActivityGroup.coreTasks(
                new LookAtTarget<>(),
                new PushToWalkTarget<>().startCondition(entity -> this.isInWaterOrBubble()).cooldownFor(entity -> entity.getRandom().nextInt(1, 2))
        );
    }

    @Override
    public BrainActivityGroup<Shark> getIdleTasks() { // These are the tasks that run when the mob isn't doing anything else (usually)
        return BrainActivityGroup.idleTasks(
                new FirstApplicableBehaviour<Shark>(
                        new TargetOrRetaliateHLT<>(),
                        new SetPlayerLookTarget<>(),
                        new SetRandomLookTarget<>()
                )
        );

    }

    @Override
    public void jumpInFluid(FluidType type) {
    }

    @Override
    public BrainActivityGroup<Shark> getFightTasks() { // These are the tasks that handle fighting
        return BrainActivityGroup.fightTasks(
                new InvalidateAttackTarget<>().invalidateIf((entity, target) -> target instanceof Player pl && (pl.isCreative() || pl.isSpectator()) || !target.isInWaterOrBubble()),
                new Retaliate<>(),
                new SetWalkTargetToAttackTarget<>()
        );

    }




    @Override
    public boolean canDrownInFluidType(FluidType type) {
        if (type.equals(ForgeMod.WATER_TYPE.get())) {
            return false;
        }
        if (type.equals(ForgeMod.EMPTY_TYPE.get())) return true;
        return super.canDrownInFluidType(type);
    }




    @Override
    protected float getWaterSlowDown() {
      //  if (this.getTarget() != null) return 0.98f;
        return 0.9f;

    }


    @Override
    public boolean isPushedByFluid(FluidType type) {
        if (type.equals(ForgeMod.WATER_TYPE.get())) {
            return false;
        }
        return super.isPushedByFluid(type);
    }

    @Override
    public boolean isNoGravity() {
        return this.isInWaterOrBubble();
    }

    @Override
    public double getFluidMotionScale(FluidType type) {
        if (type.equals(ForgeMod.WATER_TYPE.get())) {
            return 0.98;
        }
        return super.getFluidMotionScale(type);
    }






    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(new AnimationController<>(this, "idle", 0, this::idle));
        controllerRegistrar.add(new AnimationController<>(this, "controller", 0, this::mouthpred));
        controllerRegistrar.add(new AnimationController<>(this, "walk", 0, this::walkpred));
        controllerRegistrar.add(new AnimationController<>(this, "tiltrl", 0, this::tiltrl));
        controllerRegistrar.add(new AnimationController<>(this, "tiltud", 0, this::tiltud));
    }

    private <T extends GeoAnimatable> PlayState idle(AnimationState<T> tAnimationState) {

        tAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.shark.idle", Animation.LoopType.LOOP));
        return PlayState.CONTINUE;
    }

    private <T extends GeoAnimatable> PlayState tiltrl(AnimationState<T> tAnimationState) {
        int j = this.getTiltRL();
        int i = this.getTiltRLNUM();

        if (j == 1) {
        tAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.shark.left" + i, Animation.LoopType.LOOP));
        return PlayState.CONTINUE; }
        if (j == -1) {
            tAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.shark.right" + i, Animation.LoopType.LOOP));
            return PlayState.CONTINUE; }

        tAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.shark.straightrl", Animation.LoopType.LOOP));
        return PlayState.CONTINUE;

    }

    private <T extends GeoAnimatable> PlayState tiltud(AnimationState<T> tAnimationState) {
        int j = this.getTiltUD();
        switch(j){
            case -2: {
                tAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.shark.tiltdown2", Animation.LoopType.LOOP));
                return PlayState.CONTINUE;
            }
            case -1: {
                tAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.shark.tiltdown", Animation.LoopType.LOOP));
                return PlayState.CONTINUE;
            }
            case 0: {
                tAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.shark.straight", Animation.LoopType.LOOP));
                return PlayState.CONTINUE;
            }
            case 1: {
                tAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.shark.tiltup", Animation.LoopType.LOOP));
                return PlayState.CONTINUE;
            }
            case 2: {
                tAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.shark.tiltup2", Animation.LoopType.LOOP));
                return PlayState.CONTINUE;
            }

        }

        tAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.shark.straight", Animation.LoopType.LOOP));
        return PlayState.CONTINUE;

    }


    private <T extends GeoAnimatable> PlayState walkpred(AnimationState<T> tAnimationState) {


        tAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.shark.swim", Animation.LoopType.LOOP));
        return PlayState.CONTINUE;

    }
    private <T extends GeoAnimatable> PlayState mouthpred(AnimationState<T> tAnimationState) {
        int i = this.getMouthPose();
        switch(i){
            case -1: {
                tAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.shark.mouthclothed", Animation.LoopType.LOOP));
                return PlayState.CONTINUE;
            }
            case 0: {
                tAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.shark.mouthopen", Animation.LoopType.LOOP));
                return PlayState.CONTINUE;
            }
            case 1: {
                tAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.shark.mouthbite", Animation.LoopType.LOOP));
                return PlayState.CONTINUE;
            }
        }

        tAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.shark.mouthopen", Animation.LoopType.LOOP));
        return PlayState.CONTINUE;

    }




    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

}

