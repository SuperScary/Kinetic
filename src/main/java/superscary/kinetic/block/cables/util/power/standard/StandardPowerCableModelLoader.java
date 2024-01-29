package superscary.kinetic.block.cables.util.power.standard;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.Material;
import net.minecraft.client.resources.model.ModelBaker;
import net.minecraft.client.resources.model.ModelState;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.event.ModelEvent;
import net.minecraftforge.client.model.geometry.IGeometryBakingContext;
import net.minecraftforge.client.model.geometry.IGeometryLoader;
import net.minecraftforge.client.model.geometry.IUnbakedGeometry;
import superscary.kinetic.Kinetic;

import java.util.function.Function;

public class StandardPowerCableModelLoader implements IGeometryLoader<StandardPowerCableModelLoader.CableModelGeometry>
{

    public static final ResourceLocation GENERATOR_LOADER = new ResourceLocation(Kinetic.MODID, "standardcableloader");

    public static void register (ModelEvent.RegisterGeometryLoaders event)
    {
        event.register("standardcableloader", new StandardPowerCableModelLoader());
    }


    @Override
    public CableModelGeometry read (JsonObject jsonObject, JsonDeserializationContext deserializationContext) throws JsonParseException
    {
        boolean facade = jsonObject.has("facade") && jsonObject.get("facade").getAsBoolean();
        return new CableModelGeometry(facade);
    }

    public static class CableModelGeometry implements IUnbakedGeometry<CableModelGeometry>
    {

        private final boolean facade;

        public CableModelGeometry (boolean facade)
        {
            this.facade = facade;
        }

        @Override
        public BakedModel bake (IGeometryBakingContext context, ModelBaker baker, Function<Material, TextureAtlasSprite> spriteGetter, ModelState modelState, ItemOverrides overrides, ResourceLocation modelLocation)
        {
            return new StandardPowerCableBakedModel(context, facade);
        }
    }
}