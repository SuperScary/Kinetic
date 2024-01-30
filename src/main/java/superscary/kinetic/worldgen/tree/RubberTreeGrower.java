package superscary.kinetic.worldgen.tree;

import net.minecraft.resources.ResourceKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import org.jetbrains.annotations.Nullable;
import superscary.kinetic.worldgen.KineticConfiguredFeatures;

public class RubberTreeGrower extends AbstractTreeGrower
{


    @Nullable
    @Override
    protected ResourceKey<ConfiguredFeature<?, ?>> getConfiguredFeature (RandomSource randomSource, boolean b)
    {
        return KineticConfiguredFeatures.RUBBER_KEY;
    }
}
