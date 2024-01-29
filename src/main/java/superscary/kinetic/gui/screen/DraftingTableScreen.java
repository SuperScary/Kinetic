package superscary.kinetic.gui.screen;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraftforge.energy.EnergyStorage;
import net.minecraftforge.energy.IEnergyStorage;
import superscary.kinetic.gui.KineticContainerScreen;
import superscary.kinetic.gui.menu.DraftingTableMenu;
import superscary.kinetic.util.helpers.GetTexture;

public class DraftingTableScreen extends KineticContainerScreen<DraftingTableMenu>
{

    public DraftingTableScreen (DraftingTableMenu menu, Inventory inventory, Component title)
    {
        super(menu, inventory, title);
    }

    @Override
    public ResourceLocation getTexture ()
    {
        return GetTexture.ofGui("drafting_table");
    }

    @Override
    public IEnergyStorage getEnergyStorage ()
    {
        return new EnergyStorage(0, 0, 0);
    }

}
