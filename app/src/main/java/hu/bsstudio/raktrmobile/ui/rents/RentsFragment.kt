package hu.bsstudio.raktrmobile.ui.rents

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import hu.bsstudio.raktrmobile.MainActivity
import hu.bsstudio.raktrmobile.R
import hu.bsstudio.raktrmobile.adapter.RentAdapter
import hu.bsstudio.raktrmobile.data.DataProvider
import hu.bsstudio.raktrmobile.model.Rent
import hu.bsstudio.raktrmobile.network.interactor.RentInteractor
import kotlinx.android.synthetic.main.fragment_rents.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class RentsFragment : Fragment() {

    private var adapter: RentAdapter? = null

    private val dataProvider = DataProvider()

    private val interactor = RentInteractor()

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

        srlRents.setOnRefreshListener {
            interactor.getRents(
                this::loadRents
            ) {
                Snackbar.make(rvRents, "Nem sikerült elérni a szervert", Snackbar.LENGTH_LONG)
                    .show()
            }
        }

        val divider: RecyclerView.ItemDecoration =
            DividerItemDecoration(context, DividerItemDecoration.VERTICAL)

        rvRents.layoutManager = LinearLayoutManager(context)
        rvRents.addItemDecoration(divider)

        interactor.getRents(
            this::loadRents
        ) { Snackbar.make(rvRents, "Nem sikerült elérni a szervert", Snackbar.LENGTH_LONG).show() }
    }

    private fun loadRents(rents: List<Rent>) {
        val rentComparator = Comparator { r1: Rent, r2: Rent ->
            LocalDate.parse(r2.outDate, DateTimeFormatter.ofPattern("yyyy.MM.dd."))
                .compareTo(LocalDate.parse(r1.outDate, DateTimeFormatter.ofPattern("yyyy.MM.dd.")))
        }

        val sortedRents = rents.sortedWith(rentComparator)

        adapter = RentAdapter(context, sortedRents)
        rvRents.adapter = adapter
        srlRents.isRefreshing = false
    }
}
