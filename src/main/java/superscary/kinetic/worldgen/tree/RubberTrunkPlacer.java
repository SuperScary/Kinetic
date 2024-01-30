package superscary.kinetic.worldgen.tree;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;
import superscary.kinetic.register.KineticTrunkPlacerTypes;

import java.util.List;
import java.util.function.BiConsumer;

public class RubberTrunkPlacer extends TrunkPlacer
{

    public static final Codec<RubberTrunkPlacer> CODEC = RecordCodecBuilder.create(rubberTrunkPlacerInstance ->
            trunkPlacerParts(rubberTrunkPlacerInstance).apply(rubberTrunkPlacerInstance, RubberTrunkPlacer::new));

    public RubberTrunkPlacer (int pBaseHeight, int pHeightRandA, int pHeightRandB)
    {
        super(pBaseHeight, pHeightRandA, pHeightRandB);
    }

    @Override
    protected TrunkPlacerType<?> type ()
    {
        return KineticTrunkPlacerTypes.RUBBER_TRUNK_PLACER.get();
    }

    @Override
    public List<FoliagePlacer.FoliageAttachment> placeTrunk (LevelSimulatedReader pLevel, BiConsumer<BlockPos, BlockState> pBlockSetter, RandomSource pRandom, int pFreeTreeHeight, BlockPos pPos, TreeConfiguration pConfig)
    {
        setDirtAt(pLevel, pBlockSetter, pRandom, pPos.below(), pConfig);
        int height = pFreeTreeHeight + pRandom.nextInt(heightRandA, heightRandA + 1) + pRandom.nextInt(heightRandB - 1, heightRandB + 1);

        for (int i = 0; i < height; i++)
        {
            placeLog(pLevel, pBlockSetter, pRandom, pPos.above(i), pConfig);
        }

        return ImmutableList.of(new FoliagePlacer.FoliageAttachment(pPos.above(height), 0, false));
    }
}
