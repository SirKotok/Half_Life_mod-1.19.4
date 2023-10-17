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
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;

public class HalfLifeMonster<T extends HalfLifeMonster<T>> extends Monster {

    public static final EntityDataAccessor<Integer> ENTITY_MODEL = SynchedEntityData.defineId(HalfLifeMonster.class, EntityDataSerializers.INT);




    protected HalfLifeMonster(EntityType<? extends Monster> type, Level level) {
        super(type, level);
        this.xpReward = 5;

    }


    public boolean ConfigurabledoHurtTarget(Entity entity, int attack_modifier, int knockback_modifier, @Nullable MobEffect effect, int duration, boolean visible) {
        float f = (float) this.getAttributeValue(Attributes.ATTACK_DAMAGE);
        f = attack_modifier*f;
        float f1 = (float) this.getAttributeValue(Attributes.ATTACK_KNOCKBACK);
        f1 = knockback_modifier*f1;

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



        boolean flag = entity.hurt(this.damageSources().mobAttack(this), f);
        if (flag) {
            if (f1 > 0.0F && entity instanceof LivingEntity) {
                ((LivingEntity) entity).knockback((double) (f1 * 0.5F), (double) Mth.sin(this.getYRot() * ((float) Math.PI / 180F)), (double) (-Mth.cos(this.getYRot() * ((float) Math.PI / 180F))));
                this.setDeltaMovement(this.getDeltaMovement().multiply(0.6D, 1.0D, 0.6D));
            }


            this.doEnchantDamageEffects(this, entity);
            this.setLastHurtMob(entity);
        }

        return flag;
    }




}
