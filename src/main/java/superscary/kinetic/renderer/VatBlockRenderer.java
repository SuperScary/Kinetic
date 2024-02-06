package superscary.kinetic.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraftforge.fluids.FluidStack;
import org.jetbrains.annotations.NotNull;
import superscary.kinetic.block.blocks.DraftingTableBlock;
import superscary.kinetic.block.entity.VatBlockEntity;
import superscary.kinetic.util.helpers.FluidUtils;

public class VatBlockRenderer implements BlockEntityRenderer<VatBlockEntity>
{

    private float height = 0.125f;

    private final BlockEntityRendererProvider.Context context;

    public VatBlockRenderer (BlockEntityRendererProvider.Context context)
    {
        this.context = context;
    }

    @Override
    public void render (VatBlockEntity pBlockEntity, float pPartialTick, @NotNull PoseStack pPoseStack, @NotNull MultiBufferSource pBufferSource, int pPackedLight, int pPackedOverlay)
    {
        renderFluid(pBlockEntity, pPartialTick, pPoseStack, pBufferSource, pPackedLight, pPackedOverlay);
        renderItem(pBlockEntity, pPartialTick, pPoseStack, pBufferSource, pPackedLight, pPackedOverlay, 0);
    }

    private void renderItem (VatBlockEntity pBlockEntity, float pPartialTick, @NotNull PoseStack pPoseStack, @NotNull MultiBufferSource pBufferSource, int pPackedLight, int pPackedOverlay, int slot)
    {
        ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
        BlockState state = pBlockEntity.getBlockState();
        if (state == null) return;

        Block block = state.getBlock();
        if (block == null) return;

        ItemStack itemStack = pBlockEntity.getItems().getStackInSlot(slot);

        float x = slot == 0 ? 0.25f : 0.75f;

        pPoseStack.pushPose();
        pPoseStack.translate(x, height, 0.5f);
        pPoseStack.scale(0.5f, 0.5f, 0.5f);
        pPoseStack.mulPose(Axis.XP.rotationDegrees(90));

        itemRenderer.renderStatic(itemStack, ItemDisplayContext.FIXED, getLightLevel(pBlockEntity.getLevel(), pBlockEntity.getBlockPos()), OverlayTexture.NO_OVERLAY, pPoseStack, pBufferSource, pBlockEntity.getLevel(), 1);
        pPoseStack.popPose();
    }

    private void renderFluid (VatBlockEntity pBlockEntity, float pPartialTick, @NotNull PoseStack pPoseStack, @NotNull MultiBufferSource pBufferSource, int pPackedLight, int pPackedOverlay)
    {
        FluidStack fluidStack = pBlockEntity.getFluidTank().getFluid();
        if (fluidStack.isEmpty()) return;

        FluidState state = fluidStack.getFluid().defaultFluidState();

        TextureAtlasSprite sprite = FluidUtils.getStillFluidSprite(fluidStack);
        int tint = FluidUtils.getTintColor(fluidStack, pBlockEntity.getLevel(), pBlockEntity.getBlockPos());
        FluidUtils.RGBA rgba = new FluidUtils.RGBA(tint);

        height = ((((float) pBlockEntity.getFluidTank().getFluidAmount()) / ((float) pBlockEntity.getFluidTank().getCapacity())) * 0.750f) + 0.125f;

        VertexConsumer builder = pBufferSource.getBuffer(ItemBlockRenderTypes.getRenderLayer(state));
        drawQuad(builder, pPoseStack, 0.0f, height, 0.0f, 1f, height, 1f, sprite.getU0(), sprite.getV0(), sprite.getU1(), sprite.getV1(), pPackedLight, rgba);

    }

    private int getLightLevel (Level level, BlockPos pos)
    {
        int bLight = level.getBrightness(LightLayer.BLOCK, pos);
        int sLight = level.getBrightness(LightLayer.SKY, pos);
        return LightTexture.pack(bLight, sLight);
    }

    private static void drawVertex (VertexConsumer builder, PoseStack poseStack, float x, float y, float z, float u, float v, int packedLight, FluidUtils.RGBA rgba)
    {
        builder.vertex(poseStack.last().pose(), x, y, z).color(rgba.tint()).uv(u, v).uv2(packedLight).normal(1, 0, 0).endVertex();
    }

    private static void drawQuad (VertexConsumer builder, PoseStack poseStack, float x0, float y0, float z0, float x1, float y1, float z1, float u0, float v0, float u1, float v1, int packedLight, FluidUtils.RGBA rgba)
    {
        drawVertex(builder, poseStack, x0, y0, z0, u0, v0, packedLight, rgba);
        drawVertex(builder, poseStack, x0, y1, z1, u0, v1, packedLight, rgba);
        drawVertex(builder, poseStack, x1, y1, z1, u1, v1, packedLight, rgba);
        drawVertex(builder, poseStack, x1, y0, z0, u1, v0, packedLight, rgba);
    }

}
