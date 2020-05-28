package hu.bsstudio.raktrmobile

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import hu.bsstudio.raktrmobile.adapter.RentItemAdapter
import hu.bsstudio.raktrmobile.model.CompositeItem
import hu.bsstudio.raktrmobile.model.Device
import hu.bsstudio.raktrmobile.model.Rent
import kotlinx.android.synthetic.main.activity_rent_details.*

class RentDetailsActivity : AppCompatActivity() {

    var edit = false
    lateinit var rent: Rent

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

        }
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

        rent.rentItems?.forEach { rentItem ->
            if (rentItem.scannable.javaClass == Device::class.java) {
                weight += (rentItem.scannable as Device).weight * rentItem.outQuantity
            } else {
                (rentItem.scannable as CompositeItem).devices?.forEach {
                    weight += it.weight
                }
            }
        }

        return weight / 1000.0
    }

    private fun countSumValue(): Int {
        var value = 0

        rent.rentItems?.forEach { rentItem ->
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
                //TODO delete device

                finish()
            }
            R.id.detailsPdf -> {
                //TODO

                Toast.makeText(this, "Coming soon...", Toast.LENGTH_SHORT).show()
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

        loadItems()
    }

    private fun loadItems() {
        val adapter = RentItemAdapter(this, rent.rentItems)
        rvRentDetailRentItems.adapter = adapter
    }
}
