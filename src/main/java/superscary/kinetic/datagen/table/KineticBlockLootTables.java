package superscary.kinetic.datagen.table;

import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.RegistryObject;
import superscary.kinetic.register.KineticBlocks;
import superscary.kinetic.register.KineticItems;

import java.util.Set;

public class KineticBlockLootTables extends BlockLootSubProvider
{

    public KineticBlockLootTables ()
    {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags());
    }

    @Override
    protected void generate ()
    {
        this.add(KineticBlocks.DURACITE_ORE.get(), block -> createOreDrop(KineticBlocks.DEEPSLATE_DURACITE_ORE.get(), KineticItems.RAW_DURACITE.get()));
        this.add(KineticBlocks.DEEPSLATE_DURACITE_ORE.get(), block -> createOreDrop(KineticBlocks.DEEPSLATE_DURACITE_ORE.get(), KineticItems.RAW_DURACITE.get()));
        this.add(KineticBlocks.SULFUR_ORE.get(), block -> createOreDrop(KineticBlocks.SULFUR_ORE.get(), KineticItems.SULFUR.get()));
        this.dropSelf(KineticBlocks.RAW_DURACITE_BLOCK.get());
        this.dropSelf(KineticBlocks.DURACITE_BLOCK.get());
        this.dropSelf(KineticBlocks.STEEL_BLOCK.get());
        this.dropSelf(KineticBlocks.BATTERY_FRAME.get());
        this.dropSelf(KineticBlocks.MACHINE_BASE_BASIC.get());
        this.dropSelf(KineticBlocks.MACHINE_BASE_STANDARD.get());
        this.dropSelf(KineticBlocks.MACHINE_BASE_PREMIUM.get());
        this.dropSelf(KineticBlocks.MACHINE_BASE_DELUXE.get());
        this.dropSelf(KineticBlocks.MACHINE_BASE_ULTIMATE.get());
        this.dropSelf(KineticBlocks.UNFILLED_QUANTUM_SATELLITE.get());
        this.dropSelf(KineticBlocks.FILLED_QUANTUM_SATELLITE.get());
        this.dropSelf(KineticBlocks.TANK_BASIC.get());
        this.dropSelf(KineticBlocks.TANK_STANDARD.get());
        this.dropSelf(KineticBlocks.TANK_PREMIUM.get());
        this.dropSelf(KineticBlocks.TANK_DELUXE.get());
        this.dropSelf(KineticBlocks.TANK_ULTIMATE.get());
        this.dropSelf(KineticBlocks.QUANTUM_QUARRY.get());
        this.dropSelf(KineticBlocks.COMPRESSOR.get());
        this.dropSelf(KineticBlocks.EXTRACTOR.get());
        this.dropSelf(KineticBlocks.SAWMILL.get());
        this.dropSelf(KineticBlocks.CRUSHER.get());
        this.dropSelf(KineticBlocks.COAL_GENERATOR.get());
        this.dropSelf(KineticBlocks.CHARGER.get());
        this.dropSelf(KineticBlocks.BASIC_POWER_CABLE_BLOCK.get());
        this.dropSelf(KineticBlocks.STANDARD_POWER_CABLE_BLOCK.get());
        this.dropSelf(KineticBlocks.PREMIUM_POWER_CABLE_BLOCK.get());
        this.dropSelf(KineticBlocks.DELUXE_POWER_CABLE_BLOCK.get());
        this.dropSelf(KineticBlocks.ULTIMATE_POWER_CABLE_BLOCK.get());
        this.dropSelf(KineticBlocks.FACADE_BLOCK.get());
        this.dropSelf(KineticBlocks.REACTOR_FRAME.get());
        this.dropSelf(KineticBlocks.REACTOR_CORE.get());
        this.dropSelf(KineticBlocks.REACTOR_POWER_TAP_BLOCK.get());
        this.dropSelf(KineticBlocks.REACTOR_FLUID_PORT.get());
        this.dropSelf(KineticBlocks.PRINTER_BLOCK.get());
        this.dropSelf(KineticBlocks.INSCRIBER_BLOCK.get());
        this.dropSelf(KineticBlocks.BASIC_SOLAR_PANEL.get());
        this.dropSelf(KineticBlocks.STANDARD_SOLAR_PANEL.get());
        this.dropSelf(KineticBlocks.PREMIUM_SOLAR_PANEL.get());
        this.dropSelf(KineticBlocks.DELUXE_SOLAR_PANEL.get());
        this.dropSelf(KineticBlocks.ULTIMATE_SOLAR_PANEL.get());
        this.dropSelf(KineticBlocks.BASIC_BATTERY.get());
        this.dropSelf(KineticBlocks.DRAFTING_TABLE.get());
        this.dropSelf(KineticBlocks.RUBBER_LOG.get());
        this.dropSelf(KineticBlocks.RUBBER_WOOD.get());
        this.dropSelf(KineticBlocks.STRIPPED_RUBBER_LOG.get());
        this.dropSelf(KineticBlocks.STRIPPED_RUBBER_WOOD.get());
        this.dropSelf(KineticBlocks.RUBBER_PLANKS.get());
        this.dropSelf(KineticBlocks.RUBBER_SAPLING.get());
        this.dropSelf(KineticBlocks.BRICK.get());
        this.dropSelf(KineticBlocks.BRICK_STAIRS.get());
        this.dropSelf(KineticBlocks.PLASTIC_BLOCK.get());

        this.add(KineticBlocks.BRICK_SLAB.get(), block -> createSlabItemTable(KineticBlocks.BRICK_SLAB.get()));

        this.add(KineticBlocks.RUBBER_LEAVES.get(), block -> createLeavesDrops(block, KineticBlocks.RUBBER_SAPLING.get(), NORMAL_LEAVES_SAPLING_CHANCES));

    }

    @Override
    protected Iterable<Block> getKnownBlocks ()
    {
        return KineticBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get)::iterator;
    }
}
