package net.sirkotok.half_life_mod.item.custom;

import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import net.minecraftforge.event.ForgeEventFactory;
import net.sirkotok.half_life_mod.entity.HalfLifeEntities;
import net.sirkotok.half_life_mod.entity.mob.mob_geckolib.custom.Chumtoad;
import net.sirkotok.half_life_mod.item.client.renderer.ChumtoadItemRenderer;
import net.sirkotok.half_life_mod.item.custom.gun.base.GunItem;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.util.GeckoLibUtil;
import software.bernie.geckolib.util.RenderUtils;

import java.util.ArrayList;
import java.util.Set;
import java.util.function.Consumer;

public class ChumtoadItem extends EntityThrowingItem implements GeoItem {
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    public ChumtoadItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public EntityType getentitytype() {
        return HalfLifeEntities.CHUMTOAD.get();
    }

    public static void addentitynbtdata(ItemStack stack, CompoundTag tag) {
        if (stack.getItem() instanceof ChumtoadItem) {
           tag.remove("Pos");
           tag.remove("Rotation");
            tag.remove("UUID");
           stack.setTag(tag);
        }
    }


    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pHand);
        pLevel.playSound((Player)null, pPlayer.getX(), pPlayer.getY(), pPlayer.getZ(), SoundEvents.SNOWBALL_THROW, SoundSource.NEUTRAL, 0.5F, 0.4F / (pLevel.getRandom().nextFloat() * 0.4F + 0.8F));
        if (!pLevel.isClientSide) {
            boolean f = false;
            Entity summon = getentitytype().create(pLevel);
            if (summon instanceof Chumtoad toad){
                ForgeEventFactory.onFinalizeSpawn((Mob) summon, (ServerLevelAccessor) pLevel, pLevel.getCurrentDifficultyAt(summon.blockPosition()), MobSpawnType.REINFORCEMENT, null, itemstack.hasTag() ? null : itemstack.getTag());
                if (itemstack.hasTag()) {
                    CompoundTag tag = summon.serializeNBT();
                    CompoundTag tag1 = itemstack.getTag();
                    tag1.put("Pos", tag.get("Pos"));
                    tag1.put("Rotation", tag.get("Rotation"));
                    tag1.put("UUID", tag.get("UUID"));
                    summon.deserializeNBT(itemstack.getTag());
                    tag1.remove("Pos");
                    tag1.remove("Rotation");
                    tag1.remove("UUID");
                    f = true;
                } else {
                    toad.setIsSixeye(false);
                    toad.setIsLongjump(RandomSource.create().nextFloat() < 0.1);
                    toad.settexture(0);
                }
                if (itemstack.hasCustomHoverName())
                    summon.setCustomName(itemstack.getHoverName());
                float yrot = pPlayer.getYRot();
                float yuserot = pPlayer.yHeadRot * ((float)Math.PI / 180F);
                float xr = Math.min(pPlayer.getXRot(), 60);
                float xrot = xr*((float)Math.PI / 180F);
                double bb = (pPlayer.getBbWidth() + 1.0F) * 0.5D;
                double dx = -bb * (double) Mth.sin(yuserot)*Mth.cos(xrot/1.5f)*1.2;
                double dy = -0.8*(double) Mth.sin(xrot);
                double dz = +bb * (double) Mth.cos(yuserot)*Mth.cos(xrot/1.5f)*1.2;
                summon.moveTo(pPlayer.getX() + dx,
                        pPlayer.getEyeY() + dy,
                        pPlayer.getZ() + dz,
                        yrot, 0.0f);
                this.shootEntityFromRotation(summon, pPlayer, pPlayer.getXRot(), pPlayer.getYRot(), 0.0F, 1.5F, 1.0F);
                pLevel.addFreshEntity(summon);
                ((Mob) summon).setPersistenceRequired();
            }




        pPlayer.awardStat(Stats.ITEM_USED.get(this));
        if (!pPlayer.getAbilities().instabuild) {
            itemstack.shrink(1);
            f = false;
        }


        }
        return InteractionResultHolder.sidedSuccess(itemstack, pLevel.isClientSide());
    }


    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {

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
            private ChumtoadItemRenderer renderer;

            @Override
            public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                if (this.renderer == null) {
                    renderer = new ChumtoadItemRenderer();
                }
                return this.renderer;
            }
        });
    }
}
