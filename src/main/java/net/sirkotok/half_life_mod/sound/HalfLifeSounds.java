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

    // Gonome

  public static final RegistryObject<SoundEvent> GONOME_DEATH2=
          registerSoundEvent("gonome_death2");
  public static final RegistryObject<SoundEvent> GONOME_DEATH3=
          registerSoundEvent("gonome_death3");
  public static final RegistryObject<SoundEvent> GONOME_DEATH4=
          registerSoundEvent("gonome_death4");
  public static final RegistryObject<SoundEvent> GONOME_EAT=
          registerSoundEvent("gonome_eat");
  public static final RegistryObject<SoundEvent> GONOME_IDLE1=
          registerSoundEvent("gonome_idle1");
  public static final RegistryObject<SoundEvent> GONOME_IDLE2=
          registerSoundEvent("gonome_idle2");
  public static final RegistryObject<SoundEvent> GONOME_IDLE3=
          registerSoundEvent("gonome_idle3");
  public static final RegistryObject<SoundEvent> GONOME_JUMPATTACK=
          registerSoundEvent("gonome_jumpattack");
  public static final RegistryObject<SoundEvent> GONOME_MELEE1=
          registerSoundEvent("gonome_melee1");
  public static final RegistryObject<SoundEvent> GONOME_MELEE2=
          registerSoundEvent("gonome_melee2");
  public static final RegistryObject<SoundEvent> GONOME_PAIN1=
          registerSoundEvent("gonome_pain1");
  public static final RegistryObject<SoundEvent> GONOME_PAIN2=
          registerSoundEvent("gonome_pain2");
  public static final RegistryObject<SoundEvent> GONOME_PAIN3=
          registerSoundEvent("gonome_pain3");
  public static final RegistryObject<SoundEvent> GONOME_PAIN4=
          registerSoundEvent("gonome_pain4");
  public static final RegistryObject<SoundEvent> GONOME_RUN=
          registerSoundEvent("gonome_run");

        /*  "sounds.half_life_mod.gonome_death2": "Gonome ",
          "sounds.half_life_mod.gonome_death3": "Gonome ",
          "sounds.half_life_mod.gonome_death4": "Gonome ",
          "sounds.half_life_mod.gonome_eat": "Gonome ",
          "sounds.half_life_mod.gonome_idle1": "Gonome ",
          "sounds.half_life_mod.gonome_idle2": "Gonome ",
          "sounds.half_life_mod.gonome_idle3": "Gonome ",
          "sounds.half_life_mod.gonome_jumpattack": "Gonome ",
          "sounds.half_life_mod.gonome_melee1": "Gonome ",
          "sounds.half_life_mod.gonome_melee2": "Gonome ",
          "sounds.half_life_mod.gonome_pain1": "Gonome ",
          "sounds.half_life_mod.gonome_pain2": "Gonome ",
          "sounds.half_life_mod.gonome_pain3": "Gonome ",
          "sounds.half_life_mod.gonome_pain4": "Gonome ",
          "sounds.half_life_mod.gonome_run": "Gonome ", */














  //SHOTGUN

  public static final RegistryObject<SoundEvent> SGHL1DOUBLE=
          registerSoundEvent("sghl1double");
  public static final RegistryObject<SoundEvent> SGHL1RELOAD=
          registerSoundEvent("sghl1reload");
  public static final RegistryObject<SoundEvent> SGHL1SINGLE=
          registerSoundEvent("sghl1single");
  public static final RegistryObject<SoundEvent> SG_HECU=
          registerSoundEvent("sghl1singlehecu");

  //        "sounds.half_life_mod.sghl1double": "Shotgun ",
   //       "sounds.half_life_mod.sghl1reload": "Shotgun ",
   //       "sounds.half_life_mod.sghl1single": "Shotgun ",
 // "sounds.half_life_mod.sg_single_two": "Shotgun ",

 // M249 and Deagle

  public static final RegistryObject<SoundEvent> DESERT_EAGLE_FIRE=
          registerSoundEvent("desert_eagle_fire");
  public static final RegistryObject<SoundEvent> DESERT_EAGLE_RELOAD=
          registerSoundEvent("desert_eagle_reload");
  public static final RegistryObject<SoundEvent> DESERT_EAGLE_SIGHT=
          registerSoundEvent("desert_eagle_sight");
  public static final RegistryObject<SoundEvent> DESERT_EAGLE_SIGHT2=
          registerSoundEvent("desert_eagle_sight2");
  public static final RegistryObject<SoundEvent> SAW_FIRE1=
          registerSoundEvent("saw_fire1");
  public static final RegistryObject<SoundEvent> SAW_FIRE2=
          registerSoundEvent("saw_fire2");
  public static final RegistryObject<SoundEvent> SAW_FIRE3=
          registerSoundEvent("saw_fire3");
  public static final RegistryObject<SoundEvent> SAW_RELOAD=
          registerSoundEvent("saw_reload");

    /*      "sounds.half_life_mod.desert_eagle_fire": "M249/Deagle ",
          "sounds.half_life_mod.desert_eagle_reload": "M249/Deagle ",
          "sounds.half_life_mod.desert_eagle_sight": "M249/Deagle ",
          "sounds.half_life_mod.desert_eagle_sight2": "M249/Deagle ",
          "sounds.half_life_mod.saw_fire1": "M249/Deagle ",
          "sounds.half_life_mod.saw_fire2": "M249/Deagle ",
          "sounds.half_life_mod.saw_fire3": "M249/Deagle ",
          "sounds.half_life_mod.saw_reload": "M249/Deagle ",
 */



    // GARG
    public static final RegistryObject<SoundEvent> MINE_CHARGE=
            registerSoundEvent("mine_charge");
  public static final RegistryObject<SoundEvent> GAR_ALERT1=
          registerSoundEvent("gar_alert1");
  public static final RegistryObject<SoundEvent> GAR_ALERT2=
          registerSoundEvent("gar_alert2");
  public static final RegistryObject<SoundEvent> GAR_ALERT3=
          registerSoundEvent("gar_alert3");
  public static final RegistryObject<SoundEvent> GAR_ATTACK1=
          registerSoundEvent("gar_attack1");
  public static final RegistryObject<SoundEvent> GAR_ATTACK2=
          registerSoundEvent("gar_attack2");
  public static final RegistryObject<SoundEvent> GAR_ATTACK3=
          registerSoundEvent("gar_attack3");
  public static final RegistryObject<SoundEvent> GAR_BREATHE1=
          registerSoundEvent("gar_breathe1");
  public static final RegistryObject<SoundEvent> GAR_BREATHE2=
          registerSoundEvent("gar_breathe2");
  public static final RegistryObject<SoundEvent> GAR_BREATHE3=
          registerSoundEvent("gar_breathe3");
  public static final RegistryObject<SoundEvent> GAR_DIE1=
          registerSoundEvent("gar_die1");
  public static final RegistryObject<SoundEvent> GAR_DIE2=
          registerSoundEvent("gar_die2");
  public static final RegistryObject<SoundEvent> GAR_FLAMEOFF1=
          registerSoundEvent("gar_flameoff1");
  public static final RegistryObject<SoundEvent> GAR_FLAMEON1=
          registerSoundEvent("gar_flameon1");
  public static final RegistryObject<SoundEvent> GAR_FLAMERUN1=
          registerSoundEvent("gar_flamerun1");
  public static final RegistryObject<SoundEvent> GAR_IDLE1=
          registerSoundEvent("gar_idle1");
  public static final RegistryObject<SoundEvent> GAR_IDLE2=
          registerSoundEvent("gar_idle2");
  public static final RegistryObject<SoundEvent> GAR_IDLE3=
          registerSoundEvent("gar_idle3");
  public static final RegistryObject<SoundEvent> GAR_IDLE4=
          registerSoundEvent("gar_idle4");
  public static final RegistryObject<SoundEvent> GAR_IDLE5=
          registerSoundEvent("gar_idle5");
  public static final RegistryObject<SoundEvent> GAR_PAIN1=
          registerSoundEvent("gar_pain1");
  public static final RegistryObject<SoundEvent> GAR_PAIN2=
          registerSoundEvent("gar_pain2");
  public static final RegistryObject<SoundEvent> GAR_PAIN3=
          registerSoundEvent("gar_pain3");
  public static final RegistryObject<SoundEvent> GAR_STEP1=
          registerSoundEvent("gar_step1");
  public static final RegistryObject<SoundEvent> GAR_STEP2=
          registerSoundEvent("gar_step2");
  public static final RegistryObject<SoundEvent> GAR_STOMP1=
          registerSoundEvent("gar_stomp1");

      /*    "sounds.half_life_mod.gar_alert1": "Gargantua ",
          "sounds.half_life_mod.gar_alert2": "Gargantua ",
          "sounds.half_life_mod.gar_alert3": "Gargantua ",
          "sounds.half_life_mod.gar_attack1": "Gargantua ",
          "sounds.half_life_mod.gar_attack2": "Gargantua ",
          "sounds.half_life_mod.gar_attack3": "Gargantua ",
          "sounds.half_life_mod.gar_breathe1": "Gargantua ",
          "sounds.half_life_mod.gar_breathe2": "Gargantua ",
          "sounds.half_life_mod.gar_breathe3": "Gargantua ",
          "sounds.half_life_mod.gar_die1": "Gargantua ",
          "sounds.half_life_mod.gar_die2": "Gargantua ",
          "sounds.half_life_mod.gar_flameoff1": "Gargantua ",
          "sounds.half_life_mod.gar_flameon1": "Gargantua ",
          "sounds.half_life_mod.gar_flamerun1": "Gargantua ",
          "sounds.half_life_mod.gar_idle1": "Gargantua ",
          "sounds.half_life_mod.gar_idle2": "Gargantua ",
          "sounds.half_life_mod.gar_idle3": "Gargantua ",
          "sounds.half_life_mod.gar_idle4": "Gargantua ",
          "sounds.half_life_mod.gar_idle5": "Gargantua ",
          "sounds.half_life_mod.gar_pain1": "Gargantua ",
          "sounds.half_life_mod.gar_pain2": "Gargantua ",
          "sounds.half_life_mod.gar_pain3": "Gargantua ",
          "sounds.half_life_mod.gar_step1": "Gargantua ",
          "sounds.half_life_mod.gar_step2": "Gargantua ",
          "sounds.half_life_mod.gar_stomp1": "Gargantua ", */




    // Spore

  public static final RegistryObject<SoundEvent> SPLAUNCHER_ALTFIRE=
          registerSoundEvent("splauncher_altfire");
  public static final RegistryObject<SoundEvent> SPLAUNCHER_BOUNCE=
          registerSoundEvent("splauncher_bounce");
  public static final RegistryObject<SoundEvent> SPLAUNCHER_FIRE=
          registerSoundEvent("splauncher_fire");
  public static final RegistryObject<SoundEvent> SPLAUNCHER_IMPACT=
          registerSoundEvent("splauncher_impact");
  public static final RegistryObject<SoundEvent> SPLAUNCHER_PET=
          registerSoundEvent("splauncher_pet");
  public static final RegistryObject<SoundEvent> SPLAUNCHER_RELOAD=
          registerSoundEvent("splauncher_reload");
  public static final RegistryObject<SoundEvent> SPORE_AMMO=
          registerSoundEvent("spore_ammo");
  public static final RegistryObject<SoundEvent> SPORE_HIT1=
          registerSoundEvent("spore_hit1");
  public static final RegistryObject<SoundEvent> SPORE_HIT2=
          registerSoundEvent("spore_hit2");
  public static final RegistryObject<SoundEvent> SPORE_HIT3=
          registerSoundEvent("spore_hit3");

       /*    "sounds.half_life_mod.splauncher_altfire": "Spore ",
          "sounds.half_life_mod.splauncher_bounce": "Spore ",
          "sounds.half_life_mod.splauncher_fire": "Spore ",
          "sounds.half_life_mod.splauncher_impact": "Spore ",
          "sounds.half_life_mod.splauncher_pet": "Spore ",
          "sounds.half_life_mod.splauncher_reload": "Spore ",
          "sounds.half_life_mod.spore_ammo": "Spore ",
          "sounds.half_life_mod.spore_hit1": "Spore ",
          "sounds.half_life_mod.spore_hit2": "Spore ",
          "sounds.half_life_mod.spore_hit3": "Spore ", */



    //
    public static final RegistryObject<SoundEvent> AG_ALERT1=
            registerSoundEvent("ag_alert1");
  public static final RegistryObject<SoundEvent> AG_ALERT2=
          registerSoundEvent("ag_alert2");
  public static final RegistryObject<SoundEvent> AG_ALERT3=
          registerSoundEvent("ag_alert3");
  public static final RegistryObject<SoundEvent> AG_ALERT4=
          registerSoundEvent("ag_alert4");
  public static final RegistryObject<SoundEvent> AG_ALERT5=
          registerSoundEvent("ag_alert5");
  public static final RegistryObject<SoundEvent> AG_ATTACK1=
          registerSoundEvent("ag_attack1");
  public static final RegistryObject<SoundEvent> AG_ATTACK2=
          registerSoundEvent("ag_attack2");
  public static final RegistryObject<SoundEvent> AG_ATTACK3=
          registerSoundEvent("ag_attack3");
  public static final RegistryObject<SoundEvent> AG_DIE1=
          registerSoundEvent("ag_die1");
  public static final RegistryObject<SoundEvent> AG_DIE2=
          registerSoundEvent("ag_die2");
  public static final RegistryObject<SoundEvent> AG_DIE3=
          registerSoundEvent("ag_die3");
  public static final RegistryObject<SoundEvent> AG_DIE4=
          registerSoundEvent("ag_die4");
  public static final RegistryObject<SoundEvent> AG_DIE5=
          registerSoundEvent("ag_die5");
  public static final RegistryObject<SoundEvent> AG_FIRE1=
          registerSoundEvent("ag_fire1");
  public static final RegistryObject<SoundEvent> AG_FIRE2=
          registerSoundEvent("ag_fire2");
  public static final RegistryObject<SoundEvent> AG_FIRE3=
          registerSoundEvent("ag_fire3");
  public static final RegistryObject<SoundEvent> AG_IDLE1=
          registerSoundEvent("ag_idle1");
  public static final RegistryObject<SoundEvent> AG_IDLE2=
          registerSoundEvent("ag_idle2");
  public static final RegistryObject<SoundEvent> AG_IDLE3=
          registerSoundEvent("ag_idle3");
  public static final RegistryObject<SoundEvent> AG_IDLE4=
          registerSoundEvent("ag_idle4");
  public static final RegistryObject<SoundEvent> AG_IDLE5=
          registerSoundEvent("ag_idle5");
  public static final RegistryObject<SoundEvent> AG_PAIN1=
          registerSoundEvent("ag_pain1");
  public static final RegistryObject<SoundEvent> AG_PAIN2=
          registerSoundEvent("ag_pain2");
  public static final RegistryObject<SoundEvent> AG_PAIN3=
          registerSoundEvent("ag_pain3");
  public static final RegistryObject<SoundEvent> AG_PAIN4=
          registerSoundEvent("ag_pain4");
  public static final RegistryObject<SoundEvent> AG_PAIN5=
          registerSoundEvent("ag_pain5");

  public static final RegistryObject<SoundEvent> AG_STEP1=
          registerSoundEvent("ag_step1");
  public static final RegistryObject<SoundEvent> AG_STEP2=
          registerSoundEvent("ag_step2");
  public static final RegistryObject<SoundEvent> AG_STEP3=
          registerSoundEvent("ag_step3");
  public static final RegistryObject<SoundEvent> AG_STEP4=
          registerSoundEvent("ag_step4");
  public static final RegistryObject<SoundEvent> AG_BUZZ1=
          registerSoundEvent("ag_buzz1");
  public static final RegistryObject<SoundEvent> AG_BUZZ2=
          registerSoundEvent("ag_buzz2");
  public static final RegistryObject<SoundEvent> AG_BUZZ3=
          registerSoundEvent("ag_buzz3");
  public static final RegistryObject<SoundEvent> AG_HORNETHIT1=
          registerSoundEvent("ag_hornethit1");
  public static final RegistryObject<SoundEvent> AG_HORNETHIT2=
          registerSoundEvent("ag_hornethit2");
  public static final RegistryObject<SoundEvent> AG_HORNETHIT3=
          registerSoundEvent("ag_hornethit3");


       /*  "sounds.half_life_mod.ag_buzz1": "Hornet ",
          "sounds.half_life_mod.ag_buzz2": "Hornet ",
          "sounds.half_life_mod.ag_buzz3": "Hornet ",
          "sounds.half_life_mod.ag_hornethit1": "Hornet ",
          "sounds.half_life_mod.ag_hornethit2": "Hornet ",
          "sounds.half_life_mod.ag_hornethit3": "Hornet ",

          "sounds.half_life_mod.ag_step1": "Alien Grunt ",
          "sounds.half_life_mod.ag_step2": "Alien Grunt ",
          "sounds.half_life_mod.ag_step3": "Alien Grunt ",
          "sounds.half_life_mod.ag_step4": "Alien Grunt ",

         "sounds.half_life_mod.ag_alert1": "Alien Grunt ",
          "sounds.half_life_mod.ag_alert2": "Alien Grunt ",
          "sounds.half_life_mod.ag_alert3": "Alien Grunt ",
          "sounds.half_life_mod.ag_alert4": "Alien Grunt ",
          "sounds.half_life_mod.ag_alert5": "Alien Grunt ",
          "sounds.half_life_mod.ag_attack1": "Alien Grunt ",
          "sounds.half_life_mod.ag_attack2": "Alien Grunt ",
          "sounds.half_life_mod.ag_attack3": "Alien Grunt ",
          "sounds.half_life_mod.ag_die1": "Alien Grunt ",
          "sounds.half_life_mod.ag_die2": "Alien Grunt ",
          "sounds.half_life_mod.ag_die3": "Alien Grunt ",
          "sounds.half_life_mod.ag_die4": "Alien Grunt ",
          "sounds.half_life_mod.ag_die5": "Alien Grunt ",
          "sounds.half_life_mod.ag_fire1": "Alien Grunt ",
          "sounds.half_life_mod.ag_fire2": "Alien Grunt ",
          "sounds.half_life_mod.ag_fire3": "Alien Grunt ",
          "sounds.half_life_mod.ag_idle1": "Alien Grunt ",
          "sounds.half_life_mod.ag_idle2": "Alien Grunt ",
          "sounds.half_life_mod.ag_idle3": "Alien Grunt ",
          "sounds.half_life_mod.ag_idle4": "Alien Grunt ",
          "sounds.half_life_mod.ag_idle5": "Alien Grunt ",
          "sounds.half_life_mod.ag_pain1": "Alien Grunt ",
          "sounds.half_life_mod.ag_pain2": "Alien Grunt ",
          "sounds.half_life_mod.ag_pain3": "Alien Grunt ",
          "sounds.half_life_mod.ag_pain4": "Alien Grunt ",
          "sounds.half_life_mod.ag_pain5": "Alien Grunt ", */


  // Gravity Gun
    public static final RegistryObject<SoundEvent> PHYSCANNON_CHARGE=
            registerSoundEvent("physcannon_charge");
  public static final RegistryObject<SoundEvent> PHYSCANNON_CLAWS_CLOSE=
          registerSoundEvent("physcannon_claws_close");
  public static final RegistryObject<SoundEvent> PHYSCANNON_CLAWS_OPEN=
          registerSoundEvent("physcannon_claws_open");
  public static final RegistryObject<SoundEvent> PHYSCANNON_DROP=
          registerSoundEvent("physcannon_drop");
  public static final RegistryObject<SoundEvent> PHYSCANNON_DRYFIRE=
          registerSoundEvent("physcannon_dryfire");
  public static final RegistryObject<SoundEvent> PHYSCANNON_HOLD_LOOP=
          registerSoundEvent("physcannon_hold_loop");
  public static final RegistryObject<SoundEvent> PHYSCANNON_PICKUP=
          registerSoundEvent("physcannon_pickup");
  public static final RegistryObject<SoundEvent> PHYSCANNON_TOOHEAVY=
          registerSoundEvent("physcannon_tooheavy");
  public static final RegistryObject<SoundEvent> SUPERPHYS_HOLD_LOOP=
          registerSoundEvent("superphys_hold_loop");
  public static final RegistryObject<SoundEvent> SUPERPHYS_LAUNCH1=
          registerSoundEvent("superphys_launch1");
  public static final RegistryObject<SoundEvent> SUPERPHYS_LAUNCH2=
          registerSoundEvent("superphys_launch2");
  public static final RegistryObject<SoundEvent> SUPERPHYS_LAUNCH3=
          registerSoundEvent("superphys_launch3");
  public static final RegistryObject<SoundEvent> SUPERPHYS_LAUNCH4=
          registerSoundEvent("superphys_launch4");
  public static final RegistryObject<SoundEvent> SUPERPHYS_SMALL_ZAP1=
          registerSoundEvent("superphys_small_zap1");
  public static final RegistryObject<SoundEvent> SUPERPHYS_SMALL_ZAP2=
          registerSoundEvent("superphys_small_zap2");
  public static final RegistryObject<SoundEvent> SUPERPHYS_SMALL_ZAP3=
          registerSoundEvent("superphys_small_zap3");
  public static final RegistryObject<SoundEvent> SUPERPHYS_SMALL_ZAP4=
          registerSoundEvent("superphys_small_zap4");

      /*    "sounds.half_life_mod.physcannon_charge": "Gravity Gun ",
          "sounds.half_life_mod.physcannon_claws_close": "Gravity Gun ",
          "sounds.half_life_mod.physcannon_claws_open": "Gravity Gun ",
          "sounds.half_life_mod.physcannon_drop": "Gravity Gun ",
          "sounds.half_life_mod.physcannon_dryfire": "Gravity Gun ",
          "sounds.half_life_mod.physcannon_hold_loop": "Gravity Gun ",
          "sounds.half_life_mod.physcannon_pickup": "Gravity Gun ",
          "sounds.half_life_mod.physcannon_tooheavy": "Gravity Gun ",
          "sounds.half_life_mod.superphys_hold_loop": "Gravity Gun ",
          "sounds.half_life_mod.superphys_launch1": "Gravity Gun ",
          "sounds.half_life_mod.superphys_launch2": "Gravity Gun ",
          "sounds.half_life_mod.superphys_launch3": "Gravity Gun ",
          "sounds.half_life_mod.superphys_launch4": "Gravity Gun ",
          "sounds.half_life_mod.superphys_small_zap1": "Gravity Gun ",
          "sounds.half_life_mod.superphys_small_zap2": "Gravity Gun ",
          "sounds.half_life_mod.superphys_small_zap3": "Gravity Gun ",
          "sounds.half_life_mod.superphys_small_zap4": "Gravity Gun ", */



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

    // NEUTRAL
    public static final RegistryObject<SoundEvent> GUARD_PAIN_1 =
            registerSoundEvent("ba_pain1");
    public static final RegistryObject<SoundEvent> GUARD_PAIN_2 =
            registerSoundEvent("ba_pain2");
    public static final RegistryObject<SoundEvent> GUARD_PAIN_3 =
            registerSoundEvent("ba_pain3");


  public static final RegistryObject<SoundEvent> BA_AP1=
          registerSoundEvent("ba_ap1");
  public static final RegistryObject<SoundEvent> BA_AP2=
          registerSoundEvent("ba_ap2");
  public static final RegistryObject<SoundEvent> BA_AP3=
          registerSoundEvent("ba_ap3");
  public static final RegistryObject<SoundEvent> BA_AP4=
          registerSoundEvent("ba_ap4");
  public static final RegistryObject<SoundEvent> BA_DIE1=
          registerSoundEvent("ba_die1");
  public static final RegistryObject<SoundEvent> BA_DIE2=
          registerSoundEvent("ba_die2");
  public static final RegistryObject<SoundEvent> BA_DIE3=
          registerSoundEvent("ba_die3");
  public static final RegistryObject<SoundEvent> BA_FOLLOW1=
          registerSoundEvent("ba_follow1");
  public static final RegistryObject<SoundEvent> BA_FOLLOW2=
          registerSoundEvent("ba_follow2");
  public static final RegistryObject<SoundEvent> BA_FOLLOW3=
          registerSoundEvent("ba_follow3");
  public static final RegistryObject<SoundEvent> BA_FOLLOW4=
          registerSoundEvent("ba_follow4");
  public static final RegistryObject<SoundEvent> BA_FOLLOW5=
          registerSoundEvent("ba_follow5");
  public static final RegistryObject<SoundEvent> BA_FOLLOW6=
          registerSoundEvent("ba_follow6");
  public static final RegistryObject<SoundEvent> BA_FOLLOW7=
          registerSoundEvent("ba_follow7");
  public static final RegistryObject<SoundEvent> BA_IDLE_A1=
          registerSoundEvent("ba_idle_a1");
  public static final RegistryObject<SoundEvent> BA_IDLE_A2=
          registerSoundEvent("ba_idle_a2");
  public static final RegistryObject<SoundEvent> BA_IDLE_A3=
          registerSoundEvent("ba_idle_a3");
  public static final RegistryObject<SoundEvent> BA_IDLE_A4=
          registerSoundEvent("ba_idle_a4");
  public static final RegistryObject<SoundEvent> BA_IDLE_A5=
          registerSoundEvent("ba_idle_a5");
  public static final RegistryObject<SoundEvent> BA_IDLE_A6=
          registerSoundEvent("ba_idle_a6");
  public static final RegistryObject<SoundEvent> BA_IDLE_A7=
          registerSoundEvent("ba_idle_a7");
  public static final RegistryObject<SoundEvent> BA_IDLE_C1=
          registerSoundEvent("ba_idle_c1");
  public static final RegistryObject<SoundEvent> BA_IDLE_C2=
          registerSoundEvent("ba_idle_c2");
  public static final RegistryObject<SoundEvent> BA_IDLE_C3=
          registerSoundEvent("ba_idle_c3");
  public static final RegistryObject<SoundEvent> BA_IDLE_C4=
          registerSoundEvent("ba_idle_c4");
  public static final RegistryObject<SoundEvent> BA_IDLE_C5=
          registerSoundEvent("ba_idle_c5");
  public static final RegistryObject<SoundEvent> BA_IDLE_C6=
          registerSoundEvent("ba_idle_c6");
  public static final RegistryObject<SoundEvent> BA_IDLE_C7=
          registerSoundEvent("ba_idle_c7");
  public static final RegistryObject<SoundEvent> BA_IDLE_C8=
          registerSoundEvent("ba_idle_c8");
  public static final RegistryObject<SoundEvent> BA_IDLE_HURT1=
          registerSoundEvent("ba_idle_hurt1");
  public static final RegistryObject<SoundEvent> BA_IDLE_HURT2=
          registerSoundEvent("ba_idle_hurt2");
  public static final RegistryObject<SoundEvent> BA_IDLE_PL_HURT1=
          registerSoundEvent("ba_idle_pl_hurt1");
  public static final RegistryObject<SoundEvent> BA_IDLE_PL_HURT2=
          registerSoundEvent("ba_idle_pl_hurt2");
  public static final RegistryObject<SoundEvent> BA_IDLE_Q1=
          registerSoundEvent("ba_idle_q1");
  public static final RegistryObject<SoundEvent> BA_IDLE_Q2=
          registerSoundEvent("ba_idle_q2");
  public static final RegistryObject<SoundEvent> BA_IDLE_Q3=
          registerSoundEvent("ba_idle_q3");
  public static final RegistryObject<SoundEvent> BA_IDLE_Q4=
          registerSoundEvent("ba_idle_q4");
  public static final RegistryObject<SoundEvent> BA_IDLE_Q5=
          registerSoundEvent("ba_idle_q5");
  public static final RegistryObject<SoundEvent> BA_IDLE_Q6=
          registerSoundEvent("ba_idle_q6");
  public static final RegistryObject<SoundEvent> BA_IDLE_Q7=
          registerSoundEvent("ba_idle_q7");
  public static final RegistryObject<SoundEvent> BA_IDLE_Q8=
          registerSoundEvent("ba_idle_q8");
  public static final RegistryObject<SoundEvent> BA_KILL1=
          registerSoundEvent("ba_kill1");
  public static final RegistryObject<SoundEvent> BA_KILL2=
          registerSoundEvent("ba_kill2");
  public static final RegistryObject<SoundEvent> BA_KILL3=
          registerSoundEvent("ba_kill3");
  public static final RegistryObject<SoundEvent> BA_KILL4=
          registerSoundEvent("ba_kill4");
  public static final RegistryObject<SoundEvent> BA_KILL5=
          registerSoundEvent("ba_kill5");
  public static final RegistryObject<SoundEvent> BA_KILL6=
          registerSoundEvent("ba_kill6");
  public static final RegistryObject<SoundEvent> BA_STAY1=
          registerSoundEvent("ba_stay1");
  public static final RegistryObject<SoundEvent> BA_STAY2=
          registerSoundEvent("ba_stay2");
  public static final RegistryObject<SoundEvent> BA_STAY3=
          registerSoundEvent("ba_stay3");
  public static final RegistryObject<SoundEvent> BA_STAY4=
          registerSoundEvent("ba_stay4");
  public static final RegistryObject<SoundEvent> BA_STAY5=
          registerSoundEvent("ba_stay5");
  public static final RegistryObject<SoundEvent> BA_STAY6=
          registerSoundEvent("ba_stay6");
  public static final RegistryObject<SoundEvent> BA_STAY7=
          registerSoundEvent("ba_stay7");
  public static final RegistryObject<SoundEvent> BA_WARN1=
          registerSoundEvent("ba_warn1");
  public static final RegistryObject<SoundEvent> BA_WARN2=
          registerSoundEvent("ba_warn2");
  public static final RegistryObject<SoundEvent> BA_WARN3=
          registerSoundEvent("ba_warn3");
  public static final RegistryObject<SoundEvent> BA_WARN4=
          registerSoundEvent("ba_warn4");
  public static final RegistryObject<SoundEvent> BA_WARN6=
          registerSoundEvent("ba_warn6");
  public static final RegistryObject<SoundEvent> SCIENTIST_HEADCRABONHEAD=
          registerSoundEvent("scientist_headcrabonhead");
  public static final RegistryObject<SoundEvent> SCI_DIE1=
          registerSoundEvent("sci_die1");
  public static final RegistryObject<SoundEvent> SCI_DIE2=
          registerSoundEvent("sci_die2");
  public static final RegistryObject<SoundEvent> SCI_DIE3=
          registerSoundEvent("sci_die3");
  public static final RegistryObject<SoundEvent> SCI_DIE4=
          registerSoundEvent("sci_die4");
  public static final RegistryObject<SoundEvent> SCI_DIE5=
          registerSoundEvent("sci_die5");
  public static final RegistryObject<SoundEvent> SCI_PAIN1=
          registerSoundEvent("sci_pain1");
  public static final RegistryObject<SoundEvent> SCI_PAIN10=
          registerSoundEvent("sci_pain10");
  public static final RegistryObject<SoundEvent> SCI_PAIN2=
          registerSoundEvent("sci_pain2");
  public static final RegistryObject<SoundEvent> SCI_PAIN3=
          registerSoundEvent("sci_pain3");
  public static final RegistryObject<SoundEvent> SCI_PAIN4=
          registerSoundEvent("sci_pain4");
  public static final RegistryObject<SoundEvent> SCI_PAIN5=
          registerSoundEvent("sci_pain5");
  public static final RegistryObject<SoundEvent> SCI_PAIN6=
          registerSoundEvent("sci_pain6");
  public static final RegistryObject<SoundEvent> SCI_PAIN7=
          registerSoundEvent("sci_pain7");
  public static final RegistryObject<SoundEvent> SCI_PAIN8=
          registerSoundEvent("sci_pain8");
  public static final RegistryObject<SoundEvent> SCI_PAIN9=
          registerSoundEvent("sci_pain9");
  public static final RegistryObject<SoundEvent> S_AN1=
          registerSoundEvent("s_an1");
  public static final RegistryObject<SoundEvent> S_AN10=
          registerSoundEvent("s_an10");
  public static final RegistryObject<SoundEvent> S_AN11=
          registerSoundEvent("s_an11");
  public static final RegistryObject<SoundEvent> S_AN12=
          registerSoundEvent("s_an12");
  public static final RegistryObject<SoundEvent> S_AN13=
          registerSoundEvent("s_an13");
  public static final RegistryObject<SoundEvent> S_AN14=
          registerSoundEvent("s_an14");
  public static final RegistryObject<SoundEvent> S_AN15=
          registerSoundEvent("s_an15");
  public static final RegistryObject<SoundEvent> S_AN16=
          registerSoundEvent("s_an16");
  public static final RegistryObject<SoundEvent> S_AN17=
          registerSoundEvent("s_an17");
  public static final RegistryObject<SoundEvent> S_AN18=
          registerSoundEvent("s_an18");
  public static final RegistryObject<SoundEvent> S_AN19=
          registerSoundEvent("s_an19");
  public static final RegistryObject<SoundEvent> S_AN2=
          registerSoundEvent("s_an2");
  public static final RegistryObject<SoundEvent> S_AN20=
          registerSoundEvent("s_an20");
  public static final RegistryObject<SoundEvent> S_AN21=
          registerSoundEvent("s_an21");
  public static final RegistryObject<SoundEvent> S_AN3=
          registerSoundEvent("s_an3");
  public static final RegistryObject<SoundEvent> S_AN4=
          registerSoundEvent("s_an4");
  public static final RegistryObject<SoundEvent> S_AN5=
          registerSoundEvent("s_an5");
  public static final RegistryObject<SoundEvent> S_AN6=
          registerSoundEvent("s_an6");
  public static final RegistryObject<SoundEvent> S_AN7=
          registerSoundEvent("s_an7");
  public static final RegistryObject<SoundEvent> S_AN8=
          registerSoundEvent("s_an8");
  public static final RegistryObject<SoundEvent> S_AN9=
          registerSoundEvent("s_an9");
  public static final RegistryObject<SoundEvent> S_AP1=
          registerSoundEvent("s_ap1");
  public static final RegistryObject<SoundEvent> S_AP2=
          registerSoundEvent("s_ap2");
  public static final RegistryObject<SoundEvent> S_C1=
          registerSoundEvent("s_c1");
  public static final RegistryObject<SoundEvent> S_C10=
          registerSoundEvent("s_c10");
  public static final RegistryObject<SoundEvent> S_C11=
          registerSoundEvent("s_c11");
  public static final RegistryObject<SoundEvent> S_C12=
          registerSoundEvent("s_c12");
  public static final RegistryObject<SoundEvent> S_C13=
          registerSoundEvent("s_c13");
  public static final RegistryObject<SoundEvent> S_C14=
          registerSoundEvent("s_c14");
  public static final RegistryObject<SoundEvent> S_C2=
          registerSoundEvent("s_c2");
  public static final RegistryObject<SoundEvent> S_C3=
          registerSoundEvent("s_c3");
  public static final RegistryObject<SoundEvent> S_C4=
          registerSoundEvent("s_c4");
  public static final RegistryObject<SoundEvent> S_C5=
          registerSoundEvent("s_c5");
  public static final RegistryObject<SoundEvent> S_C6=
          registerSoundEvent("s_c6");
  public static final RegistryObject<SoundEvent> S_C7=
          registerSoundEvent("s_c7");
  public static final RegistryObject<SoundEvent> S_C8=
          registerSoundEvent("s_c8");
  public static final RegistryObject<SoundEvent> S_C9=
          registerSoundEvent("s_c9");
  public static final RegistryObject<SoundEvent> S_FOLLOW1=
          registerSoundEvent("s_follow1");
  public static final RegistryObject<SoundEvent> S_FOLLOW2=
          registerSoundEvent("s_follow2");
  public static final RegistryObject<SoundEvent> S_FOLLOW3=
          registerSoundEvent("s_follow3");
  public static final RegistryObject<SoundEvent> S_FOLLOW4=
          registerSoundEvent("s_follow4");
  public static final RegistryObject<SoundEvent> S_FOLLOW5=
          registerSoundEvent("s_follow5");
  public static final RegistryObject<SoundEvent> S_FOLLOW6=
          registerSoundEvent("s_follow6");
  public static final RegistryObject<SoundEvent> S_FOLLOW7=
          registerSoundEvent("s_follow7");
  public static final RegistryObject<SoundEvent> S_FOLLOW8=
          registerSoundEvent("s_follow8");
  public static final RegistryObject<SoundEvent> S_FOLLOW9=
          registerSoundEvent("s_follow9");
  public static final RegistryObject<SoundEvent> S_HEAL1=
          registerSoundEvent("s_heal1");
  public static final RegistryObject<SoundEvent> S_HEAL2=
          registerSoundEvent("s_heal2");
  public static final RegistryObject<SoundEvent> S_HEAL3=
          registerSoundEvent("s_heal3");
  public static final RegistryObject<SoundEvent> S_HEAL4=
          registerSoundEvent("s_heal4");
  public static final RegistryObject<SoundEvent> S_HEAL5=
          registerSoundEvent("s_heal5");
  public static final RegistryObject<SoundEvent> S_HEAL6=
          registerSoundEvent("s_heal6");
  public static final RegistryObject<SoundEvent> S_HEAL7=
          registerSoundEvent("s_heal7");
  public static final RegistryObject<SoundEvent> S_HURT1=
          registerSoundEvent("s_hurt1");
  public static final RegistryObject<SoundEvent> S_HURT2=
          registerSoundEvent("s_hurt2");
  public static final RegistryObject<SoundEvent> S_HURT3=
          registerSoundEvent("s_hurt3");
  public static final RegistryObject<SoundEvent> S_HURT4=
          registerSoundEvent("s_hurt4");
  public static final RegistryObject<SoundEvent> S_PL_HURT1=
          registerSoundEvent("s_pl_hurt1");
  public static final RegistryObject<SoundEvent> S_PL_HURT2=
          registerSoundEvent("s_pl_hurt2");
  public static final RegistryObject<SoundEvent> S_PL_HURT3=
          registerSoundEvent("s_pl_hurt3");
  public static final RegistryObject<SoundEvent> S_PL_HURT4=
          registerSoundEvent("s_pl_hurt4");
  public static final RegistryObject<SoundEvent> S_Q1=
          registerSoundEvent("s_q1");
  public static final RegistryObject<SoundEvent> S_Q10=
          registerSoundEvent("s_q10");
  public static final RegistryObject<SoundEvent> S_Q11=
          registerSoundEvent("s_q11");
  public static final RegistryObject<SoundEvent> S_Q12=
          registerSoundEvent("s_q12");
  public static final RegistryObject<SoundEvent> S_Q13=
          registerSoundEvent("s_q13");
  public static final RegistryObject<SoundEvent> S_Q14=
          registerSoundEvent("s_q14");
  public static final RegistryObject<SoundEvent> S_Q2=
          registerSoundEvent("s_q2");
  public static final RegistryObject<SoundEvent> S_Q3=
          registerSoundEvent("s_q3");
  public static final RegistryObject<SoundEvent> S_Q4=
          registerSoundEvent("s_q4");
  public static final RegistryObject<SoundEvent> S_Q5=
          registerSoundEvent("s_q5");
  public static final RegistryObject<SoundEvent> S_Q6=
          registerSoundEvent("s_q6");
  public static final RegistryObject<SoundEvent> S_Q7=
          registerSoundEvent("s_q7");
  public static final RegistryObject<SoundEvent> S_Q8=
          registerSoundEvent("s_q8");
  public static final RegistryObject<SoundEvent> S_Q9=
          registerSoundEvent("s_q9");
  public static final RegistryObject<SoundEvent> S_STAY1=
          registerSoundEvent("s_stay1");
  public static final RegistryObject<SoundEvent> S_STAY10=
          registerSoundEvent("s_stay10");
  public static final RegistryObject<SoundEvent> S_STAY11=
          registerSoundEvent("s_stay11");
  public static final RegistryObject<SoundEvent> S_STAY12=
          registerSoundEvent("s_stay12");
  public static final RegistryObject<SoundEvent> S_STAY13=
          registerSoundEvent("s_stay13");
  public static final RegistryObject<SoundEvent> S_STAY14=
          registerSoundEvent("s_stay14");
  public static final RegistryObject<SoundEvent> S_STAY2=
          registerSoundEvent("s_stay2");
  public static final RegistryObject<SoundEvent> S_STAY3=
          registerSoundEvent("s_stay3");
  public static final RegistryObject<SoundEvent> S_STAY4=
          registerSoundEvent("s_stay4");
  public static final RegistryObject<SoundEvent> S_STAY5=
          registerSoundEvent("s_stay5");
  public static final RegistryObject<SoundEvent> S_STAY6=
          registerSoundEvent("s_stay6");
  public static final RegistryObject<SoundEvent> S_STAY7=
          registerSoundEvent("s_stay7");
  public static final RegistryObject<SoundEvent> S_STAY8=
          registerSoundEvent("s_stay8");
  public static final RegistryObject<SoundEvent> S_STAY9=
          registerSoundEvent("s_stay9");
  public static final RegistryObject<SoundEvent> S_WARN1=
          registerSoundEvent("s_warn1");
  public static final RegistryObject<SoundEvent> S_WARN2=
          registerSoundEvent("s_warn2");
  public static final RegistryObject<SoundEvent> S_WARN3=
          registerSoundEvent("s_warn3");
  public static final RegistryObject<SoundEvent> S_WARN4=
          registerSoundEvent("s_warn4");
  public static final RegistryObject<SoundEvent> S_WARN5=
          registerSoundEvent("s_warn5");
  public static final RegistryObject<SoundEvent> S_WARN6=
          registerSoundEvent("s_warn6");
  public static final RegistryObject<SoundEvent> VANSWER01=
          registerSoundEvent("vanswer01");
  public static final RegistryObject<SoundEvent> VANSWER02=
          registerSoundEvent("vanswer02");
  public static final RegistryObject<SoundEvent> VANSWER03=
          registerSoundEvent("vanswer03");
  public static final RegistryObject<SoundEvent> VANSWER04=
          registerSoundEvent("vanswer04");
  public static final RegistryObject<SoundEvent> VANSWER05=
          registerSoundEvent("vanswer05");
  public static final RegistryObject<SoundEvent> VANSWER06=
          registerSoundEvent("vanswer06");
  public static final RegistryObject<SoundEvent> VANSWER07=
          registerSoundEvent("vanswer07");
  public static final RegistryObject<SoundEvent> VANSWER08=
          registerSoundEvent("vanswer08");
  public static final RegistryObject<SoundEvent> VANSWER09=
          registerSoundEvent("vanswer09");
  public static final RegistryObject<SoundEvent> VANSWER10=
          registerSoundEvent("vanswer10");
  public static final RegistryObject<SoundEvent> VANSWER11=
          registerSoundEvent("vanswer11");
  public static final RegistryObject<SoundEvent> VANSWER12=
          registerSoundEvent("vanswer12");
  public static final RegistryObject<SoundEvent> VANSWER13=
          registerSoundEvent("vanswer13");
  public static final RegistryObject<SoundEvent> VANSWER14=
          registerSoundEvent("vanswer14");
  public static final RegistryObject<SoundEvent> VANSWER15=
          registerSoundEvent("vanswer15");
  public static final RegistryObject<SoundEvent> VANSWER16=
          registerSoundEvent("vanswer16");
  public static final RegistryObject<SoundEvent> VANSWER17=
          registerSoundEvent("vanswer17");
  public static final RegistryObject<SoundEvent> VANSWER18=
          registerSoundEvent("vanswer18");
  public static final RegistryObject<SoundEvent> V_AN1=
          registerSoundEvent("v_an1");
  public static final RegistryObject<SoundEvent> V_AN2=
          registerSoundEvent("v_an2");
  public static final RegistryObject<SoundEvent> V_AN3=
          registerSoundEvent("v_an3");
  public static final RegistryObject<SoundEvent> V_AN4=
          registerSoundEvent("v_an4");
  public static final RegistryObject<SoundEvent> V_AN5=
          registerSoundEvent("v_an5");

  public static final RegistryObject<SoundEvent> SCI_FEAR1=
          registerSoundEvent("sci_fear1");
  public static final RegistryObject<SoundEvent> SCI_FEAR2=
          registerSoundEvent("sci_fear2");
  public static final RegistryObject<SoundEvent> SCI_FEAR3=
          registerSoundEvent("sci_fear3");
  public static final RegistryObject<SoundEvent> SCI_FEAR4=
          registerSoundEvent("sci_fear4");
  public static final RegistryObject<SoundEvent> SCI_FEAR5=
          registerSoundEvent("sci_fear5");
  public static final RegistryObject<SoundEvent> SCI_FEAR6=
          registerSoundEvent("sci_fear6");
  public static final RegistryObject<SoundEvent> SCI_FEAR7=
          registerSoundEvent("sci_fear7");
  public static final RegistryObject<SoundEvent> SCI_FEAR8=
          registerSoundEvent("sci_fear8");

  /*         "sounds.half_life_mod.sci_fear1": "Scientist ",
          "sounds.half_life_mod.sci_fear2": "Scientist ",
          "sounds.half_life_mod.sci_fear3": "Scientist ",
          "sounds.half_life_mod.sci_fear4": "Scientist ",
          "sounds.half_life_mod.sci_fear5": "Scientist ",
          "sounds.half_life_mod.sci_fear6": "Scientist ",
          "sounds.half_life_mod.sci_fear7": "Scientist ",
          "sounds.half_life_mod.sci_fear8": "Scientist ", */

  public static final RegistryObject<SoundEvent> V_C1=
          registerSoundEvent("v_c1");
  public static final RegistryObject<SoundEvent> V_C2=
          registerSoundEvent("v_c2");
  public static final RegistryObject<SoundEvent> V_C3=
          registerSoundEvent("v_c3");
  public static final RegistryObject<SoundEvent> V_C4=
          registerSoundEvent("v_c4");
  public static final RegistryObject<SoundEvent> V_C5=
          registerSoundEvent("v_c5");
  public static final RegistryObject<SoundEvent> V_C6=
          registerSoundEvent("v_c6");
  public static final RegistryObject<SoundEvent> V_C7=
          registerSoundEvent("v_c7");
  public static final RegistryObject<SoundEvent> V_KILL1=
          registerSoundEvent("v_kill1");
  public static final RegistryObject<SoundEvent> V_KILL2=
          registerSoundEvent("v_kill2");

    /*      "sounds.half_life_mod.ba_ap1": " ",
          "sounds.half_life_mod.ba_ap2": " ",
          "sounds.half_life_mod.ba_ap3": " ",
          "sounds.half_life_mod.ba_ap4": " ",
          "sounds.half_life_mod.ba_die1": " ",
          "sounds.half_life_mod.ba_die2": " ",
          "sounds.half_life_mod.ba_die3": " ",
          "sounds.half_life_mod.ba_follow1": " ",
          "sounds.half_life_mod.ba_follow2": " ",
          "sounds.half_life_mod.ba_follow3": " ",
          "sounds.half_life_mod.ba_follow4": " ",
          "sounds.half_life_mod.ba_follow5": " ",
          "sounds.half_life_mod.ba_follow6": " ",
          "sounds.half_life_mod.ba_follow7": " ",
          "sounds.half_life_mod.ba_idle_a1": " ",
          "sounds.half_life_mod.ba_idle_a2": " ",
          "sounds.half_life_mod.ba_idle_a3": " ",
          "sounds.half_life_mod.ba_idle_a4": " ",
          "sounds.half_life_mod.ba_idle_a5": " ",
          "sounds.half_life_mod.ba_idle_a6": " ",
          "sounds.half_life_mod.ba_idle_a7": " ",
          "sounds.half_life_mod.ba_idle_c1": " ",
          "sounds.half_life_mod.ba_idle_c2": " ",
          "sounds.half_life_mod.ba_idle_c3": " ",
          "sounds.half_life_mod.ba_idle_c4": " ",
          "sounds.half_life_mod.ba_idle_c5": " ",
          "sounds.half_life_mod.ba_idle_c6": " ",
          "sounds.half_life_mod.ba_idle_c7": " ",
          "sounds.half_life_mod.ba_idle_c8": " ",
          "sounds.half_life_mod.ba_idle_hurt1": " ",
          "sounds.half_life_mod.ba_idle_hurt2": " ",
          "sounds.half_life_mod.ba_idle_pl_hurt1": " ",
          "sounds.half_life_mod.ba_idle_pl_hurt2": " ",
          "sounds.half_life_mod.ba_idle_q1": " ",
          "sounds.half_life_mod.ba_idle_q2": " ",
          "sounds.half_life_mod.ba_idle_q3": " ",
          "sounds.half_life_mod.ba_idle_q4": " ",
          "sounds.half_life_mod.ba_idle_q5": " ",
          "sounds.half_life_mod.ba_idle_q6": " ",
          "sounds.half_life_mod.ba_idle_q7": " ",
          "sounds.half_life_mod.ba_idle_q8": " ",
          "sounds.half_life_mod.ba_kill1": " ",
          "sounds.half_life_mod.ba_kill2": " ",
          "sounds.half_life_mod.ba_kill3": " ",
          "sounds.half_life_mod.ba_kill4": " ",
          "sounds.half_life_mod.ba_kill5": " ",
          "sounds.half_life_mod.ba_kill6": " ",
          "sounds.half_life_mod.ba_stay1": " ",
          "sounds.half_life_mod.ba_stay2": " ",
          "sounds.half_life_mod.ba_stay3": " ",
          "sounds.half_life_mod.ba_stay4": " ",
          "sounds.half_life_mod.ba_stay5": " ",
          "sounds.half_life_mod.ba_stay6": " ",
          "sounds.half_life_mod.ba_stay7": " ",
          "sounds.half_life_mod.ba_warn1": " ",
          "sounds.half_life_mod.ba_warn2": " ",
          "sounds.half_life_mod.ba_warn3": " ",
          "sounds.half_life_mod.ba_warn4": " ",
          "sounds.half_life_mod.ba_warn6": " ",
          "sounds.half_life_mod.scientist_headcrabonhead": " ",
          "sounds.half_life_mod.sci_die1": " ",
          "sounds.half_life_mod.sci_die2": " ",
          "sounds.half_life_mod.sci_die3": " ",
          "sounds.half_life_mod.sci_die4": " ",
          "sounds.half_life_mod.sci_die5": " ",
          "sounds.half_life_mod.sci_pain1": " ",
          "sounds.half_life_mod.sci_pain10": " ",
          "sounds.half_life_mod.sci_pain2": " ",
          "sounds.half_life_mod.sci_pain3": " ",
          "sounds.half_life_mod.sci_pain4": " ",
          "sounds.half_life_mod.sci_pain5": " ",
          "sounds.half_life_mod.sci_pain6": " ",
          "sounds.half_life_mod.sci_pain7": " ",
          "sounds.half_life_mod.sci_pain8": " ",
          "sounds.half_life_mod.sci_pain9": " ",
          "sounds.half_life_mod.s_an1": " ",
          "sounds.half_life_mod.s_an10": " ",
          "sounds.half_life_mod.s_an11": " ",
          "sounds.half_life_mod.s_an12": " ",
          "sounds.half_life_mod.s_an13": " ",
          "sounds.half_life_mod.s_an14": " ",
          "sounds.half_life_mod.s_an15": " ",
          "sounds.half_life_mod.s_an16": " ",
          "sounds.half_life_mod.s_an17": " ",
          "sounds.half_life_mod.s_an18": " ",
          "sounds.half_life_mod.s_an19": " ",
          "sounds.half_life_mod.s_an2": " ",
          "sounds.half_life_mod.s_an20": " ",
          "sounds.half_life_mod.s_an21": " ",
          "sounds.half_life_mod.s_an3": " ",
          "sounds.half_life_mod.s_an4": " ",
          "sounds.half_life_mod.s_an5": " ",
          "sounds.half_life_mod.s_an6": " ",
          "sounds.half_life_mod.s_an7": " ",
          "sounds.half_life_mod.s_an8": " ",
          "sounds.half_life_mod.s_an9": " ",
          "sounds.half_life_mod.s_ap1": " ",
          "sounds.half_life_mod.s_ap2": " ",
          "sounds.half_life_mod.s_c1": " ",
          "sounds.half_life_mod.s_c10": " ",
          "sounds.half_life_mod.s_c11": " ",
          "sounds.half_life_mod.s_c12": " ",
          "sounds.half_life_mod.s_c13": " ",
          "sounds.half_life_mod.s_c14": " ",
          "sounds.half_life_mod.s_c2": " ",
          "sounds.half_life_mod.s_c3": " ",
          "sounds.half_life_mod.s_c4": " ",
          "sounds.half_life_mod.s_c5": " ",
          "sounds.half_life_mod.s_c6": " ",
          "sounds.half_life_mod.s_c7": " ",
          "sounds.half_life_mod.s_c8": " ",
          "sounds.half_life_mod.s_c9": " ",
          "sounds.half_life_mod.s_follow1": " ",
          "sounds.half_life_mod.s_follow2": " ",
          "sounds.half_life_mod.s_follow3": " ",
          "sounds.half_life_mod.s_follow4": " ",
          "sounds.half_life_mod.s_follow5": " ",
          "sounds.half_life_mod.s_follow6": " ",
          "sounds.half_life_mod.s_follow7": " ",
          "sounds.half_life_mod.s_follow8": " ",
          "sounds.half_life_mod.s_follow9": " ",
          "sounds.half_life_mod.s_heal1": " ",
          "sounds.half_life_mod.s_heal2": " ",
          "sounds.half_life_mod.s_heal3": " ",
          "sounds.half_life_mod.s_heal4": " ",
          "sounds.half_life_mod.s_heal5": " ",
          "sounds.half_life_mod.s_heal6": " ",
          "sounds.half_life_mod.s_heal7": " ",
          "sounds.half_life_mod.s_hurt1": " ",
          "sounds.half_life_mod.s_hurt2": " ",
          "sounds.half_life_mod.s_hurt3": " ",
          "sounds.half_life_mod.s_hurt4": " ",
          "sounds.half_life_mod.s_pl_hurt1": " ",
          "sounds.half_life_mod.s_pl_hurt2": " ",
          "sounds.half_life_mod.s_pl_hurt3": " ",
          "sounds.half_life_mod.s_pl_hurt4": " ",
          "sounds.half_life_mod.s_q1": " ",
          "sounds.half_life_mod.s_q10": " ",
          "sounds.half_life_mod.s_q11": " ",
          "sounds.half_life_mod.s_q12": " ",
          "sounds.half_life_mod.s_q13": " ",
          "sounds.half_life_mod.s_q14": " ",
          "sounds.half_life_mod.s_q2": " ",
          "sounds.half_life_mod.s_q3": " ",
          "sounds.half_life_mod.s_q4": " ",
          "sounds.half_life_mod.s_q5": " ",
          "sounds.half_life_mod.s_q6": " ",
          "sounds.half_life_mod.s_q7": " ",
          "sounds.half_life_mod.s_q8": " ",
          "sounds.half_life_mod.s_q9": " ",
          "sounds.half_life_mod.s_stay1": " ",
          "sounds.half_life_mod.s_stay10": " ",
          "sounds.half_life_mod.s_stay11": " ",
          "sounds.half_life_mod.s_stay12": " ",
          "sounds.half_life_mod.s_stay13": " ",
          "sounds.half_life_mod.s_stay14": " ",
          "sounds.half_life_mod.s_stay2": " ",
          "sounds.half_life_mod.s_stay3": " ",
          "sounds.half_life_mod.s_stay4": " ",
          "sounds.half_life_mod.s_stay5": " ",
          "sounds.half_life_mod.s_stay6": " ",
          "sounds.half_life_mod.s_stay7": " ",
          "sounds.half_life_mod.s_stay8": " ",
          "sounds.half_life_mod.s_stay9": " ",
          "sounds.half_life_mod.s_warn1": " ",
          "sounds.half_life_mod.s_warn2": " ",
          "sounds.half_life_mod.s_warn3": " ",
          "sounds.half_life_mod.s_warn4": " ",
          "sounds.half_life_mod.s_warn5": " ",
          "sounds.half_life_mod.s_warn6": " ",
          "sounds.half_life_mod.vanswer01": " ",
          "sounds.half_life_mod.vanswer02": " ",
          "sounds.half_life_mod.vanswer03": " ",
          "sounds.half_life_mod.vanswer04": " ",
          "sounds.half_life_mod.vanswer05": " ",
          "sounds.half_life_mod.vanswer06": " ",
          "sounds.half_life_mod.vanswer07": " ",
          "sounds.half_life_mod.vanswer08": " ",
          "sounds.half_life_mod.vanswer09": " ",
          "sounds.half_life_mod.vanswer10": " ",
          "sounds.half_life_mod.vanswer11": " ",
          "sounds.half_life_mod.vanswer12": " ",
          "sounds.half_life_mod.vanswer13": " ",
          "sounds.half_life_mod.vanswer14": " ",
          "sounds.half_life_mod.vanswer15": " ",
          "sounds.half_life_mod.vanswer16": " ",
          "sounds.half_life_mod.vanswer17": " ",
          "sounds.half_life_mod.vanswer18": " ",
          "sounds.half_life_mod.v_an1": " ",
          "sounds.half_life_mod.v_an2": " ",
          "sounds.half_life_mod.v_an3": " ",
          "sounds.half_life_mod.v_an4": " ",
          "sounds.half_life_mod.v_an5": " ",
          "sounds.half_life_mod.v_c1": " ",
          "sounds.half_life_mod.v_c2": " ",
          "sounds.half_life_mod.v_c3": " ",
          "sounds.half_life_mod.v_c4": " ",
          "sounds.half_life_mod.v_c5": " ",
          "sounds.half_life_mod.v_c6": " ",
          "sounds.half_life_mod.v_c7": " ",
          "sounds.half_life_mod.v_kill1": " ",
          "sounds.half_life_mod.v_kill2": " ", */





  private static RegistryObject<SoundEvent> registerSoundEvent(String name) {
        ResourceLocation id = new ResourceLocation(HalfLifeMod.MOD_ID, name);
        return SOUND_EVENTS.register(name, () -> SoundEvent.createVariableRangeEvent(id));
    }

    public static void register(IEventBus eventBus) {
        SOUND_EVENTS.register(eventBus);
    }
}
