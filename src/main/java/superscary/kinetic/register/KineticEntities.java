package superscary.kinetic.register;

import com.google.common.collect.ImmutableSet;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import superscary.kinetic.Kinetic;

public class KineticEntities
{

    public static final DeferredRegister<PoiType> POI_TYPES = DeferredRegister.create(ForgeRegistries.POI_TYPES, Kinetic.MODID);

    public static final DeferredRegister<VillagerProfession> VILLAGER_PROFESSIONS = DeferredRegister.create(ForgeRegistries.VILLAGER_PROFESSIONS, Kinetic.MODID);

    public static final RegistryObject<PoiType> ENGINEER_POI = POI_TYPES.register("engineer_poi",
            () -> new PoiType(ImmutableSet.copyOf(KineticBlocks.DRAFTING_TABLE.get().getStateDefinition().getPossibleStates()),
                    1, 1));

    public static final RegistryObject<VillagerProfession> ENGINEER = VILLAGER_PROFESSIONS.register("engineer",
            () -> new VillagerProfession("engineer", x -> x.get() == ENGINEER_POI.get(), x -> x.get() == ENGINEER_POI.get(), ImmutableSet.of(), ImmutableSet.of(),
                    SoundEvents.VILLAGER_WORK_TOOLSMITH));

}
