package hu.bsstudio.raktrmobile

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.isVisible
import com.google.android.material.snackbar.Snackbar
import hu.bsstudio.raktrmobile.model.Category
import hu.bsstudio.raktrmobile.model.Device
import hu.bsstudio.raktrmobile.model.DeviceStatus
import hu.bsstudio.raktrmobile.model.Location
import hu.bsstudio.raktrmobile.network.interactor.DeviceInteractor
import kotlinx.android.synthetic.main.activity_device_details.*
import java.lang.Integer.parseInt

class DeviceDetailsActivity : AppCompatActivity() {
    var edit: Boolean = false
    lateinit var device: Device

    private val deviceInteractor =
        DeviceInteractor()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_device_details)

        val toolbar: Toolbar = findViewById(R.id.deviceDetailsToolbar)
        setSupportActionBar(toolbar)

        device = intent.extras?.get(MainActivity.DEVICE_KEY) as Device
        edit = intent.extras?.get(MainActivity.EDIT_KEY) as Boolean

        setFieldValues()
        updateViewsEditable()

        btnBackDevice.setOnClickListener {
            finish()
        }

        btnSaveDevice.setOnClickListener {
            if (device.name != "") {
                val updatedDevice = updateDevice()

                deviceInteractor.updateDevice(device,
                    {
                        Snackbar.make(btnSaveDevice, "Sikeresen mentve", Snackbar.LENGTH_LONG)
                            .show()
                        device = updatedDevice
                        edit = false
                        updateViewsEditable()
                    },
                    {
                        Snackbar.make(btnSaveDevice, "Nem sikerült menteni", Snackbar.LENGTH_LONG)
                            .show()
                    })
            } else {
                device = updateDevice()

                deviceInteractor.addDevice(device,
                    {
                        Snackbar.make(btnSaveDevice, "Sikeresen mentve", Snackbar.LENGTH_LONG)
                            .show()
                        edit = false
                        updateViewsEditable()
                    },
                    {
                        Snackbar.make(btnSaveDevice, "Nem sikerült menteni", Snackbar.LENGTH_LONG)
                            .show()
                    })
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.details_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.detailsEdit -> {
                edit = !edit

                updateViewsEditable()
                setFieldValues()
            }
            R.id.detailsDelete -> {
                deviceInteractor.deleteDevice(device,
                    { finish() },
                    {
                        Snackbar.make(
                            tilDeviceDetailsName,
                            "Nem lehetett kitörölni :(",
                            Snackbar.LENGTH_LONG
                        ).show()
                    })
            }
        }

        return true
    }

    private fun updateDevice(): Device {
        val updatedDevice = device
        updatedDevice.name = tilDeviceDetailsName.editText?.text.toString()
        updatedDevice.maker = tilDeviceDetailsMaker.editText?.text.toString()
        updatedDevice.type = tilDeviceDetailsType.editText?.text.toString()
        updatedDevice.barcode = tilDeviceDetailsBarcode.editText?.text.toString()
        updatedDevice.serial = tilDeviceDetailsSerial.editText?.text.toString()
        updatedDevice.weight = parseInt(tilDeviceDetailsWeight.editText?.text.toString())
        updatedDevice.quantity =
            parseInt(tilDeviceDetailsQuantity.editText?.text.toString())
        updatedDevice.value = parseInt(tilDeviceDetailsValue.editText?.text.toString())

        val currentCategory = Category(name = tilDeviceDetailsCategory.editText?.text.toString())
        updatedDevice.category = currentCategory

        val currentLocation = Location(name = tilDeviceDetailsLocation.editText?.text.toString())
        updatedDevice.location = currentLocation

        when {
            rbGood.isChecked -> {
                updatedDevice.status = DeviceStatus.GOOD
            }
            rbNeedsRepair.isChecked -> {
                updatedDevice.status = DeviceStatus.NEEDS_REPAIR
            }
            else -> {
                updatedDevice.status = DeviceStatus.SCRAPPED
            }
        }

        return updatedDevice
    }

    private fun setFieldValues() {
        tilDeviceDetailsName.editText?.setText(device.name)
        tilDeviceDetailsBarcode.editText?.setText(device.barcode)
        tilDeviceDetailsMaker.editText?.setText(device.maker)
        tilDeviceDetailsType.editText?.setText(device.type)
        tilDeviceDetailsSerial.editText?.setText(device.serial)
        tilDeviceDetailsQuantity.editText?.setText(device.quantity.toString())
        tilDeviceDetailsValue.editText?.setText(device.value.toString())
        tilDeviceDetailsWeight.editText?.setText(device.weight.toString())
        tilDeviceDetailsCategory.editText?.setText(device.category?.name)
        tilDeviceDetailsLocation.editText?.setText(device.location?.name)

        when (device.status) {
            DeviceStatus.GOOD -> rbGood.isChecked = true
            DeviceStatus.NEEDS_REPAIR -> rbNeedsRepair.isChecked = true
            DeviceStatus.SCRAPPED -> rbScrapped.isChecked = true
        }
    }

    private fun updateViewsEditable() {
        tilDeviceDetailsName.editText?.isEnabled = edit
        tilDeviceDetailsBarcode.editText?.isEnabled = edit
        tilDeviceDetailsMaker.editText?.isEnabled = edit
        tilDeviceDetailsType.editText?.isEnabled = edit
        tilDeviceDetailsSerial.editText?.isEnabled = edit
        tilDeviceDetailsQuantity.editText?.isEnabled = edit
        tilDeviceDetailsValue.editText?.isEnabled = edit
        tilDeviceDetailsWeight.editText?.isEnabled = edit
        tilDeviceDetailsCategory.editText?.isEnabled = edit
        tilDeviceDetailsLocation.editText?.isEnabled = edit

        rbGood.isEnabled = edit
        rbNeedsRepair.isEnabled = edit
        rbScrapped.isEnabled = edit

        btnSaveDevice.isVisible = edit
    }
}
