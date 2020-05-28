package hu.bsstudio.raktrmobile

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import hu.bsstudio.raktrmobile.adapter.DevicesAdapter
import hu.bsstudio.raktrmobile.model.CompositeItem
import hu.bsstudio.raktrmobile.model.Location
import hu.bsstudio.raktrmobile.network.interactor.CompositeItemInteractor
import hu.bsstudio.raktrmobile.network.interactor.MiscInteractor
import kotlinx.android.synthetic.main.activity_composite_item_details.*
import kotlinx.android.synthetic.main.activity_device_details.*

class CompositeItemDetailsActivity : AppCompatActivity() {

    private var edit: Boolean = false
    private lateinit var compositeItem: CompositeItem
    private val interactor = CompositeItemInteractor()
    private val miscInteractor = MiscInteractor()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_composite_item_details)

        val toolbar: Toolbar = findViewById(R.id.compositeDetailsToolbar)
        setSupportActionBar(toolbar)

        compositeItem = intent.extras?.get(MainActivity.COMPOSITE_KEY) as CompositeItem
        edit = intent.extras?.get(MainActivity.EDIT_KEY) as Boolean

        if (edit) {
            miscInteractor.getLocations(
                this::setLocations
            ) { Log.e("location", it.printStackTrace().toString()) }
        }

        setFieldValues()
        updateViewsEditable()

        btnBackComposite.setOnClickListener {
            finish()
        }

        btnSaveComposite.setOnClickListener {
            if (compositeItem.name != "") {
                val updatedItem = updateCompositeItem()

                interactor.updateCompositeItem(
                    updatedItem,
                    {
                        Snackbar.make(btnSaveComposite, "Sikeresen mentve", Snackbar.LENGTH_LONG)
                            .show()
                        compositeItem = it
                        edit = false
                        updateViewsEditable()
                    },
                    {
                        Snackbar.make(btnSaveComposite, "Nem sikerült menteni", Snackbar.LENGTH_LONG)
                            .show()
                    })
            } else {
                interactor.addCompositeItem(updateCompositeItem(),
                    {
                        compositeItem = it
                        Snackbar.make(btnSaveComposite, "Sikeresen mentve", Snackbar.LENGTH_LONG)
                            .show()
                        edit = false
                        updateViewsEditable()
                    },
                    {
                        Snackbar.make(btnSaveComposite, "Nem sikerült menteni", Snackbar.LENGTH_LONG)
                            .show()
                    })
            }
        }
    }

    override fun onResume() {
        super.onResume()

        val divider: RecyclerView.ItemDecoration =
            DividerItemDecoration(this, DividerItemDecoration.VERTICAL)

        rvCompositeDetailsDevices.layoutManager = LinearLayoutManager(this)
        rvCompositeDetailsDevices.addItemDecoration(divider)

        loadItems()
    }

    private fun loadItems() {
        val adapter = DevicesAdapter(this, compositeItem.devices)
        rvCompositeDetailsDevices.adapter = adapter
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.details_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.detailsEdit -> {
                edit = !edit

                if (edit) {
                    miscInteractor.getLocations(
                        this::setLocations
                    ) { Log.e("location", it.printStackTrace().toString()) }
                }

                updateViewsEditable()
                setFieldValues()
            }
            R.id.detailsDelete -> {
                interactor.deleteCompositeItem(compositeItem,
                    { finish() },
                    {
                        Snackbar.make(
                            tilDeviceDetailsName,
                            "Nem lehetett kitörölni :(",
                            Snackbar.LENGTH_LONG
                        ).show()
                    })

                finish()
            }
        }

        return true
    }

    private fun setFieldValues() {
        tilCompositeDetailsName.editText?.setText(compositeItem.name)
        tilCompositeDetailsBarcode.editText?.setText(compositeItem.barcode)
        tilCompositeDetailsLocation.editText?.setText(compositeItem.location?.name)

        tvCompositeWeight.text =
            getString(R.string.composite_weight).plus(countSumWeight()).plus(" kg")
    }

    private fun updateViewsEditable() {
        tilCompositeDetailsLocation.isEnabled = edit
        tilCompositeDetailsBarcode.isEnabled = edit
        tilCompositeDetailsName.isEnabled = edit

        btnSaveComposite.isVisible = edit
    }

    private fun countSumWeight(): Double {
        var weight = 0

        compositeItem.devices.forEach {
            weight += it.weight
        }

        return weight / 1000.0
    }

    private fun updateCompositeItem(): CompositeItem {
        val updatedCompositeItem = compositeItem

        updatedCompositeItem.name = tilCompositeDetailsName.editText?.text.toString()
        updatedCompositeItem.barcode = tilCompositeDetailsBarcode.editText?.text.toString()
        updatedCompositeItem.location =
            Location(name = tilCompositeDetailsLocation.editText?.text.toString())

        return updatedCompositeItem
    }

    private fun setLocations(locations: List<Location>) {
        val locationNames = mutableListOf<String>()

        locations.forEach {
            locationNames.add(it.name)
        }

        val adapter = ArrayAdapter(
            this,
            R.layout.dropdown_menu_popup_item,
            locationNames
        )

        tvCompositeLocation.setAdapter(adapter)
    }
}
