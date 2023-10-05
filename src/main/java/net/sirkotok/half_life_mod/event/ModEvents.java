package net.sirkotok.half_life_mod.event;


import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.animal.Cat;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.SpawnPlacementRegisterEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.sirkotok.half_life_mod.HalfLifeMod;
import net.sirkotok.half_life_mod.entity.ModEntities;
import net.sirkotok.half_life_mod.entity.mob.custom.*;
import net.sirkotok.half_life_mod.item.custom.gun.GunItem;
import net.sirkotok.half_life_mod.networking.ModPackets;
import net.sirkotok.half_life_mod.networking.packet.GunLeftC2SPacket;
import net.sirkotok.half_life_mod.networking.packet.GunReloadC2SPacket;
import net.sirkotok.half_life_mod.networking.packet.GunRightC2SPacket;
import net.sirkotok.half_life_mod.util.KeyBinding;
import net.tslat.smartbrainlib.api.SmartBrainOwner;
import net.tslat.smartbrainlib.object.SquareRadius;
import net.tslat.smartbrainlib.util.BrainUtils;
import net.tslat.smartbrainlib.util.EntityRetrievalUtil;

import javax.swing.text.JTextComponent;
import java.util.List;
import java.util.ListIterator;


public class ModEvents {


    @Mod.EventBusSubscriber(modid = HalfLifeMod.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientModBusEvents {
        @SubscribeEvent
        public static void onKeyRegister(RegisterKeyMappingsEvent event) {
            event.register(KeyBinding.RELOAD_KEY);
        }

    }

    @Mod.EventBusSubscriber(modid = HalfLifeMod.MOD_ID, value = Dist.CLIENT)
    public static class ClientForgeEvents {
        @SubscribeEvent
        public static void inputEvent(InputEvent.InteractionKeyMappingTriggered event) {
            if (event.isAttack() || event.isUseItem()) {
                LocalPlayer localPlayer = Minecraft.getInstance().player;
                if (localPlayer != null && localPlayer.getMainHandItem().getItem() instanceof GunItem) {
                    event.setSwingHand(false);
                    event.setCanceled(true);
                }
            }
         }


        @SubscribeEvent
        public static void onKeyinput(InputEvent.Key event ) {
            if (KeyBinding.RELOAD_KEY.consumeClick()) {
                LocalPlayer localPlayer = Minecraft.getInstance().player;
                if (localPlayer != null) {
                    boolean holdingGun = localPlayer.getMainHandItem().getItem() instanceof GunItem;
                    if (holdingGun) {
                        ModPackets.sendToServer(new GunReloadC2SPacket());
                    }
                }
            }
        }


        @SubscribeEvent
        public static void clientTick(TickEvent.ClientTickEvent event) {
            if (event.phase == TickEvent.Phase.END) {
                LocalPlayer localPlayer = Minecraft.getInstance().player;
                if (localPlayer != null) {
                    boolean leftMouse = Minecraft.getInstance().mouseHandler.isLeftPressed();
                    boolean rightMouse = Minecraft.getInstance().mouseHandler.isRightPressed();
                    boolean holdingGun = localPlayer.getMainHandItem().getItem() instanceof GunItem;
                    if (holdingGun && leftMouse) {
                        ModPackets.sendToServer(new GunLeftC2SPacket());
                    }
                    if (holdingGun && rightMouse) {
                        ModPackets.sendToServer(new GunRightC2SPacket());
                    }
                }
                }
            }

    }

    @Mod.EventBusSubscriber(modid = HalfLifeMod.MOD_ID)
    public static class ForgeEvents {


        @SubscribeEvent
        public static void  onLivingChangeTick(LivingEvent.LivingTickEvent event) {
            LivingEntity entity = event.getEntity();
            if (entity.level.isClientSide()) return;
            if (event.isCanceled()) return;
            if (entity instanceof Chumtoad) {
                if (entity.isDeadOrDying()) {
                    SquareRadius radius1 = new SquareRadius(30, 7);
                    List<Mob> targets = EntityRetrievalUtil.getEntities(entity.level, entity.getBoundingBox().inflate(radius1.xzRadius(), radius1.yRadius(), radius1.xzRadius()), obj -> ((obj instanceof Mob target) && !(obj instanceof Chumtoad)));
                    if (!targets.isEmpty()) {
                        for (Mob entity1 : targets) {
                            if (entity1.getTarget() == entity) {
                                entity1.setTarget(null);
                            }
                        }
                    }
                }

                if (entity.tickCount % 60 != 0) return;


                if (!entity.isDeadOrDying()) {
                    SquareRadius radius = new SquareRadius(20, 7);
                    List<Mob> targets = EntityRetrievalUtil.getEntities(entity.level, entity.getBoundingBox().inflate(radius.xzRadius(), radius.yRadius(), radius.xzRadius()), obj -> ((obj instanceof Mob target) && !(obj instanceof Chumtoad)));
                    if (!targets.isEmpty()) {
                        for (Mob entity1 : targets) {
                            if (!(entity1.getTarget() instanceof Chumtoad)) {
                                entity1.setTarget(entity);
                                if (entity1 instanceof SmartBrainOwner) {
                                    if (!(BrainUtils.getMemory(entity1, MemoryModuleType.ATTACK_TARGET) instanceof Chumtoad)) {
                                        BrainUtils.setMemory(entity1, MemoryModuleType.ATTACK_TARGET, entity);
                                    }
                                }
                            }
                        }
                    }
                }
            }

        }


        @SubscribeEvent
        public static void onLivingDeath(LivingDeathEvent event) {
            LivingEntity entity = event.getEntity();
            if (entity.level.isClientSide()) return;
            if (event.isCanceled()) return;

            if (entity instanceof Chumtoad) {
                SquareRadius radius = new SquareRadius(20, 7);
                List<LivingEntity> attackers = EntityRetrievalUtil.getEntities(entity.level, entity.getBoundingBox().inflate(radius.xzRadius(), radius.yRadius(), radius.xzRadius()), obj -> obj instanceof Mob);// /* && !(obj instanceof Cat) */);
                ListIterator<LivingEntity>
                iterator = attackers.listIterator();
                while (iterator.hasNext()) {
                    LivingEntity entity1 = iterator.next();
                    if (entity1 instanceof Mob) {
                       if (entity.equals(((Mob) entity1).getTarget()))
                        ((Mob) entity1).setTarget(null);
                    }
                    }


            }



           /* if (event.getSource().getEntity() instanceof LivingEntity) {
                if (event.getSource().getEntity().getType().is(ModTags.EntityTypes.HEAD_CRAB)) {
                    LivingEntity headcrab = (LivingEntity) event.getSource().getEntity();
                    if (entity instanceof Player player) {

                        EntityType<Headcrab_zombie_standart> type = ModEntities.HEADCRAB_ZOMBIE_1.get();
                        Headcrab_zombie_standart zombie = type.create(player.getLevel());
                        if (zombie != null) {
                            zombie.setCustomName(player.getDisplayName());
                            zombie.moveTo(player.getX(), player.getY(), player.getZ(), player.getYRot(), 0.0F);
                            ForgeEventFactory.onFinalizeSpawn(zombie, (ServerLevelAccessor) zombie.getLevel(), zombie.getLevel().getCurrentDifficultyAt(zombie.blockPosition()), MobSpawnType.REINFORCEMENT, null, null);
                            headcrab.startRiding(zombie);
                            zombie.setPlayerName(player.getName().toString());
                            zombie.setPlayerUUID(player.getUUID().toString());
                            zombie.getLevel().addFreshEntity(zombie);
                        }
                    }
                }
            } */
        }
    }

    @Mod.EventBusSubscriber(modid = HalfLifeMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ModEventBusEvents {

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



}


/*   @SubscribeEvent
    public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(PigSummonModel.LAYER_LOCATION, PigSummonModel::createBodyLayer);
        event.registerLayerDefinition(DogSummonModel.LAYER_LOCATION, DogSummonModel::createBodyLayer);
    } */