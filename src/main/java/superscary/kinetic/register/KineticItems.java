package superscary.kinetic.register;

import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import superscary.kinetic.Kinetic;
import superscary.kinetic.KineticTabs;
import superscary.kinetic.item.*;
import superscary.kinetic.register.KineticBlocks;
import superscary.kinetic.block.cables.blocks.FacadeBlockItem;

import java.util.function.Supplier;

public class KineticItems
{

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Kinetic.MODID);

    public static final RegistryObject<Item> RAW_DURACITE = reg("raw_duracite", KineticItem::new);
    public static final RegistryObject<Item> DURACITE_DUST = reg("duracite_dust", KineticItem::new);
    public static final RegistryObject<Item> DURACITE_INGOT = reg("duracite_ingot", KineticItem::new);
    public static final RegistryObject<Item> DURACITE_NUGGET = reg("duracite_nugget", KineticItem::new);
    public static final RegistryObject<Item> STEEL_DUST = reg("steel_dust", KineticItem::new);
    public static final RegistryObject<Item> STEEL_INGOT = reg("steel_ingot", KineticItem::new);
    public static final RegistryObject<Item> STEEL_NUGGET = reg("steel_nugget", KineticItem::new);
    public static final RegistryObject<Item> WOOD_GEAR = reg("wood_gear", KineticItem::new);
    public static final RegistryObject<Item> STONE_GEAR = reg("stone_gear", KineticItem::new);
    public static final RegistryObject<Item> IRON_GEAR = reg("iron_gear", KineticItem::new);
    public static final RegistryObject<Item> STEEL_GEAR = reg("steel_gear", KineticItem::new);
    public static final RegistryObject<Item> PLASTIC_GEAR = reg("plastic_gear", KineticItem::new);
    public static final RegistryObject<Item> PLASTIC = reg("plastic", KineticItem::new);
    public static final RegistryObject<Item> RAW_PLASTIC = reg("raw_plastic", KineticItem::new);
    public static final RegistryObject<Item> RUBBER = reg("rubber", KineticItem::new);
    public static final RegistryObject<Item> UNTREATED_RUBBER = reg("untreated_rubber", KineticItem::new);
    public static final RegistryObject<Item> FLUX_COIL = reg("flux_coil", KineticItem::new);
    public static final RegistryObject<Item> SULFUR = reg("sulfur", KineticItem::new);
    public static final RegistryObject<Item> CAPACITOR_BASIC = reg("capacitor_basic", () -> new CapacitorItem(64000, 128, 1));
    public static final RegistryObject<Item> CAPACITOR_STANDARD = reg("capacitor_standard", () -> new CapacitorItem(128000, 256, 1));
    public static final RegistryObject<Item> CAPACITOR_PREMIUM = reg("capacitor_premium", () -> new CapacitorItem(256000, 512, 2));
    public static final RegistryObject<Item> CAPACITOR_DELUXE = reg("capacitor_deluxe", () -> new CapacitorItem(512000, 1024, 3));
    public static final RegistryObject<Item> CAPACITOR_ULTIMATE = reg("capacitor_ultimate", () -> new CapacitorItem(1024000, 2048, 4));
    public static final RegistryObject<Item> UPGRADE_BASE = reg("upgrade_base", KineticItem::new);
    public static final RegistryObject<Item> SPEED_UPGRADE = reg("upgrade_speed", UpgradeItem::new);
    public static final RegistryObject<Item> POWER_EFFICIENCY_UPGRADE = reg("upgrade_power_efficiency", UpgradeItem::new);
    public static final RegistryObject<Item> MAGNET = reg("magnet", MagnetItem::new);
    public static final RegistryObject<Item> CONFIGURATION_WIZARD = reg("configuration_wizard", ConfigurationWizardItem::new);
    public static final RegistryObject<Item> SD_CARD = reg("sd_card", SDCardItem::new);
    public static final RegistryObject<Item> BLUEPRINT = reg("blueprint", BlueprintItem::new);
    public static final RegistryObject<Item> BASIC_PHOTOVOLTAIC_CELL = reg("basic_photovoltaic_cell", KineticItem::new);
    public static final RegistryObject<Item> STANDARD_PHOTOVOLTAIC_CELL = reg("standard_photovoltaic_cell", KineticItem::new);
    public static final RegistryObject<Item> PREMIUM_PHOTOVOLTAIC_CELL = reg("premium_photovoltaic_cell", KineticItem::new);
    public static final RegistryObject<Item> DELUXE_PHOTOVOLTAIC_CELL = reg("deluxe_photovoltaic_cell", KineticItem::new);
    public static final RegistryObject<Item> ULTIMATE_PHOTOVOLTAIC_CELL = reg("ultimate_photovoltaic_cell", KineticItem::new);

    public static final RegistryObject<Item> ORE_FINDER = reg("ore_finder", () -> new OreFinderItem(new Item.Properties()));
    public static final RegistryObject<Item> WRENCH = reg("wrench", () -> new WrenchItem(new Item.Properties().defaultDurability(128)));
    public static final RegistryObject<Item> FACADE_BLOCK_ITEM = KineticTabs.addItemToTab(ITEMS.register("facade", () -> new FacadeBlockItem(KineticBlocks.FACADE_BLOCK.get(), new Item.Properties())));

    public static <T extends Item> RegistryObject<T> reg (final String name, final Supplier<? extends T> supplier)
    {
        return KineticTabs.addItemToTab(ITEMS.register(name, supplier));
    }

}
