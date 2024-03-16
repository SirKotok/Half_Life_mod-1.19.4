package net.sirkotok.half_life_mod.item.custom.gun;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.sirkotok.half_life_mod.entity.particle.HalfLifeParticles;
import net.sirkotok.half_life_mod.misc.util.HLTags;
import net.sirkotok.half_life_mod.sound.HalfLifeSounds;

public class SuperGravityGun extends GravityGun{
    public SuperGravityGun(Properties pProperties) {
        super(pProperties);
    }
    public int getrange() {
        return 8;
    }
    public int getrange2() {
        return 32;
    }

    public void sendparticles(Player pPlayer, Vec3 from, Vec3 to) {
        ((ServerLevel) pPlayer.level).sendParticles(HalfLifeParticles.GRAV_GUN_BLUE_LIGHTNING.get(), from.x, from.y, from.z, 0, to.x, to.y, to.z, 1d);
    }


    public boolean checkblockstate(BlockState pBlockState){
        return pBlockState.is(HLTags.Blocks.SUPER_GRAVITY_GUN_BLACKLIST);
    }

    public boolean isentityhittablewithggun(Entity pEntity){
        if (pEntity instanceof LivingEntity) {
                return true;
        }
        return false;
    }


    @Override
    public float getgundamage() {
        return 10;
    }

    public SoundEvent getLoopSound(){
        return HalfLifeSounds.SUPERPHYS_HOLD_LOOP.get();
    }




}
