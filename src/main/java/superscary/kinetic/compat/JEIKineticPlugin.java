package superscary.kinetic.compat;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeManager;
import superscary.kinetic.Kinetic;
import superscary.kinetic.gui.screen.CompressorScreen;
import superscary.kinetic.recipe.CompressorRecipe;
import superscary.kinetic.recipe.SawmillRecipe;

import java.util.List;

@JeiPlugin
public class JEIKineticPlugin implements IModPlugin
{

    @Override
    public ResourceLocation getPluginUid ()
    {
        return new ResourceLocation(Kinetic.MODID, "jei_plugin");
    }

    @Override
    public void registerCategories (IRecipeCategoryRegistration registration)
    {
        registration.addRecipeCategories(new CompressorCategory(registration.getJeiHelpers().getGuiHelper()));
        registration.addRecipeCategories(new SawmillCategory(registration.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void registerRecipes (IRecipeRegistration registration)
    {
        RecipeManager recipeManager = Minecraft.getInstance().level.getRecipeManager();
        List<CompressorRecipe> compressorRecipeList = recipeManager.getAllRecipesFor(CompressorRecipe.Type.INSTANCE);
        registration.addRecipes(CompressorCategory.COMPRESSOR_RECIPE_TYPE, compressorRecipeList);

        List<SawmillRecipe> sawmillRecipeList = recipeManager.getAllRecipesFor(SawmillRecipe.Type.INSTANCE);
        registration.addRecipes(SawmillCategory.SAWMILL_RECIPE_TYPE, sawmillRecipeList);
    }

    @Override
    public void registerGuiHandlers (IGuiHandlerRegistration registration)
    {
        registration.addRecipeClickArea(CompressorScreen.class, 90, 30, 20, 30, CompressorCategory.COMPRESSOR_RECIPE_TYPE);
    }

}
