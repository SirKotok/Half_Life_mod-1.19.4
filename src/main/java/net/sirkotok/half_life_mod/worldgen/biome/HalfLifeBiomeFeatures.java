package net.sirkotok.half_life_mod.worldgen.biome;

import net.minecraft.data.worldgen.Carvers;
import net.minecraft.data.worldgen.placement.MiscOverworldPlacements;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.sirkotok.half_life_mod.entity.HalfLifeEntities;

public class HalfLifeBiomeFeatures {

    public static void addmaxheadcrabs(MobSpawnSettings.Builder pBuilder) {
        pBuilder.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(HalfLifeEntities.HEADCRAB_HL1.get(), 12, 4, 4));
        pBuilder.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(HalfLifeEntities.HEADCRAB_HL2.get(), 12, 4, 4));
    }

    public static void addcarversandlakes(BiomeGenerationSettings.Builder pBuilder) {
        pBuilder.addCarver(GenerationStep.Carving.AIR, HLCarvers.CAVE_XEN);
        pBuilder.addCarver(GenerationStep.Carving.AIR, HLCarvers.CAVE_EXTRA_UNDERGROUND_XEN);
        pBuilder.addCarver(GenerationStep.Carving.AIR, HLCarvers.CANYON_XEN);
        pBuilder.addCarver(GenerationStep.Carving.LIQUID, HLCarvers.NETHER_CAVE_XEN);


    //    pBuilder.addFeature(GenerationStep.Decoration.LAKES, MiscOverworldPlacements.LAKE_LAVA_UNDERGROUND);
     //   pBuilder.addFeature(GenerationStep.Decoration.LAKES, MiscOverworldPlacements.LAKE_LAVA_SURFACE);
    }


}
