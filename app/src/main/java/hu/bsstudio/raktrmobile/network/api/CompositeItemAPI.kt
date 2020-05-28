package hu.bsstudio.raktrmobile.network.api

import com.google.gson.JsonObject
import hu.bsstudio.raktrmobile.model.CompositeItem
import hu.bsstudio.raktrmobile.model.Device
import retrofit2.Call
import retrofit2.http.*

interface CompositeItemAPI {

    @GET("composite")
    fun getCompositeItems(): Call<List<CompositeItem>>

    @Headers("Content-Type: application/json")
    @POST("composite")
    fun addCompositeItem(@Body compositeItem: JsonObject): Call<CompositeItem>

    @Headers("Content-Type: application/json")
    @PUT("composite")
    fun updateCompositeItem(@Body compositeItem: JsonObject): Call<CompositeItem>

    @GET("composite/{id}")
    fun getOneCompositeItem(@Path("id") id: Long): Call<CompositeItem>

    @Headers("Content-Type: application/json")
    @HTTP(method = "DELETE", path = "composite", hasBody = true)
    fun deleteCompositeItem(@Body compositeItem: JsonObject): Call<CompositeItem>

    @Headers("Content-Type: application/json")
    @PUT("composite/{id}")
    fun addDeviceToComposite(@Path("id") id: Long, @Body compositeItem: JsonObject): Call<CompositeItem>

    @Headers("Content-Type: application/json")
    @HTTP(method = "DELETE", path = "composite/{id}", hasBody = true)
    fun deleteDeviceFromComposite(@Path("id") id: Long, @Body compositeItem: JsonObject): Call<CompositeItem>
}