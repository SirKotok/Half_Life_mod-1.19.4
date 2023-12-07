package net.sirkotok.half_life_mod.event;

import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.SpawnPlacementRegisterEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.sirkotok.half_life_mod.HalfLifeMod;
import net.sirkotok.half_life_mod.entity.ModEntities;
import net.sirkotok.half_life_mod.entity.mob_effect_entity.custom.ShockWaveEffect;
import net.sirkotok.half_life_mod.entity.mob_effect_entity.custom.SitThenBlowUpEffect;
import net.sirkotok.half_life_mod.entity.mob_effect_entity.custom.VoltigoreProjectileAftereffect;
import net.sirkotok.half_life_mod.entity.mob_geckolib.custom.*;
import net.sirkotok.half_life_mod.entity.mob_normal.custom.Barnacle;

@Mod.EventBusSubscriber(modid = HalfLifeMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEventBusEvents {

    @SubscribeEvent
    public static void entityAttributeEvent(EntityAttributeCreationEvent event) {
        event.put(ModEntities.CHUMTOAD.get(), Chumtoad.setAttributes());
        event.put(ModEntities.BARNEY.get(), Barney.setAttributes());
        event.put(ModEntities.COCKROACH.get(), Cockroach.setAttributes());
        event.put(ModEntities.BABY_HEADCRAB.get(), Baby_Headcrab.setAttributes());
        event.put(ModEntities.MANHACK.get(), Manhack.setAttributes());
        event.put(ModEntities.HUNTER.get(), Hunter.setAttributes());
        event.put(ModEntities.LEECH.get(), Leech.setAttributes());
        event.put(ModEntities.SHARK.get(), Shark.setAttributes());
        event.put(ModEntities.BARNACLE.get(), Barnacle.setAttributes());
        event.put(ModEntities.SNARK.get(), Snark.setAttributes());
        event.put(ModEntities.SHOCKWAVEEFFECT.get(), ShockWaveEffect.setAttributes());
        event.put(ModEntities.SITBLOWUP.get(), SitThenBlowUpEffect.setAttributes());
        event.put(ModEntities.VOLTIGOREPROJECTEFFECT.get(), VoltigoreProjectileAftereffect.setAttributes());
        event.put(ModEntities.SNARKNEST.get(), Snarknest.setAttributes());
        event.put(ModEntities.PENGUIN.get(), Snark.setAttributes());
        event.put(ModEntities.VOLTIGORE.get(), Voltigore.setAttributes());
        event.put(ModEntities.SHOCKTROOPER.get(), Shocktrooper.setAttributes());
        event.put(ModEntities.HEADCRAB_HL1.get(), Headcrab_1.setAttributes());
        event.put(ModEntities.HEADCRAB_ZOMBIE_1.get(), Headcrab_zombie_standart.setAttributes());
        event.put(ModEntities.HEADCRAB_HL2.get(), Headcrab_2.setAttributes());
        event.put(ModEntities.HEADCRAB_HLA.get(), Headcrab_3.setAttributes());
        event.put(ModEntities.HEADCRAB_ARMORED.get(), Headcrab_Armored.setAttributes());
        event.put(ModEntities.HEADCRAB_FAST.get(), Headcrab_Fast.setAttributes());
        event.put(ModEntities.HEADCRAB_POISON_HL2.get(), Headcrab_Poison_2.setAttributes());
        event.put(ModEntities.HEADCRAB_POISON_HLA.get(), Headcrab_Poison_3.setAttributes());
        event.put(ModEntities.BULLSQUID.get(), Bullsquid.setAttributes());
        event.put(ModEntities.PITDRONE.get(), Pitdrone.setAttributes());
        event.put(ModEntities.GONARCHBM.get(), GonarchBM.setAttributes());
        event.put(ModEntities.GONARCH.get(), Gonarch.setAttributes());
        event.put(ModEntities.HOUNDEYE.get(), Houndeye.setAttributes());
        event.put(ModEntities.HOTEYE.get(), Hoteye.setAttributes());
        event.put(ModEntities.CONTROLLER.get(), Controller.setAttributes());
        event.put(ModEntities.SHOCKROACH.get(), Shockroach.setAttributes());
    }


    @SubscribeEvent
    public static void entitySpawnRestriction(SpawnPlacementRegisterEvent event) {
        event.register(ModEntities.VOLTIGORE.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                Monster::checkMonsterSpawnRules, SpawnPlacementRegisterEvent.Operation.REPLACE);
      //  event.register(ModEntities.SHOCKTROOPER.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
     //           Monster::checkMonsterSpawnRules, SpawnPlacementRegisterEvent.Operation.REPLACE);
        event.register(ModEntities.PITDRONE.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                Monster::checkMonsterSpawnRules, SpawnPlacementRegisterEvent.Operation.REPLACE);
        event.register(ModEntities.HEADCRAB_HL1.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                Monster::checkMonsterSpawnRules, SpawnPlacementRegisterEvent.Operation.REPLACE);
        event.register(ModEntities.HOUNDEYE.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
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
        event.register(ModEntities.SNARKNEST.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                Snarknest::checkNestSpawnRules, SpawnPlacementRegisterEvent.Operation.REPLACE);
        event.register(ModEntities.BARNACLE.get(), SpawnPlacements.Type.NO_RESTRICTIONS, Heightmap.Types.MOTION_BLOCKING,
                Barnacle::checkBarnacleSpawnRules, SpawnPlacementRegisterEvent.Operation.REPLACE);
        event.register(ModEntities.SHARK.get(), SpawnPlacements.Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                Shark::checkSharkSpawnRules, SpawnPlacementRegisterEvent.Operation.REPLACE);

    }







}
