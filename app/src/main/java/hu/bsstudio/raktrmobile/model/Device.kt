package hu.bsstudio.raktrmobile.model

import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Device(
    override var id: Long = 0L,
    override var name: String = "",
    override var barcode: String = "",
    var category: Category? = null,
    var location: Location? = null,
    var maker: String = "",
    var quantity: Int = 0,
    var serial: String = "",
    var status: DeviceStatus? = null,
    var type: String = "",
    var value: Int = 0,
    var weight: Int = 0,
    @SerializedName("@type")
    override val objType: String = "device"
) : Scannable(), Serializable {

    fun toJsonWithRoot(): JsonObject {
        val gson = Gson()
        val requestJson = JsonObject()
        val deviceJson = gson.toJsonTree(this)
        requestJson.add("Device", deviceJson)

        return requestJson
    }
}