package net.sirkotok.half_life_mod.worldgen.dimension;

import com.mojang.datafixers.util.Pair;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.*;
import net.minecraft.world.level.dimension.BuiltinDimensionTypes;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;
import net.sirkotok.half_life_mod.HalfLifeMod;

import java.util.List;
import java.util.OptionalLong;

public class ModDimensions {
    public static final ResourceKey<LevelStem> HALFLIFE_KEY = ResourceKey.create(Registries.LEVEL_STEM,
            new ResourceLocation(HalfLifeMod.MOD_ID, "half_life_dim"));
    public static final ResourceKey<Level> HALFLIFE_LEVEL_KEY = ResourceKey.create(Registries.DIMENSION,
            new ResourceLocation(HalfLifeMod.MOD_ID, "half_life_dim"));
    public static final ResourceKey<DimensionType> HALFLIFE_DIM_TYPE = ResourceKey.create(Registries.DIMENSION_TYPE,
            new ResourceLocation(HalfLifeMod.MOD_ID, "half_life_dim_type"));




    public static void bootstrapType(BootstapContext<DimensionType> context) {
        context.register(HALFLIFE_DIM_TYPE, new DimensionType(
                OptionalLong.of(12000), // fixedTime
                false, // hasSkylight
                false, // hasCeiling
                false, // ultraWarm
                false, // natural
                1.0, // coordinateScale
                true, // bedWorks
                false, // respawnAnchorWorks
                0, // minY
                256, // height
                256, // logicalHeight
                BlockTags.INFINIBURN_OVERWORLD, // infiniburn
                BuiltinDimensionTypes.OVERWORLD_EFFECTS, // effectsLocation
                1.0f, // ambientLight
                new DimensionType.MonsterSettings(false, false, ConstantInt.of(0), 0)));
    }

    public static void bootstrapStem(BootstapContext<LevelStem> context) {
        HolderGetter<Biome> biomeRegistry = context.lookup(Registries.BIOME);
        HolderGetter<DimensionType> dimTypes = context.lookup(Registries.DIMENSION_TYPE);
        HolderGetter<NoiseGeneratorSettings> noiseGenSettings = context.lookup(Registries.NOISE_SETTINGS);

      /*  NoiseBasedChunkGenerator wrappedChunkGenerator = new NoiseBasedChunkGenerator(
                new FixedBiomeSource(biomeRegistry.getOrThrow(ModBiomes.TEST_BIOME)),
                noiseGenSettings.getOrThrow(NoiseGeneratorSettings.AMPLIFIED)); */ // One biome generation

        NoiseBasedChunkGenerator noiseBasedChunkGenerator = new NoiseBasedChunkGenerator(
                MultiNoiseBiomeSource.createFromList(new Climate.ParameterList<>(List.of(
                        Pair.of(
                                Climate.parameters(
                                        0.8F,
                                        0.0F,
                                        0.0F,
                                        0.5F,
                                        0.0F,
                                        0.1F,
                                        0.0F),
                                biomeRegistry.getOrThrow(Biomes.NETHER_WASTES)),
                        Pair.of(
                                Climate.parameters(
                                        0.0F,
                                        0.0F,
                                        0.0F,
                                        0.0F,
                                        0.0F,
                                        0.0F,
                                        0.0F),
                                biomeRegistry.getOrThrow(Biomes.THE_VOID)),
                        Pair.of(
                                Climate.parameters(
                                        0.2F,
                                        0.7F,
                                        0.4F,
                                        0.2F,
                                        0.0F,
                                        0.1F,
                                        0.0F),
                                biomeRegistry.getOrThrow(Biomes.BIRCH_FOREST)),
                        Pair.of(
                                Climate.parameters(
                                        0.1F,
                                        0.4F,
                                        0.0F,
                                        0.0F,
                                        0.0F,
                                        0.49F,
                                        0.0F),
                                biomeRegistry.getOrThrow(Biomes.DRIPSTONE_CAVES)),
                        Pair.of(
                                Climate.parameters(
                                        0.1F,
                                        0.4F,
                                        0.0F,
                                        0.0F,
                                        0.0F,
                                        0.51F,
                                        0.0F),
                                biomeRegistry.getOrThrow(Biomes.BAMBOO_JUNGLE))


                                    ))),
                noiseGenSettings.getOrThrow(NoiseGeneratorSettings.OVERWORLD));



        LevelStem stem = new LevelStem(dimTypes.getOrThrow(ModDimensions.HALFLIFE_DIM_TYPE), noiseBasedChunkGenerator);

        context.register(HALFLIFE_KEY, stem);
    }





}
