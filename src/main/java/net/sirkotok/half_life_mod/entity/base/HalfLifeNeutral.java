package net.sirkotok.half_life_mod.entity.base;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.sirkotok.half_life_mod.entity.mob.mob_geckolib.custom.VortigauntHL2;
import net.sirkotok.half_life_mod.misc.util.CommonSounds;
import net.sirkotok.half_life_mod.misc.util.HLperUtil;
import net.tslat.smartbrainlib.util.BrainUtils;
import net.tslat.smartbrainlib.util.EntityRetrievalUtil;

import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;

public class HalfLifeNeutral extends PathfinderMob implements NeutralMob {

    protected HalfLifeNeutral(EntityType<? extends PathfinderMob> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }


    public float getNeutralSoundVolume(){
        return this.getSoundVolume();
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

    public static final EntityDataAccessor<Integer> SOUNDTYPE = SynchedEntityData.defineId(HalfLifeNeutral.class, EntityDataSerializers.INT);

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
        this.entityData.define(SOUNDTYPE, 0);
        this.entityData.define(SUS_TIMESTAMP, 0);
        this.entityData.define(TIME_STAMP_2, -30);
    }

    public void setSoundType(int i){
        this.entityData.set(SOUNDTYPE, i);
    }

    public int getSoundType(){
        return this.entityData.get(SOUNDTYPE);
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


    @Override
    public float getVoicePitch() {
        return 1;
    }


    public boolean hurt(DamageSource pSource, float pAmount) {
        Entity source = pSource.getEntity();
        boolean u = false;
        if (!this.isDeadOrDying() && source instanceof Player player && !player.getAbilities().instabuild) {
           u = true;
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
    protected void playHurtSound(DamageSource pSource) {
        Entity source = pSource.getEntity();
        boolean x = (source instanceof Player player && !this.ismyenemy(player) && !player.getAbilities().instabuild);
        if (!x) super.playHurtSound(pSource);
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


    private void maybeDisableShield(Player pPlayer, float f, ItemStack pPlayerItemStack) {
        if (!pPlayerItemStack.isEmpty() && pPlayerItemStack.is(Items.SHIELD)) {
            if (this.random.nextFloat() < f) {
                pPlayer.getCooldowns().addCooldown(Items.SHIELD, 100);
                this.level.broadcastEntityEvent(pPlayer, (byte)30);
                pPlayer.stopUsingItem();
            }
        }

    }

    public boolean ConfigurabledoHurtTarget(Entity entity, float disablechance, float attack_modifier, float knockback_modifier, @Nullable MobEffect effect, int duration, boolean visible) {
        return ConfigurabledoHurtTargetShieldBoolean(true, entity, disablechance, attack_modifier, knockback_modifier, effect, duration, visible);
    }
    public boolean ConfigurabledoHurtTargetShieldBoolean(boolean after, Entity entity, float disablechance, float attack_modifier, float knockback_modifier, @Nullable MobEffect effect, int duration, boolean visible) {
        float p = (float) this.getAttributeValue(Attributes.ATTACK_DAMAGE);
        float f = attack_modifier*p;
        float f1 = (float) this.getAttributeValue(Attributes.ATTACK_KNOCKBACK);
        f1 = knockback_modifier*f1;


        if (entity instanceof Player && disablechance>0 && !after) {
            Player player = (Player)entity;
            this.maybeDisableShield(player, disablechance, player.isUsingItem() ? player.getUseItem() : ItemStack.EMPTY);
        }



        if (entity instanceof LivingEntity) {
            f += EnchantmentHelper.getDamageBonus(this.getMainHandItem(), ((LivingEntity) entity).getMobType());
            f1 += (float) EnchantmentHelper.getKnockbackBonus(this);
        }

        int i = EnchantmentHelper.getFireAspect(this);
        if (i > 0) {
            entity.setSecondsOnFire(i * 4);
        }


        if (entity instanceof LivingEntity && effect != null) {
            float t = this.level.getCurrentDifficultyAt(this.blockPosition()).getEffectiveDifficulty();
            ((LivingEntity)entity).addEffect(new MobEffectInstance(effect, duration, 0, false, visible), this);
        }
        boolean flag;
        flag = entity.hurt(this.damageSources().mobAttack(this), f);
        if (flag) {
            if (f1 > 0.0F && entity instanceof LivingEntity) {
                ((LivingEntity) entity).knockback((double) (f1 * 0.5F), (double) Mth.sin(this.getYRot() * ((float) Math.PI / 180F)), (double) (-Mth.cos(this.getYRot() * ((float) Math.PI / 180F))));
                this.setDeltaMovement(this.getDeltaMovement().multiply(0.6D, 1.0D, 0.6D));
            }


            this.doEnchantDamageEffects(this, entity);
            this.setLastHurtMob(entity);
        }

        if (entity instanceof Player && disablechance>0 && after) {
            Player player = (Player)entity;
            this.maybeDisableShield(player, disablechance, player.isUsingItem() ? player.getUseItem() : ItemStack.EMPTY);
        }




        return flag;
    }

    public boolean friendaround(){
        ServerLevel pLevel = (ServerLevel) this.level;
        BlockPos pBlockPos = this.blockPosition();
        int rad = 8;
        List<HalfLifeNeutral> friends = EntityRetrievalUtil.getEntities((Level) pLevel,
                new AABB(pBlockPos.getX() - 15, pBlockPos.getY() - 15, pBlockPos.getZ() - 15,
                        pBlockPos.getX() + 15, pBlockPos.getY() + 15, pBlockPos.getZ() + 15), obj -> (obj != this) && ((obj instanceof Player p && !this.ismyenemy(p)) || (obj instanceof HalfLifeNeutral neutral && !(this instanceof VortigauntHL2)) || ((this instanceof VortigauntHL2) && obj instanceof HalfLifeNeutral && !(obj instanceof VortigauntHL2))));
        return !friends.isEmpty();
    }


    public int count;
    public HalfLifeNeutral answerto = null;

    public void setAnswerto(HalfLifeNeutral e) {
        this.answerto = e;
    }

    public HalfLifeNeutral getAnswerto() {
       return this.answerto;
    }

    public HalfLifeNeutral askto = null;

    public void setAskto(HalfLifeNeutral e) {
        this.askto = e;
    }

    public HalfLifeNeutral getAskto() {
        return this.askto;
    }


    @Override
    public void tick() {
        super.tick();
        if (this.ambientSoundTime == 0 && !this.level.isClientSide()) {
            this.choosesoundtype();
        }

        if (random.nextFloat() > 0.1 && this.tickCount % 20 == 0 && !this.level.isClientSide() && (this.getTarget() == null || random.nextFloat() < 0.1f || (this.getTarget() != null && this.getTarget().isDeadOrDying()))) {
            ServerLevel pLevel = (ServerLevel) this.level;
            BlockPos pBlockPos = this.blockPosition();
            int rad = 20;
            List<LivingEntity> dead = EntityRetrievalUtil.getEntities((Level) pLevel,
                    new AABB(pBlockPos.getX() - rad, pBlockPos.getY() - rad, pBlockPos.getZ() - rad,
                            pBlockPos.getX() + rad, pBlockPos.getY() + rad, pBlockPos.getZ() + rad), obj -> obj instanceof LivingEntity living && living.isDeadOrDying() && living.getLastAttacker() == this);
            if (!dead.isEmpty()) {
                CommonSounds.PlaySoundAsOwn(this, this.getKillReactionSound());
                this.ambientSoundTime = -this.getAmbientSoundInterval();
            }
        }


        if (this.getTarget() != null && !this.level.isClientSide()) {
            this.setAskto(null);
            this.setAnswerto(null);
        }
        if (this.getTarget() == null && this.tickCount % 3 == 0  && !this.level.isClientSide()) {
            if (this.getAnswerto() != null) {
                if (this.tickCount % 9 == 0) HLperUtil.slowEntityFor(this, 13);
                this.setXRot((float) HLperUtil.xanglefromvec3(this.getAnswerto().position().subtract(this.position())));
                this.setYRot((float) HLperUtil.yanglefromvec3(this.getAnswerto().position().subtract(this.position())));
            } else if (this.getAskto() != null) {
                if (this.tickCount % 9 == 0) HLperUtil.slowEntityFor(this, 13);
                this.setXRot((float) HLperUtil.xanglefromvec3(this.getAskto().position().subtract(this.position())));
                this.setYRot((float) HLperUtil.yanglefromvec3(this.getAskto().position().subtract(this.position())));
            }
        }
    }



    protected SoundEvent getAmbientSoundS() {
        return null;
    }
    @Override
    protected SoundEvent getAmbientSound() {
        int i = this.getSoundType();
        switch (i) {
            case 0:
                return super.getAmbientSound();
            case 1:
                return this.getIdleCommentSound();
            case 2:
                return this.getIdleHurtSound();
            case 3:
                return this.getIdlePLHurtSound();
        }
        return super.getAmbientSound();
    }


        public void choosesoundtype(){
        if (BrainUtils.getMemory(this, MemoryModuleType.ATTACK_TARGET) != null) this.setSoundType(0);
        if (random.nextFloat() > 0.8 || this instanceof VortigauntHL2 && (!friendaround() || random.nextFloat() > 0.5)) {this.setSoundType(0); return;}
        if (random.nextFloat() < 0.25 && this.getHealth() < this.getMaxHealth()*0.3) {this.setSoundType(2); return;}
        if (random.nextFloat() > 0.7) this.setSoundType(1);
       if (random.nextFloat() < 0.25) {
           ServerLevel pLevel = (ServerLevel) this.level;
           BlockPos pBlockPos = this.blockPosition();
           int rad = 7;
           List<LivingEntity> friend = EntityRetrievalUtil.getEntities((Level) pLevel,
                   new AABB(pBlockPos.getX() - rad, pBlockPos.getY() - rad, pBlockPos.getZ() - rad,
                           pBlockPos.getX() + rad, pBlockPos.getY() + rad, pBlockPos.getZ() + rad), obj -> (obj != this && obj instanceof LivingEntity living && living.getHealth() < living.getMaxHealth() * 0.3) && (obj instanceof HalfLifeNeutral || (obj instanceof Player player && !this.ismyenemy(player))));
       if (!friend.isEmpty()){this.setSoundType(3); return;}
       }
            this.setSoundType(0);
        }

    @Override
    public int getAmbientSoundInterval() {
        if (this.getSoundType() == 0) return random.nextInt(120, 900);
        else return random.nextInt(400, 1800);
    }



    public SoundEvent getQuestionSound() {
        return null;
    }
    public SoundEvent getIdleCommentSound() {
        return null;
    }
    public SoundEvent getAnswerSound() {
        return null;
    }
    public SoundEvent getIdleHurtSound() {
        return null;
    }
    public SoundEvent getIdlePLHurtSound() {
        return null;
    }
    public SoundEvent getKillReactionSound() {
        return null;
    }

    public SoundEvent getWarningSound() {
        return SoundEvents.COW_AMBIENT;
    }

    public SoundEvent getAttackReactionSound() {
        return SoundEvents.COW_DEATH;
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
        if (this.ismyenemy(pPlayer)) return InteractionResult.FAIL;
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
