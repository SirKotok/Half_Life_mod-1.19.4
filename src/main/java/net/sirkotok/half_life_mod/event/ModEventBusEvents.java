package net.sirkotok.half_life_mod.event;

import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.SpawnPlacementRegisterEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.sirkotok.half_life_mod.HalfLifeMod;
import net.sirkotok.half_life_mod.entity.ModEntities;
import net.sirkotok.half_life_mod.entity.mob.custom.*;

@Mod.EventBusSubscriber(modid = HalfLifeMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEventBusEvents {

    @SubscribeEvent
    public static void entityAttributeEvent(EntityAttributeCreationEvent event) {
        event.put(ModEntities.CHUMTOAD.get(), Chumtoad.setAttributes());
        event.put(ModEntities.BARNEY.get(), Barney.setAttributes());
        event.put(ModEntities.SNARK.get(), Snark.setAttributes());
        event.put(ModEntities.PENGUIN.get(), Snark.setAttributes());
        event.put(ModEntities.HEADCRAB_HL1.get(), Headcrab_1.setAttributes());
        event.put(ModEntities.HEADCRAB_ZOMBIE_1.get(), Headcrab_zombie_standart.setAttributes());
        event.put(ModEntities.HEADCRAB_HL2.get(), Headcrab_2.setAttributes());
        event.put(ModEntities.HEADCRAB_HLA.get(), Headcrab_3.setAttributes());
        event.put(ModEntities.HEADCRAB_ARMORED.get(), Headcrab_Armored.setAttributes());
        event.put(ModEntities.HEADCRAB_FAST.get(), Headcrab_Fast.setAttributes());
        event.put(ModEntities.HEADCRAB_POISON_HL2.get(), Headcrab_Poison_2.setAttributes());
        event.put(ModEntities.HEADCRAB_POISON_HLA.get(), Headcrab_Poison_3.setAttributes());
        event.put(ModEntities.BULLSQUID.get(), Bullsquid.setAttributes());
        event.put(ModEntities.SHOCKROACH.get(), Shockroach.setAttributes());
    }


    @SubscribeEvent
    public static void entitySpawnRestriction(SpawnPlacementRegisterEvent event) {
        event.register(ModEntities.HEADCRAB_HL1.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                Monster::checkMonsterSpawnRules, SpawnPlacementRegisterEvent.Operation.REPLACE);
        event.register(ModEntities.CHUMTOAD.get(), SpawnPlacements.Type.NO_RESTRICTIONS, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                Monster::checkAnyLightMonsterSpawnRules, SpawnPlacementRegisterEvent.Operation.REPLACE);
        event.register(ModEntities.HEADCRAB_POISON_HLA.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                Monster::checkMonsterSpawnRules, SpawnPlacementRegisterEvent.Operation.REPLACE);
        event.register(ModEntities.HEADCRAB_POISON_HL2.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                Monster::checkMonsterSpawnRules, SpawnPlacementRegisterEvent.Operation.REPLACE);
        event.register(ModEntities.HEADCRAB_FAST.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                Monster::checkMonsterSpawnRules, SpawnPlacementRegisterEvent.Operation.REPLACE);
        event.register(ModEntities.HEADCRAB_HL2.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                Monster::checkMonsterSpawnRules, SpawnPlacementRegisterEvent.Operation.REPLACE);
        event.register(ModEntities.HEADCRAB_HLA.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                Monster::checkMonsterSpawnRules, SpawnPlacementRegisterEvent.Operation.REPLACE);
        event.register(ModEntities.HEADCRAB_ARMORED.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                Monster::checkMonsterSpawnRules, SpawnPlacementRegisterEvent.Operation.REPLACE);
        event.register(ModEntities.BULLSQUID.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                Monster::checkMonsterSpawnRules, SpawnPlacementRegisterEvent.Operation.REPLACE);


    }




}
