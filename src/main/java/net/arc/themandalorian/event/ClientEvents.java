package net.arc.themandalorian.event;

import net.arc.themandalorian.TheMandalorian;
import net.arc.themandalorian.block.entity.ModBlockEntities;
import net.arc.themandalorian.block.entity.renderer.MandalorianForgeBlockEntityRenderer;
import net.arc.themandalorian.screen.MandalorianGadgetsMenu;
import net.arc.themandalorian.util.KeyBinding;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.Nullable;

public class ClientEvents
{
    public static boolean gadgetKeyPressed = false;

    @Mod.EventBusSubscriber(modid = TheMandalorian.MOD_ID, value = Dist.CLIENT)
    public static class ClientForgeEvents
    {
        @SubscribeEvent
        public static void onKeyInput(InputEvent.Key event)
        {
            if(KeyBinding.OPEN_GADGETS_KEY.consumeClick())
            {
                gadgetKeyPressed = true;
                Minecraft.getInstance().player.sendSystemMessage(Component.literal("TEMP: Opened gadgets menu"));
                Minecraft.getInstance().player.openMenu(new MenuProvider() {
                    @Override
                    public Component getDisplayName() {
                        return null;
                    }

                    @Nullable
                    @Override
                    public AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
                        return new MandalorianGadgetsMenu(pContainerId, pPlayerInventory, null);
                    }
                });
            }
        }
    }

    @Mod.EventBusSubscriber(modid = TheMandalorian.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientModBusEvents
    {
        @SubscribeEvent
        public static void onKeyRegister(RegisterKeyMappingsEvent event)
        {
            event.register((KeyBinding.OPEN_GADGETS_KEY));
        }

        @SubscribeEvent
        public static void registerRenderers(final EntityRenderersEvent.RegisterRenderers event) {
            event.registerBlockEntityRenderer(ModBlockEntities.MANDALORIAN_FORGE.get(),
                    MandalorianForgeBlockEntityRenderer::new);
        }
    }
}
