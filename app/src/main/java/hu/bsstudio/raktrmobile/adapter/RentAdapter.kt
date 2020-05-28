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
import hu.bsstudio.raktrmobile.RentDetailsActivity
import hu.bsstudio.raktrmobile.model.Rent
import kotlinx.android.synthetic.main.li_rent.view.*

class RentAdapter(
    private val context: Context?,
    private var rents: List<Rent>
) : RecyclerView.Adapter<RentAdapter.ViewHolder>() {
    private val layoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = layoutInflater.inflate(R.layout.li_rent, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return rents.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currRent = rents[position]

        holder.rentDestTv.text = currRent.destination
        holder.renterNameTv.text = currRent.issuer.plus(" -> ").plus(currRent.renter)
        holder.rentDatesTv.text = currRent.outDate.plus(" - ")
            .plus(if (currRent.actBackDate.isNotEmpty()) currRent.actBackDate else currRent.expBackDate)
        holder.rentNumItemsTv.text =
            currRent.rentItems.size.toString().plus(" ").plus(context?.getString(R.string.device))

        holder.itemView.setOnClickListener {
            val intent = Intent(context, RentDetailsActivity::class.java)
            intent.putExtra(MainActivity.RENT_KEY, currRent)
            intent.putExtra(MainActivity.EDIT_KEY, false)

            context?.startActivity(intent)
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val rentDestTv: TextView = itemView.tvRentDestinationLi
        val renterNameTv: TextView = itemView.tvRenterNameLi
        val rentDatesTv: TextView = itemView.tvRentDatesLi
        val rentNumItemsTv: TextView = itemView.tvNumRentedItemsLi
    }
}