package superscary.kinetic.item;

import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.phys.Vec3;

//TODO: Store boolean data to item with NBT && pull items
public class MagnetItem extends Item
{

    private boolean enabled;
    private Vec3 pullRange = new Vec3(16, 16, 16);

    public MagnetItem ()
    {
        super(new Properties().durability(24000).defaultDurability(0));
        enabled = false;
    }

    @Override
    public Rarity getRarity (ItemStack pStack)
    {
        if (enabled) return Rarity.EPIC;
        else return Rarity.COMMON;
    }

    @Override
    public InteractionResult useOn (UseOnContext pContext)
    {
        if (enabled) enabled = false;
        else enabled = true;
        return InteractionResult.PASS;
    }

    public boolean isEnabled ()
    {
        return enabled;
    }

    public Vec3 getPullRange ()
    {
        return pullRange;
    }

}
