package hu.bsstudio.raktrmobile.network.interactor

import android.os.Handler
import hu.bsstudio.raktrmobile.MainActivity
import hu.bsstudio.raktrmobile.model.CompositeItem
import hu.bsstudio.raktrmobile.model.Device
import hu.bsstudio.raktrmobile.network.api.CompositeItemAPI
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CompositeItemInteractor {

    private val compositeItemAPI: CompositeItemAPI

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(MainActivity.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        this.compositeItemAPI = retrofit.create(CompositeItemAPI::class.java)
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

    fun getCompositeItems(
        onSuccess: (List<CompositeItem>) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        val getCompositeItemsRequest = compositeItemAPI.getCompositeItems()
        runCallOnBackgroundThread(getCompositeItemsRequest, onSuccess, onError)
    }

    fun getCompositeItem(
        compositeItem: CompositeItem,
        onSuccess: (CompositeItem) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        val getCompositeItemRequest = compositeItemAPI.getCompositeItem(compositeItem.id)
        runCallOnBackgroundThread(getCompositeItemRequest, onSuccess, onError)
    }

    fun addCompositeItem(
        compositeItem: CompositeItem,
        onSuccess: (CompositeItem) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        val addCompositeItemsRequest =
            compositeItemAPI.addCompositeItem(compositeItem.toJsonWithRoot())
        runCallOnBackgroundThread(addCompositeItemsRequest, onSuccess, onError)
    }

    fun deleteCompositeItem(
        compositeItem: CompositeItem,
        onSuccess: (CompositeItem) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        val deleteCompositeItemsRequest =
            compositeItemAPI.deleteCompositeItem(compositeItem.toJsonWithRoot())
        runCallOnBackgroundThread(deleteCompositeItemsRequest, onSuccess, onError)
    }

    fun updateCompositeItem(
        compositeItem: CompositeItem,
        onSuccess: (CompositeItem) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        val updateCompositeItemRequest =
            compositeItemAPI.updateCompositeItem(compositeItem.toJsonWithRoot())
        runCallOnBackgroundThread(updateCompositeItemRequest, onSuccess, onError)
    }

    fun deleteDeviceFromCompositeItem(
        compositeItem: CompositeItem,
        device: Device,
        onSuccess: (CompositeItem) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        val deleteFromCompositeItemRequest =
            compositeItemAPI.deleteDeviceFromComposite(compositeItem.id, device.toJsonWithRoot())
        runCallOnBackgroundThread(deleteFromCompositeItemRequest, onSuccess, onError)
    }

    fun addDeviceToCompositeItem(
        compositeItem: CompositeItem,
        device: Device,
        onSuccess: (CompositeItem) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        val deleteFromCompositeItemRequest =
            compositeItemAPI.addDeviceToComposite(compositeItem.id, device.toJsonWithRoot())
        runCallOnBackgroundThread(deleteFromCompositeItemRequest, onSuccess, onError)
    }
}