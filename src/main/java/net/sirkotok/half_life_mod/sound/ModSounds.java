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


    // SMG

  public static final RegistryObject<SoundEvent> DRYFIRE1=
          registerSoundEvent("dryfire1");
  public static final RegistryObject<SoundEvent> EXPLODE3=
          registerSoundEvent("explode3");
  public static final RegistryObject<SoundEvent> EXPLODE4=
          registerSoundEvent("explode4");
  public static final RegistryObject<SoundEvent> EXPLODE5=
          registerSoundEvent("explode5");
  public static final RegistryObject<SoundEvent> GLAUNCHER=
          registerSoundEvent("glauncher");
  public static final RegistryObject<SoundEvent> GLAUNCHER2=
          registerSoundEvent("glauncher2");
  public static final RegistryObject<SoundEvent> SMG_SHOT1=
          registerSoundEvent("smg_shot1");
  public static final RegistryObject<SoundEvent> SMG_SHOT2=
          registerSoundEvent("smg_shot2");
  public static final RegistryObject<SoundEvent> SMG_SHOT3=
          registerSoundEvent("smg_shot3");


  public static final RegistryObject<SoundEvent> SMG_RELOAD=
          registerSoundEvent("smg_reload");



/*          "sounds.half_life_mod.dryfire1": " ",
          "sounds.half_life_mod.explode3": " ",
          "sounds.half_life_mod.explode4": " ",
          "sounds.half_life_mod.explode5": " ",
          "sounds.half_life_mod.glauncher": " ",
          "sounds.half_life_mod.glauncher2": " ",
          "sounds.half_life_mod.smg_shot1": " ",
          "sounds.half_life_mod.smg_shot2": " ",
          "sounds.half_life_mod.smg_shot3": " ",
          "sounds.half_life_mod.smg_reload": " ",
*/


  // Hunter

  public static final RegistryObject<SoundEvent> BODY_MEDIUM_IMPACT_HARD1=
          registerSoundEvent("body_medium_impact_hard1");
  public static final RegistryObject<SoundEvent> BODY_MEDIUM_IMPACT_HARD2=
          registerSoundEvent("body_medium_impact_hard2");
  public static final RegistryObject<SoundEvent> BODY_MEDIUM_IMPACT_HARD3=
          registerSoundEvent("body_medium_impact_hard3");
  public static final RegistryObject<SoundEvent> BODY_MEDIUM_IMPACT_HARD4=
          registerSoundEvent("body_medium_impact_hard4");
  public static final RegistryObject<SoundEvent> BODY_MEDIUM_IMPACT_HARD5=
          registerSoundEvent("body_medium_impact_hard5");
  public static final RegistryObject<SoundEvent> BODY_MEDIUM_IMPACT_HARD6=
          registerSoundEvent("body_medium_impact_hard6");
  public static final RegistryObject<SoundEvent> FLECHETTELTOR01=
          registerSoundEvent("flechetteltor01");
  public static final RegistryObject<SoundEvent> FLECHETTELTOR02=
          registerSoundEvent("flechetteltor02");
  public static final RegistryObject<SoundEvent> FLECHETTELTOR03=
          registerSoundEvent("flechetteltor03");
  public static final RegistryObject<SoundEvent> FLECHETTELTOR04=
          registerSoundEvent("flechetteltor04");
  public static final RegistryObject<SoundEvent> FLECHETTE_EXPLODE1=
          registerSoundEvent("flechette_explode1");
  public static final RegistryObject<SoundEvent> FLECHETTE_EXPLODE2=
          registerSoundEvent("flechette_explode2");
  public static final RegistryObject<SoundEvent> FLECHETTE_EXPLODE3=
          registerSoundEvent("flechette_explode3");
  public static final RegistryObject<SoundEvent> FLECHETTE_FLESH_IMPACT1=
          registerSoundEvent("flechette_flesh_impact1");
  public static final RegistryObject<SoundEvent> FLECHETTE_FLESH_IMPACT2=
          registerSoundEvent("flechette_flesh_impact2");
  public static final RegistryObject<SoundEvent> FLECHETTE_FLESH_IMPACT3=
          registerSoundEvent("flechette_flesh_impact3");
  public static final RegistryObject<SoundEvent> FLECHETTE_FLESH_IMPACT4=
          registerSoundEvent("flechette_flesh_impact4");
  public static final RegistryObject<SoundEvent> FLECHETTE_IMPACT_STICK1=
          registerSoundEvent("flechette_impact_stick1");
  public static final RegistryObject<SoundEvent> FLECHETTE_IMPACT_STICK2=
          registerSoundEvent("flechette_impact_stick2");
  public static final RegistryObject<SoundEvent> FLECHETTE_IMPACT_STICK3=
          registerSoundEvent("flechette_impact_stick3");
  public static final RegistryObject<SoundEvent> FLECHETTE_IMPACT_STICK4=
          registerSoundEvent("flechette_impact_stick4");
  public static final RegistryObject<SoundEvent> FLECHETTE_IMPACT_STICK5=
          registerSoundEvent("flechette_impact_stick5");
  public static final RegistryObject<SoundEvent> HUNTER_ALERT1=
          registerSoundEvent("hunter_alert1");
  public static final RegistryObject<SoundEvent> HUNTER_ALERT2=
          registerSoundEvent("hunter_alert2");
  public static final RegistryObject<SoundEvent> HUNTER_ALERT3=
          registerSoundEvent("hunter_alert3");
  public static final RegistryObject<SoundEvent> HUNTER_ANGRY1=
          registerSoundEvent("hunter_angry1");
  public static final RegistryObject<SoundEvent> HUNTER_ANGRY2=
          registerSoundEvent("hunter_angry2");
  public static final RegistryObject<SoundEvent> HUNTER_ANGRY3=
          registerSoundEvent("hunter_angry3");
  public static final RegistryObject<SoundEvent> HUNTER_CHARGE3=
          registerSoundEvent("hunter_charge3");
  public static final RegistryObject<SoundEvent> HUNTER_CHARGE4=
          registerSoundEvent("hunter_charge4");
  public static final RegistryObject<SoundEvent> HUNTER_DEFENDSTRIDER1=
          registerSoundEvent("hunter_defendstrider1");
  public static final RegistryObject<SoundEvent> HUNTER_DEFENDSTRIDER2=
          registerSoundEvent("hunter_defendstrider2");
  public static final RegistryObject<SoundEvent> HUNTER_DEFENDSTRIDER3=
          registerSoundEvent("hunter_defendstrider3");
  public static final RegistryObject<SoundEvent> HUNTER_DIE2=
          registerSoundEvent("hunter_die2");
  public static final RegistryObject<SoundEvent> HUNTER_DIE3=
          registerSoundEvent("hunter_die3");
  public static final RegistryObject<SoundEvent> HUNTER_FIRE_LOOP3=
          registerSoundEvent("hunter_fire_loop3");
  public static final RegistryObject<SoundEvent> HUNTER_FLANK_ANNOUNCE1=
          registerSoundEvent("hunter_flank_announce1");
  public static final RegistryObject<SoundEvent> HUNTER_FLANK_ANNOUNCE2=
          registerSoundEvent("hunter_flank_announce2");
  public static final RegistryObject<SoundEvent> HUNTER_FLECHETTE_PREEXPLODE1=
          registerSoundEvent("hunter_flechette_preexplode1");
  public static final RegistryObject<SoundEvent> HUNTER_FLECHETTE_PREEXPLODE2=
          registerSoundEvent("hunter_flechette_preexplode2");
  public static final RegistryObject<SoundEvent> HUNTER_FLECHETTE_PREEXPLODE3=
          registerSoundEvent("hunter_flechette_preexplode3");
  public static final RegistryObject<SoundEvent> HUNTER_FOUNDENEMY1=
          registerSoundEvent("hunter_foundenemy1");
  public static final RegistryObject<SoundEvent> HUNTER_FOUNDENEMY2=
          registerSoundEvent("hunter_foundenemy2");
  public static final RegistryObject<SoundEvent> HUNTER_FOUNDENEMY3=
          registerSoundEvent("hunter_foundenemy3");
  public static final RegistryObject<SoundEvent> HUNTER_FOUNDENEMY_ACK1=
          registerSoundEvent("hunter_foundenemy_ack1");
  public static final RegistryObject<SoundEvent> HUNTER_FOUNDENEMY_ACK2=
          registerSoundEvent("hunter_foundenemy_ack2");
  public static final RegistryObject<SoundEvent> HUNTER_FOUNDENEMY_ACK3=
          registerSoundEvent("hunter_foundenemy_ack3");
  public static final RegistryObject<SoundEvent> HUNTER_IDLE1=
          registerSoundEvent("hunter_idle1");
  public static final RegistryObject<SoundEvent> HUNTER_IDLE2=
          registerSoundEvent("hunter_idle2");
  public static final RegistryObject<SoundEvent> HUNTER_IDLE3=
          registerSoundEvent("hunter_idle3");
  public static final RegistryObject<SoundEvent> HUNTER_LAUGH1=
          registerSoundEvent("hunter_laugh1");
  public static final RegistryObject<SoundEvent> HUNTER_LAUGH2=
          registerSoundEvent("hunter_laugh2");
  public static final RegistryObject<SoundEvent> HUNTER_LAUGH3=
          registerSoundEvent("hunter_laugh3");
  public static final RegistryObject<SoundEvent> HUNTER_LAUGH4=
          registerSoundEvent("hunter_laugh4");
  public static final RegistryObject<SoundEvent> HUNTER_LAUGH5=
          registerSoundEvent("hunter_laugh5");
  public static final RegistryObject<SoundEvent> HUNTER_PAIN2=
          registerSoundEvent("hunter_pain2");
  public static final RegistryObject<SoundEvent> HUNTER_PAIN4=
          registerSoundEvent("hunter_pain4");
  public static final RegistryObject<SoundEvent> HUNTER_PRESTRIKE1=
          registerSoundEvent("hunter_prestrike1");
  public static final RegistryObject<SoundEvent> HUNTER_SCAN1=
          registerSoundEvent("hunter_scan1");
  public static final RegistryObject<SoundEvent> HUNTER_SCAN2=
          registerSoundEvent("hunter_scan2");
  public static final RegistryObject<SoundEvent> HUNTER_SCAN3=
          registerSoundEvent("hunter_scan3");
  public static final RegistryObject<SoundEvent> HUNTER_SCAN4=
          registerSoundEvent("hunter_scan4");
  public static final RegistryObject<SoundEvent> MINISTRIDER_FIRE1=
          registerSoundEvent("ministrider_fire1");
  public static final RegistryObject<SoundEvent> MINISTRIDER_FOOTSTEP1=
          registerSoundEvent("ministrider_footstep1");
  public static final RegistryObject<SoundEvent> MINISTRIDER_FOOTSTEP2=
          registerSoundEvent("ministrider_footstep2");
  public static final RegistryObject<SoundEvent> MINISTRIDER_FOOTSTEP3=
          registerSoundEvent("ministrider_footstep3");
  public static final RegistryObject<SoundEvent> MINISTRIDER_FOOTSTEP4=
          registerSoundEvent("ministrider_footstep4");
  public static final RegistryObject<SoundEvent> MINISTRIDER_FOOTSTEP5=
          registerSoundEvent("ministrider_footstep5");
  public static final RegistryObject<SoundEvent> MINISTRIDER_PREFLECHETTE=
          registerSoundEvent("ministrider_preflechette");
  public static final RegistryObject<SoundEvent> MINISTRIDER_SKEWER1=
          registerSoundEvent("ministrider_skewer1");

    /*      "sounds.half_life_mod.body_medium_impact_hard1": "Hunter ",
          "sounds.half_life_mod.body_medium_impact_hard2": "Hunter ",
          "sounds.half_life_mod.body_medium_impact_hard3": "Hunter ",
          "sounds.half_life_mod.body_medium_impact_hard4": "Hunter ",
          "sounds.half_life_mod.body_medium_impact_hard5": "Hunter ",
          "sounds.half_life_mod.body_medium_impact_hard6": "Hunter ",
          "sounds.half_life_mod.flechetteltor01": "Hunter ",
          "sounds.half_life_mod.flechetteltor02": "Hunter ",
          "sounds.half_life_mod.flechetteltor03": "Hunter ",
          "sounds.half_life_mod.flechetteltor04": "Hunter ",
          "sounds.half_life_mod.flechette_explode1": "Hunter ",
          "sounds.half_life_mod.flechette_explode2": "Hunter ",
          "sounds.half_life_mod.flechette_explode3": "Hunter ",
          "sounds.half_life_mod.flechette_flesh_impact1": "Hunter ",
          "sounds.half_life_mod.flechette_flesh_impact2": "Hunter ",
          "sounds.half_life_mod.flechette_flesh_impact3": "Hunter ",
          "sounds.half_life_mod.flechette_flesh_impact4": "Hunter ",
          "sounds.half_life_mod.flechette_impact_stick1": "Hunter ",
          "sounds.half_life_mod.flechette_impact_stick2": "Hunter ",
          "sounds.half_life_mod.flechette_impact_stick3": "Hunter ",
          "sounds.half_life_mod.flechette_impact_stick4": "Hunter ",
          "sounds.half_life_mod.flechette_impact_stick5": "Hunter ",
          "sounds.half_life_mod.hunter_alert1": "Hunter ",
          "sounds.half_life_mod.hunter_alert2": "Hunter ",
          "sounds.half_life_mod.hunter_alert3": "Hunter ",
          "sounds.half_life_mod.hunter_angry1": "Hunter ",
          "sounds.half_life_mod.hunter_angry2": "Hunter ",
          "sounds.half_life_mod.hunter_angry3": "Hunter ",
          "sounds.half_life_mod.hunter_charge3": "Hunter ",
          "sounds.half_life_mod.hunter_charge4": "Hunter ",
          "sounds.half_life_mod.hunter_defendstrider1": "Hunter ",
          "sounds.half_life_mod.hunter_defendstrider2": "Hunter ",
          "sounds.half_life_mod.hunter_defendstrider3": "Hunter ",
          "sounds.half_life_mod.hunter_die2": "Hunter ",
          "sounds.half_life_mod.hunter_die3": "Hunter ",
          "sounds.half_life_mod.hunter_fire_loop3": "Hunter ",
          "sounds.half_life_mod.hunter_flank_announce1": "Hunter ",
          "sounds.half_life_mod.hunter_flank_announce2": "Hunter ",
          "sounds.half_life_mod.hunter_flechette_preexplode1": "Hunter ",
          "sounds.half_life_mod.hunter_flechette_preexplode2": "Hunter ",
          "sounds.half_life_mod.hunter_flechette_preexplode3": "Hunter ",
          "sounds.half_life_mod.hunter_foundenemy1": "Hunter ",
          "sounds.half_life_mod.hunter_foundenemy2": "Hunter ",
          "sounds.half_life_mod.hunter_foundenemy3": "Hunter ",
          "sounds.half_life_mod.hunter_foundenemy_ack1": "Hunter ",
          "sounds.half_life_mod.hunter_foundenemy_ack2": "Hunter ",
          "sounds.half_life_mod.hunter_foundenemy_ack3": "Hunter ",
          "sounds.half_life_mod.hunter_idle1": "Hunter ",
          "sounds.half_life_mod.hunter_idle2": "Hunter ",
          "sounds.half_life_mod.hunter_idle3": "Hunter ",
          "sounds.half_life_mod.hunter_laugh1": "Hunter ",
          "sounds.half_life_mod.hunter_laugh2": "Hunter ",
          "sounds.half_life_mod.hunter_laugh3": "Hunter ",
          "sounds.half_life_mod.hunter_laugh4": "Hunter ",
          "sounds.half_life_mod.hunter_laugh5": "Hunter ",
          "sounds.half_life_mod.hunter_pain2": "Hunter ",
          "sounds.half_life_mod.hunter_pain4": "Hunter ",
          "sounds.half_life_mod.hunter_prestrike1": "Hunter ",
          "sounds.half_life_mod.hunter_scan1": "Hunter ",
          "sounds.half_life_mod.hunter_scan2": "Hunter ",
          "sounds.half_life_mod.hunter_scan3": "Hunter ",
          "sounds.half_life_mod.hunter_scan4": "Hunter ",
          "sounds.half_life_mod.ministrider_fire1": "Hunter ",
          "sounds.half_life_mod.ministrider_footstep1": "Hunter ",
          "sounds.half_life_mod.ministrider_footstep2": "Hunter ",
          "sounds.half_life_mod.ministrider_footstep3": "Hunter ",
          "sounds.half_life_mod.ministrider_footstep4": "Hunter ",
          "sounds.half_life_mod.ministrider_footstep5": "Hunter ",
          "sounds.half_life_mod.ministrider_preflechette": "Hunter ",
          "sounds.half_life_mod.ministrider_skewer1": "Hunter ", */






    // GONARHC

  public static final RegistryObject<SoundEvent> RIC1=
          registerSoundEvent("ric1");
  public static final RegistryObject<SoundEvent> RIC2=
          registerSoundEvent("ric2");
  public static final RegistryObject<SoundEvent> RIC3=
          registerSoundEvent("ric3");
  public static final RegistryObject<SoundEvent> RIC4=
          registerSoundEvent("ric4");
  public static final RegistryObject<SoundEvent> RIC5=
          registerSoundEvent("ric5");




  public static final RegistryObject<SoundEvent> GON_DIE1=
            registerSoundEvent("gon_die1");
  public static final RegistryObject<SoundEvent> CLAW_STRIKE1=
          registerSoundEvent("claw_strike1");
  public static final RegistryObject<SoundEvent> CLAW_STRIKE2=
          registerSoundEvent("claw_strike2");
  public static final RegistryObject<SoundEvent> CLAW_STRIKE3=
          registerSoundEvent("claw_strike3");
  public static final RegistryObject<SoundEvent> GON_ATTACK1=
          registerSoundEvent("gon_attack1");
  public static final RegistryObject<SoundEvent> GON_ATTACK2=
          registerSoundEvent("gon_attack2");
  public static final RegistryObject<SoundEvent> GON_ATTACK3=
          registerSoundEvent("gon_attack3");
  public static final RegistryObject<SoundEvent> GON_ALERT1=
          registerSoundEvent("gon_alert1");
  public static final RegistryObject<SoundEvent> GON_ALERT2=
          registerSoundEvent("gon_alert2");
  public static final RegistryObject<SoundEvent> GON_ALERT3=
          registerSoundEvent("gon_alert3");
  public static final RegistryObject<SoundEvent> GON_CHILDDIE1=
          registerSoundEvent("gon_childdie1");
  public static final RegistryObject<SoundEvent> GON_CHILDDIE2=
          registerSoundEvent("gon_childdie2");
  public static final RegistryObject<SoundEvent> GON_CHILDDIE3=
          registerSoundEvent("gon_childdie3");
  public static final RegistryObject<SoundEvent> GON_SACK1=
          registerSoundEvent("gon_sack1");
  public static final RegistryObject<SoundEvent> GON_SACK2=
          registerSoundEvent("gon_sack2");
  public static final RegistryObject<SoundEvent> GON_SACK3=
          registerSoundEvent("gon_sack3");
  public static final RegistryObject<SoundEvent> GON_PAIN1=
          registerSoundEvent("gon_pain1");
  public static final RegistryObject<SoundEvent> GON_PAIN2=
          registerSoundEvent("gon_pain2");
  public static final RegistryObject<SoundEvent> GON_PAIN3=
          registerSoundEvent("gon_pain3");
  public static final RegistryObject<SoundEvent> GON_STEP1=
          registerSoundEvent("gon_step1");
  public static final RegistryObject<SoundEvent> GON_STEP2=
          registerSoundEvent("gon_step2");
  public static final RegistryObject<SoundEvent> GON_STEP3=
          registerSoundEvent("gon_step3");
  public static final RegistryObject<SoundEvent> GON_BIRTH1=
          registerSoundEvent("gon_birth1");
  public static final RegistryObject<SoundEvent> GON_BIRTH2=
          registerSoundEvent("gon_birth2");
  public static final RegistryObject<SoundEvent> GON_BIRTH3=
          registerSoundEvent("gon_birth3");







  // LEECH
  public static final RegistryObject<SoundEvent> LEECH_BITE3 =
          registerSoundEvent("leech_bite1");
    public static final RegistryObject<SoundEvent> LEECH_BITE2 =
            registerSoundEvent("leech_bite2");
    public static final RegistryObject<SoundEvent> LEECH_BITE1 =
            registerSoundEvent("leech_bite3");
    public static final RegistryObject<SoundEvent> LEECH_IDLE2 =
            registerSoundEvent("leech_alert1");
    public static final RegistryObject<SoundEvent> LEECH_IDLE1 =
            registerSoundEvent("leech_alert2");




    // ICHTYOSAUR
    public static final RegistryObject<SoundEvent> ICHY_BITE1 =
            registerSoundEvent("ichy_bite1");
  public static final RegistryObject<SoundEvent> ICHY_BITE2 =
          registerSoundEvent("ichy_bite2");
  public static final RegistryObject<SoundEvent> ICHY_ATTACK1 =
          registerSoundEvent("ichy_attack1");
  public static final RegistryObject<SoundEvent> ICHY_ATTACK2 =
          registerSoundEvent("ichy_attack2");
  public static final RegistryObject<SoundEvent> ICHY_ALERT2 =
          registerSoundEvent("ichy_alert1");
  public static final RegistryObject<SoundEvent> ICHY_ALERT1 =
          registerSoundEvent("ichy_alert2");
  public static final RegistryObject<SoundEvent> ICHY_ALERT3 =
          registerSoundEvent("ichy_alert3");
  public static final RegistryObject<SoundEvent> ICHY_DIE1 =
          registerSoundEvent("ichy_die1");
  public static final RegistryObject<SoundEvent> ICHY_DIE2 =
          registerSoundEvent("ichy_die2");
  public static final RegistryObject<SoundEvent> ICHY_DIE3 =
          registerSoundEvent("ichy_die3");
  public static final RegistryObject<SoundEvent> ICHY_DIE4 =
          registerSoundEvent("ichy_die4");
  public static final RegistryObject<SoundEvent> ICHY_IDLE1 =
          registerSoundEvent("ichy_idle1");
  public static final RegistryObject<SoundEvent> ICHY_IDLE2 =
          registerSoundEvent("ichy_idle2");
  public static final RegistryObject<SoundEvent> ICHY_IDLE3 =
          registerSoundEvent("ichy_idle3");
  public static final RegistryObject<SoundEvent> ICHY_IDLE4 =
          registerSoundEvent("ichy_idle4");
  public static final RegistryObject<SoundEvent> ICHY_PAIN1 =
          registerSoundEvent("ichy_pain1");
  public static final RegistryObject<SoundEvent> ICHY_PAIN2 =
          registerSoundEvent("ichy_pain2");
  public static final RegistryObject<SoundEvent> ICHY_PAIN3 =
          registerSoundEvent("ichy_pain3");
  public static final RegistryObject<SoundEvent> ICHY_PAIN4 =
          registerSoundEvent("ichy_pain4");






  //Manhack

    public static final RegistryObject<SoundEvent> MANHACK_DIES =
            registerSoundEvent("mh_die");
    public static final RegistryObject<SoundEvent> MANHACK_LOOP1 =
            registerSoundEvent("mh_loop1");
    public static final RegistryObject<SoundEvent> MANHACK_LOOP2 =
            registerSoundEvent("mh_loop2");
    public static final RegistryObject<SoundEvent> MANHACK_GRIND1 =
            registerSoundEvent("mh_g1");
    public static final RegistryObject<SoundEvent> MANHACK_GRIND2 =
            registerSoundEvent("mh_g2");


    public static final RegistryObject<SoundEvent> COCKROACH_DIES =
            registerSoundEvent("rch_die");
    public static final RegistryObject<SoundEvent> COCKROACH_IS_STOMPED =
            registerSoundEvent("rch_smash");
    public static final RegistryObject<SoundEvent> COCKROACH_WALK =
            registerSoundEvent("rch_walk");





    public static final RegistryObject<SoundEvent> MANHACK_GRIND3 =
            registerSoundEvent("mh_g3");
    public static final RegistryObject<SoundEvent> MANHACK_GRIND4 =
            registerSoundEvent("mh_g4");
    public static final RegistryObject<SoundEvent> MANHACK_GRIND5 =
            registerSoundEvent("mh_g5");
    public static final RegistryObject<SoundEvent> MANHACK_GRIND_FLESH_1 =
            registerSoundEvent("mh_gf1");
    public static final RegistryObject<SoundEvent> MANHACK_GRIND_FLESH_2 =
            registerSoundEvent("mh_gf2");
    public static final RegistryObject<SoundEvent> MANHACK_GRIND_FLESH_3 =
            registerSoundEvent("mh_gf3");
    public static final RegistryObject<SoundEvent> MANHACK_BLADE_SNICK =
            registerSoundEvent("mh_blade_snick1");






    //PitDrone sounds
    public static final RegistryObject<SoundEvent> PITDRONE_METAL_STEP =
            registerSoundEvent("pit_step_metal");
    public static final RegistryObject<SoundEvent> PITDRONE_MELEEATTACK1 =
            registerSoundEvent("pit_drone_melee_attack1");
    public static final RegistryObject<SoundEvent> PITDRONE_MELEEATTACK2 =
            registerSoundEvent("pit_drone_melee_attack2");
    public static final RegistryObject<SoundEvent> PITDRONE_DIE1 =
            registerSoundEvent("pit_drone_die1");
    public static final RegistryObject<SoundEvent> PITDRONE_DIE2 =
            registerSoundEvent("pit_drone_die2");
    public static final RegistryObject<SoundEvent> PITDRONE_DIE3 =
            registerSoundEvent("pit_drone_die3");
    public static final RegistryObject<SoundEvent> PITDRONE_TALK1 =
            registerSoundEvent("pit_drone_communicate1");
    public static final RegistryObject<SoundEvent> PITDRONE_TALK2 =
            registerSoundEvent("pit_drone_communicate2");
    public static final RegistryObject<SoundEvent> PITDRONE_TALK3 =
            registerSoundEvent("pit_drone_communicate3");
    public static final RegistryObject<SoundEvent> PITDRONE_TALK4 =
            registerSoundEvent("pit_drone_communicate4");
    public static final RegistryObject<SoundEvent> PITDRONE_IDLE1 =
            registerSoundEvent("pit_drone_idle1");
    public static final RegistryObject<SoundEvent> PITDRONE_IDLE2 =
            registerSoundEvent("pit_drone_idle2");
    public static final RegistryObject<SoundEvent> PITDRONE_IDLE3 =
            registerSoundEvent("pit_drone_idle3");
    public static final RegistryObject<SoundEvent> PITDRONE_IDLE4 =
            registerSoundEvent("pit_drone_idle4");
    public static final RegistryObject<SoundEvent> PITDRONE_IDLE5 =
            registerSoundEvent("pit_drone_idle5");
    public static final RegistryObject<SoundEvent> PITDRONE_IDLE6 =
            registerSoundEvent("pit_drone_idle6");
    public static final RegistryObject<SoundEvent> PITDRONE_PAIN1 =
            registerSoundEvent("pit_drone_pain1");
    public static final RegistryObject<SoundEvent> PITDRONE_PAIN2 =
            registerSoundEvent("pit_drone_pain2");
    public static final RegistryObject<SoundEvent> PITDRONE_PAIN3 =
            registerSoundEvent("pit_drone_pain3");
    public static final RegistryObject<SoundEvent> PITDRONE_PAIN4 =
            registerSoundEvent("pit_drone_pain4");
    public static final RegistryObject<SoundEvent> PITDRONE_SPIKE1 =
            registerSoundEvent("pit_drone_attack_spike1");
    public static final RegistryObject<SoundEvent> PITDRONE_SPIKE2 =
            registerSoundEvent("pit_drone_attack_spike2");
    public static final RegistryObject<SoundEvent> PITDRONE_SPIKE_HIT =
            registerSoundEvent("pit_hit");


    //Shocktrooper sounds
    public static final RegistryObject<SoundEvent> SHOCKTROOPER_ENEMY_SPOT =
            registerSoundEvent("st_alien_spotted");
    public static final RegistryObject<SoundEvent> SHOCKTROOPER_IDLE_A =
            registerSoundEvent("st_idle_answer");
    public static final RegistryObject<SoundEvent> SHOCKTROOPER_IDLE_CHECK_IN =
            registerSoundEvent("st_idle_check_in");
    public static final RegistryObject<SoundEvent> SHOCKTROOPER_IDLE =
            registerSoundEvent("st_idle");
    public static final RegistryObject<SoundEvent> SHOCKTROOPER_CHARGE =
            registerSoundEvent("st_charging_enemy_position");
    public static final RegistryObject<SoundEvent> SHOCKTROOPER_IDLE_Q =
            registerSoundEvent("st_idle_question");
    public static final RegistryObject<SoundEvent> SHOCKTROOPER_WARN_NADE =
            registerSoundEvent("st_nade_warning");
    public static final RegistryObject<SoundEvent> SHOCKTROOPER_THROW =
            registerSoundEvent("st_throwing");
    public static final RegistryObject<SoundEvent> SHOCKTROOPER_TAUNT =
            registerSoundEvent("st_taunt");
    public static final RegistryObject<SoundEvent> SHOCKTROOPER_PL_SPOT3 =
            registerSoundEvent("st_player_spot_1");
    public static final RegistryObject<SoundEvent> SHOCKTROOPER_PL_SPOT2 =
            registerSoundEvent("st_player_spotted_2");
    public static final RegistryObject<SoundEvent> SHOCKTROOPER_PL_SPOT1 =
            registerSoundEvent("st_player_spotted_3");
    public static final RegistryObject<SoundEvent> SHOCKTROOPER_ATTACK =
            registerSoundEvent("shock_trooper_attack");
    public static final RegistryObject<SoundEvent> SHOCKTROOPER_DIE1 =
            registerSoundEvent("shock_trooper_die1");
    public static final RegistryObject<SoundEvent> SHOCKTROOPER_DIE2 =
            registerSoundEvent("shock_trooper_die2");
    public static final RegistryObject<SoundEvent> SHOCKTROOPER_DIE3 =
            registerSoundEvent("shock_trooper_die3");
    public static final RegistryObject<SoundEvent> SHOCKTROOPER_DIE4 =
            registerSoundEvent("shock_trooper_die4");
    public static final RegistryObject<SoundEvent> SHOCKTROOPER_PAIN4 =
            registerSoundEvent("shock_trooper_pain1");
    public static final RegistryObject<SoundEvent> SHOCKTROOPER_PAIN3 =
            registerSoundEvent("shock_trooper_pain2");
    public static final RegistryObject<SoundEvent> SHOCKTROOPER_PAIN2 =
            registerSoundEvent("shock_trooper_pain3");
    public static final RegistryObject<SoundEvent> SHOCKTROOPER_PAIN5 =
            registerSoundEvent("shock_trooper_pain4");
    public static final RegistryObject<SoundEvent> SHOCKTROOPER_PAIN1 =
            registerSoundEvent("shock_trooper_pain5");
    public static final RegistryObject<SoundEvent> SHOCKTROOPER_IDLE_CLEAR =
            registerSoundEvent("st_idle_clear_response");





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
