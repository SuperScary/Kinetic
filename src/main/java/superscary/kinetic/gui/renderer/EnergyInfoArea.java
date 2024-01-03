package superscary.kinetic.gui.renderer;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.Rect2i;
import net.minecraft.network.chat.Component;
import net.minecraftforge.energy.IEnergyStorage;

import java.util.List;

public class EnergyInfoArea extends InfoArea
{

    private final IEnergyStorage energy;

    public EnergyInfoArea(int xMin, int yMin, IEnergyStorage energy)
    {
        super(new Rect2i(xMin, yMin, 7, 46));
        this.energy = energy;
    }

    @Override
    public void fillTooltipOverArea(int mouseX, int mouseY, List<Component> tooltip)
    {
        tooltip.add(Component.literal(energy.getEnergyStored()+"/"+energy.getMaxEnergyStored()+" FE"));
    }

    @Override
    public void draw(GuiGraphics graphics)
    {
        final int height = area.getHeight();
        int stored = (int)(height * (energy.getEnergyStored() / (float)energy.getMaxEnergyStored()));
        graphics.fillGradient(area.getX(), area.getY() + (height - stored), area.getX() + area.getWidth(), area.getY() + area.getHeight(), 0xffb51500, 0xff600b00);
    }
}
