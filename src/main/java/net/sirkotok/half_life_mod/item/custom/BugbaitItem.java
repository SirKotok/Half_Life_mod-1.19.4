package net.sirkotok.half_life_mod.item.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.sirkotok.half_life_mod.misc.effect.HalfLifeEffects;
import net.sirkotok.half_life_mod.entity.base.HalfLifeMonster;
import net.sirkotok.half_life_mod.entity.brain.ModMemoryModuleType;
import net.sirkotok.half_life_mod.entity.mob.mob_geckolib.custom.Antlion;
import net.sirkotok.half_life_mod.entity.mob.mob_geckolib.custom.AntlionWorker;
import net.sirkotok.half_life_mod.entity.mob.modinterface.HasLeaderMob;
import net.sirkotok.half_life_mod.entity.projectile.BugbaitProjectile;
import net.sirkotok.half_life_mod.item.custom.gun.base.GunItem;
import net.sirkotok.half_life_mod.sound.HalfLifeSounds;
import net.sirkotok.half_life_mod.misc.util.CommonSounds;
import net.tslat.smartbrainlib.util.BrainUtils;
import net.tslat.smartbrainlib.util.EntityRetrievalUtil;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class BugbaitItem extends GunItem {
    public BugbaitItem(Properties pProperties) {
        super(pProperties.durability(512), 1);
    }


    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
    }

    public int getRightClickCooldown(){
        return 30;
    }
    public int getLeftClickCooldown(){
        return 10;
    }
    public int getReloadCooldown(){
        return 20;
    }
    @Override
    public InteractionResultHolder<ItemStack> Reload(Level pLevel, Player pPlayer, InteractionHand pHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pHand);
        if (GetCooldow(itemstack) > 0) return InteractionResultHolder.fail(itemstack);
        pLevel.playSound((Player) null, pPlayer.getX(), pPlayer.getY(), pPlayer.getZ(), this.getSqeezeSound(), SoundSource.NEUTRAL, 0.5F, 0.4F / (pLevel.getRandom().nextFloat() * 0.4F + 0.8F));
        SetCooldow(pPlayer, getReloadCooldown());
        if (!pLevel.isClientSide) {

            itemstack.hurtAndBreak(1, pPlayer, (p_43076_) -> {
                p_43076_.broadcastBreakEvent(EquipmentSlot.MAINHAND);
            });

            BlockPos pBlockPos = pPlayer.blockPosition();
            int rad = 16;
            List<HalfLifeMonster> antlions = EntityRetrievalUtil.getEntities((Level) pLevel,
                    new AABB(pBlockPos.getX() - rad, pBlockPos.getY() - rad, pBlockPos.getZ() - rad,
                            pBlockPos.getX() + rad, pBlockPos.getY() + rad, pBlockPos.getZ() + rad), obj -> obj instanceof Antlion || obj instanceof AntlionWorker);
            float f = 1;
            for (HalfLifeMonster y : antlions) {
                if (y instanceof HasLeaderMob<?> x){
                    x.setLeader(y);
                    BrainUtils.clearMemory(y, ModMemoryModuleType.NOANTLIONATTACK.get());
                    if (RandomSource.create().nextFloat() < f) {
                        CommonSounds.PlaySoundQuiet(y, HalfLifeSounds.ANT_DISTRACT1.get());
                        f = f*0.7f;
                    }
                }}
        }


        pPlayer.awardStat(Stats.ITEM_USED.get(this));


        return InteractionResultHolder.consume(itemstack);
    }

    public SoundEvent getSqeezeSound(){
       float f = RandomSource.create().nextFloat();
        return f < 0.33f ? HalfLifeSounds.BUGBAIT_SQUEEZE1.get() : (f > 0.66f ? HalfLifeSounds.BUGBAIT_SQUEEZE2.get() : HalfLifeSounds.BUGBAIT_SQUEEZE3.get());
    }

    @Override
    public InteractionResultHolder<ItemStack> leftuse(Level pLevel, Player pPlayer, InteractionHand pHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pHand);
        if (GetCooldow(itemstack) > 0) return InteractionResultHolder.fail(itemstack);
        pLevel.playSound((Player) null, pPlayer.getX(), pPlayer.getY(), pPlayer.getZ(), SoundEvents.SNOWBALL_THROW , SoundSource.NEUTRAL, 0.5F, 0.4F / (pLevel.getRandom().nextFloat() * 0.4F + 0.8F));

        SetCooldow(pPlayer, getLeftClickCooldown());
        if (!pLevel.isClientSide) {
            itemstack.hurtAndBreak(1, pPlayer, (p_43076_) -> {
                p_43076_.broadcastBreakEvent(EquipmentSlot.MAINHAND);
            });
            BugbaitProjectile snowball = new BugbaitProjectile(pLevel, pPlayer);
            snowball.setItem(itemstack);
            snowball.shootFromRotation(pPlayer, pPlayer.getXRot(), pPlayer.getYRot(), 0.0F, 1.5F, 1.0F);
            pLevel.addFreshEntity(snowball);
        }

        pPlayer.awardStat(Stats.ITEM_USED.get(this));


        return InteractionResultHolder.sidedSuccess(itemstack, pLevel.isClientSide());
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pHand);
        if (GetCooldow(itemstack) > 0) return InteractionResultHolder.fail(itemstack);
        pLevel.playSound((Player) null, pPlayer.getX(), pPlayer.getY(), pPlayer.getZ(), this.getSqeezeSound(), SoundSource.NEUTRAL, 0.5F, 0.4F / (pLevel.getRandom().nextFloat() * 0.4F + 0.8F));
        if (!pLevel.isClientSide) {
            SetCooldow(pPlayer, getRightClickCooldown());
          int effect =  pPlayer.hasEffect(HalfLifeEffects.ANTLION_PHEROMONE_FRIEND.get()) ? 2 : 4;

           itemstack.hurtAndBreak(effect, pPlayer, (p_43076_) -> {
                p_43076_.broadcastBreakEvent(EquipmentSlot.MAINHAND);
            });

            pPlayer.addEffect(new MobEffectInstance(HalfLifeEffects.ANTLION_PHEROMONE_FRIEND.get(), 2400, 1, false, true, true));
            BlockPos pBlockPos = pPlayer.blockPosition();
            int rad = 16;

            List<HalfLifeMonster> antlions = EntityRetrievalUtil.getEntities((Level) pLevel,
                    new AABB(pBlockPos.getX() - rad, pBlockPos.getY() - rad, pBlockPos.getZ() - rad,
                            pBlockPos.getX() + rad, pBlockPos.getY() + rad, pBlockPos.getZ() + rad), obj -> obj instanceof Antlion || obj instanceof AntlionWorker);
            float f = 1;
            for (HalfLifeMonster y : antlions) {
                if (y instanceof HasLeaderMob<?> x){
                    x.setLeader(pPlayer);
                    BrainUtils.clearMemory(y, ModMemoryModuleType.NOANTLIONATTACK.get());

                    if (RandomSource.create().nextFloat() < f) {
                    CommonSounds.PlaySoundQuiet(y, HalfLifeSounds.ANT_DISTRACT1.get());
                    f = f*0.7f;
                    }
                }}
        }


        pPlayer.awardStat(Stats.ITEM_USED.get(this));


        return InteractionResultHolder.consume(itemstack);
    }






}
