package hu.bsstudio.raktrmobile.network.api

import hu.bsstudio.raktrmobile.model.Scannable
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ScannableAPI {

    @GET("scannable/{barcode}")
    fun getScannable(@Path("barcode") barcode: String): Call<Scannable>
}