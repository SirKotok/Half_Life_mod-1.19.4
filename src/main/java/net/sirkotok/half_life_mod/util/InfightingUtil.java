package net.sirkotok.half_life_mod.util;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.levelgen.structure.structures.NetherFortressPieces;
import net.sirkotok.half_life_mod.config.HalfLifeCommonConfigs;
import net.sirkotok.half_life_mod.effect.HalfLifeEffects;
import net.sirkotok.half_life_mod.entity.base.HalfLifeMonster;
import net.sirkotok.half_life_mod.entity.base.HalfLifeNeutral;
import net.sirkotok.half_life_mod.entity.mob_geckolib.custom.Pitdrone;
import net.sirkotok.half_life_mod.entity.mob_geckolib.custom.Shockroach;

public class InfightingUtil {

    public static boolean issameteam(LivingEntity one, LivingEntity two){
        boolean antlion = (one.getType().is(HLTags.EntityTypes.FACTION_ANTLION) && two.getType().is(HLTags.EntityTypes.FACTION_ANTLION))
                || (one.hasEffect(HalfLifeEffects.ANTLION_PHEROMONE_FRIEND.get()) && two.getType().is(HLTags.EntityTypes.FACTION_ANTLION))
                || (two.hasEffect(HalfLifeEffects.ANTLION_PHEROMONE_FRIEND.get()) && one.getType().is(HLTags.EntityTypes.FACTION_ANTLION))
                ;
        boolean combine = (one.getType().is(HLTags.EntityTypes.FACTION_COMBINE) && two.getType().is(HLTags.EntityTypes.FACTION_COMBINE));
        boolean science_team = (one.getType().is(HLTags.EntityTypes.FACTION_SCIENCE_TEAM) && two.getType().is(HLTags.EntityTypes.FACTION_SCIENCE_TEAM));
        boolean neutral_vs_enemy_player = (one instanceof HalfLifeNeutral neutral && two instanceof Player player && neutral.ismyenemy(player.getStringUUID())) || (two instanceof HalfLifeNeutral neutral2 && one instanceof Player player2 && neutral2.ismyenemy(player2.getStringUUID()));
        boolean race_x = (one.getType().is(HLTags.EntityTypes.FACTION_RACE_X) && two.getType().is(HLTags.EntityTypes.FACTION_RACE_X));
        boolean xen = (one.getType().is(HLTags.EntityTypes.FACTION_XEN) && two.getType().is(HLTags.EntityTypes.FACTION_XEN));
        boolean pitdrone_unique = !((one instanceof Pitdrone && two instanceof Shockroach) || (one instanceof Shockroach && two instanceof Pitdrone));
        boolean headcrab = (one.getType().is(HLTags.EntityTypes.FACTION_HEADCRAB) && two.getType().is(HLTags.EntityTypes.FACTION_HEADCRAB));
        return (antlion || science_team || race_x || combine || headcrab || xen) && pitdrone_unique && !neutral_vs_enemy_player;
    }


    public static boolean commonenemy(LivingEntity target) {
        return target instanceof HalfLifeNeutral || target instanceof AbstractVillager || target instanceof IronGolem || target instanceof Player;
    }

    public static boolean nonfactionSpecific(LivingEntity target) {
        boolean combine = (iscombine(target) && HalfLifeCommonConfigs.INFIGHTING_COMBINE_VS_NORMAL.get());
        boolean racex = (isracex(target) && HalfLifeCommonConfigs.INFIGHTING_RACEX_NORMAL.get());
        boolean antlion = (isantlion(target) && HalfLifeCommonConfigs.INFIGHTING_ANTLION_NORMAL.get());
        boolean headcrab = (isfactionheadcrab(target) && HalfLifeCommonConfigs.INFIGHTING_HEADCRAB_NORMAL.get());
        boolean xen = (isxenforces(target) && HalfLifeCommonConfigs.INFIGHTING_XEN_NORMAL.get());
        return commonenemy(target) || combine || racex ||  antlion || headcrab || xen;
    }

    public static boolean iscombine(LivingEntity target) {
        return target.getType().is(HLTags.EntityTypes.FACTION_COMBINE);
    }
    public static boolean isscienceteam(LivingEntity target) {
        return target.getType().is(HLTags.EntityTypes.FACTION_SCIENCE_TEAM);
    }
    public static boolean isantlion(LivingEntity target) {
        return target.getType().is(HLTags.EntityTypes.FACTION_ANTLION);
    }
    public static boolean isfactionheadcrab(LivingEntity target) {
        return target.getType().is(HLTags.EntityTypes.FACTION_HEADCRAB);
    }
    public static boolean isxenforces(LivingEntity target) {
        return target.getType().is(HLTags.EntityTypes.FACTION_XEN);
    }
    public static boolean isracex(LivingEntity target) {
        return target.getType().is(HLTags.EntityTypes.FACTION_RACE_X);
    }


    public static boolean hasfaction(LivingEntity target) {
        boolean race_x = target.getType().is(HLTags.EntityTypes.FACTION_RACE_X);
        boolean s_team = target.getType().is(HLTags.EntityTypes.FACTION_SCIENCE_TEAM);
        boolean combine = target.getType().is(HLTags.EntityTypes.FACTION_COMBINE);
        boolean headcrab = target.getType().is(HLTags.EntityTypes.FACTION_HEADCRAB);
        boolean xen = target.getType().is(HLTags.EntityTypes.FACTION_XEN);
        boolean ant = target.getType().is(HLTags.EntityTypes.FACTION_ANTLION);
        return race_x || s_team || combine || headcrab || xen || ant;
    }
    public static boolean CombineSpecific(LivingEntity target) {
        boolean normal = (target instanceof Enemy && !hasfaction(target) && HalfLifeCommonConfigs.INFIGHTING_COMBINE_VS_NORMAL.get());
        boolean racex = (isracex(target) && HalfLifeCommonConfigs.INFIGHTING_RACEX_COMBINE.get());
        boolean headcrab = (isfactionheadcrab(target) && HalfLifeCommonConfigs.INFIGHTING_HEADCRAB_COMBINE.get());
        boolean xen = (isxenforces(target) && HalfLifeCommonConfigs.INFIGHTING_XEN_COMBINE.get());
        boolean ant = (isantlion(target) && HalfLifeCommonConfigs.INFIGHTING_ANTLION_COMBINE.get());
        return racex || normal || headcrab || xen || ant;
    }

    public static boolean RaceXSpecific(LivingEntity target) {
        boolean normal = (target instanceof Enemy && !hasfaction(target) && HalfLifeCommonConfigs.INFIGHTING_RACEX_NORMAL.get());
        boolean combine = (iscombine(target) && HalfLifeCommonConfigs.INFIGHTING_RACEX_COMBINE.get());
        boolean headcrab = (isfactionheadcrab(target) && HalfLifeCommonConfigs.INFIGHTING_RACEX_HEADCRAB.get());
        boolean xen = (isxenforces(target) && HalfLifeCommonConfigs.INFIGHTING_RACEX_XEN.get());
        boolean ant = (isantlion(target) && HalfLifeCommonConfigs.INFIGHTING_RACEX_ANTLION.get());
        return combine || normal || headcrab || xen || ant;
    }

    public static boolean HeadcrabFactionSpecific(LivingEntity target) {
        boolean normal = (target instanceof Enemy && !hasfaction(target) && HalfLifeCommonConfigs.INFIGHTING_HEADCRAB_NORMAL.get());
        boolean combine = (iscombine(target) && HalfLifeCommonConfigs.INFIGHTING_HEADCRAB_COMBINE.get());
        boolean racex = (isracex(target) && HalfLifeCommonConfigs.INFIGHTING_RACEX_HEADCRAB.get());
        boolean xen = (isxenforces(target) && HalfLifeCommonConfigs.INFIGHTING_HEADCRAB_XEN.get());
        boolean ant = (isantlion(target) && HalfLifeCommonConfigs.INFIGHTING_HEADCRAB_ANTLIONS.get());
        return combine || normal || racex || xen || ant;
    }

    public static boolean XenForcesSpecific(LivingEntity target) {
        boolean normal = (target instanceof Enemy && !hasfaction(target) && HalfLifeCommonConfigs.INFIGHTING_XEN_NORMAL.get());
        boolean combine = (iscombine(target) && HalfLifeCommonConfigs.INFIGHTING_XEN_COMBINE.get());
        boolean racex = (isracex(target) && HalfLifeCommonConfigs.INFIGHTING_RACEX_XEN.get());
        boolean headcrab = (isfactionheadcrab(target) && HalfLifeCommonConfigs.INFIGHTING_HEADCRAB_XEN.get());
        boolean ant = (isantlion(target) && HalfLifeCommonConfigs.INFIGHTING_XEN_ANTLION.get());
        return combine || normal || racex || headcrab || ant;
    }

    public static boolean AntlionSpecific(LivingEntity target) {
        boolean normal = (target instanceof Enemy && !hasfaction(target) && HalfLifeCommonConfigs.INFIGHTING_ANTLION_NORMAL.get());
        boolean combine = (iscombine(target) && HalfLifeCommonConfigs.INFIGHTING_ANTLION_COMBINE.get());
        boolean racex = (isracex(target) && HalfLifeCommonConfigs.INFIGHTING_RACEX_ANTLION.get());
        boolean headcrab = (isfactionheadcrab(target) && HalfLifeCommonConfigs.INFIGHTING_HEADCRAB_ANTLIONS.get());
        boolean xen = (isxenforces(target) && HalfLifeCommonConfigs.INFIGHTING_XEN_ANTLION.get());
        return combine || normal || racex || headcrab || xen;
    }


}
