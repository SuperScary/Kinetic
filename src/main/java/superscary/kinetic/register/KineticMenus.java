package superscary.kinetic.register;

import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.network.IContainerFactory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import superscary.kinetic.Kinetic;
import superscary.kinetic.gui.menu.*;

public class KineticMenus
{

    public static final DeferredRegister<MenuType<?>> MENUS = DeferredRegister.create(ForgeRegistries.MENU_TYPES, Kinetic.MODID);

    private static <T extends AbstractContainerMenu> RegistryObject<MenuType<T>> registerMenuType (String name, IContainerFactory<T> factory)
    {
        return MENUS.register(name, () -> IForgeMenuType.create(factory));
    }

    public static final RegistryObject<MenuType<CompressorMenu>> COMPRESSOR_MENU = registerMenuType("compressor_menu", CompressorMenu::new);
    public static final RegistryObject<MenuType<SawmillMenu>> SAWMILL_MENU = registerMenuType("sawmill_menu", SawmillMenu::new);
    public static final RegistryObject<MenuType<CoalGeneratorMenu>> COAL_GENERATOR_MENU = registerMenuType("coal_generator_menu", CoalGeneratorMenu::new);
    public static final RegistryObject<MenuType<ChargerMenu>> CHARGER_MENU = registerMenuType("charger_menu", ChargerMenu::new);
    public static final RegistryObject<MenuType<PrinterMenu>> PRINTER_MENU = registerMenuType("printer_menu", PrinterMenu::new);
    public static final RegistryObject<MenuType<InscriberMenu>> INSCRIBER_MENU = registerMenuType("inscriber_menu", InscriberMenu::new);
    public static final RegistryObject<MenuType<BasicBatteryMenu>> BASIC_BATTERY_MENU = registerMenuType("basic_battery_menu", BasicBatteryMenu::new);
    public static final RegistryObject<MenuType<DraftingTableMenu>> DRAFTING_TABLE_MENU = registerMenuType("drafting_table_menu", DraftingTableMenu::new);


}
