package hu.bsstudio.raktrmobile.network.api

import com.google.gson.JsonObject
import hu.bsstudio.raktrmobile.model.Rent
import hu.bsstudio.raktrmobile.model.RentItem
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface RentAPI {

    @GET("rent")
    fun getRents(): Call<List<Rent>>

    @Headers("Content-Type: application/json")
    @HTTP(method = "DELETE", path = "rent", hasBody = true)
    fun deleteRent(@Body rent: JsonObject): Call<Rent>

    @Headers("Content-Type: application/json")
    @POST("rent")
    fun addRent(@Body rent: JsonObject): Call<Rent>

    @Headers("Content-Type: application/json")
    @PUT("rent")
    fun updateRent(@Body rent: JsonObject): Call<Rent>

    @GET("rent/{id}")
    fun getOneRent(@Path("id") id: Long): Call<Rent>

    @Headers("Content-Type: application/json")
    @PUT("rent/{id}")
    fun updateDeviceInRent(@Path("id") id: Long, @Body rentItem: RentItem): Call<Rent>

    @Headers("Content-Type: application/json")
    @POST("rent/{id}")
    fun getPdf(@Path("id") id: Long, @Body pdfRequest: JsonObject): Call<ResponseBody>

}