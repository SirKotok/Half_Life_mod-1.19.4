package net.sirkotok.half_life_mod.util;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.RandomSource;
import net.sirkotok.half_life_mod.sound.ModSounds;

public final class CommonSounds {

    public static SoundEvent getClawHitSound(){
        switch (RandomSource.create().nextInt(1,4)) {
            case 1:  return ModSounds.CLAW_STRIKE1.get();
            case 2:  return ModSounds.CLAW_STRIKE2.get();
            case 3:  return ModSounds.CLAW_STRIKE3.get();
        }
        return ModSounds.HEADCRAB_1_ALERT_1.get();
    }
    public static SoundEvent getRicSound(){
        switch (RandomSource.create().nextInt(1,6)) {
            case 1:  return ModSounds.RIC1.get();
            case 2:  return ModSounds.RIC2.get();
            case 3:  return ModSounds.RIC3.get();
            case 4:  return ModSounds.RIC4.get();
            case 5:  return ModSounds.RIC5.get();
        }
        return ModSounds.HEADCRAB_1_ALERT_1.get();
    }



}
