package net.sirkotok.half_life_mod.entity.mob_geckolib.custom;


import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.Vec3i;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.monster.Drowned;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.fluids.FluidType;
import net.sirkotok.half_life_mod.entity.base.HalfLifeMonster;
import net.sirkotok.half_life_mod.entity.base.HalfLifeNeutral;
import net.sirkotok.half_life_mod.entity.brain.behaviour.*;
import net.sirkotok.half_life_mod.entity.brain.sensor.SmellSensor;
import net.sirkotok.half_life_mod.entity.mob_geckolib.custom.parts.SharkPart;
import net.sirkotok.half_life_mod.sound.HalfLifeSounds;
import net.sirkotok.half_life_mod.util.HLperUtil;
import net.sirkotok.half_life_mod.util.HLTags;
import net.tslat.smartbrainlib.api.SmartBrainOwner;
import net.tslat.smartbrainlib.api.core.BrainActivityGroup;
import net.tslat.smartbrainlib.api.core.SmartBrainProvider;
import net.tslat.smartbrainlib.api.core.behaviour.FirstApplicableBehaviour;
import net.tslat.smartbrainlib.api.core.behaviour.OneRandomBehaviour;
import net.tslat.smartbrainlib.api.core.behaviour.custom.look.LookAtTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.misc.CustomBehaviour;
import net.tslat.smartbrainlib.api.core.behaviour.custom.move.MoveToWalkTarget;
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
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.List;


public class Shark extends HalfLifeMonster implements GeoEntity, SmartBrainOwner<Shark> {

    //TODO: sounds
    public int light = 0;

    private final SharkPart mouth;
    private final SharkPart[] parts;

    @Override
    public boolean isMultipartEntity() {
        return true;
    }

    @Override
    public net.minecraftforge.entity.PartEntity<?>[] getParts() {
        return this.parts;
    }


    @Override
    protected void actuallyHurt(DamageSource p_21240_, float p_21241_) {
        super.actuallyHurt(p_21240_, p_21241_);
    }

    private BlockPos targetPosition;

    private int attackbehaviour;
    private AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);


    @Override
    protected void playSwimSound(float pVolume) {
        if (RandomSource.create().nextFloat() < 0.08f) super.playSwimSound(pVolume);
    }


    protected void playAgroSound(float pVolume) {
        this.playSound(this.getAgroSound(), pVolume, 1.0F + (this.random.nextFloat() - this.random.nextFloat()) * 0.05F);
    }

    protected SoundEvent getAgroSound() {
        return RandomSource.create().nextFloat() < 0.5 ? HalfLifeSounds.ICHY_ATTACK1.get() : HalfLifeSounds.ICHY_ATTACK2.get();
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        if (this.getTarget() != null)
            switch (RandomSource.create().nextInt(1, 4)) {
                case 1:
                    return HalfLifeSounds.ICHY_ALERT1.get();
                case 2:
                    return HalfLifeSounds.ICHY_ALERT2.get();
                case 3:
                    return HalfLifeSounds.ICHY_ALERT3.get();
            }
        switch (RandomSource.create().nextInt(1, 5)) {
            case 1:
                return HalfLifeSounds.ICHY_IDLE1.get();
            case 2:
                return HalfLifeSounds.ICHY_IDLE2.get();
            case 3:
                return HalfLifeSounds.ICHY_IDLE3.get();
            case 4:
                return HalfLifeSounds.ICHY_IDLE4.get();
        }
        return HalfLifeSounds.ICHY_IDLE1.get();
    }


    public SoundEvent getBiteSound() {
        switch (RandomSource.create().nextInt(1, 4)) {
            case 1:
                return HalfLifeSounds.ICHY_BITE1.get();
            case 2:
                return HalfLifeSounds.ICHY_BITE2.get();
        }
        return HalfLifeSounds.LEECH_BITE3.get();
    }

    public static final EntityDataAccessor<Integer> MOUTH_OP = SynchedEntityData.defineId(Shark.class, EntityDataSerializers.INT); // -1 to 1
    public static final EntityDataAccessor<Integer> TITL_UD = SynchedEntityData.defineId(Shark.class, EntityDataSerializers.INT); // -2 to 2
    public static final EntityDataAccessor<Integer> TITL_RL_NUM = SynchedEntityData.defineId(Shark.class, EntityDataSerializers.INT); // 1 to 3
    public static final EntityDataAccessor<Integer> TITL_RL = SynchedEntityData.defineId(Shark.class, EntityDataSerializers.INT); // -1 to 1
    public static final EntityDataAccessor<Integer> ID_SPECIAL = SynchedEntityData.defineId(Shark.class, EntityDataSerializers.INT);

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(MOUTH_OP, 0);
        this.entityData.define(TITL_UD, 0);
        this.entityData.define(TITL_RL, 0);
        this.entityData.define(TITL_RL_NUM, 1);
        this.entityData.define(ID_SPECIAL, 0);
    }

    public int getMouthPose() {
        return this.entityData.get(MOUTH_OP);
    }

    public int getTiltRL() {
        return this.entityData.get(TITL_RL);
    }

    public int getTiltRLNUM() {
        return this.entityData.get(TITL_RL_NUM);
    }

    public int getTiltUD() {
        return this.entityData.get(TITL_UD);
    }

    public void setAnimationPose(int mouth, int tilt_ud, int tilt_rl, int tilt_rlnum) {
        this.entityData.set(MOUTH_OP, mouth);
        this.entityData.set(TITL_RL, tilt_rl);
        this.entityData.set(TITL_UD, tilt_ud);
        this.entityData.set(TITL_RL_NUM, tilt_rlnum);
    }


    public int gettexture() {
        return this.entityData.get(ID_SPECIAL);
    }

    protected void settexture(int texture) {
        this.entityData.set(ID_SPECIAL, texture);
    }

    public void addAdditionalSaveData(CompoundTag p_33619_) {
        super.addAdditionalSaveData(p_33619_);
        p_33619_.putInt("Textur", this.gettexture());
    }

    public void readAdditionalSaveData(CompoundTag p_33607_) {
        this.settexture(p_33607_.getInt("Textur"));
        super.readAdditionalSaveData(p_33607_);
    }


    public void setMouthPose(int mouth) {
        this.setAnimationPose(mouth, this.getTiltUD(), this.getTiltRL(), this.getTiltRLNUM());
    }

    public void setTitlUd(int titlud) {
        this.setAnimationPose(this.getMouthPose(), titlud, this.getTiltRL(), this.getTiltRLNUM());
    }

    public void setTitlRl(int titlrl, int titlrlNum) {
        this.setAnimationPose(this.getMouthPose(), this.getTiltUD(), titlrl, titlrlNum);
    }

    public void randomtitlRl() {
        int i = RandomSource.create().nextInt(-1, 2);
        int j = RandomSource.create().nextInt(1, 4);
        if (i == 0) i = RandomSource.create().nextInt(-1, 2);
        this.setTitlRl(i, j);
    }

    public void resettitlRl() {
        this.setTitlRl(0, 1);
    }

    public void updatetitlUD() {
        int i = 0;
        Float y = (float) this.getLookAngle().y();
        if (y > 0.4) i = 1;
        if (y > 0.25) i = 2;
        if (y < -0.3) i = -1;
        if (y < -0.5) i = -2;
        int s = Mth.sign(i);
        float f = (float) this.getDeltaMovement().normalize().y;
        int j = Mth.sign(f);
        if ((s != j) || (Math.abs(f) < 0.1) || (this.getwateremountabove() < 2 && s > 0) || (this.getwateremountbelow() < 2 && s < 0))
            i = 0;


        this.setTitlUd(i);
    }


    @Override
    public boolean doHurtTarget(Entity pEntity) {
        this.randomtitlRl();
        return super.doHurtTarget(pEntity);
    }

    @Override
    public boolean ConfigurabledoHurtTarget(Entity entity, float disablechance, float attack_modifier, float knockback_modifier, @Nullable MobEffect effect, int duration, boolean visible) {
        if (RandomSource.create().nextFloat() < 0.2f) this.randomtitlRl();
        return super.ConfigurabledoHurtTarget(entity, disablechance, attack_modifier, knockback_modifier, effect, duration, visible);
    }

    @Override
    public void aiStep() {
        super.aiStep();
        tickMouth(this.mouth);

        if (this.tickCount % 4 == 0 && this.getwateremountbelow() > 0 && !this.isInWaterOrBubble()) {
            this.setDeltaMovement(this.getDeltaMovement().add(0, -0.2f, 0));
            if (this.random.nextFloat() < 0.1f) BrainUtils.clearMemory(this, MemoryModuleType.WALK_TARGET);
        }

        if (this.tickCount % 30 == 0) {
            this.resettitlRl();
        }

        if (this.tickCount % 10 == 0) this.updatetitlUD();

        if (!this.isInWaterOrBubble() & this.isOnGround() && this.tickCount % 13 == 0) {
            this.setTitlUd(RandomSource.create().nextInt(-2, 3));
            this.randomtitlRl();
            if (this.getTiltRL() > 0) this.randomtitlRl();
        }


        if (this.attackbehaviour == 1) {
            if (this.tickCount % 5 == 0) {
                List<Entity> list = this.level.getEntities(this, this.mouth.getBoundingBox(), obj -> !(obj instanceof Shark));
                for (Entity entity : list) {
                    if (entity instanceof LivingEntity living) {
                        this.doHurtTarget(living);
                        this.playSound(this.getBiteSound(), this.getSoundVolume(), this.getVoicePitch());
                    }
                }
            } else if (this.tickCount % 3 == 0) this.playAgroSound(this.getSoundVolume() * 0.5f);
        }

    }


    @Override
    protected SoundEvent getDeathSound() {
        switch (RandomSource.create().nextInt(1, 5)) {
            case 1:
                return HalfLifeSounds.ICHY_DIE1.get();
            case 2:
                return HalfLifeSounds.ICHY_DIE2.get();
            case 3:
                return HalfLifeSounds.ICHY_DIE3.get();
            case 4:
                return HalfLifeSounds.ICHY_DIE4.get();
        }
        return HalfLifeSounds.ICHY_IDLE1.get();
    }


    @Override
    protected SoundEvent getHurtSound(DamageSource pDamageSource) {
        switch (RandomSource.create().nextInt(1, 5)) {
            case 1:
                return HalfLifeSounds.ICHY_PAIN1.get();
            case 2:
                return HalfLifeSounds.ICHY_PAIN2.get();
            case 3:
                return HalfLifeSounds.ICHY_PAIN3.get();
            case 4:
                return HalfLifeSounds.ICHY_PAIN4.get();
        }
        return HalfLifeSounds.ICHY_IDLE1.get();
    }


    @Override
    public boolean hurt(DamageSource pSource, float pAmount) {

        if (this.attackbehaviour == 1 && RandomSource.create().nextFloat() < 0.1f) {
            BrainUtils.clearMemory(this, MemoryModuleType.WALK_TARGET);
            this.attackbehaviour = 0;
        }


        return super.hurt(pSource, pAmount);
    }

    public Shark(EntityType type, Level level) {
        super(type, level);
        this.xpReward = 20;
        this.setPathfindingMalus(BlockPathTypes.WATER, 0.0F);
        this.setPathfindingMalus(BlockPathTypes.WATER_BORDER, 0.0F);
        this.mouth = new SharkPart(this, "mouth", 1F, 1f);
        this.parts = new SharkPart[]{
                this.mouth
        };
        this.setId(ENTITY_COUNTER.getAndAdd(this.parts.length + 1) + 1);
    }

    @Override
    public void setId(int pId) {
        super.setId(pId);
        for (int i = 0; i < this.parts.length; i++) // Forge: Fix MC-158205: Set part ids to successors of parent mob id
            this.parts[i].setId(pId + i + 1);
    }


    private void tickMouth(SharkPart pPart) {
        pPart.moveTo(this.getX(), this.getY() + 0.5, this.getZ());
        double yrot = this.yBodyRot / 180 * Math.PI;
        double d1 = Math.sin(yrot);
        double d2 = Math.cos(yrot);
        double d3 = 0.5;
        switch (this.getTiltUD()) {
            case -2:
                d3 = -0.8d;
                break;
            case -1:
                d3 = -0.2d;
                break;
            case 0:
                d3 = 0.5;
                break;
            case 1:
                d3 = 1d;
                break;
            case 2:
                d3 = 1.5d;
                break;
        }
        pPart.moveTo(this.getX() - 2.1 * d1, this.getY() + d3, this.getZ() + 2.1 * d2);
    }


    public static AttributeSupplier setAttributes() {
        return Monster.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 150D)
                .add(Attributes.ATTACK_DAMAGE, 8.5f)
                .add(Attributes.ATTACK_SPEED, 3.0f)
                .add(Attributes.ATTACK_KNOCKBACK, 0.7f)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.5f)
                .add(Attributes.MOVEMENT_SPEED, 0.15f).build();
    }


    public int getwateremountbelow() {
        int i = 0;
        for (int j = 1; j < 20; j++) {
            BlockPos pos = new BlockPos(this.blockPosition().getX(), this.blockPosition().getY() - j, this.blockPosition().getZ());
            BlockState blockstate = this.level.getBlockState(pos);
            if (!blockstate.is(Blocks.WATER)) return i;
            i++;
        }
        return i;
    }

    public int getwateremountabove() {
        int i = 0;
        for (int j = 1; j < 20; j++) {
            BlockPos pos = new BlockPos(this.blockPosition().getX(), this.blockPosition().getY() + j, this.blockPosition().getZ());
            BlockState blockstate = this.level.getBlockState(pos);
            if (!blockstate.is(Blocks.WATER)) return i;
            i++;
        }
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

        this.setMouthPose(this.attackbehaviour == 1 ? 1 : 0);

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

        } else if (!this.onGround) {
            this.setDeltaMovement(this.getDeltaMovement().x(), -0.1f, this.getDeltaMovement().z());
        } else if (this.getTarget() != null) this.attackbehaviour = 1;


    }


    @Override
    public List<ExtendedSensor<Shark>> getSensors() {
        return ObjectArrayList.of(
                new HurtBySensor<>(),
                new SmellSensor<>(),
                new NearbyPlayersSensor<>(),
                new NearbyBlocksSensor<Shark>().setRadius(16D, 16D).setPredicate((state, entity) -> state.is(Blocks.WATER)),
                new NearbyLivingEntitySensor<Shark>().setRadius(32, 32)
                        .setPredicate((target, entity) ->
                                (target instanceof Player ||
                                        target.getType().is(HLTags.EntityTypes.FACTION_COMBINE) ||
                                        target.getType().getCategory().getName().equals("axolotls") ||
                                        target instanceof IronGolem ||
                                        target instanceof AbstractVillager ||
                                        target instanceof HalfLifeNeutral) && target.isInWaterOrBubble() && !(target instanceof Shark)
                        ));
    }


    @Override
    public BrainActivityGroup<Shark> getCoreTasks() { // These are the tasks that run all the time (usually)
        return BrainActivityGroup.coreTasks(
                new LookAtTarget<>(),
                new MoveToWalkTarget<>().startCondition(entity -> !this.isInWaterOrBubble() && this.isOnGround())
        );
    }

    @Override
    public BrainActivityGroup<Shark> getIdleTasks() { // These are the tasks that run when the mob isn't doing anything else (usually)
        return BrainActivityGroup.idleTasks(
                new FirstApplicableBehaviour<Shark>(
                        new CustomBehaviour<>(entity -> BrainUtils.clearMemory(this, MemoryModuleType.WALK_TARGET)).cooldownFor(entity -> 150),
                        new CustomBehaviour<>(entity -> this.attackbehaviour = 0).startCondition(entity -> this.attackbehaviour != 0),
                        new TargetOrRetaliate<>(),
                        new SetPlayerLookTarget<>(),
                        new SetRandomLookTarget<>(),
                        new SetBlockToWalkTargetNoInterest<>(),
                        new FirstApplicableBehaviour<>(
                                new PushToWalkTarget<>(true)
                                        .setXZMoveSpeedMod(entity -> 0.3f)
                                        .startCondition(entity -> (this.isInWaterOrBubble() || !this.isOnGround()) && this.getwateremountbelow() < 3)
                                        .cooldownFor(entity -> 1),
                                new PushToWalkTarget<>()
                                        .setXZMoveSpeedMod(entity -> 0.2f)
                                        .startCondition(entity -> this.isInWaterOrBubble())
                                        .cooldownFor(entity -> entity.getRandom().nextInt(1, 2)))
                )

        );

    }


    public void setAttackbehaviour(int attackbehaviour) {
        this.attackbehaviour = attackbehaviour;
        // this.setCustomName(Component.literal(""+this.attackbehaviour));
    }

    @Override
    public void jumpInFluid(FluidType type) {
    }

    @Override
    public BrainActivityGroup<Shark> getFightTasks() { // These are the tasks that handle fighting
        return BrainActivityGroup.fightTasks(
                new CustomBehaviour<>(entity -> BrainUtils.clearMemory(this, MemoryModuleType.WALK_TARGET)).cooldownFor(entity -> 260),
                new InvalidateAttackTarget<Shark>().invalidateIf((entity, target) -> (target instanceof Player pl && (pl.isCreative() || pl.isSpectator()))), //|| ((!target.isInWaterOrBubble() && target.isOnGround()) && (!this.isOnGround() || this.isInWaterOrBubble()))),
                new Retaliate<Shark>(),
                new SwitchTarget<>().startCondition(entity -> RandomSource.create().nextFloat() < 0.01f).cooldownFor(entity -> 400),
                new CustomBehaviour<>(entity -> BrainUtils.clearMemory(this, MemoryModuleType.WALK_TARGET)).cooldownFor(entity -> 80).startCondition(entity -> this.attackbehaviour == 0),
                new OneRandomBehaviour<>(
                        new SetBlockToWalkTargetNoInterest<Shark>().whenStarting(entity -> this.setAttackbehaviour(0)).startCondition(entity -> (this.isInWaterOrBubble() || !this.isOnGround())),
                        new SetBlockToWalkTargetNoInterest<Shark>().whenStarting(entity -> this.setAttackbehaviour(0)).startCondition(entity -> (this.isInWaterOrBubble() || !this.isOnGround())),
                        new SetBlockToWalkTargetNoInterest<Shark>().whenStarting(entity -> this.setAttackbehaviour(0)),
                        new SetWalkTargetToAttackTargetIfNoWalkTarget<Shark>().whenStarting(entity -> this.setAttackbehaviour(1)),
                        new SetWalkTargetToAttackTargetIfNoWalkTarget<Shark>().whenStarting(entity -> this.setAttackbehaviour(1)),
                        new SetWalkTargetToAttackTargetIfNoWalkTarget<Shark>().whenStarting(entity -> this.setAttackbehaviour(1))  //RandomSource.create().nextInt(1, 3)))
                ),
                new SetWalkTargetToAttackTarget<>().whenStarting(entity -> this.setAttackbehaviour(1)).startCondition(entity -> this.distanceTo(HLperUtil.TargetOrThis(this)) > 24),
                new SetWalkTargetToAttackTarget<>().whenStarting(entity -> this.setAttackbehaviour(1)).startCondition(entity -> this.getLastHurtByMob() != null).cooldownFor(entity -> 600),
                new FirstApplicableBehaviour<>(
                        new PushToWalkTarget<>(true)
                                .setXZMoveSpeedMod(entity -> 0.3f)
                                .startCondition(entity -> (this.isInWaterOrBubble() || !this.isOnGround()) && this.attackbehaviour == 0 && this.getwateremountbelow() < 3)
                                .cooldownFor(entity -> 1),
                        new PushToWalkTarget<>()
                                .setXZMoveSpeedMod(entity -> 0.3f)
                                .startCondition(entity -> (this.isInWaterOrBubble() || !this.isOnGround()) && this.attackbehaviour == 0 )
                                .cooldownFor(entity -> 1),
                        new PushToWalkTargetDontStop<>()
                                .setXZMoveSpeedMod(entity -> 0.33f)
                                .startCondition(entity -> (this.isInWaterOrBubble() || !this.isOnGround()) && this.attackbehaviour != 0)
                                .cooldownFor(entity -> 1)
                ),
                new OneRandomBehaviour<>(
                        new ConfigurableAnimatableMeleeAttack<Shark>(9, 0.3f, 1.4f, 1.7f, null, 0, this.getBiteSound(), null) //this.getBiteSound(), this.getAttackGrowlSound())
                                .whenStarting(entity -> triggerAnim("onetime", "attack"))
                                .cooldownFor(entity -> random.nextInt(20, 50)),
                        new ConfigurableAnimatableMeleeAttack<Shark>(13, 0.3f, 1.4f, 1.7f, null, 0, this.getBiteSound(), null) //this.getBiteSound(), this.getAttackGrowlSound())
                                .whenStarting(entity -> triggerAnim("onetime", "bigattack"))
                                .cooldownFor(entity -> random.nextInt(20, 50)),
                        new DoubleMeleeAttack<Shark>(15, 10, 0, 1.2f, 1.7f, null, 0, this.getBiteSound(), null) //this.getDoubleAttackSound())
                                .whenStarting(entity -> triggerAnim("onetime", "twice"))
                                .cooldownFor(entity -> random.nextInt(20, 50))
                ).cooldownFor(entity -> 20).startCondition(entity -> this.attackbehaviour != 0)
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
        return 0.5f;

    }


    public void recreateFromPacket(ClientboundAddEntityPacket pPacket) {
        super.recreateFromPacket(pPacket);
        this.yBodyRot = 0.0F;
        this.yBodyRotO = 0.0F;
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
            return 0.5;
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
        controllerRegistrar.add(new AnimationController<>(this, "onetime", state -> PlayState.STOP)
                .triggerableAnim("attack", RawAnimation.begin().then("animation.shark.attack", Animation.LoopType.PLAY_ONCE)) // 0.4583
                .triggerableAnim("bigattack", RawAnimation.begin().then("animation.shark.attackbig", Animation.LoopType.PLAY_ONCE)) // 0.5833
                .triggerableAnim("twice", RawAnimation.begin().then("animation.shark.attacktwice", Animation.LoopType.PLAY_ONCE))); // 0.5 + 0.7083
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
            return PlayState.CONTINUE;
        }
        if (j == -1) {
            tAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.shark.right" + i, Animation.LoopType.LOOP));
            return PlayState.CONTINUE;
        }

        tAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.shark.straightrl", Animation.LoopType.LOOP));
        return PlayState.CONTINUE;

    }

    private <T extends GeoAnimatable> PlayState tiltud(AnimationState<T> tAnimationState) {
        int j = this.getTiltUD();
        switch (j) {
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
        switch (i) {
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


    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor pLevel, DifficultyInstance pDifficulty, MobSpawnType pReason, @Nullable SpawnGroupData pSpawnData, @Nullable CompoundTag pDataTag) {
        if (random.nextFloat() < 0.02f) this.settexture(random.nextInt(0, 3));
        return super.finalizeSpawn(pLevel, pDifficulty, pReason, pSpawnData, pDataTag);
    }


    /* public static boolean checkSharkSpawnRules(EntityType<Shark> pType, ServerLevelAccessor pServerLevel, MobSpawnType pMobSpawnType, BlockPos pPos, RandomSource pRandom) {
       BlockPos pos1 = pPos.offset(new Vec3i(1,-1,1));
        BlockPos pos2 = pPos.offset(new Vec3i(0,-1,1));
        BlockPos pos3 = pPos.offset(new Vec3i(-1,-1,1));
        BlockPos pos4 = pPos.offset(new Vec3i(1,-1,-1));
        BlockPos pos5 = pPos.offset(new Vec3i(1,-1,0));
        BlockPos pos6 = pPos.offset(new Vec3i(-1,-1,-1));
        BlockPos pos7 = pPos.offset(new Vec3i(-1,-1,1));
        BlockPos pos8 = pPos.offset(new Vec3i(0,-1,-1));


        boolean flag0 = pServerLevel.getFluidState(pPos.below()).is(FluidTags.WATER);
        boolean flag1 = pServerLevel.getFluidState(pos1).is(FluidTags.WATER);
        boolean flag2 = pServerLevel.getFluidState(pos2).is(FluidTags.WATER);
        boolean flag3 = pServerLevel.getFluidState(pos3).is(FluidTags.WATER);
        boolean flag4 = pServerLevel.getFluidState(pos4).is(FluidTags.WATER);
        boolean flag5 = pServerLevel.getFluidState(pos5).is(FluidTags.WATER);
        boolean flag6 = pServerLevel.getFluidState(pos6).is(FluidTags.WATER);
        boolean flag7 = pServerLevel.getFluidState(pos7).is(FluidTags.WATER);
        boolean flag8 = pServerLevel.getFluidState(pos8).is(FluidTags.WATER);

        boolean flag9 = pServerLevel.getFluidState(pPos.below()).is(FluidTags.WATER);
        boolean flag10 = pServerLevel.getFluidState(pPos.below().below()).is(FluidTags.WATER);
        boolean flag11 = pServerLevel.getFluidState(pPos.below(4)).is(FluidTags.WATER);
        boolean flag12 = pServerLevel.getFluidState(pPos.below(7)).is(FluidTags.WATER);

        return pRandom.nextInt(20) == 0 && flag0 && flag1 && flag2 && flag3 && flag4 && flag5 && flag6 && flag7 && flag8 && flag9 && flag10 && flag11 && !pServerLevel.getDifficulty().equals(Difficulty.PEACEFUL) && flag12 && isDarkEnoughToSpawn(pServerLevel, pPos, pRandom);
    } */


    public static boolean checkSharkSpawnRules(EntityType<? extends LivingEntity> type, ServerLevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource randomSource) {
        return true;
    }

  //  level.getFluidState(pos).is(FluidTags.WATER) && pos.getY() < level.getSeaLevel() - 25
}
