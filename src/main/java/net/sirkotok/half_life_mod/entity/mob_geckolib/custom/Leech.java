package net.sirkotok.half_life_mod.entity.mob_geckolib.custom;


import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.BlockPos;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Slime;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.fml.common.Mod;
import net.sirkotok.half_life_mod.entity.base.HalfLifeMonster;
import net.sirkotok.half_life_mod.entity.base.HalfLifeNeutral;
import net.sirkotok.half_life_mod.entity.brain.behaviour.*;
import net.sirkotok.half_life_mod.entity.brain.sensor.SmellSensor;
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
import net.tslat.smartbrainlib.api.core.behaviour.custom.move.EscapeSun;
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
import net.tslat.smartbrainlib.util.EntityRetrievalUtil;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.ArrayList;
import java.util.List;


public class Leech extends HalfLifeMonster implements GeoEntity, SmartBrainOwner<Leech> {

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


    protected void defineSynchedData() {
        super.defineSynchedData();
    }



    @Override
    public void aiStep() {
        super.aiStep();

        if (this.tickCount % 3 == 0) {
            List<Entity> list = this.level.getEntities(this, this.getBoundingBox(), obj -> obj.equals(this.getTarget()));
            for (Entity entity : list) {
                if (entity instanceof LivingEntity living) {
                    this.doHurtTarget(living);
                    this.playSound(this.getBiteSound(), this.getSoundVolume(), this.getVoicePitch());
                }
            }
        }


    }

    public Leech(EntityType type, Level level) {
        super(type, level);
        this.xpReward = 0;
        this.setPathfindingMalus(BlockPathTypes.WATER, 0.0F);
        this.setPathfindingMalus(BlockPathTypes.WATER_BORDER, 0.0F);
    }


    @Override
    public boolean shouldDropExperience() {
        return false;
    }

    public static AttributeSupplier setAttributes() {
        return Monster.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 3D)
                .add(Attributes.ATTACK_DAMAGE, 1f)
                .add(Attributes.ATTACK_SPEED, 3.0f)
                .add(Attributes.ATTACK_KNOCKBACK, 0.1f)
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

    protected void handleAirSupply(int pAirSupply) {
        if (this.isAlive() && !this.isInWaterOrBubble()) {
            this.setAirSupply(pAirSupply - 1);
            if (this.getAirSupply() == -20) {
                this.setAirSupply(0);
                this.hurt(this.damageSources().drown(), 2.0F);
            }
        } else {
            this.setAirSupply(300);
        }

    }


    @Override
    protected Brain.Provider<?> brainProvider() {
        return new SmartBrainProvider<>(this);
    }

    @Override
    protected void customServerAiStep() {
        tickBrain(this);
        int i = this.getAirSupply();
        handleAirSupply(i);

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
            if (this.getTarget() == null) this.setDeltaMovement(vec31);
            float f = (float) (Mth.atan2(vec31.z, vec31.x) * (double) (180F / (float) Math.PI)) - 90.0F;
            float f1 = Mth.wrapDegrees(f - this.getYRot());
            this.zza = 0.5F;
            this.setYRot(this.getYRot() + f1);

        }



    }



    @Override
    public List<ExtendedSensor<Leech>> getSensors() {
        return ObjectArrayList.of(
                new HurtBySensor<>(),
                new SmellSensor<>(),
                new NearbyPlayersSensor<>(),
                new NearbyLivingEntitySensor<Leech>()
                        .setPredicate((target, entity) ->
                                (target instanceof Player ||
                                target.getType().is(ModTags.EntityTypes.FACTION_COMBINE) ||
                                (target.getType().getCategory().getName().equals("creature") && !(target instanceof Cockroach)) ||
                                target.getType().getCategory().getName().equals("water_creature") ||
                                target.getType().getCategory().getName().equals("water_ambient") ||
                                target.getType().getCategory().getName().equals("underground_water_creature") ||
                                target.getType().getCategory().getName().equals("axolotls") ||
                                target instanceof IronGolem ||
                                target instanceof Slime ||
                                target instanceof AbstractVillager ||
                                target instanceof HalfLifeNeutral ) && target.isInWaterOrBubble() && !(target instanceof Leech)
                        ));
    }



    @Override
    public BrainActivityGroup<Leech> getCoreTasks() { // These are the tasks that run all the time (usually)
        return BrainActivityGroup.coreTasks(
                new LookAtTarget<>(),
                new PushToWalkTarget<>().startCondition(entity -> this.isInWaterOrBubble()).cooldownFor(entity -> entity.getRandom().nextInt(1, 40))
        );
    }

    @Override
    public BrainActivityGroup<Leech> getIdleTasks() { // These are the tasks that run when the mob isn't doing anything else (usually)
        return BrainActivityGroup.idleTasks(
                new FirstApplicableBehaviour<Leech>(
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
    public BrainActivityGroup<Leech> getFightTasks() { // These are the tasks that handle fighting
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
        controllerRegistrar.add(new AnimationController<>(this, "controller", 0, this::predicate));
        controllerRegistrar.add(new AnimationController<>(this, "walk", 0, this::walkpred));

    }



    private <T extends GeoAnimatable> PlayState walkpred(AnimationState<T> tAnimationState) {
        tAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.leech.swim", Animation.LoopType.LOOP));
        return PlayState.CONTINUE;

    }
    private <T extends GeoAnimatable> PlayState predicate(AnimationState<T> tAnimationState) {
        tAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.leech.face", Animation.LoopType.LOOP));
        return PlayState.CONTINUE;

    }




    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

}

