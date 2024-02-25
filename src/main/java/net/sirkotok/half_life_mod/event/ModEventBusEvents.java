package net.sirkotok.half_life_mod.event;

import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.SpawnPlacementRegisterEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.sirkotok.half_life_mod.HalfLifeMod;
import net.sirkotok.half_life_mod.entity.HalfLifeEntities;
import net.sirkotok.half_life_mod.entity.mob_effect_entity.custom.ShockWaveEffect;
import net.sirkotok.half_life_mod.entity.mob_effect_entity.custom.SitThenBlowUpEffect;
import net.sirkotok.half_life_mod.entity.mob_effect_entity.custom.VoltigoreProjectileAftereffect;
import net.sirkotok.half_life_mod.entity.mob_geckolib.custom.*;
import net.sirkotok.half_life_mod.entity.mob_normal.custom.Barnacle;

@Mod.EventBusSubscriber(modid = HalfLifeMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEventBusEvents {

    @SubscribeEvent
    public static void entityAttributeEvent(EntityAttributeCreationEvent event) {
        event.put(HalfLifeEntities.CHUMTOAD.get(), Chumtoad.setAttributes());
        event.put(HalfLifeEntities.BARNEY.get(), Barney.setAttributes());
        event.put(HalfLifeEntities.SCIENTIST.get(), Scientist.setAttributes());
        event.put(HalfLifeEntities.ZOMBIE_SCIENTIST_HL1.get(), HL1ZombieScientist.setAttributes());
        event.put(HalfLifeEntities.ZOMBIE_HL2.get(), HL2Zombie.setAttributes());
        event.put(HalfLifeEntities.ZOMBIE_FAST.get(), HL2Zombie_fast.setAttributes());
        event.put(HalfLifeEntities.PZOMBIE.get(), Pzombie.setAttributes());
        event.put(HalfLifeEntities.VZOMBIE.get(), HLZombieVillager.setAttributes());
        event.put(HalfLifeEntities.COCKROACH.get(), Cockroach.setAttributes());
        event.put(HalfLifeEntities.BABY_HEADCRAB.get(), Baby_Headcrab.setAttributes());
        event.put(HalfLifeEntities.MANHACK.get(), Manhack.setAttributes());
        event.put(HalfLifeEntities.HUNTER.get(), Hunter.setAttributes());
        event.put(HalfLifeEntities.LEECH.get(), Leech.setAttributes());
        event.put(HalfLifeEntities.SHARK.get(), Shark.setAttributes());
        event.put(HalfLifeEntities.BARNACLE.get(), Barnacle.setAttributes());
        event.put(HalfLifeEntities.SNARK.get(), Snark.setAttributes());
        event.put(HalfLifeEntities.SHOCKWAVEEFFECT.get(), ShockWaveEffect.setAttributes());
        event.put(HalfLifeEntities.SITBLOWUP.get(), SitThenBlowUpEffect.setAttributes());
        event.put(HalfLifeEntities.VOLTIGOREPROJECTEFFECT.get(), VoltigoreProjectileAftereffect.setAttributes());
        event.put(HalfLifeEntities.SNARKNEST.get(), Snarknest.setAttributes());
        event.put(HalfLifeEntities.PENGUIN.get(), Snark.setAttributes());
        event.put(HalfLifeEntities.VOLTIGORE.get(), Voltigore.setAttributes());
        event.put(HalfLifeEntities.SHOCKTROOPER.get(), Shocktrooper.setAttributes());
        event.put(HalfLifeEntities.HEADCRAB_HL1.get(), Headcrab_1.setAttributes());

        event.put(HalfLifeEntities.HEADCRAB_HL2.get(), Headcrab_2.setAttributes());
        event.put(HalfLifeEntities.HEADCRAB_HLA.get(), Headcrab_3.setAttributes());
        event.put(HalfLifeEntities.HEADCRAB_ARMORED.get(), Headcrab_Armored.setAttributes());
        event.put(HalfLifeEntities.HEADCRAB_FAST.get(), Headcrab_Fast.setAttributes());
        event.put(HalfLifeEntities.HEADCRAB_POISON_HL2.get(), Headcrab_Poison_2.setAttributes());
        event.put(HalfLifeEntities.HEADCRAB_POISON_HLA.get(), Headcrab_Poison_3.setAttributes());
        event.put(HalfLifeEntities.BULLSQUID.get(), Bullsquid.setAttributes());
        event.put(HalfLifeEntities.PITDRONE.get(), Pitdrone.setAttributes());
        event.put(HalfLifeEntities.ANTLION.get(), Antlion.setAttributes());
        event.put(HalfLifeEntities.ANTLIONWORKER.get(), AntlionWorker.setAttributes());
        event.put(HalfLifeEntities.GONARCHBM.get(), GonarchBM.setAttributes());
        event.put(HalfLifeEntities.GONARCH.get(), Gonarch.setAttributes());
        event.put(HalfLifeEntities.HOUNDEYE.get(), Houndeye.setAttributes());
        event.put(HalfLifeEntities.HOTEYE.get(), Hoteye.setAttributes());
        event.put(HalfLifeEntities.CONTROLLER.get(), Controller.setAttributes());
        event.put(HalfLifeEntities.SHOCKROACH.get(), Shockroach.setAttributes());
    }


    @SubscribeEvent
    public static void entitySpawnRestriction(SpawnPlacementRegisterEvent event) {
        event.register(HalfLifeEntities.VOLTIGORE.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                Monster::checkMonsterSpawnRules, SpawnPlacementRegisterEvent.Operation.REPLACE);
      //  event.register(ModEntities.SHOCKTROOPER.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
     //           Monster::checkMonsterSpawnRules, SpawnPlacementRegisterEvent.Operation.REPLACE);
        event.register(HalfLifeEntities.PITDRONE.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                Monster::checkMonsterSpawnRules, SpawnPlacementRegisterEvent.Operation.REPLACE);
        event.register(HalfLifeEntities.ANTLION.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                Monster::checkMonsterSpawnRules, SpawnPlacementRegisterEvent.Operation.REPLACE);
        event.register(HalfLifeEntities.ANTLIONWORKER.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                Monster::checkMonsterSpawnRules, SpawnPlacementRegisterEvent.Operation.REPLACE);
        event.register(HalfLifeEntities.ZOMBIE_HL2.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                Monster::checkMonsterSpawnRules, SpawnPlacementRegisterEvent.Operation.REPLACE);
        event.register(HalfLifeEntities.ZOMBIE_SCIENTIST_HL1.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                Monster::checkMonsterSpawnRules, SpawnPlacementRegisterEvent.Operation.REPLACE);
        event.register(HalfLifeEntities.ZOMBIE_FAST.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                Monster::checkMonsterSpawnRules, SpawnPlacementRegisterEvent.Operation.REPLACE);
        event.register(HalfLifeEntities.PZOMBIE.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                Monster::checkMonsterSpawnRules, SpawnPlacementRegisterEvent.Operation.REPLACE);
        event.register(HalfLifeEntities.HEADCRAB_HL1.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                Monster::checkMonsterSpawnRules, SpawnPlacementRegisterEvent.Operation.REPLACE);
        event.register(HalfLifeEntities.HOUNDEYE.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                Monster::checkMonsterSpawnRules, SpawnPlacementRegisterEvent.Operation.REPLACE);
        event.register(HalfLifeEntities.CHUMTOAD.get(), SpawnPlacements.Type.NO_RESTRICTIONS, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                Monster::checkAnyLightMonsterSpawnRules, SpawnPlacementRegisterEvent.Operation.REPLACE);
        event.register(HalfLifeEntities.HEADCRAB_POISON_HLA.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                Monster::checkMonsterSpawnRules, SpawnPlacementRegisterEvent.Operation.REPLACE);
        event.register(HalfLifeEntities.HEADCRAB_POISON_HL2.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                Monster::checkMonsterSpawnRules, SpawnPlacementRegisterEvent.Operation.REPLACE);
        event.register(HalfLifeEntities.HEADCRAB_FAST.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                Monster::checkMonsterSpawnRules, SpawnPlacementRegisterEvent.Operation.REPLACE);
        event.register(HalfLifeEntities.HEADCRAB_HL2.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                Monster::checkMonsterSpawnRules, SpawnPlacementRegisterEvent.Operation.REPLACE);
        event.register(HalfLifeEntities.HEADCRAB_HLA.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                Monster::checkMonsterSpawnRules, SpawnPlacementRegisterEvent.Operation.REPLACE);
        event.register(HalfLifeEntities.HEADCRAB_ARMORED.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                Monster::checkMonsterSpawnRules, SpawnPlacementRegisterEvent.Operation.REPLACE);
        event.register(HalfLifeEntities.BULLSQUID.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                Monster::checkMonsterSpawnRules, SpawnPlacementRegisterEvent.Operation.REPLACE);
        event.register(HalfLifeEntities.SNARKNEST.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                Snarknest::checkNestSpawnRules, SpawnPlacementRegisterEvent.Operation.REPLACE);
        event.register(HalfLifeEntities.BARNACLE.get(), SpawnPlacements.Type.NO_RESTRICTIONS, Heightmap.Types.MOTION_BLOCKING,
                Barnacle::checkBarnacleSpawnRules, SpawnPlacementRegisterEvent.Operation.REPLACE);
        event.register(HalfLifeEntities.SHARK.get(), SpawnPlacements.Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                Shark::checkSharkSpawnRules, SpawnPlacementRegisterEvent.Operation.REPLACE);

    }







}
