package net.sirkotok.half_life_mod.entity.mob.mob_procedural_effect;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;

import net.minecraft.world.entity.monster.Monster;

import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;

import net.minecraft.world.phys.Vec3;
import net.sirkotok.half_life_mod.entity.base.HalfLifeMonster;

import net.sirkotok.half_life_mod.entity.brain.behaviour.Retaliate;

import net.sirkotok.half_life_mod.entity.mob.mob_geckolib.custom.Antlion;

import net.sirkotok.half_life_mod.entity.mob.mob_procedural_effect.parts.HLHydraSegment;
import net.sirkotok.half_life_mod.entity.particle.HalfLifeParticles;
import net.sirkotok.half_life_mod.misc.gamerules.HalfLifeGameRules;

import net.sirkotok.half_life_mod.misc.util.HLperUtil;
import net.sirkotok.half_life_mod.misc.util.InfightingUtil;
import net.tslat.smartbrainlib.api.SmartBrainOwner;
import net.tslat.smartbrainlib.api.core.BrainActivityGroup;
import net.tslat.smartbrainlib.api.core.SmartBrainProvider;
import net.tslat.smartbrainlib.api.core.behaviour.FirstApplicableBehaviour;
import net.tslat.smartbrainlib.api.core.behaviour.custom.look.LookAtTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.misc.CustomBehaviour;

import net.tslat.smartbrainlib.api.core.behaviour.custom.target.InvalidateAttackTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.target.SetPlayerLookTarget;

import net.tslat.smartbrainlib.api.core.behaviour.custom.target.TargetOrRetaliate;
import net.tslat.smartbrainlib.api.core.sensor.ExtendedSensor;

import net.tslat.smartbrainlib.api.core.sensor.vanilla.HurtBySensor;
import net.tslat.smartbrainlib.api.core.sensor.vanilla.NearbyLivingEntitySensor;
import net.tslat.smartbrainlib.api.core.sensor.vanilla.NearbyPlayersSensor;

import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;

import java.util.List;

public class HLHydra extends HalfLifeMonster implements SmartBrainOwner<HLHydra> {

    @Override
    public net.minecraftforge.entity.PartEntity<?>[] getParts() { return this.halfsegments; }

    private final HLHydraSegment end;
    private final HLHydraSegment s11;
    private final HLHydraSegment s12;
    private final HLHydraSegment s21;
    private final HLHydraSegment s22;
    private final HLHydraSegment s31;
    private final HLHydraSegment s32;
    private final HLHydraSegment s41;
    private final HLHydraSegment s42;
    private final HLHydraSegment s51;
    private final HLHydraSegment s52;
    private final HLHydraSegment[] halfsegments;


    public HLHydra(EntityType type, Level level) {
        super(type, level);
        this.setNoGravity(true);
        this.noPhysics = true;
        this.end = new HLHydraSegment(this, "end", cube/2,cube/2);
        this.s11 = new HLHydraSegment(this, "s11", cube,cube);
        this.s12 = new HLHydraSegment(this, "s12",  cube,cube);
        this.s21 = new HLHydraSegment(this, "s21",  cube,cube);
        this.s22 = new HLHydraSegment(this, "s22",  cube,cube);
        this.s31 = new HLHydraSegment(this, "s31",  cube,cube);
        this.s32 = new HLHydraSegment(this, "s32",  cube,cube);
        this.s41 = new HLHydraSegment(this, "s41",  cube,cube);
        this.s42 = new HLHydraSegment(this, "s42",  cube,cube);
        this.s51 = new HLHydraSegment(this, "s51",  cube,cube);
        this.s52 = new HLHydraSegment(this, "s52",  cube,cube);
        this.halfsegments = new HLHydraSegment[] {
                this.s11,
                this.s12,
                this.s21,
                this.s22,
                this.s32,
                this.s31,
                this.s41,
                this.s42,
                this.s51,
                this.s52,
                this.end
        };
        this.setId(ENTITY_COUNTER.getAndAdd(this.halfsegments.length + 1) + 1); // Forge: Fix MC-158205: Make sure part ids are successors of parent mob id

    }

    @Override
    public void setId(int pId) {
        super.setId(pId);
        for (int i = 0; i < this.halfsegments.length; i++) // Forge: Fix MC-158205: Set part ids to successors of parent mob id
            this.halfsegments[i].setId(pId + i + 1);
    }



    public static final EntityDataAccessor<Boolean> IS_ANGRY = SynchedEntityData.defineId(Antlion.class, EntityDataSerializers.BOOLEAN);

    public static final EntityDataAccessor<Vector3f> START_POS = SynchedEntityData.defineId(Antlion.class, EntityDataSerializers.VECTOR3);
    public static final EntityDataAccessor<Vector3f> S1_END = SynchedEntityData.defineId(Antlion.class, EntityDataSerializers.VECTOR3);
    public static final EntityDataAccessor<Vector3f> S1_START = SynchedEntityData.defineId(Antlion.class, EntityDataSerializers.VECTOR3);
    public static final EntityDataAccessor<Vector3f> S2_END = SynchedEntityData.defineId(Antlion.class, EntityDataSerializers.VECTOR3);
    public static final EntityDataAccessor<Vector3f> S3_END = SynchedEntityData.defineId(Antlion.class, EntityDataSerializers.VECTOR3);
    public static final EntityDataAccessor<Vector3f> S4_END = SynchedEntityData.defineId(Antlion.class, EntityDataSerializers.VECTOR3);
    public static final EntityDataAccessor<Vector3f> S5_END = SynchedEntityData.defineId(Antlion.class, EntityDataSerializers.VECTOR3);
    public static final EntityDataAccessor<Vector3f> DESTINATION = SynchedEntityData.defineId(Antlion.class, EntityDataSerializers.VECTOR3);
    public static final EntityDataAccessor<Float> SEG_LENGTH = SynchedEntityData.defineId(Antlion.class, EntityDataSerializers.FLOAT);



    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DESTINATION, new Vector3f(3, 3, 3));
        this.entityData.define(S5_END, new Vector3f(0, 10f, 0));
        this.entityData.define(S4_END, new Vector3f(0, 8, 0));
        this.entityData.define(S3_END, new Vector3f(0, 6f, 0));
        this.entityData.define(S2_END, new Vector3f(0, 4, 0));
        this.entityData.define(S1_END, new Vector3f(0, 2f, 0));
        this.entityData.define(S1_START, new Vector3f(0, 0, 0));
        this.entityData.define(START_POS, new Vector3f(0, 0, 0));
        this.entityData.define(IS_ANGRY, false);
        this.entityData.define(SEG_LENGTH, 2f);
    }

    float cube = 0.8f;

    @Override
    public boolean isMultipartEntity() {
        return true;
    }

    public void recreateFromPacket(ClientboundAddEntityPacket pPacket) {
        super.recreateFromPacket(pPacket);
        this.yBodyRot = 0.0F;
        this.yBodyRotO = 0.0F;
    }

    public Vec3 getStart(){
        return new Vec3(this.entityData.get(START_POS));
    }
    public void setStart(Vec3 vec3){
        this.entityData.set(START_POS, vec3.toVector3f());
    }
    public Vec3 getDestination(){
        return new Vec3(this.entityData.get(DESTINATION));
    }

    public void setDestination(Vec3 vec3){
        this.entityData.set(DESTINATION, vec3.toVector3f());
    }

    public Vec3 getSegmentStart(int i){
        switch(i){
            case 1: return new Vec3(this.entityData.get(S1_START));
            case 2: return new Vec3(this.entityData.get(S1_END));
            case 3: return new Vec3(this.entityData.get(S2_END));
            case 4: return new Vec3(this.entityData.get(S3_END));
            case 5: return new Vec3(this.entityData.get(S4_END));
        }
        return new Vec3(0,0,0);
    }

    public Vec3 getSegmentEnd(int i){
        switch(i){
            case 1: return new Vec3(this.entityData.get(S1_END));
            case 2: return new Vec3(this.entityData.get(S2_END));
            case 3: return new Vec3(this.entityData.get(S3_END));
            case 4: return new Vec3(this.entityData.get(S4_END));
            case 5: return new Vec3(this.entityData.get(S5_END));
        }
        return new Vec3(this.entityData.get(DESTINATION));
    }


    public void SetEnd(Vec3 vec){
        Vector3f vec3f = vec.toVector3f();
        this.entityData.set(S5_END, vec3f);
    }
    public void SetSegmentStart(int i, Vec3 vec){
        Vector3f vec3f = vec.toVector3f();
        switch(i){
            case 1: this.entityData.set(S1_START, vec3f); break;
            case 2: this.entityData.set(S1_END, vec3f); break;
            case 3: this.entityData.set(S2_END, vec3f); break;
            case 4: this.entityData.set(S3_END, vec3f); break;
            case 5: this.entityData.set(S4_END, vec3f); break;
        }
    }







    public void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        this.setStart(new Vec3(pCompound.getFloat("Startx"), pCompound.getFloat("Starty"), pCompound.getFloat("Startz")));
        int i = 1;
        while (i < 6) {
        this.SetSegmentStart(i, new Vec3(pCompound.getFloat("s"+i+"x"), pCompound.getFloat("s"+i+"y"), pCompound.getFloat("s"+i+"z")));
        i++;
        }
        this.SetEnd(new Vec3(pCompound.getFloat("Endx"), pCompound.getFloat("Endy"), pCompound.getFloat("Endz")));
    }

    public void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);

        pCompound.putFloat("Startx", (float)this.getStart().x());
        pCompound.putFloat("Starty", (float)this.getStart().y());
        pCompound.putFloat("Startz", (float)this.getStart().z());

        int i = 1;
        while (i < 6) {
            pCompound.putFloat("s"+i+"x", (float)this.getSegmentStart(i).x());
            pCompound.putFloat("s"+i+"y", (float)this.getSegmentStart(i).y());
            pCompound.putFloat("s"+i+"z", (float)this.getSegmentStart(i).z());
            i++;
        }
        pCompound.putFloat("Endx", (float)this.getSegmentEnd(5).x());
        pCompound.putFloat("Endy", (float)this.getSegmentEnd(5).y());
        pCompound.putFloat("Endz", (float)this.getSegmentEnd(5).z());


    }





    public float getlength(){
        return this.entityData.get(SEG_LENGTH);
    }

    public void SetRecursiveSegmentStart(int i){
        Vec3 endnew = getSegmentEnd(i);
        Vec3 startold = getSegmentStart(i);
        Vec3 startnew = (startold.subtract(endnew).normalize().scale((double) getlength())).add(endnew);
        SetSegmentStart(i, startnew);
    }

    public void SetRecursiveStartAll() {
        int i = 1;
        while (i < 6) {
        SetRecursiveSegmentStart(i);
        i++;
        }
    }

    public void GoDown() {
        Vec3 down = this.getSegmentStart(1);
        int i = 1;
        while (i < 6) {
        this.SetSegmentStart(i, this.getSegmentStart(i).subtract(down));
        i++;
        }
        this.SetEnd(this.getSegmentEnd(5).subtract(down));
    }



    private void tickHalfSegment(HLHydraSegment segmeng, int segmentnumber, float percent) {
        double x = getStart().x()+this.getSegmentStart(segmentnumber).x()+(-this.getSegmentStart(segmentnumber).x()+this.getSegmentEnd(segmentnumber).x())*percent;
        double y = getStart().y()+this.getSegmentStart(segmentnumber).y()+(-this.getSegmentStart(segmentnumber).y()+this.getSegmentEnd(segmentnumber).y())*percent;
        double z = getStart().z()+this.getSegmentStart(segmentnumber).z()+(-this.getSegmentStart(segmentnumber).z()+this.getSegmentEnd(segmentnumber).z())*percent;
        segmeng.moveTo(x, y, z);
    }

    private void tickEnd(HLHydraSegment segmeng) {
        int segmentnumber = 5;
        float percent = 1;
        double x = getStart().x()+this.getSegmentStart(segmentnumber).x()+(-this.getSegmentStart(segmentnumber).x()+this.getSegmentEnd(segmentnumber).x())*percent;
        double y = getStart().y()+this.getSegmentStart(segmentnumber).y()+(-this.getSegmentStart(segmentnumber).y()+this.getSegmentEnd(segmentnumber).y())*percent;
        double z = getStart().z()+this.getSegmentStart(segmentnumber).z()+(-this.getSegmentStart(segmentnumber).z()+this.getSegmentEnd(segmentnumber).z())*percent;
        segmeng.moveTo(x, y, z);
    }

    private void tickAllHalfSegments(){
        int i = 1;
        int k = 0;
        for (HLHydraSegment seg : this.halfsegments) {
            if (!seg.name.equals("end")) {
            if (k != i) {
                k++;
                tickHalfSegment(seg, i, 0.33f);
            } else {
                tickHalfSegment(seg, i, 0.66f);
                i++;
            }
        }
        }
    }



    private void addAllParticles(){
        int i = 1;
        int k = 0;
        int e = 10;
        while (i<6) {
            if (k != i*e) {
                k++;
                addParticleForSegment(i, (float) 1/e * (i*e-k));
            } else {
                addParticleForSegment(i, 0);
                i++;
            }
        }
    }
    private void addParticleForSegment(int segmentnumber, float percent) {
        double x = getStart().x()+this.getSegmentStart(segmentnumber).x()+(-this.getSegmentStart(segmentnumber).x()+this.getSegmentEnd(segmentnumber).x())*percent;
        double y = getStart().y()+this.getSegmentStart(segmentnumber).y()+(-this.getSegmentStart(segmentnumber).y()+this.getSegmentEnd(segmentnumber).y())*percent;
        double z = getStart().z()+this.getSegmentStart(segmentnumber).z()+(-this.getSegmentStart(segmentnumber).z()+this.getSegmentEnd(segmentnumber).z())*percent;
        this.level.addParticle(HalfLifeParticles.STAT_GLOW.get(), x, y, z, this.hurtMarked ? 2 : 4, 0.1d, 0);
    }


    public boolean canBeCollidedWith() {
        return false;
    }

 /*
    public void AddAllParticles() {
        int i = 1;
        while (i < 6) {
            addParticleForSegment(i);
            i++;
        }
    }
    private void addParticleForSegment(int i) {
        Vec3 start = this.getStart();
        Vec3 segstart = start.add(this.getSegmentStart(i));
        Vec3 end = this.getSegmentEnd(i);
        level.addParticle(HalfLifeParticles.GRAV_GUN_BLUE_LIGHTNING.get(), segstart.x(), segstart.y(), segstart.z(), end.x(), end.y(), end.z());
    } */


    public static AttributeSupplier setAttributes() {
        return Monster.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 5D)
                .add(Attributes.ATTACK_DAMAGE, 4f)
                .add(Attributes.ATTACK_SPEED, 3.0f)
                .add(Attributes.ATTACK_KNOCKBACK, 1f)
                .add(Attributes.MOVEMENT_SPEED, 0f).build();
    }


    private void SetEndFromDestination(){
        Vec3 destination = this.getDestination();
        Vec3 start = this.getStart();
        Vec3 currentEnd = this.getSegmentEnd(5);
        Vec3 relativeDest = destination.subtract(start);
        Vec3 newEnd = (relativeDest.subtract(currentEnd).normalize().scale(0.2f * (this.attackbehaviour == 1 ? 1.7f : 1))).add(currentEnd);
        SetEnd(newEnd);
    }

    private void SetRandomDestination(){
        this.setDestination(this.getStart().add(random.nextFloat()*10*(random.nextBoolean() ? 1 : -1), random.nextFloat()*10, random.nextFloat()*10*(random.nextBoolean() ? 1 : -1)));
    }

    private void SetUPDestination(){
        this.setDestination(this.getStart().add(0, 6, 0));
    }

    public void aiStep() {
        super.aiStep();
        if (this.isDeadOrDying()) return;
        tickAllHalfSegments();
        tickEnd(this.end);
        if (!this.level.isClientSide()) {
            if (this.tickCount % 5 == 0) {
                List<Entity> list = this.level.getEntities(this, this.end.getBoundingBox(), obj -> !(obj instanceof HLHydra));
                for (Entity entity : list) {
                    if (entity instanceof LivingEntity living) {
                        this.doHurtTarget(living);
                       if (this.random.nextFloat() < 0.5) {
                        this.attackbehaviour = 0;
                        }
                    }
                }
            }
        }
    }

    public boolean startRiding(Entity pEntity, boolean pForce) {
        return false;
    }
    public void push(Entity pEntity) {
    }

    public float getPickRadius() {
        return 0.0F;
    }
    private int attackbehaviour = 0;

    @Override
    public void tick() {
        super.tick();



        this.moveTo(this.getStart());

        SetEndFromDestination();
        SetRecursiveStartAll();
        GoDown();

        if (this.tickCount % 2 == 0) addAllParticles();

        if (this.level.getGameRules().getRule(HalfLifeGameRules.HALF_LIFE_AI_IS_ON).get()) {
            if (this.attackbehaviour == 0 && this.tickCount % 60 == 0) this.SetRandomDestination();
            if (this.attackbehaviour == 1) this.setDestination(HLperUtil.TargetOrThis(this).position());
        }


    }

    @Override
    protected Brain.Provider<?> brainProvider() {
        return new SmartBrainProvider<>(this);
    }



    @Override
    protected void customServerAiStep() {
        if (!this.level.getGameRules().getRule(HalfLifeGameRules.HALF_LIFE_AI_IS_ON).get()) return;
        tickBrain(this);
    }
    @Override
    public List<ExtendedSensor<HLHydra>> getSensors() {
        return ObjectArrayList.of(
                new HurtBySensor<>(),
                new NearbyPlayersSensor<>(),
                new NearbyLivingEntitySensor<HLHydra>().setRadius(6, 6)
                        .setPredicate((target, entity) ->
                                InfightingUtil.nonfactionSpecific(target)
                        ));
    }


    @Override
    public boolean hurt(DamageSource pSource, float pAmount) {
        this.attackbehaviour = 1;
        return super.hurt(pSource, pAmount);
    }

    @Override
    public BrainActivityGroup<HLHydra> getCoreTasks() { // These are the tasks that run all the time (usually)
        return BrainActivityGroup.coreTasks(
                new LookAtTarget<>()
        );
    }

    @Override
    public BrainActivityGroup<HLHydra> getIdleTasks() { // These are the tasks that run when the mob isn't doing anything else (usually)
        return BrainActivityGroup.idleTasks(
                new FirstApplicableBehaviour<HLHydra>(
                new TargetOrRetaliate<>(),
                new SetPlayerLookTarget<>(),
                new CustomBehaviour<>(entity -> this.attackbehaviour = 0).cooldownFor(entity -> 100)
        )
        );
    }

    @Override
    public BrainActivityGroup<HLHydra> getFightTasks() { // These are the tasks that handle fighting
        return BrainActivityGroup.fightTasks(
                new InvalidateAttackTarget<HLHydra>(), //|| ((!target.isInWaterOrBubble() && target.isOnGround()) && (!this.isOnGround() || this.isInWaterOrBubble()))),
                new Retaliate<HLHydra>(),
                new CustomBehaviour<>(entity -> this.attackbehaviour = this.random.nextFloat() < 0.3 ? 1 : 0).cooldownFor(entity -> 100)
              //  new CustomBehaviour<>(entity -> this.attackbehaviour = 1).cooldownFor(entity -> 100)
        );
    }


















    @Override
    public boolean isPickable() {
        return false;
    }

    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor pLevel, DifficultyInstance pDifficulty, MobSpawnType pReason, @Nullable SpawnGroupData pSpawnData, @Nullable CompoundTag pDataTag) {
        this.setStart(this.position());
        SetRandomDestination();
        SetEndFromDestination();
        SetRecursiveStartAll();
        GoDown();
        return super.finalizeSpawn(pLevel, pDifficulty, pReason, pSpawnData, pDataTag);
    }
}
