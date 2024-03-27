package net.sirkotok.half_life_mod.entity.projectile;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.sirkotok.half_life_mod.entity.HalfLifeEntities;
import net.sirkotok.half_life_mod.entity.base.FireballNoTrail;
import net.sirkotok.half_life_mod.item.HalfLifeItems;
import net.sirkotok.half_life_mod.entity.particle.HalfLifeParticles;
import net.sirkotok.half_life_mod.sound.HalfLifeSounds;

public class ShockProjectile extends FireballNoTrail {
    public ShockProjectile(EntityType<ShockProjectile> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public ShockProjectile(Level pLevel, LivingEntity pShooter) {
        super(HalfLifeEntities.SHOCK_SHOT.get(), pShooter, pLevel);
    }

    public ShockProjectile(Level pLevel, LivingEntity pShooter, double pOffsetX, double pOffsetY, double pOffsetZ) {
        super(HalfLifeEntities.SHOCK_SHOT.get(), pShooter, pOffsetX, pOffsetY, pOffsetZ, pLevel);
    }

    public ShockProjectile(Level pLevel, double pX, double pY, double pZ, double pOffsetX, double pOffsetY, double pOffsetZ) {
        super(HalfLifeEntities.SHOCK_SHOT.get(), pX, pY, pZ, pOffsetX, pOffsetY, pOffsetZ, pLevel);
    }





    @Override
    protected boolean shouldBurn() {
        return false;
    }


    @Override
    public ItemStack GetAllwaysItem() {
        return HalfLifeItems.FAKE_SHOCK.get().getDefaultInstance();
    }


    protected SoundEvent getHitSound() {
        return HalfLifeSounds.SHOCK_IMPACT.get();
    }


    public float getDamageEmount(){
        return 2.5f;
    }


    public void makeParticle(int direction, BlockHitResult blockhit) {
            switch(direction){
                case 0:  this.getLevel().addAlwaysVisibleParticle(HalfLifeParticles.SHOCK_IMPACT.get(), blockhit.getLocation().x(), blockhit.getLocation().y()-0.01f, blockhit.getLocation().z(), direction, 0, 0);
                break;
                case 1: this.getLevel().addAlwaysVisibleParticle(HalfLifeParticles.SHOCK_IMPACT.get(), blockhit.getLocation().x(), blockhit.getLocation().y()+0.01f, blockhit.getLocation().z(), direction, 0, 0);
                    break;
                case 2: this.getLevel().addAlwaysVisibleParticle(HalfLifeParticles.SHOCK_IMPACT.get(), blockhit.getLocation().x(), blockhit.getLocation().y(), blockhit.getLocation().z()-0.01f, direction, 0, 0);
                    break;
                case 3: this.getLevel().addAlwaysVisibleParticle(HalfLifeParticles.SHOCK_IMPACT.get(), blockhit.getLocation().x(), blockhit.getLocation().y(), blockhit.getLocation().z()+0.01f, direction, 0, 0);
                    break;
                case 4: this.getLevel().addAlwaysVisibleParticle(HalfLifeParticles.SHOCK_IMPACT.get(), blockhit.getLocation().x()-0.01f, blockhit.getLocation().y(), blockhit.getLocation().z(), direction, 0, 0);
                    break;
                case 5: this.getLevel().addAlwaysVisibleParticle(HalfLifeParticles.SHOCK_IMPACT.get(), blockhit.getLocation().x()+0.01f, blockhit.getLocation().y(), blockhit.getLocation().z(), direction, 0, 0);
                    break;
        }
    }

    protected void onHitEntity(EntityHitResult pResult) {
        super.onHitEntity(pResult);
        if (!this.level.isClientSide) {
            Entity entity = pResult.getEntity();
            Entity entity1 = this.getOwner();
            if (entity1 instanceof LivingEntity) {
                LivingEntity shooter = (LivingEntity) entity1;
                entity.hurt(this.damageSources().mobProjectile(this, shooter), getDamageEmount());
            }
        }
    }
    protected void onHitBlock(BlockHitResult blockhit) {
        super.onHitBlock(blockhit);
        // Ordering index for D-U-N-S-W-E (DOWN UP NORTH SOUTH WEST EAST)
        int direction = blockhit.getDirection().get3DDataValue();
        if (this.level.isClientSide()){
            this.makeParticle(direction, blockhit);
            this.discard();
        }}

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


    public boolean hurt(DamageSource pSource, float pAmount) {
        return false;
    }





}
