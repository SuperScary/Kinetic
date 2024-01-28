package superscary.kinetic.register;

import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DropExperienceBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import superscary.kinetic.Kinetic;
import superscary.kinetic.KineticTabs;
import superscary.kinetic.block.blocks.*;
import superscary.kinetic.block.blocks.battery.BasicBatteryBlock;
import superscary.kinetic.block.blocks.reactor.ReactorCoreBlock;
import superscary.kinetic.block.blocks.reactor.ReactorFluidPortBlock;
import superscary.kinetic.block.blocks.reactor.ReactorFrameBlock;
import superscary.kinetic.block.blocks.reactor.ReactorPowerTapBlock;
import superscary.kinetic.block.blocks.solar.*;
import superscary.kinetic.block.cables.blocks.*;
import superscary.kinetic.block.cables.blocks.power.*;

import java.util.function.Supplier;

public class KineticBlocks
{

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Kinetic.MODID);

    public static final RegistryObject<Block> DURACITE_ORE = reg("duracite_ore", () -> new DropExperienceBlock(BlockBehaviour.Properties.copy(Blocks.IRON_ORE).requiresCorrectToolForDrops(), UniformInt.of(3, 6)));
    public static final RegistryObject<Block> DEEPSLATE_DURACITE_ORE = reg("deepslate_duracite_ore", () -> new DropExperienceBlock(BlockBehaviour.Properties.copy(Blocks.DEEPSLATE_IRON_ORE).requiresCorrectToolForDrops(), UniformInt.of(3, 6)));
    public static final RegistryObject<Block> RAW_DURACITE_BLOCK = reg("raw_duracite_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    public static final RegistryObject<Block> DURACITE_BLOCK = reg("duracite_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    public static final RegistryObject<Block> SULFUR_ORE = reg("sulfur_ore", () -> new DropExperienceBlock(BlockBehaviour.Properties.copy(Blocks.IRON_ORE).requiresCorrectToolForDrops(), UniformInt.of(2, 3)));
    public static final RegistryObject<Block> STEEL_BLOCK = reg("steel_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    public static final RegistryObject<Block> BATTERY_FRAME = reg("battery_frame", () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    public static final RegistryObject<Block> MACHINE_BASE_BASIC = reg("machine_base_basic", () -> new MachineBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK), 1));
    public static final RegistryObject<Block> MACHINE_BASE_STANDARD = reg("machine_base_standard", () -> new MachineBlock(BlockBehaviour.Properties.copy(MACHINE_BASE_BASIC.get()), 1));
    public static final RegistryObject<Block> MACHINE_BASE_PREMIUM = reg("machine_base_premium", () -> new MachineBlock(BlockBehaviour.Properties.copy(MACHINE_BASE_STANDARD.get()), 2));
    public static final RegistryObject<Block> MACHINE_BASE_DELUXE = reg("machine_base_deluxe", () -> new MachineBlock(BlockBehaviour.Properties.copy(MACHINE_BASE_PREMIUM.get()), 3));
    public static final RegistryObject<Block> MACHINE_BASE_ULTIMATE = reg("machine_base_ultimate", () -> new MachineBlock(BlockBehaviour.Properties.copy(MACHINE_BASE_DELUXE.get()), 4));
    public static final RegistryObject<Block> UNFILLED_QUANTUM_SATELLITE = reg("unfilled_quantum_satellite", () -> new Block(BlockBehaviour.Properties.copy(MACHINE_BASE_ULTIMATE.get())));
    public static final RegistryObject<Block> FILLED_QUANTUM_SATELLITE = reg("filled_quantum_satellite", () -> new Block(BlockBehaviour.Properties.copy(MACHINE_BASE_ULTIMATE.get())));
    public static final RegistryObject<Block> DRAFTING_TABLE = reg("drafting_table", () -> new Block(BlockBehaviour.Properties.copy(MACHINE_BASE_BASIC.get())));
    public static final RegistryObject<Block> TANK_BASIC = reg("tank_basic", () -> new TankBlock(64));
    public static final RegistryObject<Block> TANK_STANDARD = reg("tank_standard", () -> new TankBlock(128));
    public static final RegistryObject<Block> TANK_PREMIUM = reg("tank_premium", () -> new TankBlock(256));
    public static final RegistryObject<Block> TANK_DELUXE = reg("tank_deluxe", () -> new TankBlock(512));
    public static final RegistryObject<Block> TANK_ULTIMATE = reg("tank_ultimate", () -> new TankBlock(1024));
    public static final RegistryObject<Block> QUANTUM_QUARRY = reg("quantum_quarry", QuantumQuarryBlock::new);
    public static final RegistryObject<Block> COMPRESSOR = reg("compressor", CompressorBlock::new);
    public static final RegistryObject<Block> EXTRACTOR = reg("extractor", ExtractorBlock::new);
    public static final RegistryObject<Block> SAWMILL = reg("sawmill", SawmillBlock::new);
    public static final RegistryObject<Block> CRUSHER = reg("crusher", CrusherBlock::new);
    public static final RegistryObject<Block> COAL_GENERATOR = reg("coal_generator", CoalGeneratorBlock::new);
    public static final RegistryObject<Block> CHARGER = reg("charger", ChargerBlock::new);
    public static final RegistryObject<Block> BASIC_POWER_CABLE_BLOCK = reg("basic_power_cable", BasicPowerCableBlock::new);
    public static final RegistryObject<Block> STANDARD_POWER_CABLE_BLOCK = reg("standard_power_cable", StandardPowerCableBlock::new);
    public static final RegistryObject<Block> PREMIUM_POWER_CABLE_BLOCK = reg("premium_power_cable", PremiumPowerCableBlock::new);
    public static final RegistryObject<Block> DELUXE_POWER_CABLE_BLOCK = reg("deluxe_power_cable", DeluxePowerCableBlock::new);
    public static final RegistryObject<Block> ULTIMATE_POWER_CABLE_BLOCK = reg("ultimate_power_cable", UltimatePowerCableBlock::new);
    public static final RegistryObject<FacadeBlock> FACADE_BLOCK = KineticTabs.addBlockToTab(BLOCKS.register("facade", FacadeBlock::new));
    public static final RegistryObject<Block> REACTOR_FRAME = reg("reactor_frame", ReactorFrameBlock::new);
    public static final RegistryObject<Block> REACTOR_CORE = reg("reactor_core", ReactorCoreBlock::new);
    public static final RegistryObject<Block> REACTOR_POWER_TAP_BLOCK = reg("reactor_power_tap", ReactorPowerTapBlock::new);
    public static final RegistryObject<Block> REACTOR_FLUID_PORT = reg("reactor_fluid_port", ReactorFluidPortBlock::new);
    public static final RegistryObject<Block> PRINTER_BLOCK = reg("printer", PrinterBlock::new);
    public static final RegistryObject<Block> INSCRIBER_BLOCK = reg("inscriber", InscriberBlock::new);
    public static final RegistryObject<Block> BASIC_SOLAR_PANEL = reg("basic_solar_panel", BasicSolarPanelBlock::new);
    public static final RegistryObject<Block> STANDARD_SOLAR_PANEL = reg("standard_solar_panel", StandardSolarPanelBlock::new);
    public static final RegistryObject<Block> PREMIUM_SOLAR_PANEL = reg("premium_solar_panel", PremiumSolarPanelBlock::new);
    public static final RegistryObject<Block> DELUXE_SOLAR_PANEL = reg("deluxe_solar_panel", DeluxeSolarPanelBlock::new);
    public static final RegistryObject<Block> ULTIMATE_SOLAR_PANEL = reg("ultimate_solar_panel", UltimateSolarPanelBlock::new);
    public static final RegistryObject<Block> BASIC_BATTERY = reg("basic_battery", BasicBatteryBlock::new);

    public static <T extends Block> RegistryObject<T> reg (final String name, final Supplier<? extends T> supplier)
    {
        RegistryObject<T> obj = KineticTabs.addBlockToTab(BLOCKS.register(name, supplier));
        KineticItems.reg(name, () -> new BlockItem(obj.get(), new Item.Properties()));
        return obj;
    }

}
