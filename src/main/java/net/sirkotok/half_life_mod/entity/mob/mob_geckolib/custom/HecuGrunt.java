package net.sirkotok.half_life_mod.entity.mob.mob_geckolib.custom;


import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.sirkotok.half_life_mod.entity.base.HalfLifeMonster;
import net.sirkotok.half_life_mod.entity.brain.behaviour.*;
import net.sirkotok.half_life_mod.entity.mob.modinterface.AmmoCountMob;
import net.sirkotok.half_life_mod.entity.mob.modinterface.DoubleRangedMob;
import net.sirkotok.half_life_mod.entity.projectile.Bullet;
import net.sirkotok.half_life_mod.entity.projectile.Granade;
import net.sirkotok.half_life_mod.item.HalfLifeItems;
import net.sirkotok.half_life_mod.misc.gamerules.HalfLifeGameRules;
import net.sirkotok.half_life_mod.misc.util.CommonSounds;
import net.sirkotok.half_life_mod.misc.util.HLperUtil;
import net.sirkotok.half_life_mod.misc.util.InfightingUtil;
import net.sirkotok.half_life_mod.sound.HalfLifeSounds;
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
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.List;


public class HecuGrunt extends HalfLifeMonster implements AmmoCountMob, RangedAttackMob, DoubleRangedMob, GeoEntity, SmartBrainOwner<HecuGrunt> {

    @Override
    protected float getSoundVolume() {
        return 0.5f;
    }


    public void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        pCompound.putInt("AmmoCount", this.getAmmo());
        pCompound.putInt("MaxAmmoCount", this.getMaxAmmo());
        pCompound.putInt("GruntType", this.getGruntType());
        pCompound.putInt("GunType", this.getGunType());
        pCompound.putInt("Helm", this.getHelmet());
        pCompound.putInt("Pouch", this.getPouch());
        pCompound.putBoolean("HasCig", this.getCig());
        pCompound.putBoolean("SkinCol", this.getSkin());
    }

    public void readAdditionalSaveData(CompoundTag pCompound) {
        this.setAmmo(pCompound.getInt("AmmoCount"));
        this.setMaxAmmo(pCompound.getInt("MaxAmmoCount"));
        this.setGruntType(pCompound.getInt("GruntType"));
        this.setGunType(pCompound.getInt("GunType"));
        this.setHelmet(pCompound.getInt("Helm"));
        this.setPouch(pCompound.getInt("Pouch"));
        this.setCig(pCompound.getBoolean("HasCig"));
        this.setSkin(pCompound.getBoolean("SkinCol"));

        super.readAdditionalSaveData(pCompound);
    }

    private AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    public static final EntityDataAccessor<Boolean> IS_SITTING = SynchedEntityData.defineId(HecuGrunt.class, EntityDataSerializers.BOOLEAN);
    public static final EntityDataAccessor<Boolean> IS_ANGRY = SynchedEntityData.defineId(HecuGrunt.class, EntityDataSerializers.BOOLEAN);
    public static final EntityDataAccessor<Integer> ORDER_ID = SynchedEntityData.defineId(HecuGrunt.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Boolean> CAN_ATTACK = SynchedEntityData.defineId(HecuGrunt.class, EntityDataSerializers.BOOLEAN);
    public static final EntityDataAccessor<Integer> AMMO = SynchedEntityData.defineId(HecuGrunt.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Integer> MAX_AMMO = SynchedEntityData.defineId(HecuGrunt.class, EntityDataSerializers.INT);

    public static final EntityDataAccessor<Integer> GUNTYPE = SynchedEntityData.defineId(HecuGrunt.class, EntityDataSerializers.INT);
    //0 = smg
    //1 = shotgun
    //2 = pistol
    //3 = deagle
    //4 = SAW
    public static final EntityDataAccessor<Integer> GRUNTTYPE = SynchedEntityData.defineId(HecuGrunt.class, EntityDataSerializers.INT);
    // 0 = gasmask soldier (SMG)
    // 1 = beret (SMG)
    // 2 = balaclava (shotgunner) -> no helmet
    // 3 = bandana (SAW) -> own helmet
    // 4 = vent (Deagle) -> own helmet, backpack, pouch
    // 5 = medic (Pistol) -> own helmet, face, backpack, pouch

    public static final EntityDataAccessor<Boolean> SKIN = SynchedEntityData.defineId(HecuGrunt.class, EntityDataSerializers.BOOLEAN);
    // true = white
    // false = black
    public static final EntityDataAccessor<Boolean> CIG = SynchedEntityData.defineId(HecuGrunt.class, EntityDataSerializers.BOOLEAN);
    // true = yes
    // false = no

    public static final EntityDataAccessor<Integer> HELMET = SynchedEntityData.defineId(HecuGrunt.class, EntityDataSerializers.INT);
    // 0 = helmet 1
    // 1 = helmet 2
    // 2 = red beret
    // 3 = black beret
    // 4 = green beret



    public static final EntityDataAccessor<Integer> POUCH = SynchedEntityData.defineId(HecuGrunt.class, EntityDataSerializers.INT);
    //0 = normal
    //1 = 4
    //2 = decay



    public void setHelmet(int i){
      this.entityData.set(HELMET, i);
    }
    public int getHelmet(){
       return this.entityData.get(HELMET);
    }
    public void setGruntType(int i){
        this.entityData.set(GRUNTTYPE, i);
    }
    public void setRandomGruntType(){
        float f = random.nextFloat();
        if (f < 0.35) {
            setGruntType(0);
            return;
        }
        if (f < 0.7) {
            setGruntType(random.nextInt(0, 3));
            return;
        }

        if (f < 0.8) {
            setGruntType(random.nextInt(2, 4));
            return;
        }
            setGruntType(random.nextInt(4, 6));
     }




    public void setKitFromGruntType(){
        int i = getGruntType();
        if (i == 0) setHelmet(random.nextInt(0, 2));
        if (i == 1) setHelmet(random.nextInt(2, 5));
        if (i<4 && random.nextFloat() < 0.2) this.setPouch(random.nextInt(0, 3));
        if (i<2) this.setGunType(0);
        if (i==2) this.setGunType(1);
        if (i==3) this.setGunType(4);
        if (i==4) this.setGunType(random.nextFloat() < 0.1 ? 3 : 2);
        if (i==5) this.setGunType(random.nextFloat() < 0.1 ? 2 : 3);
    }


    public int getGruntType(){
        return this.entityData.get(GRUNTTYPE);
    }
    public void setGunType(int i){
        this.entityData.set(GUNTYPE, i);
        switch (i) {
            case 0: this.setItemSlot(EquipmentSlot.MAINHAND, HalfLifeItems.SMG_HL1.get().getDefaultInstance()); this.setMaxAmmo(50); break;
            case 1: this.setItemSlot(EquipmentSlot.MAINHAND, HalfLifeItems.SHOTGUN.get().getDefaultInstance()); this.setMaxAmmo(8); break;
            case 2: this.setItemSlot(EquipmentSlot.MAINHAND, HalfLifeItems.PISTOL.get().getDefaultInstance()); this.setMaxAmmo(17); break;
            case 3: this.setItemSlot(EquipmentSlot.MAINHAND, HalfLifeItems.DEAGLE.get().getDefaultInstance()); this.setMaxAmmo(7); break;
            case 4: this.setItemSlot(EquipmentSlot.MAINHAND, HalfLifeItems.SAW.get().getDefaultInstance()); this.setMaxAmmo(50); break;
        }
    }

    public int getGunType(){
        return this.entityData.get(GUNTYPE);
    }
    public void setPouch(int i){
        this.entityData.set(POUCH, i);
    }
    public int getPouch(){
        return this.entityData.get(POUCH);
    }

    public void setAmmo(int i){
        this.entityData.set(AMMO, i);
    }

    public int getAmmo() {
        return this.entityData.get(AMMO);
    }
    public void setMaxAmmo(int i){
        this.entityData.set(MAX_AMMO, i);
    }
    public int getMaxAmmo(){
        return this.entityData.get(MAX_AMMO);
    }

    public void setCig(boolean i){
        this.entityData.set(CIG, i);
    }
    public boolean getCig(){
        return this.entityData.get(CIG);
    }
    public void setSkin(boolean i){
        this.entityData.set(SKIN, i);
    }
    public boolean getSkin(){
        return this.entityData.get(SKIN);
    }


    public boolean isMedic(){
        return getGruntType() == 5;
    }
    public boolean isVent(){
        return getGruntType() == 4;
    }
    public boolean isBalaclava(){
        return getGruntType() == 2;
    }
    public boolean isBandana(){
        return getGruntType() == 3;
    }



    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(CAN_ATTACK, true);
        this.entityData.define(IS_SITTING, false);
        this.entityData.define(IS_ANGRY, false);
        this.entityData.define(ORDER_ID, 1);

        this.entityData.define(AMMO, 0);
        this.entityData.define(MAX_AMMO, 0);

        this.entityData.define(GUNTYPE, 0);
        this.entityData.define(GRUNTTYPE, 0);
        this.entityData.define(HELMET, 0);
        this.entityData.define(POUCH, 0);
        this.entityData.define(CIG, false);
        this.entityData.define(SKIN, true);


    }




    public int getorder(){
        return this.entityData.get(ORDER_ID);
    }

    public void setorder(int order){
        this.entityData.set(ORDER_ID, order);
    }

    public boolean getcanattack() {
        return this.getAmmo() > 0 && this.getorder() == 1 && (!this.isBalaclava() || HLperUtil.DistanceToTarget(this) < 4.5f);
    }








    public void setIsSitting(boolean sit) {
        this.entityData.set(IS_SITTING, sit);
    }
    public boolean getIssitting() {
        return this.entityData.get(IS_SITTING);
    }

    protected boolean isangry() {
        return this.entityData.get(IS_ANGRY);
    }

    public HecuGrunt(EntityType type, Level level) {
        super(type, level);
        this.xpReward = 10;
         this.setPathfindingMalus(BlockPathTypes.DAMAGE_FIRE, -1F);
        this.setPathfindingMalus(BlockPathTypes.DANGER_FIRE, -1F);
    }


    public static AttributeSupplier setAttributes() {
        return Monster.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 25D)
                .add(Attributes.ATTACK_DAMAGE, 5f)
                .add(Attributes.ATTACK_SPEED, 1.0f)
                .add(Attributes.ATTACK_KNOCKBACK, 1f)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.25f)
                .add(Attributes.ARMOR, 15f)
                .add(Attributes.ARMOR_TOUGHNESS, 10f)
                .add(Attributes.MOVEMENT_SPEED, 0.34f).build();
    }





    @Override
    protected Brain.Provider<?> brainProvider() {
        return new SmartBrainProvider<>(this);
    }

    // ORDERS: 1 = stay; 2 = change position;

    int hit = 0;
    int track = 200;

    public void changeorder(){
        if (track < 200) return;
        if (random.nextFloat() < 0.1f) return;
        if (this.getorder() != 1) this.setorder(1); else setorder(0);
        this.hit = 0;
        track = 0;
    }


    @Override
    public boolean hurt(DamageSource pSource, float pAmount) {
        if (!this.getcanattack()) this.changeorder();
        return super.hurt(pSource, pAmount);
    }

    @Override
    public void aiStep() {
        super.aiStep();
        if (BrainUtils.getTargetOfEntity(this) != null) {
        if (!this.getcanattack() && !this.level.isClientSide() && this.tickCount % (70) == 0) {
            ServerLevel pLevel = (ServerLevel) this.level;
            BlockPos pBlockPos = this.blockPosition();
            int rad = 15;
            List<Mob> myfaction = EntityRetrievalUtil.getEntities((Level) pLevel,
                    new AABB(pBlockPos.getX() - rad, pBlockPos.getY() - rad, pBlockPos.getZ() - rad,
                            pBlockPos.getX() + rad, pBlockPos.getY() + rad, pBlockPos.getZ() + rad), obj -> obj instanceof HecuGrunt trooper && trooper.getcanattack() && !obj.equals(this));
            int size = myfaction.size();
            this.hit+=(14-Math.min(size, 13))+1;
            if (this.random.nextInt(100) < Math.min(hit, 80)) this.changeorder();
        }
        if (!this.level.isClientSide() && this.getcanattack() && this.tickCount % (150+random.nextInt(200)) == 0) {
        ServerLevel pLevel = (ServerLevel) this.level;
        BlockPos pBlockPos = this.blockPosition();
        int rad = 15;
        List<Mob> myfaction = EntityRetrievalUtil.getEntities((Level) pLevel,
                new AABB(pBlockPos.getX() - rad, pBlockPos.getY() - rad, pBlockPos.getZ() - rad,
                        pBlockPos.getX() + rad, pBlockPos.getY() + rad, pBlockPos.getZ() + rad), obj -> obj instanceof HecuGrunt trooper && trooper.getcanattack() && !obj.equals(this));

        int size = myfaction.size();
        this.hit+=size+1;
        if (this.random.nextInt(100) < Math.min(hit, 70)) this.changeorder();
    }
    }
    }
    @Override
    public void tick() {
        track++;
        super.tick();
        if (this.tickCount % 50 == 0) {
            InfightingUtil.alertsameteam(this);
        }
    }



    @Override
    protected void customServerAiStep() {
        if (!this.level.getGameRules().getRule(HalfLifeGameRules.HALF_LIFE_AI_IS_ON).get()) return;
        tickBrain(this);
    }






    @Override
    public List<ExtendedSensor<HecuGrunt>> getSensors() {
        return ObjectArrayList.of(
                new HurtBySensor<>(),
                new NearbyPlayersSensor<>(),
                new NearbyLivingEntitySensor<HecuGrunt>()
                        .setPredicate((target, entity) ->
                                InfightingUtil.commonenemy(target) // || InfightingUtil.RaceXSpecific(target)
                            ));
    }



    @Override
    public BrainActivityGroup<HecuGrunt> getCoreTasks() {
        return BrainActivityGroup.coreTasks(
                new LookAtTarget<>(),
                new MoveToWalkTarget<>(),
                new CustomBehaviour<>(entity -> setorder(random.nextInt(2))).cooldownFor(entity -> random.nextInt(300, 1000)));
    }




    @Override
    public BrainActivityGroup<HecuGrunt> getIdleTasks() {
        return BrainActivityGroup.idleTasks(
                new FirstApplicableBehaviour<HecuGrunt>(
                        new FirstApplicableBehaviour<>(
                                new CustomBehaviour<>(entity -> setIsSitting(random.nextBoolean())).startCondition(entity -> !getIssitting() && getorder() == 1),
                                new CustomBehaviour<>(entity -> setIsSitting(false)).startCondition(entity -> getIssitting())
                        ).cooldownFor(entity -> 200+random.nextInt(200)),
                        new TargetOrRetaliateHLT<>(),
                        new SetPlayerLookTarget<>(),
                        new CustomBehaviour<>(entity -> this.entityData.set(IS_ANGRY, false)).startCondition(entity -> this.isangry()),
                        new SetRandomLookTarget<>()),
                new OneRandomBehaviour<>(
                        new SetRandomWalkTarget<>(),
                        new Idle<>().runFor(entity -> entity.getRandom().nextInt(10, 20)),
                        new Idle<>().runFor(entity -> entity.getRandom().nextInt(10, 90))
                ));
    }



    int shotdelay = 0;

    public void playChargeSound() {
        if (this.random.nextFloat() < 0.1f) {
        CommonSounds.PlaySoundAsOwn(this, HalfLifeSounds.SHOCKTROOPER_CHARGE.get()); }
    }


    @Override
    public BrainActivityGroup<HecuGrunt> getFightTasks() {
        return BrainActivityGroup.fightTasks(
                new InvalidateAttackTarget<>(),
                new Retaliate<>(),
                new SetWalkTargetToRandomSpotAroundAttackTarget<>().startCondition(entity -> distanceTo(HLperUtil.TargetOrThis(this)) > 15 || (this.isBalaclava() && HLperUtil.DistanceToTarget(this) > 4)),
                new SetWalkTargetToRandomSpotAwayFromAttackTarget<>().startCondition(entity -> distanceTo(HLperUtil.TargetOrThis(this)) < 4 && !this.isBalaclava()),
                new CustomBehaviour<>(entity -> this.entityData.set(IS_ANGRY, true)).startCondition(entity -> !this.isangry()), //.whenStarting(entity -> CommonSounds.PlaySoundAsOwn(this, this.getSpotSound(HLperUtil.TargetOrThis(this) instanceof Player)))
                new FirstApplicableBehaviour<>(
                new CustomBehaviour<>(entity -> setIsSitting(random.nextBoolean())).startCondition(entity -> !getIssitting() && getorder() == 1),
                new CustomBehaviour<>(entity -> setIsSitting(false)).startCondition(entity -> getIssitting())
                ).cooldownFor(entity -> 200),
                new FirstApplicableBehaviour<HecuGrunt>(
                        new SetWalkTargetToRandomSpotAroundAttackTarget<>().startCondition(entity -> distanceTo(HLperUtil.TargetOrThis(this)) > 10).whenStarting(entity -> this.playChargeSound()),
                        new SetWalkTargetToRandomSpotAwayFromAttackTarget<>().startCondition(entity -> distanceTo(HLperUtil.TargetOrThis(this)) < 8 && !this.isBalaclava()),
                        new SetRandomWalkTarget<>()
                ).startCondition(entity -> !entity.getcanattack()),
                new FirstApplicableBehaviour<>(
                new StopAndReload<HecuGrunt>(20, 8, HalfLifeSounds.SMG_RELOAD.get()).cooldownFor(entity -> 100)
                                .whenStarting(entity -> triggerAnim("onetime", "reload")).startCondition(entity -> this.getAmmo() < 1 && !this.isBandana()),
                        new StopAndReload<HecuGrunt>(65, 8, HalfLifeSounds.SAW_RELOAD.get()).cooldownFor(entity -> 100)
                                .whenStarting(entity -> triggerAnim("onetime", "reloadsaw")).startCondition(entity -> this.getAmmo() < 1 && this.isBandana()),
                new StopAndShoot<HecuGrunt>(2, 10, 4f).startCondition(entity -> this.getcanattack())
                        .whenStarting(entity ->  triggerAnim("onetime", "shoot"))
                        .cooldownFor(entity -> getGunDelay()),
                new StopAndSecondShoot<HecuGrunt>(11, 10, 0.7f, HalfLifeSounds.SHOCKTROOPER_THROW.get()).startCondition(entity -> this.getorder() == 1 && this.random.nextFloat() < 0.01f)
                        .whenStarting(entity -> triggerAnim("onetime", "nade"))
                        .cooldownFor(entity -> 200 + random.nextInt(300))
                )
        );

    }





    public int getGunDelay(){
        int i = getGunType();
        if (i == 0 || i == 4) return this.shotdelay % 3 == 0 ? 4 : 0;
        if (i == 1) return random.nextInt(15,25);
        if (i == 2) return 10;
        return 17;
    }




    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor pLevel, DifficultyInstance pDifficulty, MobSpawnType pReason, @Nullable SpawnGroupData pSpawnData, @Nullable CompoundTag pDataTag) {


        this.setCig(random.nextFloat() < 0.2f);
        this.setSkin(random.nextFloat() > 0.2f);
        this.setRandomGruntType();
        this.setKitFromGruntType();




        return super.finalizeSpawn(pLevel, pDifficulty, pReason, pSpawnData, pDataTag);
    }

    @Override
    public double getMeleeAttackRangeSqr(LivingEntity pEntity) {
        return (double)(this.getBbWidth() * 2.5F * this.getBbWidth() * 2.5F + pEntity.getBbWidth());
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(new AnimationController<>(this, "noattack", 0, this::noattack));
        controllerRegistrar.add(new AnimationController<>(this, "gun", 0, this::gunpredicate));
        controllerRegistrar.add(new AnimationController<>(this, "mainloop", 0, this::mainpredicate));
        controllerRegistrar.add(new AnimationController<>(this, "onetime", state -> PlayState.STOP)
                .triggerableAnim("kick", RawAnimation.begin().then("animation.hecu.kick", Animation.LoopType.PLAY_ONCE))
                .triggerableAnim("shoot", RawAnimation.begin().then("animation.hecu.fire", Animation.LoopType.PLAY_ONCE)) // 0.125
                .triggerableAnim("nade", RawAnimation.begin().then("animation.hecu.granade", Animation.LoopType.PLAY_ONCE)) // 0.84
                .triggerableAnim("undernade", RawAnimation.begin().then("animation.hecu.underbarrel", Animation.LoopType.PLAY_ONCE)) // 0.3
                .triggerableAnim("reload", RawAnimation.begin().then("animation.hecu.fightingstand_reload", Animation.LoopType.PLAY_ONCE))
                .triggerableAnim("reloadsaw", RawAnimation.begin().then("animation.hecu.fightingstand_reload_saw", Animation.LoopType.PLAY_ONCE))
        );
        //animation.hecu.granade_ground 0.7 -> 1.25
        // animation.hecu.torch
        //animation.hecu.medbay
    }

    private <T extends GeoAnimatable> PlayState gunpredicate(AnimationState<T> tAnimationState) {
        int i = getGunType();
        switch (i){
            case 1:
                tAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.hecu.shotgun", Animation.LoopType.LOOP));
                return PlayState.CONTINUE;
            case 2:
                tAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.hecu.pistol", Animation.LoopType.LOOP));
                return PlayState.CONTINUE;
            case 3:
                tAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.hecu.deagle", Animation.LoopType.LOOP));
                return PlayState.CONTINUE;
            case 4:
                tAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.hecu.saw", Animation.LoopType.LOOP));
                return PlayState.CONTINUE;
        }

        tAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.hecu.smg", Animation.LoopType.LOOP));
        return PlayState.CONTINUE;
    }

    private <T extends GeoAnimatable> PlayState noattack(AnimationState<T> tAnimationState) {
        tAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.hecu.noshoot", Animation.LoopType.LOOP));
        return PlayState.CONTINUE;
    }


    private <T extends GeoAnimatable> PlayState mainpredicate(AnimationState<T> tAnimationState) {
        // animation.hecu.cover

        if(tAnimationState.isMoving()) {
            if (this.isangry()) {
                tAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.hecu.walk_fighting", Animation.LoopType.LOOP));
                return PlayState.CONTINUE;
            }
            tAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.hecu.walk_idle", Animation.LoopType.LOOP));
            return PlayState.CONTINUE;
        }

        if (this.getIssitting()) {
            if (this.getGunType() == 2 || this.getGunType() == 4) {
                tAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.hecu.sit_pistolsaw", Animation.LoopType.LOOP));
                return PlayState.CONTINUE;
            }
            tAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.hecu.sit", Animation.LoopType.LOOP));
            return PlayState.CONTINUE;
        }


        if (this.isangry()) {
            if (this.getGunType() == 2 || this.getGunType() == 4) {
                tAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.hecu.fightingstand_pistolsaw", Animation.LoopType.LOOP));
                return PlayState.CONTINUE;
            }
            tAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.hecu.fightingstand_rest", Animation.LoopType.LOOP));
            return PlayState.CONTINUE;
        }

        tAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.hecu.idle", Animation.LoopType.LOOP));
        return PlayState.CONTINUE;
    }




    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }



    private float length(){
        return this.getBbWidth()/2+0.5f;
    }

    private Vec3 rotvec(int angledegree){
        float i = length();
        double yrot = (this.yBodyRot+angledegree)/180*Math.PI;
        double d1 = Math.sin(yrot);
        double d2 = Math.cos(yrot);
        return new Vec3((float)this.getX()-i*d1, this.getY()+(this.getIssitting() ? 1.20 : 1.35f), (float)this.getZ()+i*d2);
    }

    @Override
    public void performSecondRangedAttack(LivingEntity pTarget, float p_33318_)   {
        Vec3 vec3 = pTarget.getDeltaMovement();
        double d0 = pTarget.getX() + vec3.x - this.getX();
        double d1 = pTarget.getEyeY() - this.getY()-this.getBbHeight();
        double d2 = pTarget.getZ() + vec3.z - this.getZ();
        double d3 = Math.sqrt(d0 * d0 + d2 * d2);
        Granade spore = new Granade(this.level, this);
        spore.minus = -0.93f;
        spore.maxrichochet = 25;
        spore.setXRot(spore.getXRot() - -20.0F);
        spore.shoot(d0+0.2, d1+1+d3 * (d3 > 15 ? 1f : 2*d3/10), d2+0.2, 0.7f, 1.0F);
        this.level.addFreshEntity(spore);
}
    @Override
    public void performRangedAttack(LivingEntity livingentity, float p_33318_) {


        double d1 = livingentity.getX() - this.getX();
        double d2 = livingentity.getY(0.4D) - this.getY(0.4D);
        double d3 = livingentity.getZ() - this.getZ();

        this.playSound(getShootSound(), this.getSoundVolume(), 1f);
        this.setAmmo(this.getAmmo()-1);


        int i = getGunType();
        switch (i) {
            case 0:
                shotdelay++;
                Bullet bullet = new Bullet(this.level, this, d1, d2, d3);
                bullet.setdamage(0.8f);
                bullet.setPos(this.getX() - (double)(this.getBbWidth() + 1.0F) * 0.5D * (double) Mth.sin(this.yBodyRot * ((float)Math.PI / 180F)), this.getEyeY() - (double)0.1F, this.getZ() + (double)(this.getBbWidth() + 1.0F) * 0.5D * (double) Mth.cos(this.yBodyRot * ((float)Math.PI / 180F)));
                bullet.shoot(d1, d2, d3, p_33318_, 4f);
                this.level.addFreshEntity(bullet);
                break;
            case 1:
                int j = 6;
                while (j > 0) {
                    Bullet snowball = new Bullet(this.level, this, d1, d2, d3);
                    snowball.setdamage(0.8f);
                    snowball.sethasknockback();
                    snowball.setPos(this.getX() - (double)(this.getBbWidth() + 1.0F) * 0.5D * (double) Mth.sin(this.yBodyRot * ((float)Math.PI / 180F)), this.getEyeY() - (double)0.1F, this.getZ() + (double)(this.getBbWidth() + 1.0F) * 0.5D * (double) Mth.cos(this.yBodyRot * ((float)Math.PI / 180F)));
                    snowball.shoot(d1, d2, d3, p_33318_, 8f);
                    this.level.addFreshEntity(snowball);
                    j--;
                }
                break;
            case 2:
                Bullet bullet1 = new Bullet(this.level, this, d1, d2, d3);
                bullet1.setPos(this.getX() - (double)(this.getBbWidth() + 1.0F) * 0.5D * (double) Mth.sin(this.yBodyRot * ((float)Math.PI / 180F)), this.getEyeY() - (double)0.1F, this.getZ() + (double)(this.getBbWidth() + 1.0F) * 0.5D * (double) Mth.cos(this.yBodyRot * ((float)Math.PI / 180F)));
                bullet1.shoot(d1, d2, d3, p_33318_, 1.5f);
                this.level.addFreshEntity(bullet1);
                break;
            case 3:
                Bullet bullet4 = new Bullet(this.level, this, d1, d2, d3);
                bullet4.setdamage(15f);
                bullet4.setPos(this.getX() - (double)(this.getBbWidth() + 1.0F) * 0.5D * (double) Mth.sin(this.yBodyRot * ((float)Math.PI / 180F)), this.getEyeY() - (double)0.1F, this.getZ() + (double)(this.getBbWidth() + 1.0F) * 0.5D * (double) Mth.cos(this.yBodyRot * ((float)Math.PI / 180F)));
                bullet4.shoot(d1, d2, d3, p_33318_, 1.5f);
                this.level.addFreshEntity(bullet4);
                break;
            case 4:
                shotdelay++;
                Bullet bullet5 = new Bullet(this.level, this, d1, d2, d3);
                bullet5.setdamage(3.5f);
                bullet5.setPos(this.getX() - (double)(this.getBbWidth() + 1.0F) * 0.5D * (double) Mth.sin(this.yBodyRot * ((float)Math.PI / 180F)), this.getEyeY() - (double)0.1F, this.getZ() + (double)(this.getBbWidth() + 1.0F) * 0.5D * (double) Mth.cos(this.yBodyRot * ((float)Math.PI / 180F)));
                bullet5.shoot(d1, d2, d3, p_33318_, 4.5f);
                this.level.addFreshEntity(bullet5);
                break;
        }



    }


  /*  @Override
    public void playAmbientSound() {
        super.playAmbientSound();
    } */


    public SoundEvent getSawSound(){
        switch (RandomSource.create().nextInt(1,4)) {
            case 1:  return HalfLifeSounds.SAW_FIRE1.get();
            case 2:  return HalfLifeSounds.SAW_FIRE2.get();
            case 3:  return HalfLifeSounds.SAW_FIRE3.get();
        }
        return HalfLifeSounds.HEADCRAB_1_ALERT_1.get();
    }

    public SoundEvent getShootSound() {
        int i = getGunType();
        switch (i) {
            case 1: return HalfLifeSounds.SG_HECU.get();
            case 2: return HalfLifeSounds.PISTOL_SHOOT.get();
            case 3: return HalfLifeSounds.DESERT_EAGLE_FIRE.get();
            case 4: return getSawSound();
        }
        switch (RandomSource.create().nextInt(1,4)) {
            case 1:  return HalfLifeSounds.SMG_SHOT1.get();
            case 2:  return HalfLifeSounds.SMG_SHOT2.get();
            case 3:  return HalfLifeSounds.SMG_SHOT3.get();
        }
        return HalfLifeSounds.HEADCRAB_1_ALERT_1.get();
    }

    @Override
    public void performReloadAction() {
        this.setAmmo(this.getMaxAmmo());
    }


    //  public SoundEvent getDoubleAttackSound() {
  //     return HalfLifeSounds.SHOCKTROOPER_ATTACK.get();
  //  }


  /*  protected SoundEvent getDeathSound() {
        switch (this.random.nextInt(1,5)) {
            case 1:  return HalfLifeSounds.SHOCKTROOPER_DIE1.get();
            case 2:  return HalfLifeSounds.SHOCKTROOPER_DIE2.get();
            case 3:  return HalfLifeSounds.SHOCKTROOPER_DIE3.get();
            case 4:  return HalfLifeSounds.SHOCKTROOPER_DIE4.get();
        }
        return HalfLifeSounds.HEADCRAB_1_DIE_1.get();
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource pDamageSource) {
        switch (this.random.nextInt(1,6)) {
            case 1:  return HalfLifeSounds.SHOCKTROOPER_PAIN1.get();
            case 2:  return HalfLifeSounds.SHOCKTROOPER_PAIN2.get();
            case 3:  return HalfLifeSounds.SHOCKTROOPER_PAIN3.get();
            case 4:  return HalfLifeSounds.SHOCKTROOPER_PAIN4.get();
            case 5:  return HalfLifeSounds.SHOCKTROOPER_PAIN5.get();
        }
        return HalfLifeSounds.HEADCRAB_1_DIE_1.get();
    }

    @Override
    public int getAmbientSoundInterval() {
        return 200;
    }

    protected SoundEvent getAmbientSound() {
        boolean a = this.isangry();
        int f = a ? random.nextInt(1, 6) : random.nextFloat() < 0.3 ? 6 : random.nextInt(1, 4);
        switch (f) {
            case 1:
                return HalfLifeSounds.SHOCKTROOPER_IDLE.get();
            case 2:
                return HalfLifeSounds.SHOCKTROOPER_IDLE_A.get();
            case 3:
                return HalfLifeSounds.SHOCKTROOPER_IDLE_Q.get();
            case 4:
                return HalfLifeSounds.SHOCKTROOPER_IDLE_CHECK_IN.get();
            case 5:
                return HalfLifeSounds.SHOCKTROOPER_IDLE_CLEAR.get();
            case 6:
                return HalfLifeSounds.SHOCKTROOPER_TAUNT.get();
        }
        return HalfLifeSounds.HEADCRAB_1_DIE_1.get();
    }

    protected SoundEvent getSpotSound(Boolean yes) {
        if (yes && random.nextFloat() < 0.8f) {
        int f = random.nextInt(1, 4);
        switch (f) {
            case 1:
                return HalfLifeSounds.SHOCKTROOPER_PL_SPOT1.get();
            case 2:
                return HalfLifeSounds.SHOCKTROOPER_PL_SPOT2.get();
            case 3:
                return HalfLifeSounds.SHOCKTROOPER_PL_SPOT3.get();
        }
        }
        return HalfLifeSounds.SHOCKTROOPER_ENEMY_SPOT.get();
    }
     */

    }









