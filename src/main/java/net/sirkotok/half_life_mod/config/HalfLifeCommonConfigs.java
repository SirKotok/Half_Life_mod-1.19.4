package net.sirkotok.half_life_mod.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class HalfLifeCommonConfigs  {
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;

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
    public static final ForgeConfigSpec.ConfigValue<Boolean> INFIGHTING_HEADCRAB_COMBINE;
    public static final ForgeConfigSpec.ConfigValue<Boolean> INFIGHTING_COMBINE_VS_NORMAL;
    public static final ForgeConfigSpec.ConfigValue<Boolean> INFIGHTING_BULLSQUID_HUNTS_HEADCRABS;
    public static final ForgeConfigSpec.ConfigValue<Boolean> INFIGHTING_BULLSQUID_HUNTS_ANIMALS;
    public static final ForgeConfigSpec.ConfigValue<Boolean> INFIGHTING_PITDRONE_HUNTS_HEADCRABS;
    public static final ForgeConfigSpec.ConfigValue<Boolean> INFIGHTING_PITDRONE_HUNTS_SHOCKROACH;
    public static final ForgeConfigSpec.ConfigValue<Boolean> INFIGHTING_BULLSQUID;
    public static final ForgeConfigSpec.ConfigValue<Boolean> INFIGHTING_BULLSQUID_PITDRONE;

    static {
        BUILDER.push("Configs for HalfLifeMod");
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
        SPEC = BUILDER.build();
    }
}
