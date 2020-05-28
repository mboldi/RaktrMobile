package hu.bsstudio.raktrmobile.network.interactor

import android.os.Handler
import hu.bsstudio.raktrmobile.MainActivity
import hu.bsstudio.raktrmobile.model.Device
import hu.bsstudio.raktrmobile.network.api.DeviceAPI
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DeviceInteractor {

    private val deviceAPI: DeviceAPI

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(MainActivity.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        this.deviceAPI = retrofit.create(DeviceAPI::class.java)
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

    fun getDevices(
        onSuccess: (List<Device>) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        val getDevicesRequest = deviceAPI.getDevices()
        runCallOnBackgroundThread(getDevicesRequest, onSuccess, onError)
    }

    fun addDevice(
        device: Device,
        onSuccess: (Device) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        val deleteDeviceRequest = deviceAPI.addDevice(device.toJsonWithRoot())
        runCallOnBackgroundThread(deleteDeviceRequest, onSuccess, onError)
    }

    fun deleteDevice(
        device: Device,
        onSuccess: (Device) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        val deleteDeviceRequest = deviceAPI.deleteDevice(device.toJsonWithRoot())
        runCallOnBackgroundThread(deleteDeviceRequest, onSuccess, onError)
    }

    fun updateDevice(
        device: Device,
        onSuccess: (Device) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        val deleteDeviceRequest = deviceAPI.updateDevice(device.toJsonWithRoot())
        runCallOnBackgroundThread(deleteDeviceRequest, onSuccess, onError)
    }
}