package superscary.kinetic.item;

import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;

public class WrenchItem extends Item
{

    public WrenchItem (Properties properties)
    {
        super(properties);
    }

    @Override
    public InteractionResult useOn (UseOnContext context)
    {
        context.getItemInHand().hurtAndBreak(1, context.getPlayer(), player -> player.broadcastBreakEvent(player.getUsedItemHand()));
        return InteractionResult.SUCCESS;
    }
}
