package superscary.kinetic.compat;

import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import superscary.kinetic.Kinetic;
import superscary.kinetic.register.KineticBlocks;
import superscary.kinetic.recipe.SawmillRecipe;

public class SawmillCategory implements IRecipeCategory<SawmillRecipe>
{

    public static final ResourceLocation UID = new ResourceLocation(Kinetic.MODID, "sawmill");
    public static final ResourceLocation TEXTURE = new ResourceLocation(Kinetic.MODID, "textures/gui/sawmill_gui.png");

    public static final RecipeType<SawmillRecipe> SAWMILL_RECIPE_TYPE = new RecipeType<>(UID, SawmillRecipe.class);
    private final IDrawable background;
    private final IDrawable icon;

    public SawmillCategory (IGuiHelper helper)
    {
        this.background = helper.createDrawable(TEXTURE, 0, 0, 176, 85);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(KineticBlocks.SAWMILL.get()));
    }

    @Override
    public RecipeType<SawmillRecipe> getRecipeType ()
    {
        return SAWMILL_RECIPE_TYPE;
    }

    @Override
    public Component getTitle ()
    {
        return Component.translatable("block.kinetic.compressor");
    }

    @Override
    public IDrawable getBackground ()
    {
        return this.background;
    }

    @Override
    public IDrawable getIcon ()
    {
        return this.icon;
    }

    @Override
    public void setRecipe (IRecipeLayoutBuilder builder, SawmillRecipe recipe, IFocusGroup focuses)
    {
        builder.addSlot(RecipeIngredientRole.INPUT, 80, 11).addIngredients(recipe.getIngredients().get(0));
        builder.addSlot(RecipeIngredientRole.OUTPUT, 80, 59).addItemStack(recipe.getResultItem(null));
    }
}
