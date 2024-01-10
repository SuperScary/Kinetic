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

public class CompressorRecipe implements Recipe<SimpleContainer>
{

    private final NonNullList<Ingredient> inputItems;
    private final ItemStack output;
    private final int energyReq;
    private final int craftTime;
    private final ResourceLocation id;

    public CompressorRecipe (NonNullList<Ingredient> inputItems, ItemStack output, int energyReq, int craftTime, ResourceLocation id)
    {
        this.inputItems = inputItems;
        this.output = output;
        this.id = id;
        this.energyReq = energyReq;
        this.craftTime = craftTime;
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

    public int getEnergyReq ()
    {
        return energyReq;
    }

    public int getCraftTime ()
    {
        return craftTime;
    }

    @Override
    public RecipeType<?> getType ()
    {
        return Type.INSTANCE;
    }

    public static class Type implements RecipeType<CompressorRecipe>
    {
        public static final Type INSTANCE = new Type();
        public static final String ID = "compressor";
    }

    public static class Serializer implements RecipeSerializer<CompressorRecipe>
    {

        public static final Serializer INSTANCE = new Serializer();
        public static final ResourceLocation ID = new ResourceLocation(Kinetic.MODID, "compressor");

        @Override
        public CompressorRecipe fromJson (ResourceLocation recipeId, JsonObject serializedRecipe)
        {
            ItemStack output = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(serializedRecipe, "output"));
            JsonArray ingredients = GsonHelper.getAsJsonArray(serializedRecipe, "ingredients");
            NonNullList<Ingredient> inputs = NonNullList.withSize(1, Ingredient.EMPTY);

            for (int i = 0; i < inputs.size(); i++)
            {
                inputs.set(i, Ingredient.fromJson(ingredients.get(i)));
            }

            int craftTime = GsonHelper.getAsInt(serializedRecipe, "craftTime");
            int energy = GsonHelper.getAsInt(serializedRecipe, "energy");
            return new CompressorRecipe(inputs, output, energy, craftTime, recipeId);
        }

        @Override
        public @Nullable CompressorRecipe fromNetwork (ResourceLocation recipeId, FriendlyByteBuf buffer)
        {
            NonNullList<Ingredient> inputs = NonNullList.withSize(buffer.readInt(), Ingredient.EMPTY);
            for (int i = 0; i < inputs.size(); i++)
            {
                inputs.set(i, Ingredient.fromNetwork(buffer));
            }

            int craftTime = buffer.readInt();
            int energy = buffer.readInt();
            ItemStack output = buffer.readItem();
            return new CompressorRecipe(inputs, output, energy, craftTime, recipeId);
        }

        @Override
        public void toNetwork (FriendlyByteBuf buffer, CompressorRecipe recipe)
        {
            buffer.writeInt(recipe.inputItems.size());

            for (Ingredient ingredient : recipe.getIngredients())
            {
                ingredient.toNetwork(buffer);
            }

            buffer.writeInt(recipe.craftTime);
            buffer.writeInt(recipe.energyReq);
            buffer.writeItemStack(recipe.getResultItem(null), false);

        }
    }

}
