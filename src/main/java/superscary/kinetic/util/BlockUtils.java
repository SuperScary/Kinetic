package superscary.kinetic.util;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

public class BlockUtils
{

    public static final int MAX_WORLD_Y = 320;

    public static boolean solarPanelPlacementValid (Level level, BlockPos pos)
    {
        return level.isDay();
    }

}
