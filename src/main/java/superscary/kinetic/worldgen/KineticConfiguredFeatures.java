package superscary.kinetic.worldgen;

import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.BlobFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.trunkplacers.StraightTrunkPlacer;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;
import superscary.kinetic.Kinetic;
import superscary.kinetic.register.KineticBlocks;
import superscary.kinetic.worldgen.tree.RubberFoliagePlacer;
import superscary.kinetic.worldgen.tree.RubberTrunkPlacer;

import java.util.List;

public class KineticConfiguredFeatures
{

    public static final ResourceKey<ConfiguredFeature<?, ?>> RUBBER_KEY = registerKey("rubber");
    public static final ResourceKey<ConfiguredFeature<?, ?>> OVERWORLD_DURACITE_ORE_KEY_LARGE = registerKey("duracite_ore_large");
    public static final ResourceKey<ConfiguredFeature<?, ?>> OVERWORLD_DURACITE_ORE_KEY_SMALL = registerKey("duracite_ore_small");
    public static final ResourceKey<ConfiguredFeature<?, ?>> OVERWORLD_SULFUR_ORE_KEY_LARGE = registerKey("sulfur_ore_large");
    public static final ResourceKey<ConfiguredFeature<?, ?>> OVERWORLD_SULFUR_ORE_KEY_SMALL = registerKey("sulfur_ore_small");

    public static void bootstrap (BootstapContext<ConfiguredFeature<?, ?>> context)
    {
        RuleTest stoneReplaceables = new TagMatchTest(BlockTags.STONE_ORE_REPLACEABLES);
        RuleTest deepslateReplaceables = new TagMatchTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES);

        List<OreConfiguration.TargetBlockState> overworldDuraciteOres = List.of(OreConfiguration.target(stoneReplaceables,
                KineticBlocks.DURACITE_ORE.get().defaultBlockState()),
                OreConfiguration.target(deepslateReplaceables, KineticBlocks.DEEPSLATE_DURACITE_ORE.get().defaultBlockState()));
        List<OreConfiguration.TargetBlockState> overworldSulfurOres = List.of(OreConfiguration.target(stoneReplaceables,
                KineticBlocks.SULFUR_ORE.get().defaultBlockState()));

        register(context, RUBBER_KEY, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(
                BlockStateProvider.simple(KineticBlocks.RUBBER_LOG.get()),
                new RubberTrunkPlacer(3, 2, 1),
                BlockStateProvider.simple(KineticBlocks.RUBBER_LEAVES.get()),
                new BlobFoliagePlacer(ConstantInt.of(3), ConstantInt.of(1), 3),
                new TwoLayersFeatureSize(1, 0, 2)).build());

        register(context, OVERWORLD_DURACITE_ORE_KEY_LARGE, Feature.ORE, new OreConfiguration(overworldDuraciteOres, 8));
        register(context, OVERWORLD_DURACITE_ORE_KEY_SMALL, Feature.ORE, new OreConfiguration(overworldDuraciteOres, 4));
        register(context, OVERWORLD_SULFUR_ORE_KEY_LARGE, Feature.ORE, new OreConfiguration(overworldSulfurOres, 12));
        register(context, OVERWORLD_SULFUR_ORE_KEY_SMALL, Feature.ORE, new OreConfiguration(overworldSulfurOres, 8));
    }

    private static ResourceKey<ConfiguredFeature<?,?>> registerKey (String name)
    {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, Kinetic.getResource(name));
    }

    private static <FC extends FeatureConfiguration, F extends Feature<FC>> void register (BootstapContext<ConfiguredFeature<?, ?>> context, ResourceKey<ConfiguredFeature<?, ?>> key, F feature, FC configuration)
    {
        context.register(key, new ConfiguredFeature<>(feature, configuration));
    }


}
