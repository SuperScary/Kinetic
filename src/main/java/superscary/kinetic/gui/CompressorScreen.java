package superscary.kinetic.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.entity.player.Inventory;
import superscary.kinetic.Kinetic;
import superscary.kinetic.gui.renderer.EnergyInfoArea;
import superscary.kinetic.util.MouseUtil;

import java.util.Optional;

public class CompressorScreen extends AbstractContainerScreen<CompressorMenu>
{

    private static final ResourceLocation TEXTURE = new ResourceLocation(Kinetic.MODID, "textures/gui/compressor_gui.png");
    private EnergyInfoArea energyInfoArea;

    public CompressorScreen (CompressorMenu menu, Inventory inventory, Component title)
    {
        super(menu, inventory, title);
    }

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
        energyInfoArea = new EnergyInfoArea(x + 10, y +9, menu.blockEntity.getEnergyStorage());
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
        if (isMouseAboveArea(mouseX, mouseY, x, y, 10, 9, 8, 64))
        {
            //energyInfoArea.fillTooltipOverArea(mouseX - x, mouseY - y, null);
        }
    }

    @Override
    protected void renderBg (GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY)
    {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
        RenderSystem.setShaderTexture(0, TEXTURE);
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        guiGraphics.blit(TEXTURE, x, y, 0, 0, imageWidth, imageHeight);

        renderProgressArrow(guiGraphics, x, y);
        energyInfoArea.draw(guiGraphics);
    }

    private void renderProgressArrow (GuiGraphics guiGraphics, int x, int y)
    {
        if (menu.isCrafting())
        {
            guiGraphics.blit(TEXTURE, x + 80, y + 35, 176, 0, menu.getScaledProgress(), 24);
        }
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

}
