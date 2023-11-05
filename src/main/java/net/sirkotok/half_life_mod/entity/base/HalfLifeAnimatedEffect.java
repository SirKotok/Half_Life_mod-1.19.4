package net.sirkotok.half_life_mod.entity.base;

import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import net.sirkotok.half_life_mod.entity.mob_geckolib.custom.Houndeye;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

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
