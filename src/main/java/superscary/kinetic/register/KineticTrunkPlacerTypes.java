package superscary.kinetic.register;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import superscary.kinetic.Kinetic;
import superscary.kinetic.worldgen.tree.RubberTrunkPlacer;

public class KineticTrunkPlacerTypes
{

    public static final DeferredRegister<TrunkPlacerType<?>> TRUNK_PLACERS = DeferredRegister.create(Registries.TRUNK_PLACER_TYPE, Kinetic.MODID);

    public static final RegistryObject<TrunkPlacerType<RubberTrunkPlacer>> RUBBER_TRUNK_PLACER = TRUNK_PLACERS.register("rubber_trunk_placer", () -> new TrunkPlacerType<>(RubberTrunkPlacer.CODEC));

}
