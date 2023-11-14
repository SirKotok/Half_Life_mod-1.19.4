package net.sirkotok.half_life_mod;

import com.mojang.logging.LogUtils;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.MinecraftForge;


import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import net.sirkotok.half_life_mod.block.ModBlocks;

import net.sirkotok.half_life_mod.block.blockentity.ModBlockEntities;
import net.sirkotok.half_life_mod.effect.ModEffects;
import net.sirkotok.half_life_mod.entity.brain.ModMemoryModuleType;
import net.sirkotok.half_life_mod.entity.brain.ModSensorType;
import net.sirkotok.half_life_mod.entity.ModEntities;
import net.sirkotok.half_life_mod.item.ModCreativeModeTabs;
import net.sirkotok.half_life_mod.item.ModItems;
import net.sirkotok.half_life_mod.networking.ModPackets;
import net.sirkotok.half_life_mod.particle.ModParticles;
import net.sirkotok.half_life_mod.sound.ModSounds;
import org.slf4j.Logger;


@Mod(HalfLifeMod.MOD_ID)
public class HalfLifeMod
{
    // Define mod id in a common place for everything to reference
    public static final String MOD_ID = "half_life_mod";
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();
    public HalfLifeMod()
    {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(this::commonSetup);
        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);
        ModBlockEntities.register(modEventBus);
        ModEntities.register(modEventBus);
        ModSounds.register(modEventBus);
        ModMemoryModuleType.register(modEventBus);
        ModSensorType.register(modEventBus);
        ModEffects.register(modEventBus);
        ModParticles.register(modEventBus);

        MinecraftForge.EVENT_BUS.register(this);
        modEventBus.addListener(this::addCreative);

    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {
        event.enqueueWork(() -> {
            ModPackets.register(); //this should be at the top, don't move it down

        });


    }


    private void addCreative(CreativeModeTabEvent.BuildContents event)
    {
        if(event.getTab().equals(ModCreativeModeTabs.HALF_LIFE_SPAWN_EGGS)) {
            //  spawn eggs

            // This is here for more easy structure building in the future
          //  event.accept(Blocks.STRUCTURE_VOID);
         //   event.accept(Blocks.STRUCTURE_BLOCK);
         //   event.accept(Blocks.JIGSAW);


            event.accept(ModItems.HEADCRAB_ONE_SPAWN_EGG);
            event.accept(ModItems.HEADCRAB_TWO_SPAWN_EGG);
            event.accept(ModItems.HEADCRAB_FAST_SPAWN_EGG);
            event.accept(ModItems.HEADCRAB_POISON_HL2_SPAWN_EGG);
            event.accept(ModItems.HEADCRAB_POISON_HLA_SPAWN_EGG);
            event.accept(ModItems.HEADCRAB_ALYX_SPAWN_EGG);
            event.accept(ModItems.HEADCRAB_ARMOR_SPAWN_EGG);

          //  event.accept(ModItems.HEADCRAB_ZOMBIE_SPAWN_EGG);

            event.accept(ModItems.BULLSQUID_SPAWN_EGG);
            event.accept(ModItems.HOUNDEYE_SPAWN_EGG);
            event.accept(ModItems.HOTEYE_SPAWN_EGG);


            event.accept(ModItems.SNARKNEST_SPAWN_EGG);
            event.accept(ModItems.SNARK_SPAWN_EGG);
            event.accept(ModItems.PENGUIN_SPAWN_EGG);
            event.accept(ModItems.CHUMTOAD_SPAWN_EGG);

            event.accept(ModItems.BARNACLE_ONE_SPAWN_EGG);

            event.accept(ModItems.SHOCKROACH_SPAWN_EGG);
            event.accept(ModItems.SHOCKTROOPER_SPAWN_EGG);
            event.accept(ModItems.VORTIGORE_SPAWN_EGG);
            event.accept(ModItems.PITDRONE_SPAWN_EGG);



            event.accept(ModItems.BARNEY_SPAWN_EGG);



        }

        if(event.getTab().equals(ModCreativeModeTabs.HALF_LIFE_BLOCKS_TAB)) {
            event.accept(ModBlocks.VOLTIGORE_NEST);
            event.accept(ModItems.VOLTIGORE_EGG_BLOCK_ITEM);
        }

        if(event.getTab().equals(ModCreativeModeTabs.HALF_LIFE_WEOPONS_TAB)) {
            // items that are guns
            event.accept(ModItems.PISTOL);
            event.accept(ModItems.SHOCKROACH_ITEM);
         //   items that throw entities
            event.accept(ModItems.CHUMTOAD_THROWER);
            event.accept(ModItems.SNARK_THROWER);
            event.accept(ModItems.PENGUIN_THROWER);
            event.accept(ModItems.SECURITY_GUARD_HELMET);
            event.accept(ModItems.SECURITY_GUARD_VEST);
        }
    }



}
//TODO:
/*  1) Better chumtoad, penguin, snark models; 2) Poison headcrab screen effect 3) Bullsquid and snark particles 4) better pistol reload sound and model... and so much more  */
// TODO: Fix the fucking bullet hole particles
//TODO fix the fucking jumping guns
