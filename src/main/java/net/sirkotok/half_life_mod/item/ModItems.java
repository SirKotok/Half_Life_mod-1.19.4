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
import net.sirkotok.half_life_mod.block.ModBlocks;
import net.sirkotok.half_life_mod.entity.ModEntities;
import net.sirkotok.half_life_mod.item.custom.*;
import net.sirkotok.half_life_mod.item.custom.armor.SecurityGuardArmorItem;
import net.sirkotok.half_life_mod.item.custom.fake.FakeBulletItem;
import net.sirkotok.half_life_mod.item.custom.gun.Pistol_1_Item;
import net.sirkotok.half_life_mod.item.custom.gun.Shockroach_Item;
import net.sirkotok.half_life_mod.item.custom.spawnegg.BarnacleSpawnEggItem;
import net.sirkotok.half_life_mod.item.custom.spawnegg.VortigoreSpawnEggItem;


public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, HalfLifeMod.MOD_ID);

   public static final RegistryObject<Item> HEADCRAB_ONE_SPAWN_EGG = ITEMS.register("headcrab_1_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntities.HEADCRAB_HL1, 0xa39b74, 0x614a0d   ,
                    new Item.Properties()));
   public static final RegistryObject<Item> HOUNDEYE_SPAWN_EGG = ITEMS.register("houndeye_spawn_egg",
           () -> new ForgeSpawnEggItem(ModEntities.HOUNDEYE, 0xecb016, 0x28bae1   ,
                   new Item.Properties()));

   public static final RegistryObject<Item> HOTEYE_SPAWN_EGG = ITEMS.register("hoteye_spawn_egg",
           () -> new ForgeSpawnEggItem(ModEntities.HOTEYE, 0xef7401, 0xff0000   ,
                   new Item.Properties()));

   public static final RegistryObject<Item> BARNACLE_ONE_SPAWN_EGG = ITEMS.register("barnacle_1_spawn_egg",
           () -> new BarnacleSpawnEggItem(ModEntities.BARNACLE, 0xef5b38, 0x9e7408   ,
                   new Item.Properties()));
    public static final RegistryObject<Item> SHOCKROACH_SPAWN_EGG = ITEMS.register("shockroach_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntities.SHOCKROACH, 0x11117b, 0xc5a54c  ,
                    new Item.Properties()));
    public static final RegistryObject<Item> CHUMTOAD_SPAWN_EGG = ITEMS.register("chumtoad_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntities.CHUMTOAD, 0xc82eba, 0x40073a   ,
                    new Item.Properties()));
    public static final RegistryObject<Item> SNARK_SPAWN_EGG = ITEMS.register("snark_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntities.SNARK, 0xff011b, 0x54060e  ,
                    new Item.Properties()));
 public static final RegistryObject<Item> SNARKNEST_SPAWN_EGG = ITEMS.register("snarknest_spawn_egg",
         () -> new ForgeSpawnEggItem(ModEntities.SNARKNEST, 0x7d010e, 0xff011b   ,
                 new Item.Properties()));
    public static final RegistryObject<Item> PENGUIN_SPAWN_EGG = ITEMS.register("penguin_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntities.PENGUIN, 0xffffff, 0x000000   ,
                    new Item.Properties()));

   public static final RegistryObject<Item> VORTIGORE_SPAWN_EGG = ITEMS.register("vortigore_spawn_egg",
           () -> new VortigoreSpawnEggItem(ModEntities.VOLTIGORE, 0xf15100, 0xf100cd   ,
                   new Item.Properties()));

    public static final RegistryObject<Item> SHOCKTROOPER_SPAWN_EGG = ITEMS.register("shocktrooper_spawn_egg",
            () -> new VortigoreSpawnEggItem(ModEntities.SHOCKTROOPER, 0x9fae9c, 0x0bf3eb,
                    new Item.Properties()));





    public static final RegistryObject<Item> HEADCRAB_TWO_SPAWN_EGG = ITEMS.register("headcrab_2_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntities.HEADCRAB_HL2, 0xa68c60, 0x47271b   ,
                    new Item.Properties()));
    public static final RegistryObject<Item> HEADCRAB_ALYX_SPAWN_EGG = ITEMS.register("headcrab_a_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntities.HEADCRAB_HLA, 0xbfa67a, 0x0d0c0c   ,
                    new Item.Properties()));
    public static final RegistryObject<Item> HEADCRAB_ARMOR_SPAWN_EGG = ITEMS.register("headcrab_armored_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntities.HEADCRAB_ARMORED, 0x706e66, 0xa3051f   ,
                    new Item.Properties()));


    public static final RegistryObject<Item> HEADCRAB_POISON_HL2_SPAWN_EGG = ITEMS.register("headcrab_poison_2_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntities.HEADCRAB_POISON_HL2, 0x2f2f2f  , 0x989fa1 ,
                    new Item.Properties()));
    public static final RegistryObject<Item> HEADCRAB_POISON_HLA_SPAWN_EGG = ITEMS.register("headcrab_poison_3_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntities.HEADCRAB_POISON_HLA, 0x989fa1, 0x2f2f2f   ,
                    new Item.Properties()));

    public static final RegistryObject<Item> HEADCRAB_FAST_SPAWN_EGG = ITEMS.register("headcrab_fast_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntities.HEADCRAB_FAST, 0xa68c60, 0x722F37   ,
                    new Item.Properties()));
    public static final RegistryObject<Item> HEADCRAB_ZOMBIE_SPAWN_EGG = ITEMS.register("headcrab_zombie_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntities.HEADCRAB_ZOMBIE_1, 0xf4eaeb, 0x4243f   ,
                    new Item.Properties()));

    public static final RegistryObject<Item> BULLSQUID_SPAWN_EGG = ITEMS.register("bullsquid_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntities.BULLSQUID, 0x4f6307, 0x781104   ,
                    new Item.Properties()));


    public static final RegistryObject<Item> PITDRONE_SPAWN_EGG = ITEMS.register("pitdrone_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntities.PITDRONE, 0x7d4a10, 0x9b9b9b   ,
                    new Item.Properties()));

    public static final RegistryObject<Item> BARNEY_SPAWN_EGG = ITEMS.register("barney_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntities.BARNEY, 0x5352fa, 0x000000   ,
                    new Item.Properties()));
    public static final RegistryObject<Item> GONARCH_SPAWN_EGG = ITEMS.register("gonarch_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntities.GONARCH, 0x5d573a, 0xf2e297   ,
                    new Item.Properties()));
    public static final RegistryObject<Item> COCKROACH_SPAWN_EGG = ITEMS.register("cockroach_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntities.COCKROACH, 0xc2660b, 0xeae3dc   ,
                    new Item.Properties()));


    public static final RegistryObject<Item> BABY_HEADCRAB_SPAWN_EGG = ITEMS.register("baby_headcrab_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntities.BABY_HEADCRAB, 0xe9f1a3, 0xc8c9c5   ,
                    new Item.Properties()));
    public static final RegistryObject<Item> LEECH_SPAWN_EGG = ITEMS.register("leech_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntities.LEECH, 0xeeeddb, 0xaba76b   ,
                    new Item.Properties()));
   public static final RegistryObject<Item> SHARK_SPAWN_EGG = ITEMS.register("shark_spawn_egg",
         () -> new ForgeSpawnEggItem(ModEntities.SHARK, 0x71a549, 0xb22c2a   ,
                 new Item.Properties()));

    public static final RegistryObject<Item> MANHACK_SPAWN_EGG = ITEMS.register("manhack_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntities.MANHACK, 0x6d6161, 0xf20b0b   ,
                    new Item.Properties()));



    //Fake Items used for models/textures in projectiles lmao
    public static final RegistryObject<Item> FAKE_BULLET = ITEMS.register("bullet",
            () -> new FakeBulletItem(new Item.Properties()));
    public static final RegistryObject<Item> FAKE_SHOCK = ITEMS.register("shock",
            () -> new Item(new Item.Properties()));


    // Entity throwers

    public static final RegistryObject<Item> CHUMTOAD_THROWER = ITEMS.register("chumtoad_thrower",
            () -> new ChumtoadItem(new Item.Properties()));
    public static final RegistryObject<Item> SNARK_THROWER = ITEMS.register("snark_thrower",
            () -> new SnarkItem(new Item.Properties()));
    public static final RegistryObject<Item> PENGUIN_THROWER = ITEMS.register("penguin_thrower",
            () -> new PenguinItem(new Item.Properties()));

    //Guns
    public static final RegistryObject<Item> PISTOL = ITEMS.register("pistol_one_item",
            () -> new Pistol_1_Item(new Item.Properties()));
    public static final RegistryObject<Item> SHOCKROACH_ITEM = ITEMS.register("shockroach_item",
            () -> new Shockroach_Item(new Item.Properties()));

    //Armor
    public static final RegistryObject<Item> SECURITY_GUARD_HELMET = ITEMS.register("security_guard_helmet",
            () -> new SecurityGuardArmorItem(ModArmorMaterials.SECURITY_GUARD, ArmorItem.Type.HELMET, new Item.Properties()));
    public static final RegistryObject<Item> SECURITY_GUARD_VEST = ITEMS.register("security_guard_chestplate",
            () -> new SecurityGuardArmorItem(ModArmorMaterials.SECURITY_GUARD, ArmorItem.Type.CHESTPLATE, new Item.Properties()));

    // Block items

    public static final RegistryObject<Item> VOLTIGORE_EGG_BLOCK_ITEM = ITEMS.register("voltigore_egg_block_item",
            () -> new BlockItem(ModBlocks.VOLTIGORE_EGG.get(), new Item.Properties()));


    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}