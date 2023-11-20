package net.sirkotok.half_life_mod.entity;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

import net.minecraft.world.level.Level;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.sirkotok.half_life_mod.HalfLifeMod;
import net.sirkotok.half_life_mod.entity.mob_effect_entity.custom.ShockWaveEffect;
import net.sirkotok.half_life_mod.entity.mob_effect_entity.custom.VoltigoreProjectileAftereffect;
import net.sirkotok.half_life_mod.entity.mob_geckolib.custom.*;
import net.sirkotok.half_life_mod.entity.mob_normal.custom.Barnacle;
import net.sirkotok.half_life_mod.entity.projectile.*;


public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, HalfLifeMod.MOD_ID);


    public static final RegistryObject<EntityType<Houndeye>> HOUNDEYE =
            ENTITY_TYPES.register("houndeye",
                    () -> EntityType.Builder.of(Houndeye::new, MobCategory.MONSTER)
                            .sized(0.7f, 0.8f)
                            .build(new ResourceLocation(HalfLifeMod.MOD_ID, "houndeye").toString()));

    public static final RegistryObject<EntityType<Hoteye>> HOTEYE =
            ENTITY_TYPES.register("hoteye",
                    () -> EntityType.Builder.of(Hoteye::new, MobCategory.MONSTER)
                            .sized(0.6f, 0.7f)
                            .build(new ResourceLocation(HalfLifeMod.MOD_ID, "hoteye").toString()));


    public static final RegistryObject<EntityType<Headcrab_1>> HEADCRAB_HL1 =
            ENTITY_TYPES.register("headcrab_1",
                    () -> EntityType.Builder.of(Headcrab_1::new, MobCategory.MONSTER)
                            .sized(0.7f, 0.8f)
                            .build(new ResourceLocation(HalfLifeMod.MOD_ID, "headcrab_1").toString()));
    public static final RegistryObject<EntityType<Shockroach>> SHOCKROACH =
            ENTITY_TYPES.register("shockroach",
                    () -> EntityType.Builder.of(Shockroach::new, MobCategory.MONSTER)
                            .sized(0.6f, 0.5f)
                            .build(new ResourceLocation(HalfLifeMod.MOD_ID, "shockroach").toString()));

    public static final RegistryObject<EntityType<Barnacle>> BARNACLE =
            ENTITY_TYPES.register("barnacle",
                    () -> EntityType.Builder.of((EntityType<Barnacle> pEntityType, Level pLevel) -> new Barnacle(pEntityType, pLevel), MobCategory.MONSTER)
                            .sized(1f, 1f)
                            .build(new ResourceLocation(HalfLifeMod.MOD_ID, "barnacle").toString()));


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

    public static final RegistryObject<EntityType<Pitdrone>> PITDRONE =
            ENTITY_TYPES.register("pitdrone",
                    () -> EntityType.Builder.of(Pitdrone::new, MobCategory.MONSTER)
                            .sized(0.6f, 1f)
                            .build(new ResourceLocation(HalfLifeMod.MOD_ID, "pitdrone").toString()));


    public static final RegistryObject<EntityType<Barney>> BARNEY =
            ENTITY_TYPES.register("barney",
                    () -> EntityType.Builder.of(Barney::new, MobCategory.MONSTER)
                            .sized(0.6F, 1.95F)
                            .build(new ResourceLocation(HalfLifeMod.MOD_ID, "barney").toString()));



    public static final RegistryObject<EntityType<Cockroach>> COCKROACH =
            ENTITY_TYPES.register("cockroach",
                    () -> EntityType.Builder.of(Cockroach::new, MobCategory.CREATURE)
                            .sized(0.2F, 0.1F)
                            .build(new ResourceLocation(HalfLifeMod.MOD_ID, "cockroach").toString()));
    public static final RegistryObject<EntityType<Leech>> LEECH =
            ENTITY_TYPES.register("leech",
                    () -> EntityType.Builder.of(Leech::new, MobCategory.CREATURE)
                            .sized(0.2F, 0.2F)
                            .build(new ResourceLocation(HalfLifeMod.MOD_ID, "leech").toString()));


    public static final RegistryObject<EntityType<Manhack>> MANHACK =
            ENTITY_TYPES.register("manhack",
                    () -> EntityType.Builder.of(Manhack::new, MobCategory.MONSTER)
                            .sized(0.6F, 0.6F)
                            .build(new ResourceLocation(HalfLifeMod.MOD_ID, "manhack").toString()));
    public static final RegistryObject<EntityType<Chumtoad>> CHUMTOAD =
            ENTITY_TYPES.register("chumtoad",
                    () -> EntityType.Builder.of(Chumtoad::new, MobCategory.MONSTER)
                            .sized(0.6f, 0.6f)
                            .build(new ResourceLocation(HalfLifeMod.MOD_ID, "chumtoad").toString()));
    public static final RegistryObject<EntityType<Snarknest>> SNARKNEST =
            ENTITY_TYPES.register("snarknest",
                    () -> EntityType.Builder.of(Snarknest::new, MobCategory.MONSTER)
                            .sized(0.7f, 0.7f)
                            .build(new ResourceLocation(HalfLifeMod.MOD_ID, "snarknest").toString()));
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
    public static final RegistryObject<EntityType<Voltigore>> VOLTIGORE =
            ENTITY_TYPES.register("vortigore",
                    () -> EntityType.Builder.of(Voltigore::new, MobCategory.MONSTER)
                            .sized(2f, 2f)
                            .build(new ResourceLocation(HalfLifeMod.MOD_ID, "vortigore").toString()));

    public static final RegistryObject<EntityType<Shocktrooper>> SHOCKTROOPER =
            ENTITY_TYPES.register("shocktrooper",
                    () -> EntityType.Builder.of(Shocktrooper::new, MobCategory.MONSTER)
                            .sized(0.8f, 2.4f)
                            .build(new ResourceLocation(HalfLifeMod.MOD_ID, "shocktrooper").toString()));




    public static final RegistryObject<EntityType<ShockWaveEffect>> SHOCKWAVEEFFECT =
            ENTITY_TYPES.register("shockwave_effect",
                    () -> EntityType.Builder.of(ShockWaveEffect::new, MobCategory.MONSTER)
                            .sized(0.6f, 0.6f)
                            .build(new ResourceLocation(HalfLifeMod.MOD_ID, "shockwave_effect").toString()));


    public static final RegistryObject<EntityType<VoltigoreProjectileAftereffect>> VOLTIGOREPROJECTEFFECT =
            ENTITY_TYPES.register("voltigore_projectile_aftereffect",
                    () -> EntityType.Builder.of(VoltigoreProjectileAftereffect::new, MobCategory.MONSTER)
                            .sized(0.6f, 0.6f)
                            .build(new ResourceLocation(HalfLifeMod.MOD_ID, "voltigore_projectile_aftereffect").toString()));

    public static final RegistryObject<EntityType<AcidBall>> ACID_BALL =
            ENTITY_TYPES.register("acid_ball",
                            () -> EntityType.Builder.<AcidBall>of(AcidBall::new, MobCategory.MISC)
                                    .sized(0.25F, 0.25F)
                                    .clientTrackingRange(20)
                                    .updateInterval(1)
                    .build(new ResourceLocation(HalfLifeMod.MOD_ID, "acid_ball").toString()));

    public static final RegistryObject<EntityType<SporeShot>> SPORESHOT =
            ENTITY_TYPES.register("spore_shot",
                    () -> EntityType.Builder.<SporeShot>of(SporeShot::new, MobCategory.MISC)
                            .sized(0.3F, 0.3F)
                            .clientTrackingRange(20)
                            .updateInterval(1)
                            .build(new ResourceLocation(HalfLifeMod.MOD_ID, "spore_shot").toString()));


    public static final RegistryObject<EntityType<PitdroneSpike>> PITDRONE_SPIKE =
            ENTITY_TYPES.register("pit_spike",
                    () -> EntityType.Builder.<PitdroneSpike>of(PitdroneSpike::new, MobCategory.MISC)
                            .sized(0.25F, 0.25F)
                            .clientTrackingRange(20)
                            .updateInterval(1)
                            .build(new ResourceLocation(HalfLifeMod.MOD_ID, "pit_spike").toString()));


    public static final RegistryObject<EntityType<VoltigoreShock>> VOLTIGORE_SHOCK =
            ENTITY_TYPES.register("voltigore_shock",
                    () -> EntityType.Builder.<VoltigoreShock>of(VoltigoreShock::new, MobCategory.MISC)
                            .sized(0.25F, 0.25F)
                            .clientTrackingRange(20)
                            .updateInterval(1)
                            .build(new ResourceLocation(HalfLifeMod.MOD_ID, "voltigore_shock").toString()));

    public static final RegistryObject<EntityType<Bullet>> BULLET_ONE =
            ENTITY_TYPES.register("bullet_one",
                    () -> EntityType.Builder.<Bullet>of(Bullet::new, MobCategory.MISC)
                            .sized(0.1F, 0.1F)
                            .clientTrackingRange(20)
                            .updateInterval(1)
                            .build(new ResourceLocation(HalfLifeMod.MOD_ID, "bullet_one").toString()));

    public static final RegistryObject<EntityType<ShockProjectile>> SHOCK_SHOT =
            ENTITY_TYPES.register("shock_shot",
                    () -> EntityType.Builder.<ShockProjectile>of(ShockProjectile::new, MobCategory.MISC)
                            .sized(0.25F, 0.25F)
                            .clientTrackingRange(20)
                            .updateInterval(1)
                            .build(new ResourceLocation(HalfLifeMod.MOD_ID, "shock_shot").toString()));



    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}