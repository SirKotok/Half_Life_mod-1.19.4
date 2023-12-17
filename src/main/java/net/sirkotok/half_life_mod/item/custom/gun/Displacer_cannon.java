package net.sirkotok.half_life_mod.item.custom.gun;

import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import net.sirkotok.half_life_mod.entity.projectile.client.TeleportingBullet;
import net.sirkotok.half_life_mod.item.client.renderer.Displacer_cannon_renderer;
import net.sirkotok.half_life_mod.item.custom.gun.base.EnergyGunItem;
import net.sirkotok.half_life_mod.item.custom.gun.base.GunAltFireItem;
import net.sirkotok.half_life_mod.sound.HalfLifeSounds;
import net.sirkotok.half_life_mod.worldgen.dimension.HalfLifeDimensions;
import net.sirkotok.half_life_mod.worldgen.portal.XenTeleporter;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.animatable.SingletonGeoAnimatable;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;
import software.bernie.geckolib.util.RenderUtils;

import java.util.function.Consumer;

public class Displacer_cannon extends EnergyGunItem implements GeoItem {

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);



    public static void SetShootLeftTimer(ItemStack gunstack, int cooldown) {
        CompoundTag compoundtag = gunstack.getOrCreateTag();
        compoundtag.putInt("ShootLeftTimer", cooldown);
    }
    public static int GetShootLeftTimer(ItemStack gunstack) {
        CompoundTag compoundtag = gunstack.getTag();
        if (compoundtag != null) return compoundtag.getInt("ShootLeftTimer");
        return -1;
    }

    public static void SetShootRightTimer(ItemStack gunstack, int cooldown) {
        CompoundTag compoundtag = gunstack.getOrCreateTag();
        compoundtag.putInt("ShootRightTimer", cooldown);
    }
    public static int GetShootRightTimer(ItemStack gunstack) {
        CompoundTag compoundtag = gunstack.getTag();
        if (compoundtag != null) return compoundtag.getInt("ShootRightTimer");
        return -1;
    }





    @Override
    public ItemStack getaltammoitem(){
        return Items.DIAMOND.getDefaultInstance();
    }
    public Displacer_cannon(Properties pProperties) {
        super(pProperties);
        SingletonGeoAnimatable.registerSyncedAnimatable(this);
    }

   @Override
    public int getRightClickCooldown(){
        return 30;
    } //4
    @Override
    public int getLeftClickCooldown(){
        return 30;
    } //6
    @Override
    public int getReloadCooldown(){
        return 20;
    }

    @Override
    public float getgundamage() {
        return 1f;
    }
    @Override public int GetMaxAmmo() {
        return 50;
    }


    public SoundEvent getShootingSound(){
        return HalfLifeSounds.DISPLACER_SPIN.get();
    }
    public SoundEvent getAltfireSound(){
        return HalfLifeSounds.DISPLACER_SPIN2.get();
    }


    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pHand);
        Inventory inventory = pPlayer.getInventory();
        if (!pLevel.isClientSide) {
            if ((GetAltAmmo(itemstack) == 0  || inventory.findSlotMatchingItem(getaltammoitem()) == -1) && !pPlayer.getAbilities().instabuild) {
                pLevel.playSound((Player) null, pPlayer.getX(), pPlayer.getY(), pPlayer.getZ(), HalfLifeSounds.DRYFIRE1.get(), SoundSource.NEUTRAL, 0.5F, 1F);
                return InteractionResultHolder.fail(itemstack);
            }
            if (GetCooldow(itemstack) > 0) return InteractionResultHolder.fail(itemstack);
            triggerAnim(pPlayer, GeoItem.getOrAssignId(pPlayer.getItemInHand(pHand), (ServerLevel) pLevel),"onetime", "shoot");
            pLevel.playSound((Player) null, pPlayer.getX(), pPlayer.getY(), pPlayer.getZ(), this.getAltfireSound(), SoundSource.NEUTRAL, 0.5F, 1F);
            SetCooldow(pPlayer, this.getRightClickCooldown());
            SetShootRightTimer(itemstack, 21);
        }
        return InteractionResultHolder.success(itemstack);
    }

    @Override
    public InteractionResultHolder<ItemStack> leftuse(Level pLevel, Player pPlayer, InteractionHand pHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pHand);
        if (!pLevel.isClientSide) {
            if (GetAmmo(itemstack) == 0 && !pPlayer.getAbilities().instabuild) {
                pLevel.playSound((Player) null, pPlayer.getX(), pPlayer.getY(), pPlayer.getZ(), HalfLifeSounds.DRYFIRE1.get(), SoundSource.NEUTRAL, 0.5F, 1F);
                return InteractionResultHolder.fail(itemstack);
            }
            if (GetCooldow(itemstack) > 0) return InteractionResultHolder.fail(itemstack);
            triggerAnim(pPlayer, GeoItem.getOrAssignId(pPlayer.getItemInHand(pHand), (ServerLevel) pLevel),"onetime", "shoot");
            pLevel.playSound((Player) null, pPlayer.getX(), pPlayer.getY(), pPlayer.getZ(), getShootingSound(), SoundSource.NEUTRAL, 0.5F, 1F);
            SetCooldow(pPlayer, this.getLeftClickCooldown());
            SetShootLeftTimer(itemstack, 21);
        }
        return InteractionResultHolder.success(itemstack);
    }


    @Override
    public SoundEvent shootleftsound() {
        return HalfLifeSounds.DISPLACER_TELEPORT_PLAYER.get();
    }

    public SoundEvent startsound(){
        return HalfLifeSounds.DISPLACER_START.get();
    }

    public SoundEvent selfportsound(){
        return HalfLifeSounds.DISPLACER_SELF.get();
    }
    public void shootleft(Level pLevel, Player pPlayer, ItemStack itemstack) {
        if (!pLevel.isClientSide) {
            pPlayer.level.gameEvent(pPlayer, GameEvent.PROJECTILE_SHOOT, pPlayer.blockPosition());
            pLevel.playSound((Player) null, pPlayer.getX(), pPlayer.getY(), pPlayer.getZ(), shootleftsound(), SoundSource.NEUTRAL, 0.9F, 1F);
            TeleportingBullet snowball = new TeleportingBullet(pLevel, pPlayer);
            snowball.setdamage(this.getgundamage());
            snowball.shootFromRotation(pPlayer, pPlayer.getXRot(), pPlayer.getYRot(), 0.0F, 4F, 1.0F);
            pLevel.addFreshEntity(snowball);
        }
        award(pPlayer);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(new AnimationController<>(this, "noshoot", 0, this::predicate));
        controllerRegistrar.add(new AnimationController<>(this, "onetime", state -> PlayState.STOP)
                .triggerableAnim("shoot", RawAnimation.begin().then("animation.cannon.shoot", Animation.LoopType.PLAY_ONCE)));
    }


    private <T extends GeoAnimatable> PlayState predicate(AnimationState<T> tAnimationState) {
        tAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.cannon.noattack", Animation.LoopType.LOOP));
        return PlayState.CONTINUE;

    }


    @Override
    public void inventoryTick(ItemStack pStack, Level pLevel, Entity pEntity, int pSlotId, boolean pIsSelected) {
        super.inventoryTick(pStack, pLevel, pEntity, pSlotId, pIsSelected);

        if (pEntity instanceof Player pplayer) {
            if (GetShootLeftTimer(pStack) > -1) {
                int i = GetShootLeftTimer(pStack);
                if (i == 1 && pIsSelected && !pLevel.isClientSide) {
                    pLevel.playSound((Player) null, pEntity.getX(), pEntity.getY(), pEntity.getZ(), startsound(), SoundSource.NEUTRAL, 0.2F, 1F);
                }
                if (i == 0 && pIsSelected && !pLevel.isClientSide()) {
                    shootleft(pLevel, (Player) pEntity, pStack);
                }
                SetShootLeftTimer(pStack, i-1);
            }

            if (GetShootRightTimer(pStack) > -3) {
                int i = GetShootRightTimer(pStack);
                if (i == 1 && pIsSelected && !pLevel.isClientSide) {
                    pLevel.playSound((Player) null, pEntity.getX(), pEntity.getY(), pEntity.getZ(), startsound(), SoundSource.NEUTRAL, 0.2F, 1F);
                }
                if (i == 0 && pIsSelected && !pLevel.isClientSide()) {
                    shootright(pLevel, (Player) pEntity, pStack);
                }
                if (i == -1 && pIsSelected && !pLevel.isClientSide()) {
                    pLevel.playSound((Player) null, pEntity.getX(), pEntity.getY(), pEntity.getZ(), selfportsound(), SoundSource.NEUTRAL, 0.5F, 1F);
                }
                SetShootRightTimer(pStack, i-1);
            }
        }


    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    @Override
    public double getTick(Object itemStack) {
        return RenderUtils.getCurrentTick();
    }



    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {
            private Displacer_cannon_renderer renderer;
            @Override
            public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                if (this.renderer == null) {
                    renderer = new Displacer_cannon_renderer();
                }
                return this.renderer;
            }
        });
    }



    public void shootright(Level pLevel, Player pPlayer, ItemStack itemStack) {
        if (!pLevel.isClientSide) {
            if (pPlayer.canChangeDimensions()) {
                handleHalfLifePortal(pPlayer, pPlayer.blockPosition());
        }
        award(pPlayer);
    }
    }


    private void handleHalfLifePortal(Entity player, BlockPos pPos) {
        if (player.level instanceof ServerLevel serverlevel) {
            MinecraftServer minecraftserver = serverlevel.getServer();
            ResourceKey<Level> resourcekey = player.level.dimension() == HalfLifeDimensions.HALFLIFE_LEVEL_KEY ?
                    Level.OVERWORLD : HalfLifeDimensions.HALFLIFE_LEVEL_KEY;
            ServerLevel portalDimension = minecraftserver.getLevel(resourcekey);
            if (portalDimension != null && !player.isPassenger()) {
                if(resourcekey == HalfLifeDimensions.HALFLIFE_LEVEL_KEY) {
                    player.changeDimension(portalDimension, new XenTeleporter(pPos, true));
                } else {
                    player.changeDimension(portalDimension, new XenTeleporter(pPos, false));
                }
            }
        }
    }



}
