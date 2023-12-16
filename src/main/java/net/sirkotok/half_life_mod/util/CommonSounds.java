package net.sirkotok.half_life_mod.util;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.RandomSource;
import net.sirkotok.half_life_mod.entity.base.HalfLifeMonster;
import net.sirkotok.half_life_mod.sound.HalfLifeSounds;

public final class CommonSounds {

    public static void PlaySoundAsOwn(HalfLifeMonster monster, SoundEvent soundEvent){
        monster.playSound(soundEvent, monster.getMonsterSoundVolume(), monster.getVoicePitch());
    }

    public static SoundEvent getHL1Explosion(){
        switch (RandomSource.create().nextInt(1,4)) {
            case 1:  return HalfLifeSounds.EXPLODE3.get();
            case 2:  return HalfLifeSounds.EXPLODE4.get();
            case 3:  return HalfLifeSounds.EXPLODE5.get();
        }
        return HalfLifeSounds.HEADCRAB_1_ALERT_1.get();
    }

    public static SoundEvent getClawHitSound(){
        switch (RandomSource.create().nextInt(1,4)) {
            case 1:  return HalfLifeSounds.CLAW_STRIKE1.get();
            case 2:  return HalfLifeSounds.CLAW_STRIKE2.get();
            case 3:  return HalfLifeSounds.CLAW_STRIKE3.get();
        }
        return HalfLifeSounds.HEADCRAB_1_ALERT_1.get();
    }
    public static SoundEvent getRicSound(){
        switch (RandomSource.create().nextInt(1,6)) {
            case 1:  return HalfLifeSounds.RIC1.get();
            case 2:  return HalfLifeSounds.RIC2.get();
            case 3:  return HalfLifeSounds.RIC3.get();
            case 4:  return HalfLifeSounds.RIC4.get();
            case 5:  return HalfLifeSounds.RIC5.get();
        }
        return HalfLifeSounds.HEADCRAB_1_ALERT_1.get();
    }



}
