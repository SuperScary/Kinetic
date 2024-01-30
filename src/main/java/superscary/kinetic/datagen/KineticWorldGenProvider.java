package superscary.kinetic.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.DatapackBuiltinEntriesProvider;
import net.minecraftforge.registries.ForgeRegistries;
import superscary.kinetic.Kinetic;
import superscary.kinetic.worldgen.KineticBiomeModifiers;
import superscary.kinetic.worldgen.KineticConfiguredFeatures;
import superscary.kinetic.worldgen.KineticPlacedFeatures;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class KineticWorldGenProvider extends DatapackBuiltinEntriesProvider
{

    public static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
            .add(Registries.CONFIGURED_FEATURE, KineticConfiguredFeatures::bootstrap)
            .add(Registries.PLACED_FEATURE, KineticPlacedFeatures::bootstrap)
            .add(ForgeRegistries.Keys.BIOME_MODIFIERS, KineticBiomeModifiers::bootstrap);

    public KineticWorldGenProvider (PackOutput output, CompletableFuture<HolderLookup.Provider> registries)
    {
        super(output, registries, BUILDER, Set.of(Kinetic.MODID));
    }
}
