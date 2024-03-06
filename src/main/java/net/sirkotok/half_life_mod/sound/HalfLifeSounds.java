package net.sirkotok.half_life_mod.sound;


import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.sirkotok.half_life_mod.HalfLifeMod;



public class HalfLifeSounds {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS =
            DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, HalfLifeMod.MOD_ID);


    // Controller

  public static final RegistryObject<SoundEvent> CON_ALERT1=
          registerSoundEvent("con_alert1");
  public static final RegistryObject<SoundEvent> CON_ALERT2=
          registerSoundEvent("con_alert2");
  public static final RegistryObject<SoundEvent> CON_ALERT3=
          registerSoundEvent("con_alert3");
  public static final RegistryObject<SoundEvent> CON_ATTACK1=
          registerSoundEvent("con_attack1");
  public static final RegistryObject<SoundEvent> CON_ATTACK2=
          registerSoundEvent("con_attack2");
  public static final RegistryObject<SoundEvent> CON_ATTACK3=
          registerSoundEvent("con_attack3");
  public static final RegistryObject<SoundEvent> CON_DIE1=
          registerSoundEvent("con_die1");
  public static final RegistryObject<SoundEvent> CON_DIE2=
          registerSoundEvent("con_die2");
  public static final RegistryObject<SoundEvent> CON_ELECTRO4=
          registerSoundEvent("con_electro4");
  public static final RegistryObject<SoundEvent> CON_IDLE1=
          registerSoundEvent("con_idle1");
  public static final RegistryObject<SoundEvent> CON_IDLE2=
          registerSoundEvent("con_idle2");
  public static final RegistryObject<SoundEvent> CON_IDLE3=
          registerSoundEvent("con_idle3");
  public static final RegistryObject<SoundEvent> CON_IDLE4=
          registerSoundEvent("con_idle4");
  public static final RegistryObject<SoundEvent> CON_IDLE5=
          registerSoundEvent("con_idle5");
  public static final RegistryObject<SoundEvent> CON_PAIN1=
          registerSoundEvent("con_pain1");
  public static final RegistryObject<SoundEvent> CON_PAIN2=
          registerSoundEvent("con_pain2");
  public static final RegistryObject<SoundEvent> CON_PAIN3=
          registerSoundEvent("con_pain3");

    /*      "sounds.half_life_mod.con_alert1": "Controller ",
          "sounds.half_life_mod.con_alert2": "Controller ",
          "sounds.half_life_mod.con_alert3": "Controller ",
          "sounds.half_life_mod.con_attack1": "Controller ",
          "sounds.half_life_mod.con_attack2": "Controller ",
          "sounds.half_life_mod.con_attack3": "Controller ",
          "sounds.half_life_mod.con_die1": "Controller ",
          "sounds.half_life_mod.con_die2": "Controller ",
          "sounds.half_life_mod.con_electro4": "Controller ",
          "sounds.half_life_mod.con_idle1": "Controller ",
          "sounds.half_life_mod.con_idle2": "Controller ",
          "sounds.half_life_mod.con_idle3": "Controller ",
          "sounds.half_life_mod.con_idle4": "Controller ",
          "sounds.half_life_mod.con_idle5": "Controller ",
          "sounds.half_life_mod.con_pain1": "Controller ",
          "sounds.half_life_mod.con_pain2": "Controller ",
          "sounds.half_life_mod.con_pain3": "Controller ", */


    // Antlion


  public static final RegistryObject<SoundEvent> BUGBAIT_IMPACT1=
          registerSoundEvent("bugbait_impact1");
  public static final RegistryObject<SoundEvent> BUGBAIT_IMPACT3=
          registerSoundEvent("bugbait_impact3");
  public static final RegistryObject<SoundEvent> BUGBAIT_SQUEEZE1=
          registerSoundEvent("bugbait_squeeze1");
  public static final RegistryObject<SoundEvent> BUGBAIT_SQUEEZE2=
          registerSoundEvent("bugbait_squeeze2");
  public static final RegistryObject<SoundEvent> BUGBAIT_SQUEEZE3=
          registerSoundEvent("bugbait_squeeze3");

     /*     "sounds.half_life_mod.bugbait_impact1": "Pheropod ",
          "sounds.half_life_mod.bugbait_impact3": "Pheropod ",
          "sounds.half_life_mod.bugbait_squeeze1": "Pheropod ",
          "sounds.half_life_mod.bugbait_squeeze2": "Pheropod ",
          "sounds.half_life_mod.bugbait_squeeze3": "Pheropod ",
          */


  public static final RegistryObject<SoundEvent> ANTLION_ATTACK1=
          registerSoundEvent("antlion_attack1");
  public static final RegistryObject<SoundEvent> ANTLION_ATTACK2=
          registerSoundEvent("antlion_attack2");
  public static final RegistryObject<SoundEvent> ANTLION_ATTACK3=
          registerSoundEvent("antlion_attack3");
  public static final RegistryObject<SoundEvent> ANTLION_ATTACK4=
          registerSoundEvent("antlion_attack4");
  public static final RegistryObject<SoundEvent> ANTLION_ATTACK5=
          registerSoundEvent("antlion_attack5");
  public static final RegistryObject<SoundEvent> ANTLION_ATTACK6=
          registerSoundEvent("antlion_attack6");
  public static final RegistryObject<SoundEvent> ANTLION_BURST1=
          registerSoundEvent("antlion_burst1");
  public static final RegistryObject<SoundEvent> ANTLION_BURST2=
          registerSoundEvent("antlion_burst2");
  public static final RegistryObject<SoundEvent> ANTLION_SHOOT1=
          registerSoundEvent("antlion_shoot1");
  public static final RegistryObject<SoundEvent> ANTLION_SHOOT2=
          registerSoundEvent("antlion_shoot2");
  public static final RegistryObject<SoundEvent> ANTLION_SHOOT3=
          registerSoundEvent("antlion_shoot3");
  public static final RegistryObject<SoundEvent> ANT_DIGDOWN1=
          registerSoundEvent("ant_digdown1");
  public static final RegistryObject<SoundEvent> ANT_DIGUP1=
          registerSoundEvent("ant_digup1");
  public static final RegistryObject<SoundEvent> ANT_DISTRACT1=
          registerSoundEvent("ant_distract1");

  // VORT HL2

  public static final RegistryObject<SoundEvent> VORTIGESE02=
          registerSoundEvent("vortigese02");
  public static final RegistryObject<SoundEvent> VORTIGESE03=
          registerSoundEvent("vortigese03");
  public static final RegistryObject<SoundEvent> VORTIGESE04=
          registerSoundEvent("vortigese04");
  public static final RegistryObject<SoundEvent> VORTIGESE05=
          registerSoundEvent("vortigese05");
  public static final RegistryObject<SoundEvent> VORTIGESE07=
          registerSoundEvent("vortigese07");
  public static final RegistryObject<SoundEvent> VORTIGESE08=
          registerSoundEvent("vortigese08");
  public static final RegistryObject<SoundEvent> VORTIGESE09=
          registerSoundEvent("vortigese09");
  public static final RegistryObject<SoundEvent> VORTIGESE11=
          registerSoundEvent("vortigese11");
  public static final RegistryObject<SoundEvent> VORTIGESE12=
          registerSoundEvent("vortigese12");
  public static final RegistryObject<SoundEvent> VORT_ACCOMPANY1=
          registerSoundEvent("vort_accompany1");
  public static final RegistryObject<SoundEvent> VORT_ACCOMPANY10=
          registerSoundEvent("vort_accompany10");
  public static final RegistryObject<SoundEvent> VORT_ACCOMPANY11=
          registerSoundEvent("vort_accompany11");
  public static final RegistryObject<SoundEvent> VORT_ACCOMPANY2=
          registerSoundEvent("vort_accompany2");
  public static final RegistryObject<SoundEvent> VORT_ACCOMPANY3=
          registerSoundEvent("vort_accompany3");
  public static final RegistryObject<SoundEvent> VORT_ACCOMPANY4=
          registerSoundEvent("vort_accompany4");
  public static final RegistryObject<SoundEvent> VORT_ACCOMPANY5=
          registerSoundEvent("vort_accompany5");
  public static final RegistryObject<SoundEvent> VORT_ACCOMPANY6=
          registerSoundEvent("vort_accompany6");
  public static final RegistryObject<SoundEvent> VORT_ACCOMPANY7=
          registerSoundEvent("vort_accompany7");
  public static final RegistryObject<SoundEvent> VORT_ACCOMPANY8=
          registerSoundEvent("vort_accompany8");
  public static final RegistryObject<SoundEvent> VORT_ACCOMPANY9=
          registerSoundEvent("vort_accompany9");
  public static final RegistryObject<SoundEvent> VORT_REGRET1=
          registerSoundEvent("vort_regret1");
  public static final RegistryObject<SoundEvent> VORT_REGRET2=
          registerSoundEvent("vort_regret2");
  public static final RegistryObject<SoundEvent> VORT_STAY1=
          registerSoundEvent("vort_stay1");
  public static final RegistryObject<SoundEvent> VORT_STAY2=
          registerSoundEvent("vort_stay2");
  public static final RegistryObject<SoundEvent> VORT_STAY3=
          registerSoundEvent("vort_stay3");
  public static final RegistryObject<SoundEvent> VORT_STAY4=
          registerSoundEvent("vort_stay4");
  public static final RegistryObject<SoundEvent> VORT_STAY5=
          registerSoundEvent("vort_stay5");
  public static final RegistryObject<SoundEvent> VORT_STAY6=
          registerSoundEvent("vort_stay6");

  public static final RegistryObject<SoundEvent> VORT2_ATTACK=
          registerSoundEvent("vorthl2attack");
  public static final RegistryObject<SoundEvent> VORT_WANTSTOKILL=
          registerSoundEvent("vort_wantstokill");
  public static final RegistryObject<SoundEvent> VORT_WANTSTOKILL2=
          registerSoundEvent("vort_wantstokill2");
  public static final RegistryObject<SoundEvent> VORT_WANTSTOKILL3=
          registerSoundEvent("vort_wantstokill3");

  /*      "sounds.half_life_mod.vortigese02": "Vortigaunt ",
          "sounds.half_life_mod.vorthl2attack": "Vortigaunt ",
          "sounds.half_life_mod.vortigese03": "Vortigaunt ",
          "sounds.half_life_mod.vortigese04": "Vortigaunt ",
          "sounds.half_life_mod.vortigese05": "Vortigaunt ",
          "sounds.half_life_mod.vortigese07": "Vortigaunt ",
          "sounds.half_life_mod.vortigese08": "Vortigaunt ",
          "sounds.half_life_mod.vortigese09": "Vortigaunt ",
          "sounds.half_life_mod.vortigese11": "Vortigaunt ",
          "sounds.half_life_mod.vortigese12": "Vortigaunt ",
          "sounds.half_life_mod.vort_accompany1": "Vortigaunt ",
          "sounds.half_life_mod.vort_accompany10": "Vortigaunt ",
          "sounds.half_life_mod.vort_accompany11": "Vortigaunt ",
          "sounds.half_life_mod.vort_accompany2": "Vortigaunt ",
          "sounds.half_life_mod.vort_accompany3": "Vortigaunt ",
          "sounds.half_life_mod.vort_accompany4": "Vortigaunt ",
          "sounds.half_life_mod.vort_accompany5": "Vortigaunt ",
          "sounds.half_life_mod.vort_accompany6": "Vortigaunt ",
          "sounds.half_life_mod.vort_accompany7": "Vortigaunt ",
          "sounds.half_life_mod.vort_accompany8": "Vortigaunt ",
          "sounds.half_life_mod.vort_accompany9": "Vortigaunt ",
          "sounds.half_life_mod.vort_regret1": "Vortigaunt ",
          "sounds.half_life_mod.vort_regret2": "Vortigaunt ",
          "sounds.half_life_mod.vort_stay1": "Vortigaunt ",
          "sounds.half_life_mod.vort_stay2": "Vortigaunt ",
          "sounds.half_life_mod.vort_stay3": "Vortigaunt ",
          "sounds.half_life_mod.vort_stay4": "Vortigaunt ",
          "sounds.half_life_mod.vort_stay5": "Vortigaunt ",
          "sounds.half_life_mod.vort_stay6": "Vortigaunt ",
          "sounds.half_life_mod.vort_wantstokill": "Vortigaunt ",
          "sounds.half_life_mod.vort_wantstokill2": "Vortigaunt ",
          "sounds.half_life_mod.vort_wantstokill3": "Vortigaunt ", */



  // VORT hl1
  public static final RegistryObject<SoundEvent> ASLV_ALERT0=
          registerSoundEvent("aslv_alert0");
  public static final RegistryObject<SoundEvent> ASLV_ALERT1=
          registerSoundEvent("aslv_alert1");
  public static final RegistryObject<SoundEvent> ASLV_ALERT2=
          registerSoundEvent("aslv_alert2");
  public static final RegistryObject<SoundEvent> ASLV_ALERT3=
          registerSoundEvent("aslv_alert3");
  public static final RegistryObject<SoundEvent> ASLV_ALERT4=
          registerSoundEvent("aslv_alert4");
  public static final RegistryObject<SoundEvent> ASLV_ALERT5=
          registerSoundEvent("aslv_alert5");
  public static final RegistryObject<SoundEvent> SLV_ARC=
          registerSoundEvent("slv_arc");
  public static final RegistryObject<SoundEvent> SLV_DIE1=
          registerSoundEvent("slv_die1");
  public static final RegistryObject<SoundEvent> SLV_DIE2=
          registerSoundEvent("slv_die2");
  public static final RegistryObject<SoundEvent> SLV_IDLE0=
          registerSoundEvent("slv_idle0");
  public static final RegistryObject<SoundEvent> SLV_IDLE1=
          registerSoundEvent("slv_idle1");
  public static final RegistryObject<SoundEvent> SLV_IDLE10=
          registerSoundEvent("slv_idle10");
  public static final RegistryObject<SoundEvent> SLV_IDLE2=
          registerSoundEvent("slv_idle2");
  public static final RegistryObject<SoundEvent> SLV_IDLE3=
          registerSoundEvent("slv_idle3");
  public static final RegistryObject<SoundEvent> SLV_IDLE4=
          registerSoundEvent("slv_idle4");
  public static final RegistryObject<SoundEvent> SLV_IDLE5=
          registerSoundEvent("slv_idle5");
  public static final RegistryObject<SoundEvent> SLV_IDLE6=
          registerSoundEvent("slv_idle6");
  public static final RegistryObject<SoundEvent> SLV_IDLE7=
          registerSoundEvent("slv_idle7");
  public static final RegistryObject<SoundEvent> SLV_IDLE8=
          registerSoundEvent("slv_idle8");
  public static final RegistryObject<SoundEvent> SLV_IDLE9=
          registerSoundEvent("slv_idle9");
  public static final RegistryObject<SoundEvent> SLV_IMPACT=
          registerSoundEvent("slv_impact");
  public static final RegistryObject<SoundEvent> SLV_PAIN1=
          registerSoundEvent("slv_pain1");
  public static final RegistryObject<SoundEvent> SLV_PAIN2=
          registerSoundEvent("slv_pain2");

   /*       "sounds.half_life_mod.aslv_alert0": "Alien Slave ",
          "sounds.half_life_mod.aslv_alert1": "Alien Slave ",
          "sounds.half_life_mod.aslv_alert2": "Alien Slave ",
          "sounds.half_life_mod.aslv_alert3": "Alien Slave ",
          "sounds.half_life_mod.aslv_alert4": "Alien Slave ",
          "sounds.half_life_mod.aslv_alert5": "Alien Slave ",
          "sounds.half_life_mod.slv_arc": "Alien Slave ",
          "sounds.half_life_mod.slv_die1": "Alien Slave ",
          "sounds.half_life_mod.slv_die2": "Alien Slave ",
          "sounds.half_life_mod.slv_idle0": "Alien Slave ",
          "sounds.half_life_mod.slv_idle1": "Alien Slave ",
          "sounds.half_life_mod.slv_idle10": "Alien Slave ",
          "sounds.half_life_mod.slv_idle2": "Alien Slave ",
          "sounds.half_life_mod.slv_idle3": "Alien Slave ",
          "sounds.half_life_mod.slv_idle4": "Alien Slave ",
          "sounds.half_life_mod.slv_idle5": "Alien Slave ",
          "sounds.half_life_mod.slv_idle6": "Alien Slave ",
          "sounds.half_life_mod.slv_idle7": "Alien Slave ",
          "sounds.half_life_mod.slv_idle8": "Alien Slave ",
          "sounds.half_life_mod.slv_idle9": "Alien Slave ",
          "sounds.half_life_mod.slv_impact": "Alien Slave ",
          "sounds.half_life_mod.slv_pain1": "Alien Slave ",
          "sounds.half_life_mod.slv_pain2": "Alien Slave ", */
  public static final RegistryObject<SoundEvent> ANT_FLY1=
          registerSoundEvent("ant_fly1");
  public static final RegistryObject<SoundEvent> ANT_IDLE1=
          registerSoundEvent("ant_idle1");
  public static final RegistryObject<SoundEvent> ANT_IDLE2=
          registerSoundEvent("ant_idle2");
  public static final RegistryObject<SoundEvent> ANT_IDLE3=
          registerSoundEvent("ant_idle3");
  public static final RegistryObject<SoundEvent> ANT_IDLE4=
          registerSoundEvent("ant_idle4");
  public static final RegistryObject<SoundEvent> ANT_IDLE5=
          registerSoundEvent("ant_idle5");
  public static final RegistryObject<SoundEvent> ANT_PAIN1=
          registerSoundEvent("ant_pain1");
  public static final RegistryObject<SoundEvent> ANT_PAIN2=
          registerSoundEvent("ant_pain2");

          /*
          "sounds.half_life_mod.antlion_attack1": "Antlion ",
          "sounds.half_life_mod.antlion_attack2": "Antlion ",
          "sounds.half_life_mod.antlion_attack3": "Antlion ",
          "sounds.half_life_mod.antlion_attack4": "Antlion ",
          "sounds.half_life_mod.antlion_attack5": "Antlion ",
          "sounds.half_life_mod.antlion_attack6": "Antlion ",
          "sounds.half_life_mod.antlion_burst1": "Antlion ",
          "sounds.half_life_mod.antlion_burst2": "Antlion ",
          "sounds.half_life_mod.antlion_shoot1": "Antlion ",
          "sounds.half_life_mod.antlion_shoot2": "Antlion ",
          "sounds.half_life_mod.antlion_shoot3": "Antlion ",
          "sounds.half_life_mod.ant_digdown1": "Antlion ",
          "sounds.half_life_mod.ant_digup1": "Antlion ",
          "sounds.half_life_mod.ant_distract1": "Antlion ",
          "sounds.half_life_mod.ant_fly1": "Antlion ",
          "sounds.half_life_mod.ant_idle1": "Antlion ",
          "sounds.half_life_mod.ant_idle2": "Antlion ",
          "sounds.half_life_mod.ant_idle3": "Antlion ",
          "sounds.half_life_mod.ant_idle4": "Antlion ",
          "sounds.half_life_mod.ant_idle5": "Antlion ",
          "sounds.half_life_mod.ant_pain1": "Antlion ",
          "sounds.half_life_mod.ant_pain2": "Antlion ",

    */

    //Poison zombie sounds
    public static final RegistryObject<SoundEvent> PZ_ALERT1=
            registerSoundEvent("pz_alert1");
  public static final RegistryObject<SoundEvent> PZ_ALERT2=
          registerSoundEvent("pz_alert2");
  public static final RegistryObject<SoundEvent> PZ_BREATHE_LOOP1=
          registerSoundEvent("pz_breathe_loop1");
  public static final RegistryObject<SoundEvent> PZ_CALL1=
          registerSoundEvent("pz_call1");
  public static final RegistryObject<SoundEvent> PZ_DIE1=
          registerSoundEvent("pz_die1");
  public static final RegistryObject<SoundEvent> PZ_DIE2=
          registerSoundEvent("pz_die2");
  public static final RegistryObject<SoundEvent> PZ_IDLE1=
          registerSoundEvent("pz_idle2");
  public static final RegistryObject<SoundEvent> PZ_IDLE2=
          registerSoundEvent("pz_idle3");
  public static final RegistryObject<SoundEvent> PZ_IDLE3=
          registerSoundEvent("pz_idle4");
  public static final RegistryObject<SoundEvent> PZ_PAIN1=
          registerSoundEvent("pz_pain1");
  public static final RegistryObject<SoundEvent> PZ_PAIN2=
          registerSoundEvent("pz_pain2");
  public static final RegistryObject<SoundEvent> PZ_PAIN3=
          registerSoundEvent("pz_pain3");
  public static final RegistryObject<SoundEvent> PZ_THROW1=
          registerSoundEvent("pz_throw2");
  public static final RegistryObject<SoundEvent> PZ_THROW2=
          registerSoundEvent("pz_throw3");
  public static final RegistryObject<SoundEvent> PZ_WARN1=
          registerSoundEvent("pz_warn1");
  public static final RegistryObject<SoundEvent> PZ_WARN2=
          registerSoundEvent("pz_warn2");

      /*    "sounds.half_life_mod.pz_alert1": "Poison Zombie ",
          "sounds.half_life_mod.pz_alert2": "Poison Zombie ",
          "sounds.half_life_mod.pz_breathe_loop1": "Poison Zombie ",
          "sounds.half_life_mod.pz_call1": "Poison Zombie ",
          "sounds.half_life_mod.pz_die1": "Poison Zombie ",
          "sounds.half_life_mod.pz_die2": "Poison Zombie ",
          "sounds.half_life_mod.pz_idle2": "Poison Zombie ",
          "sounds.half_life_mod.pz_idle3": "Poison Zombie ",
          "sounds.half_life_mod.pz_idle4": "Poison Zombie ",
          "sounds.half_life_mod.pz_pain1": "Poison Zombie ",
          "sounds.half_life_mod.pz_pain2": "Poison Zombie ",
          "sounds.half_life_mod.pz_pain3": "Poison Zombie ",
          "sounds.half_life_mod.pz_throw2": "Poison Zombie ",
          "sounds.half_life_mod.pz_throw3": "Poison Zombie ",
          "sounds.half_life_mod.pz_warn1": "Poison Zombie ",
          "sounds.half_life_mod.pz_warn2": "Poison Zombie ", */





  // Fast zombie sounds

  public static final RegistryObject<SoundEvent> FZ_BREATHE_LOOP1=
          registerSoundEvent("breathe_loop1");
  public static final RegistryObject<SoundEvent> FZ_ALERT_CLOSE1=
          registerSoundEvent("fz_alert_close1");
  public static final RegistryObject<SoundEvent> FZ_ALERT_FAR1=
          registerSoundEvent("fz_alert_far1");
  public static final RegistryObject<SoundEvent> FZ_FRENZY1=
          registerSoundEvent("fz_frenzy1");
  public static final RegistryObject<SoundEvent> FZ_SCREAM1=
          registerSoundEvent("fz_scream1");
  public static final RegistryObject<SoundEvent> FZ_IDLE1=
          registerSoundEvent("idle1");
  public static final RegistryObject<SoundEvent> FZ_IDLE2=
          registerSoundEvent("idle2");
  public static final RegistryObject<SoundEvent> FZ_IDLE3=
          registerSoundEvent("idle3");
  public static final RegistryObject<SoundEvent> FZ_LEAP1=
          registerSoundEvent("leap1");
  public static final RegistryObject<SoundEvent> FZ_WAKE1=
          registerSoundEvent("wake1");

  /*        "sounds.half_life_mod.breathe_loop1": "Fast Zombie ",
          "sounds.half_life_mod.fz_alert_close1": "Fast Zombie ",
          "sounds.half_life_mod.fz_alert_far1": "Fast Zombie ",
          "sounds.half_life_mod.fz_frenzy1": "Fast Zombie ",
          "sounds.half_life_mod.fz_scream1": "Fast Zombie ",
          "sounds.half_life_mod.idle1": "Fast Zombie ",
          "sounds.half_life_mod.idle2": "Fast Zombie ",
          "sounds.half_life_mod.idle3": "Fast Zombie ",
          "sounds.half_life_mod.leap1": "Fast Zombie ",
          "sounds.half_life_mod.wake1": "Fast Zombie ", */








  // Zombie HL2

  public static final RegistryObject<SoundEvent> ZOMBIE_FOOT1=
          registerSoundEvent("foot1");
  public static final RegistryObject<SoundEvent> ZOMBIE_FOOT2=
          registerSoundEvent("foot2");
  public static final RegistryObject<SoundEvent> ZOMBIE_FOOT3=
          registerSoundEvent("foot3");
  public static final RegistryObject<SoundEvent> ZOMBIE_ALERT1=
          registerSoundEvent("zombie_alert1");
  public static final RegistryObject<SoundEvent> ZOMBIE_ALERT2=
          registerSoundEvent("zombie_alert2");
  public static final RegistryObject<SoundEvent> ZOMBIE_ALERT3=
          registerSoundEvent("zombie_alert3");
  public static final RegistryObject<SoundEvent> ZOMBIE_DIE1=
          registerSoundEvent("zombie_die1");
  public static final RegistryObject<SoundEvent> ZOMBIE_DIE2=
          registerSoundEvent("zombie_die2");
  public static final RegistryObject<SoundEvent> ZOMBIE_DIE3=
          registerSoundEvent("zombie_die3");
  public static final RegistryObject<SoundEvent> ZOMBIE_FOOT_SLIDE1=
          registerSoundEvent("zombie_foot_slide1");
  public static final RegistryObject<SoundEvent> ZOMBIE_FOOT_SLIDE2=
          registerSoundEvent("zombie_foot_slide2");
  public static final RegistryObject<SoundEvent> ZOMBIE_FOOT_SLIDE3=
          registerSoundEvent("zombie_foot_slide3");
  public static final RegistryObject<SoundEvent> ZOMBIE_HIT=
          registerSoundEvent("zombie_hit");
  public static final RegistryObject<SoundEvent> ZOMBIE_PAIN1=
          registerSoundEvent("zombie_pain1");
  public static final RegistryObject<SoundEvent> ZOMBIE_PAIN2=
          registerSoundEvent("zombie_pain2");
  public static final RegistryObject<SoundEvent> ZOMBIE_PAIN3=
          registerSoundEvent("zombie_pain3");
  public static final RegistryObject<SoundEvent> ZOMBIE_PAIN4=
          registerSoundEvent("zombie_pain4");
  public static final RegistryObject<SoundEvent> ZOMBIE_PAIN5=
          registerSoundEvent("zombie_pain5");
  public static final RegistryObject<SoundEvent> ZOMBIE_PAIN6=
          registerSoundEvent("zombie_pain6");
  public static final RegistryObject<SoundEvent> ZOMBIE_VOICE_IDLE1=
          registerSoundEvent("zombie_voice_idle1");
  public static final RegistryObject<SoundEvent> ZOMBIE_VOICE_IDLE10=
          registerSoundEvent("zombie_voice_idle10");
  public static final RegistryObject<SoundEvent> ZOMBIE_VOICE_IDLE11=
          registerSoundEvent("zombie_voice_idle11");
  public static final RegistryObject<SoundEvent> ZOMBIE_VOICE_IDLE12=
          registerSoundEvent("zombie_voice_idle12");
  public static final RegistryObject<SoundEvent> ZOMBIE_VOICE_IDLE13=
          registerSoundEvent("zombie_voice_idle13");
  public static final RegistryObject<SoundEvent> ZOMBIE_VOICE_IDLE14=
          registerSoundEvent("zombie_voice_idle14");
  public static final RegistryObject<SoundEvent> ZOMBIE_VOICE_IDLE2=
          registerSoundEvent("zombie_voice_idle2");
  public static final RegistryObject<SoundEvent> ZOMBIE_VOICE_IDLE3=
          registerSoundEvent("zombie_voice_idle3");
  public static final RegistryObject<SoundEvent> ZOMBIE_VOICE_IDLE4=
          registerSoundEvent("zombie_voice_idle4");
  public static final RegistryObject<SoundEvent> ZOMBIE_VOICE_IDLE5=
          registerSoundEvent("zombie_voice_idle5");
  public static final RegistryObject<SoundEvent> ZOMBIE_VOICE_IDLE6=
          registerSoundEvent("zombie_voice_idle6");
  public static final RegistryObject<SoundEvent> ZOMBIE_VOICE_IDLE7=
          registerSoundEvent("zombie_voice_idle7");
  public static final RegistryObject<SoundEvent> ZOMBIE_VOICE_IDLE8=
          registerSoundEvent("zombie_voice_idle8");
  public static final RegistryObject<SoundEvent> ZOMBIE_VOICE_IDLE9=
          registerSoundEvent("zombie_voice_idle9");

         /* "sounds.half_life_mod.foot1": "Zombie ",
          "sounds.half_life_mod.foot2": "Zombie ",
          "sounds.half_life_mod.foot3": "Zombie ",
          "sounds.half_life_mod.zombie_alert1": "Zombie ",
          "sounds.half_life_mod.zombie_alert2": "Zombie ",
          "sounds.half_life_mod.zombie_alert3": "Zombie ",
          "sounds.half_life_mod.zombie_die1": "Zombie ",
          "sounds.half_life_mod.zombie_die2": "Zombie ",
          "sounds.half_life_mod.zombie_die3": "Zombie ",
          "sounds.half_life_mod.zombie_foot_slide1": "Zombie ",
          "sounds.half_life_mod.zombie_foot_slide2": "Zombie ",
          "sounds.half_life_mod.zombie_foot_slide3": "Zombie ",
          "sounds.half_life_mod.zombie_hit": "Zombie ",
          "sounds.half_life_mod.zombie_pain1": "Zombie ",
          "sounds.half_life_mod.zombie_pain2": "Zombie ",
          "sounds.half_life_mod.zombie_pain3": "Zombie ",
          "sounds.half_life_mod.zombie_pain4": "Zombie ",
          "sounds.half_life_mod.zombie_pain5": "Zombie ",
          "sounds.half_life_mod.zombie_pain6": "Zombie ",
          "sounds.half_life_mod.zombie_voice_idle1": "Zombie ",
          "sounds.half_life_mod.zombie_voice_idle10": "Zombie ",
          "sounds.half_life_mod.zombie_voice_idle11": "Zombie ",
          "sounds.half_life_mod.zombie_voice_idle12": "Zombie ",
          "sounds.half_life_mod.zombie_voice_idle13": "Zombie ",
          "sounds.half_life_mod.zombie_voice_idle14": "Zombie ",
          "sounds.half_life_mod.zombie_voice_idle2": "Zombie ",
          "sounds.half_life_mod.zombie_voice_idle3": "Zombie ",
          "sounds.half_life_mod.zombie_voice_idle4": "Zombie ",
          "sounds.half_life_mod.zombie_voice_idle5": "Zombie ",
          "sounds.half_life_mod.zombie_voice_idle6": "Zombie ",
          "sounds.half_life_mod.zombie_voice_idle7": "Zombie ",
          "sounds.half_life_mod.zombie_voice_idle8": "Zombie ",
          "sounds.half_life_mod.zombie_voice_idle9": "Zombie ",
*/



  // Zombie HL1

  public static final RegistryObject<SoundEvent> CLAW_MISS1=
          registerSoundEvent("claw_miss1");
  public static final RegistryObject<SoundEvent> CLAW_MISS2=
          registerSoundEvent("claw_miss2");
  public static final RegistryObject<SoundEvent> ZO_ALERT10=
          registerSoundEvent("zo_alert10");
  public static final RegistryObject<SoundEvent> ZO_ALERT20=
          registerSoundEvent("zo_alert20");
  public static final RegistryObject<SoundEvent> ZO_ALERT30=
          registerSoundEvent("zo_alert30");
  public static final RegistryObject<SoundEvent> ZO_ATTACK1=
          registerSoundEvent("zo_attack1");
  public static final RegistryObject<SoundEvent> ZO_ATTACK2=
          registerSoundEvent("zo_attack2");
  public static final RegistryObject<SoundEvent> ZO_IDLE1=
          registerSoundEvent("zo_idle1");
  public static final RegistryObject<SoundEvent> ZO_IDLE2=
          registerSoundEvent("zo_idle2");
  public static final RegistryObject<SoundEvent> ZO_IDLE3=
          registerSoundEvent("zo_idle3");
  public static final RegistryObject<SoundEvent> ZO_IDLE4=
          registerSoundEvent("zo_idle4");
  public static final RegistryObject<SoundEvent> ZO_PAIN1=
          registerSoundEvent("zo_pain1");
  public static final RegistryObject<SoundEvent> ZO_PAIN2=
          registerSoundEvent("zo_pain2");



   /*       "sounds.half_life_mod.claw_miss1": "Zombie ",
          "sounds.half_life_mod.claw_miss2": "Zombie ",
          "sounds.half_life_mod.zo_alert10": "Zombie ",
          "sounds.half_life_mod.zo_alert20": "Zombie ",
          "sounds.half_life_mod.zo_alert30": "Zombie ",
          "sounds.half_life_mod.zo_attack1": "Zombie ",
          "sounds.half_life_mod.zo_attack2": "Zombie ",
          "sounds.half_life_mod.zo_idle1": "Zombie ",
          "sounds.half_life_mod.zo_idle2": "Zombie ",
          "sounds.half_life_mod.zo_idle3": "Zombie ",
          "sounds.half_life_mod.zo_idle4": "Zombie ",
          "sounds.half_life_mod.zo_pain1": "Zombie ",
          "sounds.half_life_mod.zo_pain2": "Zombie ", */




  // Displacer


  public static final RegistryObject<SoundEvent> DISPLACER_IMPACT=
          registerSoundEvent("displacer_impact");
  public static final RegistryObject<SoundEvent> DISPLACER_SELF=
          registerSoundEvent("displacer_self");
  public static final RegistryObject<SoundEvent> DISPLACER_SPIN=
          registerSoundEvent("displacer_spin");
  public static final RegistryObject<SoundEvent> DISPLACER_SPIN2=
          registerSoundEvent("displacer_spin2");
  public static final RegistryObject<SoundEvent> DISPLACER_START=
          registerSoundEvent("displacer_start");
  public static final RegistryObject<SoundEvent> DISPLACER_TELEPORT=
          registerSoundEvent("displacer_teleport");
  public static final RegistryObject<SoundEvent> DISPLACER_TELEPORT_PLAYER=
          registerSoundEvent("displacer_teleport_player");

      /*    "sounds.half_life_mod.displacer_impact": "Displacer Cannon ",
          "sounds.half_life_mod.displacer_self": "Displacer Cannon ",
          "sounds.half_life_mod.displacer_spin": "Displacer Cannon ",
          "sounds.half_life_mod.displacer_spin2": "Displacer Cannon ",
          "sounds.half_life_mod.displacer_start": "Displacer Cannon ",
          "sounds.half_life_mod.displacer_teleport": "Displacer Cannon ",
          "sounds.half_life_mod.displacer_teleport_player": "Displacer Cannon ", */




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
