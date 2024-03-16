package net.sirkotok.half_life_mod.misc.gamerules;

import net.minecraft.world.level.GameRules;

public class HalfLifeGameRules {
    public static final GameRules.Key<GameRules.BooleanValue> HALF_LIFE_AI_IS_ON = GameRules
            .register("halflifehasai", GameRules.Category.MOBS, GameRules.BooleanValue.create(true));

}
