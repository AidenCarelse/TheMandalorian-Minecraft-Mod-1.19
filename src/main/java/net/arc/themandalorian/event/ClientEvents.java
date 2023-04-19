package net.arc.themandalorian.event;

import net.arc.themandalorian.TheMandalorian;
import net.arc.themandalorian.block.entity.ModBlockEntities;
import net.arc.themandalorian.block.entity.renderer.MandalorianForgeBlockEntityRenderer;
import net.arc.themandalorian.util.KeyBinding;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

public class ClientEvents
{
    @Mod.EventBusSubscriber(modid = TheMandalorian.MOD_ID, value = Dist.CLIENT)
    public static class ClientForgeEvents
    {
        @SubscribeEvent
        public static void onKeyInput(InputEvent.Key event)
        {
            if(KeyBinding.OPEN_GADGETS_KEY.consumeClick())
            {
                Minecraft.getInstance().player.sendSystemMessage(Component.literal("TEMP: Opened gagdets menu"));
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
