package hu.bsstudio.raktrmobile.model

import com.google.gson.Gson
import com.google.gson.JsonObject
import java.io.Serializable

data class Rent(
    var actBackDate: String = "",
    var destination: String = "",
    var rentItems: MutableList<RentItem> = mutableListOf(),
    var expBackDate: String = "",
    var id: Long = 0L,
    var issuer: String = "",
    var renter: String = "",
    var outDate: String = ""
) : Serializable {

    fun toJsonWithRoot(): JsonObject {
        val gson = Gson()
        val requestJson = JsonObject()
        val json = gson.toJsonTree(this)
        requestJson.add("Rent", json)

        return requestJson
    }
}