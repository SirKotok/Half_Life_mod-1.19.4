package net.sirkotok.half_life_mod.entity.mob_geckolib.custom;


import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.BlockPos;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
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
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.sirkotok.half_life_mod.entity.base.HalfLifeMonster;
import net.sirkotok.half_life_mod.entity.base.HalfLifeNeutral;
import net.sirkotok.half_life_mod.entity.brain.behaviour.*;
import net.sirkotok.half_life_mod.entity.modinterface.VariableRangedMob;
import net.sirkotok.half_life_mod.entity.projectile.ControllerBaseProjectile;
import net.sirkotok.half_life_mod.entity.projectile.ControllerBigProjectile;
import net.sirkotok.half_life_mod.particle.HalfLifeParticles;
import net.sirkotok.half_life_mod.sound.HalfLifeSounds;
import net.sirkotok.half_life_mod.util.HLperUtil;
import net.sirkotok.half_life_mod.util.HLTags;
import net.sirkotok.half_life_mod.util.InfightingUtil;
import net.tslat.smartbrainlib.api.SmartBrainOwner;
import net.tslat.smartbrainlib.api.core.BrainActivityGroup;
import net.tslat.smartbrainlib.api.core.SmartBrainProvider;
import net.tslat.smartbrainlib.api.core.behaviour.FirstApplicableBehaviour;
import net.tslat.smartbrainlib.api.core.behaviour.OneRandomBehaviour;
import net.tslat.smartbrainlib.api.core.behaviour.custom.look.LookAtTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.misc.CustomBehaviour;
import net.tslat.smartbrainlib.api.core.behaviour.custom.path.SetRandomWalkTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.path.SetWalkTargetToAttackTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.target.InvalidateAttackTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.target.SetPlayerLookTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.target.SetRandomLookTarget;
import net.tslat.smartbrainlib.api.core.sensor.ExtendedSensor;
import net.tslat.smartbrainlib.api.core.sensor.custom.NearbyBlocksSensor;
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


public class Controller extends HalfLifeMonster implements GeoEntity, RangedAttackMob, VariableRangedMob, SmartBrainOwner<Controller> {

    private BlockPos targetPosition;

    @Override
    protected void actuallyHurt(DamageSource p_21240_, float p_21241_) {
        if (p_21240_.is(DamageTypes.IN_WALL)) {
            return; }

        super.actuallyHurt(p_21240_, p_21241_);
    }





    private AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);



    public static final EntityDataAccessor<Integer> CHARGE = SynchedEntityData.defineId(Controller.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Boolean> IS_ANGRY = SynchedEntityData.defineId(Controller.class, EntityDataSerializers.BOOLEAN);

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(IS_ANGRY, false);
        this.entityData.define(CHARGE, 0);
    }

    public int getcharge(){
        return this.entityData.get(CHARGE);
    }
    public void setcharge(int i){
        this.entityData.set(CHARGE, i);
    }

    @Override
    public void tick() {
        super.tick();

     //   if (this.tickCount % 75 == 0) this.triggerAnim("onetime", "idle");

        if (this.level.isClientSide() && this.getcharge() == 1) {
            Vec3 startPos = new Vec3(this.getX() - (double)(this.getBbWidth() + 1.0F) * 0.5D * (double)Mth.sin(this.yBodyRot * ((float)Math.PI / 180F)), this.getEyeY() - (double)0.4F, this.getZ() + (double)(this.getBbWidth() + 1.0F) * 0.5D * (double) Mth.cos(this.yBodyRot * ((float)Math.PI / 180F)));
            this.level.addParticle(HalfLifeParticles.STAT_GLOW.get(), startPos.x, startPos.y, startPos.z, 0, 1d, 0);
        }
        if (this.level.isClientSide() && this.getcharge() == 2) {
            Vec3 startPos = this.position().add(0, this.getBbHeight()-0.2f, 0);
            this.level.addParticle(HalfLifeParticles.STAT_GLOW.get(), startPos.x, startPos.y, startPos.z, 0, 3d, 0);
        }



    }
    protected boolean isangry() {
        return this.entityData.get(IS_ANGRY);
    }

    @Override
    public boolean isNoGravity() {
        return true;
    }

    public Controller(EntityType type, Level level) {
        super(type, level);
        this.xpReward = 5;
    }







    public static AttributeSupplier setAttributes() {
        return Monster.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 30D)
                .add(Attributes.ATTACK_DAMAGE, 4f)
                .add(Attributes.ATTACK_SPEED, 1.0f)
                .add(Attributes.ATTACK_KNOCKBACK, 1.0f)
                .add(Attributes.MOVEMENT_SPEED, 0.22f).build();
    }


    public int getairemountbelow() {
        int i = 0;
        for (int j = 1; j<20; j++){
            BlockPos pos = new BlockPos(this.blockPosition().getX(), this.blockPosition().getY() - j, this.blockPosition().getZ());
            BlockState blockstate = this.level.getBlockState(pos);
            if (!blockstate.isAir()) return i;
            i++;}
        return i;
    }

    public int getairemountabove() {
        int i = 0;
        for (int j = 1; j<20; j++){
            BlockPos pos = new BlockPos(this.blockPosition().getX(), this.blockPosition().getY() + j, this.blockPosition().getZ());
            BlockState blockstate = this.level.getBlockState(pos);
            if (!blockstate.isAir()) return i;
            i++;}
        return i;
    }



    @Override
    public void aiStep() {
        super.aiStep();

        if (!this.level.isClientSide) {
        if (this.tickCount % 4 == 0) {
            int m = random.nextFloat() < 0.5f ? 1 : -1;
            int n = random.nextFloat() < 0.5f ? 1 : -1;
            int k = random.nextFloat() < 0.5f ? 1 : -1;
        if (BrainUtils.getMemory(this, MemoryModuleType.WALK_TARGET) == null) this.setDeltaMovement(this.getDeltaMovement().x(), 0.2f * m * random.nextFloat(), this.getDeltaMovement().z());
        if (this.getairemountbelow() < 3 && this.random.nextFloat() < 0.1f) this.setDeltaMovement(this.getDeltaMovement().x(), 0.3f, this.getDeltaMovement().z());
        if (this.getairemountbelow() > 10) this.setDeltaMovement(this.getDeltaMovement().x(), -0.05, this.getDeltaMovement().z());
        if (this.getairemountabove() < 3) this.setDeltaMovement(this.getDeltaMovement().x(), -0.05, this.getDeltaMovement().z());
        if (this.random.nextFloat() < 0.05f) this.setDeltaMovement(this.getDeltaMovement().x() + 0.3f * n * random.nextFloat(), this.getDeltaMovement().y() +  0.3f * m * random.nextFloat(), this.getDeltaMovement().z() + 0.3f * k * random.nextFloat());


    }
    }
    }



    public SoundEvent getattacksound() {
        switch (this.random.nextInt(1,4)) {
            case 1:  return HalfLifeSounds.CON_ATTACK1.get();
            case 2:  return HalfLifeSounds.CON_ATTACK2.get();
            case 3:  return HalfLifeSounds.CON_ATTACK3.get();
        }
        return HalfLifeSounds.HEADCRAB_1_DIE_1.get();
    }

    protected SoundEvent getDeathSound() {
        switch (this.random.nextInt(1,3)) {
            case 1:  return HalfLifeSounds.CON_DIE1.get();
            case 2:  return HalfLifeSounds.CON_DIE2.get();
        }
        return HalfLifeSounds.HEADCRAB_1_DIE_1.get();
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource pDamageSource) {
        switch (this.random.nextInt(1,4)) {
            case 1:  return HalfLifeSounds.CON_PAIN1.get();
            case 2:  return HalfLifeSounds.CON_PAIN2.get();
            case 3:  return HalfLifeSounds.CON_PAIN3.get();
        }
        return HalfLifeSounds.HEADCRAB_1_DIE_1.get();
    }




    protected SoundEvent getAmbientSound() {
        int f = !this.isangry() ? random.nextInt(1, 6) : random.nextInt(random.nextFloat() < 0.2f ? 0 : 6, 8);
        switch (f) {
            case 1:  return HalfLifeSounds.CON_IDLE1.get();
            case 2:  return HalfLifeSounds.CON_IDLE2.get();
            case 3:  return HalfLifeSounds.CON_IDLE3.get();
            case 4:  return HalfLifeSounds.CON_IDLE4.get();
            case 5:  return HalfLifeSounds.CON_IDLE5.get();
            case 6:  return HalfLifeSounds.CON_ALERT1.get();
            case 7:  return HalfLifeSounds.CON_ALERT2.get();
            case 8:  return HalfLifeSounds.CON_ALERT3.get();
        }
        return SoundEvents.FROG_STEP;
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
        return 0.6f;
    }





    @Override
    protected Brain.Provider<?> brainProvider() {
        return new SmartBrainProvider<>(this);
    }

    @Override
    protected void customServerAiStep() {
        tickBrain(this);

        if (BrainUtils.getTargetOfEntity(this) != null) {
            Vec3 vec31 =  BrainUtils.getTargetOfEntity(this).position();
            Vec3 vec3 = this.position();
            Vec3 between = vec3.subtract(vec31);
            this.setYRot((float) HLperUtil.yanglefromvec3(between));
            this.setXRot((float) HLperUtil.yanglefromvec3(between));
            return;
        }

        if (this.targetPosition != null && (!this.level.isEmptyBlock(this.targetPosition) || this.targetPosition.getY() <= this.level.getMinBuildHeight())) {
            this.targetPosition = null;
        }
        if (this.targetPosition == null || this.random.nextInt(30) == 0 || this.targetPosition.closerToCenterThan(this.position(), 2.0D)) {
            this.targetPosition = BlockPos.containing(this.getX() + (double)this.random.nextInt(7) - (double)this.random.nextInt(7), this.getY() + (double)this.random.nextInt(6) - 2.0D, this.getZ() + (double)this.random.nextInt(7) - (double)this.random.nextInt(7));
        }

        if (BrainUtils.getTargetOfEntity(this) != null) {this.targetPosition = BrainUtils.getTargetOfEntity(this).blockPosition();}

        double d2 = (double)this.targetPosition.getX() + 0.5D - this.getX();
        double d0 = (double)this.targetPosition.getY() + 0.1D - this.getY();
        double d1 = (double)this.targetPosition.getZ() + 0.5D - this.getZ();
        Vec3 vec3 = this.getDeltaMovement();
        Vec3 vec31 = vec3.add((Math.signum(d2) * 0.5D - vec3.x) * (double)0.1F, (Math.signum(d0) * (double)0.7F - vec3.y) * (double)0.1F, (Math.signum(d1) * 0.5D - vec3.z) * (double)0.1F);
        // this.setDeltaMovement(vec31);
        float f = (float)(Mth.atan2(vec31.z, vec31.x) * (double)(180F / (float)Math.PI)) - 90.0F;
        float f1 = Mth.wrapDegrees(f - this.getYRot());
        this.zza = 0.5F;
        this.setYRot(this.getYRot() + f1);





    }



    @Override
    public List<ExtendedSensor<Controller>> getSensors() {
        return ObjectArrayList.of(
                new HurtBySensor<>(),
                new NearbyPlayersSensor<>(),
                new NearbyBlocksSensor<Controller>().setRadius(16D, 6D).setPredicate((state, entity) -> state.isAir()),
                new NearbyLivingEntitySensor<Controller>()
                        .setPredicate((target, entity) -> InfightingUtil.commonenemy(target) || InfightingUtil.XenForcesSpecific(target)
                              ));
    }



    @Override
    public BrainActivityGroup<Controller> getCoreTasks() {
        return BrainActivityGroup.coreTasks(
                new LookAtTarget<>(),
                new PushToWalkTarget<>().setYMoveSpeedMod(entity -> 0.3f),
                new CustomBehaviour<>(entity -> BrainUtils.clearMemory(this, MemoryModuleType.WALK_TARGET)).cooldownFor(entity -> 100),
                new CustomBehaviour<>(entity -> BrainUtils.clearMemory(this, MemoryModuleType.LOOK_TARGET)).cooldownFor(entity -> 250)

        );

    }

    @Override
    public BrainActivityGroup<Controller> getIdleTasks() { // These are the tasks that run when the mob isn't doing anything else (usually)
        return BrainActivityGroup.idleTasks(
                new FirstApplicableBehaviour<Controller>(
                       new TargetOrRetaliateHLT<>(),
                         new SetPlayerLookTarget<>(),
                        new SetRandomLookTarget<>()),
                        new CustomBehaviour<>(entity -> this.entityData.set(IS_ANGRY, false)).startCondition(entity -> this.entityData.get(IS_ANGRY)),
                        new SetRandomWalkTarget<>().startCondition(entity -> this.getairemountbelow() > 14),
                        new OneRandomBehaviour<>(
                             new SetRandomWalkTarget<>(),
                             new SetBlockToWalkTargetNoInterest<>(),
                             new SetBlockToWalkTargetNoInterest<>()
                        )

        );
    }

    public float horiz(Entity pEntity) {
        float f = (float)(this.getX() - pEntity.getX());
        float f2 = (float)(this.getZ() - pEntity.getZ());
        return Mth.sqrt(f * f + f2 * f2);
    }
    public float vert(Entity pEntity) {
        float f1 = (float)(this.getY() - pEntity.getY());
        return Mth.sqrt(f1*f1);
    }

    @Override
    public BrainActivityGroup<Controller> getFightTasks() { // These are the tasks that handle fighting
        return BrainActivityGroup.fightTasks(
                new InvalidateAttackTarget<>(),
                new Retaliate<>(),
                new CustomBehaviour<>(entity -> this.entityData.set(IS_ANGRY, true)).startCondition(entity -> !this.entityData.get(IS_ANGRY)),
                new SetLookTargetToAttackTarget<>(),
                new SetWalkTargetToAttackTarget<>().startCondition(entity -> this.distanceTo(HLperUtil.TargetOrThis(this)) > 10 || this.vert(HLperUtil.TargetOrThis(this)) > 7).cooldownFor(entity -> 5),
                new CustomBehaviour<>(entity -> BrainUtils.clearMemory(this, MemoryModuleType.WALK_TARGET)).startCondition(entity -> this.distanceTo(HLperUtil.TargetOrThis(this)) < 6 || this.horiz(HLperUtil.TargetOrThis(this)) < 13).cooldownFor(entity -> 5),
                new OneRandomBehaviour<>(
                        new SetRandomWalkTarget<>(),
                        new SetWalkTargetToRandomSpotAroundAttackTarget<>(),
                        new SetBlockToWalkTargetNoInterest<>(),
                        new SetBlockToWalkTargetNoInterest<>()
                ),
                new OneRandomBehaviour<>(
                     new StopAndShoot<>(16, 5, 0.5f, this.getattacksound()).cooldownFor(entity -> random.nextInt(100, 200))
                             .whenStarting(entity -> triggerAnim("onetime", "bigshoot")),
                     new StopAndShootOverTime<>(60, 1, 22, 57, 4, 2, false, this.getattacksound(), null).cooldownFor(entity -> random.nextInt(40, 100))
                             .whenStarting(entity -> triggerAnim("onetime", "shoot"))
                )
                );
    }





    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(new AnimationController<>(this, "allways", 0, this::allways));
        controllerRegistrar.add(new AnimationController<>(this, "onetime", state -> PlayState.STOP)
                .triggerableAnim("shoot", RawAnimation.begin().then("animation.controller.normalattack", Animation.LoopType.PLAY_ONCE)) //1.125 to 2.875 interval 0.24
                .triggerableAnim("bigshoot", RawAnimation.begin().then("animation.controller.bigattack", Animation.LoopType.PLAY_ONCE)) // 0.75
                .triggerableAnim("idle", RawAnimation.begin().then("animation.controller.wierdidle", Animation.LoopType.PLAY_ONCE)) // 0.75
        );
    }



    private <T extends GeoAnimatable> PlayState allways(AnimationState<T> tAnimationState) {
        tAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.controller.wierdidle", Animation.LoopType.LOOP));
        return PlayState.CONTINUE;
    }




    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    @Override
    public void performRangedAttack(LivingEntity pTarget, float pVelocity) {
        double d0 = this.distanceToSqr(pTarget);
        double d1 = pTarget.getX() - this.getX();
        double d2 = pTarget.getY(0.4D) - this.getY(0.4D);
        double d3 = pTarget.getZ() - this.getZ();
        double d4 = Math.sqrt(Math.sqrt(d0)) * 0.5D;
        ControllerBigProjectile acidBall = new ControllerBigProjectile(this.level, this, d1, d2, d3, pTarget);
        acidBall.setPos(this.getX() - (double)(this.getBbWidth() + 1.0F) * 0.5D * (double)Mth.sin(this.yBodyRot * ((float)Math.PI / 180F)), this.getEyeY() - (double)0.1F, this.getZ() + (double)(this.getBbWidth() + 1.0F) * 0.5D * (double) Mth.cos(this.yBodyRot * ((float)Math.PI / 180F)));
        acidBall.shoot(d1, d2, d3, pVelocity, 1f);
        this.level.addFreshEntity(acidBall);
    }

    @Override
    public void performVariableRangedAttack(LivingEntity pTarget, float pVelocity, float down) {
        double d0 = this.distanceToSqr(pTarget);
        double d1 = pTarget.getX() - this.getX();
        double d2 = pTarget.getY(0.4D) - this.getY(0.4D);
        double d3 = pTarget.getZ() - this.getZ();
        double d4 = Math.sqrt(Math.sqrt(d0)) * 0.5D;
        ControllerBaseProjectile acidBall = new ControllerBaseProjectile(this.level, this, d1, d2, d3);
        acidBall.setPos(this.getX() - (double)(this.getBbWidth() + 1.0F) * 0.5D * (double)Mth.sin(this.yBodyRot * ((float)Math.PI / 180F)), this.getEyeY() - (double)0.5F, this.getZ() + (double)(this.getBbWidth() + 1.0F) * 0.5D * (double) Mth.cos(this.yBodyRot * ((float)Math.PI / 180F)));
        this.level.addFreshEntity(acidBall);
    }
}

