package hu.bsstudio.raktrmobile.ui.devices

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import hu.bsstudio.raktrmobile.R
import hu.bsstudio.raktrmobile.adapter.DevicesAdapter
import hu.bsstudio.raktrmobile.model.Category
import hu.bsstudio.raktrmobile.model.Device
import hu.bsstudio.raktrmobile.model.DeviceStatus
import hu.bsstudio.raktrmobile.model.Location
import kotlinx.android.synthetic.main.fragment_devices.*

class DevicesFragment : Fragment() {

    private lateinit var devicesViewModel: DevicesViewModel

    private var adapter: DevicesAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        devicesViewModel = ViewModelProviders.of(this).get(DevicesViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_devices, container, false)
        val textView: TextView = root.findViewById(R.id.text_home)
        devicesViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })

        return root
    }

    override fun onResume() {
        super.onResume()
        srlDevices.setOnRefreshListener { loadDevices() }

        val divider: RecyclerView.ItemDecoration = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)

        rvDevices.layoutManager = LinearLayoutManager(context)
        rvDevices.addItemDecoration(divider)

        loadDevices()
    }

    private fun loadDevices() {
        val devices: MutableList<Device> = mutableListOf()
        devices += Device(1L, "BSS320-1", "B-1111", Category(1L,"Videó"), Location(3L, "110"), "Sony", 1, "11223344", DeviceStatus.GOOD, "PMW-320", 4500000, 8000)
        devices += Device(2L, "EX3", "B-1121", Category(1L,"Videó"), Location(2L, "Páncél"), "Sony", 1, "11223344", DeviceStatus.GOOD, "PMW-EX3", 2500000, 4500)

        adapter = DevicesAdapter(context, devices)
        rvDevices.adapter = adapter
        srlDevices.isRefreshing = false
    }
}
