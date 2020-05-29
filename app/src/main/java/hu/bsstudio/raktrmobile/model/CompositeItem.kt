package hu.bsstudio.raktrmobile.model

import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class CompositeItem(
    override var barcode: String = "",
    override var id: Long = 0L,
    override var name: String = "",
    var devices: MutableList<Device> = mutableListOf(),
    var location: Location? = null,
    @SerializedName("@type")
    override val objType: String = "compositeItem"
) : Scannable(), Serializable {

    fun toJsonWithRoot(): JsonObject {
        val gson = Gson()
        val requestJson = JsonObject()
        val json = gson.toJsonTree(this)
        requestJson.add("CompositeItem", json)

        return requestJson
    }
}