package net.arc.themandalorian.entity;

import net.arc.themandalorian.TheMandalorian;
import net.arc.themandalorian.entity.custom.GroguEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEntityTypes
{
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, TheMandalorian.MOD_ID);

    public static final RegistryObject<EntityType<GroguEntity>> GROGU =
            ENTITY_TYPES.register("grogu",
                    () -> EntityType.Builder.of(GroguEntity::new, MobCategory.MONSTER)
                            .sized(1f, 1f)
                            .build(new ResourceLocation(TheMandalorian.MOD_ID, "grogu").toString()));


    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}
