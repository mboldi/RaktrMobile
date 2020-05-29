package hu.bsstudio.raktrmobile.network.api

import com.google.gson.JsonObject
import hu.bsstudio.raktrmobile.model.Device
import retrofit2.Call
import retrofit2.http.*

interface DeviceAPI {

    @GET("device")
    fun getDevices(): Call<List<Device>>

    @Headers("Content-Type: application/json")
    @HTTP(method = "DELETE", path = "device", hasBody = true)
    fun deleteDevice(@Body device: JsonObject): Call<Device>

    @Headers("Content-Type: application/json")
    @POST("device")
    fun addDevice(@Body device: JsonObject): Call<Device>

    @Headers("Content-Type: application/json")
    @PUT("device")
    fun updateDevice(@Body device: JsonObject): Call<Device>

    @GET("device/{id}")
    fun getDevice(@Path("id") id: Long): Call<Device>
}