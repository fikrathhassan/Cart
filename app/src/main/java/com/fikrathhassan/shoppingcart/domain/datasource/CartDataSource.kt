package com.fikrathhassan.shoppingcart.domain.datasource

import com.fikrathhassan.shoppingcart.domain.model.CartTotals
import com.fikrathhassan.shoppingcart.domain.model.Item
import kotlinx.coroutines.flow.Flow

interface CartDataSource {

    fun getItemList(): Flow<List<Item>>

    fun getCartTotals(): Flow<CartTotals>

    fun addItem(
        item: Item
    ): Flow<Int>

    fun increaseItemQuantity(
        itemId: String
    ): Flow<*>

    fun reduceItemQuantity(
        itemId: String
    ): Flow<*>

}