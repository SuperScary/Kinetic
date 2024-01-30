package superscary.kinetic.fluid;

import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import superscary.kinetic.Kinetic;
import superscary.kinetic.register.KineticBlocks;
import superscary.kinetic.register.KineticItems;

public class KineticFluids
{

    public static final DeferredRegister<Fluid> FLUIDS = DeferredRegister.create(ForgeRegistries.FLUIDS, Kinetic.MODID);

    public static final RegistryObject<FlowingFluid> OIL_SOURCE = FLUIDS.register("oil", () -> new ForgeFlowingFluid.Source(KineticFluids.OIL_FLUID_PROPERTIES));
    public static final RegistryObject<FlowingFluid> OIL_FLOWING = FLUIDS.register("oil_flowing", () -> new ForgeFlowingFluid.Flowing(KineticFluids.OIL_FLUID_PROPERTIES));

    public static final ForgeFlowingFluid.Properties OIL_FLUID_PROPERTIES = new ForgeFlowingFluid.Properties(KineticFluidTypes.OIL, OIL_SOURCE, OIL_FLOWING).slopeFindDistance(2).levelDecreasePerBlock(1).block(KineticBlocks.OIL_BLOCK).bucket(KineticItems.OIL_BUCKET);

}
