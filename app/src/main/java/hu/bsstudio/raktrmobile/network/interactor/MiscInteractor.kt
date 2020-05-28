package hu.bsstudio.raktrmobile.network.interactor

import android.os.Handler
import hu.bsstudio.raktrmobile.MainActivity
import hu.bsstudio.raktrmobile.model.Category
import hu.bsstudio.raktrmobile.model.CompositeItem
import hu.bsstudio.raktrmobile.model.Location
import hu.bsstudio.raktrmobile.model.Scannable
import hu.bsstudio.raktrmobile.network.api.CategoryAPI
import hu.bsstudio.raktrmobile.network.api.LocationAPI
import hu.bsstudio.raktrmobile.network.api.ScannableAPI
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MiscInteractor {

    private val locationAPI: LocationAPI
    private val categoryAPI: CategoryAPI
    private val scannableAPI: ScannableAPI

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(MainActivity.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        this.locationAPI = retrofit.create(LocationAPI::class.java)
        this.categoryAPI = retrofit.create(CategoryAPI::class.java)
        this.scannableAPI = retrofit.create(ScannableAPI::class.java)
    }

    private fun <T> runCallOnBackgroundThread(
        call: Call<T>,
        onSuccess: (T) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        val handler = Handler()
        Thread {
            try {
                val response = call.execute().body()!!
                handler.post { onSuccess(response) }

            } catch (e: Exception) {
                e.printStackTrace()
                handler.post { onError(e) }
            }
        }.start()
    }

    fun getCategories(
        onSuccess: (List<Category>) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        val getCompositeItemsRequest = categoryAPI.getCategories()
        runCallOnBackgroundThread(getCompositeItemsRequest, onSuccess, onError)
    }

    fun getLocations(
        onSuccess: (List<Location>) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        val getCompositeItemsRequest = locationAPI.getLocations()
        runCallOnBackgroundThread(getCompositeItemsRequest, onSuccess, onError)
    }

    fun getScannable(
        barcode: String,
        onSuccess: (Scannable) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        val getCompositeItemsRequest = scannableAPI.getScannable(barcode)
        runCallOnBackgroundThread(getCompositeItemsRequest, onSuccess, onError)
    }
}