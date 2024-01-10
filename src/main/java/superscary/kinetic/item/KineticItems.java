package superscary.kinetic.item;

import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import superscary.kinetic.Kinetic;
import superscary.kinetic.KineticTabs;

import java.util.function.Supplier;

public class KineticItems
{

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Kinetic.MODID);

    public static final RegistryObject<Item> RAW_DURACITE = reg("raw_duracite", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> DURACITE_DUST = reg("duracite_dust", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> DURACITE_INGOT = reg("duracite_ingot", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> DURACITE_NUGGET = reg("duracite_nugget", () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> STEEL_DUST = reg("steel_dust", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> STEEL_INGOT = reg("steel_ingot", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> STEEL_NUGGET = reg("steel_nugget", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> WOOD_GEAR = reg("wood_gear", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> STONE_GEAR = reg("stone_gear", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> IRON_GEAR = reg("iron_gear", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> PLASTIC = reg("plastic", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> RAW_PLASTIC = reg("raw_plastic", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> RUBBER = reg("rubber", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> UNTREATED_RUBBER = reg("untreated_rubber", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> FLUX_COIL = reg("flux_coil", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> SULFUR = reg("sulfur", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> CAPACITOR_BASIC = reg("capacitor_basic", () -> new CapacitorItem(new Item.Properties().stacksTo(1), 64000, 128, 1));
    public static final RegistryObject<Item> CAPACITOR_STANDARD = reg("capacitor_standard", () -> new CapacitorItem(new Item.Properties().stacksTo(1), 128000, 256, 1));
    public static final RegistryObject<Item> CAPACITOR_PREMIUM = reg("capacitor_premium", () -> new CapacitorItem(new Item.Properties().stacksTo(1), 256000, 512, 2));
    public static final RegistryObject<Item> CAPACITOR_DELUXE = reg("capacitor_deluxe", () -> new CapacitorItem(new Item.Properties().stacksTo(1), 512000, 1024, 3));
    public static final RegistryObject<Item> CAPACITOR_ULTIMATE = reg("capacitor_ultimate", () -> new CapacitorItem(new Item.Properties().stacksTo(1), 1024000, 2048, 4));

    public static final RegistryObject<Item> ORE_FINDER = reg("ore_finder", () -> new OreFinderItem(new Item.Properties()));
    public static final RegistryObject<Item> WRENCH = reg("wrench", () -> new WrenchItem(new Item.Properties().defaultDurability(128)));

    public static <T extends Item> RegistryObject<T> reg (final String name, final Supplier<? extends T> supplier)
    {
        return KineticTabs.addItemToTab(ITEMS.register(name, supplier));
    }

}
