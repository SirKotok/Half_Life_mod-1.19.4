package net.sirkotok.half_life_mod;

import com.mojang.logging.LogUtils;
import net.minecraftforge.common.MinecraftForge;


import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import net.sirkotok.half_life_mod.block.HalfLifeBlocks;

import net.sirkotok.half_life_mod.block.blockentity.HalfLifeBlockEntities;
import net.sirkotok.half_life_mod.effect.ModEffects;
import net.sirkotok.half_life_mod.entity.brain.ModMemoryModuleType;
import net.sirkotok.half_life_mod.entity.brain.ModSensorType;
import net.sirkotok.half_life_mod.entity.HalfLifeEntities;
import net.sirkotok.half_life_mod.item.HalfLifeCreativeModeTabs;
import net.sirkotok.half_life_mod.item.HalfLifeItems;
import net.sirkotok.half_life_mod.networking.HalfLifePackets;
import net.sirkotok.half_life_mod.particle.HalfLifeParticles;
import net.sirkotok.half_life_mod.sound.HalfLifeSounds;
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
        HalfLifeItems.register(modEventBus);
        HalfLifeBlocks.register(modEventBus);
        HalfLifeBlockEntities.register(modEventBus);
        HalfLifeEntities.register(modEventBus);
        HalfLifeSounds.register(modEventBus);
        ModMemoryModuleType.register(modEventBus);
        ModSensorType.register(modEventBus);
        ModEffects.register(modEventBus);
        HalfLifeParticles.register(modEventBus);

        MinecraftForge.EVENT_BUS.register(this);
        modEventBus.addListener(this::addCreative);

    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {
        event.enqueueWork(() -> {
            HalfLifePackets.register(); //this should be at the top, don't move it down

        });


    }


    private void addCreative(CreativeModeTabEvent.BuildContents event)
    {
        if(event.getTab().equals(HalfLifeCreativeModeTabs.HALF_LIFE_SPAWN_EGGS)) {
            //  spawn eggs

            // This is here for more easy structure building in the future
          //  event.accept(Blocks.STRUCTURE_VOID);
         //   event.accept(Blocks.STRUCTURE_BLOCK);
         //   event.accept(Blocks.JIGSAW);


            event.accept(HalfLifeItems.HEADCRAB_ONE_SPAWN_EGG);
            event.accept(HalfLifeItems.BABY_HEADCRAB_SPAWN_EGG);
            event.accept(HalfLifeItems.HEADCRAB_TWO_SPAWN_EGG);
            event.accept(HalfLifeItems.HEADCRAB_FAST_SPAWN_EGG);
            event.accept(HalfLifeItems.HEADCRAB_POISON_HL2_SPAWN_EGG);
            event.accept(HalfLifeItems.HEADCRAB_POISON_HLA_SPAWN_EGG);
            event.accept(HalfLifeItems.HEADCRAB_ALYX_SPAWN_EGG);
            event.accept(HalfLifeItems.HEADCRAB_ARMOR_SPAWN_EGG);

            event.accept(HalfLifeItems.HL1_ZOMBIE_SCIENTIST_SPAWN_EGG);
            event.accept(HalfLifeItems.HL2_ZOMBIE_SPAWN_EGG);


            event.accept(HalfLifeItems.GONARCH_SPAWN_EGG);
            event.accept(HalfLifeItems.GONARCHBM_SPAWN_EGG);


          //  event.accept(ModItems.HEADCRAB_ZOMBIE_SPAWN_EGG);

            event.accept(HalfLifeItems.BULLSQUID_SPAWN_EGG);
            event.accept(HalfLifeItems.HOUNDEYE_SPAWN_EGG);
            event.accept(HalfLifeItems.HOTEYE_SPAWN_EGG);

            event.accept(HalfLifeItems.CONTROLLER_SPAWN_EGG);


            event.accept(HalfLifeItems.SNARKNEST_SPAWN_EGG);
            event.accept(HalfLifeItems.SNARK_SPAWN_EGG);
            event.accept(HalfLifeItems.PENGUIN_SPAWN_EGG);
            event.accept(HalfLifeItems.CHUMTOAD_SPAWN_EGG);

            event.accept(HalfLifeItems.BARNACLE_ONE_SPAWN_EGG);

            event.accept(HalfLifeItems.SHOCKROACH_SPAWN_EGG);
            event.accept(HalfLifeItems.SHOCKTROOPER_SPAWN_EGG);
            event.accept(HalfLifeItems.VORTIGORE_SPAWN_EGG);
            event.accept(HalfLifeItems.PITDRONE_SPAWN_EGG);



            event.accept(HalfLifeItems.BARNEY_SPAWN_EGG);
            event.accept(HalfLifeItems.SCIENTIST_SPAWN_EGG);

            event.accept(HalfLifeItems.COCKROACH_SPAWN_EGG);
            event.accept(HalfLifeItems.LEECH_SPAWN_EGG);
            event.accept(HalfLifeItems.SHARK_SPAWN_EGG);
            event.accept(HalfLifeItems.MANHACK_SPAWN_EGG);
            event.accept(HalfLifeItems.HUNTER_SPAWN_EGG);


        }

        if(event.getTab().equals(HalfLifeCreativeModeTabs.HALF_LIFE_BLOCKS_TAB)) {

            event.accept(HalfLifeBlocks.XEN_GROUND);
            event.accept(HalfLifeBlocks.XEN_ROCK);

            event.accept(HalfLifeBlocks.VOLTIGORE_NEST);
            event.accept(HalfLifeItems.VOLTIGORE_EGG_BLOCK_ITEM);
            event.accept(HalfLifeBlocks.HALF_LIFE_PORTAL);
        }

        if(event.getTab().equals(HalfLifeCreativeModeTabs.HALF_LIFE_WEOPONS_TAB)) {
            // items that are guns
            event.accept(HalfLifeItems.PISTOL);
            event.accept(HalfLifeItems.SMG_HL1);
            event.accept(HalfLifeItems.SHOCKROACH_ITEM);
            event.accept(HalfLifeItems.DISPLACER);
            event.accept(HalfLifeItems.DISPLACER_NETHER);
            event.accept(HalfLifeItems.DISPLACER_END);
         //   items that throw entities
            event.accept(HalfLifeItems.CHUMTOAD_THROWER);
            event.accept(HalfLifeItems.SNARK_THROWER);
            event.accept(HalfLifeItems.PENGUIN_THROWER);
            event.accept(HalfLifeItems.SECURITY_GUARD_HELMET);
            event.accept(HalfLifeItems.SECURITY_GUARD_VEST);
        }
    }



}
//TODO:
/*  1) Better chumtoad, penguin, snark models; 2) Poison headcrab screen effect 3) Bullsquid and snark particles 4) better pistol reload sound and model... and so much more  */
// TODO: Fix the fucking bullet hole particles
//TODO fix the fucking jumping guns
