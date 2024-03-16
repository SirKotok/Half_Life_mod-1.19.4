package net.sirkotok.half_life_mod.misc.damagesource;

import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.sirkotok.half_life_mod.HalfLifeMod;
import net.minecraft.network.chat.Component;

import javax.annotation.Nullable;


public class ModDamageTypes {
    public static final DeferredRegister<DamageType> DAMAGE_TYPES =
            DeferredRegister.create(Registries.DAMAGE_TYPE, HalfLifeMod.MOD_ID);



    public static final ResourceKey<DamageType> HL_BULLET = register("hl_bullet");

    private static ResourceKey<DamageType> register(String name)
    {
        return ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation(HalfLifeMod.MOD_ID, name));
    }

    public static void register(IEventBus eventBus) {
        DAMAGE_TYPES.register(eventBus);
    }
    public static DamageSource bulletdamage(RegistryAccess access, Entity pProjectile, @Nullable LivingEntity pThrower) {
        return new DamageSourceMultipleMesseges(access.registry(Registries.DAMAGE_TYPE).get().getHolderOrThrow(HL_BULLET), pProjectile, pThrower, 3 );
    }


    private static class DamageSourceMultipleMesseges extends DamageSource {

        private int amount;

        public DamageSourceMultipleMesseges(Holder.Reference<DamageType> message, int amount) {
            super(message);
            this.amount = amount;
        }

        public DamageSourceMultipleMesseges(Holder.Reference<DamageType> message, Entity source, int amount) {
            super(message, source);
            this.amount = amount;
        }

        public DamageSourceMultipleMesseges(Holder.Reference<DamageType> message, Entity pProjectile, @Nullable LivingEntity pThrower, int amount) {
            super(message, pProjectile, pThrower);
            this.amount = amount;
        }

        @Override
        public Component getLocalizedDeathMessage(LivingEntity attacked) {
            int number = attacked.getRandom().nextInt(this.amount);
            String part1 = "death.attack." + this.getMsgId() + "_" + number;
            Entity entity = this.getDirectEntity() != null ? this.getDirectEntity() : null;
            if (entity instanceof Projectile projectile) entity = projectile.getOwner();
            if (entity != null) {
                return Component.translatable(part1 + ".entity", attacked.getDisplayName(), entity.getDisplayName());
            }else{
                return Component.translatable(part1, attacked.getDisplayName());
            }
        }
    }
}




