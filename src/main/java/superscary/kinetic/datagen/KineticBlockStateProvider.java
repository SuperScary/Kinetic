package superscary.kinetic.datagen;

import com.google.gson.JsonObject;
import net.minecraft.core.Direction;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraftforge.client.model.generators.*;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import superscary.kinetic.Kinetic;
import superscary.kinetic.block.cables.util.power.basic.BasicPowerCableModelLoader;
import superscary.kinetic.register.KineticBlocks;

import java.util.function.BiConsumer;

public class KineticBlockStateProvider extends BlockStateProvider
{

    public static final ResourceLocation BOTTOM = Kinetic.getResource("block/machine_bottom");
    public static final ResourceLocation TOP = Kinetic.getResource("block/machine_top");
    public static final ResourceLocation SIDE = Kinetic.getResource("block/machine_side");
    public KineticBlockStateProvider (PackOutput output, ExistingFileHelper exFileHelper)
    {
        super(output, Kinetic.MODID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels ()
    {
        registerFacade();
        registerCable(KineticBlocks.BASIC_POWER_CABLE_BLOCK, "basic_power_cable", Kinetic.getResource("basiccableloader"));
        registerCable(KineticBlocks.STANDARD_POWER_CABLE_BLOCK, "standard_power_cable", Kinetic.getResource("standardcableloader"));
        registerCable(KineticBlocks.PREMIUM_POWER_CABLE_BLOCK, "premium_power_cable", Kinetic.getResource("premiumcableloader"));
        registerCable(KineticBlocks.DELUXE_POWER_CABLE_BLOCK, "deluxe_power_cable", Kinetic.getResource("deluxecableloader"));
        registerCable(KineticBlocks.ULTIMATE_POWER_CABLE_BLOCK, "ultimate_power_cable", Kinetic.getResource("ultimatecableloader"));

        blockWithItem(KineticBlocks.DURACITE_ORE);
        blockWithItem(KineticBlocks.DEEPSLATE_DURACITE_ORE);
        blockWithItem(KineticBlocks.RAW_DURACITE_BLOCK);
        blockWithItem(KineticBlocks.DURACITE_BLOCK);
        blockWithItem(KineticBlocks.SULFUR_ORE);
        blockWithItem(KineticBlocks.STEEL_BLOCK);
        blockWithItem(KineticBlocks.BATTERY_FRAME);
        blockWithItem(KineticBlocks.MACHINE_FRAME);
        blockWithItem(KineticBlocks.UNFILLED_QUANTUM_SATELLITE);
        blockWithItem(KineticBlocks.FILLED_QUANTUM_SATELLITE);
        blockWithItem(KineticBlocks.REACTOR_FRAME);
        blockWithItem(KineticBlocks.REACTOR_CORE);
        blockWithItem(KineticBlocks.REACTOR_POWER_TAP_BLOCK);
        blockWithItem(KineticBlocks.REACTOR_FLUID_PORT);
        blockWithItem(KineticBlocks.BASIC_BATTERY);
        blockWithItem(KineticBlocks.BRICK);
        blockWithItem(KineticBlocks.PLASTIC_BLOCK);
        blockWithItem(KineticBlocks.WOOD_CASING);
        blockWithItem(KineticBlocks.STONE_CASING);

        stairsBlock((StairBlock) KineticBlocks.BRICK_STAIRS.get(), blockTexture(KineticBlocks.BRICK.get()));
        slabBlock(((SlabBlock) KineticBlocks.BRICK_SLAB.get()), blockTexture(KineticBlocks.BRICK.get()), blockTexture(KineticBlocks.BRICK.get()));
        blockItem(KineticBlocks.BRICK_STAIRS);
        blockItem(KineticBlocks.BRICK_SLAB);

        machine(KineticBlocks.QUANTUM_QUARRY, "quantum_quarry");
        machine(KineticBlocks.COMPRESSOR, "compressor");
        machine(KineticBlocks.EXTRACTOR, "extractor");
        machine(KineticBlocks.SAWMILL, "sawmill");
        machine(KineticBlocks.CRUSHER, "crusher");
        machine(KineticBlocks.COAL_GENERATOR, "coal_generator");
        machine(KineticBlocks.CHARGER, "charger");
        machine(KineticBlocks.PRINTER_BLOCK, "printer");
        machine(KineticBlocks.INSCRIBER_BLOCK, "inscriber");

        solarPanel(KineticBlocks.BASIC_SOLAR_PANEL, "basic");
        solarPanel(KineticBlocks.STANDARD_SOLAR_PANEL, "standard");
        solarPanel(KineticBlocks.PREMIUM_SOLAR_PANEL, "premium");
        solarPanel(KineticBlocks.DELUXE_SOLAR_PANEL, "deluxe");
        solarPanel(KineticBlocks.ULTIMATE_SOLAR_PANEL, "ultimate");

        logBlock(((RotatedPillarBlock) KineticBlocks.RUBBER_LOG.get()));
        axisBlock(((RotatedPillarBlock) KineticBlocks.RUBBER_WOOD.get()), blockTexture(KineticBlocks.RUBBER_LOG.get()), blockTexture(KineticBlocks.RUBBER_LOG.get()));
        axisBlock((RotatedPillarBlock) KineticBlocks.STRIPPED_RUBBER_LOG.get(), Kinetic.getResource("block/stripped_rubber_log"), Kinetic.getResource("block/stripped_rubber_log_top"));
        axisBlock((RotatedPillarBlock) KineticBlocks.STRIPPED_RUBBER_WOOD.get(), Kinetic.getResource("block/stripped_rubber_log"), Kinetic.getResource("block/stripped_rubber_log"));

        blockItem(KineticBlocks.RUBBER_LOG);
        blockItem(KineticBlocks.RUBBER_WOOD);
        blockItem(KineticBlocks.STRIPPED_RUBBER_LOG);
        blockItem(KineticBlocks.STRIPPED_RUBBER_WOOD);
        blockWithItem(KineticBlocks.RUBBER_PLANKS);
        leavesBlock(KineticBlocks.RUBBER_LEAVES);
        saplingBlock(KineticBlocks.RUBBER_SAPLING);

        draftingTable(KineticBlocks.DRAFTING_TABLE);
    }

    private void leavesBlock(RegistryObject<Block> blockRegistryObject) {
        simpleBlockWithItem(blockRegistryObject.get(),
                models().cubeAll(ForgeRegistries.BLOCKS.getKey(blockRegistryObject.get()).getPath(), blockTexture(blockRegistryObject.get())).renderType("cutout"));
    }

    private void saplingBlock(RegistryObject<Block> blockRegistryObject) {
        simpleBlock(blockRegistryObject.get(),
                models().cross(ForgeRegistries.BLOCKS.getKey(blockRegistryObject.get()).getPath(), blockTexture(blockRegistryObject.get())).renderType("cutout"));
    }

    private void blockItem(RegistryObject<Block> blockRegistryObject, String appendix) {
        simpleBlockItem(blockRegistryObject.get(), new ModelFile.UncheckedModelFile("kinetic:block/" + ForgeRegistries.BLOCKS.getKey(blockRegistryObject.get()).getPath() + appendix));
    }

    private void blockItem(RegistryObject<Block> blockRegistryObject) {
        simpleBlockItem(blockRegistryObject.get(), new ModelFile.UncheckedModelFile("kinetic:block/" + ForgeRegistries.BLOCKS.getKey(blockRegistryObject.get()).getPath()));
    }

    private void blockWithItem(RegistryObject<Block> blockRegistryObject) {
        simpleBlockWithItem(blockRegistryObject.get(), cubeAll(blockRegistryObject.get()));
    }

    private void registerCable (RegistryObject<Block> block, String loader, ResourceLocation resourceLocation)
    {
        BlockModelBuilder model = models().getBuilder(loader)
                .parent(models().getExistingFile(mcLoc("cube")))
                .customLoader((builder, helper) -> new CableLoaderBuilder(resourceLocation, builder, helper, false))
                .end();
        simpleBlock(block.get(), model);
    }

    private void registerFacade ()
    {
        BlockModelBuilder model = models().getBuilder("facade")
                .parent(models().getExistingFile(mcLoc("cube")))
                .customLoader((builder, helper) -> new CableLoaderBuilder(BasicPowerCableModelLoader.GENERATOR_LOADER, builder, helper, true))
                .end();
        simpleBlock(KineticBlocks.FACADE_BLOCK.get(), model);
    }

    private void machine (RegistryObject<Block> block, String name)
    {
        BlockModelBuilder modelOn = models().cube("block/" + block.getId().getPath() + "/" + block.getId().getPath() + "_on", BOTTOM, TOP, modLoc("block/" + name + "/" + name + "_on"), SIDE, SIDE, SIDE).texture("particle", SIDE);
        BlockModelBuilder modelOff = models().cube("block/" + block.getId().getPath() + "/" + block.getId().getPath() + "_off", BOTTOM, TOP, modLoc("block/" + name + "/" + name + "_off"), SIDE, SIDE, SIDE).texture("particle", SIDE);
        directionBlock(block.get(), (state, builder) -> builder.modelFile(state.getValue(BlockStateProperties.POWERED) ? modelOn : modelOff));
    }

    private void draftingTable (RegistryObject<Block> block)
    {
        String path = "block/" + block.getId().getPath() + "/" + block.getId().getPath();
        BlockModelBuilder modelBuilder = models().cube(path, Kinetic.getResource(path + "_bottom"), Kinetic.getResource(path + "_top"), Kinetic.getResource(path + "_front"), Kinetic.getResource(path + "_side"), Kinetic.getResource(path + "_side"), Kinetic.getResource(path + "_side")).texture("particle", Kinetic.getResource(path + "_side"));
        directionBlock(block.get(), ((state, builder) -> builder.modelFile(modelBuilder)));
    }

    private void solarPanel (RegistryObject<Block> block, String name)
    {
        BlockModelBuilder modelOn = models().cube("block/" + "solar_panel/" + name + "/on", BOTTOM, modLoc("block/" + block.getId().getPath() + "_top_on"), SIDE, SIDE, SIDE, SIDE).texture("particle", SIDE);
        BlockModelBuilder modelOff = models().cube("block/" + "solar_panel/" + name + "/off", BOTTOM, modLoc("block/solar_panel_top_off"), SIDE, SIDE, SIDE, SIDE).texture("particle", SIDE);
        directionBlock(block.get(), (state, builder) -> builder.modelFile(state.getValue(BlockStateProperties.POWERED) ? modelOn : modelOff));
    }

    private VariantBlockStateBuilder directionBlock (Block block, BiConsumer<BlockState, ConfiguredModel.Builder<?>> model)
    {
        VariantBlockStateBuilder builder = getVariantBuilder(block);
        builder.forAllStates(state -> {
            ConfiguredModel.Builder<?> bld = ConfiguredModel.builder();
            model.accept(state, bld);
            applyRotationBld(bld, state.getValue(BlockStateProperties.FACING));
            return bld.build();
        });
        return builder;
    }

    private void applyRotationBld (ConfiguredModel.Builder<?> builder, Direction direction)
    {
        switch (direction)
        {
            case DOWN -> builder.rotationX(90);
            case UP -> builder.rotationX(-90);
            case NORTH -> {}
            case SOUTH -> builder.rotationY(180);
            case WEST -> builder.rotationY(270);
            case EAST -> builder.rotationY(90);
        }
    }

    public static class CableLoaderBuilder extends CustomLoaderBuilder<BlockModelBuilder>
    {
        private final boolean facade;

        public CableLoaderBuilder (ResourceLocation loader, BlockModelBuilder parent, ExistingFileHelper existingFileHelper, boolean facade)
        {
            super(loader, parent, existingFileHelper);
            this.facade = facade;
        }

        @Override
        public JsonObject toJson (JsonObject json)
        {
            JsonObject obj = super.toJson(json);
            obj.addProperty("facade", facade);
            return obj;
        }
    }

}
