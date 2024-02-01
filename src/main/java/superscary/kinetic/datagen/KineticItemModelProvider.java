package superscary.kinetic.datagen;

import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import superscary.kinetic.Kinetic;
import superscary.kinetic.register.KineticBlocks;
import superscary.kinetic.register.KineticItems;

public class KineticItemModelProvider extends ItemModelProvider
{

    public KineticItemModelProvider (PackOutput output, ExistingFileHelper existingFileHelper)
    {
        super(output, Kinetic.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels ()
    {
        simpleItem(KineticItems.RAW_DURACITE);
        simpleItem(KineticItems.DURACITE_DUST);
        simpleItem(KineticItems.DURACITE_INGOT);
        simpleItem(KineticItems.DURACITE_NUGGET);
        simpleItem(KineticItems.STEEL_DUST);
        simpleItem(KineticItems.STEEL_INGOT);
        simpleItem(KineticItems.STEEL_NUGGET);
        simpleItem(KineticItems.WOOD_GEAR);
        simpleItem(KineticItems.STONE_GEAR);
        simpleItem(KineticItems.IRON_GEAR);
        simpleItem(KineticItems.STEEL_GEAR);
        simpleItem(KineticItems.PLASTIC_GEAR);
        simpleItem(KineticItems.PLASTIC);
        simpleItem(KineticItems.RAW_PLASTIC);
        simpleItem(KineticItems.RUBBER);
        simpleItem(KineticItems.UNTREATED_RUBBER);
        simpleItem(KineticItems.FLUX_COIL);
        simpleItem(KineticItems.SULFUR);
        simpleItem(KineticItems.CAPACITOR);
        upgrade(KineticItems.UPGRADE_BASE);
        upgrade(KineticItems.SPEED_UPGRADE);
        upgrade(KineticItems.POWER_EFFICIENCY_UPGRADE);
        simpleItem(KineticItems.MAGNET);
        simpleItem(KineticItems.CONFIGURATION_WIZARD);
        simpleItem(KineticItems.SD_CARD);
        simpleItem(KineticItems.BLUEPRINT);
        simpleItem(KineticItems.BASIC_PHOTOVOLTAIC_CELL);
        simpleItem(KineticItems.STANDARD_PHOTOVOLTAIC_CELL);
        simpleItem(KineticItems.PREMIUM_PHOTOVOLTAIC_CELL);
        simpleItem(KineticItems.DELUXE_PHOTOVOLTAIC_CELL);
        simpleItem(KineticItems.ULTIMATE_PHOTOVOLTAIC_CELL);
        simpleItem(KineticItems.ORE_FINDER);
        simpleItem(KineticItems.WRENCH);
        simpleItem(KineticItems.HONEY_BUN);
        simpleItem(KineticItems.HARD_BOILED_EGG);
        simpleItem(KineticItems.SCREWDRIVER);
        simpleItem(KineticItems.ENGINEERS_HAMMER);
        simpleItem(KineticItems.OIL_BUCKET);

        simpleBlockItem(KineticBlocks.DURACITE_ORE);
        simpleBlockItem(KineticBlocks.DEEPSLATE_DURACITE_ORE);
        simpleBlockItem(KineticBlocks.RAW_DURACITE_BLOCK);
        simpleBlockItem(KineticBlocks.DURACITE_BLOCK);
        simpleBlockItem(KineticBlocks.SULFUR_ORE);
        simpleBlockItem(KineticBlocks.STEEL_BLOCK);
        simpleBlockItem(KineticBlocks.BATTERY_FRAME);
        simpleBlockItem(KineticBlocks.MACHINE_FRAME);
        simpleBlockItem(KineticBlocks.UNFILLED_QUANTUM_SATELLITE);
        simpleBlockItem(KineticBlocks.FILLED_QUANTUM_SATELLITE);
        tank(KineticBlocks.TANK_BASIC);
        tank(KineticBlocks.TANK_STANDARD);
        tank(KineticBlocks.TANK_PREMIUM);
        tank(KineticBlocks.TANK_DELUXE);
        tank(KineticBlocks.TANK_ULTIMATE);
        blockOff(KineticBlocks.QUANTUM_QUARRY.get());
        blockOff(KineticBlocks.COMPRESSOR.get());
        blockOff(KineticBlocks.EXTRACTOR.get());
        blockOff(KineticBlocks.SAWMILL.get());
        blockOff(KineticBlocks.CRUSHER.get());
        blockOff(KineticBlocks.COAL_GENERATOR.get());
        blockOff(KineticBlocks.CHARGER.get());
        simpleBlockItem(KineticBlocks.BASIC_POWER_CABLE_BLOCK);
        simpleBlockItem(KineticBlocks.STANDARD_POWER_CABLE_BLOCK);
        simpleBlockItem(KineticBlocks.PREMIUM_POWER_CABLE_BLOCK);
        simpleBlockItem(KineticBlocks.DELUXE_POWER_CABLE_BLOCK);
        simpleBlockItem(KineticBlocks.ULTIMATE_POWER_CABLE_BLOCK);
        withExistingParent(KineticBlocks.FACADE_BLOCK.getId().getPath(), modLoc("block/facade"));
        reactor(KineticBlocks.REACTOR_FRAME);
        reactor(KineticBlocks.REACTOR_CORE);
        reactor(KineticBlocks.REACTOR_POWER_TAP_BLOCK);
        reactor(KineticBlocks.REACTOR_FLUID_PORT);
        blockOff(KineticBlocks.PRINTER_BLOCK.get());
        blockOff(KineticBlocks.INSCRIBER_BLOCK.get());
        solarPanel(KineticBlocks.BASIC_SOLAR_PANEL.get(), "basic");
        solarPanel(KineticBlocks.STANDARD_SOLAR_PANEL.get(), "standard");
        solarPanel(KineticBlocks.PREMIUM_SOLAR_PANEL.get(), "premium");
        solarPanel(KineticBlocks.DELUXE_SOLAR_PANEL.get(), "deluxe");
        solarPanel(KineticBlocks.ULTIMATE_SOLAR_PANEL.get(), "ultimate");
        simpleBlockItem(KineticBlocks.BASIC_BATTERY);
        draftingTable(KineticBlocks.DRAFTING_TABLE);
        simpleBlockItem(KineticBlocks.BRICK);
        simpleBlockItem(KineticBlocks.PLASTIC_BLOCK);

        saplingItem(KineticBlocks.RUBBER_SAPLING);

    }

    private ItemModelBuilder blockOff (Block block)
    {
        return withExistingParent(ForgeRegistries.BLOCKS.getKey(block).getPath(), Kinetic.getResource("block/" + ForgeRegistries.BLOCKS.getKey(block).getPath() + "/" + ForgeRegistries.BLOCKS.getKey(block).getPath() + "_off"));
    }

    private ItemModelBuilder solarPanel (Block block, String basicName)
    {
        return withExistingParent(ForgeRegistries.BLOCKS.getKey(block).getPath(), Kinetic.getResource("block/solar_panel/" + basicName + "/on"));
    }

    private ItemModelBuilder simpleBlockItem (RegistryObject<Block> item)
    {
        return withExistingParent(ForgeRegistries.BLOCKS.getKey(item.get()).getPath(), Kinetic.getResource("block/" + ForgeRegistries.BLOCKS.getKey(item.get()).getPath()));
    }

    private ItemModelBuilder tank (RegistryObject<Block> item)
    {
        return withExistingParent(ForgeRegistries.BLOCKS.getKey(item.get()).getPath(), Kinetic.getResource("block/tank/" + ForgeRegistries.BLOCKS.getKey(item.get()).getPath()));
    }

    private ItemModelBuilder draftingTable (RegistryObject<Block> item)
    {
        return withExistingParent(ForgeRegistries.BLOCKS.getKey(item.get()).getPath(), Kinetic.getResource("block/drafting_table/" + ForgeRegistries.BLOCKS.getKey(item.get()).getPath()));
    }

    private ItemModelBuilder reactor (RegistryObject<Block> item)
    {
        return withExistingParent(ForgeRegistries.BLOCKS.getKey(item.get()).getPath(), Kinetic.getResource("block/" + ForgeRegistries.BLOCKS.getKey(item.get()).getPath()));
    }

    private ItemModelBuilder simpleItem (RegistryObject<Item> item)
    {
        return withExistingParent(item.getId().getPath(),
                new ResourceLocation("item/generated")).texture("layer0",
                Kinetic.getResource("item/" + item.getId().getPath()));
    }

    private ItemModelBuilder upgrade (RegistryObject<Item> item)
    {
        return withExistingParent(item.getId().getPath(),
                new ResourceLocation("item/generated")).texture("layer0",
                Kinetic.getResource("item/upgrades/" + item.getId().getPath()));
    }

    private ItemModelBuilder saplingItem (RegistryObject<Block> item)
    {
        return withExistingParent(item.getId().getPath(), new ResourceLocation("item/generated")).texture("layer0",
                Kinetic.getResource("block/" + item.getId().getPath()));
    }

}
