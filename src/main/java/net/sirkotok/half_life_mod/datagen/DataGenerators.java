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
import net.sirkotok.half_life_mod.worldgen.dimension.HalfLifeDimensions;
import net.sirkotok.half_life_mod.worldgen.noise.AetherDensityFunctions;
import net.sirkotok.half_life_mod.worldgen.noise.AetherNoiseSettings;
import net.sirkotok.half_life_mod.worldgen.noise.AetherNoises;

import java.util.Set;
import java.util.concurrent.CompletableFuture;


@Mod.EventBusSubscriber(modid = HalfLifeMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)

public class DataGenerators {

    private static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
            .add(Registries.DAMAGE_TYPE, ModDamageTypesBootstrap::bootstrap)
            .add(Registries.DIMENSION_TYPE, HalfLifeDimensions::bootstrapType)


            .add(Registries.DENSITY_FUNCTION, AetherDensityFunctions::bootstrap)
            .add(Registries.NOISE, AetherNoises::bootstrap)
            .add(Registries.NOISE_SETTINGS, AetherNoiseSettings::bootstrap)
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
