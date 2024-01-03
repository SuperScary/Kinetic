package superscary.kinetic.block.entity;

import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import superscary.kinetic.Kinetic;
import superscary.kinetic.block.KineticBlocks;

public class KineticBlockEntities
{

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, Kinetic.MODID);

    public static final RegistryObject<BlockEntityType<CompressorBlockEntity>> COMPRESSOR_BE =
            BLOCK_ENTITIES.register("compressor_be", () -> BlockEntityType.Builder.of(CompressorBlockEntity::new,
                    KineticBlocks.COMPRESSOR.get()).build(null));

    public static final RegistryObject<BlockEntityType<SawmillBlockEntity>> SAWMILL_BE =
            BLOCK_ENTITIES.register("sawmill_be", () -> BlockEntityType.Builder.of(SawmillBlockEntity::new,
                    KineticBlocks.SAWMILL.get()).build(null));


}
