package superscary.kinetic.util;

import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;

public class ReplaceBlockItemUseContext extends BlockPlaceContext
{

    public ReplaceBlockItemUseContext (UseOnContext context)
    {
        super(context);
        replaceClicked = true;
    }

}
