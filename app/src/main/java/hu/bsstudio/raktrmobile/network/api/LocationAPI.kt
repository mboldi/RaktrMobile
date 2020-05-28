package hu.bsstudio.raktrmobile.network.api

import hu.bsstudio.raktrmobile.model.Location
import retrofit2.Call
import retrofit2.http.GET

interface LocationAPI {

    @GET("location")
    fun getLocations(): Call<List<Location>>
}