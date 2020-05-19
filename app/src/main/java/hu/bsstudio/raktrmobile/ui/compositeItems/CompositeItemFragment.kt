package hu.bsstudio.raktrmobile.ui.compositeItems

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import hu.bsstudio.raktrmobile.R
import hu.bsstudio.raktrmobile.adapter.CompositeItemAdapter
import hu.bsstudio.raktrmobile.model.*
import kotlinx.android.synthetic.main.fragment_composites.*

class CompositeItemFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_composites, container, false)

        return root
    }

    override fun onResume() {
        super.onResume()

        srlCompositeItems.setOnRefreshListener { loadItems() }

        val divider: RecyclerView.ItemDecoration =
            DividerItemDecoration(context, DividerItemDecoration.VERTICAL)

        rvComposites.layoutManager = LinearLayoutManager(context)
        rvComposites.addItemDecoration(divider)

        loadItems()
    }

    private fun loadItems() {
        val devices: MutableList<Device> = mutableListOf()
        devices += Device(
            1L,
            "BSS320-1",
            "B-1111",
            Category(1L, "Videó"),
            Location(3L, "110"),
            "Sony",
            1,
            "11223344",
            DeviceStatus.GOOD,
            "PMW-320",
            4500000,
            8000
        )
        devices += Device(
            2L,
            "EX3",
            "B-1121",
            Category(1L, "Videó"),
            Location(2L, "Páncél"),
            "Sony",
            1,
            "11223344",
            DeviceStatus.GOOD,
            "PMW-EX3",
            2500000,
            4500
        )

        val items = mutableListOf<CompositeItem>()
        val loc110 = Location(2L, "110")
        items += CompositeItem("B-WLRack-01", 1L, "Wireless rack", devices, loc110)
        items += CompositeItem("B-kozvszett-01", 2L, "Közvetítőszett", devices, loc110)

        val adapter = CompositeItemAdapter(context, items)
        rvComposites.adapter = adapter

        srlCompositeItems.isRefreshing = false
    }
}
