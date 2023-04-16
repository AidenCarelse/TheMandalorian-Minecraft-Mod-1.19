package net.arc.themandalorian.block;

import net.arc.themandalorian.TheMandalorian;
import net.arc.themandalorian.block.custom.MandalorianForgeBlock;
import net.arc.themandalorian.item.ModCreativeModeTab;
import net.arc.themandalorian.item.ModItems;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DropExperienceBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModBlocks
{
    public static final DeferredRegister<Block>BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, TheMandalorian.MOD_ID);

    public static final RegistryObject<Block> DEEPSLATE_BESKAR_ORE = registerBlock("deepslate_beskar_ore",
            () -> new DropExperienceBlock(BlockBehaviour.Properties.of(Material.AMETHYST).strength(6f).requiresCorrectToolForDrops(),
                    UniformInt.of(5, 9)), ModCreativeModeTab.THE_MANDALORIAN_TAB);

    public static final RegistryObject<Block> MANDALORIAN_FORGE = registerBlock("mandalorian_forge",
            () -> new MandalorianForgeBlock(BlockBehaviour.Properties.of(Material.METAL).strength(6f).requiresCorrectToolForDrops().noOcclusion())
            , ModCreativeModeTab.THE_MANDALORIAN_TAB);

    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block, CreativeModeTab tab)
    {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn, tab);
        return toReturn;
    }

    private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block, CreativeModeTab tab)
    {
        return ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties().tab(tab)));
    }

    public static void register(IEventBus eventBus)
    {
        BLOCKS.register(eventBus);
    }
}
