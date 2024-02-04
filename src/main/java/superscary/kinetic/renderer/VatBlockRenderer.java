package superscary.kinetic.renderer;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions;
import net.minecraftforge.common.util.Lazy;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidType;
import org.jetbrains.annotations.NotNull;
import superscary.kinetic.block.blocks.DraftingTableBlock;
import superscary.kinetic.block.entity.VatBlockEntity;

public class VatBlockRenderer implements BlockEntityRenderer<VatBlockEntity>
{

    private final BlockEntityRendererProvider.Context context;

    public VatBlockRenderer (BlockEntityRendererProvider.Context context)
    {
        this.context = context;
    }

    @Override
    public void render (VatBlockEntity pBlockEntity, float pPartialTick, @NotNull PoseStack pPoseStack, @NotNull MultiBufferSource pBufferSource, int pPackedLight, int pPackedOverlay)
    {
        renderFluid(pBlockEntity, pPartialTick, pPoseStack, pBufferSource, pPackedLight, pPackedOverlay);
        renderItem(pBlockEntity, pPartialTick, pPoseStack, pBufferSource, pPackedLight, pPackedOverlay);
    }

    private void renderItem (VatBlockEntity pBlockEntity, float pPartialTick, @NotNull PoseStack pPoseStack, @NotNull MultiBufferSource pBufferSource, int pPackedLight, int pPackedOverlay)
    {
        ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
        BlockState state = pBlockEntity.getBlockState();
        if (state == null) return;

        Block block = state.getBlock();
        if (block == null) return;

        ItemStack itemStack = pBlockEntity.getItems().getStackInSlot(0);

        pPoseStack.pushPose();
        pPoseStack.translate(0.5f, 0.935f, 0.5f);
        pPoseStack.scale(0.5f, 0.5f, 0.5f);
        pPoseStack.mulPose(Axis.YN.rotationDegrees(pBlockEntity.getBlockState().getValue(DraftingTableBlock.FACING).toYRot()));
        pPoseStack.mulPose(Axis.XP.rotationDegrees(270));

        itemRenderer.renderStatic(itemStack, ItemDisplayContext.FIXED, getLightLevel(pBlockEntity.getLevel(), pBlockEntity.getBlockPos()), OverlayTexture.NO_OVERLAY, pPoseStack, pBufferSource, pBlockEntity.getLevel(), 1);
        pPoseStack.popPose();
    }

    private void renderFluid (VatBlockEntity pBlockEntity, float pPartialTick, @NotNull PoseStack pPoseStack, @NotNull MultiBufferSource pBufferSource, int pPackedLight, int pPackedOverlay)
    {
        FluidStack fluid = pBlockEntity.getFluidTank().getFluid();
        Lazy<VertexConsumer> buffer = Lazy.of(() -> pBufferSource.getBuffer(Sheets.translucentCullBlockSheet()));
        FluidType type = fluid.getFluid().getFluidType();
        if (!fluid.isEmpty() && fluid.getAmount() > 0)
        {
            RenderSystem.setShaderTexture(0, getStillFluidSprite(fluid).atlasLocation());
            pPoseStack.pushPose();
            pPoseStack.translate(0.5, 0.0625, 0.5);
            pPoseStack.scale(0.875f, 0.875f, 0.875f);
            pPoseStack.mulPose(Axis.YP.rotationDegrees(180));
            pPoseStack.mulPose(Axis.XP.rotationDegrees(270));
            int color = getColor(fluid);
            int r = (color >> 16) & 0xFF;
            int g = (color >> 8) & 0xFF;
            int b = color & 0xFF;
            buffer.get().vertex(pPoseStack.last().pose(), -0.5f, 0, -0.5f).color(r, g, b, 255).uv(0, 0).uv2(pPackedLight).endVertex();
            buffer.get().vertex(pPoseStack.last().pose(), -0.5f, 0, 0.5f).color(r, g, b, 255).uv(0, 1).uv2(pPackedLight).endVertex();
            buffer.get().vertex(pPoseStack.last().pose(), 0.5f, 0, 0.5f).color(r, g, b, 255).uv(1, 1).uv2(pPackedLight).endVertex();
            buffer.get().vertex(pPoseStack.last().pose(), 0.5f, 0, -0.5f).color(r, g, b, 255).uv(1, 0).uv2(pPackedLight).endVertex();
            pPoseStack.popPose();
        }

    }

    private int getLightLevel (Level level, BlockPos pos)
    {
        int bLight = level.getBrightness(LightLayer.BLOCK, pos);
        int sLight = level.getBrightness(LightLayer.SKY, pos);
        return LightTexture.pack(bLight, sLight);
    }

    private int getColor (FluidStack stack)
    {
        Fluid fluid = stack.getFluid();
        IClientFluidTypeExtensions renderProperties = IClientFluidTypeExtensions.of(fluid);
        return renderProperties.getTintColor(stack);
    }

    private TextureAtlasSprite getStillFluidSprite (FluidStack stack)
    {
        Fluid fluid = stack.getFluid();
        IClientFluidTypeExtensions renderProperties = IClientFluidTypeExtensions.of(fluid);
        ResourceLocation fluidStill = renderProperties.getStillTexture(stack);
        Minecraft minecraft = Minecraft.getInstance();
        return minecraft.getTextureAtlas(InventoryMenu.BLOCK_ATLAS).apply(fluidStill);
    }

}
