package superscary.kinetic.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import superscary.kinetic.Kinetic;

public class SawmillRecipe implements Recipe<SimpleContainer>
{

    private final NonNullList<Ingredient> inputItems;
    private final ItemStack output;
    private final ResourceLocation id;

    public SawmillRecipe (NonNullList<Ingredient> inputItems, ItemStack output, ResourceLocation id)
    {
        this.inputItems = inputItems;
        this.output = output;
        this.id = id;
    }

    @Override
    public boolean matches (SimpleContainer container, Level level)
    {
        if (level.isClientSide())
        {
            return false;
        }

        return inputItems.get(0).test(container.getItem(0));
    }

    @Override
    public NonNullList<Ingredient> getIngredients ()
    {
        return inputItems;
    }

    @Override
    public ItemStack assemble (SimpleContainer container, RegistryAccess registryAccess)
    {
        return output.copy();
    }

    @Override
    public boolean canCraftInDimensions (int width, int height)
    {
        return true;
    }

    @Override
    public ItemStack getResultItem (RegistryAccess registryAccess)
    {
        return output.copy();
    }

    @Override
    public ResourceLocation getId ()
    {
        return id;
    }

    @Override
    public RecipeSerializer<?> getSerializer ()
    {
        return Serializer.INSTANCE;
    }

    @Override
    public RecipeType<?> getType ()
    {
        return Type.INSTANCE;
    }

    public static class Type implements RecipeType<SawmillRecipe>
    {
        public static final Type INSTANCE = new Type();
        public static final String ID = "sawmill";
    }

    public static class Serializer implements RecipeSerializer<SawmillRecipe>
    {

        public static final Serializer INSTANCE = new Serializer();
        public static final ResourceLocation ID = new ResourceLocation(Kinetic.MODID, "sawmill");

        @Override
        public SawmillRecipe fromJson (ResourceLocation recipeId, JsonObject serializedRecipe)
        {
            ItemStack output = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(serializedRecipe, "output"));
            JsonArray ingredients = GsonHelper.getAsJsonArray(serializedRecipe, "ingredients");
            NonNullList<Ingredient> inputs = NonNullList.withSize(1, Ingredient.EMPTY);

            for (int i = 0; i < inputs.size(); i++)
            {
                inputs.set(i, Ingredient.fromJson(ingredients.get(i)));
            }
            
            return new SawmillRecipe(inputs, output, recipeId);
        }

        @Override
        public @Nullable SawmillRecipe fromNetwork (ResourceLocation recipeId, FriendlyByteBuf buffer)
        {
            NonNullList<Ingredient> inputs = NonNullList.withSize(buffer.readInt(), Ingredient.EMPTY);
            for (int i = 0; i < inputs.size(); i++)
            {
                inputs.set(i, Ingredient.fromNetwork(buffer));
            }

            ItemStack output = buffer.readItem();
            return new SawmillRecipe(inputs, output, recipeId);
        }

        @Override
        public void toNetwork (FriendlyByteBuf buffer, SawmillRecipe recipe)
        {
            buffer.writeInt(recipe.inputItems.size());

            for (Ingredient ingredient : recipe.getIngredients())
            {
                ingredient.toNetwork(buffer);
            }

            buffer.writeItemStack(recipe.getResultItem(null), false);

        }
    }

}
