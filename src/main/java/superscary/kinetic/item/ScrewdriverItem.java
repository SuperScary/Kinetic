package superscary.kinetic.item;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import superscary.kinetic.block.blocks.MachineBlock;

public class ScrewdriverItem extends Item
{

    public ScrewdriverItem ()
    {
        super(new Item.Properties().stacksTo(1));
    }

}
