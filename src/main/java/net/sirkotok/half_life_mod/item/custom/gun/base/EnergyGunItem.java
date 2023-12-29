package net.sirkotok.half_life_mod.item.custom.gun.base;

import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.sirkotok.half_life_mod.entity.projectile.Bullet;
import net.sirkotok.half_life_mod.sound.HalfLifeSounds;
import org.jetbrains.annotations.Nullable;

import java.util.List;


public class EnergyGunItem extends GunItem {


    @Override public InteractionResultHolder<ItemStack> Reload(Level pLevel, Player pPlayer, InteractionHand pHand) {
        return InteractionResultHolder.pass(pPlayer.getItemInHand(pHand));
    }

    private static final String ALTAMMO = "AltAmmo";
    public EnergyGunItem(Properties pProperties) {
        super(pProperties);
    }

    public ItemStack getaltammoitem(){
        return Items.DIAMOND.getDefaultInstance();
    }

    public static int GetAltAmmo(ItemStack gunstack) {
        CompoundTag compoundtag = gunstack.getTag();
        if (compoundtag != null) return compoundtag.getInt("AltAmmo");
        return 0;
    }
    public static void SetAltAmmo(ItemStack gunstack, int ammo) {
        CompoundTag compoundtag = gunstack.getOrCreateTag();
        compoundtag.putInt("AltAmmo", ammo);
    }







    public void SetInventoryAltAmmo(Player pPlayer, ItemStack gunstack) {
        int altammo = 0;
        Inventory inventory = pPlayer.getInventory();
        for (int i = 0; i < inventory.getContainerSize(); ++i) {
            ItemStack itemstack1 = inventory.getItem(i);
            if (itemstack1.is(this.getaltammoitem().getItem())) {
                altammo += itemstack1.getCount();
            }
        }
        SetAltAmmo(gunstack, altammo);
    }


    @Override
    public void inventoryTick(ItemStack pStack, Level pLevel, Entity pEntity, int pSlotId, boolean pIsSelected) {
        super.inventoryTick(pStack, pLevel, pEntity, pSlotId, pIsSelected);
        if (pEntity instanceof Player player && !pLevel.isClientSide()) {
        this.SetInventoryAltAmmo(player, pStack); }
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(getAmmooutofMax(pStack).withStyle(ChatFormatting.GRAY));
    }

    public MutableComponent getAmmooutofMax(ItemStack pStack) {
        return Component.literal("Ammo: "+GetAltAmmo(pStack));
    }


    public void shrinkSlotWithAltAmmo(Player pPlayer){
        Inventory inventory = pPlayer.getInventory();
        int slotwithitem = findSlotMatchingItem(pPlayer, getaltammoitem());
        ItemStack stack = inventory.getItem(slotwithitem);
        stack.shrink(1);
    }


    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pHand);
        Inventory inventory = pPlayer.getInventory();
        if ((GetAltAmmo(itemstack) == 0  || findSlotMatchingItem(pPlayer, getaltammoitem()) == -1) && !pPlayer.getAbilities().instabuild) {
            pLevel.playSound((Player) null, pPlayer.getX(), pPlayer.getY(), pPlayer.getZ(), HalfLifeSounds.DRYFIRE1.get(), SoundSource.NEUTRAL, 0.5F, 1F);
            return InteractionResultHolder.fail(itemstack);
        }
        if (GetCooldow(itemstack) > 0) return InteractionResultHolder.fail(itemstack);
        pLevel.playSound((Player) null, pPlayer.getX(), pPlayer.getY(), pPlayer.getZ(), SoundEvents.SNOWBALL_THROW, SoundSource.NEUTRAL, 0.5F, 0.4F / (pLevel.getRandom().nextFloat() * 0.4F + 0.8F));
        shootright(pLevel, pPlayer, pHand);

        return InteractionResultHolder.pass(itemstack);
    }



    @Override
    public InteractionResultHolder<ItemStack> leftuse(Level pLevel, Player pPlayer, InteractionHand pHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pHand);
        Inventory inventory = pPlayer.getInventory();
        if ((GetAltAmmo(itemstack) == 0  || findSlotMatchingItem(pPlayer, getaltammoitem()) == -1) && !pPlayer.getAbilities().instabuild) {
            pLevel.playSound((Player) null, pPlayer.getX(), pPlayer.getY(), pPlayer.getZ(), HalfLifeSounds.DRYFIRE1.get(), SoundSource.NEUTRAL, 0.5F, 1F);
            return InteractionResultHolder.fail(itemstack);
        }
        if (GetCooldow(itemstack) > 0) return InteractionResultHolder.fail(itemstack);
        pLevel.playSound((Player) null, pPlayer.getX(), pPlayer.getY(), pPlayer.getZ(), SoundEvents.SNOWBALL_THROW, SoundSource.NEUTRAL, 0.5F, 0.4F / (pLevel.getRandom().nextFloat() * 0.4F + 0.8F));
        shootleft(pLevel, pPlayer, pHand);

        return InteractionResultHolder.pass(itemstack);
    }















}



