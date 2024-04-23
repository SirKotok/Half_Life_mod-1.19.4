package net.sirkotok.half_life_mod.item.custom.spawnegg;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.SpawnerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.sirkotok.half_life_mod.entity.HalfLifeEntities;
import net.sirkotok.half_life_mod.entity.mob.mob_geckolib.custom.HecuGrunt;
import net.sirkotok.half_life_mod.entity.mob.mob_geckolib.custom.Voltigore;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Supplier;

public class HecuSpawnEgg extends ForgeSpawnEggItem {

    private final int gtype;

    public HecuSpawnEgg(Supplier<? extends EntityType<? extends Mob>> type, int backgroundColor, int highlightColor, Properties props, int gtype) {
        super(type, backgroundColor, highlightColor, props);
        this.gtype = gtype;
    }



    @Override
    public InteractionResult useOn(UseOnContext pContext) {
        Level level = pContext.getLevel();
        if (!(level instanceof ServerLevel)) {
            return InteractionResult.SUCCESS;
        } else {
            ItemStack itemstack = pContext.getItemInHand();
            BlockPos blockpos = pContext.getClickedPos();
            Direction direction = pContext.getClickedFace();
            BlockState blockstate = level.getBlockState(blockpos);
            if (blockstate.is(Blocks.SPAWNER)) {
                BlockEntity blockentity = level.getBlockEntity(blockpos);
                if (blockentity instanceof SpawnerBlockEntity) {
                    return InteractionResult.FAIL;
                }
            }

            BlockPos blockpos1;
            if (blockstate.getCollisionShape(level, blockpos).isEmpty()) {
                blockpos1 = blockpos;
            } else {
                blockpos1 = blockpos.relative(direction);
            }

            EntityType<?> pEntityType = this.getType(itemstack.getTag());
            HecuGrunt grunt = HalfLifeEntities.HECU_GRUNT.get().spawn((ServerLevel) level, itemstack, pContext.getPlayer(), blockpos1, MobSpawnType.SPAWN_EGG, true, false);
            if (grunt == null) {
                return InteractionResult.FAIL;
            } else {
                grunt.setGruntType(gtype);
                grunt.setKitFromGruntType();
                itemstack.shrink(1);
                level.gameEvent(pContext.getPlayer(), GameEvent.ENTITY_PLACE, blockpos);
            }
            return InteractionResult.CONSUME;

        }


    }
}



