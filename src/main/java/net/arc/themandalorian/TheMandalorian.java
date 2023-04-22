package net.arc.themandalorian;

import com.mojang.logging.LogUtils;
import net.arc.themandalorian.block.ModBlocks;
import net.arc.themandalorian.block.entity.MandalorianForgeBlockEntity;
import net.arc.themandalorian.block.entity.ModBlockEntities;
import net.arc.themandalorian.entity.ModEntityTypes;
import net.arc.themandalorian.entity.client.GroguRenderer;
import net.arc.themandalorian.item.ModItems;
import net.arc.themandalorian.networking.ModMessages;
import net.arc.themandalorian.recipe.ModRecipes;
import net.arc.themandalorian.screen.MandalorianForgeScreen;
import net.arc.themandalorian.screen.ModMenuTypes;
import net.arc.themandalorian.world.feature.ModConfiguredFeatures;
import net.arc.themandalorian.world.feature.ModPlacedFeatures;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;
import software.bernie.geckolib3.GeckoLib;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(TheMandalorian.MOD_ID)
public class TheMandalorian
{
    public static final String MOD_ID = "themandalorian";
    private static final Logger LOGGER = LogUtils.getLogger();

    public TheMandalorian()
    {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);

        ModConfiguredFeatures.register(modEventBus);
        ModPlacedFeatures.register(modEventBus);

        ModBlockEntities.register(modEventBus);
        ModMenuTypes.register(modEventBus);

        ModRecipes.register(modEventBus);
        ModEntityTypes.register(modEventBus);

        GeckoLib.initialize();

        modEventBus.addListener(this::commonSetup);

        MinecraftForge.EVENT_BUS.register(this);
    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {
        event.enqueueWork(() -> {
            ModMessages.register();
        });
    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {
            MenuScreens.register(ModMenuTypes.MANDALORIAN_FORGE_MENU.get(), MandalorianForgeScreen::new);
            EntityRenderers.register(ModEntityTypes.GROGU.get(), GroguRenderer::new);
        }
    }
}
