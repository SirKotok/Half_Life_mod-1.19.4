package net.sirkotok.half_life_mod.misc.util;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.settings.KeyConflictContext;
import org.lwjgl.glfw.GLFW;

public class KeyBinding {
        public static final String KEY_CATEGORY_HALF_LIFE = "key.category.half_life_mod.half_life";
        public static final String KEY_RELOAD_GUN = "key.half_life_mod.reload_gun";
        public static final KeyMapping RELOAD_KEY = new KeyMapping(KEY_RELOAD_GUN, KeyConflictContext.IN_GAME,
                InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_R, KEY_CATEGORY_HALF_LIFE);


}
