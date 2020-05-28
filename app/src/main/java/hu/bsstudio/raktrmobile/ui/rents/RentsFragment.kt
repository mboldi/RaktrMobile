package hu.bsstudio.raktrmobile.ui.rents

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
import hu.bsstudio.raktrmobile.MainActivity
import hu.bsstudio.raktrmobile.R
import hu.bsstudio.raktrmobile.adapter.RentAdapter
import hu.bsstudio.raktrmobile.data.DataProvider
import hu.bsstudio.raktrmobile.model.*
import kotlinx.android.synthetic.main.fragment_rents.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class RentsFragment : Fragment() {

    private var adapter: RentAdapter? = null

    private val dataProvider = DataProvider()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_rents, container, false)

        MainActivity.CURRENT_TYPE = "rent"

        return root
    }

    override fun onResume() {
        super.onResume()

        srlRents.setOnRefreshListener { loadRents() }

        val divider: RecyclerView.ItemDecoration = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)

        rvRents.layoutManager = LinearLayoutManager(context)
        rvRents.addItemDecoration(divider)

        loadRents()
    }

    private fun loadRents() {
        val rents = dataProvider.getRents()

        val rentComparator = Comparator { r1: Rent, r2: Rent ->
            LocalDate.parse(r2.outDate, DateTimeFormatter.ofPattern("yyyy.MM.dd."))
                .compareTo(LocalDate.parse(r1.outDate, DateTimeFormatter.ofPattern("yyyy.MM.dd.")))
        }

        val sortedRents = rents.sortedWith(rentComparator)

        adapter = RentAdapter(context, sortedRents as MutableList<Rent>)
        rvRents.adapter = adapter
        srlRents.isRefreshing = false
    }
}
