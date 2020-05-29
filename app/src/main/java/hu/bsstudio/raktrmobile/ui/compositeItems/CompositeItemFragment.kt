package hu.bsstudio.raktrmobile.ui.compositeItems

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
import hu.bsstudio.raktrmobile.adapter.CompositeItemAdapter
import hu.bsstudio.raktrmobile.model.CompositeItem
import hu.bsstudio.raktrmobile.network.interactor.CompositeItemInteractor
import kotlinx.android.synthetic.main.fragment_composites.*

class CompositeItemFragment : Fragment() {

    private val interactor = CompositeItemInteractor()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_composites, container, false)

        MainActivity.CURRENT_TYPE = "composite"

        return root
    }

    override fun onResume() {
        super.onResume()

        srlCompositeItems.setOnRefreshListener {
            interactor.getCompositeItems(
                this::loadItems
            ) { Snackbar.make(rvComposites, "Adat letöltési hiba", Snackbar.LENGTH_LONG).show() }
        }

        val divider: RecyclerView.ItemDecoration =
            DividerItemDecoration(context, DividerItemDecoration.VERTICAL)

        rvComposites.layoutManager = LinearLayoutManager(context)
        rvComposites.addItemDecoration(divider)

        interactor.getCompositeItems(
            this::loadItems
        ) { Snackbar.make(rvComposites, "Adat letöltési hiba", Snackbar.LENGTH_LONG).show() }
    }

    private fun loadItems(compositeItems: List<CompositeItem>) {
        val compositeComparator = Comparator { c1: CompositeItem, c2: CompositeItem ->
            c1.name.compareTo(c2.name)
        }

        val adapter = CompositeItemAdapter(
            context,
            compositeItems.sortedWith(compositeComparator)
        )
        rvComposites.adapter = adapter

        srlCompositeItems.isRefreshing = false
    }
}
