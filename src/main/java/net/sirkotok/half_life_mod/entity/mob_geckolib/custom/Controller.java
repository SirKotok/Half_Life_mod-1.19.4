package net.sirkotok.half_life_mod.entity.mob_geckolib.custom;


import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.BlockPos;
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
import net.sirkotok.half_life_mod.entity.projectile.AcidBall;
import net.sirkotok.half_life_mod.util.HLperUtil;
import net.sirkotok.half_life_mod.util.ModTags;
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







    @Override
    public void tick() {
        super.tick();
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



   /*
    @Override
    protected SoundEvent getAmbientSound() {
        if (random.nextFloat() < 0.5f) return ModSounds.MANHACK_LOOP1.get();
        return ModSounds.MANHACK_LOOP2.get();
    }

    @Override
    public void playAmbientSound() {
        SoundEvent soundevent = this.getAmbientSound();
        if (soundevent != null) {
            this.playSound(soundevent, this.getSoundVolume()/4, this.getVoicePitch());
        }
    }

 */

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
                        .setPredicate((target, entity) ->
                            target instanceof Player || target instanceof IronGolem || target instanceof HalfLifeNeutral ||
                            target instanceof AbstractVillager || target.getType().is(ModTags.EntityTypes.FACTION_COMBINE)));
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
                     new StopAndShoot<>(16, 5, 1).cooldownFor(entity -> random.nextInt(100, 200))
                             .whenStarting(entity -> triggerAnim("onetime", "bigshoot")),
                     new StopAndShootOverTime<>(60, 1, 22, 57, 4, 2, false, null, null).cooldownFor(entity -> random.nextInt(40, 100))
                             .whenStarting(entity -> triggerAnim("onetime", "shoot"))
                )
                );
    }





    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(new AnimationController<>(this, "noattack", 0, this::allways));
        controllerRegistrar.add(new AnimationController<>(this, "idle", 0, this::idle));
        controllerRegistrar.add(new AnimationController<>(this, "open", 0, state -> PlayState.STOP)
                .triggerableAnim("open", RawAnimation.begin().then("animation.controller.openslightly", Animation.LoopType.PLAY_ONCE)));
        controllerRegistrar.add(new AnimationController<>(this, "onetime", state -> PlayState.STOP)
                .triggerableAnim("shoot", RawAnimation.begin().then("animation.controller.normalattack", Animation.LoopType.PLAY_ONCE)) //1.125 to 2.875 interval 0.24
                .triggerableAnim("bigshoot", RawAnimation.begin().then("animation.controller.bigattack", Animation.LoopType.PLAY_ONCE)) // 0.75
        );
    }

    private <T extends GeoAnimatable> PlayState idle(AnimationState<T> tAnimationState) {
        tAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.controller.idle", Animation.LoopType.LOOP));
        return PlayState.CONTINUE;
    }


    private <T extends GeoAnimatable> PlayState allways(AnimationState<T> tAnimationState) {
        tAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.controller.noattack", Animation.LoopType.LOOP));
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
        AcidBall acidBall = new AcidBall(this.level, this, d1, d2, d3);
        acidBall.setPos(this.getX() - (double)(this.getBbWidth() + 1.0F) * 0.5D * (double)Mth.sin(this.yBodyRot * ((float)Math.PI / 180F)), this.getEyeY() - (double)0.1F, this.getZ() + (double)(this.getBbWidth() + 1.0F) * 0.5D * (double) Mth.cos(this.yBodyRot * ((float)Math.PI / 180F)));
        this.level.addFreshEntity(acidBall);
    }

    @Override
    public void performVariableRangedAttack(LivingEntity pTarget, float pVelocity, float down) {
        double d0 = this.distanceToSqr(pTarget);
        double d1 = pTarget.getX() - this.getX();
        double d2 = pTarget.getY(0.4D) - this.getY(0.4D);
        double d3 = pTarget.getZ() - this.getZ();
        double d4 = Math.sqrt(Math.sqrt(d0)) * 0.5D;
        AcidBall acidBall = new AcidBall(this.level, this, d1, d2, d3);
        acidBall.setPos(this.getX() - (double)(this.getBbWidth() + 1.0F) * 0.5D * (double)Mth.sin(this.yBodyRot * ((float)Math.PI / 180F)), this.getEyeY() - (double)0.1F, this.getZ() + (double)(this.getBbWidth() + 1.0F) * 0.5D * (double) Mth.cos(this.yBodyRot * ((float)Math.PI / 180F)));
        this.level.addFreshEntity(acidBall);
    }
}

