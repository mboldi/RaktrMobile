package hu.bsstudio.raktrmobile

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.isVisible
import hu.bsstudio.raktrmobile.model.Device
import hu.bsstudio.raktrmobile.model.DeviceStatus
import kotlinx.android.synthetic.main.activity_device_details.*

class DeviceDetailsActivity : AppCompatActivity() {
    var edit: Boolean = false
    lateinit var device: Device

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
                //TODO delete device

                finish()
            }
        }

        return true
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
        tilDeviceDetailsCategory.editText?.setText(device.category.name)
        tilDeviceDetailsLocation.editText?.setText(device.location.name)

        when(device.status) {
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
