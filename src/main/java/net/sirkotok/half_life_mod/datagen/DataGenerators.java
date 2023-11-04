package net.sirkotok.half_life_mod.datagen;


import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.sirkotok.half_life_mod.HalfLifeMod;


@Mod.EventBusSubscriber(modid = HalfLifeMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)

public class DataGenerators {
    @SubscribeEvent
    public static void gatherData (GatherDataEvent event){
        DataGenerator generator = event.getGenerator();
        PackOutput packOutput = generator.getPackOutput();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();


        generator.addProvider(true, ModLootTableProvider.create(packOutput));
        generator.addProvider(true, new ModBlockStateProvider(packOutput, existingFileHelper));
        generator.addProvider(true, new ModItemModelProvider(packOutput, existingFileHelper));

    }

}
