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
import net.sirkotok.half_life_mod.entity.fallingblock.GravityGunFallingBlockEntity;
import net.sirkotok.half_life_mod.entity.mob.mob_effect_entity.custom.ShockWaveEffect;
import net.sirkotok.half_life_mod.entity.mob.mob_effect_entity.custom.SitThenBlowUpEffect;
import net.sirkotok.half_life_mod.entity.mob.mob_effect_entity.custom.VoltigoreProjectileAftereffect;
import net.sirkotok.half_life_mod.entity.mob.mob_effect_entity.custom.VortShockWaveEffect;
import net.sirkotok.half_life_mod.entity.mob.mob_geckolib.custom.*;
import net.sirkotok.half_life_mod.entity.mob.mob_normal.custom.Barnacle;
import net.sirkotok.half_life_mod.entity.mob.mob_procedural_effect.HLHydra;
import net.sirkotok.half_life_mod.entity.projectile.*;
import net.sirkotok.half_life_mod.entity.projectile.arrowlike.Flechette;
import net.sirkotok.half_life_mod.entity.projectile.TeleportingBullet;


public class HalfLifeEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, HalfLifeMod.MOD_ID);



    public static final RegistryObject<EntityType<GravityGunFallingBlockEntity>> GG_BLOCK =
            ENTITY_TYPES.register("gravitygun_block",
                    () -> EntityType.Builder.<GravityGunFallingBlockEntity>of(GravityGunFallingBlockEntity::new, MobCategory.MISC)
                    .sized(0.98F, 0.98F)
                    .clientTrackingRange(30)
                    .updateInterval(1)
                    .build(new ResourceLocation(HalfLifeMod.MOD_ID, "gravitygun_block").toString()));


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


    public static final RegistryObject<EntityType<Controller>> CONTROLLER =
            ENTITY_TYPES.register("controller",
                    () -> EntityType.Builder.of(Controller::new, MobCategory.MONSTER)
                            .sized(1f, 2.0f)
                            .build(new ResourceLocation(HalfLifeMod.MOD_ID, "controller").toString()));


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
                    () -> EntityType.Builder.of((EntityType<Barnacle> pEntityType, Level pLevel) ->
                            new Barnacle(pEntityType, pLevel), MobCategory.MONSTER)
                            .sized(1f, 1f)
                            .build(new ResourceLocation(HalfLifeMod.MOD_ID, "barnacle").toString()));


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


    public static final RegistryObject<EntityType<Gonarch>> GONARCH =
            ENTITY_TYPES.register("gonarch",
                    () -> EntityType.Builder.of(Gonarch::new, MobCategory.MONSTER)
                            .sized(3f, 4.5f)
                            .build(new ResourceLocation(HalfLifeMod.MOD_ID, "gonarch").toString()));
    public static final RegistryObject<EntityType<GonarchBM>> GONARCHBM =
            ENTITY_TYPES.register("gonarchbm",
                    () -> EntityType.Builder.of(GonarchBM::new, MobCategory.MONSTER)
                            .sized(3f, 4.5f)
                            .build(new ResourceLocation(HalfLifeMod.MOD_ID, "gonarchbn").toString()));


    public static final RegistryObject<EntityType<Pitdrone>> PITDRONE =
            ENTITY_TYPES.register("pitdrone",
                    () -> EntityType.Builder.of(Pitdrone::new, MobCategory.MONSTER)
                            .sized(0.6f, 1f)
                            .build(new ResourceLocation(HalfLifeMod.MOD_ID, "pitdrone").toString()));


    public static final RegistryObject<EntityType<Antlion>> ANTLION =
            ENTITY_TYPES.register("antlion",
                    () -> EntityType.Builder.of(Antlion::new, MobCategory.MONSTER)
                            .sized(0.6f, 1.1f)
                            .build(new ResourceLocation(HalfLifeMod.MOD_ID, "antlion").toString()));

    public static final RegistryObject<EntityType<AntlionWorker>> ANTLIONWORKER =
            ENTITY_TYPES.register("antlionworker",
                    () -> EntityType.Builder.of(AntlionWorker::new, MobCategory.MONSTER)
                            .sized(1.1f, 1f)
                            .build(new ResourceLocation(HalfLifeMod.MOD_ID, "antlionworker").toString()));
    public static final RegistryObject<EntityType<Barney>> BARNEY =
            ENTITY_TYPES.register("barney",
                    () -> EntityType.Builder.of(Barney::new, MobCategory.MONSTER)
                            .sized(0.6F, 1.8F)
                            .build(new ResourceLocation(HalfLifeMod.MOD_ID, "barney").toString()));

    public static final RegistryObject<EntityType<HecuGrunt>> HECU_GRUNT =
            ENTITY_TYPES.register("hecu_grunt",
                    () -> EntityType.Builder.of(HecuGrunt::new, MobCategory.MONSTER)
                            .sized(0.6F, 1.8F)
                            .build(new ResourceLocation(HalfLifeMod.MOD_ID, "hecu_grunt").toString()));



    public static final RegistryObject<EntityType<Scientist>> SCIENTIST =
            ENTITY_TYPES.register("scientist",
                    () -> EntityType.Builder.of(Scientist::new, MobCategory.MONSTER)
                            .sized(0.6F, 1.8F)
                            .build(new ResourceLocation(HalfLifeMod.MOD_ID, "scientist").toString()));

    public static final RegistryObject<EntityType<ZombieHl1>> ZOMBIE_HL1 =
            ENTITY_TYPES.register("zombie_scientist_hl1",
                    () -> EntityType.Builder.of(ZombieHl1::new, MobCategory.MONSTER)
                            .sized(0.6F, 1.85F)
                            .build(new ResourceLocation(HalfLifeMod.MOD_ID, "zombie_scientist_hl1").toString()));


    public static final RegistryObject<EntityType<HL2Zombie>> ZOMBIE_HL2 =
            ENTITY_TYPES.register("zombie_hl2",
                    () -> EntityType.Builder.of(HL2Zombie::new, MobCategory.MONSTER)
                            .sized(0.6F, 1.85F)
                            .build(new ResourceLocation(HalfLifeMod.MOD_ID, "zombie_hl2").toString()));

    public static final RegistryObject<EntityType<HL2Zombie_fast>> ZOMBIE_FAST =
            ENTITY_TYPES.register("zombie_fast",
                    () -> EntityType.Builder.of(HL2Zombie_fast::new, MobCategory.MONSTER)
                            .sized(0.6F, 1.47F)
                            .build(new ResourceLocation(HalfLifeMod.MOD_ID, "zombie_fast").toString()));

    public static final RegistryObject<EntityType<Pzombie>> PZOMBIE =
            ENTITY_TYPES.register("pzombie",
                    () -> EntityType.Builder.of(Pzombie::new, MobCategory.MONSTER)
                            .sized(0.6F, 1.63F)
                            .build(new ResourceLocation(HalfLifeMod.MOD_ID, "pzombie").toString()));

    public static final RegistryObject<EntityType<VortigauntHL1>> VORTHL1 =
            ENTITY_TYPES.register("vorthl1",
                    () -> EntityType.Builder.of(VortigauntHL1::new, MobCategory.MONSTER)
                            .sized(0.6F, 1.8F)
                            .build(new ResourceLocation(HalfLifeMod.MOD_ID, "vorthl1").toString()));
    public static final RegistryObject<EntityType<VortigauntHL2>> VORTHL2 =
            ENTITY_TYPES.register("vorthl2",
                    () -> EntityType.Builder.of(VortigauntHL2::new, MobCategory.MONSTER)
                            .sized(0.6F, 1.8F)
                            .build(new ResourceLocation(HalfLifeMod.MOD_ID, "vorthl2").toString()));

    public static final RegistryObject<EntityType<HLZombieVillager>> VZOMBIE =
            ENTITY_TYPES.register("vzombie",
                    () -> EntityType.Builder.of(HLZombieVillager::new, MobCategory.MONSTER)
                            .sized(0.6F, 1.7F)
                            .build(new ResourceLocation(HalfLifeMod.MOD_ID, "vzombie").toString()));

    public static final RegistryObject<EntityType<Cockroach>> COCKROACH =
            ENTITY_TYPES.register("cockroach",
                    () -> EntityType.Builder.of(Cockroach::new, MobCategory.CREATURE)
                            .sized(0.2F, 0.1F)
                            .build(new ResourceLocation(HalfLifeMod.MOD_ID, "cockroach").toString()));

    public static final RegistryObject<EntityType<Baby_Headcrab>> BABY_HEADCRAB =
            ENTITY_TYPES.register("baby_headcrab",
                    () -> EntityType.Builder.of(Baby_Headcrab::new, MobCategory.MONSTER)
                            .sized(0.4F, 0.4F)
                            .build(new ResourceLocation(HalfLifeMod.MOD_ID, "baby_headcrab").toString()));
    public static final RegistryObject<EntityType<Leech>> LEECH =
            ENTITY_TYPES.register("leech",
                    () -> EntityType.Builder.of(Leech::new, MobCategory.MONSTER)
                            .sized(0.3F, 0.3F)
                            .build(new ResourceLocation(HalfLifeMod.MOD_ID, "leech").toString()));

    public static final RegistryObject<EntityType<AlienGrunt>> ALIENGRUNT =
            ENTITY_TYPES.register("aliengrunt",
                    () -> EntityType.Builder.of(AlienGrunt::new, MobCategory.MONSTER)
                            .sized(1.2F, 2.7F)
                            .build(new ResourceLocation(HalfLifeMod.MOD_ID, "aliengrunt").toString()));
    public static final RegistryObject<EntityType<Gargantua>> GARGANTUA =
            ENTITY_TYPES.register("garg",
                    () -> EntityType.Builder.of(Gargantua::new, MobCategory.MONSTER)
                            .sized(3F, 4F)
                            .build(new ResourceLocation(HalfLifeMod.MOD_ID, "garg").toString()));
    public static final RegistryObject<EntityType<Shark>> SHARK =
            ENTITY_TYPES.register("shark",
                    () -> EntityType.Builder.of(Shark::new, MobCategory.MONSTER)
                            .sized(2F, 1.5F)
                            .build(new ResourceLocation(HalfLifeMod.MOD_ID, "shark").toString()));


    public static final RegistryObject<EntityType<HLHydra>> HYDRA =
            ENTITY_TYPES.register("hl_hydra",
                    () -> EntityType.Builder.of(HLHydra::new, MobCategory.MONSTER)
                            .sized(1F, 1F)
                            .build(new ResourceLocation(HalfLifeMod.MOD_ID, "hl_hydra").toString()));

    public static final RegistryObject<EntityType<Archer>> ARCHER =
            ENTITY_TYPES.register("archerfish",
                    () -> EntityType.Builder.of(Archer::new, MobCategory.MONSTER)
                            .sized(1F, 1F)
                            .build(new ResourceLocation(HalfLifeMod.MOD_ID, "archerfish").toString()));


    public static final RegistryObject<EntityType<Manhack>> MANHACK =
            ENTITY_TYPES.register("manhack",
                    () -> EntityType.Builder.of(Manhack::new, MobCategory.MONSTER)
                            .sized(0.6F, 0.6F)
                            .build(new ResourceLocation(HalfLifeMod.MOD_ID, "manhack").toString()));

    public static final RegistryObject<EntityType<Hunter>> HUNTER =
            ENTITY_TYPES.register("hunter",
                    () -> EntityType.Builder.of(Hunter::new, MobCategory.MONSTER)
                            .sized(1.1F, 2.3F)
                            .build(new ResourceLocation(HalfLifeMod.MOD_ID, "hunter").toString()));


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

    public static final RegistryObject<EntityType<VortShockWaveEffect>> VORTSHOCKWAVEEFFECT =
            ENTITY_TYPES.register("vshockwave_effect",
                    () -> EntityType.Builder.of(VortShockWaveEffect::new, MobCategory.MONSTER)
                            .sized(0.6f, 0.6f)
                            .build(new ResourceLocation(HalfLifeMod.MOD_ID, "vshockwave_effect").toString()));

    public static final RegistryObject<EntityType<SitThenBlowUpEffect>> SITBLOWUP =
            ENTITY_TYPES.register("blow_up_effect",
                    () -> EntityType.Builder.of(SitThenBlowUpEffect::new, MobCategory.MONSTER)
                            .sized(0.6f, 0.6f)
                            .build(new ResourceLocation(HalfLifeMod.MOD_ID, "blow_up_effect").toString()));
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


    public static final RegistryObject<EntityType<AcidThrown>> ACID_THROWN =
            ENTITY_TYPES.register("acid_thrown",
                    () -> EntityType.Builder.<AcidThrown>of(AcidThrown::new, MobCategory.MISC)
                            .sized(0.4F, 0.4F)
                            .clientTrackingRange(20)
                            .updateInterval(1)
                            .build(new ResourceLocation(HalfLifeMod.MOD_ID, "acid_thrown").toString()));
    public static final RegistryObject<EntityType<AcidThrownBM>> ACID_THROWNBM =
            ENTITY_TYPES.register("acid_thrownbm",
                    () -> EntityType.Builder.<AcidThrownBM>of(AcidThrownBM::new, MobCategory.MISC)
                            .sized(0.4F, 0.4F)
                            .clientTrackingRange(20)
                            .updateInterval(1)
                            .build(new ResourceLocation(HalfLifeMod.MOD_ID, "acid_thrownbm").toString()));


    public static final RegistryObject<EntityType<UnderbarrelGranade>> UNDER_NADE =
            ENTITY_TYPES.register("undernade",
                    () -> EntityType.Builder.<UnderbarrelGranade>of(UnderbarrelGranade::new, MobCategory.MISC)
                            .sized(0.4F, 0.4F)
                            .clientTrackingRange(20)
                            .updateInterval(1)
                            .build(new ResourceLocation(HalfLifeMod.MOD_ID, "undernade").toString()));

    public static final RegistryObject<EntityType<SporeShot>> SPORESHOT =
            ENTITY_TYPES.register("spore_shot",
                    () -> EntityType.Builder.<SporeShot>of(SporeShot::new, MobCategory.MISC)
                            .sized(0.3F, 0.3F)
                            .clientTrackingRange(20)
                            .updateInterval(1)
                            .build(new ResourceLocation(HalfLifeMod.MOD_ID, "spore_shot").toString()));

    public static final RegistryObject<EntityType<Granade>> GRANADE_ONE =
            ENTITY_TYPES.register("granade_one",
                    () -> EntityType.Builder.<Granade>of(Granade::new, MobCategory.MISC)
                            .sized(0.3F, 0.3F)
                            .clientTrackingRange(20)
                            .updateInterval(1)
                            .build(new ResourceLocation(HalfLifeMod.MOD_ID, "granade_one").toString()));


    public static final RegistryObject<EntityType<PitdroneSpike>> PITDRONE_SPIKE =
            ENTITY_TYPES.register("pit_spike",
                    () -> EntityType.Builder.<PitdroneSpike>of(PitdroneSpike::new, MobCategory.MISC)
                            .sized(0.25F, 0.25F)
                            .clientTrackingRange(20)
                            .updateInterval(1)
                            .build(new ResourceLocation(HalfLifeMod.MOD_ID, "pit_spike").toString()));
    public static final RegistryObject<EntityType<BeeProjectile>> BEE_PROJECTILE =
            ENTITY_TYPES.register("bee_projectile",
                    () -> EntityType.Builder.<BeeProjectile>of(BeeProjectile::new, MobCategory.MISC)
                            .sized(0.2F, 0.2F)
                            .clientTrackingRange(20)
                            .updateInterval(1)
                            .build(new ResourceLocation(HalfLifeMod.MOD_ID, "bee_projectile").toString()));


    public static final RegistryObject<EntityType<Flechette>> FLECHETTE =
            ENTITY_TYPES.register("flechette",
                    () -> EntityType.Builder.<Flechette>of(Flechette::new, MobCategory.MISC)
                            .sized(0.25F, 0.25F)
                            .clientTrackingRange(20)
                            .updateInterval(1)
                            .build(new ResourceLocation(HalfLifeMod.MOD_ID, "flechette").toString()));


    public static final RegistryObject<EntityType<VoltigoreShock>> VOLTIGORE_SHOCK =
            ENTITY_TYPES.register("voltigore_shock",
                    () -> EntityType.Builder.<VoltigoreShock>of(VoltigoreShock::new, MobCategory.MISC)
                            .sized(0.25F, 0.25F)
                            .clientTrackingRange(20)
                            .updateInterval(1)
                            .build(new ResourceLocation(HalfLifeMod.MOD_ID, "voltigore_shock").toString()));



    public static final RegistryObject<EntityType<BugbaitProjectile>> BUGBAIT_PROJECTILE =
            ENTITY_TYPES.register("bugbait_projectile",
                    () -> EntityType.Builder.<BugbaitProjectile>of(BugbaitProjectile::new, MobCategory.MISC)
                            .sized(0.2F, 0.2F)
                            .clientTrackingRange(20)
                            .updateInterval(1)
                            .build(new ResourceLocation(HalfLifeMod.MOD_ID, "bugbait_projectile").toString()));


    public static final RegistryObject<EntityType<Bullet>> BULLET_ONE =
            ENTITY_TYPES.register("bullet_one",
                    () -> EntityType.Builder.<Bullet>of(Bullet::new, MobCategory.MISC)
                            .sized(0.1F, 0.1F)
                            .clientTrackingRange(20)
                            .updateInterval(1)
                            .build(new ResourceLocation(HalfLifeMod.MOD_ID, "bullet_one").toString()));
    public static final RegistryObject<EntityType<FloorBullet>> BULLET_FLOOR =
            ENTITY_TYPES.register("bullet_floor",
                    () -> EntityType.Builder.<FloorBullet>of(FloorBullet::new, MobCategory.MISC)
                            .sized(0.35F, 0.5F)
                            .clientTrackingRange(20)
                            .updateInterval(1)
                            .build(new ResourceLocation(HalfLifeMod.MOD_ID, "bullet_floor").toString()));


    public static final RegistryObject<EntityType<VortLightningProjectile>> VORT_LIGHTNING_PROJETILE =
            ENTITY_TYPES.register("vlp",
                    () -> EntityType.Builder.<VortLightningProjectile>of(VortLightningProjectile::new, MobCategory.MISC)
                            .sized(0.1F, 0.1F)
                            .clientTrackingRange(20)
                            .updateInterval(1)
                            .build(new ResourceLocation(HalfLifeMod.MOD_ID, "vlp").toString()));


    public static final RegistryObject<EntityType<TeleportingBullet>> BULLET_TP =
            ENTITY_TYPES.register("bullet_tp",
                    () -> EntityType.Builder.<TeleportingBullet>of(TeleportingBullet::new, MobCategory.MISC)
                            .sized(0.6F, 0.6F)
                            .clientTrackingRange(20)
                            .updateInterval(1)
                            .build(new ResourceLocation(HalfLifeMod.MOD_ID, "bullet_tp").toString()));


    public static final RegistryObject<EntityType<ShockProjectile>> SHOCK_SHOT =
            ENTITY_TYPES.register("shock_shot",
                    () -> EntityType.Builder.<ShockProjectile>of(ShockProjectile::new, MobCategory.MISC)
                            .sized(0.25F, 0.25F)
                            .clientTrackingRange(20)
                            .updateInterval(1)
                            .build(new ResourceLocation(HalfLifeMod.MOD_ID, "shock_shot").toString()));

    public static final RegistryObject<EntityType<ControllerBaseProjectile>> CON_PROJECTILE =
            ENTITY_TYPES.register("con_projectile",
                    () -> EntityType.Builder.<ControllerBaseProjectile>of(ControllerBaseProjectile::new, MobCategory.MISC)
                            .sized(0.25F, 0.25F)
                            .clientTrackingRange(20)
                            .updateInterval(1)
                            .build(new ResourceLocation(HalfLifeMod.MOD_ID, "con_projectile").toString()));
    public static final RegistryObject<EntityType<ControllerBigProjectile>> CON_BIG_PROJECTILE =
            ENTITY_TYPES.register("con_b_projectile",
                    () -> EntityType.Builder.<ControllerBigProjectile>of(ControllerBigProjectile::new, MobCategory.MISC)
                            .sized(0.25F, 0.25F)
                            .clientTrackingRange(20)
                            .updateInterval(1)
                            .build(new ResourceLocation(HalfLifeMod.MOD_ID, "con_b_projectile").toString()));


    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}