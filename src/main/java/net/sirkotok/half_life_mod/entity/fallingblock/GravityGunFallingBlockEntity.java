package net.sirkotok.half_life_mod.entity.fallingblock;

import com.mojang.logging.LogUtils;
import net.minecraft.CrashReportCategory;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.protocol.game.ClientboundBlockUpdatePacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;

import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.DirectionalPlaceContext;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.*;
import net.sirkotok.half_life_mod.entity.HalfLifeEntities;
import net.sirkotok.half_life_mod.item.HalfLifeItems;
import net.sirkotok.half_life_mod.misc.util.HLTags;
import net.tslat.smartbrainlib.util.EntityRetrievalUtil;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;

public class GravityGunFallingBlockEntity extends FallingBlockEntity {
    private static final Logger LOGGER = LogUtils.getLogger();
    private BlockState blockState = Blocks.SAND.defaultBlockState();
    public int time;
    public boolean dropItem = true;

    public Player player;
    @Nullable
    private UUID ownerUUID;
    @Nullable
    private Entity cachedOwner;
    private boolean leftOwner;

    public void setOwner(@Nullable Entity pOwner) {
        if (pOwner != null) {
            this.ownerUUID = pOwner.getUUID();
            this.cachedOwner = pOwner;
        }

    }

    @Nullable
    public Entity getOwner() {
        if (this.cachedOwner != null && !this.cachedOwner.isRemoved()) {
            return this.cachedOwner;
        } else if (this.ownerUUID != null && this.level instanceof ServerLevel) {
            this.cachedOwner = ((ServerLevel)this.level).getEntity(this.ownerUUID);
            return this.cachedOwner;
        } else {
            return null;
        }
    }
    private boolean cancelDrop;
    private boolean hurtEntities;
    private int fallDamageMax = 40;
    private float fallDamagePerDistance;
    @Nullable
    public CompoundTag blockData;
    protected static final EntityDataAccessor<String> PLAYER_STRING_UUID = SynchedEntityData.defineId(GravityGunFallingBlockEntity.class, EntityDataSerializers.STRING);
    protected static final EntityDataAccessor<Integer> PLAYER_NOTFIND_COUNTER = SynchedEntityData.defineId(GravityGunFallingBlockEntity.class, EntityDataSerializers.INT);
    protected static final EntityDataAccessor<Boolean> LAUNCHED = SynchedEntityData.defineId(GravityGunFallingBlockEntity.class, EntityDataSerializers.BOOLEAN);
    protected static final EntityDataAccessor<Boolean> FALL_DOWN_NOT = SynchedEntityData.defineId(GravityGunFallingBlockEntity.class, EntityDataSerializers.BOOLEAN);


    public GravityGunFallingBlockEntity(EntityType pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public GravityGunFallingBlockEntity(Level pLevel, double pX, double pY, double pZ, BlockState pState) {
        this(HalfLifeEntities.GG_BLOCK.get(), pLevel);
        this.blockState = pState;
        this.blocksBuilding = true;
        this.setPos(pX, pY, pZ);
        this.setDeltaMovement(Vec3.ZERO);
        this.xo = pX;
        this.yo = pY;
        this.zo = pZ;
        this.setStartPos(this.blockPosition());
    }



    public static GravityGunFallingBlockEntity fall(Level pLevel, BlockPos pPos, BlockState pBlockState) {
        GravityGunFallingBlockEntity gg_block = new GravityGunFallingBlockEntity(pLevel, (double)pPos.getX() + 0.5D, (double)pPos.getY(), (double)pPos.getZ() + 0.5D, pBlockState.hasProperty(BlockStateProperties.WATERLOGGED) ? pBlockState.setValue(BlockStateProperties.WATERLOGGED, Boolean.valueOf(false)) : pBlockState);
        pLevel.setBlock(pPos, pBlockState.getFluidState().createLegacyBlock(), 3);
        pLevel.addFreshEntity(gg_block);
        return gg_block;
    }

    /**
     * Returns {@code true} if it's possible to attack this entity with an item.
     */
    public boolean isAttackable() {
        return false;
    }

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

    public void resetFALLDOWNNOT() {
        if (this.level.isClientSide()) return;
        boolean flag = this.player == null;
        this.entityData.set(FALL_DOWN_NOT, flag);
    }

    public boolean isFALLDOWNNOT() {
        return this.entityData.get(FALL_DOWN_NOT);
    }


    public void setLaunched() {
        this.entityData.set(FALL_DOWN_NOT, true);
    }

    public boolean isLaunched() {
        return this.entityData.get(LAUNCHED);
    }




    protected Entity.MovementEmission getMovementEmission() {
        return Entity.MovementEmission.NONE;
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(PLAYER_STRING_UUID, "");
        this.entityData.define(PLAYER_NOTFIND_COUNTER, 0);
        this.entityData.define(FALL_DOWN_NOT, true);
        this.entityData.define(LAUNCHED, false);
    }

    /**
     * Returns {@code true} if other Entities should be prevented from moving through this Entity.
     */
    public boolean isPickable() {
        return !this.isRemoved();
    }

    /**
     * Called to update the entity's position/logic.
     */

    public void setnoplayer() {
        this.setPlayerNoFindCounter(0);
        this.setPlayerStringUuid("");
        this.player = null;
        this.setNoGravity(false);
    }


    protected boolean canHitEntity(Entity pTarget) {
        if (!pTarget.canBeHitByProjectile()) {
            return false;
        } else return !pTarget.noPhysics;
    }


    protected void onHit(HitResult pResult) {
        HitResult.Type hitresult$type = pResult.getType();
        if (this.blockState.is(HLTags.Blocks.EXPLOSION)) {
            this.level.explode(this, this.getX(), this.getY(), this.getZ(), 3f, Level.ExplosionInteraction.TNT);
            this.discard();
            return;
        }
        if (hitresult$type == HitResult.Type.ENTITY) {
            this.onHitEntity((EntityHitResult)pResult);
            this.level.gameEvent(GameEvent.PROJECTILE_LAND, pResult.getLocation(), GameEvent.Context.of(this, (BlockState)null));
        } else if (hitresult$type == HitResult.Type.BLOCK) {
            BlockHitResult blockhitresult = (BlockHitResult)pResult;
            this.onHitBlock(blockhitresult);
            BlockPos blockpos = blockhitresult.getBlockPos();
            this.level.gameEvent(GameEvent.PROJECTILE_LAND, blockpos, GameEvent.Context.of(this, this.level.getBlockState(blockpos)));
        }
    }



    protected void onHitEntity(EntityHitResult pResult) {
    //    super.onHitEntity(pResult);
        if (!this.level.isClientSide) {


            int damage = (int) this.getDeltaMovement().scale(4).length();
           DamageSource damagesource = this.damageSources().fallingBlock(this);
            Entity entity = pResult.getEntity();
            if (entity == player) return;
           Entity entity1 = this.getOwner();
           // playSound(this.getAcidSound(), 0.3f, 1f);
           if (entity1 instanceof LivingEntity) {
            LivingEntity owner = (LivingEntity) entity1;
            entity.hurt(this.damageSources().mobProjectile(this, owner), damage);
           } else entity.hurt(damagesource, damage);
        }
        onHitBlock();
    }
    protected void onHitBlock(BlockHitResult pResult) {
        this.onHitBlock();
    }
    protected void onHitBlock() {
        BlockPos blockpos = this.blockPosition();
        boolean flag = this.blockState.getBlock() instanceof ConcretePowderBlock;
        boolean flag1 = flag && this.blockState.canBeHydrated(this.level, blockpos, this.level.getFluidState(blockpos), blockpos);
        Block block = this.blockState.getBlock();
        BlockState blockstate = this.level.getBlockState(blockpos);
        if (!blockstate.is(Blocks.MOVING_PISTON)) {
            if (!this.cancelDrop) {
                boolean flag2 = blockstate.canBeReplaced(new DirectionalPlaceContext(this.level, blockpos, Direction.DOWN, ItemStack.EMPTY, Direction.UP));
                boolean flag3 = FallingBlock.isFree(this.level.getBlockState(blockpos.below())) && (!flag || !flag1);
                boolean flag4 = this.blockState.canSurvive(this.level, blockpos) && !flag3;
                if (flag2 && flag4) {
                    if (this.blockState.hasProperty(BlockStateProperties.WATERLOGGED) && this.level.getFluidState(blockpos).getType() == Fluids.WATER) {
                        this.blockState = this.blockState.setValue(BlockStateProperties.WATERLOGGED, Boolean.valueOf(true));
                    }

                    if (this.level.setBlock(blockpos, this.blockState, 3)) {
                        ((ServerLevel)this.level).getChunkSource().chunkMap.broadcast(this, new ClientboundBlockUpdatePacket(blockpos, this.level.getBlockState(blockpos)));
                        this.discard();
                        if (block instanceof Fallable) {
                            ((Fallable)block).onLand(this.level, blockpos, this.blockState, blockstate, this);
                        }

                        if (this.blockData != null && this.blockState.hasBlockEntity()) {
                            BlockEntity blockentity = this.level.getBlockEntity(blockpos);
                            if (blockentity != null) {
                                CompoundTag compoundtag = blockentity.saveWithoutMetadata();

                                for(String s : this.blockData.getAllKeys()) {
                                    compoundtag.put(s, this.blockData.get(s).copy());
                                }

                                try {
                                    blockentity.load(compoundtag);
                                } catch (Exception exception) {
                                    LOGGER.error("Failed to load block entity from falling block", (Throwable)exception);
                                }

                                blockentity.setChanged();
                            }
                        }
                    } else if (this.dropItem && this.level.getGameRules().getBoolean(GameRules.RULE_DOENTITYDROPS)) {
                        this.discard();
                        this.callOnBrokenAfterFall(block, blockpos);
                        this.spawnAtLocation(block);
                    }
                } else {
                    this.discard();
                    if (this.dropItem && this.level.getGameRules().getBoolean(GameRules.RULE_DOENTITYDROPS)) {
                        this.callOnBrokenAfterFall(block, blockpos);
                        this.spawnAtLocation(block);
                    }
                }
            } else {
                this.discard();
                this.callOnBrokenAfterFall(block, blockpos);
            }
        }
    }


    public void tick() {
        this.resetFALLDOWNNOT();
        if (this.blockState.isAir()) {
            this.discard();
            return;
        }




        if (this.getPlayerNoFindCounter() > 5 || !this.getHasPlayer()) {
            this.setnoplayer();
        }

        if (this.getHasPlayer() && this.player == null) {
            this.makemyplayer();
            if (this.getHasPlayer() && this.player == null) {
                this.setPlayerNoFindCounter(this.getPlayerNoFindCounter()+1);
            }
        } else this.setPlayerNoFindCounter(0);

            Block block = this.blockState.getBlock();
            ++this.time;


        if (this.player != null) {
            if (this.tickCount % 10 == 0)  this.nullmyplayer();
            if (this.player != null) {
            this.setNoGravity(true);
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
      if (isFALLDOWNNOT() && !this.isNoGravity() && !this.level.isClientSide()) {
               this.setDeltaMovement(this.getDeltaMovement().add(0.0D, this.isLaunched() ? -0.01f : -0.04f, 0.0D));
           }

            this.move(MoverType.SELF, this.getDeltaMovement());
            //
           if (!this.level.isClientSide()) {

               HitResult hitresult = ProjectileUtil.getHitResult(this, this::canHitEntity);
               if (hitresult.getType() != HitResult.Type.MISS && !this.getHasPlayer()) {
                   this.onHit(hitresult);
               } else {
                   BlockPos blockpos = this.blockPosition();
                   boolean flag = this.blockState.getBlock() instanceof ConcretePowderBlock;
                   boolean flag1 = flag && this.blockState.canBeHydrated(this.level, blockpos, this.level.getFluidState(blockpos), blockpos);
                   double d0 = this.getDeltaMovement().lengthSqr();
                   if (flag && d0 > 1.0D) {
                       BlockHitResult blockhitresult = this.level.clip(new ClipContext(new Vec3(this.xo, this.yo, this.zo), this.position(), ClipContext.Block.COLLIDER, ClipContext.Fluid.SOURCE_ONLY, this));
                       if (blockhitresult.getType() != HitResult.Type.MISS && this.blockState.canBeHydrated(this.level, blockpos, this.level.getFluidState(blockhitresult.getBlockPos()), blockhitresult.getBlockPos())) {
                           blockpos = blockhitresult.getBlockPos();
                           flag1 = true;
                       }
                   }
                   if (!this.onGround && !flag1) {
                       if (!this.level.isClientSide && (this.time > 100 && (blockpos.getY() <= this.level.getMinBuildHeight() || blockpos.getY() > this.level.getMaxBuildHeight()))) {
                           if (this.dropItem && this.level.getGameRules().getBoolean(GameRules.RULE_DOENTITYDROPS)) {
                               this.spawnAtLocation(block);
                           }
                           this.discard();
                       }
                   } else {
                       BlockState blockstate = this.level.getBlockState(blockpos);
                       if (!blockstate.is(Blocks.MOVING_PISTON)) {
                           if (!this.cancelDrop) {
                               boolean flag2 = blockstate.canBeReplaced(new DirectionalPlaceContext(this.level, blockpos, Direction.DOWN, ItemStack.EMPTY, Direction.UP));
                               boolean flag3 = FallingBlock.isFree(this.level.getBlockState(blockpos.below())) && (!flag || !flag1);
                               boolean flag4 = this.blockState.canSurvive(this.level, blockpos) && !flag3;
                               if (flag2 && flag4) {
                                   if (this.blockState.hasProperty(BlockStateProperties.WATERLOGGED) && this.level.getFluidState(blockpos).getType() == Fluids.WATER) {
                                       this.blockState = this.blockState.setValue(BlockStateProperties.WATERLOGGED, Boolean.valueOf(true));
                                   }

                                   if (this.level.setBlock(blockpos, this.blockState, 3)) {
                                       ((ServerLevel) this.level).getChunkSource().chunkMap.broadcast(this, new ClientboundBlockUpdatePacket(blockpos, this.level.getBlockState(blockpos)));
                                       this.discard();
                                       if (block instanceof Fallable) {
                                           ((Fallable) block).onLand(this.level, blockpos, this.blockState, blockstate, this);
                                       }

                                       if (this.blockData != null && this.blockState.hasBlockEntity()) {
                                           BlockEntity blockentity = this.level.getBlockEntity(blockpos);
                                           if (blockentity != null) {
                                               CompoundTag compoundtag = blockentity.saveWithoutMetadata();

                                               for (String s : this.blockData.getAllKeys()) {
                                                   compoundtag.put(s, this.blockData.get(s).copy());
                                               }

                                               try {
                                                   blockentity.load(compoundtag);
                                               } catch (Exception exception) {
                                                   LOGGER.error("Failed to load block entity from falling block", (Throwable) exception);
                                               }

                                               blockentity.setChanged();
                                           }
                                       }
                                   } else if (this.dropItem && this.level.getGameRules().getBoolean(GameRules.RULE_DOENTITYDROPS)) {
                                       this.discard();
                                       this.callOnBrokenAfterFall(block, blockpos);
                                       this.spawnAtLocation(block);
                                   }
                               } else {
                                   this.discard();
                                   if (this.dropItem && this.level.getGameRules().getBoolean(GameRules.RULE_DOENTITYDROPS)) {
                                       this.callOnBrokenAfterFall(block, blockpos);
                                       this.spawnAtLocation(block);
                                   }
                               }
                           } else {
                               this.discard();
                               this.callOnBrokenAfterFall(block, blockpos);
                           }
                       }
                   }
               }
           }



        this.setDeltaMovement(this.getDeltaMovement().scale(0.98D));

    }

    public Vec3 findpos(Player pPlayer) {
        if (pPlayer == null) return this.position();
        return findposstat(pPlayer);
    }



    public static Vec3 findposstat(Player pPlayer) {
        float yrot = pPlayer.getYRot();
        float yuserot = pPlayer.yHeadRot * ((float)Math.PI / 180F);
        float xr = Math.min(pPlayer.getXRot(), 80);
        float xrot = xr*((float)Math.PI / 180F);
        double bb = (pPlayer.getBbWidth() + 3.0F) * 0.5D;
        double dx = -bb * (double)Mth.sin(yuserot)*Mth.cos(xrot)*1.2;
        double dy = -1*(double) Mth.sin(xrot);
        double dz = +bb * (double) Mth.cos(yuserot)*Mth.cos(xrot)*1.2;
        return new Vec3(pPlayer.getX() + dx,
                pPlayer.getEyeY()-0.3 + dy,
                pPlayer.getZ() + dz);
    }



    public void makemyplayer() {
        if (this.level.isClientSide()) return;
        BlockPos pBlockPos = new BlockPos(this.getBlockX(), this.getBlockY(), this.getBlockZ());
        ServerLevel pLevel = (ServerLevel)this.level;
        int rad = 5;
        List<Player> players = EntityRetrievalUtil.getEntities((Level) pLevel,
                new AABB(pBlockPos.getX() - rad, pBlockPos.getY() - rad, pBlockPos.getZ() - rad,
                        pBlockPos.getX() + rad, pBlockPos.getY() + rad, pBlockPos.getZ() + rad), obj -> obj instanceof Player player && (player.getMainHandItem().is(HalfLifeItems.GRAVITYGUN.get()) || player.getMainHandItem().is(HalfLifeItems.SUPERGRAVITYGUN.get())));
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



    public void callOnBrokenAfterFall(Block pBlock, BlockPos pPos) {
        if (pBlock instanceof Fallable) {
            ((Fallable)pBlock).onBrokenAfterFall(this.level, pPos, this);
        }

    }

    public boolean causeFallDamage(float pFallDistance, float pMultiplier, DamageSource pSource) {
        if (!this.hurtEntities) {
            return false;
        } else {
            int i = Mth.ceil(pFallDistance - 1.0F);
            if (i < 0) {
                return false;
            } else {
                Predicate<Entity> predicate;
                DamageSource damagesource;
                if (this.blockState.getBlock() instanceof Fallable) {
                    Fallable fallable = (Fallable)this.blockState.getBlock();
                    predicate = fallable.getHurtsEntitySelector();
                    damagesource = fallable.getFallDamageSource(this);
                } else {
                    predicate = EntitySelector.NO_SPECTATORS;
                    damagesource = this.damageSources().fallingBlock(this);
                }

                float f = (float)Math.min(Mth.floor((float)i * this.fallDamagePerDistance), this.fallDamageMax);
                this.level.getEntities(this, this.getBoundingBox(), predicate).forEach((p_149649_) -> {
                    p_149649_.hurt(damagesource, f);
                });
                boolean flag = this.blockState.is(BlockTags.ANVIL);
                if (flag && f > 0.0F && this.random.nextFloat() < 0.05F + (float)i * 0.05F) {
                    BlockState blockstate = AnvilBlock.damage(this.blockState);
                    if (blockstate == null) {
                        this.cancelDrop = true;
                    } else {
                        this.blockState = blockstate;
                    }
                }

                return false;
            }
        }
    }

    protected void addAdditionalSaveData(CompoundTag pCompound) {
        pCompound.put("BlockState", NbtUtils.writeBlockState(this.blockState));
        pCompound.putInt("Time", this.time);
        pCompound.putBoolean("DropItem", this.dropItem);
        pCompound.putBoolean("HurtEntities", this.hurtEntities);
        pCompound.putFloat("FallHurtAmount", this.fallDamagePerDistance);
        pCompound.putInt("FallHurtMax", this.fallDamageMax);
        if (this.blockData != null) {
            pCompound.put("TileEntityData", this.blockData);
        }

    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    protected void readAdditionalSaveData(CompoundTag pCompound) {
        this.blockState = NbtUtils.readBlockState(this.level.holderLookup(Registries.BLOCK), pCompound.getCompound("BlockState"));
        this.time = pCompound.getInt("Time");
        if (pCompound.contains("HurtEntities", 99)) {
            this.hurtEntities = pCompound.getBoolean("HurtEntities");
            this.fallDamagePerDistance = pCompound.getFloat("FallHurtAmount");
            this.fallDamageMax = pCompound.getInt("FallHurtMax");
        } else if (this.blockState.is(BlockTags.ANVIL)) {
            this.hurtEntities = true;
        }

        if (pCompound.contains("DropItem", 99)) {
            this.dropItem = pCompound.getBoolean("DropItem");
        }

        if (pCompound.contains("TileEntityData", 10)) {
            this.blockData = pCompound.getCompound("TileEntityData");
        }

        if (this.blockState.isAir()) {
            this.blockState = Blocks.SAND.defaultBlockState();
        }

    }

    public void setHurtsEntities(float pFallDamagePerDistance, int pFallDamageMax) {
        this.hurtEntities = true;
        this.fallDamagePerDistance = pFallDamagePerDistance;
        this.fallDamageMax = pFallDamageMax;
    }

    public void disableDrop() {
        this.cancelDrop = true;
    }

    /**
     * Return whether this entity should be rendered as on fire.
     */
    public boolean displayFireAnimation() {
        return false;
    }

    public void fillCrashReportCategory(CrashReportCategory pCategory) {
        super.fillCrashReportCategory(pCategory);
        pCategory.setDetail("Immitating BlockState", this.blockState.toString());
    }

    public BlockState getBlockState() {
        return this.blockState;
    }

    protected Component getTypeName() {
        return Component.translatable("entity.minecraft.falling_block_type", this.blockState.getBlock().getName());
    }

    /**
     * Checks if players can use this entity to access operator (permission level 2) commands either directly or
     * indirectly, such as give or setblock. A similar method exists for entities at {@link
     * net.minecraft.world.entity.Entity#onlyOpCanSetNbt()}.<p>For example, {@link
     * net.minecraft.world.entity.vehicle.MinecartCommandBlock#onlyOpCanSetNbt() command block minecarts} and {@link
     * net.minecraft.world.entity.vehicle.MinecartSpawner#onlyOpCanSetNbt() mob spawner minecarts} (spawning command
     * block minecarts or drops) are considered accessible.</p>@return true if this entity offers ways for unauthorized
     * players to use restricted commands
     */
    public boolean onlyOpCanSetNbt() {
        return true;
    }

    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return new ClientboundAddEntityPacket(this, Block.getId(this.getBlockState()));
    }

    public void recreateFromPacket(ClientboundAddEntityPacket pPacket) {
        super.recreateFromPacket(pPacket);
        this.blockState = Block.stateById(pPacket.getData());
        this.blocksBuilding = true;
        double d0 = pPacket.getX();
        double d1 = pPacket.getY();
        double d2 = pPacket.getZ();
        this.setPos(d0, d1, d2);
        this.setStartPos(this.blockPosition());
    }
}