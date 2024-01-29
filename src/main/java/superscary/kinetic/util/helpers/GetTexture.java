package superscary.kinetic.util.helpers;

import net.minecraft.resources.ResourceLocation;
import superscary.kinetic.Kinetic;

public class GetTexture
{

    public static ResourceLocation ofGui (String name)
    {
        return Kinetic.getResource("textures/gui/" + name + "_gui.png");
    }

}
