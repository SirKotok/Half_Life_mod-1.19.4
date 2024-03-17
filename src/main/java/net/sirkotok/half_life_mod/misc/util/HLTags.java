package net.sirkotok.half_life_mod.misc.util;

import net.minecraft.core.registries.Registries;

import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.EntityType;


import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.sirkotok.half_life_mod.HalfLifeMod;


public class HLTags {
    public static class Blocks {
        public static final TagKey<Block> HOUNDEYE_INTEREST = tag("houndeye_interest");
        public static final TagKey<Block> EXPLOSION = tag("hl_explosion_blocks");
        public static final TagKey<Block> XEN_CARVER_REPLACABLE = tag("xen_carver_replacable");
        public static final TagKey<Block> GRAVITY_GUN_BLACKLIST = tag("gravity_gun_blacklist");
        public static final TagKey<Block> SUPER_GRAVITY_GUN_BLACKLIST = tag("supergrav_gun_blacklist");
        private static TagKey<Block> tag(String name) {
            return BlockTags.create(new ResourceLocation(HalfLifeMod.MOD_ID, name));
        }
    }


    public static class DamageTypes {
        public static final TagKey<DamageType> HEADCRAB_ARMOR_PROTECTS_FROM = tag("headcrab_armor_protects_from");
        private static TagKey<DamageType> tag(String name) {
            return TagKey.create(Registries.DAMAGE_TYPE, new ResourceLocation(HalfLifeMod.MOD_ID, name));
        }
    }

    public static class Items {

        private static TagKey<Item> tag(String name) {
            return ItemTags.create(new ResourceLocation(HalfLifeMod.MOD_ID, name));
        }
    }

    public static class EntityTypes {
        public static final TagKey<EntityType<?>> GRAVITY_GUN_EXPLOSION = tag("gravity_gun_explosion");
        public static final TagKey<EntityType<?>> GRAVITY_GUN_CATCHABLE = tag("gravity_gun_catchable");
        public static final TagKey<EntityType<?>> GRAVITY_GUN_IMMUNE = tag("gravity_gun_immune");
        public static final TagKey<EntityType<?>> HEADCRAB = tag("headcrab");
        public static final TagKey<EntityType<?>> FACTION_HEADCRAB = tag("fac_headcrab");

        public static final TagKey<EntityType<?>> FACTION_RACE_X = tag("race_x");
        public static final TagKey<EntityType<?>> FACTION_XEN = tag("xen_forces");
        public static final TagKey<EntityType<?>> FACTION_SCIENCE_TEAM = tag("science_team");
        public static final TagKey<EntityType<?>> FACTION_COMBINE = tag("combine");

        public static final TagKey<EntityType<?>> FACTION_ANTLION = tag("antlion");

        public static final TagKey<EntityType<?>> HEAD_CRAB_TARGET = tag("can_be_headcrabbed");
        private static TagKey<EntityType<?>> tag(String name)
        {
            return TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation(HalfLifeMod.MOD_ID, name));
        }
    }
}

