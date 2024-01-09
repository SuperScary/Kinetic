package superscary.kinetic.block.cables;

import net.minecraft.util.StringRepresentable;

import java.util.Locale;

public enum ConnectorType implements StringRepresentable
{
    NONE,
    CABLE,
    BLOCK;

    public static final ConnectorType[] VALUES = values();

    @Override
    public String getSerializedName ()
    {
        return name().toLowerCase(Locale.ROOT);
    }

}
