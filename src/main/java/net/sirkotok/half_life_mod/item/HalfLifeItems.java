package net.sirkotok.half_life_mod.item;


import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.sirkotok.half_life_mod.HalfLifeMod;
import net.sirkotok.half_life_mod.block.HalfLifeBlocks;
import net.sirkotok.half_life_mod.entity.HalfLifeEntities;
import net.sirkotok.half_life_mod.item.custom.*;
import net.sirkotok.half_life_mod.item.custom.armor.SecurityGuardArmorItem;
import net.sirkotok.half_life_mod.item.custom.fake.FakeBulletItem;
import net.sirkotok.half_life_mod.item.custom.gun.*;
import net.sirkotok.half_life_mod.item.custom.spawnegg.BarnacleSpawnEggItem;
import net.sirkotok.half_life_mod.item.custom.spawnegg.HecuSpawnEgg;
import net.sirkotok.half_life_mod.item.custom.spawnegg.VortigoreSpawnEggItem;


public class HalfLifeItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, HalfLifeMod.MOD_ID);

   public static final RegistryObject<Item> HEADCRAB_ONE_SPAWN_EGG = ITEMS.register("headcrab_1_spawn_egg",
            () -> new ForgeSpawnEggItem(HalfLifeEntities.HEADCRAB_HL1, 0xa39b74, 0x614a0d   ,
                    new Item.Properties()));
   public static final RegistryObject<Item> HOUNDEYE_SPAWN_EGG = ITEMS.register("houndeye_spawn_egg",
           () -> new ForgeSpawnEggItem(HalfLifeEntities.HOUNDEYE, 0xecb016, 0x28bae1   ,
                   new Item.Properties()));

   public static final RegistryObject<Item> HOTEYE_SPAWN_EGG = ITEMS.register("hoteye_spawn_egg",
           () -> new ForgeSpawnEggItem(HalfLifeEntities.HOTEYE, 0xef7401, 0xff0000   ,
                   new Item.Properties()));

 public static final RegistryObject<Item> CONTROLLER_SPAWN_EGG = ITEMS.register("controller_spawn_egg",
         () -> new ForgeSpawnEggItem(HalfLifeEntities.CONTROLLER, 0xd14816, 0x450200   ,
                 new Item.Properties()));

   public static final RegistryObject<Item> BARNACLE_ONE_SPAWN_EGG = ITEMS.register("barnacle_1_spawn_egg",
           () -> new BarnacleSpawnEggItem(HalfLifeEntities.BARNACLE, 0xef5b38, 0x9e7408   ,
                   new Item.Properties()));
    public static final RegistryObject<Item> SHOCKROACH_SPAWN_EGG = ITEMS.register("shockroach_spawn_egg",
            () -> new ForgeSpawnEggItem(HalfLifeEntities.SHOCKROACH, 0x11117b, 0xc5a54c  ,
                    new Item.Properties()));
    public static final RegistryObject<Item> CHUMTOAD_SPAWN_EGG = ITEMS.register("chumtoad_spawn_egg",
            () -> new ForgeSpawnEggItem(HalfLifeEntities.CHUMTOAD, 0xc82eba, 0x40073a   ,
                    new Item.Properties()));
    public static final RegistryObject<Item> SNARK_SPAWN_EGG = ITEMS.register("snark_spawn_egg",
            () -> new ForgeSpawnEggItem(HalfLifeEntities.SNARK, 0xff011b, 0x54060e  ,
                    new Item.Properties()));
 public static final RegistryObject<Item> SNARKNEST_SPAWN_EGG = ITEMS.register("snarknest_spawn_egg",
         () -> new ForgeSpawnEggItem(HalfLifeEntities.SNARKNEST, 0x7d010e, 0xff011b   ,
                 new Item.Properties()));
    public static final RegistryObject<Item> PENGUIN_SPAWN_EGG = ITEMS.register("penguin_spawn_egg",
            () -> new ForgeSpawnEggItem(HalfLifeEntities.PENGUIN, 0xffffff, 0x000000   ,
                    new Item.Properties()));

   public static final RegistryObject<Item> VORTIGORE_SPAWN_EGG = ITEMS.register("vortigore_spawn_egg",
           () -> new VortigoreSpawnEggItem(HalfLifeEntities.VOLTIGORE, 0xf15100, 0xf100cd   ,
                   new Item.Properties()));

    public static final RegistryObject<Item> SHOCKTROOPER_SPAWN_EGG = ITEMS.register("shocktrooper_spawn_egg",
            () -> new VortigoreSpawnEggItem(HalfLifeEntities.SHOCKTROOPER, 0x9fae9c, 0x0bf3eb,
                    new Item.Properties()));





    public static final RegistryObject<Item> HEADCRAB_TWO_SPAWN_EGG = ITEMS.register("headcrab_2_spawn_egg",
            () -> new ForgeSpawnEggItem(HalfLifeEntities.HEADCRAB_HL2, 0xa68c60, 0x47271b   ,
                    new Item.Properties()));
    public static final RegistryObject<Item> HEADCRAB_ALYX_SPAWN_EGG = ITEMS.register("headcrab_a_spawn_egg",
            () -> new ForgeSpawnEggItem(HalfLifeEntities.HEADCRAB_HLA, 0xbfa67a, 0x0d0c0c   ,
                    new Item.Properties()));
    public static final RegistryObject<Item> HEADCRAB_ARMOR_SPAWN_EGG = ITEMS.register("headcrab_armored_spawn_egg",
            () -> new ForgeSpawnEggItem(HalfLifeEntities.HEADCRAB_ARMORED, 0x706e66, 0xa3051f   ,
                    new Item.Properties()));


    public static final RegistryObject<Item> HEADCRAB_POISON_HL2_SPAWN_EGG = ITEMS.register("headcrab_poison_2_spawn_egg",
            () -> new ForgeSpawnEggItem(HalfLifeEntities.HEADCRAB_POISON_HL2, 0x2f2f2f  , 0x989fa1 ,
                    new Item.Properties()));
    public static final RegistryObject<Item> HEADCRAB_POISON_HLA_SPAWN_EGG = ITEMS.register("headcrab_poison_3_spawn_egg",
            () -> new ForgeSpawnEggItem(HalfLifeEntities.HEADCRAB_POISON_HLA, 0x989fa1, 0x2f2f2f   ,
                    new Item.Properties()));

    public static final RegistryObject<Item> HEADCRAB_FAST_SPAWN_EGG = ITEMS.register("headcrab_fast_spawn_egg",
            () -> new ForgeSpawnEggItem(HalfLifeEntities.HEADCRAB_FAST, 0xa68c60, 0x722F37   ,
                    new Item.Properties()));


    public static final RegistryObject<Item> HEADCRAB_ZOMBIE_FAST_SPAWN_EGG = ITEMS.register("headcrab_zombie_fast_spawn_egg",
            () -> new ForgeSpawnEggItem(HalfLifeEntities.ZOMBIE_FAST, 0xf4ebae, 0xa2201c   ,
                    new Item.Properties()));
    public static final RegistryObject<Item> GONOME_SPAWN_EGG = ITEMS.register("gonome_spawn_egg",
            () -> new ForgeSpawnEggItem(HalfLifeEntities.GONOME, 0x9b5209, 0xa3ec9f,
                    new Item.Properties()));
    public static final RegistryObject<Item> PZOMBIE_SPAWN_EGG = ITEMS.register("pzombie_spawn_egg",
            () -> new ForgeSpawnEggItem(HalfLifeEntities.PZOMBIE, 0x7f15b0, 0x16071d   ,
                    new Item.Properties()));
    public static final RegistryObject<Item> VORTHL1_SPAWN_EGG = ITEMS.register("vorthl1_spawn_egg",
            () -> new ForgeSpawnEggItem(HalfLifeEntities.VORTHL1, 0x6c4b07, 0x329b05   ,
                    new Item.Properties()));

    public static final RegistryObject<Item> VORTHL2_SPAWN_EGG = ITEMS.register("vorthl2_spawn_egg",
            () -> new ForgeSpawnEggItem(HalfLifeEntities.VORTHL2, 0x808759, 0x329b05   ,
                    new Item.Properties()));


    public static final RegistryObject<Item> VZOMBIE_SPAWN_EGG = ITEMS.register("vzombie_spawn_egg",
            () -> new ForgeSpawnEggItem(HalfLifeEntities.VZOMBIE, 0x975103, 0xff5600   ,
                    new Item.Properties()));

    public static final RegistryObject<Item> BULLSQUID_SPAWN_EGG = ITEMS.register("bullsquid_spawn_egg",
            () -> new ForgeSpawnEggItem(HalfLifeEntities.BULLSQUID, 0x4f6307, 0x781104   ,
                    new Item.Properties()));
    public static final RegistryObject<Item> AQUATIC_BULLSQUID_SPAWN_EGG = ITEMS.register("aquatic_bullsquid_spawn_egg",
            () -> new ForgeSpawnEggItem(HalfLifeEntities.AQUATIC_BULLSQUID, 0x296307, 0xc5be4e  ,
                    new Item.Properties()));

    public static final RegistryObject<Item> PITDRONE_SPAWN_EGG = ITEMS.register("pitdrone_spawn_egg",
            () -> new ForgeSpawnEggItem(HalfLifeEntities.PITDRONE, 0x7d4a10, 0x9b9b9b   ,
                    new Item.Properties()));
    public static final RegistryObject<Item> ANTLION_SPAWN_EGG = ITEMS.register("antlion_spawn_egg",
            () -> new ForgeSpawnEggItem(HalfLifeEntities.ANTLION, 0xa0e463, 0xd3c905   ,
                    new Item.Properties()));
    public static final RegistryObject<Item> ANTLIONWORKER_SPAWN_EGG = ITEMS.register("antlionworker_spawn_egg",
            () -> new ForgeSpawnEggItem(HalfLifeEntities.ANTLIONWORKER, 0x6ffcbb, 0x656864   ,
                    new Item.Properties()));

    public static final RegistryObject<Item> BARNEY_SPAWN_EGG = ITEMS.register("barney_spawn_egg",
            () -> new ForgeSpawnEggItem(HalfLifeEntities.BARNEY, 0x5352fa, 0x000000   ,
                    new Item.Properties()));

    public static final RegistryObject<Item> HECU_SPAWN_EGG = ITEMS.register("hecu_grunt_spawn_egg",
            () -> new ForgeSpawnEggItem(HalfLifeEntities.HECU_GRUNT, 0xd6cec3, 0x505237  ,
                    new Item.Properties()));
    public static final RegistryObject<Item> HECU_GASMASK_SPAWN_EGG = ITEMS.register("hecu_gasmask_spawn_egg",
            () -> new HecuSpawnEgg(HalfLifeEntities.HECU_GRUNT, 0xd6cec3, 0x505237  ,
                    new Item.Properties(), 0));
    public static final RegistryObject<Item> HECU_BERET_SPAWN_EGG = ITEMS.register("hecu_beret_spawn_egg",
            () -> new HecuSpawnEgg(HalfLifeEntities.HECU_GRUNT, 0xd6cec3, 0x505237  ,
                    new Item.Properties(), 1));
    public static final RegistryObject<Item> HECU_SAW_SPAWN_EGG = ITEMS.register("hecu_saw_spawn_egg",
            () -> new HecuSpawnEgg(HalfLifeEntities.HECU_GRUNT, 0xd6cec3, 0x505237  ,
                    new Item.Properties(), 3));
    public static final RegistryObject<Item> HECU_SHOTGUN_SPAWN_EGG = ITEMS.register("hecu_shotgun_spawn_egg",
            () -> new HecuSpawnEgg(HalfLifeEntities.HECU_GRUNT, 0xd6cec3, 0x505237  ,
                    new Item.Properties(), 2));
    public static final RegistryObject<Item> HECU_MEDIC_SPAWN_EGG = ITEMS.register("hecu_medic_spawn_egg",
            () -> new HecuSpawnEgg(HalfLifeEntities.HECU_GRUNT, 0xd6cec3, 0x505237  ,
                    new Item.Properties(), 5));
    public static final RegistryObject<Item> HECU_VENT_SPAWN_EGG = ITEMS.register("hecu_vent_spawn_egg",
            () -> new HecuSpawnEgg(HalfLifeEntities.HECU_GRUNT, 0xd6cec3, 0x505237  ,
                    new Item.Properties(), 4));
 public static final RegistryObject<Item> SCIENTIST_SPAWN_EGG = ITEMS.register("scientist_spawn_egg",
         () -> new ForgeSpawnEggItem(HalfLifeEntities.SCIENTIST, 0xe7dada, 0xcbff9a   ,
                 new Item.Properties()));

    public static final RegistryObject<Item> HL1_ZOMBIE_SCIENTIST_SPAWN_EGG = ITEMS.register("hl1_zombie_scientist_spawn_egg",
            () -> new ForgeSpawnEggItem(HalfLifeEntities.ZOMBIE_HL1, 0xaf3300, 0xdbd9d6   ,
                    new Item.Properties()));

    public static final RegistryObject<Item> HL2_ZOMBIE_SPAWN_EGG = ITEMS.register("hl2_zombie_spawn_egg",
            () -> new ForgeSpawnEggItem(HalfLifeEntities.ZOMBIE_HL2, 0xeca540, 0xdbd9d6   ,
                    new Item.Properties()));
    public static final RegistryObject<Item> GONARCH_SPAWN_EGG = ITEMS.register("gonarch_spawn_egg",
            () -> new ForgeSpawnEggItem(HalfLifeEntities.GONARCH, 0x5d573a, 0xf2e297   ,
                    new Item.Properties()));

    public static final RegistryObject<Item> GONARCHBM_SPAWN_EGG = ITEMS.register("gonarchbm_spawn_egg",
            () -> new ForgeSpawnEggItem(HalfLifeEntities.GONARCHBM, 0xee7c7c, 0x9d0808   ,
                    new Item.Properties()));
    public static final RegistryObject<Item> COCKROACH_SPAWN_EGG = ITEMS.register("cockroach_spawn_egg",
            () -> new ForgeSpawnEggItem(HalfLifeEntities.COCKROACH, 0xc2660b, 0xeae3dc   ,
                    new Item.Properties()));


    public static final RegistryObject<Item> BABY_HEADCRAB_SPAWN_EGG = ITEMS.register("baby_headcrab_spawn_egg",
            () -> new ForgeSpawnEggItem(HalfLifeEntities.BABY_HEADCRAB, 0xe9f1a3, 0xc8c9c5   ,
                    new Item.Properties()));
    public static final RegistryObject<Item> LEECH_SPAWN_EGG = ITEMS.register("leech_spawn_egg",
            () -> new ForgeSpawnEggItem(HalfLifeEntities.LEECH, 0xeeeddb, 0xaba76b   ,
                    new Item.Properties()));
    public static final RegistryObject<Item> ALIENGRUNT_SPAWN_EGG = ITEMS.register("aliengrunt_spawn_egg",
            () -> new ForgeSpawnEggItem(HalfLifeEntities.ALIENGRUNT, 0x664e2b, 0x925cf4   ,
                    new Item.Properties()));
    public static final RegistryObject<Item> GARG_SPAWN_EGG = ITEMS.register("garg_spawn_egg",
            () -> new ForgeSpawnEggItem(HalfLifeEntities.GARGANTUA, 0x348494, 0xafafaf   ,
                    new Item.Properties()));
    public static final RegistryObject<Item> HUNTER_SPAWN_EGG = ITEMS.register("hunter_spawn_egg",
            () -> new ForgeSpawnEggItem(HalfLifeEntities.HUNTER, 0xc3898, 0x18fce3   ,
                    new Item.Properties()));
   public static final RegistryObject<Item> SHARK_SPAWN_EGG = ITEMS.register("shark_spawn_egg",
         () -> new ForgeSpawnEggItem(HalfLifeEntities.SHARK, 0x71a549, 0xb22c2a   ,
                 new Item.Properties()));

    public static final RegistryObject<Item> HYDRA_SPAWN_EGG = ITEMS.register("hl_hydra_spawn_egg",
            () -> new ForgeSpawnEggItem(HalfLifeEntities.HYDRA, 0x28eaef, 0xb2f3f5   ,
                    new Item.Properties()));


    public static final RegistryObject<Item> ARCHER_SPAWN_EGG = ITEMS.register("archerfish_spawn_egg",
            () -> new ForgeSpawnEggItem(HalfLifeEntities.ARCHER, 0xe91e1e, 0x01a7ff   ,
                    new Item.Properties()));

    public static final RegistryObject<Item> MANHACK_SPAWN_EGG = ITEMS.register("manhack_spawn_egg",
            () -> new ForgeSpawnEggItem(HalfLifeEntities.MANHACK, 0x6d6161, 0xf20b0b   ,
                    new Item.Properties()));



    //Fake Items used for models/textures in projectiles lmao

    //TODO: remove this shit and change it into normal textures/models with a normal renderer, there is literally no benefit to doing it this way and not that way
    public static final RegistryObject<Item> FAKE_BULLET = ITEMS.register("bullet",
            () -> new FakeBulletItem(new Item.Properties()));

    public static final RegistryObject<Item> FAKE_SHOCK = ITEMS.register("shock",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> FAKE_CON = ITEMS.register("con_shock",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> FAKE_PORTAL = ITEMS.register("portal_project",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> VORT_DEBUG = ITEMS.register("debug",
            () -> new Debugitem(new Item.Properties()));
    // Entity throwers

    public static final RegistryObject<Item> CHUMTOAD_THROWER = ITEMS.register("chumtoad_thrower",
            () -> new ChumtoadItem(new Item.Properties()));
    public static final RegistryObject<Item> SNARK_THROWER = ITEMS.register("snark_thrower",
            () -> new SnarkItem(new Item.Properties()));
    public static final RegistryObject<Item> PENGUIN_THROWER = ITEMS.register("penguin_thrower",
            () -> new PenguinItem(new Item.Properties()));

    public static final RegistryObject<Item> GRANADE_HL1 = ITEMS.register("granade",
            () -> new Granade_hl1(new Item.Properties()));

    //Guns
    public static final RegistryObject<Item> PISTOL = ITEMS.register("pistol_one_item",
            () -> new Pistol_1_Item(new Item.Properties()));
    public static final RegistryObject<Item> SAW = ITEMS.register("m249",
            () -> new SAW_Item(new Item.Properties()));
    public static final RegistryObject<Item> DEAGLE = ITEMS.register("desert_eagle",
            () -> new Deagle_Item(new Item.Properties()));

    public static final RegistryObject<Item> SHOTGUN = ITEMS.register("shotgun_hl1",
            () -> new Shotgun_1_Item(new Item.Properties()));

    public static final RegistryObject<Item> GRAVITYGUN = ITEMS.register("gravitygun",
            () -> new GravityGun(new Item.Properties()));
    public static final RegistryObject<Item> SUPERGRAVITYGUN = ITEMS.register("supergravitygun",
            () -> new SuperGravityGun(new Item.Properties()));

    public static final RegistryObject<Item> BUGBAIT = ITEMS.register("bugbait",
            () -> new BugbaitItem(new Item.Properties()));
    public static final RegistryObject<Item> SMG_HL1 = ITEMS.register("smg_hl1",
         () -> new SMG_1_Item(new Item.Properties()));

   // public static final RegistryObject<Item> SHOTGUN_HL1 = ITEMS.register("shotgun_hl1",
    //        () -> new ShotgunGunItem(new Item.Properties()));
    public static final RegistryObject<Item> DISPLACER = ITEMS.register("displacer_cannon",
         () -> new Displacer_cannon(new Item.Properties()));
    public static final RegistryObject<Item> DISPLACER_NETHER = ITEMS.register("displacer_nether",
            () -> new Displacer_nether(new Item.Properties()));
    public static final RegistryObject<Item> DISPLACER_END = ITEMS.register("displacer_end",
            () -> new Displacer_end(new Item.Properties()));
    public static final RegistryObject<Item> SHOCKROACH_ITEM = ITEMS.register("shockroach_item",
            () -> new Shockroach_Item(new Item.Properties()));
    public static final RegistryObject<Item> HIVEHAND_ITEM = ITEMS.register("hivegun",
            () -> new Hivehand_Item(new Item.Properties()));

    //Armor
    public static final RegistryObject<Item> SECURITY_GUARD_HELMET = ITEMS.register("security_guard_helmet",
            () -> new SecurityGuardArmorItem(HalfLifeArmorMaterials.SECURITY_GUARD, ArmorItem.Type.HELMET, new Item.Properties()));
    public static final RegistryObject<Item> SECURITY_GUARD_VEST = ITEMS.register("security_guard_chestplate",
            () -> new SecurityGuardArmorItem(HalfLifeArmorMaterials.SECURITY_GUARD, ArmorItem.Type.CHESTPLATE, new Item.Properties()));

    // Block items

    public static final RegistryObject<Item> VOLTIGORE_EGG_BLOCK_ITEM = ITEMS.register("voltigore_egg_block_item",
            () -> new BlockItem(HalfLifeBlocks.VOLTIGORE_EGG.get(), new Item.Properties()));


    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}