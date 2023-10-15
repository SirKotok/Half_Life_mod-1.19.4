package net.sirkotok.half_life_mod.entity.mob.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.sirkotok.half_life_mod.item.ModItems;
import net.sirkotok.half_life_mod.util.ModTags;

public class Headcrab_Armored extends Headcrab_3{



    public static AttributeSupplier setAttributes() {
        return Monster.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 1D)
                .add(Attributes.ATTACK_DAMAGE, 3f)
                .add(Attributes.ATTACK_SPEED, 1.0f)
                .add(Attributes.ATTACK_KNOCKBACK, 1.0f)
                .add(Attributes.MOVEMENT_SPEED, 0.22f).build();
    }


    public Headcrab_Armored(EntityType type, Level level) {
        super(type, level);
        this.xpReward = 5;
    }

    public static final EntityDataAccessor<Boolean> IS_VULNERABLE = SynchedEntityData.defineId(Headcrab_3.class, EntityDataSerializers.BOOLEAN);

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(IS_VULNERABLE, false);
    }

    public boolean vulnerable() {return this.entityData.get(IS_VULNERABLE);}
    public void setIsVulnerable(boolean vulnerable) {
        this.entityData.set(IS_VULNERABLE, vulnerable);
    }


    @Override
    public boolean hurt(DamageSource pSource, float pAmount) {
        if (pSource.is(DamageTypeTags.IS_PROJECTILE) && !this.vulnerable()) {
            return false; }
        return super.hurt(pSource, pAmount);
    }

    @Override
    protected void actuallyHurt (DamageSource damageSource, float v) {
        if (damageSource.is(DamageTypes.IN_WALL)) {
            return; }

        if ((!this.vulnerable() && (damageSource.is(ModTags.DamageTypes.HEADCRAB_ARMOR_PROTECTS_FROM)))) {
           if (this.getVehicle() != null) {
               if (this.isPassenger() && damageSource.getEntity().equals(this.getVehicle())){
               super.actuallyHurt(damageSource, v);
               return;
           }
           }
            return;
        }
        super.actuallyHurt(damageSource, v);
    }
}
