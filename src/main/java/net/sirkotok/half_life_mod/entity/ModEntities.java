package net.sirkotok.half_life_mod.entity;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.sirkotok.half_life_mod.HalfLifeMod;
import net.sirkotok.half_life_mod.entity.mob.custom.*;
import net.sirkotok.half_life_mod.entity.projectile.AcidBall;
import net.sirkotok.half_life_mod.entity.projectile.Bullet;


public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, HalfLifeMod.MOD_ID);

    public static final RegistryObject<EntityType<Headcrab_1>> HEADCRAB_HL1 =
            ENTITY_TYPES.register("headcrab_1",
                    () -> EntityType.Builder.of(Headcrab_1::new, MobCategory.MONSTER)
                            .sized(0.7f, 0.8f)
                            .build(new ResourceLocation(HalfLifeMod.MOD_ID, "headcrab_1").toString()));

    public static final RegistryObject<EntityType<Headcrab_zombie_standart>> HEADCRAB_ZOMBIE_1 =
            ENTITY_TYPES.register("headcrab_zombie_1",
                    () -> EntityType.Builder.of(Headcrab_zombie_standart::new, MobCategory.MONSTER)
                            .sized(0.8f, 1.7f)
                            .build(new ResourceLocation(HalfLifeMod.MOD_ID, "headcrab_zombie_1").toString()));
    public static final RegistryObject<EntityType<Headcrab_Poison_2>> HEADCRAB_POISON_HL2 =
            ENTITY_TYPES.register("headcrab_poison_2",
                    () -> EntityType.Builder.of(Headcrab_Poison_2::new, MobCategory.MONSTER)
                            .sized(0.7f, 0.8f)
                            .build(new ResourceLocation(HalfLifeMod.MOD_ID, "headcrab_poison_2").toString()));
    public static final RegistryObject<EntityType<Headcrab_Poison_3>> HEADCRAB_POISON_HLA =
            ENTITY_TYPES.register("headcrab_poison_a",
                    () -> EntityType.Builder.of(Headcrab_Poison_3::new, MobCategory.MONSTER)
                            .sized(0.7f, 0.8f)
                            .build(new ResourceLocation(HalfLifeMod.MOD_ID, "headcrab_poison_a").toString()));

    public static final RegistryObject<EntityType<Headcrab_2>> HEADCRAB_HL2 =
            ENTITY_TYPES.register("headcrab_2",
                    () -> EntityType.Builder.of(Headcrab_2::new, MobCategory.MONSTER)
                            .sized(0.7f, 0.8f)
                            .build(new ResourceLocation(HalfLifeMod.MOD_ID, "headcrab_2").toString()));
    public static final RegistryObject<EntityType<Headcrab_3>> HEADCRAB_HLA =
            ENTITY_TYPES.register("headcrab_a",
                    () -> EntityType.Builder.of(Headcrab_3::new, MobCategory.MONSTER)
                            .sized(0.7f, 0.8f)
                            .build(new ResourceLocation(HalfLifeMod.MOD_ID, "headcrab_a").toString()));
    public static final RegistryObject<EntityType<Headcrab_Armored>> HEADCRAB_ARMORED =
            ENTITY_TYPES.register("headcrab_armor",
                    () -> EntityType.Builder.of(Headcrab_Armored::new, MobCategory.MONSTER)
                            .sized(0.7f, 0.8f)
                            .build(new ResourceLocation(HalfLifeMod.MOD_ID, "headcrab_armor").toString()));
    public static final RegistryObject<EntityType<Headcrab_Fast>> HEADCRAB_FAST =
            ENTITY_TYPES.register("headcrab_fast",
                    () -> EntityType.Builder.of(Headcrab_Fast::new, MobCategory.MONSTER)
                            .sized(0.7f, 0.8f)
                            .build(new ResourceLocation(HalfLifeMod.MOD_ID, "headcrab_fast").toString()));

    public static final RegistryObject<EntityType<Bullsquid>> BULLSQUID =
            ENTITY_TYPES.register("bullsquid",
                    () -> EntityType.Builder.of(Bullsquid::new, MobCategory.MONSTER)
                            .sized(1f, 1f)
                            .build(new ResourceLocation(HalfLifeMod.MOD_ID, "bullsquid").toString()));
    public static final RegistryObject<EntityType<Barney>> BARNEY =
            ENTITY_TYPES.register("barney",
                    () -> EntityType.Builder.of(Barney::new, MobCategory.MONSTER)
                            .sized(0.6F, 1.95F)
                            .build(new ResourceLocation(HalfLifeMod.MOD_ID, "barney").toString()));
    public static final RegistryObject<EntityType<Chumtoad>> CHUMTOAD =
            ENTITY_TYPES.register("chumtoad",
                    () -> EntityType.Builder.of(Chumtoad::new, MobCategory.CREATURE)
                            .sized(0.6f, 0.6f)
                            .build(new ResourceLocation(HalfLifeMod.MOD_ID, "chumtoad").toString()));
    public static final RegistryObject<EntityType<Snark>> SNARK =
            ENTITY_TYPES.register("snark",
                    () -> EntityType.Builder.of(Snark::new, MobCategory.MONSTER)
                            .sized(0.6f, 0.6f)
                            .build(new ResourceLocation(HalfLifeMod.MOD_ID, "snark").toString()));
    public static final RegistryObject<EntityType<Penguin>> PENGUIN =
            ENTITY_TYPES.register("penguin",
                    () -> EntityType.Builder.of(Penguin::new, MobCategory.MONSTER)
                            .sized(0.6f, 0.6f)
                            .build(new ResourceLocation(HalfLifeMod.MOD_ID, "penguin").toString()));

    public static final RegistryObject<EntityType<AcidBall>> ACID_BALL =
            ENTITY_TYPES.register("acid_ball",
                            () -> EntityType.Builder.<AcidBall>of(AcidBall::new, MobCategory.MISC)
                                    .sized(0.25F, 0.25F)
                                    .clientTrackingRange(4)
                                    .updateInterval(10)
                    .build(new ResourceLocation(HalfLifeMod.MOD_ID, "acid_ball").toString()));

    public static final RegistryObject<EntityType<Bullet>> BULLET_ONE =
            ENTITY_TYPES.register("bullet_one",
                    () -> EntityType.Builder.<Bullet>of(Bullet::new, MobCategory.MISC)
                            .sized(0.1F, 0.1F)
                            .clientTrackingRange(4)
                            .updateInterval(10)
                            .build(new ResourceLocation(HalfLifeMod.MOD_ID, "bullet_one").toString()));
    




    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}