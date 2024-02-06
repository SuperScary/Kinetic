package superscary.kinetic.util.helpers;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;

public class FluidUtils
{

    public static void fillTank (FluidTank tank, FluidStack stack, ItemStack container, Player player)
    {
        tank.fill(new FluidStack(stack.getFluid(), stack.getAmount()), IFluidHandler.FluidAction.EXECUTE);
        int slot = player.getInventory().selected;
        if (!player.isCreative() || !player.isSpectator()) player.getInventory().setItem(slot, container);
    }

    public static int getTintColor (FluidStack stack, Level level, BlockPos pos)
    {
        return getTintColor(stack.getFluid().defaultFluidState(), level, pos);
    }

    public static int getTintColor (FluidState state, Level level, BlockPos pos)
    {
        Fluid fluid = state.getType();
        IClientFluidTypeExtensions fluidTypeExtensions = IClientFluidTypeExtensions.of(fluid);
        return fluidTypeExtensions.getTintColor(state, level, pos);
    }

    public static TextureAtlasSprite getStillFluidSprite (FluidStack stack)
    {
        Fluid fluid = stack.getFluid();
        IClientFluidTypeExtensions renderProperties = IClientFluidTypeExtensions.of(fluid);
        ResourceLocation fluidStill = renderProperties.getStillTexture(stack);
        Minecraft minecraft = Minecraft.getInstance();
        return minecraft.getTextureAtlas(InventoryMenu.BLOCK_ATLAS).apply(fluidStill);
    }

    public record RGBA (int tint)
    {
        public float getAlpha ()
        {
            return ((tint >> 24) & 0xFF) / 255f;
        }

        public float getRed ()
        {
            return ((tint >> 16) & 0xFF) / 255f;
        }

        public float getGreen ()
        {
            return ((tint >> 8) & 0xFF) / 255f;
        }

        public float getBlue ()
        {
            return (tint & 0xFF) / 255f;
        }
    }

}
