package net.sirkotok.half_life_mod.sound;


import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.sirkotok.half_life_mod.HalfLifeMod;



public class ModSounds {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS =
            DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, HalfLifeMod.MOD_ID);

    // Vortigore sounds
    public static final RegistryObject<SoundEvent> VORTIGORE_SHOCK =
            registerSoundEvent("voltigore_attack_shock");
    public static final RegistryObject<SoundEvent> VORTIGORE_EAT =
            registerSoundEvent("voltigore_eat");
    public static final RegistryObject<SoundEvent> VORTIGORE_RIGHTATTACK =
            registerSoundEvent("voltigore_melee1");
    public static final RegistryObject<SoundEvent> VORTIGORE_BIGATTACK =
            registerSoundEvent("voltigore_melee2");
    public static final RegistryObject<SoundEvent> VORTIGORE_RUN_STEP1 =
            registerSoundEvent("voltigore_run_grunt1");
    public static final RegistryObject<SoundEvent> VORTIGORE_RUN_STEP2 =
            registerSoundEvent("voltigore_run_grunt2");


    public static final RegistryObject<SoundEvent> VORTIGORE_STEP3 =
            registerSoundEvent("voltigore_footstep1");
    public static final RegistryObject<SoundEvent> VORTIGORE_STEP2 =
            registerSoundEvent("voltigore_footstep2");
    public static final RegistryObject<SoundEvent> VORTIGORE_STEP1 =
            registerSoundEvent("voltigore_footstep3");
    public static final RegistryObject<SoundEvent> VORTIGORE_DIE3 =
            registerSoundEvent("voltigore_die1");
    public static final RegistryObject<SoundEvent> VORTIGORE_DIE2 =
            registerSoundEvent("voltigore_die2");
    public static final RegistryObject<SoundEvent> VORTIGORE_DIE1 =
            registerSoundEvent("voltigore_die3");
    public static final RegistryObject<SoundEvent> VORTIGORE_TALK3 =
            registerSoundEvent("voltigore_communicate1");
    public static final RegistryObject<SoundEvent> VORTIGORE_TALK2 =
            registerSoundEvent("voltigore_communicate2");
    public static final RegistryObject<SoundEvent> VORTIGORE_TALK1 =
            registerSoundEvent("voltigore_communicate3");
    public static final RegistryObject<SoundEvent> VORTIGORE_IDLE3 =
            registerSoundEvent("voltigore_idle1");
    public static final RegistryObject<SoundEvent> VORTIGORE_IDLE2 =
            registerSoundEvent("voltigore_idle2");
    public static final RegistryObject<SoundEvent> VORTIGORE_IDLE1 =
            registerSoundEvent("voltigore_idle3");
    public static final RegistryObject<SoundEvent> VORTIGORE_PAIN4 =
            registerSoundEvent("voltigore_pain1");
    public static final RegistryObject<SoundEvent> VORTIGORE_PAIN3 =
            registerSoundEvent("voltigore_pain2");
    public static final RegistryObject<SoundEvent> VORTIGORE_PAIN2 =
            registerSoundEvent("voltigore_pain3");
    public static final RegistryObject<SoundEvent> VORTIGORE_PAIN1 =
            registerSoundEvent("voltigore_pain4");
    public static final RegistryObject<SoundEvent> VORTIGORE_ALERT1 =
            registerSoundEvent("voltigore_alert1");
    public static final RegistryObject<SoundEvent> VORTIGORE_ALERT2 =
            registerSoundEvent("voltigore_alert2");
    public static final RegistryObject<SoundEvent> VORTIGORE_ALERT3 =
            registerSoundEvent("voltigore_alert3");







    //Houndeye sounds
    public static final RegistryObject<SoundEvent> HOUNDEYE_ATTACK_1 =
            registerSoundEvent("he_attack1");
    public static final RegistryObject<SoundEvent> HOUNDEYE_ATTACK_2 =
            registerSoundEvent("he_attack2");
    public static final RegistryObject<SoundEvent> HOUNDEYE_ATTACK_3 =
            registerSoundEvent("he_attack3");
    public static final RegistryObject<SoundEvent> SONIC_BLAST_1 =
            registerSoundEvent("he_blast1");
    public static final RegistryObject<SoundEvent> SONIC_BLAST_2 =
            registerSoundEvent("he_blast2");
    public static final RegistryObject<SoundEvent> SONIC_BLAST_3 =
            registerSoundEvent("he_blast3");
    public static final RegistryObject<SoundEvent> HOUNDEYE_DIE_1 =
            registerSoundEvent("he_die1");
    public static final RegistryObject<SoundEvent> HOUNDEYE_DIE_2 =
            registerSoundEvent("he_die2");
    public static final RegistryObject<SoundEvent> HOUNDEYE_DIE_3 =
            registerSoundEvent("he_die3");

    public static final RegistryObject<SoundEvent> HOUNDEYE_BARK =
            registerSoundEvent("he_bark");
    public static final RegistryObject<SoundEvent> HOUNDEYE_STEP1 =
            registerSoundEvent("he_hunt1");
    public static final RegistryObject<SoundEvent> HOUNDEYE_STEP2 =
            registerSoundEvent("he_hunt2");
    public static final RegistryObject<SoundEvent> HOUNDEYE_STEP3 =
            registerSoundEvent("he_hunt3");
    public static final RegistryObject<SoundEvent> HOUNDEYE_PAIN1 =
            registerSoundEvent("he_hunt4");
    public static final RegistryObject<SoundEvent> HOUNDEYE_IDLE1 =
            registerSoundEvent("he_idle1");
    public static final RegistryObject<SoundEvent> HOUNDEYE_IDLE2 =
            registerSoundEvent("he_idle2");
    public static final RegistryObject<SoundEvent> HOUNDEYE_IDLE3 =
            registerSoundEvent("he_idle3");
    public static final RegistryObject<SoundEvent> HOUNDEYE_IDLE4 =
            registerSoundEvent("he_idle4");
    public static final RegistryObject<SoundEvent> HOUNDEYE_PAIN2 =
            registerSoundEvent("he_pain2");
    public static final RegistryObject<SoundEvent> HOUNDEYE_PAIN3 =
            registerSoundEvent("he_pain3");
    public static final RegistryObject<SoundEvent> HOUNDEYE_PAIN4 =
            registerSoundEvent("he_pain4");
    public static final RegistryObject<SoundEvent> HOUNDEYE_PAIN5 =
            registerSoundEvent("he_pain5");
    public static final RegistryObject<SoundEvent> HOUNDEYE_ALERT1 =
            registerSoundEvent("he_alert1");
    public static final RegistryObject<SoundEvent> HOUNDEYE_ALERT2 =
            registerSoundEvent("he_alert2");
    public static final RegistryObject<SoundEvent> HOUNDEYE_ALERT3 =
            registerSoundEvent("he_alert3");









    // BARNACLE SOUNDS

    public static final RegistryObject<SoundEvent> BARNACLE_TONGUE =
            registerSoundEvent("barnacle_tongue");
    public static final RegistryObject<SoundEvent> BARNACLE_PAIN =
            registerSoundEvent("barnacle_pain");
    public static final RegistryObject<SoundEvent> BARNACLE_BITE1 =
            registerSoundEvent("barnacle_attack1");
    public static final RegistryObject<SoundEvent> BARNACLE_BITE2 =
            registerSoundEvent("barnacle_attack2");
    public static final RegistryObject<SoundEvent> BARNACLE_BITE3 =
            registerSoundEvent("barnacle_attack3");
    public static final RegistryObject<SoundEvent> BARNACLE_BITE4 =
            registerSoundEvent("barnacle_attack4");
    public static final RegistryObject<SoundEvent> BARNACLE_DIE1 =
            registerSoundEvent("barnacle_die1");
    public static final RegistryObject<SoundEvent> BARNACLE_DIE2 =
            registerSoundEvent("barnacle_die2");













    //Shockroach item and entity sounds

    public static final RegistryObject<SoundEvent> SHOCKROACH_DIE =
            registerSoundEvent("shock_die");
    public static final RegistryObject<SoundEvent> SHOCKROACH_JUMP_1 =
            registerSoundEvent("shock_jump2");
    public static final RegistryObject<SoundEvent> SHOCKROACH_JUMP_2 =
            registerSoundEvent("shock_jump1");
    public static final RegistryObject<SoundEvent> SHOCKROACH_WALK =
            registerSoundEvent("shock_walk");
    public static final RegistryObject<SoundEvent> SHOCKROACH_BITE =
            registerSoundEvent("shock_bite");
    public static final RegistryObject<SoundEvent> SHOCKROACH_PAIN =
            registerSoundEvent("shock_flinch");
    public static final RegistryObject<SoundEvent> SHOCKROACH_ANGRY =
            registerSoundEvent("shock_angry");
    public static final RegistryObject<SoundEvent> SHOCKROACH_IDLE_1 =
            registerSoundEvent("shock_idle1");
    public static final RegistryObject<SoundEvent> SHOCKROACH_IDLE_2 =
            registerSoundEvent("shock_idle2");
    public static final RegistryObject<SoundEvent> SHOCKROACH_IDLE_3 =
            registerSoundEvent("shock_idle3");
    public static final RegistryObject<SoundEvent> SHOCKROACH_DRAW =
            registerSoundEvent("shock_draw");
    public static final RegistryObject<SoundEvent> SHOCK_FIRE =
            registerSoundEvent("shock_fire");
    public static final RegistryObject<SoundEvent> SHOCK_IMPACT =
            registerSoundEvent("shock_impact");
    public static final RegistryObject<SoundEvent> SHOCKROACH_RECHARGE =
            registerSoundEvent("shock_recharge");



    //headcrab hl1 sounds
    public static final RegistryObject<SoundEvent> HEADCRAB_1_IDLE_1 =
            registerSoundEvent("headcrab_1_idle_1");
    public static final RegistryObject<SoundEvent> HEADCRAB_1_IDLE_2 =
            registerSoundEvent("headcrab_1_idle_2");
    public static final RegistryObject<SoundEvent> HEADCRAB_1_IDLE_3 =
            registerSoundEvent("headcrab_1_idle_3");
    public static final RegistryObject<SoundEvent> HEADCRAB_1_IDLE_4 =
            registerSoundEvent("headcrab_1_idle_4");
    public static final RegistryObject<SoundEvent> HEADCRAB_1_IDLE_5 =
            registerSoundEvent("headcrab_1_idle_5");


    public static final RegistryObject<SoundEvent> HEADCRAB_1_DIE_1 =
            registerSoundEvent("headcrab_1_die_1");
    public static final RegistryObject<SoundEvent> HEADCRAB_1_DIE_2 =
            registerSoundEvent("headcrab_1_die_2");


    public static final RegistryObject<SoundEvent> HEADCRAB_1_PAIN_1 =
            registerSoundEvent("headcrab_1_pain_1");
    public static final RegistryObject<SoundEvent> HEADCRAB_1_PAIN_2 =
            registerSoundEvent("headcrab_1_pain_2");
    public static final RegistryObject<SoundEvent> HEADCRAB_1_PAIN_3 =
            registerSoundEvent("headcrab_1_pain_3");



    public static final RegistryObject<SoundEvent> HEADCRAB_1_ATTACK_1 =
            registerSoundEvent("headcrab_1_attack_1");
    public static final RegistryObject<SoundEvent> HEADCRAB_1_ATTACK_2 =
            registerSoundEvent("headcrab_1_attack_2");
    public static final RegistryObject<SoundEvent> HEADCRAB_1_ATTACK_3 =
            registerSoundEvent("headcrab_1_attack_3");

    public static final RegistryObject<SoundEvent> HEADCRAB_1_ALERT_1 =
            registerSoundEvent("headcrab_1_alert_1");
    public static final RegistryObject<SoundEvent> HEADCRAB_1_ALERT_2 =
            registerSoundEvent("headcrab_1_alert_2");


    public static final RegistryObject<SoundEvent> HEADCRAB_1_HEADBITE =
            registerSoundEvent("headcrab_1_headbite");



    // Headcrab half life 2 sounds
    public static final RegistryObject<SoundEvent> HEADCRAB_2_HEADBITE =
            registerSoundEvent("headcrab_2_headbite");

    public static final RegistryObject<SoundEvent> HEADCRAB_2_BURNING =
            registerSoundEvent("headcrab_2_burning");



    // Bullsquid sounds

    public static final RegistryObject<SoundEvent> BULLSQUID_IDLE_1 =
            registerSoundEvent("bc_idle1");
    public static final RegistryObject<SoundEvent> BULLSQUID_IDLE_2 =
            registerSoundEvent("bc_idle2");
    public static final RegistryObject<SoundEvent> BULLSQUID_IDLE_3 =
            registerSoundEvent("bc_idle3");
    public static final RegistryObject<SoundEvent> BULLSQUID_IDLE_4 =
            registerSoundEvent("bc_idle4");
    public static final RegistryObject<SoundEvent> BULLSQUID_IDLE_5 =
            registerSoundEvent("bc_idle5");

    public static final RegistryObject<SoundEvent> BULLSQUID_PAIN_1 =
            registerSoundEvent("bc_pain1");
    public static final RegistryObject<SoundEvent> BULLSQUID_PAIN_2 =
            registerSoundEvent("bc_pain2");
    public static final RegistryObject<SoundEvent> BULLSQUID_PAIN_3 =
            registerSoundEvent("bc_pain3");
    public static final RegistryObject<SoundEvent> BULLSQUID_PAIN_4 =
            registerSoundEvent("bc_pain4");
    public static final RegistryObject<SoundEvent> BULLSQUID_DIE_1 =
            registerSoundEvent("bc_die1");
    public static final RegistryObject<SoundEvent> BULLSQUID_DIE_2 =
            registerSoundEvent("bc_die2");
    public static final RegistryObject<SoundEvent> BULLSQUID_DIE_3 =
            registerSoundEvent("bc_die3");

    public static final RegistryObject<SoundEvent> BULLSQUID_SHOOT_1 =
            registerSoundEvent("bc_attack1");
    public static final RegistryObject<SoundEvent> BULLSQUID_SHOOT_2 =
            registerSoundEvent("bc_attack2");
    public static final RegistryObject<SoundEvent> BULLSQUID_SHOOT_3 =
            registerSoundEvent("bc_attack3");
    public static final RegistryObject<SoundEvent> BULLSQUID_ATTACK_1 =
            registerSoundEvent("bc_attackgrowl1");
    public static final RegistryObject<SoundEvent> BULLSQUID_ATTACK_2 =
            registerSoundEvent("bc_attackgrowl2");
    public static final RegistryObject<SoundEvent> BULLSQUID_ATTACK_3 =
            registerSoundEvent("bc_attackgrowl3");

    public static final RegistryObject<SoundEvent> BULLSQUID_BITE_1 =
            registerSoundEvent("bc_bite1");
    public static final RegistryObject<SoundEvent> BULLSQUID_BITE_2 =
            registerSoundEvent("bc_bite2");
    public static final RegistryObject<SoundEvent> BULLSQUID_BITE_3 =
            registerSoundEvent("bc_bite3");

    // Bullsquid projectile sounds

    public static final RegistryObject<SoundEvent> BULLSQUID_ACID_1 =
            registerSoundEvent("bc_acid1");
    public static final RegistryObject<SoundEvent> BULLSQUID_ACID_2 =
            registerSoundEvent("bc_acid2");
    public static final RegistryObject<SoundEvent> BULLSQUID_SPIT_1 =
            registerSoundEvent("bc_spithit1");
    public static final RegistryObject<SoundEvent> BULLSQUID_SPIT_2 =
            registerSoundEvent("bc_spithit2");
    public static final RegistryObject<SoundEvent> BULLSQUID_SPIT_3 =
            registerSoundEvent("bc_spithit3");




    //HL_A headcrab sounds
    public static final RegistryObject<SoundEvent> HEADCRAB_A_ALERT_1 = registerSoundEvent("alyx_alert_1");
    public static final RegistryObject<SoundEvent> HEADCRAB_A_ALERT_2 =
    registerSoundEvent("alyx_alert_2");
    public static final RegistryObject<SoundEvent> HEADCRAB_A_BITE_1 =
    registerSoundEvent("alyx_bite_1");
    public static final RegistryObject<SoundEvent> HEADCRAB_A_BITE_2 =
    registerSoundEvent("alyx_bite_2");
    public static final RegistryObject<SoundEvent> HEADCRAB_A_BITE_3 =
    registerSoundEvent("alyx_bite_3");
    public static final RegistryObject<SoundEvent> HEADCRAB_A_BITE_4 =
    registerSoundEvent("alyx_bite_4");
    public static final RegistryObject<SoundEvent> HEADCRAB_A_DIE_1 =
    registerSoundEvent("alyx_death_1");
    public static final RegistryObject<SoundEvent> HEADCRAB_A_DIE_2=
    registerSoundEvent("alyx_death_2");
    public static final RegistryObject<SoundEvent> HEADCRAB_A_DIE_3 =
    registerSoundEvent("alyx_death_3");
    public static final RegistryObject<SoundEvent> HEADCRAB_A_DIE_4 =
    registerSoundEvent("alyx_death_4");
    public static final RegistryObject<SoundEvent> HEADCRAB_A_PAIN_1 =
    registerSoundEvent("alyx_pain_1");
    public static final RegistryObject<SoundEvent> HEADCRAB_A_PAIN_2 =
    registerSoundEvent("alyx_pain_2");
    public static final RegistryObject<SoundEvent> HEADCRAB_A_PAIN_3 =
    registerSoundEvent("alyx_pain_3");
    public static final RegistryObject<SoundEvent> HEADCRAB_A_PAIN_4 =
    registerSoundEvent("alyx_pain_4");
    public static final RegistryObject<SoundEvent> HEADCRAB_A_PAIN_5 =
    registerSoundEvent("alyx_pain_5");
    public static final RegistryObject<SoundEvent> HEADCRAB_A_PAIN_6 =
    registerSoundEvent("alyx_pain_6");
    public static final RegistryObject<SoundEvent> HEADCRAB_A_IDLE_1 =
    registerSoundEvent("idle_chirp_01");
    public static final RegistryObject<SoundEvent> HEADCRAB_A_IDLE_2 =
    registerSoundEvent("idle_chirp_02");
    public static final RegistryObject<SoundEvent> HEADCRAB_A_IDLE_3 =
    registerSoundEvent("idle_chirp_03");
    public static final RegistryObject<SoundEvent> HEADCRAB_A_IDLE_4 =
    registerSoundEvent("idle_chirp_04");
    public static final RegistryObject<SoundEvent> HEADCRAB_A_IDLE_5 =
    registerSoundEvent("idle_chirp_05");
    public static final RegistryObject<SoundEvent> HEADCRAB_A_IDLE_6 =
    registerSoundEvent("idle_chirp_06");
    public static final RegistryObject<SoundEvent> HEADCRAB_A_IDLE_7 =
    registerSoundEvent("idle_chirp_07");
    public static final RegistryObject<SoundEvent> HEADCRAB_A_IDLE_8 =
    registerSoundEvent("idle_chirp_08");
    public static final RegistryObject<SoundEvent> HEADCRAB_A_IDLE_9 =
    registerSoundEvent("idle_chirp_09");
    public static final RegistryObject<SoundEvent> HEADCRAB_A_IDLE_10 =
    registerSoundEvent("idle_chirp_10");
    public static final RegistryObject<SoundEvent> HEADCRAB_A_IDLE_11 =
    registerSoundEvent("idle_chirp_11");
    public static final RegistryObject<SoundEvent> HEADCRAB_A_IDLE_12 =
    registerSoundEvent("idle_chirp_12");
    public static final RegistryObject<SoundEvent> HEADCRAB_A_IDLE_13 =
    registerSoundEvent("idle_chirp_13");
    public static final RegistryObject<SoundEvent> HEADCRAB_A_IDLE_14 =
    registerSoundEvent("idle_chirp_14");
    public static final RegistryObject<SoundEvent> HEADCRAB_A_IDLE_15 =
    registerSoundEvent("idle_chirp_15");
    public static final RegistryObject<SoundEvent> HEADCRAB_A_JUMP =
    registerSoundEvent("alyx_jump");

    // Poison headcrab sounds:

    public static final RegistryObject<SoundEvent> HEADCRAB_POISON_A_ATTACK_GROWL =
    registerSoundEvent("ph_a_attack_growl");
    public static final RegistryObject<SoundEvent> HEADCRAB_POISON_A_ATTACK_SCREECH =
    registerSoundEvent("ph_a_attack_screech");
    public static final RegistryObject<SoundEvent> HEADCRAB_POISON_A_IDLE_1 =
    registerSoundEvent("ph_a_idle1");
    public static final RegistryObject<SoundEvent> HEADCRAB_POISON_A_IDLE_2 =
    registerSoundEvent("ph_a_idle2");
    public static final RegistryObject<SoundEvent> HEADCRAB_POISON_A_IDLE_3 =
    registerSoundEvent("ph_a_idle3");
    public static final RegistryObject<SoundEvent> HEADCRAB_POISON_A_IDLE_4 =
    registerSoundEvent("ph_a_idle4");
    public static final RegistryObject<SoundEvent> HEADCRAB_POISON_A_IDLE_5 =
    registerSoundEvent("ph_a_idle5");
    public static final RegistryObject<SoundEvent> HEADCRAB_POISON_A_IDLE_6 =
    registerSoundEvent("ph_a_idle6");
    public static final RegistryObject<SoundEvent> HEADCRAB_POISON_A_IDLE_7 =
    registerSoundEvent("ph_a_idle7");
    public static final RegistryObject<SoundEvent> HEADCRAB_POISON_A_IDLE_8 =
    registerSoundEvent("ph_a_idle8");
    public static final RegistryObject<SoundEvent> HEADCRAB_POISON_A_IDLE_9 =
    registerSoundEvent("ph_a_idle9");
    public static final RegistryObject<SoundEvent> HEADCRAB_POISON_A_IDLE_10 =
    registerSoundEvent("ph_a_idle10");
    public static final RegistryObject<SoundEvent> HEADCRAB_POISON_A_IDLE_11 =
    registerSoundEvent("ph_a_idle11");
    public static final RegistryObject<SoundEvent> HEADCRAB_POISON_A_IDLE_12 =
    registerSoundEvent("ph_a_idle12");
    public static final RegistryObject<SoundEvent> HEADCRAB_POISON_A_IDLE_13 =
    registerSoundEvent("ph_a_idle13");
    public static final RegistryObject<SoundEvent> HEADCRAB_POISON_A_IDLE_14 =
    registerSoundEvent("ph_a_idle14");
    public static final RegistryObject<SoundEvent> HEADCRAB_POISON_A_IDLE_15 =
    registerSoundEvent("ph_a_idle15");
    public static final RegistryObject<SoundEvent> HEADCRAB_POISON_A_IDLE_16 =
    registerSoundEvent("ph_a_idle16");
    public static final RegistryObject<SoundEvent> HEADCRAB_POISON_A_IDLE_17 =
    registerSoundEvent("ph_a_idle17");
    public static final RegistryObject<SoundEvent> HEADCRAB_POISON_A_DIE_1 =
    registerSoundEvent("ph_a_die1");
    public static final RegistryObject<SoundEvent> HEADCRAB_POISON_A_DIE_2 =
    registerSoundEvent("ph_a_die2");
    public static final RegistryObject<SoundEvent> HEADCRAB_POISON_A_DIE_3 =
    registerSoundEvent("ph_a_die3");
    public static final RegistryObject<SoundEvent> HEADCRAB_POISON_A_DIE_4 =
    registerSoundEvent("ph_a_die4");
    public static final RegistryObject<SoundEvent> HEADCRAB_POISON_A_DIE_5 =
    registerSoundEvent("ph_a_die5");
    public static final RegistryObject<SoundEvent> HEADCRAB_POISON_A_PAIN_1 =
    registerSoundEvent("ph_a_pain1");
    public static final RegistryObject<SoundEvent> HEADCRAB_POISON_A_PAIN_2 =
    registerSoundEvent("ph_a_pain2");
    public static final RegistryObject<SoundEvent> HEADCRAB_POISON_A_PAIN_3 =
    registerSoundEvent("ph_a_pain3");
    public static final RegistryObject<SoundEvent> HEADCRAB_POISON_A_PAIN_4 =
    registerSoundEvent("ph_a_pain4");
    public static final RegistryObject<SoundEvent> HEADCRAB_POISON_A_PAIN_5 =
    registerSoundEvent("ph_a_pain5");
    public static final RegistryObject<SoundEvent> HEADCRAB_POISON_A_PAIN_6 =
    registerSoundEvent("ph_a_pain6");
    public static final RegistryObject<SoundEvent> HEADCRAB_POISON_A_RATTLE_WARNING_1 =
    registerSoundEvent("ph_rattle_warning1");
    public static final RegistryObject<SoundEvent> HEADCRAB_POISON_A_RATTLE_WARNING_2 =
    registerSoundEvent("ph_rattle_warning2");
    public static final RegistryObject<SoundEvent> HEADCRAB_POISON_A_RATTLE_WARNING_3 =
    registerSoundEvent("ph_rattle_warning3");
    public static final RegistryObject<SoundEvent> HEADCRAB_POISON_A_RATTLE_WARNING_4 =
    registerSoundEvent("ph_rattle_warning4");
    public static final RegistryObject<SoundEvent> HEADCRAB_POISON_2_ALERT_4 =
    registerSoundEvent("ph_hiss1");
    public static final RegistryObject<SoundEvent> HEADCRAB_POISON_A_BITE_1 =
            registerSoundEvent("ph_a_bite1");
    public static final RegistryObject<SoundEvent> HEADCRAB_POISON_A_BITE_2 =
            registerSoundEvent("ph_a_bite2");
    public static final RegistryObject<SoundEvent> HEADCRAB_POISON_A_BITE_3 =
            registerSoundEvent("ph_a_bite3");
    public static final RegistryObject<SoundEvent> HEADCRAB_POISON_A_BITE_4 =
            registerSoundEvent("ph_a_bite4");
    public static final RegistryObject<SoundEvent> HEADCRAB_POISON_2_BITE_2 =
            registerSoundEvent("ph_poisonbite2");
    public static final RegistryObject<SoundEvent> HEADCRAB_POISON_2_BITE_3 =
            registerSoundEvent("ph_poisonbite3");
    public static final RegistryObject<SoundEvent> HEADCRAB_POISON_2_BITE_1 =
            registerSoundEvent("ph_poisonbite1");
    public static final RegistryObject<SoundEvent> HEADCRAB_POISON_2_IDLE_1 =
    registerSoundEvent("ph_idle1");
    public static final RegistryObject<SoundEvent> HEADCRAB_POISON_2_IDLE_2 =
    registerSoundEvent("ph_idle2");
    public static final RegistryObject<SoundEvent> HEADCRAB_POISON_2_IDLE_3 =
    registerSoundEvent("ph_idle3");
    public static final RegistryObject<SoundEvent> HEADCRAB_POISON_2_JUMP_1 =
    registerSoundEvent("ph_jump1");
    public static final RegistryObject<SoundEvent> HEADCRAB_POISON_2_JUMP_2 =
    registerSoundEvent("ph_jump2");
    public static final RegistryObject<SoundEvent> HEADCRAB_POISON_2_JUMP_3 =
    registerSoundEvent("ph_jump3");
    public static final RegistryObject<SoundEvent> HEADCRAB_POISON_A_JUMP_1 =
            registerSoundEvent("ph_a_jump1");
    public static final RegistryObject<SoundEvent> HEADCRAB_POISON_A_JUMP_2 =
            registerSoundEvent("ph_a_jump2");
    public static final RegistryObject<SoundEvent> HEADCRAB_POISON_A_JUMP_3 =
            registerSoundEvent("ph_a_jump3");
    public static final RegistryObject<SoundEvent> HEADCRAB_POISON_2_PAIN_1 =
    registerSoundEvent("ph_pain1");
    public static final RegistryObject<SoundEvent> HEADCRAB_POISON_2_PAIN_2 =
    registerSoundEvent("ph_pain2");
    public static final RegistryObject<SoundEvent> HEADCRAB_POISON_2_PAIN_3 =
    registerSoundEvent("ph_pain3");
    public static final RegistryObject<SoundEvent> HEADCRAB_POISON_2_RATTLE_1 =
    registerSoundEvent("ph_rattle1");
    public static final RegistryObject<SoundEvent> HEADCRAB_POISON_2_RATTLE_2 =
    registerSoundEvent("ph_rattle2");
    public static final RegistryObject<SoundEvent> HEADCRAB_POISON_2_RATTLE_3 =
    registerSoundEvent("ph_rattle3");
    public static final RegistryObject<SoundEvent> HEADCRAB_POISON_2_SCREAM_1 =
    registerSoundEvent("ph_scream1");
    public static final RegistryObject<SoundEvent> HEADCRAB_POISON_2_SCREAM_2 =
    registerSoundEvent("ph_scream2");
    public static final RegistryObject<SoundEvent> HEADCRAB_POISON_2_SCREAM_3 =
    registerSoundEvent("ph_scream3");
    public static final RegistryObject<SoundEvent> HEADCRAB_POISON_2_IDLE_4 =
    registerSoundEvent("ph_talk1");
    public static final RegistryObject<SoundEvent> HEADCRAB_POISON_2_IDLE_5 =
    registerSoundEvent("ph_talk2");
    public static final RegistryObject<SoundEvent> HEADCRAB_POISON_2_IDLE_6 =
    registerSoundEvent("ph_talk3");
    public static final RegistryObject<SoundEvent> HEADCRAB_POISON_2_ALERT_1 =
    registerSoundEvent("ph_warning1");
    public static final RegistryObject<SoundEvent> HEADCRAB_POISON_2_ALERT_2 =
    registerSoundEvent("ph_warning2");
    public static final RegistryObject<SoundEvent> HEADCRAB_POISON_2_ALERT_3 =
    registerSoundEvent("ph_warning3");

 // Snark sounds
 public static final RegistryObject<SoundEvent> SNARK_ATTACK =
         registerSoundEvent("sqk_deploy1");
    public static final RegistryObject<SoundEvent> SNARK_BLAST =
            registerSoundEvent("sqk_blast1");
    public static final RegistryObject<SoundEvent> SNARK_DIE =
            registerSoundEvent("sqk_die1");
    public static final RegistryObject<SoundEvent> SNARK_IDLE_1 =
            registerSoundEvent("sqk_hunt1");
    public static final RegistryObject<SoundEvent> SNARK_IDLE_2 =
            registerSoundEvent("sqk_hunt2");
    public static final RegistryObject<SoundEvent> SNARK_IDLE_3 =
            registerSoundEvent("sqk_hunt3");

 // Pistol sounds
 public static final RegistryObject<SoundEvent> PISTOL_RELOAD =
         registerSoundEvent("pistol_reload");
    public static final RegistryObject<SoundEvent> PISTOL_SHOOT =
            registerSoundEvent("pistol_shot");

    public static final RegistryObject<SoundEvent> BULLET_HIT_1 =
            registerSoundEvent("bullet_hit1");
    public static final RegistryObject<SoundEvent> BULLET_HIT_2 =
            registerSoundEvent("bullet_hit2");

    // BARNEY
    public static final RegistryObject<SoundEvent> GUARD_PAIN_1 =
            registerSoundEvent("ba_pain1");
    public static final RegistryObject<SoundEvent> GUARD_PAIN_2 =
            registerSoundEvent("ba_pain2");
    public static final RegistryObject<SoundEvent> GUARD_PAIN_3 =
            registerSoundEvent("ba_pain3");
    public static final RegistryObject<SoundEvent> GUARD_STAY_1 =
            registerSoundEvent("ba_leave1");
    public static final RegistryObject<SoundEvent> GUARD_FOLLOW_1 =
            registerSoundEvent("ba_follow1");
    public static final RegistryObject<SoundEvent> GUARD_STAY_2 =
            registerSoundEvent("ba_leave2");
    public static final RegistryObject<SoundEvent> GUARD_FOLLOW_2 =
            registerSoundEvent("ba_follow2");
    public static final RegistryObject<SoundEvent> GUARD_STAY_3 =
            registerSoundEvent("ba_leave3");
    public static final RegistryObject<SoundEvent> GUARD_FOLLOW_3 =
            registerSoundEvent("ba_follow3");
    public static final RegistryObject<SoundEvent> GUARD_STAY_4 =
            registerSoundEvent("ba_leave4");
    public static final RegistryObject<SoundEvent> GUARD_FOLLOW_4 =
            registerSoundEvent("ba_follow4");
    public static final RegistryObject<SoundEvent> GUARD_STAY_5 =
            registerSoundEvent("ba_leave5");
    public static final RegistryObject<SoundEvent> GUARD_FOLLOW_5 =
            registerSoundEvent("ba_follow5");
    public static final RegistryObject<SoundEvent> GUARD_STAY_6 =
            registerSoundEvent("ba_leave6");
    public static final RegistryObject<SoundEvent> GUARD_FOLLOW_6 =
            registerSoundEvent("ba_follow6");






    private static RegistryObject<SoundEvent> registerSoundEvent(String name) {
        ResourceLocation id = new ResourceLocation(HalfLifeMod.MOD_ID, name);
        return SOUND_EVENTS.register(name, () -> SoundEvent.createVariableRangeEvent(id));
    }

    public static void register(IEventBus eventBus) {
        SOUND_EVENTS.register(eventBus);
    }
}
