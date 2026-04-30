package com.fikrathhassan.shoppingcart.ui.fragment.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.fikrathhassan.shoppingcart.domain.datasource.CartDataSource
import com.fikrathhassan.shoppingcart.domain.model.CartTotals
import com.fikrathhassan.shoppingcart.domain.model.Item
import com.fikrathhassan.shoppingcart.ui.base.BaseVM
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val cartDataSource: CartDataSource
) : BaseVM() {

    val items: LiveData<List<Item>> = cartDataSource.getItemList().asLiveData()
    val cartTotals: LiveData<CartTotals> = cartDataSource.getCartTotals().asLiveData()

    fun increaseItemQuantity(itemId: String) {
        viewModelScope.launch {
            cartDataSource.increaseItemQuantity(itemId = itemId).collect {
            }
        }
    }

    fun reduceItemQuantity(itemId: String) {
        viewModelScope.launch {
            cartDataSource.reduceItemQuantity(itemId = itemId).collect {
            }
        }
    }

}