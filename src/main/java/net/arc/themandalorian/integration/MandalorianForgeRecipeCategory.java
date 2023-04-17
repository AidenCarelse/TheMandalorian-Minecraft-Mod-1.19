package net.arc.themandalorian.integration;

import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.arc.themandalorian.TheMandalorian;
import net.arc.themandalorian.block.ModBlocks;
import net.arc.themandalorian.recipe.MandalorianForgeRecipe;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.SlotItemHandler;

public class MandalorianForgeRecipeCategory implements IRecipeCategory<MandalorianForgeRecipe>
{
    public final static ResourceLocation UID = new ResourceLocation(TheMandalorian.MOD_ID, "mandalorian_forging");
    public final static ResourceLocation TEXTURE =
            new ResourceLocation(TheMandalorian.MOD_ID, "textures/gui/mandalorian_forge_gui.png");

    private final IDrawable background;
    private final IDrawable icon;

    public MandalorianForgeRecipeCategory(IGuiHelper helper) {
        this.background = helper.createDrawable(TEXTURE, 0, 0, 176, 85);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ModBlocks.MANDALORIAN_FORGE.get()));
    }

    @Override
    public RecipeType<MandalorianForgeRecipe> getRecipeType() {
        return JEITheMandalorianPlugin.FORGING_TYPE;
    }

    @Override
    public Component getTitle() {
        return Component.literal("Mandalorian Forge");
    }

    @Override
    public IDrawable getBackground() {
        return this.background;
    }

    @Override
    public IDrawable getIcon() {
        return this.icon;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, MandalorianForgeRecipe recipe, IFocusGroup focuses) {
        builder.addSlot(RecipeIngredientRole.INPUT, 143, 7).addIngredients(recipe.getIngredients().get(0));
        builder.addSlot(RecipeIngredientRole.OUTPUT, 143, 63).addItemStack(recipe.getResultItem());
    }
}
