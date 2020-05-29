package hu.bsstudio.raktrmobile

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import hu.bsstudio.raktrmobile.adapter.RentItemAdapter
import hu.bsstudio.raktrmobile.model.CompositeItem
import hu.bsstudio.raktrmobile.model.Device
import hu.bsstudio.raktrmobile.model.Rent
import hu.bsstudio.raktrmobile.network.interactor.RentInteractor
import kotlinx.android.synthetic.main.activity_device_details.*
import kotlinx.android.synthetic.main.activity_rent_details.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

class RentDetailsActivity : AppCompatActivity() {

    var edit = false
    lateinit var rent: Rent
    private val interactor = RentInteractor()
    private val calendar = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rent_details)

        val toolbar: Toolbar = findViewById(R.id.rentDetailsToolbar)
        setSupportActionBar(toolbar)

        rent = intent.extras?.get(MainActivity.RENT_KEY) as Rent
        edit = intent.extras?.get(MainActivity.EDIT_KEY) as Boolean

        setFieldValues()
        updateViewsEditable()

        btnBackRent.setOnClickListener {
            finish()
        }

        btnSaveRent.setOnClickListener {
            if (rent.destination != "") {
                val updatedRent = updateRent()

                interactor.updateRent(updatedRent,
                    {
                        Snackbar.make(btnSaveRent, "Sikeresen mentve", Snackbar.LENGTH_LONG)
                            .show()
                        rent = it
                        edit = false
                        updateViewsEditable()
                    },
                    {
                        Snackbar.make(btnSaveRent, "Nem sikerült menteni", Snackbar.LENGTH_LONG)
                            .show()
                    })
            } else {
                rent = updateRent()

                interactor.addRent(rent,
                    {
                        rent = it
                        Snackbar.make(btnSaveRent, "Sikeresen mentve", Snackbar.LENGTH_LONG)
                            .show()
                        edit = false
                        updateViewsEditable()
                    },
                    {
                        Snackbar.make(btnSaveRent, "Nem sikerült menteni", Snackbar.LENGTH_LONG)
                            .show()
                    })
            }
        }

        val outDateListener = DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, month)
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            updateDateLabel(tilRentDetailOutDate.editText!!)
        }

        tilRentDetailOutDate.editText?.setOnClickListener {
            DatePickerDialog(
                this,
                outDateListener,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }

        val expBackDateListener =
            DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                calendar.set(Calendar.YEAR, year)
                calendar.set(Calendar.MONTH, month)
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                updateDateLabel(tilRentDetailExpBackDate.editText!!)
            }

        tilRentDetailExpBackDate.editText?.setOnClickListener {
            DatePickerDialog(
                this,
                expBackDateListener,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }

        val actBackDateListener =
            DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                calendar.set(Calendar.YEAR, year)
                calendar.set(Calendar.MONTH, month)
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                updateDateLabel(tilRentDetailActBackDate.editText!!)
            }

        tilRentDetailActBackDate.editText?.setOnClickListener {
            DatePickerDialog(
                this,
                actBackDateListener,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }

        btnAddDeviceToRent.setOnClickListener {
            val intent = Intent(this, ScannerActivity::class.java)
            intent.putExtra(MainActivity.RENT_KEY, rent)
            startActivity(intent)
        }
    }

    private fun updateDateLabel(editText: EditText) {
        val formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd.")
        val date = LocalDate.of(
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH) + 1,
            calendar.get(Calendar.DAY_OF_MONTH)
        )

        editText.setText(date.format(formatter))
    }

    private fun updateRent(): Rent {
        val updatedRent = rent

        updatedRent.destination = tilRentDetailDestination.editText?.text.toString()
        updatedRent.issuer = tilRentDetailIssuer.editText?.text.toString()
        updatedRent.outDate = tilRentDetailOutDate.editText?.text.toString()
        updatedRent.expBackDate = tilRentDetailExpBackDate.editText?.text.toString()
        updatedRent.actBackDate = tilRentDetailActBackDate.editText?.text.toString()
        updatedRent.renter = tilRentDetailRenter.editText?.text.toString()

        return updatedRent
    }

    private fun setFieldValues() {
        tilRentDetailDestination.editText?.setText(rent.destination)
        tilRentDetailRenter.editText?.setText(rent.renter)
        tilRentDetailIssuer.editText?.setText(rent.issuer)
        tilRentDetailOutDate.editText?.setText(rent.outDate)
        tilRentDetailExpBackDate.editText?.setText(rent.expBackDate)
        tilRentDetailActBackDate.editText?.setText(rent.actBackDate)

        tvRentWeight.text = "Összesített tömeg: ~".plus(countSumWeight()).plus(" kg")
        tvRentValue.text = "Összesített érték: ~".plus(countSumValue()).plus(" Ft")
    }

    private fun updateViewsEditable() {
        tilRentDetailDestination.isEnabled = edit
        tilRentDetailRenter.isEnabled = edit
        tilRentDetailIssuer.isEnabled = edit
        tilRentDetailOutDate.isEnabled = edit
        tilRentDetailExpBackDate.isEnabled = edit
        tilRentDetailActBackDate.isEnabled = edit

        btnSaveRent.isVisible = edit
    }

    private fun countSumWeight(): Double {
        var weight = 0

        rent.rentItems.forEach { rentItem ->
            if (rentItem.scannable.javaClass == Device::class.java) {
                weight += (rentItem.scannable as Device).weight * rentItem.outQuantity
            } else {
                (rentItem.scannable as CompositeItem).devices.forEach {
                    weight += it.weight
                }
            }
        }

        return weight / 1000.0
    }

    private fun countSumValue(): Int {
        var value = 0

        rent.rentItems.forEach { rentItem ->
            if (rentItem.scannable.javaClass == Device::class.java) {
                value += (rentItem.scannable as Device).value * rentItem.outQuantity
            } else {
                (rentItem.scannable as CompositeItem).devices?.forEach {
                    value += it.value
                }
            }
        }

        return value
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.rent_details_menu, menu)
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
                interactor.deleteRent(rent,
                    { finish() },
                    {
                        Snackbar.make(
                            tilDeviceDetailsName,
                            "Nem sikerült kitörölni :(",
                            Snackbar.LENGTH_LONG
                        ).show()

                        Log.e("delete", it.printStackTrace().toString())
                    })
            }
            R.id.detailsPdf -> {
                //TODO

                Snackbar.make(btnBackRent, "Coming soon...", Snackbar.LENGTH_SHORT).show()
            }
        }

        return true
    }

    override fun onResume() {
        super.onResume()

        val divider: RecyclerView.ItemDecoration =
            DividerItemDecoration(this, DividerItemDecoration.VERTICAL)

        rvRentDetailRentItems.layoutManager = LinearLayoutManager(this)
        rvRentDetailRentItems.addItemDecoration(divider)

        updateFromServer()
    }

    private fun updateFromServer() {
        interactor.getRent(rent,
            this::updateData
        ) {
            Snackbar.make(
                rvRentDetailRentItems,
                "Nem sikerült lekérni",
                Snackbar.LENGTH_LONG
            ).show()
        }
    }

    private fun updateData(rent: Rent) {
        this.rent = rent

        setFieldValues()
        loadItems()
    }

    private fun loadItems() {
        val adapter = RentItemAdapter(this, rent.rentItems) { rentItem ->
            interactor.updateDeviceInRent(rent, rentItem,
                { updateData(it) },
                {
                    Snackbar.make(
                        rvRentDetailRentItems,
                        "Nem sikerült menteni",
                        Snackbar.LENGTH_LONG
                    ).show()
                }
            )
        }
        rvRentDetailRentItems.adapter = adapter
    }
}
