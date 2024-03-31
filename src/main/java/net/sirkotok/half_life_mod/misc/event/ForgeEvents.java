package net.sirkotok.half_life_mod.misc.event;


import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.*;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.sirkotok.half_life_mod.HalfLifeMod;
import net.sirkotok.half_life_mod.entity.HalfLifeEntities;
import net.sirkotok.half_life_mod.entity.base.HalfLifeNeutral;
import net.sirkotok.half_life_mod.entity.fallingblock.GravityGunFallingBlockEntity;
import net.sirkotok.half_life_mod.entity.mob.mob_geckolib.custom.*;
import net.sirkotok.half_life_mod.entity.mob.mob_normal.custom.Barnacle;
import net.sirkotok.half_life_mod.item.HalfLifeItems;
import net.sirkotok.half_life_mod.misc.config.HalfLifeCommonConfigs;
import net.sirkotok.half_life_mod.misc.damagesource.HalfLifeDamageTypes;
import net.sirkotok.half_life_mod.misc.util.HLTags;
import net.tslat.smartbrainlib.api.SmartBrainOwner;
import net.tslat.smartbrainlib.object.SquareRadius;
import net.tslat.smartbrainlib.util.BrainUtils;
import net.tslat.smartbrainlib.util.EntityRetrievalUtil;

import java.util.List;
import java.util.ListIterator;



@Mod.EventBusSubscriber(modid = HalfLifeMod.MOD_ID)
public class ForgeEvents {

/*
    private static final Logger LOGGER = LogUtils.getLogger();
        @SubscribeEvent
        public static void onEntityJoinEvent(EntityJoinLevelEvent event) {
            Entity entity = event.getEntity();
            if (entity instanceof Shark) {
                LOGGER.debug("Sharktriedtospawn");
            }
        }

 */


    protected static boolean canHitEntity(Entity pTarget) {
        if (!pTarget.canBeHitByProjectile()) {
            return false;
        } else return !pTarget.noPhysics;
    }


    protected static void onHit(HitResult pResult, Entity pEntity) {
        HitResult.Type hitresult$type = pResult.getType();
        if (pEntity.getType().is(HLTags.EntityTypes.GRAVITY_GUN_EXPLOSION)) {
            pEntity.level.explode(pEntity, pEntity.getX(), pEntity.getY(), pEntity.getZ(), pEntity instanceof Creeper creeper && creeper.isPowered() ? 5 : 3, Level.ExplosionInteraction.MOB);
            pEntity.discard();
            return;
        }
        if (hitresult$type == HitResult.Type.ENTITY) {
            onHitEntity((EntityHitResult)pResult, pEntity);
            pEntity.level.gameEvent(GameEvent.PROJECTILE_LAND, pResult.getLocation(), GameEvent.Context.of(pEntity, (BlockState)null));
        } else if (hitresult$type == HitResult.Type.BLOCK) {
            BlockHitResult blockhitresult = (BlockHitResult)pResult;
            onHitBlock(blockhitresult, pEntity);
            BlockPos blockpos = blockhitresult.getBlockPos();
            pEntity.level.gameEvent(GameEvent.PROJECTILE_LAND, blockpos, GameEvent.Context.of(pEntity, pEntity.level.getBlockState(blockpos)));
        }
    }



    protected static void onHitEntity(EntityHitResult pResult, Entity pEntity) {
        if (!pEntity.level.isClientSide) {

            List<ServerPlayer> pplayers = ((ServerLevel)pEntity.level).getPlayers((pPlayer) -> pEntity.getTags().contains("launchedby"+pPlayer.getStringUUID()));
            Player owner = null;
            if (!pplayers.isEmpty()) {
                for (ServerPlayer ppplayer : pplayers) {
                    pEntity.removeTag("launchedby"+ppplayer.getStringUUID());
                    owner = ppplayer;
                }
            }

            int damage = (int) pEntity.getDeltaMovement().scale(4).length();
            Entity entity = pResult.getEntity();
            entity.hurt(pEntity.level.damageSources().mobProjectile(pEntity, owner), damage);
            pEntity.hurt(pEntity.level.damageSources().playerAttack(owner), damage);
        }
    }
    protected static void onHitBlock(BlockHitResult pResult, Entity pEntity) {
        if (!pEntity.level.isClientSide) {
        List<ServerPlayer> pplayers = ((ServerLevel)pEntity.level).getPlayers((pPlayer) -> pEntity.getTags().contains("launchedby"+pPlayer.getStringUUID()));
        Player owner = null;
        int damage = (int) pEntity.getDeltaMovement().scale(4).length();
        if (!pplayers.isEmpty()) {
            for (ServerPlayer ppplayer : pplayers) {
                pEntity.removeTag("launchedby"+ppplayer.getStringUUID());
                owner = ppplayer;
            }
        }
        pEntity.hurt(pEntity.level.damageSources().playerAttack(owner), damage);
        }
    }


    @SubscribeEvent
    public static void  onDamagedEvent(LivingDamageEvent event) {
        DamageSource source = event.getSource();
        LivingEntity entity = event.getEntity();
        if (entity instanceof Creeper creeper && source.is(HalfLifeDamageTypes.HL_ELECTRIC)) creeper.ignite(); //TODO make it power the creeper
        if (source.is(HalfLifeDamageTypes.HL_BULLET) && !entity.isSilent()) {
            entity.addTag("gunsilencedfor11");
        }

    }

        @SubscribeEvent
        public static void  onLivingChangeTick(LivingEvent.LivingTickEvent event) {
            LivingEntity entity = event.getEntity();
            if (entity.level.isClientSide()) return;
            if (event.isCanceled()) return;

            if (entity.getTags().contains("gunsilencedfor0")) {
                entity.setSilent(false);
                entity.removeTag("gunsilencedfor0");
            }

            if (entity.getTags().contains("gunsilencedfor10")) {
                entity.setSilent(true);
            }

            int p = 1;
            while (p<12) {
            if (entity.getTags().contains("gunsilencedfor"+p)) {
                entity.removeTag("gunsilencedfor"+p);
                entity.addTag("gunsilencedfor"+(p-1));
                break;
            }
                p++;
            }


            if (entity.getTags().contains("gglaunched")) {
                HitResult hitresult = ProjectileUtil.getHitResult(entity, ForgeEvents::canHitEntity);
                if (hitresult.getType() != HitResult.Type.MISS) {
                    onHit(hitresult, entity);
                }
                if (entity.isOnGround()) {
                    entity.removeTag("gglaunched");
                    List<ServerPlayer> pplayers = ((ServerLevel)entity.level).getPlayers((pPlayer) -> entity.getTags().contains("launchedby"+pPlayer.getStringUUID()));
                    if (!pplayers.isEmpty()) {
                        for (ServerPlayer ppplayer : pplayers) {
                            entity.removeTag("launchedby"+ppplayer.getStringUUID());
                        }
                    }

                }
            }

            if (entity.getTags().contains("ggcaught")) {
                BlockPos pBlockPos = new BlockPos(entity.getBlockX(), entity.getBlockY(), entity.getBlockZ());
                ServerLevel pLevel = (ServerLevel)entity.level;
                int rad = 5;
                List<Player> players = EntityRetrievalUtil.getEntities((Level) pLevel,
                        new AABB(pBlockPos.getX() - rad, pBlockPos.getY() - rad, pBlockPos.getZ() - rad,
                                pBlockPos.getX() + rad, pBlockPos.getY() + rad, pBlockPos.getZ() + rad), obj -> obj instanceof Player player && entity.getTags().contains("caughtby"+player.getStringUUID()) && (player.getMainHandItem().is(HalfLifeItems.GRAVITYGUN.get()) || player.getMainHandItem().is(HalfLifeItems.SUPERGRAVITYGUN.get())));
                if (players.isEmpty()) {
                    List<ServerPlayer> pplayers = pLevel.getPlayers((pPlayer) -> entity.getTags().contains("caughtby"+pPlayer.getStringUUID()));
                    if (!pplayers.isEmpty()) {
                        for (ServerPlayer ppplayer : pplayers) {
                            entity.removeTag("caughtby"+ppplayer.getStringUUID());
                        }
                    }
                    entity.removeTag("ggcaught");
                } else {
                    if (players.size()>1) {
                        int i=0;
                        for (Player pl : players) {
                            if (i != 0) {
                                entity.removeTag("caughtby"+pl.getStringUUID());
                            }
                            i++;
                        }
                    }
                    Player currentplayer = players.get(0);
                    Vec3 vec3 = GravityGunFallingBlockEntity.findposstat(currentplayer);
                    Vec3 vec31 = entity.position();
                    Vec3 movement = vec3.subtract(vec31);
                    entity.setDeltaMovement(movement.scale(0.98D));
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
                    List<Mob> targets = EntityRetrievalUtil.getEntities(entity.level, entity.getBoundingBox().inflate(radius.xzRadius(), radius.yRadius(), radius.xzRadius()), obj -> ((obj instanceof Mob target && (target.hasLineOfSight(entity) || !HalfLifeCommonConfigs.CHUMTOAD_LINE_OF_SIGHT.get())) && !(obj instanceof Chumtoad)));
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



            if (entity.getItemBySlot(EquipmentSlot.HEAD).isEmpty()) {
            if (event.getSource().getEntity() instanceof Headcrab_Fast headcrab) {

                if (entity instanceof HalfLifeNeutral || entity instanceof AbstractVillager || entity instanceof Player) {
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



            if (entity instanceof Villager scientist) {
                if (event.getSource().getEntity() instanceof Headcrab_1 || event.getSource().getEntity() instanceof Headcrab_2 || event.getSource().getEntity() instanceof Headcrab_3) {
                    LivingEntity headcrab = (LivingEntity)event.getSource().getEntity();
                    HLZombieVillager zombie = HalfLifeEntities.VZOMBIE.get().create(scientist.level);
                    if (zombie != null) {
                        zombie.moveTo(scientist.position());
                        zombie.setVillagerData(scientist.getVillagerData());
                        if (scientist.hasCustomName()) zombie.setCustomName(scientist.getCustomName());
                        headcrab.startRiding(zombie);
                        ForgeEventFactory.onFinalizeSpawn((Mob) zombie, (ServerLevelAccessor) scientist.level, scientist.level.getCurrentDifficultyAt(zombie.blockPosition()), MobSpawnType.CONVERSION, null, null);
                        zombie.getLevel().addFreshEntity(zombie);
                        scientist.discard();
                    }
                }
            }



                if (event.getSource().getEntity() instanceof Headcrab_1 headcrab) {

                    if (entity instanceof Barney scientist) {
                        HL1ZombieScientist zombie = HalfLifeEntities.ZOMBIE_SCIENTIST_HL1.get().create(scientist.level);
                        if (zombie != null) {
                            zombie.moveTo(scientist.position());
                            zombie.settexture(-5);
                            if (scientist.hasCustomName()) zombie.setCustomName(scientist.getCustomName());
                            headcrab.startRiding(zombie);
                            ForgeEventFactory.onFinalizeSpawn((Mob) zombie, (ServerLevelAccessor) scientist.level, scientist.level.getCurrentDifficultyAt(zombie.blockPosition()), MobSpawnType.CONVERSION, null, null);
                            zombie.getLevel().addFreshEntity(zombie);
                            scientist.discard();
                        }
                    }


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

                if (entity instanceof Barney scientist) {
                    HL2Zombie zombie = HalfLifeEntities.ZOMBIE_HL2.get().create(scientist.level);
                    if (zombie != null) {
                        zombie.moveTo(scientist.position());
                        zombie.settexture(-5);
                        if (scientist.hasCustomName()) zombie.setCustomName(scientist.getCustomName());
                        headcrab.startRiding(zombie);
                        ForgeEventFactory.onFinalizeSpawn((Mob) zombie, (ServerLevelAccessor) scientist.level, scientist.level.getCurrentDifficultyAt(zombie.blockPosition()), MobSpawnType.CONVERSION, null, null);
                        zombie.getLevel().addFreshEntity(zombie);
                        scientist.discard();
                    }
                }


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
    }


/*                          zombie.setCustomName(player.getDisplayName());
                            zombie.moveTo(player.getX(), player.getY(), player.getZ(), player.getYRot(), 0.0F);
                            ForgeEventFactory.onFinalizeSpawn(zombie, (ServerLevelAccessor) zombie.getLevel(), zombie.getLevel().getCurrentDifficultyAt(zombie.blockPosition()), MobSpawnType.REINFORCEMENT, null, null);
                            headcrab.startRiding(zombie);
                            zombie.setPlayerName(player.getName().toString());
                            zombie.setPlayerUUID(player.getUUID().toString());
                            zombie.getLevel().addFreshEntity(zombie); */


