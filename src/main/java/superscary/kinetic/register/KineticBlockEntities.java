package superscary.kinetic.register;

import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import superscary.kinetic.Kinetic;
import superscary.kinetic.block.blocks.ElectricMotorBlock;
import superscary.kinetic.block.cables.blocks.entity.*;
import superscary.kinetic.block.cables.blocks.entity.power.*;
import superscary.kinetic.block.entity.*;

public class KineticBlockEntities
{

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, Kinetic.MODID);

    public static final RegistryObject<BlockEntityType<CompressorBlockEntity>> COMPRESSOR_BE =
            BLOCK_ENTITIES.register("compressor_be", () -> BlockEntityType.Builder.of(CompressorBlockEntity::new,
                    KineticBlocks.COMPRESSOR.get()).build(null));

    public static final RegistryObject<BlockEntityType<SawmillBlockEntity>> SAWMILL_BE =
            BLOCK_ENTITIES.register("sawmill_be", () -> BlockEntityType.Builder.of(SawmillBlockEntity::new,
                    KineticBlocks.SAWMILL.get()).build(null));

    public static final RegistryObject<BlockEntityType<QQBlockEntity>> QQ_BE =
            BLOCK_ENTITIES.register("qq_be", () -> BlockEntityType.Builder.of(QQBlockEntity::new,
                    KineticBlocks.QUANTUM_QUARRY.get()).build(null));

    public static final RegistryObject<BlockEntityType<BasicTankBlockEntity>> BASIC_TANK_BE =
            BLOCK_ENTITIES.register("basic_tank_be", () -> BlockEntityType.Builder.of(BasicTankBlockEntity::new,
                    KineticBlocks.TANK.get()).build(null));

    public static final RegistryObject<BlockEntityType<CoalGeneratorBlockEntity>> COAL_GENERATOR_BE =
            BLOCK_ENTITIES.register("coal_generator_be", () -> BlockEntityType.Builder.of(CoalGeneratorBlockEntity::new,
                    KineticBlocks.COAL_GENERATOR.get()).build(null));

    public static final RegistryObject<BlockEntityType<ChargerBlockEntity>> CHARGER_BE =
            BLOCK_ENTITIES.register("charger_be", () -> BlockEntityType.Builder.of(ChargerBlockEntity::new,
                    KineticBlocks.CHARGER.get()).build(null));
    public static final RegistryObject<BlockEntityType<DraftingTableBlockEntity>> DRAFTING_TABLE_BE =
            BLOCK_ENTITIES.register("drafting_table_be", () -> BlockEntityType.Builder.of(DraftingTableBlockEntity::new,
                    KineticBlocks.DRAFTING_TABLE.get()).build(null));

    public static final RegistryObject<BlockEntityType<BasicPowerCableBlockEntity>> BASIC_POWER_CABLE_BE =
            BLOCK_ENTITIES.register("basic_cable_be", () -> BlockEntityType.Builder.of(BasicPowerCableBlockEntity::new,
                    KineticBlocks.BASIC_POWER_CABLE_BLOCK.get()).build(null));

    public static final RegistryObject<BlockEntityType<StandardPowerCableBlockEntity>> STANDARD_POWER_CABLE_BE =
            BLOCK_ENTITIES.register("standard_cable_be", () -> BlockEntityType.Builder.of(StandardPowerCableBlockEntity::new,
                    KineticBlocks.STANDARD_POWER_CABLE_BLOCK.get()).build(null));

    public static final RegistryObject<BlockEntityType<PremiumPowerCableBlockEntity>> PREMIUM_POWER_CABLE_BE =
            BLOCK_ENTITIES.register("premium_cable_be", () -> BlockEntityType.Builder.of(PremiumPowerCableBlockEntity::new,
                    KineticBlocks.PREMIUM_POWER_CABLE_BLOCK.get()).build(null));

    public static final RegistryObject<BlockEntityType<DeluxePowerCableBlockEntity>> DELUXE_POWER_CABLE_BE =
            BLOCK_ENTITIES.register("deluxe_cable_be", () -> BlockEntityType.Builder.of(DeluxePowerCableBlockEntity::new,
                    KineticBlocks.DELUXE_POWER_CABLE_BLOCK.get()).build(null));

    public static final RegistryObject<BlockEntityType<UltimatePowerCableBlockEntity>> ULTIMATE_POWER_CABLE_BE =
            BLOCK_ENTITIES.register("ultimate_cable_be", () -> BlockEntityType.Builder.of(UltimatePowerCableBlockEntity::new,
                    KineticBlocks.ULTIMATE_POWER_CABLE_BLOCK.get()).build(null));

    public static final RegistryObject<BlockEntityType<FacadeBlockEntity>> FACADE_BE =
            BLOCK_ENTITIES.register("facade_be", () -> BlockEntityType.Builder.of(FacadeBlockEntity::new,
                    KineticBlocks.FACADE_BLOCK.get()).build(null));

    public static final RegistryObject<BlockEntityType<PrinterBlockEntity>> PRINTER_BE =
            BLOCK_ENTITIES.register("printer_be", () -> BlockEntityType.Builder.of(PrinterBlockEntity::new,
                    KineticBlocks.PRINTER_BLOCK.get()).build(null));

    public static final RegistryObject<BlockEntityType<InscriberBlockEntity>> INSCRIBER_BE =
            BLOCK_ENTITIES.register("inscriber_be", () -> BlockEntityType.Builder.of(InscriberBlockEntity::new,
                    KineticBlocks.INSCRIBER_BLOCK.get()).build(null));
    public static final RegistryObject<BlockEntityType<BasicSolarPanelBlockEntity>> BASIC_SOLAR_PANEL_BE =
            BLOCK_ENTITIES.register("basic_solar_panel_be", () -> BlockEntityType.Builder.of(BasicSolarPanelBlockEntity::new,
                    KineticBlocks.BASIC_SOLAR_PANEL.get()).build(null));
    public static final RegistryObject<BlockEntityType<StandardSolarPanelBlockEntity>> STANDARD_SOLAR_PANEL_BE =
            BLOCK_ENTITIES.register("standard_solar_panel_be", () -> BlockEntityType.Builder.of(StandardSolarPanelBlockEntity::new,
                    KineticBlocks.STANDARD_SOLAR_PANEL.get()).build(null));
    public static final RegistryObject<BlockEntityType<PremiumSolarPanelBlockEntity>> PREMIUM_SOLAR_PANEL_BE =
            BLOCK_ENTITIES.register("premium_solar_panel_be", () -> BlockEntityType.Builder.of(PremiumSolarPanelBlockEntity::new,
                    KineticBlocks.PREMIUM_SOLAR_PANEL.get()).build(null));
    public static final RegistryObject<BlockEntityType<DeluxeSolarPanelBlockEntity>> DELUXE_SOLAR_PANEL_BE =
            BLOCK_ENTITIES.register("deluxe_solar_panel_be", () -> BlockEntityType.Builder.of(DeluxeSolarPanelBlockEntity::new,
                    KineticBlocks.DELUXE_SOLAR_PANEL.get()).build(null));
    public static final RegistryObject<BlockEntityType<UltimateSolarPanelBlockEntity>> ULTIMATE_SOLAR_PANEL_BE =
            BLOCK_ENTITIES.register("ultimate_solar_panel_be", () -> BlockEntityType.Builder.of(UltimateSolarPanelBlockEntity::new,
                    KineticBlocks.ULTIMATE_SOLAR_PANEL.get()).build(null));
    public static final RegistryObject<BlockEntityType<BasicBatteryBlockEntity>> BASIC_BATTERY_BE =
            BLOCK_ENTITIES.register("basic_battery_be", () -> BlockEntityType.Builder.of(BasicBatteryBlockEntity::new,
                    KineticBlocks.BASIC_BATTERY.get()).build(null));

    public static final RegistryObject<BlockEntityType<VatBlockEntity>> VAT_BE =
            BLOCK_ENTITIES.register("vat_be", () -> BlockEntityType.Builder.of(VatBlockEntity::new,
                    KineticBlocks.VAT.get()).build(null));

    public static final RegistryObject<BlockEntityType<ElectricMotorBlockEntity>> ELECTRIC_MOTOR_BE =
            BLOCK_ENTITIES.register("electric_motor_be", () -> BlockEntityType.Builder.of(ElectricMotorBlockEntity::new,
                    KineticBlocks.ELECTRIC_MOTOR.get()).build(null));


}
