package net.sirkotok.half_life_mod.worldgen.biome;

import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BiomeDefaultFeatures;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.placement.MiscOverworldPlacements;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.Musics;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.*;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.sirkotok.half_life_mod.HalfLifeMod;

public class XenBiomes {
    public static final ResourceKey<Biome> XEN_BASIC = ResourceKey.create(Registries.BIOME,
            new ResourceLocation(HalfLifeMod.MOD_ID, "xen_basic_biome"));

    public static void boostrap(BootstapContext<Biome> context) {
        context.register(XEN_BASIC, xen_basic(context));
    }

    public static void globalGeneration(BiomeGenerationSettings.Builder builder) {
          HalfLifeBiomeFeatures.addcarversandlakes(builder);
     //   BiomeDefaultFeatures.addDefaultCrystalFormations(builder);
     //   BiomeDefaultFeatures.addDefaultMonsterRoom(builder);
    //    BiomeDefaultFeatures.addDefaultUndergroundVariety(builder);
          builder.addFeature(GenerationStep.Decoration.FLUID_SPRINGS, MiscOverworldPlacements.SPRING_WATER);
    //    BiomeDefaultFeatures.addSurfaceFreezing(builder);
    }

    public static Biome xen_basic(BootstapContext<Biome> context) {
        MobSpawnSettings.Builder spawnBuilder = new MobSpawnSettings.Builder();

        HalfLifeBiomeFeatures.addmaxheadcrabs(spawnBuilder);
        HalfLifeBiomeFeatures.addXenForcesNormal(spawnBuilder);
        HalfLifeBiomeFeatures.addXenWildlifeNormal(spawnBuilder);


        BiomeGenerationSettings.Builder biomeBuilder =
                new BiomeGenerationSettings.Builder(context.lookup(Registries.PLACED_FEATURE), context.lookup(Registries.CONFIGURED_CARVER));
        //we need to follow the same order as vanilla biomes for the BiomeDefaultFeatures
        globalGeneration(biomeBuilder);
  //      BiomeDefaultFeatures.addMossyStoneBlock(biomeBuilder);
     //   BiomeDefaultFeatures.addForestFlowers(biomeBuilder);
    //    BiomeDefaultFeatures.addFerns(biomeBuilder);
    //    BiomeDefaultFeatures.addDefaultOres(biomeBuilder);
   //     BiomeDefaultFeatures.addExtraGold(biomeBuilder);

    //    biomeBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.TREES_PLAINS);

    //    BiomeDefaultFeatures.addDefaultMushrooms(biomeBuilder);
   //     BiomeDefaultFeatures.addDefaultExtraVegetation(biomeBuilder);
    //    biomeBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ModPlacedFeatures.PINE_PLACED_KEY);

        return new Biome.BiomeBuilder()
                .hasPrecipitation(false)
                .downfall(0.8f)
                .temperature(0.7f)
                .generationSettings(biomeBuilder.build())
                .mobSpawnSettings(spawnBuilder.build())
                .specialEffects((new BiomeSpecialEffects.Builder())
                        .waterColor(0xe82e3b)
                        .waterFogColor(0xbf1b26)
                        .skyColor(0x30c918)
                        .grassColorOverride(0x7f03fc)
                        .foliageColorOverride(0xd203fc)
                        .fogColor(0x22a1e6)
                        .ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS)
                        .backgroundMusic(Musics.END).build())
                .build();
    }
}