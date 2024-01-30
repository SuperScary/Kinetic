package superscary.kinetic.fluid;

import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.joml.Vector3f;
import superscary.kinetic.Kinetic;

public class KineticFluidTypes
{

    public static final DeferredRegister<FluidType> FLUID_TYPES = DeferredRegister.create(ForgeRegistries.Keys.FLUID_TYPES, Kinetic.MODID);

    public static final RegistryObject<FluidType> OIL = register("oil", new BaseFluidType(Kinetic.getMinecraftResource("block/water_still"),
            Kinetic.getMinecraftResource("block/water_flow"), Kinetic.getMinecraftResource("block/water_overlay"), 0xff3b3131, new Vector3f(0, 0, 0),
            FluidType.Properties.create().lightLevel(0).viscosity(15).density(15).canSwim(false).supportsBoating(false).canExtinguish(false).canConvertToSource(false).canHydrate(false)));

    private static RegistryObject<FluidType> register (String name, FluidType fluidType)
    {
        return FLUID_TYPES.register(name, () -> fluidType);
    }

}
