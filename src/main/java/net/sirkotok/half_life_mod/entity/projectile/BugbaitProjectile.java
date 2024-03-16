package net.sirkotok.half_life_mod.entity.projectile;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.WalkTarget;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.sirkotok.half_life_mod.misc.effect.HalfLifeEffects;
import net.sirkotok.half_life_mod.entity.HalfLifeEntities;
import net.sirkotok.half_life_mod.entity.base.HalfLifeMonster;
import net.sirkotok.half_life_mod.entity.brain.ModMemoryModuleType;
import net.sirkotok.half_life_mod.entity.mob_geckolib.custom.Antlion;
import net.sirkotok.half_life_mod.entity.mob_geckolib.custom.AntlionWorker;
import net.sirkotok.half_life_mod.entity.modinterface.HasLeaderMob;
import net.sirkotok.half_life_mod.item.HalfLifeItems;
import net.sirkotok.half_life_mod.sound.HalfLifeSounds;
import net.sirkotok.half_life_mod.misc.util.CommonSounds;
import net.sirkotok.half_life_mod.misc.util.HLTags;
import net.tslat.smartbrainlib.util.BrainUtils;
import net.tslat.smartbrainlib.util.EntityRetrievalUtil;

import java.util.List;

public class BugbaitProjectile extends ThrowableItemProjectile {
    public BugbaitProjectile(EntityType<? extends BugbaitProjectile> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public BugbaitProjectile(Level pLevel, LivingEntity pShooter) {
        super(HalfLifeEntities.BUGBAIT_PROJECTILE.get(), pShooter, pLevel);
    }

    public BugbaitProjectile(Level pLevel, double pX, double pY, double pZ) {
        super(HalfLifeEntities.BUGBAIT_PROJECTILE.get(), pX, pY, pZ, pLevel);
    }

    protected Item getDefaultItem() {
        return HalfLifeItems.BUGBAIT.get();
    }

    public SoundEvent hitsound() {
        return random.nextFloat() > 0.5f ? HalfLifeSounds.BUGBAIT_IMPACT1.get() : HalfLifeSounds.BUGBAIT_IMPACT3.get();
    }

    private ParticleOptions getParticle() {
        ItemStack itemstack = this.getItemRaw();
        return (ParticleOptions)(itemstack.isEmpty() ? ParticleTypes.ITEM_SNOWBALL : new ItemParticleOption(ParticleTypes.ITEM, itemstack));
    }




    public void handleEntityEvent(byte pId) {
        if (pId == 3) {
            ParticleOptions particleoptions = this.getParticle();

            for(int i = 0; i < 8; ++i) {
                this.level.addParticle(particleoptions, this.getX(), this.getY(), this.getZ(), 0.0D, 0.0D, 0.0D);
            }
        }

    }

    /**
     * Called when the arrow hits an entity
     */
    protected void onHitEntity(EntityHitResult pResult) {
        super.onHitEntity(pResult);
        Entity entity = pResult.getEntity();
        if (entity.getType().is(HLTags.EntityTypes.FACTION_ANTLION)) return;

        if (!entity.level.isClientSide() && entity instanceof LivingEntity enemy) {
            ServerLevel pLevel = (ServerLevel) entity.level;
            enemy.addEffect(new MobEffectInstance(HalfLifeEffects.ANTLION_PHEROMONE_FOE.get(), 600, 1, false, true, false));
            BlockPos pBlockPos = enemy.blockPosition();
            int rad = 16;
            List<HalfLifeMonster> antlions = EntityRetrievalUtil.getEntities((Level) pLevel,
                    new AABB(pBlockPos.getX() - rad, pBlockPos.getY() - rad, pBlockPos.getZ() - rad,
                            pBlockPos.getX() + rad, pBlockPos.getY() + rad, pBlockPos.getZ() + rad), obj -> obj instanceof Antlion || obj instanceof AntlionWorker);
            float f = 1;
            for (HalfLifeMonster y : antlions) {
                if (y instanceof HasLeaderMob<?> x){
                x.setLeader(y);
                BrainUtils.clearMemory(y, MemoryModuleType.WALK_TARGET);
                BrainUtils.clearMemory(y, ModMemoryModuleType.NOANTLIONATTACK.get());
                BrainUtils.setMemory(y, MemoryModuleType.ATTACK_TARGET, enemy);
                    if (RandomSource.create().nextFloat() < f) {
                        CommonSounds.PlaySoundQuiet(y, HalfLifeSounds.ANT_DISTRACT1.get());
                        f = f*0.7f;
                    }
            }}
        }


    }

    @Override
    protected void onHitBlock(BlockHitResult pResult) {
        super.onHitBlock(pResult);
        if (!level.isClientSide()) {
            ServerLevel pLevel = (ServerLevel) level;
            BlockPos pBlockPos = pResult.getBlockPos();
            int rad = 16;
            List<HalfLifeMonster> antlions = EntityRetrievalUtil.getEntities((Level) pLevel,
                    new AABB(pBlockPos.getX() - rad, pBlockPos.getY() - rad, pBlockPos.getZ() - rad,
                            pBlockPos.getX() + rad, pBlockPos.getY() + rad, pBlockPos.getZ() + rad), obj -> obj instanceof Antlion || obj instanceof AntlionWorker);
            float f = 1;
            for (HalfLifeMonster y : antlions) {
                if (y instanceof HasLeaderMob<?> x){
                    x.setLeader(y);
                    BrainUtils.setMemory(y, MemoryModuleType.WALK_TARGET, new WalkTarget(pBlockPos, 1.2f, 1));
                    BrainUtils.setForgettableMemory(y, ModMemoryModuleType.NOANTLIONATTACK.get(), true, 200);
                    BrainUtils.clearMemory(y, MemoryModuleType.ATTACK_TARGET);
                    if (RandomSource.create().nextFloat() < f) {
                        CommonSounds.PlaySoundQuiet(y, HalfLifeSounds.ANT_DISTRACT1.get());
                        f = f*0.7f;
                    }
                }}
        }
    }

    /**
     * Called when this EntityFireball hits a block or entity.
     */


    protected void onHit(HitResult pResult) {
        super.onHit(pResult);
        if (!this.level.isClientSide) {
            playSound(this.hitsound());
            this.level.broadcastEntityEvent(this, (byte)3);
            this.discard();
        }

    }
}