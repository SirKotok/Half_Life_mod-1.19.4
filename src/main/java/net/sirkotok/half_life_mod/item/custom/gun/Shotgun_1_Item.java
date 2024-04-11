package net.sirkotok.half_life_mod.item.custom.gun;

import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import net.sirkotok.half_life_mod.entity.projectile.Bullet;
import net.sirkotok.half_life_mod.item.client.renderer.Pistol_1_ItemRenderer;
import net.sirkotok.half_life_mod.item.client.renderer.Shotty_1_ItemRenderer;
import net.sirkotok.half_life_mod.item.custom.gun.base.GunItem;
import net.sirkotok.half_life_mod.misc.config.HalfLifeCommonConfigs;
import net.sirkotok.half_life_mod.sound.HalfLifeSounds;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.animatable.SingletonGeoAnimatable;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;
import software.bernie.geckolib.util.RenderUtils;

import java.util.function.Consumer;

public class Shotgun_1_Item extends GunItem implements GeoItem {

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    @Override
    public ItemStack getammoitem(){
        return Items.IRON_INGOT.getDefaultInstance();
    }
    public Shotgun_1_Item(Properties pProperties) {
        super(pProperties);
        SingletonGeoAnimatable.registerSyncedAnimatable(this);
    }

    @Override
    public int GetMaxAmmo() {
        return 8;
    }


    @Override
    public void reloadtick(ItemStack pStack, Boolean pIsSelected, Level pLevel, Player pEntity) {
        if (GetReloadTimer(pStack) == 0 && pIsSelected && !pLevel.isClientSide()) {
            reloadgun(pLevel, pEntity, pStack);
        }
        SetReloadTimer(pStack, GetReloadTimer(pStack)-1);
    }


    @Override
    public void reloadgun(Level pLevel, Player pPlayer, ItemStack itemstack) {
        if (GetAmmo(itemstack) != GetMaxAmmo() && !pPlayer.getAbilities().instabuild)
        {
            int slotwithitem = findSlotMatchingItem(pPlayer, getammoitem());
            if (!(slotwithitem == -1)) {
                ItemStack inslot = pPlayer.getInventory().getItem(slotwithitem);
                inslot.shrink(1);
                SetAmmo(itemstack, GetAmmo(itemstack)+1);
                if (GetAmmo(itemstack) < GetMaxAmmo()) {
                    SetReloadTimer(itemstack, 6);
                    pLevel.playSound((Player) null, pPlayer.getX(), pPlayer.getY(), pPlayer.getZ(), HalfLifeSounds.SGHL1RELOAD.get(), SoundSource.NEUTRAL, 0.5F, 1F);

                }
            } else {
                pLevel.playSound((Player) null, pPlayer.getX(), pPlayer.getY(), pPlayer.getZ(), HalfLifeSounds.DRYFIRE1.get(), SoundSource.NEUTRAL, 0.5F, 1F);
            }
        }
        if (pPlayer.getAbilities().instabuild) {
            SetCooldow(pPlayer, getReloadCooldown());
            SetAmmo(itemstack, Math.min(GetAmmo(itemstack)+1, GetMaxAmmo()));
            if (GetAmmo(itemstack) < GetMaxAmmo()) {
                SetReloadTimer(itemstack, 6);
                pLevel.playSound((Player) null, pPlayer.getX(), pPlayer.getY(), pPlayer.getZ(), HalfLifeSounds.SGHL1RELOAD.get(), SoundSource.NEUTRAL, 0.5F, 1F);

            }
        }
    }


    public void actuallyreload(Level pLevel, Player pPlayer, InteractionHand pHand) {
        SetCooldow(pPlayer, getReloadCooldown()+10);
        ItemStack itemstack = pPlayer.getItemInHand(pHand);
    //    pLevel.playSound((Player) null, pPlayer.getX(), pPlayer.getY(), pPlayer.getZ(), HalfLifeSounds.PISTOL_RELOAD.get(), SoundSource.NEUTRAL, 0.5F, 1F);
        String s = "reload" + (GetMaxAmmo() - GetAmmo(itemstack));
        triggerAnim(pPlayer, GeoItem.getOrAssignId(pPlayer.getItemInHand(pHand), (ServerLevel) pLevel),"onetime", s);
        SetReloadTimer(itemstack, getReloadCooldown());
    }


    public void shootright(Level pLevel, Player pPlayer, InteractionHand pHand) {

        ItemStack itemstack = pPlayer.getItemInHand(pHand);
        SetReloadTimer(itemstack, -2);
        if (!pLevel.isClientSide) {
            pPlayer.level.gameEvent(pPlayer, GameEvent.PROJECTILE_SHOOT, pPlayer.blockPosition());
            SetCooldow(pPlayer, getRightClickCooldown());
            int i = 12;

            while (i > 0) {
                Bullet snowball = new Bullet(pLevel, pPlayer);
                snowball.setdamage(this.getgundamage());
                snowball.sethasknockback();
                snowball.shootFromRotation(pPlayer, pPlayer.getXRot(), pPlayer.getYRot(), 0.0F, 4F, 10F);
                pLevel.addFreshEntity(snowball);
                i--;
            }
        }
        award(pPlayer);
        if (!pPlayer.getAbilities().instabuild) SetAmmo(itemstack, GetAmmo(itemstack)-2);
    }

    public void shootleft(Level pLevel, Player pPlayer, InteractionHand pHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pHand);
        SetReloadTimer(itemstack, -2);
        if (!pLevel.isClientSide) {
            pPlayer.level.gameEvent(pPlayer, GameEvent.PROJECTILE_SHOOT, pPlayer.blockPosition());
            SetCooldow(pPlayer, getRightClickCooldown());
            int i = 6;

            while (i > 0) {
                Bullet snowball = new Bullet(pLevel, pPlayer);
                snowball.setdamage(this.getgundamage());
                snowball.sethasknockback();
                snowball.shootFromRotation(pPlayer, pPlayer.getXRot(), pPlayer.getYRot(), 0.0F, 4F, 7.5F);
                pLevel.addFreshEntity(snowball);
                i--;
            }
        }
        award(pPlayer);
        if (!pPlayer.getAbilities().instabuild) SetAmmo(itemstack, GetAmmo(itemstack)-1);
    }

    @Override
    public int getReloadCooldown(){
        return 10;
    }
    @Override
    public int getRightClickCooldown(){
        return 23;
    }
    @Override
    public int getLeftClickCooldown(){
        return 14;
    }

    @Override
    public float getgundamage() {
        return 1.5f;
    }

    @Override
    public InteractionResultHolder<ItemStack> Reload(Level pLevel, Player pPlayer, InteractionHand pHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pHand);
        int slotwithitem = findSlotMatchingItem(pPlayer, getammoitem());
        if (slotwithitem == -1 && !pPlayer.getAbilities().instabuild) return InteractionResultHolder.fail(itemstack);
        if (!pLevel.isClientSide) {
            if (GetAmmo(itemstack) != GetMaxAmmo() || pPlayer.getAbilities().instabuild) {
            if (GetCooldow(itemstack) > 0) return InteractionResultHolder.fail(itemstack);
            actuallyreload(pLevel, pPlayer, pHand);
        }}
        return InteractionResultHolder.success(itemstack);
    }

    /*
    public void shootright(Level pLevel, Player pPlayer, InteractionHand pHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pHand);
        if (!pLevel.isClientSide) {
            pPlayer.level.gameEvent(pPlayer, GameEvent.PROJECTILE_SHOOT, pPlayer.blockPosition());
            SetCooldow(pPlayer, getRightClickCooldown());
            SporeShot snowball = new SporeShot(pLevel, pPlayer);
            snowball.shootFromRotation(pPlayer, pPlayer.getXRot(), pPlayer.getYRot(), 0.0F, 0.8F, 6.0F);
            pLevel.addFreshEntity(snowball);
        }
        award(pPlayer);
        if (!pPlayer.getAbilities().instabuild) SetAmmo(itemstack, GetAmmo(itemstack)-1);
    } */





    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pHand);

        if (!pLevel.isClientSide) {
            if (GetCooldow(itemstack) > 0) return InteractionResultHolder.fail(itemstack);
            if (GetAmmo(itemstack) <= 1 && !pPlayer.getAbilities().instabuild) {
                int slotwithitem = findSlotMatchingItem(pPlayer, getammoitem());
                if (slotwithitem == -1  || !HalfLifeCommonConfigs.AUTO_RELOAD.get()) {
                pLevel.playSound((Player) null, pPlayer.getX(), pPlayer.getY(), pPlayer.getZ(), HalfLifeSounds.DRYFIRE1.get(), SoundSource.NEUTRAL, 0.5F, 1F);
                SetCooldow(pPlayer, 10);
                return InteractionResultHolder.fail(itemstack);
                } else {
                    if (GetAmmo(itemstack) != GetMaxAmmo()) {
                        actuallyreload(pLevel, pPlayer, pHand);
                    }
                    return InteractionResultHolder.success(itemstack);
                }
            }
            triggerAnim(pPlayer, GeoItem.getOrAssignId(pPlayer.getItemInHand(pHand), (ServerLevel) pLevel),"onetime", "shoot2");
            pLevel.playSound((Player) null, pPlayer.getX(), pPlayer.getY(), pPlayer.getZ(), HalfLifeSounds.SGHL1DOUBLE.get(), SoundSource.NEUTRAL, 0.5F, 1F);
            shootright(pLevel, pPlayer, pHand);
        }
        return InteractionResultHolder.success(itemstack);
    }

    @Override
    public InteractionResultHolder<ItemStack> leftuse(Level pLevel, Player pPlayer, InteractionHand pHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pHand);
        if (!pLevel.isClientSide) {
            if (GetCooldow(itemstack) > 0) return InteractionResultHolder.fail(itemstack);
            if (GetAmmo(itemstack) == 0 && !pPlayer.getAbilities().instabuild) {
                int slotwithitem = findSlotMatchingItem(pPlayer, getammoitem());
                if (slotwithitem == -1  || !HalfLifeCommonConfigs.AUTO_RELOAD.get()) {
                    pLevel.playSound((Player) null, pPlayer.getX(), pPlayer.getY(), pPlayer.getZ(), HalfLifeSounds.DRYFIRE1.get(), SoundSource.NEUTRAL, 0.5F, 1F);
                    SetCooldow(pPlayer, 10);
                    return InteractionResultHolder.fail(itemstack);
                } else {
                    if (GetAmmo(itemstack) != GetMaxAmmo()) {
                        actuallyreload(pLevel, pPlayer, pHand);
                    }
                    return InteractionResultHolder.success(itemstack);
                }
            }
            triggerAnim(pPlayer, GeoItem.getOrAssignId(pPlayer.getItemInHand(pHand), (ServerLevel) pLevel),"onetime", "shoot");
            pLevel.playSound((Player) null, pPlayer.getX(), pPlayer.getY(), pPlayer.getZ(), HalfLifeSounds.SGHL1SINGLE.get(), SoundSource.NEUTRAL, 0.5F, 1F);
            shootleft(pLevel, pPlayer, pHand);
        }
        return InteractionResultHolder.success(itemstack);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(new AnimationController<>(this, "noshoot", 0, this::predicate));
        controllerRegistrar.add(new AnimationController<>(this, "onetime", state -> PlayState.STOP)
                .triggerableAnim("shoot", RawAnimation.begin().then("animation.shotgun.shoot", Animation.LoopType.PLAY_ONCE))
                .triggerableAnim("shoot2", RawAnimation.begin().then("animation.shotgun.shoot2", Animation.LoopType.PLAY_ONCE))
                .triggerableAnim("reload1", RawAnimation.begin().then("animation.shotgun.reload1", Animation.LoopType.PLAY_ONCE))
                .triggerableAnim("reload2", RawAnimation.begin().then("animation.shotgun.reload2", Animation.LoopType.PLAY_ONCE))
                .triggerableAnim("reload3", RawAnimation.begin().then("animation.shotgun.reload3", Animation.LoopType.PLAY_ONCE))
                .triggerableAnim("reload4", RawAnimation.begin().then("animation.shotgun.reload4", Animation.LoopType.PLAY_ONCE))
                .triggerableAnim("reload5", RawAnimation.begin().then("animation.shotgun.reload5", Animation.LoopType.PLAY_ONCE))
                .triggerableAnim("reload6", RawAnimation.begin().then("animation.shotgun.reload6", Animation.LoopType.PLAY_ONCE))
                .triggerableAnim("reload7", RawAnimation.begin().then("animation.shotgun.reload7", Animation.LoopType.PLAY_ONCE))
                .triggerableAnim("reload8", RawAnimation.begin().then("animation.shotgun.reload8", Animation.LoopType.PLAY_ONCE))
        );

    }


    private <T extends GeoAnimatable> PlayState predicate(AnimationState<T> tAnimationState) {
        tAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.shotgun.noshoot", Animation.LoopType.LOOP));
        return PlayState.CONTINUE;

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
            private Shotty_1_ItemRenderer renderer;
            @Override
            public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                if (this.renderer == null) {
                    renderer = new Shotty_1_ItemRenderer();
                }
                return this.renderer;
            }
        });
    }

 /*
    @Override
    public void shootleft(Level pLevel, Player pPlayer, InteractionHand pHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pHand);
        if (!pLevel.isClientSide) {
            pPlayer.level.gameEvent(pPlayer, GameEvent.PROJECTILE_SHOOT, pPlayer.blockPosition());
            SetCooldow(itemstack, getLeftClickCooldown());
            SporeShot snowball = new SporeShot(pLevel, pPlayer);
            snowball.shootFromRotation(pPlayer, pPlayer.getXRot(), pPlayer.getYRot(), 0.0F, 1F, 1.0F);
            pLevel.addFreshEntity(snowball);
        }
        award(pPlayer);
        if (!pPlayer.getAbilities().instabuild) SetAmmo(itemstack, GetAmmo(itemstack)-1);
    } */

}
