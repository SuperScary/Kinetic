package superscary.kinetic.gui.renderer;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.Rect2i;
import net.minecraft.network.chat.Component;

import java.util.List;

public abstract class InfoArea
{
    protected final Rect2i area;

    protected InfoArea(Rect2i area)
    {
        this.area = area;
    }

    public final void fillTooltip(int mouseX, int mouseY, List<Component> tooltip)
    {
        if(area.contains(mouseX, mouseY))
            fillTooltipOverArea(mouseX, mouseY, tooltip);
    }

    protected abstract void fillTooltipOverArea(int mouseX, int mouseY, List<Component> tooltip);

    public abstract void draw(GuiGraphics graphics);
}
