package superscary.kinetic.block.cables.blocks.entity.power;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;
import superscary.kinetic.register.KineticBlockEntities;

import java.util.Set;
import java.util.function.Consumer;

public class UltimatePowerCableBlockEntity extends BasePowerCableBlockEntity
{

    public UltimatePowerCableBlockEntity (BlockPos pos, BlockState state)
    {
        super(KineticBlockEntities.ULTIMATE_POWER_CABLE_BE.get(), pos, state, PowerCableType.ULTIMATE);
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
                if (level.getBlockEntity(p) instanceof UltimatePowerCableBlockEntity cable)
                {
                    consumer.accept(cable);
                    cable.traverse(p, traversed, consumer);
                }
            }
        }
    }

}