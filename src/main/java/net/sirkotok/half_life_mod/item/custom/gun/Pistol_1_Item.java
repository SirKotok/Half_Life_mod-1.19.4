package net.sirkotok.half_life_mod.item.custom.gun;

import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import net.sirkotok.half_life_mod.item.client.renderer.ChumtoadItemRenderer;
import net.sirkotok.half_life_mod.item.client.renderer.Pistol_1_ItemRenderer;
import net.sirkotok.half_life_mod.sound.ModSounds;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.animatable.SingletonGeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.Animation;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;
import software.bernie.geckolib.util.RenderUtils;

import java.util.function.Consumer;

public class Pistol_1_Item extends GunItem implements GeoItem {

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    @Override
    public ItemStack getammoitem(){
        return Items.BEDROCK.getDefaultInstance();
    }
    public Pistol_1_Item(Properties pProperties) {
        super(pProperties);
        SingletonGeoAnimatable.registerSyncedAnimatable(this);
    }

   @Override
    public int getRightClickCooldown(){
        return 4;
    }
    @Override
    public int getLeftClickCooldown(){
        return 6;
    }
    @Override
    public int getReloadCooldown(){
        return 20;
    }


    @Override
    public InteractionResultHolder<ItemStack> Reload(Level pLevel, Player pPlayer, InteractionHand pHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pHand);
        int slotwithitem = pPlayer.getInventory().findSlotMatchingItem(getammoitem());
        if (slotwithitem == -1 && !pPlayer.getAbilities().instabuild) return InteractionResultHolder.fail(itemstack);
        if (!pLevel.isClientSide) {
            if (GetAmmo(itemstack) != GetMaxAmmo() || pPlayer.getAbilities().instabuild) {
            if (GetCooldow(itemstack) > 0) return InteractionResultHolder.fail(itemstack);
            pLevel.playSound((Player) null, pPlayer.getX(), pPlayer.getY(), pPlayer.getZ(), ModSounds.PISTOL_RELOAD.get(), SoundSource.NEUTRAL, 0.5F, 1F);
            triggerAnim(pPlayer, GeoItem.getOrAssignId(pPlayer.getItemInHand(pHand), (ServerLevel) pLevel),"onetime", "reload");
            SetReloadTimer(itemstack, getReloadCooldown());
            SetCooldow(itemstack, getReloadCooldown());
        }}
        return InteractionResultHolder.pass(itemstack);
    }


    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pHand);

        if (!pLevel.isClientSide) {
            if (GetAmmo(itemstack) == 0 && !pPlayer.getAbilities().instabuild)
                return InteractionResultHolder.fail(itemstack);
            if (GetCooldow(itemstack) > 0) return InteractionResultHolder.fail(itemstack);
            triggerAnim(pPlayer, GeoItem.getOrAssignId(pPlayer.getItemInHand(pHand), (ServerLevel) pLevel),"onetime", "shoot");
            pLevel.playSound((Player) null, pPlayer.getX(), pPlayer.getY(), pPlayer.getZ(), ModSounds.PISTOL_SHOOT.get(), SoundSource.NEUTRAL, 0.5F, 1F);
            shootright(pLevel, pPlayer, pHand);
        }
        return InteractionResultHolder.pass(itemstack);
    }

    @Override
    public InteractionResultHolder<ItemStack> leftuse(Level pLevel, Player pPlayer, InteractionHand pHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pHand);
        if (!pLevel.isClientSide) {
            if (GetAmmo(itemstack) == 0 && !pPlayer.getAbilities().instabuild)
                return InteractionResultHolder.fail(itemstack);
            if (GetCooldow(itemstack) > 0) return InteractionResultHolder.fail(itemstack);
            triggerAnim(pPlayer, GeoItem.getOrAssignId(pPlayer.getItemInHand(pHand), (ServerLevel) pLevel),"onetime", "shoot");
            pLevel.playSound((Player) null, pPlayer.getX(), pPlayer.getY(), pPlayer.getZ(), ModSounds.PISTOL_SHOOT.get(), SoundSource.NEUTRAL, 0.5F, 1F);
            shootleft(pLevel, pPlayer, pHand);
        }
        return InteractionResultHolder.pass(itemstack);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(new AnimationController<>(this, "onetime", state -> PlayState.STOP)
                .triggerableAnim("shoot", RawAnimation.begin().then("animation.pistol.shoot", Animation.LoopType.PLAY_ONCE))
                .triggerableAnim("reload", RawAnimation.begin().then("animation.pistol.reload", Animation.LoopType.PLAY_ONCE)));
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
            private Pistol_1_ItemRenderer renderer;
            @Override
            public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                if (this.renderer == null) {
                    renderer = new Pistol_1_ItemRenderer();
                }
                return this.renderer;
            }
        });
    }



}
