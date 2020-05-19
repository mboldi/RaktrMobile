package hu.bsstudio.raktrmobile.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import hu.bsstudio.raktrmobile.DeviceDetailsActivity
import hu.bsstudio.raktrmobile.MainActivity
import hu.bsstudio.raktrmobile.R
import hu.bsstudio.raktrmobile.model.Device
import kotlinx.android.synthetic.main.li_device.view.*

class DevicesAdapter(
    private val context: Context?,
    private var devices: MutableList<Device>
) : RecyclerView.Adapter<DevicesAdapter.ViewHolder>() {

    private val layoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = layoutInflater.inflate(R.layout.li_device, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return devices.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val device = devices[position]

        holder.deviceNameTv.text = device.name
        holder.deviceTypeTv.text = device.maker.plus(" - ").plus(device.type)
        holder.deviceCategoryTv.text = device.category.name
        holder.deviceLocationTv.text = device.location.name

        holder.itemView.setOnClickListener {
            val intent = Intent(context, DeviceDetailsActivity::class.java)
            intent.putExtra(MainActivity.DEVICE_KEY, device)
            intent.putExtra(MainActivity.EDIT_KEY, false)

            context?.startActivity(intent)
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val deviceNameTv: TextView = itemView.tvDeviceNameLi
        val deviceTypeTv: TextView = itemView.tvDeviceTypeLi
        val deviceCategoryTv: TextView = itemView.tvCategoryLi
        val deviceLocationTv: TextView = itemView.tvLocationLi
    }
}