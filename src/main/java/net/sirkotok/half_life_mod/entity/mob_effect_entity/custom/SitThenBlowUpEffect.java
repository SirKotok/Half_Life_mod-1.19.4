package net.sirkotok.half_life_mod.entity.mob_effect_entity.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.sirkotok.half_life_mod.entity.base.HalfLifeAnimatedEffect;
import net.sirkotok.half_life_mod.misc.util.HLperUtil;
import net.sirkotok.half_life_mod.misc.util.HLTags;
import net.tslat.smartbrainlib.util.EntityRetrievalUtil;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.List;


public class SitThenBlowUpEffect extends HalfLifeAnimatedEffect implements GeoEntity {
    private AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    public SitThenBlowUpEffect(EntityType type, Level level) {
        super(type, level);
        this.xpReward = 0;
        this.noCulling = true;
    }

    protected LivingEntity own;
    public void setOwn(LivingEntity entity){
        this.own = entity;
    }
    public boolean notdone = true;
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

    public void blowup(){
        int rad = 3;
       BlockPos pBlockPos = this.blockPosition();
       Level serverlevel = this.level;
        List<LivingEntity> targets = EntityRetrievalUtil.getEntities((Level) serverlevel,
                new AABB(pBlockPos.getX() - rad, pBlockPos.getY() - rad, pBlockPos.getZ() - rad,
                        pBlockPos.getX() + rad, pBlockPos.getY() + rad, pBlockPos.getZ() + rad), obj -> !(obj.getType().is(HLTags.EntityTypes.FACTION_HEADCRAB)) && obj instanceof LivingEntity);
        for (LivingEntity entity2 : targets) {
                entity2.hurt(this.damageSources().explosion(null, null ), 30f);
                HLperUtil.DisableShieldFor(entity2, 0.1f, 20, (ServerLevel) serverlevel);
            }
        }













    @Override
    public void tick() {
        super.tick();
        this.setDeltaMovement(0, 0, 0);
        this.setPos(this.getX(), this.getY(), this.getZ());
        if (this.tickCount > 60 && notdone && !this.level.isClientSide()) {
            this.blowup();
            notdone = false;}
        if (this.tickCount > 65) this.discard();
    }

    @Override
    public boolean isNoGravity() {
        return true;
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
    }


    public static AttributeSupplier setAttributes() {
        return Monster.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 20D)
                .add(Attributes.ATTACK_DAMAGE, 0f)
                .add(Attributes.ATTACK_SPEED, 0f)
                .add(Attributes.ATTACK_KNOCKBACK, 0f)
                .add(Attributes.MOVEMENT_SPEED, 0f).build();
    }


    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
       }



    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }


}
