package net.sirkotok.half_life_mod.item.custom.gun.base;

import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.commands.SpreadPlayersCommand;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.sirkotok.half_life_mod.entity.projectile.Bullet;
import net.sirkotok.half_life_mod.sound.HalfLifeSounds;
import org.jetbrains.annotations.Nullable;

import java.util.List;


public class GunItem extends Item {


    public static int findSlotMatchingItem(Player player, ItemStack pStack) {
        Inventory inv = player.getInventory();
        for(int i = 0; i < inv.items.size(); ++i) {
            if (!inv.getItem(i).isEmpty() && ItemStack.isSame(pStack, inv.getItem(i))) {
                return i;
            }
        }

        return -1;
    }

    private static final String ATTACK_COOLDOWN = "Cooldow";
    private static final String AMMO_COUNT = "Ammo";
    private static final String RELOAD_TIMER = "ReloadTimer";

    public ItemStack getammoitem(){
        return Items.BEDROCK.getDefaultInstance();
    }

    public static void SetAmmo(ItemStack gunstack, int ammo) {
        CompoundTag compoundtag = gunstack.getOrCreateTag();
        compoundtag.putInt("Ammo", ammo);
    }
    public static int GetAmmo(ItemStack gunstack) {
        CompoundTag compoundtag = gunstack.getTag();
        if (compoundtag != null) return compoundtag.getInt("Ammo");
        return 0;
    }








    public int GetMaxAmmo() {
        return 17;
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(getAmmooutofMax(pStack).withStyle(ChatFormatting.GRAY));
    }

    public MutableComponent getAmmooutofMax(ItemStack pStack) {
        return Component.literal("Ammo: "+GetAmmo(pStack)+"\\"+GetMaxAmmo());
    }

    public static void SetupCooldow(ItemStack gunstack, int cooldown) {
        CompoundTag compoundtag = gunstack.getOrCreateTag();
        compoundtag.putInt("Cooldow", cooldown);
    }

    public static void SetReloadTimer(ItemStack gunstack, int cooldown) {
        CompoundTag compoundtag = gunstack.getOrCreateTag();
        compoundtag.putInt("ReloadTimer", cooldown);
    }
    public static int GetReloadTimer(ItemStack gunstack) {
        CompoundTag compoundtag = gunstack.getTag();
        if (compoundtag != null) return compoundtag.getInt("ReloadTimer");
        return -1;
    }


    public static void SetCooldow(Player pplayer, int cooldown){
        Inventory playerinv = pplayer.getInventory();
        for (ItemStack stack : playerinv.items) {
            SetupCooldow(stack, cooldown);
        }
    }


    public static int GetCooldow(ItemStack gunstack) {
        CompoundTag compoundtag = gunstack.getTag();
        if (compoundtag != null) return compoundtag.getInt("Cooldow");
        return 0;
    }



    @Override
    public void inventoryTick(ItemStack pStack, Level pLevel, Entity pEntity, int pSlotId, boolean pIsSelected) {
        if (pEntity instanceof Player pPlayer && !pLevel.isClientSide() && pIsSelected) {
          ItemStack pPlayerItemStack =  pPlayer.getOffhandItem();
            if (!pPlayerItemStack.isEmpty() && pPlayerItemStack.is(Items.SHIELD) && pPlayer.isUsingItem()) {
                    pPlayer.getCooldowns().addCooldown(Items.SHIELD, 10);
                    pLevel.broadcastEntityEvent(pPlayer, (byte)30);
                    pPlayer.stopUsingItem();
                }
        }



        if (pEntity instanceof Player pplayer) {
        if (GetCooldow(pStack)>0) {
            int i = GetCooldow(pStack);
            SetupCooldow(pStack, i-1);
        }
        if (GetReloadTimer(pStack) > -1) {
            int i = GetReloadTimer(pStack);
            if (i == 0 && pIsSelected && !pLevel.isClientSide()) {
                reloadgun(pLevel, (Player) pEntity, pStack);
            }
            SetReloadTimer(pStack, i-1);
        }
    }
    }




    public SoundEvent shootrightsound(){
        return HalfLifeSounds.PISTOL_SHOOT.get();
    }

    public SoundEvent shootleftsound(){
        return HalfLifeSounds.PISTOL_SHOOT.get();
    }

    public SoundEvent reloadsound(){
        return HalfLifeSounds.PISTOL_RELOAD.get();
    }

    public GunItem(Properties pProperties) {
        super(pProperties.stacksTo(1));
    }

    public GunItem(Properties pProperties, int amogus) {
        super(pProperties);
    }


    public int getRightClickCooldown(){
        return 4;
    }
    public int getLeftClickCooldown(){
        return 6;
    }
    public int getReloadCooldown(){
        return 20;
    }
    public float getgundamage(){
        return 3.5f;
    }


    public void award(Player pPlayer) {
        pPlayer.awardStat(Stats.ITEM_USED.get(this));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pHand);
        if (GetAmmo(itemstack) == 0 && !pPlayer.getAbilities().instabuild) {
            pLevel.playSound((Player) null, pPlayer.getX(), pPlayer.getY(), pPlayer.getZ(), HalfLifeSounds.DRYFIRE1.get(), SoundSource.NEUTRAL, 0.5F, 1F);
            return InteractionResultHolder.fail(itemstack);
        }
        if (GetCooldow(itemstack) > 0) return InteractionResultHolder.fail(itemstack);
        pLevel.playSound((Player) null, pPlayer.getX(), pPlayer.getY(), pPlayer.getZ(), this.shootrightsound(), SoundSource.NEUTRAL, 0.5F, 0.4F / (pLevel.getRandom().nextFloat() * 0.4F + 0.8F));
        shootright(pLevel, pPlayer, pHand);

        return InteractionResultHolder.pass(itemstack);
    }

    public InteractionResultHolder<ItemStack> Reload(Level pLevel, Player pPlayer, InteractionHand pHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pHand);
        int slotwithitem = findSlotMatchingItem(pPlayer, getammoitem());
        if (slotwithitem == -1 && !pPlayer.getAbilities().instabuild) return InteractionResultHolder.fail(itemstack);
        if (!pLevel.isClientSide) {
            if (GetAmmo(itemstack) != GetMaxAmmo() || pPlayer.getAbilities().instabuild) {
            if (GetCooldow(itemstack) > 0) return InteractionResultHolder.fail(itemstack);
            SetReloadTimer(itemstack, getReloadCooldown());
            pLevel.playSound((Player) null, pPlayer.getX(), pPlayer.getY(), pPlayer.getZ(), this.reloadsound(), SoundSource.NEUTRAL, 0.5F, 1F);
                SetCooldow(pPlayer, getReloadCooldown());
        }}
        return InteractionResultHolder.pass(itemstack);
    }

    public InteractionResultHolder<ItemStack> leftuse(Level pLevel, Player pPlayer, InteractionHand pHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pHand);
        if (GetAmmo(itemstack) == 0 && !pPlayer.getAbilities().instabuild) {
            pLevel.playSound((Player) null, pPlayer.getX(), pPlayer.getY(), pPlayer.getZ(), HalfLifeSounds.DRYFIRE1.get(), SoundSource.NEUTRAL, 0.5F, 1F);
            return InteractionResultHolder.fail(itemstack);
        }
        if (GetCooldow(itemstack) > 0) return InteractionResultHolder.fail(itemstack);
        pLevel.playSound((Player) null, pPlayer.getX(), pPlayer.getY(), pPlayer.getZ(), this.shootleftsound(), SoundSource.NEUTRAL, 0.5F, 0.4F / (pLevel.getRandom().nextFloat() * 0.4F + 0.8F));
        shootleft(pLevel, pPlayer, pHand);

        return InteractionResultHolder.pass(itemstack);
    }


    public void shootright(Level pLevel, Player pPlayer, InteractionHand pHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pHand);
        if (!pLevel.isClientSide) {
            pPlayer.level.gameEvent(pPlayer, GameEvent.PROJECTILE_SHOOT, pPlayer.blockPosition());
            SetCooldow(pPlayer, getRightClickCooldown());
            Bullet snowball = new Bullet(pLevel, pPlayer);
            snowball.setdamage(this.getgundamage());
            snowball.shootFromRotation(pPlayer, pPlayer.getXRot(), pPlayer.getYRot(), 0.0F, 4F, 6.0F);
            pLevel.addFreshEntity(snowball);
        }
        award(pPlayer);
        if (!pPlayer.getAbilities().instabuild) SetAmmo(itemstack, GetAmmo(itemstack)-1);
    }


    @Override
    public boolean onEntitySwing(ItemStack stack, LivingEntity entity) {
        return true;
    }




    @Override
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
        return super.shouldCauseReequipAnimation(oldStack, newStack, slotChanged) && slotChanged;
    }

    public void shootleft(Level pLevel, Player pPlayer, InteractionHand pHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pHand);
        if (!pLevel.isClientSide) {
            pPlayer.level.gameEvent(pPlayer, GameEvent.PROJECTILE_SHOOT, pPlayer.blockPosition());
           SetCooldow(pPlayer, getLeftClickCooldown());
           Bullet snowball = new Bullet(pLevel, pPlayer);
            snowball.setdamage(this.getgundamage());
          snowball.shootFromRotation(pPlayer, pPlayer.getXRot(), pPlayer.getYRot(), 0.0F, 4F, 1.0F);
            pLevel.addFreshEntity(snowball);
      }
        award(pPlayer);
       if (!pPlayer.getAbilities().instabuild) SetAmmo(itemstack, GetAmmo(itemstack)-1);
    }

    public void reloadgun(Level pLevel, Player pPlayer, ItemStack itemstack) {
        if (GetAmmo(itemstack) != GetMaxAmmo() && !pPlayer.getAbilities().instabuild)
        {
            while (true) {
                pPlayer.level.gameEvent(pPlayer, GameEvent.PROJECTILE_SHOOT, pPlayer.blockPosition());
                SetCooldow(pPlayer, getReloadCooldown());
                int slotwithitem = findSlotMatchingItem(pPlayer, getammoitem());
                if (slotwithitem == -1) break;
                int sub = GetMaxAmmo() - GetAmmo(itemstack);
                ItemStack inslot = pPlayer.getInventory().getItem(slotwithitem);
                int count = inslot.getCount();
                if (count < sub){
                    inslot.shrink(count);
                    SetAmmo(itemstack, GetAmmo(itemstack)+count);
                } else {
                    inslot.shrink(sub);
                    SetAmmo(itemstack,GetMaxAmmo());
                    break;
                }
            }
        }
        if (pPlayer.getAbilities().instabuild) {
            SetCooldow(pPlayer, getReloadCooldown());
            SetAmmo(itemstack,GetMaxAmmo());
        }
    }




}



