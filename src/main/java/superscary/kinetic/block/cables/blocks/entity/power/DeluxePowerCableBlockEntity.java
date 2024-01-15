package superscary.kinetic.block.cables.blocks.entity.power;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;
import superscary.kinetic.block.KineticBlockEntities;

import java.util.Set;
import java.util.function.Consumer;

public class DeluxePowerCableBlockEntity extends BasePowerCableBlockEntity
{

    public DeluxePowerCableBlockEntity (BlockPos pos, BlockState state)
    {
        super(KineticBlockEntities.DELUXE_POWER_CABLE_BE.get(), pos, state);
    }

    @Override
    public void traverse (BlockPos pos, Set<BlockPos> traversed, Consumer<BasePowerCableBlockEntity> consumer)
    {
        for (Direction direction : Direction.values())
        {
            BlockPos p = pos.relative(direction);
            if (!traversed.contains(p))
            {
                traversed.add(p);
                if (level.getBlockEntity(p) instanceof DeluxePowerCableBlockEntity cable)
                {
                    consumer.accept(cable);
                    cable.traverse(p, traversed, consumer);
                }
            }
        }
    }

}