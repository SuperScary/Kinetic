package superscary.kinetic.block.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import superscary.kinetic.block.KineticBaseEntityBlock;
import superscary.kinetic.block.entity.ElectricMotorBlockEntity;
import superscary.kinetic.register.KineticBlocks;

public class ElectricMotorBlock extends KineticBaseEntityBlock
{
    public ElectricMotorBlock ()
    {
        super(BlockBehaviour.Properties.copy(KineticBlocks.MACHINE_FRAME.get()).strength(3.5f).requiresCorrectToolForDrops().noOcclusion());
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity (BlockPos blockPos, BlockState blockState)
    {
        return new ElectricMotorBlockEntity(blockPos, blockState);
    }

}
