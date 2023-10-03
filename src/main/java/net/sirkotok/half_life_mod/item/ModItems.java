package net.sirkotok.half_life_mod.item;


import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.sirkotok.half_life_mod.HalfLifeMod;
import net.sirkotok.half_life_mod.entity.ModEntities;
import net.sirkotok.half_life_mod.item.custom.*;
import net.sirkotok.half_life_mod.item.custom.armor.SecurityGuardArmorItem;
import net.sirkotok.half_life_mod.item.custom.gun.Pistol_1_Item;


public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, HalfLifeMod.MOD_ID);

   public static final RegistryObject<Item> HEADCRAB_ONE_SPAWN_EGG = ITEMS.register("headcrab_1_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntities.HEADCRAB_HL1, 0xa39b74, 0x614a0d   ,
                    new Item.Properties()));
    public static final RegistryObject<Item> CHUMTOAD_SPAWN_EGG = ITEMS.register("chumtoad_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntities.CHUMTOAD, 0xc82eba, 0x40073a   ,
                    new Item.Properties()));
    public static final RegistryObject<Item> SNARK_SPAWN_EGG = ITEMS.register("snark_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntities.SNARK, 0xff011b, 0x000000   ,
                    new Item.Properties()));
    public static final RegistryObject<Item> PENGUIN_SPAWN_EGG = ITEMS.register("penguin_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntities.PENGUIN, 0xffffff, 0x000000   ,
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
    public static final RegistryObject<Item> BARNEY_SPAWN_EGG = ITEMS.register("barney_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntities.BARNEY, 0x5352fa, 0x000000   ,
                    new Item.Properties()));


    //Fake Items used for models in projectiles lmao
    public static final RegistryObject<Item> FAKE_BULLET = ITEMS.register("bullet",
            () -> new FakeBulletItem(new Item.Properties()));

  //  public static final RegistryObject<Item> ENTITY_THROWER = ITEMS.register("entity_thrower",
  //          () -> new EntityThrowingItem(new Item.Properties()));
    public static final RegistryObject<Item> CHUMTOAD_THROWER = ITEMS.register("chumtoad_thrower",
            () -> new ChumtoadItem(new Item.Properties()));
    public static final RegistryObject<Item> SNARK_THROWER = ITEMS.register("snark_thrower",
            () -> new SnarkItem(new Item.Properties()));
    public static final RegistryObject<Item> PENGUIN_THROWER = ITEMS.register("penguin_thrower",
            () -> new PenguinItem(new Item.Properties()));

    //Guns
    public static final RegistryObject<Item> PISTOL = ITEMS.register("pistol_one_item",
            () -> new Pistol_1_Item(new Item.Properties()));

    //Armor
    public static final RegistryObject<Item> SECURITY_GUARD_HELMET = ITEMS.register("security_guard_helmet",
            () -> new SecurityGuardArmorItem(ModArmorMaterials.SECURITY_GUARD, ArmorItem.Type.HELMET, new Item.Properties()));
    public static final RegistryObject<Item> SECURITY_GUARD_VEST = ITEMS.register("security_guard_chestplate",
            () -> new SecurityGuardArmorItem(ModArmorMaterials.SECURITY_GUARD, ArmorItem.Type.CHESTPLATE, new Item.Properties()));



    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}