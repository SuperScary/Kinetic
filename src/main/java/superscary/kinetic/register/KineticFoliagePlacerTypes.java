package superscary.kinetic.register;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import superscary.kinetic.Kinetic;
import superscary.kinetic.worldgen.tree.RubberFoliagePlacer;

public class KineticFoliagePlacerTypes
{

    public static final DeferredRegister<FoliagePlacerType<?>> FOLIAGE_PLACERS = DeferredRegister.create(Registries.FOLIAGE_PLACER_TYPE, Kinetic.MODID);

    public static final RegistryObject<FoliagePlacerType<RubberFoliagePlacer>> RUBBER_FOLIAGE_PLACER = FOLIAGE_PLACERS.register("rubber_foliage_placer", () -> new FoliagePlacerType<>(RubberFoliagePlacer.CODEC));

}
