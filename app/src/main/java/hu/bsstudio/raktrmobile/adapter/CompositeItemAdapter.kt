package hu.bsstudio.raktrmobile.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import hu.bsstudio.raktrmobile.CompositeItemDetailsActivity
import hu.bsstudio.raktrmobile.DeviceDetailsActivity
import hu.bsstudio.raktrmobile.MainActivity
import hu.bsstudio.raktrmobile.R
import hu.bsstudio.raktrmobile.model.CompositeItem
import kotlinx.android.synthetic.main.li_composite.view.*

class CompositeItemAdapter(
    private val context: Context?,
    private var items: List<CompositeItem>
) : RecyclerView.Adapter<CompositeItemAdapter.ViewHolder>() {

    private val layoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = layoutInflater.inflate(R.layout.li_composite, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val compositeItem = items[position]

        holder.compositeNameTv.text = compositeItem.name
        holder.compositeNumOfItemsTv.text = compositeItem.devices?.size.toString().plus(" ")
            .plus(context?.getString(R.string.device))
        holder.compositeLocationTv.text = compositeItem.location?.name

        holder.itemView.setOnClickListener {
            val intent = Intent(context, CompositeItemDetailsActivity::class.java)
            intent.putExtra(MainActivity.COMPOSITE_KEY, compositeItem)
            intent.putExtra(MainActivity.EDIT_KEY, false)

            context?.startActivity(intent)
        }
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var compositeNameTv: TextView = itemView.tvCompositeNameLi
        var compositeNumOfItemsTv: TextView = itemView.tvNumCompositedItemsLi
        var compositeLocationTv: TextView = itemView.tvCompositeLocationLi
    }
}