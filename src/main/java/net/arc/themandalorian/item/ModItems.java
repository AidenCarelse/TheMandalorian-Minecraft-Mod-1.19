package net.arc.themandalorian.item;

import net.arc.themandalorian.TheMandalorian;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems
{
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, TheMandalorian.MOD_ID);

    public static final RegistryObject<Item> BESKAR_BAR = ITEMS.register("beskar_bar",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.THE_MANDALORIAN_TAB)));

    public static final RegistryObject<Item> RAW_BESKAR = ITEMS.register("raw_beskar",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.THE_MANDALORIAN_TAB)));

    public static void register(IEventBus eventBus)
    {
        ITEMS.register(eventBus);
    }
}
