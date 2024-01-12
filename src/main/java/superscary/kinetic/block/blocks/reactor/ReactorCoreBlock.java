package superscary.kinetic.block.blocks.reactor;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class ReactorCoreBlock extends Block
{

    public ReactorCoreBlock ()
    {
        super(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).strength(4.0f));
    }

}
