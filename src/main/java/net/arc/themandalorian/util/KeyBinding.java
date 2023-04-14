package net.arc.themandalorian.util;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.settings.KeyConflictContext;
import org.lwjgl.glfw.GLFW;

public class KeyBinding
{
    public static final String KEY_CATEGORY_MANDALORIAN ="key.category.themandalorian.mandalorian";
    public static final String KEY_OPEN_GADGETS = "key.themandalorian.open_gadgets";

    public static final KeyMapping OPEN_GADGETS_KEY = new KeyMapping(KEY_OPEN_GADGETS, KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_G, KEY_CATEGORY_MANDALORIAN);
}
