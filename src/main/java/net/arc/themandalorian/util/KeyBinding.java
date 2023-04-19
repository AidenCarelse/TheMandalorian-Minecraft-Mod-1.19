package net.arc.themandalorian.util;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.settings.KeyConflictContext;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.system.windows.MOUSEINPUT;

public class KeyBinding
{
    public static final String KEY_CATEGORY_MANDALORIAN ="key.category.themandalorian.mandalorian";
    public static final String KEY_OPEN_GADGETS = "key.themandalorian.open_gadgets";
    public static final String MOUSE_LEFT_DOWN = "mouse.themandalorian.mouse_left_down";

    public static final KeyMapping OPEN_GADGETS_KEY = new KeyMapping(KEY_OPEN_GADGETS, KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_G, KEY_CATEGORY_MANDALORIAN);

    public static final KeyMapping PRESS_BUTTON_KEY = new KeyMapping(MOUSE_LEFT_DOWN, KeyConflictContext.IN_GAME,
            InputConstants.Type.MOUSE, GLFW.GLFW_MOUSE_BUTTON_1, KEY_CATEGORY_MANDALORIAN);
}
