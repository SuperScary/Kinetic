package superscary.kinetic.datagen;

import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import superscary.kinetic.datagen.table.KineticBlockLootTables;

import java.util.List;
import java.util.Set;

public class KineticLootTableProvider
{

    public static LootTableProvider create (PackOutput packOutput)
    {
        return new LootTableProvider(packOutput, Set.of(), List.of(new LootTableProvider.SubProviderEntry(KineticBlockLootTables::new, LootContextParamSets.BLOCK)));
    }

}
