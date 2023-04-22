package net.arc.themandalorian.item;

import net.arc.themandalorian.TheMandalorian;
import net.arc.themandalorian.entity.ModEntityTypes;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.ForgeSpawnEggItem;
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
            () -> new ArmorItem(ModArmorMaterials.MANDALORIAN, EquipmentSlot.HEAD, new Item.Properties().tab(ModCreativeModeTab.THE_MANDALORIAN_TAB).stacksTo(1)));

    public static final RegistryObject<Item> MANDALORIAN_CHESTPLATE = ITEMS.register("mandalorian_chestplate",
            () -> new ArmorItem(ModArmorMaterials.MANDALORIAN, EquipmentSlot.CHEST, new Item.Properties().tab(ModCreativeModeTab.THE_MANDALORIAN_TAB).stacksTo(1)));

    public static final RegistryObject<Item> MANDALORIAN_LEGGINGS = ITEMS.register("mandalorian_leggings",
            () -> new ArmorItem(ModArmorMaterials.MANDALORIAN, EquipmentSlot.LEGS, new Item.Properties().tab(ModCreativeModeTab.THE_MANDALORIAN_TAB).stacksTo(1)));

    public static final RegistryObject<Item> MANDALORIAN_BOOTS = ITEMS.register("mandalorian_boots",
            () -> new ArmorItem(ModArmorMaterials.MANDALORIAN, EquipmentSlot.FEET, new Item.Properties().tab(ModCreativeModeTab.THE_MANDALORIAN_TAB).stacksTo(1)));

    public static final RegistryObject<Item> GROGU_SPAWN_EGG = ITEMS.register("grogu_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntityTypes.GROGU, 0x5ede73, 0xfba8aa,
                    new Item.Properties().tab(ModCreativeModeTab.THE_MANDALORIAN_TAB)));

    public static void register(IEventBus eventBus)
    {
        ITEMS.register(eventBus);
    }
}
