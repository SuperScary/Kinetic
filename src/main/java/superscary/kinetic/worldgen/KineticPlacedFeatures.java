package superscary.kinetic.worldgen;

import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.HeightRangePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import superscary.kinetic.Kinetic;
import superscary.kinetic.register.KineticBlocks;

import java.util.List;

public class KineticPlacedFeatures
{
    public static final ResourceKey<PlacedFeature> RUBBER_PLACED_KEY = registerKey("rubber_placed");
    public static final ResourceKey<PlacedFeature> DURACITE_ORE_PLACED_SMALL_KEY = registerKey("duracite_ore_placed_small");
    public static final ResourceKey<PlacedFeature> DURACITE_ORE_PLACED_LARGE_KEY = registerKey("duracite_ore_placed_large");
    public static final ResourceKey<PlacedFeature> SULFUR_ORE_PLACED_SMALL_KEY = registerKey("sulfur_ore_placed_small");
    public static final ResourceKey<PlacedFeature> SULFUR_ORE_PLACED_LARGE_KEY = registerKey("sulfur_ore_placed_large");

    public static void bootstrap (BootstapContext<PlacedFeature> context)
    {
        HolderGetter<ConfiguredFeature<?, ?>> configuredFeatures = context.lookup(Registries.CONFIGURED_FEATURE);
        register(context, RUBBER_PLACED_KEY, configuredFeatures.getOrThrow(KineticConfiguredFeatures.RUBBER_KEY),
                VegetationPlacements.treePlacement(PlacementUtils.countExtra(1, 0.1f, 1),
                        KineticBlocks.RUBBER_SAPLING.get()));
        register(context, DURACITE_ORE_PLACED_SMALL_KEY, configuredFeatures.getOrThrow(KineticConfiguredFeatures.OVERWORLD_DURACITE_ORE_KEY_SMALL),
                KineticOrePlacement.commonOrePlacement(12, HeightRangePlacement.uniform(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(64))));
        register(context, DURACITE_ORE_PLACED_LARGE_KEY, configuredFeatures.getOrThrow(KineticConfiguredFeatures.OVERWORLD_DURACITE_ORE_KEY_LARGE),
                KineticOrePlacement.commonOrePlacement(6, HeightRangePlacement.uniform(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(64))));
        register(context, SULFUR_ORE_PLACED_SMALL_KEY, configuredFeatures.getOrThrow(KineticConfiguredFeatures.OVERWORLD_SULFUR_ORE_KEY_SMALL),
                KineticOrePlacement.commonOrePlacement(12, HeightRangePlacement.uniform(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(80))));
        register(context, SULFUR_ORE_PLACED_LARGE_KEY, configuredFeatures.getOrThrow(KineticConfiguredFeatures.OVERWORLD_SULFUR_ORE_KEY_LARGE),
                KineticOrePlacement.commonOrePlacement(6, HeightRangePlacement.uniform(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(80))));
    }

    private static ResourceKey<PlacedFeature> registerKey (String name)
    {
        return ResourceKey.create(Registries.PLACED_FEATURE, Kinetic.getResource(name));
    }

    private static void register (BootstapContext<PlacedFeature> context, ResourceKey<PlacedFeature> key, Holder<ConfiguredFeature<?, ?>> configuration, List<PlacementModifier> modifiers)
    {
        context.register(key, new PlacedFeature(configuration, List.copyOf(modifiers)));
    }

}
