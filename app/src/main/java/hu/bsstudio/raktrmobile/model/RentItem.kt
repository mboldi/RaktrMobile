package hu.bsstudio.raktrmobile.model

import com.google.gson.Gson
import com.google.gson.JsonObject
import java.io.Serializable

data class RentItem(
    var id: Long,
    var backStatus: BackStatus,
    var outQuantity: Int,
    var scannable: Scannable
) : Serializable {

    fun toJsonWithRoot(): JsonObject {
        val gson = Gson()
        val requestJson = JsonObject()
        val json = gson.toJsonTree(this)
        requestJson.add("RentItem", json)

        return requestJson
    }
}