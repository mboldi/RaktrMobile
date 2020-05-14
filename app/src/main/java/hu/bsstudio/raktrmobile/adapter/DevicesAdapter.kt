package hu.bsstudio.raktrmobile.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import hu.bsstudio.raktrmobile.R
import hu.bsstudio.raktrmobile.model.Device
import kotlinx.android.synthetic.main.li_device.view.*

class DevicesAdapter(
    private val context: Context?,
    private var devices: MutableList<Device>
) : RecyclerView.Adapter<DevicesAdapter.ViewHolder>() {

    private val layoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DevicesAdapter.ViewHolder {
        val view = layoutInflater.inflate(R.layout.li_device, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return devices.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.deviceNameTv.text = devices[position].name
        holder.deviceTypeTv.text = devices[position].maker.plus(" - ").plus(devices[position].type)
        holder.deviceCategoryTv.text = devices[position].category.name
        holder.deviceLocationTv.text = devices[position].location.name
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val deviceNameTv: TextView = itemView.tvDeviceNameLi
        val deviceTypeTv: TextView = itemView.tvDeviceTypeLi
        val deviceCategoryTv: TextView = itemView.tvCategoryLi
        val deviceLocationTv: TextView = itemView.tvLocationLi
    }
}