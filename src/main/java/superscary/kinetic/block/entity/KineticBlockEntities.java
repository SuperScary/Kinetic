package superscary.kinetic.block.entity;

import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import superscary.kinetic.Kinetic;
import superscary.kinetic.block.KineticBlocks;
import superscary.kinetic.block.cables.blocks.entity.CableBlockEntity;
import superscary.kinetic.block.cables.blocks.entity.FacadeBlockEntity;

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
                    KineticBlocks.TANK_BASIC.get()).build(null));

    public static final RegistryObject<BlockEntityType<CoalGeneratorBlockEntity>> COAL_GENERATOR_BE =
            BLOCK_ENTITIES.register("coal_generator_be", () -> BlockEntityType.Builder.of(CoalGeneratorBlockEntity::new,
                    KineticBlocks.COAL_GENERATOR.get()).build(null));

    public static final RegistryObject<BlockEntityType<ChargerBlockEntity>> CHARGER_BE =
            BLOCK_ENTITIES.register("charger_be", () -> BlockEntityType.Builder.of(ChargerBlockEntity::new,
                    KineticBlocks.CHARGER.get()).build(null));

    public static final RegistryObject<BlockEntityType<CableBlockEntity>> CABLE_BE =
            BLOCK_ENTITIES.register("cable_be", () -> BlockEntityType.Builder.of(CableBlockEntity::new,
                    KineticBlocks.CABLE_BLOCK.get()).build(null));

    public static final RegistryObject<BlockEntityType<FacadeBlockEntity>> FACADE_BE =
            BLOCK_ENTITIES.register("facade_be", () -> BlockEntityType.Builder.of(FacadeBlockEntity::new,
                    KineticBlocks.FACADE_BLOCK.get()).build(null));


}
