package net.sirkotok.half_life_mod.util;

import net.minecraft.util.RandomSource;

import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;

public final class HLperUtil {
    @Nullable
    public static UUID getMaxUUID(List<UUID> list) {
        if (list == null || list.isEmpty()) return null;
        UUID max = list.get(0);
        for (UUID id : list) {
            if (max.compareTo(id) < 0) max = id;
        }
      return max;
    }



    public static int getrandomof(int i, int j, int k, boolean a, boolean b) {
        int r = 1;
        int changer = j;
        if (a && b) {
            r = 3;
        } else if(a) {
            r = 2;
        } else if(b) {
            r = 2;
            changer = k;
        }

        switch(RandomSource.create().nextInt(1,r+1)) {
            case 2: return changer;
            case 3: return k;
        }
        return i;
    }



    public static int getMaxUUIDnumber(List<UUID> list) {
        if (list == null || list.isEmpty()) return -1;
        int i = -1;
        int currentmaxi = 0;
        UUID max = list.get(0);
        for (UUID id : list) {
            i++;
            if (max.compareTo(id) < 0) {
                max = id;
                currentmaxi = i;
            }
        }
        return currentmaxi;
    }




    public static int getMinUUIDnumber(List<UUID> list) {
        if (list == null || list.isEmpty()) return -1;
        int i = -1;
        int currentmini = 0;
        UUID min = list.get(0);
        for (UUID id : list) {
            i++;
            if (min.compareTo(id) > 0) {
                min = id;
                currentmini = i;
            }
        }
        return currentmini;
    }





    @Nullable
    public static UUID getMinUUID(List<UUID> list) {
        if (list == null || list.isEmpty()) return null;
        UUID min = list.get(0);
        for (UUID id : list) {
            if (min.compareTo(id) > 0) min = id;
        }
        return min;
    }




}
