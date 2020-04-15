package hu.bsstudio.raktrmobile.ui.compositeItems

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import hu.bsstudio.raktrmobile.R

class CompositeItemFragment : Fragment() {

    private lateinit var compositeItemsViewModel: CompositeItemsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        compositeItemsViewModel =
            ViewModelProviders.of(this).get(CompositeItemsViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_composites, container, false)
        val textView: TextView = root.findViewById(R.id.text_composites)
        compositeItemsViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }
}
