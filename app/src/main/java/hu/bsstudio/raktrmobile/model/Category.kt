package hu.bsstudio.raktrmobile.model

import com.google.gson.Gson
import com.google.gson.JsonObject
import java.io.Serializable

data class Category(
    var id: Long = 0L,
    var name: String = ""
) : Serializable {

    fun toJsonWithRoot(): JsonObject {
        val gson = Gson()
        val requestJson = JsonObject()
        val json = gson.toJsonTree(this)
        requestJson.add("Category", json)

        return requestJson
    }
}