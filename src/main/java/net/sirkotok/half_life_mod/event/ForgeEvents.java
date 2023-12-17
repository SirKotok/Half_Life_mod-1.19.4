package net.sirkotok.half_life_mod.event;


import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.sirkotok.half_life_mod.HalfLifeMod;
import net.sirkotok.half_life_mod.entity.mob_geckolib.custom.Chumtoad;
import net.sirkotok.half_life_mod.entity.mob_normal.custom.Barnacle;
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

            if (entity.getTags().contains("xenported")) {
                if (entity.isOnGround() && !entity.isInWall()) entity.removeTag("xenported");
                else {
                    SquareRadius radius = new SquareRadius(25, 60);
                    for (BlockPos pos : BlockPos.betweenClosed(entity.blockPosition().subtract(radius.toVec3i()), entity.blockPosition().offset(radius.toVec3i()))) {
                        BlockState state = entity.level.getBlockState(pos);
                        BlockState statebelow = entity.level.getBlockState(pos.below());
                        BlockState stateabove = entity.level.getBlockState(pos.above());
                        if (state.isAir() && !statebelow.isAir() && stateabove.isAir()) {
                            entity.moveTo(pos.getCenter());
                            break;
                        }
                    }
                   if (RandomSource.create().nextFloat() < 0.9f) entity.removeTag("xenported");
            }
            }


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





