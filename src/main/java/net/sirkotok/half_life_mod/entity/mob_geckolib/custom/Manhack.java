package net.sirkotok.half_life_mod.entity.mob_geckolib.custom;


import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.BlockPos;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.DamageTypeTags;
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
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.sirkotok.half_life_mod.entity.base.HalfLifeMonster;
import net.sirkotok.half_life_mod.entity.brain.behaviour.*;
import net.sirkotok.half_life_mod.misc.gamerules.HalfLifeGameRules;
import net.sirkotok.half_life_mod.sound.HalfLifeSounds;
import net.sirkotok.half_life_mod.misc.util.InfightingUtil;
import net.tslat.smartbrainlib.api.SmartBrainOwner;
import net.tslat.smartbrainlib.api.core.BrainActivityGroup;
import net.tslat.smartbrainlib.api.core.SmartBrainProvider;
import net.tslat.smartbrainlib.api.core.behaviour.FirstApplicableBehaviour;
import net.tslat.smartbrainlib.api.core.behaviour.custom.look.LookAtTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.misc.CustomBehaviour;
import net.tslat.smartbrainlib.api.core.behaviour.custom.path.SetRandomWalkTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.path.SetWalkTargetToAttackTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.target.InvalidateAttackTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.target.SetPlayerLookTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.target.SetRandomLookTarget;
import net.tslat.smartbrainlib.api.core.sensor.ExtendedSensor;
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


public class Manhack extends HalfLifeMonster implements GeoEntity, SmartBrainOwner<Manhack> {

    // TODO: make yellow when hurt + add alarm sound when hurt

    private BlockPos targetPosition;
    public Player player;
    @Override
    protected void actuallyHurt(DamageSource p_21240_, float p_21241_) {
        if (p_21240_.is(DamageTypes.IN_WALL)) {
            return; }

        super.actuallyHurt(p_21240_, p_21241_);
    }

 /*
    protected static final EntityDataAccessor<String> PLAYER_STRING_UUID = SynchedEntityData.defineId(Manhack.class, EntityDataSerializers.STRING);
    protected static final EntityDataAccessor<Integer> PLAYER_NOTFIND_COUNTER = SynchedEntityData.defineId(Manhack.class, EntityDataSerializers.INT);

    public void setPlayerStringUuid(UUID uuid) {
        this.entityData.set(PLAYER_STRING_UUID, uuid.toString());
    }
    public void setPlayerStringUuid(String uuid) {
        this.entityData.set(PLAYER_STRING_UUID, uuid);
    }

    public String getPlayerStringUUID() {
        return this.entityData.get(PLAYER_STRING_UUID);
    }

    public boolean getHasPlayer() {
        return !this.entityData.get(PLAYER_STRING_UUID).equals("");
    }
    public void setPlayerNoFindCounter(int count) {
        this.entityData.set(PLAYER_NOTFIND_COUNTER, count);
    }

    public int getPlayerNoFindCounter() {
        return this.entityData.get(PLAYER_NOTFIND_COUNTER);
    }


 */


    @Override
    public boolean canBeLeashed(Player p_21418_) {
        return !this.isLeashed();
    }

    private AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);



    public static final EntityDataAccessor<Integer> CAN_CONTROL_TIMESTAMP = SynchedEntityData.defineId(Manhack.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Integer> TILTDIRECTION = SynchedEntityData.defineId(Manhack.class, EntityDataSerializers.INT);

    // 0

    public static final EntityDataAccessor<Boolean> ISOPEN = SynchedEntityData.defineId(Manhack.class, EntityDataSerializers.BOOLEAN);
    protected void defineSynchedData() {
        super.defineSynchedData();
     //   this.entityData.define(PLAYER_STRING_UUID, "");
     //   this.entityData.define(PLAYER_NOTFIND_COUNTER, 0);
        this.entityData.define(ISOPEN, false);
        this.entityData.define(CAN_CONTROL_TIMESTAMP, 0);
        this.entityData.define(TILTDIRECTION, 0);
    }


    public boolean getOpen(){
        return this.entityData.get(ISOPEN);
    }
    public void setOpen(boolean b){
        this.entityData.set(ISOPEN, b);
    }
    public int getTilt(){
        return this.entityData.get(TILTDIRECTION);
    }
    public void setTilt(int tilt){
        this.entityData.set(TILTDIRECTION, tilt);
    }

/*
    public void makemyplayer() {
        if (this.level.isClientSide()) return;
        BlockPos pBlockPos = new BlockPos(this.getBlockX(), this.getBlockY(), this.getBlockZ());
        ServerLevel pLevel = (ServerLevel)this.level;
        int rad = 5;
        List<Player> players = EntityRetrievalUtil.getEntities((Level) pLevel,
                new AABB(pBlockPos.getX() - rad, pBlockPos.getY() - rad, pBlockPos.getZ() - rad,
                        pBlockPos.getX() + rad, pBlockPos.getY() + rad, pBlockPos.getZ() + rad), obj -> obj instanceof Player player && player.getMainHandItem().is(HalfLifeItems.GRAVITYGUN.get()));
        if (!players.isEmpty()) {
            for (Player p : players) {
                String s1 = p.getUUID().toString();
                String s2 = this.getPlayerStringUUID();
                if (s1.equals(s2)) {
                    this.player = p;
                    return;
                }
            }
        }
        this.player = null;
    }
    public void nullmyplayer() {
        if (this.level.isClientSide()) return;
        BlockPos pBlockPos = new BlockPos(this.getBlockX(), this.getBlockY(), this.getBlockZ());
        ServerLevel pLevel = (ServerLevel)this.level;
        int rad = 5;
        List<Player> players = EntityRetrievalUtil.getEntities((Level) pLevel,
                new AABB(pBlockPos.getX() - rad, pBlockPos.getY() - rad, pBlockPos.getZ() - rad,
                        pBlockPos.getX() + rad, pBlockPos.getY() + rad, pBlockPos.getZ() + rad), obj -> obj instanceof Player player && player.getMainHandItem().is(HalfLifeItems.VORT_DEBUG.get()));
        if (!players.isEmpty()) {
            for (Player p : players) {
                String s1 = p.getUUID().toString();
                String s2 = this.getPlayerStringUUID();
                if (s1.equals(s2)) {
                    //   this.player = p;
                    return;
                }
            }
        }
        this.player = null;
    }

    public void setnoplayer() {
        this.setPlayerNoFindCounter(0);
        this.setPlayerStringUuid("");
        this.player = null;
    }

    public Vec3 findpos(Player pPlayer) {
        if (pPlayer == null) return this.position();
        return GravityGunFallingBlockEntity.findposstat(pPlayer);
    } */
    @Override
    public void tick() {
        super.tick();
 /*
        if (this.getPlayerNoFindCounter() > 5 || !this.getHasPlayer()) {
            this.setnoplayer();
        }

        if (this.getHasPlayer() && this.player == null) {
            this.makemyplayer();
            if (this.getHasPlayer() && this.player == null) {
                this.setPlayerNoFindCounter(this.getPlayerNoFindCounter()+1);
            }
        } else this.setPlayerNoFindCounter(0);

        if (this.player != null) {
            if (this.tickCount % 10 == 0)  this.nullmyplayer();
            if (this.player != null) {
                Vec3 vec3 = findpos(player);
                Vec3 vec31 = this.position();
                Vec3 movement = vec3.subtract(vec31);
                if (movement.length() < 0.5f) movement.scale(0.3f);
                if (this.player.getDeltaMovement().length() < 0.01f) {
                    this.player.setDeltaMovement(this.player.getDeltaMovement().add(this.player.position().subtract(this.position()).normalize().scale(0.1f)));
                }
                this.setDeltaMovement(movement);
            }
        }


 */


        LivingEntity entity = this.getTarget();
        if (entity != null) {
            float f = this.distanceTo(entity);
            if (f < 3.8 && !this.getOpen()) {
                this.setOpen(true);
                this.playSound(HalfLifeSounds.MANHACK_BLADE_SNICK.get());
            }
            if (f > 4.2 && this.getOpen()) {
                this.setOpen(false);
                this.playSound(HalfLifeSounds.MANHACK_BLADE_SNICK.get());
            }

        }

    /*
        Vec3 vec31 = this.getLookAngle();
        this.setYBodyRot((float) Math.atan2(vec31.x, vec31.z));


        if (this.tickCount % 10 == 0) {
            int t = 0;
            if (this.getDeltaMovement().length() > 2f) {
            Vec3 vec3 = this.getDeltaMovement().normalize();
            Vec2 vec2 = this.getRotationVector();
            double rotb = (Math.atan2(vec2.x, vec2.y));
            double rotm = (Math.atan2(vec3.x, vec3.z));  //+ Math.PI /2
            double d = rotm - rotb;
            if (d < 0) d += 2 * Math.PI;
            if (d >= 0 && d <= Math.PI / 8) t = 1;
            if (d > Math.PI / 8 && d <= 3 * Math.PI / 8) t = 2;
            if (d > 3 * Math.PI / 8 && d <= 5 * Math.PI / 8) t = 3;
            if (d > 5 * Math.PI / 8 && d <= 7 * Math.PI / 8) t = 4;
            if (d > 7 * Math.PI / 8 && d <= 9 * Math.PI / 8) t = 5;
            if (d > 9 * Math.PI / 8 && d <= 11 * Math.PI / 8) t = 6;
            if (d > 11 * Math.PI / 8 && d <= 13 * Math.PI / 8) t = 7;
            if (d > 13 * Math.PI / 8 && d <= 15 * Math.PI / 8) t = 8;
            if (d > 15 * Math.PI / 8 && d <= 16 * Math.PI / 8) t = 1;
            }
            this.setTilt(t);
        } */

    }

    public boolean cancontrol(){
        boolean flag1 = this.tickCount < 50;
        boolean flag2 = (this.tickCount - this.entityData.get(CAN_CONTROL_TIMESTAMP) > 40);
        return flag1 || flag2;
    }

    public void setcontroltimestamp(){
        this.entityData.set(CAN_CONTROL_TIMESTAMP, this.tickCount);
    }


    @Override
    public boolean isNoGravity() {
        return true;
    }

    public Manhack(EntityType type, Level level) {
        super(type, level);
        this.xpReward = 3;
    }







    public static AttributeSupplier setAttributes() {
        return Monster.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 10D)
                .add(Attributes.ATTACK_DAMAGE, 3f)
                .add(Attributes.ATTACK_SPEED, 1.0f)
                .add(Attributes.ATTACK_KNOCKBACK, 1.0f)
                .add(Attributes.MOVEMENT_SPEED, 0.22f).build();
    }


    public int getairemount() {
        int i = 0;
        for (int j = 1; j<20; j++){
            BlockPos pos = new BlockPos(this.blockPosition().getX(), this.blockPosition().getY() - j, this.blockPosition().getZ());
            BlockState blockstate = this.level.getBlockState(pos);
            if (!blockstate.isAir()) return i;
            i++;}
        return i;
    }

    public void ricochete(float a) {
    //    if (this.getHasPlayer()) return;
        int i = random.nextFloat() < 0.5f ? 1 : -1;
        int j = random.nextFloat() < 0.5f ? 1 : -1;
        int k = random.nextFloat() < 0.5f ? 1 : -1;
        this.setDeltaMovement(i*2*random.nextFloat()*a, j*2*random.nextFloat()*a, k*2*random.nextFloat()*a);
        this.setcontroltimestamp();
    }



    @Override
    public void aiStep() {
        super.aiStep();

        if (!this.level.isClientSide) {
        if ((horizontalCollision || verticalCollision) && this.tickCount % 15 == 0) {
            this.playSound(getGrindSound(), getSoundVolume(), getVoicePitch());
            this.ricochete(random.nextFloat()*random.nextFloat());
        }

        if (this.tickCount % 4 == 0) {
            int m = random.nextFloat() < 0.5f ? 1 : -1;
        if (BrainUtils.getMemory(this, MemoryModuleType.WALK_TARGET) == null) this.setDeltaMovement(this.getDeltaMovement().x(), 0.2f * m * random.nextFloat(), this.getDeltaMovement().z());
        if (this.getairemount() < 3 && this.random.nextFloat() < 0.1f) this.setDeltaMovement(this.getDeltaMovement().x(), 0.3f, this.getDeltaMovement().z());
        if (this.getairemount() > 10) this.setDeltaMovement(this.getDeltaMovement().x(), -0.05, this.getDeltaMovement().z());

        List<Entity> list = this.level.getEntities(this, this.getBoundingBox(), obj -> obj instanceof LivingEntity && !(obj instanceof Manhack));
        for (Entity entity : list){
            if (entity instanceof LivingEntity living) {
                this.doHurtTarget(living);
                this.playSound(getGrindFleshSound(), getSoundVolume(), getVoicePitch());
                this.ricochete(1);
            }
        }
    }
    }
    }



    protected SoundEvent getGrindFleshSound() {
        switch (this.random.nextInt(1,4)) {
            case 1:  return HalfLifeSounds.MANHACK_GRIND_FLESH_1.get();
            case 2:  return HalfLifeSounds.MANHACK_GRIND_FLESH_2.get();
            case 3:  return HalfLifeSounds.MANHACK_GRIND_FLESH_3.get();
        }
        return HalfLifeSounds.HEADCRAB_1_ATTACK_1.get();
    }

    protected SoundEvent getGrindSound(){
    switch (this.random.nextInt(1,6)) {
        case 1:  return HalfLifeSounds.MANHACK_GRIND1.get();
        case 2:  return HalfLifeSounds.MANHACK_GRIND2.get();
        case 3:  return HalfLifeSounds.MANHACK_GRIND3.get();
        case 4:  return HalfLifeSounds.MANHACK_GRIND4.get();
        case 5:  return HalfLifeSounds.MANHACK_GRIND5.get();
    }
        return HalfLifeSounds.HEADCRAB_1_ATTACK_1.get();
   }

    @Override
    protected SoundEvent getAmbientSound() {
        if (random.nextFloat() < 0.5f) return HalfLifeSounds.MANHACK_LOOP1.get();
        return HalfLifeSounds.MANHACK_LOOP2.get();
    }

    @Override
    public void playAmbientSound() {
        SoundEvent soundevent = this.getAmbientSound();
        if (soundevent != null) {
            this.playSound(soundevent, this.getSoundVolume()/4, this.getVoicePitch());
        }
    }

    @Override
    public int getAmbientSoundInterval() {
        return 145;
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
        return 0.5f;
    }

     @Override
    public boolean hurt(DamageSource pSource, float pAmount) {
        if (this.level.isClientSide) {
            return false;
        } else {
            if (!pSource.is(DamageTypeTags.AVOIDS_GUARDIAN_THORNS) && !pSource.is(DamageTypes.THORNS)) {
                Entity entity = pSource.getDirectEntity();
                if (entity instanceof LivingEntity living) {
                    if (living.getMainHandItem().isEmpty()) {
                    living.hurt(this.damageSources().thorns(this), 1.0F);
                }
                }
            }
            return super.hurt(pSource, pAmount);
        }
    }

    protected SoundEvent getHurtSound(DamageSource pSource) {
        Entity entity = pSource.getDirectEntity();
            if (entity instanceof LivingEntity living && !(living instanceof IronGolem)) {
                if (!pSource.is(DamageTypeTags.AVOIDS_GUARDIAN_THORNS) && !pSource.is(DamageTypes.THORNS)) {
                    if (living.getMainHandItem().isEmpty()) {
                        return this.getGrindFleshSound();
                    }
                }
            }
        return this.getGrindSound();
    }


    protected SoundEvent getDeathSound() {
        return HalfLifeSounds.MANHACK_DIES.get();
    }




    @Override
    protected Brain.Provider<?> brainProvider() {
        return new SmartBrainProvider<>(this);
    }

    @Override
    protected void customServerAiStep() {
        if (!this.level.getGameRules().getRule(HalfLifeGameRules.HALF_LIFE_AI_IS_ON).get()) return;
        tickBrain(this);




        if (this.targetPosition != null && (!this.level.isEmptyBlock(this.targetPosition) || this.targetPosition.getY() <= this.level.getMinBuildHeight())) {
            this.targetPosition = null;
        }

        if (this.targetPosition == null || this.random.nextInt(30) == 0 || this.targetPosition.closerToCenterThan(this.position(), 2.0D)) {
            this.targetPosition = BlockPos.containing(this.getX() + (double)this.random.nextInt(7) - (double)this.random.nextInt(7), this.getY() + (double)this.random.nextInt(6) - 2.0D, this.getZ() + (double)this.random.nextInt(7) - (double)this.random.nextInt(7));
        }

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
    public List<ExtendedSensor<Manhack>> getSensors() {
        return ObjectArrayList.of(
                new HurtBySensor<>(),
                new NearbyPlayersSensor<>(),
                new NearbyLivingEntitySensor<Manhack>()
                        .setPredicate((target, entity) ->
                                InfightingUtil.CombineSpecific(target) || InfightingUtil.commonenemy(target)
                        ));
    }



    @Override
    public BrainActivityGroup<Manhack> getCoreTasks() {
        return BrainActivityGroup.coreTasks(
                new LookAtTarget<>(),
                new PushToWalkTarget<>().startCondition(entity -> this.cancontrol()),
                new CustomBehaviour<>(entity -> BrainUtils.clearMemory(this, MemoryModuleType.WALK_TARGET)).cooldownFor(entity -> 150)
        );

    }

    @Override
    public BrainActivityGroup<Manhack> getIdleTasks() { // These are the tasks that run when the mob isn't doing anything else (usually)
        return BrainActivityGroup.idleTasks(
                new FirstApplicableBehaviour<Manhack>(
                        new TargetOrRetaliateHLT<>(),
                         new SetPlayerLookTarget<>(),
                        new SetRandomLookTarget<>()),
                        new SetRandomWalkTarget<>()

        );
    }




    @Override
    public BrainActivityGroup<Manhack> getFightTasks() { // These are the tasks that handle fighting
        return BrainActivityGroup.fightTasks(
                new InvalidateAttackTarget<>(),
                new Retaliate<>(),
                new SetWalkTargetToAttackTarget<>()
                );
    }





    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {


   //     controllerRegistrar.add(new AnimationController<>(this, "direction", 0, this::predicate));
        controllerRegistrar.add(new AnimationController<>(this, "spin", 0, this::allways));
        controllerRegistrar.add(new AnimationController<>(this, "open", 0, this::opencontrol));
        controllerRegistrar.add(new AnimationController<>(this, "die", state -> PlayState.STOP)
                .triggerableAnim("die", RawAnimation.begin().then("animation.manhack.die", Animation.LoopType.PLAY_ONCE)));


    }

    private <T extends GeoAnimatable> PlayState opencontrol(AnimationState<T> tAnimationState) {
        if (this.getOpen()) {
            tAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.manhack.open", Animation.LoopType.LOOP));
            return PlayState.CONTINUE;
        }
        tAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.manhack.close", Animation.LoopType.LOOP));
        return PlayState.CONTINUE;

    }




    private <T extends GeoAnimatable> PlayState allways(AnimationState<T> tAnimationState) {
        tAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.manhack.spin", Animation.LoopType.LOOP));
        return PlayState.CONTINUE;
    }




    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

}

