package net.sirkotok.half_life_mod.entity.base;

import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;

import net.minecraft.world.entity.ai.attributes.Attributes;

import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.sirkotok.half_life_mod.entity.mob_geckolib.custom.Voltigore;

import javax.annotation.Nullable;

public class HalfLifeMonster<T extends HalfLifeMonster<T>> extends Monster {

    public static final EntityDataAccessor<Integer> ENTITY_MODEL = SynchedEntityData.defineId(HalfLifeMonster.class, EntityDataSerializers.INT);




    protected HalfLifeMonster(EntityType<? extends Monster> type, Level level) {
        super(type, level);
        this.xpReward = 5;

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
        if (this instanceof Voltigore voltigore && voltigore.isBB()) flag = entity.hurt(this.damageSources().mobAttack(this), 2f); else flag = entity.hurt(this.damageSources().mobAttack(this), f);
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




}
