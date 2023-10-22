package net.sirkotok.half_life_mod.entity.mob_normal.custom;

import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.BodyRotationControl;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3f;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;

public class Barnacle extends PathfinderMob {

    private final BarnacleTongue tongue;
    private final BarnacleTongue[] tongues;
    @Override
    public boolean isMultipartEntity() {
        return true;
    }






    @Override
    public net.minecraftforge.entity.PartEntity<?>[] getParts() { return this.tongues; }











         private static final EntityDataAccessor<Boolean> ATTACKING =
            SynchedEntityData.defineId(Barnacle.class, EntityDataSerializers.BOOLEAN);
        protected static final EntityDataAccessor<Direction> DATA_ATTACH_FACE_ID = SynchedEntityData.defineId(Barnacle.class, EntityDataSerializers.DIRECTION);

        static final Vector3f FORWARD = Util.make(() -> {
            Vec3i vec3i = Direction.SOUTH.getNormal();
            return new Vector3f((float)vec3i.getX(), (float)vec3i.getY(), (float)vec3i.getZ());
        });



    public final AnimationState idleAnimationState = new AnimationState();
    private int idleAnimationTimeout = 0;
    public final AnimationState attackAnimationState = new AnimationState();
    public int attackAnimationTimeout = 0;
    public final AnimationState dyingAnimationState = new AnimationState();
    public int dyingAnimationTimeout = 0;


    private void setupAnimationStates() {
        if(this.idleAnimationTimeout <= 0) {
            this.idleAnimationTimeout = 60;
            this.idleAnimationState.start(this.tickCount);
        } else {
            --this.idleAnimationTimeout;
        }


        if (this.isDeadOrDying() && dyingAnimationTimeout<=0) {
           dyingAnimationTimeout = 25; // Length in ticks of your animation
            dyingAnimationState.start(this.tickCount);
        } else {
            --this.dyingAnimationTimeout;
        }

        if(this.isAttacking() && attackAnimationTimeout <= 0) {
            attackAnimationTimeout = 12; // Length in ticks of your animation
            attackAnimationState.start(this.tickCount);
        } else {
            --this.attackAnimationTimeout;
        }

        if(!this.isAttacking()) {
            attackAnimationState.stop();
        }


    }








        @Nullable
        private BlockPos clientOldAttachPosition;
        private int clientSideTeleportInterpolation;


        public Barnacle(EntityType<? extends Barnacle> pEntityType, Level pLevel) {
            super(pEntityType, pLevel);
            this.tongue = new BarnacleTongue(this, "tongue", 0.4F, 1f);
            this.xpReward = 5;
            this.tongues = new BarnacleTongue[] {
                    this.tongue
            };
        }



    private void tickPart(BarnacleTongue pPart) {
        pPart.moveTo(this.getX(), this.getY() - getairemount()+0.5, this.getZ());
    }




    public void aiStep() {
        super.aiStep();
        tickPart(this.tongue);
        this.tongue.setBoundingBox(new AABB(this.blockPosition().getX() + 0.3, this.blockPosition().getY(), this.blockPosition().getZ() + 0.3, this.blockPosition().getX()+0.7, this.blockPosition().getY() - getairemount(), this.blockPosition().getZ()+0.7));
        this.checkTongue(this.tongue.getBoundingBox());
    }


    private void checkTongue(AABB box) {
        List<Entity> list = this.level.getEntities(this, box);
        if (!list.isEmpty()) {
            for(int i = 0; i < list.size(); ++i) {
                Entity entity = list.get(i);
                if (entity instanceof LivingEntity) {
                    ((LivingEntity)entity).addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 5, 0, false, true), this);
                    break;
                }
            }
        }
    }


    public int getairemount() {
        int i = 0;
        for (int j = 0; j<20; j++){
            BlockPos pos = new BlockPos(this.blockPosition().getX(), this.blockPosition().getY() - j, this.blockPosition().getZ());
            BlockState blockstate = this.level.getBlockState(pos);
            if (!blockstate.isAir()) return i;
            i++;}



        return i;
    }






    public static AttributeSupplier setAttributes() {
        return Monster.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 10D)
                .add(Attributes.ATTACK_DAMAGE, 0f)
                .add(Attributes.ATTACK_SPEED, 0f)
                .add(Attributes.ATTACK_KNOCKBACK, 0f)
                .add(Attributes.MOVEMENT_SPEED, 0f).build();
    }



        protected Entity.MovementEmission getMovementEmission() {
            return Entity.MovementEmission.NONE;
        }

        public SoundSource getSoundSource() {
            return SoundSource.HOSTILE;
        }

        protected SoundEvent getAmbientSound() {
            return SoundEvents.SHULKER_AMBIENT;
        }


        public void playAmbientSound() {

                super.playAmbientSound();


        }

        protected SoundEvent getDeathSound() {
            return SoundEvents.SHULKER_DEATH;
        }

        protected SoundEvent getHurtSound(DamageSource pDamageSource) {
            return SoundEvents.SHULKER_HURT;
        }

        protected Direction CorrectDirection() {return Direction.UP;}

        protected void defineSynchedData() {
            super.defineSynchedData();
            this.entityData.define(DATA_ATTACH_FACE_ID, CorrectDirection());
            this.entityData.define(ATTACKING, false);
        }



    public void setAttacking(boolean attacking) {
        this.entityData.set(ATTACKING, attacking);
    }

    public boolean isAttacking() {
        return this.entityData.get(ATTACKING);
    }



        protected BodyRotationControl createBodyControl() {
            return new BarnacleBodyRotationControl(this);
        }

        /**
         * (abstract) Protected helper method to read subclass entity data from NBT.
         */
        public void readAdditionalSaveData(CompoundTag pCompound) {
            super.readAdditionalSaveData(pCompound);
            this.setAttachFace(Direction.from3DDataValue(pCompound.getByte("AttachFace")));


        }

        public void addAdditionalSaveData(CompoundTag pCompound) {
            super.addAdditionalSaveData(pCompound);
            pCompound.putByte("AttachFace", (byte)this.getAttachFace().get3DDataValue());

        }

        /**
         * Called to update the entity's position/logic.
         */
        public void tick() {
            super.tick();
            if (!this.level.isClientSide && !this.canStayAt(this.blockPosition(), this.getAttachFace())) {
                this.setHealth(0);
            }
            if(this.level.isClientSide()) {
                setupAnimationStates();
            }
        }



        protected AABB makeBoundingBox() {
            float f = getPhysicalPeek(0);
            Direction direction = this.getAttachFace().getOpposite();
            float f1 = this.getType().getWidth() / 2.0F;
            return getProgressAabb(direction, f).move(this.getX() - (double)f1, this.getY(), this.getZ() - (double)f1);
        }

        private static float getPhysicalPeek(float pPeek) {
            return 0.5F - Mth.sin((0.5F + pPeek) * (float)Math.PI) * 0.5F;
        }



        public static AABB getProgressAabb(Direction pDirection, float pDelta) {
            return getProgressDeltaAabb(pDirection, -1.0F, pDelta);
        }

        public static AABB getProgressDeltaAabb(Direction pDirection, float pDelta, float pDeltaO) {
            double d0 = (double)Math.max(pDelta, pDeltaO);
            double d1 = (double)Math.min(pDelta, pDeltaO);
            return (new AABB(BlockPos.ZERO)).expandTowards((double)pDirection.getStepX() * d0, (double)pDirection.getStepY() * d0, (double)pDirection.getStepZ() * d0).contract((double)(-pDirection.getStepX()) * (1.0D + d1), (double)(-pDirection.getStepY()) * (1.0D + d1), (double)(-pDirection.getStepZ()) * (1.0D + d1));
        }

        /**
         * Returns the Y Offset of this entity.
         */
        public double getMyRidingOffset() {
            EntityType<?> entitytype = this.getVehicle().getType();
            return !(this.getVehicle() instanceof Boat) && entitytype != EntityType.MINECART ? super.getMyRidingOffset() : 0.1875D - this.getVehicle().getPassengersRidingOffset();
        }

        public boolean startRiding(Entity pEntity, boolean pForce) {
            return false;
        }

        /**
         * Dismounts this entity from the entity it is riding.
         */
        public void stopRiding() {
            super.stopRiding();
        }

        @Nullable
        public SpawnGroupData finalizeSpawn(ServerLevelAccessor pLevel, DifficultyInstance pDifficulty, MobSpawnType pReason, @Nullable SpawnGroupData pSpawnData, @Nullable CompoundTag pDataTag) {
            this.setYRot(0.0F);
            this.yHeadRot = this.getYRot();
            this.setOldPosAndRot();
            return super.finalizeSpawn(pLevel, pDifficulty, pReason, pSpawnData, pDataTag);
        }


        public Vec3 getDeltaMovement() {
            return Vec3.ZERO;
        }

        public void setDeltaMovement(Vec3 pDeltaMovement) {
        }

        /**
         * Sets the x,y,z of the entity from the given parameters. Also seems to set up a bounding box.
         */
        public void setPos(double pX, double pY, double pZ) {
            BlockPos blockpos = this.blockPosition();
            if (this.isPassenger()) {
                super.setPos(pX, pY, pZ);
            } else {
                super.setPos((double)Mth.floor(pX) + 0.5D, (double)Mth.floor(pY + 0.5D), (double)Mth.floor(pZ) + 0.5D);
            }

            if (this.tickCount != 0) {
                BlockPos blockpos1 = this.blockPosition();
                if (!blockpos1.equals(blockpos)) {
                    this.hasImpulse = true;
                    if (this.level.isClientSide && !this.isPassenger() && !blockpos1.equals(this.clientOldAttachPosition)) {
                        this.clientOldAttachPosition = blockpos;
                        this.clientSideTeleportInterpolation = 6;
                        this.xOld = this.getX();
                        this.yOld = this.getY();
                        this.zOld = this.getZ();
                    }
                }

            }
        }



        public boolean canStayAt(BlockPos pPos, Direction pFacing) {
            if (this.isPositionBlocked(pPos)) {
                return false;
            } else {
                Direction direction = pFacing.getOpposite();
                if (!this.level.loadedAndEntityCanStandOnFace(pPos.relative(pFacing), this, direction)) {
                    return false;
                } else {
                    return true;
                }
            }
        }

        private boolean isPositionBlocked(BlockPos pPos) {
            BlockState blockstate = this.level.getBlockState(pPos);
            if (blockstate.isAir()) {
                return false;
            } else {
                boolean flag = blockstate.is(Blocks.MOVING_PISTON) && pPos.equals(this.blockPosition());
                return !flag;
            }
        }


        /**
         * Sets a target for the client to interpolate towards over the next few ticks
         */
        public void lerpTo(double pX, double pY, double pZ, float pYaw, float pPitch, int pPosRotationIncrements, boolean pTeleport) {
            this.lerpSteps = 0;
            this.setPos(pX, pY, pZ);
            this.setRot(pYaw, pPitch);
        }


        public boolean canBeCollidedWith() {
            return this.isAlive();
        }

        public Direction getAttachFace() {
            return this.entityData.get(DATA_ATTACH_FACE_ID);
        }

        private void setAttachFace(Direction pAttachFace) {
            this.entityData.set(DATA_ATTACH_FACE_ID, pAttachFace);
        }

        public void onSyncedDataUpdated(EntityDataAccessor<?> pKey) {
            if (DATA_ATTACH_FACE_ID.equals(pKey)) {
                this.setBoundingBox(this.makeBoundingBox());
            }

            super.onSyncedDataUpdated(pKey);
        }





        protected float getStandingEyeHeight(Pose pPose, EntityDimensions pSize) {
            return 0.5F;
        }

        public void recreateFromPacket(ClientboundAddEntityPacket pPacket) {
            super.recreateFromPacket(pPacket);
            this.yBodyRot = 0.0F;
            this.yBodyRotO = 0.0F;
        }

        /**
         * The speed it takes to move the entity's head rotation through the faceEntity method.
         */
        public int getMaxHeadXRot() {
            return 180;
        }

        public int getMaxHeadYRot() {
            return 180;
        }

        /**
         * Applies a velocity to the entities, to push them away from each other.
         */
        public void push(Entity pEntity) {
        }

        public float getPickRadius() {
            return 0.0F;
        }

        public Optional<Vec3> getRenderPosition(float pPartial) {
            if (this.clientOldAttachPosition != null && this.clientSideTeleportInterpolation > 0) {
                double d0 = (double)((float)this.clientSideTeleportInterpolation - pPartial) / 6.0D;
                d0 *= d0;
                BlockPos blockpos = this.blockPosition();
                double d1 = (double)(blockpos.getX() - this.clientOldAttachPosition.getX()) * d0;
                double d2 = (double)(blockpos.getY() - this.clientOldAttachPosition.getY()) * d0;
                double d3 = (double)(blockpos.getZ() - this.clientOldAttachPosition.getZ()) * d0;
                return Optional.of(new Vec3(-d1, -d2, -d3));
            } else {
                return Optional.empty();
            }
        }






static class BarnacleBodyRotationControl extends BodyRotationControl {
    public BarnacleBodyRotationControl(Mob pMob) {
        super(pMob);
    }

    /**
     * Update the Head and Body rendering angles
     */
    public void clientTick() {
    }
}




}
