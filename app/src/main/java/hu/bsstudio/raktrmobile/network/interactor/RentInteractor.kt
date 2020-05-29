package hu.bsstudio.raktrmobile.network.interactor

import android.os.Handler
import com.google.gson.GsonBuilder
import hu.bsstudio.raktrmobile.MainActivity
import hu.bsstudio.raktrmobile.data.ScannableDeserializer
import hu.bsstudio.raktrmobile.model.Rent
import hu.bsstudio.raktrmobile.model.RentItem
import hu.bsstudio.raktrmobile.model.Scannable
import hu.bsstudio.raktrmobile.network.api.RentAPI
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RentInteractor {

    private val rentAPI: RentAPI

    init {
        val gson = GsonBuilder()
        .registerTypeAdapter(Scannable::class.java, ScannableDeserializer())
        .create()

        val retrofit = Retrofit.Builder()
            .baseUrl(MainActivity.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

        this.rentAPI = retrofit.create(RentAPI::class.java)
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

    fun getRents(
        onSuccess: (List<Rent>) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        val getDevicesRequest = rentAPI.getRents()
        runCallOnBackgroundThread(getDevicesRequest, onSuccess, onError)
    }

    fun getRent(
        rent: Rent,
        onSuccess: (Rent) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        val getDevicesRequest = rentAPI.getRent(rent.id)
        runCallOnBackgroundThread(getDevicesRequest, onSuccess, onError)
    }

    fun addRent(
        rent: Rent,
        onSuccess: (Rent) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        val deleteDeviceRequest = rentAPI.addRent(rent.toJsonWithRoot())
        runCallOnBackgroundThread(deleteDeviceRequest, onSuccess, onError)
    }

    fun deleteRent(
        rent: Rent,
        onSuccess: (Rent) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        val deleteDeviceRequest = rentAPI.deleteRent(rent.toJsonWithRoot())
        runCallOnBackgroundThread(deleteDeviceRequest, onSuccess, onError)
    }

    fun updateRent(
        rent: Rent,
        onSuccess: (Rent) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        val deleteDeviceRequest = rentAPI.updateRent(rent.toJsonWithRoot())
        runCallOnBackgroundThread(deleteDeviceRequest, onSuccess, onError)
    }

    fun updateDeviceInRent (
        rent: Rent,
        rentItem: RentItem,
        onSuccess: (Rent) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        val updateRequest = rentAPI.updateDeviceInRent(rent.id, rentItem.toJsonWithRoot())
        runCallOnBackgroundThread(updateRequest, onSuccess, onError)
    }
}