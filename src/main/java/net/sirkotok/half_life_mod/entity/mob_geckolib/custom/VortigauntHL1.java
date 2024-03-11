package net.sirkotok.half_life_mod.entity.mob_geckolib.custom;


import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.BlockPos;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.Difficulty;
import net.minecraft.world.damagesource.DamageSource;
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
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import net.sirkotok.half_life_mod.entity.base.HalfLifeMonster;
import net.sirkotok.half_life_mod.entity.base.HalfLifeNeutral;
import net.sirkotok.half_life_mod.entity.brain.behaviour.*;
import net.sirkotok.half_life_mod.entity.brain.sensor.SmellSensor;
import net.sirkotok.half_life_mod.entity.projectile.Bullet;
import net.sirkotok.half_life_mod.entity.projectile.VortLightningProjectile;
import net.sirkotok.half_life_mod.particle.HalfLifeParticles;
import net.sirkotok.half_life_mod.sound.HalfLifeSounds;
import net.sirkotok.half_life_mod.util.CommonSounds;
import net.sirkotok.half_life_mod.util.HLTags;
import net.sirkotok.half_life_mod.util.HLperUtil;
import net.sirkotok.half_life_mod.util.InfightingUtil;
import net.tslat.smartbrainlib.api.SmartBrainOwner;
import net.tslat.smartbrainlib.api.core.BrainActivityGroup;
import net.tslat.smartbrainlib.api.core.SmartBrainProvider;
import net.tslat.smartbrainlib.api.core.behaviour.FirstApplicableBehaviour;
import net.tslat.smartbrainlib.api.core.behaviour.OneRandomBehaviour;
import net.tslat.smartbrainlib.api.core.behaviour.custom.look.LookAtTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.misc.CustomBehaviour;
import net.tslat.smartbrainlib.api.core.behaviour.custom.misc.Idle;
import net.tslat.smartbrainlib.api.core.behaviour.custom.move.FleeTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.move.MoveToWalkTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.path.SetRandomWalkTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.target.InvalidateAttackTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.target.SetPlayerLookTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.target.SetRandomLookTarget;
import net.tslat.smartbrainlib.api.core.sensor.ExtendedSensor;
import net.tslat.smartbrainlib.api.core.sensor.vanilla.HurtBySensor;
import net.tslat.smartbrainlib.api.core.sensor.vanilla.NearbyLivingEntitySensor;
import net.tslat.smartbrainlib.api.core.sensor.vanilla.NearbyPlayersSensor;
import net.tslat.smartbrainlib.util.BrainUtils;
import net.tslat.smartbrainlib.util.EntityRetrievalUtil;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.List;
import java.util.Objects;


public class VortigauntHL1 extends HalfLifeMonster implements RangedAttackMob, GeoEntity, SmartBrainOwner<VortigauntHL1> {

    @Override
    protected float getSoundVolume() {
        return 0.9f;
    }


    private AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);




    public static final EntityDataAccessor<Boolean> IS_ANGRY = SynchedEntityData.defineId(VortigauntHL1.class, EntityDataSerializers.BOOLEAN);

    public static final EntityDataAccessor<Integer> CHARGE = SynchedEntityData.defineId(VortigauntHL1.class, EntityDataSerializers.INT);

    public static final EntityDataAccessor<Boolean> CAN_RANGED_ATTACK = SynchedEntityData.defineId(VortigauntHL1.class, EntityDataSerializers.BOOLEAN);
    public static final EntityDataAccessor<Integer> ARC_PARTICLES = SynchedEntityData.defineId(VortigauntHL1.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Integer> MAX_ARC_PARTICLES = SynchedEntityData.defineId(VortigauntHL1.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Float> TARGET_X = SynchedEntityData.defineId(VortigauntHL1.class, EntityDataSerializers.FLOAT);
    public static final EntityDataAccessor<Float> TARGET_Y = SynchedEntityData.defineId(VortigauntHL1.class, EntityDataSerializers.FLOAT);
    public static final EntityDataAccessor<Float> TARGET_Z = SynchedEntityData.defineId(VortigauntHL1.class, EntityDataSerializers.FLOAT);

    public static final EntityDataAccessor<Float> START_X = SynchedEntityData.defineId(VortigauntHL1.class, EntityDataSerializers.FLOAT);
    public static final EntityDataAccessor<Float> START_Y = SynchedEntityData.defineId(VortigauntHL1.class, EntityDataSerializers.FLOAT);
    public static final EntityDataAccessor<Float> START_Z = SynchedEntityData.defineId(VortigauntHL1.class, EntityDataSerializers.FLOAT);


    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(IS_ANGRY, false);
        this.entityData.define(CAN_RANGED_ATTACK, false);
        this.entityData.define(CHARGE, 0);
        this.entityData.define(TARGET_Y, 0F);
        this.entityData.define(TARGET_Z, 0F);
        this.entityData.define(TARGET_X, 0F);
        this.entityData.define(START_Z, 0F);
        this.entityData.define(START_Y, 0F);
        this.entityData.define(START_X, 0F);
        this.entityData.define(ARC_PARTICLES, 0);
        this.entityData.define(MAX_ARC_PARTICLES, 0);
    }

    public int getcharge(){
        return this.entityData.get(CHARGE);
    }
    public void setcharge(int i){
        this.entityData.set(CHARGE, i);
    }

    public int getarcp(){
        return this.entityData.get(ARC_PARTICLES);
    }
    public void setarcp(int i){
        this.entityData.set(ARC_PARTICLES, i);
    }

    public int getMAX(){
        return this.entityData.get(MAX_ARC_PARTICLES);
    }
    public void setMAX(int i){
        this.entityData.set(MAX_ARC_PARTICLES, i);
    }

    public Vec3 gettargetvec(){
        return new Vec3(this.entityData.get(TARGET_X),  this.entityData.get(TARGET_Y),  this.entityData.get(TARGET_Z));
    }
    public void settargetvec(Vec3 vec3){
        this.entityData.set(TARGET_X, (float) vec3.x());
        this.entityData.set(TARGET_Y, (float) vec3.y());
        this.entityData.set(TARGET_Z, (float) vec3.z());
    }

    public Vec3 getstartvec(){
        return new Vec3(this.entityData.get(START_X),  this.entityData.get(START_Y),  this.entityData.get(START_Z));
    }
    public void setstartvec(Vec3 vec3){
        this.entityData.set(START_X, (float) vec3.x());
        this.entityData.set(START_Y, (float) vec3.y());
        this.entityData.set(START_Z, (float) vec3.z());
    }










    @Override
    public void tick() {
        super.tick();



        if (!this.level.isClientSide() && this.getarcp() > 0) {
            LivingEntity target = BrainUtils.getTargetOfEntity(this);
            if (this.getarcp() > this.getMAX()-2 && this.getarcp() > 1 && target != null) {

                Vec3 from = Vec3.atCenterOf(this.blockPosition()).add(0, 1, 0);
                Vec3 vec3 = this.gettargetvec();
                Vec3 to = vec3.subtract(from);

                int time = (int) to.length();

                double d1 = target.getX() - this.getX();
                double d2 = target.getEyeY() - this.getEyeY()+0.1D;
                double d3 = target.getZ() - this.getZ();
                VortLightningProjectile bullet = new VortLightningProjectile(this.level, this, d1, d2, d3);
                bullet.setPos(this.getX() - (double)(this.getBbWidth() + 1.0F) * 0.5D * (double) Mth.sin(this.yBodyRot * ((float)Math.PI / 180F)), this.getEyeY() - (double)0.1F, this.getZ() + (double)(this.getBbWidth() + 1.0F) * 0.5D * (double) Mth.cos(this.yBodyRot * ((float)Math.PI / 180F)));
                bullet.shoot(d1, d2, d3, 2, 1.0f);
                bullet.setTime(3+time);
                this.level.addFreshEntity(bullet);
            }

         //   if (this.getarcp() == 1) {
         //       if (target!= null && this.getSensing().hasLineOfSight(target)) {
          //      this.ConfigurabledoHurtTarget(target, 0, 1.5f, 1, null, 0, false);
         //       }


            this.setarcp(this.getarcp()-1);
        }





        if (level.isClientSide && this.getarcp() > this.getMAX()-4 && this.getarcp() > 1) {
            Vec3 from = Vec3.atCenterOf(this.blockPosition()).add(0, 1, 0);
            for (int i = 0; i < 3 + level.random.nextInt(3); i++) {
                Vec3 vec3 = this.gettargetvec();
                Vec3 to = vec3.subtract(from);
                to = to.add(to.normalize().scale(0.5));
             //   to = vec3;
                level.addParticle(HalfLifeParticles.VORT_ARC_LIGHTNING.get(), from.x, from.y, from.z, to.x, to.y, to.z);
            }
        }

       if (level.isClientSide && this.getcharge() > 0 && this.tickCount % 3 == 0) {
            Vec3 startPos = new Vec3(this.getX(), this.getY() + 1.4f, this.getZ());
            this.level.addParticle(HalfLifeParticles.VORT_LIGHTNING.get(), startPos.x, startPos.y, startPos.z, 1, 0, 0);
       }

        if (this.tickCount % 10 == 0) {
            this.entityData.set(CAN_RANGED_ATTACK, (!this.isScared() || (random.nextFloat() < 0.15f && this.isScared() && !HLperUtil.cansee(this, HLperUtil.TargetOrThis(this)))));
        }

        if (this.tickCount % 100 == 0 && !this.level.isClientSide()) {
            ServerLevel pLevel = (ServerLevel) this.level;
            BlockPos pBlockPos = this.blockPosition();
            int rad = 20;
            List<Mob> xen_fac = EntityRetrievalUtil.getEntities((Level) pLevel,
                    new AABB(pBlockPos.getX() - rad, pBlockPos.getY() - rad, pBlockPos.getZ() - rad,
                            pBlockPos.getX() + rad, pBlockPos.getY() + rad, pBlockPos.getZ() + rad), obj -> obj.getType().is(HLTags.EntityTypes.FACTION_XEN));
            if (BrainUtils.getTargetOfEntity(this) != null && !(BrainUtils.getTargetOfEntity(this) instanceof Shockroach)) {
                for (Mob x : xen_fac) {
                    if (BrainUtils.getTargetOfEntity(x) == null) {
                        BrainUtils.setTargetOfEntity(x, BrainUtils.getTargetOfEntity(this));
                    }
                }
            }
            }

    }





    protected boolean isangry() {
        return this.entityData.get(IS_ANGRY);
    }

    public VortigauntHL1(EntityType type, Level level) {
        super(type, level);
        this.xpReward = 10;
         this.setPathfindingMalus(BlockPathTypes.DAMAGE_FIRE, -1F);
        this.setPathfindingMalus(BlockPathTypes.DANGER_FIRE, -1F);
    }


    public static AttributeSupplier setAttributes() {
        return Monster.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 30D)
                .add(Attributes.ATTACK_DAMAGE, 4f)
                .add(Attributes.ATTACK_SPEED, 1.0f)
                .add(Attributes.ATTACK_KNOCKBACK, 1f)
                .add(Attributes.ARMOR, 2f)
                .add(Attributes.MOVEMENT_SPEED, 0.31f).build();
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
    public List<ExtendedSensor<VortigauntHL1>> getSensors() {
        return ObjectArrayList.of(
                new SmellSensor<>(),
                new HurtBySensor<>(),
                new NearbyPlayersSensor<>(),
                new NearbyLivingEntitySensor<VortigauntHL1>()
                        .setPredicate((target, entity) ->
                                InfightingUtil.XenForcesSpecific(target) || InfightingUtil.commonenemy(target)));
    }



    @Override
    public BrainActivityGroup<VortigauntHL1> getCoreTasks() {
        return BrainActivityGroup.coreTasks(
                new LookAtTarget<>(),
                new MoveToWalkTarget<>());
    }




    @Override
    public BrainActivityGroup<VortigauntHL1> getIdleTasks() {
        return BrainActivityGroup.idleTasks(
                new FirstApplicableBehaviour<VortigauntHL1>(
                        new TargetOrRetaliateHLT<>(),
                        new SetPlayerLookTarget<>(),
                        new SetRandomLookTarget<>()),
                new OneRandomBehaviour<>(
                        new SetRandomWalkTarget<>(),
                        new Idle<>().runFor(entity -> entity.getRandom().nextInt(10, 40))
                                .whenStarting(entity -> this.entityData.set(IS_ANGRY, false))));
    }

    public boolean isScared(){
        return this.getHealth()/this.getMaxHealth() < 0.2f;
    }


    protected SoundEvent getMissSound() {
        switch (this.random.nextInt(1,3)) {
            case 1:  return HalfLifeSounds.CLAW_MISS1.get();
            case 2:  return HalfLifeSounds.CLAW_MISS2.get();
        }
        return HalfLifeSounds.HEADCRAB_1_PAIN_1.get();
    }


    public boolean canattack(){
        return this.entityData.get(CAN_RANGED_ATTACK);
    }


    @Override
    public BrainActivityGroup<VortigauntHL1> getFightTasks() {
        return BrainActivityGroup.fightTasks(
                new InvalidateAttackTarget<>(),
                new Retaliate<>(),
                new SetWalkTargetToRandomSpotAroundAttackTarget<>().speedMod(1.1f).startCondition(entity -> !this.isScared() && this.distanceTo(HLperUtil.TargetOrThis(this))  > 10),
                new CustomBehaviour<>(entity -> this.entityData.set(IS_ANGRY, true)).startCondition(entity -> !this.isangry()),
                new OneRandomBehaviour<>(
                        new SetRandomWalkTarget<>().setRadius(10, 4).cooldownFor(entity -> 100),
                        new SetWalkTargetToRandomSpotAroundAttackTarget<>().radius(10, 4).speedMod(1.1f),
                        new SetWalkTargetToRandomSpotAroundAttackTarget<>().radius(10, 4).speedMod(1.2f),
                        new SetWalkTargetToRandomSpotAroundAttackTarget<>().radius(10, 4)
                         ).startCondition(entity -> !this.isScared()),
                new FleeTarget<>().fleeDistance(7).startCondition(entity -> this.isScared()),
                new SetRandomWalkTarget<>().setRadius(8, 4).startCondition(entity -> this.isScared()),
                new FirstApplicableBehaviour<>(
                new TripleMeleeAttack<>(15, 7, 11, 0, 1, 1, null, 0, CommonSounds.getClawHitSound(), null, this.getMissSound())
                        .whenStarting(entity -> triggerAnim("onetime", "triple")),
                new StopAndShoot<VortigauntHL1>(30, 3, 1f, HalfLifeSounds.SLV_ARC.get()).cooldownFor(entity -> random.nextInt(20, 60) * (this.isScared() ? 2 : 1))
                .whenStarting(entity -> triggerAnim("onetime", "easy"))
                .startCondition(entity -> this.canattack() && !this.level.getDifficulty().equals(Difficulty.HARD)),
                new StopAndShoot<VortigauntHL1>(20, 3, 1f, HalfLifeSounds.SLV_ARC.get()).cooldownFor(entity -> random.nextInt(50, 90) * (this.isScared() ? 2 : 1))
                .whenStarting(entity -> triggerAnim("onetime", "hard"))
                .startCondition(entity -> this.canattack() && this.level.getDifficulty().equals(Difficulty.HARD))
                ));


    }



    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(new AnimationController<>(this, "mainloop", 0, this::mainpredicate));
        controllerRegistrar.add(new AnimationController<>(this, "onetime", state -> PlayState.STOP)
                .triggerableAnim("triple", RawAnimation.begin().then("animation.vort.melee3", Animation.LoopType.PLAY_ONCE))
                .triggerableAnim("easy", RawAnimation.begin().then("animation.vort.easyshot", Animation.LoopType.PLAY_ONCE))
                .triggerableAnim("hard", RawAnimation.begin().then("animation.vort.hardshot", Animation.LoopType.PLAY_ONCE))
        );

    }



    private <T extends GeoAnimatable> PlayState mainpredicate(AnimationState<T> tAnimationState) {

        if(tAnimationState.isMoving()) {
            tAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.vort.walk", Animation.LoopType.LOOP));
            return PlayState.CONTINUE;
        }


        tAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.vort.idle", Animation.LoopType.LOOP));
        return PlayState.CONTINUE;
    }




    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }






    @Override
    public void performRangedAttack(LivingEntity target, float p_33318_) {
            int l = (int) this.gettargetvec().subtract(this.getEyePosition()).length()/3;
        if (!this.level.isClientSide() && BrainUtils.getTargetOfEntity(this) != null) {
            this.settargetvec(BrainUtils.getTargetOfEntity(this).getEyePosition());
            this.setstartvec(this.getEyePosition());
        }
            CommonSounds.PlaySoundAsOwn(this, getzapsound());
            this.setarcp((int) 5+l);
            this.setMAX((int) 5+l);
    }







    public SoundEvent getzapsound() {
        return HalfLifeSounds.SLV_IMPACT.get();
    }

    protected SoundEvent getDeathSound() {
        switch (this.random.nextInt(1,3)) {
            case 1:  return HalfLifeSounds.SLV_DIE1.get();
            case 2:  return HalfLifeSounds.SLV_DIE2.get();
        }
        return HalfLifeSounds.HEADCRAB_1_DIE_1.get();
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource pDamageSource) {
        switch (this.random.nextInt(1,3)) {
            case 1:  return HalfLifeSounds.SLV_PAIN1.get();
            case 2:  return HalfLifeSounds.SLV_PAIN2.get();
        }
        return HalfLifeSounds.HEADCRAB_1_DIE_1.get();
    }




    protected SoundEvent getAmbientSound() {
        int f = !this.isangry() ? random.nextInt(1, 12) : random.nextInt(random.nextFloat() < 0.2f ? 0 : 12, 18);
        switch (f) {
            case 1:  return HalfLifeSounds.SLV_IDLE0.get();
            case 2:  return HalfLifeSounds.SLV_IDLE2.get();
            case 3:  return HalfLifeSounds.SLV_IDLE3.get();
            case 4:  return HalfLifeSounds.SLV_IDLE7.get();
            case 5:  return HalfLifeSounds.SLV_IDLE8.get();
            case 6:  return HalfLifeSounds.SLV_IDLE9.get();
            case 7:  return HalfLifeSounds.SLV_IDLE1.get();
            case 8:  return HalfLifeSounds.SLV_IDLE4.get();
            case 9:  return HalfLifeSounds.SLV_IDLE5.get();
            case 10:  return HalfLifeSounds.SLV_IDLE6.get();
            case 11:  return HalfLifeSounds.SLV_IDLE10.get();
            case 12:  return HalfLifeSounds.ASLV_ALERT0.get();
            case 13:  return HalfLifeSounds.ASLV_ALERT1.get();
            case 14:  return HalfLifeSounds.ASLV_ALERT2.get();
            case 15:  return HalfLifeSounds.ASLV_ALERT3.get();
            case 16:  return HalfLifeSounds.ASLV_ALERT4.get();
            case 17:  return HalfLifeSounds.ASLV_ALERT5.get();
        }
        return SoundEvents.FROG_STEP;
    }




}





