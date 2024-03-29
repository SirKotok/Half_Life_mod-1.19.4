package net.sirkotok.half_life_mod.misc.util;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.RandomSource;
import net.sirkotok.half_life_mod.entity.base.HalfLifeMonster;
import net.sirkotok.half_life_mod.entity.base.HalfLifeNeutral;
import net.sirkotok.half_life_mod.sound.HalfLifeSounds;
import org.jetbrains.annotations.Nullable;

public final class CommonSounds {

    public static void PlaySoundAsOwn(HalfLifeMonster monster, @Nullable SoundEvent soundEvent){
        if (soundEvent == null) return;
        monster.playSound(soundEvent, monster.getMonsterSoundVolume(), monster.getVoicePitch());
    }

    public static void PlaySoundAsOwn(HalfLifeNeutral monster, @Nullable SoundEvent soundEvent){
        if (soundEvent == null) return;
        monster.playSound(soundEvent, monster.getNeutralSoundVolume(), monster.getVoicePitch());
    }

    public static void PlaySoundQuiet(HalfLifeMonster monster, @Nullable SoundEvent soundEvent){
        if (soundEvent == null) return;
        monster.playSound(soundEvent, 0.2f, monster.getVoicePitch());
    }


    public static SoundEvent getClawMissSound() {
        switch (RandomSource.create().nextInt(1,3)) {
            case 1:  return HalfLifeSounds.CLAW_MISS1.get();
            case 2:  return HalfLifeSounds.CLAW_MISS2.get();
        }
        return HalfLifeSounds.HEADCRAB_1_PAIN_1.get();
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
