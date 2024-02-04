package superscary.kinetic.util.helpers;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;

public class FluidUtils
{

    public static void fillTank (FluidTank tank, FluidStack stack, ItemStack container, Player player)
    {
        tank.fill(new FluidStack(stack.getFluid(), stack.getAmount()), IFluidHandler.FluidAction.EXECUTE);
        int slot = player.getInventory().selected;
        player.getInventory().setItem(slot, container);
    }

}
