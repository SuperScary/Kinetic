package superscary.kinetic.item;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import superscary.kinetic.block.blocks.MachineBlock;

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

    @Override
    public boolean canAttackBlock (BlockState state, Level level, BlockPos pos, Player player)
    {
        return state.getBlock() instanceof MachineBlock;
    }
}
