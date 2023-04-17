package net.arc.themandalorian.recipe;

import net.arc.themandalorian.TheMandalorian;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModRecipes
{
    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS =
            DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, TheMandalorian.MOD_ID);

    public static final RegistryObject<RecipeSerializer<MandalorianForgeRecipe>> MANDALORIAN_FORGE_SERIALIZER =
            SERIALIZERS.register("mandalorian_forging", () -> MandalorianForgeRecipe.Serializer.INSTANCE);

    public static void register(IEventBus eventBus)
    {
        SERIALIZERS.register(eventBus);
    }
}
