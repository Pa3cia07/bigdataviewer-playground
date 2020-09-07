package sc.fiji.bdvpg.services.serializers;

import bdv.tools.transformation.TransformedSource;
import bdv.viewer.SourceAndConverter;
import com.google.gson.*;
import net.imglib2.realtransform.AffineTransform3D;
import sc.fiji.bdvpg.services.SourceAndConverterSerializer;

import java.lang.reflect.Type;

public class TransformedSourceAndConverterAdapter {

    SourceAndConverterSerializer sacSerializer;

    public TransformedSourceAndConverterAdapter(SourceAndConverterSerializer sacSerializer) {
        this.sacSerializer = sacSerializer;
    }
    public JsonElement serialize(SourceAndConverter sac, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject obj = new JsonObject();

        TransformedSource source = (TransformedSource) sac.getSpimSource();
        AffineTransform3D fixedTr = new AffineTransform3D();
        AffineTransform3D incrTr = new AffineTransform3D();
        source.getIncrementalTransform(incrTr);
        source.getFixedTransform(fixedTr);

        obj.add("affinetransform_fixed", jsonSerializationContext.serialize(fixedTr));
        obj.add("affinetransform_incremental", jsonSerializationContext.serialize(incrTr));
        obj.addProperty("wrapped_source_id", sacSerializer.getSourceToId().get(source.getWrappedSource()));
        return obj;
    }

    public SourceAndConverter deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            /*JsonObject obj = jsonElement.getAsJsonObject();
            AbstractSpimData asd = jsonDeserializationContext.deserialize(obj.get("spimdata"), AbstractSpimData.class);
            int setupId = obj.getAsJsonPrimitive("viewsetup").getAsInt();
            final ISourceAndConverterService sacservice =  SourceAndConverterServices
                    .getSourceAndConverterService();
            Optional<SourceAndConverter> futureSac = sacservice.getSourceAndConverters()
                    .stream()
                    .filter(sac -> sacservice.containsMetadata(sac, SPIM_DATA_INFO))
                    .filter(sac -> {
                        SourceAndConverterService.SpimDataInfo sdi = (SourceAndConverterService.SpimDataInfo) sacservice.getMetadata(sac,SPIM_DATA_INFO);
                        return sdi.asd.equals(asd)&&sdi.setupId ==setupId;
                    }).findFirst();
            if (futureSac.isPresent()) {
                return futureSac.get();
            } else {
                System.err.println("Couldn't deserialize spim source from json element "+jsonElement.getAsString());
                return null;
            }*/
        return null;

    }
}