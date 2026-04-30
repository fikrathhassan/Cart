package com.fikrathhassan.shoppingcart.ui.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fikrathhassan.shoppingcart.ui.util.helper.livedata.Event

open class BaseVM : ViewModel() {

    private val _progress = MutableLiveData(false)
    val progress: LiveData<Boolean> = _progress
    fun setProgress(value: Boolean) {
        _progress.postValue(value)
    }

    private val _toastMessage = MutableLiveData<Event<String>>()
    val toastMessage: LiveData<Event<String>> = _toastMessage
    fun setToastMessage(message: String) {
        _toastMessage.postValue(Event(message))
    }

}