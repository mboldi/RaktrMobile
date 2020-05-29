package hu.bsstudio.raktrmobile.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import hu.bsstudio.raktrmobile.R
import hu.bsstudio.raktrmobile.model.BackStatus
import hu.bsstudio.raktrmobile.model.RentItem
import hu.bsstudio.raktrmobile.network.interactor.RentInteractor
import kotlinx.android.synthetic.main.li_rent_item.view.*
import java.lang.Integer.parseInt

class RentItemAdapter(
    private val context: Context?,
    private var items: MutableList<RentItem>,
    private val onItemUpdate: (RentItem) -> Unit
) : RecyclerView.Adapter<RentItemAdapter.ViewHolder>() {

    private val interactor = RentInteractor()

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

        when (rentItem.backStatus) {
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
            /*if(rentItem.scannable.javaClass == Device::class.java) {
                val intent = Intent(context, DeviceDetailsActivity::class.java)
                intent.putExtra(MainActivity.DEVICE_KEY, rentItem.scannable as Device)
                intent.putExtra(MainActivity.EDIT_KEY, false)

                context?.startActivity(intent)
            } else {
                val intent = Intent(context, CompositeItemDetailsActivity::class.java)
                intent.putExtra(MainActivity.COMPOSITE_KEY, rentItem.scannable as CompositeItem)
                intent.putExtra(MainActivity.EDIT_KEY, false)

                context?.startActivity(intent)
            }*/

            if (holder.cvItemMenu.visibility == GONE) {
                holder.etRentedQuantity?.setText(rentItem.outQuantity.toString())
                if (rentItem.backStatus == BackStatus.BACK)
                    holder.cbBack.isChecked = true

                holder.cvItemMenu.visibility = VISIBLE
                holder.rentItemQuantity.visibility = GONE
            } else {
                holder.cvItemMenu.visibility = GONE
                holder.rentItemQuantity.visibility = VISIBLE
            }
        }

        holder.btnUpdateItem.setOnClickListener {
            rentItem.outQuantity = parseInt(holder.etRentedQuantity?.text.toString())
            rentItem.backStatus = if (holder.cbBack.isChecked) BackStatus.BACK else BackStatus.OUT

            onItemUpdate(rentItem)
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val rentItemName: TextView = itemView.tvRentedItemName
        val rentItemStatusBack: TextView = itemView.tvRentItemStatusBack
        val rentItemStatusOut: TextView = itemView.tvRentItemStatusOut
        val rentItemQuantity: TextView = itemView.tvRentedItemQuantity

        val btnUpdateItem: Button = itemView.btnUpdateRentItem
        val etRentedQuantity = itemView.tilRentItemQuantity.editText
        val cbBack: CheckBox = itemView.cbRentItemBack

        val cvItemMenu: ConstraintLayout = itemView.cvItemMenu
    }
}