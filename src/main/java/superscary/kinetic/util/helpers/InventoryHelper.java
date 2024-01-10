package superscary.kinetic.util.helpers;

import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import superscary.kinetic.item.CapacitorItem;

public class InventoryHelper
{

    public static boolean capacitorIsValid (IItemHandler handler, int slot)
    {
        if (handler == null) return false;
        if (handler.getStackInSlot(slot).isEmpty()) return false;

        ItemStack stack = handler.getStackInSlot(slot);
        return stack.getItem() instanceof CapacitorItem;
    }

}
