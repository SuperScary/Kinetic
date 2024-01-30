package superscary.kinetic.worldgen;

import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.ForgeBiomeModifiers;
import net.minecraftforge.registries.ForgeRegistries;
import superscary.kinetic.Kinetic;

public class KineticBiomeModifiers
{

    public static final ResourceKey<BiomeModifier> ADD_TREE_RUBBER = registerKey("add_tree_rubber");
    public static final ResourceKey<BiomeModifier> DURACITE_ORE_SMALL = registerKey("duracite_ore_small");
    public static final ResourceKey<BiomeModifier> DURACITE_ORE_LARGE = registerKey("duracite_ore_large");

    public static void bootstrap (BootstapContext<BiomeModifier> context)
    {
        var placedFeatures = context.lookup(Registries.PLACED_FEATURE);
        var biomes = context.lookup(Registries.BIOME);

        context.register(ADD_TREE_RUBBER, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(BiomeTags.IS_OVERWORLD),
                HolderSet.direct(placedFeatures.getOrThrow(KineticPlacedFeatures.RUBBER_PLACED_KEY)),
                GenerationStep.Decoration.VEGETAL_DECORATION));
        context.register(DURACITE_ORE_SMALL, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(BiomeTags.IS_OVERWORLD),
                HolderSet.direct(placedFeatures.getOrThrow(KineticPlacedFeatures.DURACITE_ORE_PLACED_SMALL_KEY)),
                GenerationStep.Decoration.UNDERGROUND_ORES));
        context.register(DURACITE_ORE_LARGE, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(BiomeTags.IS_OVERWORLD),
                HolderSet.direct(placedFeatures.getOrThrow(KineticPlacedFeatures.DURACITE_ORE_PLACED_LARGE_KEY)),
                GenerationStep.Decoration.UNDERGROUND_ORES));

    }

    private static ResourceKey<BiomeModifier> registerKey (String name)
    {
        return ResourceKey.create(ForgeRegistries.Keys.BIOME_MODIFIERS, Kinetic.getResource(name));
    }

}
