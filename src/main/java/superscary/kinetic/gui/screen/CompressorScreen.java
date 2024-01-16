package superscary.kinetic.gui.screen;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraftforge.energy.IEnergyStorage;
import superscary.kinetic.gui.KineticContainerScreen;
import superscary.kinetic.gui.menu.CompressorMenu;
import superscary.kinetic.util.helpers.GetTexture;

public class CompressorScreen extends KineticContainerScreen<CompressorMenu>
{

    public CompressorScreen (CompressorMenu menu, Inventory inventory, Component title)
    {
        super(menu, inventory, title);
    }

    @Override
    public ResourceLocation getTexture ()
    {
        return GetTexture.ofGui("compressor");
    }

    @Override
    public IEnergyStorage getEnergyStorage ()
    {
        return menu.blockEntity.getEnergyStorage();
    }

    private void renderProgressArrow (GuiGraphics guiGraphics, int x, int y)
    {
        if (menu.isCrafting())
        {
            guiGraphics.blit(getTexture(), x + 85, y + 30, 203, 0, 8, menu.getScaledProgress());
        }
    }

    @Override
    public void renderArrow (GuiGraphics guiGraphics, int x, int y)
    {
        renderProgressArrow(guiGraphics, x, y);
    }

}
