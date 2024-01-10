package superscary.kinetic.gui;

import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.network.IContainerFactory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import superscary.kinetic.Kinetic;
import superscary.kinetic.gui.menu.ChargerMenu;
import superscary.kinetic.gui.menu.CoalGeneratorMenu;
import superscary.kinetic.gui.menu.CompressorMenu;
import superscary.kinetic.gui.menu.SawmillMenu;

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


}
