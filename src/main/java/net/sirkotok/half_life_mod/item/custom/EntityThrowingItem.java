package net.sirkotok.half_life_mod.item.custom;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Snowball;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.ForgeEventFactory;


public abstract class EntityThrowingItem extends Item {

    //TODO:correct position of the spawned entity should be infront of the screen
    public EntityThrowingItem(Properties pProperties) {
        super(pProperties);
    }

    public EntityType getentitytype() {
        return EntityType.PIG;
    }

    public void shoot(Entity projectile, double pX, double pY, double pZ, float pVelocity, float pInaccuracy) {
        Vec3 vec3 = (new Vec3(pX, pY, pZ)).normalize().add(RandomSource.create().triangle(0.0D, 0.0172275D * (double)pInaccuracy), RandomSource.create().triangle(0.0D, 0.0172275D * (double)pInaccuracy), RandomSource.create().triangle(0.0D, 0.0172275D * (double)pInaccuracy)).scale((double)pVelocity);
        projectile.setDeltaMovement(vec3);
        projectile.yRotO = projectile.getYRot();
        projectile.xRotO = projectile.getXRot();
    }

    public void shootEntityFromRotation(Entity projectile, Entity pShooter, float pX, float pY, float pZ, float pVelocity, float pInaccuracy) {
        float f = -Mth.sin(pY * ((float)Math.PI / 180F)) * Mth.cos(pX * ((float)Math.PI / 180F));
        float f1 = -Mth.sin((pX + pZ) * ((float)Math.PI / 180F));
        float f2 = Mth.cos(pY * ((float)Math.PI / 180F)) * Mth.cos(pX * ((float)Math.PI / 180F));
        this.shoot(projectile, (double)f, (double)f1, (double)f2, pVelocity, pInaccuracy);
        Vec3 vec3 = pShooter.getDeltaMovement();
        projectile.setDeltaMovement(projectile.getDeltaMovement().add(vec3.x, pShooter.isOnGround() ? 0.0D : vec3.y, vec3.z));
    }



    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pHand);
        pLevel.playSound((Player)null, pPlayer.getX(), pPlayer.getY(), pPlayer.getZ(), SoundEvents.SNOWBALL_THROW, SoundSource.NEUTRAL, 0.5F, 0.4F / (pLevel.getRandom().nextFloat() * 0.4F + 0.8F));
        if (!pLevel.isClientSide) {
            Entity summon = getentitytype().create(pLevel);
            if (summon instanceof Mob){
                float yrot = pPlayer.getYRot();
                float yuserot = pPlayer.yHeadRot * ((float)Math.PI / 180F);
                float xr = Math.min(pPlayer.getXRot(), 60);
                float xrot = xr*((float)Math.PI / 180F);
                double bb = (pPlayer.getBbWidth() + 1.0F) * 0.5D;
                double dx = -bb * (double)Mth.sin(yuserot)*Mth.cos(xrot/1.5f)*1.2;
                double dy = -0.8*(double) Mth.sin(xrot);
                double dz = +bb * (double) Mth.cos(yuserot)*Mth.cos(xrot/1.5f)*1.2;
            summon.moveTo(pPlayer.getX() + dx,
                    pPlayer.getEyeY() + dy,
                    pPlayer.getZ() + dz,
                    yrot, 0.0f);
            this.shootEntityFromRotation(summon, pPlayer, pPlayer.getXRot(), pPlayer.getYRot(), 0.0F, 1.5F, 1.0F);
            ForgeEventFactory.onFinalizeSpawn((Mob) summon, (ServerLevelAccessor) pLevel, pLevel.getCurrentDifficultyAt(summon.blockPosition()), MobSpawnType.REINFORCEMENT, null, null);
            pLevel.addFreshEntity(summon);
                if (itemstack.hasCustomHoverName())
                    summon.setCustomName(itemstack.getHoverName());
                ((Mob) summon).setPersistenceRequired();
            }
        }





        pPlayer.awardStat(Stats.ITEM_USED.get(this));
        if (!pPlayer.getAbilities().instabuild) {
            itemstack.shrink(1);
        }

        return InteractionResultHolder.sidedSuccess(itemstack, pLevel.isClientSide());
    }



}

/*
M summon = summonType.create(level.getLevel());
        summon.moveTo(this.posX, this.posY, this.posZ);
        ForgeEventFactory.onFinalizeSpawn(summon, level, level.getCurrentDifficultyAt(summon.blockPosition()), MobSpawnType.REINFORCEMENT, null, null);
        level.addFreshEntity(summon);
 */