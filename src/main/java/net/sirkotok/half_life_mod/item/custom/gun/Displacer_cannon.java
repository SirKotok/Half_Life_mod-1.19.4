package net.sirkotok.half_life_mod.item.custom.gun;

import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import net.sirkotok.half_life_mod.entity.projectile.UnderbarrelGranade;
import net.sirkotok.half_life_mod.item.client.renderer.Displacer_cannon_renderer;
import net.sirkotok.half_life_mod.item.client.renderer.SMG_1_ItemRenderer;
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

public class Displacer_cannon extends GunAltFireItem implements GeoItem {

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    @Override
    public ItemStack getammoitem(){
        return Items.IRON_INGOT.getDefaultInstance();
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
        return 50;
    } //4
    @Override
    public int getLeftClickCooldown(){
        return 50;
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
        switch (RandomSource.create().nextInt(1,4)) {
            case 1:  return HalfLifeSounds.SMG_SHOT1.get();
            case 2:  return HalfLifeSounds.SMG_SHOT2.get();
            case 3:  return HalfLifeSounds.SMG_SHOT3.get();
        }
        return HalfLifeSounds.HEADCRAB_1_ALERT_1.get();
    }
    public SoundEvent getAltfireSound(){
        switch (RandomSource.create().nextInt(1,3)) {
            case 1:  return HalfLifeSounds.GLAUNCHER.get();
            case 2:  return HalfLifeSounds.GLAUNCHER2.get();
        }
        return HalfLifeSounds.HEADCRAB_1_ALERT_1.get();
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
            triggerAnim(pPlayer, GeoItem.getOrAssignId(pPlayer.getItemInHand(pHand), (ServerLevel) pLevel),"onetime", "altfire");
            pLevel.playSound((Player) null, pPlayer.getX(), pPlayer.getY(), pPlayer.getZ(), this.getAltfireSound(), SoundSource.NEUTRAL, 0.5F, 1F);
            shootright(pLevel, pPlayer, pHand);
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
            shootleft(pLevel, pPlayer, pHand);
        }
        return InteractionResultHolder.success(itemstack);
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


    @Override
    public void shootright(Level pLevel, Player pPlayer, InteractionHand pHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pHand);
        if (!pLevel.isClientSide) {
            SetCooldow(itemstack, getRightClickCooldown());
            if (pPlayer.canChangeDimensions()) {
                pPlayer.addTag("xenported");
                handleHalfLifePortal(pPlayer, pPlayer.blockPosition());
        }
        award(pPlayer);
    } }


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
