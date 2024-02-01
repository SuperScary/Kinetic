package superscary.kinetic.block.blocks;

import net.minecraft.world.level.block.Block;

public class MachineBlock extends Block
{

    private final int rarity;

    public MachineBlock (Properties properties, int rarity)
    {
        super(properties.noOcclusion());
        this.rarity = rarity;
    }

    public MachineBlock (Properties properties)
    {
        this(properties, 1);
    }

}
