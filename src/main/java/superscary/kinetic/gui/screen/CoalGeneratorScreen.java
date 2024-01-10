package superscary.kinetic.gui.screen;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraftforge.energy.IEnergyStorage;
import superscary.kinetic.gui.KineticContainerScreen;
import superscary.kinetic.gui.menu.CoalGeneratorMenu;
import superscary.kinetic.util.helpers.GetTexture;

public class CoalGeneratorScreen extends KineticContainerScreen<CoalGeneratorMenu>
{

    public CoalGeneratorScreen (CoalGeneratorMenu menu, Inventory inventory, Component title)
    {
        super(menu, inventory, title);
    }

    @Override
    public ResourceLocation getTexture ()
    {
        return GetTexture.ofGui("coal_generator");
    }

    @Override
    public IEnergyStorage getEnergyStorage ()
    {
        return menu.blockEntity.getEnergyStorage();
    }


}
