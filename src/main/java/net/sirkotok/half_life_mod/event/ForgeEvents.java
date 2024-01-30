package net.sirkotok.half_life_mod.event;


import net.minecraft.client.CloudStatus;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Options;
import net.minecraft.core.BlockPos;
import net.minecraft.server.dedicated.Settings;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.sirkotok.half_life_mod.HalfLifeMod;
import net.sirkotok.half_life_mod.entity.HalfLifeEntities;
import net.sirkotok.half_life_mod.entity.mob_geckolib.custom.*;
import net.sirkotok.half_life_mod.entity.mob_normal.custom.Barnacle;
import net.sirkotok.half_life_mod.worldgen.dimension.HalfLifeDimensions;
import net.tslat.smartbrainlib.api.SmartBrainOwner;
import net.tslat.smartbrainlib.object.SquareRadius;
import net.tslat.smartbrainlib.util.BrainUtils;
import net.tslat.smartbrainlib.util.EntityRetrievalUtil;

import java.util.List;
import java.util.ListIterator;


@Mod.EventBusSubscriber(modid = HalfLifeMod.MOD_ID)
public class ForgeEvents {






        @SubscribeEvent
        public static void  onLivingChangeTick(LivingEvent.LivingTickEvent event) {
            LivingEntity entity = event.getEntity();
            if (entity.level.isClientSide()) return;
            if (event.isCanceled()) return;




            if (entity.getTags().contains("barnaclefood")) {
                if (!entity.hasEffect(MobEffects.LEVITATION)) entity.removeTag("barnaclefood");
            }

            if (entity.isDeadOrDying() && entity.getKillCredit() instanceof Barnacle) {
                entity.discard();
            }


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

            if (event.getSource().getEntity() instanceof Barnacle barnacle) {
                barnacle.setHealth(barnacle.getMaxHealth());
            }

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


            if (event.getSource().getEntity() instanceof Headcrab_Fast headcrab) {
                if (entity instanceof Scientist || entity instanceof AbstractVillager || entity instanceof Player) {
                    HL2Zombie_fast zombie = HalfLifeEntities.ZOMBIE_FAST.get().create(entity.level);
                    if (zombie != null) {
                        zombie.moveTo(entity.position());
                        if (entity.hasCustomName()) zombie.setCustomName(entity.getCustomName());
                        if (entity instanceof Player player) zombie.setCustomName(player.getDisplayName());
                        headcrab.startRiding(zombie);
                        ForgeEventFactory.onFinalizeSpawn((Mob) zombie, (ServerLevelAccessor) entity.level, entity.level.getCurrentDifficultyAt(zombie.blockPosition()), MobSpawnType.CONVERSION, null, null);
                        zombie.getLevel().addFreshEntity(zombie);
                        entity.discard();
                    }
                }
            }



                if (event.getSource().getEntity() instanceof Headcrab_1 headcrab) {
                    if (entity instanceof Scientist scientist) {
                        HL1ZombieScientist zombie = HalfLifeEntities.ZOMBIE_SCIENTIST_HL1.get().create(scientist.level);
                        if (zombie != null) {
                            zombie.moveTo(scientist.position());
                            zombie.setColor(scientist.getColor());
                            zombie.settexture(scientist.gettexture());
                            zombie.setShirt(scientist.getShirt());
                            if (scientist.hasCustomName()) zombie.setCustomName(scientist.getCustomName());
                            headcrab.startRiding(zombie);
                            ForgeEventFactory.onFinalizeSpawn((Mob) zombie, (ServerLevelAccessor) scientist.level, scientist.level.getCurrentDifficultyAt(zombie.blockPosition()), MobSpawnType.CONVERSION, null, null);
                            zombie.getLevel().addFreshEntity(zombie);
                            scientist.discard();
                        }
                    }
                    if (entity instanceof Player player) {
                        HL1ZombieScientist zombie = HalfLifeEntities.ZOMBIE_SCIENTIST_HL1.get().create(player.level);
                        if (zombie != null) {
                            zombie.moveTo(player.position());
                            zombie.setCustomName(player.getDisplayName());
                            zombie.setPlayerName(player.getName().toString());
                            zombie.setPlayerUUID(player.getUUID().toString());
                            headcrab.startRiding(zombie);
                            ForgeEventFactory.onFinalizeSpawn((Mob) zombie, (ServerLevelAccessor) player.level, player.level.getCurrentDifficultyAt(zombie.blockPosition()), MobSpawnType.CONVERSION, null, null);
                            zombie.getLevel().addFreshEntity(zombie);
                            player.discard();
                        }
                    }
                }


            if (event.getSource().getEntity() instanceof Headcrab_2 headcrab) {
                if (entity instanceof Scientist scientist) {
                    HL2Zombie zombie = HalfLifeEntities.ZOMBIE_HL2.get().create(scientist.level);
                    if (zombie != null) {
                        zombie.moveTo(scientist.position());
                        zombie.setColor(scientist.getColor());
                        zombie.settexture(scientist.gettexture());
                        zombie.setShirt(scientist.getShirt());
                        if (scientist.hasCustomName()) zombie.setCustomName(scientist.getCustomName());
                        headcrab.startRiding(zombie);
                        ForgeEventFactory.onFinalizeSpawn((Mob) zombie, (ServerLevelAccessor) scientist.level, scientist.level.getCurrentDifficultyAt(zombie.blockPosition()), MobSpawnType.CONVERSION, null, null);
                        zombie.getLevel().addFreshEntity(zombie);
                        scientist.discard();
                    }
                }
                if (entity instanceof Player player) {
                    HL2Zombie zombie = HalfLifeEntities.ZOMBIE_HL2.get().create(player.level);
                    if (zombie != null) {
                        zombie.moveTo(player.position());
                        zombie.setCustomName(player.getDisplayName());
                        zombie.setPlayerName(player.getName().toString());
                        zombie.setPlayerUUID(player.getUUID().toString());
                        zombie.setPersistenceRequired();
                        headcrab.startRiding(zombie);
                        ForgeEventFactory.onFinalizeSpawn((Mob) zombie, (ServerLevelAccessor) player.level, player.level.getCurrentDifficultyAt(zombie.blockPosition()), MobSpawnType.CONVERSION, null, null);
                        zombie.getLevel().addFreshEntity(zombie);
                        player.discard();
                    }
                }
            }








        }
    }


/*                          zombie.setCustomName(player.getDisplayName());
                            zombie.moveTo(player.getX(), player.getY(), player.getZ(), player.getYRot(), 0.0F);
                            ForgeEventFactory.onFinalizeSpawn(zombie, (ServerLevelAccessor) zombie.getLevel(), zombie.getLevel().getCurrentDifficultyAt(zombie.blockPosition()), MobSpawnType.REINFORCEMENT, null, null);
                            headcrab.startRiding(zombie);
                            zombie.setPlayerName(player.getName().toString());
                            zombie.setPlayerUUID(player.getUUID().toString());
                            zombie.getLevel().addFreshEntity(zombie); */


