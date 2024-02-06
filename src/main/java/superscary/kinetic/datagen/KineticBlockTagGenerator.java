package superscary.kinetic.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import superscary.kinetic.Kinetic;
import superscary.kinetic.register.KineticBlocks;

import javax.annotation.Nullable;
import java.util.concurrent.CompletableFuture;

public class KineticBlockTagGenerator extends BlockTagsProvider
{

    public KineticBlockTagGenerator (PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper)
    {
        super(output, lookupProvider, Kinetic.MODID, existingFileHelper);
    }

    @Override
    protected void addTags (HolderLookup.Provider provider)
    {
        this.tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(KineticBlocks.DURACITE_ORE.get())
                .add(KineticBlocks.DEEPSLATE_DURACITE_ORE.get())
                .add(KineticBlocks.RAW_DURACITE_BLOCK.get())
                .add(KineticBlocks.DURACITE_BLOCK.get())
                .add(KineticBlocks.SULFUR_ORE.get())
                .add(KineticBlocks.STEEL_BLOCK.get())
                .add(KineticBlocks.BATTERY_FRAME.get())
                .add(KineticBlocks.MACHINE_FRAME.get())
                .add(KineticBlocks.UNFILLED_QUANTUM_SATELLITE.get())
                .add(KineticBlocks.FILLED_QUANTUM_SATELLITE.get())
                .add(KineticBlocks.TANK.get())
                .add(KineticBlocks.QUANTUM_QUARRY.get())
                .add(KineticBlocks.COMPRESSOR.get())
                .add(KineticBlocks.EXTRACTOR.get())
                .add(KineticBlocks.SAWMILL.get())
                .add(KineticBlocks.CRUSHER.get())
                .add(KineticBlocks.COAL_GENERATOR.get())
                .add(KineticBlocks.CHARGER.get())
                .add(KineticBlocks.BASIC_POWER_CABLE_BLOCK.get())
                .add(KineticBlocks.STANDARD_POWER_CABLE_BLOCK.get())
                .add(KineticBlocks.PREMIUM_POWER_CABLE_BLOCK.get())
                .add(KineticBlocks.DELUXE_POWER_CABLE_BLOCK.get())
                .add(KineticBlocks.ULTIMATE_POWER_CABLE_BLOCK.get())
                .add(KineticBlocks.FACADE_BLOCK.get())
                .add(KineticBlocks.REACTOR_FRAME.get())
                .add(KineticBlocks.REACTOR_CORE.get())
                .add(KineticBlocks.REACTOR_POWER_TAP_BLOCK.get())
                .add(KineticBlocks.REACTOR_FLUID_PORT.get())
                .add(KineticBlocks.PRINTER_BLOCK.get())
                .add(KineticBlocks.INSCRIBER_BLOCK.get())
                .add(KineticBlocks.BASIC_SOLAR_PANEL.get())
                .add(KineticBlocks.STANDARD_SOLAR_PANEL.get())
                .add(KineticBlocks.PREMIUM_SOLAR_PANEL.get())
                .add(KineticBlocks.DELUXE_SOLAR_PANEL.get())
                .add(KineticBlocks.ULTIMATE_SOLAR_PANEL.get())
                .add(KineticBlocks.BASIC_BATTERY.get())
                .add(KineticBlocks.BRICK.get())
                .add(KineticBlocks.PLASTIC_BLOCK.get())
                .add(KineticBlocks.STONE_CASING.get())
                .add(KineticBlocks.VAT.get());

        this.tag(BlockTags.MINEABLE_WITH_AXE)
                .add(KineticBlocks.DRAFTING_TABLE.get())
                .add(KineticBlocks.RUBBER_LOG.get())
                .add(KineticBlocks.RUBBER_WOOD.get())
                .add(KineticBlocks.STRIPPED_RUBBER_LOG.get())
                .add(KineticBlocks.STRIPPED_RUBBER_WOOD.get())
                .add(KineticBlocks.RUBBER_PLANKS.get())
                .add(KineticBlocks.WOOD_CASING.get());

        this.tag(BlockTags.NEEDS_IRON_TOOL)
                .add(KineticBlocks.DURACITE_ORE.get())
                .add(KineticBlocks.DEEPSLATE_DURACITE_ORE.get())
                .add(KineticBlocks.RAW_DURACITE_BLOCK.get())
                .add(KineticBlocks.DURACITE_BLOCK.get())
                .add(KineticBlocks.SULFUR_ORE.get())
                .add(KineticBlocks.STEEL_BLOCK.get())
                .add(KineticBlocks.BATTERY_FRAME.get())
                .add(KineticBlocks.MACHINE_FRAME.get())
                .add(KineticBlocks.UNFILLED_QUANTUM_SATELLITE.get())
                .add(KineticBlocks.FILLED_QUANTUM_SATELLITE.get())
                .add(KineticBlocks.TANK.get())
                .add(KineticBlocks.QUANTUM_QUARRY.get())
                .add(KineticBlocks.COMPRESSOR.get())
                .add(KineticBlocks.EXTRACTOR.get())
                .add(KineticBlocks.SAWMILL.get())
                .add(KineticBlocks.CRUSHER.get())
                .add(KineticBlocks.COAL_GENERATOR.get())
                .add(KineticBlocks.CHARGER.get())
                .add(KineticBlocks.BASIC_POWER_CABLE_BLOCK.get())
                .add(KineticBlocks.STANDARD_POWER_CABLE_BLOCK.get())
                .add(KineticBlocks.PREMIUM_POWER_CABLE_BLOCK.get())
                .add(KineticBlocks.DELUXE_POWER_CABLE_BLOCK.get())
                .add(KineticBlocks.ULTIMATE_POWER_CABLE_BLOCK.get())
                .add(KineticBlocks.FACADE_BLOCK.get())
                .add(KineticBlocks.REACTOR_FRAME.get())
                .add(KineticBlocks.REACTOR_CORE.get())
                .add(KineticBlocks.REACTOR_POWER_TAP_BLOCK.get())
                .add(KineticBlocks.REACTOR_FLUID_PORT.get())
                .add(KineticBlocks.PRINTER_BLOCK.get())
                .add(KineticBlocks.INSCRIBER_BLOCK.get())
                .add(KineticBlocks.BASIC_SOLAR_PANEL.get())
                .add(KineticBlocks.STANDARD_SOLAR_PANEL.get())
                .add(KineticBlocks.PREMIUM_SOLAR_PANEL.get())
                .add(KineticBlocks.DELUXE_SOLAR_PANEL.get())
                .add(KineticBlocks.ULTIMATE_SOLAR_PANEL.get())
                .add(KineticBlocks.BASIC_BATTERY.get())
                .add(KineticBlocks.DRAFTING_TABLE.get())
                .add(KineticBlocks.VAT.get());

        this.tag(BlockTags.NEEDS_STONE_TOOL)
                .add(KineticBlocks.BRICK.get())
                .add(KineticBlocks.WOOD_CASING.get())
                .add(KineticBlocks.STONE_CASING.get());

        this.tag(BlockTags.LOGS_THAT_BURN)
                .add(KineticBlocks.RUBBER_LOG.get())
                .add(KineticBlocks.RUBBER_WOOD.get())
                .add(KineticBlocks.STRIPPED_RUBBER_LOG.get())
                .add(KineticBlocks.STRIPPED_RUBBER_WOOD.get())
                .add(KineticBlocks.WOOD_CASING.get());

        this.tag(BlockTags.PLANKS)
                .add(KineticBlocks.RUBBER_PLANKS.get());

        this.tag(Tags.Blocks.ORES)
                .add(KineticBlocks.DURACITE_ORE.get())
                .add(KineticBlocks.DEEPSLATE_DURACITE_ORE.get())
                .add(KineticBlocks.SULFUR_ORE.get());

        this.tag(BlockTags.LEAVES)
                .add(KineticBlocks.RUBBER_LEAVES.get());

    }
}
