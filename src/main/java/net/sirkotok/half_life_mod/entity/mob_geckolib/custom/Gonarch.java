package net.sirkotok.half_life_mod.entity.mob_geckolib.custom;


import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.BossEvent;
import net.minecraft.world.Difficulty;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
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
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.fluids.FluidType;
import net.sirkotok.half_life_mod.entity.HalfLifeEntities;
import net.sirkotok.half_life_mod.entity.base.HalfLifeMonster;
import net.sirkotok.half_life_mod.entity.base.HalfLifeNeutral;
import net.sirkotok.half_life_mod.entity.brain.behaviour.*;
import net.sirkotok.half_life_mod.entity.mob_geckolib.custom.parts.GonarchPart;
import net.sirkotok.half_life_mod.entity.modinterface.DoubleRangedMob;
import net.sirkotok.half_life_mod.entity.modinterface.MultiMeleeEntity;
import net.sirkotok.half_life_mod.entity.projectile.AcidThrown;
import net.sirkotok.half_life_mod.sound.HalfLifeSounds;
import net.sirkotok.half_life_mod.util.CommonSounds;
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


public class Gonarch extends HalfLifeMonster implements MultiMeleeEntity, RangedAttackMob, DoubleRangedMob, GeoEntity, SmartBrainOwner<Gonarch> {

    private int destroyBlocksTick = 40;
    private int babyemount = 0;
    private boolean runround = false;
    private final int babymaxemount = 20;
    private final GonarchPart sack;
    private final GonarchPart leg1;
    private final GonarchPart leg2;
    private final GonarchPart leg3;
    private final GonarchPart leg4;
    private final GonarchPart front;
    private int dyingticks = 0;
    private final GonarchPart armorabove;
    private final GonarchPart[] parts;
    private final ServerBossEvent bossEvent = (ServerBossEvent)(new ServerBossEvent(this.getDisplayName(), BossEvent.BossBarColor.YELLOW, BossEvent.BossBarOverlay.PROGRESS)).setDarkenScreen(false);
    @Override
    public boolean isMultipartEntity() {
        return true;
    }

    @Override
    public net.minecraftforge.entity.PartEntity<?>[] getParts() { return this.parts; }



    @Override
    protected float getSoundVolume() {
        return 0.5f;
    }
    @Override
    public boolean canDrownInFluidType(FluidType type) {
        if (type.equals(ForgeMod.WATER_TYPE.get())) {
            return false;
        }
        return super.canDrownInFluidType(type);
    }

    @Override
    protected float getWaterSlowDown() {
        return 0.98f;
    }

    protected boolean isangry() {
        return this.entityData.get(IS_ANGRY);
    }

    @Override
    public boolean isPushedByFluid(FluidType type) {
        if (type.equals(ForgeMod.WATER_TYPE.get())) {
            return false;
         }
        return super.isPushedByFluid(type);
    }

    @Override
    public double getFluidMotionScale(FluidType type) {
        if (type.equals(ForgeMod.WATER_TYPE.get())) {
            return 1;
        }
        return super.getFluidMotionScale(type);
    }



    public void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        if (this.hasCustomName()) {
            this.bossEvent.setName(this.getDisplayName());
        }

    }

    public void setCustomName(@Nullable Component pName) {
        super.setCustomName(pName);
        this.bossEvent.setName(this.getDisplayName());
    }




    private AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);




    public static final EntityDataAccessor<Boolean> IS_ANGRY = SynchedEntityData.defineId(Gonarch.class, EntityDataSerializers.BOOLEAN);



    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(IS_ANGRY, false);
    }


    public void checkDespawn() {
        if (this.level.getDifficulty() == Difficulty.PEACEFUL && this.shouldDespawnInPeaceful()) {
            this.discard();
        } else {
            this.noActionTime = 0;
        }
    }

    @Override
    protected float nextStep() {
        return (float)((int)this.moveDist + 2);
    }

    public Gonarch(EntityType type, Level level) {
        super(type, level);
        this.xpReward = 150;
         this.setPathfindingMalus(BlockPathTypes.DAMAGE_FIRE, -1F);
        this.setPathfindingMalus(BlockPathTypes.DANGER_FIRE, -1F);
        this.setMaxUpStep(2f);
        this.sack = new GonarchPart(this, "mouth", 2F, 2f, true, true, true);
        this.leg1 = new GonarchPart(this, "mouth", 0.7F, 3f, false, true, false);
        this.leg2 = new GonarchPart(this, "mouth", 0.7F, 3f, false, true, false);
        this.leg3 = new GonarchPart(this, "mouth", 0.7F, 3f, false, true, false);
        this.leg4 = new GonarchPart(this, "mouth", 0.7F, 3f, false, true, false);
        this.front = new GonarchPart(this, "mouth", this.getBbWidth()+1f, 1f, false, false, false);
        this.armorabove = new GonarchPart(this, "mouth", 4F, 1.5f, false, true, true);
        this.parts = new GonarchPart[] {
                this.sack,
                this.leg1,
                this.front,
                this.leg2,
                this.leg3,
                this.leg4,
                this.armorabove
        };
        this.setId(ENTITY_COUNTER.getAndAdd(this.parts.length + 1) + 1);
    }

    @Override
    public void setId(int pId) {
        super.setId(pId);
        for (int i = 0; i < this.parts.length; i++) {
            this.parts[i].setId(pId + i + 1);
        }
    }


    private float length(){
        return this.getBbWidth()/2+2.2f;
    }
    private void tickSack(GonarchPart pPart) {
        pPart.moveTo(this.getX(), this.getY() + 1.5f, this.getZ());
    }
    private void tickarmor(GonarchPart pPart){
        pPart.moveTo(this.getX(), this.getY() + 3f, this.getZ());
    }
    private void tickLeg1(GonarchPart pPart) {
        pPart.moveTo(rotvec(45));
    }
    private void tickfront(GonarchPart pPart) {
        float i = length()-1.8f;
        double yrot = (this.yBodyRot)/180*Math.PI;
        double d1 = Math.sin(yrot);
        double d2 = Math.cos(yrot);
        Vec3 vec3 = new Vec3((float)this.getX()-i*d1, this.getY(), (float)this.getZ()+i*d2);
        pPart.moveTo(vec3);
    }
    private Vec3 rotvec(int angledegree){
        float i = length();
        double yrot = (this.yBodyRot+angledegree)/180*Math.PI;
        double d1 = Math.sin(yrot);
        double d2 = Math.cos(yrot);
        return new Vec3((float)this.getX()-i*d1, this.getY(), (float)this.getZ()+i*d2);
    }

    @Override
    protected void dropExperience() {
    }

    protected void actuallydropExperience() {
        if (this.level instanceof ServerLevel && !this.wasExperienceConsumed() && (this.isAlwaysExperienceDropper() || this.lastHurtByPlayerTime > 0 && this.shouldDropExperience() && this.level.getGameRules().getBoolean(GameRules.RULE_DOMOBLOOT))) {
            int reward = net.minecraftforge.event.ForgeEventFactory.getExperienceDrop(this, this.lastHurtByPlayer, this.getExperienceReward());
            ExperienceOrb.award((ServerLevel)this.level, this.position(), reward);
        }
    }

    private void tickLeg2(GonarchPart pPart) {
        pPart.moveTo(rotvec(-45));
    }
    private void tickLeg3(GonarchPart pPart) {
        pPart.moveTo(rotvec(135));
    }
    private void tickLeg4(GonarchPart pPart) {
        pPart.moveTo(rotvec(-135));
    }




    public void aiStep() {
        super.aiStep();
        this.bossEvent.setProgress(this.getHealth() / this.getMaxHealth());
        tickLeg1(leg1);
        tickLeg2(leg2);
        tickfront(front);
        tickLeg3(leg3);
        tickLeg4(leg4);
        tickarmor(armorabove);
        tickSack(sack);
    }

    public void startSeenByPlayer(ServerPlayer pPlayer) {
        super.startSeenByPlayer(pPlayer);
        this.bossEvent.addPlayer(pPlayer);
    }
    public void stopSeenByPlayer(ServerPlayer pPlayer) {
        super.stopSeenByPlayer(pPlayer);
        this.bossEvent.removePlayer(pPlayer);
    }


    @Override
    public boolean isPickable() {
        return false;
    }

    @Override
    public void push(Entity pEntity) {
    }
    @Override
    public boolean isPushable() {
        return false;
    }

    @Override
    protected void pushEntities() {
    }



    public void recreateFromPacket(ClientboundAddEntityPacket pPacket) {
        super.recreateFromPacket(pPacket);
        this.yBodyRot = 0.0F;
        this.yBodyRotO = 0.0F;
    }


    public static AttributeSupplier setAttributes() {
        return Monster.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 220D)
                .add(Attributes.ATTACK_DAMAGE, 10f)
                .add(Attributes.ATTACK_SPEED, 1.0f)
                .add(Attributes.ATTACK_KNOCKBACK, 3.4f).add(Attributes.KNOCKBACK_RESISTANCE, 1f)
                .add(Attributes.ARMOR, 30f)
                .add(Attributes.FOLLOW_RANGE, 200)
                .add(Attributes.MOVEMENT_SPEED, 0.24f).build();
    }





    protected SoundEvent getSpitSound() {
        switch (this.random.nextInt(1,4)) {
            case 1:  return HalfLifeSounds.BULLSQUID_SHOOT_1.get();
            case 2:  return HalfLifeSounds.BULLSQUID_SHOOT_2.get();
            case 3:  return HalfLifeSounds.BULLSQUID_SHOOT_3.get();
        }
        return HalfLifeSounds.HEADCRAB_1_PAIN_1.get();
    }



    protected SoundEvent getHurtSound(DamageSource p_33034_) {
        switch (this.random.nextInt(1,4)) {
            case 1:  return HalfLifeSounds.GON_PAIN1.get();
            case 2:  return HalfLifeSounds.GON_PAIN2.get();
            case 3:  return HalfLifeSounds.GON_PAIN3.get();
        }
        return HalfLifeSounds.HEADCRAB_1_PAIN_1.get();
    }

    protected SoundEvent getDeathSound() {
        return HalfLifeSounds.GON_DIE1.get();
    }


    protected SoundEvent getAmbientSound() {
        switch (this.random.nextInt(1,4)) {
            case 1:  return HalfLifeSounds.GON_ALERT1.get();
            case 2:  return HalfLifeSounds.GON_ALERT2.get();
            case 3:  return HalfLifeSounds.GON_ALERT3.get();
        }
        return HalfLifeSounds.HEADCRAB_1_ALERT_1.get();
    }

    protected SoundEvent getHitSound(){
        return CommonSounds.getClawHitSound();
    }



    protected SoundEvent getAttackSound(){
        switch (this.random.nextInt(1,4)) {
            case 1:  return HalfLifeSounds.GON_ATTACK1.get();
            case 2:  return HalfLifeSounds.GON_ATTACK2.get();
            case 3:  return HalfLifeSounds.GON_ATTACK3.get();
        }
        return HalfLifeSounds.HEADCRAB_1_ALERT_1.get();
    }
    protected SoundEvent getBirthSound(){
        switch (this.random.nextInt(1,4)) {
            case 1:  return HalfLifeSounds.GON_BIRTH1.get();
            case 2:  return HalfLifeSounds.GON_BIRTH2.get();
            case 3:  return HalfLifeSounds.GON_BIRTH3.get();
        }
        return HalfLifeSounds.HEADCRAB_1_ALERT_1.get();
    }
    protected SoundEvent getCrySound(){
        switch (this.random.nextInt(1,4)) {
            case 1:  return HalfLifeSounds.GON_CHILDDIE1.get();
            case 2:  return HalfLifeSounds.GON_CHILDDIE2.get();
            case 3:  return HalfLifeSounds.GON_CHILDDIE3.get();
        }
        return HalfLifeSounds.HEADCRAB_1_ALERT_1.get();
    }

    protected SoundEvent getSackSound(){
        switch (this.random.nextInt(1,4)) {
            case 1:  return HalfLifeSounds.GON_SACK1.get();
            case 2:  return HalfLifeSounds.GON_SACK2.get();
            case 3:  return HalfLifeSounds.GON_SACK3.get();
        }
        return HalfLifeSounds.HEADCRAB_1_ALERT_1.get();
    }

    protected SoundEvent getStepSound() {
        switch (this.random.nextInt(1,4)) {
            case 1:  return HalfLifeSounds.GON_STEP1.get();
            case 2:  return HalfLifeSounds.GON_STEP2.get();
            case 3:  return HalfLifeSounds.GON_STEP3.get();

        }
        return SoundEvents.FROG_STEP;
    }

    @Override
    protected void playStepSound(BlockPos pPos, BlockState pState) {
        if (this.tickCount%3 == 0) {
            BlockState blockstate = this.level.getBlockState(pPos.above());
            boolean flag = blockstate.is(BlockTags.INSIDE_STEP_SOUND_BLOCKS);
            if (flag || !pState.getMaterial().isLiquid()) {
                this.playSound(getStepSound(), this.getSoundVolume(), this.getVoicePitch());
            }
        }
    }




    @Override
    protected Brain.Provider<?> brainProvider() {
        return new SmartBrainProvider<>(this);
    }


    @Override
    public void tick() {
        super.tick();


        if (this.isDeadOrDying() && !this.level.isClientSide()) {
            if (dyingticks == 0) this.triggerAnim("onetime", "die");
            dyingticks++;
            if (dyingticks == 17){
                this.level.explode(this, this.getX(), this.getY(), this.getZ(), 5f, Level.ExplosionInteraction.MOB);
            }
            if (dyingticks == 18) {
                this.makebaby(true);
                this.makebaby(true);
                this.makebaby(true);
                this.makebaby(true);
                this.makebaby(true);
                this.makebaby(true);
                this.makebaby(false);
                this.makebaby(false);
                this.makebaby(false);
                this.actuallydropExperience();
            }
        }



        if (this.tickCount % 20 == 0 && !this.level.isClientSide()) {
            boolean done = false;
            ServerLevel pLevel = (ServerLevel) this.level;
            BlockPos pBlockPos = this.blockPosition();
            int rad = 40;
            int i = 0;
            List<Mob> friends = EntityRetrievalUtil.getEntities((Level) pLevel,
                    new AABB(pBlockPos.getX() - rad, pBlockPos.getY() - rad, pBlockPos.getZ() - rad,
                            pBlockPos.getX() + rad, pBlockPos.getY() + rad, pBlockPos.getZ() + rad), obj -> obj.getType().is(HLTags.EntityTypes.FACTION_HEADCRAB));

            if (BrainUtils.getTargetOfEntity(this) != null) {
             for (Mob x : friends) {
                    if (x instanceof Baby_Headcrab) i++;
                    if (BrainUtils.getTargetOfEntity(x) == null || random.nextFloat() < 0.1f) {
                        BrainUtils.setTargetOfEntity(x, BrainUtils.getTargetOfEntity(this));
                    }
                }
            } else {
                for (Mob x : friends){
                    if (x instanceof Baby_Headcrab) {i++;
                    if (x.isDeadOrDying()) this.playSound(this.getCrySound(), this.getSoundVolume(), this.getVoicePitch());
                    }
                    if (BrainUtils.getTargetOfEntity(x) != null && !done) {
                        BrainUtils.setTargetOfEntity(this, BrainUtils.getTargetOfEntity(x));
                        done = true;
                         }
                    if (x.getKillCredit() != null && !done) {
                            BrainUtils.setTargetOfEntity(this, x.getKillCredit());
                            done = true;
                        }
                    if (x.getLastHurtByMob() != null && !done) {
                            BrainUtils.setTargetOfEntity(this, x.getLastHurtByMob());
                            done = true;
                        }
                }
            }
            this.babyemount = i;
        }


        if (BrainUtils.getTargetOfEntity(this) == null && !this.level.isClientSide() && this.tickCount % 100 == 0) {
            ServerLevel pLevel = (ServerLevel) this.level;
            BlockPos pBlockPos = this.blockPosition();
            int rad = 35;
            int i = 0;
            List<Mob> enemies = EntityRetrievalUtil.getEntities((Level) pLevel,
                    new AABB(pBlockPos.getX() - rad, pBlockPos.getY() - rad, pBlockPos.getZ() - rad,
                            pBlockPos.getX() + rad, pBlockPos.getY() + rad, pBlockPos.getZ() + rad), target ->
                            target instanceof Player ||
                            target instanceof IronGolem ||
                            target instanceof AbstractVillager ||
                            target.getType().is(HLTags.EntityTypes.FACTION_COMBINE) ||
                            target instanceof HalfLifeNeutral);
            if (!enemies.isEmpty()) BrainUtils.setTargetOfEntity(this, enemies.get(random.nextInt(0, enemies.size())));
        }




    }

    @Override
    protected void customServerAiStep() {
        tickBrain(this);
 /*
        if (this.destroyBlocksTick > 0 && !this.runround &&
                (( // (HLperUtil.TargetOrThis(this).getY() - this.getY() < 6) &&
                         BrainUtils.getTargetOfEntity(this) != null)
                        || (this.getLastDamageSource() != null && this.random.nextFloat() < 0.1f))) {
            --this.destroyBlocksTick;
            if (this.destroyBlocksTick == 0 && net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.level, this)) {
                this.destroyBlocksTick = 40;
                int j1 = Mth.floor(this.getY());
                int i2 = Mth.floor(this.getX());
                int j2 = Mth.floor(this.getZ());
                boolean flag = false;

                for(int j = -3; j <= 3; ++j) {
                    for(int k2 = -3; k2 <= 3; ++k2) {


                        int o1 = (HLperUtil.TargetOrThis(this).getY() - this.getY() > 2) ? 1 : 0;
                        int o2 = (HLperUtil.TargetOrThis(this).getY() - this.getY() < -2) ? -1 : 0;

                        for(int k =  o1 + o2 ; k <= 4 + o1 + o2; ++k) {

                            int l2 = i2 + j;
                            int l = j1 + k ;
                            int i1 = j2 + k2;
                            BlockPos blockpos = new BlockPos(l2, l, i1);
                            BlockState blockstate = this.level.getBlockState(blockpos);
                            if (this.canbreak(blockstate) && blockstate.canEntityDestroy(this.level, blockpos, this) && net.minecraftforge.event.ForgeEventFactory.onEntityDestroyBlock(this, blockpos, blockstate)) {
                                flag = this.level.destroyBlock(blockpos, this.random.nextFloat() < 0.4f, this) || flag;
                            }
                        }
                    }
                }

                if (flag) {
                    this.level.levelEvent((Player)null, 1022, this.blockPosition(), 0);
                }
            }
        }

 */

    }


    public boolean canbreak(BlockState state){
       return (state.is(BlockTags.MINEABLE_WITH_AXE) || state.is(BlockTags.MINEABLE_WITH_HOE) || state.is(BlockTags.MINEABLE_WITH_PICKAXE) || state.is(BlockTags.MINEABLE_WITH_SHOVEL))
               && !(state.is(BlockTags.NEEDS_DIAMOND_TOOL) || state.is(BlockTags.NEEDS_IRON_TOOL));
    }






    @Override
    public List<ExtendedSensor<Gonarch>> getSensors() {
        return ObjectArrayList.of(
                new HurtBySensor<>(),
                new NearbyPlayersSensor<>(),
                new NearbyLivingEntitySensor<Gonarch>()
                        .setPredicate((target, entity)  ->
                                InfightingUtil.HeadcrabFactionSpecific(target) || InfightingUtil.commonenemy(target)
                        ));
    }



    @Override
    public BrainActivityGroup<Gonarch> getCoreTasks() {
        return BrainActivityGroup.coreTasks(
               new LookAtTarget<>(),
               new MoveToWalkTarget<>());
    }




    @Override
    public BrainActivityGroup<Gonarch> getIdleTasks() {
        return BrainActivityGroup.idleTasks(
                new FirstApplicableBehaviour<Gonarch>(
                        new SetPlayerLookTarget<>(),
                        new CustomBehaviour<>(entity -> this.heal(1)).cooldownFor(entity -> 20).startCondition(entity -> this.getLastHurtByMob() == null),
                        new CustomBehaviour<>(entity -> this.entityData.set(IS_ANGRY, false)).startCondition(entity -> this.entityData.get(IS_ANGRY)),
                        new TargetOrRetaliateHLT<>(),
                      //  new TargetOrRetaliate<>(),
                        new SetRandomLookTarget<>()),
                new OneRandomBehaviour<>(
                        new SetRandomWalkTarget<>().speedModifier(1f),
                        new Idle<>().runFor(entity -> entity.getRandom().nextInt(50, 100))
                               ));
    }




    @Override
    public boolean hurt(DamageSource pSource, float pAmount) {

        if (RandomSource.create().nextFloat() < 0.1f) {
            this.runround = true;
        }


        return super.hurt(pSource, pAmount);
    }



    @Override
    public boolean isWithinMeleeAttackRange(LivingEntity p_217067_) {
        double d0 = this.getPerceivedTargetDistanceSquareForMeleeAttack(p_217067_);
        return d0 <= this.getMeleeAttackRangeSqr(p_217067_)/3;
    }

    @Override
    public BrainActivityGroup<Gonarch> getFightTasks() {
        return BrainActivityGroup.fightTasks(
                new CustomBehaviour<>(entity -> this.runround = !this.runround).cooldownFor(entity -> 200),
                new InvalidateAttackTarget<>(),
                new Retaliate<>(),
                new CustomBehaviour<>(entity -> this.entityData.set(IS_ANGRY, true)).startCondition(entity -> !this.entityData.get(IS_ANGRY)),
                new OneRandomBehaviour<>(
                        new SetWalkTargetToAttackTargetIfNoWalkTarget<Gonarch>().speedMod(2.0f).startCondition(entity -> !this.runround),
                        new SetRandomWalkTarget<>().setRadius(25, 5).speedModifier(2.0f).cooldownFor(entity -> 50).startCondition(entity -> this.runround),
                        new SetRandomWalkTarget<>().setRadius(25, 5).speedModifier(2.0f).cooldownFor(entity -> 100).startCondition(entity -> this.runround),
                        new SetWalkTargetToAttackTargetIfNoWalkTarget<Gonarch>().speedMod(2.0f).startCondition(entity -> !this.runround),
                        new SetWalkTargetToAttackTargetIfNoWalkTarget<Gonarch>().speedMod(2.0f)
                ),
                new SetWalkTargetToAttackTarget<>().speedMod(2.0f).startCondition(entity -> this.distanceTo(BrainUtils.getTargetOfEntity(this) != null ? BrainUtils.getTargetOfEntity(this) : this) > 24),
                new SetWalkTargetToAttackTarget<>().speedMod(2.0f).startCondition(entity -> this.getLastHurtByMob() != null).cooldownFor(entity -> 600),
                new FirstApplicableBehaviour<>(
                        new OneRandomBehaviour<>(
                 new MultiMeleeAttack<Gonarch>(true, 4, 1f, 1, 1.5f, null, 0, this.getHitSound(), this.getAttackSound())
                        .whenStarting(entity -> triggerAnim("onetime", "left"))
                        .cooldownFor(entity -> random.nextInt(10, 20)),
                 new MultiMeleeAttack<Gonarch>(true,4, 1f, 1, 1.5f, null, 0, this.getHitSound(), this.getAttackSound())
                                .whenStarting(entity -> triggerAnim("onetime", "right"))
                                .cooldownFor(entity -> random.nextInt(10, 20)),
                 new MultiMeleeAttack<Gonarch>(true,4, 1f, 1, 1.5f, null, 0, this.getHitSound(), this.getAttackSound())
                                .whenStarting(entity -> triggerAnim("onetime", "double"))
                                .cooldownFor(entity -> random.nextInt(10, 20))),
                new StopAndShoot<Gonarch>(10, 10, 1f).attackRadius(32f).cooldownFor(entity -> random.nextFloat() < 0.1f ? 220 : random.nextInt(30, 60))
                              .whenStarting(entity -> triggerAnim("onetime", "shoot")).startCondition(emtity -> this.distanceTo(HLperUtil.TargetOrThis(this)) > 7f || (HLperUtil.TargetOrThis(this).getY() - this.getY()+4)>0),
                new StopAndSecondShootx3<Gonarch>(31, 13, 15, 100, 1f, this.getBirthSound()).attackRadius(32f).cooldownFor(entity -> random.nextInt(20, 40)).startCondition(entity -> this.babyemount <= this.babymaxemount)
                       .whenStarting(entity -> triggerAnim("onetime", "birth")).cooldownFor(entity -> random.nextInt(60, 180))
                )
        );

    }




    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(new AnimationController<>(this, "main_loop", 0, this::predicate));
        controllerRegistrar.add(new AnimationController<>(this, "onetime", state -> PlayState.STOP)
                .triggerableAnim("right", RawAnimation.begin().then("animation.mom.attack1right", Animation.LoopType.PLAY_ONCE))
                        .triggerableAnim("left", RawAnimation.begin().then("animation.mom.attack1left", Animation.LoopType.PLAY_ONCE))
                        .triggerableAnim("double", RawAnimation.begin().then("animation.mom.attack3", Animation.LoopType.PLAY_ONCE))
                        .triggerableAnim("birth", RawAnimation.begin().then("animation.mom.babyx2", Animation.LoopType.PLAY_ONCE))
                        .triggerableAnim("shoot", RawAnimation.begin().then("animation.mom.shoot", Animation.LoopType.PLAY_ONCE))
                        .triggerableAnim("die", RawAnimation.begin().then("animation.mom.die", Animation.LoopType.PLAY_ONCE))
                    );



    }

    private void makebaby(boolean launch){
        Baby_Headcrab summon = HalfLifeEntities.BABY_HEADCRAB.get().create(level);
        if (summon != null) {
            summon.moveTo(this.position());
            ForgeEventFactory.onFinalizeSpawn((Mob) summon, (ServerLevelAccessor) level, level.getCurrentDifficultyAt(summon.blockPosition()), MobSpawnType.REINFORCEMENT, null, null);
            level.addFreshEntity(summon);
            if (launch) summon.setDeltaMovement(this.random.nextFloat()*(this.random.nextFloat() < 0.5 ? -1 : 1)*1.5f, 2f,this.random.nextFloat()*(this.random.nextFloat() < 0.5 ? -1 : 1)*1.5f);
            if (this.isDeadOrDying()) BrainUtils.setMemory(summon, MemoryModuleType.ATTACK_TARGET, this.getKillCredit());
            else BrainUtils.setMemory(summon, MemoryModuleType.ATTACK_TARGET, BrainUtils.getMemory(this, MemoryModuleType.ATTACK_TARGET));



        }
    }


    private <T extends GeoAnimatable> PlayState predicate(AnimationState<T> tAnimationState) {


        if(tAnimationState.isMoving()) {
         if (!this.isangry()) {
            tAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.mom.walk", Animation.LoopType.LOOP));
            return PlayState.CONTINUE;
        }
            tAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.mom.run", Animation.LoopType.LOOP));
            return PlayState.CONTINUE;
        }


        tAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.mom.idle", Animation.LoopType.LOOP));

        return PlayState.CONTINUE;

    }




    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    @Override
    public void performSecondRangedAttack(LivingEntity livingentity, float p_33318_) {
        this.playSound(this.getSackSound(), this.getSoundVolume(), this.getVoicePitch());
        this.makebaby(false);
    }


    @Override
    public void performRangedAttack(LivingEntity pTarget, float p_33318_) {
        Vec3 vec3 = pTarget.getDeltaMovement();
        double d0 = pTarget.getX() + vec3.x - this.getX();
        double d1 = pTarget.getEyeY() - this.getY()-this.getBbHeight();
        double d2 = pTarget.getZ() + vec3.z - this.getZ();
        double d3 = Math.sqrt(d0 * d0 + d2 * d2);
        this.playSound(getSpitSound(), this.getSoundVolume(), this.getVoicePitch());
        AcidThrown acidThrown = new AcidThrown(this.level, this);
        acidThrown.setXRot(acidThrown.getXRot() - -20.0F);
        acidThrown.shoot(d0+0.2, d1+1+d3 * (d3 > 15 ? 1f : 2*d3/10), d2+0.2, 0.7f, 1.0F);
        this.level.addFreshEntity(acidThrown);



    }


    @Override
    public boolean performMultiAttack(boolean after, Entity entity, float disablechance, float attack_modifier, float knockback_modifier, @Nullable MobEffect effect, int duration, boolean visible) {
        boolean u = false;
        List<Entity> list = this.level.getEntities(this, this.front.getBoundingBox(), obj -> obj instanceof LivingEntity && !(obj.getType().is(HLTags.EntityTypes.FACTION_HEADCRAB)));
        for (Entity entity1 : list) {
            if (entity1 instanceof LivingEntity living) {
                this.ConfigurabledoHurtTargetShieldBoolean(after, entity1, disablechance, attack_modifier, knockback_modifier, effect, duration, visible);
                u = true;
            }
        }
        return u;
    }

    @Override
    public boolean isinside() {
         List<Entity> list = this.level.getEntities(this, this.front.getBoundingBox(), obj -> obj instanceof LivingEntity && !(obj.getType().is(HLTags.EntityTypes.FACTION_HEADCRAB)));
        return !list.isEmpty();
    }
}





