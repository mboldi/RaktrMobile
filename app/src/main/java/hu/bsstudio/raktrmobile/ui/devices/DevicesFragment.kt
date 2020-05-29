package hu.bsstudio.raktrmobile.ui.devices

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import hu.bsstudio.raktrmobile.MainActivity
import hu.bsstudio.raktrmobile.R
import hu.bsstudio.raktrmobile.adapter.DevicesAdapter
import hu.bsstudio.raktrmobile.data.DataProvider
import hu.bsstudio.raktrmobile.model.Category
import hu.bsstudio.raktrmobile.model.Device
import hu.bsstudio.raktrmobile.model.DeviceStatus
import hu.bsstudio.raktrmobile.model.Location
import kotlinx.android.synthetic.main.fragment_devices.*

class DevicesFragment : Fragment() {


    private var adapter: DevicesAdapter? = null

    private val dataProvider = DataProvider()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_devices, container, false)

        MainActivity.CURRENT_TYPE = "device"

        return root
    }

    override fun onResume() {
        super.onResume()
        srlDevices.setOnRefreshListener { dataProvider.getDevices(this::loadDevices) }

        val divider: RecyclerView.ItemDecoration =
            DividerItemDecoration(context, DividerItemDecoration.VERTICAL)

        rvDevices.layoutManager = LinearLayoutManager(context)
        rvDevices.addItemDecoration(divider)

        dataProvider.getDevices(this::loadDevices)
    }

    private fun loadDevices(devices: List<Device>) {
        val deviceComparator = Comparator{ d1: Device, d2: Device ->
            d1.name.compareTo(d2.name)
        }

        adapter = DevicesAdapter(context,
            devices.sortedWith(deviceComparator)
        )
        rvDevices.adapter = adapter
        srlDevices.isRefreshing = false
    }
}
