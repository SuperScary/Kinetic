package superscary.kinetic.datagen;

import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;
import org.jetbrains.annotations.NotNull;
import superscary.kinetic.Kinetic;
import superscary.kinetic.datagen.recipes.CompressorRecipeGen;
import superscary.kinetic.datagen.recipes.SawmillRecipeGen;
import superscary.kinetic.register.KineticBlocks;
import superscary.kinetic.register.KineticItems;

import java.util.List;
import java.util.function.Consumer;

public class KineticRecipeProvider extends RecipeProvider implements IConditionBuilder
{

    private static final List<ItemLike> DURACITE_TABLE = List.of(KineticBlocks.DURACITE_ORE.get(), KineticBlocks.DEEPSLATE_DURACITE_ORE.get(), KineticItems.RAW_DURACITE.get(), KineticItems.DURACITE_DUST.get());
    private static final List<ItemLike> DURACITE_BLOCK_TABLE = List.of(KineticBlocks.RAW_DURACITE_BLOCK.get());
    public static final List<ItemLike> STEEL_TABLE = List.of(KineticItems.STEEL_DUST.get());
    public static final List<ItemLike> SULFUR_TABLE = List.of(KineticBlocks.SULFUR_ORE.get());

    public KineticRecipeProvider (PackOutput pOutput)
    {
        super(pOutput);
    }

    @Override
    protected void buildRecipes (@NotNull Consumer<FinishedRecipe> pWriter)
    {
        addBlockRecipes(pWriter);
        addItemRecipes(pWriter);
        addSmeltingRecipes(pWriter);
        addSawmillRecipes(pWriter);
        addCompressingRecipes(pWriter);
    }

    protected static void addBlockRecipes (Consumer<FinishedRecipe> pWriter)
    {
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, KineticBlocks.RAW_DURACITE_BLOCK.get())
                .pattern("AAA")
                .pattern("AAA")
                .pattern("AAA")
                .define('A', KineticItems.RAW_DURACITE.get()).unlockedBy("has_log", inventoryTrigger(ItemPredicate.Builder.item().of(ItemTags.LOGS).build())).save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, KineticBlocks.DURACITE_BLOCK.get())
                .pattern("AAA")
                .pattern("AAA")
                .pattern("AAA")
                .define('A', KineticItems.DURACITE_INGOT.get()).unlockedBy("has_log", inventoryTrigger(ItemPredicate.Builder.item().of(ItemTags.LOGS).build())).save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, KineticBlocks.STEEL_BLOCK.get())
                .pattern("AAA")
                .pattern("AAA")
                .pattern("AAA")
                .define('A', KineticItems.STEEL_INGOT.get()).unlockedBy("has_log", inventoryTrigger(ItemPredicate.Builder.item().of(ItemTags.LOGS).build())).save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, KineticBlocks.BATTERY_FRAME.get())
                .pattern("DSD")
                .pattern("SMS")
                .pattern("DSD")
                .define('D', KineticItems.DURACITE_INGOT.get())
                .define('S', KineticItems.STEEL_INGOT.get())
                .define('M', KineticBlocks.MACHINE_FRAME.get()).unlockedBy("has_log", inventoryTrigger(ItemPredicate.Builder.item().of(ItemTags.LOGS).build())).save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, KineticBlocks.MACHINE_FRAME.get())
                .pattern("SIS")
                .pattern("IGI")
                .pattern("SIS")
                .define('S', KineticItems.STEEL_INGOT.get())
                .define('I', Tags.Items.INGOTS_IRON)
                .define('G', Tags.Items.GLASS).unlockedBy("has_log", inventoryTrigger(ItemPredicate.Builder.item().of(ItemTags.LOGS).build())).save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, KineticBlocks.UNFILLED_QUANTUM_SATELLITE.get())
                .pattern("APH")
                .pattern("STS")
                .pattern("MMM")
                .define('A', Items.NETHERITE_AXE)
                .define('P', Items.NETHERITE_PICKAXE)
                .define('H', Items.NETHERITE_SHOVEL)
                .define('S', Items.NETHER_STAR)
                .define('T', KineticBlocks.TANK.get())
                .define('M', KineticBlocks.MACHINE_FRAME.get()).unlockedBy("has_log", inventoryTrigger(ItemPredicate.Builder.item().of(ItemTags.LOGS).build())).save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, KineticBlocks.PLASTIC_BLOCK.get())
                .pattern("AAA")
                .pattern("AAA")
                .pattern("AAA")
                .define('A', KineticItems.PLASTIC.get()).unlockedBy("has_log", inventoryTrigger(ItemPredicate.Builder.item().of(ItemTags.LOGS).build())).save(pWriter);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, KineticBlocks.BRICK.get(), 1)
                .requires(Blocks.BRICKS).unlockedBy("has_log", inventoryTrigger(ItemPredicate.Builder.item().of(ItemTags.LOGS).build())).save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, KineticBlocks.BRICK_STAIRS.get())
                .pattern("A  ")
                .pattern("AA ")
                .pattern("AAA")
                .define('A', KineticBlocks.BRICK.get()).unlockedBy("has_log", inventoryTrigger(ItemPredicate.Builder.item().of(ItemTags.LOGS).build())).save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, KineticBlocks.BRICK_SLAB.get())
                .pattern("AAA")
                .define('A', KineticBlocks.BRICK_SLAB.get()).unlockedBy("has_log", inventoryTrigger(ItemPredicate.Builder.item().of(ItemTags.LOGS).build())).save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, KineticBlocks.DRAFTING_TABLE.get())
                .pattern("SSS")
                .pattern("WBW")
                .pattern("WWW")
                .define('S', ItemTags.SLABS)
                .define('W', ItemTags.PLANKS)
                .define('B', ItemTags.LOGS).unlockedBy("has_log", inventoryTrigger(ItemPredicate.Builder.item().of(ItemTags.LOGS).build())).save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, KineticBlocks.TANK.get())
                .pattern("CCC")
                .pattern("CGC")
                .pattern("SSS")
                .define('C', Items.COPPER_INGOT)
                .define('G', Tags.Items.GLASS)
                .define('S', KineticItems.STEEL_INGOT.get()).unlockedBy("has_log", inventoryTrigger(ItemPredicate.Builder.item().of(ItemTags.LOGS).build())).save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, KineticBlocks.QUANTUM_QUARRY.get())
                .pattern("DPD")
                .pattern("GMG")
                .pattern("SSS")
                .define('D', Items.DIAMOND)
                .define('P', Items.NETHERITE_PICKAXE)
                .define('G', KineticItems.STEEL_GEAR.get())
                .define('M', KineticBlocks.MACHINE_FRAME.get())
                .define('S', KineticBlocks.STEEL_BLOCK.get()).unlockedBy("has_log", inventoryTrigger(ItemPredicate.Builder.item().of(ItemTags.LOGS).build())).save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, KineticBlocks.COMPRESSOR.get())
                .pattern("GCG")
                .pattern("PMP")
                .pattern("SSS")
                .define('G', KineticItems.STEEL_GEAR.get())
                .define('C', KineticItems.FLUX_COIL.get())
                .define('P', Blocks.PISTON)
                .define('M', KineticBlocks.MACHINE_FRAME.get())
                .define('S', ItemTags.STONE_CRAFTING_MATERIALS).unlockedBy("has_log", inventoryTrigger(ItemPredicate.Builder.item().of(ItemTags.LOGS).build())).save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, KineticBlocks.EXTRACTOR.get())
                .pattern(" C ")
                .pattern("TMT")
                .pattern("SSS")
                .define('C', KineticItems.FLUX_COIL.get())
                .define('T', KineticBlocks.TANK.get())
                .define('M', KineticBlocks.MACHINE_FRAME.get())
                .define('S', ItemTags.STONE_CRAFTING_MATERIALS).unlockedBy("has_log", inventoryTrigger(ItemPredicate.Builder.item().of(ItemTags.LOGS).build())).save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, KineticBlocks.SAWMILL.get())
                .pattern(" C ")
                .pattern("TMT")
                .pattern("SSS")
                .define('C', KineticItems.FLUX_COIL.get())
                .define('T', Items.IRON_SWORD)
                .define('M', KineticBlocks.MACHINE_FRAME.get())
                .define('S', ItemTags.STONE_CRAFTING_MATERIALS).unlockedBy("has_log", inventoryTrigger(ItemPredicate.Builder.item().of(ItemTags.LOGS).build())).save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, KineticBlocks.CRUSHER.get())
                .pattern(" C ")
                .pattern("TMG")
                .pattern("SSS")
                .define('C', KineticItems.FLUX_COIL.get())
                .define('T', Blocks.ANVIL)
                .define('G', KineticItems.STEEL_GEAR.get())
                .define('M', KineticBlocks.MACHINE_FRAME.get())
                .define('S', ItemTags.STONE_CRAFTING_MATERIALS).unlockedBy("has_log", inventoryTrigger(ItemPredicate.Builder.item().of(ItemTags.LOGS).build())).save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, KineticBlocks.COAL_GENERATOR.get())
                .pattern("RCR")
                .pattern("FMF")
                .pattern("SSS")
                .define('R', Items.REDSTONE)
                .define('F', Blocks.FURNACE)
                .define('M', KineticBlocks.MACHINE_FRAME.get())
                .define('C', KineticItems.FLUX_COIL.get())
                .define('S', ItemTags.STONE_CRAFTING_MATERIALS).unlockedBy("has_log", inventoryTrigger(ItemPredicate.Builder.item().of(ItemTags.LOGS).build())).save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, KineticBlocks.CHARGER.get())
                .pattern("CCC")
                .pattern("PMP")
                .pattern("AAA")
                .define('C', KineticItems.FLUX_COIL.get())
                .define('P', KineticItems.CAPACITOR.get())
                .define('M', KineticBlocks.MACHINE_FRAME.get())
                .define('A', KineticBlocks.DURACITE_BLOCK.get());

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, KineticBlocks.BASIC_POWER_CABLE_BLOCK.get(), 8)
                .pattern("RRR")
                .pattern("DSD")
                .pattern("RRR")
                .define('R', KineticItems.RUBBER.get())
                .define('D', KineticItems.DURACITE_INGOT.get())
                .define('S', Items.REDSTONE).unlockedBy("has_log", inventoryTrigger(ItemPredicate.Builder.item().of(ItemTags.LOGS).build())).save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, KineticBlocks.STANDARD_POWER_CABLE_BLOCK.get())
                .pattern("RRR")
                .pattern("DSD")
                .pattern("RRR")
                .define('R', KineticItems.RUBBER.get())
                .define('D', Items.IRON_INGOT)
                .define('S', KineticBlocks.BASIC_POWER_CABLE_BLOCK.get()).unlockedBy("has_log", inventoryTrigger(ItemPredicate.Builder.item().of(ItemTags.LOGS).build())).save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, KineticBlocks.PREMIUM_POWER_CABLE_BLOCK.get())
                .pattern("RRR")
                .pattern("DSD")
                .pattern("RRR")
                .define('R', KineticItems.RUBBER.get())
                .define('D', Items.GOLD_INGOT)
                .define('S', KineticBlocks.STANDARD_POWER_CABLE_BLOCK.get()).unlockedBy("has_log", inventoryTrigger(ItemPredicate.Builder.item().of(ItemTags.LOGS).build())).save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, KineticBlocks.DELUXE_POWER_CABLE_BLOCK.get())
                .pattern("RRR")
                .pattern("DSD")
                .pattern("RRR")
                .define('R', KineticItems.RUBBER.get())
                .define('D', Items.DIAMOND)
                .define('S', KineticBlocks.PREMIUM_POWER_CABLE_BLOCK.get()).unlockedBy("has_log", inventoryTrigger(ItemPredicate.Builder.item().of(ItemTags.LOGS).build())).save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, KineticBlocks.ULTIMATE_POWER_CABLE_BLOCK.get())
                .pattern("RRR")
                .pattern("DSD")
                .pattern("RRR")
                .define('R', KineticItems.RUBBER.get())
                .define('D', Items.NETHERITE_INGOT)
                .define('S', KineticBlocks.DELUXE_POWER_CABLE_BLOCK.get()).unlockedBy("has_log", inventoryTrigger(ItemPredicate.Builder.item().of(ItemTags.LOGS).build())).save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, KineticBlocks.FACADE_BLOCK.get(), 4)
                .pattern(" S ")
                .pattern("SDS")
                .pattern(" S ")
                .define('S', Blocks.STONE)
                .define('D', KineticItems.DURACITE_INGOT.get()).unlockedBy("has_log", inventoryTrigger(ItemPredicate.Builder.item().of(ItemTags.LOGS).build())).save(pWriter);

        //REACTOR_FRAME
        //REACTOR_CORE
        //REACTOR_POWER_TAP_BLOCK
        //REACTOR_FLUID_PORT

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, KineticBlocks.PRINTER_BLOCK.get())
                .pattern(" C ")
                .pattern("LMP")
                .pattern("SSS")
                .define('C', KineticItems.FLUX_COIL.get())
                .define('L', Blocks.MAGMA_BLOCK)
                .define('M', KineticBlocks.MACHINE_FRAME.get())
                .define('P', KineticBlocks.PLASTIC_BLOCK.get())
                .define('S', KineticItems.STEEL_INGOT.get()).unlockedBy("has_log", inventoryTrigger(ItemPredicate.Builder.item().of(ItemTags.LOGS).build())).save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, KineticBlocks.INSCRIBER_BLOCK.get())
                .pattern(" C ")
                .pattern("BMB")
                .pattern("SSS")
                .define('C', KineticItems.FLUX_COIL.get())
                .define('B', Blocks.BOOKSHELF)
                .define('M', KineticBlocks.MACHINE_FRAME.get())
                .define('S', ItemTags.PLANKS).unlockedBy("has_log", inventoryTrigger(ItemPredicate.Builder.item().of(ItemTags.LOGS).build())).save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, KineticBlocks.BASIC_SOLAR_PANEL.get())
                .pattern("PPP")
                .pattern("SMS")
                .pattern("SSS")
                .define('P', KineticItems.BASIC_PHOTOVOLTAIC_CELL.get())
                .define('S', KineticItems.STEEL_INGOT.get())
                .define('M', KineticBlocks.MACHINE_FRAME.get()).unlockedBy("has_log", inventoryTrigger(ItemPredicate.Builder.item().of(ItemTags.LOGS).build())).save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, KineticBlocks.STANDARD_SOLAR_PANEL.get())
                .pattern("PPP")
                .pattern("SMS")
                .pattern("SSS")
                .define('P', KineticItems.STANDARD_PHOTOVOLTAIC_CELL.get())
                .define('S', KineticItems.STEEL_INGOT.get())
                .define('M', KineticBlocks.BASIC_SOLAR_PANEL.get()).unlockedBy("has_log", inventoryTrigger(ItemPredicate.Builder.item().of(ItemTags.LOGS).build())).save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, KineticBlocks.PREMIUM_SOLAR_PANEL.get())
                .pattern("PPP")
                .pattern("SMS")
                .pattern("SSS")
                .define('P', KineticItems.PREMIUM_PHOTOVOLTAIC_CELL.get())
                .define('S', KineticItems.STEEL_INGOT.get())
                .define('M', KineticBlocks.STANDARD_SOLAR_PANEL.get()).unlockedBy("has_log", inventoryTrigger(ItemPredicate.Builder.item().of(ItemTags.LOGS).build())).save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, KineticBlocks.DELUXE_SOLAR_PANEL.get())
                .pattern("PPP")
                .pattern("SMS")
                .pattern("SSS")
                .define('P', KineticItems.DELUXE_PHOTOVOLTAIC_CELL.get())
                .define('S', KineticItems.STEEL_INGOT.get())
                .define('M', KineticBlocks.PREMIUM_SOLAR_PANEL.get()).unlockedBy("has_log", inventoryTrigger(ItemPredicate.Builder.item().of(ItemTags.LOGS).build())).save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, KineticBlocks.ULTIMATE_SOLAR_PANEL.get())
                .pattern("PPP")
                .pattern("SMS")
                .pattern("SSS")
                .define('P', KineticItems.ULTIMATE_PHOTOVOLTAIC_CELL.get())
                .define('S', KineticItems.STEEL_INGOT.get())
                .define('M', KineticBlocks.DELUXE_SOLAR_PANEL.get()).unlockedBy("has_log", inventoryTrigger(ItemPredicate.Builder.item().of(ItemTags.LOGS).build())).save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, KineticBlocks.BASIC_BATTERY.get())
                .pattern("SSS")
                .pattern("CMC")
                .pattern("SSS")
                .define('S', KineticItems.STEEL_INGOT.get())
                .define('C', KineticItems.CAPACITOR.get())
                .define('M', KineticBlocks.MACHINE_FRAME.get()).unlockedBy("has_log", inventoryTrigger(ItemPredicate.Builder.item().of(ItemTags.LOGS).build())).save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, KineticBlocks.WOOD_CASING.get())
                .pattern("WSW")
                .pattern("SSS")
                .pattern("WSW")
                .define('W', ItemTags.PLANKS)
                .define('S', Tags.Items.RODS_WOODEN).unlockedBy("has_log", inventoryTrigger(ItemPredicate.Builder.item().of(ItemTags.LOGS).build())).save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, KineticBlocks.STONE_CASING.get())
                .pattern("WWW")
                .pattern("WSW")
                .pattern("WWW")
                .define('W', ItemTags.STONE_CRAFTING_MATERIALS)
                .define('S', KineticBlocks.WOOD_CASING.get()).unlockedBy("has_log", inventoryTrigger(ItemPredicate.Builder.item().of(ItemTags.LOGS).build())).save(pWriter);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, KineticBlocks.RUBBER_PLANKS.get())
                .requires(KineticBlocks.RUBBER_LOG.get()).unlockedBy("has_log", inventoryTrigger(ItemPredicate.Builder.item().of(ItemTags.LOGS).build())).save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.BREWING, KineticBlocks.VAT.get())
                .pattern("S S")
                .pattern("S S")
                .pattern("SSS")
                .define('S', KineticItems.STEEL_INGOT.get()).unlockedBy("has_log", inventoryTrigger(ItemPredicate.Builder.item().of(ItemTags.LOGS).build())).save(pWriter);

    }

    protected static void addItemRecipes (Consumer<FinishedRecipe> pWriter)
    {
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, KineticItems.DURACITE_INGOT.get())
                .pattern("AAA")
                .pattern("AAA")
                .pattern("AAA")
                .define('A', KineticItems.DURACITE_NUGGET.get()).unlockedBy("has_log", inventoryTrigger(ItemPredicate.Builder.item().of(ItemTags.LOGS).build())).save(pWriter);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, KineticItems.DURACITE_NUGGET.get(), 9)
                .requires(KineticItems.DURACITE_INGOT.get()).unlockedBy("has_log", inventoryTrigger(ItemPredicate.Builder.item().of(ItemTags.LOGS).build())).save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, KineticItems.STEEL_INGOT.get())
                .pattern("AAA")
                .pattern("AAA")
                .pattern("AAA")
                .define('A', KineticItems.STEEL_NUGGET.get()).unlockedBy("has_log", inventoryTrigger(ItemPredicate.Builder.item().of(ItemTags.LOGS).build())).save(pWriter);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, KineticItems.STEEL_NUGGET.get(), 9)
                .requires(KineticItems.STEEL_INGOT.get()).unlockedBy("has_log", inventoryTrigger(ItemPredicate.Builder.item().of(ItemTags.LOGS).build())).save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, KineticItems.WOOD_GEAR.get())
                .pattern("WWW")
                .pattern("WSW")
                .pattern("WWW")
                .define('W', ItemTags.PLANKS)
                .define('S', Items.STICK).unlockedBy("has_log", inventoryTrigger(ItemPredicate.Builder.item().of(ItemTags.LOGS).build())).save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, KineticItems.STONE_GEAR.get())
                .pattern(" S ")
                .pattern("SGS")
                .pattern(" S ")
                .define('S', ItemTags.STONE_CRAFTING_MATERIALS)
                .define('G', KineticItems.WOOD_GEAR.get()).unlockedBy("has_log", inventoryTrigger(ItemPredicate.Builder.item().of(ItemTags.LOGS).build())).save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, KineticItems.IRON_GEAR.get())
                .pattern(" S ")
                .pattern("SGS")
                .pattern(" S ")
                .define('S', Items.IRON_INGOT)
                .define('G', KineticItems.STONE_GEAR.get()).unlockedBy("has_log", inventoryTrigger(ItemPredicate.Builder.item().of(ItemTags.LOGS).build())).save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, KineticItems.STEEL_GEAR.get())
                .pattern(" S ")
                .pattern("SGS")
                .pattern(" S ")
                .define('S', KineticItems.STEEL_INGOT.get())
                .define('G', KineticItems.IRON_GEAR.get()).unlockedBy("has_log", inventoryTrigger(ItemPredicate.Builder.item().of(ItemTags.LOGS).build())).save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, KineticItems.FLUX_COIL.get())
                .pattern("CS ")
                .pattern("C S")
                .pattern("CS ")
                .define('C', Items.COPPER_INGOT)
                .define('S', KineticItems.STEEL_INGOT.get()).unlockedBy("has_log", inventoryTrigger(ItemPredicate.Builder.item().of(ItemTags.LOGS).build())).save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, KineticItems.CAPACITOR.get())
                .pattern(" R ")
                .pattern("DRD")
                .pattern("SRS")
                .define('R', Items.REDSTONE)
                .define('D', KineticItems.DURACITE_INGOT.get())
                .define('S', KineticItems.SULFUR.get()).unlockedBy("has_log", inventoryTrigger(ItemPredicate.Builder.item().of(ItemTags.LOGS).build())).save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, KineticItems.UPGRADE_BASE.get())
                .pattern("NNG")
                .pattern("NNG")
                .pattern("NNG")
                .define('N', Items.QUARTZ)
                .define('G', Items.GOLD_INGOT).unlockedBy("has_log", inventoryTrigger(ItemPredicate.Builder.item().of(ItemTags.LOGS).build())).save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, KineticItems.SPEED_UPGRADE.get())
                .pattern("UR")
                .define('U', KineticItems.UPGRADE_BASE.get())
                .define('R', Items.LAPIS_LAZULI).unlockedBy("has_log", inventoryTrigger(ItemPredicate.Builder.item().of(ItemTags.LOGS).build())).save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, KineticItems.POWER_EFFICIENCY_UPGRADE.get())
                .pattern("UR")
                .define('U', KineticItems.UPGRADE_BASE.get())
                .define('R', Items.REDSTONE).unlockedBy("has_log", inventoryTrigger(ItemPredicate.Builder.item().of(ItemTags.LOGS).build())).save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, KineticItems.MAGNET.get())
                .pattern("RS ")
                .pattern("  S")
                .pattern("LS ")
                .define('R', Items.REDSTONE)
                .define('S', KineticItems.STEEL_INGOT.get())
                .define('L', Items.LAPIS_LAZULI).unlockedBy("has_log", inventoryTrigger(ItemPredicate.Builder.item().of(ItemTags.LOGS).build())).save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, KineticItems.CONFIGURATION_WIZARD.get())
                .pattern(" R ")
                .pattern("PGP")
                .pattern("PSP")
                .define('R', Items.REDSTONE)
                .define('P', KineticItems.PLASTIC.get())
                .define('G', Tags.Items.GLASS)
                .define('S', KineticItems.STEEL_INGOT.get()).unlockedBy("has_log", inventoryTrigger(ItemPredicate.Builder.item().of(ItemTags.LOGS).build())).save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, KineticItems.SD_CARD.get())
                .pattern("PPP")
                .pattern("PPP")
                .pattern("GGG")
                .define('P', KineticItems.PLASTIC.get())
                .define('G', Items.GOLD_INGOT).unlockedBy("has_log", inventoryTrigger(ItemPredicate.Builder.item().of(ItemTags.LOGS).build())).save(pWriter);

        //BLUEPRINT

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, KineticItems.BASIC_PHOTOVOLTAIC_CELL.get())
                .pattern("CGC")
                .pattern("SRS")
                .pattern("SSS")
                .define('S', KineticItems.STEEL_INGOT.get())
                .define('G', Blocks.BLUE_STAINED_GLASS_PANE)
                .define('R', Items.REDSTONE)
                .define('C', KineticItems.FLUX_COIL.get()).unlockedBy("has_log", inventoryTrigger(ItemPredicate.Builder.item().of(ItemTags.LOGS).build())).save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, KineticItems.STANDARD_PHOTOVOLTAIC_CELL.get())
                .pattern("CPC")
                .pattern("PRP")
                .pattern("SSS")
                .define('S', KineticItems.STEEL_INGOT.get())
                .define('P', KineticItems.BASIC_PHOTOVOLTAIC_CELL.get())
                .define('R', Items.REDSTONE)
                .define('C', KineticItems.FLUX_COIL.get()).unlockedBy("has_log", inventoryTrigger(ItemPredicate.Builder.item().of(ItemTags.LOGS).build())).save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, KineticItems.PREMIUM_PHOTOVOLTAIC_CELL.get())
                .pattern("CPC")
                .pattern("PRP")
                .pattern("SSS")
                .define('S', KineticItems.STEEL_INGOT.get())
                .define('P', KineticItems.STANDARD_PHOTOVOLTAIC_CELL.get())
                .define('R', Items.REDSTONE)
                .define('C', KineticItems.FLUX_COIL.get()).unlockedBy("has_log", inventoryTrigger(ItemPredicate.Builder.item().of(ItemTags.LOGS).build())).save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, KineticItems.DELUXE_PHOTOVOLTAIC_CELL.get())
                .pattern("CPC")
                .pattern("PRP")
                .pattern("SSS")
                .define('S', KineticItems.STEEL_INGOT.get())
                .define('P', KineticItems.PREMIUM_PHOTOVOLTAIC_CELL.get())
                .define('R', Items.REDSTONE)
                .define('C', KineticItems.FLUX_COIL.get()).unlockedBy("has_log", inventoryTrigger(ItemPredicate.Builder.item().of(ItemTags.LOGS).build())).save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, KineticItems.ULTIMATE_PHOTOVOLTAIC_CELL.get())
                .pattern("CPC")
                .pattern("PRP")
                .pattern("SSS")
                .define('S', KineticItems.STEEL_INGOT.get())
                .define('P', KineticItems.DELUXE_PHOTOVOLTAIC_CELL.get())
                .define('R', Items.REDSTONE)
                .define('C', KineticItems.FLUX_COIL.get()).unlockedBy("has_log", inventoryTrigger(ItemPredicate.Builder.item().of(ItemTags.LOGS).build())).save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, KineticItems.STEEL_PLATE.get())
                .pattern("IP")
                .define('I', KineticItems.STEEL_INGOT.get())
                .define('P', KineticItems.ENGINEERS_HAMMER.get()).unlockedBy("has_log", inventoryTrigger(ItemPredicate.Builder.item().of(ItemTags.LOGS).build())).save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, KineticItems.COPPER_PLATE.get())
                .pattern("IP")
                .define('I', Items.COPPER_INGOT)
                .define('P', KineticItems.ENGINEERS_HAMMER.get()).unlockedBy("has_log", inventoryTrigger(ItemPredicate.Builder.item().of(ItemTags.LOGS).build())).save(pWriter);

        //ORE_FINDER
        //WRENCH
        //SCREWDRIVER
        //ENGINEERS_HAMMER

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, KineticItems.RAW_DOUGH.get())
                .pattern("DW")
                .define('D', KineticItems.WHEAT_DUST.get())
                .define('W', Items.WATER_BUCKET).unlockedBy("has_log", inventoryTrigger(ItemPredicate.Builder.item().of(ItemTags.LOGS).build())).save(pWriter);

    }

    protected static void addSawmillRecipes (Consumer<FinishedRecipe> pWriter)
    {
        new SawmillRecipeGen(KineticBlocks.RUBBER_LOG.get(), KineticBlocks.RUBBER_PLANKS.get(), 6, 35, 150, KineticItems.SAWDUST.get(), 0.5f).unlockedBy("has_log", inventoryTrigger(ItemPredicate.Builder.item().of(ItemTags.LOGS).build())).save(pWriter);
    }

    protected static void addCompressingRecipes (Consumer<FinishedRecipe> pWriter)
    {
        new CompressorRecipeGen(KineticItems.RAW_PLASTIC.get(), KineticItems.PLASTIC.get(), 2, 45, 300).unlockedBy("has_log", inventoryTrigger(ItemPredicate.Builder.item().of(ItemTags.LOGS).build())).save(pWriter);
        new CompressorRecipeGen(KineticItems.STEEL_INGOT.get(), KineticItems.STEEL_PLATE.get(), 2, 65, 320).unlockedBy("has_log", inventoryTrigger(ItemPredicate.Builder.item().of(ItemTags.LOGS).build())).save(pWriter);
        new CompressorRecipeGen(Items.COPPER_INGOT, KineticItems.COPPER_PLATE.get(), 2, 65, 305).unlockedBy("has_log", inventoryTrigger(ItemPredicate.Builder.item().of(ItemTags.LOGS).build())).save(pWriter);
        new CompressorRecipeGen(KineticBlocks.RUBBER_LOG.get(), KineticItems.UNTREATED_RUBBER.get(), 3, 43, 280).unlockedBy("has_log", inventoryTrigger(ItemPredicate.Builder.item().of(ItemTags.LOGS).build())).save(pWriter);
    }

    protected static void addSmeltingRecipes (Consumer<FinishedRecipe> pWriter)
    {
        oreSmelting(pWriter, DURACITE_TABLE, RecipeCategory.MISC, KineticItems.DURACITE_INGOT.get(), 0.25f, 200, "duracite");
        oreSmelting(pWriter, SULFUR_TABLE, RecipeCategory.MISC, KineticItems.SULFUR.get(), 0.25f, 200, "sulfur");
        oreSmelting(pWriter, STEEL_TABLE, RecipeCategory.MISC, KineticItems.STEEL_INGOT.get(), 0.25f, 400, "steel");
        oreSmelting(pWriter, DURACITE_BLOCK_TABLE, RecipeCategory.MISC, KineticBlocks.DURACITE_BLOCK.get(), 2.25f, 1800, "duracite");
    }

    protected static void oreSmelting (@NotNull Consumer<FinishedRecipe> pFinishedRecipeConsumer, List<ItemLike> pIngredients, RecipeCategory pCategory, @NotNull ItemLike pResult, float pExperience, int pCookTime, String pGroup)
    {
        oreCooking(pFinishedRecipeConsumer, RecipeSerializer.SMELTING_RECIPE, pIngredients, pCategory, pResult, pExperience, pCookTime, pGroup, "_smelting");
    }

    protected static void oreBlasting (@NotNull Consumer<FinishedRecipe> pFinishedRecipeConsumer, List<ItemLike> pIngredients, RecipeCategory pCategory, @NotNull ItemLike pResult, float pExperience, int pCookTime, String pGroup)
    {
        oreCooking(pFinishedRecipeConsumer, RecipeSerializer.BLASTING_RECIPE, pIngredients, pCategory, pResult, pExperience, pCookTime, pGroup, "_blasting");
    }

    protected static void oreCooking (Consumer<FinishedRecipe> pFinishedRecipeConsumer, RecipeSerializer<? extends AbstractCookingRecipe> pCookingSerializer, List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult, float pExperience, int pCookTime, String pGroup, String pRecipeName)
    {
        for (ItemLike itemLike : pIngredients)
        {
            SimpleCookingRecipeBuilder.generic(Ingredient.of(itemLike), pCategory, pResult, pExperience, pCookTime, pCookingSerializer).group(pGroup).unlockedBy(getHasName(itemLike), has(itemLike)).save(pFinishedRecipeConsumer, Kinetic.MODID + ":" + getItemName(pResult) + pRecipeName + "_" + getItemName(itemLike));
        }
    }

}
