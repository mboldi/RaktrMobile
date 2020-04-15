package hu.bsstudio.raktrmobile.ui.rents

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class RentsViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is rents Fragment"
    }
    val text: LiveData<String> = _text
}