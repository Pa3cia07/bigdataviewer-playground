/*-
 * #%L
 * BigDataViewer-Playground
 * %%
 * Copyright (C) 2019 - 2020 Nicolas Chiaruttini, EPFL - Robert Haase, MPI CBG - Christian Tischer, EMBL
 * %%
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 
 * 1. Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 * #L%
 */
package sc.fiji.bdvpg.services.serializers.plugins;

import com.google.gson.*;
import net.imglib2.realtransform.InvertibleRealTransform;
import net.imglib2.realtransform.RealTransform;
import net.imglib2.realtransform.Wrapped2DTransformAs3D;
import org.scijava.plugin.Plugin;

import java.lang.reflect.Type;

@Plugin(type = IClassRuntimeAdapter.class)
public class Wrapped2DTransformAs3DRealTransformAdapter implements IClassRuntimeAdapter<RealTransform, Wrapped2DTransformAs3D> {
    @Override
    public Class<? extends RealTransform> getBaseClass() {
        return RealTransform.class;
    }

    @Override
    public Class<? extends Wrapped2DTransformAs3D> getRunTimeClass() {
        return Wrapped2DTransformAs3D.class;
    }

    @Override
    public Wrapped2DTransformAs3D deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject obj = jsonElement.getAsJsonObject();
        RealTransform rt = jsonDeserializationContext.deserialize(obj.get("wrappedTransform"), RealTransform.class);

        if (!(rt instanceof InvertibleRealTransform)) {
            System.err.println("Wrapped transform not invertible -> deserialization impossible...");
            // TODO : see if autowrapping works ?
            return null;
        }

        Wrapped2DTransformAs3D wrapped2DTransformAs3D =
                new Wrapped2DTransformAs3D((InvertibleRealTransform) rt);
        return wrapped2DTransformAs3D;
    }

    @Override
    public JsonElement serialize(Wrapped2DTransformAs3D wrapped2DTransformAs3D, Type type, JsonSerializationContext jsonSerializationContext) {
        Wrapped2DTransformAs3D rt = wrapped2DTransformAs3D;
        JsonObject obj = new JsonObject();

        obj.addProperty("type", Wrapped2DTransformAs3D.class.getSimpleName());
        obj.add("wrappedTransform", jsonSerializationContext.serialize(rt.getTransform()));
        return obj;
    }
}
