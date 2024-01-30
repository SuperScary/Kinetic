package superscary.kinetic.worldgen.tree;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;
import superscary.kinetic.register.KineticFoliagePlacerTypes;

public class RubberFoliagePlacer extends FoliagePlacer
{

    public static final Codec<RubberFoliagePlacer> CODEC = RecordCodecBuilder.create((instance) -> foliagePlacerParts(instance)
            .and(Codec.intRange(0, 16).fieldOf("height").forGetter(fp -> fp.height)).apply(instance, RubberFoliagePlacer::new));
    protected final int height;

    public RubberFoliagePlacer (IntProvider pRadius, IntProvider pOffset, int height)
    {
        super(pRadius, pOffset);
        this.height = height;
    }

    @Override
    protected FoliagePlacerType<?> type ()
    {
        return KineticFoliagePlacerTypes.RUBBER_FOLIAGE_PLACER.get();
    }

    @Override
    protected void createFoliage (LevelSimulatedReader pLevel, FoliageSetter pBlockSetter, RandomSource pRandom, TreeConfiguration pConfig, int maxFreeTreeHeight, FoliageAttachment pAttachment, int pFoliageHeight, int pFoliageRadius, int pOffset)
    {
        for(int i = pOffset; i >= pOffset - pFoliageHeight; --i)
        {
            int range = Math.max(pFoliageRadius + pAttachment.radiusOffset() - 1 - i / 2, 0);
            this.placeLeavesRowWithHangingLeavesBelow(pLevel, pBlockSetter, pRandom, pConfig, pAttachment.pos(), range, i, pAttachment.doubleTrunk(), 0.25f, 0.25f);
        }

    }

    @Override
    public int foliageHeight (RandomSource randomSource, int i, TreeConfiguration treeConfiguration)
    {
        return this.height;
    }

    @Override
    protected boolean shouldSkipLocation(RandomSource pRandom, int pLocalX, int pLocalY, int pLocalZ, int pRange, boolean pLarge)
    {
        return pLocalX == pRange && pLocalZ == pRange && (pRandom.nextInt(2) == 0 || pLocalY == 0);
    }
}
