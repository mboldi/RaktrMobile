package hu.bsstudio.raktrmobile.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import hu.bsstudio.raktrmobile.DeviceDetailsActivity
import hu.bsstudio.raktrmobile.MainActivity
import hu.bsstudio.raktrmobile.R
import hu.bsstudio.raktrmobile.model.Device
import kotlinx.android.synthetic.main.li_device_in_composite.view.*

class DeviceInCompositeAdapter(
    private val context: Context,
    private val devices: MutableList<Device>,
    private val deleteItem: (Device) -> Unit
) : RecyclerView.Adapter<DeviceInCompositeAdapter.ViewHolder>() {

    private val layoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = layoutInflater.inflate(R.layout.li_device_in_composite, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return devices.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val device = devices[position]

        holder.deviceNameTv.text = device.name
        holder.deviceTypeTv.text = device.maker.plus(" - ").plus(device.type)

        holder.itemView.setOnClickListener {
            val intent = Intent(context, DeviceDetailsActivity::class.java)
            intent.putExtra(MainActivity.DEVICE_KEY, device)
            intent.putExtra(MainActivity.EDIT_KEY, false)

            context.startActivity(intent)
        }

        holder.deleteButton.setOnClickListener {
            MaterialAlertDialogBuilder(this.context)
                .setTitle("Eszköz törlése összetett eszközből")
                .setMessage("Biztos ki akarod törölni?")
                .setNegativeButton("Nem") { dialog, which -> }
                .setPositiveButton("Igen") { dialog, which -> deleteItem(device) }
                .show()
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val deviceNameTv: TextView = itemView.tvDeviceNameLiInComposite
        val deviceTypeTv: TextView = itemView.tvDeviceTypeLiInComposite

        val deleteButton: Button = itemView.btnDeleteDeviceFromComposite
    }
}