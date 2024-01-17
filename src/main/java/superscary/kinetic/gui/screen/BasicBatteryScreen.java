package superscary.kinetic.gui.screen;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraftforge.energy.IEnergyStorage;
import superscary.kinetic.gui.KineticBatteryContainerScreen;
import superscary.kinetic.gui.KineticContainerScreen;
import superscary.kinetic.gui.menu.BasicBatteryMenu;
import superscary.kinetic.gui.menu.ChargerMenu;
import superscary.kinetic.util.helpers.GetTexture;

public class BasicBatteryScreen extends KineticBatteryContainerScreen<BasicBatteryMenu>
{

    public BasicBatteryScreen (BasicBatteryMenu menu, Inventory inventory, Component title)
    {
        super(menu, inventory, title);
    }

    @Override
    public ResourceLocation getTexture ()
    {
        return GetTexture.ofGui("battery");
    }

    @Override
    public IEnergyStorage getEnergyStorage ()
    {
        return menu.blockEntity.getEnergyStorage();
    }

}
