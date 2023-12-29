package net.sirkotok.half_life_mod.datagen;


import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.DatapackBuiltinEntriesProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.sirkotok.half_life_mod.HalfLifeMod;
import net.sirkotok.half_life_mod.worldgen.biome.HLCarvers;
import net.sirkotok.half_life_mod.worldgen.biome.XenBiomes;
import net.sirkotok.half_life_mod.worldgen.dimension.HalfLifeDimensions;
import net.sirkotok.half_life_mod.worldgen.noise.HalfLifeDensityFunctions;
import net.sirkotok.half_life_mod.worldgen.noise.HalfLifeNoiseSettings;
import net.sirkotok.half_life_mod.worldgen.noise.HalfLifeNoises;

import java.util.Set;
import java.util.concurrent.CompletableFuture;


@Mod.EventBusSubscriber(modid = HalfLifeMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)

public class DataGenerators {

    private static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
            .add(Registries.DAMAGE_TYPE, ModDamageTypesBootstrap::bootstrap)
            .add(Registries.DIMENSION_TYPE, HalfLifeDimensions::bootstrapType)
            .add(Registries.CONFIGURED_CARVER, HLCarvers::bootstrap)
            .add(Registries.BIOME, XenBiomes::boostrap)


            .add(Registries.DENSITY_FUNCTION, HalfLifeDensityFunctions::bootstrap)
            .add(Registries.NOISE, HalfLifeNoises::bootstrap)
            .add(Registries.NOISE_SETTINGS, HalfLifeNoiseSettings::bootstrap)


            // ty Aether team for permission to use thouse!
            // TODO: not forget to give proper credit when the mod releases

            .add(Registries.LEVEL_STEM, HalfLifeDimensions::bootstrapStem);



//     .add(Registries.CONFIGURED_FEATURE, ModConfiguredFeatures::bootstrap)
//     .add(Registries.PLACED_FEATURE, ModPlacedFeatures::bootstrap)
//     .add(ForgeRegistries.Keys.BIOME_MODIFIERS, ModBiomeModifiers::bootstrap)
//     .add(Registries.BIOME, ModBiomes::boostrap)

    @SubscribeEvent
    public static void gatherData (GatherDataEvent event){
        DataGenerator generator = event.getGenerator();
        PackOutput packOutput = generator.getPackOutput();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();




        generator.addProvider(true, new ModRecipeProvider(packOutput));
        generator.addProvider(true, new ModItemModelProvider(packOutput, existingFileHelper));
        generator.addProvider(true, ModLootTableProvider.create(packOutput));
        generator.addProvider(true, new ModBlockStateProvider(packOutput, existingFileHelper));



        generator.addProvider(event.includeServer(), new DatapackBuiltinEntriesProvider(packOutput, lookupProvider, BUILDER, Set.of(HalfLifeMod.MOD_ID)));
    }

}
