package net.sirkotok.half_life_mod.entity.base;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.level.Level;

public class HalfLifeAnimatedEffect extends PathfinderMob {
    public HalfLifeAnimatedEffect(EntityType type, Level level) {
        super(type, level);
        this.xpReward = 0;
        this.noCulling = true;
    }


    public int remainingticks() {
        return 60;
    }


    @Override
    public boolean isPickable() {
        return false;
    }


    @Override
    public boolean hurt(DamageSource pSource, float pAmount) {
        return false;
    }

    @Override
    public boolean canBeAffected(MobEffectInstance pEffectInstance) {
        return false;
    }

    @Override
    public boolean canBeSeenByAnyone() {
        return false;
    }

    @Override
    public void tick() {
        super.tick();
        this.setDeltaMovement(0, 0, 0);
        this.setPos(this.getX(), this.getY(), this.getZ());
        if (this.tickCount > remainingticks()) this.discard();
    }

    @Override
    public boolean isNoGravity() {
        return true;
    }





}
