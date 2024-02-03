package superscary.kinetic.datagen.recipes;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.advancements.RequirementsStrategy;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import superscary.kinetic.Kinetic;
import superscary.kinetic.recipe.CompressorRecipe;

import java.util.function.Consumer;

public class CompressorRecipeGen implements RecipeBuilder
{

    private final Item itemResult;
    private final Ingredient ingredient;
    private final int count;
    private final Advancement.Builder advancement = Advancement.Builder.advancement();
    private final int energy;
    private final int craftTime;

    public CompressorRecipeGen (ItemLike ingredient, ItemLike result, int count, int energy, int craftTime)
    {
        this.ingredient = Ingredient.of(ingredient);
        this.itemResult = result.asItem();
        this.count = count;
        this.energy = energy;
        this.craftTime = craftTime;
    }

    @Override
    public @NotNull RecipeBuilder unlockedBy (@NotNull String s, @NotNull CriterionTriggerInstance criterionTriggerInstance)
    {
        this.advancement.addCriterion(s, criterionTriggerInstance);
        return this;
    }

    @Override
    public @NotNull RecipeBuilder group (@Nullable String s)
    {
        return this;
    }

    @Override
    public Item getResult ()
    {
        return itemResult;
    }

    @Override
    public void save(Consumer<FinishedRecipe> pFinishedRecipeConsumer, @NotNull ResourceLocation pRecipeId) {
        this.advancement.parent(new ResourceLocation("recipes/root"))
                .addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(pRecipeId))
                .rewards(AdvancementRewards.Builder.recipe(pRecipeId)).requirements(RequirementsStrategy.OR);

        pFinishedRecipeConsumer.accept(new Result(pRecipeId, this.itemResult, this.count, this.energy, this.craftTime, this.ingredient,
                this.advancement, new ResourceLocation(pRecipeId.getNamespace(), "recipes/"
                + pRecipeId.getPath())));

    }

    public static class Result implements FinishedRecipe {
        private final ResourceLocation id;
        private final Item result;
        private final Ingredient ingredient;
        private final int count;
        private final int energy;
        private final int craftTime;
        private final Advancement.Builder advancement;
        private final ResourceLocation advancementId;

        public Result(ResourceLocation pId, Item pResult, int pCount, int energy, int craftTime, Ingredient ingredient, Advancement.Builder pAdvancement,
                      ResourceLocation pAdvancementId) {
            this.id = pId;
            this.result = pResult;
            this.count = pCount;
            this.energy = energy;
            this.craftTime = craftTime;
            this.ingredient = ingredient;
            this.advancement = pAdvancement;
            this.advancementId = pAdvancementId;
        }

        @Override
        public void serializeRecipeData(JsonObject pJson) {
            JsonArray jsonarray = new JsonArray();
            pJson.addProperty("energy", this.energy);
            pJson.addProperty("craftTime", this.craftTime);

            jsonarray.add(ingredient.toJson());

            pJson.add("ingredients", jsonarray);
            JsonObject jsonobject = new JsonObject();
            jsonobject.addProperty("item", ForgeRegistries.ITEMS.getKey(this.result).toString());
            if (this.count > 1) {
                jsonobject.addProperty("count", this.count);
            }

            pJson.add("output", jsonobject);
        }

        @Override
        public @NotNull ResourceLocation getId() {
            return new ResourceLocation(Kinetic.MODID,
                    ForgeRegistries.ITEMS.getKey(this.result).getPath() + "_from_compressing");
        }

        @Override
        public @NotNull RecipeSerializer<?> getType() {
            return CompressorRecipe.Serializer.INSTANCE;
        }

        @javax.annotation.Nullable
        public JsonObject serializeAdvancement() {
            return this.advancement.serializeToJson();
        }

        @javax.annotation.Nullable
        public ResourceLocation getAdvancementId() {
            return this.advancementId;
        }
    }

}
