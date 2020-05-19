package hu.bsstudio.raktrmobile.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import hu.bsstudio.raktrmobile.CompositeItemDetailsActivity
import hu.bsstudio.raktrmobile.DeviceDetailsActivity
import hu.bsstudio.raktrmobile.MainActivity
import hu.bsstudio.raktrmobile.R
import hu.bsstudio.raktrmobile.model.BackStatus
import hu.bsstudio.raktrmobile.model.CompositeItem
import hu.bsstudio.raktrmobile.model.Device
import hu.bsstudio.raktrmobile.model.RentItem
import kotlinx.android.synthetic.main.li_rent_item.view.*

class RentItemAdapter(
    private val context: Context?,
    private var items: MutableList<RentItem>
) : RecyclerView.Adapter<RentItemAdapter.ViewHolder>() {

    private val layoutInflater = LayoutInflater.from(context)
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view = layoutInflater.inflate(R.layout.li_rent_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val rentItem = items[position]

        holder.rentItemName.text = rentItem.scannable.name
        holder.rentItemQuantity.text = rentItem.outQuantity.toString().plus(" db")

        when(rentItem.backStatus) {
            BackStatus.BACK -> {
                holder.rentItemStatusBack.isVisible = true
                holder.rentItemStatusOut.isVisible = false
            }
            BackStatus.OUT -> {
                holder.rentItemStatusBack.isVisible = false
                holder.rentItemStatusOut.isVisible = true
            }
        }

        holder.itemView.setOnClickListener {
            if(rentItem.scannable.javaClass == Device::class.java) {
                val intent = Intent(context, DeviceDetailsActivity::class.java)
                intent.putExtra(MainActivity.DEVICE_KEY, rentItem.scannable as Device)
                intent.putExtra(MainActivity.EDIT_KEY, false)

                context?.startActivity(intent)
            } else {
                val intent = Intent(context, CompositeItemDetailsActivity::class.java)
                intent.putExtra(MainActivity.COMPOSITE_KEY, rentItem.scannable as CompositeItem)
                intent.putExtra(MainActivity.EDIT_KEY, false)

                context?.startActivity(intent)
            }
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var rentItemName: TextView = itemView.tvRentedItemName
        var rentItemStatusBack: TextView = itemView.tvRentItemStatusBack
        var rentItemStatusOut: TextView = itemView.tvRentItemStatusOut
        var rentItemQuantity: TextView = itemView.tvRentedItemQuantity
    }
}