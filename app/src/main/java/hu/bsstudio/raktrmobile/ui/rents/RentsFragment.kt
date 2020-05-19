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
import hu.bsstudio.raktrmobile.R
import hu.bsstudio.raktrmobile.adapter.RentAdapter
import hu.bsstudio.raktrmobile.model.*
import kotlinx.android.synthetic.main.fragment_rents.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class RentsFragment : Fragment() {

    private var adapter: RentAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_rents, container, false)

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
        val rentItems: MutableList<RentItem> = mutableListOf()

        rentItems += RentItem(
            3L,
            BackStatus.OUT,
            2,
            Device(
                1L,
                "BSS320-1",
                "B-1111",
                Category(1L, "Videó"),
                Location(3L, "110"),
                "Sony",
                1,
                "11223344",
                DeviceStatus.GOOD,
                "PMW-320",
                4500000,
                8000
            )
        )

        rentItems += RentItem(
            2L,
            BackStatus.BACK,
            1,
            Device(
                2L,
                "EX3",
                "B-1121",
                Category(1L, "Videó"),
                Location(2L, "Páncél"),
                "Sony",
                1,
                "11223344",
                DeviceStatus.GOOD,
                "PMW-EX3",
                2500000,
                4500
            )
        )

        val rentItems2: MutableList<RentItem> = mutableListOf()

        rentItems2 += RentItem(
            1L,
            BackStatus.OUT,
            1,
            Device(
                2L,
                "EX3",
                "B-1121",
                Category(1L, "Videó"),
                Location(2L, "Páncél"),
                "Sony",
                1,
                "11223344",
                DeviceStatus.GOOD,
                "PMW-EX3",
                2500000,
                4500
            )
        )

        val devices: MutableList<Device> = mutableListOf()
        devices += Device(
            1L,
            "BSS320-1",
            "B-1111",
            Category(1L, "Videó"),
            Location(3L, "110"),
            "Sony",
            1,
            "11223344",
            DeviceStatus.GOOD,
            "PMW-320",
            4500000,
            8000
        )
        devices += Device(
            2L,
            "EX3",
            "B-1121",
            Category(1L, "Videó"),
            Location(2L, "Páncél"),
            "Sony",
            1,
            "11223344",
            DeviceStatus.GOOD,
            "PMW-EX3",
            2500000,
            4500
        )

        rentItems2 += RentItem(
            5L,
            BackStatus.OUT,
            1,
            CompositeItem("B-WLRack-01", 1L, "Wireless rack", devices, Location(2L, "Páncél"))
        )

        val rents = mutableListOf<Rent>()
        rents += (Rent(
            "2020.02.27.",
            "TEDxRajk",
            rentItems2,
            "2020.02.28.",
            1,
            "Benedek",
            "Benedek",
            "2020.02.26."
        ))
        rents += (Rent(
            "",
            "Régi valami",
            rentItems,
            "2019.04.30.",
            1,
            "ifjBoldi",
            "idBoldi",
            "2019.04.28."
        ))
        rents += (Rent(
            "",
            "Simonyi konferencia",
            rentItems,
            "2020.04.30.",
            1,
            "ifjBoldi",
            "idBoldi",
            "2020.04.28."
        ))

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
