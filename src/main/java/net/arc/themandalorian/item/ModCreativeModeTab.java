package net.arc.themandalorian.item;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class ModCreativeModeTab
{
    public static final CreativeModeTab THE_MANDALORIAN_TAB = new CreativeModeTab("the_mandalorian_tab")
    {
        @Override
        public ItemStack makeIcon()
        {
            return new ItemStack(ModItems.MANDALORIAN_HELMET.get());
        }
    };
}
