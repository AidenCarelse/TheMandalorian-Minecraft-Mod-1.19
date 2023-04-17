package net.arc.themandalorian.integration;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.arc.themandalorian.TheMandalorian;
import net.arc.themandalorian.recipe.MandalorianForgeRecipe;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeManager;

import java.util.List;
import java.util.Objects;

@JeiPlugin
public class JEITheMandalorianPlugin implements IModPlugin
{
    public static RecipeType<MandalorianForgeRecipe> FORGING_TYPE =
            new RecipeType<>(MandalorianForgeRecipeCategory.UID, MandalorianForgeRecipe.class);

    @Override
    public ResourceLocation getPluginUid() {
        return new ResourceLocation(TheMandalorian.MOD_ID, "jei_plugin");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(new
                MandalorianForgeRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        RecipeManager rm = Objects.requireNonNull(Minecraft.getInstance().level).getRecipeManager();

        List<MandalorianForgeRecipe> recipesInfusing = rm.getAllRecipesFor(MandalorianForgeRecipe.Type.INSTANCE);
        registration.addRecipes(FORGING_TYPE, recipesInfusing);
    }
}
