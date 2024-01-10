package superscary.kinetic.item;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;

public class CapacitorItem extends Item
{

    private final int maxCap;
    private final int maxTrans;
    private final int rarity;

    public CapacitorItem (Properties properties, int maxCap, int maxTrans, int rarity)
    {
        super(properties);
        this.maxCap = maxCap;
        this.maxTrans = maxTrans;
        this.rarity = rarity;
    }

    @Override
    public Rarity getRarity (ItemStack p_41461_)
    {
        return switch (rarity)
        {
            default -> Rarity.COMMON;
            case 2 -> Rarity.UNCOMMON;
            case 3 -> Rarity.RARE;
            case 4 -> Rarity.EPIC;
        };
    }

    public int getMaxCapacity ()
    {
        return maxCap;
    }

    public int getMaxTransfer ()
    {
        return maxTrans;
    }

}
