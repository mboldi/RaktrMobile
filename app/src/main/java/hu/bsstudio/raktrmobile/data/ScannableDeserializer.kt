package hu.bsstudio.raktrmobile.data

import com.google.gson.*
import hu.bsstudio.raktrmobile.model.CompositeItem
import hu.bsstudio.raktrmobile.model.Device
import hu.bsstudio.raktrmobile.model.Scannable
import java.lang.reflect.Type


class ScannableDeserializer : JsonDeserializer<Scannable> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): Scannable {
        val jsonObject = json!!.asJsonObject
        val classNamePrimitive = jsonObject["@type"] as JsonPrimitive

        val className = classNamePrimitive.asString

        when(className) {
            "device" -> return context!!.deserialize(jsonObject, Device::class.java)
            "compositeItem" -> return context!!.deserialize(jsonObject, CompositeItem::class.java)
        }

        throw JsonParseException("Couldnt deserialize Scannable")
    }
}