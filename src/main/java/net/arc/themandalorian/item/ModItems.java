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

    public static final RegistryObject<Item> BESKAR_SCRAP = ITEMS.register("beskar_scrap",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.THE_MANDALORIAN_TAB)));

    public static final RegistryObject<Item> MANDALORIAN_HELMET = ITEMS.register("mandalorian_helmet",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.THE_MANDALORIAN_TAB)));

    public static final RegistryObject<Item> MANDALORIAN_CHESTPLATE = ITEMS.register("mandalorian_chestplate",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.THE_MANDALORIAN_TAB)));

    public static final RegistryObject<Item> MANDALORIAN_LEGGINGS = ITEMS.register("mandalorian_leggings",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.THE_MANDALORIAN_TAB)));

    public static final RegistryObject<Item> MANDALORIAN_BOOTS = ITEMS.register("mandalorian_boots",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.THE_MANDALORIAN_TAB)));

    public static void register(IEventBus eventBus)
    {
        ITEMS.register(eventBus);
    }
}
