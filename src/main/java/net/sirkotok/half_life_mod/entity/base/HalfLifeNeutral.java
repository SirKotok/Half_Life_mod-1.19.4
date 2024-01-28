package net.sirkotok.half_life_mod.entity.base;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.level.NoteBlockEvent;
import net.sirkotok.half_life_mod.entity.mob_geckolib.custom.Houndeye;
import net.sirkotok.half_life_mod.entity.mob_geckolib.custom.Scientist;
import net.tslat.smartbrainlib.api.SmartBrainOwner;
import net.tslat.smartbrainlib.util.BrainUtils;
import net.tslat.smartbrainlib.util.EntityRetrievalUtil;

import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;

public class HalfLifeNeutral extends PathfinderMob implements NeutralMob {

    protected HalfLifeNeutral(EntityType<? extends PathfinderMob> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public static final EntityDataAccessor<String> STRING_UUID_SUS = SynchedEntityData.defineId(HalfLifeNeutral.class, EntityDataSerializers.STRING);
    public static final EntityDataAccessor<Integer> SUS_TIMESTAMP = SynchedEntityData.defineId(HalfLifeNeutral.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<String> STRING_UUID_ENEMY1 = SynchedEntityData.defineId(HalfLifeNeutral.class, EntityDataSerializers.STRING);
    public static final EntityDataAccessor<String> STRING_UUID_ENEMY2 = SynchedEntityData.defineId(HalfLifeNeutral.class, EntityDataSerializers.STRING);
    public static final EntityDataAccessor<String> STRING_UUID_ENEMY3 = SynchedEntityData.defineId(HalfLifeNeutral.class, EntityDataSerializers.STRING);
    public static final EntityDataAccessor<String> STRING_UUID_ENEMY4 = SynchedEntityData.defineId(HalfLifeNeutral.class, EntityDataSerializers.STRING);
    public static final EntityDataAccessor<String> STRING_UUID_ENEMY5 = SynchedEntityData.defineId(HalfLifeNeutral.class, EntityDataSerializers.STRING);
    public static final EntityDataAccessor<String> STRING_UUID_FOLLOW = SynchedEntityData.defineId(HalfLifeNeutral.class, EntityDataSerializers.STRING);
    public static final EntityDataAccessor<Integer> TIME_STAMP = SynchedEntityData.defineId(HalfLifeNeutral.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Integer> TIME_STAMP_2 = SynchedEntityData.defineId(HalfLifeNeutral.class, EntityDataSerializers.INT);


    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(STRING_UUID_ENEMY1, "");
        this.entityData.define(STRING_UUID_SUS, "");
        this.entityData.define(STRING_UUID_ENEMY2, "");
        this.entityData.define(STRING_UUID_ENEMY3, "");
        this.entityData.define(STRING_UUID_ENEMY4, "");
        this.entityData.define(STRING_UUID_ENEMY5, "");
        this.entityData.define(STRING_UUID_FOLLOW, "");
        this.entityData.define(TIME_STAMP, -30);
        this.entityData.define(SUS_TIMESTAMP, 0);
        this.entityData.define(TIME_STAMP_2, -30);
    }


    @Override
    public void aiStep() {
        super.aiStep();
        if (this.tickCount % 20 == 0 && !this.level.isClientSide() && BrainUtils.getTargetOfEntity(this) == null) {
            ServerLevel pLevel = (ServerLevel) this.level;
            BlockPos pBlockPos = this.blockPosition();
            List<HalfLifeNeutral> friendswithtarget = EntityRetrievalUtil.getEntities((Level) pLevel,
                    new AABB(pBlockPos.getX() - 15, pBlockPos.getY() - 15, pBlockPos.getZ() - 15,
                            pBlockPos.getX() + 15, pBlockPos.getY() + 15, pBlockPos.getZ() + 15), obj -> obj instanceof HalfLifeNeutral neutral && this.hasLineOfSight(neutral) && BrainUtils.getTargetOfEntity(neutral) != null);
            if (!friendswithtarget.isEmpty()) {
              LivingEntity target = BrainUtils.getTargetOfEntity(friendswithtarget.get(0));
              BrainUtils.setTargetOfEntity(this, target);
              if (target instanceof Player player) this.makemyenemy(player.getStringUUID());
            }
        }
    }

    public SoundEvent getWarningSound() {
        return SoundEvents.COW_AMBIENT;
    }

    public SoundEvent getAttackReactionSound() {
        return SoundEvents.COW_DEATH;
    }


    @Override
    public boolean hurt(DamageSource pSource, float pAmount) {
        Entity source = pSource.getEntity();
        if (source instanceof Player player) {
            String uuid_string = player.getStringUUID();
            if (!this.ismyenemy(uuid_string)) {
                if (uuid_string.equals(this.getSUS()) && (this.tickCount - this.entityData.get(SUS_TIMESTAMP) < 600)) {
                    this.playSound(this.getAttackReactionSound(), this.getSoundVolume(), this.getVoicePitch());
                    this.makemyenemy(uuid_string);
                } else {
                    this.playSound(this.getWarningSound(), this.getSoundVolume(), this.getVoicePitch());
                    this.entityData.set(STRING_UUID_SUS, uuid_string);
                    this.entityData.set(SUS_TIMESTAMP, this.tickCount);
                }
            }
        }
        return super.hurt(pSource, pAmount);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        pCompound.putString("Enemy1", this.entityData.get(STRING_UUID_ENEMY1));
        pCompound.putString("Enemy2", this.entityData.get(STRING_UUID_ENEMY2));
        pCompound.putString("Enemy3", this.entityData.get(STRING_UUID_ENEMY3));
        pCompound.putString("Enemy4", this.entityData.get(STRING_UUID_ENEMY4));
        pCompound.putString("Enemy5", this.entityData.get(STRING_UUID_ENEMY5));
    }

    @Override
    public void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        this.entityData.set(STRING_UUID_ENEMY1, pCompound.getString("Enemy1"));
        this.entityData.set(STRING_UUID_ENEMY2, pCompound.getString("Enemy2"));
        this.entityData.set(STRING_UUID_ENEMY3, pCompound.getString("Enemy3"));
        this.entityData.set(STRING_UUID_ENEMY4, pCompound.getString("Enemy4"));
        this.entityData.set(STRING_UUID_ENEMY5, pCompound.getString("Enemy5"));
    }

    public String getFollowUUIDString() {
       return this.entityData.get(STRING_UUID_FOLLOW);
    }


    public String getSUS() {
        return this.entityData.get(STRING_UUID_SUS);
    }


    public boolean ismyenemy(LivingEntity entity) {
        if (!(entity instanceof Player)) return false;
       String string = entity.getStringUUID();
       return ismyenemy(string);
    }

    public boolean ismyenemy(String string) {
        boolean f1 = string.equals(this.entityData.get(STRING_UUID_ENEMY1));
        boolean f2 = string.equals(this.entityData.get(STRING_UUID_ENEMY2));
        boolean f3 = string.equals(this.entityData.get(STRING_UUID_ENEMY3));
        boolean f4 = string.equals(this.entityData.get(STRING_UUID_ENEMY4));
        boolean f5 = string.equals(this.entityData.get(STRING_UUID_ENEMY5));
        return f1 || f2 || f3 || f4 || f5;
    }


    public void makemyenemy(String enemy) {
        String string = "";
        boolean f1 = string.equals(this.entityData.get(STRING_UUID_ENEMY1));
        boolean f2 = string.equals(this.entityData.get(STRING_UUID_ENEMY2));
        boolean f3 = string.equals(this.entityData.get(STRING_UUID_ENEMY3));
        boolean f4 = string.equals(this.entityData.get(STRING_UUID_ENEMY4));
        boolean f5 = string.equals(this.entityData.get(STRING_UUID_ENEMY5));
        if (f1) {
            this.entityData.set(STRING_UUID_ENEMY1, enemy);
        return;}
        if (f2){ this.entityData.set(STRING_UUID_ENEMY2, enemy);
            return;}
        if (f3) {this.entityData.set(STRING_UUID_ENEMY3, enemy);
            return;}
        if (f4) {this.entityData.set(STRING_UUID_ENEMY4, enemy);
            return;}
        if (f5) {this.entityData.set(STRING_UUID_ENEMY5, enemy);
            return;}

        int i = random.nextInt(5);
        switch (i) {
            case 0:  {this.entityData.set(STRING_UUID_ENEMY1, enemy);
            return;}
            case 1:  {this.entityData.set(STRING_UUID_ENEMY2, enemy);
            return;}
            case 2:  {this.entityData.set(STRING_UUID_ENEMY3, enemy);
            return;}
            case 3:  {this.entityData.set(STRING_UUID_ENEMY4, enemy);
            return;}
            case 4:  this.entityData.set(STRING_UUID_ENEMY5, enemy);
        }



    }




    public SoundEvent getStartFollowingSound() {
        return SoundEvents.PIG_AMBIENT;
    }
    public SoundEvent getStopFollowingSound() {
        return SoundEvents.PIG_DEATH;
    }


    @Override
    public InteractionResult interactAt(Player pPlayer, Vec3 pVec, InteractionHand pHand) {
        String string = pPlayer.getStringUUID();
        String second = this.getFollowUUIDString();
        if (second.equals(string) && (this.tickCount - this.entityData.get(TIME_STAMP)>40) && this.tickCount-this.entityData.get(TIME_STAMP_2)>40) {
            this.playSound(getStopFollowingSound(), this.getSoundVolume(), 1f);
            this.entityData.set(STRING_UUID_FOLLOW, "");
            this.entityData.set(TIME_STAMP_2, this.tickCount);
            return super.interactAt(pPlayer, pVec, pHand);
        }
        if (this.tickCount-this.entityData.get(TIME_STAMP_2)>40 && (this.tickCount - this.entityData.get(TIME_STAMP)>40)) {
        this.playSound(getStartFollowingSound(), this.getSoundVolume(), 1f);
        this.entityData.set(TIME_STAMP, this.tickCount);
        this.entityData.set(STRING_UUID_FOLLOW, string); }
        return super.interactAt(pPlayer, pVec, pHand);
    }

    @Override
    public boolean removeWhenFarAway(double pDistanceToClosestPlayer) {
        return false;
    }

    @Nullable
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor p_33601_, DifficultyInstance p_33602_, MobSpawnType p_33603_, @Nullable SpawnGroupData p_33604_, @Nullable CompoundTag p_33605_) {
        this.setPersistenceRequired();
        return super.finalizeSpawn(p_33601_, p_33602_, p_33603_, p_33604_, p_33605_);
    }



    @Override
    public int getRemainingPersistentAngerTime() {
        return 0;
    }

    @Override
    public void setRemainingPersistentAngerTime(int pRemainingPersistentAngerTime) {

    }

    @org.jetbrains.annotations.Nullable
    @Override
    public UUID getPersistentAngerTarget() {
        return null;
    }

    @Override
    public void setPersistentAngerTarget(@org.jetbrains.annotations.Nullable UUID pPersistentAngerTarget) {

    }

    @Override
    public void startPersistentAngerTimer() {
    }
}
