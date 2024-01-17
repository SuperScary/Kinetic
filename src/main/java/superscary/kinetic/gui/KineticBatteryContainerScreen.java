package superscary.kinetic.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraftforge.energy.IEnergyStorage;
import superscary.kinetic.gui.renderer.EnergyDisplayTooltipArea;
import superscary.kinetic.util.helpers.MouseUtil;

import java.util.List;
import java.util.Optional;

public abstract class KineticBatteryContainerScreen<T extends KineticContainerMenu> extends KineticContainerScreen<T>
{

    private static final int ENERGY_LEFT = 49;
    private static final int ENERGY_WIDTH = 78;
    private static final int ENERGY_TOP = 9;
    private static final int ENERGY_HEIGHT = 64;

    private EnergyDisplayTooltipArea energyInfoArea;

    public KineticBatteryContainerScreen (T menu, Inventory inventory, Component title)
    {
        super(menu, inventory, title);
        this.imageWidth = 176;
    }

    public abstract ResourceLocation getTexture ();

    public abstract IEnergyStorage getEnergyStorage ();

    @Override
    protected void init ()
    {
        super.init();
        assignEnergyInfoArea();
    }

    private void assignEnergyInfoArea ()
    {
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;
        this.energyInfoArea = new EnergyDisplayTooltipArea(x + 10, y + 9, getEnergyStorage());
    }

    @Override
    protected void renderLabels (GuiGraphics graphics, int mouseX, int mouseY)
    {
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;
        renderEnergyAreaTooltips(graphics, mouseX, mouseY, x, y);
    }

    private void renderEnergyAreaTooltips (GuiGraphics guiGraphics, int mouseX, int mouseY, int x, int y)
    {
        if (isMouseAboveArea(mouseX, mouseY, x, y, ENERGY_LEFT, ENERGY_TOP, ENERGY_WIDTH, ENERGY_HEIGHT))
        {
            guiGraphics.renderTooltip(this.font, getTooltips(), Optional.empty(), mouseX - x, mouseY - y);
        }
    }

    @Override
    protected void renderBg (GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY)
    {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
        RenderSystem.setShaderTexture(0, getTexture());
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        guiGraphics.blit(getTexture(), x, y, 0, 0, imageWidth, imageHeight);
        int power = menu.getPower();
        int p = (int) ((power / (float) getEnergyStorage().getMaxEnergyStored()) * ENERGY_HEIGHT);
        int left = leftPos + ENERGY_LEFT;
        int top = topPos + ENERGY_TOP;
        int e_left = left + ENERGY_WIDTH;
        int e_top = top + ENERGY_HEIGHT;
        guiGraphics.fillGradient(e_left, e_top, left, e_top - p, 0xff000000, 0x21FC0D);
    }

    @Override
    public void render (GuiGraphics guiGraphics, int mouseX, int mouseY, float delta)
    {
        renderBackground(guiGraphics);
        super.render(guiGraphics, mouseX, mouseY, delta);
        renderTooltip(guiGraphics, mouseX, mouseY);
    }

    private boolean isMouseAboveArea (int pMouseX, int pMouseY, int x, int y, int offsetX, int offsetY, int width, int height)
    {
        return MouseUtil.isMouseOver(pMouseX, pMouseY, x + offsetX, y + offsetY, width, height);
    }

    public List<Component> getTooltips ()
    {
        return List.of(Component.literal(menu.getPower() + " / " + getEnergyStorage().getMaxEnergyStored() + " FE"));
    }

}
