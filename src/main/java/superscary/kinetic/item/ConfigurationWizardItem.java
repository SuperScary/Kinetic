package superscary.kinetic.item;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.entity.BlockEntity;
import superscary.kinetic.util.IConfigurator;

//TODO: Create menus and options for this to change.
public class ConfigurationWizardItem extends Item
{

    public ConfigurationWizardItem ()
    {
        super(new Properties().stacksTo(1));
    }

    @Override
    public InteractionResult useOn (UseOnContext pContext)
    {
        pContext.getClickedPos();
        BlockPos pos = pContext.getClickedPos();
        BlockEntity blockEntity = pContext.getPlayer().level().getBlockEntity(pos);
        if (blockEntity.getClass().isInstance(IConfigurator.class))
        {

        }
        return super.useOn(pContext);
    }
}
