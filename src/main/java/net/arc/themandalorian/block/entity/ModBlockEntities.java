package net.arc.themandalorian.block.entity;

import net.arc.themandalorian.TheMandalorian;
import net.arc.themandalorian.block.ModBlocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockEntities
{
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, TheMandalorian.MOD_ID);

    public static final RegistryObject<BlockEntityType<MandalorianForgeBlockEntity>> MANDALORIAN_FORGE =
            BLOCK_ENTITIES.register("mandalorian_forge", () ->
                    BlockEntityType.Builder.of(MandalorianForgeBlockEntity::new, ModBlocks.MANDALORIAN_FORGE.get())
                            .build(null));

    public static void register(IEventBus eventBus)
    {
        BLOCK_ENTITIES.register(eventBus);
    }
}
