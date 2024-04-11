package net.sirkotok.half_life_mod.item.custom;

import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
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
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import net.minecraftforge.event.ForgeEventFactory;
import net.sirkotok.half_life_mod.entity.HalfLifeEntities;
import net.sirkotok.half_life_mod.entity.projectile.Granade;
import net.sirkotok.half_life_mod.entity.projectile.client.renderer.Granade1_renderer;
import net.sirkotok.half_life_mod.item.client.renderer.Granade_1_ItemRenderer;
import net.sirkotok.half_life_mod.item.client.renderer.SnarkItemRenderer;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.util.GeckoLibUtil;
import software.bernie.geckolib.util.RenderUtils;

import java.util.function.Consumer;


public class Granade_hl1 extends Item implements GeoItem {

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    public Granade_hl1(Properties pProperties) {
        super(pProperties.stacksTo(10));
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
            Granade summon = new Granade(pLevel, pPlayer);
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
            pLevel.addFreshEntity(summon);
        }

        pPlayer.awardStat(Stats.ITEM_USED.get(this));
        if (!pPlayer.getAbilities().instabuild) {
            itemstack.shrink(1);
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
            private Granade_1_ItemRenderer renderer;
            @Override
            public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                if (this.renderer == null) {
                    renderer = new Granade_1_ItemRenderer();
                }
                return this.renderer;
            }
        });
    }
}

/*
M summon = summonType.create(level.getLevel());
        summon.moveTo(this.posX, this.posY, this.posZ);
        ForgeEventFactory.onFinalizeSpawn(summon, level, level.getCurrentDifficultyAt(summon.blockPosition()), MobSpawnType.REINFORCEMENT, null, null);
        level.addFreshEntity(summon);
 */