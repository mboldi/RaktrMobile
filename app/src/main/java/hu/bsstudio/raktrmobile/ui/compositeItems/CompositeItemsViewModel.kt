package hu.bsstudio.raktrmobile.ui.compositeItems

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CompositeItemsViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is Composite Items Fragment"
    }
    val text: LiveData<String> = _text
}