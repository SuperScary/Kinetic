package superscary.kinetic.gui.screen;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraftforge.energy.IEnergyStorage;
import superscary.kinetic.gui.KineticContainerScreen;
import superscary.kinetic.gui.menu.InscriberMenu;
import superscary.kinetic.util.helpers.GetTexture;

public class InscriberScreen extends KineticContainerScreen<InscriberMenu>
{

    public InscriberScreen (InscriberMenu menu, Inventory inventory, Component title)
    {
        super(menu, inventory, title);
    }

    @Override
    public ResourceLocation getTexture ()
    {
        return GetTexture.ofGui("inscriber");
    }

    @Override
    public IEnergyStorage getEnergyStorage ()
    {
        return menu.blockEntity.getEnergyStorage();
    }

}
