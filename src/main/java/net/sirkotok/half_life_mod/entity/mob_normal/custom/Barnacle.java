package net.sirkotok.half_life_mod.entity.mob_normal.custom;

import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.BodyRotationControl;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.sirkotok.half_life_mod.entity.mob_geckolib.custom.Headcrab_Poison_2;
import net.sirkotok.half_life_mod.sound.HalfLifeSounds;
import net.sirkotok.half_life_mod.misc.util.HLperUtil;
import net.tslat.smartbrainlib.util.EntityRetrievalUtil;
import org.joml.Vector3f;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;

public class Barnacle extends PathfinderMob {

    //TODO: bugfix
    /*
    bug 1 = headcrabs jumps and breaks it
    bug 2 = items and everything to do with them
    bug 3 = snark explode and it breaks (shitty fix applied)
     */



    @Nullable
    private LivingEntity foodliving;
    @Nullable
    private ItemEntity fooditementity;

    private final BarnaclePart tongue;
    private final BarnaclePart mouth;
    private final BarnaclePart body;
    private final BarnaclePart[] parts;


    public float mouthwidth = 0.1f;

    @Override
    public boolean isMultipartEntity() {
        return true;
    }

    @Override
    public net.minecraftforge.entity.PartEntity<?>[] getParts() { return this.parts; }


    private static final EntityDataAccessor<Float> TONGUE_LENGTH =
            SynchedEntityData.defineId(Barnacle.class, EntityDataSerializers.FLOAT);


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



    public static boolean checkBarnacleSpawnRules(EntityType<Barnacle> pType, ServerLevelAccessor pLevel, MobSpawnType pSpawnType, BlockPos pBlockPos, RandomSource pRandom) {

        if (!canSpawnAt(pBlockPos, pLevel)) {
            return false;
        }

        boolean flag = HLperUtil.isWithinSpawnChunks(pBlockPos, pLevel);
        if (flag) return false;

        int radius = 70;
        int rad = 10;
        List<Mob>entities2 = EntityRetrievalUtil.getEntities((Level) pLevel,
                new AABB(pBlockPos.getX() - rad, pBlockPos.getY() - rad, pBlockPos.getZ() - rad,
                        pBlockPos.getX() + rad, pBlockPos.getY() + rad, pBlockPos.getZ() + rad), obj -> obj instanceof Barnacle);
        List<Mob> entities = EntityRetrievalUtil.getEntities((Level) pLevel,
                new AABB(pBlockPos.getX() - radius, pBlockPos.getY() - radius, pBlockPos.getZ() - radius,
                        pBlockPos.getX() + radius, pBlockPos.getY() + radius, pBlockPos.getZ() + radius), obj -> obj instanceof Barnacle);
       if (entities.isEmpty()) return pLevel.getDifficulty() != Difficulty.PEACEFUL && Monster.isDarkEnoughToSpawn(pLevel, pBlockPos, pRandom);
       else if (entities.equals(entities2) && entities.size() <= 10)
        return pLevel.getDifficulty() != Difficulty.PEACEFUL && Monster.isDarkEnoughToSpawn(pLevel, pBlockPos, pRandom);
        else return false;
    }

    public static boolean canSpawnAt(BlockPos pPos, ServerLevelAccessor pLevel) {
        BlockPos pos = new BlockPos(pPos.getX(), pPos.getY() + 1, pPos.getZ());
        BlockState blockstate = pLevel.getBlockState(pos);
        boolean flag = !blockstate.isAir();
        return flag;
    }




        @Nullable
        private BlockPos clientOldAttachPosition;
        private int clientSideTeleportInterpolation;


        public Barnacle(EntityType<? extends Barnacle> pEntityType, Level pLevel) {
            super(pEntityType, pLevel);
            this.tongue = new BarnaclePart(this, "tongue", 0.4F, 1f, false);
            this.body = new BarnaclePart(this, "body", 0.8f, 0.8f, true);
            this.mouth = new BarnaclePart(this, "mouth", 0.4F, 1f, false);
            this.xpReward = 5;

            this.noPhysics = true;
         //   this.noCulling = true;

            this.parts = new BarnaclePart[] {
                    this.tongue,
                    this.body,
                    this.mouth
            };
            this.setId(ENTITY_COUNTER.getAndAdd(this.parts.length + 1) + 1); // Forge: Fix MC-158205: Make sure part ids are successors of parent mob id
        }

    @Override
    public void setId(int pId) {
        super.setId(pId);
        for (int i = 0; i < this.parts.length; i++) // Forge: Fix MC-158205: Set part ids to successors of parent mob id
            this.parts[i].setId(pId + i + 1);
    }



    private void tickTongue(BarnaclePart pPart) {
        pPart.moveTo(this.getX(), this.getY() - getairemount()+0.5, this.getZ());
        double f = this.gettonguelength();
        pPart.setBoundingBox(new AABB(this.blockPosition().getX() + 0.5 + mouthwidth/2, this.blockPosition().getY()+0.8, this.blockPosition().getZ() + 0.5 + mouthwidth/2, this.blockPosition().getX()+ 0.5 - mouthwidth/2, this.blockPosition().getY()-f, this.blockPosition().getZ()+ 0.5 - mouthwidth/2));
    }
    private void tickMouth(BarnaclePart pPart) {
        pPart.moveTo(this.getX(), this.getY() - 0.5f, this.getZ());
        pPart.setBoundingBox(new AABB(this.blockPosition().getX() + 0.5 + mouthwidth, this.blockPosition().getY()+0.1f, this.blockPosition().getZ() + 0.5 + mouthwidth, this.blockPosition().getX()+ 0.5 - mouthwidth, this.blockPosition().getY() + 0.7f, this.blockPosition().getZ()+ 0.5 - mouthwidth));
    }

    private void tickBody(BarnaclePart pPart) {
        pPart.moveTo(this.getX(), this.getY(), this.getZ());
        pPart.setBoundingBox(new AABB(this.blockPosition().getX(), this.blockPosition().getY()+0.5f, this.blockPosition().getZ(), this.blockPosition().getX()+1, this.blockPosition().getY()+1, this.blockPosition().getZ()+1));
    }


    @Override
    public boolean canCollideWith(Entity pEntity) {
        return false;
    }
    @Override
    public boolean isPickable() {
        return false;
    }

    public void aiStep() {



            if (this.isDeadOrDying()) return;


            if (this.foodliving != null && this.foodliving.isDeadOrDying()) {
                this.foodliving = null;
            }

            if (this.fooditementity != null && (this.fooditementity.getTags().contains(this.getStringUUID()+"checked"))) {
                this.fooditementity = null;
            }

        super.aiStep();
        tickTongue(this.tongue);
        tickBody(this.body);
        tickMouth(this.mouth);
     //   if (!this.level.isClientSide){
        this.checkTongue(this.tongue.getBoundingBox());
        if (this.tickCount % 15 == 0) this.checkMouth(this.mouth.getBoundingBox());
      //  }
        this.setBoundingBox(new AABB(this.blockPosition().getX(), this.blockPosition().getY()+1, this.blockPosition().getZ(), this.blockPosition().getX()+1f, this.blockPosition().getY()-this.getairemount(), this.blockPosition().getZ()+1f));

    }




    private void checkMouth(AABB box) {
        List<Entity> list = this.level.getEntities(this, box);
        this.setAttacking(false);



        if (!list.isEmpty()) {
            for (int i = 0; i < list.size(); ++i) {
                Entity entity = list.get(i);

                if (entity instanceof ItemEntity food) {
                    ItemStack stack = food.getItem();
                    Item item = stack.getItem();
                    if (item.isEdible())  {
                        this.playSound(this.getBiteSound(), 0.5f, 1f);
                    this.doHurtTarget(entity);
                    this.setAttacking(true);
                    if (stack.is(Items.POISONOUS_POTATO)) {
                        addEffect(new MobEffectInstance(MobEffects.POISON, 100), this);
                        } else {
                        this.heal(4);
                    }
                    entity.discard();
                    this.fooditementity = null;
                    } else {
                        entity.addTag(this.getStringUUID()+"checked");
                        entity.removeTag("barnaclefood");
                        this.fooditementity = null;
                        entity.setDeltaMovement(0.5f*RandomSource.create().nextInt(-1, 1)*RandomSource.create().nextFloat(), 0.5f, 0.5f*RandomSource.create().nextInt(-1, 2)*RandomSource.create().nextFloat());
                        this.playSound(this.getDisgustedSound(), 0.5f, 1f);
                    }



                }

                if (entity instanceof LivingEntity) {
                    this.playSound(this.getBiteSound(), 0.5f, 1f);
                    this.doHurtTarget(entity);
                    this.setAttacking(true);
                    if (entity instanceof Headcrab_Poison_2) {
                        this.eatpoisonheadcrab(entity);
                        this.setHealth(0);
                    this.playSound(this.getDeathSound());
                    } else {
                        this.doHurtTarget(entity);
                    this.heal(4);
                }
                }
            }
        }

    }


    public boolean eatpoisonheadcrab(Entity pEntity) {
        float f = (float) 100;
        float f1 = (float)this.getAttributeValue(Attributes.ATTACK_KNOCKBACK);
        if (pEntity instanceof LivingEntity) {
            f += EnchantmentHelper.getDamageBonus(this.getMainHandItem(), ((LivingEntity)pEntity).getMobType());
            f1 += (float)EnchantmentHelper.getKnockbackBonus(this);
        }

        int i = EnchantmentHelper.getFireAspect(this);
        if (i > 0) {
            pEntity.setSecondsOnFire(i * 4);
        }

        boolean flag = pEntity.hurt(this.damageSources().mobAttack(this), f);
        if (flag) {
            if (f1 > 0.0F && pEntity instanceof LivingEntity) {
                ((LivingEntity)pEntity).knockback((double)(f1 * 0.5F), (double)Mth.sin(this.getYRot() * ((float)Math.PI / 180F)), (double)(-Mth.cos(this.getYRot() * ((float)Math.PI / 180F))));
                this.setDeltaMovement(this.getDeltaMovement().multiply(0.6D, 1.0D, 0.6D));
            }


            this.doEnchantDamageEffects(this, pEntity);
            this.setLastHurtMob(pEntity);
        }

        return flag;
    }





    private void checkTongue(AABB box) {

        if (this.foodliving == null && this.fooditementity == null)
        {
            List<Entity> list = this.level.getEntities(this, box);

            if(!list.contains(this.fooditementity)) this.fooditementity = null;

            if (!list.isEmpty()) {
                for (int i = 0; i < list.size(); ++i) {
                    Entity entity = list.get(i);

                    if (entity instanceof ItemEntity && !entity.getTags().contains(this.getStringUUID()+"checked") && !entity.getTags().contains("barnaclefood")) {
                        if (this.tickCount % 3 == 0) {
                            this.fooditementity = (ItemEntity)entity;
                            entity.moveTo(this.getX(), entity.getY(), this.getZ());
                            entity.setDeltaMovement(0, 0.2, 0);
                        }
                        entity.addTag("barnaclefood");
                        break;
                    }

                    Entity entity1;
                    List<Entity> list1 = entity.getPassengers();
                    if (list1.isEmpty()) entity1 = entity;
                    else {
                        entity1 = list1.get(RandomSource.create().nextInt(0, list1.size()));
                        entity1.stopRiding();
                    }
                    if (entity1 instanceof LivingEntity && !((LivingEntity) entity1).isDeadOrDying() && !entity1.getTags().contains("barnaclefood") && !(entity1 instanceof Player pl && (pl.isCreative() || pl.isSpectator()))) {

                        if(entity1.isPassenger()) entity1.stopRiding();

                        this.playSound(this.getTongueSound(), 0.5f, 1);

                        if (this.tickCount % 3 == 0) {
                            entity1.moveTo(this.getX(), entity1.getY(), this.getZ());
                            if (entity1 instanceof Player) {
                                entity1.setDeltaMovement(0, 0.1, 0);
                            } else {entity1.setDeltaMovement(0, 0, 0);}
                        }
                        ((LivingEntity)entity1).addEffect(new MobEffectInstance(MobEffects.LEVITATION, 3, 5, false, false, false), this);

                        this.foodliving = (LivingEntity) entity1;
                        entity1.addTag("barnaclefood");
                        break;
                    }
                }
                }
            }

        if(this.fooditementity != null) {
            ItemEntity food = this.fooditementity;
            if (this.tickCount % 3 == 0) {
                food.moveTo(this.getX(), food.getY(), this.getZ());
                food.setDeltaMovement(0, 0.2, 0);
            }
        }



        if (this.foodliving != null) {
            LivingEntity food = this.foodliving;
            if (this.tickCount % 3 == 0) {
                food.moveTo(this.getX(), food.getY(), this.getZ());
                if (food instanceof Player) {
                    food.setDeltaMovement(0, 0.1, 0);
                } else {food.setDeltaMovement(0, 0, 0);}
            }
            (food).addEffect(new MobEffectInstance(MobEffects.LEVITATION, 3, 5, false, false, false), this);
        }


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






    public static AttributeSupplier setAttributes() {
        return Monster.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 10D)
                .add(Attributes.ATTACK_DAMAGE, 10f)
                .add(Attributes.ATTACK_KNOCKBACK, 0f)
                .add(Attributes.MOVEMENT_SPEED, 0f).build();
    }



        protected Entity.MovementEmission getMovementEmission() {
            return Entity.MovementEmission.NONE;
        }



        protected SoundEvent getDeathSound() {
            switch (this.random.nextInt(1,3)) {
                case 1:  return HalfLifeSounds.BARNACLE_DIE1.get();
                case 2:  return HalfLifeSounds.BARNACLE_DIE2.get();
            }
            return HalfLifeSounds.HEADCRAB_1_PAIN_1.get();
        }

        protected SoundEvent getHurtSound(DamageSource pDamageSource) {
            return HalfLifeSounds.BARNACLE_PAIN.get();
        }
    protected SoundEvent getDisgustedSound() {
        return HalfLifeSounds.BARNACLE_PAIN.get();
    }

    protected SoundEvent getBiteSound() {
        switch (this.random.nextInt(1,5)) {
            case 1:  return HalfLifeSounds.BARNACLE_BITE1.get();
            case 2:  return HalfLifeSounds.BARNACLE_BITE2.get();
            case 3:  return HalfLifeSounds.BARNACLE_BITE3.get();
            case 4:  return HalfLifeSounds.BARNACLE_BITE4.get();
        }
        return HalfLifeSounds.HEADCRAB_1_PAIN_1.get();
    }
    protected SoundEvent getTongueSound() {
            return HalfLifeSounds.BARNACLE_TONGUE.get();
    }


        protected Direction CorrectDirection() {return Direction.UP;}

        protected void defineSynchedData() {
            super.defineSynchedData();
            this.entityData.define(DATA_ATTACH_FACE_ID, CorrectDirection());
            this.entityData.define(ATTACKING, false);
            this.entityData.define(TONGUE_LENGTH, 0.0f);
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


        public float gettonguelength() {
            return this.entityData.get(TONGUE_LENGTH);
        }
    protected void settonguelength(float lngth) {
        this.entityData.set(TONGUE_LENGTH, lngth);
    }


        public void readAdditionalSaveData(CompoundTag pCompound) {
            super.readAdditionalSaveData(pCompound);
            this.settonguelength(pCompound.getFloat("TongueLength") + 1);
            this.setAttachFace(Direction.from3DDataValue(pCompound.getByte("AttachFace")));

        }

        public void addAdditionalSaveData(CompoundTag pCompound) {
            super.addAdditionalSaveData(pCompound);
            pCompound.putByte("AttachFace", (byte)this.getAttachFace().get3DDataValue());
            pCompound.putFloat("TongueLength", this.gettonguelength() - 1 );

        }

        /**
         * Called to update the entity's position/logic.
         */
        public void tick() {
            super.tick();

            if (this.tickCount % 600 == 0) {
                this.fooditementity = null;
            }
            if (this.tickCount % 3000 == 0) {
                this.foodliving = null;
            }

            if (this.fooditementity != null) {
               if (!this.fooditementity.isAddedToWorld()) {
                    this.fooditementity = null;
                }
            }

            float f;
            if (this.foodliving != null) {
                f = (float) this.getY() - (float) this.foodliving.getY();
            } else if (this.fooditementity!=null) {
                f = (float) this.getY() - (float) this.fooditementity.getY();
            } else {
                f = gettonguelength();
                if (f<getairemount()) {
                    f = f+0.1f;
                }
            }

            if (f>getairemount()) {
                f = getairemount();
                this.foodliving = null;
            }


            this.settonguelength(f);





            if (!this.level.isClientSide && !this.canStayAt(this.blockPosition(), this.getAttachFace())) {
                this.setHealth(0);
            }
            if(this.level.isClientSide()) {
                setupAnimationStates();
            }
        }





        private static float getPhysicalPeek(float pPeek) {
            return 0.5F - Mth.sin((0.5F + pPeek) * (float)Math.PI) * 0.5F;
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
            this.setPersistenceRequired();
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



        public Direction getAttachFace() {
            return this.entityData.get(DATA_ATTACH_FACE_ID);
        }

        private void setAttachFace(Direction pAttachFace) {
            this.entityData.set(DATA_ATTACH_FACE_ID, pAttachFace);
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
