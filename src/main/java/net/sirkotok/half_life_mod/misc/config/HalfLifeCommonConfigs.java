package net.sirkotok.half_life_mod.misc.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class HalfLifeCommonConfigs  {
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec.Builder BUILDER2 = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec.Builder BUILDER3 = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec INFIGHTINGSPEC;
    public static final ForgeConfigSpec WEOPONSPEC;
    public static final ForgeConfigSpec ENTITYSPEC;

  //  public static final ForgeConfigSpec.ConfigValue<String> GRAVITY_GUN_BLACKLIST;
  public static final ForgeConfigSpec.ConfigValue<Boolean> AUTO_RELOAD;
    public static final ForgeConfigSpec.ConfigValue<Boolean> INFIGHTING_RACEX_XEN;
    public static final ForgeConfigSpec.ConfigValue<Boolean> INFIGHTING_RACEX_NORMAL;
    public static final ForgeConfigSpec.ConfigValue<Boolean> INFIGHTING_RACEX_ANTLION;
    public static final ForgeConfigSpec.ConfigValue<Boolean> INFIGHTING_ANTLION_NORMAL;
    public static final ForgeConfigSpec.ConfigValue<Boolean> INFIGHTING_RACEX_HEADCRAB;
    public static final ForgeConfigSpec.ConfigValue<Boolean> INFIGHTING_HEADCRAB_ANTLIONS;
    public static final ForgeConfigSpec.ConfigValue<Boolean> INFIGHTING_HEADCRAB_XEN;
    public static final ForgeConfigSpec.ConfigValue<Boolean> INFIGHTING_HEADCRAB_NORMAL;
    public static final ForgeConfigSpec.ConfigValue<Boolean> INFIGHTING_ANTLION_COMBINE;
    public static final ForgeConfigSpec.ConfigValue<Boolean> INFIGHTING_RACEX_COMBINE;
    public static final ForgeConfigSpec.ConfigValue<Boolean> INFIGHTING_XEN_COMBINE;
    public static final ForgeConfigSpec.ConfigValue<Boolean> INFIGHTING_XEN_NORMAL;
    public static final ForgeConfigSpec.ConfigValue<Boolean> INFIGHTING_XEN_ANTLION;
    public static final ForgeConfigSpec.ConfigValue<Boolean> INFIGHTING_HECU_ANTLION;
    public static final ForgeConfigSpec.ConfigValue<Boolean> INFIGHTING_HECU_HEADCRAB;
    public static final ForgeConfigSpec.ConfigValue<Boolean> INFIGHTING_HECU_COMBINE;
    public static final ForgeConfigSpec.ConfigValue<Boolean> INFIGHTING_HECU_XEN;
    public static final ForgeConfigSpec.ConfigValue<Boolean> INFIGHTING_HECU_NORMAL;
    public static final ForgeConfigSpec.ConfigValue<Boolean> INFIGHTING_HECU_RACEX;
    public static final ForgeConfigSpec.ConfigValue<Boolean> INFIGHTING_HEADCRAB_COMBINE;
    public static final ForgeConfigSpec.ConfigValue<Boolean> INFIGHTING_COMBINE_VS_NORMAL;
    public static final ForgeConfigSpec.ConfigValue<Boolean> INFIGHTING_BULLSQUID_HUNTS_HEADCRABS;
    public static final ForgeConfigSpec.ConfigValue<Boolean> INFIGHTING_BULLSQUID_HUNTS_ANIMALS;
    public static final ForgeConfigSpec.ConfigValue<Boolean> INFIGHTING_PITDRONE_HUNTS_HEADCRABS;
    public static final ForgeConfigSpec.ConfigValue<Boolean> INFIGHTING_PITDRONE_HUNTS_SHOCKROACH;
    public static final ForgeConfigSpec.ConfigValue<Boolean> INFIGHTING_BULLSQUID;
    public static final ForgeConfigSpec.ConfigValue<Boolean> INFIGHTING_BULLSQUID_PITDRONE;




    public static final ForgeConfigSpec.ConfigValue<Float> GARG_SETFIRE_CHANCE;
    public static final ForgeConfigSpec.ConfigValue<Boolean> CHUMTOAD_LINE_OF_SIGHT;

    static {
        BUILDER.push("InfightingConfig");
        BUILDER.comment("This Config defines the infighting between factions of enemies, as well as some factionless entities (bullsquids).");
        BUILDER.comment("The \"Normal\" category includes any creature that is an enemy, but isnt from one of the factions. " +
                "That includes normal enemies like Zombies and Skeletons, as well as new enemies like Houndeyes. It will also include most enemies from other mods.");
        INFIGHTING_RACEX_XEN = BUILDER.comment("Do Race X and Xen forces fight")
                   .define("racexvsxen", true);
        INFIGHTING_RACEX_HEADCRAB = BUILDER.comment("Do Race X and Headcrabs fight")
                .define("racexvsheadcrab", false);
        INFIGHTING_RACEX_ANTLION = BUILDER.comment("Do Race X and Antlions fight")
                .define("racexvsantlion", false);
        INFIGHTING_RACEX_NORMAL = BUILDER.comment("Do Race X attack other nonfriendly opponents")
                .define("racexvsnormal", false);
        INFIGHTING_RACEX_COMBINE = BUILDER.comment("Do Race X and The Combine fight")
                .define("racexvscombine", true);
        INFIGHTING_HEADCRAB_ANTLIONS = BUILDER.comment("Do Antlions and Headcrabs fight")
                .define("antlionvsheadcrab", true);
        INFIGHTING_ANTLION_COMBINE = BUILDER.comment("Do Antlions and The Combine fight")
                .define("antlionvscombine", true);
        INFIGHTING_HEADCRAB_COMBINE = BUILDER.comment("Do Headcrabs and The Combine fight")
                .define("headcrabvscombine", true);
        INFIGHTING_HEADCRAB_NORMAL = BUILDER.comment("Do Headcrabs attack other nonfriendly opponents")
                .define("racexvsnormal", false);
        INFIGHTING_XEN_ANTLION = BUILDER.comment("Do Xen forces and Antlions fight")
                .define("xenvsantlion", false);
        INFIGHTING_XEN_NORMAL = BUILDER.comment("Do Xen forces attack other nonfriendly opponents")
                .define("xenvsnormal", false);
        INFIGHTING_HEADCRAB_XEN = BUILDER.comment("Do Headcrabs and Xen forces fight")
                .define("headcrabvsnormal", false);
        INFIGHTING_XEN_COMBINE = BUILDER.comment("Do The Combine and Xen forces fight")
                .define("combinevsxen", true);
        INFIGHTING_COMBINE_VS_NORMAL = BUILDER.comment("Do The Combine attack other nonfriendly opponents")
                .define("combinevsnormal", true);
        INFIGHTING_ANTLION_NORMAL = BUILDER.comment("Do Antlions attack other nonfriendly opponents")
                .define("antlionvsnormal", false);

        INFIGHTING_HECU_ANTLION = BUILDER.comment("Do HECU Soldiers and Antlions fight")
                .define("antlionvshecu", true);
        INFIGHTING_HECU_COMBINE = BUILDER.comment("Do HECU Soldiers and The Combine fight")
                .define("combinevshecu", true);
        INFIGHTING_HECU_HEADCRAB = BUILDER.comment("Do HECU Soldiers and Headcrabs fight")
                .define("headcrabvshecu", true);
        INFIGHTING_HECU_XEN = BUILDER.comment("Do HECU Soldiers and Xen Forces fight")
                .define("xenvshecu", true);
        INFIGHTING_HECU_RACEX = BUILDER.comment("Do HECU Soldiers and Race X fight")
                .define("racexvshecu", true);
        INFIGHTING_HECU_NORMAL = BUILDER.comment("Do HECU Soldiers attack other opponents")
                .define("normalvshecu", false);

        INFIGHTING_BULLSQUID_HUNTS_ANIMALS = BUILDER.comment("Do Bullsquids hunt animals")
                .define("bullsquidvscreature", true);
        INFIGHTING_BULLSQUID_HUNTS_HEADCRABS = BUILDER.comment("Do Bullsquids hunt Headcrabs")
                .define("bullsquidvsheadcrab", true);
        INFIGHTING_PITDRONE_HUNTS_HEADCRABS = BUILDER.comment("Do Pitdrones hunt Headcrabs")
                .define("pitdronevsheadcrab", true);
        INFIGHTING_PITDRONE_HUNTS_SHOCKROACH = BUILDER.comment("Do Pitdrones hunt Shockroaches")
                .define("pitdronevsshockroach", true);
        INFIGHTING_BULLSQUID = BUILDER.comment("Do Bullsquids fight each other")
                .define("bullsquidvsbullsquid", true);
        INFIGHTING_BULLSQUID_PITDRONE = BUILDER.comment("Do Bullsquids and Pitdrones fight")
                .define("bullsquidvspitdrone", true);

        BUILDER.pop();
        INFIGHTINGSPEC = BUILDER.build();
    }

    static {
        BUILDER3.push("Weopons Config");
        BUILDER3.comment("This config determines things related to weopons.");
      //  GRAVITY_GUN_BLACKLIST = BUILDER3.comment("A blacklist for any blocks that shouldnt be pickable by the gravity gun")
      //          .define("gravitygunblacklist", "#minecraft");
        AUTO_RELOAD = BUILDER3.comment("Does the gun automatically reload if you try to shoot after running out of ammo")
                .define("auto_reload", true);
        BUILDER3.pop();
        WEOPONSPEC = BUILDER3.build();
    }


      static {
        BUILDER2.push("Entity Config");
        BUILDER2.comment("This config determines some specific paramers related to different entities");
          GARG_SETFIRE_CHANCE = BUILDER2.comment("A chance per tick per block that it will be set on fire by gargantua's flames")
                  .define("garg_fire_chance", 0.01f);
          CHUMTOAD_LINE_OF_SIGHT = BUILDER2.comment("Does a mob need to be able to see the chumtoad to get distracted by it")
                  .define("chumtoadsight", true);
        BUILDER2.pop();
        ENTITYSPEC = BUILDER2.build();
    }


}
