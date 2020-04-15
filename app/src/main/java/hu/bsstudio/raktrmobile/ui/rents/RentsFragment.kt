package hu.bsstudio.raktrmobile.ui.rents

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import hu.bsstudio.raktrmobile.R

class RentsFragment : Fragment() {

    private lateinit var rentsViewModel: RentsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        rentsViewModel =
            ViewModelProviders.of(this).get(RentsViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_rents, container, false)
        val textView: TextView = root.findViewById(R.id.text_slideshow)
        rentsViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }
}
