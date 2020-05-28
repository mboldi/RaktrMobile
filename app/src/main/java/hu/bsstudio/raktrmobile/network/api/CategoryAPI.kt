package hu.bsstudio.raktrmobile.network.api

import hu.bsstudio.raktrmobile.model.Category
import retrofit2.Call
import retrofit2.http.GET

interface CategoryAPI {

    @GET("category")
    fun getCategories(): Call<List<Category>>
}