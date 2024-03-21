package net.sirkotok.half_life_mod.entity.mob.modinterface;

import net.minecraft.world.phys.AABB;

public interface RushingMob {
    void hitbody();
    void hitwall();


    AABB getrushingbox();
}