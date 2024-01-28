package superscary.kinetic.register;

import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import superscary.kinetic.Kinetic;
import superscary.kinetic.recipe.CompressorRecipe;
import superscary.kinetic.recipe.SawmillRecipe;

public class KineticRecipes
{

    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, Kinetic.MODID);

    public static final RegistryObject<RecipeSerializer<CompressorRecipe>> COMPRESSOR_SERIALIZER = SERIALIZERS.register("compress", () -> CompressorRecipe.Serializer.INSTANCE);
    public static final RegistryObject<RecipeSerializer<SawmillRecipe>> SAWMILL_SERIALIZER = SERIALIZERS.register("sawmill", () -> SawmillRecipe.Serializer.INSTANCE);

}
