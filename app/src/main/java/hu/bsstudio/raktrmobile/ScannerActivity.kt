package hu.bsstudio.raktrmobile

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.google.zxing.Result
import com.livinglifetechway.quickpermissions_kotlin.runWithPermissions
import hu.bsstudio.raktrmobile.model.*
import hu.bsstudio.raktrmobile.network.interactor.CompositeItemInteractor
import hu.bsstudio.raktrmobile.network.interactor.MiscInteractor
import hu.bsstudio.raktrmobile.network.interactor.RentInteractor
import kotlinx.android.synthetic.main.activity_scanner.*
import me.dm7.barcodescanner.zxing.ZXingScannerView
import java.lang.Integer.parseInt

class ScannerActivity : AppCompatActivity(), ZXingScannerView.ResultHandler {

    private val miscInteractor = MiscInteractor()
    private val rentInteractor = RentInteractor()
    private val compositeItemInteractor = CompositeItemInteractor()
    private var rent: Rent? = null
    private var compositeItem: CompositeItem? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scanner)

        if (intent.extras != null) {
            if (intent.extras?.containsKey(MainActivity.COMPOSITE_KEY)!!)
                compositeItem = intent.extras?.get(MainActivity.COMPOSITE_KEY) as CompositeItem
            else if (intent.extras?.containsKey(MainActivity.RENT_KEY)!!)
                rent = intent.extras?.get(MainActivity.RENT_KEY) as Rent
        }
        else {
            tilRentQuantity.visibility = GONE
            btnGotoDetails.visibility = GONE
            btnAddToContainer.visibility = GONE
        }

        if (compositeItem != null) {
            btnAddToContainer.text = "Hozzáadás az összetett eszközhöz"
            tilRentQuantity.visibility = GONE
            btnGotoDetails.visibility = GONE
        }

        if (rent != null) {
            btnAddToContainer.text = "Hozzáadás a kivitelhez"
            btnGotoDetails.visibility = GONE
        }

        btnGotoDetails.isEnabled = false
        btnAddToContainer.isEnabled = false

        tvNameScannedDevice.visibility = GONE
        tvTypeScannedDevice.visibility = GONE

        btnRescan.setOnClickListener {
            scannerBox.resumeCameraPreview(this)
        }

        btnBackScan.setOnClickListener {
            finish()
        }
    }

    override fun onResume() {
        super.onResume()
        startCamera()
    }

    override fun onPause() {
        super.onPause()
        scannerBox.stopCamera()
    }

    private fun startCamera() = runWithPermissions(Manifest.permission.CAMERA) {
        scannerBox.setResultHandler(this)
        scannerBox.startCamera()
    }

    override fun handleResult(result: Result) {
        miscInteractor.getScannable(
            result.text,
            this::handleDeviceResponse
        ) {
            Snackbar.make(
                scannerBox,
                "Hibás kód",
                Snackbar.LENGTH_LONG
            ).show()
            Log.e("scannable", it.toString())

            scannerBox.resumeCameraPreview(this)
        }
    }

    private fun handleDeviceResponse(scannable: Scannable) {
        if (scannable.javaClass == Device::class.java) {
            val device = scannable as Device

            btnGotoDetails.setOnClickListener {
                val detailsIntent = Intent(this, DeviceDetailsActivity::class.java)
                detailsIntent.putExtra(MainActivity.DEVICE_KEY, device)
                detailsIntent.putExtra(MainActivity.EDIT_KEY, false)
                startActivity(intent)
                finish()
            }

            if (compositeItem != null) {
                btnAddToContainer.setOnClickListener {
                    compositeItemInteractor.addDeviceToCompositeItem(compositeItem!!, device,
                        { finish() },
                        {
                            Snackbar.make(
                                btnAddToContainer,
                                "Nem sikerült hozzáadni",
                                Snackbar.LENGTH_LONG
                            ).show()
                        })
                }
            } else if (rent != null) {
                btnAddToContainer.setOnClickListener {
                    val rentItem = RentItem(
                        0L,
                        BackStatus.OUT,
                        parseInt(tilRentQuantity.editText?.text.toString()),
                        device
                    )

                    rentInteractor.updateDeviceInRent(rent!!, rentItem,
                        { finish() },
                        {
                            Snackbar.make(
                                btnAddToContainer,
                                "Nem sikerült hozzáadni",
                                Snackbar.LENGTH_LONG
                            ).show()
                        })
                }
            }

            btnGotoDetails.isEnabled = true
            btnAddToContainer.isEnabled = true

            tvNameScannedDevice.visibility = VISIBLE
            tvNameScannedDevice.text = "Neve: ".plus(device.name)

            tvTypeScannedDevice.visibility = VISIBLE
            tvTypeScannedDevice.text = device.maker.plus(" - ").plus(device.type)
        } else {
            val compositeItem = scannable as CompositeItem
            if (rent == null) {
                Snackbar.make(
                    scannerBox,
                    "A beolvasott kód nem eszközköz tartozik",
                    Snackbar.LENGTH_LONG
                ).show()

                btnGotoDetails.isEnabled = false
                btnAddToContainer.isEnabled = false

                scannerBox.resumeCameraPreview(this)
            } else {
                btnGotoDetails.setOnClickListener {
                    val detailsIntent = Intent(this, CompositeItemDetailsActivity::class.java)
                    detailsIntent.putExtra(MainActivity.COMPOSITE_KEY, compositeItem)
                    detailsIntent.putExtra(MainActivity.EDIT_KEY, false)
                    startActivity(intent)
                    finish()
                }

                btnAddToContainer.setOnClickListener {
                    val rentItem = RentItem(
                        0L,
                        BackStatus.OUT,
                        parseInt(tilRentQuantity.editText?.text.toString()),
                        compositeItem
                    )

                    rentInteractor.updateDeviceInRent(rent!!, rentItem,
                        { finish() },
                        {
                            Snackbar.make(
                                btnAddToContainer,
                                "Nem sikerült hozzáadni",
                                Snackbar.LENGTH_LONG
                            ).show()
                        })
                }

                btnGotoDetails.isEnabled = true
                btnAddToContainer.isEnabled = true

                tvNameScannedDevice.visibility = VISIBLE
                tvNameScannedDevice.text = "Neve: ".plus(scannable.name)
            }
        }

    }
}
