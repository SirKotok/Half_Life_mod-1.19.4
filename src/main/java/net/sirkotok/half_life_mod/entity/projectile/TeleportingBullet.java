package net.sirkotok.half_life_mod.entity.projectile;

import net.minecraft.core.BlockPos;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.sirkotok.half_life_mod.entity.HalfLifeEntities;
import net.sirkotok.half_life_mod.entity.base.FireballNoTrail;
import net.sirkotok.half_life_mod.item.HalfLifeItems;
import net.sirkotok.half_life_mod.sound.HalfLifeSounds;
import net.sirkotok.half_life_mod.misc.util.HLperUtil;
import net.sirkotok.half_life_mod.worldgen.dimension.HalfLifeDimensions;
import net.sirkotok.half_life_mod.worldgen.portal.NetherTeleporter;
import net.sirkotok.half_life_mod.worldgen.portal.XenTeleporter;
import net.tslat.smartbrainlib.util.EntityRetrievalUtil;

import java.util.List;

public class TeleportingBullet extends FireballNoTrail {



    public TeleportingBullet(EntityType<? extends TeleportingBullet> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }


    public TeleportingBullet(Level pLevel, LivingEntity pShooter) {
        super(HalfLifeEntities.BULLET_TP.get(), pShooter, pLevel);
    }

    public TeleportingBullet(Level pLevel, LivingEntity pShooter, double pOffsetX, double pOffsetY, double pOffsetZ) {
        super(HalfLifeEntities.BULLET_TP.get(), pShooter, pOffsetX, pOffsetY, pOffsetZ, pLevel);
    }


    public TeleportingBullet(Level pLevel, double pX, double pY, double pZ, double pOffsetX, double pOffsetY, double pOffsetZ) {
        super(HalfLifeEntities.BULLET_TP.get(), pX, pY, pZ, pOffsetX, pOffsetY, pOffsetZ, pLevel);
    }



    public ItemStack GetAllwaysItem() {
        return HalfLifeItems.FAKE_PORTAL.get().getDefaultInstance();
    }


    public static final EntityDataAccessor<Integer> DESTINATION = SynchedEntityData.defineId(Bullet.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Float> BULLETDAMAGE = SynchedEntityData.defineId(Bullet.class, EntityDataSerializers.FLOAT);
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(BULLETDAMAGE, 40f);
        this.entityData.define(DESTINATION, 0);
    }
    public void setDestination(int i){
        this.entityData.set(DESTINATION, i);
    }
    public float getDestination(){
        return entityData.get(DESTINATION);
    }

    public void setdamage(float i){
        this.entityData.set(BULLETDAMAGE, i);
    }
    public float getdamage(){
        return entityData.get(BULLETDAMAGE);
    }




    @Override
    protected boolean shouldBurn() {
        return false;
    }

    private boolean UsingShield(Player pPlayer, ServerLevel level, ItemStack pPlayerItemStack) {
        if (!pPlayerItemStack.isEmpty() && pPlayerItemStack.is(Items.SHIELD)) {
            return true;
        } else return false;
    }

    @Override
    protected void onHitEntity(EntityHitResult pResult) {

        if (level.isClientSide()) return;

        Entity target = pResult.getEntity();
        LivingEntity entity1 = (this.getOwner() instanceof LivingEntity living) ? living : null;

        ServerLevel serverlevel = (ServerLevel) level;
        BlockPos pBlockPos = target.blockPosition();

        int rad = 5;
        List<LivingEntity> targets = EntityRetrievalUtil.getEntities((Level) serverlevel,
                new AABB(pBlockPos.getX() - rad, pBlockPos.getY() - rad, pBlockPos.getZ() - rad,
                        pBlockPos.getX() + rad, pBlockPos.getY() + rad, pBlockPos.getZ() + rad), obj -> obj instanceof LivingEntity);
        for (LivingEntity entity2 : targets) {
            if (entity2 != target) {
                entity2.hurt(this.damageSources().mobProjectile(this, (LivingEntity) entity1 ), this.getdamage()/2);
                HLperUtil.DisableShieldFor(entity2, 1f, 40, serverlevel);
            }
        }

        boolean flag = target instanceof Player player && UsingShield(player, serverlevel, player.isUsingItem() ? player.getUseItem() : ItemStack.EMPTY);

        if (flag || !target.canChangeDimensions()) { //target instanceof LivingEntity living && living.getHealth() > 50
            target.hurt(this.level.damageSources().mobProjectile(this, entity1), this.getdamage());
            HLperUtil.DisableShieldFor(target, 1f, 100, serverlevel);
        } else if (target.canChangeDimensions()) {
            if (target instanceof Mob) {
            ((Mob) target).setPersistenceRequired();
            }
            handleHalfLifePortal(target, target.blockPosition());
        }






        this.discard();

    }

    protected SoundEvent getHitSound(){
        return HalfLifeSounds.DISPLACER_TELEPORT.get();
    }


    @Override
    protected void onHitBlock(BlockHitResult pResult) {
        super.onHitBlock(pResult);

        if (level.isClientSide()) return;

        LivingEntity entity1 = (this.getOwner() instanceof LivingEntity living) ? living : null;
        ServerLevel serverlevel = (ServerLevel) level;
        BlockPos pBlockPos = pResult.getBlockPos();
        int rad = 5;
        List<LivingEntity> targets = EntityRetrievalUtil.getEntities((Level) serverlevel,
                new AABB(pBlockPos.getX() - rad, pBlockPos.getY() - rad, pBlockPos.getZ() - rad,
                        pBlockPos.getX() + rad, pBlockPos.getY() + rad, pBlockPos.getZ() + rad), obj -> obj instanceof LivingEntity);
        for (LivingEntity entity2 : targets) {
                entity2.hurt(this.damageSources().mobProjectile(this, (LivingEntity) entity1 ), this.getdamage()/2);
                HLperUtil.DisableShieldFor(entity2, 1f, 40, serverlevel);
            }
    }

    protected void onHit(HitResult pResult) {
        super.onHit(pResult);
        if (!this.level.isClientSide) {
            playSound(this.getHitSound(), 0.3f, 1f);
            this.discard();
        }





    }


    public boolean isPickable() {
        return false;
    }

    /**
     * Called when the entity is attacked.
     */
    public boolean hurt(DamageSource pSource, float pAmount) {
        return false;
    }

    private void handleHalfLifePortal(Entity target, BlockPos pPos) {
        if (target.level instanceof ServerLevel serverlevel) {
            MinecraftServer minecraftserver = serverlevel.getServer();
            if (this.getDestination() == 0) {
            ResourceKey<Level> resourcekey = target.level.dimension() == HalfLifeDimensions.HALFLIFE_LEVEL_KEY ?
                    Level.OVERWORLD : HalfLifeDimensions.HALFLIFE_LEVEL_KEY;
            ServerLevel portalDimension = minecraftserver.getLevel(resourcekey);
            if (portalDimension != null && !target.isPassenger()) {
                if(resourcekey == HalfLifeDimensions.HALFLIFE_LEVEL_KEY) {
                    target.changeDimension(portalDimension, new XenTeleporter(pPos, true));
                } else {
                    target.changeDimension(portalDimension, new XenTeleporter(pPos, false));
                }
            }
            return;
        }

            if (this.getDestination() == 1) {
                ResourceKey<Level> resourcekey = target.level.dimension() == Level.NETHER ?
                        Level.OVERWORLD : Level.NETHER;
                ServerLevel portalDimension = minecraftserver.getLevel(resourcekey);
                if (portalDimension != null && !target.isPassenger()) {
                    if(resourcekey == Level.NETHER) {
                        target.changeDimension(portalDimension, new NetherTeleporter(pPos, true));
                    } else {
                        target.changeDimension(portalDimension, new NetherTeleporter(pPos, false));
                    }
                }
                return;
            }

            if (this.getDestination() == 2) {
                ResourceKey<Level> resourcekey = target.level.dimension() == Level.END ?
                        Level.OVERWORLD : Level.END;
                ServerLevel portalDimension = minecraftserver.getLevel(resourcekey);
                if (portalDimension != null && !target.isPassenger()) {
                    if(resourcekey == Level.END) {
                        target.changeDimension(portalDimension, new XenTeleporter(pPos, true));
                    } else {
                        target.changeDimension(portalDimension, new XenTeleporter(pPos, false));
                    }
                }
            }
        }
    }

}
