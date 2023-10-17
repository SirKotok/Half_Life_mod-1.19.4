package net.sirkotok.half_life_mod.entity.mob_geckolib.custom;

import com.mojang.authlib.GameProfile;
import com.mojang.util.UUIDTypeAdapter;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraftforge.event.ForgeEventFactory;
import net.sirkotok.half_life_mod.entity.base.HalfLifeMonster;
import net.sirkotok.half_life_mod.entity.brain.behaviour.Retaliate;
import net.sirkotok.half_life_mod.entity.ModEntities;
import net.tslat.smartbrainlib.api.SmartBrainOwner;
import net.tslat.smartbrainlib.api.core.BrainActivityGroup;
import net.tslat.smartbrainlib.api.core.SmartBrainProvider;
import net.tslat.smartbrainlib.api.core.behaviour.FirstApplicableBehaviour;
import net.tslat.smartbrainlib.api.core.behaviour.OneRandomBehaviour;
import net.tslat.smartbrainlib.api.core.behaviour.custom.attack.AnimatableMeleeAttack;
import net.tslat.smartbrainlib.api.core.behaviour.custom.look.LookAtTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.misc.Idle;
import net.tslat.smartbrainlib.api.core.behaviour.custom.move.MoveToWalkTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.path.SetRandomWalkTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.path.SetWalkTargetToAttackTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.target.InvalidateAttackTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.target.SetPlayerLookTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.target.SetRandomLookTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.target.TargetOrRetaliate;
import net.tslat.smartbrainlib.api.core.sensor.ExtendedSensor;
import net.tslat.smartbrainlib.api.core.sensor.vanilla.HurtBySensor;
import net.tslat.smartbrainlib.api.core.sensor.vanilla.NearbyLivingEntitySensor;
import net.tslat.smartbrainlib.api.core.sensor.vanilla.NearbyPlayersSensor;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.util.GeckoLibUtil;

import javax.annotation.Nullable;
import java.util.List;

public class Headcrab_zombie_standart extends HalfLifeMonster implements GeoEntity, SmartBrainOwner<Headcrab_zombie_standart> {


    public Headcrab_zombie_standart(EntityType type, Level level) {
        super(type, level);
    }
    private AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    // TODO: this should really be done with Additional spawn data, but I have no idea how so will do that latter proboboly
    public static final EntityDataAccessor<Integer> ID_TEXTURE = SynchedEntityData.defineId(Headcrab_zombie_standart.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<String> UUID_STRING = SynchedEntityData.defineId(Headcrab_zombie_standart.class, EntityDataSerializers.STRING);
    public static final EntityDataAccessor<String> PLAYER_NAME_STRING = SynchedEntityData.defineId(Headcrab_zombie_standart.class, EntityDataSerializers.STRING);



    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(ID_TEXTURE, 1);
        this.entityData.define(UUID_STRING, "");
        this.entityData.define(PLAYER_NAME_STRING, "");
    }

    public int getdefaulttexture() {
        return this.entityData.get(ID_TEXTURE);
    }
    protected void setdefaulttexture(int texture) {
        this.entityData.set(ID_TEXTURE, texture);
    }

    public String getplayerUUID() {
        return this.entityData.get(UUID_STRING);
    }
    public String getplayername() {
        return this.entityData.get(PLAYER_NAME_STRING);
    }
    public void setPlayerUUID(String playerUUID) {
        this.entityData.set(UUID_STRING, playerUUID);
    }
    public void setPlayerName(String playerName) {
        this.entityData.set(PLAYER_NAME_STRING, playerName);
    }


    public void addAdditionalSaveData(CompoundTag p_33619_) {
        super.addAdditionalSaveData(p_33619_);
        p_33619_.putInt("Texture", this.getdefaulttexture() - 1 );
        p_33619_.putString("UUID_String", this.getplayerUUID() );
        p_33619_.putString("Player_name", this.getplayername() );
    }

    public void readAdditionalSaveData(CompoundTag tag) {
        this.setdefaulttexture(tag.getInt("Texture") + 1);
        this.setPlayerUUID(tag.getString("UUID_String"));
        this.setPlayerName(tag.getString("Player_name"));
        super.readAdditionalSaveData(tag);
    }

    public GameProfile getprofile() {
        if (!this.getplayerUUID().equals("")) {return new GameProfile(UUIDTypeAdapter.fromString(this.getplayerUUID()), this.getplayername());}
        return null;
    }





    public static AttributeSupplier setAttributes() {
        return Monster.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 20D)
                .add(Attributes.ATTACK_DAMAGE, 5f)
                .add(Attributes.ATTACK_SPEED, 1.0f)
                .add(Attributes.ATTACK_KNOCKBACK, 1f)
                .add(Attributes.MOVEMENT_SPEED, 0.25f).build();
    }



    @Override
    protected Brain.Provider<?> brainProvider() {
        return new SmartBrainProvider<>(this);
    }

    @Override
    protected void customServerAiStep() {
        tickBrain(this);
    }







    @Override
    public List<ExtendedSensor<Headcrab_zombie_standart>> getSensors() {
        return ObjectArrayList.of(
                new HurtBySensor<>(),
                new NearbyPlayersSensor<>(),
                new NearbyLivingEntitySensor<Headcrab_zombie_standart>()
                        .setPredicate((target, entity) ->
                                target instanceof AbstractVillager));
    }

    @Override
    public BrainActivityGroup<Headcrab_zombie_standart> getCoreTasks() {
        return BrainActivityGroup.coreTasks(
                new LookAtTarget<>(),
                new MoveToWalkTarget<>());
    }

    @Override
    public BrainActivityGroup<Headcrab_zombie_standart> getIdleTasks() {
        return BrainActivityGroup.idleTasks(
                new FirstApplicableBehaviour<Headcrab_zombie_standart>(
                        new TargetOrRetaliate<>(),
                        new SetPlayerLookTarget<>(),
                        new SetRandomLookTarget<>()),
                new OneRandomBehaviour<>(
                        new SetRandomWalkTarget<>(),
                        new Idle<>().runFor(entity -> entity.getRandom().nextInt(30, 90))));
    }

    @Override
    public BrainActivityGroup<Headcrab_zombie_standart> getFightTasks() { // These are the tasks that handle fighting
        return BrainActivityGroup.fightTasks(
                new InvalidateAttackTarget<>(),
                new SetWalkTargetToAttackTarget<>(),
                new Retaliate<>(),
                new AnimatableMeleeAttack<>(0)
                );
    }


    //TODO: fix the code since it fukin broke for random reasons, I cant be bothered rn
    @Nullable
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor levelaccessor, DifficultyInstance difficulty, MobSpawnType spawntype, @Nullable SpawnGroupData spawnGroupData, @Nullable CompoundTag tag, int playerid) {
        if (!spawntype.equals(MobSpawnType.REINFORCEMENT)) {
        RandomSource randomsource = levelaccessor.getRandom();
        int i = randomsource.nextInt(4);
        this.setdefaulttexture(i);

        EntityType<Headcrab_1> type = ModEntities.HEADCRAB_HL1.get();
        Headcrab_1 headcrab1 = type.create(this.level);
        if (headcrab1 != null) {
            headcrab1.moveTo(this.getX(), this.getY(), this.getZ(), this.getYRot(), 0.0F);
            ForgeEventFactory.onFinalizeSpawn(headcrab1, levelaccessor, difficulty, MobSpawnType.REINFORCEMENT, null, null);
            headcrab1.startRiding(this);
                levelaccessor.addFreshEntity(headcrab1);
        }
        return super.finalizeSpawn(levelaccessor, difficulty, spawntype, spawnGroupData, tag);
    }

        this.setPersistenceRequired();
        return super.finalizeSpawn(levelaccessor, difficulty, spawntype, spawnGroupData, tag);
    }





    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }



}

