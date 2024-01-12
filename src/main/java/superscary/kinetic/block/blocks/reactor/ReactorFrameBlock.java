package superscary.kinetic.block.blocks.reactor;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class ReactorFrameBlock extends Block
{

    public ReactorFrameBlock ()
    {
        super (BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).strength(3.5f));
    }

}
