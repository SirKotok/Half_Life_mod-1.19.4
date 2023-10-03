package net.sirkotok.half_life_mod.entity.projectile;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Fireball;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkHooks;
import net.sirkotok.half_life_mod.entity.ModEntities;
import net.sirkotok.half_life_mod.entity.base.FireballNoTrail;
import net.sirkotok.half_life_mod.sound.ModSounds;

public class AcidBall extends FireballNoTrail {
    public AcidBall(EntityType<AcidBall> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.setItem(Items.SLIME_BALL.getDefaultInstance());
    }
    public AcidBall(Level pLevel, LivingEntity pShooter, double pOffsetX, double pOffsetY, double pOffsetZ) {
        super(ModEntities.ACID_BALL.get(), pShooter, pOffsetX, pOffsetY, pOffsetZ, pLevel);
    }

    public AcidBall(Level pLevel, double pX, double pY, double pZ, double pOffsetX, double pOffsetY, double pOffsetZ) {
        super(ModEntities.ACID_BALL.get(), pX, pY, pZ, pOffsetX, pOffsetY, pOffsetZ, pLevel);
    }


    @Override
    protected boolean shouldBurn() {
        return false;
    }

    protected SoundEvent getAcidSound(){
            switch (this.random.nextInt(1,3)) {
                case 1:  return ModSounds.BULLSQUID_ACID_1.get();
                case 2:  return ModSounds.BULLSQUID_ACID_2.get();
            }
            return ModSounds.HEADCRAB_1_DIE_1.get();
    }

    protected SoundEvent getHitSound(){
        switch (this.random.nextInt(1,4)) {
            case 1:  return ModSounds.BULLSQUID_SPIT_1.get();
            case 2:  return ModSounds.BULLSQUID_SPIT_2.get();
            case 3:  return ModSounds.BULLSQUID_SPIT_3.get();
        }
        return ModSounds.HEADCRAB_1_DIE_1.get();
    }

    protected void onHitEntity(EntityHitResult pResult) {
        super.onHitEntity(pResult);
        if (!this.level.isClientSide) {
            Entity entity = pResult.getEntity();
            playSound(this.getAcidSound(), 0.3f, 1f);
            Entity entity1 = this.getOwner();
            if (entity1 instanceof LivingEntity) {
                LivingEntity bullsquid = (LivingEntity) entity1;
                entity.hurt(this.damageSources().mobProjectile(this, bullsquid), 5f);
            }
        }
    }

    protected void onHitBlock(BlockHitResult pResult) {
        super.onHitBlock(pResult);
    }

    /**
     * Called when this EntityFireball hits a block or entity.
     */
    protected void onHit(HitResult pResult) {
        super.onHit(pResult);
        if (!this.level.isClientSide) {
            playSound(this.getHitSound(), 0.3f, 1f);
            this.discard();
        }

    }

    /**
     * Returns {@code true} if other Entities should be prevented from moving through this Entity.
     */
    public boolean isPickable() {
        return false;
    }

    /**
     * Called when the entity is attacked.
     */
    public boolean hurt(DamageSource pSource, float pAmount) {
        return false;
    }






}
