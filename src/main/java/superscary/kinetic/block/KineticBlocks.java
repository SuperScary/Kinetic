package superscary.kinetic.block;

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
import superscary.kinetic.item.KineticItems;

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
    public static final RegistryObject<Block> MACHINE_BASE_BASIC = reg("machine_base_basic", () -> new MachineBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK), 1));
    public static final RegistryObject<Block> MACHINE_BASE_STANDARD = reg("machine_base_standard", () -> new MachineBlock(BlockBehaviour.Properties.copy(MACHINE_BASE_BASIC.get()), 1));
    public static final RegistryObject<Block> MACHINE_BASE_PREMIUM = reg("machine_base_premium", () -> new MachineBlock(BlockBehaviour.Properties.copy(MACHINE_BASE_STANDARD.get()), 2));
    public static final RegistryObject<Block> MACHINE_BASE_DELUXE = reg("machine_base_deluxe", () -> new MachineBlock(BlockBehaviour.Properties.copy(MACHINE_BASE_PREMIUM.get()), 3));
    public static final RegistryObject<Block> MACHINE_BASE_ULTIMATE = reg("machine_base_ultimate", () -> new MachineBlock(BlockBehaviour.Properties.copy(MACHINE_BASE_DELUXE.get()), 4));
    public static final RegistryObject<Block> COMPRESSOR = reg("compressor", () -> new CompressorBlock(BlockBehaviour.Properties.copy(MACHINE_BASE_BASIC.get()).noOcclusion()));
    public static final RegistryObject<Block> EXTRACTOR = reg("extractor", () -> new ExtractorBlock(BlockBehaviour.Properties.copy(MACHINE_BASE_BASIC.get())));
    public static final RegistryObject<Block> SAWMILL = reg("sawmill", () -> new SawmillBlock(BlockBehaviour.Properties.copy(MACHINE_BASE_BASIC.get())));
    public static final RegistryObject<Block> CRUSHER = reg("crusher", () -> new CrusherBlock(BlockBehaviour.Properties.copy(MACHINE_BASE_BASIC.get())));
    public static final RegistryObject<Block> QUANTUM_QUARRY = reg("quantum_quarry", () -> new QuantumQuarryBlock(BlockBehaviour.Properties.copy(MACHINE_BASE_ULTIMATE.get())));

    public static <T extends Block> RegistryObject<T> reg (final String name, final Supplier<? extends T> supplier)
    {
        RegistryObject<T> obj = KineticTabs.addBlockToTab(BLOCKS.register(name, supplier));
        KineticItems.reg(name, () -> new BlockItem(obj.get(), new Item.Properties()));
        return obj;
    }

}
