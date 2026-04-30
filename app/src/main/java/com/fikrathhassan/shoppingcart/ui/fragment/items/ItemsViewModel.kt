package com.fikrathhassan.shoppingcart.ui.fragment.items

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.fikrathhassan.shoppingcart.domain.datasource.CartDataSource
import com.fikrathhassan.shoppingcart.domain.datasource.ItemDataSource
import com.fikrathhassan.shoppingcart.domain.model.CartTotals
import com.fikrathhassan.shoppingcart.domain.model.Item
import com.fikrathhassan.shoppingcart.ui.base.BaseVM
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ItemsViewModel @Inject constructor(
    private val itemDataSource: ItemDataSource,
    private val cartDataSource: CartDataSource
) : BaseVM() {

    private val _itemList = MutableLiveData<List<Item>>()
    val itemList: LiveData<List<Item>> = _itemList
    private fun setItemList(list: List<Item>) {
        _itemList.value = list
    }

    private var job: Job? = null
    fun getItems() {
        job?.cancel()
        job = viewModelScope.launch {
            itemDataSource.getItemList(
                onStart = {
                    setProgress(true)
                },
                onComplete = {
                    setProgress(false)
                }
            ).collect {
                setItemList(it)
            }
        }
    }

    fun addItem(item: Item) {
        viewModelScope.launch {
            cartDataSource.addItem(
                item = item
            ).collect {
                val message = if (it == 1) {
                    "${item.name} has been added to cart"
                } else {
                    "${item.name} quantity has been updated to $it"
                }
                setToastMessage(message)
            }
        }
    }


    val cartTotals: LiveData<CartTotals> = cartDataSource.getCartTotals().asLiveData()

    init {
        getItems()
    }

}