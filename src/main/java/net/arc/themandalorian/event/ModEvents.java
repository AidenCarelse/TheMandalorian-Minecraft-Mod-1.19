package net.arc.themandalorian.event;

import net.arc.themandalorian.TheMandalorian;
import net.arc.themandalorian.entity.ModEntityTypes;
import net.arc.themandalorian.entity.custom.GroguEntity;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

public class ModEvents
{
    @Mod.EventBusSubscriber(modid = TheMandalorian.MOD_ID)
    public static class ForgeEvents {

    }

    @Mod.EventBusSubscriber(modid = TheMandalorian.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ModEventBusEvents {
        @SubscribeEvent
        public static void entityAttributeEvent(EntityAttributeCreationEvent event) {
            event.put(ModEntityTypes.GROGU.get(), GroguEntity.setAttributes());
        }
    }
}
