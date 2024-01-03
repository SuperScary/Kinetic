package superscary.kinetic.item;

import net.minecraft.client.resources.language.I18n;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.Tags;

public class OreFinderItem extends Item
{

    public OreFinderItem (Properties properties)
    {
        super(properties);
    }

    @Override
    public InteractionResult useOn (UseOnContext context)
    {
        if (!context.getLevel().isClientSide())
        {
            BlockPos positionClicked = context.getClickedPos();
            Player player = context.getPlayer();
            boolean foundBlock = false;

            for (int i = 0; i <= positionClicked.getY() + 64; i++)
            {
                BlockState state = context.getLevel().getBlockState(positionClicked.below(i));
                if (findOres(state))
                {
                    displayCoords(positionClicked.below(i), player, state);
                    foundBlock = true;
                    break;
                }
            }

            if (!foundBlock) player.sendSystemMessage(Component.translatable("chat.ore_not_found"));

        }

        context.getItemInHand().hurtAndBreak(1, context.getPlayer(), player -> player.broadcastBreakEvent(player.getUsedItemHand()));
        return InteractionResult.SUCCESS;
    }

    private void displayCoords (BlockPos pos, Player player, BlockState state)
    {
        player.sendSystemMessage(Component.translatable("chat.ore_found", I18n.get(state.getBlock().getDescriptionId()), pos.getX(), pos.getY(), pos.getZ()));
    }

    private boolean findOres (BlockState state)
    {
        return state.is(Tags.Blocks.ORES);
    }
}
